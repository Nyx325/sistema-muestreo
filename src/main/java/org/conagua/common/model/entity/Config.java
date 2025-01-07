package org.conagua.common.model.entity;

public class Config {
  private static Config instance;

  private final String dbUrl;
  private final long pageSize;

  private Config() {
    this.dbUrl = "jdbc:sqlite:mi_base_de_datos.db";
    this.pageSize = 15;
  }

  public static Config getInstance() {
    if (Config.instance == null)
      Config.instance = new Config();

    return Config.instance;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public long getPageSize() {
    return pageSize;
  }
}
