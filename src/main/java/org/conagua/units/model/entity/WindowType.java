package org.conagua.units.model.entity;

public enum WindowType {
  TIPO1,

  // Fin de las constantes del enum.
  ;

  public static WindowType parseWindowType(String type) {
    switch (type.toUpperCase()) {
      case "TIPO1":
        return WindowType.TIPO1;
      default:
        throw new IllegalArgumentException("Tipo de ventana no válido: " + type);
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case TIPO1:
        return "TIPO1";
      default:
        throw new RuntimeException("Not implemented for this variant yet");
    }
  }
}
