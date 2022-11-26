package weblogic.socket;

import java.net.ConnectException;

public final class UnrecoverableConnectException extends ConnectException {
   public UnrecoverableConnectException(String val) {
      super("[" + val + "]");
   }

   public UnrecoverableConnectException() {
   }
}
