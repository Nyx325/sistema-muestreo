package org.conagua.common.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.*;

public abstract class SQLiteRepository<E extends IEntity, C> implements IRepository<E, C> {
  protected final Config cfg;
  protected final String tableName;

  public SQLiteRepository(String tableName) {
    this.tableName = tableName;
    this.cfg = Config.getInstance();
  }

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

  @Override
  public void add(E data) throws Exception {
    if (!tableExists())
      createTable();

    String[] columns = getColumnsWithoutId();
    String[] values = fieldsWithoutId(data);

    if (columns.length != values.length)
      throw new Exception("El número de columnas y valores no coincide.");

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
  public void update(E data) throws SQLException {
    if (!tableExists())
      createTable();

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
  public Optional<E> get(UUID id) throws SQLException {
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
          return Optional.of(fromResultSet(rs));
        else
          return Optional.empty();
      }
    }
  }

  @Override
  public void delete(UUID id) throws SQLException {
    this.delete(id);
  }

  public void delete(E data) throws SQLException {
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
  public Search<E, C> getBy(C criteria, long page) throws Exception {
    if (page <= 0)
      throw new IllegalArgumentException("El número de página debe ser mayor que 0");

    List<String> conditions = getConditions(criteria);

    StringBuilder whereQuery = new StringBuilder();
    if (!conditions.isEmpty())
      whereQuery.append(" WHERE ").append(String.join(" AND ", conditions));

    long totalPages = totalPagesQuery(whereQuery.toString(), criteria);
    Search<E, C> s = searchQuery(whereQuery.toString(), criteria, page, totalPages);
    return s;
  }

  protected long totalPagesQuery(String whereQuery, C criteria) throws SQLException {
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

  protected Search<E, C> searchQuery(String whereQuery, C criteria, long page, long totalPages)
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
      if (queryData.getRs().next()) {

        List<E> result = new ArrayList<>();
        E s = fromResultSet(queryData.getRs());
        result.add(s);

        while (queryData.getRs().next()) {
          s = fromResultSet(queryData.getRs());
          result.add(s);
        }

        return new Search<E, C>(totalPages, page, criteria, result);
      } else {
        return new Search<E, C>(totalPages, page, criteria);
      }

    }
  }

  protected abstract QueryData criteriaQuery(String query, C criteria, Long offset) throws SQLException;

  protected abstract List<String> getConditions(C criteria);

  public abstract void createTable() throws SQLException;

  public abstract String[] getColumnsWithoutId();

  public abstract String[] fieldsWithoutId(E obj);

  public abstract String idField();

  public abstract E fromResultSet(ResultSet rs) throws SQLException;
}
