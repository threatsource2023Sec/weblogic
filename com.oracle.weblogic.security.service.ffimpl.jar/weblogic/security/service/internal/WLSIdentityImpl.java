package weblogic.security.service.internal;

import com.bea.common.security.service.Identity;
import java.security.Principal;
import javax.security.auth.Subject;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.principal.IdentityDomainPrincipal;

public class WLSIdentityImpl implements Identity {
   private AuthenticatedSubject authenticatedSubject;

   public WLSIdentityImpl(AuthenticatedSubject authenticatedSubject) {
      this.authenticatedSubject = authenticatedSubject;
   }

   public Subject getSubject() {
      return this.authenticatedSubject == null ? null : this.authenticatedSubject.getSubject();
   }

   public boolean isAnonymous() {
      return SubjectUtils.isUserAnonymous(this.authenticatedSubject);
   }

   public String getUsername() {
      return SubjectUtils.getUsername(this.authenticatedSubject);
   }

   public String toString() {
      return this.authenticatedSubject == null ? "" : SubjectUtils.displaySubject(this.authenticatedSubject);
   }

   public AuthenticatedSubject getAuthenticatedSubject() {
      return this.authenticatedSubject;
   }

   public IdentityDomainNames getUser() {
      if (this.isAnonymous()) {
         return new IdentityDomainNames(this.getUsername(), (String)null);
      } else {
         Principal userPrincipal = SubjectUtils.getUserPrincipal(this.authenticatedSubject);
         if (userPrincipal == null) {
            return new IdentityDomainNames(this.getUsername(), (String)null);
         } else {
            String userName = userPrincipal.getName();
            String idd = userPrincipal instanceof IdentityDomainPrincipal ? ((IdentityDomainPrincipal)userPrincipal).getIdentityDomain() : null;
            return new IdentityDomainNames(userName, idd);
         }
      }
   }
}
