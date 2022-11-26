package org.jboss.weld.bootstrap.api.helpers;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;

public class ServiceRegistries {
   private ServiceRegistries() {
   }

   public static ServiceRegistry unmodifiableServiceRegistry(final ServiceRegistry serviceRegistry) {
      return new ForwardingServiceRegistry() {
         public void add(Class type, Service service) {
            throw new UnsupportedOperationException("This service registry is unmodifiable");
         }

         protected ServiceRegistry delegate() {
            return serviceRegistry;
         }
      };
   }
}
