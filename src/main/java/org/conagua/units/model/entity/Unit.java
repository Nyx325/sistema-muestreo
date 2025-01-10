package org.conagua.units.model.entity;

import java.util.Optional;
import java.util.UUID;

public class Unit implements IUnit {
  private UUID id;
  private boolean active;
  private String longName;
  private String shortName;
  private WindowType winT;
  private Optional<String> regex;

  @Override
  public WindowType getWindowType() {
    return winT;
  }

  @Override
  public String getShortName() {
    return shortName;
  }

  @Override
  public String getLongName() {
    return longName;
  }

  @Override
  public Optional<String> getRegex() {
    return regex;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setWindowType(WindowType type) {
    winT = type;
  }

  @Override
  public void setRegex(Optional<String> regex) {
    this.regex = regex;
  }

  @Override
  public void setShortName(String name) {
    shortName = name;
  }

  @Override
  public void setLongName(String name) {
    longName = name;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }
}
