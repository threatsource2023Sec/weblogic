package javolution.realtime;

public class ConcurrentException extends RuntimeException {
   private Throwable _cause;
   private static final long serialVersionUID = 1L;

   ConcurrentException(Throwable var1) {
      this._cause = var1;
   }

   public Throwable getCause() {
      return this._cause;
   }
}
