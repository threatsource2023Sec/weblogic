package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent {
   public ContextRefreshedEvent(ApplicationContext source) {
      super(source);
   }
}
