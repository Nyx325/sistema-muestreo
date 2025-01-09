package org.conagua.standard.model.entity;

import java.util.UUID;

public class Standard implements IStandard {
  private UUID id;
  private String name;
  private boolean active;

  public Standard(UUID id, String name, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
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
  public boolean isActive() {
    return active;
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
    StringBuilder str = new StringBuilder("ID: ")
        .append(id)
        .append(" Activo: ")
        .append(active)
        .append(" Nombre: ")
        .append(name);

    return str.toString();
  }
}
