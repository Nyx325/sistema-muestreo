package org.conagua.common.model.entity;

import java.util.UUID;

public interface IEntity {
  /**
   * Metodo que permite obtener un ID único que identifica
   * a la entidad
   *
   * @return {UUID} el ID único que identifica a la entidad
   */
  public UUID getId();

  /**
   * Método que convierte los valores que van
   * a imprimirse en la tabla a un array de
   * Strings
   *
   * @return {Stirng[]} atributos en forma de
   *         array de Strings
   */
  public String[] attributesToArray();
}
