package org.conagua.signataryDetail.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.signataryDetail.model.entity.*;

import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.entity.QueryData;
import org.conagua.common.model.repository.SQLiteRepository;

public class SignataryDetailSQLiteRepository extends SQLiteRepository<ISignataryDetail, SignataryDetailCriteria> {
  public SignataryDetailSQLiteRepository() {
    super("signataries_details");
  }

  public SignataryDetailSQLiteRepository(String tableName) {
    super(tableName);
  }

  public SignataryDetailSQLiteRepository(IConfig config) {
    super("signataries_details", config);
  }

  public SignataryDetailSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( folio UUID PRIMARY KEY NOT NULL,")
        .append("signatary UUID,")
        .append("action UUID,")
        .append("FOREIGN KEY (action) REFERENCES Actions(id) ")
        .append("ON DELETE SET NULL ON UPDATE CASCADE,")
        .append("FOREIGN KEY (signatary) REFERENCES Signataries(id) ")
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
        "signatary",
        "action"
    };
    return columns;
  }

  @Override
  public String idField() {
    return "folio";
  }

  @Override
  public String[] fieldsWithoutId(ISignataryDetail obj) {
    String[] fields = {
        obj.getSignatary().toString(),
        obj.getAction().toString()
    };

    return fields;
  }

  @Override
  public ISignataryDetail fromResultSet(ResultSet rs) throws SQLException {
    return new SignataryDetail(
        UUID.fromString(rs.getString("folio")),
        UUID.fromString(rs.getString("action")),
        UUID.fromString(rs.getString("signatary")));
  }

  @Override
  protected List<String> getConditions(SignataryDetailCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.action.isPresent()) {
      conditions.add("action=?");
    }

    if (c.signatary.isPresent()) {
      conditions.add("signatary=?");
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, SignataryDetailCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.action.isPresent()) {
      UUID active = c.action.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.signatary.isPresent()) {
      UUID active = c.signatary.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
