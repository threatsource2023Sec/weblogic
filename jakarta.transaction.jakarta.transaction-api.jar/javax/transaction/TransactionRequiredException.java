package javax.transaction;

import java.rmi.RemoteException;

public class TransactionRequiredException extends RemoteException {
   private static final long serialVersionUID = -1898806419937446439L;

   public TransactionRequiredException() {
   }

   public TransactionRequiredException(String msg) {
      super(msg);
   }
}
