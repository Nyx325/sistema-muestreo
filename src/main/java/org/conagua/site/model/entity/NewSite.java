package org.conagua.site.model.entity;

import java.util.Optional;

public class NewSite implements INewSite {
  private String clave;
  private String nombre;
  private Optional<String> cuenca;
  private Optional<String> claveAcuifero;
  private Optional<String> acuifero;
  private Optional<String> organismo;
  private Optional<String> direccionLocal;
  private Optional<String> estado;
  private Optional<String> municipio;
  private Optional<String> cuerpoAgua;
  private Optional<String> tipoCuerpo;
  private Optional<String> subtipoCuerpo;
  private Optional<String> latitud;
  private Optional<String> longitud;
  private Optional<String> uso;
  private Optional<String> lugarToma;
  private Optional<String> cliente;

  public NewSite(String clave, String nombre, Optional<String> cuenca, Optional<String> claveAcuifero,
      Optional<String> acuifero, Optional<String> organismo, Optional<String> direccionLocal,
      Optional<String> estado, Optional<String> municipio, Optional<String> cuerpoAgua,
      Optional<String> tipoCuerpo, Optional<String> subtipoCuerpo, Optional<String> latitud,
      Optional<String> longitud, Optional<String> uso, Optional<String> lugarToma,
      Optional<String> cliente) {
    this.clave = clave;
    this.nombre = nombre;
    this.cuenca = cuenca;
    this.claveAcuifero = claveAcuifero;
    this.acuifero = acuifero;
    this.organismo = organismo;
    this.direccionLocal = direccionLocal;
    this.estado = estado;
    this.municipio = municipio;
    this.cuerpoAgua = cuerpoAgua;
    this.tipoCuerpo = tipoCuerpo;
    this.subtipoCuerpo = subtipoCuerpo;
    this.latitud = latitud;
    this.longitud = longitud;
    this.uso = uso;
    this.lugarToma = lugarToma;
    this.cliente = cliente;
  }

  // Implementación de los métodos getters y setters
  @Override
  public String getClave() {
    return clave;
  }

  @Override
  public void setClave(String clave) {
    this.clave = clave;
  }

  @Override
  public String getNombre() {
    return nombre;
  }

  @Override
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public Optional<String> getCuenca() {
    return cuenca;
  }

  @Override
  public void setCuenca(Optional<String> cuenca) {
    this.cuenca = cuenca;
  }

  @Override
  public Optional<String> getClaveAcuifero() {
    return claveAcuifero;
  }

  @Override
  public void setClaveAcuifero(Optional<String> claveAcuifero) {
    this.claveAcuifero = claveAcuifero;
  }

  @Override
  public Optional<String> getAcuifero() {
    return acuifero;
  }

  @Override
  public void setAcuifero(Optional<String> acuifero) {
    this.acuifero = acuifero;
  }

  @Override
  public Optional<String> getOrganismo() {
    return organismo;
  }

  @Override
  public void setOrganismo(Optional<String> organismo) {
    this.organismo = organismo;
  }

  @Override
  public Optional<String> getDireccionLocal() {
    return direccionLocal;
  }

  @Override
  public void setDireccionLocal(Optional<String> direccionLocal) {
    this.direccionLocal = direccionLocal;
  }

  @Override
  public Optional<String> getEstado() {
    return estado;
  }

  @Override
  public void setEstado(Optional<String> estado) {
    this.estado = estado;
  }

  @Override
  public Optional<String> getMunicipio() {
    return municipio;
  }

  @Override
  public void setMunicipio(Optional<String> municipio) {
    this.municipio = municipio;
  }

  @Override
  public Optional<String> getCuerpoAgua() {
    return cuerpoAgua;
  }

  @Override
  public void setCuerpoAgua(Optional<String> cuerpoAgua) {
    this.cuerpoAgua = cuerpoAgua;
  }

  @Override
  public Optional<String> getTipoCuerpo() {
    return tipoCuerpo;
  }

  @Override
  public void setTipoCuerpo(Optional<String> tipoCuerpo) {
    this.tipoCuerpo = tipoCuerpo;
  }

  @Override
  public Optional<String> getSubtipoCuerpo() {
    return subtipoCuerpo;
  }

  @Override
  public void setSubtipoCuerpo(Optional<String> subtipoCuerpo) {
    this.subtipoCuerpo = subtipoCuerpo;
  }

  @Override
  public Optional<String> getLatitud() {
    return latitud;
  }

  @Override
  public void setLatitud(Optional<String> latitud) {
    this.latitud = latitud;
  }

  @Override
  public Optional<String> getLongitud() {
    return longitud;
  }

  @Override
  public void setLongitud(Optional<String> longitud) {
    this.longitud = longitud;
  }

  @Override
  public Optional<String> getUso() {
    return uso;
  }

  @Override
  public void setUso(Optional<String> uso) {
    this.uso = uso;
  }

  @Override
  public Optional<String> getLugarToma() {
    return lugarToma;
  }

  @Override
  public void setLugarToma(Optional<String> lugarToma) {
    this.lugarToma = lugarToma;
  }

  @Override
  public Optional<String> getCliente() {
    return cliente;
  }

  @Override
  public void setCliente(Optional<String> cliente) {
    this.cliente = cliente;
  }
}
