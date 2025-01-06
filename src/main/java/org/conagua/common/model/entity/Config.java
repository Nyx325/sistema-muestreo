package org.conagua.common.model.entity;

public class Config {
  private static Config instance;

  private String dbUrl;

  private Config() {
    this.dbUrl = "jdbc:sqlite:mi_base_de_datos.db";
  }

  public static Config getInstance() {
    if (Config.instance == null)
      Config.instance = new Config();

    return Config.instance;
  }

  public String getDbUrl() {
    return dbUrl;
  }
}
