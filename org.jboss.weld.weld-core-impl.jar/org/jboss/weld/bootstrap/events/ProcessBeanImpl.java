package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.ProcessBean;
import org.jboss.weld.annotated.EmptyAnnotated;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class ProcessBeanImpl extends AbstractDefinitionContainerEvent implements ProcessBean {
   private final Bean bean;
   private final Annotated annotated;

   protected static void fire(BeanManagerImpl beanManager, AbstractBean bean) {
      fire(beanManager, bean, bean.getAnnotated());
   }

   protected static void fire(BeanManagerImpl beanManager, Bean bean) {
      fire(beanManager, bean, EmptyAnnotated.INSTANCE);
   }

   private static void fire(BeanManagerImpl beanManager, Bean bean, Annotated annotated) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessBeanImpl(beanManager, bean, annotated) {
         }).fire();
      }

   }

   ProcessBeanImpl(BeanManagerImpl beanManager, Bean bean, Annotated annotated) {
      super(beanManager, ProcessBean.class, new Type[]{bean.getBeanClass()});
      this.bean = bean;
      this.annotated = annotated;
   }

   public Annotated getAnnotated() {
      this.checkWithinObserverNotification();
      return this.annotated;
   }

   public Bean getBean() {
      this.checkWithinObserverNotification();
      return this.bean;
   }
}
