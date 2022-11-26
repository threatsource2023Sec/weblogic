package weblogic.rmi.extensions.server;

import java.rmi.RemoteException;

public class RemoteExceptionWrapper extends RemoteException {
   public RemoteExceptionWrapper(String message, RemoteException cause) {
      super(message, cause);
   }

   public RemoteExceptionWrapper(RemoteException cause) {
      super("", cause);
   }
}
