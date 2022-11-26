package weblogic.entitlement.rules;

import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.entitlement.util.IdentityDomainUtil;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.principal.WLSPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;
import weblogic.security.spi.WLSUser;

public final class OwnerIDDUser extends OwnerIDDUserPredicate {
   public OwnerIDDUser() {
      super("OwnerIDDUserPredicateName", "OwnerIDDUserPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String user = this.getUser();
      return this.isOwnerIDDUser(subject, user, context);
   }

   public boolean isOwnerIDDUser(Subject subject, String user, ContextHandler context) {
      Iterator p = subject.getPrincipals().iterator();

      while(p.hasNext()) {
         Principal princ = (Principal)p.next();
         if (princ instanceof WLSPrincipal && princ instanceof WLSUser && princ.getName().equals(user)) {
            String identityDomain = ((IdentityDomainPrincipal)princ).getIdentityDomain();
            String resource_idd = IdentityDomainUtil.fetchOwnerIDD(context);
            if (IdentityDomainUtil.isMatch(identityDomain, resource_idd)) {
               return true;
            }
         }
      }

      return false;
   }
}
