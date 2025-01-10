package org.conagua.common.controller;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.repository.IRepository;

public abstract class Controller<E extends IEntity, NE, C> implements IController<E, NE, C> {
  protected IRepository<E, C> repo;

  public Controller(IRepository<E, C> repo) {
    this.repo = repo;
  }
}
