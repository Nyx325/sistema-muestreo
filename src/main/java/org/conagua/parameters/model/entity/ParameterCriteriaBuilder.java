package org.conagua.parameters.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class ParameterCriteriaBuilder {
  private ParameterCriteria criteria;

  public ParameterCriteriaBuilder() {
    criteria = new ParameterCriteria();
  }

  public ParameterCriteria build() {
    return criteria;
  }

  public ParameterCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }

  public ParameterCriteriaBuilder nameLike(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.LIKE, name));
    return this;
  }

  public ParameterCriteriaBuilder nameEq(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.EQ, name));
    return this;
  }

  public ParameterCriteriaBuilder includePruebas(boolean include) {
    criteria.includePruebas = include;
    return this;
  }
}
