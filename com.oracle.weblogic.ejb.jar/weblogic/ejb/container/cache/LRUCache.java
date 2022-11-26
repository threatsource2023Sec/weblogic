package weblogic.ejb.container.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.SingleInstanceCache;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.Debug;

public final class LRUCache implements SingleInstanceCache, TimerListener {
   private static final DebugLogger DEBUG_LOGGER;
   private static final boolean DUMP_CACHE;
   private final String cacheName;
   private final long scrubIntervalMillis;
   private final Map cache = new HashMap();
   private final CacheScrubberTimer scrubberTimer;
   private final BeanManager bm;
   private int maxCacheSize = 0;
   private int currentCacheSize = 0;
   private int scrubCount = 0;
   private Node lruChainHead;
   private Node lruChainTail;

   public LRUCache(String cacheName, int maxBeansInCache, int scrubInterval, BeanManager bm) {
      this.maxCacheSize = maxBeansInCache;
      this.cacheName = cacheName;
      this.bm = bm;
      this.scrubberTimer = new CacheScrubberTimer(this, 0L, cacheName);
      this.scrubIntervalMillis = scrubInterval > 0 ? (long)scrubInterval * 1000L : 0L;
      if (this.scrubIntervalMillis > 0L) {
         this.scrubberTimer.setScrubInterval(this.scrubIntervalMillis);
      }

      this.setLruChainTail((Node)null);
      this.setLruChainHead((Node)null);
   }

   public void updateIdleTimeoutSeconds(int idleTimeoutSeconds) {
      this.scrubberTimer.resetScrubInterval((long)idleTimeoutSeconds * 1000L);
   }

   public synchronized Object get(CacheKey key) {
      assert this.validateDataStructures();

      Node node = (Node)this.cache.get(key);
      if (node != null) {
         node.touch();
         node.pin();
         this.lruUse(node);

         assert this.validateDataStructures();

         return node.getBean();
      } else {
         return null;
      }
   }

