package weblogic.entitlement.rules;

import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.security.principal.IDCSAppRole;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class IDCSAppRoleName extends IDCSAppRoleNamePredicate {
   public IDCSAppRoleName() {
      super("IDCSAppRoleNamePredicateName", "IDCSAppRoleNamePredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String appName = this.getAppName();
      String roleName = this.getRoleName();
      return this.isAppRole(subject, roleName, appName);
   }

   public boolean isAppRole(Subject subject, String roleName, String appName) {
      boolean result = false;
      if (subject != null) {
         Iterator principals = subject.getPrincipals().iterator();

         while(principals.hasNext()) {
            Principal p = (Principal)principals.next();
            if (p instanceof IDCSAppRole) {
               IDCSAppRole role = (IDCSAppRole)p;
               String aN = role.getAppName();
               String rN = role.getName();
               if (aN != null && rN != null && !aN.isEmpty() && !rN.isEmpty() && aN.equals(appName) && rN.equals(roleName)) {
                  result = true;
                  if (log.isDebugEnabled()) {
                     log.debug("Found a matched IDCS AppRole in the subject: " + role);
                  }
                  break;
               }
            }

            if (log.isDebugEnabled()) {
               log.debug("IDCS AppRole not found in the subject.");
            }
         }
      }

      return result;
   }
}
