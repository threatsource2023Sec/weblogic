package weblogic.ejb20.locks;

import java.rmi.RemoteException;

public class LockTimedOutException extends RemoteException {
   public LockTimedOutException() {
   }

   public LockTimedOutException(String s) {
      super(s);
   }

   public LockTimedOutException(String s, Throwable t) {
      super(s, t);
   }
}
