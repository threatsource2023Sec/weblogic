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

public final class IDDGroup extends IDDGroupPredicate {
   public IDDGroup() {
      super("IDDGroupPredicateName", "IDDGroupPredicateDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      String group = this.getGroup();
      String idd = this.getIdd();
      return this.isIDDGroup(subject, group, idd);
   }

   public boolean isIDDGroup(Subject subject, String group, String idd) {
      Iterator p = subject.getPrincipals().iterator();

      while(p.hasNext()) {
         Principal princ = (Principal)p.next();
         if (princ instanceof WLSPrincipal && princ instanceof WLSGroup && princ.getName().equals(group)) {
            String identityDomain = ((IdentityDomainPrincipal)princ).getIdentityDomain();
            if (IdentityDomainUtil.isMatch(identityDomain, idd)) {
               return true;
            }
         }
      }

      return false;
   }
}
