package org.conagua.unitsPerStandard.model.entity;

import java.util.UUID;

public interface INewUnitPerStd {
  public UUID getStandard();

  public UUID getUnit();

  public void setStandard(UUID std);

  public void setUnit(UUID unit);

}
