package org.conagua.parameters.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.entity.StringCriteria;
import org.conagua.parameters.model.entity.*;
import org.conagua.common.model.repository.SQLiteRepository;

public class ParameterSQLiteRepository extends SQLiteRepository<IParameter, ParameterCriteria> {
  public ParameterSQLiteRepository() {
    super("Parameters");
  }

  public ParameterSQLiteRepository(String tableName) {
    super(tableName);
  }

  public ParameterSQLiteRepository(IConfig config) {
    super("Parameters", config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("name VARCHAR(50) NOT NULL")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public String[] getColumnsWithoutId() {
    String[] columns = {
        "active",
        "name"
    };
    return columns;
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(IParameter obj) {
    String[] fields = {
        Boolean.toString(obj.isActive()),
        obj.getName()
    };

    return fields;
  }

  @Override
  public IParameter fromResultSet(ResultSet rs) throws SQLException {
    return new Parameter(
        UUID.fromString(rs.getString("id")),
        Boolean.parseBoolean(rs.getString("active")),
        rs.getString("name"));
  }

  @Override
  protected List<String> getConditions(ParameterCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.name.isPresent()) {
      StringCriteria name = c.name.get();
      if (name.isEqMode()) {
        conditions.add("name=?");
      }

      if (name.isLikeMode()) {
        conditions.add("name LIKE ?");
      }
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, ParameterCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      Boolean active = c.active.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.name.isPresent()) {
      StringCriteria name = c.name.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
