package com.bea.security.saml2.util.cache;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.store.data.SAML2CacheEntry;
import com.bea.common.security.store.data.SAML2CacheEntryId;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.Saml2Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

public final class SAML2StoreServiceBasedCacheImpl implements SAML2Cache {
   private static final int MIN_POLL_INTERVAL = 20;
   private static final int MAX_POLL_INTERVAL = 3600;
   private static final int DEFAULT_POLL_INTERVAL = 30;
   private LoggerSpi logger;
   private StoreService storeService;
   private String domainName;
   private String realmName;
   private String cacheName;
   private int maxSize;
   private int timeout;
   private Timer timer;

   public SAML2StoreServiceBasedCacheImpl(LoggerSpi log, StoreService store, String domainName, String realmName, String cacheName, int maxSize, int timeout) {
      if (maxSize >= 0 && timeout >= 0) {
         this.logger = log;
         this.storeService = store;
         this.domainName = domainName;
         this.realmName = realmName;
         this.cacheName = cacheName;
         this.maxSize = maxSize;
         this.timeout = timeout;
         this.resetTimer();
      } else {
         throw new IllegalArgumentException(Saml2Logger.getMaxCacheSizeOrTimeoutMustNotBeNegative());
      }
   }

   SAML2StoreServiceBasedCacheImpl(LoggerSpi log, StoreService store, String domainName, String realmName, String cacheName, int maxSize, int timeout, boolean init) {
      if (maxSize >= 0 && timeout >= 0) {
         this.logger = log;
         this.storeService = store;
         this.domainName = domainName;
         this.realmName = realmName;
         this.cacheName = cacheName;
         this.maxSize = maxSize;
         this.timeout = timeout;
         if (init) {
            this.resetTimer();
         }

      } else {
         throw new IllegalArgumentException(Saml2Logger.getMaxCacheSizeOrTimeoutMustNotBeNegative());
      }
   }

   private synchronized void resetTimer() {
      String method = "initTimer: ";
      if (this.timer != null) {
         this.timer.cancel();
      }

      this.timer = new Timer(true);
      int period = 30;
      if (this.timeout != 0) {
         period = Math.max(this.timeout, 20);
      }

      this.timer.scheduleAtFixedRate(new TimerTask() {
         public void run() {
            SAML2StoreServiceBasedCacheImpl.this.itemExpirationCheck();
         }
      }, new Date(), (long)(period * 1000));
      if (this.logger != null && this.logger.isDebugEnabled()) {
         this.logger.debug(method + "shall run itemExpirationCheck() every " + period + " seconds.");
      }

      period = Math.min(period * 10, 3600);
      this.timer.scheduleAtFixedRate(new TimerTask() {
         public void run() {
            SAML2StoreServiceBasedCacheImpl.this.cacheSizeOverflowCheck();
         }
      }, new Date(), (long)(period * 1000));
      if (this.logger != null && this.logger.isDebugEnabled()) {
         this.logger.debug(method + "shall run cacheSizeOverflowCheck() every " + period + " seconds.");
      }

   }

