package weblogic.socket.utils;

public final class QueueFullException extends Exception {
   private static final long serialVersionUID = -6075992994979730359L;

   public QueueFullException(String s) {
      super(s);
   }

   public QueueFullException() {
   }
}
