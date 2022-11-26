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
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.Debug;

public final class EntityNRUCache implements SingleInstanceCache, TimerListener {
   private static final DebugLogger DEBUG_LOGGER;
   private static final int MIN_CAPACITY = 8;
   private final Map cache;
   private final Queue freeQueue;
   private final Queue activeQueue;
   private final Queue inActiveQueue;
   private final String cacheName;
   private final CacheScrubberTimer scrubberTimer;
   private final boolean usesMaxBeansInCache;
   private final List cachingManagers;
   private long maxCacheSize;
   private long currentCacheSize;
   private long minFreeSize;
   private long targetFreeSize;
   private long targetInactiveSize;
   private int maxBeanSize;
   private long scrubIntervalMillisDD;
   private long scrubIntervalMillis;

   private EntityNRUCache(String cacheName, long maxCacheSize, boolean usesMaxBeansInCache) {
      this.cache = new HashMap();
      this.freeQueue = new Queue();
      this.activeQueue = new Queue();
      this.inActiveQueue = new Queue();
      this.cachingManagers = new ArrayList();
      this.maxCacheSize = 0L;
      this.currentCacheSize = 0L;
      this.maxBeanSize = 0;
      this.maxCacheSize = maxCacheSize;
      this.usesMaxBeansInCache = usesMaxBeansInCache;
      this.cacheName = cacheName;
      this.scrubberTimer = new CacheScrubberTimer(this, 0L, cacheName);
   }

   public EntityNRUCache(String cacheName, int maxBeansInCache) {
      this(cacheName, (long)maxBeansInCache, true);
   }

   public EntityNRUCache(String cacheName, long maxCacheSize) {
      this(cacheName, maxCacheSize, false);
   }

   public boolean usesMaxBeansInCache() {
      return this.usesMaxBeansInCache;
   }

   public void updateIdleTimeoutSeconds(int idleTimeoutSeconds) {
      this.scrubberTimer.resetScrubInterval((long)idleTimeoutSeconds * 1000L);
   }

   public void register(CachingManager cm) {
      if (!this.cachingManagers.contains(cm)) {
         this.cachingManagers.add(cm);
      }

      this.maxBeanSize = Math.max(this.maxBeanSize, cm.getBeanSize());
      this.updateCacheValues();
   }

