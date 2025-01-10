package org.conagua.units.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class UnitCriteriaBuilder {
  private UnitCriteria criteria;

  public UnitCriteriaBuilder() {
    this.criteria = new UnitCriteria();
  }

  public UnitCriteria build() {
    return criteria;
  }

  public UnitCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }

  public UnitCriteriaBuilder longNameEq(String name) {
    criteria.longName = Optional.of(new StringCriteria(Mode.EQ, name));
    return this;
  }

  public UnitCriteriaBuilder longNameLike(String name) {
    criteria.shortName = Optional.of(new StringCriteria(Mode.LIKE, name));
    return this;
  }

  public UnitCriteriaBuilder shortNameEq(String name) {
    criteria.shortName = Optional.of(new StringCriteria(Mode.EQ, name));
    return this;
  }

  public UnitCriteriaBuilder shortNameLike(String name) {
    criteria.shortName = Optional.of(new StringCriteria(Mode.LIKE, name));
    return this;
  }

  public UnitCriteriaBuilder windowType(WindowType type) {
    criteria.windowType = Optional.of(type);
    return this;
  }

  public UnitCriteriaBuilder windowType(String type) {
    criteria.windowType = Optional.of(WindowType.parseWindowType(type));
    return this;
  }

  public UnitCriteriaBuilder regexLike(String regex) {
    criteria.regex = Optional.of(new StringCriteria(Mode.LIKE, regex));
    return this;
  }

  public UnitCriteriaBuilder regexEq(String regex) {
    criteria.regex = Optional.of(new StringCriteria(Mode.EQ, regex));
    return this;
  }

  public UnitCriteriaBuilder includeActions() {
    criteria.includeActions = true;
    return this;
  }
}
