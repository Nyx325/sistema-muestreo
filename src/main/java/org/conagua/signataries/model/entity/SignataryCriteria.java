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

  public Optional<Boolean> getActive() {
    return active;
  }

  public Optional<StringCriteria> getMidName() {
    return midName;
  }

  public Optional<StringCriteria> getFirstName() {
    return firstName;
  }

  public Optional<StringCriteria> getFatherLastname() {
    return fatherLastname;
  }

  public Optional<StringCriteria> getMotherLastname() {
    return motherLastname;
  }

  public void setActive(Optional<Boolean> active) {
    this.active = active;
  }

  public void setMidName(Optional<StringCriteria> midName) {
    this.midName = midName;
  }

  public void setFirstName(Optional<StringCriteria> firstName) {
    this.firstName = firstName;
  }

  public void setFatherLastname(Optional<StringCriteria> fatherLastname) {
    this.fatherLastname = fatherLastname;
  }

  public void setMotherLastname(Optional<StringCriteria> motherLastname) {
    this.motherLastname = motherLastname;
  }
}
