package weblogic.diagnostics.watch;

public class InvalidWatchException extends IllegalArgumentException {
   public InvalidWatchException() {
   }

   public InvalidWatchException(String msg) {
      super(msg);
   }

   public InvalidWatchException(Throwable t) {
      this.initCause(t);
   }

   public InvalidWatchException(String msg, Throwable t) {
      super(msg);
      this.initCause(t);
   }
}
