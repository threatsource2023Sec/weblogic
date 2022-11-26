package com.bea.common.security.saml.utils;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public abstract class SAMLAbstractMemCache {
   private static final String BASE_NAME = "SAMLMemCache";
   private String name = null;
   private long period = 0L;
   private HashMap cache = null;
   private Timer timer = null;
   private LoggerSpi LOGGER = null;

   protected void logDebug(String msg) {
      if (this.LOGGER != null && this.LOGGER.isDebugEnabled()) {
         this.LOGGER.debug(this.name + ": " + msg);
      }

   }

   protected final void logDebug(String msg, Throwable t) {
      this.logDebug(msg + ": " + t.toString());
   }

   private SAMLAbstractMemCache() {
   }

   public SAMLAbstractMemCache(LoggerSpi logger, String name, long period) {
      this.LOGGER = logger;
      this.name = "SAMLMemCache/" + name;
      this.period = period;
      this.cache = new HashMap();
      this.logDebug("SAMLAbstractMemCache()");
   }

   protected final synchronized boolean init() {
      this.timer = new Timer(true);
      this.timer.schedule(new CacheTimerTask(this), this.period, this.period);
      if (this.timer == null) {
         return false;
      } else {
         this.logDebug("Initialized");
         return true;
      }
   }

   protected final synchronized void release() {
      this.timer.cancel();
      this.timer = null;
      this.logDebug("release(): timer cancelled");
      this.flush();
      this.logDebug("release(): cache flushed");
      this.logDebug("Released");
   }

   protected final synchronized void flush() {
      this.cache = new HashMap();
      this.logDebug("Flushed cache");
   }

   protected final synchronized boolean addEntry(String key, long expire, Object object) {
      if (key != null && object != null) {
         if (this.cache.containsKey(key)) {
            return false;
         } else {
            this.cache.put(key, new CacheEntry(key, expire, object));
            return true;
         }
      } else {
         return false;
      }
   }

   protected final synchronized Object getEntry(String key) {
      return this.getEntryInternal(key);
   }

   protected final synchronized Object removeEntry(String key) {
      Object obj = this.getEntryInternal(key);
      if (obj != null) {
         this.removeEntryInternal(key);
      }

      return obj;
   }

   private final synchronized Object getEntryInternal(String key) {
      if (key == null) {
         return null;
      } else {
         CacheEntry entry = (CacheEntry)this.cache.get(key);
         return entry != null ? entry.getObject() : null;
      }
   }

   private final synchronized void removeEntryInternal(String key) {
      this.cache.remove(key);
   }

   private final synchronized void removeEntryInternal(String[] keys) {
      if (keys != null) {
         for(int i = 0; i < keys.length; ++i) {
            String key = keys[i];
            if (key != null) {
               this.cache.remove(key);
            }
         }

      }
   }

   private final synchronized HashMap getClone() {
      return (HashMap)this.cache.clone();
   }

   private static class CacheTimerTask extends TimerTask {
      private static final int REMOVE_CHUNK_SIZE = 10;
      private SAMLAbstractMemCache mc = null;
      private volatile boolean active = true;

      public CacheTimerTask(SAMLAbstractMemCache mc) {
         this.mc = mc;
      }

      public void run() {
         long start = System.currentTimeMillis();
         if (!this.active) {
            this.mc.logDebug("timerExpired(): not active, returning");
         } else {
            HashMap clone = this.mc.getClone();
            Collection values = clone.values();
            int entryCount = values.size();
            int removeCount = 0;
            String[] deadKeys = new String[10];
            int keyidx = 0;
            Iterator it = values.iterator();

            while(it.hasNext()) {
               if (!this.active) {
                  this.mc.logDebug("timerExpired(): Execution cancelled, returning");
                  return;
               }

               CacheEntry entry = (CacheEntry)it.next();
               if (entry.getExpire() < start) {
                  deadKeys[keyidx++] = entry.getKey();
                  ++removeCount;
               }

               if (keyidx == 10) {
                  if (!this.active) {
                     this.mc.logDebug("timerExpired(): Execution cancelled, returning");
                     return;
                  }

                  this.mc.removeEntryInternal(deadKeys);
                  keyidx = 0;
               }
            }

            if (keyidx > 0) {
               for(int i = keyidx; i < 10; ++i) {
                  deadKeys[i] = null;
               }

               if (!this.active) {
                  this.mc.logDebug("timerExpired(): Execution cancelled, returning");
                  return;
               }

               this.mc.removeEntryInternal(deadKeys);
            }

            long finish = System.currentTimeMillis();
            this.mc.logDebug("timerExpired(): Removed " + removeCount + " of " + entryCount + " entries in " + (finish - start) + " milliseconds");
         }
      }

      public boolean cancel() {
         this.mc.logDebug("CacheTimerTask.cancel()");
         this.active = false;
         return true;
      }
   }

   private static class CacheEntry {
      private String key = null;
      private long expire = 0L;
      private Object object = null;

      public CacheEntry(String key, long expire, Object object) {
         this.key = key;
         this.expire = expire;
         this.object = object;
      }

      public String getKey() {
         return this.key;
      }

      public long getExpire() {
         return this.expire;
      }

      public Object getObject() {
         return this.object;
      }
   }
}
