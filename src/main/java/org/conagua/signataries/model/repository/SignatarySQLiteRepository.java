package org.conagua.signataries.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.*;
import org.conagua.common.model.repository.*;

import org.conagua.signataries.model.entity.*;

public class SignatarySQLiteRepository extends SQLiteRepository {
  public SignatarySQLiteRepository() {
    super("Signataries");
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS Signataries (")
        .append("id UUID PRIMARY KEY NOT NULL,")
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
  public void checkInstance(IEntity obj) throws InvalidInstanceException {
    if (obj instanceof ISignatary == false)
      throw new InvalidInstanceException("Se debe tener una instancia ISignatary");
  }

  @Override
  public String[] getColumns() {
    String[] arr = {
        "id",
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
  public String[] genValues(IEntity obj) {
    ISignatary sig = (ISignatary) obj;

    String[] values = {
        sig.getId().toString(),
        Boolean.toString(sig.isActive()),
        sig.getFirstName(),
        sig.getMidName(),
        sig.getFatherLastname(),
        sig.getMotherLastname(),
    };

    return values;
  }

  @Override
  public IEntity fromResultSet(ResultSet rs) throws SQLException {
    if (rs.next()) {
      return new Signatary(
          UUID.fromString(rs.getString("id")),
          rs.getBoolean("active"),
          rs.getString("first_name"),
          rs.getString("mid_name"),
          rs.getString("father_lastname"),
          rs.getString("mother_lastname"));
    } else {
      return null;
    }
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    if (page <= 0)
      throw new IllegalArgumentException("El número de página debe ser mayor que 0");

    StringBuilder whereQuery = new StringBuilder(" FROM ")
        .append(tableName);

    if (!(criteria instanceof SignataryCriteria))
      throw new IllegalArgumentException("criteria debe ser de tipo SignataryCriteria");

    SignataryCriteria c = (SignataryCriteria) criteria;

    List<String> conditions = new ArrayList<>();
    if (c.active != null)
      conditions.add("active = ?");

    if (c.firstName != null)
      conditions.add("first_name LIKE ?");

    if (c.midName != null)
      conditions.add("mid_name LIKE ?");

    if (c.fatherLastname != null)
      conditions.add("father_lastname LIKE ?");

    if (c.motherLastname != null)
      conditions.add("mother_lastname LIKE ?");

    if (!conditions.isEmpty()) {
      whereQuery.append(" WHERE ").append(String.join(" AND ", conditions));
    }

    long totalPages = totalPagesQuery(whereQuery.toString(), c);
    return searchQuery(whereQuery.toString(), c, page, totalPages);

  }

  private long totalPagesQuery(String whereQuery, SignataryCriteria criteria) throws SQLException {
    StringBuilder query = new StringBuilder("SELECT COUNT(*) ")
        .append(whereQuery);

    try (ResultSet rs = this.criteriaQuery(query.toString(), criteria, null)) {

      if (!rs.next())
        throw new SQLException("El resultado de un COUNT(*) deberia ser un número");

      long totalRecords = rs.getLong(1);
      return (long) Math.ceil((double) totalRecords / cfg.getPageSize());
    }
  }

  private Search searchQuery(String whereQuery, SignataryCriteria criteria, long page, long totalPages)
      throws SQLException {
    long pageSize = cfg.getPageSize();
    long offset = (page - 1) * pageSize;
    String[] columns = getColumns();

    StringBuilder query = new StringBuilder("SELECT ")
        .append(String.join(",", columns))
        .append(whereQuery)
        .append(" LIMIT=?")
        .append(" OFFSET=?");

    try (ResultSet rs = this.criteriaQuery(query.toString(), criteria, offset)) {
      List<IEntity> result = new ArrayList<>();
      while (rs.next()) {
        IEntity s = fromResultSet(rs);
        result.add(s);
      }
      rs.close();

      return new Search(totalPages, page, criteria, result);
    }
  }

  private ResultSet criteriaQuery(String query, SignataryCriteria criteria, Long offset) throws SQLException {
    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query)) {

      int paramIndex = 1;

      // Configuración de parámetros dinámicos
      if (!criteria.isEmpty()) {
        SignataryCriteria c = (SignataryCriteria) criteria;

        if (c.active != null)
          pstmt.setBoolean(paramIndex++, c.active);

        if (c.firstName != null)
          pstmt.setString(paramIndex++, "%" + c.firstName + "%");

        if (c.midName != null)
          pstmt.setString(paramIndex++, "%" + c.midName + "%");

        if (c.fatherLastname != null)
          pstmt.setString(paramIndex++, "%" + c.fatherLastname + "%");

        if (c.motherLastname != null)
          pstmt.setString(paramIndex++, "%" + c.motherLastname + "%");
      }

      if (offset != null) {
        pstmt.setLong(paramIndex++, cfg.getPageSize());
        pstmt.setLong(paramIndex, offset);
      }

      return pstmt.executeQuery();
    }
  }
}
