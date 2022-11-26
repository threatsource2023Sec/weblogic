package weblogic.diagnostics.watch;

public class WatchAlreadyExistsException extends WatchException {
   public WatchAlreadyExistsException() {
   }

   public WatchAlreadyExistsException(String msg) {
      super(msg);
   }

   public WatchAlreadyExistsException(Throwable t) {
      super(t);
   }

   public WatchAlreadyExistsException(String msg, Throwable t) {
      super(msg, t);
   }
}
