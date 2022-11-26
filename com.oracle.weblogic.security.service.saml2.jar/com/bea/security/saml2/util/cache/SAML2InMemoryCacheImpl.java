package com.bea.security.saml2.util.cache;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.Saml2Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SAML2InMemoryCacheImpl implements SAML2Cache {
   static final float DEFAULT_LOAD_FACTOR = 0.75F;
   static final int DEFAULT_INITIAL_CAPACITY = 16;
   protected final LoggerSpi logger;
   private final String cacheName;
   private int maxSize;
   private int timeOut;
   private final Map map;
   private Timer timer;

   SAML2InMemoryCacheImpl(String theCacheName, LoggerSpi theLogger, int theMaxSize, int theTimeout) {
      this(theCacheName, theLogger, theMaxSize, theTimeout, false);
   }

   SAML2InMemoryCacheImpl(String theCacheName, LoggerSpi theLogger, int theMaxSize, int theTimeout, boolean accessOrder) {
      this.timer = new Timer(true);
      if (theMaxSize >= 0 && theTimeout >= 0) {
         this.cacheName = theCacheName;
         this.maxSize = theMaxSize;
         this.timeOut = theTimeout;
         this.logger = theLogger;
         this.map = this.createMap(this.maxSize, accessOrder);
      } else {
         throw new IllegalArgumentException(Saml2Logger.getMaxCacheSizeOrTimeoutMustNotBeNegative());
      }
   }

   protected Map createMap(int theMaxSize, boolean accessOrder) {
      return (Map)(theMaxSize > 0 ? new BoundedLinkedHashMap(this.logger, theMaxSize, 16, 0.75F, accessOrder) : new HashMap(16));
   }

   public synchronized void put(Object key, Object value) throws SAML2CacheException {
      this.put(key, value, (Date)null);
   }

   public synchronized void put(Object key, Object value, Date expireTime) throws SAML2CacheException {
      if (value == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("put", "value"));
      } else {
         expireTime = expireTime == null ? new Date(System.currentTimeMillis() + (long)(this.timeOut * 1000)) : expireTime;
         this.doPut(key, value, expireTime);
      }
   }

   protected void doPut(final Object key, Object value, Date expireTime) throws SAML2CacheException {
      if (key == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("doPut", "key"));
      } else {
         TimerTask timerTask = new TimerTask() {
            public void run() {
               SAML2InMemoryCacheImpl.this.handleExpiration(key);
            }
         };
         if (expireTime.before(new Date(System.currentTimeMillis()))) {
            throw new SAML2CacheException(Saml2Logger.getSAML2AlreadyExpiredItem(expireTime.toString()));
         } else {
            this.timer.schedule(timerTask, expireTime);
            Entry entry = new Entry(value, timerTask);
            Entry previousEntry = (Entry)this.map.put(key, entry);
            if (previousEntry != null) {
               previousEntry.cancelTimerTask();
            }

         }
      }
   }

   protected synchronized void handleExpiration(Object key) {
      if (key != null) {
         if (this.logger != null && this.logger.isDebugEnabled()) {
            this.logger.debug(this.cacheName + " - item: " + key + " expired.");
         }

         this.map.remove(key);
      }

   }

   public synchronized void clear() {
      this.logger.info(Saml2Logger.getClearingCache(this.cacheName));
      this.map.clear();
      this.timer.cancel();
      this.timer = new Timer(true);
      this.logger.info(Saml2Logger.getCacheCleared(this.cacheName));
   }

   public Object get(Object key) {
      if (key == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("get", "value"));
      } else {
         Entry entry = (Entry)this.map.get(key);
         return entry == null ? null : entry.value;
      }
   }

   public synchronized Object remove(Object key) {
      if (key == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("remove", "key"));
      } else {
         Entry entry = (Entry)this.map.remove(key);
         Object value = null;
         if (entry != null) {
            entry.cancelTimerTask();
            value = entry.value;
         }

         return value;
      }
   }

   public void configUpdated(int theMaxSize, int theTimeout) {
      if (theMaxSize >= 0 && theTimeout >= 0) {
         this.maxSize = theMaxSize;
         this.timeOut = theTimeout;
         if (this.map instanceof BoundedLinkedHashMap) {
            ((BoundedLinkedHashMap)this.map).setMaxSize(this.maxSize);
         }

      } else {
         throw new IllegalArgumentException(Saml2Logger.getMaxCacheSizeOrTimeoutMustNotBeNegative());
      }
   }

   public int getCacheSize() {
      return this.map.size();
   }

   static class BoundedLinkedHashMap extends LinkedHashMap {
      private final LoggerSpi logger;
      private int maxSize;

      void setMaxSize(int theMaxSize) {
         this.maxSize = theMaxSize;
      }

      BoundedLinkedHashMap(LoggerSpi theLogger, int theMaxSize, int theInitialCapacity, float loadFactor, boolean accessOrder) {
         super(theInitialCapacity, loadFactor, accessOrder);
         this.logger = theLogger;
         this.maxSize = theMaxSize;
      }

      protected boolean removeEldestEntry(Map.Entry eldest) {
         boolean canRemove = this.maxSize != 0 && this.size() > this.maxSize;
         if (canRemove && eldest != null) {
            Entry entry = (Entry)eldest.getValue();
            if (entry != null) {
               entry.cancelTimerTask();
            }

            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug("maximum size reached, item to be removed: " + eldest.getKey());
            }
         }

         return canRemove;
      }
   }

   static final class Entry {
      final Object value;
      final TimerTask timerTask;

      Entry(Object aValue, TimerTask aTimerTask) {
         this.value = aValue;
         this.timerTask = aTimerTask;
      }

      void cancelTimerTask() {
         if (this.timerTask != null) {
            this.timerTask.cancel();
         }

      }
   }
}
