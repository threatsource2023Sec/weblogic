package com.googlecode.cqengine.query.option;

public enum EngineThresholds {
   INDEX_ORDERING_SELECTIVITY(0.0);

   final double thresholdDefault;

   private EngineThresholds(double thresholdDefault) {
      this.thresholdDefault = thresholdDefault;
   }

   public double getThresholdDefault() {
      return this.thresholdDefault;
   }
}
