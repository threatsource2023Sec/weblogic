package weblogic.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.rmi.spi.HostID;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

public final class NodeInfo {
   private static final boolean debug = false;
   private Collection installedOffers = new ArrayList();
   private Collection pendingOffers = null;
   int numUnprocessedRequests;
   private String name;
   private Context initialCtx;
   private ConflictHandler conHandler;
   private long approximateAge;
   private long ageThresholdMillis;

   public NodeInfo(Context initialCtx, ConflictHandler conHandler, String name, long ageThreshold) {
      this.initialCtx = initialCtx;
      this.conHandler = conHandler;
      this.name = name;
      this.ageThresholdMillis = ageThreshold * 1000L;
   }

   synchronized void install(ServiceOffer offer, boolean isLocal) {
      try {
         if (!isLocal) {
            offer.install(this.initialCtx);
         }

         if (this.installedOffers.size() == 0) {
            this.approximateAge = offer.approximateAge();
         }

         this.installedOffers.add(offer);
         if (this.pendingOffers != null) {
            this.conHandler.conflictStart(offer);
         }
      } catch (AssertionError var4) {
         ClusterExtensionLogger.logAssertionError(this.buildAssertionErrorMessage(var4));
      } catch (NamingException var5) {
         if (this.pendingOffers == null) {
            this.conflictStart(this.installedOffers);
            this.pendingOffers = new ArrayList();
         }

         if (this.approximateAge <= this.ageThresholdMillis && offer.approximateAge() > this.ageThresholdMillis) {
            this.switchWithInstalledOffers(offer);
         } else {
            this.pendingOffers.add(offer);
         }

         this.conHandler.conflictStart(offer);
      }

   }

   synchronized void update(ServiceOffer offer, boolean isLocal) {
      try {
         if (!isLocal) {
            offer.update(this.initialCtx);
         }

         if (this.installedOffers.size() == 0) {
            this.approximateAge = offer.approximateAge();
         }

         this.remove(offer.getOldID(), offer.getServerID(), this.installedOffers);
         this.installedOffers.add(offer);
         if (this.pendingOffers != null) {
            this.conHandler.conflictStart(offer);
         }
      } catch (AssertionError var4) {
         ClusterExtensionLogger.logAssertionError(this.buildAssertionErrorMessage(var4));
      } catch (NamingException var5) {
         if (this.pendingOffers == null) {
            this.conflictStart(this.installedOffers);
            this.pendingOffers = new ArrayList();
         }

         if (this.approximateAge <= this.ageThresholdMillis && offer.approximateAge() > this.ageThresholdMillis) {
            this.switchWithInstalledOffers(offer);
         } else {
            this.pendingOffers.add(offer);
         }

         this.conHandler.conflictStart(offer);
      }

   }

   private String buildAssertionErrorMessage(AssertionError ae) {
      StringBuilder text = new StringBuilder();
      boolean firstMessage = true;

      while(true) {
         if (!firstMessage) {
            text.append(" caused by ");
         } else {
            firstMessage = false;
         }

         text.append(ae.toString());
         Throwable t = ae.getNested();
         if (t == null) {
            break;
         }

         if (!(t instanceof AssertionError)) {
            text.append(" caused by " + t);
            break;
         }

         ae = (AssertionError)t;
      }

      return text.toString();
   }

   private void switchWithInstalledOffers(ServiceOffer newOffer) {
      Iterator it = this.installedOffers.iterator();

      while(it.hasNext()) {
         ServiceOffer cur = (ServiceOffer)it.next();
         it.remove();

         try {
            cur.retract(this.initialCtx);
            this.pendingOffers.add(cur);
         } catch (NamingException var6) {
            throw new AssertionError("Impossible exception", var6);
         }
      }

      try {
         newOffer.install(this.initialCtx);
         this.installedOffers.add(newOffer);
         this.approximateAge = newOffer.approximateAge();
      } catch (NamingException var5) {
         ClusterLogger.logOfferReplacementError(newOffer.toString(), var5);
         this.pendingOffers.add(newOffer);
      }

   }

   synchronized void retract(ServiceOffer offer, boolean isLocal) {
      if (this.remove(offer.id(), offer.getServerID(), this.installedOffers)) {
         if (!isLocal) {
            try {
               offer.retract(this.initialCtx);
            } catch (NamingException var7) {
            }
         }

         if (this.pendingOffers != null) {
            this.conHandler.conflictStop(offer);
            if (this.installedOffers.isEmpty()) {
               Iterator offersIter = this.pendingOffers.iterator();

               while(offersIter.hasNext()) {
                  ServiceOffer offerAgain = (ServiceOffer)offersIter.next();

                  try {
                     offerAgain.install(this.initialCtx);
                     offersIter.remove();
                     this.installedOffers.add(offerAgain);
                  } catch (NamingException var6) {
                  }
               }
            }
         }
      } else {
         boolean removed = false;
         if (this.pendingOffers != null) {
            removed = this.remove(offer.id(), offer.getServerID(), this.pendingOffers);
         }

         if (!removed) {
            ClusterLogger.logRetractUnrecognizedOfferError(offer.toString());
         }
      }

      if (this.pendingOffers != null && this.pendingOffers.isEmpty()) {
         this.conflictStop(this.installedOffers);
         this.pendingOffers = null;
      }

   }

   boolean isEmpty() {
      return this.pendingOffers == null && this.installedOffers.isEmpty();
   }

   private boolean remove(int offerID, HostID serverID, Collection offers) {
      Iterator offersIter = offers.iterator();

      ServiceOffer candidate;
      do {
         if (!offersIter.hasNext()) {
            return false;
         }

         candidate = (ServiceOffer)offersIter.next();
      } while(!candidate.getServerID().equals(serverID) || candidate.id() != offerID);

      offersIter.remove();
      return true;
   }

   private void conflictStart(Collection installedOffers) {
      Iterator it = installedOffers.iterator();

      while(it.hasNext()) {
         ServiceOffer cur = (ServiceOffer)it.next();
         this.conHandler.conflictStart(cur);
      }

   }

   private void conflictStop(Collection offers) {
      Iterator offersIter = offers.iterator();

      while(offersIter.hasNext()) {
         ServiceOffer offer = (ServiceOffer)offersIter.next();
         this.conHandler.conflictStop(offer);
      }

   }

   private void debugMsg(String message) {
      StringBuilder sb = new StringBuilder(64);
      sb.append(new Date()).append(" ").append(Thread.currentThread().getName()).append(": ").append(message);
      Debug.say(sb.toString());
   }
}
