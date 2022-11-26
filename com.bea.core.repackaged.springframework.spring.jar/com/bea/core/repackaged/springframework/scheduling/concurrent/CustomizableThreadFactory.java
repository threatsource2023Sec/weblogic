package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.util.CustomizableThreadCreator;
import java.util.concurrent.ThreadFactory;

public class CustomizableThreadFactory extends CustomizableThreadCreator implements ThreadFactory {
   public CustomizableThreadFactory() {
   }

   public CustomizableThreadFactory(String threadNamePrefix) {
      super(threadNamePrefix);
   }

   public Thread newThread(Runnable runnable) {
      return this.createThread(runnable);
   }
}
