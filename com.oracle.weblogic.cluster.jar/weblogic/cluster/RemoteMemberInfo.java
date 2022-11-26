package weblogic.cluster;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.internal.VersionInfo;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

final class RemoteMemberInfo {
   private JVMID memberID;
   private MemberAttributes attributes;
   private HashMap receivers = new HashMap();
   private int checksLeft;
   int numUnprocessedMessages;
   private RJVM rjvm;
   private long localJoinTime;
   private int srvrRuntimeState = 9;
   private final AtomicInteger previousStatusChangeNumber = new AtomicInteger(-2);
   private final Set activeMulticastSessionIds = new HashSet();
   private final Set inactiveMulticastSessionIds = new HashSet();
   private static final Set alwaysActiveMulticastSessionIds = new HashSet();
   private static final AuthenticatedSubject kernelId;

   RemoteMemberInfo(HostID memberID, long localJoinTime) {
      this.memberID = (JVMID)memberID;
      this.attributes = null;
      this.checksLeft = MemberManager.theOne().getIdlePeriodsUntilTimeout();
      this.rjvm = RJVMManager.getRJVMManager().find(this.memberID);
      if (this.rjvm != null) {
         this.rjvm.addPeerGoneListener(MemberManager.theOne());
      }

      this.localJoinTime = localJoinTime;
   }

   MemberAttributes getAttributes() {
      return this.attributes;
   }

   RJVM getRJVM() {
      if (this.rjvm == null) {
         this.rjvm = RJVMManager.getRJVMManager().find(this.memberID);
         if (this.rjvm != null) {
            this.rjvm.addPeerGoneListener(MemberManager.theOne());
         }
      }

      return this.rjvm;
   }

   void resetTimeout() {
      this.checksLeft = MemberManager.theOne().getIdlePeriodsUntilTimeout();
      RJVM theRJVM = this.getRJVM();
      if (theRJVM != null) {
         this.rjvm.messageReceived();
      }

      AlternateLivelinessChecker.getInstance().reachable(this.memberID);
   }

   boolean hasTimedout() {
      if (this.checksLeft > 0) {
         --this.checksLeft;
         return false;
      } else {
         ClusterMessageReceiver receiver = this.findOrCreateReceiver(ClusterMessagesManager.ANNOUNCEMENT_MANAGER_ID, true);
         return receiver == null ? true : AlternateLivelinessChecker.getInstance().isUnreachable(receiver.getCurrentSeqNum(), this.memberID);
      }
   }

   MemberServices getMemberServices(MulticastSessionId multicastSessionId) {
      return this.findOrCreateMulticastSessionReceiver(multicastSessionId).getMemberServices();
   }

   void forceTimeout() {
      this.checksLeft = 0;
   }

   void shutdown() {
      this.updateRuntimeState(9);
   }

