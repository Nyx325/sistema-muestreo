package org.conagua.actions.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.actions.model.entity.*;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.repository.SQLiteRepository;

public class ActionSQLiteRepository extends SQLiteRepository<IAction, ActionCriteria> {
  public ActionSQLiteRepository() {
    super("Actions");
  }

  public ActionSQLiteRepository(String tableName) {
    super(tableName);
  }

  public ActionSQLiteRepository(IConfig config) {
    super("Actions", config);
  }

  public ActionSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("name VARCHAR(50) NOT NULL,")
        .append("param UUID,")
        .append("FOREIGN KEY (param) REFERENCES Parameters(id)")
        .append("ON DELETE SET NULL ON UPDATE CASCADE")
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
        "name",
        "param",
    };
    return columns;
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(IAction obj) {
    String param = obj.getParam().isPresent() ? obj.getParam().get().toString() : null;
    String[] fields = {
        Boolean.toString(obj.isActive()),
        obj.getName(),
        param
    };
    return fields;
  }

  @Override
  public IAction fromResultSet(ResultSet rs) throws SQLException {
    String paramStr = rs.getString("param");
    Optional<UUID> param = paramStr == null || paramStr.isEmpty()
        ? Optional.empty()
        : Optional.of(UUID.fromString(paramStr));

    return new Action(
        UUID.fromString(rs.getString("id")),
        Boolean.parseBoolean(rs.getString("active")),
        rs.getString("name"),
        param);
  }

  @Override
  protected List<String> getConditions(ActionCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.name.isPresent()) {
      conditions.add(getSqlStringCriteriaFilter("name", c.name));
    }

    if (c.param.isPresent()) {
      conditions.add("param=?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, ActionCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      Boolean active = c.active.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.name.isPresent()) {
      pstmt.setString(paramIndex++, stringOfCriteriaToBind(c.name));
    }

    if (c.param.isPresent()) {
      pstmt.setString(paramIndex++, c.param.get().toString());
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
