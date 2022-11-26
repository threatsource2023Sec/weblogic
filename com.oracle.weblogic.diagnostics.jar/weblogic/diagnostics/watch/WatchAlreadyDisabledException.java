package weblogic.diagnostics.watch;

public class WatchAlreadyDisabledException extends WatchException {
   public WatchAlreadyDisabledException() {
   }

   public WatchAlreadyDisabledException(String msg) {
      super(msg);
   }
}
