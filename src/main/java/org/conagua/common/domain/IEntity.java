package org.conagua.common.domain;

import java.util.UUID;

public interface IEntity {
  /**
   * 
   */
  public UUID getId();

  // Convertir los atributos que se quieren
  // imprimir a un array de strings
  public String[] toStrArr();
}
