package weblogic.cluster;

import java.util.ArrayList;
import java.util.Iterator;
import weblogic.rmi.spi.HostID;

final class MemberServices {
   private HostID memberID;
   private ArrayList allOffers;

   MemberServices(HostID memberID) {
      this.memberID = memberID;
      this.allOffers = new ArrayList();
   }

   ArrayList getAllOffers() {
      return this.allOffers;
   }

   void retractAllOffers(boolean isLocal) {
      synchronized(this.allOffers) {
         Iterator allOffersIter = this.allOffers.iterator();

         while(allOffersIter.hasNext()) {
            ServiceOffer offer = (ServiceOffer)allOffersIter.next();
            TreeManager.theOne().retract(offer, isLocal);
         }

         this.allOffers.clear();
      }
   }

   void processRetract(ServiceRetract retract, boolean isLocal) {
      synchronized(this.allOffers) {
         Iterator allOffersIter = this.allOffers.iterator();

         ServiceOffer offer;
         do {
            if (!allOffersIter.hasNext()) {
               return;
            }

            offer = (ServiceOffer)allOffersIter.next();
         } while(offer.id() != retract.id());

         allOffersIter.remove();
         TreeManager.theOne().retract(offer, isLocal);
      }
   }

   void processOffer(ServiceOffer offer, boolean isLocal) {
      synchronized(this.allOffers) {
         offer.setServer(this.memberID);
         TreeManager.theOne().install(offer, isLocal);
         this.allOffers.add(offer);
      }
   }

   void processUpdate(ServiceOffer offer, boolean isLocal, int oldID) {
      synchronized(this.allOffers) {
         Iterator allOffersIter = this.allOffers.iterator();

         while(allOffersIter.hasNext()) {
            ServiceOffer oldOffer = (ServiceOffer)allOffersIter.next();
            if (oldOffer.id() == offer.getOldID()) {
               allOffersIter.remove();
               break;
            }
         }

         offer.setServer(this.memberID);
         TreeManager.theOne().update(offer, isLocal);
         this.allOffers.add(offer);
      }
   }
}
