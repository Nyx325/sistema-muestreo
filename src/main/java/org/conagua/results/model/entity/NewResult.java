package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.json.JsonObject;

public class NewResult implements INewResult {
  private JsonObject result;
  private String regex;
  private LocalDate analysisDate;
  private UUID analyst;
  private UUID test;
  private UUID std;
  private UUID sample;

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
