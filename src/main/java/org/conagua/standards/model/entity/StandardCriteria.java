package org.conagua.standards.model.entity;

import org.conagua.common.model.entity.Criteria;

public class StandardCriteria implements Criteria {
  public Boolean active;
  public String name;

  public StandardCriteria() {
  }

  public StandardCriteria(String name) {
    this(name, null);
  }

  public StandardCriteria(Boolean active) {
    this(null, active);
  }

  public StandardCriteria(String name, Boolean active) {
    this.name = name;
    this.active = active;
  }

  @Override
  public boolean isEmpty() {
    return name == null;
  }
}
