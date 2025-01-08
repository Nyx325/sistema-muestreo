package org.conagua.common.model.repository;

import java.util.UUID;

import org.conagua.common.model.entity.Criteria;
import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.Search;
import org.conagua.common.model.exceptions.InvalidInstanceException;

/**
 * Interfaz que define las operaciones básicas para un repositorio de entidades.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar)
 * y búsquedas avanzadas en función de criterios.
 */
public interface IRepository {

  /**
   * Obtiene una entidad específica por su identificador único.
   * 
   * @param id El identificador único de la entidad.
   * @return La entidad correspondiente al identificador.
   * @throws Exception Si ocurre un error al buscar la entidad.
   */
  public IEntity get(UUID id) throws Exception;

  /**
   * Agrega una nueva entidad al repositorio.
   * 
   * @param data La entidad que se va a agregar.
   * @throws Exception Si ocurre un error al agregar la entidad.
   */
  public void add(IEntity data) throws Exception;

  /**
   * Actualiza una entidad existente en el repositorio.
   * 
   * @param data La entidad con los datos actualizados.
   * @throws InvalidInstanceException Si se recibe una instancia de
   *                                  {@link IEntity} que no sea admitida por la
   *                                  implementación.
   * @throws Exception                Si ocurre un error al actualizar la entidad.
   */
  public void update(IEntity data) throws InvalidInstanceException, Exception;

  /**
   * Elimina una entidad específica del repositorio.
   * 
   * @param data La entidad que se desea eliminar.
   * @throws InvalidInstanceException Si se recibe una instancia de
   *                                  {@link IEntity} que no sea admitida por la
   *                                  implementación.
   * @throws Exception                Si ocurre un error al eliminar la entidad.
   */
  public void delete(IEntity data) throws Exception;

  /**
   * Elimina una entidad específica por su identificador único.
   * 
   * @param id El identificador único de la entidad que se desea eliminar.
   * @throws Exception Si ocurre un error al eliminar la entidad.
   */
  public void delete(UUID id) throws Exception;

  /**
   * Busca entidades que cumplan con los criterios especificados y devuelve una
   * búsqueda paginada.
   * 
   * @param criteria Los criterios de búsqueda.
   * @param page     El número de la página que se desea recuperar (1-indexado).
   * @return Un objeto {@link Search} que contiene los resultados de la búsqueda.
   * @throws Exception Si ocurre un error al realizar la búsqueda.
   */
  public Search getBy(Criteria criteria, long page) throws Exception;
}
