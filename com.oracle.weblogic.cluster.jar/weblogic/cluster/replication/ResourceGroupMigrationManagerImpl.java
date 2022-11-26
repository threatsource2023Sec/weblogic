package weblogic.cluster.replication;

import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterService;
import weblogic.jndi.Environment;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.annotation.internal.RmiMethodInternal;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManagerFactory;

public class ResourceGroupMigrationManagerImpl implements ResourceGroupMigrationManager {
   private boolean migrationInitiated = false;

   public static ResourceGroupMigrationManagerImpl theOne() {
      return ResourceGroupMigrationManagerImpl.SingletonMaker.singleton;
   }

   public void start() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ctx = env.getInitialContext();
         ctx.bind("weblogic.cluster.replication.ResourceGroupMigrationManager", this);
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3.getMessage());
      }
   }

   public void stop() throws ServiceFailureException {
      try {
         ResourceGroupMigrationHandler.getInstance().unbindResourceGroupMigrationMap();
      } catch (NamingException var2) {
         throw new ServiceFailureException(var2.getMessage());
      }
   }

   @RmiMethodInternal(
      asynchronousResult = true
   )
   public Future initiateResourceGroupMigration(String partitionName, String resourceGroupName, String targetCluster) throws MigrationInProgressException {
      Object result;
      try {
         result = this.initiateResourceGroupMigration(partitionName, resourceGroupName, targetCluster, -1);
      } catch (TimeoutException var6) {
         var6.printStackTrace();
         result = new StatusImpl(-1, "Timeout reached in initiateResourceGroupMigration");
      }

      return new ResourceGroupMigrationAsyncResult(result);
   }

   public Status initiateResourceGroupMigration(String partitionName, String resourceGroupName, String targetCluster, int timeout) throws TimeoutException, IllegalStateException, MigrationInProgressException {
      synchronized(this) {
         if (this.migrationInitiated) {
            throw new MigrationInProgressException("Resource Group migration already initiated.");
         }

         this.migrationInitiated = true;
      }

      boolean var16 = false;

      Status var6;
      try {
         var16 = true;
         this.initiateMigration(partitionName, resourceGroupName, targetCluster);
         ResourceGroupMigrationHandler.getInstance().blockTillCompletion(timeout);
         Status status = ResourceGroupMigrationHandler.getInstance().getClusterMigrationStatus();
         if (status.getStatus() == 1) {
            ResourceGroupMigrationHandler.getInstance().addTargetClusterAddressForPartition(partitionName, resourceGroupName, targetCluster);
         }

         var6 = status;
         var16 = false;
      } finally {
         if (var16) {
            synchronized(this) {
               this.migrationInitiated = false;
            }
         }
      }

      synchronized(this) {
         this.migrationInitiated = false;
         return var6;
      }
   }

   private void initiateMigration(final String partitionName, final String resourceGroupName, final String targetCluster) throws IllegalStateException {
      final RemoteClusterSecondarySelector remoteClusterSecondarySelector = ResourceGroupMigrationHandler.getInstance().getRemoteClusterSecondarySelector(targetCluster);
      String[] remoteServers = remoteClusterSecondarySelector.getActiveServersInRemoteCluster();
      if (remoteServers != null && remoteServers.length == 0) {
         throw new IllegalStateException("No active servers in target cluster: " + targetCluster);
      } else {
         ResourceGroupMigrationHandler.getInstance().broadcastInitiatePartitionMigrationRequest(partitionName, resourceGroupName, targetCluster);
         final ServerIdentity localID = ClusterService.getClusterServiceInternal().getLocalMember().identity();
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               ResourceGroupMigrationHandler.getInstance().initiateMigration(localID, partitionName, resourceGroupName, targetCluster, remoteClusterSecondarySelector);
            }
         });
      }
   }

   private static class SingletonMaker {
      private static final ResourceGroupMigrationManagerImpl singleton = new ResourceGroupMigrationManagerImpl();
   }
}
