package org.conagua.common.model.configs;

/**
 * Clase que contiene variables de configuracion
 * utilizadas dentro del sistema
 */
public class TestConfig implements IConfig {
  private static TestConfig instance;

  private final String dbUrl;
  private final long pageSize;

  private TestConfig() {
    this.dbUrl = "jdbc:sqlite:mi_base_de_datos.db";
    this.pageSize = 15;
  }

  public static TestConfig getInstance() {
    if (TestConfig.instance == null)
      TestConfig.instance = new TestConfig();

    return TestConfig.instance;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public long getPageSize() {
    return pageSize;
  }
}
