package org.conagua.signataries.model.entity;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.signataries.controller.SiglasBuilder;

public class Signatary implements ISignatary {
  private UUID id;
  private boolean active;
  private String firstName;
  private String fatherLastname;
  private Optional<String> midName;
  private Optional<String> motherLastname;

  private SiglasBuilder siglasBuilder = new SiglasBuilder();

  public Signatary(UUID id, boolean active, String firstName, Optional<String> midName, String fatherLastname,
      Optional<String> motherLastname) {
    this.id = id;
    this.active = active;
    this.motherLastname = motherLastname;
    this.fatherLastname = fatherLastname;
    this.firstName = firstName;
    this.midName = midName;
  }

  public Signatary(UUID id, boolean active, String firstName, String midName, String fatherLastname,
      String motherLastname) {
    this(id, active, firstName, Optional.of(midName), fatherLastname, Optional.of(fatherLastname));
  }

  public Signatary(UUID id, String firstName, String midName, String fatherLastname,
      String motherLastname) {
    this(id, true, firstName, midName, fatherLastname, motherLastname);
  }

  public Signatary(UUID id, String firstName, String fatherLastname, String motherLastname) {
    this(id, true, firstName, Optional.empty(), fatherLastname, Optional.of(motherLastname));
  }

  public Signatary(UUID id, String firstName, String fatherLastname) {
    this(id, true, firstName, Optional.empty(), fatherLastname, Optional.empty());
  }

  public static Signatary parseSignatary(IEntity entity) {
    if (entity instanceof ISignatary == false)
      throw new InputMismatchException();

    ISignatary s = (ISignatary) entity;
    return new Signatary(
        s.getId(),
        s.isActive(),
        s.getFirstName(),
        s.getMidName(),
        s.getFatherLastname(),
        s.getMotherLastname());
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
  public Optional<String> getMidName() {
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
  public Optional<String> getMotherLastname() {
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
  public void setMidName(Optional<String> name) {
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
  public void setMotherLastname(Optional<String> lastname) {
    this.motherLastname = lastname;
  }

  @Override
  public String[] fieldsToArray() {
    String[] fields = {
        firstName,
        midName.isPresent() ? midName.get() : "-",
        fatherLastname,
        motherLastname.isPresent() ? motherLastname.get() : "-"
    };

    return fields;
  }

  @Override
  public String getFullName() {
    StringBuilder name = new StringBuilder(firstName)
        .append(" ");

    if (midName.isPresent()) {
      name.append(midName.get())
          .append(" ");
    }

    name.append(fatherLastname).append(" ");

    if (motherLastname.isPresent()) {
      name.append(motherLastname.get())
          .append(" ");
    }

    return name.toString();
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("ID: ")
        .append(id)
        .append(" Siglas: ")
        .append(siglasBuilder.generateSiglas(this))
        .append(" Nombre: ")
        .append(getFullName());

    return str.toString();
  }
}
