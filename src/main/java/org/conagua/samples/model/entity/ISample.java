package org.conagua.samples.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.ILogicalDeletable;

public interface ISample extends IEntity, ILogicalDeletable {
  public LocalDateTime getSampleDate();

  public LocalDate getReceiptDate();

  public long getConaguaAiId();

  public long getSampleNumber();

  public String getProject();

  public String getSamplingPlan();

  public UUID getSampler();

  public UUID getSite();

  public UUID getStandard();

  public void setSampleDate(LocalDateTime sampleDate);

  public void setReceiptDate(LocalDate receiptDate);

  public void setConaguaAiId(long conaguaAiId);

  public void setSampleNumber(long sampleNumber);

  public void setProject(String project);

  public void setSamplingPlan(String samplingPlan);

  public void setSampler(UUID sampler);

  public void setSite(UUID site);

  public void setStandard(UUID standard);
}
