package org.conagua.common.model.repository;

import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.Search;

public interface IRepository {
  public IEntity get(UUID id) throws Exception;

  public void add(IEntity data) throws Exception;

  public void update(IEntity data) throws Exception;

  public void delete(IEntity data) throws Exception;

  public void delete(UUID id) throws Exception;

  public Search getBy(Object criteria, long page) throws Exception;
}
