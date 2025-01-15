package org.conagua.unitsPerStandard.model.entity;

import java.util.UUID;

import org.conagua.common.model.entity.IEntity;

public interface IUnitPerStd extends IEntity {
  public UUID getStandard();

  public UUID getUnit();

  public void setStandard(UUID std);

  public void setUnit(UUID unit);
}
