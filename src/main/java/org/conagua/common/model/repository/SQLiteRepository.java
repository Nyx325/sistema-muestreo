package org.conagua.common.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    String[] columns = getColumnsWithoutId();
    String[] values = fieldsWithoutId(data);

    if (columns.length != values.length)
      throw new IllegalArgumentException("El número de columnas y valores no coincide.");

    // Construir dinámicamente la consulta
    StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (")
        .append(idField()).append(", ")
        .append(String.join(", ", columns))
        .append(") VALUES (")
        .append("?,");

    for (int i = 0; i < columns.length - 1; i++)
      query.append("?,");

    query.append("?)");

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

      pstmt.setString(1, data.getId().toString());

      for (int i = 0; i < values.length; i++)
        pstmt.setString(i + 2, values[i]);

      pstmt.executeUpdate();
    }
  }

  @Override
  public void update(IEntity data) throws SQLException, InvalidInstanceException {
    if (!tableExists())
      createTable();

    this.checkInstance(data);

    // Obtener los nombres de las columnas y los valores de la entidad
    String[] columns = getColumnsWithoutId();
    String[] values = fieldsWithoutId(data);

    if (columns.length != values.length)
      throw new IllegalArgumentException("El número de columnas y valores no coincide.");

    StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" ");

    query.append("SET ");
    for (int i = 0; i < columns.length; i++) {
      if (i > 0)
        query.append(", ");
      query.append(columns[i]).append("= ?");
    }

    query.append(" WHERE ")
        .append(idField())
        .append("=?");

    try (
        Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      int i;
      for (i = 0; i < values.length; i++)
        pstmt.setString(i + 1, values[i]);

      pstmt.setString(i + 1, data.getId().toString());

      pstmt.executeUpdate();
    }
  }

  @Override
  public IEntity get(UUID id) throws SQLException, InvalidInstanceException {
    if (!tableExists())
      createTable();

    String[] columns = this.getColumnsWithoutId();
    StringBuilder query = new StringBuilder("SELECT ")
        .append(idField()).append(",")
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
        if (rs.next())
          return fromResultSet(rs);
        else
          return null;
      }
    }
  }

  @Override
  public void delete(UUID id) throws SQLException, InvalidInstanceException {
    IEntity data = this.get(id);
    this.delete(data);
  }

  public void delete(IEntity data) throws SQLException, InvalidInstanceException {
    if (!tableExists())
      createTable();

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

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    if (page <= 0)
      throw new IllegalArgumentException("El número de página debe ser mayor que 0");

    List<String> conditions = getConditions(criteria);

    StringBuilder whereQuery = new StringBuilder();
    if (!conditions.isEmpty())
      whereQuery.append(" WHERE ").append(String.join(" AND ", conditions));

    long totalPages = totalPagesQuery(whereQuery.toString(), criteria);
    return searchQuery(whereQuery.toString(), criteria, page, totalPages);
  }

  /**
   * Calcula el número total de páginas basado en los criterios de búsqueda.
   * 
   * @param whereQuery La consulta SQL con las condiciones de búsqueda.
   * @param criteria   Los criterios de búsqueda.
   * @return El número total de páginas.
   * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
   */
  protected long totalPagesQuery(String whereQuery, Criteria criteria) throws SQLException {
    StringBuilder query = new StringBuilder("SELECT COUNT(*) ")
        .append(" FROM ")
        .append(tableName)
        .append(whereQuery);

    try (QueryData queryData = this.criteriaQuery(query.toString(), criteria, null)) {

      if (!queryData.getRs().next())
        throw new SQLException("El resultado de un COUNT(*) deberia ser un número");

      long totalRecords = queryData.getRs().getLong(1);
      long totalPages = (long) Math.ceil((double) totalRecords / cfg.getPageSize());
      return totalPages == 0 ? 1 : totalPages;
    }
  }

  /**
   * Ejecuta una consulta para recuperar los resultados de búsqueda.
   * 
   * @param whereQuery La consulta SQL con las condiciones de búsqueda.
   * @param criteria   Los criterios de búsqueda.
   * @param page       La página actual.
   * @param totalPages El número total de páginas.
   * @return Un objeto {@link Search} con los resultados.
   * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
   */
  protected Search searchQuery(String whereQuery, Criteria criteria, long page, long totalPages)
      throws SQLException {
    long pageSize = cfg.getPageSize();
    long offset = (page - 1) * pageSize;
    String[] columns = getColumnsWithoutId();

    StringBuilder query = new StringBuilder("SELECT ")
        .append(idField()).append(",")
        .append(String.join(",", columns))
        .append(" FROM ")
        .append(tableName)
        .append(whereQuery)
        .append(" LIMIT ?")
        .append(" OFFSET ?");

    try (QueryData queryData = this.criteriaQuery(query.toString(), criteria, offset)) {
      List<IEntity> result = new ArrayList<>();
      while (queryData.getRs().next()) {
        IEntity s = fromResultSet(queryData.getRs());
        result.add(s);
      }

      return new Search(totalPages, page, criteria, result);
    }
  }

  /**
   * Ejecuta y sustituye los valores en un {@link PreparedStatement} de
   * una consulta SQL basada en los criterios proporcionados.
   * Recibe un offset el cual puede ser null, sin embargo el que este
   * valor sea null significa que no se pretende paginar la busqueda,
   * es decir se omite un LIMIT y un OFFSET necesario para el conteo
   * total de registros existentes para obtener numero de paginas
   * 
   * @param query    La consulta SQL.
   * @param criteria Los criterios de búsqueda.
   * @param offset   El desplazamiento para la paginación (puede ser null).
   * @return Un {@link QueryData} con los resultados de la consulta.
   * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
   */
  protected abstract QueryData criteriaQuery(String query, Criteria criteria, Long offset) throws SQLException;

  /**
   * Genera una lista de cadenas correspondientes a los campos que se
   * brindan en el criterio.
   *
   * Ej. [ "active = ?", "id = ?" ]
   * 
   * @param criteria el criterio de la busqueda
   * @return Lista de campos a sustituir en la busqueda
   * @throws IllegalArgumentException si se brinda una instancia incorrecta de
   *                                  {@link Criteria}
   */
  protected abstract List<String> getConditions(Criteria criteria) throws IllegalArgumentException;

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
   * Obtiene los nombres de las columnas de la tabla asociada al repositorio
   * omitiendo la columna del ID.
   * 
   * @return Un arreglo con los nombres de las columnas omitiendo el ID.
   */
  public abstract String[] getColumnsWithoutId();

  /**
   * Genera los valores que se usarán en una consulta a partir de la entidad
   * proporcionada. El campo del ID será omitido
   * 
   * @param obj La entidad a procesar.
   * @return Un arreglo con los valores en el mismo orden que las columnas
   *         omitiendo el ID.
   * @throws InvalidInstanceException Si se recibe un IEntity de una instancia
   *                                  inválida
   */
  public abstract String[] fieldsWithoutId(IEntity obj);

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
