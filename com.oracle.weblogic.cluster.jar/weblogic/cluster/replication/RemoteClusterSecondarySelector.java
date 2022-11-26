package weblogic.cluster.replication;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.cluster.RemoteClusterMemberManager;
import weblogic.cluster.RemoteClusterMembersChangeListener;
import weblogic.cluster.RemoteClusterMemberManager.Locator;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class RemoteClusterSecondarySelector implements RemoteClusterMembersChangeListener, SecondarySelector {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final HostID LOCAL_HOSTID = LocalServerIdentity.getIdentity();
   private final Map hostIDToRepChannelMap = new ConcurrentHashMap(11);
   private final WorkManager workManager = WorkManagerFactory.getInstance().getSystem();
   private final HashSet remoteServerInfos = new HashSet();
   private HostID secondaryHostID;
   private TreeSet localServers;

   RemoteClusterSecondarySelector(String remoteClusterUrl) {
      RemoteClusterMemberManager remoteClusterMemberManager = Locator.locateLocalSiteManager();
      remoteClusterMemberManager.setRemoteClusterURL(remoteClusterUrl);
      remoteClusterMemberManager.addRemoteClusterMemberListener(this);
      remoteClusterMemberManager.start();
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

      for(int i = 0; i < size; ++i) {
         ClusterMemberInfo info = (ClusterMemberInfo)list.get(i);
         this.hostIDToRepChannelMap.put(info.identity(), info.replicationChannel());
      }

   }

   public HostID getSecondarySrvr() {
      if (this.secondaryHostID != null) {
         return this.secondaryHostID;
      } else {
         synchronized(this) {
            this.secondaryHostID = this.selectSecondaryFromRemoteCluster();
         }

         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("New Secondary server " + this.secondaryHostID);
         }

         return this.secondaryHostID;
      }
   }

   synchronized String getReplicationChannelFor(HostID host) {
      return (String)this.hostIDToRepChannelMap.get(host);
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

      while(i.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)i.next();
         remoteClusterSet.add(info.identity());
      }

      this.getLocalClusterMembers(localClusterSet);
      this.localServers = localClusterSet;
      int remoteClusterSize = remoteClusterSet.size();
      if (remoteClusterSize == 0) {
         return null;
      } else {
         ArrayList remoteClusterList = new ArrayList(remoteClusterSet);
         ArrayList localClusterList = new ArrayList(localClusterSet);
         int index = localClusterList.indexOf(LOCAL_HOSTID);
         return (HostID)remoteClusterList.get(index % remoteClusterSize);
      }
   }

   void getLocalClusterMembers(TreeSet localClusterSet) {
      Iterator i = ClusterService.getServices().getRemoteMembers().iterator();
      localClusterSet.add(LOCAL_HOSTID);

      while(i.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)i.next();
         localClusterSet.add(info.identity());
      }

   }

   public synchronized void removeDeadSecondarySrvr(HostID hostID) {
      if (hostID.equals(this.secondaryHostID)) {
         this.secondaryHostID = null;
      }

      boolean found = false;
      Iterator i = this.remoteServerInfos.iterator();

      while(i.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)i.next();
         if (info.identity().equals(hostID)) {
            i.remove();
            found = true;
            break;
         }
      }

      this.hostIDToRepChannelMap.remove(hostID);
   }

   public void addNewServer(ClusterMemberInfo info) {
   }

   public TreeSet getLocalServers() {
      if (this.localServers == null) {
         this.localServers = new TreeSet();
         this.getLocalClusterMembers(this.localServers);
      }

      return this.localServers;
   }
}
