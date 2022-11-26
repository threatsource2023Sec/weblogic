package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.Ordered;
import java.lang.reflect.Method;

public class DefaultEventListenerFactory implements EventListenerFactory, Ordered {
   private int order = Integer.MAX_VALUE;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public boolean supportsMethod(Method method) {
      return true;
   }

   public ApplicationListener createApplicationListener(String beanName, Class type, Method method) {
      return new ApplicationListenerMethodAdapter(beanName, type, method);
   }
}
