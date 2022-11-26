package com.bea.core.repackaged.springframework.jmx.export.metadata;

import com.bea.core.repackaged.springframework.jmx.support.MetricType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ManagedMetric extends AbstractJmxAttribute {
   @Nullable
   private String category;
   @Nullable
   private String displayName;
   private MetricType metricType;
   private int persistPeriod;
   @Nullable
   private String persistPolicy;
   @Nullable
   private String unit;

   public ManagedMetric() {
      this.metricType = MetricType.GAUGE;
      this.persistPeriod = -1;
   }

   public void setCategory(@Nullable String category) {
      this.category = category;
   }

   @Nullable
   public String getCategory() {
      return this.category;
   }

   public void setDisplayName(@Nullable String displayName) {
      this.displayName = displayName;
   }

   @Nullable
   public String getDisplayName() {
      return this.displayName;
   }

   public void setMetricType(MetricType metricType) {
      Assert.notNull(metricType, (String)"MetricType must not be null");
      this.metricType = metricType;
   }

   public MetricType getMetricType() {
      return this.metricType;
   }

   public void setPersistPeriod(int persistPeriod) {
      this.persistPeriod = persistPeriod;
   }

   public int getPersistPeriod() {
      return this.persistPeriod;
   }

   public void setPersistPolicy(@Nullable String persistPolicy) {
      this.persistPolicy = persistPolicy;
   }

   @Nullable
   public String getPersistPolicy() {
      return this.persistPolicy;
   }

   public void setUnit(@Nullable String unit) {
      this.unit = unit;
   }

   @Nullable
   public String getUnit() {
      return this.unit;
   }
}
