package com.codahale.metrics;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class Slf4jReporter extends ScheduledReporter {
   private final LoggerProxy loggerProxy;
   private final Marker marker;
   private final String prefix;

   public static Builder forRegistry(MetricRegistry registry) {
      return new Builder(registry);
   }

   private Slf4jReporter(MetricRegistry registry, LoggerProxy loggerProxy, Marker marker, String prefix, TimeUnit rateUnit, TimeUnit durationUnit, MetricFilter filter, ScheduledExecutorService executor, boolean shutdownExecutorOnStop) {
      super(registry, "logger-reporter", filter, rateUnit, durationUnit, executor, shutdownExecutorOnStop);
      this.loggerProxy = loggerProxy;
      this.marker = marker;
      this.prefix = prefix;
   }

   public void report(SortedMap gauges, SortedMap counters, SortedMap histograms, SortedMap meters, SortedMap timers) {
      if (this.loggerProxy.isEnabled(this.marker)) {
         Iterator var6 = gauges.entrySet().iterator();

         Map.Entry entry;
         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            this.logGauge((String)entry.getKey(), (Gauge)entry.getValue());
         }

         var6 = counters.entrySet().iterator();

         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            this.logCounter((String)entry.getKey(), (Counter)entry.getValue());
         }

         var6 = histograms.entrySet().iterator();

         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            this.logHistogram((String)entry.getKey(), (Histogram)entry.getValue());
         }

         var6 = meters.entrySet().iterator();

         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            this.logMeter((String)entry.getKey(), (Meter)entry.getValue());
         }

         var6 = timers.entrySet().iterator();

         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            this.logTimer((String)entry.getKey(), (Timer)entry.getValue());
         }
      }

   }

   private void logTimer(String name, Timer timer) {
      Snapshot snapshot = timer.getSnapshot();
      this.loggerProxy.log(this.marker, "type={}, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}, duration_unit={}", "TIMER", this.prefix(name), timer.getCount(), this.convertDuration((double)snapshot.getMin()), this.convertDuration((double)snapshot.getMax()), this.convertDuration(snapshot.getMean()), this.convertDuration(snapshot.getStdDev()), this.convertDuration(snapshot.getMedian()), this.convertDuration(snapshot.get75thPercentile()), this.convertDuration(snapshot.get95thPercentile()), this.convertDuration(snapshot.get98thPercentile()), this.convertDuration(snapshot.get99thPercentile()), this.convertDuration(snapshot.get999thPercentile()), this.convertRate(timer.getMeanRate()), this.convertRate(timer.getOneMinuteRate()), this.convertRate(timer.getFiveMinuteRate()), this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit(), this.getDurationUnit());
   }

   private void logMeter(String name, Meter meter) {
      this.loggerProxy.log(this.marker, "type={}, name={}, count={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}", "METER", this.prefix(name), meter.getCount(), this.convertRate(meter.getMeanRate()), this.convertRate(meter.getOneMinuteRate()), this.convertRate(meter.getFiveMinuteRate()), this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
   }

   private void logHistogram(String name, Histogram histogram) {
      Snapshot snapshot = histogram.getSnapshot();
      this.loggerProxy.log(this.marker, "type={}, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}", "HISTOGRAM", this.prefix(name), histogram.getCount(), snapshot.getMin(), snapshot.getMax(), snapshot.getMean(), snapshot.getStdDev(), snapshot.getMedian(), snapshot.get75thPercentile(), snapshot.get95thPercentile(), snapshot.get98thPercentile(), snapshot.get99thPercentile(), snapshot.get999thPercentile());
   }

   private void logCounter(String name, Counter counter) {
      this.loggerProxy.log(this.marker, "type={}, name={}, count={}", "COUNTER", this.prefix(name), counter.getCount());
   }

   private void logGauge(String name, Gauge gauge) {
      this.loggerProxy.log(this.marker, "type={}, name={}, value={}", "GAUGE", this.prefix(name), gauge.getValue());
   }

   protected String getRateUnit() {
      return "events/" + super.getRateUnit();
   }

   private String prefix(String... components) {
      return MetricRegistry.name(this.prefix, components);
   }

   // $FF: synthetic method
   Slf4jReporter(MetricRegistry x0, LoggerProxy x1, Marker x2, String x3, TimeUnit x4, TimeUnit x5, MetricFilter x6, ScheduledExecutorService x7, boolean x8, Object x9) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8);
   }

   private static class ErrorLoggerProxy extends LoggerProxy {
      public ErrorLoggerProxy(Logger logger) {
         super(logger);
      }

      public void log(Marker marker, String format, Object... arguments) {
         this.logger.error(marker, format, arguments);
      }

      public boolean isEnabled(Marker marker) {
         return this.logger.isErrorEnabled(marker);
      }
   }

   private static class WarnLoggerProxy extends LoggerProxy {
      public WarnLoggerProxy(Logger logger) {
         super(logger);
      }

      public void log(Marker marker, String format, Object... arguments) {
         this.logger.warn(marker, format, arguments);
      }

      public boolean isEnabled(Marker marker) {
         return this.logger.isWarnEnabled(marker);
      }
   }

   private static class InfoLoggerProxy extends LoggerProxy {
      public InfoLoggerProxy(Logger logger) {
         super(logger);
      }

      public void log(Marker marker, String format, Object... arguments) {
         this.logger.info(marker, format, arguments);
      }

      public boolean isEnabled(Marker marker) {
         return this.logger.isInfoEnabled(marker);
      }
   }

   private static class TraceLoggerProxy extends LoggerProxy {
      public TraceLoggerProxy(Logger logger) {
         super(logger);
      }

      public void log(Marker marker, String format, Object... arguments) {
         this.logger.trace(marker, format, arguments);
      }

      public boolean isEnabled(Marker marker) {
         return this.logger.isTraceEnabled(marker);
      }
   }

   private static class DebugLoggerProxy extends LoggerProxy {
      public DebugLoggerProxy(Logger logger) {
         super(logger);
      }

      public void log(Marker marker, String format, Object... arguments) {
         this.logger.debug(marker, format, arguments);
      }

      public boolean isEnabled(Marker marker) {
         return this.logger.isDebugEnabled(marker);
      }
   }

   abstract static class LoggerProxy {
      protected final Logger logger;

      public LoggerProxy(Logger logger) {
         this.logger = logger;
      }

      abstract void log(Marker var1, String var2, Object... var3);

      abstract boolean isEnabled(Marker var1);
   }

   public static class Builder {
      private final MetricRegistry registry;
      private Logger logger;
      private LoggingLevel loggingLevel;
      private Marker marker;
      private String prefix;
      private TimeUnit rateUnit;
      private TimeUnit durationUnit;
      private MetricFilter filter;
      private ScheduledExecutorService executor;
      private boolean shutdownExecutorOnStop;

      private Builder(MetricRegistry registry) {
         this.registry = registry;
         this.logger = LoggerFactory.getLogger("metrics");
         this.marker = null;
         this.prefix = "";
         this.rateUnit = TimeUnit.SECONDS;
         this.durationUnit = TimeUnit.MILLISECONDS;
         this.filter = MetricFilter.ALL;
         this.loggingLevel = Slf4jReporter.LoggingLevel.INFO;
         this.executor = null;
         this.shutdownExecutorOnStop = true;
      }

      public Builder shutdownExecutorOnStop(boolean shutdownExecutorOnStop) {
         this.shutdownExecutorOnStop = shutdownExecutorOnStop;
         return this;
      }

      public Builder scheduleOn(ScheduledExecutorService executor) {
         this.executor = executor;
         return this;
      }

      public Builder outputTo(Logger logger) {
         this.logger = logger;
         return this;
      }

      public Builder markWith(Marker marker) {
         this.marker = marker;
         return this;
      }

      public Builder prefixedWith(String prefix) {
         this.prefix = prefix;
         return this;
      }

      public Builder convertRatesTo(TimeUnit rateUnit) {
         this.rateUnit = rateUnit;
         return this;
      }

      public Builder convertDurationsTo(TimeUnit durationUnit) {
         this.durationUnit = durationUnit;
         return this;
      }

      public Builder filter(MetricFilter filter) {
         this.filter = filter;
         return this;
      }

      public Builder withLoggingLevel(LoggingLevel loggingLevel) {
         this.loggingLevel = loggingLevel;
         return this;
      }

      public Slf4jReporter build() {
         Object loggerProxy;
         switch (this.loggingLevel) {
            case TRACE:
               loggerProxy = new TraceLoggerProxy(this.logger);
               break;
            case INFO:
               loggerProxy = new InfoLoggerProxy(this.logger);
               break;
            case WARN:
               loggerProxy = new WarnLoggerProxy(this.logger);
               break;
            case ERROR:
               loggerProxy = new ErrorLoggerProxy(this.logger);
               break;
            case DEBUG:
            default:
               loggerProxy = new DebugLoggerProxy(this.logger);
         }

         return new Slf4jReporter(this.registry, (LoggerProxy)loggerProxy, this.marker, this.prefix, this.rateUnit, this.durationUnit, this.filter, this.executor, this.shutdownExecutorOnStop);
      }

      // $FF: synthetic method
      Builder(MetricRegistry x0, Object x1) {
         this(x0);
      }
   }

   public static enum LoggingLevel {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR;
   }
}
