package com.codahale.metrics;

public interface MetricFilter {
   MetricFilter ALL = (name, metric) -> {
      return true;
   };

   static MetricFilter startsWith(String prefix) {
      return (name, metric) -> {
         return name.startsWith(prefix);
      };
   }

   static MetricFilter endsWith(String suffix) {
      return (name, metric) -> {
         return name.endsWith(suffix);
      };
   }

   static MetricFilter contains(String substring) {
      return (name, metric) -> {
         return name.contains(substring);
      };
   }

   boolean matches(String var1, Metric var2);
}
