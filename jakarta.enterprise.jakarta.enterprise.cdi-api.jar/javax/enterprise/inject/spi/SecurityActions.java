package javax.enterprise.inject.spi;

import java.security.AccessController;
import java.util.ServiceLoader;

final class SecurityActions {
   private SecurityActions() {
   }

   static ServiceLoader loadService(Class service, ClassLoader classLoader) {
      return (ServiceLoader)AccessController.doPrivileged(() -> {
         return ServiceLoader.load(service, classLoader);
      });
   }
}
