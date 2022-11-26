package com.bea.core.repackaged.springframework.context;

public interface SmartLifecycle extends Lifecycle, Phased {
   int DEFAULT_PHASE = Integer.MAX_VALUE;

   default boolean isAutoStartup() {
      return true;
   }

   default void stop(Runnable callback) {
      this.stop();
      callback.run();
   }

   default int getPhase() {
      return Integer.MAX_VALUE;
   }
}
