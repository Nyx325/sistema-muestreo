package org.conagua.parameterDetail.model.entity;

import java.util.UUID;

public class ParameterDetail implements IParameterDetail {
  private UUID id;
  private UUID param;
  private UUID std;

  public ParameterDetail(UUID id, UUID param, UUID std) {
    this.id = id;
    this.param = param;
    this.std = std;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID getParameter() {
    return param;
  }

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public void setParameter(UUID param) {
    this.param = param;
  }

  @Override
  public void setStandard(UUID std) {
    this.std = std;
  }
}
