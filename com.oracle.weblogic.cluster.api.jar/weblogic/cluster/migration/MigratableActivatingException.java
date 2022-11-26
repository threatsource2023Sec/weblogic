package weblogic.cluster.migration;

import java.rmi.RemoteException;

public class MigratableActivatingException extends RemoteException {
   public MigratableActivatingException() {
   }

   public MigratableActivatingException(String msg) {
      super(msg);
   }

   public MigratableActivatingException(String msg, Throwable th) {
      super(msg, th);
   }
}
