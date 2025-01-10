package org.conagua.units.model.entity;

import java.util.Optional;

public class NewUnit implements INewUnit {
  private String longName;
  private String shortName;
  private WindowType windowType;
  private Optional<String> regex;

  public NewUnit(
      String longName,
      String shortName,
      WindowType windowType,
      Optional<String> regex) {
    this.longName = longName;
    this.shortName = shortName;
    this.windowType = windowType;
    this.regex = regex;
  }

  public NewUnit(
      String longName,
      String shortName,
      String windowType,
      Optional<String> regex) {
    this.longName = longName;
    this.shortName = shortName;
    this.windowType = WindowType.parseWindowType(windowType);
    this.regex = regex;
  }

  @Override
  public Optional<String> getRegex() {
    return regex;
  }

  @Override
  public String getLongName() {
    return longName;
  }

  @Override
  public String getShortName() {
    return shortName;
  }

  @Override
  public WindowType getWindowType() {
    return windowType;
  }

  @Override
  public void setLongName(String name) {
    longName = name;
  }

  @Override
  public void setShortName(String name) {
    shortName = name;
  }

  @Override
  public void setRegex(Optional<String> regex) {
    this.regex = regex;
  }

  @Override
  public void setWindowType(WindowType type) {
    windowType = type;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder(longName)
        .append("(")
        .append(shortName)
        .append(") Tipo Ventana: ")
        .append(windowType.toString())
        .append(" regex: ")
        .append(regex.isPresent() ? regex.get() : "None");

    return str.toString();
  }
}
