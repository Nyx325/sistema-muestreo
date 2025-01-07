package org.conagua.signataries.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.*;
import org.conagua.common.model.repository.*;

import org.conagua.signataries.model.entity.*;

/**
 * Implementación de un repositorio para manejar entidades de tipo Signatary
 * en una base de datos SQLite.
 * 
 * Esta clase extiende {@link SQLiteRepository} y proporciona métodos
 * específicos
 * para operaciones CRUD y búsquedas avanzadas relacionadas con la tabla
 * "Signataries".
 */
public class SignatarySQLiteRepository extends SQLiteRepository {
  public SignatarySQLiteRepository() {
    super("Signataries");
  }

  public SignatarySQLiteRepository(String tableName) {
    super(tableName);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("first_name VARCHAR(50) NOT NULL,")
        .append("mid_name VARCHAR(50),")
        .append("father_lastname VARCHAR(50) NOT NULL,")
        .append("mother_lastname VARCHAR(50)")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public void checkInstance(IEntity obj) throws InvalidInstanceException {
    if (obj instanceof ISignatary == false)
      throw new InvalidInstanceException("Se debe tener una instancia ISignatary");
  }

  @Override
  public String[] getColumns() {
    String[] arr = {
        "id",
        "active",
        "first_name",
        "mid_name",
        "father_lastname",
        "mother_lastname"
    };

    return arr;
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] genValues(IEntity obj) {
    ISignatary sig = (ISignatary) obj;

    String[] values = {
        sig.getId().toString(),
        Boolean.toString(sig.isActive()),
        sig.getFirstName(),
        sig.getMidName(),
        sig.getFatherLastname(),
        sig.getMotherLastname(),
    };

    return values;
  }

  @Override
  public IEntity fromResultSet(ResultSet rs) throws SQLException {
    return new Signatary(
        UUID.fromString(rs.getString("id")),
        rs.getBoolean("active"),
        rs.getString("first_name"),
        rs.getString("mid_name"),
        rs.getString("father_lastname"),
        rs.getString("mother_lastname"));
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    if (page <= 0)
      throw new IllegalArgumentException("El número de página debe ser mayor que 0");

    if (!(criteria instanceof SignataryCriteria))
      throw new IllegalArgumentException("criteria debe ser de tipo SignataryCriteria");

    SignataryCriteria c = (SignataryCriteria) criteria;

    List<String> conditions = new ArrayList<>();
    if (c.active != null)
      conditions.add("active = ?");

    if (c.firstName != null)
      conditions.add("first_name LIKE ?");

    if (c.midName != null)
      conditions.add("mid_name LIKE ?");

    if (c.fatherLastname != null)
      conditions.add("father_lastname LIKE ?");

    if (c.motherLastname != null)
      conditions.add("mother_lastname LIKE ?");

    StringBuilder whereQuery = new StringBuilder();
    if (!conditions.isEmpty()) {
      whereQuery.append(" WHERE ").append(String.join(" AND ", conditions));
    }

    long totalPages = totalPagesQuery(whereQuery.toString(), c);
    return searchQuery(whereQuery.toString(), c, page, totalPages);

  }

  /**
   * Calcula el número total de páginas basado en los criterios de búsqueda.
   * 
   * @param whereQuery La consulta SQL con las condiciones de búsqueda.
   * @param criteria   Los criterios de búsqueda.
   * @return El número total de páginas.
   * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
   */
  private long totalPagesQuery(String whereQuery, SignataryCriteria criteria) throws SQLException {
    StringBuilder query = new StringBuilder("SELECT COUNT(*) ")
        .append(" FROM ")
        .append(tableName)
        .append(whereQuery);

    try (QueryData queryData = this.criteriaQuery(query.toString(), criteria, null)) {

      if (!queryData.getRs().next())
        throw new SQLException("El resultado de un COUNT(*) deberia ser un número");

      long totalRecords = queryData.getRs().getLong(1);
      return (long) Math.ceil((double) totalRecords / cfg.getPageSize());
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
  private Search searchQuery(String whereQuery, SignataryCriteria criteria, long page, long totalPages)
      throws SQLException {
    long pageSize = cfg.getPageSize();
    long offset = (page - 1) * pageSize;
    String[] columns = getColumns();

    StringBuilder query = new StringBuilder("SELECT ")
        .append(String.join(",", columns))
        .append(" FROM ")
        .append(tableName)
        .append(whereQuery)
        .append(" LIMIT ?")
        .append(" OFFSET ?");

    try (QueryData queryData = this.criteriaQuery(query.toString(), criteria, offset)) {
      List<IEntity> result = new ArrayList<>();
      int values = 0;
      while (queryData.getRs().next()) {
        values++;
        IEntity s = fromResultSet(queryData.getRs());
        result.add(s);
      }

      return new Search(totalPages, page, criteria, result);
    }
  }

  /**
   * Ejecuta una consulta SQL basada en los criterios proporcionados.
   * 
   * @param query    La consulta SQL.
   * @param criteria Los criterios de búsqueda.
   * @param offset   El desplazamiento para la paginación (puede ser null).
   * @return Un {@link ResultSet} con los resultados de la consulta.
   * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
   */
  private QueryData criteriaQuery(String query, SignataryCriteria criteria, Long offset) throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query); // en esta linea sucede la excepcion

    int paramIndex = 1;

    // Configuración de parámetros dinámicos
    if (!criteria.isEmpty()) {
      SignataryCriteria c = (SignataryCriteria) criteria;

      if (c.active != null)
        pstmt.setBoolean(paramIndex++, c.active);

      if (c.firstName != null)
        pstmt.setString(paramIndex++, "%" + c.firstName + "%");

      if (c.midName != null)
        pstmt.setString(paramIndex++, "%" + c.midName + "%");

      if (c.fatherLastname != null)
        pstmt.setString(paramIndex++, "%" + c.fatherLastname + "%");

      if (c.motherLastname != null)
        pstmt.setString(paramIndex++, "%" + c.motherLastname + "%");
    }

    if (offset != null) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset);
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
