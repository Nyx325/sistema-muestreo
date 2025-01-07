package org.conagua.signataries.controller;

public class NameValidator {
  private static final String COMPOUND_NAME_REGEX = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( (de(l)?|de la))*$";
  private static final String SINGLE_NAME_REGEX = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$";

  /**
   * Valida si un nombre compuesto cumple con el formato esperado.
   * 
   * @param name El nombre compuesto a validar.
   * @return true si el nombre es válido, false en caso contrario.
   */
  public boolean compoudNameIsValid(String name) {
    if (name == null) {
      return false;
    }
    return name.matches(COMPOUND_NAME_REGEX);
  }

  /**
   * Valida si un nombre simple cumple con el formato esperado.
   * 
   * @param name El nombre simple a validar.
   * @return true si el nombre es válido, false en caso contrario.
   */
  public boolean singleNameIsValid(String name) {
    if (name == null) {
      return true; // Si el nombre es nulo, se considera válido (según el diseño original).
    }
    return name.matches(SINGLE_NAME_REGEX);
  }
}
