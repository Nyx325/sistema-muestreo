package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.json.JsonObject;

public class Result implements IResult {
  private UUID id;
  private boolean active;
  private JsonObject result;
  private String regex;
  private LocalDate analysisDate;
  private UUID analyst;
  private UUID test;
  private UUID std;
  private UUID sample;

  public Result(
      UUID id, boolean active, JsonObject result,
      String regex, LocalDate analysisDate, UUID analyst,
      UUID test, UUID std, UUID sample) {
    this.id = id;
    this.active = active;
    this.result = result;
    this.regex = regex;
    this.analysisDate = analysisDate;
    this.analyst = analyst;
    this.test = test;
    this.std = std;
    this.sample = sample;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public UUID getTest() {
    return test;
  }

  @Override
  public UUID getSample() {
    return sample;
  }

  @Override
  public UUID getAnalyst() {
    return analyst;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public JsonObject getResult() {
    return result;
  }

  @Override
  public LocalDate getAnalysisDate() {
    return analysisDate;
  }

  @Override
  public void setTest(UUID test) {
    this.test = test;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public void setSample(UUID sample) {
    this.sample = sample;
  }

  @Override
  public void setAnalyst(UUID analyst) {
    this.analyst = analyst;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public void setStandard(UUID std) {
    this.std = std;
  }

  @Override
  public void setResult(JsonObject result) {
    this.result = result;
  }

  @Override
  public void setAnalysisDate(LocalDate analysisDate) {
    this.analysisDate = analysisDate;
  }
}
