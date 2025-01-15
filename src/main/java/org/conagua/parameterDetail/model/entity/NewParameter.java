package org.conagua.parameterDetail.model.entity;

import java.util.UUID;

public class NewParameter implements INewParameterDetail {
  private UUID std;
  private UUID param;

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public UUID getParameter() {
    return param;
  }

  @Override
  public void setStandard(UUID std) {
    this.std = std;
  }

  @Override
  public void setParameter(UUID param) {
    this.param = param;
  }
}
