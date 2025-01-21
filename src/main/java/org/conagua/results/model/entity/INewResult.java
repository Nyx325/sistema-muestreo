package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.json.JsonObject;

public interface INewResult {

  JsonObject getResult();

  String getRegex();

  LocalDate getAnalysisDate();

  UUID getAnalyst();

  UUID getTest();

  UUID getStandard();

  UUID getSample();

  void setResult(JsonObject result);

  void setRegex(String regex);

  void setAnalysisDate(LocalDate analysisDate);

  void setAnalyst(UUID analyst);

  void setTest(UUID test);

  void setStandard(UUID standard);

  void setSample(UUID sample);
}
