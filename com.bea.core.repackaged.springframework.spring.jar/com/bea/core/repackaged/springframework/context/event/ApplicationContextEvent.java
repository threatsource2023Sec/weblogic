package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;

public abstract class ApplicationContextEvent extends ApplicationEvent {
   public ApplicationContextEvent(ApplicationContext source) {
      super(source);
   }

   public final ApplicationContext getApplicationContext() {
      return (ApplicationContext)this.getSource();
   }
}
