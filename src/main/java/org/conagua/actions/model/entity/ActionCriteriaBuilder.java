package org.conagua.actions.model.entity;

import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class ActionCriteriaBuilder {
  private ActionCriteria criteria;

  public ActionCriteriaBuilder() {
    this.criteria = new ActionCriteria();
  }

  public ActionCriteria build() {
    return criteria;
  }

  public ActionCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }

  public ActionCriteriaBuilder nameLike(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.LIKE, name));
    return this;
  }

  public ActionCriteriaBuilder nameEq(String name) {
    criteria.name = Optional.of(new StringCriteria(Mode.EQ, name));
    return this;
  }

  public ActionCriteriaBuilder param(UUID param) {
    criteria.param = Optional.of(param);
    return this;
  }
}
