package org.conagua.standard.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.repository.SQLiteRepository;
import org.conagua.standard.model.entity.*;

public class StandardSQLiteRepository extends SQLiteRepository<IStandard, StandardCriteria> {
  public StandardSQLiteRepository() {
    super("Standards");
  }

  public StandardSQLiteRepository(String tableName) {
    super(tableName);
  }

  public StandardSQLiteRepository(IConfig config) {
    super("Parameters", config);
  }

  public StandardSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
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
  public String[] fieldsWithoutId(IStandard obj) {
    String[] fields = {
        Boolean.toString(obj.isActive()),
        obj.getName()
    };

    return fields;
  }

  @Override
  public IStandard fromResultSet(ResultSet rs) throws SQLException {
    return new Standard(
        UUID.fromString(rs.getString("id")),
        rs.getString("name"),
        Boolean.parseBoolean(rs.getString("active")));
  }

  @Override
  protected List<String> getConditions(StandardCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.name.isPresent()) {
      StringCriteria name = c.name.get();

      if (name.isLikeMode())
        conditions.add("name LIKE ?");

      if (name.isEqMode())
        conditions.add("name=?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, StandardCriteria c, Optional<Long> offset) throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      boolean active = c.active.get();
      pstmt.setBoolean(paramIndex++, active);
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
