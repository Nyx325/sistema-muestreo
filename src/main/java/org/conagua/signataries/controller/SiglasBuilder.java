package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.ISignatary;

public class SiglasBuilder {
  public String generateSiglas(ISignatary s) {
    StringBuilder siglas = new StringBuilder();

    siglas.append(s.getFirstName().charAt(0));

    if (s.getMidName().isPresent()) {
      String name = s.getMidName().get();
      siglas.append(name.charAt(0));
    }

    siglas.append(s.getFatherLastname().charAt(0));

    if (s.getMotherLastname().isPresent()) {
      String name = s.getMotherLastname().get();
      siglas.append(name.charAt(0));
    }

    return siglas.toString();
  }
}
