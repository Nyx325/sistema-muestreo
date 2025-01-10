package org.conagua.units.model.entity;

import java.util.Optional;

public interface INewUnit {
  public String getLongName();

  public String getShortName();

  public WindowType getWindowType();

  public Optional<String> getRegex();

  public void setLongName(String name);

  public void setShortName(String name);

  public void setWindowType(WindowType type);

  public void setRegex(Optional<String> regex);
}
