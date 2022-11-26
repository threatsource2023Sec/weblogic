package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.Trigger;
import com.bea.core.repackaged.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import com.bea.core.repackaged.springframework.scheduling.support.SimpleTriggerContext;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ErrorHandler;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class ReschedulingRunnable extends DelegatingErrorHandlingRunnable implements ScheduledFuture {
   private final Trigger trigger;
   private final SimpleTriggerContext triggerContext = new SimpleTriggerContext();
   private final ScheduledExecutorService executor;
   @Nullable
   private ScheduledFuture currentFuture;
   @Nullable
   private Date scheduledExecutionTime;
   private final Object triggerContextMonitor = new Object();

   public ReschedulingRunnable(Runnable delegate, Trigger trigger, ScheduledExecutorService executor, ErrorHandler errorHandler) {
      super(delegate, errorHandler);
      this.trigger = trigger;
      this.executor = executor;
   }

   @Nullable
   public ScheduledFuture schedule() {
      synchronized(this.triggerContextMonitor) {
         this.scheduledExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
         if (this.scheduledExecutionTime == null) {
            return null;
         } else {
            long initialDelay = this.scheduledExecutionTime.getTime() - System.currentTimeMillis();
            this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
            return this;
         }
      }
   }

   private ScheduledFuture obtainCurrentFuture() {
      Assert.state(this.currentFuture != null, "No scheduled future");
      return this.currentFuture;
   }

   public void run() {
      Date actualExecutionTime = new Date();
      super.run();
      Date completionTime = new Date();
      synchronized(this.triggerContextMonitor) {
         Assert.state(this.scheduledExecutionTime != null, "No scheduled execution");
         this.triggerContext.update(this.scheduledExecutionTime, actualExecutionTime, completionTime);
         if (!this.obtainCurrentFuture().isCancelled()) {
            this.schedule();
         }

      }
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      synchronized(this.triggerContextMonitor) {
         return this.obtainCurrentFuture().cancel(mayInterruptIfRunning);
      }
   }

   public boolean isCancelled() {
      synchronized(this.triggerContextMonitor) {
         return this.obtainCurrentFuture().isCancelled();
      }
   }

   public boolean isDone() {
      synchronized(this.triggerContextMonitor) {
         return this.obtainCurrentFuture().isDone();
      }
   }

   public Object get() throws InterruptedException, ExecutionException {
      ScheduledFuture curr;
      synchronized(this.triggerContextMonitor) {
         curr = this.obtainCurrentFuture();
      }

      return curr.get();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      ScheduledFuture curr;
      synchronized(this.triggerContextMonitor) {
         curr = this.obtainCurrentFuture();
      }

      return curr.get(timeout, unit);
   }

   public long getDelay(TimeUnit unit) {
      ScheduledFuture curr;
      synchronized(this.triggerContextMonitor) {
         curr = this.obtainCurrentFuture();
      }

      return curr.getDelay(unit);
   }

   public int compareTo(Delayed other) {
      if (this == other) {
         return 0;
      } else {
         long diff = this.getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
         return diff == 0L ? 0 : (diff < 0L ? -1 : 1);
      }
   }
}
