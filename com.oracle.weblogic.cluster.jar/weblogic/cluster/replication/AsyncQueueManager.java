package weblogic.cluster.replication;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class AsyncQueueManager implements QueueManager, PropertyChangeListener {
   private static final int DEFAULT_MIN_CONCURRENCY = 1;
   private static final int DEFAULT_MAX_CONCURRENCY = 1;
   private long asyncSessionQueueTimeout;
   private int UPDATE_SIZE;
   private BlockingQueue updateSet;
   private WorkManager workManager;
   private AsyncFlush flushManager;
   private int updateIndex = 0;
   private Timer updateTimer;
   private long timeAtLastUpdateFlush = 0L;
   private int sessionFlushInterval;
   private TimerManager sessionUpdateFlushTimerManager;
   private boolean greedy = false;
   private ResourceGroupKey resourceGroupKey;
   private final ReentrantLock lock = new ReentrantLock(true);

   AsyncQueueManager(AsyncFlush flushManager, boolean greedy) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      this.UPDATE_SIZE = cluster.getSessionFlushThreshold() == -1 ? 0 : cluster.getSessionFlushThreshold();
      if (greedy) {
         this.sessionFlushInterval = cluster.getGreedySessionFlushInterval() == -1 ? 0 : cluster.getGreedySessionFlushInterval() * 1000;
      } else {
         this.sessionFlushInterval = cluster.getSessionFlushInterval() == -1 ? 0 : cluster.getSessionFlushInterval() * 1000;
      }

      this.asyncSessionQueueTimeout = cluster.getAsyncSessionQueueTimeout() == -1 ? 0L : (long)cluster.getAsyncSessionQueueTimeout();
      cluster.addPropertyChangeListener(this);
      this.init(flushManager, greedy);
   }

   AsyncQueueManager(AsyncFlush flushManager, boolean greedy, int flushInterval, int flushThreshold, int queueTimeout) {
      this.sessionFlushInterval = flushInterval * 1000;
      this.UPDATE_SIZE = flushThreshold;
      this.asyncSessionQueueTimeout = (long)queueTimeout;
      this.init(flushManager, greedy);
   }

   private void init(AsyncFlush flushManager, boolean greedy) {
      this.workManager = WorkManagerFactory.getInstance().findOrCreate("ASYNC_REP_FLUSH_WM", 1, 1);
      this.updateSet = createUpdateSet(this.UPDATE_SIZE);
      this.flushManager = flushManager;
      this.greedy = greedy;
      this.sessionUpdateFlushTimerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("asyncSessionUpdateFlushTimerManager", this.workManager);
      this.updateTimer = this.scheduleSessionUpdateTimer();
   }

   public synchronized void propertyChange(PropertyChangeEvent event) {
      Integer sessionFlushTime;
      if ("SessionFlushThreshold".equals(event.getPropertyName())) {
         if (AsyncQueueDebugLogger.isDebugEnabled()) {
            AsyncQueueDebugLogger.debug("SessionFlushThreshold property change has occurred with new value: " + event.getNewValue() + " Resetting AsyncQueue max size.");
         }

         sessionFlushTime = (Integer)event.getNewValue();
         this.UPDATE_SIZE = sessionFlushTime;
      } else if ("SessionFlushInterval".equals(event.getPropertyName())) {
         if (AsyncQueueDebugLogger.isDebugEnabled()) {
            AsyncQueueDebugLogger.debug("SessionFlushInterval property change has occurred with new value: " + event.getNewValue() + " Will reset timer as appropriate.");
         }

         if (this.greedy) {
            return;
         }

         sessionFlushTime = (Integer)event.getNewValue();
         this.sessionFlushInterval = sessionFlushTime * 1000;
         this.updateTimer.cancel();
         this.updateTimer = this.scheduleSessionUpdateTimer();
      } else if ("GreedySessionFlushInterval".equals(event.getPropertyName())) {
         if (AsyncQueueDebugLogger.isDebugEnabled()) {
            AsyncQueueDebugLogger.debug("GreedySessionFlushInterval property change has occurred with new value: " + event.getNewValue() + " This will only change for Greedy Async Queues.");
         }

         if (!this.greedy) {
            return;
         }

         sessionFlushTime = (Integer)event.getNewValue();
         this.sessionFlushInterval = sessionFlushTime * 1000;
         this.updateTimer.cancel();
         this.updateTimer = this.scheduleSessionUpdateTimer();
      } else if ("AsyncSessionQueueTimeout".equals(event.getPropertyName())) {
         if (AsyncQueueDebugLogger.isDebugEnabled()) {
            AsyncQueueDebugLogger.debug("AsyncSessionQueueTimeout property change has occurred with new value: " + event.getNewValue() + " Resetting queue blocking timeout.");
         }

         sessionFlushTime = (Integer)event.getNewValue();
         this.asyncSessionQueueTimeout = (long)sessionFlushTime;
      }

   }

   private Timer scheduleSessionUpdateTimer() {
      if (AsyncQueueDebugLogger.isDebugEnabled()) {
         AsyncQueueDebugLogger.debug("Session Flush Interval " + this.sessionFlushInterval + "ms and threshold is " + this.UPDATE_SIZE);
      }

      return this.sessionUpdateFlushTimerManager.schedule(new SessionUpdateFlushTrigger(this, this.sessionFlushInterval), (long)this.sessionFlushInterval, (long)this.sessionFlushInterval);
   }

   private static BlockingQueue createUpdateSet(int defaultSize) {
      assert defaultSize >= 1;

      return new ArrayBlockingQueue(defaultSize);
   }

   public void addToUpdates(Object update) {
      try {
         if (!this.updateSet.offer(update, this.asyncSessionQueueTimeout, TimeUnit.SECONDS)) {
            ClusterExtensionLogger.logAsyncReplicationRequestTimeout(update.toString());
            return;
         }

         ++this.updateIndex;
         boolean flushSessions = this.updateIndex == this.UPDATE_SIZE;
         if (flushSessions) {
            if (AsyncQueueDebugLogger.isDebugEnabled()) {
               AsyncQueueDebugLogger.debug("The AsyncQueue has reached its maximum size and will schedule a flush");
            }

            this.workManager.schedule(new FlushWork(this));
         }
      } catch (InterruptedException var3) {
         this.addToUpdates(update);
      }

   }

   public long getTimeAtLastUpdateFlush() {
      return this.timeAtLastUpdateFlush;
   }

   public int getQueueSize() {
      return this.updateSet != null ? this.updateSet.size() : 0;
   }

   public Iterator iterator() {
      return this.updateSet.iterator();
   }

   public void remove(Object update) {
      this.updateSet.remove(update);
   }

   public void flushOnce() {
      if (!this.updateSet.isEmpty()) {
         this.lock.lock();

         try {
            this.updateIndex = 0;
            this.timeAtLastUpdateFlush = System.currentTimeMillis();
            this.flushManager.flushQueue(this.updateSet, this.resourceGroupKey);
         } finally {
            this.lock.unlock();
         }

      }
   }

   public void flush() {
      if (AsyncQueueDebugLogger.isDebugEnabled()) {
         AsyncQueueDebugLogger.debug("AsyncQueueManager flushing with queue size: " + this.updateSet.size() + " for flushManager: " + this.flushManager);
      }

      this.flushOnce();
      if (this.greedy && !this.updateSet.isEmpty()) {
         AsyncQueueDebugLogger.debug("greedy flush again");
         this.flush();
      }

   }

   protected void setResourceGroupKey(ResourceGroupKey resourceGroup) {
      this.resourceGroupKey = resourceGroup;
   }

   protected ResourceGroupKey getResourceGroupKey() {
      return this.resourceGroupKey;
   }

   private static final class FlushWork implements Runnable {
      private final AsyncQueueManager manager;

      private FlushWork(AsyncQueueManager manager) {
         this.manager = manager;
      }

      public void run() {
         this.manager.flush();
      }

      // $FF: synthetic method
      FlushWork(AsyncQueueManager x0, Object x1) {
         this(x0);
      }
   }

   private static final class SessionUpdateFlushTrigger implements NakedTimerListener {
      private final AsyncQueueManager manager;
      private final int flushPeriod;

      private SessionUpdateFlushTrigger(AsyncQueueManager manager, int flushPeriod) {
         this.manager = manager;
         this.flushPeriod = flushPeriod;
      }

      public void timerExpired(Timer timer) {
         if (System.currentTimeMillis() - this.manager.getTimeAtLastUpdateFlush() > (long)this.flushPeriod) {
            this.manager.flush();
         }

      }

      // $FF: synthetic method
      SessionUpdateFlushTrigger(AsyncQueueManager x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
