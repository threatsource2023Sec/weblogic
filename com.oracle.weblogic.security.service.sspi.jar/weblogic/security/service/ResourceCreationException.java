package weblogic.security.service;

public class ResourceCreationException extends SecurityServiceException {
   public ResourceCreationException() {
   }

   public ResourceCreationException(String msg) {
      super(msg);
   }

   public ResourceCreationException(Throwable nested) {
      super(nested);
   }

   public ResourceCreationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
