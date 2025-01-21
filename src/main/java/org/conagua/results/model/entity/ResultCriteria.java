package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.StringCriteria;

import jakarta.json.JsonObject;

public class ResultCriteria {
  public Optional<JsonObject> result;
  public Optional<StringCriteria> regex;
  public Optional<LocalDate> analysisDate;
  public Optional<UUID> analyst;
  public Optional<UUID> test;
  public Optional<UUID> std;
  public Optional<UUID> sample;

  public ResultCriteria(
      Optional<JsonObject> result,
      Optional<StringCriteria> regex,
      Optional<LocalDate> analysisDate,
      Optional<UUID> analyst,
      Optional<UUID> test,
      Optional<UUID> std,
      Optional<UUID> sample) {
    this.result = result;
    this.regex = regex;
    this.analysisDate = analysisDate;
    this.analyst = analyst;
    this.test = test;
    this.std = std;
    this.sample = sample;
  }

  public ResultCriteria() {
    this.result = Optional.empty();
    this.regex = Optional.empty();
    this.analysisDate = Optional.empty();
    this.analyst = Optional.empty();
    this.test = Optional.empty();
    this.std = Optional.empty();
    this.sample = Optional.empty();
  }
}
