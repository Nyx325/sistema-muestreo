package org.conagua.common.model.entity;

/**
 * Interfaz que representa una entidad la cual
 * puede determinarse si está activa o inactiva
 */
public interface ILogicalDeletable {
  public boolean isActive();

  public void setActive(boolean active);
}
