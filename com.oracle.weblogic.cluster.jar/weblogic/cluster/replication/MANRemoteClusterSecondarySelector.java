package weblogic.cluster.replication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterService;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.RemoteClusterMemberManager;
import weblogic.cluster.RemoteClusterMembersChangeListener;
import weblogic.cluster.RemoteClusterMemberManager.Locator;
import weblogic.cluster.replication.SecondarySelector.Locator.SelectorPolicy;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service(
   name = "RemoteMANClusterSecondarySelector"
)
@Singleton
public class MANRemoteClusterSecondarySelector implements ClusterMembersChangeListener, RemoteClusterMembersChangeListener, SecondarySelector {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final HostID LOCAL_HOSTID = LocalServerIdentity.getIdentity();
   private final Map hostIDToRepChannelMap = new ConcurrentHashMap(11);
   private final WorkManager workManager = WorkManagerFactory.getInstance().getSystem();
   private final HashSet remoteServerInfos = new HashSet();
   private final HashSet localServerInfos = new HashSet();
   private HostID secondaryHostID;
   @Inject
   private ReplicationServicesFactoryImpl replicationServicesFactoryImpl;

   @PostConstruct
   public void postConstruct() {
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (cluster != null) {
         ClusterServices cs = ClusterService.getClusterServiceInternal();
         cs.addClusterMembersListener(this);
         this.localServerInfos.add(cs.getLocalMember());
      }

      RemoteClusterMemberManager memberManager = Locator.locateRemoteSiteManager();
      memberManager.addRemoteClusterMemberListener(this);
   }

   @PreDestroy
   public void preDestroy() {
      RemoteClusterMemberManager memberManager = Locator.locateRemoteSiteManager();
      memberManager.removeRemoteClusterMemberListener(this);
   }

   public synchronized void clusterMembersChanged(ClusterMembersChangeEvent cmce) {
      this.secondaryHostID = null;
      HostID host = cmce.getClusterMemberInfo().identity();
      switch (cmce.getAction()) {
         case 0:
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ClusterLogger.logNewServerJoinedCluster(host.toString());
            }

            this.addNewLocalClusterServer(cmce.getClusterMemberInfo());
            break;
         case 1:
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ClusterLogger.logRemovingServerFromCluster(host.toString());
            }

