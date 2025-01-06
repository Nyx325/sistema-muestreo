package org.conagua.common.model.exceptions;

import java.sql.SQLException;

public class TableNotExistsException extends SQLException {
  public TableNotExistsException(String msg) {
    super(msg);
  }
}
