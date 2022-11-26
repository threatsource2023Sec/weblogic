package weblogic.rmi.extensions;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConnectMonitor {
   String JNDI_NAME = "weblogic.ConnectMonitor";

   void addConnectListener(ConnectListener var1);

   void removeConnectListener(ConnectListener var1);

   void addConnectDisconnectListener(ConnectListener var1, DisconnectListener var2);
}
