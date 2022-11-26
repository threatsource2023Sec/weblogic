package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Meter;

public class DisabledMeter extends Meter implements DisabledMetric {
   public void mark() {
   }

   public void mark(long n) {
   }

   public long getCount() {
      return 0L;
   }

   public double getFifteenMinuteRate() {
      return 0.0;
   }

   public double getFiveMinuteRate() {
      return 0.0;
   }

   public double getMeanRate() {
      return 0.0;
   }

   public double getOneMinuteRate() {
      return 0.0;
   }
}
