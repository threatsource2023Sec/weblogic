package org.jboss.weld.injection.producer;

import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.injection.ConstructorInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractInstantiator implements Instantiator {
   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      return this.getConstructorInjectionPoint().newInstance(manager, ctx);
   }

   public abstract ConstructorInjectionPoint getConstructorInjectionPoint();
}
