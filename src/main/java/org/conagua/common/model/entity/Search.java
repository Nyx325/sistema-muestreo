package org.conagua.common.model.entity;

import java.util.List;
import java.util.Optional;

public class Search<T, C> {
  long totalPages;
  long currentPage;
  C criteria;
  Optional<List<T>> result;

  public Search(long totalPages, long currentPage, C criteria, Optional<List<T>> result) {
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

  public Search(long totalPages, long currentPage, C criteria, List<T> result) {
    this(totalPages, currentPage, criteria, Optional.of(result));
  }

  public Search(long totalPages, long currentPage, C criteria) {
    this(totalPages, currentPage, criteria, Optional.empty());
  }

  public void setCurrentPage(long currentPage) {
    this.currentPage = currentPage;
  }

  public Optional<List<T>> getResult() {
    return result;
  }

  public C getCriteria() {
    return criteria;
  }

  public long getTotalPages() {
    return totalPages;
  }

  public long getCurrentPage() {
    return currentPage;
  }
}
