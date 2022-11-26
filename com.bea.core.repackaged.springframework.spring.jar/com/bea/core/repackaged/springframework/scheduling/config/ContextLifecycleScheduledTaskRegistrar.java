package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;

public class ContextLifecycleScheduledTaskRegistrar extends ScheduledTaskRegistrar implements SmartInitializingSingleton {
   public void afterPropertiesSet() {
   }

   public void afterSingletonsInstantiated() {
      this.scheduleTasks();
   }
}
