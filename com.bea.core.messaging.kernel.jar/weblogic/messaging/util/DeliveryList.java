package weblogic.messaging.util;

import java.util.ArrayList;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.utils.concurrent.ConcurrentBlockingQueue;
import weblogic.utils.concurrent.ConcurrentFactory;
import weblogic.work.WorkManager;

public abstract class DeliveryList implements Runnable {
   private final Object lockObject = new Object();
   protected final ConcurrentBlockingQueue deliveryQueue = ConcurrentFactory.createConcurrentBlockingQueue();
   private static final int DEFAULT_PUSH_SIZE_LIMIT = getIntProperty("weblogic.messaging.PushSizeLimit", Integer.MAX_VALUE);
   private int pushSizeLimit;
   private int pushImpatiencePoint;
   private int pushDelay;
   private int pushDelayStart;
   private int maxTotalDelay;
   private int throughputEmphasis;
   private long lastTime;
   private int totalMessages;
   private int consecutiveEvents;
   private int consecutiveNegativeEventsRequired;
   private int consecutivePositiveEventsRequired;
   private boolean neverAggregate;
   private boolean running;
   private WorkManager workManager;

   public DeliveryList() {
      this.pushSizeLimit = DEFAULT_PUSH_SIZE_LIMIT;
      this.pushImpatiencePoint = Integer.MAX_VALUE;
      this.pushDelay = 0;
      this.pushDelayStart = 0;
      this.throughputEmphasis = 25;
      this.lastTime = 0L;
      this.totalMessages = 0;
      this.consecutiveEvents = 0;
      this.neverAggregate = false;
   }

