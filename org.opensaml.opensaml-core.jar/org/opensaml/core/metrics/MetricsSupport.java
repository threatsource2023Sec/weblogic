package org.opensaml.core.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.config.ConfigurationService;

public final class MetricsSupport {
   private MetricsSupport() {
   }

   @Nullable
   public static MetricRegistry getMetricRegistry() {
      return (MetricRegistry)ConfigurationService.get(MetricRegistry.class);
   }

   @Nullable
   public static Metric register(@Nonnull String name, @Nonnull Metric metric) {
      return register(name, metric, true, (MetricRegistry)null);
   }

   @Nullable
   public static Metric register(@Nonnull String name, @Nonnull Metric metric, boolean replaceExisting) {
      return register(name, metric, replaceExisting, (MetricRegistry)null);
   }

   @Nullable
   public static Metric register(@Nonnull String name, @Nonnull Metric metric, boolean replaceExisting, @Nullable MetricRegistry registry) {
      Constraint.isNotNull(name, "Metric name was null");
      Constraint.isNotNull(metric, "Metric was null");
      MetricRegistry metricRegistry = registry;
      if (registry == null) {
         metricRegistry = getMetricRegistry();
      }

      if (metricRegistry == null) {
         return null;
      } else {
         synchronized(metricRegistry) {
            Metric var10000;
            try {
               if (replaceExisting) {
                  metricRegistry.remove(name);
               }

               var10000 = metricRegistry.register(name, metric);
            } catch (IllegalArgumentException var8) {
               if (replaceExisting) {
                  metricRegistry.remove(name);
                  return metricRegistry.register(name, metric);
               }

               throw var8;
            }

            return var10000;
         }
      }
   }

   public static boolean remove(@Nonnull String name) {
      return remove(name, (Metric)null, (MetricRegistry)null);
   }

   public static boolean remove(@Nonnull String name, @Nullable Metric metric) {
      return remove(name, metric, (MetricRegistry)null);
   }

   public static boolean remove(@Nonnull String name, @Nullable Metric metric, @Nullable MetricRegistry registry) {
      Constraint.isNotNull(name, "Metric name was null");
      MetricRegistry metricRegistry = registry;
      if (registry == null) {
         metricRegistry = getMetricRegistry();
      }

      if (metricRegistry == null) {
         return false;
      } else {
         synchronized(metricRegistry) {
            return metric != null && !isMetricInstanceRegisteredUnderName(name, metric, metricRegistry) ? false : metricRegistry.remove(name);
         }
      }
   }

   public static boolean isMetricInstanceRegisteredUnderName(@Nonnull String name, @Nonnull Metric metric, @Nonnull MetricRegistry registry) {
      Constraint.isNotNull(registry, "MetricRegistry was null");
      Constraint.isNotNull(name, "Metric name was null");
      Constraint.isNotNull(metric, "Metric was null");
      Metric registeredMetric = (Metric)registry.getMetrics().get(name);
      return metric == registeredMetric;
   }

   @Nullable
   public static Timer.Context startTimer(@Nullable Timer timer) {
      return timer != null ? timer.time() : null;
   }

   @Nullable
   public static Long stopTimer(@Nullable Timer.Context context) {
      return context != null ? context.stop() : null;
   }
}
