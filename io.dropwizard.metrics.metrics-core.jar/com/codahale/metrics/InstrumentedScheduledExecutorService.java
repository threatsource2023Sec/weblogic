package com.codahale.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class InstrumentedScheduledExecutorService implements ScheduledExecutorService {
   private static final AtomicLong NAME_COUNTER = new AtomicLong();
   private final ScheduledExecutorService delegate;
   private final Meter submitted;
   private final Counter running;
   private final Meter completed;
   private final Timer duration;
   private final Meter scheduledOnce;
   private final Meter scheduledRepetitively;
   private final Counter scheduledOverrun;
   private final Histogram percentOfPeriod;

   public InstrumentedScheduledExecutorService(ScheduledExecutorService delegate, MetricRegistry registry) {
      this(delegate, registry, "instrumented-scheduled-executor-service-" + NAME_COUNTER.incrementAndGet());
   }

   public InstrumentedScheduledExecutorService(ScheduledExecutorService delegate, MetricRegistry registry, String name) {
      this.delegate = delegate;
      this.submitted = registry.meter(MetricRegistry.name(name, "submitted"));
      this.running = registry.counter(MetricRegistry.name(name, "running"));
      this.completed = registry.meter(MetricRegistry.name(name, "completed"));
      this.duration = registry.timer(MetricRegistry.name(name, "duration"));
      this.scheduledOnce = registry.meter(MetricRegistry.name(name, "scheduled.once"));
      this.scheduledRepetitively = registry.meter(MetricRegistry.name(name, "scheduled.repetitively"));
      this.scheduledOverrun = registry.counter(MetricRegistry.name(name, "scheduled.overrun"));
      this.percentOfPeriod = registry.histogram(MetricRegistry.name(name, "scheduled.percent-of-period"));
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      this.scheduledOnce.mark();
      return this.delegate.schedule(new InstrumentedRunnable(command), delay, unit);
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      this.scheduledOnce.mark();
      return this.delegate.schedule(new InstrumentedCallable(callable), delay, unit);
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      this.scheduledRepetitively.mark();
      return this.delegate.scheduleAtFixedRate(new InstrumentedPeriodicRunnable(command, period, unit), initialDelay, period, unit);
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      this.scheduledRepetitively.mark();
      return this.delegate.scheduleWithFixedDelay(new InstrumentedRunnable(command), initialDelay, delay, unit);
   }

   public void shutdown() {
      this.delegate.shutdown();
   }

   public List shutdownNow() {
      return this.delegate.shutdownNow();
   }

   public boolean isShutdown() {
      return this.delegate.isShutdown();
   }

   public boolean isTerminated() {
      return this.delegate.isTerminated();
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate.awaitTermination(timeout, unit);
   }

   public Future submit(Callable task) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedCallable(task));
   }

   public Future submit(Runnable task, Object result) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedRunnable(task), result);
   }

   public Future submit(Runnable task) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedRunnable(task));
   }

   public List invokeAll(Collection tasks) throws InterruptedException {
      this.submitted.mark((long)tasks.size());
      Collection instrumented = this.instrument(tasks);
      return this.delegate.invokeAll(instrumented);
   }

   public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      this.submitted.mark((long)tasks.size());
      Collection instrumented = this.instrument(tasks);
      return this.delegate.invokeAll(instrumented, timeout, unit);
   }

   public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      this.submitted.mark((long)tasks.size());
      Collection instrumented = this.instrument(tasks);
      return this.delegate.invokeAny(instrumented);
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      this.submitted.mark((long)tasks.size());
      Collection instrumented = this.instrument(tasks);
      return this.delegate.invokeAny(instrumented, timeout, unit);
   }

   private Collection instrument(Collection tasks) {
      List instrumented = new ArrayList(tasks.size());
      Iterator var3 = tasks.iterator();

      while(var3.hasNext()) {
         Callable task = (Callable)var3.next();
         instrumented.add(new InstrumentedCallable(task));
      }

      return instrumented;
   }

   public void execute(Runnable command) {
      this.submitted.mark();
      this.delegate.execute(new InstrumentedRunnable(command));
   }

   private class InstrumentedCallable implements Callable {
      private final Callable task;

      InstrumentedCallable(Callable task) {
         this.task = task;
      }

      public Object call() throws Exception {
         InstrumentedScheduledExecutorService.this.running.inc();
         Timer.Context context = InstrumentedScheduledExecutorService.this.duration.time();

         Object var2;
         try {
            var2 = this.task.call();
         } finally {
            context.stop();
            InstrumentedScheduledExecutorService.this.running.dec();
            InstrumentedScheduledExecutorService.this.completed.mark();
         }

         return var2;
      }
   }

   private class InstrumentedPeriodicRunnable implements Runnable {
      private final Runnable command;
      private final long periodInNanos;

      InstrumentedPeriodicRunnable(Runnable command, long period, TimeUnit unit) {
         this.command = command;
         this.periodInNanos = unit.toNanos(period);
      }

      public void run() {
         InstrumentedScheduledExecutorService.this.running.inc();
         Timer.Context context = InstrumentedScheduledExecutorService.this.duration.time();
         boolean var8 = false;

         try {
            var8 = true;
            this.command.run();
            var8 = false;
         } finally {
            if (var8) {
               long elapsedx = context.stop();
               InstrumentedScheduledExecutorService.this.running.dec();
               InstrumentedScheduledExecutorService.this.completed.mark();
               if (elapsedx > this.periodInNanos) {
                  InstrumentedScheduledExecutorService.this.scheduledOverrun.inc();
               }

               InstrumentedScheduledExecutorService.this.percentOfPeriod.update(100L * elapsedx / this.periodInNanos);
            }
         }

         long elapsed = context.stop();
         InstrumentedScheduledExecutorService.this.running.dec();
         InstrumentedScheduledExecutorService.this.completed.mark();
         if (elapsed > this.periodInNanos) {
            InstrumentedScheduledExecutorService.this.scheduledOverrun.inc();
         }

         InstrumentedScheduledExecutorService.this.percentOfPeriod.update(100L * elapsed / this.periodInNanos);
      }
   }

   private class InstrumentedRunnable implements Runnable {
      private final Runnable command;

      InstrumentedRunnable(Runnable command) {
         this.command = command;
      }

      public void run() {
         InstrumentedScheduledExecutorService.this.running.inc();
         Timer.Context context = InstrumentedScheduledExecutorService.this.duration.time();

         try {
            this.command.run();
         } finally {
            context.stop();
            InstrumentedScheduledExecutorService.this.running.dec();
            InstrumentedScheduledExecutorService.this.completed.mark();
         }

      }
   }
}
