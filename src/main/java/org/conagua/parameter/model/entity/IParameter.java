package org.conagua.parameter.model.entity;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

public interface IParameter extends IEntity, ILogicalDeletable {
  String getName();
}
