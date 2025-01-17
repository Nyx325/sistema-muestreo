package org.conagua.samples.model.repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.conagua.common.model.entity.*;
import org.conagua.common.model.configs.*;
import org.conagua.common.model.repository.*;

import org.conagua.samples.model.entity.*;

public class SampleSQLiteRepository extends SQLiteRepository<ISample, SampleCriteria> {
  public SampleSQLiteRepository() {
    super("Samples");
  }

  public SampleSQLiteRepository(String tableName) {
    super(tableName);
  }

  public SampleSQLiteRepository(IConfig config) {
    super("Samples", config);
  }

  public SampleSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("receipt_date DATE NOT NULL,")
        .append("sample_date DATETIME NOT NULL,")
        .append("conagua_ai_id INT NOT NULL UNIQUE,")
        .append("sample_number INT NOT NULL,")
        .append("project VARCHAR(50) NOT NULL,")
        .append("sampling_plan VARCHAR(10) NOT NULL,")
        .append("sampler UUID,")
        .append("site UUID,")
        .append("standard UUID,")
        .append("FOREIGN KEY (sampler) REFERENCES Signataries(id) ON DELETE SET NULL ON UPDATE CASCADE,")
        .append("FOREIGN KEY (site) REFERENCES Sites(id) ON DELETE SET NULL ON UPDATE CASCADE)");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public String[] getColumnsWithoutId() {
    return new String[] {
        "receipt_date",
        "sample_date",
        "conagua_ai_id",
        "sample_number",
        "project",
        "sampling_plan",
        "sampler",
        "site",
        "standard"
    };
  }

  @Override
  public String idField() {
    return "id";
  }

  private String uuidToString(UUID uuid) {
    return uuid != null ? uuid.toString() : null;
  }

  @Override
  public String[] fieldsWithoutId(ISample obj) {
    return new String[] {
        obj.getReceiptDate().toString(),
        obj.getSampleDate().toString(),
        String.valueOf(obj.getConaguaAiId()),
        String.valueOf(obj.getSampleNumber()),
        obj.getProject(),
        obj.getSamplingPlan(),
        uuidToString(obj.getSampler()),
        uuidToString(obj.getSite()),
        uuidToString(obj.getStandard())
    };
  }

  @Override
  public ISample fromResultSet(ResultSet rs) throws SQLException {
    return new Sample(
        UUID.fromString(rs.getString("id")),
        true, // Asume que los registros son activos
        rs.getTimestamp("sample_date").toLocalDateTime(),
        rs.getDate("receipt_date").toLocalDate(),
        rs.getLong("conagua_ai_id"),
        rs.getLong("sample_number"),
        rs.getString("project"),
        rs.getString("sampling_plan"),
        rs.getString("sampler") != null ? UUID.fromString(rs.getString("sampler")) : null,
        rs.getString("site") != null ? UUID.fromString(rs.getString("site")) : null,
        rs.getString("standard") != null ? UUID.fromString(rs.getString("standard")) : null);
  }

  @Override
  protected List<String> getConditions(SampleCriteria c) {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active = ?");
    }

    if (c.sampler.isPresent()) {
      conditions.add("sampler = ?");
    }

    if (c.site.isPresent()) {
      conditions.add("site = ?");
    }

    if (c.std.isPresent()) {
      conditions.add("standard = ?");
    }

    if (c.sampleDate.isPresent()) {
      conditions.add("sample_date = ?");
    }

    if (c.receiptDate.isPresent()) {
      conditions.add("receipt_date = ?");
    }

    if (c.conaguaAiId.isPresent()) {
      conditions.add("conagua_ai_id LIKE ?");
    }

    if (c.sampleNumber.isPresent()) {
      conditions.add("sample_number LIKE ?");
    }

    if (c.samplingPlan.isPresent()) {
      conditions.add(getSqlStringCriteriaFilter("sampling_plan", c.samplingPlan));
    }

    if (c.project.isPresent()) {
      conditions.add(getSqlStringCriteriaFilter("project", c.project));
    }

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, SampleCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      pstmt.setBoolean(paramIndex++, c.active.get());
    }

    if (c.sampler.isPresent()) {
      UUID id = c.sampler.get();
      pstmt.setString(paramIndex++, id.toString());
    }

    if (c.site.isPresent()) {
      UUID id = c.site.get();
      pstmt.setString(paramIndex++, id.toString());
    }

    if (c.std.isPresent()) {
      UUID id = c.std.get();
      pstmt.setString(paramIndex++, id.toString());
    }

    if (c.sampleDate.isPresent()) {
      LocalDateTime sampleDate = c.sampleDate.get();
      pstmt.setString(paramIndex++, sampleDate.toString());
    }

    if (c.receiptDate.isPresent()) {
      LocalDate receiptDate = c.receiptDate.get();
      pstmt.setString(paramIndex++, receiptDate.toString());
    }

    if (c.conaguaAiId.isPresent()) {
      long aiId = c.conaguaAiId.get();
      pstmt.setLong(paramIndex++, aiId);
    }

    if (c.sampleNumber.isPresent()) {
      long sampleNumber = c.sampleNumber.get();
      pstmt.setLong(paramIndex++, sampleNumber);
    }

    if (c.samplingPlan.isPresent()) {
      String value = stringOfCriteriaToBind(c.samplingPlan);
      pstmt.setString(paramIndex++, value);
    }

    if (c.project.isPresent()) {
      String value = stringOfCriteriaToBind(c.project);
      pstmt.setString(paramIndex++, value);
    }

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
