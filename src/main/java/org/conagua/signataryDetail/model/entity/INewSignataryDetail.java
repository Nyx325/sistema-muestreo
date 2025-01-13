package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

public interface INewSignataryDetail {
  public UUID getSignatary();

  public UUID getAction();

  public void setSignatary(UUID id);

  public void setAction(UUID id);
}
