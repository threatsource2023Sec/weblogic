package weblogic.rmi.extensions.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HeartbeatHelper extends Remote {
   String JNDI_NAME = "weblogic/rmi/extensions/server/HeartbeatHelper";
   int PING_INTERVAL = 60000;
   int PING_PERIODS_UNTIL_TIMEOUT = 4;

   void ping() throws RemoteException;
}
