package org.conagua.units.controller;

import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.units.model.entity.*;

import org.conagua.common.controller.Controller;
import org.conagua.common.controller.StringUtils;
import org.conagua.common.model.repository.IRepository;
import org.conagua.units.model.repository.UnitSQLiteRepository;

public class UnitController extends Controller<IUnit, INewUnit, UnitCriteria> {
  public UnitController(IRepository<IUnit, UnitCriteria> repo) {
    super(repo);
  }

  public UnitController() {
    this(new UnitSQLiteRepository());
  }

  private Result<Void, String> validateNewUnit(INewUnit u) {
    List<String> errors = new ArrayList<>();

    if (u.getLongName() == null || u.getLongName().isEmpty()) {
      errors.add("formato de nombre largo inválido");
    }

    if (u.getShortName() == null || u.getShortName().isEmpty()) {
      errors.add("formato de nombre corto inválido");
    }

    if (u.getWindowType() == null) {
      errors.add("no se especificó un tipo de ventana");
    }

    if (u.getRegex().isPresent()) {
      String regex = u.getRegex().get();
      if (regex == null || regex.isEmpty()) {
        errors.add("formato de regex inválido");
      }
    }

    if (!errors.isEmpty()) {
      String err = String.join(", ", errors);
      return Result.err(StringUtils.capitalize(err));
    }

    return Result.ok();
  }

  @Override
  public Result<IUnit, String> add(INewUnit data) throws Exception {
    List<String> errors = new ArrayList<>();
    Result<Void, String> validation = validateNewUnit(data);
    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    UnitCriteria c1 = new UnitCriteriaBuilder()
        .longNameEq(data.getLongName())
        .build();

    UnitCriteria c2 = new UnitCriteriaBuilder()
        .shortNameEq(data.getShortName())
        .build();

    Search<IUnit, UnitCriteria> s1 = repo.getBy(c1, 1);
    Search<IUnit, UnitCriteria> s2 = repo.getBy(c2, 1);

    if (s1.getResult().isPresent()) {
      errors.add("ya existe una unidad con ese nombre largo");
    }

    if (s2.getResult().isPresent()) {
      errors.add("ya existe una unidad con ese nombre corto");
    }

    if (!errors.isEmpty()) {
      String err = String.join(", ", errors);
      return Result.err(StringUtils.capitalize(err));
    }

    IUnit unit = new Unit(
        UUID.randomUUID(),
        true,
        data.getLongName(),
        data.getShortName(),
        data.getWindowType(),
        data.getRegex());

    repo.add(unit);
    return Result.ok(unit);
  }

  @Override
  public Optional<IUnit> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (!this.get(id).isPresent()) {
      Result.err("no se encontró el registro");
    }

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(IUnit data) throws Exception {
    if (!this.get(data.getId()).isPresent()) {
      return Result.err("no se encontró el registro");
    }

    repo.delete(data);
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(IUnit data) throws Exception {
    if (!this.get(data.getId()).isPresent()) {
      return Result.err("no se encontró el registro");
    }

    List<String> errors = new ArrayList<>();

    INewUnit u = new NewUnit(
        data.getLongName(),
        data.getShortName(),
        data.getWindowType(),
        data.getRegex());

    Result<Void, String> validation = validateNewUnit(u);
    if (validation.isErr()) {
      errors.add(validation.unwrapErr());
    }

    UnitCriteria c1 = new UnitCriteriaBuilder()
        .longNameEq(data.getLongName())
        .build();

    UnitCriteria c2 = new UnitCriteriaBuilder()
        .shortNameEq(data.getShortName())
        .build();

    Search<IUnit, UnitCriteria> s1 = repo.getBy(c1, 1);
    Search<IUnit, UnitCriteria> s2 = repo.getBy(c2, 1);

    if (s1.getResult().isPresent()) {
      List<IUnit> list = s1.getResult().get();
      for (IUnit unit : list) {
        if (!unit.getId().equals(data.getId()) &&
            unit.getLongName().equals(data.getLongName())) {
          errors.add("ya existe otro elemento con ese nombre largo");
          break;
        }
      }
    }

    if (s2.getResult().isPresent()) {
      List<IUnit> list = s2.getResult().get();
      for (IUnit unit : list) {
        if (!unit.getId().equals(data.getId()) &&
            unit.getShortName().equals(data.getShortName())) {
          errors.add("ya existe otro elemento con ese nombre corto");
          break;
        }
      }
    }

    return Result.ok();
  }

  @Override
  public Search<IUnit, UnitCriteria> getBy(UnitCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }
}
