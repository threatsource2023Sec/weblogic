package weblogic.security.service;

public class ConsumptionException extends SecurityServiceException {
   public ConsumptionException() {
   }

   public ConsumptionException(String msg) {
      super(msg);
   }

   public ConsumptionException(Throwable nested) {
      super(nested);
   }

   public ConsumptionException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
