package org.jboss.weld.module.ejb;

import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.injection.producer.DefaultInjector;
import org.jboss.weld.manager.BeanManagerImpl;

class DynamicInjectionPointInjector extends DefaultInjector {
   private final CurrentInjectionPoint currentInjectionPoint;
   private boolean pushDynamicInjectionPoints;

   DynamicInjectionPointInjector(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      super(type, bean, beanManager);
      this.currentInjectionPoint = (CurrentInjectionPoint)beanManager.getServices().get(CurrentInjectionPoint.class);
   }

   public void inject(Object instance, CreationalContext ctx, BeanManagerImpl manager, SlimAnnotatedType type, InjectionTarget injectionTarget) {
      ThreadLocalStack.ThreadLocalStackReference stack = null;
      if (this.pushDynamicInjectionPoints) {
         stack = this.currentInjectionPoint.push(new DynamicInjectionPoint(manager));
      }

      try {
         super.inject(instance, ctx, manager, type, injectionTarget);
      } finally {
         if (this.pushDynamicInjectionPoints) {
            stack.pop();
         }

      }

   }

   public void registerInjectionPoints(Set injectionPoints) {
      super.registerInjectionPoints(injectionPoints);
      this.pushDynamicInjectionPoints = this.hasInjectionPointMetadata(injectionPoints);
   }

   private boolean hasInjectionPointMetadata(Set injectionPoints) {
      Iterator var2 = injectionPoints.iterator();

      InjectionPoint injectionPoint;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         injectionPoint = (InjectionPoint)var2.next();
      } while(injectionPoint.getType() != InjectionPoint.class);

      return true;
   }
}
