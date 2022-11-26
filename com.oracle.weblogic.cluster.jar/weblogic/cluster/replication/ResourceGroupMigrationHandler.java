package weblogic.cluster.replication;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterService;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.jndi.Environment;
import weblogic.protocol.ClusterURLFactory;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;

public class ResourceGroupMigrationHandler {
   private static TreeSet localServers;
   static AtomicBoolean inMigrationMode = new AtomicBoolean(false);
   private static Object notifier = new Object();
   private static boolean complete = false;
   private MigrationStatus localMigrationStatus;
   private StatusImpl clusterMigrationStatus;
   private static String JNDI_NAME = "weblogic.cluster.RGMigrationTargetMap";
   private Context ctx;

   public static ResourceGroupMigrationHandler getInstance() {
      return ResourceGroupMigrationHandler.SingletonMaker.singleton;
   }

   private ResourceGroupMigrationHandler() {
      this.localMigrationStatus = ResourceGroupMigrationHandler.MigrationStatus.NOT_STARTED;
      this.clusterMigrationStatus = new StatusImpl(-1, "Cluster Resource Group Migration has not been initiated.");

      try {
         Environment env = new Environment();
         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(true);
         this.ctx = env.getInitialContext();
      } catch (NamingException var2) {
         var2.printStackTrace(System.out);
      }

   }

   public synchronized void broadcastInitiatePartitionMigrationRequest(String partitionName, String resourceGroupName, String targetCluster) {
      MulticastSession sender = ClusterService.getClusterServiceInternal().createMulticastSession((RecoverListener)null, -1);

      try {
         sender.send(new InitiateResourceGroupMigration(partitionName, resourceGroupName, targetCluster));
      } catch (IOException var7) {
         var7.printStackTrace();
         String msg = var7.getMessage() == null ? "IOException when sending broadcast message" : var7.getMessage();
         this.setClusterMigrationStatus(-1, msg);
      }

   }

   public synchronized void handleResponse(HostID sender, InitiateResourceGroupMigrationResponse response) {
      ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationHandler.handleResponse sender: " + sender + ", servers in response table: " + localServers);
      Iterator itr = localServers.iterator();

      while(itr.hasNext()) {
         HostID member = (HostID)itr.next();
         if (sender.equals(member)) {
            itr.remove();
            break;
         }
      }

      ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationHandler.handleResponse server response table after processing: " + localServers);
      if (localServers.size() == 0) {
         ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationHandler.handleResponse last sender response: " + sender + " notifying orchestrator");
         this.setClusterMigrationStatus(1, "All session state migrated");
         synchronized(notifier) {
            complete = true;
            notifier.notify();
         }
      }

   }

   boolean isServerInLocalCluster(HostID host) {
      return localServers != null && host != null ? localServers.contains(host) : false;
   }

   public synchronized RemoteClusterSecondarySelector getRemoteClusterSecondarySelector(String targetCluster) throws IllegalStateException {
      String targetClusterAddress;
      try {
         targetClusterAddress = ClusterURLFactory.getInstance().parseClusterURL("cluster:t3://" + targetCluster);
      } catch (MalformedURLException var4) {
         throw new IllegalStateException(var4.getMessage());
      }

      ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationManagerImpl.initiateMigration targetClusterAddress: " + targetClusterAddress);
      RemoteClusterSecondarySelector remoteClusterSecondarySelector = new RemoteClusterSecondarySelector(targetClusterAddress);
      return remoteClusterSecondarySelector;
   }

   public synchronized void initiateMigration(HostID sender, String partitionName, String resourceGroupName, String targetClusterName) {
      this.initiateMigration(sender, partitionName, resourceGroupName, targetClusterName, this.getRemoteClusterSecondarySelector(targetClusterName));
   }

