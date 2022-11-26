package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.ScheduledFuture;

public final class ScheduledTask {
   private final Task task;
   @Nullable
   volatile ScheduledFuture future;

   ScheduledTask(Task task) {
      this.task = task;
   }

   public Task getTask() {
      return this.task;
   }

   public void cancel() {
      ScheduledFuture future = this.future;
      if (future != null) {
         future.cancel(true);
      }

   }

   public String toString() {
      return this.task.toString();
   }
}
