package weblogic.security.jacc.simpleprovider;

import java.security.Principal;
import javax.security.auth.Subject;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.principal.WLSGroupImpl;
import weblogic.security.principal.WLSUserImpl;

public class WLSJACCPrincipalImpl implements Principal {
   private String name = null;

   public WLSJACCPrincipalImpl(String userName) {
      this.name = userName;
   }

   public String getName() {
      return new String(this.name);
   }

   public String toString() {
      return this.getName();
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object another) {
      if (another == null) {
         return false;
      } else if (this == another) {
         return true;
      } else if (!(another instanceof WLSAbstractPrincipal)) {
         return false;
      } else {
         WLSAbstractPrincipal anotherPrincipal = (WLSAbstractPrincipal)another;
         if (!(anotherPrincipal instanceof WLSUserImpl) && !(anotherPrincipal instanceof WLSGroupImpl)) {
            return false;
         } else if (this.name != null && anotherPrincipal.getName() != null) {
            return this.name.equals(anotherPrincipal.getName());
         } else {
            return this.name == anotherPrincipal.getName();
         }
      }
   }

   public boolean implies(Subject subject) {
      if (subject == null) {
         return false;
      } else {
         Principal[] principals = new Principal[subject.getPrincipals().size()];
         subject.getPrincipals().toArray(principals);

         for(int i = 0; i < principals.length; ++i) {
            if (principals[i] instanceof WLSAbstractPrincipal && this.equals((WLSAbstractPrincipal)principals[i])) {
               return true;
            }
         }

         return false;
      }
   }
}
