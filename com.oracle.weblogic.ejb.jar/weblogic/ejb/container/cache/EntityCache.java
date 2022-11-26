package weblogic.ejb.container.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.EntityBean;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.MultiVersionCache;
import weblogic.ejb.container.interfaces.PassivatibleEntityCache;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.Debug;

public final class EntityCache implements MultiVersionCache, PassivatibleEntityCache, TimerListener {
   private static final DebugLogger debugLogger;
   private final ConcurrentHashMap cachedEntitiesByKey = new ConcurrentHashMap();
   private final SizeTracker sizeTracker = new SizeTracker();
   private final boolean usesMaxBeansInCache;
   private long limit;
   private final String cacheName;
   private List cachingManagers = new ArrayList();
   private boolean disableReadyCache = false;
   private long scrubIntervalMillisDD;
   private long scrubIntervalMillis;
   private EntityPassivator passivator;
   private CacheScrubberTimer scrubberTimer;

   public EntityCache(String name, int maxBeansInCache) {
      this.cacheName = name;
      this.limit = (long)maxBeansInCache;
      this.usesMaxBeansInCache = true;
      this.passivator = new EntityPassivator(this);
      this.scrubberTimer = new CacheScrubberTimer(this, 0L, this.cacheName);
      if (debugLogger.isDebugEnabled()) {
         debug(this.cacheName + " Creating EntityCache for " + name + " with " + this.limit);
      }

   }

   public EntityCache(String name, long maxCacheSize) {
      this.cacheName = name;
      this.limit = maxCacheSize;
      this.usesMaxBeansInCache = false;
      this.passivator = new EntityPassivator(this);
      this.scrubberTimer = new CacheScrubberTimer(this, 0L, this.cacheName);
      if (debugLogger.isDebugEnabled()) {
         debug(this.cacheName + " Creating EntityCache for " + name + " with " + this.limit);
      }

   }

   public void register(CachingManager cm) {
      if (debugLogger.isDebugEnabled()) {
         debug(this.cacheName + " register CachingManager " + cm);
      }

      if (!this.cachingManagers.contains(cm)) {
         this.cachingManagers.add(cm);
      }

   }

   public List getCachingManagers() {
      return this.cachingManagers;
   }

   public int getMaxBeansInCache() {
      return (int)this.limit;
   }

   public void setMaxBeansInCache(int val) {
      this.limit = (long)val;
   }

   public boolean usesMaxBeansInCache() {
      return this.usesMaxBeansInCache;
   }

   public long getMaxCacheSize() {
      return 2147483647L;
   }

   public void setMaxCacheSize(long val) {
   }

   public void setDisableReadyCache(boolean noReadyCache) {
      this.disableReadyCache = noReadyCache;
   }

   public long getCurrentSize() {
      return this.sizeTracker.getSize();
   }

   public boolean contains(Object txOrThread, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd != null && kd.contains(txOrThread);
   }

   public EntityBean get(Object txOrThread, CacheKey key, boolean pin) throws InternalException {
      return this.get(txOrThread, key, (RSInfo)null, pin);
   }

   public EntityBean getValid(Object txOrThread, CacheKey key, boolean pin) throws InternalException {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd == null ? null : kd.getValid(txOrThread, pin);
   }

   public EntityBean get(Object txOrThread, CacheKey key, RSInfo rsInfo, boolean pin) throws InternalException {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd == null ? null : kd.get(txOrThread, rsInfo, pin);
   }

