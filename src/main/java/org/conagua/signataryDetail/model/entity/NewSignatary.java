package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

public class NewSignatary implements INewSignataryDetail {
  private UUID unit;
  private UUID standard;

  @Override
  public UUID getUnit() {
    return unit;
  }

  @Override
  public UUID getStandard() {
    return standard;
  }

  @Override
  public void setStandard(UUID standard) {
    this.standard = standard;
  }

  @Override
  public void setUnit(UUID unit) {
    this.unit = unit;
  }
}
