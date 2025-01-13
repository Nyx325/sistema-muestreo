package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

public class SignataryDetail implements ISignataryDetail {
  private UUID id;
  private UUID action;
  private UUID signatary;

  public SignataryDetail(UUID id, UUID action, UUID signatary) {
    this.id = id;
    this.action = action;
    this.signatary = signatary;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID getAction() {
    return action;
  }

  @Override
  public UUID getSignatary() {
    return signatary;
  }

  @Override
  public void setAction(UUID action) {
    this.action = action;
  }

  @Override
  public void setSignatary(UUID signatary) {
    this.signatary = signatary;
  }
}
