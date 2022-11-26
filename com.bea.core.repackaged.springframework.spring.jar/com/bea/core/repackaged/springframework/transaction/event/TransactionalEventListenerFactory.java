package com.bea.core.repackaged.springframework.transaction.event;

import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.context.event.EventListenerFactory;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import java.lang.reflect.Method;

public class TransactionalEventListenerFactory implements EventListenerFactory, Ordered {
   private int order = 50;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public boolean supportsMethod(Method method) {
      return AnnotatedElementUtils.hasAnnotation(method, TransactionalEventListener.class);
   }

   public ApplicationListener createApplicationListener(String beanName, Class type, Method method) {
      return new ApplicationListenerMethodTransactionalAdapter(beanName, type, method);
   }
}
