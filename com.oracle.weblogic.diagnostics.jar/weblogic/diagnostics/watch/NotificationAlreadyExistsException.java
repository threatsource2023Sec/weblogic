package weblogic.diagnostics.watch;

public class NotificationAlreadyExistsException extends WatchException {
   public NotificationAlreadyExistsException() {
   }

   public NotificationAlreadyExistsException(String msg) {
      super(msg);
   }

   public NotificationAlreadyExistsException(Throwable t) {
      super(t);
   }

   public NotificationAlreadyExistsException(String msg, Throwable t) {
      super(msg, t);
   }
}
