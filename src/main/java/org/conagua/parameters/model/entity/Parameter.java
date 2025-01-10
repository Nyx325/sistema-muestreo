package org.conagua.parameters.model.entity;

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
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder()
        .append("ID: ")
        .append(id)
        .append(" Activo: ")
        .append(active)
        .append(" Nombre: ")
        .append(name);

    return str.toString();
  }
}
