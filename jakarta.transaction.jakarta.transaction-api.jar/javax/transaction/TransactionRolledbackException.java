package javax.transaction;

import java.rmi.RemoteException;

public class TransactionRolledbackException extends RemoteException {
   private static final long serialVersionUID = -3142798139623020577L;

   public TransactionRolledbackException() {
   }

   public TransactionRolledbackException(String msg) {
      super(msg);
   }
}
