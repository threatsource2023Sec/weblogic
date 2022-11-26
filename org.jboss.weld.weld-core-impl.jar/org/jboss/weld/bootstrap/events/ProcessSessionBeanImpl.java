package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessSessionBean;
import javax.enterprise.inject.spi.SessionBeanType;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessSessionBeanImpl extends AbstractProcessClassBean implements ProcessSessionBean {
   protected static void fire(BeanManagerImpl beanManager, SessionBean bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessSessionBeanImpl(beanManager, bean) {
         }).fire();
      }

   }

   private ProcessSessionBeanImpl(BeanManagerImpl beanManager, SessionBean bean) {
      super(beanManager, ProcessSessionBean.class, new Type[]{bean.getAnnotated().getBaseType()}, bean);
   }

   public String getEjbName() {
      this.checkWithinObserverNotification();
      return ((SessionBean)this.getBean()).getEjbDescriptor().getEjbName();
   }

   public SessionBeanType getSessionBeanType() {
      this.checkWithinObserverNotification();
      if (((SessionBean)this.getBean()).getEjbDescriptor().isStateless()) {
         return SessionBeanType.STATELESS;
      } else if (((SessionBean)this.getBean()).getEjbDescriptor().isStateful()) {
         return SessionBeanType.STATEFUL;
      } else if (((SessionBean)this.getBean()).getEjbDescriptor().isSingleton()) {
         return SessionBeanType.SINGLETON;
      } else {
         throw BootstrapLogger.LOG.beanTypeNotEjb(this.getBean());
      }
   }

   public AnnotatedType getAnnotatedBeanClass() {
      this.checkWithinObserverNotification();
      return ((SessionBean)this.getBean()).getAnnotated();
   }

   // $FF: synthetic method
   ProcessSessionBeanImpl(BeanManagerImpl x0, SessionBean x1, Object x2) {
      this(x0, x1);
   }
}