   void initiateMigration(HostID sender, String partitionName, String resourceGroupName, String targetClusterName, RemoteClusterSecondarySelector remoteClusterSecondarySelector) {
      ResourceGroupKey resourceGroupKey = null;

      try {
         this.setLocalMigrationStatus(ResourceGroupMigrationHandler.MigrationStatus.IN_PROGRESS);
         ReplicationServicesImplBase.validateResourceGroup(partitionName, resourceGroupName);
         resourceGroupKey = ResourceGroupKeyImpl.createKey(partitionName, resourceGroupName);
         if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
            String[] remoteServers = remoteClusterSecondarySelector.getActiveServersInRemoteCluster();

            for(int i = 0; i < remoteServers.length; ++i) {
               ResourceGroupMigrationDebugLogger.debug("ReplicationManager.initiateMigration remote server[" + i + "]: " + remoteServers[i]);
            }

            ResourceGroupMigrationDebugLogger.debug("ReplicationManager.initiateMigration remote secondary: " + remoteClusterSecondarySelector.getSecondarySrvr());
         }

         setLocalServers(remoteClusterSecondarySelector.getLocalServers());
         ReplicationManager replicationManager = (ReplicationManager)GlobalServiceLocator.getServiceLocator().getService(ReplicationManager.class, new Annotation[0]);
         replicationManager.setSecondarySelector(resourceGroupKey, remoteClusterSecondarySelector);
         inMigrationMode.set(true);
         this.migratePrimarySessionsToRemoteCluster(resourceGroupKey);
         inMigrationMode.set(false);
         this.setLocalMigrationStatus(ResourceGroupMigrationHandler.MigrationStatus.SUCCESS);
      } catch (Exception var10) {
         var10.printStackTrace();
         ResourceGroupMigrationDebugLogger.debug((String)("Exception in ResourceGroupMigrationHandler.initiateMigration ResourceGroup " + resourceGroupKey), (Throwable)var10);
         this.setLocalMigrationStatus(ResourceGroupMigrationHandler.MigrationStatus.FAILURE);
      }

