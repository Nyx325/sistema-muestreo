package org.conagua.common.controller;

public abstract class Controller implements IController {
  /*
   * Convierte en mayúscula el primer caracter de una cadena de
   * texto
   */
  protected String capitalizeString(String text) {
    if (text == null || text.isEmpty())
      return text;

    return Character.toUpperCase(text.charAt(0)) + text.substring(1);
  }
}
