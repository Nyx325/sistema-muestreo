package org.conagua.unitsPerStandard.controller;

import java.util.*;
import org.conagua.common.model.entity.*;

import org.conagua.common.controller.Controller;
import org.conagua.common.model.repository.IRepository;
import org.conagua.unitsPerStandard.model.entity.*;
import org.conagua.unitsPerStandard.model.repository.UnitPerStdSQLiteRepository;

public class UnitPerStdController extends Controller<IUnitPerStd, INewUnitPerStd, UnitPerStdCriteria> {
  public UnitPerStdController(IRepository<IUnitPerStd, UnitPerStdCriteria> repo) {
    super(repo);
  }

  public UnitPerStdController() {
    this(new UnitPerStdSQLiteRepository());
  }

  public Result<Void, String> validateNewStd(INewUnitPerStd obj) {
    List<String> errors = new ArrayList<>();

    if (obj.getStandard() == null) {
      errors.add("no se especificó una norma");
    }

    if (obj.getUnit() == null) {
      errors.add("no se especificó una unidad");
    }

    if (errors.isEmpty()) {
      return Result.ok();
    } else {
      return Result.err(String.join(", ", errors));
    }
  }

  @Override
  public Result<IUnitPerStd, String> add(INewUnitPerStd data) throws Exception {
    Result<Void, String> validation = validateNewStd(data);

    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    UnitPerStdCriteria criteria = new UnitPerStdCriteria(
        Optional.of(data.getStandard()),
        Optional.of(data.getUnit()));

    Search<IUnitPerStd, UnitPerStdCriteria> search = getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      return Result.err("ya existe un estandar con ese nombre");
    }

    IUnitPerStd std = new UnitPerStd(
        UUID.randomUUID(),
        data.getStandard(),
        data.getUnit());

    repo.add(std);
    return Result.ok(std);
  }

  @Override
  public Optional<IUnitPerStd> get(UUID id) throws Exception {
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
  public Result<Void, String> delete(IUnitPerStd data) throws Exception {
    if (!get(data.getId()).isPresent())
      return Result.err("No se encontró el estandar");

    repo.delete(data.getId());
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(IUnitPerStd data) throws Exception {
    INewUnitPerStd obj = new NewUnitPerStd(
        data.getStandard(),
        data.getUnit());

    Result<Void, String> validation = validateNewStd(obj);

    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    UnitPerStdCriteria criteria = new UnitPerStdCriteria(
        Optional.of(data.getStandard()),
        Optional.of(data.getUnit()));

    Search<IUnitPerStd, UnitPerStdCriteria> search = getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IUnitPerStd> result = search.getResult().get();
      IUnitPerStd std = result.get(0);

      if (!std.getId().equals(data.getId()))
        return Result.err("ya existe un estandar con ese nombre");
    }

    repo.update(data);
    return Result.ok();
  }

  @Override
  public Search<IUnitPerStd, UnitPerStdCriteria> getBy(UnitPerStdCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }
}
