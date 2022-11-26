package com.codahale.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class InstrumentedExecutorService implements ExecutorService {
   private static final AtomicLong NAME_COUNTER = new AtomicLong();
   private final ExecutorService delegate;
   private final Meter submitted;
   private final Counter running;
   private final Meter completed;
   private final Timer idle;
   private final Timer duration;

   public InstrumentedExecutorService(ExecutorService delegate, MetricRegistry registry) {
      this(delegate, registry, "instrumented-delegate-" + NAME_COUNTER.incrementAndGet());
   }

   public InstrumentedExecutorService(ExecutorService delegate, MetricRegistry registry, String name) {
      this.delegate = delegate;
      this.submitted = registry.meter(MetricRegistry.name(name, "submitted"));
      this.running = registry.counter(MetricRegistry.name(name, "running"));
      this.completed = registry.meter(MetricRegistry.name(name, "completed"));
      this.idle = registry.timer(MetricRegistry.name(name, "idle"));
      this.duration = registry.timer(MetricRegistry.name(name, "duration"));
   }

   public void execute(Runnable runnable) {
      this.submitted.mark();
      this.delegate.execute(new InstrumentedRunnable(runnable));
   }

   public Future submit(Runnable runnable) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedRunnable(runnable));
   }

   public Future submit(Runnable runnable, Object result) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedRunnable(runnable), result);
   }

   public Future submit(Callable task) {
      this.submitted.mark();
      return this.delegate.submit(new InstrumentedCallable(task));
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

   public Object invokeAny(Collection tasks) throws ExecutionException, InterruptedException {
      this.submitted.mark((long)tasks.size());
      Collection instrumented = this.instrument(tasks);
      return this.delegate.invokeAny(instrumented);
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
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

   public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
      return this.delegate.awaitTermination(l, timeUnit);
   }

   private class InstrumentedCallable implements Callable {
      private final Callable callable;

      InstrumentedCallable(Callable callable) {
         this.callable = callable;
      }

      public Object call() throws Exception {
         InstrumentedExecutorService.this.running.inc();
         Timer.Context context = InstrumentedExecutorService.this.duration.time();

         Object var2;
         try {
            var2 = this.callable.call();
         } finally {
            context.stop();
            InstrumentedExecutorService.this.running.dec();
            InstrumentedExecutorService.this.completed.mark();
         }

         return var2;
      }
   }

   private class InstrumentedRunnable implements Runnable {
      private final Runnable task;
      private final Timer.Context idleContext;

      InstrumentedRunnable(Runnable task) {
         this.task = task;
         this.idleContext = InstrumentedExecutorService.this.idle.time();
      }

      public void run() {
         this.idleContext.stop();
         InstrumentedExecutorService.this.running.inc();
         Timer.Context durationContext = InstrumentedExecutorService.this.duration.time();

         try {
            this.task.run();
         } finally {
            durationContext.stop();
            InstrumentedExecutorService.this.running.dec();
            InstrumentedExecutorService.this.completed.mark();
         }

      }
   }
}
