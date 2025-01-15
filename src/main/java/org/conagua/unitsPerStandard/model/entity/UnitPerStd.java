package org.conagua.unitsPerStandard.model.entity;

import java.util.UUID;

public class UnitPerStd implements IUnitPerStd {
  private UUID id;
  private UUID std;
  private UUID unit;

  public UnitPerStd(UUID id, UUID std, UUID unit) {
    this.id = id;
    this.std = std;
    this.unit = unit;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID getUnit() {
    return unit;
  }

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public void setUnit(UUID unit) {
    this.unit = unit;
  }

  @Override
  public void setStandard(UUID std) {
    this.std = std;
  }
}
