package weblogic.scheduler;

public class TimerAlreadyExistsException extends Exception {
   public TimerAlreadyExistsException() {
   }

   public TimerAlreadyExistsException(String message) {
      super(message);
   }

   public TimerAlreadyExistsException(String message, Throwable cause) {
      super(message, cause);
   }

   public TimerAlreadyExistsException(Throwable cause) {
      super(cause);
   }
}
