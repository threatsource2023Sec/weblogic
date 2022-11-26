package weblogic.management;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.management.MBeanServer;

/** @deprecated */
@Deprecated
public interface RemoteMBeanServer extends MBeanServer, Remote {
   String JNDI_NAME = "weblogic.management.server";

   MBeanHome getMBeanHome() throws RemoteException;

   String getServerName();
}
