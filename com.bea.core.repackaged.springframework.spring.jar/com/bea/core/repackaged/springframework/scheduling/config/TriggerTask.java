package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.scheduling.Trigger;
import com.bea.core.repackaged.springframework.util.Assert;

public class TriggerTask extends Task {
   private final Trigger trigger;

   public TriggerTask(Runnable runnable, Trigger trigger) {
      super(runnable);
      Assert.notNull(trigger, (String)"Trigger must not be null");
      this.trigger = trigger;
   }

   public Trigger getTrigger() {
      return this.trigger;
   }
}
