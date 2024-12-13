package org.conagua.user.extern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.IRepository;
import org.conagua.common.domain.Search;
import org.conagua.common.domain.UserError;
import org.conagua.user.domain.IUser;

public class InMemoryUserRepository implements IRepository {
  private List<IEntity> users;

  public InMemoryUserRepository() {
    this.users = new ArrayList<>();
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));
    users.add(new InMemoryUser("C", "D"));

  }

  @Override
  public void add(IEntity data) throws UserError {
    if (!(data instanceof IUser))
      throw new UserError("data must be a IUser");
    users.add(data);
  }

  @Override
  public void delete(UUID id) throws UserError {
    IEntity usr = get(id);

    if (usr == null)
      throw new UserError("User not found");

    usr.setActive(false);
  }

  @Override
  public void delete(IEntity data) throws UserError {
    delete(data.getId());
  }

  @Override
  public void update(IEntity data) throws UserError {
    IEntity usr = get(data.getId());

    if (usr == null)
      throw new UserError("User not found");

    usr = data;
  }

  @Override
  public IEntity get(UUID id) throws UserError {
    for (IEntity user : users) {
      if (user.getId() == id) {
        return user;
      }
    }

    return null;
  }

  @Override
  public Search getBy(Object criteria, long page) throws UserError {
    return new Search(1, 1, criteria, users);
  }
}