   public void setWorkManager(WorkManager workManager) {
      this.workManager = workManager;
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   protected void initDeliveryList(int windowSize, int throughputEmphasis, int pushDelayStart, int maxTotalDelay) {
      synchronized(this.lockObject) {
         this.pushDelay = pushDelayStart;
         this.pushDelayStart = pushDelayStart;
         this.maxTotalDelay = maxTotalDelay;
         if (throughputEmphasis <= 12) {
            this.neverAggregate = true;
            this.pushDelay = 0;
            this.pushDelayStart = 0;
            this.pushSizeLimit = 1;
         } else if (throughputEmphasis <= 37) {
            this.pushDelay = 0;
            this.pushDelayStart = 0;
            this.pushSizeLimit = DEFAULT_PUSH_SIZE_LIMIT;
            this.pushImpatiencePoint = 1;
         } else if (throughputEmphasis <= 62) {
            this.pushSizeLimit = 10240;
            this.pushImpatiencePoint = windowSize / 2 + 1;
            this.consecutiveNegativeEventsRequired = 3;
            this.consecutivePositiveEventsRequired = 5;
            if (maxTotalDelay == 0) {
               this.maxTotalDelay = 10 * this.pushDelay;
            }
         } else if (throughputEmphasis <= 87) {
            this.consecutiveNegativeEventsRequired = 5;
            this.consecutivePositiveEventsRequired = 5;
            this.pushSizeLimit = 20480;
            this.pushImpatiencePoint = windowSize * 3 / 4 + 1;
            if (maxTotalDelay == 0) {
               this.maxTotalDelay = 20 * this.pushDelay;
            }
         } else {
            this.consecutiveNegativeEventsRequired = 5;
            this.consecutivePositiveEventsRequired = 2;
            this.pushSizeLimit = 40960;
            this.pushImpatiencePoint = windowSize;
            if (maxTotalDelay == 0) {
               this.maxTotalDelay = 100 * this.pushDelay;
            }
         }

      }
   }

   public Runnable deliver(ListenRequest request, java.util.List messageList) {
      return this.deliver(messageList);
   }

   public Runnable deliver(ListenRequest request, MessageElement element) {
      return this.deliver(element);
   }

   public Runnable deliver(java.util.List messageList) {
      this.deliveryQueue.addAll(messageList);
      return this.checkAndStartRunning();
   }

   public final Runnable deliver(MessageElement element) {
      this.deliveryQueue.add(element);
      return this.checkAndStartRunning();
   }

   private Runnable checkAndStartRunning() {
      synchronized(this.lockObject) {
         if (this.running) {
            return null;
         } else {
            this.running = true;
            return this;
         }
      }
   }

   protected java.util.List getPendingMessages() {
      ArrayList ret = new ArrayList();
      long milliThatHelped = 0L;
      long enterTime = 0L;
      if (this.pushDelayStart != 0) {
         enterTime = System.currentTimeMillis();
         if (this.pushDelay != this.pushDelayStart && enterTime - this.lastTime < (long)this.pushDelayStart) {
            ++this.consecutiveEvents;
            if (this.consecutiveEvents >= this.consecutivePositiveEventsRequired) {
               this.pushDelay = this.pushDelayStart;
               this.consecutiveEvents = 0;
            }
         } else {
            this.consecutiveEvents = 0;
         }
      }

      boolean maxDelayExceeded = false;
      int totalSize = 0;

      do {
         Object elt = null;

         try {
            elt = this.deliveryQueue.poll();
            if (!this.neverAggregate && elt == null && !maxDelayExceeded && this.pushDelay != 0) {
               int usePushDelay;
               if (ret.size() >= this.pushImpatiencePoint) {
                  usePushDelay = 0;
               } else {
                  usePushDelay = this.pushDelay;
               }

               elt = this.deliveryQueue.poll((long)usePushDelay);
               if (elt != null && this.maxTotalDelay != 0) {
                  long thisTime = System.currentTimeMillis();
                  if (thisTime - enterTime >= (long)this.maxTotalDelay) {
                     maxDelayExceeded = true;
                  }
               }
            }
         } catch (InterruptedException var12) {
         }

         if (elt == null || ((MessageElement)elt).getMessage() == null) {
            break;
         }

         ret.add(elt);
         totalSize = (int)((long)totalSize + ((MessageElement)elt).getMessage().size());
      } while(totalSize < this.pushSizeLimit);

      if (this.pushDelay > 0) {
         if (ret.size() == 1) {
            ++this.consecutiveEvents;
            if (this.consecutiveEvents >= this.consecutiveNegativeEventsRequired) {
               this.pushDelay = 0;
               this.consecutiveEvents = 0;
            }
         } else {
            this.consecutiveEvents = 0;
         }
      }

      if (this.pushDelayStart != 0 && totalSize < this.pushSizeLimit) {
         this.lastTime = enterTime;
      }

      this.totalMessages += ret.size();
      return ret;
   }

   protected java.util.List getAllPendingMessages() {
      ArrayList ret = new ArrayList();

      while(true) {
         Object elt = this.deliveryQueue.poll();
         if (elt == null) {
            return ret;
         }

         ret.add(elt);
      }
   }

   public void run() {
      do {
         java.util.List pushees = this.getPendingMessages();
         if (!pushees.isEmpty()) {
            this.pushMessages(pushees);
         }

         synchronized(this.lockObject) {
            if (this.deliveryQueue.isEmpty()) {
               this.lockObject.notifyAll();
               this.running = false;
               return;
            }
         }
      } while(!this.workManager.scheduleIfBusy(this));

   }

   protected abstract void pushMessages(java.util.List var1);

   protected void waitUntilIdle() {
      synchronized(this.lockObject) {
         while(this.running) {
            try {
               this.lockObject.wait();
            } catch (InterruptedException var4) {
            }
         }

      }
   }

   private static int getIntProperty(String propName, int def) {
      String propVal = System.getProperty(propName);
      if (propVal == null) {
         return def;
      } else {
         int ret = def;
         String action = "Syntax Error, using default " + def;

         try {
            ret = Integer.parseInt(propVal);
            action = "Changed from default " + def + " to " + ret;
         } catch (NumberFormatException var6) {
         }

         System.out.println("DeliveryList: Prop [" + propName + "]" + action + ".");
         return ret;
      }
   }
}
