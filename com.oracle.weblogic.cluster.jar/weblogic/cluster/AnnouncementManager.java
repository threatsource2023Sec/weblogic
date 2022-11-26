package weblogic.cluster;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.cluster.replication.ReplicationManager;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class AnnouncementManager implements RecoverListener, MulticastSessionIDConstants {
   private MulticastSessionId multicastSessionId;
   private MemberServices localServices;
   private final AtomicReference announcementSender = new AtomicReference();
   private AtomicBoolean blocked;
   private ArrayList blockedItems;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   MulticastSessionId getMulticastSessionId() {
      return this.multicastSessionId;
   }

   AnnouncementManager(long ageThreshold, MulticastSessionId multicastSessionId) {
      this.multicastSessionId = multicastSessionId;
      this.blocked = new AtomicBoolean(true);
      this.blockedItems = new ArrayList();
      TreeManager.initialize(ageThreshold);
      this.localServices = new MemberServices(LocalServerIdentity.getIdentity());
   }

   private MulticastSession getAnnouncementSender() {
      if (this.announcementSender.get() == null) {
         synchronized(this.announcementSender) {
            if (this.announcementSender.get() == null) {
               this.announcementSender.set(ClusterService.getClusterServiceInternal().createMulticastSession(this.multicastSessionId, this, -1, true));
            }
         }
      }

      return (MulticastSession)this.announcementSender.get();
   }

   synchronized void unblockAnnouncements() {
      this.blocked.set(false);
      if (!this.blockedItems.isEmpty()) {
         this.sendAnnouncement(this.blockedItems);
         this.blockedItems = new ArrayList();
      }

   }

   synchronized void blockAnnouncements() {
      this.blocked.set(true);
      this.announcementSender.set((Object)null);
      this.blockedItems = new ArrayList();
   }

   synchronized void shutdown() {
      this.localServices.retractAllOffers(true);
      this.blocked.set(true);
      ClusterMessagesManager.theOne().removeSender(this.multicastSessionId);
      this.announcementSender.set((Object)null);
      String partitionName = PartitionAwareSenderManager.theOne().getPartitionNameFromPartitionId(this.multicastSessionId.getPartitionID());
      ReplicationServices syncRepServ = Locator.locate().getReplicationService(ServiceType.SYNC);
      ReplicationServices asyncRepServ = Locator.locate().getReplicationService(ServiceType.ASYNC);
      if (syncRepServ != null && syncRepServ instanceof ReplicationManager) {
         ((ReplicationManager)syncRepServ).removeSecondarySelector(partitionName, this.multicastSessionId.getResourceGroupName());
      }

      if (syncRepServ != null && asyncRepServ instanceof ReplicationManager) {
         ((ReplicationManager)asyncRepServ).removeSecondarySelector(partitionName, this.multicastSessionId.getResourceGroupName());
      }

   }

   public synchronized void announce(ServiceRetract retract, ServiceOffer offer) {
      if (retract != null) {
         this.localServices.processRetract(retract, true);
      }

      if (offer != null) {
         this.localServices.processOffer(offer, true);
      }

      if (this.blocked.get()) {
         if (retract != null) {
            int retractID = retract.id();
            Object originalOffer = null;
            Iterator i = this.blockedItems.iterator();

            while(i.hasNext()) {
               Object item = i.next();
               if (item instanceof ServiceOffer) {
                  ServiceOffer service = (ServiceOffer)item;
                  if (service.id() == retractID) {
                     originalOffer = service;
                     break;
                  }
               }
            }

            if (originalOffer != null && !retract.ignoreRetract()) {
               this.blockedItems.remove(originalOffer);
            } else {
               this.blockedItems.add(retract);
            }
         }

         if (offer != null) {
            this.blockedItems.add(offer);
         }
      } else {
         ArrayList items = new ArrayList();
         if (retract != null) {
            items.add(retract);
         }

         if (offer != null) {
            items.add(offer);
         }

         this.sendAnnouncement(items);
      }

   }

   private void sendAnnouncement(ArrayList items) {
      AnnouncementMessage message = new AnnouncementMessage(this.multicastSessionId, items);
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("Sending " + message);
      }

      try {
         this.getAnnouncementSender().send(message);
      } catch (IOException var4) {
         ClusterLogger.logMulticastSendError(var4);
      }

   }

   void receiveAnnouncement(final HostID memberID, final AnnouncementMessage message) {
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("Received " + message + " from " + memberID);
            }

            RemoteMemberInfo remoteMember = MemberManager.theOne().findOrCreate(memberID);

            try {
               remoteMember.processAnnouncement(message);
            } finally {
               MemberManager.theOne().done(remoteMember);
            }

            return null;
         }
      });
   }

   public synchronized GroupMessage createRecoverMessage() {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("***** AnnouncementManager.createRecoverMessage() Received a request for : " + this.multicastSessionId);
      }

      StateDumpMessage message = new StateDumpMessage(this.localServices.getAllOffers(), this.multicastSessionId, this.getCurrentSeqNum());
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("Sending " + message);
      }

      return message;
   }

   long getCurrentSeqNum() {
      return ClusterMessagesManager.theOne().findSender(this.multicastSessionId).getCurrentSeqNum();
   }

   void receiveStateDump(final HostID memberID, final StateDumpMessage message) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("***** AnnouncementManager.receiveStateDump() hostID: " + memberID + "; multicastSessionID: " + message.multicastSessionId + " ******");
      }

      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("Received " + message + " from " + memberID);
            }

            RemoteMemberInfo remoteMember = MemberManager.theOne().findOrCreate(memberID);

            try {
               remoteMember.processStateDump(message.offers, message.multicastSessionId, message.currentSeqNum);
            } finally {
               MemberManager.theOne().done(remoteMember);
            }

            return null;
         }
      });
   }

   public boolean isBlocked() {
      return this.blocked.get();
   }
}
