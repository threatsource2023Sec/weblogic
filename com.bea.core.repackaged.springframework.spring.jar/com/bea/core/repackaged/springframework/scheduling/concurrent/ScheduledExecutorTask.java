package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTask {
   @Nullable
   private Runnable runnable;
   private long delay = 0L;
   private long period = -1L;
   private TimeUnit timeUnit;
   private boolean fixedRate;

   public ScheduledExecutorTask() {
      this.timeUnit = TimeUnit.MILLISECONDS;
      this.fixedRate = false;
   }

   public ScheduledExecutorTask(Runnable executorTask) {
      this.timeUnit = TimeUnit.MILLISECONDS;
      this.fixedRate = false;
      this.runnable = executorTask;
   }

   public ScheduledExecutorTask(Runnable executorTask, long delay) {
      this.timeUnit = TimeUnit.MILLISECONDS;
      this.fixedRate = false;
      this.runnable = executorTask;
      this.delay = delay;
   }

   public ScheduledExecutorTask(Runnable executorTask, long delay, long period, boolean fixedRate) {
      this.timeUnit = TimeUnit.MILLISECONDS;
      this.fixedRate = false;
      this.runnable = executorTask;
      this.delay = delay;
      this.period = period;
      this.fixedRate = fixedRate;
   }

   public void setRunnable(Runnable executorTask) {
      this.runnable = executorTask;
   }

   public Runnable getRunnable() {
      Assert.state(this.runnable != null, "No Runnable set");
      return this.runnable;
   }

   public void setDelay(long delay) {
      this.delay = delay;
   }

   public long getDelay() {
      return this.delay;
   }

   public void setPeriod(long period) {
      this.period = period;
   }

   public long getPeriod() {
      return this.period;
   }

   public boolean isOneTimeTask() {
      return this.period <= 0L;
   }

   public void setTimeUnit(@Nullable TimeUnit timeUnit) {
      this.timeUnit = timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS;
   }

   public TimeUnit getTimeUnit() {
      return this.timeUnit;
   }

   public void setFixedRate(boolean fixedRate) {
      this.fixedRate = fixedRate;
   }

   public boolean isFixedRate() {
      return this.fixedRate;
   }
}
