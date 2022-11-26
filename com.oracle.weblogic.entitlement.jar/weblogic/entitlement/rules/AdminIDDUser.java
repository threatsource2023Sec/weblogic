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

public final class AdminIDDUser extends AdminIDDUserPredicate {
   public AdminIDDUser() {
      super("AdminIDDUserPredicateName", "AdminIDDUserPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String user = this.getUser();
      return this.isAdminIDDUser(subject, user, context);
   }

   public boolean isAdminIDDUser(Subject subject, String user, ContextHandler context) {
      Iterator p = subject.getPrincipals().iterator();

      while(p.hasNext()) {
         Principal princ = (Principal)p.next();
         if (princ instanceof WLSPrincipal && princ instanceof WLSUser && princ.getName().equals(user)) {
            String identityDomain = ((IdentityDomainPrincipal)princ).getIdentityDomain();
            String admin_idd = IdentityDomainUtil.fetchAdminIDD(context);
            if (IdentityDomainUtil.isMatch(identityDomain, admin_idd)) {
               return true;
            }
         }
      }

      return false;
   }
}
