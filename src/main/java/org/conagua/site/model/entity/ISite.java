package org.conagua.site.model.entity;

import java.util.*;
import org.conagua.common.model.entity.*;

public interface ISite extends IEntity, ILogicalDeletable, ITablePrintable {
  public String getClave();

  public void setClave(String clave);

  public String getNombre();

  public void setNombre(String nombre);

  public Optional<String> getCuenca();

  public void setCuenca(Optional<String> cuenca);

  public Optional<String> getClaveAcuifero();

  public void setClaveAcuifero(Optional<String> claveAcuifero);

  public Optional<String> getAcuifero();

  public void setAcuifero(Optional<String> acuifero);

  public Optional<String> getOrganismo();

  public void setOrganismo(Optional<String> organismo);

  public Optional<String> getDireccionLocal();

  public void setDireccionLocal(Optional<String> direccionLocal);

  public Optional<String> getEstado();

  public void setEstado(Optional<String> estado);

  public Optional<String> getMunicipio();

  public void setMunicipio(Optional<String> municipio);

  public Optional<String> getCuerpoAgua();

  public void setCuerpoAgua(Optional<String> cuerpoAgua);

  public Optional<String> getTipoCuerpo();

  public void setTipoCuerpo(Optional<String> tipoCuerpo);

  public Optional<String> getSubtipoCuerpo();

  public void setSubtipoCuerpo(Optional<String> subtipoCuerpo);

  public Optional<String> getLatitud();

  public void setLatitud(Optional<String> latitud);

  public Optional<String> getLongitud();

  public void setLongitud(Optional<String> longitud);

  public Optional<String> getUso();

  public void setUso(Optional<String> uso);

  public Optional<String> getLugarToma();

  public void setLugarToma(Optional<String> lugarToma);

  public Optional<String> getCliente();

  public void setCliente(Optional<String> cliente);

  public String getSiteDescription();
}
