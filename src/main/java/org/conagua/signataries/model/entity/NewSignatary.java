package org.conagua.signataries.model.entity;

import java.util.Optional;

public class NewSignatary implements INewSignatary {
  private String firstName;
  private String fatherLastname;
  private Optional<String> motherLastname;
  private Optional<String> midName;

  public NewSignatary(String firstName, Optional<String> midName, String fatherLastname,
      Optional<String> motherLastname) {
    this.firstName = firstName;
    this.midName = midName;
    this.fatherLastname = fatherLastname;
    this.motherLastname = motherLastname;
  }

  public NewSignatary(String firstName, String midName, String fatherLastname, String motherLastname) {
    this(firstName, Optional.of(midName), fatherLastname, Optional.of(motherLastname));
  }

  public NewSignatary(String firstName, String fatherLastname, String motherLastname) {
    this(firstName, Optional.empty(), fatherLastname, Optional.of(motherLastname));
  }

  public NewSignatary(String firstName, String fatherLastname) {
    this(firstName, Optional.empty(), fatherLastname, Optional.empty());
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
  public Optional<String> getMidName() {
    return midName;
  }

  @Override
  public Optional<String> getMotherLastname() {
    return motherLastname;
  }
}
