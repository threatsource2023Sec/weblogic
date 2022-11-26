package weblogic.entitlement.rules;

import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.entitlement.util.IdentityDomainUtil;
import weblogic.security.principal.IDCSScope;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.principal.WLSPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public final class Scope extends ScopePredicate {
   public Scope() {
      super("ScopePredicateName", "ScopePredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String user = this.getScope();
      String iddArg = this.getIdd();
      return this.isScope(subject, user, iddArg, context);
   }

   public boolean isScope(Subject subject, String scope, String iddArg, ContextHandler context) {
      Iterator p = subject.getPrincipals().iterator();

      String identityDomain;
      label47:
      do {
         String resource_idd;
         do {
            Principal princ;
            do {
               do {
                  do {
                     if (!p.hasNext()) {
                        if (log.isDebugEnabled()) {
                           log.debug("Scope not found in the subject: " + scope);
                        }

                        return false;
                     }

                     princ = (Principal)p.next();
                  } while(!(princ instanceof WLSPrincipal));
               } while(!(princ instanceof IDCSScope));
            } while(!princ.getName().equals(scope));

            identityDomain = ((IdentityDomainPrincipal)princ).getIdentityDomain();
            resource_idd = IdentityDomainUtil.fetchOwnerIDD(context);
            if (log.isDebugEnabled()) {
               log.debug("Looking for scope in the subject: " + scope + " idd_arg: " + iddArg + " res_idd: " + resource_idd);
            }

            if (iddArg != null && !iddArg.isEmpty()) {
               continue label47;
            }
         } while(!IdentityDomainUtil.isMatch(identityDomain, resource_idd));

         return true;
      } while(!IdentityDomainUtil.isMatch(identityDomain, iddArg));

      return true;
   }
}
