package weblogic.security.principal;

import java.security.Principal;
import javax.security.auth.Subject;

public abstract class WLSAbstractPolicyFilePrincipal implements Principal {
   private String name = null;
   private boolean matchUser;
   private boolean matchGroup;

   public WLSAbstractPolicyFilePrincipal(String userName) {
      this.name = userName;
   }

   public String getName() {
      return new String(this.name);
   }

   public String toString() {
      return this.getName();
   }

   protected void setMatchUser(boolean matchUser) {
      this.matchUser = matchUser;
   }

   protected void setMatchGroup(boolean matchGroup) {
      this.matchGroup = matchGroup;
   }

   public boolean implies(Subject subject) {
      if (subject == null) {
         return false;
      } else {
         Principal[] principals = new Principal[subject.getPrincipals().size()];
         subject.getPrincipals().toArray(principals);

         for(int i = 0; i < principals.length; ++i) {
            if (principals[i] instanceof WLSAbstractPrincipal && this.principalMatches((WLSAbstractPrincipal)principals[i])) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean principalMatches(WLSAbstractPrincipal anotherPrincipal) {
      if (anotherPrincipal == null) {
         return false;
      } else if (!(anotherPrincipal instanceof WLSAbstractPrincipal)) {
         return false;
      } else if (this.matchUser && anotherPrincipal instanceof WLSUserImpl || this.matchGroup && anotherPrincipal instanceof WLSGroupImpl) {
         if (this.name != null && anotherPrincipal.getName() != null) {
            return this.name.equals(anotherPrincipal.getName());
         } else {
            return this.name == anotherPrincipal.getName();
         }
      } else {
         return false;
      }
   }
}
