package org.conagua.standard.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;

public class StandardCriteria {
  public Optional<StringCriteria> name;
  public Optional<Boolean> active;

  public StandardCriteria() {
    this.name = Optional.empty();
    this.active = Optional.empty();
  }

  public StandardCriteria(Optional<StringCriteria> name, Optional<Boolean> active) {
    this.name = name;
    this.active = active;
  }
}
