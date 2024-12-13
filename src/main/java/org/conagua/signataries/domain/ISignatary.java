package org.conagua.signataries.domain;

import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.ILogicalDeletable;

/**
 * Interfaz que representa la entidad de Singatario
 * dentro del sistema con los atributos y metodos
 * que deben tener
 */
public interface ISignatary extends IEntity, ILogicalDeletable {

  /**
   * Getter para obtener el primer nombre del signatario
   * 
   * @returns {String} El primer nombre del signatario
   * @NonNull
   */
  public String getfirstName();

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

  /**
   * Setter para establecer el primer nombre del signatario
   * 
   * @param {String} Primer nombre, nunca null
   */
  public void setFirstName(String name);

  /**
   * Setter para establecer el segundo nombre del signatario
   * 
   * @param {String} Segundo nombre, puede ser null
   */
  public void setMidName(String name);

  /**
   * Setter para establecer el apellido paterno del signatario
   * 
   * @param {String} Primer apellido, nunca null
   */
  public void setFatherLastname(String lastname);

  /**
   * Setter para establecer el apellido materno del signatario
   * 
   * @param {String} Segundo apellido, nunca null
   */
  public void setMotherLastname(String lastname);

  /**
   * Método que obtiene las siglas del signatario con base al nombre
   * 
   * Las siglas están formadas de Máximo 4 letras correspondientes a
   * Las iniciales de sus nombres apellidos en orden: primer nombre, segundo
   * nombre, primer apellido, segundo apellido. En caso de que el segundo nombre o
   * segundo apellido sea null simplemente se omite la inicial
   *
   * @returns {String} Siglas del usuario en formato ABCD
   * @NonNull
   */
  public String getSiglas();
}
