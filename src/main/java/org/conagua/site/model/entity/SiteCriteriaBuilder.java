package org.conagua.site.model.entity;

import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class SiteCriteriaBuilder {
  private SiteCriteria criteria;

  public SiteCriteriaBuilder() {
    this.criteria = new SiteCriteria();
  }

  public SiteCriteria build() {
    return criteria;
  }

  public SiteCriteriaBuilder id(UUID id) {
    criteria.id = Optional.of(id);
    return this;
  }

  public SiteCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }

  public SiteCriteriaBuilder claveEq(String clave) {
    criteria.clave = Optional.of(new StringCriteria(Mode.EQ, clave));
    return this;
  }

  public SiteCriteriaBuilder claveLike(String clave) {
    criteria.clave = Optional.of(new StringCriteria(Mode.LIKE, clave));
    return this;
  }

  public SiteCriteriaBuilder nombreEq(String nombre) {
    criteria.nombre = Optional.of(new StringCriteria(Mode.EQ, nombre));
    return this;
  }

  public SiteCriteriaBuilder nombreLike(String nombre) {
    criteria.nombre = Optional.of(new StringCriteria(Mode.LIKE, nombre));
    return this;
  }

  public SiteCriteriaBuilder claveAcuiferoEq(String claveAcuifero) {
    criteria.claveAcuifero = Optional.of(new StringCriteria(Mode.EQ, claveAcuifero));
    return this;
  }

  public SiteCriteriaBuilder claveAcuiferoLike(String claveAcuifero) {
    criteria.claveAcuifero = Optional.of(new StringCriteria(Mode.LIKE, claveAcuifero));
    return this;
  }

  public SiteCriteriaBuilder acuiferoEq(String acuifero) {
    criteria.acuifero = Optional.of(new StringCriteria(Mode.EQ, acuifero));
    return this;
  }

  public SiteCriteriaBuilder acuiferoLike(String acuifero) {
    criteria.acuifero = Optional.of(new StringCriteria(Mode.LIKE, acuifero));
    return this;
  }

  public SiteCriteriaBuilder organismoEq(String organismo) {
    criteria.organismo = Optional.of(new StringCriteria(Mode.EQ, organismo));
    return this;
  }

  public SiteCriteriaBuilder organismoLike(String organismo) {
    criteria.organismo = Optional.of(new StringCriteria(Mode.LIKE, organismo));
    return this;
  }

  public SiteCriteriaBuilder direccionLocalEq(String direccionLocal) {
    criteria.direccionLocal = Optional.of(new StringCriteria(Mode.EQ, direccionLocal));
    return this;
  }

  public SiteCriteriaBuilder direccionLocalLike(String direccionLocal) {
    criteria.direccionLocal = Optional.of(new StringCriteria(Mode.LIKE, direccionLocal));
    return this;
  }

  public SiteCriteriaBuilder estadoEq(String estado) {
    criteria.estado = Optional.of(new StringCriteria(Mode.EQ, estado));
    return this;
  }

  public SiteCriteriaBuilder estadoLike(String estado) {
    criteria.estado = Optional.of(new StringCriteria(Mode.LIKE, estado));
    return this;
  }

  public SiteCriteriaBuilder municipioEq(String municipio) {
    criteria.municipio = Optional.of(new StringCriteria(Mode.EQ, municipio));
    return this;
  }

  public SiteCriteriaBuilder municipioLike(String municipio) {
    criteria.municipio = Optional.of(new StringCriteria(Mode.LIKE, municipio));
    return this;
  }

  public SiteCriteriaBuilder cuerpoDeAguaEq(String cuerpoDeAgua) {
    criteria.cuerpoDeAgua = Optional.of(new StringCriteria(Mode.EQ, cuerpoDeAgua));
    return this;
  }

  public SiteCriteriaBuilder cuerpoDeAguaLike(String cuerpoDeAgua) {
    criteria.cuerpoDeAgua = Optional.of(new StringCriteria(Mode.LIKE, cuerpoDeAgua));
    return this;
  }

  public SiteCriteriaBuilder tipoCuerpoEq(String tipoCuerpo) {
    criteria.tipoCuerpo = Optional.of(new StringCriteria(Mode.EQ, tipoCuerpo));
    return this;
  }

  public SiteCriteriaBuilder tipoCuerpoLike(String tipoCuerpo) {
    criteria.tipoCuerpo = Optional.of(new StringCriteria(Mode.LIKE, tipoCuerpo));
    return this;
  }

  public SiteCriteriaBuilder subtipoCuerpoEq(String subtipoCuerpo) {
    criteria.subtipoCuerpo = Optional.of(new StringCriteria(Mode.EQ, subtipoCuerpo));
    return this;
  }

  public SiteCriteriaBuilder subtipoCuerpoLike(String subtipoCuerpo) {
    criteria.subtipoCuerpo = Optional.of(new StringCriteria(Mode.LIKE, subtipoCuerpo));
    return this;
  }

  public SiteCriteriaBuilder longitudEq(String longitud) {
    criteria.longitud = Optional.of(new StringCriteria(Mode.EQ, longitud));
    return this;
  }

  public SiteCriteriaBuilder longitudLike(String longitud) {
    criteria.longitud = Optional.of(new StringCriteria(Mode.LIKE, longitud));
    return this;
  }

  public SiteCriteriaBuilder latitudEq(String latitud) {
    criteria.latitud = Optional.of(new StringCriteria(Mode.EQ, latitud));
    return this;
  }

  public SiteCriteriaBuilder latitudLike(String latitud) {
    criteria.latitud = Optional.of(new StringCriteria(Mode.LIKE, latitud));
    return this;
  }

  public SiteCriteriaBuilder usoEq(String uso) {
    criteria.uso = Optional.of(new StringCriteria(Mode.EQ, uso));
    return this;
  }

  public SiteCriteriaBuilder usoLike(String uso) {
    criteria.uso = Optional.of(new StringCriteria(Mode.LIKE, uso));
    return this;
  }

  public SiteCriteriaBuilder lugarTomaEq(String lugarToma) {
    criteria.lugarToma = Optional.of(new StringCriteria(Mode.EQ, lugarToma));
    return this;
  }

  public SiteCriteriaBuilder lugarTomaLike(String lugarToma) {
    criteria.lugarToma = Optional.of(new StringCriteria(Mode.LIKE, lugarToma));
    return this;
  }

  public SiteCriteriaBuilder clienteEq(String cliente) {
    criteria.cliente = Optional.of(new StringCriteria(Mode.EQ, cliente));
    return this;
  }

  public SiteCriteriaBuilder clienteLike(String cliente) {
    criteria.cliente = Optional.of(new StringCriteria(Mode.LIKE, cliente));
    return this;
  }

  public SiteCriteriaBuilder cuencaLike(String cuenca) {
    criteria.cliente = Optional.of(new StringCriteria(Mode.LIKE, cuenca));
    return this;
  }

  public SiteCriteriaBuilder cuencaEq(String cuenca) {
    criteria.cliente = Optional.of(new StringCriteria(Mode.EQ, cuenca));
    return this;
  }
}
