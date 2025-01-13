package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

public class NewSignatary implements INewSignataryDetail {
  private UUID signatary;
  private UUID action;

  @Override
  public UUID getSignatary() {
    return signatary;
  }

  @Override
  public UUID getAction() {
    return action;
  }

  @Override
  public void setSignatary(UUID id) {
    this.signatary = id;
  }

  @Override
  public void setAction(UUID id) {
    this.action = id;
  }
}
