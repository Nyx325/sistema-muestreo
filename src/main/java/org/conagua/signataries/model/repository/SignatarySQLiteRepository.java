package org.conagua.signataries.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.conagua.common.model.entity.Criteria;
import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.Search;
import org.conagua.common.model.exceptions.InvalidInstanceException;
import org.conagua.common.model.repository.SQLiteRepository;
import org.conagua.signataries.model.entity.ISignatary;
import org.conagua.signataries.model.entity.Signatary;
import org.conagua.signataries.model.entity.SignataryCriteria;

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

    try (Connection conn = DriverManager.getConnection(dbUrl);
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
  public IEntity fromResultSet(ResultSet rs) throws SQLException, IllegalArgumentException {
    if (rs.next()) {
      try {
        return new Signatary(
            UUID.fromString(rs.getString("id")),
            rs.getBoolean("active"),
            rs.getString("first_name"),
            rs.getString("mid_name"),
            rs.getString("father_lastname"),
            rs.getString("mother_lastname"));
      } catch (SQLException e) {
        throw new IllegalArgumentException("El result set no tiene las columnas esperadas");
      }
    } else {
      return null;
    }
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    String[] columns = getColumns();

    StringBuilder query = new StringBuilder("SELECT ")
        .append(String.join(",", columns))
        .append(" FROM ")
        .append(tableName);

    if (!criteria.isEmpty()) {

    }

    return null;
  }
}
