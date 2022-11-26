package weblogic.cluster.singleton;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MigratorInterface extends Remote {
   boolean migrate(String var1, String var2) throws RemoteException;

   boolean migrate(String var1, String var2, boolean var3, boolean var4) throws RemoteException;

   boolean migrateJTA(String var1, String var2, boolean var3, boolean var4) throws RemoteException;
}
