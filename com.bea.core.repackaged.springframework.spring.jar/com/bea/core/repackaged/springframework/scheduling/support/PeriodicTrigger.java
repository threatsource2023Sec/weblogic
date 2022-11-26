package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.Trigger;
import com.bea.core.repackaged.springframework.scheduling.TriggerContext;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PeriodicTrigger implements Trigger {
   private final long period;
   private final TimeUnit timeUnit;
   private volatile long initialDelay;
   private volatile boolean fixedRate;

   public PeriodicTrigger(long period) {
      this(period, (TimeUnit)null);
   }

   public PeriodicTrigger(long period, @Nullable TimeUnit timeUnit) {
      this.initialDelay = 0L;
      this.fixedRate = false;
      Assert.isTrue(period >= 0L, "period must not be negative");
      this.timeUnit = timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS;
      this.period = this.timeUnit.toMillis(period);
   }

   public long getPeriod() {
      return this.period;
   }

   public TimeUnit getTimeUnit() {
      return this.timeUnit;
   }

   public void setInitialDelay(long initialDelay) {
      this.initialDelay = this.timeUnit.toMillis(initialDelay);
   }

   public long getInitialDelay() {
      return this.initialDelay;
   }

   public void setFixedRate(boolean fixedRate) {
      this.fixedRate = fixedRate;
   }

   public boolean isFixedRate() {
      return this.fixedRate;
   }

   public Date nextExecutionTime(TriggerContext triggerContext) {
      Date lastExecution = triggerContext.lastScheduledExecutionTime();
      Date lastCompletion = triggerContext.lastCompletionTime();
      if (lastExecution != null && lastCompletion != null) {
         return this.fixedRate ? new Date(lastExecution.getTime() + this.period) : new Date(lastCompletion.getTime() + this.period);
      } else {
         return new Date(System.currentTimeMillis() + this.initialDelay);
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof PeriodicTrigger)) {
         return false;
      } else {
         PeriodicTrigger otherTrigger = (PeriodicTrigger)other;
         return this.fixedRate == otherTrigger.fixedRate && this.initialDelay == otherTrigger.initialDelay && this.period == otherTrigger.period;
      }
   }

   public int hashCode() {
      return (this.fixedRate ? 17 : 29) + (int)(37L * this.period) + (int)(41L * this.initialDelay);
   }
}
