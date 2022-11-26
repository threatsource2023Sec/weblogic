package weblogic.rmi.extensions;

import weblogic.rmi.extensions.server.DisconnectMonitorProvider;

public interface DisconnectMonitorList {
   void addDisconnectMonitor(DisconnectMonitorProvider var1);

   void removeDisconnectMonitor(DisconnectMonitorProvider var1);
}
