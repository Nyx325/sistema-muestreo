package org.conagua.signataries.model.entity;

import java.util.Optional;
import org.conagua.common.model.entity.StringCriteria;

public class SignataryCriteria {
  public Optional<Boolean> active;
  public Optional<StringCriteria> firstName;
  public Optional<StringCriteria> midName;
  public Optional<StringCriteria> fatherLastname;
  public Optional<StringCriteria> motherLastname;

  public SignataryCriteria() {
    this(
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }

  public SignataryCriteria(
      Optional<StringCriteria> firstName,
      Optional<StringCriteria> midName,
      Optional<StringCriteria> fatherLastname,
      Optional<StringCriteria> motherLastname,
      Optional<Boolean> active) {
    this.active = active;
    this.firstName = firstName;
    this.midName = midName;
    this.motherLastname = motherLastname;
    this.fatherLastname = fatherLastname;
  }
}
