package org.conagua.common.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Search {
  long totalPages;
  long currentPage;
  Criteria criteria;
  List<IEntity> result;

  public Search(long totalPages, long currentPage, Criteria criteria, List<IEntity> result) {
    int errors = 0;
    StringBuilder str = new StringBuilder();
    if (totalPages <= 0) {
      str.append("total pages cannot be 0 or less");
      errors++;
    }
    if (currentPage <= 0) {
      if (errors > 0)
        str.append(", ");
      str.append("total pages cannot be 0 or less");
      errors++;
    }

    if (errors > 0)
      throw new RuntimeException(str.toString());

    this.criteria = criteria;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    this.result = result;
  }

  public Search(long totalPages, long currentPage, Criteria criteria) {
    this(totalPages, currentPage, criteria, new ArrayList<>());
  }

  public void setCurrentPage(long currentPage) {
    this.currentPage = currentPage;
  }

  public List<IEntity> getResult() {
    return result;
  }

  public Criteria getCriteria() {
    return criteria;
  }

  public long getTotalPages() {
    return totalPages;
  }

  public long getCurrentPage() {
    return currentPage;
  }
}
