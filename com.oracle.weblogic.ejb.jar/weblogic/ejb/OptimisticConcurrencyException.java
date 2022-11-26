package weblogic.ejb;

public class OptimisticConcurrencyException extends RuntimeException {
   public OptimisticConcurrencyException() {
   }

   public OptimisticConcurrencyException(String s) {
      super(s);
   }
}
