package weblogic.health;

import java.util.ArrayList;
import java.util.Iterator;
import weblogic.platform.GCListener;
import weblogic.platform.GarbageCollectionEvent;
import weblogic.platform.VM;

public final class LowMemoryNotificationService implements GCListener {
   private static final boolean DEBUG = false;
   private static final int MINOR_GC_COUNT_THRESHOLD = 5;
   private static final MemoryEvent LOW_MEMORY_EVENT = new MemoryEvent(1);
   private static final MemoryEvent OK_MEMORY_EVENT = new MemoryEvent(0);
   private static final ArrayList list = new ArrayList();
   private static boolean initialized;
   private final int lowThreshold;
   private final int highThreshold;
   private boolean lowMemoryThresholdReached;
   private int consecutiveMinorGCCount;

   private LowMemoryNotificationService(int lowThreshold, int highThreshold) {
      this.lowThreshold = lowThreshold;
      this.highThreshold = highThreshold;
   }

   public static synchronized void addMemoryListener(MemoryListener listener) {
      list.add(listener);
   }

   public static synchronized void removeMemoryListener(MemoryListener listener) {
      list.remove(listener);
   }

   public static synchronized void initialize(int lowMemoryThresholdPercent, int highMemoryThresholdPercent) {
      if (!initialized) {
         VM.getVM().addGCListener(new LowMemoryNotificationService(lowMemoryThresholdPercent, highMemoryThresholdPercent));
         initialized = true;
      }
   }

   private static final synchronized void sendMemoryEvent(MemoryEvent event) {
      Iterator iter = list.iterator();

      while(iter.hasNext()) {
         MemoryListener listener = (MemoryListener)iter.next();
         listener.memoryChanged(event);
      }

   }

   public void onGarbageCollection(GarbageCollectionEvent event) {
      int percent = HealthUtils.logAndGetFreeMemoryPercent();
      if (event != null) {
         synchronized(this) {
            if (percent < this.lowThreshold && !this.lowMemoryThresholdReached && this.acceptGCEvent(event)) {
               this.lowMemoryThresholdReached = true;
               sendMemoryEvent(LOW_MEMORY_EVENT);
            } else {
               if (percent > this.highThreshold) {
                  this.consecutiveMinorGCCount = 0;
                  if (!this.lowMemoryThresholdReached) {
                     return;
                  }

                  this.lowMemoryThresholdReached = false;
                  sendMemoryEvent(OK_MEMORY_EVENT);
               }

            }
         }
      }
   }

   private boolean acceptGCEvent(GarbageCollectionEvent event) {
      if (event.getEventType() != 0 && this.consecutiveMinorGCCount < 5) {
         ++this.consecutiveMinorGCCount;
         return false;
      } else {
         this.consecutiveMinorGCCount = 0;
         return true;
      }
   }

   private static void debug(String s) {
      System.out.println("[LowMemoryNotificationService] " + s);
   }
}
