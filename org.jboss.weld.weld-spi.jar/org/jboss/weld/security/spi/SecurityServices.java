package org.jboss.weld.security.spi;

import java.security.Principal;
import java.util.function.Consumer;
import org.jboss.weld.bootstrap.api.Service;

public interface SecurityServices extends Service {
   Principal getPrincipal();

   default SecurityContext getSecurityContext() {
      return SecurityContext.NOOP_SECURITY_CONTEXT;
   }

   default Consumer getSecurityContextAssociator() {
      SecurityContext securityContext = this.getSecurityContext();
      return (action) -> {
         try {
            securityContext.associate();
            action.run();
         } finally {
            securityContext.dissociate();
            securityContext.close();
         }

      };
   }
}
