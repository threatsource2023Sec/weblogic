package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessManagedBean;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessManagedBeanImpl extends AbstractProcessClassBean implements ProcessManagedBean {
   protected static void fire(BeanManagerImpl beanManager, ManagedBean bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessManagedBeanImpl(beanManager, bean) {
         }).fire();
      }

   }

   public ProcessManagedBeanImpl(BeanManagerImpl beanManager, ManagedBean bean) {
      super(beanManager, ProcessManagedBean.class, new Type[]{bean.getAnnotated().getBaseType()}, bean);
   }

   public AnnotatedType getAnnotatedBeanClass() {
      this.checkWithinObserverNotification();
      return ((ManagedBean)this.getBean()).getAnnotated();
   }
}
