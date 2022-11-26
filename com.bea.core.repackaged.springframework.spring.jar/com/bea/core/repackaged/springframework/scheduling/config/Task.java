package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.util.Assert;

public class Task {
   private final Runnable runnable;

   public Task(Runnable runnable) {
      Assert.notNull(runnable, (String)"Runnable must not be null");
      this.runnable = runnable;
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   public String toString() {
      return this.runnable.toString();
   }
}
