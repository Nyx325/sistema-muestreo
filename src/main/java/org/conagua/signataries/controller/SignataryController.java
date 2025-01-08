package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.*;

import java.util.*;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.Result;
import org.conagua.common.model.repository.IRepository;
import org.conagua.signataries.model.repository.SignatarySQLiteRepository;

public class SignataryController implements IController<ISignatary, INewSignatary, SignataryCriteria> {
  private final NameValidator validator = new NameValidator();
  private final IRepository<ISignatary, SignataryCriteria> repo;

  public SignataryController(IRepository<ISignatary, SignataryCriteria> repo) {
    this.repo = repo;
  }

  public SignataryController() {
    this(new SignatarySQLiteRepository());
  }

  @Override
  public Result<ISignatary, String> add(INewSignatary data) throws Exception {
    List<String> errors = new ArrayList<>();

    if (!validator.compoudNameIsValid(data.getFirstName()))
      errors.add("Formato de primer nombre inválido");

    if (errors.size() != 0)
      return Result.err(String.join(", ", errors));

    Signatary s = new Signatary(
        UUID.randomUUID(),
        true,
        data.getFirstName(),
        data.getMidName(),
        data.getFatherLastname(),
        data.getMotherLastname());

    this.repo.add(s);
    return Result.ok(s);
  }
}
