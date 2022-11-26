package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Reservoir;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class DisabledTimer extends Timer implements DisabledMetric {
   public DisabledTimer() {
      super((Reservoir)null);
   }

   public void update(long duration, TimeUnit unit) {
   }

   public Object time(Callable event) throws Exception {
      return null;
   }

   public Timer.Context time() {
      return null;
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

   public Snapshot getSnapshot() {
      return null;
   }
}
