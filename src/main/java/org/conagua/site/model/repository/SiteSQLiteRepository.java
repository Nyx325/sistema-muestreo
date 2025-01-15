package org.conagua.site.model.repository;

import java.sql.*;
import java.util.*;
import org.conagua.common.model.entity.*;
import org.conagua.site.model.entity.*;
import org.conagua.common.model.configs.IConfig;
import org.conagua.common.model.repository.SQLiteRepository;

public class SiteSQLiteRepository extends SQLiteRepository<ISite, SiteCriteria> {
  public SiteSQLiteRepository() {
    super("Sites");
  }

  public SiteSQLiteRepository(String tableName) {
    super(tableName);
  }

  public SiteSQLiteRepository(IConfig config) {
    super("Sites", config);
  }

  public SiteSQLiteRepository(String tableName, IConfig config) {
    super(tableName, config);
  }

  @Override
  public void createTable() throws SQLException {
    StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
        .append(tableName)
        .append("( id UUID PRIMARY KEY NOT NULL,")
        .append("active BOOLEAN NOT NULL,")
        .append("clave VARCHAR(100) NOT NULL,")
        .append("nombre VARCHAR(100) NOT NULL,")
        .append("cuenca VARCHAR(50),")
        .append("clave_acuifero VARCHAR(50),")
        .append("acuifero VARCHAR(50),")
        .append("organismo VARCHAR(50),")
        .append("direccion_local VARCHAR(50),")
        .append("estado VARCHAR(25),")
        .append("municipio VARCHAR(25),")
        .append("cuerpo_agua VARCHAR(50),")
        .append("tipo_cuerpo VARCHAR(50),")
        .append("subtipo_cuerpo VARCHAR(50),")
        .append("latitud VARCHAR(50),")
        .append("longitud VARCHAR(50),")
        .append("uso VARCHAR(50),")
        .append("lugar_toma VARCHAR(100),")
        .append("cliente VARCHAR(50)")
        .append(")");

    try (Connection conn = DriverManager.getConnection(cfg.getDbUrl());
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public String[] getColumnsWithoutId() {
    return new String[] {
        "active", "clave", "nombre", "cuenca", "clave_acuifero", "acuifero", "organismo",
        "direccion_local", "estado", "municipio", "cuerpo_agua", "tipo_cuerpo", "subtipo_cuerpo",
        "latitud", "longitud", "uso", "lugar_toma", "cliente"
    };
  }

  @Override
  public String idField() {
    return "id";
  }

  @Override
  public String[] fieldsWithoutId(ISite s) {
    String cuenca = s.getCuenca().isPresent() ? s.getCuenca().get() : null;
    String claveAcuifero = s.getClaveAcuifero().isPresent() ? s.getClaveAcuifero().get() : null;
    String acuifero = s.getAcuifero().isPresent() ? s.getAcuifero().get() : null;
    String organismo = s.getOrganismo().isPresent() ? s.getOrganismo().get() : null;
    String direccionLocal = s.getDireccionLocal().isPresent() ? s.getDireccionLocal().get() : null;
    String estado = s.getEstado().isPresent() ? s.getEstado().get() : null;
    String municipio = s.getMunicipio().isPresent() ? s.getMunicipio().get() : null;
    String cuerpoAgua = s.getCuerpoAgua().isPresent() ? s.getCuerpoAgua().get() : null;
    String tipoCuerpo = s.getTipoCuerpo().isPresent() ? s.getTipoCuerpo().get() : null;
    String subtipoCuerpo = s.getSubtipoCuerpo().isPresent() ? s.getSubtipoCuerpo().get() : null;
    String latitud = s.getLatitud().isPresent() ? s.getLatitud().get() : null;
    String longitud = s.getLongitud().isPresent() ? s.getLongitud().get() : null;
    String uso = s.getUso().isPresent() ? s.getUso().get() : null;
    String lugarToma = s.getLugarToma().isPresent() ? s.getLugarToma().get() : null;
    String cliente = s.getCliente().isPresent() ? s.getCliente().get() : null;

    return new String[] {
        Boolean.toString(s.isActive()),
        s.getClave(),
        s.getNombre(),
        cuenca,
        claveAcuifero,
        acuifero,
        organismo,
        direccionLocal,
        estado,
        municipio,
        cuerpoAgua,
        tipoCuerpo,
        subtipoCuerpo,
        latitud,
        longitud,
        uso,
        lugarToma,
        cliente
    };
  }

  private Optional<String> optionalStr(String s) {
    return s == null || s.isEmpty() ? Optional.empty() : Optional.of(s);
  }

  @Override
  public ISite fromResultSet(ResultSet rs) throws SQLException {
    String cuenca = rs.getString("cuenca");
    String claveAcuifero = rs.getString("clave_acuifero");
    String acuifero = rs.getString("acuifero");
    String organismo = rs.getString("organismo");
    String direccionLocal = rs.getString("direccion_local");
    String estado = rs.getString("estado");
    String municipio = rs.getString("municipio");
    String cuerpoAgua = rs.getString("cuerpo_agua");
    String tipoCuerpo = rs.getString("tipo_cuerpo");
    String subtipoCuerpo = rs.getString("subtipo_cuerpo");
    String latitud = rs.getString("latitud");
    String longitud = rs.getString("longitud");
    String uso = rs.getString("uso");
    String lugarToma = rs.getString("lugar_toma");
    String cliente = rs.getString("cliente");

    return new Site(
        UUID.fromString(rs.getString("id")),
        Boolean.parseBoolean(rs.getString("active")),
        rs.getString("clave"),
        rs.getString("nombre"),
        optionalStr(cuenca),
        optionalStr(claveAcuifero),
        optionalStr(acuifero),
        optionalStr(organismo),
        optionalStr(direccionLocal),
        optionalStr(estado),
        optionalStr(municipio),
        optionalStr(cuerpoAgua),
        optionalStr(tipoCuerpo),
        optionalStr(subtipoCuerpo),
        optionalStr(latitud),
        optionalStr(longitud),
        optionalStr(uso),
        optionalStr(lugarToma),
        optionalStr(cliente));
  }

  @Override
  protected List<String> getConditions(SiteCriteria c) throws IllegalArgumentException {
    List<String> conditions = new ArrayList<>();

    if (c.active.isPresent()) {
      conditions.add("active=?");
    }

    if (c.clave.isPresent()) {
      StringCriteria name = c.clave.get();

      if (name.isEqMode())
        conditions.add("clave = ?");

      if (name.isLikeMode())
        conditions.add("clave LIKE ?");
    }

    if (c.nombre.isPresent()) {
      StringCriteria name = c.nombre.get();

      if (name.isEqMode())
        conditions.add("nombre = ?");

      if (name.isLikeMode())
        conditions.add("nombre LIKE ?");
    }

    if (c.cuenca.isPresent()) {
      StringCriteria name = c.cuenca.get();

      if (name.isEqMode())
        conditions.add("cuenca = ?");

      if (name.isLikeMode())
        conditions.add("cuenca LIKE ?");
    }

    if (c.claveAcuifero.isPresent()) {
      StringCriteria name = c.claveAcuifero.get();

      if (name.isEqMode())
        conditions.add("clave_acuifero = ?");

      if (name.isLikeMode())
        conditions.add("clave_acuifero LIKE ?");
    }

    if (c.acuifero.isPresent()) {
      StringCriteria name = c.acuifero.get();

      if (name.isEqMode())
        conditions.add("acuifero = ?");

      if (name.isLikeMode())
        conditions.add("acuifero LIKE ?");
    }

    if (c.organismo.isPresent()) {
      StringCriteria name = c.organismo.get();

      if (name.isEqMode())
        conditions.add("organismo = ?");

      if (name.isLikeMode())
        conditions.add("organismo LIKE ?");
    }

    // Agregar condiciones similares para otros campos

    return conditions;
  }

  @Override
  protected QueryData criteriaQuery(String query, SiteCriteria c, Optional<Long> offset)
      throws SQLException {
    Connection conn = DriverManager.getConnection(cfg.getDbUrl());
    PreparedStatement pstmt = conn.prepareStatement(query);

    int paramIndex = 1;

    if (c.active.isPresent()) {
      Boolean active = c.active.get();
      pstmt.setString(paramIndex++, active.toString());
    }

    if (c.clave.isPresent()) {
      StringCriteria name = c.clave.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    if (c.nombre.isPresent()) {
      StringCriteria name = c.nombre.get();

      if (name.isLikeMode())
        pstmt.setString(paramIndex++, "%" + name.getValue() + "%");

      if (name.isEqMode())
        pstmt.setString(paramIndex++, name.getValue());
    }

    // Agregar parámetros para otros criterios

    if (offset.isPresent()) {
      pstmt.setLong(paramIndex++, cfg.getPageSize());
      pstmt.setLong(paramIndex, offset.get());
    }

    return new QueryData(conn, pstmt, pstmt.executeQuery());
  }
}
