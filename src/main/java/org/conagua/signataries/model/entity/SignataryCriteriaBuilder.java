package org.conagua.signataries.model.entity;

import java.util.Optional;
import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class SignataryCriteriaBuilder {
  private SignataryCriteria c;

  public SignataryCriteriaBuilder() {
    this.c = new SignataryCriteria();
  }

  public SignataryCriteria build() {
    return c;
  }

  public static Signatary of(ISignatary s) {
    return new Signatary(
        s.getId(),
        s.isActive(),
        s.getFirstName(),
        s.getMidName(),
        s.getFatherLastname(),
        s.getMotherLastname());
  }

  public SignataryCriteriaBuilder active(boolean value) {
    c.active = Optional.of(value);
    return this;
  }

  public SignataryCriteriaBuilder firstNameLike(String value) {
    c.firstName = Optional.of(new StringCriteria(Mode.LIKE, value));
    return this;
  }

  public SignataryCriteriaBuilder firstNameEq(String value) {
    c.firstName = Optional.of(new StringCriteria(Mode.EQ, value));
    return this;
  }

  public SignataryCriteriaBuilder midNameLike(String value) {
    c.midName = Optional.of(new StringCriteria(Mode.LIKE, value));
    return this;
  }

  public SignataryCriteriaBuilder midNameEq(String value) {
    c.midName = Optional.of(new StringCriteria(Mode.EQ, value));
    return this;
  }

  public SignataryCriteriaBuilder fatherLastnameLike(String value) {
    c.fatherLastname = Optional.of(new StringCriteria(Mode.LIKE, value));
    return this;
  }

  public SignataryCriteriaBuilder fatherLastnameEq(String value) {
    c.fatherLastname = Optional.of(new StringCriteria(Mode.EQ, value));
    return this;
  }

  public SignataryCriteriaBuilder motherLastnameLike(String value) {
    c.motherLastname = Optional.of(new StringCriteria(Mode.LIKE, value));
    return this;
  }

  public SignataryCriteriaBuilder motherLastnameEq(String value) {
    c.motherLastname = Optional.of(new StringCriteria(Mode.EQ, value));
    return this;
  }
}
