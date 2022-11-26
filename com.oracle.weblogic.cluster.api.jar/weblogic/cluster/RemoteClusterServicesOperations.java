package weblogic.cluster;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteClusterServicesOperations extends Remote {
   String JNDI_NAME = "weblogic.cluster.RemoteClusterServicesOperations";

   void setSessionLazyDeserializationEnabled(boolean var1) throws RemoteException;

   void setFailoverServerGroups(List var1) throws RemoteException;

   void setSessionReplicationOnShutdownEnabled(boolean var1) throws RemoteException;

   void disableSessionStateQueryProtocolAfter(int var1) throws RemoteException;

   void setSessionStateQueryProtocolEnabled(boolean var1) throws RemoteException;

   void setCleanupOrphanedSessionsEnabled(boolean var1) throws RemoteException;

   void setSessionLazyDeserializationEnabled(String var1, boolean var2) throws RemoteException;

   void setFailoverServerGroups(String var1, List var2) throws RemoteException;

   void setSessionReplicationOnShutdownEnabled(String var1, boolean var2) throws RemoteException;

   void disableSessionStateQueryProtocolAfter(String var1, int var2) throws RemoteException;

   void setSessionStateQueryProtocolEnabled(String var1, boolean var2) throws RemoteException;

   void setCleanupOrphanedSessionsEnabled(String var1, boolean var2) throws RemoteException;
}
