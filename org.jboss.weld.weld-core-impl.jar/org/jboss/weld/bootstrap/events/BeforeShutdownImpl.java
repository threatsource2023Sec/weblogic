package org.jboss.weld.bootstrap.events;

import java.util.Iterator;
import javax.enterprise.inject.spi.BeforeShutdown;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class BeforeShutdownImpl extends AbstractContainerEvent implements BeforeShutdown {
   public static void fire(BeanManagerImpl beanManager) {
      (new BeforeShutdownImpl(beanManager)).fire();
   }

   public BeforeShutdownImpl(BeanManagerImpl beanManager) {
      super(beanManager, BeforeShutdown.class, Reflections.EMPTY_TYPES);
   }

   public void fire() {
      super.fire();
      if (!this.getErrors().isEmpty()) {
         BootstrapLogger.LOG.exceptionThrownDuringBeforeShutdownObserver();
         Iterator var1 = this.getErrors().iterator();

         while(var1.hasNext()) {
            Throwable t = (Throwable)var1.next();
            BootstrapLogger.LOG.error("", t);
         }
      }

   }
}
