package org.conagua.signataries.model.entity;

public class SignataryCriteria {
  public String firstName;
  public String midName;
  public String fatherLastname;
  public String motherLastname;

  public SignataryCriteria() {
    this.firstName = null;
    this.midName = null;
    this.motherLastname = null;
    this.fatherLastname = null;
  }

  public SignataryCriteria(String firstName, String midName, String fatherLastname, String motherLastname) {
    this.firstName = firstName;
    this.midName = midName;
    this.motherLastname = motherLastname;
    this.fatherLastname = fatherLastname;
  }
}
