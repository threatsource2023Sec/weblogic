package weblogic.entitlement.rules;

import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.entitlement.util.IdentityDomainUtil;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.principal.WLSPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;
import weblogic.security.spi.WLSGroup;

public final class AdminIDDGroup extends AdminIDDGroupPredicate {
   public AdminIDDGroup() {
      super("AdminIDDGroupPredicateName", "AdminIDDGroupPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String group = this.getGroup();
      return this.isAdminIDDGroup(subject, group, context);
   }

   public boolean isAdminIDDGroup(Subject subject, String group, ContextHandler context) {
      Iterator p = subject.getPrincipals().iterator();

      while(p.hasNext()) {
         Principal princ = (Principal)p.next();
         if (princ instanceof WLSPrincipal && princ instanceof WLSGroup && princ.getName().equals(group)) {
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
