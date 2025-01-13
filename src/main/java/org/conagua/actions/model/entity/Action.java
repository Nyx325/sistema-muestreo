package org.conagua.actions.model.entity;

import java.util.Optional;
import java.util.UUID;

public class Action implements IAction {
  private UUID id;
  private boolean active;
  private String name;
  private Optional<UUID> param;

  public Action(
      UUID id,
      boolean active,
      String name,
      Optional<UUID> param) {
    this.id = id;
    this.active = active;
    this.name = name;
    this.param = param;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Optional<UUID> getParam() {
    return param;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setParam(Optional<UUID> paramId) {
    param = paramId;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }
}
