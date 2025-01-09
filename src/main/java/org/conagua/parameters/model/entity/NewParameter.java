package org.conagua.parameters.model.entity;

public class NewParameter implements INewParameter {
  private String name;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
