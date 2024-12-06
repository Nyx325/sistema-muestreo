package org.conagua.user.domain;


public interface IUser {
  public String getFirstName();

  public void setFirstName(String name);

  public String getMidName();

  public void setMidName(String name);

  public String getFatherLastname();

  public void setFatherLastname(String name);

  public String getMotherLastname();

  public void setMotherLastname(String name);
}
