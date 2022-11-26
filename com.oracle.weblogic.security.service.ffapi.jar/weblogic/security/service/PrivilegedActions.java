package weblogic.security.service;

import java.security.Principal;
import java.security.PrivilegedAction;
import weblogic.security.spi.PrincipalValidator;

public class PrivilegedActions {
   public static PrivilegedAction getKernelIdentityAction() {
      return GetKernelIdentityAction.THE_ONE;
   }

   public static PrivilegedAction getSignPrincipalAction(PrincipalValidator validator, Principal principal) {
      return new SignPrincipalAction(validator, principal);
   }

   private static class SignPrincipalAction implements PrivilegedAction {
      private Principal principal;
      private PrincipalValidator validator;

      public SignPrincipalAction(PrincipalValidator validator, Principal principal) {
         this.validator = validator;
         this.principal = principal;
      }

      public Object run() {
         this.validator.sign(this.principal);
         return null;
      }
   }
}
