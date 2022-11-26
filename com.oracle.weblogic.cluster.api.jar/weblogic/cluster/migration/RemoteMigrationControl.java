package weblogic.cluster.migration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface RemoteMigrationControl extends Remote {
   String NAME = "weblogic.cluster.migrationControl";

   void activateTarget(String var1) throws RemoteException, MigrationException;

   void deactivateTarget(String var1, String var2) throws RemoteException, MigrationException;

   void restartTarget(String var1) throws RemoteException, MigrationException;

   Collection activatedTargets() throws RemoteException;

   int getMigratableState(String var1) throws RemoteException;

   void manualActivateDynamicService(String var1, String var2) throws RemoteException, MigrationException, IllegalArgumentException;

   void manualDeactivateDynamicService(String var1, String var2, String var3) throws RemoteException, MigrationException, IllegalArgumentException;
}
