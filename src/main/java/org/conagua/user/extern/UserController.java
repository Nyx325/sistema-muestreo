package org.conagua.user.extern;

import java.util.UUID;

import org.conagua.common.domain.IController;
import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.IRepository;
import org.conagua.common.domain.Search;
import org.conagua.common.domain.UserError;

public class UserController implements IController {
  private IRepository repo;

  public UserController(IRepository repo) {
    this.repo = repo;
  }

  public UserController() {
    this(new InMemoryUserRepository());
  }

  @Override
  public void add(IEntity data) throws UserError {
    repo.add(data);
  }

  @Override
  public void update(IEntity data) throws UserError {
    repo.update(data);
  }

  @Override
  public void delete(UUID id) throws UserError {
    repo.delete(id);
  }

  @Override
  public void delete(IEntity data) throws UserError {
    repo.delete(data);
  }

  @Override
  public IEntity get(UUID id) throws UserError {
    return repo.get(id);
  }

  @Override
  public Search getBy(Object criteria, long page) throws UserError {
    return repo.getBy(criteria, page);
  }
}
