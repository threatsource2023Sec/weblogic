package weblogic.cluster.replication;

import java.rmi.RemoteException;

public final class ApplicationUnavailableRemoteException extends RemoteException {
   private ApplicationUnavailableException aue;

   public ApplicationUnavailableRemoteException(String msg, ApplicationUnavailableException aue) {
      super(msg);

      assert aue != null;

      this.aue = aue;
   }

   public ApplicationUnavailableException getApplicationUnavailableException() {
      return this.aue;
   }
}
