package com.bea.core.repackaged.springframework.scheduling.config;

public class FixedRateTask extends IntervalTask {
   public FixedRateTask(Runnable runnable, long interval, long initialDelay) {
      super(runnable, interval, initialDelay);
   }
}
