package org.conagua.standards.controller;

import java.util.InputMismatchException;
import java.util.UUID;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.NotFoundException;
import org.conagua.common.controller.IController;
import org.conagua.common.model.repository.IRepository;

import org.conagua.standards.model.entity.*;
import org.conagua.standards.model.repository.StandardSQLiteRepository;

public class StandardController implements IController {
  private IRepository repo;

  public StandardController(IRepository repo) {
    this.repo = repo;
  }

  public StandardController() {
    this(new StandardSQLiteRepository());
  }

  private void checkInstance(IEntity data) {
    if (data instanceof IStandard == false)
      throw new IllegalArgumentException("data debe ser una instancia de IStandard");
  }

  @Override
  public void add(INewEntity data) throws InputMismatchException, Exception {
    if (!(data instanceof INewStandard))
      throw new InputMismatchException("data debe ser instancia de IStandard");

    INewStandard newStd = (INewStandard) data;

    Standard s = new Standard(
        UUID.randomUUID(),
        newStd.getName(),
        true);

    this.repo.add(s);
  }

  @Override
  public IEntity get(UUID id) throws InputMismatchException, Exception {
    return this.repo.get(id);
  }

  @Override
  public void delete(UUID id) throws InputMismatchException, Exception {
    if (get(id) == null)
      throw new NotFoundException("No se encontró la norma");

    this.repo.delete(id);
  }

  @Override
  public void delete(IEntity data) throws InputMismatchException, Exception {
    checkInstance(data);
    this.delete(data.getId());
  }

  @Override
  public void update(IEntity data) throws InputMismatchException, Exception {
    checkInstance(data);

    if (get(data.getId()) == null)
      throw new NotFoundException("No se encontró el signatario");

    this.repo.update(data);
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws InputMismatchException, Exception {
    return this.repo.getBy(criteria, page);
  }
}
