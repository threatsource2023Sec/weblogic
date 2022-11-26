package com.bea.core.repackaged.springframework.scheduling;

import com.bea.core.repackaged.springframework.core.task.AsyncTaskExecutor;

public interface SchedulingTaskExecutor extends AsyncTaskExecutor {
   default boolean prefersShortLivedTasks() {
      return true;
   }
}
