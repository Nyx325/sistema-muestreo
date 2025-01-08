package org.conagua.parameter.model.entity;

import java.util.UUID;

public class Parameter implements IParameter {
  private UUID id;
  private boolean active;
  private String name;

  public Parameter(UUID id, boolean active, String name) {
    this.id = id;
    this.active = active;
    this.name = name;
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
  public String getName() {
    return name;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String[] attributesToArray() {
    String[] fields = {
        name
    };

    return fields;
  }
}
