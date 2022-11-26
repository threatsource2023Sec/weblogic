package weblogic.security.service;

public class ApplicationVersionRemovalException extends SecurityServiceException {
   public ApplicationVersionRemovalException() {
   }

   public ApplicationVersionRemovalException(String msg) {
      super(msg);
   }

   public ApplicationVersionRemovalException(Throwable nested) {
      super(nested);
   }

   public ApplicationVersionRemovalException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
