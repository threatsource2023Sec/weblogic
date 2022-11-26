package weblogic.management.eventbus.spi;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.management.eventbus.InternalEventBusLogger;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventFilter;
import weblogic.management.eventbus.apis.InternalEventListener;
import weblogic.management.eventbus.apis.PrioritizedInternalEventListener;

public class InternalEventBusImpl implements InternalEventBus {
   private List listeners = new CopyOnWriteArrayList();
   private static final InternalListenerPriorityComparator COMPARATOR = new InternalListenerPriorityComparator();
   private static final InternalEventBusFuture OK_FUTURE = new InternalEventBusFuture();

   public Future send(InternalEvent event) {
      if (event == null) {
         return null;
      } else {
         Throwable firstDeliveryError = null;
         Iterator var3 = this.listeners.iterator();

         while(var3.hasNext()) {
            InternalListenerFilterBinome listenerBinome = (InternalListenerFilterBinome)var3.next();
            InternalEventFilter filter = listenerBinome.filter;

            try {
               boolean accept = filter == null ? true : filter.acceptEvent(event);
               if (accept) {
                  listenerBinome.listener.handleEvent(event);
               }
            } catch (Throwable var7) {
               InternalEventBusLogger.logErrorProcessingEvent(event.toString(), listenerBinome.listener.toString(), var7);
               if (firstDeliveryError == null) {
                  firstDeliveryError = var7;
               }
            }
         }

         return firstDeliveryError == null ? OK_FUTURE : new InternalEventBusFuture(firstDeliveryError);
      }
   }

   public synchronized void registerListener(InternalEventListener listener, InternalEventFilter filter) {
      InternalListenerFilterBinome ilft = new InternalListenerFilterBinome(listener, filter);
      int insertionIndex = 0;

      for(Iterator var5 = this.listeners.iterator(); var5.hasNext(); ++insertionIndex) {
         InternalListenerFilterBinome compIlft = (InternalListenerFilterBinome)var5.next();
         if (COMPARATOR.compare(compIlft, ilft) > 0) {
            break;
         }
      }

      this.listeners.add(insertionIndex, ilft);
   }

   public void registerListener(InternalEventListener listener) {
      if (listener instanceof InternalEventFilter) {
         this.registerListener(listener, (InternalEventFilter)InternalEventFilter.class.cast(listener));
      } else {
         this.registerListener(listener, (InternalEventFilter)null);
      }

   }

   public synchronized void unregisterListener(InternalEventListener listener, InternalEventFilter filter) {
      if (filter == null) {
         InternalListenerFilterSingleton listenerSingleton = new InternalListenerFilterSingleton(listener);
         this.listeners.removeAll(Collections.singleton(listenerSingleton));
      } else {
         InternalListenerFilterBinome ilft = new InternalListenerFilterBinome(listener, filter);
         this.listeners.remove(ilft);
      }

   }

   public void unregisterListener(InternalEventListener listener) {
      if (listener instanceof InternalEventFilter) {
         this.unregisterListener(listener, (InternalEventFilter)InternalEventFilter.class.cast(listener));
      } else {
         this.unregisterListener(listener, (InternalEventFilter)null);
      }

   }

   private static class InternalEventBusFuture implements Future {
      private final ExecutionException deliveryException;

      InternalEventBusFuture() {
         this((Throwable)null);
      }

      InternalEventBusFuture(Throwable t) {
         if (t != null) {
            this.deliveryException = new ExecutionException(t);
         } else {
            this.deliveryException = null;
         }

      }

      public boolean cancel(boolean mayInterruptIfRunning) {
         return false;
      }

      public boolean isCancelled() {
         return false;
      }

      public boolean isDone() {
         return true;
      }

      public Void get() throws InterruptedException, ExecutionException {
         if (this.deliveryException != null) {
            throw this.deliveryException;
         } else {
            return null;
         }
      }

      public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
         return this.get();
      }
   }

   private static class InternalListenerPriorityComparator implements Comparator {
      private InternalListenerPriorityComparator() {
      }

      public int compare(InternalListenerFilterBinome o1, InternalListenerFilterBinome o2) {
         InternalEventListener l1 = o1.listener;
         InternalEventListener l2 = o2.listener;
         int pl1 = l1 instanceof PrioritizedInternalEventListener ? ((PrioritizedInternalEventListener)PrioritizedInternalEventListener.class.cast(l1)).priority() : 100;
         int pl2 = l2 instanceof PrioritizedInternalEventListener ? ((PrioritizedInternalEventListener)PrioritizedInternalEventListener.class.cast(l2)).priority() : 100;
         int result = pl1 - pl2;
         return result;
      }

      public boolean equals(Object o) {
         return o instanceof InternalListenerPriorityComparator;
      }

      // $FF: synthetic method
      InternalListenerPriorityComparator(Object x0) {
         this();
      }
   }

   private static class InternalListenerFilterSingleton extends InternalListenerFilterBinome {
      InternalListenerFilterSingleton(InternalEventListener listener) {
         super(listener, (InternalEventFilter)null);
      }

      public boolean equals(Object o) {
         assert !(o instanceof InternalListenerFilterSingleton);

         return o.equals(this);
      }
   }

   private static class InternalListenerFilterBinome {
      InternalEventListener listener;
      InternalEventFilter filter;

      InternalListenerFilterBinome(InternalEventListener listener, InternalEventFilter filter) {
         this.listener = listener;
         this.filter = filter;
      }

      public boolean equals(Object o) {
         if (!(o instanceof InternalListenerFilterBinome)) {
            return false;
         } else {
            InternalListenerFilterBinome ilft = (InternalListenerFilterBinome)InternalListenerFilterBinome.class.cast(o);
            if (ilft instanceof InternalListenerFilterSingleton) {
               return ilft.listener == this.listener;
            } else {
               return ilft.listener == this.listener && ilft.filter == this.filter;
            }
         }
      }
   }
}
