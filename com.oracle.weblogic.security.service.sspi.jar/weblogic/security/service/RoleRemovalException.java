package weblogic.security.service;

public class RoleRemovalException extends SecurityServiceException {
   public RoleRemovalException() {
   }

   public RoleRemovalException(String msg) {
      super(msg);
   }

   public RoleRemovalException(Throwable nested) {
      super(nested);
   }

   public RoleRemovalException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
