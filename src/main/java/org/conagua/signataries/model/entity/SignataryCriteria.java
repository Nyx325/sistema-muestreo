package org.conagua.signataries.model.entity;

import org.conagua.common.model.entity.Criteria;

public class SignataryCriteria implements Criteria {
  public Boolean active;
  public String firstName;
  public String midName;
  public String fatherLastname;
  public String motherLastname;

  public SignataryCriteria() {
  }

  public SignataryCriteria(String firstName, String midName, String fatherLastname, String motherLastname,
      Boolean active) {
    this.active = active;
    this.firstName = firstName;
    this.midName = midName;
    this.motherLastname = motherLastname;
    this.fatherLastname = fatherLastname;
  }

  @Override
  public boolean isEmpty() {
    return active == null &&
        firstName == null &&
        midName == null &&
        fatherLastname == null &&
        motherLastname == null;
  }
}
