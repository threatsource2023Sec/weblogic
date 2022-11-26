package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.TriggerContext;
import java.util.Date;

public class SimpleTriggerContext implements TriggerContext {
   @Nullable
   private volatile Date lastScheduledExecutionTime;
   @Nullable
   private volatile Date lastActualExecutionTime;
   @Nullable
   private volatile Date lastCompletionTime;

   public SimpleTriggerContext() {
   }

   public SimpleTriggerContext(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
      this.lastScheduledExecutionTime = lastScheduledExecutionTime;
      this.lastActualExecutionTime = lastActualExecutionTime;
      this.lastCompletionTime = lastCompletionTime;
   }

   public void update(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
      this.lastScheduledExecutionTime = lastScheduledExecutionTime;
      this.lastActualExecutionTime = lastActualExecutionTime;
      this.lastCompletionTime = lastCompletionTime;
   }

   @Nullable
   public Date lastScheduledExecutionTime() {
      return this.lastScheduledExecutionTime;
   }

   @Nullable
   public Date lastActualExecutionTime() {
      return this.lastActualExecutionTime;
   }

   @Nullable
   public Date lastCompletionTime() {
      return this.lastCompletionTime;
   }
}
