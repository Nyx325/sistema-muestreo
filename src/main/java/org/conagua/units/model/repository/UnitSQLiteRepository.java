package org.conagua.units.model.repository;

import java.sql.*;
import java.util.*;
import org.conagua.units.model.entity.*;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.repository.SQLiteRepository;

public class UnitSQLiteRepository extends SQLiteRepository<IUnit, UnitCriteria> {
  public UnitSQLiteRepository() {
    super("Units");
  }

  public UnitSQLiteRepository(String tableName) {
    super(tableName);
  }

  public UnitSQLiteRepository(IConfig config) {
    super("Units", config);
  }

  public UnitSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("long_name TEXT NOT NULL,")
        .append("short_name VARCHAR(10) NOT NULL,")
        .append("window_type TEXT NOT NULL,")
        .append("regex TEXT")
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
        "long_name",
        "short_name",
        "window_type",
        "regex"
    };
    return columns;
  }

  @Override
  public String idField() {
    return "id";
  }

  private Optional<String> strToOpt(String s) {
    return s == null || s.isEmpty() ? Optional.empty() : Optional.of(s);
  }

  private String optToStr(Optional<String> str) {
    return !str.isPresent() || str.get().isEmpty() ? null : str.get();
  }

  @Override
  public String[] fieldsWithoutId(IUnit obj) {
    String[] fields = {
        Boolean.toString(obj.isActive()),
        obj.getLongName(),
        obj.getShortName(),
        obj.getWindowType().toString(),
        optToStr(obj.getRegex())
    };
    return fields;
  }

  @Override
  public IUnit fromResultSet(ResultSet rs) throws SQLException {
    return new Unit(
        UUID.fromString(rs.getString("id")),
        Boolean.parseBoolean(rs.getString("active")),
        rs.getString("long_name"),
        rs.getString("short_name"),
        WindowType.parseWindowType(rs.getString("window_type")),
        strToOpt(rs.getString("regex")));
  }

  @Override
  protected List<String> getConditions(UnitCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.regex.isPresent()) {
      String filter = getSqlStringCriteriaFilter("regex", c.regex);
      conditions.add(filter);
    }

    if (c.shortName.isPresent()) {
      String filter = getSqlStringCriteriaFilter("short_name", c.shortName);
      conditions.add(filter);
    }

    if (c.longName.isPresent()) {
      String filter = getSqlStringCriteriaFilter("long_name", c.longName);
      conditions.add(filter);
    }

    if (c.windowType.isPresent()) {
      conditions.add("window_type = ?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, UnitCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      Boolean active = c.active.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.longName.isPresent()) {
      pstmt.setString(paramIndex++, stringOfCriteriaToBind(c.longName));
    }

    if (c.shortName.isPresent()) {
      pstmt.setString(paramIndex++, stringOfCriteriaToBind(c.shortName));
    }

    if (c.regex.isPresent()) {
      pstmt.setString(paramIndex++, stringOfCriteriaToBind(c.regex));
    }

    if (c.windowType.isPresent()) {
      pstmt.setString(paramIndex++, c.windowType.get().toString());
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
