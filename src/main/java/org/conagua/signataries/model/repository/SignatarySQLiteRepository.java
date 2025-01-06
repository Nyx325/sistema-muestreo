package org.conagua.signataries.model.repository;

import org.conagua.common.model.repository.SQLRepository;

public class SignatarySQLiteRepository extends SQLRepository {
  public SignatarySQLiteRepository() {
    super("Signataries");
  }

  @Override
  public void createTable() throws Exception {
    StringBuilder query = new StringBuilder("CREATE TABLE Signataries (")
        .append("id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append(")");
  }
}
