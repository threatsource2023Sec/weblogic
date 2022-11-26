package weblogic.diagnostics.watch;

public class NotificationCreateException extends WatchException {
   public NotificationCreateException() {
   }

   public NotificationCreateException(String msg) {
      super(msg);
   }

   public NotificationCreateException(Throwable t) {
      super(t);
   }

   public NotificationCreateException(String msg, Throwable t) {
      super(msg, t);
   }
}