   void itemExpirationCheck() {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;
      Transaction t = null;

      try {
         t = pm.currentTransaction();
         t.begin();

         try {
            q = pm.newQuery(SAML2CacheEntry.class, "domainName == :dn && realmName == :rn && cacheName == :cn && expirationTime < :now ");
            q.deletePersistentAll(new Object[]{this.domainName, this.realmName, this.cacheName, new Long(System.currentTimeMillis())});
            t.commit();
         } catch (Throwable var12) {
            if (t.isActive()) {
               t.rollback();
            }

            throw new RuntimeException(var12);
         }
      } finally {
         if (q != null) {
            q.closeAll();
         }

         try {
            pm.close();
         } catch (Throwable var11) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

   }

   void cacheSizeOverflowCheck() {
      int total = this.getCacheSize();
      if (total > this.maxSize && this.maxSize > 0) {
         PersistenceManager pm = this.storeService.getPersistenceManager();
         Query q = null;

         try {
            Transaction t = pm.currentTransaction();
            t.begin();

            try {
               q = pm.newQuery(SAML2CacheEntry.class, "domainName == :dn && realmName == :rn && cacheName == :cn");
               q.setOrdering("expirationTime ascending");
               q.setRange(0L, (long)(total - this.maxSize));
               List results = (List)q.executeWithArray(new Object[]{this.domainName, this.realmName, this.cacheName});
               pm.deletePersistentAll(results);
               t.commit();
            } catch (Throwable var13) {
               if (t.isActive()) {
                  t.rollback();
               }

               throw new RuntimeException(var13);
            }
         } finally {
            if (q != null) {
               q.closeAll();
            }

            try {
               pm.close();
            } catch (Throwable var12) {
               this.logger.debug("Unable to close persistence manager.");
            }

         }
      }

   }

   public Object get(String key) throws SAML2CacheException {
      String method = "get: ";
      PersistenceManager pm = this.storeService.getPersistenceManager();
      SAML2CacheEntryId id = new SAML2CacheEntryId(this.domainName, this.realmName, this.cacheName, key);

      Object var6;
      try {
         try {
            SAML2CacheEntry cacheObject = (SAML2CacheEntry)pm.getObjectById(id);
            if (cacheObject == null || this.isExpired(cacheObject)) {
               var6 = null;
               return var6;
            }

            var6 = this.deserializeValue(cacheObject.getValue());
            return var6;
         } catch (JDOObjectNotFoundException var18) {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "can not find cached object with key: " + key);
            }
         } catch (Exception var19) {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "errors occured.", var19);
            }

