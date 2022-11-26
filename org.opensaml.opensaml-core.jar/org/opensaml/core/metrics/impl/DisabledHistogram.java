package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Reservoir;
import com.codahale.metrics.Snapshot;

public class DisabledHistogram extends Histogram implements DisabledMetric {
   public DisabledHistogram() {
      super((Reservoir)null);
   }

   public void update(int value) {
   }

   public void update(long value) {
   }

   public long getCount() {
      return 0L;
   }

   public Snapshot getSnapshot() {
      return null;
   }
}
