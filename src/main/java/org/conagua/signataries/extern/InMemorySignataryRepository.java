package org.conagua.signataries.extern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.IRepository;
import org.conagua.common.domain.Search;
import org.conagua.common.domain.UserError;
import org.conagua.signataries.domain.ISignatary;
import org.conagua.signataries.domain.SignataryCriteria;

public class InMemorySignataryRepository implements IRepository {
  private List<IEntity> signataries = new ArrayList<>();

  @Override
  public void add(IEntity data) throws UserError {
    this.signataries.add(data);
  }

  @Override
  public IEntity get(UUID id) throws UserError {
    for (IEntity s : signataries) {
      if (id.equals(s.getId()))
        return s;
    }

    return null;
  }

  @Override
  public void update(IEntity data) throws UserError {
    ISignatary d = (ISignatary) data;
    ISignatary s = (ISignatary) this.get(data.getId());

    if (s == null)
      throw new UserError("No se encontró el registro");

    s.setActive(d.isActive());
    s.setMidName(d.getMidName());
    s.setFirstName(d.getfirstName());
    s.setMotherLastname(d.getMotherLastname());
  }

  @Override
  public void delete(IEntity data) throws UserError {
    this.signataries.remove(data);
  }

  @Override
  public void delete(UUID id) throws UserError {
    for (IEntity s : signataries) {
      if (s.getId().equals(id))
        this.signataries.remove(s);
    }

    throw new UserError("No se encontró el signatario");
  }

  @Override
  public Search getBy(Object criteria, long page) throws UserError {
    List<IEntity> result = new ArrayList<>();

    for (IEntity iEntity : signataries) {
      ISignatary s = (ISignatary) iEntity;
      if (matchCriteria(s, criteria))
        result.add(s);
    }

    return new Search(1, 1, criteria, result);
  }

  protected boolean matchCriteria(ISignatary s, Object criteria) {
    SignataryCriteria c = (SignataryCriteria) criteria;
    return (c.firstName == null || s.getfirstName().contains(c.firstName)) &&
        (c.midName == null || s.getMidName().contains(c.midName)) &&
        (c.fatherLastname == null || s.getFatherLastname().contains(c.fatherLastname)) &&
        (c.motherLastname == null || s.getMotherLastname().contains(c.motherLastname));

  }
}
