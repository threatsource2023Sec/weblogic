package weblogic.cache.util;

import commonj.work.WorkManager;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import weblogic.cache.Action;
import weblogic.cache.ActionTrigger;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.CacheStoreListener;
import weblogic.cache.ChangeListener;
import weblogic.cache.EvictionListener;
import weblogic.cache.EvictionStrategy;
import weblogic.cache.MapStatistics;
import weblogic.cache.configuration.BEACacheLogger;
import weblogic.cache.locks.LockManager;
import weblogic.cache.locks.LockManagerImpl;
import weblogic.cache.store.LoadingScheme;
import weblogic.cache.store.WritePolicy;
import weblogic.utils.collections.DelegatingCollection;
import weblogic.utils.collections.DelegatingIterator;
import weblogic.utils.collections.DelegatingSet;
import weblogic.utils.collections.MappingIterator;

public class ConcurrentCacheMap extends AbstractCacheMap implements Serializable {
   private CacheMap.CacheState state;
   private int capacity;
   private long ttl;
   private final EvictionStrategy strategy;
   private final transient LockManager lockManager;
   private transient Logger logger;
   private transient LoadingScheme loadingScheme;
   private transient WritePolicy writePolicy;
   private final ConcurrentMap map;
   private final transient EvictionListenerSupport evictionListenerSupport;
   private final transient ChangeListenerSupport changeSupport;
   private final transient MapStatisticsSupport statsSupport;
   private Set entrySet;
   private static final boolean useJdkConcurrentPkg = Boolean.getBoolean("weblogic.cache.usejdkconcurrentpkg");

   public ConcurrentCacheMap(int size, long ttl, long idleTime, EvictionStrategy s, LoadingScheme loadingScheme, WritePolicy writePolicy, boolean enableLocking, WorkManager listenerWorkManager) {
      this.state = CacheMap.CacheState.Started;
      this.logger = Logger.getLogger(this.getClass().getSimpleName());
      this.capacity = size;
      this.ttl = ttl;
      this.strategy = s;
      if (useJdkConcurrentPkg) {
         this.map = new ConcurrentHashMap(size);
      } else {
         this.map = new WLConcurrentMap(size);
      }

      this.loadingScheme = loadingScheme;
      this.writePolicy = writePolicy;
      this.lockManager = enableLocking ? new LockManagerImpl(this) : null;
      this.evictionListenerSupport = new EvictionListenerSupport(listenerWorkManager);
      this.changeSupport = new ChangeListenerSupport(listenerWorkManager);
      this.statsSupport = new MapStatisticsSupport();
      if (this.logger.isEnabled()) {
         this.logger.info("Creating ConcurrentCacheMap with capacity, strategy, loadingScheme, writePolicy & lockManager as " + this.capacity + ", " + this.strategy + ", " + loadingScheme + ", " + writePolicy + ", " + this.lockManager);
      }

   }

   public final int getCapacity() {
      return this.capacity;
   }

   public final void setCapacity(int newSize) {
      this.capacity = newSize;
   }

   public final long getTTL() {
      return this.ttl;
   }

   public final void setTTL(long ttl) {
      this.ttl = ttl;
   }

   public final EvictionStrategy getEvictionStrategy() {
      return this.strategy;
   }

