package weblogic.diagnostics.watch;

public class NotificationAlreadyEnabledException extends WatchException {
   public NotificationAlreadyEnabledException() {
   }

   public NotificationAlreadyEnabledException(String msg) {
      super(msg);
   }
}
