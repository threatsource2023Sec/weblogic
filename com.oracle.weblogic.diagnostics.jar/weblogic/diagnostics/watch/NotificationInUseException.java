package weblogic.diagnostics.watch;

public class NotificationInUseException extends WatchException {
   public NotificationInUseException() {
   }

   public NotificationInUseException(String msg) {
      super(msg);
   }

   public NotificationInUseException(Throwable t) {
      super(t);
   }

   public NotificationInUseException(String msg, Throwable t) {
      super(msg, t);
   }
}
