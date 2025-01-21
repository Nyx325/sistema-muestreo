package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

import jakarta.json.JsonObject;

public class ResultCriteriaBuilder {
  private ResultCriteria criteria;

  public ResultCriteriaBuilder() {
    this.criteria = new ResultCriteria();
  }

  public ResultCriteriaBuilder result(JsonObject result) {
    criteria.result = Optional.of(result);
    return this;
  }

  public ResultCriteriaBuilder regexLike(StringCriteria result) {
    criteria.regex = Optional.of(
        new StringCriteria(
            Mode.LIKE,
            result.getValue()));

    return this;
  }

  public ResultCriteriaBuilder regexEq(StringCriteria result) {
    criteria.regex = Optional.of(
        new StringCriteria(
            Mode.EQ,
            result.getValue()));

    return this;
  }

  public ResultCriteriaBuilder analysisDate(LocalDate date) {
    criteria.analysisDate = Optional.of(date);
    return this;
  }

  public ResultCriteriaBuilder analyst(UUID analyst) {
    criteria.analyst = Optional.of(analyst);
    return this;
  }

  public ResultCriteriaBuilder test(UUID test) {
    criteria.test = Optional.of(test);
    return this;
  }

  public ResultCriteriaBuilder standard(UUID std) {
    criteria.std = Optional.of(std);
    return this;
  }

  public ResultCriteriaBuilder sample(UUID sample) {
    criteria.sample = Optional.of(sample);
    return this;
  }
}
