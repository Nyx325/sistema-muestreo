package org.conagua.unitsPerStandard.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.repository.SQLiteRepository;

import org.conagua.unitsPerStandard.model.entity.*;

public class UnitPerStdSQLiteRepository extends SQLiteRepository<IUnitPerStd, UnitPerStdCriteria> {
  public UnitPerStdSQLiteRepository() {
    super("UnitPerStds");
  }

  public UnitPerStdSQLiteRepository(String tableName) {
    super(tableName);
  }

  public UnitPerStdSQLiteRepository(IConfig config) {
    super("UnitPerStds", config);
  }

  public UnitPerStdSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("standard UUID,")
        .append("unit UUID,")
        .append("FOREIGN KEY (standard) REFERENCES Standards(id) ")
        .append("ON DELETE SET NULL ON UPDATE CASCADE,")
        .append("FOREIGN KEY (unit) REFERENCES Units(id) ")
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
        "standard",
        "unit"
    };
    return columns;
  }

  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(IUnitPerStd obj) {
    String[] fields = {
        obj.getStandard().toString(),
        obj.getUnit().toString()
    };
    return fields;
  }

  @Override
  public IUnitPerStd fromResultSet(ResultSet rs) throws SQLException {
    return new UnitPerStd(
        UUID.fromString(rs.getString("id")),
        UUID.fromString(rs.getString("standard")),
        UUID.fromString(rs.getString("unit")));
  }

  @Override
  protected List<String> getConditions(UnitPerStdCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.unit.isPresent()) {
      conditions.add("unit=?");
    }

    if (c.std.isPresent()) {
      conditions.add("standard=?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, UnitPerStdCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.unit.isPresent()) {
      UUID unit = c.unit.get();
      pstmt.setString(paramIndex++, unit.toString());
    }

    if (c.std.isPresent()) {
      UUID std = c.std.get();
      pstmt.setString(paramIndex++, std.toString());
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
