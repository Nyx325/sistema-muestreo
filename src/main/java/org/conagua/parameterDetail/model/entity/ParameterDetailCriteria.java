package org.conagua.parameterDetail.model.entity;

import java.util.Optional;
import java.util.UUID;

public class ParameterDetailCriteria {
  public Optional<UUID> param;
  public Optional<UUID> std;

  public ParameterDetailCriteria(Optional<UUID> param, Optional<UUID> std) {
    this.param = param;
    this.std = std;
  }

  public ParameterDetailCriteria() {
    this.param = Optional.empty();
    this.std = Optional.empty();
  }
}
