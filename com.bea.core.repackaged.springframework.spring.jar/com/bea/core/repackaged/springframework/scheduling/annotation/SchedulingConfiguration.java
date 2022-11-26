package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.context.annotation.Bean;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.Role;

@Configuration
@Role(2)
public class SchedulingConfiguration {
   @Bean(
      name = {"com.bea.core.repackaged.springframework.context.annotation.internalScheduledAnnotationProcessor"}
   )
   @Role(2)
   public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
      return new ScheduledAnnotationBeanPostProcessor();
   }
}
