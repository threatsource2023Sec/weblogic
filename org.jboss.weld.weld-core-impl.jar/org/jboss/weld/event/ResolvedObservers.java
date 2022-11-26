package org.jboss.weld.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.collections.ImmutableList;

public class ResolvedObservers {
   private static final ResolvedObservers EMPTY = new ResolvedObservers(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), false) {
      public boolean isEmpty() {
         return true;
      }
   };
   private final List immediateSyncObservers;
   private final List asyncObservers;
   private final List transactionObservers;
   private final boolean metadataRequired;

   public static ResolvedObservers of(List observers) {
      if (observers.isEmpty()) {
         return EMPTY;
      } else {
         boolean metadataRequired = false;
         List immediateSyncObservers = new ArrayList();
         List transactionObservers = new ArrayList();
         List asyncObservers = new ArrayList();
         Iterator var5 = observers.iterator();

         while(var5.hasNext()) {
            ObserverMethod observer = (ObserverMethod)var5.next();
            if (observer.isAsync()) {
               asyncObservers.add(observer);
            } else if (TransactionPhase.IN_PROGRESS == observer.getTransactionPhase()) {
               immediateSyncObservers.add(observer);
            } else {
               transactionObservers.add(observer);
            }

            if (!metadataRequired && Observers.isEventMetadataRequired(observer)) {
               metadataRequired = true;
            }
         }

         return new ResolvedObservers(ImmutableList.copyOf((Collection)immediateSyncObservers), ImmutableList.copyOf((Collection)asyncObservers), ImmutableList.copyOf((Collection)transactionObservers), metadataRequired);
      }
   }

   private ResolvedObservers(List immediateSyncObservers, List asyncObservers, List transactionObservers, boolean metadataRequired) {
      this.immediateSyncObservers = immediateSyncObservers;
      this.asyncObservers = asyncObservers;
      this.transactionObservers = transactionObservers;
      this.metadataRequired = metadataRequired;
   }

   List getImmediateSyncObservers() {
      return this.immediateSyncObservers;
   }

   List getTransactionObservers() {
      return this.transactionObservers;
   }

   List getAsyncObservers() {
      return this.asyncObservers;
   }

   boolean isMetadataRequired() {
      return this.metadataRequired;
   }

   public boolean isEmpty() {
      return false;
   }

   public List getAllObservers() {
      return ImmutableList.builder().addAll((Iterable)this.immediateSyncObservers).addAll((Iterable)this.asyncObservers).addAll((Iterable)this.transactionObservers).build();
   }

   // $FF: synthetic method
   ResolvedObservers(List x0, List x1, List x2, boolean x3, Object x4) {
      this(x0, x1, x2, x3);
   }
}
