package weblogic.persist;

public class QueueTimeoutException extends Exception {
   public QueueTimeoutException() {
   }

   public QueueTimeoutException(String message) {
      super(message);
   }
}
