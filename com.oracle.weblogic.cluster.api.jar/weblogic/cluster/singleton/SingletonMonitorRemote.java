package weblogic.cluster.singleton;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.cluster.migration.DynamicLoadbalancer;

public interface SingletonMonitorRemote extends Remote, MigratorInterface {
   String JNDI_NAME = "weblogic/cluster/singleton/SingletonMonitorRemote";

   void unregister(String var1) throws RemoteException;

   void register(String var1) throws RemoteException;

   void registerJTA(String var1) throws RemoteException;

   String findServiceLocation(String var1) throws RemoteException;

   boolean migrate(String var1, String var2) throws RemoteException;

   void deactivateJTA(String var1, String var2) throws RemoteException;

   void notifyShutdown(String var1);

   void manualMigrateDynamicService(String var1, String var2, String var3) throws RemoteException, IllegalArgumentException;

   DynamicLoadbalancer.ServiceStatus getServiceStatus(String var1, String var2) throws RemoteException, IllegalArgumentException;
}
