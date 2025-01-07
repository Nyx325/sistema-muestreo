package org.conagua.standards.model.repository;

import java.sql.*;
import java.util.UUID;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.*;
import org.conagua.common.model.repository.*;
import org.conagua.standards.model.entity.IStandard;
import org.conagua.standards.model.entity.Standard;
import org.conagua.standards.model.entity.StandardCriteria;

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

  public IEntity fromResultSet(ResultSet rs) throws SQLException {
    return new Standard(
        UUID.fromString(rs.getString("id")),
        rs.getString("name"),
        rs.getBoolean("active"));
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    if (page <= 0)
      throw new IllegalArgumentException("El número de página debe ser mayor que 0");

    if (!(criteria instanceof StandardCriteria))
      throw new IllegalArgumentException("criteria debe ser de tipo SignataryCriteria");

    return null;
  }
}
