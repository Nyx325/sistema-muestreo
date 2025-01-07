package org.conagua.standards.model.entity;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

public interface IStandard extends IEntity, ILogicalDeletable {
  public String getName();

  public void setName(String name);
}
