package org.conagua.signataryDetail.model.entity;

import java.util.UUID;

import org.conagua.common.model.entity.IEntity;

public interface ISignataryDetail extends IEntity {
  public UUID getSignatary();

  public UUID getAction();

  public void setSignatary(UUID signatary);

  public void setAction(UUID action);
}
