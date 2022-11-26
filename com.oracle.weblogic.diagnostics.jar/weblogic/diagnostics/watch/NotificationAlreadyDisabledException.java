package weblogic.diagnostics.watch;

public class NotificationAlreadyDisabledException extends WatchException {
   public NotificationAlreadyDisabledException() {
   }

   public NotificationAlreadyDisabledException(String msg) {
      super(msg);
   }
}