   private void updateCacheValues() {
      this.maxCacheSize = Math.max((long)(8 * this.maxBeanSize), this.maxCacheSize);
      this.minFreeSize = Math.min(this.maxCacheSize / 8L, (long)(20 * this.maxBeanSize));
      this.targetFreeSize = Math.min(this.maxCacheSize / 8L, (long)(10 * this.maxBeanSize));
      this.targetInactiveSize = this.maxCacheSize / 8L;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Cache values for '" + this.cacheName + "': [ MaxCacheSize: " + this.maxCacheSize + " MaxBeanSize: " + this.maxBeanSize + " MinFreeSize: " + this.minFreeSize + " TargetFreeSize: " + this.targetFreeSize + " TargetInactiveSize: " + this.targetInactiveSize + "]");
      }

   }

   public synchronized Object get(CacheKey key, boolean pin) {
      assert this.validateDataStructures();

      Node n = (Node)this.cache.get(key);
      if (n == null) {
         assert this.findKeyInQueues(key) == null;

         return null;
      } else {
         if (n.isFree()) {
            assert this.freeQueue.contains(n);

            n.setActive();
            n.getCallback().swapIn(key, n.getBean());
            this.freeQueue.remove(n);
            this.activeQueue.push(n);
         } else if (n.isInActive()) {
            assert this.inActiveQueue.contains(n);

            n.setActive();
            this.inActiveQueue.remove(n);
            this.activeQueue.push(n);
         } else {
            assert this.activeQueue.contains(n);
         }

         assert this.activeQueue.contains(n);

         assert this.validateDataStructures();

         if (pin) {
            n.pin();
         }

         return n.getBean();
      }
   }

   public Object get(CacheKey key) {
      return this.get(key, true);
   }

   private Node getFreeNode(int neededSize) throws CacheFullException {
      Node n = null;
      int removeSize;
      CacheKey removeKey;
      Object removeBean;
      if (this.freeQueue.size() >= (long)neededSize) {
         for(removeSize = 0; removeSize < neededSize; removeSize += n.getSize()) {
            n = this.freeQueue.pop();
            removeKey = n.getKey();
            removeBean = n.getBean();
            if (removeBean != null) {
               this.cache.remove(removeKey);
               this.currentCacheSize -= (long)n.getSize();
               n.getCallback().removedFromCache(removeKey, removeBean);
            }
         }
      }

      if (this.currentCacheSize + (long)neededSize <= this.maxCacheSize) {
         if (n == null) {
            n = new Node();
         }
      } else {
         this.reclaimNodes(this.currentCacheSize + (long)neededSize - this.maxCacheSize);
         if (this.freeQueue.size() < (long)neededSize) {
            throw new CacheFullException("Cache '" + this.cacheName + "' is at its limit of: " + this.maxCacheSize + " *active* " + (this.usesMaxBeansInCache() ? "beans" : "bytes") + ".");
         }

         for(removeSize = 0; removeSize < neededSize; removeSize += n.getSize()) {
            n = this.freeQueue.pop();
            removeKey = n.getKey();
            removeBean = n.getBean();
            if (removeBean != null) {
               this.cache.remove(removeKey);
               this.currentCacheSize -= (long)n.getSize();
               n.getCallback().removedFromCache(removeKey, removeBean);
            }
         }

         if (n == null) {
            n = new Node();
         }
      }

      return n;
   }

   private void initializeNode(Object bean, CacheKey key, Node n) {
      n.setBean(bean);
      n.setKey(key);
      n.setActive();
      n.pin();
      this.activeQueue.push(n);
      Node old = (Node)this.cache.put(key, n);
      this.currentCacheSize += (long)n.getSize();

      assert old == null : "Adding bean:" + bean + " with key: " + key + " that was already in cache '" + this.cacheName + "' .";

      assert this.cache.get(key) == n;

      assert n.getKey().equals(key);

      assert this.validateDataStructures();

   }

   public synchronized void put(CacheKey key, Object bean) throws CacheFullException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Putting key: " + key + " into cache, current size is: " + this.currentCacheSize);
      }

      assert this.validateDataStructures();

      assert this.cache.get(key) == null;

      Node n = this.getFreeNode(key.getCallback().getBeanSize());
      this.initializeNode(bean, key, n);

      assert this.validateDataStructures();

   }

   public synchronized void release(CacheKey key) {
      assert this.validateDataStructures();

      Node n = (Node)this.cache.get(key);
      if (n != null) {
         assert n.isActive() && n.pinned();

         n.unpin();
         if (n.getCallback().needsRemoval(n.getBean())) {
            this.remove(key);
         }

         n.touch();

         assert this.validateDataStructures();

      }
   }

   public synchronized void remove(CacheKey key) {
      this.remove(key, false);
   }

   public synchronized void removeOnError(CacheKey key) {
      this.remove(key, true);
   }

   private void remove(CacheKey key, boolean onError) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Removing key: " + key);
      }

      assert this.validateDataStructures();

      Node n = (Node)this.cache.remove(key);
      if (n != null) {
         this.currentCacheSize -= (long)n.getSize();
         if (onError) {
            n.getCallback().removedOnError(key, n.getBean());
         } else {
            n.getCallback().removedFromCache(key, n.getBean());
         }

         assert n.getKey().equals(key);

         if (n.pinned()) {
            n.unpin();
         }

         if (n.isActive()) {
            assert this.activeQueue.contains(n);

            this.activeQueue.remove(n);
            n.setFree();
            this.freeQueue.push(n);
         } else if (n.isInActive()) {
            assert this.inActiveQueue.contains(n);

            this.inActiveQueue.remove(n);
            n.setFree();
            this.freeQueue.push(n);
         } else {
            assert this.freeQueue.contains(n);
         }

         n.setBean((Object)null);
         n.setKey((CacheKey)null);

         assert this.findKeyInQueues(key) == null;

         assert this.validateDataStructures();

      }
   }

   public void setScrubInterval(int seconds) {
      if (seconds > 0) {
         long l = (long)seconds * 1000L;
         if (this.scrubIntervalMillisDD <= 0L || l < this.scrubIntervalMillisDD) {
            this.scrubIntervalMillisDD = l;
         }

         this.scrubIntervalMillis = this.scrubIntervalMillisDD;
         this.scrubberTimer.setScrubInterval(this.scrubIntervalMillisDD);
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
      this.currentCacheSize = 0L;
   }

   public void startScrubber() {
      this.scrubberTimer.startScrubber();
   }

   public void stopScrubber() {
      this.scrubberTimer.stopScrubber();
   }

   public void timerExpired(Timer timer) {
      this.cacheScrubber();
   }

   private void cacheScrubber() {
      int freed = this.newScrubber(true);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " after cache scrub, scrubbed " + freed + " beans.");
      }

      if (freed <= 0) {
         if (this.scrubIntervalMillis < 120000L) {
            this.scrubIntervalMillis += this.scrubIntervalMillis;
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug(this.cacheName + "   scrubIntervalMillis: " + this.scrubIntervalMillis + " is less than 2 minutes and we've scrubbed no beans. Doubling the interval till next scrubbing to " + this.scrubIntervalMillis);
            }

            this.scrubberTimer.stopScrubber();
            this.scrubberTimer.setScrubInterval(this.scrubIntervalMillis);
            this.scrubberTimer.startScrubber();
         }
      } else if (this.scrubIntervalMillis != this.scrubIntervalMillisDD) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug(this.cacheName + "   scrubIntervalMillis: " + this.scrubIntervalMillis + " is not equal to the deployed value and we've scrubbed some beans during this scrubbing event.  Resetting scrubInterval to it's deployed value: " + this.scrubIntervalMillisDD);
         }

         this.scrubIntervalMillis = this.scrubIntervalMillisDD;
         this.scrubberTimer.stopScrubber();
         this.scrubberTimer.setScrubInterval(this.scrubIntervalMillis);
         this.scrubberTimer.startScrubber();
      }

   }

   private int newScrubber(boolean idleTimeout) {
      List removedNodes = new ArrayList();
      int freed = 0;
      synchronized(this) {
         int queueSize = (int)this.activeQueue.size();

         int i;
         Node n;
         int timeoutSecs;
         for(i = 0; i < queueSize; ++i) {
            n = this.activeQueue.pop();
            if (!n.pinned()) {
               if (idleTimeout) {
                  timeoutSecs = n.getCallback().getIdleTimeoutSeconds();
                  if (timeoutSecs <= 0) {
                     this.activeQueue.push(n);
                     continue;
                  }

                  if (!n.idleLongerThan((long)(timeoutSecs * 1000))) {
                     this.activeQueue.push(n);
                     continue;
                  }
               }

               this.removeNode(n);
               removedNodes.add(n);
               ++freed;
            } else {
               this.activeQueue.push(n);
            }
         }

         queueSize = (int)this.inActiveQueue.size();

         for(i = 0; i < queueSize; ++i) {
            n = this.inActiveQueue.pop();
            if (!n.pinned()) {
               if (idleTimeout) {
                  timeoutSecs = n.getCallback().getIdleTimeoutSeconds();
                  if (timeoutSecs <= 0) {
                     this.inActiveQueue.push(n);
                     continue;
                  }

                  if (!n.idleLongerThan((long)(timeoutSecs * 1000))) {
                     this.inActiveQueue.push(n);
                     continue;
                  }
               }

               this.removeNode(n);
               removedNodes.add(n);
               ++freed;
            }
         }

         queueSize = (int)this.freeQueue.size();

         for(i = 0; i < queueSize; ++i) {
            n = this.freeQueue.pop();
            if (n.getBean() != null) {
               if (idleTimeout) {
                  timeoutSecs = n.getCallback().getIdleTimeoutSeconds();
                  if (timeoutSecs <= 0) {
                     this.freeQueue.push(n);
                     continue;
                  }

                  if (!n.idleLongerThan((long)(timeoutSecs * 1000))) {
                     this.freeQueue.push(n);
                     continue;
                  }
               }

               n.getCallback().removedFromCache(n.getKey(), n.getBean());
            }

            this.removeNode(n);
            ++freed;
         }
      }

      Iterator var4 = removedNodes.iterator();

      while(var4.hasNext()) {
         Node n = (Node)var4.next();
         CachingManager callback = n.getCallback();
         callback.swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
         callback.removedFromCache(n.getKey(), n.getBean());
      }

      return freed;
   }

   public void reInitializeCacheAndPools() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " reInitializeCacheAndPools, size is " + this.cache.size());
      }

      Iterator var1 = this.cachingManagers.iterator();

      while(var1.hasNext()) {
         CachingManager cm = (CachingManager)var1.next();
         ((BeanManager)cm).reInitializePool();
      }

      this.newScrubber(false);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " cache reInitialization complete, size is " + this.cache.size());
      }

   }

   public void beanImplClassChangeNotification() {
      List removedNodes = new ArrayList();
      synchronized(this) {
         long freeQueueSize = this.freeQueue.size();

         long inActiveQueueSize;
         for(inActiveQueueSize = 0L; inActiveQueueSize < freeQueueSize; ++inActiveQueueSize) {
            Node n = this.freeQueue.pop();
            this.removeNode(n);
            n.getCallback().removedFromCache(n.getKey(), n.getBean());
         }

         inActiveQueueSize = this.inActiveQueue.size();

         long activeQueueSize;
         for(activeQueueSize = 0L; activeQueueSize < inActiveQueueSize; ++activeQueueSize) {
            Node n = this.inActiveQueue.pop();
            this.removeNode(n);
            removedNodes.add(n);
         }

         activeQueueSize = this.activeQueue.size();

         for(long i = 0L; i < activeQueueSize; ++i) {
            Node n = this.activeQueue.pop();
            if (!n.pinned()) {
               this.removeNode(n);
               removedNodes.add(n);
            } else {
               this.activeQueue.push(n);
            }
         }

         assert this.validateDataStructures();
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
      if (this.usesMaxBeansInCache()) {
         this.maxCacheSize = (long)max;
         this.updateCacheValues();
         this.cacheScrubber();
      }

   }

   public void updateMaxCacheSize(int max) {
      if (!this.usesMaxBeansInCache()) {
         this.maxCacheSize = (long)max;
         this.updateCacheValues();
         this.cacheScrubber();
      }

   }

   private void removeNode(Node n) {
      if (n.getBean() != null) {
         this.cache.remove(n.getKey());
         this.currentCacheSize -= (long)n.getSize();
      }

   }

   private void moveActiveToInactive() {
      Node n;
      for(long scanSize = this.activeQueue.size(); scanSize > 0L; scanSize -= (long)n.getSize()) {
         if (this.inActiveQueue.size() >= this.targetInactiveSize) {
            return;
         }

         n = this.activeQueue.pop();
         if (n.pinned()) {
            this.activeQueue.push(n);
         } else {
            n.setInActive();
            this.inActiveQueue.push(n);
         }
      }

   }

   private int moveInActiveToFree(long target) {
      int freed = 0;

      while(this.freeQueue.size() < target) {
         Node n = this.inActiveQueue.pop();
         if (n == null) {
            return freed;
         }

         assert !n.pinned();

         assert n.getBean() != null;

         assert n.getKey() != null;

         freed += n.getSize();
         n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
         n.setFree();
         this.freeQueue.push(n);
      }

      return freed;
   }

   private int reclaimNodes(long targetSize) {
      assert this.validateDataStructures();

      int freed = this.moveInActiveToFree(targetSize);
      this.moveActiveToInactive();
      if (this.freeQueue.size() == 0L) {
         freed += this.moveInActiveToFree(this.targetFreeSize);
      }

      return freed;
   }

   private boolean validateDataStructures() {
      int nodeCnt = 0;

      Node n;
      for(n = this.freeQueue.head; n != null; n = n.next) {
         ++nodeCnt;
      }

      for(n = this.inActiveQueue.head; n != null; n = n.next) {
         ++nodeCnt;
      }

      for(n = this.activeQueue.head; n != null; n = n.next) {
         ++nodeCnt;
      }

      if (nodeCnt < this.cache.size()) {
         throw new AssertionError("Node count was :" + nodeCnt + " but cache size is " + this.cache.size());
      } else {
         Debug.assertion(nodeCnt >= this.cache.size());
         Debug.assertion((long)nodeCnt <= this.maxCacheSize);
         Debug.assertion(this.freeQueue.size() >= 0L);
         Debug.assertion(this.inActiveQueue.size() >= 0L);
         Debug.assertion(this.activeQueue.size() >= 0L);
         Debug.assertion(this.currentCacheSize >= 0L);
         long listSize = this.freeQueue.size() + this.inActiveQueue.size() + this.activeQueue.size();
         if (listSize < this.currentCacheSize) {
            throw new AssertionError("listSize was :" + listSize + " but cache size is " + this.currentCacheSize);
         } else {
            long checkSize = 0L;

            int cnt;
            for(Iterator var6 = this.cache.keySet().iterator(); var6.hasNext(); Debug.assertion(cnt == 1)) {
               CacheKey key = (CacheKey)var6.next();
               Node n = (Node)this.cache.get(key);
               checkSize += (long)n.getSize();
               Debug.assertion(key == n.getKey());
               cnt = 0;
               if (this.activeQueue.contains(n)) {
                  ++cnt;
               }

               if (this.inActiveQueue.contains(n)) {
                  ++cnt;
               }

               if (this.freeQueue.contains(n)) {
                  ++cnt;
               }
            }

            Debug.assertion(checkSize == this.currentCacheSize);
            checkSize = 0L;

            Node n;
            Object bean;
            CacheKey key;
            for(n = this.activeQueue.head; n != null; n = n.next) {
               bean = n.getBean();
               Debug.assertion(bean != null);
               key = n.getKey();
               Debug.assertion(key != null);
               Debug.assertion(n == this.cache.get(key));
               checkSize += (long)n.getSize();
            }

            Debug.assertion(checkSize == this.activeQueue.size());
            checkSize = 0L;

            for(n = this.inActiveQueue.head; n != null; n = n.next) {
               bean = n.getBean();
               Debug.assertion(bean != null);
               key = n.getKey();
               Debug.assertion(key != null);
               Debug.assertion(n == this.cache.get(key));
               checkSize += (long)n.getSize();
            }

            Debug.assertion(checkSize == this.inActiveQueue.size());
            checkSize = 0L;

            for(n = this.freeQueue.head; n != null; n = n.next) {
               bean = n.getBean();
               if (bean != null) {
                  key = n.getKey();
                  Debug.assertion(key != null);
                  Debug.assertion(n == this.cache.get(key));
               }

               checkSize += (long)n.getSize();
            }

            Debug.assertion(checkSize == this.freeQueue.size());

            for(n = this.inActiveQueue.head; n != null; n = n.next) {
               Debug.assertion(!n.pinned());
            }

            for(n = this.freeQueue.head; n != null; n = n.next) {
               Debug.assertion(!n.pinned());
            }

            return true;
         }
      }
   }

   private Node findKeyInQueue(Queue q, CacheKey key) {
      for(Node n = q.head; n != null; n = n.next) {
         if (key.equals(n.getKey())) {
            return n;
         }
      }

      return null;
   }

   private Node findKeyInQueues(CacheKey key) {
      Node n = this.findKeyInQueue(this.activeQueue, key);
      if (n != null) {
         return n;
      } else {
         n = this.findKeyInQueue(this.inActiveQueue, key);
         return n != null ? n : this.findKeyInQueue(this.freeQueue, key);
      }
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
      private long size;

      Queue() {
      }

      boolean contains(Node n) {
         for(Node i = this.head; i != null; i = i.next) {
            if (i == n) {
               return true;
            }
         }

         return false;
      }

      void remove(Node n) {
         assert this.contains(n);

         this.size -= (long)n.getSize();
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

         assert !this.contains(n);

         assert this.tail == null || this.contains(this.tail);

         assert this.head == null || this.contains(this.head);

      }

      long size() {
         return this.size;
      }

      void push(Node n) {
         assert !this.contains(n);

         this.size += (long)n.getSize();
         if (this.tail == null) {
            assert this.head == null;

            this.head = n;
            this.tail = n;
            n.prev = null;
            n.next = null;
         } else {
            assert this.head != null;

            assert this.tail.next == null;

            this.tail.next = n;
            n.prev = this.tail;
            n.next = null;
            this.tail = n;
         }

      }

      Node pop() {
         if (this.head == null) {
            assert this.tail == null;

            assert this.size == 0L;

            return null;
         } else {
            assert this.size > 0L;

            this.size -= (long)this.head.getSize();
            Node n = this.head;
            this.head = this.head.next;
            if (this.head == null) {
               assert this.size == 0L;

               this.tail = null;
            } else {
               this.head.prev = null;
            }

            assert !this.contains(n);

            return n;
         }
      }
   }
}
