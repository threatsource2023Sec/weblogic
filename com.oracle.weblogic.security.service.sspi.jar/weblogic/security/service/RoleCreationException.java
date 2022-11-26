package weblogic.security.service;

public class RoleCreationException extends SecurityServiceException {
   public RoleCreationException() {
   }

   public RoleCreationException(String msg) {
      super(msg);
   }

   public RoleCreationException(Throwable nested) {
      super(nested);
   }

   public RoleCreationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
