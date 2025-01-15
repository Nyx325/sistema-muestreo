package org.conagua.parameterDetail.model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.parameterDetail.model.entity.*;
import org.conagua.parameters.model.entity.Parameter;
import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.repository.SQLiteRepository;

public class ParameterDetailSQLiteRepository extends SQLiteRepository<IParameterDetail, ParameterDetailCriteria> {
  public ParameterDetailSQLiteRepository() {
    super("Signataries");
  }

  public ParameterDetailSQLiteRepository(String tableName) {
    super(tableName);
  }

  public ParameterDetailSQLiteRepository(IConfig config) {
    super("Signataries", config);
  }

  public ParameterDetailSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("parameter UUID,")
        .append("standard UUID,")
        .append("FOREIGN KEY (parameter) REFERENCES Parameters(id) ")
        .append("ON DELETE SET NULL ON UPDATE CASCADE,")
        .append("FOREIGN KEY (standard) REFERENCES Standards(id) ")
        .append("ON DELETE SET NULL ON UPDATE CASCADE")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public String[] getColumnsWithoutId() {
    String[] arr = {
        "parameter",
        "standard"
    };
    return arr;
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(IParameterDetail obj) {
    String[] values = {
        obj.getParameter().toString(),
        obj.getStandard().toString()
    };
    return values;
  }

  @Override
  public IParameterDetail fromResultSet(ResultSet rs) throws SQLException {
    return new ParameterDetail(
        UUID.fromString(rs.getString("id")),
        UUID.fromString(rs.getString("parameter")),
        UUID.fromString(rs.getString("standard")));
  }

  @Override
  protected List<String> getConditions(ParameterDetailCriteria criteria) {
    List<String> conditions = new ArrayList<>();

    if (criteria.param.isPresent()) {
      conditions.add("parameter=?");
    }

    if (criteria.std.isPresent()) {
      conditions.add("standard=?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, ParameterDetailCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.param.isPresent()) {
      UUID param = c.param.get();
      pstmt.setString(paramIndex++, param.toString());
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
