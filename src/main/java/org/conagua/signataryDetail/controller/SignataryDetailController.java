package org.conagua.signataryDetail.controller;

import java.util.*;

import org.conagua.actions.model.entity.ActionCriteria;
import org.conagua.actions.model.entity.IAction;
import org.conagua.actions.model.repository.ActionSQLiteRepository;
import org.conagua.common.controller.*;
import org.conagua.common.model.entity.*;
import org.conagua.common.model.repository.IRepository;
import org.conagua.signataries.model.entity.ISignatary;
import org.conagua.signataries.model.entity.SignataryCriteria;
import org.conagua.signataries.model.repository.SignatarySQLiteRepository;
import org.conagua.signataryDetail.model.entity.*;
import org.conagua.signataryDetail.model.repository.SignataryDetailSQLiteRepository;

public class SignataryDetailController
    extends Controller<ISignataryDetail, INewSignataryDetail, SignataryDetailCriteria> {

  protected IRepository<ISignatary, SignataryCriteria> repoSig;
  protected IRepository<IAction, ActionCriteria> repoAct;

  public SignataryDetailController(
      IRepository<ISignataryDetail, SignataryDetailCriteria> repo,
      IRepository<ISignatary, SignataryCriteria> repoSig,
      IRepository<IAction, ActionCriteria> repoAct) {
    super(repo);
    this.repoSig = repoSig;
    this.repoAct = repoAct;
  }

  public SignataryDetailController() {
    super(new SignataryDetailSQLiteRepository());
    this.repoSig = new SignatarySQLiteRepository();
    this.repoAct = new ActionSQLiteRepository();
  }

  protected Result<Void, String> validateNewSignataryDetail(INewSignataryDetail obj) {
    List<String> errors = new ArrayList<>();

    if (obj.getAction() == null) {
      errors.add("no se proporcionó una norma");
    }

    if (obj.getSignatary() == null) {
      errors.add("no se proporcionó unidad");
    }

    if (errors.size() != 0) {
      String err = String.join(", ", errors);
      return Result.err(StringUtils.capitalize(err));
    }

    return Result.ok();
  }

  @Override
  public Result<ISignataryDetail, String> add(INewSignataryDetail data) throws Exception {
    Result<Void, String> validation = validateNewSignataryDetail(data);
    if (validation.isErr()) {
      return Result.err(validation.unwrapErr());
    }

    if (!repoAct.get(data.getAction()).isPresent()) {
      return Result.err("No se encontró la acción");
    }

    if (!repoSig.get(data.getSignatary()).isPresent()) {
      return Result.err("No se encontró el signatario");
    }

    SignataryDetailCriteria criteria = new SignataryDetailCriteria(
        Optional.of(data.getAction()),
        Optional.of(data.getSignatary()));

    if (!repo.getBy(criteria, 1).getResult().isPresent()) {
      return Result.err("ya existe esta relación");
    }

    ISignataryDetail sig = new SignataryDetail(
        UUID.randomUUID(),
        data.getAction(),
        data.getSignatary());

    repo.add(sig);
    return Result.ok(sig);
  }

  @Override
  public Optional<ISignataryDetail> get(UUID id) throws Exception {
    if (id == null)
      throw new NullPointerException();

    return repo.get(id);
  }

  @Override
  public Result<Void, String> delete(UUID id) throws Exception {
    if (!repo.get(id).isPresent()) {
      return Result.err("No se encontró el registro");
    }

    repo.delete(id);
    return Result.ok();
  }

  @Override
  public Result<Void, String> delete(ISignataryDetail data) throws Exception {
    if (!repo.get(data.getId()).isPresent()) {
      return Result.err("No se encontró el registro");
    }

    repo.delete(data);
    return Result.ok();
  }

  @Override
  public Result<Void, String> update(ISignataryDetail data) throws Exception {
    return null;
  }

  @Override
  public Search<ISignataryDetail, SignataryDetailCriteria> getBy(SignataryDetailCriteria criteria, long page)
      throws Exception {
    return repo.getBy(criteria, page);
  }
}