   public EntityBean getActive(Object txOrThread, CacheKey key, boolean pin) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd == null ? null : kd.getActive(txOrThread, pin);
   }

   public EntityBean getIfNotTimedOut(Object txOrThread, CacheKey key, boolean pin) throws InternalException {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd == null ? null : kd.getIfNotTimedOut(txOrThread, pin);
   }

   public CMPBean getLastLoadedValidInstance(CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      return kd == null ? null : kd.getLastLoadedValidInstance();
   }

   public void put(Object txOrThread, CacheKey key, EntityBean bean, BaseEntityManager callback, boolean pin) throws CacheFullException {
      int incr = callback.getBeanSize();
      Transaction tx = null;
      KeyData kd;
      if (!this.disableReadyCache) {
         if (txOrThread instanceof Transaction) {
            tx = (Transaction)txOrThread;
         }

         for(kd = this.sizeTracker.acquireSpace(tx, this.limit, incr, incr); kd != null; kd = this.sizeTracker.acquireSpace(tx, this.limit, incr, 0)) {
            kd.shrink();
         }
      }

      kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (kd == null) {
         kd = new KeyData(callback, key);
      }

      while(kd != null) {
         kd = kd.add(bean, txOrThread, pin);
      }

   }

   public void unpin(Object txOrThread, CacheKey key) {
      if (!this.disableReadyCache) {
         KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
         if (kd != null) {
            kd.unpin(txOrThread);
         }
      }
   }

   public void release(Object txOrThread, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (kd != null) {
         if (this.disableReadyCache) {
            kd.backToPool(txOrThread);
         } else {
            kd.release(txOrThread);
         }
      }

   }

   public int passivateUnModifiedBean(Transaction tx, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (debugLogger.isDebugEnabled() && kd == null) {
         debug(this.cacheName + " passivateUnModifiedBean  pk " + key.getPrimaryKey() + " is not in cache");
      }

      return kd == null ? 0 : kd.passivateUnModifiedBean(tx);
   }

   public int passivateModifiedBean(Transaction tx, CacheKey key, boolean flushSuccess) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (debugLogger.isDebugEnabled() && kd == null) {
         debug(this.cacheName + " passivateModifiedBean  pk " + key.getPrimaryKey() + " is not in cache");
      }

      return kd == null ? 0 : kd.passivateModifiedBean(tx, flushSuccess);
   }

   public void removeOnError(Object txOrThread, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (kd != null) {
         kd.remove(txOrThread, true);
      }

   }

   public void remove(Object txOrThread, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (kd != null) {
         kd.remove(txOrThread, false);
      }

   }

   public void invalidate(Object txOrThread, CacheKey key) {
      KeyData kd = (KeyData)this.cachedEntitiesByKey.get(key);
      if (kd != null) {
         if (debugLogger.isDebugEnabled() && txOrThread != null) {
            debug(this.cacheName + " invalidate: skipping invalidate, txOrThread- " + txOrThread + ", key- " + key);
         }

         kd.invalidate(txOrThread);
      }
   }

   public void invalidate(Object txOrThread, Collection keys) {
      Iterator var3 = keys.iterator();

      while(var3.hasNext()) {
         CacheKey ck = (CacheKey)var3.next();
         this.invalidate(txOrThread, ck);
      }

   }

   public void invalidateAll(Object txOrThread) {
      this.invalidate(txOrThread, (Collection)this.cachedEntitiesByKey.keySet());
   }

   public void beanImplClassChangeNotification() {
      Iterator i = this.cachedEntitiesByKey.values().iterator();

      while(i.hasNext()) {
         ((KeyData)i.next()).keepEnrolled(i);
      }

   }

   public void updateMaxBeansInCache(int max) {
      if (this.usesMaxBeansInCache) {
         this.limit = (long)max;
      }

   }

   public void updateMaxCacheSize(int max) {
      if (!this.usesMaxBeansInCache) {
         this.limit = (long)max;
      }

   }

   public void setScrubInterval(int seconds) {
      if (seconds > 0) {
         long l = (long)seconds * 1000L;
         if (this.scrubIntervalMillisDD <= 0L) {
            this.scrubIntervalMillisDD = l;
         } else if (l < this.scrubIntervalMillisDD) {
            this.scrubIntervalMillisDD = l;
         }

         this.scrubIntervalMillis = this.scrubIntervalMillisDD;
         this.scrubberTimer.setScrubInterval(this.scrubIntervalMillisDD);
      }

   }

   public void startScrubber() {
      this.scrubberTimer.startScrubber();
   }

   public void stopScrubber() {
      this.scrubberTimer.stopScrubber();
   }

   public void timerExpired(Timer timer) {
      int totalCount = this.scrubCache(true);
      if (totalCount <= 0) {
         if (this.scrubIntervalMillis < 120000L) {
            if (debugLogger.isDebugEnabled()) {
               debug(this.cacheName + " scrubIntervalMillis: " + this.scrubIntervalMillis + " is less than 2 minutes and we've scrubbed no beans.  Doubling the interval till next scrubbing.");
            }

            this.scrubIntervalMillis += this.scrubIntervalMillis;
            this.scrubberTimer.stopScrubber();
            this.scrubberTimer.setScrubInterval(this.scrubIntervalMillis);
            this.scrubberTimer.startScrubber();
         }
      } else if (this.scrubIntervalMillis != this.scrubIntervalMillisDD) {
         if (debugLogger.isDebugEnabled()) {
            debug(this.cacheName + " scrubIntervalMillis: " + this.scrubIntervalMillis + " is not equal to the deployed value  and we've scrubbed some beans during this scrubbing event.  Resetting scrubInterval to its deployed value: " + this.scrubIntervalMillisDD);
         }

         this.resetScrubberTimerToDeployedValue();
      }

   }

   private void resetScrubberTimerToDeployedValue() {
      this.scrubIntervalMillis = this.scrubIntervalMillisDD;
      this.scrubberTimer.stopScrubber();
      this.scrubberTimer.setScrubInterval(this.scrubIntervalMillis);
      this.scrubberTimer.startScrubber();
   }

   private int scrubCache(boolean idleTimeout) {
      long currTime = System.currentTimeMillis();
      long cutoff = currTime;
      int totalCount = 0;
      long origsize = 0L;
      if (debugLogger.isDebugEnabled()) {
         origsize = this.sizeTracker.getSize();
         debug("\n\n" + this.cacheName + " scrubCache() at " + currTime + ", cache size: " + origsize + (this.usesMaxBeansInCache ? " beans" : " bytes") + ", start cacheScrub");
      }

      Iterator i = this.cachedEntitiesByKey.values().iterator();

      while(true) {
         KeyData kd;
         while(true) {
            if (!i.hasNext()) {
               if (debugLogger.isDebugEnabled()) {
                  long elapsed = System.currentTimeMillis() - currTime;
                  debug(this.cacheName + " scrubCache() completed.  Scrubbed " + totalCount + " beans from cache in " + elapsed + " ms.  size of cache is now: " + this.sizeTracker.getSize() + (this.usesMaxBeansInCache ? " beans" : " bytes") + " from orig size " + origsize + (this.usesMaxBeansInCache ? " beans" : " bytes") + "\n\n");
               }

               return totalCount;
            }

            kd = (KeyData)i.next();
            if (!idleTimeout) {
               break;
            }

            int timeoutSecs = kd.cachingManager.getIdleTimeoutSeconds();
            if (timeoutSecs > 0 && kd.oldestTimestamp() > 0L) {
               cutoff = currTime - (long)timeoutSecs * 1000L;
               break;
            }
         }

         long timestamp = kd.oldestTimestamp();
         if (timestamp > 0L && timestamp <= cutoff) {
            totalCount += kd.shrinkIfExpired(cutoff, i);
         }
      }
   }

   public void reInitializeCacheAndPools() {
      if (debugLogger.isDebugEnabled()) {
         debug(this.cacheName + " reInitializeCacheAndPools");
      }

      this.reInitializeCache();
      Iterator var1 = this.cachingManagers.iterator();

      while(var1.hasNext()) {
         CachingManager bm = (CachingManager)var1.next();
         ((BeanManager)bm).reInitializePool();
      }

   }

   public void reInitializeCache() {
      this.scrubCache(false);
      if (this.scrubIntervalMillis != this.scrubIntervalMillisDD) {
         this.resetScrubberTimerToDeployedValue();
      }

   }

   public synchronized void printDataStructures() {
      System.out.println("ready--------------------------------------------");
      Iterator var1 = this.cachedEntitiesByKey.values().iterator();

      KeyData k;
      while(var1.hasNext()) {
         k = (KeyData)var1.next();
         k.print(true);
      }

      System.out.println("active--------------------------------------------");
      var1 = this.cachedEntitiesByKey.values().iterator();

      while(var1.hasNext()) {
         k = (KeyData)var1.next();
         k.print(false);
      }

      System.out.println("-------------------------------------------------");
   }

   public void updateIdleTimeoutSeconds(int idleTimeoutSeconds) {
      this.scrubberTimer.resetScrubInterval((long)idleTimeoutSeconds * 1000L);
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityCache] " + s);
   }

   static {
      debugLogger = EJBDebugService.cachingLogger;
   }

   static final class BeanData extends MRUElement {
      BeanData next;
      final EntityBean bean;
      Object txOrThread;
      long timestamp;
      int pinCount;

      BeanData(EntityBean bean, Object txOrThread) {
         this.txOrThread = txOrThread;
         this.bean = bean;
      }

      public boolean associatedWith(Object txOrThread) {
         return txOrThread == this.txOrThread || txOrThread.equals(this.txOrThread);
      }

      void enroll(Object txOrThread) {
         this.txOrThread = txOrThread;
         super.remove();
      }

      void release() {
         this.txOrThread = null;
         this.timestamp = System.currentTimeMillis();
         this.pinCount = 0;
      }

      int pin() {
         return this.pinCount++;
      }

      int unpin() {
         assert this.pinCount > 0 : " Error ! attempt to unpin bean with pinCount == " + this.pinCount + ", bean: " + this.bean;

         return this.pinCount--;
      }

      boolean isPinned() {
         return this.pinCount > 0;
      }
   }

   final class KeyData extends MRUElement {
      final CacheKey key;
      final BaseEntityManager cachingManager;
      final boolean attemptCopy;
      final boolean categoryEnabled;
      private final Ring readyRing = new Ring();
      private BeanData activeBeanList;

      KeyData(BaseEntityManager mgr, CacheKey key) {
         this.cachingManager = mgr;
         this.key = key;
         if (mgr instanceof TTLManager) {
            this.attemptCopy = ((TTLManager)mgr).supportsCopy();
            this.categoryEnabled = ((TTLManager)mgr).isCategoryEnabled();
         } else {
            this.attemptCopy = false;
            this.categoryEnabled = false;
         }

      }

      boolean isEmpty() {
         return this.activeBeanList == null && this.readyRing.isEmpty();
      }

      KeyData add(EntityBean bean, Object txOrThread, boolean pin) {
         BeanData bd = new BeanData(bean, txOrThread);
         synchronized(this) {
            if (pin && !EntityCache.this.disableReadyCache) {
               bd.pin();
            }

            boolean inMap = !this.isEmpty();
            bd.next = this.activeBeanList;
            this.activeBeanList = bd;
            return inMap ? null : (KeyData)EntityCache.this.cachedEntitiesByKey.putIfAbsent(this.key, this);
         }
      }

      void release(Object txOrThread) {
         boolean released = false;
         BeanData activeBeanToRelease;
         synchronized(this) {
            activeBeanToRelease = this.getAndUnlinkBeanForTxOrThread(txOrThread);
            if (activeBeanToRelease == null) {
               return;
            }

            if (!this.cachingManager.needsRemoval(activeBeanToRelease.bean)) {
               if (this.readyRing.isEmpty()) {
                  EntityCache.this.sizeTracker.insertKeyData(this);
               } else {
                  EntityCache.this.sizeTracker.moveKeyDataToHead(this);
               }

               activeBeanToRelease.release();
               this.readyRing.insert(activeBeanToRelease);
               released = true;
            }

            if (!released) {
               EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
               if (this.isEmpty()) {
                  EntityCache.this.cachedEntitiesByKey.remove(this.key);
               }
            }
         }

         if (!released) {
            this.cachingManager.selectedForReplacement(this.key, activeBeanToRelease.bean);
         }

         if (this.categoryEnabled) {
            TTLManager manager = (TTLManager)this.cachingManager;
            Object categoryValue = manager.getCategoryValue((CMPBean)activeBeanToRelease.bean);
            long lastLoadedTime = ((WLEntityBean)activeBeanToRelease.bean).__WL_getLastLoadTime();
            manager.registerCategoryTimer(categoryValue, lastLoadedTime);
         }

      }

      BeanData getBeanForTxOrThread(Object txOrThread) {
         BeanData currentActiveBean;
         for(currentActiveBean = this.activeBeanList; currentActiveBean != null && !currentActiveBean.associatedWith(txOrThread); currentActiveBean = currentActiveBean.next) {
         }

         return currentActiveBean;
      }

      BeanData getAndUnlinkBeanForTxOrThread(Object txOrThread) {
         BeanData currentActiveBean = this.activeBeanList;

         BeanData lastBean;
         for(lastBean = null; currentActiveBean != null && !currentActiveBean.associatedWith(txOrThread); currentActiveBean = currentActiveBean.next) {
            lastBean = currentActiveBean;
         }

         if (currentActiveBean != null) {
            if (currentActiveBean == this.activeBeanList) {
               this.activeBeanList = currentActiveBean.next;
            } else {
               lastBean.next = currentActiveBean.next;
            }
         }

         return currentActiveBean;
      }

      EntityBean getIfNotTimedOut(Object txOrThread, boolean pin) throws InternalException {
         EntityBean bean = null;
         BeanData activeBeanToReturn = null;
         synchronized(this) {
            label93: {
               activeBeanToReturn = this.getBeanForTxOrThread(txOrThread);
               if (activeBeanToReturn != null) {
                  return activeBeanToReturn.bean;
               }

               MRUElement readyBeanData = this.readyRing.fwd;
               if (readyBeanData == this.readyRing) {
                  return null;
               }

               while(true) {
                  BeanData lastLoadedActiveBean;
                  if (readyBeanData != this.readyRing) {
                     lastLoadedActiveBean = (BeanData)readyBeanData;
                     WLEntityBean entityBean = (WLEntityBean)lastLoadedActiveBean.bean;
                     if (!entityBean.__WL_isBeanStateValid()) {
                        readyBeanData = readyBeanData.fwd;
                        continue;
                     }

                     activeBeanToReturn = lastLoadedActiveBean;
                  } else {
                     if (!this.attemptCopy) {
                        break;
                     }

                     lastLoadedActiveBean = null;
                     long lastLoadTime = -1L;

                     for(BeanData nextActiveBean = this.activeBeanList; nextActiveBean != null; nextActiveBean = nextActiveBean.next) {
                        long loadTime = ((WLEntityBean)nextActiveBean.bean).__WL_getLastLoadTime();
                        if (loadTime > lastLoadTime) {
                           lastLoadTime = loadTime;
                           lastLoadedActiveBean = nextActiveBean;
                        }
                     }

                     if (lastLoadedActiveBean == null || !((WLEntityBean)lastLoadedActiveBean.bean).__WL_isBeanStateValid()) {
                        break;
                     }

                     activeBeanToReturn = (BeanData)this.readyRing.fwd;

                     try {
                        ((TTLManager)this.cachingManager).doCopy((CMPBean)lastLoadedActiveBean.bean, (CMPBean)activeBeanToReturn.bean);
                     } catch (InternalException var14) {
                        activeBeanToReturn.remove();
                        this.cleanupFailedBean(activeBeanToReturn);
                        throw var14;
                     }
                  }

                  bean = this.enroll(activeBeanToReturn, txOrThread, pin);
                  break label93;
               }

               return null;
            }
         }

         try {
            Transaction tx = null;
            if (txOrThread instanceof Transaction) {
               tx = (Transaction)txOrThread;
            }

            ((TTLManager)this.cachingManager).enrollNotTimedOutBean(tx, this.key, bean);
            return bean;
         } catch (InternalException var15) {
            this.cachingManager.removedOnError(this.key, bean);
            if (!EntityCache.this.disableReadyCache) {
               EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
            }

            if (this.isEmpty()) {
               EntityCache.this.cachedEntitiesByKey.remove(this.key);
            }

            throw var15;
         }
      }

      private void cleanupFailedBean(BeanData activeBeanData) {
         this.cachingManager.removedOnError(this.key, activeBeanData.bean);
         if (!EntityCache.this.disableReadyCache) {
            if (this.readyRing.isEmpty()) {
               EntityCache.this.sizeTracker.removeKeyData(this, this.cachingManager.getBeanSize());
            } else {
               EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
            }
         }

         if (this.isEmpty()) {
            EntityCache.this.cachedEntitiesByKey.remove(this.key);
         }

      }

      public synchronized CMPBean getLastLoadedValidInstance() {
         WLEntityBean lastLoaded = null;
         long lastLoadTime = -1L;

         WLEntityBean current;
         long loadTime;
         for(BeanData bd = this.activeBeanList; bd != null; bd = bd.next) {
            current = (WLEntityBean)bd.bean;
            loadTime = current.__WL_getLastLoadTime();
            if (loadTime > lastLoadTime) {
               lastLoadTime = loadTime;
               lastLoaded = current;
            }
         }

         if (lastLoaded != null && lastLoaded.__WL_isBeanStateValid()) {
            return (CMPBean)lastLoaded;
         } else {
            lastLoaded = null;

            for(MRUElement e = this.readyRing.fwd; e != this.readyRing; e = e.fwd) {
               current = (WLEntityBean)((BeanData)e).bean;
               loadTime = current.__WL_getLastLoadTime();
               if (loadTime > lastLoadTime) {
                  lastLoadTime = loadTime;
                  lastLoaded = current;
               }
            }

            if (lastLoaded != null && !lastLoaded.__WL_isBeanStateValid()) {
               lastLoaded = null;
            }

            return (CMPBean)lastLoaded;
         }
      }

      void backToPool(Object txOrThread) {
         BeanData bd;
         synchronized(this) {
            bd = this.activeBeanList;
            if (bd == null) {
               return;
            }

            if (txOrThread != bd.txOrThread && !txOrThread.equals(bd.txOrThread)) {
               BeanData pbd;
               do {
                  pbd = bd;
                  bd = bd.next;
                  if (bd == null) {
                     return;
                  }
               } while(txOrThread != bd.txOrThread && !txOrThread.equals(bd.txOrThread));

               pbd.next = bd.next;
            } else {
               this.activeBeanList = bd.next;
            }

            if (this.isEmpty()) {
               EntityCache.this.cachedEntitiesByKey.remove(this.key);
            }
         }

         this.cachingManager.passivateAndBacktoPool(this.key, bd.bean);
      }

      void remove(Object txOrThread, boolean onError) {
         BeanData bd;
         synchronized(this) {
            bd = this.activeBeanList;
            if (bd == null) {
               return;
            }

            if (txOrThread != bd.txOrThread && !txOrThread.equals(bd.txOrThread)) {
               BeanData pbd;
               do {
                  pbd = bd;
                  bd = bd.next;
                  if (bd == null) {
                     return;
                  }
               } while(txOrThread != bd.txOrThread && !txOrThread.equals(bd.txOrThread));

               pbd.next = bd.next;
            } else {
               this.activeBeanList = bd.next;
            }

            if (this.isEmpty()) {
               EntityCache.this.cachedEntitiesByKey.remove(this.key);
            }

            EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
         }

         if (onError) {
            this.cachingManager.removedOnError(this.key, bd.bean);
         } else {
            this.cachingManager.removedFromCache(this.key, bd.bean);
         }

      }

      synchronized void invalidate(Object txOrThread) {
         for(BeanData bd = this.activeBeanList; bd != null; bd = bd.next) {
            if (txOrThread == null || txOrThread != bd.txOrThread && !txOrThread.equals(bd.txOrThread)) {
               ((WLEntityBean)bd.bean).__WL_setBeanStateValid(false);
            }
         }

         for(MRUElement e = this.readyRing.fwd; e != this.readyRing; e = e.fwd) {
            ((WLEntityBean)((BeanData)e).bean).__WL_setBeanStateValid(false);
         }

      }

      EntityBean get(Object txOrThread, RSInfo rsi, boolean pin) throws InternalException {
         EntityBean bean = this.getActive(txOrThread, pin);
         if (bean != null) {
            if (rsi != null) {
               this.cachingManager.loadBeanFromRS(this.key, bean, rsi);
            }

            return bean;
         } else if (EntityCache.this.disableReadyCache) {
            return null;
         } else {
            synchronized(this) {
               MRUElement readyBeanData = this.readyRing.fwd;
               if (readyBeanData == this.readyRing) {
                  return null;
               }

               BeanData readyBeanToReturn;
               label80: {
                  BeanData lastLoadedActiveBean;
                  for(readyBeanToReturn = null; readyBeanData != this.readyRing; readyBeanData = readyBeanData.fwd) {
                     lastLoadedActiveBean = (BeanData)readyBeanData;
                     WLEntityBean entityBean = (WLEntityBean)lastLoadedActiveBean.bean;
                     if (entityBean.__WL_isBeanStateValid()) {
                        readyBeanToReturn = lastLoadedActiveBean;
                        break label80;
                     }
                  }

                  readyBeanToReturn = (BeanData)this.readyRing.fwd;
                  if (this.attemptCopy) {
                     lastLoadedActiveBean = null;
                     long lastLoadTime = -1L;

                     for(BeanData nextActiveBean = this.activeBeanList; nextActiveBean != null; nextActiveBean = nextActiveBean.next) {
                        long loadTime = ((WLEntityBean)nextActiveBean.bean).__WL_getLastLoadTime();
                        if (loadTime > lastLoadTime) {
                           lastLoadTime = loadTime;
                           lastLoadedActiveBean = nextActiveBean;
                        }
                     }

                     if (lastLoadedActiveBean != null && ((WLEntityBean)lastLoadedActiveBean.bean).__WL_isBeanStateValid()) {
                        try {
                           ((TTLManager)this.cachingManager).doCopy((CMPBean)lastLoadedActiveBean.bean, (CMPBean)readyBeanToReturn.bean);
                        } catch (InternalException var15) {
                           readyBeanToReturn.remove();
                           this.cleanupFailedBean(readyBeanToReturn);
                           throw var15;
                        }
                     }
                  }
               }

               bean = this.enroll(readyBeanToReturn, txOrThread, pin);
            }

            Transaction tx = null;
            if (txOrThread instanceof Transaction) {
               tx = (Transaction)txOrThread;
               this.cachingManager.enrollInTransaction(tx, this.key, bean, rsi);
               return bean;
            } else {
               return bean;
            }
         }
      }

      EntityBean getValid(Object txOrThread, boolean pin) throws InternalException {
         EntityBean bean = this.getActive(txOrThread, pin);
         if (bean != null) {
            return bean;
         } else if (EntityCache.this.disableReadyCache) {
            return null;
         } else {
            synchronized(this) {
               MRUElement e = this.readyRing.fwd;
               if (e == this.readyRing) {
                  return null;
               }

               while(e != this.readyRing && !((WLEntityBean)((BeanData)e).bean).__WL_isBeanStateValid()) {
                  e = e.fwd;
               }

               if (e == this.readyRing) {
                  return null;
               }

               bean = this.enroll((BeanData)e, txOrThread, pin);
            }

            Transaction tx = null;
            if (txOrThread instanceof Transaction) {
               tx = (Transaction)txOrThread;
               this.cachingManager.enrollInTransaction(tx, this.key, bean, (RSInfo)null);
               return bean;
            } else {
               return bean;
            }
         }
      }

      private final EntityBean enroll(BeanData bd, Object txOrThread, boolean pin) {
         bd.next = this.activeBeanList;
         this.activeBeanList = bd;
         bd.enroll(txOrThread);
         if (pin && !EntityCache.this.disableReadyCache) {
            bd.pin();
         }

         if (this.readyRing.fwd == this.readyRing) {
            EntityCache.this.sizeTracker.removeKeyData(this);
         }

         return bd.bean;
      }

      synchronized EntityBean getActive(Object txOrThread, boolean pin) {
         if (txOrThread == null) {
            return null;
         } else {
            BeanData bd = this.getBeanForTxOrThread(txOrThread);
            if (bd != null) {
               if (pin && !EntityCache.this.disableReadyCache) {
                  bd.pin();
               }

               return bd.bean;
            } else {
               return null;
            }
         }
      }

      boolean contains(Object txOrThread) {
         return this.getBeanForTxOrThread(txOrThread) != null;
      }

      synchronized void unpin(Object txOrThread) {
         if (!EntityCache.this.disableReadyCache) {
            BeanData bd = this.getBeanForTxOrThread(txOrThread);
            if (bd != null) {
               bd.unpin();
            }

         }
      }

      int passivateUnModifiedBean(Transaction tx) {
         return this.passivateBean(tx, false, false);
      }

      int passivateModifiedBean(Transaction tx, boolean flushSuccess) {
         return this.passivateBean(tx, true, flushSuccess);
      }

      private int passivateBean(Transaction tx, boolean modified, boolean flushSuccess) {
         BeanData txbd = null;
         synchronized(this) {
            txbd = this.getBeanForTxOrThread(tx);
            if (txbd == null) {
               if (EntityCache.debugLogger.isDebugEnabled()) {
                  EntityCache.debug(EntityCache.this.cacheName + " passivateBean: bean pk " + this.key + ", is not in cache.  Cannot passivate.");
               }

               return 0;
            }

            if (txbd.isPinned()) {
               if (EntityCache.debugLogger.isDebugEnabled()) {
                  EntityCache.debug(EntityCache.this.cacheName + " passivateBean: bean pk " + this.key + ", is pinned.  Cannot passivate.");
               }

               return 0;
            }

            boolean passivatible = false;
            if (modified) {
               passivatible = this.cachingManager.passivateLockedModifiedBean(tx, this.key.getPrimaryKey(), flushSuccess, txbd.bean);
            } else {
               passivatible = this.cachingManager.passivateLockedUnModifiedBean(tx, this.key.getPrimaryKey(), txbd.bean);
            }

            if (!passivatible) {
               if (EntityCache.debugLogger.isDebugEnabled()) {
                  EntityCache.debug(EntityCache.this.cacheName + " passivateBean: bean pk " + this.key + ", is not passivatible.");
               }

               return 0;
            }

            this.getAndUnlinkBeanForTxOrThread(tx);
            if (this.isEmpty()) {
               EntityCache.this.cachedEntitiesByKey.remove(this.key);
            }

            if (!EntityCache.this.disableReadyCache) {
               EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
            }
         }

         this.cachingManager.passivateAndRelease(this.key, txbd.bean);
         return this.cachingManager.getBeanSize();
      }

      synchronized void keepEnrolled(Iterator i) {
         int beanSize = this.cachingManager.getBeanSize();
         int freed = 0;

         for(MRUElement e = this.readyRing.fwd; e != this.readyRing; e = e.fwd) {
            this.cachingManager.selectedForReplacement(this.key, ((BeanData)e).bean);
            freed += beanSize;
         }

         EntityCache.this.sizeTracker.removeKeyData(this, freed);
         this.readyRing.reset();
         if (this.activeBeanList == null) {
            i.remove();
         }

      }

      synchronized void print(boolean enrolled) {
         if (enrolled) {
            for(BeanData bd = this.activeBeanList; bd != null; bd = bd.next) {
               System.out.println("key= " + this.key + ", bean= " + bd.bean);
            }

         } else {
            for(MRUElement e = this.readyRing.fwd; e != this.readyRing; e = e.fwd) {
               System.out.println("key= " + this.key + ", bean= " + ((BeanData)e).bean);
            }

         }
      }

      synchronized int validateDataStructures(CacheKey key, SizeTracker st) {
         Debug.assertion(this.key.equals(key));
         int cnt = 0;

         for(BeanData bd = this.activeBeanList; bd != null; bd = bd.next) {
            Debug.assertion(bd.txOrThread != null);
            ++cnt;
         }

         for(MRUElement e = this.readyRing.fwd; e != this.readyRing; e = e.fwd) {
            Debug.assertion(((BeanData)e).txOrThread == null);
            ++cnt;
         }

         Debug.assertion(this.readyRing.isEmpty() != st.contains(this));
         return cnt;
      }

      long oldestTimestamp() {
         MRUElement e = this.readyRing.bwd;
         return e == this.readyRing ? 0L : ((BeanData)e).timestamp;
      }

      void shrink() {
         MRUElement leastReadyBean;
         synchronized(this) {
            leastReadyBean = this.readyRing.bwd;
            if (leastReadyBean == this.readyRing) {
               return;
            }

            if (EntityCache.debugLogger.isDebugEnabled()) {
               EntityCache.debug(EntityCache.this.cacheName + " shrink:  removing " + this.key.getPrimaryKey() + " from active ring ");
            }

            leastReadyBean.remove();
            if (this.readyRing.isEmpty()) {
               EntityCache.this.sizeTracker.removeKeyData(this, this.cachingManager.getBeanSize());
               if (this.activeBeanList == null) {
                  EntityCache.this.cachedEntitiesByKey.remove(this.key);
               }
            } else {
               EntityCache.this.sizeTracker.decrementSize(this.cachingManager.getBeanSize());
            }
         }

         this.cachingManager.selectedForReplacement(this.key, ((BeanData)leastReadyBean).bean);
      }

      synchronized int shrinkIfExpired(long cutoff, Iterator it) {
         int count = 0;
         MRUElement leastActiveBean = this.readyRing.bwd;

         for(int size = this.cachingManager.getBeanSize(); leastActiveBean != this.readyRing && ((BeanData)leastActiveBean).timestamp < cutoff; leastActiveBean = this.readyRing.bwd) {
            leastActiveBean.remove();
            ++count;
            if (this.readyRing.isEmpty()) {
               if (!EntityCache.this.disableReadyCache) {
                  EntityCache.this.sizeTracker.removeKeyData(this, size);
               }

               if (this.activeBeanList == null) {
                  it.remove();
               }
            } else if (!EntityCache.this.disableReadyCache) {
               EntityCache.this.sizeTracker.decrementSize(size);
            }

            this.cachingManager.selectedForReplacement(this.key, ((BeanData)leastActiveBean).bean);
         }

         return count;
      }
   }

   final class SizeTracker extends Ring {
      private long size = 0L;

      synchronized void decrementSize(int beanSize) {
         this.size -= (long)beanSize;
      }

      synchronized void insertKeyData(KeyData e) {
         this.insert(e);
      }

      synchronized void removeKeyData(KeyData e) {
         e.remove();
      }

      synchronized void removeKeyData(KeyData e, int beanSize) {
         e.remove();
         this.size -= (long)beanSize;
      }

      synchronized void moveKeyDataToHead(KeyData e) {
         e.remove();
         this.insert(e);
      }

      synchronized long getSize() {
         return this.size;
      }

      protected KeyData acquireSpace(Transaction ourTx, long target, int beanSize, int incr) throws CacheFullException {
         synchronized(this) {
            this.size += (long)incr;
            if (EntityCache.debugLogger.isDebugEnabled()) {
               EntityCache.debug(EntityCache.this.cacheName + " shrinkNext: increased cache size is now " + this.size + ", size limit is " + target);
            }

            if (this.size <= target) {
               return null;
            }

            MRUElement lru = this.bwd;
            if (lru != this) {
               lru.remove();
               this.insert(lru);
               return (KeyData)lru;
            }
         }

         synchronized(EntityCache.this.passivator) {
            if (this.size <= target) {
               return null;
            } else {
               if (EntityCache.debugLogger.isDebugEnabled()) {
                  EntityCache.debug(EntityCache.this.cacheName + " cache full at size: " + this.size + ", begin passivation of beans in tx. ");
               }

               long freed = EntityCache.this.passivator.passivate(ourTx, target, beanSize);
               if (EntityCache.debugLogger.isDebugEnabled()) {
                  EntityCache.debug(EntityCache.this.cacheName + " after passivate, cache size is now " + this.size);
               }

               if (freed >= (long)beanSize) {
                  return null;
               } else {
                  this.decrementSize(beanSize);
                  throw new CacheFullException("cache size after cleaning=" + this.size + ", max allowable cache size=" + target + ", extra free space required but not obtainable = " + beanSize);
               }
            }
         }
      }
   }

   static class Ring extends MRUElement {
      Ring() {
         this.reset();
      }

      final boolean isEmpty() {
         return this.fwd == this;
      }

      final void reset() {
         this.fwd = this.bwd = this;
      }

      final synchronized boolean contains(MRUElement e) {
         for(MRUElement i = this.fwd; i != this; i = i.fwd) {
            if (i == e) {
               return true;
            }
         }

         return false;
      }

      final void insert(MRUElement e) {
         e.fwd = this.fwd;
         e.bwd = this;
         this.fwd.bwd = e;
         this.fwd = e;
      }
   }

   static class MRUElement {
      MRUElement fwd;
      MRUElement bwd;

      final void remove() {
         this.bwd.fwd = this.fwd;
         this.fwd.bwd = this.bwd;
         this.fwd = this.bwd = null;
      }
   }
}
