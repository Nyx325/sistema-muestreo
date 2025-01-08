package org.conagua.parameter.model.entity;

public class NewParameter implements INewParameter {
  private String name;

  public NewParameter(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}
