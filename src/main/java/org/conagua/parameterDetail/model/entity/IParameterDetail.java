package org.conagua.parameterDetail.model.entity;

import java.util.UUID;

import org.conagua.common.model.entity.IEntity;

public interface IParameterDetail extends IEntity {
  public UUID getParameter();

  public UUID getStandard();

  public void setParameter(UUID param);

  public void setStandard(UUID std);
}
