package org.conagua.common.model.repository;

import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.Search;

public interface IRepository<E extends IEntity, C> {
  public Optional<E> get(UUID id) throws Exception;

  public void add(E data) throws Exception;

  public void update(E data) throws Exception;

  public void delete(E data) throws Exception;

  public void delete(UUID id) throws Exception;

  public Search<E, C> getBy(C criteria, long page) throws Exception;
}
