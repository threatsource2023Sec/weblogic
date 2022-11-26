package weblogic.cache.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheException;
import weblogic.cache.CacheScope;
import weblogic.cache.CacheValue;
import weblogic.cache.RefWrapper;
import weblogic.cache.StallEvent;
import weblogic.cache.StallListener;
import weblogic.cache.utils.BubblingCache;

public class CacheSystem {
   protected boolean verbose = false;
   protected boolean debug = false;
   protected List scopes = new ArrayList();
   protected Map scopeMap = new HashMap();
   protected Set currentLocks = new HashSet();
   protected List listeners;
   protected boolean listening = true;
   protected int stallTime = 60000;
   protected List stallListeners = new ArrayList(1);

   private List getListeners() {
      if (this.listeners == null) {
         try {
            Object o = this.getValueFromAnyScope("weblogic.cache.CacheListener");
            if (o instanceof List) {
               this.listeners = (List)o;
               this.listening = true;
            } else if (o != null) {
               ArrayList al = new ArrayList(1);
               al.add(o);
               this.listeners = al;
               this.listening = true;
            } else {
               this.listeners = new ArrayList(0);
               this.listening = false;
            }
         } catch (CacheException var3) {
            this.listeners = new ArrayList(0);
            this.listening = false;
         }
      }

      return this.listeners;
   }

   protected void sendCacheUpdateEvent(CacheListener.CacheEvent ce) {
      int size = this.getListeners().size();

      for(int i = 0; i < size; ++i) {
         ((CacheListener)this.listeners.get(i)).cacheUpdateOccurred(ce);
      }

   }

   protected void sendCacheAccessEvent(CacheListener.CacheEvent ce) {
      int size = this.getListeners().size();

      for(int i = 0; i < size; ++i) {
         ((CacheListener)this.listeners.get(i)).cacheAccessOccurred(ce);
      }

   }

   protected void sendCacheFlushEvent(CacheListener.CacheEvent ce) {
      int size = this.getListeners().size();

      for(int i = 0; i < size; ++i) {
         ((CacheListener)this.listeners.get(i)).cacheFlushOccurred(ce);
      }

   }

   public void setStallTime(int stallTime) {
      this.stallTime = stallTime;
   }

   public int getStallTime() {
      return this.stallTime;
   }

   public void addStallListener(StallListener stallListener) {
      this.stallListeners.add(stallListener);
   }

   public void removeStallListener(StallListener stallListener) {
      this.stallListeners.remove(stallListener);
   }

   protected CacheScope getScopeObject(String scope) {
      synchronized(this.scopeMap) {
         return (CacheScope)this.scopeMap.get(scope);
      }
   }

   protected Object getValueFromAnyScope(String key) throws CacheException {
      synchronized(this.scopeMap) {
         Iterator i = this.scopes.iterator();

         Object value;
         do {
            if (!i.hasNext()) {
               return null;
            }

            CacheScope scope = (CacheScope)i.next();
            value = scope.getAttribute(key);
         } while(value == null);

         return value;
      }
   }

   public boolean takeLock(String scope, String key) throws CacheException {
      String lockKey = key + ".lock";
      synchronized(this.getScopeObject(scope)) {
         if (this.getValueFromScope(scope, lockKey) != null) {
            if (this.verbose) {
               System.out.println("Not acquired (" + scope + "): " + lockKey);
            }

            return false;
         }

         Object lock = new Object();
         this.setValueInScope(scope, lockKey, lock);
         this.currentLocks.add(new CacheKey(scope, key));
      }

      if (this.verbose) {
         System.out.println("Taken (" + scope + "): " + lockKey);
      }

      return true;
   }

