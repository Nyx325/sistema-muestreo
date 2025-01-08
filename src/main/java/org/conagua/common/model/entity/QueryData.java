package org.conagua.common.model.entity;

import java.sql.*;

/**
 * Clase que agrupa una conexión a base de datos, un
 * prepared statement y un result set para ser manipulados
 * y cerrados en caso de ocurrir alguna excepción.
 *
 * @implements {@link AutoCloseable}
 */
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
