package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessSimpleInjectionTarget extends AbstractProcessInjectionTarget implements ProcessInjectionTarget {
   private InjectionTarget injectionTarget;

   public ProcessSimpleInjectionTarget(BeanManagerImpl beanManager, AnnotatedType annotatedType, InjectionTarget injectionTarget) {
      super(beanManager, annotatedType);
      this.injectionTarget = injectionTarget;
   }

   public InjectionTarget getInjectionTarget() {
      this.checkWithinObserverNotification();
      return this.injectionTarget;
   }

   InjectionTarget getInjectionTargetInternal() {
      return this.injectionTarget;
   }

   public void setInjectionTarget(InjectionTarget injectionTarget) {
      this.checkWithinObserverNotification();
      BootstrapLogger.LOG.setInjectionTargetCalled(this.getReceiver(), this.getInjectionTarget(), injectionTarget);
      this.injectionTarget = injectionTarget;
   }
}
