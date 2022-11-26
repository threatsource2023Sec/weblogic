package weblogic.rmi.extensions;

import java.rmi.Remote;

public interface DisconnectMonitor {
   String JNDI_NAME = "weblogic.DisconnectMonitor";

   void addDisconnectListener(Remote var1, DisconnectListener var2) throws DisconnectMonitorUnavailableException;

   void removeDisconnectListener(Remote var1, DisconnectListener var2) throws DisconnectMonitorUnavailableException;
}
