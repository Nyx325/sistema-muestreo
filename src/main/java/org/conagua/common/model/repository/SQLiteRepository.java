package org.conagua.common.model.repository;

import java.sql.*;
import java.util.UUID;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.InvalidInstanceException;
import org.conagua.common.model.exceptions.TableNotExistsException;

/**
 * Clase abstracta que representa un repositorio SQL genérico para manejar
 * operaciones básicas sobre una tabla de base de datos. Las subclases deben
 * implementar los métodos abstractos para adaptarse a las entidades específicas
 * y la estructura de la tabla.
 */
public abstract class SQLiteRepository implements IRepository {
  /**
   * Configuración de distintas variables de entorno.
   */
  protected final Config cfg;

  /**
   * Nombre de la tabla asociada al repositorio.
   */
  protected final String tableName;

  /**
   * Constructor que inicializa el nombre de la tabla y la configuración.
   * 
   * @param tableName Nombre de la tabla asociada al repositorio.
   */
  public SQLiteRepository(String tableName) {
    this.tableName = tableName;
    this.cfg = Config.getInstance();
  }

  /**
   * Verifica si la tabla asociada al repositorio existe en la base de datos.
   * 
   * @throws SQLException            Si ocurre un error en la conexión.
   * @throws TableNotExistsException Si la tabla no existe
   */
  public boolean tableExists() throws SQLException {
    String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?";

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, tableName);
      try (ResultSet rs = pstmt.executeQuery()) {
        return rs.next();
      }
    }
  }

  /**
   * Agrega una nueva entidad a la tabla asociada.
   * 
   * @param data La entidad que se desea insertar.
   * @throws SQLException             Si ocurre un error en la conexión,
   *                                  validación o
   *                                  ejecución de la consulta.
   * @throws InvalidInstanceException Si ocurre un error al validar la
   *                                  instancia de data
   * @throws IllegalArgumentException Si el número de columnas esperadas
   *                                  no coincide con el número de valores
   *                                  que se obtienen de getValues()
   */
  @Override
  public void add(IEntity data) throws SQLException, InvalidInstanceException, IllegalArgumentException {
    if (!tableExists())
      createTable();

    this.checkInstance(data);

    // Obtener los nombres de las columnas y los valores de la entidad
    String[] columns = getColumns();
    String[] values = genValues(data);

    if (columns.length != values.length)
      throw new IllegalArgumentException("El número de columnas y valores no coincide.");

    // Construir dinámicamente la consulta
    StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
    query.append(String.join(", ", columns));
    query.append(") VALUES (");

    for (int i = 0; i < columns.length - 1; i++)
      query.append("?,");

    query.append("?)");

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      for (int i = 0; i < values.length; i++)
        pstmt.setString(i + 1, values[i]);

      pstmt.executeUpdate();
    }
  }

  @Override
  public void update(IEntity data) throws SQLException, InvalidInstanceException {
    this.checkInstance(data);

    // Obtener los nombres de las columnas y los valores de la entidad
    String[] columns = getColumns();
    String[] values = genValues(data);

    if (columns.length != values.length)
      throw new IllegalArgumentException("El número de columnas y valores no coincide.");

    StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" ");

    query.append("SET ");
    for (int i = 0; i < columns.length; i++) {
      if (i > 0)
        query.append(", ");
      query.append(columns[i]).append("= ?");
    }

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      for (int i = 0; i < values.length; i++)
        pstmt.setString(i + 1, values[i]);

      pstmt.executeUpdate();
    }
  }

  @Override
  public IEntity get(UUID id) throws SQLException, InvalidInstanceException {
    String[] columns = this.getColumns();
    StringBuilder query = new StringBuilder("SELECT ")
        .append(String.join(",", columns))
        .append(" FROM ")
        .append(tableName)
        .append(" WHERE ")
        .append(idField())
        .append(" = ?");

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.setString(1, id.toString());
      try (ResultSet rs = pstmt.executeQuery()) {
        return fromResultSet(rs);
      }
    }
  }

  @Override
  public void delete(UUID id) throws SQLException, InvalidInstanceException {
    IEntity data = this.get(id);
    this.delete(data);
  }

  public void delete(IEntity data) throws SQLException, InvalidInstanceException {
    if (data instanceof ILogicalDeletable) {
      ILogicalDeletable d = (ILogicalDeletable) data;
      d.setActive(false);
      this.update(data);
      return;
    }

    StringBuilder query = new StringBuilder("DELETE FROM ")
        .append(tableName)
        .append(" WHERE ")
        .append(idField())
        .append("=?");

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.setString(1, data.getId().toString());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw e;
    }
  }

  /**
   * Crea la tabla asociada al repositorio si no existe.
   * 
   * @throws Exception Si ocurre un error al ejecutar la consulta de creación.
   */
  public abstract void createTable() throws SQLException;

  /**
   * Valida que la instancia proporcionada sea del tipo esperado por el
   * repositorio.
   * 
   * @param obj La entidad a verificar.
   * @throws InvalidInstanceException Si la instancia no es del tipo esperado.
   */
  public abstract void checkInstance(IEntity obj) throws InvalidInstanceException;

  /**
   * Obtiene los nombres de las columnas de la tabla asociada al repositorio.
   * 
   * @return Un arreglo con los nombres de las columnas.
   */
  public abstract String[] getColumns();

  /**
   * Genera los valores que se usarán en una consulta a partir de la entidad
   * proporcionada.
   * 
   * @param obj La entidad a procesar.
   * @return Un arreglo con los valores en el mismo orden que las columnas.
   * @throws InvalidInstanceException Si se recibe un IEntity de una instancia
   *                                  inválida
   */
  public abstract String[] genValues(IEntity obj);

  /**
   * @return nombre que corresponde al campo que es el ID o PK de la
   *         tabla
   */
  public abstract String idField();

  /**
   * Genera una instancia de un IEntity a partir de un ResultSet proveniente
   * de una consulta SELECT
   * 
   * @return instancia del registro
   * @throws IllegalArgumentException si los campos que brinda la consulta
   *                                  no son válidos para crear una instancia
   *                                  de un registro
   */
  public abstract IEntity fromResultSet(ResultSet rs) throws SQLException;
}
