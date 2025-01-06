package org.conagua.signataries.model.entity;

import java.util.UUID;

import org.conagua.signataries.controller.SiglasBuilder;

public class Signatary implements ISignatary {
  private UUID id;
  private boolean active;
  private String firstName;
  private String midName;
  private String fatherLastname;
  private String motherLastname;

  private SiglasBuilder siglasBuilder = new SiglasBuilder();

  public Signatary(UUID id, boolean active, String firstName, String midName, String fatherLastname,
      String motherLastname) {
    this.id = id;
    this.active = active;
    this.motherLastname = motherLastname;
    this.fatherLastname = fatherLastname;
    this.firstName = firstName;
    this.midName = midName;
  }

  public Signatary(UUID id, String firstName, String midName, String fatherLastname,
      String motherLastname) {
    this(id, true, firstName, midName, fatherLastname, motherLastname);
  }

  public Signatary(UUID id, String firstName, String fatherLastname) {
    this(id, true, firstName, null, fatherLastname, null);
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String getSiglas() {
    return siglasBuilder.generateSiglas(this);
  }

  @Override
  public String getMidName() {
    return midName;
  }

  @Override
  public String getfirstName() {
    return firstName;
  }

  @Override
  public String getFatherLastname() {
    return fatherLastname;
  }

  @Override
  public String getMotherLastname() {
    return motherLastname;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public void setMidName(String name) {
    this.midName = name;
  }

  @Override
  public void setFirstName(String name) {
    this.firstName = name;
  }

  @Override
  public void setFatherLastname(String lastname) {
    this.fatherLastname = lastname;
  }

  @Override
  public void setMotherLastname(String lastname) {
    this.motherLastname = lastname;
  }

  @Override
  public String[] attributesToArray() {
    String[] attributes = new String[5];

    attributes[0] = firstName;
    attributes[1] = midName != null ? midName : "";
    attributes[2] = fatherLastname;
    attributes[3] = motherLastname != null ? motherLastname : "";

    return attributes;
  }

  @Override
  public String toString() {
    return this.siglasBuilder.generateSiglas(this);
  }
}
