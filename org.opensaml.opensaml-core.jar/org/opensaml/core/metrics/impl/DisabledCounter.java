package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Counter;

public class DisabledCounter extends Counter implements DisabledMetric {
   public void inc() {
   }

   public void inc(long n) {
   }

   public void dec() {
   }

   public void dec(long n) {
   }

   public long getCount() {
      return 0L;
   }
}
