package org.conagua.common.model.entity;

import java.sql.*;

public class QueryData implements AutoCloseable {
  private Connection conn;
  private PreparedStatement pstmt;
  private ResultSet rs;

  public QueryData(Connection conn, PreparedStatement pstmt, ResultSet rs) {
    this.conn = conn;
    this.pstmt = pstmt;
    this.rs = rs;
  }

  public ResultSet getRs() {
    return rs;
  }

  @Override
  public void close() throws SQLException {
    rs.close();
    pstmt.close();
    conn.close();
  }
}
