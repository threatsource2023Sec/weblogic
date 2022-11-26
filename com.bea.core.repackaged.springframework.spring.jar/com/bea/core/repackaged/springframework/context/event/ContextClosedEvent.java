package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationContext;

public class ContextClosedEvent extends ApplicationContextEvent {
   public ContextClosedEvent(ApplicationContext source) {
      super(source);
   }
}
