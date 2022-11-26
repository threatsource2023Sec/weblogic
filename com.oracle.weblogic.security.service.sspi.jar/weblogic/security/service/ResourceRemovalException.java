package weblogic.security.service;

public class ResourceRemovalException extends SecurityServiceException {
   public ResourceRemovalException() {
   }

   public ResourceRemovalException(String msg) {
      super(msg);
   }

   public ResourceRemovalException(Throwable nested) {
      super(nested);
   }

   public ResourceRemovalException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