   public void waitOnLock(String scope, String key) throws CacheException {
      String lockKey = key + ".lock";
      Object scopeLock = this.getScopeObject(scope);
      Object lock = this.getValueFromScope(scope, lockKey);
      if (lock == null) {
         synchronized(scopeLock) {
            lock = this.getValueFromScope(scope, lockKey);
            if (lock == null) {
               Object newLock = new Object();
               this.setValueInScope(scope, lockKey, newLock);
               this.currentLocks.add(new CacheKey(scope, key));
               if (this.verbose) {
                  System.out.println("Taken (" + scope + "): " + lockKey);
               }

               return;
            }
         }
      }

      while(lock != null) {
         long startTime = System.currentTimeMillis();
         synchronized(lock) {
            try {
               lock.wait((long)this.stallTime);
            } catch (InterruptedException var12) {
            }
         }

         if ((double)(System.currentTimeMillis() - startTime) > (double)this.stallTime * 0.99) {
            StallEvent se = new StallEvent(this.stallTime, scope, key);
            Iterator i = this.stallListeners.iterator();

            while(i.hasNext()) {
               StallListener stallListener = (StallListener)i.next();
               stallListener.stall(se);
            }
         }

         lock = this.getValueFromScope(scope, lockKey);
         if (lock == null) {
            synchronized(scopeLock) {
               lock = this.getValueFromScope(scope, lockKey);
               if (lock == null) {
                  Object newLock = new Object();
                  this.setValueInScope(scope, lockKey, newLock);
                  this.currentLocks.add(new CacheKey(scope, key));
                  break;
               }
            }
         }
      }

      if (this.verbose) {
         System.out.println("Taken (" + scope + "): " + lockKey);
      }

   }

   public void releaseLock(String scope, String key) throws CacheException {
      String lockKey = key + ".lock";
      Object scopeLock = this.getScopeObject(scope);
      synchronized(scopeLock) {
         Object lock = this.getValueFromScope(scope, lockKey);
         this.removeValueInScope(scope, lockKey);
         this.currentLocks.remove(new CacheKey(scope, key));
         if (lock != null) {
            synchronized(lock) {
               lock.notifyAll();
            }
         }
      }

      if (this.verbose) {
         System.out.println("Released (" + scope + "): " + lockKey);
      }

   }

   public void releaseAllLocks() throws CacheException {
      Iterator locks = this.currentLocks.iterator();
      boolean error = false;

      while(locks.hasNext()) {
         CacheKey cacheLock = (CacheKey)locks.next();

         try {
            this.releaseLock(cacheLock.scope, cacheLock.key);
         } catch (CacheException var5) {
            error = true;
         }
      }

      if (error) {
         throw new CacheException("Could not release all locks");
      }
   }

   public void registerScope(String name, CacheScope scope) {
      if (name == null) {
         throw new IllegalArgumentException("Name of scope cannot be null");
      } else if (scope == null) {
         throw new IllegalArgumentException("Scope cannot be null");
      } else {
         synchronized(this.scopeMap) {
            if (this.scopeMap.containsKey(name)) {
               throw new IllegalArgumentException("Scope already registered under " + name);
            } else {
               this.scopeMap.put(name, scope);
               this.scopes.add(scope);
            }
         }
      }
   }

   public CacheValue getCurrentCache(String scope, String cacheName) throws CacheException {
      Object o = this.getValueFromScope(scope, cacheName);
      if (o != null && !(o instanceof RefWrapper)) {
         System.out.println(o);
         throw new CacheException("You are using the same name for two caches that are not the same: " + cacheName);
      } else {
         RefWrapper ref = (RefWrapper)o;
         CacheValue cache = (CacheValue)((CacheValue)(ref == null ? null : ref.get()));
         return cache;
      }
   }

   public CacheValue getCache(String scope, String cacheName) throws CacheException {
      long startTime = 0L;
      if (this.listening) {
         startTime = System.currentTimeMillis();
      }

      boolean locked = this.takeLock(scope, cacheName);
      if ("true".equals(this.getValueFromAnyScope("_cache_refresh"))) {
         if (!locked) {
            this.waitOnLock(scope, cacheName);
         }

         if (this.verbose) {
            System.out.println("_cache_refresh is active");
         }

         return null;
      } else {
         CacheValue cache;
         RefWrapper ref;
         for(cache = this.getCurrentCache(scope, cacheName); !locked && cache == null; cache = (CacheValue)((CacheValue)(ref == null ? null : ref.get()))) {
            this.waitOnLock(scope, cacheName);
            locked = true;
            ref = (RefWrapper)this.getValueFromScope(scope, cacheName);
         }

         if (!locked) {
            return cache;
         } else if (cache != null) {
            int timeout = cache.getTimeout();
            if (timeout != -1) {
               long created = cache.getCreated();
               if (System.currentTimeMillis() - created > (long)timeout) {
                  if (this.verbose) {
                     System.out.println("Cache " + scope + ":" + cacheName + " timed out");
                  }

                  return null;
               }
            }

            if (cache.getFlush()) {
               return null;
            } else {
               this.releaseLock(scope, cacheName);
               if (this.verbose) {
                  System.out.println("Returning contents of cache");
               }

               if (this.listening) {
                  CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
                  ce.setTime((int)(System.currentTimeMillis() - startTime));
                  ce.setScope(scope);
                  ce.setScopeType(this.getScopeObject(scope).getClass().getName());
                  ce.setName(cacheName);
                  this.sendCacheAccessEvent(ce);
               }

               return cache;
            }
         } else {
            if (this.verbose) {
               System.out.println("Cache " + scope + ":" + cacheName + " has nothing in it");
            }

            return null;
         }
      }
   }

