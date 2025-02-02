package org.conagua.results.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

import jakarta.json.JsonObject;

public interface IResult extends IEntity, ILogicalDeletable {
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