      this.broadcastInitiatePartitionMigrationResponse(sender);
   }

   private void setLocalMigrationStatus(MigrationStatus status) {
      this.localMigrationStatus = status;
   }

   public MigrationStatus getLocalMigrationStatus() {
      return this.localMigrationStatus;
   }

   Status getClusterMigrationStatus() {
      return this.clusterMigrationStatus;
   }

   private void setClusterMigrationStatus(int status, String info) {
      this.clusterMigrationStatus.status = status;
      this.clusterMigrationStatus.info = info;
   }

   private Status getLocalStatusForResponse() {
      StatusImpl status = new StatusImpl();
      if (this.getLocalMigrationStatus() == ResourceGroupMigrationHandler.MigrationStatus.SUCCESS) {
         status.status = 1;
         status.info = ReplicationServicesImplBase.LOCAL_HOSTID + " successfully migrated sessions";
      } else {
         status.status = -1;
         status.info = ReplicationServicesImplBase.LOCAL_HOSTID + " failed to migrate sessions";
      }

      return status;
   }

   private void broadcastInitiatePartitionMigrationResponse(HostID sender) {
      InitiateResourceGroupMigrationResponse response = new InitiateResourceGroupMigrationResponse(sender, this.getLocalStatusForResponse());
      if (sender.isLocal()) {
         getInstance().handleResponse(sender, response);
      } else {
         try {
            ClusterService.getClusterServiceInternal().createMulticastSession((RecoverListener)null, -1).send(response);
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }

   }

   private void migratePrimarySessionsToRemoteCluster(ResourceGroupKey resourceGroup) {
      ReplicationManager replicationManager = (ReplicationManager)GlobalServiceLocator.getServiceLocator().getService(ReplicationManager.class, new Annotation[0]);
      Map primaries = replicationManager.getWroManager().getPrimaryResourceGroupMap(resourceGroup);
      if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
         ResourceGroupMigrationDebugLogger.debug("migratePrimarySessionsToRemoteCluster(" + resourceGroup + "): primaries count " + primaries.size());
      }

      Iterator roids = primaries.keySet().iterator();

      while(roids.hasNext()) {
         ROID id = (ROID)roids.next();
         synchronized(id) {
            WrappedRO wro = (WrappedRO)primaries.get(id);
            if (wro != null) {
               boolean isLocalSecondary = this.isServerInLocalCluster(wro.getOtherHost());
               if (wro.getOtherHost() != null && isLocalSecondary) {
                  replicationManager.removeSecondary(wro);
               }

               if (wro.getOtherHost() == null || isLocalSecondary) {
                  if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
                     ResourceGroupMigrationDebugLogger.debug("migratePrimarySessionsToRemoteCluster(" + resourceGroup + "): migrating wro=" + wro);
                  }

                  replicationManager.createSecondaryWithFullState(wro);
                  HostID remoteSecondary = wro.getOtherHost();
                  if (remoteSecondary != null && !this.isServerInLocalCluster(remoteSecondary)) {
                     wro.setIsMigrated(true);
                     if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
                        ResourceGroupMigrationDebugLogger.debug("migratePrimarySessionsToRemoteCluster(" + resourceGroup + "): wro=" + wro + " has been migrated to " + remoteSecondary);
                     }
                  }
               }
            }
         }
      }

   }

   void addTargetClusterAddressForPartition(String partitionName, String resourceGroupName, String targetClusterName) {
      if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
         ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationHandler.addTargetClusterAddressForPartition(" + partitionName + ", resourceGroupName=" + resourceGroupName + ", targetClusterName=" + targetClusterName + "), ctx=" + this.ctx);
      }

      if (this.ctx != null) {
         MapHolder holder = null;

         try {
            holder = (MapHolder)this.ctx.lookup(JNDI_NAME);
         } catch (NamingException var9) {
         }

         if (holder == null) {
            holder = new MapHolder(new HashMap());
         }

         Map map = holder.getMap();
         HashMap targetClusterAddressMap = (HashMap)map.get(partitionName);
         if (targetClusterAddressMap == null) {
            targetClusterAddressMap = new HashMap();
            map.put(partitionName, targetClusterAddressMap);
         }

         try {
            String targetClusterAddress = ClusterURLFactory.getInstance().parseClusterURL("cluster:t3://" + targetClusterName);
            targetClusterAddressMap.put(resourceGroupName, targetClusterAddress);
            this.ctx.rebind(JNDI_NAME, holder);
            if (ResourceGroupMigrationDebugLogger.isDebugEnabled()) {
               ResourceGroupMigrationDebugLogger.debug("ResourceGroupMigrationHandler.addTargetClusterAddressForPartition():  added [resourceGroupName=" + resourceGroupName + ", targetClusterAddress=" + targetClusterAddress + "] to map for partition " + partitionName);
            }
         } catch (MalformedURLException | NamingException var8) {
            var8.printStackTrace(System.out);
         }

      }
   }

   public String[] getTargetClusterAddressesForPartition(String partitionName) {
      if (this.ctx == null) {
         return null;
      } else {
         try {
            MapHolder holder = (MapHolder)this.ctx.lookup(JNDI_NAME);
            if (holder == null) {
               return null;
            }

            Map map = holder.getMap();
            Map targetClusterAddresses = (Map)map.get(partitionName);
            if (targetClusterAddresses != null && targetClusterAddresses.size() > 0) {
               ArrayList addresses = new ArrayList(targetClusterAddresses.values());
               return (String[])addresses.toArray(new String[addresses.size()]);
            }
         } catch (NamingException var6) {
         }

         return null;
      }
   }

   public void unbindResourceGroupMigrationMap() throws NamingException {
      Environment env = new Environment();
      Context ctx = env.getInitialContext();
      ctx.unbind(JNDI_NAME);
   }

   public void blockTillCompletion(int timeout) throws TimeoutException {
      try {
         long timeToWait = timeout < 0 ? 5000L : (long)(timeout * 1000);
         long endTime = timeout < 0 ? Long.MAX_VALUE : System.currentTimeMillis() + timeToWait;
         synchronized(notifier) {
            while(!complete && System.currentTimeMillis() < endTime) {
               try {
                  notifier.wait(timeToWait);
               } catch (InterruptedException var13) {
               }
            }
         }

         if (!complete && System.currentTimeMillis() >= endTime) {
            throw new TimeoutException("Timeout value: " + timeout + " reacahed.");
         }
      } finally {
         complete = false;
      }

   }

   private static void setLocalServers(TreeSet servers) {
      localServers = servers;
   }

   // $FF: synthetic method
   ResourceGroupMigrationHandler(Object x0) {
      this();
   }

   public static enum MigrationStatus {
      FAILURE(-1),
      NOT_STARTED(0),
      IN_PROGRESS(1),
      SUCCESS(2);

      private int value;

      private MigrationStatus(int v) {
         this.value = v;
      }
   }

   private static class SingletonMaker {
      private static final ResourceGroupMigrationHandler singleton = new ResourceGroupMigrationHandler();
   }
}
