package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.ProcessProducerField;
import org.jboss.weld.bean.ProducerField;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class ProcessProducerFieldImpl extends AbstractProcessProducerBean implements ProcessProducerField {
   protected static void fire(BeanManagerImpl beanManager, ProducerField bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessProducerFieldImpl(beanManager, bean) {
         }).fire();
      }

   }

   private ProcessProducerFieldImpl(BeanManagerImpl beanManager, ProducerField bean) {
      super(beanManager, ProcessProducerField.class, new Type[]{bean.getAnnotated().getBaseType(), bean.getAnnotated().getDeclaringType().getBaseType()}, bean);
   }

   public AnnotatedField getAnnotatedProducerField() {
      this.checkWithinObserverNotification();
      return ((ProducerField)this.getBean()).getAnnotated() != null ? (AnnotatedField)Reflections.cast(((ProducerField)this.getBean()).getAnnotated()) : null;
   }

   // $FF: synthetic method
   ProcessProducerFieldImpl(BeanManagerImpl x0, ProducerField x1, Object x2) {
      this(x0, x1);
   }
}
