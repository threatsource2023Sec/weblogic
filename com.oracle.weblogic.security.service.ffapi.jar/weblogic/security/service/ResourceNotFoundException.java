package weblogic.security.service;

/** @deprecated */
@Deprecated
public class ResourceNotFoundException extends SecurityServiceException {
   public ResourceNotFoundException() {
   }

   public ResourceNotFoundException(String msg) {
      super(msg);
   }

   public ResourceNotFoundException(Throwable nested) {
      super(nested);
   }

   public ResourceNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
