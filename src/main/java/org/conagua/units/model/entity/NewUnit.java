package org.conagua.units.model.entity;

import java.util.Optional;
import java.util.UUID;

public class NewUnit implements INewUnit {
  private String longName;
  private String shortName;
  private String windowType;

  @Override
  public Optional<String> getRegex() {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public String getLongName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getShortName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getWindowType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setLongName(String name) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setShortName(String name) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setRegex(Optional<String> regex) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setWindowType(String type) {
    // TODO Auto-generated method stub

  }
}
