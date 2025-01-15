package org.conagua.unitsPerStandard.model.entity;

import java.util.Optional;
import java.util.UUID;

public class UnitPerStdCriteria {
  public Optional<UUID> std;
  public Optional<UUID> unit;

  public UnitPerStdCriteria(Optional<UUID> std, Optional<UUID> unit) {
    this.std = std;
    this.unit = unit;
  }

  public UnitPerStdCriteria() {
    this.std = Optional.empty();
    this.unit = Optional.empty();
  }
}
