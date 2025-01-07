package org.conagua.standards.model.entity;

import java.util.InputMismatchException;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;

public class Standard implements IStandard {
  private UUID id;
  private String name;
  private boolean active;

  public Standard(UUID id, String name, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
  }

  public Standard(UUID id, String name) {
    this(id, name, true);
  }

  public static Standard parseStandard(IEntity entity) {
    if (entity instanceof IStandard)
      throw new InputMismatchException("entity debe ser una instancia de IStandard");

    IStandard std = (IStandard) entity;
    return new Standard(
        std.getId(),
        std.getName(),
        std.isActive());
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
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String[] attributesToArray() {
    String[] fields = {
        id.toString(),
        name.toString()
    };

    return fields;
  }
}
