package org.conagua.standards.model.entity;

public class NewStandard implements INewStandard {
  private String name;

  public NewStandard(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