   public synchronized void put(CacheKey key, Object bean) throws CacheFullException {
      assert this.validateDataStructures();

      assert this.cache.get(key) == null;

      int requiredSpace = this.currentCacheSize + 1 - this.maxCacheSize;
      List removedNodes = null;
      boolean var11 = false;

      Node n;
      try {
         var11 = true;
         if (requiredSpace > 0) {
            removedNodes = this.attemptToFreeSpace(requiredSpace);
         }

         if (this.currentCacheSize + 1 > this.maxCacheSize) {
            throw new CacheFullException();
         }

         Node node = new Node();
         node.setBean(bean);
         node.setKey(key);
         node.setActive();
         node.pin();
         n = (Node)this.cache.put(key, node);
         if (n != null) {
            this.cache.put(key, n);
            throw new AssertionError("Attempt to replace Node. Old Node: '" + n + "', new Node: " + node + "'");
         }

         ++this.currentCacheSize;
         this.prependToLRU(node);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug("Node " + node.getKey() + " is added to cache, size:" + this.currentCacheSize);
            var11 = false;
         } else {
            var11 = false;
         }
      } finally {
         if (var11) {
            if (removedNodes != null) {
               Iterator var8 = removedNodes.iterator();

               while(var8.hasNext()) {
                  Node n = (Node)var8.next();

                  assert !this.inLRUChain(n);

                  n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     debug("Node " + n.getKey() + " is passivated.");
                  }
               }
            }

            assert this.validateDataStructures();

         }
      }

      if (removedNodes != null) {
         Iterator var13 = removedNodes.iterator();

         while(var13.hasNext()) {
            n = (Node)var13.next();

            assert !this.inLRUChain(n);

            n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("Node " + n.getKey() + " is passivated.");
            }
         }
      }

      assert this.validateDataStructures();

   }

   public synchronized void release(CacheKey key) {
      assert this.validateDataStructures();

      Node n = (Node)this.cache.get(key);
      if (n != null) {
         assert n.isActive() && n.pinned();

         n.unpin();
      }
   }

   public synchronized void remove(CacheKey key) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Removing key: " + key);
      }

      assert this.validateDataStructures();

      Node n = (Node)this.cache.remove(key);
      if (n != null) {
         --this.currentCacheSize;

         assert n.getKey().equals(key);

         if (n.pinned()) {
            n.unpin();
         }

         this.removeFromLRU(n);
         n.setFree();
         n.getCallback().removedFromCache(key, n.getBean());

         assert this.validateDataStructures();

         assert this.cache.get(key) == null;

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
      ManagedInvocationContext mic = this.bm.getBeanInfo().setCIC();
      Throwable var3 = null;

      try {
         synchronized(this) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("Scrubber passivating: " + this.cacheName);
            }

            Iterator var5 = this.getPassivationList().iterator();

            while(var5.hasNext()) {
               Node node = (Node)var5.next();
               this.passivateNode(node);
            }

            if (DUMP_CACHE) {
               Debug.say(this.cacheDump());
            }
         }
      } catch (Throwable var17) {
         var3 = var17;
         throw var17;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var3.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void passivateNode(Node n) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Passivate Node key: " + n.getKey());
      }

      assert !n.pinned();

      assert n.getBean() != null;

      assert n.getKey() != null;

      n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
   }

   private List getPassivationList() {
      List candidates = new ArrayList();
      boolean scrubAll = ++this.scrubCount % 5 == 0;
      Node node = this.lruChainTail;

      Node prev;
      for(long cutoff = System.currentTimeMillis() - this.scrubIntervalMillis; node != null; node = prev) {
         assert this.inLRUChain(node);

         prev = node.prev;
         if (node.olderThan(cutoff) && !node.pinned()) {
            this.remove(node.getKey());
            candidates.add(node);
         } else if (!scrubAll) {
            break;
         }
      }

      return candidates;
   }

   public void reInitializeCacheAndPools() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " reInitializeCacheAndPools, cache size is " + this.cache.size());
      }

      this.bm.reInitializePool();
      this.reInitializeCache();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug(this.cacheName + " cache reInitialization complete, cache size is " + this.cache.size());
      }

   }

   private synchronized void reInitializeCache() {
      Node prev;
      for(Node node = this.lruChainTail; node != null; node = prev) {
         assert this.inLRUChain(node);

         prev = node.prev;
         if (!node.pinned()) {
            this.remove(node.getKey());
            this.passivateNode(node);
         }
      }

      if (DUMP_CACHE) {
         Debug.say(this.cacheDump());
      }

   }

   public synchronized void updateMaxBeansInCache(int max) {
      this.maxCacheSize = max;
      if (this.currentCacheSize > max) {
         List removedNodes = this.attemptToFreeSpace(this.currentCacheSize - max);
         if (removedNodes != null) {
            Iterator var3 = removedNodes.iterator();

            while(var3.hasNext()) {
               Node n = (Node)var3.next();
               n.getCallback().swapOut(n.getKey(), n.getBean(), n.timeLastTouched());
            }
         }
      }

   }

   private List attemptToFreeSpace(int amt) {
      Node tailNode = this.lruChainTail;
      int totalReclaimed = 0;
      List removedNodes = null;

      while(totalReclaimed < amt) {
         while(tailNode != null && tailNode.pinned()) {
            tailNode = tailNode.prev;
         }

         if (tailNode == null) {
            return removedNodes;
         }

         if (removedNodes == null) {
            removedNodes = new ArrayList();
         }

         removedNodes.add(tailNode);
         Node removeNode = tailNode;
         tailNode = tailNode.prev;
         this.remove(removeNode.getKey());
         ++totalReclaimed;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug("Node " + tailNode.getKey() + " removed from cache, size: " + this.cache.size());
         }
      }

      return removedNodes;
   }

   private String cacheDump() {
      StringBuilder sb = new StringBuilder();
      sb.append("LRU Cache '" + this.cacheName + "' [ Size: " + this.cache.size() + " ]");
      if (!this.cache.isEmpty()) {
         sb.append(" Contents: ");

         for(Node n = this.lruChainHead; n != null; n = n.next) {
            sb.append(" [ PK: " + n.getKey() + " ] ");
         }
      }

      return sb.toString();
   }

   private void chain(Node first, Node second) {
      if (first != null) {
         first.next = second;
      }

      if (second != null) {
         second.prev = first;
      }

      if (second == this.lruChainHead && first != null) {
         this.setLruChainHead(first);
      }

      if (first == this.lruChainTail && second != null) {
         this.setLruChainTail(second);
      }

   }

   private void prependToLRU(Node node) {
      assert !this.inLRUChain(node);

      this.chain((Node)null, node);
      this.chain(node, this.lruChainHead);
   }

   private void removeFromLRU(Node node) {
      assert this.inLRUChain(node);

      Node prev = node.prev;
      Node next = node.next;
      this.chain(prev, next);
      node.next = null;
      node.prev = null;
      if (node == this.lruChainHead) {
         this.setLruChainHead(next);
      }

      if (node == this.lruChainTail) {
         this.setLruChainTail(prev);
      }

   }

   private void setLruChainHead(Node node) {
      this.lruChainHead = node;
   }

   private void setLruChainTail(Node node) {
      this.lruChainTail = node;
   }

   private void lruUse(Node node) {
      assert this.inLRUChain(node) && this.validateDataStructures();

      if (node != this.lruChainHead && node.prev != null) {
         Node prev = node.prev;
         Node next = node.next;
         this.chain(prev, next);
         if (node == this.lruChainHead) {
            this.setLruChainHead(next);
         }

         if (node == this.lruChainTail) {
            this.setLruChainTail(prev);
         }

         this.chain((Node)null, node);
         this.chain(node, this.lruChainHead);

         assert this.validateDataStructures();

      }
   }

   private boolean validateDataStructures() {
      this.validateLRUChain();
      this.validateCache();
      return true;
   }

   private boolean inLRUChain(Node node) {
      for(Node tmp = node; tmp != null; tmp = tmp.prev) {
         if (tmp == this.lruChainHead) {
            return true;
         }
      }

      return false;
   }

   private void validateCache() {
      Node node = this.lruChainHead;
      int i = 0;
      Set set = new HashSet();

      int size;
      for(size = 0; node != null; ++i) {
         Object bean = node.getBean();

         assert this.cache.get(node.getKey()) != null;

         assert set.add(bean);

         ++size;
         node = node.next;
      }

      assert i == this.cache.size();

      assert size == this.currentCacheSize;

   }

   private void validateLRUChain() {
      if (this.lruChainHead == null) {
         assert this.lruChainTail == null && this.cache.isEmpty() && this.currentCacheSize == 0 : "lruChainTail = " + this.lruChainTail + ", size = " + this.cache.size() + ", currentCacheSize= " + this.currentCacheSize;
      } else if (this.lruChainTail == null) {
         assert this.cache.isEmpty() && this.currentCacheSize == 0 : "lruChainHead = " + this.lruChainHead + ", size = " + this.cache.size() + ", currentCacheSize= " + this.currentCacheSize;
      } else if (this.cache.isEmpty()) {
         assert this.currentCacheSize == 0 : "lruChainHead = " + this.lruChainHead + ", lruChainTail = " + this.lruChainTail + ", size = " + this.cache.size() + ", currentCacheSize= " + this.currentCacheSize;
      } else if (this.currentCacheSize == 0) {
         assert this.cache.isEmpty() : "lruChainHead = " + this.lruChainHead + ", lruChainTail = " + this.lruChainTail + ", size = " + this.cache.size() + ", currentCacheSize= " + this.currentCacheSize;
      } else {
         assert this.lruChainHead.prev == null;

         assert this.lruChainTail.next == null;

         int len = 0;
         Set set = new HashSet();

         for(Node node = this.lruChainHead; node != null; node = node.next) {
            ++len;

            assert set.add(node);
         }

         assert len == this.cache.size();
      }

   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[LRUCache] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.cachingLogger;
      DUMP_CACHE = Boolean.getBoolean("ejb.enableCacheDump");
   }
}
