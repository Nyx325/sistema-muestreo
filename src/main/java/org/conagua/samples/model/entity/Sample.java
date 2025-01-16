package org.conagua.samples.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Sample implements ISample {
  private UUID id;
  private boolean active;
  private LocalDateTime sampleDate;
  private LocalDate receiptDate;
  private long conaguaAiId;
  private long sampleNumber;
  private String project;
  private String samplingPlan;
  private UUID sampler;
  private UUID site;
  private UUID std;

  public Sample(
      UUID id,
      boolean active,
      LocalDateTime sampleDate,
      LocalDate receiptDate,
      long conaguaAiId,
      long sampleNumber,
      String project,
      String samplingPlan,
      UUID sampler,
      UUID site,
      UUID std) {
    this.id = id;
    this.active = active;
    this.sampleDate = sampleDate;
    this.receiptDate = receiptDate;
    this.conaguaAiId = conaguaAiId;
    this.sampleNumber = sampleNumber;
    this.project = project;
    this.samplingPlan = samplingPlan;
    this.sampler = sampler;
    this.site = site;
    this.std = std;
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
  public UUID getSite() {
    return site;
  }

  @Override
  public String getProject() {
    return project;
  }

  @Override
  public UUID getSampler() {
    return sampler;
  }

  @Override
  public UUID getStandard() {
    return std;
  }

  @Override
  public LocalDateTime getSampleDate() {
    return sampleDate;
  }

  @Override
  public long getConaguaAiId() {
    return conaguaAiId;
  }

  @Override
  public LocalDate getReceiptDate() {
    return receiptDate;
  }

  @Override
  public long getSampleNumber() {
    return sampleNumber;
  }

  @Override
  public String getSamplingPlan() {
    return samplingPlan;
  }

  @Override
  public void setSite(UUID site) {
    this.site = site;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public void setProject(String project) {
    this.project = project;
  }

  @Override
  public void setSampler(UUID sampler) {
    this.sampler = sampler;
  }

  @Override
  public void setStandard(UUID standard) {
    this.std = standard;
  }

  @Override
  public void setSampleDate(LocalDateTime sampleDate) {
    this.sampleDate = sampleDate;
  }

  @Override
  public void setConaguaAiId(long conaguaAiId) {
    this.conaguaAiId = conaguaAiId;
  }

  @Override
  public void setReceiptDate(LocalDate receiptDate) {
    this.receiptDate = receiptDate;
  }

  @Override
  public void setSampleNumber(long sampleNumber) {
    this.sampleNumber = sampleNumber;
  }

  @Override
  public void setSamplingPlan(String samplingPlan) {
    this.samplingPlan = samplingPlan;
  }
}
