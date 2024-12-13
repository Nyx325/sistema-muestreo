package org.conagua.common.domain;

public interface ILogicalDeletable {
  public boolean isActive();

  public void setActive(boolean active);
}
