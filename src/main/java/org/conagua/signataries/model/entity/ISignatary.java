package org.conagua.signataries.model.entity;

import java.util.Optional;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;
import org.conagua.common.model.entity.ITablePrintable;

public interface ISignatary extends IEntity, ILogicalDeletable, ITablePrintable {
  public String getFirstName();

  public Optional<String> getMidName();

  public String getFatherLastname();

  public Optional<String> getMotherLastname();

  public void setFirstName(String name);

  public void setMidName(Optional<String> name);

  public void setFatherLastname(String lastname);

  public void setMotherLastname(Optional<String> lastname);

  public String getSiglas();

  public String getFullName();
}
