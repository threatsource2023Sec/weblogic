package org.opensaml.core.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Timer;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.metrics.impl.DisabledCounter;
import org.opensaml.core.metrics.impl.DisabledHistogram;
import org.opensaml.core.metrics.impl.DisabledMeter;
import org.opensaml.core.metrics.impl.DisabledTimer;

public class FilteredMetricRegistry extends MetricRegistry {
   @Nullable
   private MetricFilter metricFilter;
   @Nonnull
   private final DisabledCounter disabledCounter = new DisabledCounter();
   @Nonnull
   private final DisabledHistogram disabledHistogram = new DisabledHistogram();
   @Nonnull
   private final DisabledMeter disabledMeter = new DisabledMeter();
   @Nonnull
   private final DisabledTimer disabledTimer = new DisabledTimer();

   public void setMetricFilter(@Nullable MetricFilter filter) {
      this.metricFilter = filter;
   }

   public Counter counter(String name) {
      return (Counter)(this.metricFilter != null && this.metricFilter.matches(name, (Metric)null) ? super.counter(name) : this.disabledCounter);
   }

   public Histogram histogram(String name) {
      return (Histogram)(this.metricFilter != null && this.metricFilter.matches(name, (Metric)null) ? super.histogram(name) : this.disabledHistogram);
   }

   public Meter meter(String name) {
      return (Meter)(this.metricFilter != null && this.metricFilter.matches(name, (Metric)null) ? super.meter(name) : this.disabledMeter);
   }

   public Timer timer(String name) {
      return (Timer)(this.metricFilter != null && this.metricFilter.matches(name, (Metric)null) ? super.timer(name) : this.disabledTimer);
   }

   public void registerMultiple(@Nonnull @NonnullElements Collection metricSets) throws IllegalArgumentException {
      Constraint.isNotNull(metricSets, "Collection cannot be null");
      Iterator var2 = Collections2.filter(metricSets, Predicates.notNull()).iterator();

      while(var2.hasNext()) {
         MetricSet set = (MetricSet)var2.next();
         this.registerAll(set);
      }

   }
}
