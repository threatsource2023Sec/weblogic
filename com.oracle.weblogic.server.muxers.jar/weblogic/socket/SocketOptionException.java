package weblogic.socket;

import java.net.SocketException;

public class SocketOptionException extends SocketException {
   public SocketOptionException(String msg) {
      super(msg);
   }

   public SocketOptionException() {
   }
}
