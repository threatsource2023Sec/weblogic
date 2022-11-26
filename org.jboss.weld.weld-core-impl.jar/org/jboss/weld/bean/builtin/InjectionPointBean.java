package org.jboss.weld.bean.builtin;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.bean.SerializableForwardingInjectionPoint;

public class InjectionPointBean extends AbstractStaticallyDecorableBuiltInBean {
   public InjectionPointBean(BeanManagerImpl manager) {
      super(manager, InjectionPoint.class);
   }

   protected InjectionPoint newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      if (!(ip instanceof SerializableForwardingInjectionPoint) && ip != null) {
         InjectionPoint ip = new SerializableForwardingInjectionPoint(this.getBeanManager().getContextId(), ip);
         return ip;
      } else {
         return ip;
      }
   }

   public String toString() {
      return "Implicit Bean [javax.enterprise.inject.spi.InjectionPoint] with qualifiers [@Default]";
   }
}
