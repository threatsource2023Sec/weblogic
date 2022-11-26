package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterHelper;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterService;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

@Service(
   name = "LocalClusterSecondarySelector"
)
@Singleton
public class LocalSecondarySelector implements ClusterMembersChangeListener, SecondarySelector {
   private static final HostID LOCAL_HOSTID = LocalServerIdentity.getIdentity();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String machineName;
   private final String preferredSecondaryGroup;
   private final HashSet serverInfos;
   protected boolean clusterHasSecondarySrvrs;
   private boolean placeSecondariesAutomatically;
   private final ArrayList preferredCandidates = new ArrayList();
   private final ArrayList remoteCandidates = new ArrayList();
   private final ArrayList localCandidates = new ArrayList();
   protected HostID currentSecondary = null;
   private final MachineServerMap localMap;
   private final ArrayList machineList = new ArrayList();

   protected LocalSecondarySelector() {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.machineName = server.getMachine() == null ? ClusterHelper.getMachineName() : server.getMachine().getName();
      this.preferredSecondaryGroup = server.getPreferredSecondaryGroup();
      String replicationGroup = server.getReplicationGroup();
      this.placeSecondariesAutomatically = this.preferredSecondaryGroup == null || replicationGroup == null;
      this.serverInfos = new HashSet();
      if (server.getCluster() != null) {
         ClusterService.getClusterServiceInternal().addClusterMembersListener(this);
      }

      this.localMap = new MachineServerMap(this.machineName);
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cmce) {
      HostID host = cmce.getClusterMemberInfo().identity();
      switch (cmce.getAction()) {
         case 0:
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ClusterLogger.logNewServerJoinedCluster(host.toString());
            }

            this.addNewServer(cmce.getClusterMemberInfo());
            break;
         case 1:
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ClusterLogger.logRemovingServerFromCluster(host.toString());
            }