   public void addEvictionListener(EvictionListener l) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         this.evictionListenerSupport.add(l);
      }
   }

   public void removeEvictionListener(EvictionListener l) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         this.evictionListenerSupport.remove(l);
      }
   }

   public void setListenerWorkManager(WorkManager listenerWorkManager) {
      this.evictionListenerSupport.setWorkManager(listenerWorkManager);
      this.changeSupport.setWorkManager(listenerWorkManager);
   }

   public void loadAll(List keys) {
      if (this.loadingScheme != null) {
         try {
            Map map = this.loadingScheme.loadAll(keys);
            if (map != null) {
               Iterator iter = map.entrySet().iterator();

               while(iter.hasNext()) {
                  Map.Entry entry = (Map.Entry)iter.next();
                  this.internalPut(entry.getKey(), entry.getValue());
               }
            }
         } catch (CacheRuntimeException var6) {
         }
      }

   }

   public CacheEntry getEntry(Object key) {
      CacheEntry e = (CacheEntry)this.map.get(key);
      if (e != null && !isEntryValid(e)) {
         this.remove(key);
         return null;
      } else {
         return e;
      }
   }

   public boolean restoreEntry(CacheEntry entry) {
      if (this.containsKey(entry.getKey())) {
         return false;
      } else {
         this.map.put(entry.getKey(), this.strategy.restoreEntry(entry));
         return true;
      }
   }

   public int size() {
      this.removeAllInvalidEntries();
      return this.map.size();
   }

   public boolean isEmpty() {
      this.removeAllInvalidEntries();
      return this.map.isEmpty();
   }

   public boolean containsKey(Object key) {
      CacheEntry e = (CacheEntry)this.map.get(key);
      if (e == null) {
         return false;
      } else if (!isEntryValid(e)) {
         this.remove(key);
         return false;
      } else {
         return true;
      }
   }

   public Object get(Object key) {
      this.statsSupport.incrementQueryCount();
      CacheEntry e = (CacheEntry)this.map.get(key);
      if (e != null) {
         if (System.currentTimeMillis() > e.getExpirationTime()) {
            e.discard();
         }

         if (e.isDiscarded()) {
            this.map.remove(key);
            Map evictedMap = Collections.singletonMap(key, e.getValue());

            try {
               this.evictionListenerSupport.fireEvent((Object)null, evictedMap, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var6) {
               BEACacheLogger.logUnableToFireListeners(var6);
            }

            e = null;
         }
      }

      if (e != null) {
         this.statsSupport.incrementReadCount();
      } else if (this.loadingScheme != null) {
         try {
            Object loaded = this.loadingScheme.load(key);
            e = this.createAndAddEntry(key, loaded);
         } catch (CacheRuntimeException var5) {
            e = null;
         }
      }

      return e == null ? null : e.getValue();
   }

   public Object remove(Object key) {
      CacheEntry old = (CacheEntry)this.map.remove(key);
      if (old == null) {
         return null;
      } else {
         this.statsSupport.incrementDeleteCount();
         old.discard();

         try {
            this.changeSupport.fireEvent(ChangeEvent.DELETE, old, (Object)null);
         } catch (ListenerSupport.ListenerSupportException var4) {
            BEACacheLogger.logUnableToFireListeners(var4);
         }

         return old.getValue();
      }
   }

   public Object put(Object key, Object value) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         Object oldValue = null;
         CacheEntry entry = this.getEntry(key);
         boolean updatingEntry = entry != null;
         if (entry != null) {
            oldValue = entry.getValue();
         }

         entry = this.internalPut(key, value);
         if (updatingEntry) {
            try {
               this.statsSupport.incrementUpdateCount();
               this.changeSupport.fireEvent(ChangeEvent.UPDATE, entry, oldValue);
            } catch (ListenerSupport.ListenerSupportException var8) {
               BEACacheLogger.logUnableToFireListeners(var8);
            }
         } else {
            try {
               this.statsSupport.incrementCreateCount();
               this.changeSupport.fireEvent(ChangeEvent.CREATE, entry, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var7) {
               BEACacheLogger.logUnableToFireListeners(var7);
            }
         }

         if (this.writePolicy != null) {
            this.writePolicy.store(key, value);
         }

         return oldValue;
      }
   }

   private CacheEntry internalPut(Object key, Object value) {
      CacheEntry entry = this.getEntry(key);
      if (entry == null) {
         return this.createAndAddEntry(key, value);
      } else {
         this.strategy.updateEntry(entry, value);
         return entry;
      }
   }

   private CacheEntry createAndAddEntry(Object key, Object value) {
      CacheEntry entry = this.strategy.createEntry(key, value);
      this.map.put(key, entry);
      this.evict();
      return entry;
   }

   public Object putIfAbsent(Object key, Object value) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         CacheEntry entry = this.getEntry(key);
         if (entry != null) {
            return entry.getValue();
         } else {
            entry = this.strategy.createEntry(key, value);
            CacheEntry oldEntry = (CacheEntry)this.map.putIfAbsent(key, entry);
            if (oldEntry != null) {
               entry.discard();
               return oldEntry.getValue();
            } else {
               this.evict();
               if (this.writePolicy != null) {
                  this.writePolicy.store(key, value);
               }

               return null;
            }
         }
      }
   }

   public void putAll(Map m) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else if (m != null) {
         Iterator var2 = m.keySet().iterator();

         while(var2.hasNext()) {
            Object key = var2.next();
            Object oldValue = null;
            CacheEntry entry = this.getEntry(key);
            boolean updatingEntry = entry != null;
            if (entry != null) {
               oldValue = entry.getValue();
            }

            if (entry == null) {
               entry = this.createAndAddEntry(key, m.get(key));
            } else {
               this.strategy.updateEntry(entry, m.get(key));
            }

            if (updatingEntry) {
               try {
                  this.statsSupport.incrementUpdateCount();
                  this.changeSupport.fireEvent(ChangeEvent.UPDATE, entry, oldValue);
               } catch (ListenerSupport.ListenerSupportException var8) {
                  BEACacheLogger.logUnableToFireListeners(var8);
               }
            } else {
               try {
                  this.statsSupport.incrementCreateCount();
                  this.changeSupport.fireEvent(ChangeEvent.CREATE, entry, (Object)null);
               } catch (ListenerSupport.ListenerSupportException var9) {
                  BEACacheLogger.logUnableToFireListeners(var9);
               }
            }
         }

         if (this.writePolicy != null) {
            this.writePolicy.storeAll(m);
         }

      }
   }

   public void evict() {
      if (this.capacity < this.map.size()) {
         this.removeAllInvalidEntries();

         while(this.capacity < this.map.size()) {
            Map m = this.strategy.evict();
            Iterator i = m.keySet().iterator();

            while(i.hasNext()) {
               this.remove(i.next());
            }

            try {
               this.evictionListenerSupport.fireEvent((Object)null, m, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var3) {
               BEACacheLogger.logUnableToFireListeners(var3);
            }
         }
      }

   }

   public boolean remove(Object key, Object value) {
      throw new UnsupportedOperationException("remove");
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      throw new UnsupportedOperationException("replace");
   }

   public Object replace(Object key, Object newValue) {
      throw new UnsupportedOperationException("replace");
   }

   public void setCacheLoader(CacheLoader loader) {
      if (this.loadingScheme != null) {
         this.loadingScheme.setCacheLoader(loader);
      }

   }

   public void clear() {
      this.map.clear();
      this.statsSupport.incrementClearCount();
      this.strategy.clear();

      try {
         this.changeSupport.fireEvent(ChangeEvent.CLEAR, (Object)null, (Object)null);
      } catch (ListenerSupport.ListenerSupportException var2) {
         BEACacheLogger.logUnableToFireListeners(var2);
      }

   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new AbstractSet() {
            public Iterator iterator() {
               ConcurrentCacheMap.this.removeAllInvalidEntries();
               Iterator i = ConcurrentCacheMap.this.map.values().iterator();
               return new DelegatingIterator(i) {
                  public void remove() {
                     super.remove();

                     try {
                        ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.DELETE, (CacheEntry)this.current(), (Object)null);
                     } catch (ListenerSupport.ListenerSupportException var2) {
                        BEACacheLogger.logUnableToFireListeners(var2);
                     }

                  }
               };
            }

            public boolean contains(Object o) {
               if (!(o instanceof Map.Entry)) {
                  return false;
               } else {
                  Map.Entry entry = (Map.Entry)o;
                  CacheEntry foundEntry = (CacheEntry)ConcurrentCacheMap.this.map.get(entry.getKey());
                  if (foundEntry != null) {
                     if (!ConcurrentCacheMap.isEntryValid(foundEntry)) {
                        this.remove(entry.getKey());
                        return false;
                     } else {
                        return true;
                     }
                  } else {
                     return false;
                  }
               }
            }

            public boolean remove(Object o) {
               if (!(o instanceof CacheEntry)) {
                  return false;
               } else {
                  CacheEntry entry = (CacheEntry)o;
                  Object old = ConcurrentCacheMap.this.remove(entry.getKey());
                  if (old != null) {
                     try {
                        ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.DELETE, entry, (Object)null);
                     } catch (ListenerSupport.ListenerSupportException var5) {
                        BEACacheLogger.logUnableToFireListeners(var5);
                     }

                     return true;
                  } else {
                     return false;
                  }
               }
            }

            public int size() {
               return ConcurrentCacheMap.this.size();
            }

            public void clear() {
               ConcurrentCacheMap.this.clear();

               try {
                  ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.CLEAR, (Object)null, (Object)null);
               } catch (ListenerSupport.ListenerSupportException var2) {
                  BEACacheLogger.logUnableToFireListeners(var2);
               }

            }
         };
      }

      return this.entrySet;
   }

   public ActionTrigger prepare(final Action action) {
      action.setTarget(this);
      if (action instanceof Preparable) {
         try {
            ((Preparable)action).prepare();
         } catch (RuntimeException var3) {
            action.close();
            throw var3;
         }
      }

      return new ActionTrigger() {
         private boolean closed;

         public Object run() {
            if (this.closed) {
               throw new IllegalStateException("closed");
            } else {
               Object var1;
               try {
                  var1 = action.run();
               } finally {
                  this.close();
               }

               return var1;
            }
         }

         public void close() {
            if (!this.closed) {
               this.closed = true;
               action.close();
            }
         }
      };
   }

   public LockManager getLockManager() {
      return this.lockManager;
   }

   public void addCacheLoadListener(CacheLoadListener listener) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         if (this.loadingScheme != null) {
            this.loadingScheme.addCacheLoadListener(listener);
         }

      }
   }

   public void removeCacheLoadListener(CacheLoadListener listener) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         if (this.loadingScheme != null) {
            this.loadingScheme.removeCacheLoadListener(listener);
         }

      }
   }

   public void addCacheStoreListener(CacheStoreListener listener) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         if (this.writePolicy != null) {
            this.writePolicy.addCacheStoreListener(listener);
         }

      }
   }

   public void removeCacheStoreListener(CacheStoreListener listener) {
      if (this.state != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         if (this.writePolicy != null) {
            this.writePolicy.removeCacheStoreListener(listener);
         }

      }
   }

   public LoadingScheme getLoadingScheme() {
      return this.loadingScheme;
   }

   public void setLoadingScheme(LoadingScheme loadingScheme) {
      boolean startNewScheme;
      if (this.loadingScheme != null) {
         if (loadingScheme != null) {
            loadingScheme.copyCacheLoadListeners(this.loadingScheme);
            startNewScheme = loadingScheme.isStarted();
         } else {
            startNewScheme = false;
         }
      } else {
         startNewScheme = this.state == CacheMap.CacheState.Started;
      }

      this.loadingScheme = loadingScheme;
      if (startNewScheme) {
         loadingScheme.start();
      }

   }

   public WritePolicy getWritePolicy() {
      return this.writePolicy;
   }

   public void setWritePolicy(WritePolicy newPolicy) {
      boolean startNewPolicy;
      if (this.writePolicy != null) {
         if (newPolicy != null) {
            newPolicy.copyCacheStoreListeners(this.writePolicy);
            startNewPolicy = this.writePolicy.isStarted();
         } else {
            startNewPolicy = false;
         }

         this.writePolicy.shutdown(true);
      } else {
         startNewPolicy = this.state == CacheMap.CacheState.Started;
      }

      this.writePolicy = newPolicy;
      if (startNewPolicy) {
         newPolicy.start();
      }

   }

   public void setCacheStore(CacheStore store) {
      if (this.writePolicy != null) {
         this.writePolicy.setCacheStore(store);
      }

   }

   public void stop() {
      if (this.writePolicy != null) {
         this.writePolicy.shutdown(true);
      }

      if (this.loadingScheme != null) {
         this.loadingScheme.shutdown(true);
      }

      this.evictionListenerSupport.stop(true);
      this.changeSupport.stop(true);
      this.clear();
      this.state = CacheMap.CacheState.Stopped;
   }

   public void forceStop() {
      if (this.writePolicy != null) {
         this.writePolicy.shutdown(false);
      }

      if (this.loadingScheme != null) {
         this.loadingScheme.shutdown(false);
      }

      this.evictionListenerSupport.stop(false);
      this.changeSupport.stop(false);
      this.clear();
      this.state = CacheMap.CacheState.Stopped;
   }

   public CacheMap.CacheState getState() {
      return this.state;
   }

   public void start() {
      if (this.writePolicy != null) {
         this.writePolicy.start();
      }

      if (this.loadingScheme != null) {
         this.loadingScheme.start();
      }

      this.evictionListenerSupport.start();
      this.changeSupport.start();
      this.state = CacheMap.CacheState.Started;
   }

   public Set keySet() {
      this.removeAllInvalidEntries();
      Set s = super.keySet();
      return new DelegatingSet(s) {
         public boolean remove(Object key) {
            CacheEntry entry = ConcurrentCacheMap.this.getEntry(key);
            boolean returnValue = super.remove(key);

            try {
               ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.DELETE, entry, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var5) {
               BEACacheLogger.logUnableToFireListeners(var5);
            }

            return returnValue;
         }

         public void clear() {
            super.clear();

            try {
               ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.CLEAR, (Object)null, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var2) {
               BEACacheLogger.logUnableToFireListeners(var2);
            }

         }

         public Iterator iterator() {
            Iterator i = this.getDelegate().iterator();
            return new DelegatingIterator(i) {
               public void remove() {
                  CacheEntry entry = ConcurrentCacheMap.this.getEntry(this.current());
                  super.remove();

                  try {
                     ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.DELETE, entry, (Object)null);
                  } catch (ListenerSupport.ListenerSupportException var3) {
                     BEACacheLogger.logUnableToFireListeners(var3);
                  }

               }
            };
         }
      };
   }

   public Collection values() {
      this.removeAllInvalidEntries();
      Collection c = super.values();
      return new DelegatingCollection(c) {
         public void clear() {
            super.clear();

            try {
               ConcurrentCacheMap.this.changeSupport.fireEvent(ChangeEvent.CLEAR, (Object)null, (Object)null);
            } catch (ListenerSupport.ListenerSupportException var2) {
               BEACacheLogger.logUnableToFireListeners(var2);
            }

         }

         public boolean remove(Object value) {
            Iterator i = this.iterator();
            if (value == null) {
               while(i.hasNext()) {
                  if (i.next() == null) {
                     i.remove();
                     return true;
                  }
               }
            } else {
               while(i.hasNext()) {
                  if (value.equals(i.next())) {
                     i.remove();
                     return true;
                  }
               }
            }

            return false;
         }

         public Iterator iterator() {
            Iterator i = ConcurrentCacheMap.this.entrySet().iterator();
            return new MappingIterator(i) {
               protected Object map(Object o) {
                  return ((Map.Entry)o).getValue();
               }
            };
         }
      };
   }

   private static boolean isEntryValid(CacheEntry entry) {
      return System.currentTimeMillis() < entry.getExpirationTime() && !entry.isDiscarded();
   }

   private void removeAllInvalidEntries() {
      Iterator var1 = this.map.values().iterator();

      while(var1.hasNext()) {
         Object e = var1.next();
         CacheEntry entry = (CacheEntry)e;
         if (!isEntryValid(entry)) {
            this.remove(entry.getKey());
         }
      }

   }

   public void addChangeListener(ChangeListener l) {
      if (this.getState() != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         this.changeSupport.add(l);
      }
   }

   public void removeChangeListener(ChangeListener l) {
      if (this.getState() != CacheMap.CacheState.Started) {
         throw new CacheRuntimeException("Cache not started");
      } else {
         this.changeSupport.remove(l);
      }
   }

   public MapStatistics getStatistics() {
      return this.statsSupport;
   }
}
