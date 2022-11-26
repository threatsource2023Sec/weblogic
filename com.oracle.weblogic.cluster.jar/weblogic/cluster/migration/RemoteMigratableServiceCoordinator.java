package weblogic.cluster.migration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.store.PersistentStoreException;

public interface RemoteMigratableServiceCoordinator extends Remote {
   String JNDI_NAME = "weblogic.cluster.migration.migratableServiceCoordinator";

   void migrateJTA(String var1, String var2, boolean var3, boolean var4) throws MigrationException;

   void deactivateJTA(String var1, String var2) throws RemoteException, MigrationException;

   String getCurrentLocationOfJTA(String var1) throws RemoteException, PersistentStoreException;

   void setCurrentLocation(String var1, String var2) throws RemoteException, PersistentStoreException;
}
