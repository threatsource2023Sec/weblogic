package javax.transaction;

import java.rmi.RemoteException;

public class InvalidTransactionException extends RemoteException {
   private static final long serialVersionUID = 3597320220337691496L;

   public InvalidTransactionException() {
   }

   public InvalidTransactionException(String msg) {
      super(msg);
   }
}
