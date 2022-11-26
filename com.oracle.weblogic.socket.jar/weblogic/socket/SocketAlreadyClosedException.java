package weblogic.socket;

public class SocketAlreadyClosedException extends RuntimeException {
   public SocketAlreadyClosedException(String msg) {
      super(msg);
   }
}
