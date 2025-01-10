package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.*;

import java.util.*;

import org.conagua.common.controller.Controller;
import org.conagua.common.controller.StringUtils;
import org.conagua.common.model.entity.Result;
import org.conagua.common.model.entity.Search;
import org.conagua.common.model.repository.IRepository;
import org.conagua.signataries.model.repository.SignatarySQLiteRepository;

public class SignataryController extends Controller<ISignatary, INewSignatary, SignataryCriteria> {
  private final NameValidator validator = new NameValidator();

  public SignataryController(IRepository<ISignatary, SignataryCriteria> repo) {
    super(repo);
  }

  public SignataryController() {
    this(new SignatarySQLiteRepository());
  }

  public Result<Void, String> newSignataryValidation(INewSignatary data) {
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

    return Result.ok();
  }

  @Override
  public Result<ISignatary, String> add(INewSignatary data) throws Exception {

    Result<Void, String> eval = newSignataryValidation(data);
    if (eval.isErr()) {
      String err = eval.unwrapErr();
      return Result.err(err);
    }

    Signatary s = new Signatary(
        UUID.randomUUID(),
        true,
        data.getFirstName(),
        data.getMidName(),
        data.getFatherLastname(),
        data.getMotherLastname());

    repo.add(s);
    return Result.ok(s);
  }

  @Override
  public Optional<ISignatary> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    if (!get(id).isPresent()) {
      return Result.err("No se encontró el Signatario");
    }

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(ISignatary data) throws Exception {
    if (data == null)
      throw new NullPointerException();

    if (!get(data.getId()).isPresent()) {
      return Result.err("No se encontró el Signatario");
    }

    repo.delete(data);
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(ISignatary data) throws Exception {
    Result<Void, String> result = newSignataryValidation(NewSignatary.of(data));
    if (result.isErr()) {
      return Result.err(result.unwrapErr());
    }

    repo.update(data);

    return Result.ok();
  }

  @Override
  public Search<ISignatary, SignataryCriteria> getBy(SignataryCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }
}
