package org.conagua.units.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.StringCriteria;

public class UnitCriteria {
  public Optional<Boolean> active;
  public Optional<StringCriteria> longName;
  public Optional<StringCriteria> shortName;
  public Optional<WindowType> windowType;
  public Optional<StringCriteria> regex;
  public boolean includeActions;

  public UnitCriteria() {
    this.active = Optional.empty();
    this.longName = Optional.empty();
    this.shortName = Optional.empty();
    this.windowType = Optional.empty();
    this.regex = Optional.empty();
    this.includeActions = false;
  }

  public UnitCriteria(
      Optional<Boolean> active,
      Optional<StringCriteria> longName,
      Optional<StringCriteria> shortName,
      Optional<WindowType> windowType,
      Optional<StringCriteria> regex,
      boolean includeActions) {
    this.active = active;
    this.longName = longName;
    this.shortName = shortName;
    this.windowType = windowType;
    this.regex = regex;
    this.includeActions = includeActions;
  }
}
