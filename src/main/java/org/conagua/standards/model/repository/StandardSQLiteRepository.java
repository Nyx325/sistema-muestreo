package org.conagua.standards.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.*;
import org.conagua.common.model.repository.*;
import org.conagua.standards.model.entity.*;

public class StandardSQLiteRepository extends SQLiteRepository {
  public StandardSQLiteRepository(String table) {
    super(table);
  }

  public StandardSQLiteRepository() {
    super("Standards");
  }

  @Override
  public void createTable() throws SQLException {
    // CREATE TABLE IF NOT EXISTS Standards(
    // id UUID PRIMARY KEY NOT NULL,
    // name VARCHAR(100) NOT NULL
    // );
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("name VARCHAR(100) NOT NULL")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }

  }

  @Override
  public void checkInstance(IEntity obj) throws InvalidInstanceException {
    if (obj instanceof IStandard == false)
      throw new InvalidInstanceException("obj debe ser instancia de IStandard");
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] getColumnsWithoutId() {
    String[] fields = {
        "active",
        "name"
    };

    return fields;
  }

  @Override
  public String[] fieldsWithoutId(IEntity obj) {
    IStandard std = (IStandard) obj;

    String[] values = {
        Boolean.toString(std.isActive()),
        std.getName()
    };

    return values;
  }

  @Override
  public IEntity fromResultSet(ResultSet rs) throws SQLException {
    return new Standard(
        UUID.fromString(rs.getString("id")),
        rs.getString("name"),
        rs.getBoolean("active"));
  }

  @Override
  protected List<String> getConditions(Criteria criteria) throws IllegalArgumentException {
    if (!(criteria instanceof StandardCriteria))
      throw new IllegalArgumentException("Criteria debe ser instancia de StandardCriteria");

    StandardCriteria c = (StandardCriteria) criteria;
    List<String> conditions = new ArrayList<>();

    if (c.active != null)
      conditions.add("active=?");

    if (c.name != null)
      conditions.add("name LIKE ?");

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, Criteria criteria, Long offset) throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    // Configuración de parámetros dinámicos
    if (!criteria.isEmpty()) {
      StandardCriteria c = (StandardCriteria) criteria;

      if (c.active != null)
        pstmt.setString(paramIndex++, c.active.toString());

      if (c.name != null)
        pstmt.setString(paramIndex++, "%" + c.name + "%");
    }

    if (offset != null) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset);
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
