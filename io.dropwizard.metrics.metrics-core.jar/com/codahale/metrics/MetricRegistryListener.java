package com.codahale.metrics;

import java.util.EventListener;

public interface MetricRegistryListener extends EventListener {
   void onGaugeAdded(String var1, Gauge var2);

   void onGaugeRemoved(String var1);

   void onCounterAdded(String var1, Counter var2);

   void onCounterRemoved(String var1);

   void onHistogramAdded(String var1, Histogram var2);

   void onHistogramRemoved(String var1);

   void onMeterAdded(String var1, Meter var2);

   void onMeterRemoved(String var1);

   void onTimerAdded(String var1, Timer var2);

   void onTimerRemoved(String var1);

   public abstract static class Base implements MetricRegistryListener {
      public void onGaugeAdded(String name, Gauge gauge) {
      }

      public void onGaugeRemoved(String name) {
      }

      public void onCounterAdded(String name, Counter counter) {
      }

      public void onCounterRemoved(String name) {
      }

      public void onHistogramAdded(String name, Histogram histogram) {
      }

      public void onHistogramRemoved(String name) {
      }

      public void onMeterAdded(String name, Meter meter) {
      }

      public void onMeterRemoved(String name) {
      }

      public void onTimerAdded(String name, Timer timer) {
      }

      public void onTimerRemoved(String name) {
      }
   }
}
