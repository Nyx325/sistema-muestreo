package org.conagua.actions.model.entity;

import java.util.Optional;
import java.util.UUID;

public interface INewAction {
  public String getName();

  public Optional<UUID> getParam();

  public void setName(String name);

  public void setParam(Optional<UUID> id);
}
