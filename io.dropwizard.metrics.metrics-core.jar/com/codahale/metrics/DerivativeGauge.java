package com.codahale.metrics;

public abstract class DerivativeGauge implements Gauge {
   private final Gauge base;

   protected DerivativeGauge(Gauge base) {
      this.base = base;
   }

   public Object getValue() {
      return this.transform(this.base.getValue());
   }

   protected abstract Object transform(Object var1);
}
