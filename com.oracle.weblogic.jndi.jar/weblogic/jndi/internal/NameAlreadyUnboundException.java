package weblogic.jndi.internal;

import java.rmi.RemoteException;

public class NameAlreadyUnboundException extends RemoteException {
   public NameAlreadyUnboundException(String msg) {
      super(msg);
   }
}
