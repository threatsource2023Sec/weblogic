package weblogic.transaction;

import java.rmi.RemoteException;

public class TransactionSystemException extends RemoteException {
   public TransactionSystemException() {
   }

   public TransactionSystemException(String s) {
      super(s);
   }

   public TransactionSystemException(String s, Throwable t) {
      super(s, t);
   }
}
