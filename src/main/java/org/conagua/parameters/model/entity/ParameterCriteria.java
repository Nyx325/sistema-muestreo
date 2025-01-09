package org.conagua.parameters.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;

public class ParameterCriteria {
  public Optional<Boolean> active;
  public Optional<StringCriteria> name;
  public boolean includePruebas;

  public ParameterCriteria() {
    this.active = Optional.empty();
    this.name = Optional.empty();
    this.includePruebas = false;
  }

  public ParameterCriteria(
      Optional<Boolean> active,
      Optional<StringCriteria> name,
      boolean includePruebas) {
    this.active = active;
    this.name = name;
  }
}
