package weblogic.security.service;

public class ProviderException extends SecurityServiceRuntimeException {
   public ProviderException() {
   }

   public ProviderException(String msg) {
      super(msg);
   }

   public ProviderException(Throwable nested) {
      super(nested);
   }

   public ProviderException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
