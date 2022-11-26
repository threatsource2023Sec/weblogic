package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessBeanInjectionTarget extends AbstractProcessInjectionTarget implements ProcessInjectionTarget {
   private final AbstractClassBean classBean;

   public ProcessBeanInjectionTarget(BeanManagerImpl beanManager, AbstractClassBean bean) {
      super(beanManager, bean.getAnnotated());
      this.classBean = bean;
   }

   public InjectionTarget getInjectionTarget() {
      this.checkWithinObserverNotification();
      return this.classBean.getProducer();
   }

   public void setInjectionTarget(InjectionTarget injectionTarget) {
      this.checkWithinObserverNotification();
      BootstrapLogger.LOG.setInjectionTargetCalled(this.getReceiver(), this.getInjectionTarget(), injectionTarget);
      this.classBean.setProducer(injectionTarget);
   }
}