   public void flushCache(String scope, String cacheName) throws CacheException {
      this.removeValueInScope(scope, cacheName);
      if (this.listening) {
         CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
         ce.setScope(scope);
         ce.setScopeType(this.getScopeObject(scope).getClass().getName());
         ce.setName(cacheName);
         this.sendCacheFlushEvent(ce);
      }

   }

   public void setCache(String scope, String cacheName, CacheValue value, int time) throws CacheException {
      if (value == null) {
         this.removeValueInScope(scope, cacheName);
      } else {
         if (value.getTimeout() != -1) {
            value.setCreated(System.currentTimeMillis());
         }

         if (this.verbose) {
            System.out.println("Cache " + scope + ":" + cacheName + " is now set");
         }

         this.setValueInScope(scope, cacheName, new RefWrapper(value));
      }

      this.releaseLock(scope, cacheName);
      if (this.listening) {
         CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
         ce.setTime(time);
         ce.setScope(scope);
         ce.setScopeType(this.getScopeObject(scope).getClass().getName());
         ce.setName(cacheName);
         ce.setValue(value);
         this.sendCacheUpdateEvent(ce);
      }

   }

   public CacheValue getCurrentCache(String scope, String cacheName, int size, KeySet keySet) throws CacheException {
      String key = keySet.getKey();
      this.waitOnLock(scope, cacheName);

      Object map;
      try {
         map = (Map)this.getValueFromScope(scope, cacheName);
      } catch (ClassCastException var9) {
         map = null;
      }

      if (map == null) {
         if (size == -1) {
            map = Collections.synchronizedMap(new HashMap());
         } else {
            map = new BubblingCache(size);
         }

         this.setValueInScope(scope, cacheName, map);
      }

      this.releaseLock(scope, cacheName);
      RefWrapper ref = (RefWrapper)((Map)map).get(key);
      CacheValue cache = (CacheValue)((CacheValue)(ref == null ? null : ref.get()));
      return cache;
   }

   public CacheValue getCache(String scope, String cacheName, int size, KeySet keySet) throws CacheException {
      String key = keySet.getKey();
      long startTime = 0L;
      if (this.listening) {
         startTime = System.currentTimeMillis();
      }

      String cacheKey = cacheName + '\u0000' + key;
      boolean locked = this.takeLock(scope, cacheKey);
      if ("true".equals(this.getValueFromAnyScope("_cache_refresh"))) {
         if (!locked) {
            this.waitOnLock(scope, cacheKey);
         }

         return null;
      } else {
         CacheValue cache;
         for(cache = this.getCurrentCache(scope, cacheName, size, keySet); !locked && cache == null; cache = this.getCurrentCache(scope, cacheName, size, keySet)) {
            this.waitOnLock(scope, cacheKey);
            locked = true;
         }

         if (!locked) {
            return cache;
         } else if (cache != null) {
            int timeout = cache.getTimeout();
            if (timeout != -1) {
               long created = cache.getCreated();
               if (System.currentTimeMillis() - created > (long)timeout) {
                  return null;
               }
            }

            if (cache.getFlush()) {
               return null;
            } else {
               this.releaseLock(scope, cacheKey);
               if (this.listening) {
                  CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
                  ce.setTime((int)(System.currentTimeMillis() - startTime));
                  ce.setScope(scope);
                  ce.setScopeType(this.getScopeObject(scope).getClass().getName());
                  ce.setName(cacheName);
                  ce.setKeySet(keySet);
                  this.sendCacheAccessEvent(ce);
               }

               return cache;
            }
         } else {
            return null;
         }
      }
   }

