package org.conagua.common.controller;

import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.*;

public interface IController<E extends IEntity, NE, C> {
  public Optional<E> get(UUID id) throws Exception;

  public Result<E, String> add(NE data) throws Exception;

  public Result<Void, String> update(E data) throws Exception;

  public Result<Void, String> delete(E data) throws Exception;

  public Result<Void, String> delete(UUID id) throws Exception;

  public Search<E, C> getBy(C criteria, long page) throws Exception;
}
