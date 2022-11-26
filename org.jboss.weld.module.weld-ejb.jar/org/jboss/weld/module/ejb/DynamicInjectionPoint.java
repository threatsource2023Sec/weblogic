package org.jboss.weld.module.ejb;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.Container;
import org.jboss.weld.injection.ForwardingInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;

class DynamicInjectionPoint extends ForwardingInjectionPoint implements Serializable {
   private static final long serialVersionUID = 0L;
   private final transient CurrentInvocationInjectionPoint invocationInjectionPoint;
   private final String contextId;

   DynamicInjectionPoint(BeanManagerImpl manager) {
      this.contextId = manager.getContextId();
      this.invocationInjectionPoint = (CurrentInvocationInjectionPoint)manager.getServices().get(CurrentInvocationInjectionPoint.class);
   }

   private DynamicInjectionPoint(CurrentInvocationInjectionPoint invocationInjectionPoint, String contextId) {
      this.invocationInjectionPoint = invocationInjectionPoint;
      this.contextId = contextId;
   }

   protected InjectionPoint delegate() {
      InjectionPoint injectionPoint = (InjectionPoint)this.invocationInjectionPoint.peek();
      if (injectionPoint == null) {
         throw BeanLogger.LOG.statelessSessionBeanInjectionPointMetadataNotAvailable();
      } else {
         return injectionPoint;
      }
   }

   private Object readResolve() throws ObjectStreamException {
      return new DynamicInjectionPoint((CurrentInvocationInjectionPoint)Container.instance(this.contextId).services().get(CurrentInvocationInjectionPoint.class), this.contextId);
   }
}
