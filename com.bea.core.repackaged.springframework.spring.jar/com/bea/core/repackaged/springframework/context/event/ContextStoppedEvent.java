package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationContext;

public class ContextStoppedEvent extends ApplicationContextEvent {
   public ContextStoppedEvent(ApplicationContext source) {
      super(source);
   }
}
