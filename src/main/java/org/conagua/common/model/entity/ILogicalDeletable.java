package org.conagua.common.model.entity;

public interface ILogicalDeletable {
  public boolean isActive();

  public void setActive(boolean active);
}
