package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationContext;

public class ContextStartedEvent extends ApplicationContextEvent {
   public ContextStartedEvent(ApplicationContext source) {
      super(source);
   }
}