            this.removeDeadServer(cmce.getClusterMemberInfo());
            ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
            ((ReplicationManager)serviceLocator.getService(ReplicationManager.class, new Annotation[0])).changeSecondary(host);
            ((AsyncReplicationManager)serviceLocator.getService(AsyncReplicationManager.class, new Annotation[0])).changeSecondary(host);
            break;
         case 2:
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ClusterLogger.logUpdatingServerInTheCluster(host.toString());
            }

            this.addNewServer(cmce.getClusterMemberInfo());
      }

   }

   public synchronized HostID getSecondarySrvr() {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("Has secondary servers? " + this.clusterHasSecondarySrvrs);
         ReplicationDetailsDebugLogger.debug("Current secondary server? " + this.currentSecondary);
      }

      if (this.clusterHasSecondarySrvrs) {
         if (this.currentSecondary != null) {
            return this.currentSecondary;
         }

         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Preferred list : " + this.preferredCandidates);
            ReplicationDetailsDebugLogger.debug("Remote list : " + this.remoteCandidates);
            ReplicationDetailsDebugLogger.debug("Local list : " + this.localCandidates);
         }

         if (this.placeSecondariesAutomatically) {
            this.currentSecondary = this.selectSecondaryAutomatically();
         } else {
            this.currentSecondary = this.selectSecondaryBasedOnConfig();
         }

         this.logNewSecondaryServer();
      }

      return this.currentSecondary;
   }

   protected void logNewSecondaryServer() {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("New secondary server is " + this.currentSecondary);
      }

   }

   private HostID selectSecondaryAutomatically() {
      int currSrvrIndex = this.localMap.getServerList().indexOf(LOCAL_HOSTID);
      int numOfMachines = this.machineList.size();
      int index;
      if (numOfMachines > 1) {
         int currentMachineIndex = this.machineList.indexOf(this.localMap);
         index = 1;

         ArrayList remoteSrvrList;
         for(remoteSrvrList = new ArrayList(); index < numOfMachines; ++index) {
            MachineServerMap msmap = (MachineServerMap)this.machineList.get((index + currentMachineIndex) % numOfMachines);
            List srvrList = msmap.getServerList();
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("localServerList size " + this.localMap.getServerList().size() + "\n machineName " + msmap.machineName + "\n remoteServerList size " + srvrList.size() + "\n currSrvrIndex " + currSrvrIndex);
            }

            if (srvrList.size() > currSrvrIndex) {
               return (HostID)srvrList.get(currSrvrIndex);
            }

            remoteSrvrList.addAll(srvrList);
         }

         return (HostID)remoteSrvrList.get(currSrvrIndex % remoteSrvrList.size());
      } else {
         List replicationList = this.localMap.getServerList();
         index = replicationList.size();
         return index == 1 ? null : (HostID)replicationList.get((currSrvrIndex + 1) % index);
      }
   }

   private HostID selectSecondaryBasedOnConfig() {
      int preferedCandidatesSize = this.preferredCandidates.size();
      int remoteCandidatesSize = this.remoteCandidates.size();
      int localCandidatesSize = this.localCandidates.size();
      HostID secondary;
      ArrayList allNonPreferedCandidates;
      if (preferedCandidatesSize > localCandidatesSize) {
         allNonPreferedCandidates = this.getCombinedCandidates(this.localCandidates, this.remoteCandidates);
         System.out.println("allNonPreferedCandidates: " + allNonPreferedCandidates);
         secondary = (HostID)this.preferredCandidates.get(allNonPreferedCandidates.indexOf(LOCAL_HOSTID) % preferedCandidatesSize);
      } else if (preferedCandidatesSize > 0) {
         secondary = (HostID)this.preferredCandidates.get(this.localCandidates.indexOf(LOCAL_HOSTID) % preferedCandidatesSize);
      } else if (remoteCandidatesSize > localCandidatesSize) {
         allNonPreferedCandidates = this.getCombinedCandidates(this.localCandidates, this.remoteCandidates);
         secondary = (HostID)this.remoteCandidates.get(allNonPreferedCandidates.indexOf(LOCAL_HOSTID) % remoteCandidatesSize);
      } else if (remoteCandidatesSize > 0) {
         secondary = (HostID)this.remoteCandidates.get(this.localCandidates.indexOf(LOCAL_HOSTID) % remoteCandidatesSize);
      } else {
         secondary = (HostID)this.localCandidates.get((this.localCandidates.indexOf(LOCAL_HOSTID) + 1) % localCandidatesSize);
      }

      return secondary;
   }

   private ArrayList getCombinedCandidates(ArrayList list1, ArrayList list2) {
      if (list2.size() == 0) {
         return list1;
      } else if (list1.size() == 0) {
         return list2;
      } else {
         TreeSet combinedSet = new TreeSet();
         combinedSet.addAll(list1);
         combinedSet.addAll(list2);
         ArrayList list = new ArrayList();
         list.addAll((Collection)combinedSet.clone());
         return list;
      }
   }

   public ArrayList getSecondaryCandidates() {
      ArrayList candidates = new ArrayList();
      if (this.placeSecondariesAutomatically) {
         Iterator iter = null;
         synchronized(this) {
            iter = ((Collection)this.machineList.clone()).iterator();
         }

         while(iter.hasNext()) {
            MachineServerMap msm = (MachineServerMap)iter.next();
            Iterator jter = msm.getServerList().iterator();

            while(jter.hasNext()) {
               candidates.add(jter.next());
            }
         }
      } else {
         candidates.addAll((Collection)this.preferredCandidates.clone());
         candidates.addAll((Collection)this.remoteCandidates.clone());
         candidates.addAll((Collection)this.localCandidates.clone());
      }

      candidates.remove(LOCAL_HOSTID);
      return candidates;
   }

   public synchronized void removeDeadSecondarySrvr(HostID hostID) {
      Iterator itr = this.serverInfos.iterator();

      while(itr.hasNext()) {
         ClusterMemberInfo info = (ClusterMemberInfo)itr.next();
         if (info.identity().equals(hostID)) {
            this.removeDeadServer(info);
            break;
         }
      }

      this.logRemoveDeadSecondarySrvr(hostID);
   }

   protected void logRemoveDeadSecondarySrvr(HostID hostID) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         if (this.clusterHasSecondarySrvrs) {
            ReplicationDebugLogger.debug("Unreachable secondary server: " + hostID + " New secondary server " + this.getSecondarySrvr());
         } else {
            ReplicationDebugLogger.debug("Unreachable secondary server: " + hostID + " and there are no secondary servers currently available to replication");
         }
      }

   }

   public synchronized void addNewServer(ClusterMemberInfo info) {
      this.serverInfos.add(info);
      if (info.replicationGroup() == null || info.preferredSecondaryGroup() == null) {
         this.placeSecondariesAutomatically = true;
      }

      this.reset();
   }

   protected synchronized void removeDeadServer(ClusterMemberInfo info) {
      this.serverInfos.remove(info);
      this.reset();
   }

   private void reset() {
      this.currentSecondary = null;
      if (this.placeSecondariesAutomatically) {
         this.recomputeSecondaryAutomatically((Collection)this.serverInfos.clone());
      } else {
         this.recomputeSecondary((Collection)this.serverInfos.clone());
      }

      this.clusterHasSecondarySrvrs = this.clusterHasSecondaryServers();
      this.logSecondaryServerReset();
   }

   protected void logSecondaryServerReset() {
      if (ReplicationDebugLogger.isDebugEnabled() && this.clusterHasSecondarySrvrs) {
         Object candidate = this.getSecondarySrvr();
         ReplicationDebugLogger.debug(" Secondary server reset to " + candidate);
      }

   }

   private boolean clusterHasSecondaryServers() {
      return this.preferredCandidates.size() > 0 || this.remoteCandidates.size() > 0 || this.localCandidates.size() > 1 || this.machineList.size() > 0 || this.localMap.getServerList().size() > 1;
   }

   private void recomputeSecondaryAutomatically(Collection collection) {
      HashMap map = new HashMap();
      this.localMap.reset();
      this.localMap.addServer(LOCAL_HOSTID);
      map.put(this.machineName, this.localMap);
      Iterator memIter = collection.iterator();

      while(memIter.hasNext()) {
         ClusterMemberInfo memInfo = (ClusterMemberInfo)memIter.next();
         MachineServerMap temp = (MachineServerMap)map.get(memInfo.machineName());
         if (temp == null) {
            temp = new MachineServerMap(memInfo.machineName());
            map.put(memInfo.machineName(), temp);
         }

         temp.addServer(memInfo.identity());
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            this.printDebug(memInfo);
         }
      }

      TreeSet sortedSet = new TreeSet();
      sortedSet.addAll(map.values());
      synchronized(this) {
         this.machineList.clear();
         this.machineList.addAll(sortedSet);
      }
   }

   private void printDebug(ClusterMemberInfo memInfo) {
      ReplicationDetailsDebugLogger.debug("**Processing " + memInfo.identity() + " : " + memInfo.serverName() + " on " + memInfo.machineName() + " in " + memInfo.replicationGroup() + " prefers " + memInfo.preferredSecondaryGroup());
   }

   private void recomputeSecondary(Collection collection) {
      TreeSet serversOnRemoteMachineInPreferredGroup = new TreeSet();
      TreeSet serversOnRemoteMachineNotInPreferredGroup = new TreeSet();
      TreeSet serversOnLocalMachineInPreferredGroup = new TreeSet();
      TreeSet serversOnLocalMachineNotInPreferredGroup = new TreeSet();
      serversOnLocalMachineNotInPreferredGroup.add(LOCAL_HOSTID);
      Iterator memIter = collection.iterator();

      while(memIter.hasNext()) {
         ClusterMemberInfo memInfo = (ClusterMemberInfo)memIter.next();
         if (this.preferredSecondaryGroup.equals(memInfo.replicationGroup())) {
            if (this.isServerOnSameMachine(memInfo)) {
               serversOnLocalMachineInPreferredGroup.add(memInfo.identity());
            } else {
               serversOnRemoteMachineInPreferredGroup.add(memInfo.identity());
            }
         } else if (this.isServerOnSameMachine(memInfo)) {
            serversOnLocalMachineNotInPreferredGroup.add(memInfo.identity());
         } else {
            serversOnRemoteMachineNotInPreferredGroup.add(memInfo.identity());
         }

         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            this.printDebug(memInfo);
         }
      }

      this.preferredCandidates.clear();
      this.remoteCandidates.clear();
      this.localCandidates.clear();
      this.preferredCandidates.addAll(serversOnRemoteMachineInPreferredGroup);
      if (this.preferredCandidates.isEmpty()) {
         this.preferredCandidates.addAll(serversOnLocalMachineInPreferredGroup);
      }

      this.remoteCandidates.addAll(serversOnRemoteMachineNotInPreferredGroup);
      this.localCandidates.addAll(serversOnLocalMachineNotInPreferredGroup);
   }

   private boolean isServerOnSameMachine(ClusterMemberInfo info) {
      return this.machineName.equals(info.machineName());
   }

   private static class MachineServerMap implements Comparable {
      private final TreeSet set;
      private final String machineName;

      private MachineServerMap(String machineName) {
         this.set = new TreeSet();
         this.machineName = machineName;
      }

      public synchronized void addServer(HostID hostID) {
         this.set.add(hostID);
      }

      public void reset() {
         this.set.clear();
      }

      public synchronized List getServerList() {
         ArrayList result = new ArrayList();
         result.addAll(this.set);
         return result;
      }

      public boolean equals(Object object) {
         if (object instanceof MachineServerMap) {
            MachineServerMap other = (MachineServerMap)object;
            return this.machineName.equals(other.machineName);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.machineName.hashCode();
      }

      public int compareTo(Object object) {
         try {
            MachineServerMap other = (MachineServerMap)object;
            return this.machineName.compareTo(other.machineName);
         } catch (ClassCastException var3) {
            throw new AssertionError("Unexpected exception" + var3);
         }
      }

      // $FF: synthetic method
      MachineServerMap(String x0, Object x1) {
         this(x0);
      }
   }
}
