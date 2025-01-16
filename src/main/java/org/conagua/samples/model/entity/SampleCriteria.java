package org.conagua.samples.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.conagua.common.model.entity.StringCriteria;

public class SampleCriteria {
  public Optional<UUID> id;
  public Optional<Boolean> active;
  public Optional<LocalDateTime> sampleDate;
  public Optional<LocalDate> receiptDate;
  public Optional<Long> conaguaAiId;
  public Optional<Long> sampleNumber;
  public Optional<StringCriteria> project;
  public Optional<StringCriteria> samplingPlan;
  public Optional<UUID> sampler;
  public Optional<UUID> site;
  public Optional<UUID> std;

  public SampleCriteria(
      Optional<Boolean> active,
      Optional<LocalDateTime> sampleDate,
      Optional<LocalDate> receiptDate,
      Optional<Long> conaguaAiId,
      Optional<Long> sampleNumber,
      Optional<StringCriteria> project,
      Optional<StringCriteria> samplingPlan,
      Optional<UUID> id,
      Optional<UUID> sampler,
      Optional<UUID> site,
      Optional<UUID> std) {
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

  public SampleCriteria() {
    this.id = Optional.empty();
    this.active = Optional.empty();
    this.sampleDate = Optional.empty();
    this.receiptDate = Optional.empty();
    this.conaguaAiId = Optional.empty();
    this.sampleNumber = Optional.empty();
    this.project = Optional.empty();
    this.samplingPlan = Optional.empty();
    this.sampler = Optional.empty();
    this.site = Optional.empty();
    this.std = Optional.empty();
  }
}
