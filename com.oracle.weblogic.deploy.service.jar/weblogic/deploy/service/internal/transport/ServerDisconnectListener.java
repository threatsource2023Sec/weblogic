package weblogic.deploy.service.internal.transport;

import weblogic.rmi.extensions.DisconnectListener;

public interface ServerDisconnectListener extends DisconnectListener {
   void registerListener(DisconnectListener var1);

   void unregisterListener(DisconnectListener var1);
}
