package org.jboss.weld.bean.builtin.ee;

import java.security.Principal;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.spi.SecurityServices;

public class PrincipalBean extends AbstractEEBean {
   public PrincipalBean(BeanManagerImpl beanManager) {
      super(Principal.class, new PrincipalCallable(beanManager), beanManager);
   }

   public String toString() {
      return "Built-in Bean [java.security.Principal] with qualifiers [@Default]";
   }

   private static class PrincipalCallable extends AbstractEECallable {
      private static final long serialVersionUID = -6603676793378907096L;

      public PrincipalCallable(BeanManagerImpl beanManager) {
         super(beanManager);
      }

      public Principal call() throws Exception {
         SecurityServices securityServices = (SecurityServices)this.getBeanManager().getServices().get(SecurityServices.class);
         if (securityServices != null) {
            return securityServices.getPrincipal();
         } else {
            throw BeanLogger.LOG.securityServicesNotAvailable();
         }
      }
   }
}
