package weblogic.cluster.migration;

import java.rmi.RemoteException;

public class MigratableInactiveException extends RemoteException {
   public MigratableInactiveException() {
   }

   public MigratableInactiveException(String msg) {
      super(msg);
   }

   public MigratableInactiveException(String msg, Throwable th) {
      super(msg, th);
   }
}
