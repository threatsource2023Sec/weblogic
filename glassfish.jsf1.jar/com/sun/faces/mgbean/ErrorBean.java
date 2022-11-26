package com.sun.faces.mgbean;

import java.util.List;
import javax.faces.context.FacesContext;

public class ErrorBean extends BeanBuilder {
   public ErrorBean(ManagedBeanInfo beanInfo, String message) {
      super(beanInfo);
      if (message != null && message.length() != 0) {
         this.queueMessage(message);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ErrorBean(ManagedBeanInfo beanInfo, List messages) {
      super(beanInfo);
      if (messages != null && !messages.isEmpty()) {
         this.queueMessages(messages);
      } else {
         throw new IllegalArgumentException();
      }
   }

   synchronized void bake() {
   }

   protected void buildBean(Object bean, FacesContext context) {
   }
}