   void handleMulticastSessionsStatus(MulticastSessionsStatus status) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug(new Date() + " RemoteMemberInfo.handleMulticastSessionStatus Received MulticastSessionStatus; previousStatusChangeNumber: " + this.previousStatusChangeNumber.get() + ", status.getStatusChangeNumber: " + status.getStatusChangeNumber());
      }

      if (this.previousStatusChangeNumber.get() < status.getStatusChangeNumber()) {
         synchronized(this.previousStatusChangeNumber) {
            if (this.previousStatusChangeNumber.get() < status.getStatusChangeNumber()) {
               if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                  ClusterAnnouncementsDebugLogger.debug(new Date() + " RemoteMemberInfo.handleMulticastSessionStatus Processing MulticastSessionsStatus for memberID: " + this.memberID + "status: " + status);
               }

               Set statusActiveMulticastSessionIds = new HashSet(status.getActiveMulticastSessionIds());
               Set statusInactiveMulticastSessionIds = new HashSet(status.getInactiveMulticastSessionIds());
               Set clonedActiveMulticastSessionIds = new HashSet(this.activeMulticastSessionIds);
               Set clonedInactiveMulticastSessionIds = new HashSet(this.inactiveMulticastSessionIds);
               this.activeMulticastSessionIds.clear();
               this.activeMulticastSessionIds.addAll(status.getActiveMulticastSessionIds());
               this.inactiveMulticastSessionIds.clear();
               this.inactiveMulticastSessionIds.addAll(status.getInactiveMulticastSessionIds());
               this.previousStatusChangeNumber.set(status.getStatusChangeNumber());
               this.firePartitionChangeEvent(status.getClusterMemberInfo(), statusActiveMulticastSessionIds, clonedActiveMulticastSessionIds, 0);
               this.firePartitionChangeEvent(status.getClusterMemberInfo(), statusInactiveMulticastSessionIds, clonedInactiveMulticastSessionIds, 1);
               Iterator var7 = this.inactiveMulticastSessionIds.iterator();

               while(var7.hasNext()) {
                  MulticastSessionId sessionId = (MulticastSessionId)var7.next();
                  if (!alwaysActiveMulticastSessionIds.contains(sessionId)) {
                     MulticastSessionReceiver info;
                     synchronized(this.receivers) {
                        info = (MulticastSessionReceiver)this.receivers.remove(sessionId);
                     }

                     if (info != null) {
                        info.shutdown();
                     }
                  }
               }
            }
         }
      }

   }

   private void firePartitionChangeEvent(ClusterMemberInfo member, Set statusMulticastSessionIds, Set currentMulticastSessionIds, int action) {
      statusMulticastSessionIds.removeAll(currentMulticastSessionIds);
      Iterator var5 = statusMulticastSessionIds.iterator();

      while(var5.hasNext()) {
         MulticastSessionId multicastSessionId = (MulticastSessionId)var5.next();
         if (multicastSessionId.getResourceGroupName().equals("NO_RESOURCE_GROUP")) {
            MemberManager.theOne().fireClusterMembersPartitionChangeEvent(member, action, multicastSessionId.getPartitionID());
         }
      }

   }

   void handleMulticastSessionsStatusMessage(MulticastSessionsStatusMessage message) {
      this.handleMulticastSessionsStatus(message);
   }

   void handleMulticastSessionStartedMessage(MulticastSessionStartedMessage message) {
      this.handleMulticastSessionsStatus(message);
   }

   void handleMulticastSessionStoppedMessage(MulticastSessionStoppedMessage message) {
      this.handleMulticastSessionsStatus(message);
   }

   private void shutdownInternal() {
      synchronized(this.receivers) {
         Iterator itr = this.receivers.values().iterator();

         while(itr.hasNext()) {
            MulticastSessionReceiver receiver = (MulticastSessionReceiver)itr.next();
            receiver.shutdown();
            itr.remove();
         }

      }
   }

   private void suspend() {
      if (this.attributes != null) {
         MemberManager.theOne().fireClusterMembersChangeEvent(this.attributes, 1);
      }

      synchronized(this.receivers) {
         Iterator receiverItr = this.receivers.values().iterator();

         while(receiverItr.hasNext()) {
            MulticastSessionReceiver receiver = (MulticastSessionReceiver)receiverItr.next();
            if (receiver.isGlobalPartition()) {
               MulticastSessionId id = receiver.getMulticastSessionId();
               if (id.getName().equals(MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID.getName())) {
                  receiver.shutdown();
               }
            } else {
               receiver.shutdown();
            }
         }

      }
   }

   public void onRemoteMulticastShutdown(MulticastSessionId multicastSessionId) {
      if (!alwaysActiveMulticastSessionIds.contains(multicastSessionId)) {
         MulticastSessionReceiver receiver;
         synchronized(this.receivers) {
            receiver = (MulticastSessionReceiver)this.receivers.remove(multicastSessionId);
         }

         if (receiver != null) {
            receiver.shutdown();
         }

      }
   }

   public boolean isMulticastSessionActive(MulticastSessionId sessionId) {
      return this.activeMulticastSessionIds.contains(sessionId);
   }

   public boolean isPartitionActive(String partitionId) {
      HashSet clonedMulticastSessionIds;
      synchronized(this.activeMulticastSessionIds) {
         clonedMulticastSessionIds = new HashSet(this.activeMulticastSessionIds);
      }

      Iterator var3 = clonedMulticastSessionIds.iterator();

      MulticastSessionId multicastSessionId;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         multicastSessionId = (MulticastSessionId)var3.next();
      } while(!multicastSessionId.getPartitionID().equals(partitionId));

      return true;
   }

   ClusterMessageReceiver findOrCreateReceiver(MulticastSessionId multicastSessionId, boolean useHTTPForSD) {
      MulticastSessionReceiver receiver = this.findOrCreateMulticastSessionReceiver(multicastSessionId);
      return receiver.findOrCreateReceiver(multicastSessionId, useHTTPForSD, this.memberID);
   }

   public MulticastSessionReceiver findOrCreateMulticastSessionReceiver(MulticastSessionId multicastSessionId) {
      synchronized(this.receivers) {
         MulticastSessionReceiver receiver = (MulticastSessionReceiver)this.receivers.get(multicastSessionId);
         if (receiver == null) {
            receiver = new MulticastSessionReceiver(multicastSessionId, new MemberServices(this.memberID));
            this.receivers.put(multicastSessionId, receiver);
         }

         return receiver;
      }
   }

   List getReceivers() {
      List list = new ArrayList();
      synchronized(this.receivers) {
         Iterator it = this.receivers.values().iterator();

         while(it.hasNext()) {
            MulticastSessionReceiver receiver = (MulticastSessionReceiver)it.next();
            list.addAll(receiver.getReceivers());
         }

         return list;
      }
   }

   synchronized void processAttributes(MemberAttributes newAttributes) {
      if (this.attributes == null) {
         if (!VersionInfo.theOne().compatible(newAttributes.version())) {
            ClusterLogger.logIncompatibleVersionsError(VersionInfo.theOne().getReleaseVersion(), newAttributes.serverName(), newAttributes.version());
            if (newAttributes.joinTime() <= this.localJoinTime) {
               ClusterLogger.logIncompatibleServerLeavingCluster();
               Work work = new WorkAdapter() {
                  public void run() {
                     try {
                        ClusterService.getClusterServiceInternal().getActivator().stop();
                     } catch (ServiceFailureException var2) {
                        var2.printStackTrace();
                     }

                  }
               };
               WorkManagerFactory.getInstance().getSystem().schedule(work);
            }
         } else {
            ClusterMemberInfo localMemInfo = ClusterService.getServices().getLocalMember();
            if (!localMemInfo.domainName().equals(newAttributes.domainName())) {
               ClusterLogger.logMultipleDomainsCannotUseSameMulticastAddress(localMemInfo.domainName(), newAttributes.domainName());
            } else if (!localMemInfo.clusterName().equals(newAttributes.clusterName())) {
               ClusterLogger.logMultipleClustersCannotUseSameMulticastAddress(localMemInfo.clusterName(), newAttributes.clusterName());
            } else {
               this.attributes = newAttributes;
            }
         }
      } else {
         MemberManager.theOne().fireClusterMembersChangeEvent(newAttributes, 2);
      }

   }

   void processAnnouncement(AnnouncementMessage message) {
      this.findOrCreateMulticastSessionReceiver(message.getMulticastSessionId()).processAnnouncement(message);
   }

   void processStateDump(Collection offers, MulticastSessionId multicastSessionId, long currentSeqNum) {
      this.findOrCreateMulticastSessionReceiver(multicastSessionId).processStateDump(offers, multicastSessionId, currentSeqNum, this.memberID);
   }

   public String toString() {
      return this.memberID.getHostAddress().toString();
   }

   void updateRuntimeState(int newState) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("update runtime state from " + this.srvrRuntimeState + " to " + newState + " for " + this.memberID);
      }

      switch (this.srvrRuntimeState) {
         case 2:
            if (newState == 17) {
               this.suspend();
            } else if (newState == 9 || newState == 8) {
               this.suspend();
               this.shutdownInternal();
            }
            break;
         case 8:
         case 9:
            if (newState == 2) {
               this.add();
            } else if (newState == 17) {
               this.discover();
            } else if (newState == 9 || newState == 8) {
               this.suspend();
               this.shutdownInternal();
            }
            break;
         case 17:
            if (newState == 2) {
               this.add();
            } else if (newState == 9 || newState == 8) {
               this.shutdownInternal();
            }
      }

      this.srvrRuntimeState = newState;
   }

   boolean isRunning() {
      return this.srvrRuntimeState == 2;
   }

   boolean isSuspended() {
      return this.srvrRuntimeState == 17;
   }

   private void add() {
      assert this.attributes != null;

      MemberManager.theOne().fireClusterMembersChangeEvent(this.attributes, 0);
      ClusterLogger.logAddingServer(this.attributes.serverName(), this.attributes.clusterName(), this.attributes.identity().toString());
   }

   private void discover() {
      MemberManager.theOne().fireClusterMembersChangeEvent(this.attributes, 3);
   }

   void dumpDiagnosticImageData(XMLStreamWriter xsw) throws XMLStreamException, IOException {
      xsw.writeStartElement("RemoteMemberInfo");
      xsw.writeAttribute("HostAddress", this.toString());
      Iterator var2 = this.getReceivers().iterator();

      while(var2.hasNext()) {
         ClusterMessageReceiver receiver = (ClusterMessageReceiver)var2.next();
         receiver.dumpDiagnosticImageData(xsw);
      }

      xsw.writeEndElement();
   }

   static {
      alwaysActiveMulticastSessionIds.add(MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID);
      alwaysActiveMulticastSessionIds.add(MulticastSessionIDConstants.MEMBER_MANAGER_ID);
      alwaysActiveMulticastSessionIds.add(MulticastSessionIDConstants.ATTRIBUTE_MANAGER_ID);
      alwaysActiveMulticastSessionIds.add(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
