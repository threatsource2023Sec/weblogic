package weblogic.security.spi;

public class RoleCreationException extends SecuritySpiException {
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
