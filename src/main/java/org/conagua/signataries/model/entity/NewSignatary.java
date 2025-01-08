package org.conagua.signataries.model.entity;

public class NewSignatary implements INewSignatary {
  private String motherLastname;
  private String fatherLastname;
  private String midName;
  private String firstName;

  public NewSignatary(String firstName, String midName, String fatherLastname, String motherLastname) {
    this.firstName = firstName;
    this.midName = midName;
    this.fatherLastname = fatherLastname;
    this.motherLastname = motherLastname;
  }

  public NewSignatary(String firstName, String fatherLastname, String motherLastname) {
    this(firstName, null, fatherLastname, motherLastname);
  }

  public NewSignatary(String firstName, String fatherLastname) {
    this(firstName, null, fatherLastname, null);
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public String getFatherLastname() {
    return fatherLastname;
  }

  @Override
  public String getMidName() {
    return midName;
  }

  @Override
  public String getMotherLastname() {
    return motherLastname;
  }
}
