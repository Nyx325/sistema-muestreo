package org.conagua.actions.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.controller.*;
import org.conagua.common.model.entity.*;
import org.conagua.common.model.repository.IRepository;

import org.conagua.actions.model.entity.*;
import org.conagua.actions.model.repository.ActionSQLiteRepository;

import org.conagua.parameters.model.entity.*;
import org.conagua.parameters.model.repository.ParameterSQLiteRepository;

public class ActionController extends Controller<IAction, INewAction, ActionCriteria> {
  protected final IRepository<IParameter, ParameterCriteria> parameterRepo;

  public ActionController(
      IRepository<IAction, ActionCriteria> repo,
      IRepository<IParameter, ParameterCriteria> parameterRepo) {
    super(repo);
    this.parameterRepo = parameterRepo;
  }

  public ActionController() {
    this(new ActionSQLiteRepository(), new ParameterSQLiteRepository());
  }

  public Result<Void, String> validateNewAction(INewAction data) throws Exception {
    List<String> errors = new ArrayList<>();
    if (data.getName() == null || data.getName().isEmpty()) {
      errors.add("formato de nombre inválido");
    }

    UUID param = data.getParam().get();
    System.out.println("ID param: " + param);
    System.out.println(repo.get(param).isPresent());

    if (data.getParam().isPresent() &&
        !parameterRepo.get(data.getParam().get()).isPresent()) {
      errors.add("no se encontró el parámetro");
    }

    if (!errors.isEmpty()) {
      String err = String.join(", ", errors);
      return Result.err(StringUtils.capitalize(err));
    }

    return Result.ok();
  }

  @Override
  public Result<IAction, String> add(INewAction data) throws Exception {
    Result<Void, String> validation = validateNewAction(data);

    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    ActionCriteria criteria = new ActionCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IAction, ActionCriteria> s = repo.getBy(criteria, 1);

    if (s.getResult().isPresent()) {
      return Result.err("ya existe una prueba con ese nombre");
    }

    IAction a = new Action(
        UUID.randomUUID(),
        true,
        data.getName(),
        data.getParam());

    repo.add(a);
    return Result.ok(a);
  }

  @Override
  public Optional<IAction> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (!get(id).isPresent()) {
      return Result.err("no se encontró la prueba o acción");
    }

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(IAction data) throws Exception {
    if (!get(data.getId()).isPresent()) {
      return Result.err("no se encontró la prueba o acción");
    }

    repo.delete(data);
    return Result.ok();
  }

  @Override
  public Search<IAction, ActionCriteria> getBy(ActionCriteria criteria, long page) throws Exception {
    return repo.getBy(criteria, page);
  }

  @Override
  public Result<Void, String> update(IAction data) throws Exception {
    Result<Void, String> result;
    result = validateNewAction(new NewAction(data.getName(), data.getParam()));

    if (result.isErr()) {
      return Result.err(result.unwrapErr());
    }

    ActionCriteria criteria = new ActionCriteriaBuilder()
        .nameEq(data.getName())
        .build();

    Search<IAction, ActionCriteria> s = getBy(criteria, 1);

    if (s.getResult().isPresent()) {
      List<IAction> sResult = s.getResult().get();
      for (IAction action : sResult) {
        if (!action.getId().equals(data.getId())) {
          return Result.err("ya existe una prueba o acción con ese nombre");
        }
      }
    }

    repo.update(data);
    return Result.ok();
  }
}
