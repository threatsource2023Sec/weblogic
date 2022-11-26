package weblogic.rmi.extensions;

import java.rmi.ConnectException;

public class UnrecoverableConnectionException extends ConnectException {
   public UnrecoverableConnectionException(String msg) {
      super(msg);
   }

   public UnrecoverableConnectionException(String msg, Exception th) {
      super(msg, th);
   }
}
