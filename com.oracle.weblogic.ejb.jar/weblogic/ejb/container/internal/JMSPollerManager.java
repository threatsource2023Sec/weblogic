package weblogic.ejb.container.internal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.work.WorkManager;

final class JMSPollerManager {
   private static final String TIMER_MANAGER_NAME = "JMSPoller-";
   private static final DebugLogger debugLogger;
   private final TokenBasedJMSMessagePoller[] allPollers;
   private final List availablePollers = Collections.synchronizedList(new LinkedList());
   private final WorkManager wm;
   private final TimerManager timerMgr;
   private final AtomicInteger token = new AtomicInteger(-1);
   private volatile boolean hasErrors;
   private final Object numErrorsLock = new Object();
   private int numErrors = 0;

   JMSPollerManager(String mdbName, JMSConnectionPoller cp, MessageConsumer[] mc, MDListener[] mdl, WorkManager wm, boolean continuous, Destination dest, boolean dynamicSessionClose) {
      this.wm = wm;

      for(int i = 0; i < mc.length; ++i) {
         TokenBasedJMSMessagePoller poller = new TokenBasedJMSMessagePoller(i, mdbName, cp, this, mc[i], mdl[i], continuous, dest, dynamicSessionClose);
         this.availablePollers.add(poller);
      }

      this.allPollers = (TokenBasedJMSMessagePoller[])this.availablePollers.toArray(new TokenBasedJMSMessagePoller[this.availablePollers.size()]);
      this.timerMgr = TimerManagerFactory.getTimerManagerFactory().getTimerManager("JMSPoller-" + mdbName + this, wm);
   }

   synchronized void stop() {
      TokenBasedJMSMessagePoller[] var1 = this.allPollers;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TokenBasedJMSMessagePoller poller = var1[var3];
         poller.stop();
      }

   }

   synchronized void start() {
      TokenBasedJMSMessagePoller[] var1 = this.allPollers;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TokenBasedJMSMessagePoller poller = var1[var3];
         poller.start();
      }

      int id = this.getTokenHolderId();
      if (id != -1) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Start found token holder" + this.allPollers[id] + "- scheduling");
         }

         this.wm.schedule(this.allPollers[id]);
      } else {
         this.wakeUpPoller((TokenBasedJMSMessagePoller)null, this.allPollers[0]);
      }

   }

   void waitForPollersToStop(long waitTime) {
      long startTime = System.currentTimeMillis();
      int counter = 0;
      int size = this.allPollers.length;

      do {
         if (debugLogger.isDebugEnabled()) {
            this.debug("waitForPollersToStop() size : " + size + ", availablePollers size : " + this.availablePollers.size());
            Debug.assertion(this.availablePollers.size() <= size, "JMSPollerManager.waitForPollersToStop() availablePollers.size() " + this.availablePollers.size() + " > " + size + " leaking pollers");
         }

         try {
            Thread.sleep(1000L);
            if (waitTime > 0L) {
               ++counter;
               if (counter % 10 == 0 && System.currentTimeMillis() > startTime + waitTime) {
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("waitForPollersToStop() size : " + size + ", availablePollers size : " + this.availablePollers.size() + " waitTime elapsed " + waitTime);
                  }
                  break;
               }
            }
         } catch (InterruptedException var8) {
         }
      } while(this.availablePollers.size() < size);

   }

   synchronized void wakeUpPoller(TokenBasedJMSMessagePoller curOwner, TokenBasedJMSMessagePoller newPoller) {
      int currentId = curOwner != null ? curOwner.getId() : -1;
      if (newPoller == null) {
         newPoller = this.getFromPool((TokenBasedJMSMessagePoller)null);
      } else {
         newPoller = this.getFromPool(newPoller);

         assert newPoller != null : "The new poller parameter of the wakeUpPoller method should be in the available poller list";
      }

      if (newPoller != null) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Scheduling new token holder :" + newPoller);
         }

         this.changeOwner(currentId, newPoller.getId());
         this.wm.schedule(newPoller);
      } else {
         if (debugLogger.isDebugEnabled()) {
            this.debug("All pollers busy, releasing token from :" + curOwner);
         }

         this.releaseToken(currentId);
      }

   }

   private TokenBasedJMSMessagePoller getFromPool(TokenBasedJMSMessagePoller poller) {
      if (this.availablePollers.isEmpty()) {
         return null;
      } else if (poller == null) {
         return (TokenBasedJMSMessagePoller)this.availablePollers.remove(0);
      } else {
         return this.availablePollers.remove(poller) ? poller : null;
      }
   }

   void returnToPool(TokenBasedJMSMessagePoller poller) {
      this.availablePollers.add(poller);
   }

   void scheduleTimer(TimerListener listener, long delay) {
      this.timerMgr.schedule(listener, delay);
   }

   boolean scheduleIfBusy(TokenBasedJMSMessagePoller runnable) {
      return this.wm.scheduleIfBusy(runnable);
   }

   int getBatchSize(int listenerBatchSize) {
      int retVal = listenerBatchSize;
      if (this.hasErrors) {
         synchronized(this.numErrorsLock) {
            if (this.numErrors > 0) {
               --this.numErrors;
               retVal = 1;
               if (this.numErrors == 0) {
                  this.hasErrors = false;
               }
            }
         }
      }

      return retVal;
   }

   void notifyError(int batchSize) {
      this.hasErrors = true;
      synchronized(this.numErrorsLock) {
         this.numErrors += batchSize;
      }
   }

   int getTokenHolderId() {
      return this.token.get();
   }

   boolean holdsToken(int id) {
      return this.token.get() == id;
   }

   boolean acquireToken(int id) {
      return this.token.compareAndSet(-1, id);
   }

   boolean releaseToken(int id) {
      return this.token.compareAndSet(id, -1);
   }

   private boolean changeOwner(int from, int to) {
      return this.token.compareAndSet(from, to);
   }

   private void debug(String s) {
      debugLogger.debug("[JMSPollerManager] " + s);
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }
}
