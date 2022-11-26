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

public final class IDDUser extends IDDUserPredicate {
   public IDDUser() {
      super("IDDUserPredicateName", "IDDUserPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String user = this.getUser();
      String idd = this.getIdd();
      return this.isIDDUser(subject, user, idd);
   }

   public boolean isIDDUser(Subject subject, String user, String idd) {
      Iterator p = subject.getPrincipals().iterator();

      while(p.hasNext()) {
         Principal princ = (Principal)p.next();
         if (princ instanceof WLSPrincipal && princ instanceof WLSUser && princ.getName().equals(user)) {
            String identityDomain = ((IdentityDomainPrincipal)princ).getIdentityDomain();
            if (IdentityDomainUtil.isMatch(identityDomain, idd)) {
               return true;
            }
         }
      }

      return false;
   }
}
