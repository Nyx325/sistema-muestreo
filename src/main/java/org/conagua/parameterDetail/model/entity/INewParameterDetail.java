package org.conagua.parameterDetail.model.entity;

import java.util.UUID;

public interface INewParameterDetail {
  public UUID getParameter();

  public UUID getStandard();

  public void setParameter(UUID param);

  public void setStandard(UUID std);
}
