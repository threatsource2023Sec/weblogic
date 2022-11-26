package weblogic.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.rmi.spi.HostID;

public class MulticastSessionReceiver {
   private MulticastSessionId multicastSessionId;
   private HashMap receivers = new HashMap();
   private MemberServices services;

   public MulticastSessionReceiver(MulticastSessionId multicastSessionId, MemberServices services) {
      this.multicastSessionId = multicastSessionId;
      this.services = services;
   }

   public MulticastSessionId getMulticastSessionId() {
      return this.multicastSessionId;
   }

   public ClusterMessageReceiver findOrCreateReceiver(MulticastSessionId multicastSessionId, boolean useHTTPForSD, HostID memberID) {
      synchronized(this.receivers) {
         ClusterMessageReceiver receiver = (ClusterMessageReceiver)this.receivers.get(multicastSessionId);
         if (receiver == null) {
            if (useHTTPForSD) {
               receiver = new HybridClusterMessageReceiver(memberID, multicastSessionId, ClusterService.MULTICAST_WORKMANAGER);
            } else {
               receiver = new ClusterMessageReceiver(memberID, multicastSessionId, ClusterService.MULTICAST_WORKMANAGER);
            }

            this.receivers.put(multicastSessionId, receiver);
         }

         return (ClusterMessageReceiver)receiver;
      }
   }

   public List getReceivers() {
      List list = new ArrayList();
      synchronized(this.receivers) {
         Iterator it = this.receivers.values().iterator();

         while(it.hasNext()) {
            Object rec = it.next();
            if (rec instanceof ClusterMessageReceiver) {
               list.add((ClusterMessageReceiver)rec);
            }
         }

         return list;
      }
   }

   public void shutdown() {
      MemberServices memberServices = this.getMemberServices();
      synchronized(memberServices) {
         memberServices.retractAllOffers(false);
      }

      synchronized(this.receivers) {
         Iterator var3 = this.receivers.values().iterator();

         while(var3.hasNext()) {
            ClusterMessageReceiver receiver = (ClusterMessageReceiver)var3.next();
            receiver.shutdown();
         }

         this.receivers.clear();
      }
   }

   public MemberServices getMemberServices() {
      return this.services;
   }

   public ClusterMessageReceiver getReceiver(MulticastSessionId multicastSessionId) {
      return (ClusterMessageReceiver)this.receivers.get(multicastSessionId);
   }

   public boolean isGlobalPartition() {
      return this.multicastSessionId.getPartitionID().equals("0") && this.multicastSessionId.getResourceGroupName().equals("NO_RESOURCE_GROUP");
   }

   public void processAnnouncement(AnnouncementMessage message) {
      if (!PartitionAwareSenderManager.theOne().isMulticastSessionInactive(message.getMulticastSessionId())) {
         Collection items = message.items;
         synchronized(this.services) {
            Iterator itemIter = items.iterator();

            while(itemIter.hasNext()) {
               Object item = itemIter.next();

               try {
                  ServiceRetract retract = (ServiceRetract)item;
                  if (!retract.ignoreRetract()) {
                     this.services.processRetract(retract, false);
                  }
               } catch (ClassCastException var9) {
                  try {
                     ServiceOffer offer = (ServiceOffer)item;
                     if (offer.getOldID() != -1) {
                        this.services.processUpdate(offer, false, offer.getOldID());
                     } else {
                        this.services.processOffer(offer, false);
                     }
                  } catch (ClassCastException var8) {
                  }
               }
            }

         }
      }
   }

   public void processStateDump(Collection offers, MulticastSessionId multicastSessionId, long currentSeqNum, HostID memberID) {
      synchronized(this.services) {
         this.services.retractAllOffers(false);
         synchronized(offers) {
            Iterator offerIter = offers.iterator();

            while(true) {
               if (!offerIter.hasNext()) {
                  break;
               }

               ServiceOffer offer = (ServiceOffer)offerIter.next();
               this.services.processOffer(offer, false);
            }
         }

         ClusterMessageReceiver receiver = this.findOrCreateReceiver(multicastSessionId, true, memberID);
         receiver.setInSync(currentSeqNum);
      }
   }
}
