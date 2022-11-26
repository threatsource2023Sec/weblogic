package com.bea.core.repackaged.springframework.scheduling.config;

public class FixedDelayTask extends IntervalTask {
   public FixedDelayTask(Runnable runnable, long interval, long initialDelay) {
      super(runnable, interval, initialDelay);
   }
}
