package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface RoleDescriptor {
   String getName();

   boolean isExternallyDefined();

   boolean isAnyAuthUserRoleDefined();

   Collection getAllSecurityPrincipals();
}
