package org.glassfish.soteria.authorization.spi;

import java.security.Principal;
import java.util.Set;

public interface CallerDetailsResolver {
   Principal getCallerPrincipal();

   Set getPrincipalsByType(Class var1);

   boolean isCallerInRole(String var1);

   Set getAllDeclaredCallerRoles();
}
