package weblogic.security.service;

/** @deprecated */
@Deprecated
public class RoleNotFoundException extends SecurityServiceException {
   public RoleNotFoundException() {
   }

   public RoleNotFoundException(String msg) {
      super(msg);
   }

   public RoleNotFoundException(Throwable nested) {
      super(nested);
   }

   public RoleNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
