package weblogic.utils.concurrent;

public class TimeoutException extends Exception {
   public TimeoutException() {
   }

   public TimeoutException(String message) {
      super(message);
   }
}
