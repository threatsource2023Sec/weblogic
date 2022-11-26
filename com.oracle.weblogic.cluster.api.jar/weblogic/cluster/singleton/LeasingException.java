package weblogic.cluster.singleton;

public class LeasingException extends Exception {
   private String message;

   public LeasingException(String message) {
      super(message);
      this.message = message;
   }

   public LeasingException(String message, Throwable t) {
      super(message, t);
      this.message = message;
   }

   public String toString() {
      return this.message;
   }
}
