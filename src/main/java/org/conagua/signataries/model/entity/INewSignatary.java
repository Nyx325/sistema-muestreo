package org.conagua.signataries.model.entity;

import java.util.Optional;

public interface INewSignatary {
  public String getFirstName();

  public Optional<String> getMidName();

  public String getFatherLastname();

  public Optional<String> getMotherLastname();
}
