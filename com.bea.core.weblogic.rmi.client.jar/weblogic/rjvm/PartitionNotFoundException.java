package weblogic.rjvm;

import java.rmi.RemoteException;

public class PartitionNotFoundException extends RemoteException {
   public PartitionNotFoundException() {
   }

   public PartitionNotFoundException(String message) {
      super(message);
   }
}
