package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.ISignatary;

public class SiglasBuilder {
  public String nullableValue(String v) {
    String s = "";
    s += v != null ? v.charAt(0) : "";
    return s;
  }

  public String generateSiglas(ISignatary s) {
    String siglas = "";
    siglas += s.getfirstName().charAt(0);
    siglas += nullableValue(s.getMidName());
    siglas += s.getFatherLastname().charAt(0);
    siglas += nullableValue(s.getMotherLastname());
    return siglas;
  }
}