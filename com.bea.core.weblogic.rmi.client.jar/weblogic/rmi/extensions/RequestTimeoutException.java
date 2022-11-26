package weblogic.rmi.extensions;

import java.rmi.RemoteException;

public final class RequestTimeoutException extends RemoteException {
   private static final long serialVersionUID = 2783930896687584691L;

   public RequestTimeoutException() {
   }

   public RequestTimeoutException(String s) {
      super(s);
   }
}
