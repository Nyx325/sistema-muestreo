package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

public interface INewSignataryDetail {
  public UUID getStandard();

  public UUID getUnit();

  public void setStandard(UUID standard);

  public void setUnit(UUID unit);
}
