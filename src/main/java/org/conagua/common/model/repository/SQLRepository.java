package org.conagua.common.model.repository;

import java.sql.*;
import org.conagua.common.model.entity.*;

/**
 * Clase abstracta que representa un repositorio SQL genérico para manejar
 * operaciones básicas sobre una tabla de base de datos. Las subclases deben
 * implementar los métodos abstractos para adaptarse a las entidades específicas
 * y la estructura de la tabla.
 */
public abstract class SQLRepository implements IRepository {
  /**
   * Configuración de distintas variables de entorno.
   */
  private final String dbUrl;

  /**
   * Nombre de la tabla asociada al repositorio.
   */
  private final String tableName;

  /**
   * Constructor que inicializa el nombre de la tabla y la configuración.
   * 
   * @param tableName Nombre de la tabla asociada al repositorio.
   */
  public SQLRepository(String tableName) {
    this.tableName = tableName;
    this.dbUrl = Config.getInstance().getDbUrl();
  }

  /**
   * Constructor que inicializa el nombre de la tabla y la URL de la
   * base de datos.
   * 
   * @param tableName Nombre de la tabla asociada al repositorio.
   * @param dbUrl     URL de la base de datos.
   */
  public SQLRepository(String tableName, String dbUrl) {
    this.tableName = tableName;
    this.dbUrl = dbUrl;
  }

  /**
   * Verifica si la tabla asociada al repositorio existe en la base de datos.
   * 
   * @throws Exception Si la tabla no existe o ocurre un error en la conexión.
   */
  public void tableExists() throws Exception {
    String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?";

    try (Connection conn = DriverManager.getConnection(dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, tableName);

      ResultSet rs = pstmt.executeQuery();
      if (!rs.next()) {
        throw new Exception("ERROR: La tabla '" + tableName + "' no existe.");
      }
    } catch (SQLException e) {
      throw e;
    }
  }

  /**
   * Agrega una nueva entidad a la tabla asociada.
   * 
   * @param data La entidad que se desea insertar.
   * @throws Exception Si ocurre un error en la conexión, validación o ejecución
   *                   de la consulta.
   */
  @Override
  public void add(IEntity data) throws Exception {
    this.checkInstance(data);

    // Obtener los nombres de las columnas y los valores de la entidad
    String[] columns = getColumns();
    String[] values = genInsertValues(data);

    if (columns.length != values.length) {
      throw new IllegalArgumentException("El número de columnas y valores no coincide.");
    }

    // Construir dinámicamente la consulta
    StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
    query.append(String.join(", ", columns));
    query.append(") VALUES (");

    for (int i = 0; i < columns.length - 1; i++) {
      query.append("?,");
    }
    query.append("?)");

    try (Connection conn = DriverManager.getConnection(dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

      // Asignar los valores a los marcadores
      for (int i = 0; i < values.length; i++) {
        pstmt.setString(i + 1, values[i]);
      }

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
  public abstract void createTable() throws Exception;

  /**
   * Valida que la instancia proporcionada sea del tipo esperado por el
   * repositorio.
   * 
   * @param obj La entidad a verificar.
   * @throws Exception Si la instancia no es del tipo esperado.
   */
  public abstract void checkInstance(IEntity obj) throws Exception;

  /**
   * Obtiene los nombres de las columnas de la tabla asociada al repositorio.
   * 
   * @return Un arreglo con los nombres de las columnas.
   */
  public abstract String[] getColumns();

  /**
   * Genera los valores que se insertarán en la tabla a partir de la entidad
   * proporcionada.
   * 
   * @param obj La entidad a procesar.
   * @return Un arreglo con los valores en el mismo orden que las columnas.
   * @throws Exception Si ocurre un error al procesar la entidad.
   */
  public abstract String[] genInsertValues(IEntity obj) throws Exception;
}
