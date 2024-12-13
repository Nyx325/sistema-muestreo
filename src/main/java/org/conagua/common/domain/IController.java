
package org.conagua.common.domain;

import java.util.UUID;

public interface IController {
  public IEntity get(UUID id) throws UserError;

  public void add(IEntity data) throws UserError;

  public void update(IEntity data) throws UserError;

  public void delete(IEntity data) throws UserError;

  public void delete(UUID id) throws UserError;

  public Search<IEntity> getBy(Object criteria, long page) throws UserError;
}
