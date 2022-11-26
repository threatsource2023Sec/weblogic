package weblogic.persist;

public class DeadlockException extends Exception {
   public DeadlockException() {
   }

   public DeadlockException(String message) {
      super(message);
   }
}
