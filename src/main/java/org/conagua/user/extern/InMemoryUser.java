package org.conagua.user.extern;

import java.util.UUID;

import org.conagua.common.domain.IEntity;
import org.conagua.user.domain.IUser;

public class InMemoryUser implements IUser, IEntity {
  private UUID id;
  private String firstName;
  private String midName;
  private String fatherLastname;
  private String motherLastname;
  private boolean active;

  public InMemoryUser(UUID id, String firstName, String midName, String fatherLastname, String motherLastname) {
    this.id = id;
    this.firstName = firstName;
    this.midName = midName;
    this.fatherLastname = fatherLastname;
    this.motherLastname = motherLastname;
    this.active = true;
  }

  public InMemoryUser(String firstName, String midName, String fatherLastname, String motherLastname) {
    this(UUID.randomUUID(), firstName, midName, fatherLastname, motherLastname);
  }

  public InMemoryUser(String firstName, String fatherLastname, String motherLastname) {
    this(firstName, null, fatherLastname, motherLastname);
  }

  public InMemoryUser(String firstName, String fatherLastname) {
    this(firstName, fatherLastname, null);
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String getMidName() {
    return midName;
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
  public String getMotherLastname() {
    return motherLastname;
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
  public void setFatherLastname(String name) {
    this.fatherLastname = name;

  }

  @Override
  public void setMotherLastname(String name) {
    this.motherLastname = name;

  }

  @Override
  public String[] toStrArr() {
    String[] arr = new String[4];

    arr[0] = firstName.toString();
    arr[1] = midName == null ? "X" : midName.toString();
    arr[2] = fatherLastname.toString();
    arr[3] = motherLastname == null ? "X" : midName.toString();

    return arr;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }
}
