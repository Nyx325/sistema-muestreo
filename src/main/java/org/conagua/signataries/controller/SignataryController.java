package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.*;

import java.util.*;

import org.conagua.common.controller.IController;
import org.conagua.common.controller.StringUtils;
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

    if (data.getFirstName() == null)
      throw new NullPointerException("firstName can not be null");

    if (!validator.compoudNameIsValid(data.getFirstName()))
      errors.add("formato de primer nombre inválido");

    if (data.getMidName().isPresent() &&
        !validator.singleNameIsValid(data.getMidName().get())) {
      errors.add("formato de segundo nombre inválido");
    }

    if (data.getFatherLastname() == null)
      throw new NullPointerException("fatherLastname can not be null");

    if (!validator.compoudNameIsValid(data.getFatherLastname())) {
      errors.add("formato de primer apellido inválido");
    }

    if (data.getMotherLastname().isPresent() &&
        !validator.singleNameIsValid(data.getMotherLastname().get())) {
      errors.add("formato de segundo apellido inválido");
    }

    if (errors.size() != 0) {
      String msg = String.join(", ", errors);
      return Result.err(StringUtils.capitalize(msg));
    }

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

  @Override
  public Optional<ISignatary> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return this.repo.get(id);
  }
}