   public void setCache(String scope, String cacheName, int size, KeySet keySet, CacheValue value, int time) throws CacheException {
      if (size != 0 && size >= -1) {
         String key = keySet.getKey();
         this.waitOnLock(scope, cacheName);

         Object map;
         try {
            map = (Map)this.getValueFromScope(scope, cacheName);
         } catch (ClassCastException var11) {
            map = null;
         }

         if (map == null) {
            if (size == -1) {
               map = Collections.synchronizedMap(new HashMap());
            } else {
               map = new BubblingCache(size);
            }

            this.setValueInScope(scope, cacheName, map);
         }

         if (value == null) {
            ((Map)map).remove(key);
         } else {
            if (value.getTimeout() != -1) {
               value.setCreated(System.currentTimeMillis());
            }

            ((Map)map).put(key, new RefWrapper(value));
         }

         this.setValueInScope(scope, cacheName, map);
         this.releaseLock(scope, cacheName);
         String cacheKey = cacheName + '\u0000' + key;
         this.releaseLock(scope, cacheKey);
         if (this.listening) {
            CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
            ce.setTime(time);
            ce.setScope(scope);
            ce.setScopeType(this.getScopeObject(scope).getClass().getName());
            ce.setName(cacheName);
            ce.setKeySet(keySet);
            ce.setValue(value);
            ce.setSize(size);
            this.sendCacheUpdateEvent(ce);
         }

      } else {
         throw new CacheException("Invalid value for cache size: " + size);
      }
   }

   public void flushCache(String scope, String cacheName, KeySet keySet) throws CacheException {
      String key = keySet.getKey();
      this.waitOnLock(scope, cacheName);

      Map map;
      try {
         map = (Map)this.getValueFromScope(scope, cacheName);
      } catch (ClassCastException var7) {
         map = null;
      }

      if (map != null) {
         this.releaseLock(scope, cacheName);
         map.remove(key);
         if (this.listening) {
            CacheListener.CacheEvent ce = new CacheListener.CacheEvent();
            ce.setScope(scope);
            ce.setScopeType(this.getScopeObject(scope).getClass().getName());
            ce.setName(cacheName);
            ce.setKeySet(keySet);
            this.sendCacheFlushEvent(ce);
         }

      }
   }

   public Object getValueFromScope(String scope, String key) throws CacheException {
      if (scope.equals("any")) {
         return this.getValueFromAnyScope(key);
      } else {
         CacheScope cacheScope;
         synchronized(this.scopeMap) {
            cacheScope = (CacheScope)this.scopeMap.get(scope);
         }

         if (cacheScope == null) {
            throw new CacheException("Could not find cache scope: " + scope);
         } else {
            return cacheScope.getAttribute(key);
         }
      }
   }

   public void setValueInScope(String scope, String key, Object value) throws CacheException {
      CacheScope cacheScope;
      synchronized(this.scopeMap) {
         cacheScope = (CacheScope)this.scopeMap.get(scope);
      }

      if (cacheScope == null) {
         throw new CacheException("Could not find cache scope: " + scope);
      } else {
         cacheScope.setAttribute(key, value);
      }
   }

   public void removeValueInScope(String scope, String key) throws CacheException {
      CacheScope cacheScope;
      synchronized(this.scopeMap) {
         cacheScope = (CacheScope)this.scopeMap.get(scope);
      }

      if (cacheScope == null) {
         throw new CacheException("Could not find cache scope: " + scope);
      } else {
         cacheScope.removeAttribute(key);
      }
   }

   static class CacheKey {
      String key;
      String scope;

      CacheKey(String scope, String key) {
         if (scope != null && key != null) {
            this.scope = scope;
            this.key = key;
         } else {
            throw new IllegalArgumentException("scope and key should be non-null value");
         }
      }

      public int hashCode() {
         return this.scope.hashCode() * 31 + this.key.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof CacheKey)) {
            return false;
         } else {
            return this.scope.equals(((CacheKey)obj).scope) && this.key.equals(((CacheKey)obj).key);
         }
      }
   }
}
