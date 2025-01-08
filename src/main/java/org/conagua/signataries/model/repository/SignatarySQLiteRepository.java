package org.conagua.signataries.model.repository;

import java.sql.*;
import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.*;
import org.conagua.common.model.repository.*;

import org.conagua.signataries.model.entity.*;

/**
 * Implementación de un repositorio para manejar entidades de tipo Signatary
 * en una base de datos SQLite.
 * 
 * Esta clase extiende {@link SQLiteRepository} y proporciona métodos
 * específicos
 * para operaciones CRUD y búsquedas avanzadas relacionadas con la tabla
 * "Signataries".
 */
public class SignatarySQLiteRepository extends SQLiteRepository {
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
  public void checkInstance(IEntity obj) throws InvalidInstanceException {
    if (obj instanceof ISignatary == false)
      throw new InvalidInstanceException("Se debe tener una instancia ISignatary");
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
  public String[] fieldsWithoutId(IEntity obj) {
    ISignatary sig = (ISignatary) obj;

    String[] values = {
        Boolean.toString(sig.isActive()),
        sig.getFirstName(),
        sig.getMidName(),
        sig.getFatherLastname(),
        sig.getMotherLastname()
    };

    return values;
  }

  @Override
  public IEntity fromResultSet(ResultSet rs) throws SQLException {
    return new Signatary(
        UUID.fromString(rs.getString("id")),
        rs.getBoolean("active"),
        rs.getString("first_name"),
        rs.getString("mid_name"),
        rs.getString("father_lastname"),
        rs.getString("mother_lastname"));
  }

  @Override
  protected List<String> getConditions(Criteria criteria) throws IllegalArgumentException {
    if (!(criteria instanceof SignataryCriteria))
      throw new IllegalArgumentException("Criteria debe ser instancia de SignataryCriteria");

    SignataryCriteria c = (SignataryCriteria) criteria;

    List<String> conditions = new ArrayList<>();

    if (c.active != null)
      conditions.add("active=?");

    if (c.midName != null)
      conditions.add("mid_name LIKE ?");

    if (c.firstName != null)
      conditions.add("first_name LIKE ?");

    if (c.fatherLastname != null)
      conditions.add("father_lastname LIKE ?");

    if (c.motherLastname != null)
      conditions.add("mother_lastname LIKE ?");

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, Criteria criteria, Long offset) throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    // Configuración de parámetros dinámicos
    if (!criteria.isEmpty()) {
      SignataryCriteria c = (SignataryCriteria) criteria;

      if (c.active != null)
        pstmt.setString(paramIndex++, c.active.toString());

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

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
