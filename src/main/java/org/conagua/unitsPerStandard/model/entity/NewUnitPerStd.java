package org.conagua.unitsPerStandard.model.entity;

import java.util.UUID;

public class NewUnitPerStd implements INewUnitPerStd {
  private UUID std;
  private UUID unit;

  public NewUnitPerStd(UUID std, UUID unit) {
    this.std = std;
    this.unit = unit;
  }

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public UUID getUnit() {
    return unit;
  }

  @Override
  public void setStandard(UUID std) {
    this.std = std;
  }

  @Override
  public void setUnit(UUID unit) {
    this.unit = unit;
  }
}
