package weblogic.security.spi;

public class ConsumptionException extends SecuritySpiException {
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
