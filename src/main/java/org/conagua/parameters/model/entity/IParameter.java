package org.conagua.parameters.model.entity;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

public interface IParameter extends IEntity, ILogicalDeletable {
  public String getName();

  public void setName(String name);
}
