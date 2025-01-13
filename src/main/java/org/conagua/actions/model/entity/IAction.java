package org.conagua.actions.model.entity;

import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

public interface IAction extends IEntity, ILogicalDeletable {
  public String getName();

  public Optional<UUID> getParam();

  public void setName(String name);

  public void setParam(Optional<UUID> id);
}
