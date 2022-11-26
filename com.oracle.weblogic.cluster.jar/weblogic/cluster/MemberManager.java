package weblogic.cluster;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.cluster.singleton.MigratableServerService;
import weblogic.management.provider.ManagementService;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class MemberManager implements PeerGoneListener, MulticastSessionIDConstants, RecoverListener, NakedTimerListener {
   private final int idlePeriodsUntilTimeout;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SECRET_STRING;
   private static final byte[] SERVER_HASH_VALUE;
   private ConcurrentHashMap _hostID2PartitionStatusChecksum = new ConcurrentHashMap();
   private static MemberManager theMemberManager;
   private HashMap remoteMembers = new HashMap();
   private long joinTime;
   private ArrayList clusterMembersListeners;
   private boolean waitingForFirstHeartbeats = true;
   private String clusterName;
   private HashSet clusterMembersPartitionChangeListeners;
   private MulticastSession runtimeStateSender;
   private final TimerManager timerManager;

   public static MemberManager theOne() {
      return theMemberManager;
   }

   void handleMulticastSessionsStatusMessage(HostID hostID, MulticastSessionsStatusMessage message) {
      RemoteMemberInfo info = (RemoteMemberInfo)this.remoteMembers.get(hostID);
      if (info != null) {
         info.handleMulticastSessionsStatusMessage(message);
      }

   }

   void handleMulticastSessionStartedMessage(HostID hostID, MulticastSessionStartedMessage message) {
      RemoteMemberInfo info = (RemoteMemberInfo)this.remoteMembers.get(hostID);
      if (info != null && info.getAttributes() != null) {
         info.handleMulticastSessionStartedMessage(message);
      }

   }

   void handleMulticastSessionStoppedMessage(HostID hostID, MulticastSessionStoppedMessage message) {
      RemoteMemberInfo info = (RemoteMemberInfo)this.remoteMembers.get(hostID);
      if (info != null && info.getAttributes() != null) {
         info.handleMulticastSessionStoppedMessage(message);
      }

   }

   static void initialize(long joinTime, int idlePeriodsUntilTimeout) {
      Debug.assertion(theMemberManager == null, "intialize only once");
      theMemberManager = new MemberManager(joinTime, idlePeriodsUntilTimeout);
      ClusterDropoutListener.initialize();
      theMemberManager.startHeartbeatChecker();
   }

   private MemberManager(long joinTime, int idlePeriodsUntilTimeout) {
      this.joinTime = joinTime;
      this.clusterMembersListeners = new ArrayList();
      this.clusterMembersPartitionChangeListeners = new HashSet();
      this.clusterName = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getName();
      this.idlePeriodsUntilTimeout = idlePeriodsUntilTimeout;
      this.runtimeStateSender = ClusterMessagesManager.theOne().createSender(MEMBER_MANAGER_ID, this, 1, true, true);
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("HeartbeatChecker", WorkManagerFactory.getInstance().getSystem());
   }

   int getIdlePeriodsUntilTimeout() {
      return this.idlePeriodsUntilTimeout;
   }

   synchronized void waitToSyncWithCurrentMembers() {
      long timeout = this.getMemberWarmupTimeout();
      long defaultDiscoveryTimeout = (long)(this.idlePeriodsUntilTimeout * 10000);
      if (timeout > defaultDiscoveryTimeout) {
         this.waitForDiscovery(defaultDiscoveryTimeout);
         this.waitForSync();
      } else {
         if (timeout > 0L) {
            this.waitForDiscovery(timeout);
         }

         this.waitForSync();
      }

   }

   private long getMemberWarmupTimeout() {
      long timeout = (long)(ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getMemberWarmupTimeoutSeconds() * 1000);
      String strTimeout = System.getProperty("weblogic.cluster.MemberWarmupTimeoutSeconds");
      if (strTimeout != null) {
         try {
            timeout = Long.parseLong(strTimeout.trim()) * 1000L;
         } catch (NumberFormatException var5) {
         }
      }

      return timeout;
   }

   private void waitForDiscovery(long timeout) {
      if (timeout > 0L) {
         ClusterLogger.logStartWarmup(ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getName());
      }

      long timeElapsed = 0L;

      for(long startTime = System.currentTimeMillis(); this.waitingForFirstHeartbeats && timeElapsed < timeout; timeElapsed = System.currentTimeMillis() - startTime) {
         try {
            this.wait(timeout - timeElapsed);
         } catch (InterruptedException var8) {
         }
      }

      this.waitingForFirstHeartbeats = false;
   }

   private void waitForSync() {
      HashSet waitingMembers;
      synchronized(this.remoteMembers) {
         waitingMembers = new HashSet(this.remoteMembers.values());
      }

      Iterator it = waitingMembers.iterator();
      MemberAttributes firstClusterMember = null;

      while(it.hasNext()) {
         RemoteMemberInfo m = (RemoteMemberInfo)it.next();
         MemberAttributes attr = m.getAttributes();
         if (attr == null) {
            it.remove();
         } else if (m.isRunning()) {
            firstClusterMember = attr;
            break;
         }
      }

      if (firstClusterMember != null) {
         this.getJNDIStateDump(firstClusterMember);
      }

   }

   private void getJNDIStateDump(MemberAttributes firstClusterMember) {
      ClusterService.getClusterServiceInternal().getClusterJNDIStateDump(firstClusterMember, ANNOUNCEMENT_MANAGER_ID);
   }

   void shutdown() {
      synchronized(this.remoteMembers) {
         Iterator memIter = this.remoteMembers.values().iterator();

         while(true) {
            if (!memIter.hasNext()) {
               this.remoteMembers.clear();
               break;
            }

            RemoteMemberInfo remoteMember = (RemoteMemberInfo)memIter.next();
            remoteMember.shutdown();
         }
      }

      synchronized(this) {
         this.clusterMembersListeners.clear();
      }

      this.stopHeartbeatChecker();
   }

   RemoteMemberInfo findOrCreate(HostID memID) {
      synchronized(this.remoteMembers) {
         RemoteMemberInfo remoteMember = (RemoteMemberInfo)this.remoteMembers.get(memID);
         if (remoteMember == null) {
            remoteMember = new RemoteMemberInfo(memID, this.joinTime);
            this.remoteMembers.put(memID, remoteMember);
            remoteMember.numUnprocessedMessages = 1;
         } else {
            ++remoteMember.numUnprocessedMessages;
         }

         return remoteMember;
      }
   }

   List getRemoteMemberInfos() {
      List list = new ArrayList();
      synchronized(this.remoteMembers) {
         Iterator it = this.remoteMembers.values().iterator();

         while(it.hasNext()) {
            Object rInf = it.next();
            if (rInf instanceof RemoteMemberInfo) {
               list.add((RemoteMemberInfo)rInf);
            }
         }

         return list;
      }
   }

   synchronized void done(RemoteMemberInfo remoteMember) {
      --remoteMember.numUnprocessedMessages;
      if (this.waitingForFirstHeartbeats && remoteMember.numUnprocessedMessages == 0 && remoteMember.getAttributes() != null && remoteMember.isRunning()) {
         this.waitingForFirstHeartbeats = false;
         this.notify();
      }

   }

   public void resetTimeout(HostID memID) {
      RemoteMemberInfo remoteMember = this.findOrCreate(memID);

      try {
         remoteMember.resetTimeout();
      } finally {
         this.done(remoteMember);
      }

   }

   void startHeartbeatChecker() {
      if (this.timerManager.isSuspended()) {
         this.timerManager.resume();
      } else {
         this.timerManager.scheduleAtFixedRate(this, 0L, 10000L);
      }

   }

   void stopHeartbeatChecker() {
      if (!this.timerManager.isSuspended()) {
         try {
            this.timerManager.suspend();
         } catch (IllegalStateException var2) {
         }
      }

   }

   public void timerExpired(Timer timer) {
      this.checkTimeouts();
   }

   void checkTimeouts() {
      synchronized(this.remoteMembers) {
         if (!this.remoteMembers.isEmpty()) {
            Iterator memIter = this.remoteMembers.values().iterator();

            while(memIter.hasNext()) {
               RemoteMemberInfo remoteMember = (RemoteMemberInfo)memIter.next();
               boolean b = remoteMember.hasTimedout();
               if (remoteMember.numUnprocessedMessages == 0 && b) {
                  MemberAttributes remoteMemberAttributes = remoteMember.getAttributes();
                  if (remoteMemberAttributes != null) {
                     ClusterLogger.logRemovingServerDueToTimeout(remoteMemberAttributes.serverName(), remoteMemberAttributes.clusterName(), remoteMemberAttributes.identity().toString());
                  }

                  memIter.remove();
                  remoteMember.shutdown();
               }
            }

         }
      }
   }

   public void peerGone(final PeerGoneEvent ev) {
      if (!ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getClusterMessagingMode().equals("unicast")) {
         synchronized(this.remoteMembers) {
            final RemoteMemberInfo remoteMember = (RemoteMemberInfo)this.remoteMembers.get(ev.getID());
            if (remoteMember != null) {
               if (remoteMember.numUnprocessedMessages == 0) {
                  if (remoteMember.getAttributes() != null) {
                     ClusterLogger.logRemovingServerDueToPeerGone(remoteMember.getAttributes().toString());
                  }

                  final Map rremoteMembers = this.remoteMembers;
                  SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
                     public Object run() {
                        rremoteMembers.remove(ev.getID());
                        remoteMember.shutdown();
                        return null;
                     }
                  });
               } else {
                  remoteMember.forceTimeout();
               }
            }

         }
      }
   }

   void shutdown(final HostID hostID) {
      synchronized(this.remoteMembers) {
         final RemoteMemberInfo info = (RemoteMemberInfo)this.remoteMembers.get(hostID);
         if (info == null || info.getAttributes() == null) {
            return;
         }

         ClusterLogger.logServerSuspended(info.getAttributes().serverName());
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               MemberManager.this.remoteMembers.remove(hostID);
               info.shutdown();
               return null;
            }
         });
      }

      if (ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isClusterMaster()) {
         MigratableServerService.theOne().notifyServerShutdown(hostID.getServerName());
      }

   }

   Collection getRemoteMembers() {
      return this.getRemoteMembers(false);
   }

   Collection getRemoteMembers(boolean includeSuspendedServers) {
      ArrayList clusterMembers = new ArrayList();
      HashMap remMems;
      synchronized(this.remoteMembers) {
         remMems = (HashMap)this.remoteMembers.clone();
      }

      Iterator var4 = remMems.values().iterator();

      while(true) {
         RemoteMemberInfo remoteMember;
         MemberAttributes attributes;
         do {
            while(true) {
               do {
                  if (!var4.hasNext()) {
                     return clusterMembers;
                  }

                  remoteMember = (RemoteMemberInfo)var4.next();
                  attributes = remoteMember.getAttributes();
               } while(attributes == null);

               if (includeSuspendedServers) {
                  break;
               }

               if (remoteMember.isRunning()) {
                  clusterMembers.add(attributes);
               }
            }
         } while(!remoteMember.isRunning() && !remoteMember.isSuspended());

         clusterMembers.add(attributes);
      }
   }

   Collection getRemoteMembersWithActivePartition(MulticastSessionId sessionId) {
      ArrayList clusterMembers = new ArrayList();
      HashMap remMems;
      synchronized(this.remoteMembers) {
         remMems = (HashMap)this.remoteMembers.clone();
      }

      Iterator var4 = remMems.values().iterator();

      while(var4.hasNext()) {
         RemoteMemberInfo remoteMember = (RemoteMemberInfo)var4.next();
         MemberAttributes attributes = remoteMember.getAttributes();
         if (remoteMember.isRunning() && attributes != null && remoteMember.isMulticastSessionActive(sessionId)) {
            clusterMembers.add(attributes);
         }
      }

      return clusterMembers;
   }

   Collection getRemoteMembersWithActivePartition(String partitionId) {
      ArrayList clusterMembers = new ArrayList();
      HashMap remMems;
      synchronized(this.remoteMembers) {
         remMems = (HashMap)this.remoteMembers.clone();
      }

      Iterator var4 = remMems.values().iterator();

      while(var4.hasNext()) {
         RemoteMemberInfo remoteMember = (RemoteMemberInfo)var4.next();
         MemberAttributes attributes = remoteMember.getAttributes();
         if (remoteMember.isRunning() && attributes != null && remoteMember.isPartitionActive(partitionId)) {
            clusterMembers.add(attributes);
         }
      }

      return clusterMembers;
   }

   Collection getClusterMembersWithActivePartition(String partitionId) {
      Collection clusterMembers = this.getRemoteMembersWithActivePartition(partitionId);
      if (PartitionAwareSenderManager.theOne().isKnownToBeActive(partitionId)) {
         clusterMembers.add(ClusterService.getClusterServiceInternal().getLocalMember());
      }

      return clusterMembers;
   }

   void fireClusterMembersChangeEvent(ClusterMemberInfo member, int action) {
      synchronized(this.clusterMembersListeners) {
         final ClusterMembersChangeEvent cmce = new ClusterMembersChangeEvent(this, action, member);
         Iterator listenerIter = this.clusterMembersListeners.iterator();

         while(listenerIter.hasNext()) {
            final ClusterMembersChangeListener cmcl = (ClusterMembersChangeListener)listenerIter.next();
            Work work = new WorkAdapter() {
               private ClusterMembersChangeEvent event = cmce;
               private ClusterMembersChangeListener listener = cmcl;

               public void run() {
                  this.listener.clusterMembersChanged(this.event);
               }

               public String toString() {
                  return "Cluster Members Changed";
               }
            };
            WorkManagerFactory.getInstance().getSystem().schedule(work);
         }

      }
   }

   void addClusterMembersListener(ClusterMembersChangeListener propertyChangeListener) {
      synchronized(this.clusterMembersListeners) {
         this.clusterMembersListeners.add(propertyChangeListener);
      }
   }

   void removeClusterMembersListener(ClusterMembersChangeListener propertyChangeListener) {
      synchronized(this.clusterMembersListeners) {
         int i = this.clusterMembersListeners.indexOf(propertyChangeListener);
         if (i > -1) {
            this.clusterMembersListeners.remove(i);
         }

      }
   }

   void fireClusterMembersPartitionChangeEvent(ClusterMemberInfo member, int action, String partitionId) {
      this.logPartitionChangeEvent(member, action, partitionId);
      synchronized(this.clusterMembersPartitionChangeListeners) {
         final ClusterMembersPartitionChangeEvent cmce = new ClusterMembersPartitionChangeEvent(this, action, member, partitionId);
         Iterator var6 = this.clusterMembersPartitionChangeListeners.iterator();

         while(var6.hasNext()) {
            final ClusterMembersPartitionChangeListener cmcl = (ClusterMembersPartitionChangeListener)var6.next();
            Work work = new WorkAdapter() {
               private ClusterMembersPartitionChangeEvent event = cmce;
               private ClusterMembersPartitionChangeListener listener = cmcl;

               public void run() {
                  this.listener.clusterMembersPartitionChanged(this.event);
               }

               public String toString() {
                  return "Cluster Members Partition Changed";
               }
            };
            WorkManagerFactory.getInstance().getSystem().schedule(work);
         }

      }
   }

   private void logPartitionChangeEvent(ClusterMemberInfo member, int action, String partitionId) {
      String partitionName = ClusterHelper.getPartitionNameFromPartitionId(partitionId);
      String strAction = action == 0 ? "Added" : "Removed";
      ClusterExtensionLogger.logPartitionChangeEvent(partitionName, member.serverName(), strAction, member.clusterName());
   }

   void addClusterMembersPartitionListener(ClusterMembersPartitionChangeListener partitionChangeListener) {
      synchronized(this.clusterMembersPartitionChangeListeners) {
         this.clusterMembersPartitionChangeListeners.add(partitionChangeListener);
      }
   }

   void removeClusterMembersPartitionListener(ClusterMembersPartitionChangeListener partitionChangeListener) {
      synchronized(this.clusterMembersPartitionChangeListeners) {
         this.clusterMembersPartitionChangeListeners.remove(partitionChangeListener);
      }
   }

   public void sendMemberRuntimeState() throws IOException {
      GroupMessage mesg = this.createRecoverMessage();
      this.runtimeStateSender.send(mesg);
   }

   public synchronized void updateMemberRuntimeState(HostID memberID, int srvrRuntimeState, long currentSeqNum) {
      RemoteMemberInfo memberInfo = this.findOrCreate(memberID);

      try {
         memberInfo.updateRuntimeState(srvrRuntimeState);
         ClusterMessageReceiver receiver = memberInfo.findOrCreateReceiver(MEMBER_MANAGER_ID, true);
         if (currentSeqNum >= 0L) {
            receiver.setInSync(currentSeqNum);
         }
      } finally {
         this.done(memberInfo);
      }

   }

   public synchronized GroupMessage createRecoverMessage() {
      int srvrState = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getStableState();
      long currentSeqNum = ClusterMessagesManager.theOne().findSender(MEMBER_MANAGER_ID).getCurrentSeqNum();
      RuntimeStateMessage message = new RuntimeStateMessage(srvrState, AttributeManager.theOne().getLocalAttributes(), currentSeqNum);
      return message;
   }

   void dumpDiagnosticImageData(XMLStreamWriter xsw) throws XMLStreamException, IOException {
      xsw.writeStartElement("MemberManager");
      xsw.writeAttribute("clusterName", this.clusterName);
      Iterator var2 = this.getRemoteMemberInfos().iterator();

      while(var2.hasNext()) {
         RemoteMemberInfo rmInfo = (RemoteMemberInfo)var2.next();
         rmInfo.dumpDiagnosticImageData(xsw);
      }

      xsw.writeEndElement();
   }

   static {
      SECRET_STRING = "?PeerInfo=" + ClusterHelper.STRINGFIED_PEERINFO + "&ServerName=" + ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      SERVER_HASH_VALUE = ClusterService.getClusterServiceInternal().getSecureHash();
      theMemberManager = null;
   }
}