            this.localServerInfos.remove(cmce.getClusterMemberInfo());
            if (!ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
               HostID[] hostArray = new HostID[]{host};
               this.replicationServicesFactoryImpl.getMANSyncReplicationManager().changeSecondary(hostArray);
               this.replicationServicesFactoryImpl.getMANAsyncReplicationManager().changeSecondary(hostArray);
            }
            break;
         case 2:
            this.addNewLocalClusterServer(cmce.getClusterMemberInfo());
      }

   }

   private void addNewLocalClusterServer(ClusterMemberInfo info) {
      this.localServerInfos.add(info);
      this.hostIDToRepChannelMap.put(info.identity(), info.replicationChannel());
   }

   public synchronized void remoteClusterMembersChanged(ArrayList list) {
      this.secondaryHostID = null;
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("Received new cluster list from remote cluster " + list);
      }

      this.remoteServerInfos.clear();
      this.hostIDToRepChannelMap.clear();
      int size = list.size();
      if (size > 0) {
         this.remoteServerInfos.addAll(list);
      }

      ClusterMemberInfo info;
      for(int i = 0; i < size; ++i) {
         info = (ClusterMemberInfo)list.get(i);
         this.hostIDToRepChannelMap.put(info.identity(), info.replicationChannel());
      }

      Iterator itr = this.localServerInfos.iterator();

      while(itr.hasNext()) {
         info = (ClusterMemberInfo)itr.next();
         this.hostIDToRepChannelMap.put(info.identity(), info.replicationChannel());
      }

      if (this.canReplicateToRemoteCluster()) {
         HashSet clonedSet = (HashSet)this.localServerInfos.clone();
         Iterator iterator = clonedSet.iterator();
         ArrayList localClusterList = new ArrayList(clonedSet.size());

         while(iterator.hasNext()) {
            ClusterMemberInfo info = (ClusterMemberInfo)iterator.next();
            if (!info.identity().equals(LOCAL_HOSTID)) {
               localClusterList.add(info.identity());
            }
         }

         HostID[] hostids = new HostID[localClusterList.size()];
         localClusterList.toArray(hostids);
         this.replicationServicesFactoryImpl.getMANSyncReplicationManager().changeSecondary(hostids);
         this.replicationServicesFactoryImpl.getMANAsyncReplicationManager().changeSecondary(hostids);
      }

   }

   public HostID getSecondarySrvr() {
      if (this.secondaryHostID != null) {
         return this.secondaryHostID;
      } else {
         synchronized(this) {
            if (this.canReplicateToRemoteCluster()) {
               this.secondaryHostID = this.selectSecondaryFromRemoteCluster();
            } else if (this.canReplicateToLocalCluster()) {
               this.secondaryHostID = this.selectSecondaryBasedOnLoad();
            } else {
               this.secondaryHostID = null;
            }
         }

         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("New Secondary server " + this.secondaryHostID);
         }

         return this.secondaryHostID;
      }
   }

   synchronized boolean canReplicateToRemoteCluster() {
      int localClusterSize = this.localServerInfos.size();
      int remoteClusterSize = this.remoteServerInfos.size();
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("RemoteClusterSecondarySelector.canReplicateToRemoteCluster(): Local: " + localClusterSize + " Remote: " + remoteClusterSize + " canReplicateToRemote " + (localClusterSize <= 2 * remoteClusterSize ? "TRUE" : "FALSE"));
      }

      return localClusterSize <= 2 * remoteClusterSize;
   }

   synchronized String getReplicationChannelFor(HostID host) {
      return (String)this.hostIDToRepChannelMap.get(host);
   }

   private boolean canReplicateToLocalCluster() {
      int localClusterSize = this.localServerInfos.size();
      int remoteClusterSize = this.remoteServerInfos.size();
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("RemoteClusterSecondarySelector.canReplicateToRemoteCluster(): Local: " + localClusterSize + " Remote: " + remoteClusterSize + " canReplicateToRemote " + (localClusterSize + remoteClusterSize >= 2 ? "TRUE" : "FALSE"));
      }

      return localClusterSize + remoteClusterSize >= 2;
   }

   public ArrayList getSecondaryCandidates() {
      ArrayList result = new ArrayList();
      Iterator temp;
      synchronized(this) {
         temp = ((HashSet)this.remoteServerInfos.clone()).iterator();
      }

      while(temp.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)temp.next();
         result.add(info.identity());
      }

      SecondarySelector localSelector = weblogic.cluster.replication.SecondarySelector.Locator.locate(SelectorPolicy.LOCAL);
      result.addAll(localSelector.getSecondaryCandidates());
      return result;
   }

   String[] getActiveServersInRemoteCluster() {
      ArrayList result = new ArrayList();
      Iterator temp;
      synchronized(this) {
         temp = ((HashSet)this.remoteServerInfos.clone()).iterator();
      }

      while(temp.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)temp.next();
         result.add(info.serverName());
      }

      String[] activeServers = new String[result.size()];
      result.toArray(activeServers);
      return activeServers;
   }

   private HostID selectSecondaryFromRemoteCluster() {
      TreeSet remoteClusterSet = new TreeSet();
      TreeSet localClusterSet = new TreeSet();
      Iterator i = ((HashSet)this.remoteServerInfos.clone()).iterator();

      ClusterMemberInfo info;
      while(i.hasNext()) {
         info = (ClusterMemberInfo)i.next();
         remoteClusterSet.add(info.identity());
      }

      i = ((HashSet)this.localServerInfos.clone()).iterator();

      while(i.hasNext()) {
         info = (ClusterMemberInfo)i.next();
         localClusterSet.add(info.identity());
      }

      int remoteClusterSize = remoteClusterSet.size();
      ArrayList remoteClusterList = new ArrayList(remoteClusterSet);
      ArrayList localClusterList = new ArrayList(localClusterSet);
      int index = localClusterList.indexOf(LOCAL_HOSTID);
      return (HostID)remoteClusterList.get(index % remoteClusterSize);
   }

   private HostID selectSecondaryBasedOnLoad() {
      TreeSet remoteMachineSet = new TreeSet();
      TreeSet localMachineSet = new TreeSet();
      Iterator i = ((HashSet)this.remoteServerInfos.clone()).iterator();

      ClusterMemberInfo info;
      while(i.hasNext()) {
         info = (ClusterMemberInfo)i.next();
         remoteMachineSet.add(info.identity());
      }

      i = ((HashSet)this.localServerInfos.clone()).iterator();

      while(i.hasNext()) {
         info = (ClusterMemberInfo)i.next();
         if (this.isServerOnSameMachine(info)) {
            localMachineSet.add(info.identity());
         } else {
            remoteMachineSet.add(info.identity());
         }
      }

      int remoteMachineSize = remoteMachineSet.size();
      int localMachineSize = localMachineSet.size();
      ArrayList remoteMachineList = new ArrayList(remoteMachineSet);
      ArrayList localMachineList = new ArrayList(localMachineSet);
      int index = localMachineList.indexOf(LOCAL_HOSTID);
      if (remoteMachineSize == 0) {
         return (HostID)localMachineList.get((index + 1) % localMachineSize);
      } else {
         return (HostID)remoteMachineList.get(index % remoteMachineSize);
      }
   }

   private boolean isServerOnSameMachine(ClusterMemberInfo info) {
      try {
         new ServerSocket(0, 0, InetAddress.getByName(info.hostAddress()));
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public synchronized void removeDeadSecondarySrvr(HostID hostID) {
      if (hostID.equals(this.secondaryHostID)) {
         this.secondaryHostID = null;
      }

      boolean found = false;
      Iterator i = this.remoteServerInfos.iterator();

      ClusterMemberInfo info;
      while(i.hasNext()) {
         info = (ClusterMemberInfo)i.next();
         if (info.identity().equals(hostID)) {
            i.remove();
            found = true;
            break;
         }
      }

      if (!found) {
         i = this.localServerInfos.iterator();

         while(i.hasNext()) {
            info = (ClusterMemberInfo)i.next();
            if (info.identity().equals(hostID)) {
               i.remove();
               break;
            }
         }
      }

      this.hostIDToRepChannelMap.remove(hostID);
   }

   public void addNewServer(ClusterMemberInfo info) {
      this.addNewLocalClusterServer(info);
   }
}
