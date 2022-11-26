package com.codahale.metrics;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public class SharedMetricRegistries {
   private static final ConcurrentMap REGISTRIES = new ConcurrentHashMap();
   private static AtomicReference defaultRegistryName = new AtomicReference();

   static void setDefaultRegistryName(AtomicReference defaultRegistryName) {
      SharedMetricRegistries.defaultRegistryName = defaultRegistryName;
   }

   private SharedMetricRegistries() {
   }

   public static void clear() {
      REGISTRIES.clear();
   }

   public static Set names() {
      return REGISTRIES.keySet();
   }

   public static void remove(String key) {
      REGISTRIES.remove(key);
   }

   public static MetricRegistry add(String name, MetricRegistry registry) {
      return (MetricRegistry)REGISTRIES.putIfAbsent(name, registry);
   }

   public static MetricRegistry getOrCreate(String name) {
      MetricRegistry existing = (MetricRegistry)REGISTRIES.get(name);
      if (existing == null) {
         MetricRegistry created = new MetricRegistry();
         MetricRegistry raced = add(name, created);
         return raced == null ? created : raced;
      } else {
         return existing;
      }
   }

   public static synchronized MetricRegistry setDefault(String name) {
      MetricRegistry registry = getOrCreate(name);
      return setDefault(name, registry);
   }

   public static MetricRegistry setDefault(String name, MetricRegistry metricRegistry) {
      if (defaultRegistryName.compareAndSet((Object)null, name)) {
         add(name, metricRegistry);
         return metricRegistry;
      } else {
         throw new IllegalStateException("Default metric registry name is already set.");
      }
   }

   public static MetricRegistry getDefault() {
      MetricRegistry metricRegistry = tryGetDefault();
      if (metricRegistry == null) {
         throw new IllegalStateException("Default registry name has not been set.");
      } else {
         return metricRegistry;
      }
   }

   public static MetricRegistry tryGetDefault() {
      String name = (String)defaultRegistryName.get();
      return name != null ? getOrCreate(name) : null;
   }
}
