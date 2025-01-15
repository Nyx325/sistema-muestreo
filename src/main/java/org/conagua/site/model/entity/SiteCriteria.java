package org.conagua.site.model.entity;

import java.util.*;

import org.conagua.common.model.entity.StringCriteria;

public class SiteCriteria {
  public Optional<UUID> id;
  public Optional<Boolean> active;
  public Optional<StringCriteria> clave;
  public Optional<StringCriteria> nombre;
  public Optional<StringCriteria> claveAcuifero;
  public Optional<StringCriteria> acuifero;
  public Optional<StringCriteria> organismo;
  public Optional<StringCriteria> direccionLocal;
  public Optional<StringCriteria> estado;
  public Optional<StringCriteria> municipio;
  public Optional<StringCriteria> cuerpoDeAgua;
  public Optional<StringCriteria> tipoCuerpo;
  public Optional<StringCriteria> subtipoCuerpo;
  public Optional<StringCriteria> longitud;
  public Optional<StringCriteria> latitud;
  public Optional<StringCriteria> uso;
  public Optional<StringCriteria> lugarToma;
  public Optional<StringCriteria> cliente;
  public Optional<StringCriteria> cuenca;

  // Constructor que inicializa todos los atributos
  public SiteCriteria(Optional<UUID> id, Optional<Boolean> active, Optional<StringCriteria> clave,
      Optional<StringCriteria> nombre, Optional<StringCriteria> claveAcuifero,
      Optional<StringCriteria> acuifero, Optional<StringCriteria> organismo,
      Optional<StringCriteria> direccionLocal, Optional<StringCriteria> estado,
      Optional<StringCriteria> municipio, Optional<StringCriteria> cuerpoDeAgua,
      Optional<StringCriteria> tipoCuerpo, Optional<StringCriteria> subtipoCuerpo,
      Optional<StringCriteria> longitud, Optional<StringCriteria> latitud,
      Optional<StringCriteria> uso, Optional<StringCriteria> lugarToma,
      Optional<StringCriteria> cliente, Optional<StringCriteria> cuenca) {
    this.id = id;
    this.active = active;
    this.clave = clave;
    this.nombre = nombre;
    this.claveAcuifero = claveAcuifero;
    this.acuifero = acuifero;
    this.organismo = organismo;
    this.direccionLocal = direccionLocal;
    this.estado = estado;
    this.municipio = municipio;
    this.cuerpoDeAgua = cuerpoDeAgua;
    this.tipoCuerpo = tipoCuerpo;
    this.subtipoCuerpo = subtipoCuerpo;
    this.longitud = longitud;
    this.latitud = latitud;
    this.uso = uso;
    this.lugarToma = lugarToma;
    this.cliente = cliente;
    this.cuenca = cuenca;
  }

  // Constructor que inicializa todos los atributos como Optional.empty
  public SiteCriteria() {
    this.id = Optional.empty();
    this.active = Optional.empty();
    this.clave = Optional.empty();
    this.nombre = Optional.empty();
    this.claveAcuifero = Optional.empty();
    this.acuifero = Optional.empty();
    this.organismo = Optional.empty();
    this.direccionLocal = Optional.empty();
    this.estado = Optional.empty();
    this.municipio = Optional.empty();
    this.cuerpoDeAgua = Optional.empty();
    this.tipoCuerpo = Optional.empty();
    this.subtipoCuerpo = Optional.empty();
    this.longitud = Optional.empty();
    this.latitud = Optional.empty();
    this.uso = Optional.empty();
    this.lugarToma = Optional.empty();
    this.cliente = Optional.empty();
    this.cuenca = Optional.empty();
  }
}
