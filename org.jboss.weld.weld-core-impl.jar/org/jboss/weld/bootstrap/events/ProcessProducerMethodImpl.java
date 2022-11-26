package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.ProcessProducerMethod;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class ProcessProducerMethodImpl extends AbstractProcessProducerBean implements ProcessProducerMethod {
   protected static void fire(BeanManagerImpl beanManager, ProducerMethod bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessProducerMethodImpl(beanManager, bean) {
         }).fire();
      }

   }

   private ProcessProducerMethodImpl(BeanManagerImpl beanManager, ProducerMethod bean) {
      super(beanManager, ProcessProducerMethod.class, new Type[]{bean.getAnnotated().getBaseType(), bean.getAnnotated().getDeclaringType().getBaseType()}, bean);
   }

   public AnnotatedMethod getAnnotatedProducerMethod() {
      this.checkWithinObserverNotification();
      return ((ProducerMethod)this.getBean()).getAnnotated() != null ? (AnnotatedMethod)Reflections.cast(((ProducerMethod)this.getBean()).getAnnotated()) : null;
   }

   // $FF: synthetic method
   ProcessProducerMethodImpl(BeanManagerImpl x0, ProducerMethod x1, Object x2) {
      this(x0, x1);
   }
}
