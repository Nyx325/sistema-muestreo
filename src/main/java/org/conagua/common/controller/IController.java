package org.conagua.common.controller;

import java.util.InputMismatchException;
import java.util.UUID;

import org.conagua.common.model.entity.*;

/**
 * Interfaz genérica que define operaciones básicas para un controlador que
 * interactúa con entidades del sistema. Los métodos permiten realizar
 * operaciones CRUD y búsquedas basadas en criterios específicos.
 */
public interface IController {

  /**
   * Obtiene una entidad por su identificador único.
   * 
   * @param id El identificador único de la entidad.
   * @return La entidad correspondiente al identificador proporcionado.
   * @throws InputMismatchException Si el identificador proporcionado es inválido.
   * @throws Exception              Para errores generales durante la operación.
   */
  public IEntity get(UUID id) throws InputMismatchException, Exception;

  /**
   * Agrega una nueva entidad al sistema.
   * 
   * @param data La entidad a agregar.
   * @throws InputMismatchException Si los datos proporcionados son inválidos.
   * @throws Exception              Para errores generales durante la operación.
   */
  public void add(INewEntity data) throws InputMismatchException, Exception;

  /**
   * Actualiza una entidad existente en el sistema.
   * 
   * @param data La entidad con los datos actualizados.
   * @throws InputMismatchException Si los datos proporcionados son inválidos.
   * @throws Exception              Para errores generales durante la operación.
   */
  public void update(IEntity data) throws InputMismatchException, Exception;

  /**
   * Elimina una entidad del sistema utilizando el objeto de la entidad.
   * 
   * @param data La entidad a eliminar.
   * @throws InputMismatchException Si los datos proporcionados son inválidos.
   * @throws Exception              Para errores generales durante la operación.
   */
  public void delete(IEntity data) throws InputMismatchException, Exception;

  /**
   * Elimina una entidad del sistema utilizando su identificador único.
   * 
   * @param id El identificador único de la entidad a eliminar.
   * @throws InputMismatchException Si el identificador proporcionado es inválido.
   * @throws Exception              Para errores generales durante la operación.
   */
  public void delete(UUID id) throws InputMismatchException, Exception;

  /**
   * Obtiene un conjunto de entidades que cumplen con los criterios especificados.
   * 
   * @param criteria Los criterios para filtrar las entidades.
   * @param page     El número de página a recuperar.
   * @return Un objeto {@link Search} que contiene las entidades encontradas y
   *         metadatos
   *         relacionados con la búsqueda.
   * @throws InputMismatchException Si los criterios proporcionados son inválidos.
   * @throws Exception              Para errores generales durante la operación.
   */
  public Search getBy(Criteria criteria, long page) throws InputMismatchException, Exception;
}
