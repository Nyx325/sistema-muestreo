package org.conagua.signataryDetail.model.entity;

import java.util.Optional;
import java.util.UUID;

public class SignataryDetailCriteria {
  public Optional<UUID> action;
  public Optional<UUID> signatary;

  public SignataryDetailCriteria(Optional<UUID> action, Optional<UUID> signatary) {
    this.action = action;
    this.signatary = signatary;
  }

  public SignataryDetailCriteria() {
    this(Optional.empty(), Optional.empty());
  }
}
