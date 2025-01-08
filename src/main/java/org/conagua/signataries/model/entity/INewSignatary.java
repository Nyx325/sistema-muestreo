package org.conagua.signataries.model.entity;

import org.conagua.common.model.entity.INewEntity;

public interface INewSignatary extends INewEntity {
  /**
   * Getter para obtener el primer nombre del signatario
   * 
   * @returns {String} El primer nombre del signatario
   * @NonNull
   */
  public String getFirstName();

  /**
   * Getter para obtener el segundo nombre del signatario
   * 
   * @returns {String} Segundo nombre del usuario, en caso
   *          de no tener devuelve null
   * @Nullable
   */
  public String getMidName();

  /**
   * Getter para obtener el apellido paterno del signatario
   * 
   * @returns {String} Apellido paterno
   * @NonNull
   */
  public String getFatherLastname();

  /**
   * Getter para obtener el apellido materno del signatario
   * 
   * @returns {String} Apellido materno
   * @Nullable
   */
  public String getMotherLastname();
}