            throw new SAML2CacheException(var19);
         }

         var6 = null;
      } finally {
         try {
            pm.close();
         } catch (Throwable var17) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

      return var6;
   }

   public void put(String key, Object value) throws SAML2CacheException {
      this.put(key, value, System.currentTimeMillis() + (long)(this.timeout * 1000));
   }

   public void put(String key, Object value, Date expireTime) throws SAML2CacheException {
      if (expireTime == null) {
         throw new IllegalArgumentException(Saml2Logger.getParameterMustNotBeNull("SAML2Cache.put()", "expirationTime"));
      } else if (expireTime.before(new Date())) {
         throw new SAML2CacheException(Saml2Logger.getSAML2AlreadyExpiredItem(expireTime.toString()));
      } else {
         this.put(key, value, expireTime.getTime());
      }
   }

   private void put(String key, Object value, long millisOfExpTime) throws SAML2CacheException {
      String method = "put: ";
      PersistenceManager pm = this.storeService.getPersistenceManager();
      SAML2CacheEntryId id = new SAML2CacheEntryId(this.domainName, this.realmName, this.cacheName, key);
      SAML2CacheEntry oldObj = null;
      Transaction t = null;
      byte[] tempValue = this.serializeValue(value);

      try {
         t = pm.currentTransaction();
         t.begin();

         try {
            oldObj = (SAML2CacheEntry)pm.getObjectById(id);
         } catch (Exception var20) {
         }

         if (oldObj == null) {
            oldObj = new SAML2CacheEntry(this.domainName, this.realmName, this.cacheName, key, tempValue, millisOfExpTime);
         } else {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "key " + key + " is already existing in cache, assoaciate it with new item.");
            }

            oldObj.setValue(tempValue);
            oldObj.setExpirationTime(millisOfExpTime);
         }

         pm.makePersistent(oldObj);
         t.commit();
         if (this.logger != null && this.logger.isDebugEnabled()) {
            this.logger.debug(method + "item with key " + key + " is saved in cache.");
         }
      } catch (Throwable var21) {
         if (this.logger != null && this.logger.isDebugEnabled()) {
            this.logger.debug(method + "errors occured.", var21);
         }

         if (t != null && t.isActive()) {
            t.rollback();
         }

         throw new SAML2CacheException(var21);
      } finally {
         try {
            pm.close();
         } catch (Throwable var19) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

   }

   public Object remove(String key) throws SAML2CacheException {
      String method = "remove: ";
      PersistenceManager pm = this.storeService.getPersistenceManager();
      SAML2CacheEntryId id = new SAML2CacheEntryId(this.domainName, this.realmName, this.cacheName, key);
      Object retVal = null;
      Transaction t = null;

      try {
         t = pm.currentTransaction();
         t.begin();

         try {
            SAML2CacheEntry cached = (SAML2CacheEntry)pm.getObjectById(id);
            if (cached != null && !this.isExpired(cached)) {
               retVal = this.deserializeValue(cached.getValue());
               pm.deletePersistent(cached);
            } else {
               retVal = null;
            }

            t.commit();
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "key " + key + " and associated item have been removed from cache.");
            }
         } catch (JDOObjectNotFoundException var16) {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "key " + key + " does not exist in cache.");
            }

            if (t.isActive()) {
               t.rollback();
            }
         } catch (Throwable var17) {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "errors occured", var17);
            }

            if (t.isActive()) {
               t.rollback();
            }

            throw new SAML2CacheException(var17);
         }
      } finally {
         try {
            pm.close();
         } catch (Throwable var15) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

      return retVal;
   }

   public void clear() throws SAML2CacheException {
      String method = "clear: ";
      if (this.logger != null) {
         this.logger.info(Saml2Logger.getClearingCache(this.cacheName));
      }

      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;
      Transaction t = null;

      try {
         t = pm.currentTransaction();
         t.begin();

         try {
            q = pm.newQuery(SAML2CacheEntry.class, "domainName == :dn && realmName == :rn && cacheName == :cn");
            q.deletePersistentAll(new Object[]{this.domainName, this.realmName, this.cacheName});
            t.commit();
            if (this.logger != null) {
               this.logger.info(Saml2Logger.getCacheCleared(this.cacheName));
            }
         } catch (Throwable var13) {
            if (this.logger != null && this.logger.isDebugEnabled()) {
               this.logger.debug(method + "errors occured", var13);
            }

            if (t.isActive()) {
               t.rollback();
            }

            throw new SAML2CacheException(var13);
         }
      } finally {
         if (q != null) {
            q.closeAll();
         }

         try {
            pm.close();
         } catch (Throwable var12) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

   }

   public int getCacheSize() throws SAML2CacheException {
      String method = "getCacheSize: ";
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;
      long total = 0L;

      try {
         q = pm.newQuery(SAML2CacheEntry.class, " domainName == :dn && realmName == :rn && this.cacheName == :cn && this.expirationTime >= :today");
         Object[] parameters = new Object[]{this.domainName, this.realmName, this.cacheName, new Long(System.currentTimeMillis())};
         if (this.storeService.getStoreId().startsWith("rdbms")) {
            q.setResult("count(this)");
            total = (Long)q.executeWithArray(parameters);
         } else {
            total = (long)((List)q.executeWithArray(parameters)).size();
         }
      } catch (Exception var14) {
         if (this.logger != null && this.logger.isDebugEnabled()) {
            this.logger.debug(method + "errors occured.", var14);
         }

         throw new SAML2CacheException(var14);
      } finally {
         if (q != null) {
            q.closeAll();
         }

         try {
            pm.close();
         } catch (Throwable var13) {
            this.logger.debug("Unable to close persistence manager.");
         }

      }

      return (int)total;
   }

   public void configUpdated(int newMaxCacheSize, int newTimeout) throws SAML2CacheException {
      this.maxSize = newMaxCacheSize;
      this.timeout = newTimeout;
      this.resetTimer();
   }

   private boolean isExpired(SAML2CacheEntry item) {
      return item.getExpirationTime() < System.currentTimeMillis();
   }

   private byte[] serializeValue(Object value) throws SAML2CacheException {
      if (value == null) {
         return null;
      } else if (!(value instanceof Serializable)) {
         throw new IllegalArgumentException();
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            return baos.toByteArray();
         } catch (IOException var4) {
            throw new SAML2CacheException(var4);
         }
      }
   }

   private Object deserializeValue(byte[] binVal) throws SAML2CacheException {
      if (binVal != null && binVal.length != 0) {
         try {
            ObjectInputStream oos = new ObjectInputStream(new ByteArrayInputStream(binVal));
            return oos.readObject();
         } catch (Exception var3) {
            throw new SAML2CacheException(var3);
         }
      } else {
         return null;
      }
   }
}
