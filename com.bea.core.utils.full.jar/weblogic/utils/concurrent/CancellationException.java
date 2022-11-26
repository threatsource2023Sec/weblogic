package weblogic.utils.concurrent;

public class CancellationException extends IllegalStateException {
   public CancellationException() {
   }

   public CancellationException(String message) {
      super(message);
   }
}
