package com.codahale.metrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvReporter extends ScheduledReporter {
   private static final String DEFAULT_SEPARATOR = ",";
   private static final Logger LOGGER = LoggerFactory.getLogger(CsvReporter.class);
   private final File directory;
   private final Locale locale;
   private final String separator;
   private final Clock clock;
   private final CsvFileProvider csvFileProvider;
   private final String histogramFormat;
   private final String meterFormat;
   private final String timerFormat;

   public static Builder forRegistry(MetricRegistry registry) {
      return new Builder(registry);
   }

   private CsvReporter(MetricRegistry registry, File directory, Locale locale, String separator, TimeUnit rateUnit, TimeUnit durationUnit, Clock clock, MetricFilter filter, ScheduledExecutorService executor, boolean shutdownExecutorOnStop, CsvFileProvider csvFileProvider) {
      super(registry, "csv-reporter", filter, rateUnit, durationUnit, executor, shutdownExecutorOnStop);
      this.directory = directory;
      this.locale = locale;
      this.separator = separator;
      this.clock = clock;
      this.csvFileProvider = csvFileProvider;
      this.histogramFormat = String.join(separator, "%d", "%d", "%f", "%d", "%f", "%f", "%f", "%f", "%f", "%f", "%f");
      this.meterFormat = String.join(separator, "%d", "%f", "%f", "%f", "%f", "events/%s");
      this.timerFormat = String.join(separator, "%d", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "%f", "calls/%s", "%s");
   }

   public void report(SortedMap gauges, SortedMap counters, SortedMap histograms, SortedMap meters, SortedMap timers) {
      long timestamp = TimeUnit.MILLISECONDS.toSeconds(this.clock.getTime());
      Iterator var8 = gauges.entrySet().iterator();

      Map.Entry entry;
      while(var8.hasNext()) {
         entry = (Map.Entry)var8.next();
         this.reportGauge(timestamp, (String)entry.getKey(), (Gauge)entry.getValue());
      }

      var8 = counters.entrySet().iterator();

      while(var8.hasNext()) {
         entry = (Map.Entry)var8.next();
         this.reportCounter(timestamp, (String)entry.getKey(), (Counter)entry.getValue());
      }

      var8 = histograms.entrySet().iterator();

      while(var8.hasNext()) {
         entry = (Map.Entry)var8.next();
         this.reportHistogram(timestamp, (String)entry.getKey(), (Histogram)entry.getValue());
      }

      var8 = meters.entrySet().iterator();

      while(var8.hasNext()) {
         entry = (Map.Entry)var8.next();
         this.reportMeter(timestamp, (String)entry.getKey(), (Meter)entry.getValue());
      }

      var8 = timers.entrySet().iterator();

      while(var8.hasNext()) {
         entry = (Map.Entry)var8.next();
         this.reportTimer(timestamp, (String)entry.getKey(), (Timer)entry.getValue());
      }

   }

   private void reportTimer(long timestamp, String name, Timer timer) {
      Snapshot snapshot = timer.getSnapshot();
      this.report(timestamp, name, "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit,duration_unit", this.timerFormat, timer.getCount(), this.convertDuration((double)snapshot.getMax()), this.convertDuration(snapshot.getMean()), this.convertDuration((double)snapshot.getMin()), this.convertDuration(snapshot.getStdDev()), this.convertDuration(snapshot.getMedian()), this.convertDuration(snapshot.get75thPercentile()), this.convertDuration(snapshot.get95thPercentile()), this.convertDuration(snapshot.get98thPercentile()), this.convertDuration(snapshot.get99thPercentile()), this.convertDuration(snapshot.get999thPercentile()), this.convertRate(timer.getMeanRate()), this.convertRate(timer.getOneMinuteRate()), this.convertRate(timer.getFiveMinuteRate()), this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit(), this.getDurationUnit());
   }

   private void reportMeter(long timestamp, String name, Meter meter) {
      this.report(timestamp, name, "count,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit", this.meterFormat, meter.getCount(), this.convertRate(meter.getMeanRate()), this.convertRate(meter.getOneMinuteRate()), this.convertRate(meter.getFiveMinuteRate()), this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
   }

   private void reportHistogram(long timestamp, String name, Histogram histogram) {
      Snapshot snapshot = histogram.getSnapshot();
      this.report(timestamp, name, "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999", this.histogramFormat, histogram.getCount(), snapshot.getMax(), snapshot.getMean(), snapshot.getMin(), snapshot.getStdDev(), snapshot.getMedian(), snapshot.get75thPercentile(), snapshot.get95thPercentile(), snapshot.get98thPercentile(), snapshot.get99thPercentile(), snapshot.get999thPercentile());
   }

   private void reportCounter(long timestamp, String name, Counter counter) {
      this.report(timestamp, name, "count", "%d", counter.getCount());
   }

   private void reportGauge(long timestamp, String name, Gauge gauge) {
      this.report(timestamp, name, "value", "%s", gauge.getValue());
   }

   private void report(long timestamp, String name, String header, String line, Object... values) {
      try {
         File file = this.csvFileProvider.getFile(this.directory, name);
         boolean fileAlreadyExists = file.exists();
         if (fileAlreadyExists || file.createNewFile()) {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            Throwable var10 = null;

            try {
               if (!fileAlreadyExists) {
                  out.println("t," + header);
               }

               out.printf(this.locale, String.format(this.locale, "%d" + this.separator + "%s%n", timestamp, line), values);
            } catch (Throwable var20) {
               var10 = var20;
               throw var20;
            } finally {
               if (var10 != null) {
                  try {
                     out.close();
                  } catch (Throwable var19) {
                     var10.addSuppressed(var19);
                  }
               } else {
                  out.close();
               }

            }
         }
      } catch (IOException var22) {
         LOGGER.warn("Error writing to {}", name, var22);
      }

   }

   protected String sanitize(String name) {
      return name;
   }

   // $FF: synthetic method
   CsvReporter(MetricRegistry x0, File x1, Locale x2, String x3, TimeUnit x4, TimeUnit x5, Clock x6, MetricFilter x7, ScheduledExecutorService x8, boolean x9, CsvFileProvider x10, Object x11) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10);
   }

   public static class Builder {
      private final MetricRegistry registry;
      private Locale locale;
      private String separator;
      private TimeUnit rateUnit;
      private TimeUnit durationUnit;
      private Clock clock;
      private MetricFilter filter;
      private ScheduledExecutorService executor;
      private boolean shutdownExecutorOnStop;
      private CsvFileProvider csvFileProvider;

      private Builder(MetricRegistry registry) {
         this.registry = registry;
         this.locale = Locale.getDefault();
         this.separator = ",";
         this.rateUnit = TimeUnit.SECONDS;
         this.durationUnit = TimeUnit.MILLISECONDS;
         this.clock = Clock.defaultClock();
         this.filter = MetricFilter.ALL;
         this.executor = null;
         this.shutdownExecutorOnStop = true;
         this.csvFileProvider = new FixedNameCsvFileProvider();
      }

      public Builder shutdownExecutorOnStop(boolean shutdownExecutorOnStop) {
         this.shutdownExecutorOnStop = shutdownExecutorOnStop;
         return this;
      }

      public Builder scheduleOn(ScheduledExecutorService executor) {
         this.executor = executor;
         return this;
      }

      public Builder formatFor(Locale locale) {
         this.locale = locale;
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

      public Builder withSeparator(String separator) {
         this.separator = separator;
         return this;
      }

      public Builder withClock(Clock clock) {
         this.clock = clock;
         return this;
      }

      public Builder filter(MetricFilter filter) {
         this.filter = filter;
         return this;
      }

      public Builder withCsvFileProvider(CsvFileProvider csvFileProvider) {
         this.csvFileProvider = csvFileProvider;
         return this;
      }

      public CsvReporter build(File directory) {
         return new CsvReporter(this.registry, directory, this.locale, this.separator, this.rateUnit, this.durationUnit, this.clock, this.filter, this.executor, this.shutdownExecutorOnStop, this.csvFileProvider);
      }

      // $FF: synthetic method
      Builder(MetricRegistry x0, Object x1) {
         this(x0);
      }
   }
}
