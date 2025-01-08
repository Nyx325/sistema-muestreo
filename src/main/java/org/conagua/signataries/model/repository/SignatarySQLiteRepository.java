package org.conagua.signataries.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.signataries.model.entity.*;
import org.conagua.common.model.repository.SQLiteRepository;

public class SignatarySQLiteRepository extends SQLiteRepository<ISignatary, SignataryCriteria> {
  public SignatarySQLiteRepository() {
    super("Signataries");
  }

  public SignatarySQLiteRepository(String tableName) {
    super(tableName);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("first_name VARCHAR(50) NOT NULL,")
        .append("mid_name VARCHAR(50),")
        .append("father_lastname VARCHAR(50) NOT NULL,")
        .append("mother_lastname VARCHAR(50)")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public String[] getColumnsWithoutId() {
    String[] arr = {
        "active",
        "first_name",
        "mid_name",
        "father_lastname",
        "mother_lastname"
    };

    return arr;
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(ISignatary s) {
    String[] values = {
        Boolean.toString(s.isActive()),
        s.getFirstName(),
        s.getMidName().isPresent() ? s.getMidName().get() : null,
        s.getFatherLastname(),
        s.getMotherLastname().isPresent() ? s.getMidName().get() : null
    };

    return values;
  }

  @Override
  public ISignatary fromResultSet(ResultSet rs) throws SQLException {
    return new Signatary(
        UUID.fromString(rs.getString("id")),
        rs.getBoolean("active"),
        rs.getString("first_name"),
        rs.getString("mid_name"),
        rs.getString("father_lastname"),
        rs.getString("mother_lastname"));
  }

  @Override
  protected List<String> getConditions(SignataryCriteria c) throws IllegalArgumentException {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.midName.isPresent()) {
      StringCriteria name = c.midName.get();

      if (name.isEqMode())
        conditions.add("mid_name = ?");

      if (name.isLikeMode())
        conditions.add("mid_name LIKE ?");
    }

    if (c.firstName.isPresent()) {
      StringCriteria name = c.firstName.get();

      if (name.isEqMode())
        conditions.add("first_name = ?");

      if (name.isLikeMode())
        conditions.add("first_name LIKE ?");
    }

    if (c.fatherLastname.isPresent()) {
      StringCriteria name = c.fatherLastname.get();

      if (name.isEqMode())
        conditions.add("father_lastname = ?");

      if (name.isLikeMode())
        conditions.add("father_lastname LIKE ?");
    }

    if (c.motherLastname.isPresent()) {
      StringCriteria name = c.motherLastname.get();

      if (name.isEqMode())
        conditions.add("mother_lastname = ?");

      if (name.isLikeMode())
        conditions.add("mother_lastname LIKE ?");

    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, SignataryCriteria criteria, Long offset) throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    SignataryCriteria c = (SignataryCriteria) criteria;

    if (c.active.isPresent()) {
      Boolean active = c.active.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.firstName.isPresent()) {
      StringCriteria name = c.firstName.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (c.midName.isPresent()) {
      StringCriteria name = c.midName.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (c.fatherLastname.isPresent()) {
      StringCriteria name = c.fatherLastname.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (c.motherLastname.isPresent()) {
      StringCriteria name = c.motherLastname.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (offset != null) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset);
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
