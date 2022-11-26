package weblogic.ejb.container.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.SingleInstanceCache;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public final class NRUCache implements SingleInstanceCache, TimerListener {
   private static final DebugLogger DEBUG_LOGGER;
   private static final int MIN_CAPACITY = 8;
   private final Map cache = new HashMap();
   private final Queue freeQueue = new Queue();
   private final Queue activeQueue = new Queue();
   private final Queue inActiveQueue = new Queue();
   private final String cacheName;
   private final CacheScrubberTimer scrubberTimer;
   private final boolean hasStatefulTimeoutConfig;
   private final BeanManager beanManager;
   private final long idleTimeoutMillis;
   private int maxCacheSize = 0;
   private int currentCacheSize = 0;
   private int scrubCount = 0;
   private int minFreeSize;
   private int targetFreeSize;
   private int targetInactiveSize;

   public NRUCache(String cacheName, int maxBeansInCache, boolean hasStatefulTimeoutConfig, int idleTimeoutSeconds, int scrubTimeoutSeconds, BeanManager bm) {
      this.maxCacheSize = maxBeansInCache;
      this.cacheName = cacheName;
      this.hasStatefulTimeoutConfig = hasStatefulTimeoutConfig;
      this.idleTimeoutMillis = (long)idleTimeoutSeconds * 1000L;
      this.beanManager = bm;
      this.updateCacheValues();
      long intervMillis = scrubTimeoutSeconds > 0 ? (long)scrubTimeoutSeconds * 1000L : 0L;
      this.scrubberTimer = new CacheScrubberTimer(this, intervMillis, cacheName);
   }

   public void updateIdleTimeoutSeconds(int idleTimeoutSeconds) {
      this.scrubberTimer.resetScrubInterval((long)idleTimeoutSeconds * 1000L);
   }

   private void updateCacheValues() {
      this.maxCacheSize = Math.max(8, this.maxCacheSize);
      this.minFreeSize = Math.min(this.maxCacheSize / 8, 20);
      this.targetFreeSize = Math.min(this.maxCacheSize / 8, 10);
      this.targetInactiveSize = this.maxCacheSize / 8;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Cache values for '" + this.cacheName + "': [ MaxCacheSize: " + this.maxCacheSize + " MinFreeSize: " + this.minFreeSize + " TargetFreeSize: " + this.targetFreeSize + " TargetInactiveSize: " + this.targetInactiveSize + "]");
      }

   }

   public synchronized Object get(CacheKey key) {
      Node n = (Node)this.cache.get(key);
      if (n == null) {
         return null;
      } else {
         if (n.isFree()) {
            n.setActive();
            n.getCallback().swapIn(key, n.getBean());
            this.freeQueue.remove(n);
            this.activeQueue.push(n);
         } else if (n.isInActive()) {
            n.setActive();
            this.inActiveQueue.remove(n);
            this.activeQueue.push(n);
         }

         n.pin();
         return n.getBean();
      }
   }

   private Node getFreeNode() throws CacheFullException {
      Node n = null;
      boolean nodeRemoved;
      Object removeBean;
      CacheKey removeKey;
      if (this.freeQueue.size() >= 1) {
         for(nodeRemoved = false; !nodeRemoved; nodeRemoved = true) {
            n = this.freeQueue.pop();
            removeBean = n.getBean();
            if (removeBean != null) {
               removeKey = n.getKey();
               this.cache.remove(removeKey);
               --this.currentCacheSize;
               n.getCallback().removedFromCache(removeKey, removeBean);
            }
         }
      }

      if (this.currentCacheSize + 1 <= this.maxCacheSize) {
         if (n == null) {
            n = new Node();
         }
      } else {
         this.reclaimNodes(this.currentCacheSize + 1 - this.maxCacheSize);
         if (this.freeQueue.size() < 1) {
            throw new CacheFullException("Cache '" + this.cacheName + "' is at its limit of: " + this.maxCacheSize + " *active* beans.");
         }

         for(nodeRemoved = false; !nodeRemoved; nodeRemoved = true) {
            n = this.freeQueue.pop();
            removeBean = n.getBean();
            if (removeBean != null) {
               removeKey = n.getKey();
               this.cache.remove(removeKey);
               --this.currentCacheSize;
               n.getCallback().removedFromCache(removeKey, removeBean);
            }
         }

         if (n == null) {
            n = new Node();
         }
      }

      return n;
   }

   public synchronized void put(CacheKey key, Object bean) throws CacheFullException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Putting key: " + key + " into cache, current size: " + this.currentCacheSize);
      }

      assert this.cache.get(key) == null;

      Node n = this.getFreeNode();
      n.setBean(bean);
      n.setKey(key);
      n.setActive();
      n.pin();
      this.activeQueue.push(n);
      Node old = (Node)this.cache.put(key, n);
      ++this.currentCacheSize;

      assert old == null : "Adding bean:" + bean + " with key: " + key + " that was already in cache '" + this.cacheName + "'.";

   }

   public synchronized void release(CacheKey key) {
      Node n = (Node)this.cache.get(key);
      if (n != null) {
         assert n.isActive() && n.pinned();

         if (this.hasStatefulTimeoutConfig) {
            n.touch();
         }

         n.unpin();
      }

   }

   public synchronized void remove(CacheKey key) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Removing key: " + key);
      }

      Node n = (Node)this.cache.remove(key);
      if (n != null) {
         --this.currentCacheSize;
         n.getCallback().removedFromCache(key, n.getBean());

         assert n.getKey().equals(key);

         if (n.pinned()) {
            n.unpin();
         }

         if (n.isActive()) {
            this.activeQueue.remove(n);
            n.setFree();
            this.freeQueue.push(n);
         } else if (n.isInActive()) {
            this.inActiveQueue.remove(n);
            n.setFree();
            this.freeQueue.push(n);
         }

         n.setBean((Object)null);
         n.setKey((CacheKey)null);
      }
   }

   public synchronized void clear() {
      Iterator var1 = this.cache.keySet().iterator();

      while(var1.hasNext()) {
         CacheKey eachKey = (CacheKey)var1.next();
         Object bean = this.get(eachKey);

         try {
            eachKey.getCallback().doEjbRemove(bean);
         } catch (Throwable var5) {
            EJBLogger.logExceptionDuringEJBRemove(var5);
         }
      }

      this.cache.clear();
      this.currentCacheSize = 0;
   }

   public void startScrubber() {
      this.scrubCount = 0;
      this.scrubberTimer.startScrubber();
   }

   public void stopScrubber() {
      this.scrubberTimer.stopScrubber();
   }

   public void timerExpired(Timer timer) {
      ManagedInvocationContext mic = this.beanManager.getBeanInfo().setCIC();
      Throwable var3 = null;

      try {
         this.scrubCache(true);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private int scrubCache(boolean idleTimeout) {
      if (idleTimeout) {
         int freed = 0;
         if (this.hasStatefulTimeoutConfig) {
            freed += this.enforceStatefulTimeout();
            if (++this.scrubCount % 4 != 0) {
               return freed;
            }
         }

         return freed + this.legacyScrubber();
      } else {
         return this.newScrubber(idleTimeout);
      }
   }

   private synchronized int legacyScrubber() {
      int freeSize = this.freeQueue.size() + (this.maxCacheSize - this.currentCacheSize);
      return freeSize < this.minFreeSize ? this.reclaimNodes(this.targetFreeSize) : 0;
   }

   private synchronized int newScrubber(boolean idleTimeout) {
      int freed = 0;
      int i = 0;

      int queueSize;
      Node n;
      for(queueSize = this.activeQueue.size(); i < queueSize; ++i) {
         n = this.activeQueue.pop();
         if (n.pinned()) {
            this.activeQueue.push(n);
         } else if (!idleTimeout || this.idleTimeoutMillis > 0L && n.idleLongerThan(this.idleTimeoutMillis)) {
            this.removeNode(n);
            ++freed;
            n.getCallback().removedFromCache(n.getKey(), n.getBean());
         } else {
            this.activeQueue.push(n);
         }
      }

      i = 0;

      for(queueSize = this.inActiveQueue.size(); i < queueSize; ++i) {
         n = this.inActiveQueue.pop();
         if (!n.pinned()) {
            if (!idleTimeout || this.idleTimeoutMillis > 0L && n.idleLongerThan(this.idleTimeoutMillis)) {
               this.removeNode(n);
               ++freed;
               n.getCallback().removedFromCache(n.getKey(), n.getBean());
            } else {
               this.inActiveQueue.push(n);
            }
         }
      }

      i = 0;

      for(queueSize = this.freeQueue.size(); i < queueSize; ++i) {
         n = this.freeQueue.pop();
         if (n.getBean() != null) {
            if (idleTimeout && (this.idleTimeoutMillis <= 0L || !n.idleLongerThan(this.idleTimeoutMillis))) {
               this.freeQueue.push(n);
               continue;
            }

            n.getCallback().removedFromCache(n.getKey(), n.getBean());
         }

         this.removeNode(n);
         ++freed;
      }

      return freed;
   }

   public void reInitializeCacheAndPools() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " reInitializeCacheAndPools, size is " + this.cache.size());
      }

      this.beanManager.reInitializePool();
      this.scrubCache(false);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " cache reInitialization complete, size is " + this.cache.size());
      }

   }

   public void beanImplClassChangeNotification() {
      List removedNodes = new ArrayList();
      synchronized(this) {
         int i = 0;

         int queueSize;
         Node n;
         for(queueSize = this.freeQueue.size(); i < queueSize; ++i) {
            n = this.freeQueue.pop();
            this.removeNode(n);
            n.getCallback().removedFromCache(n.getKey(), n.getBean());
         }

         i = 0;

         for(queueSize = this.inActiveQueue.size(); i < queueSize; ++i) {
            n = this.inActiveQueue.pop();
            this.removeNode(n);
            removedNodes.add(n);
         }

         i = 0;
         queueSize = this.activeQueue.size();

         while(true) {
            if (i >= queueSize) {
               break;
            }

            n = this.activeQueue.pop();
            if (!n.pinned()) {
               this.removeNode(n);
               removedNodes.add(n);
            } else {
               this.activeQueue.push(n);
            }

            ++i;
         }
      }

      Iterator var2 = removedNodes.iterator();

      while(var2.hasNext()) {
         Node n = (Node)var2.next();
         CachingManager callback = n.getCallback();
         callback.swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
         callback.removedFromCache(n.getKey(), n.getBean());
      }

   }

   public synchronized void updateMaxBeansInCache(int max) {
      this.maxCacheSize = max;
      this.updateCacheValues();
      this.scrubCache(true);
   }

   private void removeNode(Node n) {
      if (n.getBean() != null) {
         this.cache.remove(n.getKey());
         --this.currentCacheSize;
      }

   }

   private void moveActiveToInactive() {
      for(int scanSize = this.activeQueue.size(); scanSize > 0; --scanSize) {
         if (this.inActiveQueue.size() >= this.targetInactiveSize) {
            return;
         }

         Node n = this.activeQueue.pop();
         if (n.pinned()) {
            this.activeQueue.push(n);
         } else {
            n.touch();
            n.setInActive();
            this.inActiveQueue.push(n);
         }
      }

   }

   private synchronized int enforceStatefulTimeout() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Enforcing stateful-timeout: looking for expired beans in cache.");
      }

      int freed = 0;

      int scanSize;
      Node n;
      for(scanSize = this.activeQueue.size(); scanSize > 0; --scanSize) {
         n = this.activeQueue.pop();
         if (!n.pinned() && n.idleLongerThan(this.idleTimeoutMillis)) {
            this.removeBean(n);
            ++freed;
         } else {
            this.activeQueue.push(n);
         }
      }

      for(scanSize = this.inActiveQueue.size(); scanSize > 0; --scanSize) {
         n = this.inActiveQueue.pop();
         if (n.idleLongerThan(this.idleTimeoutMillis)) {
            this.removeBean(n);
            ++freed;
         } else {
            this.inActiveQueue.push(n);
         }
      }

      for(scanSize = this.freeQueue.size(); scanSize > 0; --scanSize) {
         n = this.freeQueue.pop();
         if (n.getCallback() != null && !n.idleLongerThan(this.idleTimeoutMillis)) {
            this.freeQueue.push(n);
         } else {
            Object removeBean = n.getBean();
            if (removeBean != null) {
               CacheKey removeKey = n.getKey();
               this.cache.remove(removeKey);
               --this.currentCacheSize;
               n.getCallback().removedFromCache(removeKey, removeBean);
            }

            ++freed;
         }
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Enforcing stateful-timeout: removed " + freed + " beans.");
      }

      return freed;
   }

   private void removeBean(Node n) {
      CacheKey key = n.getKey();
      this.cache.remove(key);
      --this.currentCacheSize;
      Object theBean = n.getBean();

      try {
         n.getCallback().doEjbRemove(theBean);
      } catch (Throwable var5) {
         EJBLogger.logExceptionDuringEJBRemove(var5);
      }

      n.getCallback().removedFromCache(key, theBean);
      n.setBean((Object)null);
      n.setKey((CacheKey)null);
   }

   private int moveInActiveToFree(int target) {
      int freed = 0;

      while(this.freeQueue.size() < target) {
         Node n = this.inActiveQueue.pop();
         if (n == null) {
            return freed;
         }

         if (n.idleLongerThan(this.idleTimeoutMillis)) {
            this.removeBean(n);
            ++freed;
         } else {
            assert !n.pinned();

            assert n.getBean() != null;

            assert n.getKey() != null;

            ++freed;
            n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
         }

         n.setFree();
         this.freeQueue.push(n);
      }

      return freed;
   }

   private int reclaimNodes(int targetSize) {
      int freed = this.moveInActiveToFree(targetSize);
      this.moveActiveToInactive();
      if (this.freeQueue.size() == 0) {
         freed += this.moveInActiveToFree(this.targetFreeSize);
      }

      return freed;
   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[NRUCache] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.cachingLogger;
   }

   private static final class Queue {
      private Node head;
      private Node tail;
      private int size;

      private Queue() {
      }

      void remove(Node n) {
         --this.size;
         if (this.head == n) {
            this.head = n.next;
         } else {
            n.prev.next = n.next;
         }

         if (this.tail == n) {
            this.tail = n.prev;
         } else {
            n.next.prev = n.prev;
         }

      }

      int size() {
         return this.size;
      }

      void push(Node n) {
         ++this.size;
         if (this.tail == null) {
            this.head = n;
            this.tail = n;
            n.prev = null;
            n.next = null;
         } else {
            this.tail.next = n;
            n.prev = this.tail;
            n.next = null;
            this.tail = n;
         }

      }

      Node pop() {
         if (this.head == null) {
            return null;
         } else {
            --this.size;
            Node n = this.head;
            this.head = this.head.next;
            if (this.head == null) {
               this.tail = null;
            } else {
               this.head.prev = null;
            }

            return n;
         }
      }

      // $FF: synthetic method
      Queue(Object x0) {
         this();
      }
   }
}
