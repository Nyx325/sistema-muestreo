package org.conagua.standard.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class StandardCriteriaBuilder {
  StandardCriteria criteria;

  public StandardCriteriaBuilder() {
    criteria = new StandardCriteria();
  }

  public StandardCriteria build() {
    return criteria;
  }

  public StandardCriteriaBuilder nameLike(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.LIKE, name));
    return this;
  }

  public StandardCriteriaBuilder nameEq(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.EQ, name));
    return this;
  }

  public StandardCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }
}
