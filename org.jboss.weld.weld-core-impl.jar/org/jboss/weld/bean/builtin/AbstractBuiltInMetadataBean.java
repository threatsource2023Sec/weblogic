package org.jboss.weld.bean.builtin;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class AbstractBuiltInMetadataBean extends AbstractBuiltInBean {
   private final CurrentInjectionPoint cip;

   public AbstractBuiltInMetadataBean(BeanIdentifier identifier, Class type, BeanManagerImpl beanManager) {
      super(identifier, beanManager, type);
      this.cip = (CurrentInjectionPoint)beanManager.getServices().get(CurrentInjectionPoint.class);
   }

   public Object create(CreationalContext creationalContext) {
      InjectionPoint ip = (InjectionPoint)this.cip.peek();
      if (ip != null && !EmptyInjectionPoint.INSTANCE.equals(ip)) {
         return this.newInstance(ip, creationalContext);
      } else {
         throw BeanLogger.LOG.dynamicLookupOfBuiltInNotAllowed(this.toString());
      }
   }

   protected abstract Object newInstance(InjectionPoint var1, CreationalContext var2);

   protected WeldCreationalContext getParentCreationalContext(CreationalContext ctx) {
      if (ctx instanceof WeldCreationalContext) {
         WeldCreationalContext parentContext = ((WeldCreationalContext)ctx).getParentCreationalContext();
         if (parentContext != null) {
            return parentContext;
         }
      }

      throw BeanLogger.LOG.unableToDetermineParentCreationalContext(ctx);
   }

   public String toString() {
      return "Implicit Bean [" + this.getType().getName() + "] with qualifiers [@Default]";
   }
}
