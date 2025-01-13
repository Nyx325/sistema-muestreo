package org.conagua.actions.model.entity;

import java.util.*;

import org.conagua.common.model.entity.StringCriteria;

public class ActionCriteria {
  public Optional<StringCriteria> name;
  public Optional<Boolean> active;
  public Optional<UUID> param;

  public ActionCriteria() {
    this.name = Optional.empty();
    this.active = Optional.empty();
    this.param = Optional.empty();
  }

  public ActionCriteria(
      Optional<StringCriteria> name,
      Optional<Boolean> active,
      Optional<UUID> param

  ) {
    this.name = name;
    this.active = active;
    this.param = param;
  }
}
