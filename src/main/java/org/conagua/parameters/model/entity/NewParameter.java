package org.conagua.parameters.model.entity;

public class NewParameter implements INewParameter {
  private String name;

  public NewParameter(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
