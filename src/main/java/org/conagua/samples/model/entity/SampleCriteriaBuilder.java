package org.conagua.samples.model.entity;

import java.util.*;
import java.time.*;

import org.conagua.common.model.entity.StringCriteria;
import org.conagua.common.model.entity.StringCriteria.Mode;

public class SampleCriteriaBuilder {
  private SampleCriteria criteria;

  public SampleCriteriaBuilder() {
    this.criteria = new SampleCriteria();
  }

  public SampleCriteria build() {
    return criteria;
  }

  public SampleCriteriaBuilder id(UUID id) {
    criteria.id = Optional.of(id);
    return this;
  }

  public SampleCriteriaBuilder active(boolean active) {
    criteria.active = Optional.of(active);
    return this;
  }

  public SampleCriteriaBuilder sampleDate(LocalDateTime sampleDate) {
    criteria.sampleDate = Optional.of(sampleDate);
    return this;
  }

  public SampleCriteriaBuilder receiptDate(LocalDate receiptDate) {
    criteria.receiptDate = Optional.of(receiptDate);
    return this;
  }

  public SampleCriteriaBuilder conaguaAiId(long conaguaAiId) {
    criteria.conaguaAiId = Optional.of(conaguaAiId);
    return this;
  }

  public SampleCriteriaBuilder sampleNumber(long sampleNumber) {
    criteria.sampleNumber = Optional.of(sampleNumber);
    return this;
  }

  public SampleCriteriaBuilder projectEq(String project) {
    criteria.project = Optional.of(new StringCriteria(Mode.EQ, project));
    return this;
  }

  public SampleCriteriaBuilder projectLike(String project) {
    criteria.project = Optional.of(new StringCriteria(Mode.LIKE, project));
    return this;
  }

  public SampleCriteriaBuilder samplingPlanEq(String samplingPlan) {
    criteria.samplingPlan = Optional.of(new StringCriteria(Mode.EQ, samplingPlan));
    return this;
  }

  public SampleCriteriaBuilder samplingPlanLike(String samplingPlan) {
    criteria.samplingPlan = Optional.of(new StringCriteria(Mode.LIKE, samplingPlan));
    return this;
  }

  public SampleCriteriaBuilder sampler(UUID sampler) {
    criteria.sampler = Optional.of(sampler);
    return this;
  }

  public SampleCriteriaBuilder site(UUID site) {
    criteria.site = Optional.of(site);
    return this;
  }

  public SampleCriteriaBuilder std(UUID std) {
    criteria.std = Optional.of(std);
    return this;
  }
}
