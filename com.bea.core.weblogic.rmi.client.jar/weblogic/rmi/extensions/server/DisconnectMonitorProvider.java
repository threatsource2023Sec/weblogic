package weblogic.rmi.extensions.server;

import java.rmi.Remote;
import weblogic.rmi.extensions.DisconnectListener;

public interface DisconnectMonitorProvider {
   boolean addDisconnectListener(Remote var1, DisconnectListener var2);

   boolean removeDisconnectListener(Remote var1, DisconnectListener var2);
}
