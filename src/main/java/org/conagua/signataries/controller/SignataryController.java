package org.conagua.signataries.controller;

import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.controller.Controller;
import org.conagua.common.model.repository.IRepository;
import org.conagua.signataries.model.entity.ISignatary;
import org.conagua.signataries.model.repository.SignatarySQLiteRepository;

public class SignataryController extends Controller {
  private final IRepository repo;
  private final NameValidator nameValidator;

  public SignataryController() {
    this(new SignatarySQLiteRepository(), new NameValidator());
  }

  public SignataryController(IRepository repo) {
    this(repo, new NameValidator());
  }

  public SignataryController(NameValidator nameValidator) {
    this(new SignatarySQLiteRepository(), nameValidator);
  }

  public SignataryController(IRepository repo, NameValidator nameValidator) {
    this.repo = repo;
    this.nameValidator = nameValidator;
  }

  @Override
  public void add(IEntity data) throws Exception {
    if (data instanceof ISignatary == false)
      throw new IllegalArgumentException("data debe ser una instancia de ISignatary");

    List<String> msg = new ArrayList<>();
    ISignatary s = (ISignatary) data;

    if (s.getId() == null)
      msg.add("se debe especificar un ID");

    if (!nameValidator.compoudNameIsValid(s.getFirstName()))
      msg.add("el formato del primer nombre es inválido");

    if (!nameValidator.singleNameIsValid(s.getMidName()))
      msg.add("el formato del segundo nombre es inválido");

    if (!nameValidator.compoudNameIsValid(s.getFatherLastname()))
      msg.add("el formato del primer apellido es inválido");

    if (!nameValidator.singleNameIsValid(s.getMotherLastname()))
      msg.add("el formato del segundo apellido es inválido");

    if (msg.size() != 0) {
      String error = capitalizeString(String.join(", ", msg));
      throw new InputMismatchException(error);
    }

    this.repo.add(data);
  }

  @Override
  public IEntity get(UUID id) throws Exception {
    return this.repo.get(id);
  }

  @Override
  public Search getBy(Criteria criteria, long page) throws Exception {
    return this.repo.getBy(criteria, page);
  }

  @Override
  public void update(IEntity data) throws Exception {
    this.repo.update(data);
  }

  @Override
  public void delete(UUID id) throws Exception {
    this.repo.delete(id);
  }

  @Override
  public void delete(IEntity data) throws Exception {
    this.repo.delete(data);
  }
}
