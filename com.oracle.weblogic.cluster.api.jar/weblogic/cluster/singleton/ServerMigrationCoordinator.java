package weblogic.cluster.singleton;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMigrationCoordinator extends Remote {
   String JNDI_NAME = "weblogic/cluster/singleton/ServerMigrationCoordinator";

   void migrate(String var1, String var2, String var3, boolean var4, boolean var5) throws ServerMigrationException, RemoteException;

   void migrate(String var1, String var2, String var3, boolean var4, boolean var5, String var6) throws ServerMigrationException, RemoteException;
}
