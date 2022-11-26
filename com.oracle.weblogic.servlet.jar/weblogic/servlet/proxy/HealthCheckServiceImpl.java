package weblogic.servlet.proxy;

import java.rmi.RemoteException;
import weblogic.protocol.LocalServerIdentity;
import weblogic.utils.Debug;

public final class HealthCheckServiceImpl implements HealthCheck {
   private static final boolean DEBUG = true;
   private final int serverID = LocalServerIdentity.getIdentity().hashCode();

   public int getServerID() throws RemoteException {
      return this.serverID;
   }

   public void ping() throws RemoteException {
      Debug.say("RECEIVED PING " + this.serverID);
   }
}
