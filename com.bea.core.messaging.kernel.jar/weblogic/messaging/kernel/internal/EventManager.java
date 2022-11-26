package weblogic.messaging.kernel.internal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.messaging.kernel.DestinationEvent;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.EventListener;
import weblogic.messaging.kernel.internal.events.EventImpl;
import weblogic.utils.collections.CircularQueue;
import weblogic.work.WorkManager;

final class EventManager implements Runnable {
   private boolean running = false;
   private final LinkedList listenerList = new LinkedList();
   private final CircularQueue eventList = new CircularQueue();
   private final WorkManager workManager;

   EventManager(WorkManager workManager) {
      this.workManager = workManager;
   }

   public void addListener(EventListener listener) {
      synchronized(this.listenerList) {
         this.listenerList.add(listener);
      }
   }

   public boolean removeListener(EventListener listener) {
      synchronized(this.listenerList) {
         return this.listenerList.remove(listener);
      }
   }

   public void addEvent(List eventList) {
      synchronized(this.eventList) {
         this.eventList.addAll(eventList);
         if (this.running) {
            return;
         }

         this.running = true;
      }

      this.workManager.schedule(this);
   }

   public void addEvent(EventImpl event) {
      synchronized(this.eventList) {
         this.eventList.add(event);
         if (this.running) {
            return;
         }

         this.running = true;
      }

      this.workManager.schedule(this);
   }

   public void run() {
      EventImpl event;
      synchronized(this.eventList) {
         event = (EventImpl)this.eventList.remove();
         if (event == null) {
            this.running = false;
            return;
         }
      }

      while(true) {
         if (event instanceof DestinationEvent) {
            ((DestinationImpl)((DestinationImpl)((DestinationEvent)event).getDestination())).onEvent(event);
         } else {
            onEvent(event, this.listenerList);
         }

         synchronized(this.eventList) {
            event = (EventImpl)this.eventList.peek();
            if (event == null) {
               this.running = false;
               return;
            }

            if (this.workManager.scheduleIfBusy(this)) {
               return;
            }

            this.eventList.remove();
         }
      }
   }

   static void onEvent(Event event, LinkedList listenerList) {
      Iterator iterator;
      synchronized(listenerList) {
         if (listenerList.isEmpty()) {
            return;
         }

         iterator = ((LinkedList)((LinkedList)listenerList.clone())).iterator();
      }

      while(iterator.hasNext()) {
         EventListener listener = (EventListener)iterator.next();

         try {
            listener.onEvent(event);
         } catch (Throwable var8) {
            synchronized(listenerList) {
               listenerList.remove(listener);
            }
         }
      }

   }
}
