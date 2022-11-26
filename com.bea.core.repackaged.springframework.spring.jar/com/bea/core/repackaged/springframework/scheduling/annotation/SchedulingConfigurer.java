package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.scheduling.config.ScheduledTaskRegistrar;

@FunctionalInterface
public interface SchedulingConfigurer {
   void configureTasks(ScheduledTaskRegistrar var1);
}
