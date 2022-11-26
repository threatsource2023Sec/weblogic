package weblogic.cluster.replication;

import java.rmi.Remote;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public interface ResourceGroupMigrationManager extends Remote {
   String JNDI_NAME = "weblogic.cluster.replication.ResourceGroupMigrationManager";

   Future initiateResourceGroupMigration(String var1, String var2, String var3) throws MigrationInProgressException;

   Status initiateResourceGroupMigration(String var1, String var2, String var3, int var4) throws TimeoutException, IllegalStateException, MigrationInProgressException;
}
