package org.conagua.common.model.entity;

public class StringCriteria {
  Mode mode;
  String value;

  public enum Mode {
    EQ, LIKE
  }

  public StringCriteria(Mode mode, String value) {
    this.mode = mode;
    this.value = value;
  }

  public Mode getMode() {
    return mode;
  }

  public String getValue() {
    return value;
  }

  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isEqMode() {
    return mode == Mode.EQ;
  }

  public boolean isLikeMode() {
    return mode == Mode.LIKE;
  }
}
