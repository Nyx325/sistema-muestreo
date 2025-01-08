package org.conagua.signataries.controller;

import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.model.exceptions.NotFoundException;
import org.conagua.common.controller.Controller;
import org.conagua.common.model.repository.IRepository;
import org.conagua.signataries.model.entity.INewSignatary;
import org.conagua.signataries.model.entity.ISignatary;
import org.conagua.signataries.model.entity.Signatary;
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

  private void checkInstance(IEntity data) {
    if (data instanceof ISignatary == false)
      throw new IllegalArgumentException("data debe ser una instancia de ISignatary");
  }

  @Override
  public void add(INewEntity data) throws Exception {
    if (!(data instanceof INewSignatary))
      throw new IllegalArgumentException("data debe ser una instancia de INewSignatary");

    List<String> msg = new ArrayList<>();
    INewSignatary s = (INewSignatary) data;

    if (!nameValidator.compoudNameIsValid(s.getFirstName().toUpperCase()))
      msg.add("el formato del primer nombre es inválido");

    if (!nameValidator.singleNameIsValid(s.getMidName().toUpperCase()))
      msg.add("el formato del segundo nombre es inválido");

    if (!nameValidator.compoudNameIsValid(s.getFatherLastname().toUpperCase()))
      msg.add("el formato del primer apellido es inválido");

    if (!nameValidator.singleNameIsValid(s.getMotherLastname().toUpperCase()))
      msg.add("el formato del segundo apellido es inválido");

    if (msg.size() != 0) {
      String error = capitalizeString(String.join(", ", msg));
      throw new InputMismatchException(error);
    }

    Signatary sig = new Signatary(
        UUID.randomUUID(),
        true,
        s.getFirstName().trim().toUpperCase(),
        s.getMidName().trim().toUpperCase(),
        s.getFatherLastname().trim().toUpperCase(),
        s.getMotherLastname().trim().toUpperCase());

    this.repo.add(sig);
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
    checkInstance(data);

    if (get(data.getId()) == null)
      throw new NotFoundException("No se encontró el signatario");

    this.repo.update(data);
  }

  @Override
  public void delete(UUID id) throws Exception {
    if (get(id) == null)
      throw new NotFoundException("No se encontró el signatario");

    this.repo.delete(id);
  }

  @Override
  public void delete(IEntity data) throws Exception {
    if (get(data.getId()) == null)
      throw new NotFoundException("No se encontró el signatario");

    this.repo.delete(data);
  }
}
