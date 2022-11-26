package weblogic.security.service;

public class ApplicationVersionCreationException extends SecurityServiceException {
   public ApplicationVersionCreationException() {
   }

   public ApplicationVersionCreationException(String msg) {
      super(msg);
   }

   public ApplicationVersionCreationException(Throwable nested) {
      super(nested);
   }

   public ApplicationVersionCreationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
