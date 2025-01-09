package org.conagua.standard.controller;

import java.util.*;
import org.conagua.common.model.entity.*;
import org.conagua.standard.model.entity.*;

import org.conagua.common.controller.IController;
import org.conagua.common.model.repository.IRepository;
import org.conagua.standard.model.repository.StandardSQLiteRepository;

public class StandardController implements IController<IStandard, INewStandard, StandardCriteria> {
  IRepository<IStandard, StandardCriteria> repo;

  public StandardController(IRepository<IStandard, StandardCriteria> repo) {
    this.repo = repo;
  }

  public StandardController() {
    this(new StandardSQLiteRepository());
  }

  public Result<Void, String> validateNewStd(INewStandard std) {
    List<String> errors = new ArrayList<>();

    if (std.getName() == null || std.getName().isEmpty()) {
      errors.add("El nombre no puede estar vacío");
    }

    if (errors.isEmpty()) {
      return Result.ok();
    } else {
      return Result.err(String.join(", ", errors));
    }
  }

  @Override
  public Result<IStandard, String> add(INewStandard data) throws Exception {
    Result<Void, String> validation = validateNewStd(data);

    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    StandardCriteria criteria = new StandardCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IStandard, StandardCriteria> search = getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      return Result.err("ya existe un estandar con ese nombre");
    }

    IStandard std = new Standard(
        UUID.randomUUID(),
        data.getName(),
        true);

    repo.add(std);
    return Result.ok(std);
  }

  @Override
  public Optional<IStandard> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (!get(id).isPresent())
      return Result.err("No se encontró el estandar");

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(IStandard data) throws Exception {
    if (!get(data.getId()).isPresent())
      return Result.err("No se encontró el estandar");

    repo.delete(data.getId());
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(IStandard data) throws Exception {
    Result<Void, String> validation = validateNewStd(new NewStandard(data.getName()));
    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    StandardCriteria criteria = new StandardCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IStandard, StandardCriteria> search = getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IStandard> result = search.getResult().get();
      IStandard std = result.get(0);

      if (!std.getId().equals(data.getId()))
        return Result.err("ya existe un estandar con ese nombre");
    }

    repo.update(data);
    return Result.ok();
  }

  @Override
  public Search<IStandard, StandardCriteria> getBy(StandardCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }
}
