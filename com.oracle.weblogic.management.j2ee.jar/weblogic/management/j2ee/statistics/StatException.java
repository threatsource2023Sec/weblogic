package weblogic.management.j2ee.statistics;

public class StatException extends Exception {
   public StatException() {
   }

   public StatException(String message) {
      super(message);
   }

   public StatException(String message, Throwable cause) {
      super(message, cause);
   }

   public StatException(Throwable cause) {
      super(cause);
   }
}
