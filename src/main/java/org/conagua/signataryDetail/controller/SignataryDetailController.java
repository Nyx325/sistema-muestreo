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

    if (obj.getStandard() == null) {
      errors.add("no se proporcionó una norma");
    }

    if (obj.getUnit() == null) {
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

    SignataryDetailCriteria criteria = new SignataryDetailCriteria(
        Optional.of(data.getUnit()),
        Optional.of(data.getStandard()));

    ISignataryDetail sig = new SignataryDetail(
        UUID.randomUUID(),
        data.getStandard(),
        data.getUnit());

    repo.add(sig);
    return Result.ok(sig);
  }
}
