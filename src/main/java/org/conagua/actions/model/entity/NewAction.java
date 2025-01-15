package org.conagua.actions.model.entity;

import java.util.Optional;
import java.util.UUID;

public class NewAction implements INewAction {
  private String name;
  private Optional<UUID> param;

  public NewAction(
      String name,
      Optional<UUID> param) {
    this.name = name;
    this.param = param;
  }

  @Override
  public Optional<UUID> getParam() {
    return param;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setParam(Optional<UUID> id) {
    this.param = id;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("Nombre: ")
        .append(name)
        .append(" Param: ")
        .append(param.isEmpty() ? "None" : param.get());
    return str.toString();
  }
}
