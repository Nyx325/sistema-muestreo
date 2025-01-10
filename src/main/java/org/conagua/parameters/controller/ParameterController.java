package org.conagua.parameters.controller;

import java.util.*;

import org.conagua.common.model.entity.*;
import org.conagua.common.controller.*;
import org.conagua.parameters.model.entity.*;

import org.conagua.common.model.repository.IRepository;

import org.conagua.parameters.model.repository.ParameterSQLiteRepository;

public class ParameterController extends Controller<IParameter, INewParameter, ParameterCriteria> {
  public ParameterController(IRepository<IParameter, ParameterCriteria> repo) {
    super(repo);
  }

  public ParameterController() {
    this(new ParameterSQLiteRepository());
  }

  public Result<Void, String> validateNewParameter(INewParameter data) {
    if (data.getName().isEmpty() || data.getName() == null) {
      return Result.err("formato de nombre inválido");
    }

    return Result.ok();
  }

  @Override
  public Result<IParameter, String> add(INewParameter data) throws Exception {
    Result<Void, String> result = validateNewParameter(data);

    if (result.isErr()) {
      return Result.err(result.unwrapErr());
    }

    ParameterCriteria criteria = new ParameterCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IParameter, ParameterCriteria> search = getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      return Result.err("ya existe un parámetro con ese nombre");
    }

    IParameter p = new Parameter(
        UUID.randomUUID(),
        true,
        data.getName());

    repo.add(p);
    return Result.ok(p);
  }

  @Override
  public Optional<IParameter> get(UUID id) throws Exception {
    if (id == null) {
      throw new NullPointerException();
    }

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (id == null) {
      throw new NullPointerException();
    }

    if (!repo.get(id).isPresent()) {
      return Result.err("no se encontró el parámetro");
    }

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(IParameter data) throws Exception {
    if (!repo.get(data.getId()).isPresent()) {
      return Result.err("no se encontró el parámetro");
    }

    repo.delete(data);
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(IParameter data) throws Exception {
    Result<Void, String> result;
    result = validateNewParameter(new NewParameter(data.getName()));

    if (result.isErr()) {
      return Result.err(result.unwrapErr());
    }

    ParameterCriteria criteria = new ParameterCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IParameter, ParameterCriteria> search = repo.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      IParameter p = search.getResult().get().get(0);
      if (!data.getId().equals(p.getId())) {
        return Result.err("ya existe un parametro con ese nombre");
      }
    }

    repo.update(data);
    return Result.ok();
  }

  @Override
  public Search<IParameter, ParameterCriteria> getBy(ParameterCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }
}
