package org.apache.openjpa.datacache;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;

public abstract class AbstractDataCache extends AbstractConcurrentEventManager implements DataCache, Configurable {
   private static final BitSet EMPTY_BITSET = new BitSet(0);
   private static final Localizer s_loc = Localizer.forPackage(AbstractDataCache.class);
   protected OpenJPAConfiguration conf;
   protected Log log;
   private String _name = null;
   private boolean _closed = false;
   private String _schedule = null;

   public String getName() {
      return this._name;
   }

   public void setName(String name) {
      this._name = name;
   }

   public String getEvictionSchedule() {
      return this._schedule;
   }

   public void setEvictionSchedule(String s) {
      this._schedule = s;
   }

   public void initialize(DataCacheManager manager) {
      if (this._schedule != null && !"".equals(this._schedule)) {
         DataCacheScheduler scheduler = manager.getDataCacheScheduler();
         if (scheduler != null) {
            scheduler.scheduleEviction(this, this._schedule);
         }
      }

   }

   public void commit(Collection additions, Collection newUpdates, Collection existingUpdates, Collection deletes) {
      this.removeAllInternal(deletes);
      this.putAllInternal(additions);
      this.putAllInternal(newUpdates);
      if (this.recacheUpdates()) {
         this.putAllInternal(existingUpdates);
      }

      if (this.log.isTraceEnabled()) {
         Collection addIds = new ArrayList(additions.size());
         Collection upIds = new ArrayList(newUpdates.size());
         Collection exIds = new ArrayList(existingUpdates.size());
         Iterator iter = additions.iterator();

         while(iter.hasNext()) {
            addIds.add(((DataCachePCData)iter.next()).getId());
         }

         iter = newUpdates.iterator();

         while(iter.hasNext()) {
            upIds.add(((DataCachePCData)iter.next()).getId());
         }

         iter = existingUpdates.iterator();

         while(iter.hasNext()) {
            exIds.add(((DataCachePCData)iter.next()).getId());
         }

         this.log.trace(s_loc.get("cache-commit", new Object[]{addIds, upIds, exIds, deletes}));
      }

   }

   public boolean contains(Object key) {
      DataCachePCData o = this.getInternal(key);
      if (o != null && o.isTimedOut()) {
         o = null;
         this.removeInternal(key);
         if (this.log.isTraceEnabled()) {
            this.log.trace(s_loc.get("cache-timeout", key));
         }
      }

      return o != null;
   }

   public BitSet containsAll(Collection keys) {
      if (keys.isEmpty()) {
         return EMPTY_BITSET;
      } else {
         BitSet set = new BitSet(keys.size());
         int i = 0;

         for(Iterator iter = keys.iterator(); iter.hasNext(); ++i) {
            if (this.contains(iter.next())) {
               set.set(i);
            }
         }

         return set;
      }
   }

   public DataCachePCData get(Object key) {
      DataCachePCData o = this.getInternal(key);
      if (o != null && o.isTimedOut()) {
         o = null;
         this.removeInternal(key);
         if (this.log.isTraceEnabled()) {
            this.log.trace(s_loc.get("cache-timeout", key));
         }
      }

      if (this.log.isTraceEnabled()) {
         if (o == null) {
            this.log.trace(s_loc.get("cache-miss", key));
         } else {
            this.log.trace(s_loc.get("cache-hit", key));
         }
      }

      return o;
   }

   public DataCachePCData put(DataCachePCData data) {
      DataCachePCData o = this.putInternal(data.getId(), data);
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("cache-put", data.getId()));
      }

      return o != null && !o.isTimedOut() ? o : null;
   }

   public void update(DataCachePCData data) {
      if (this.recacheUpdates()) {
         this.putInternal(data.getId(), data);
      }

   }

   public DataCachePCData remove(Object key) {
      DataCachePCData o = this.removeInternal(key);
      if (o != null && o.isTimedOut()) {
         o = null;
      }

      if (this.log.isTraceEnabled()) {
         if (o == null) {
            this.log.trace(s_loc.get("cache-remove-miss", key));
         } else {
            this.log.trace(s_loc.get("cache-remove-hit", key));
         }
      }

      return o;
   }

   public BitSet removeAll(Collection keys) {
      if (keys.isEmpty()) {
         return EMPTY_BITSET;
      } else {
         BitSet set = new BitSet(keys.size());
         int i = 0;

         for(Iterator iter = keys.iterator(); iter.hasNext(); ++i) {
            if (this.remove(iter.next()) != null) {
               set.set(i);
            }
         }

         return set;
      }
   }

   public void removeAll(Class cls, boolean subClasses) {
      this.removeAllInternal(cls, subClasses);
   }

   public boolean pin(Object key) {
      boolean bool = this.pinInternal(key);
      if (this.log.isTraceEnabled()) {
         if (bool) {
            this.log.trace(s_loc.get("cache-pin-hit", key));
         } else {
            this.log.trace(s_loc.get("cache-pin-miss", key));
         }
      }

      return bool;
   }

   public BitSet pinAll(Collection keys) {
      if (keys.isEmpty()) {
         return EMPTY_BITSET;
      } else {
         BitSet set = new BitSet(keys.size());
         int i = 0;

         for(Iterator iter = keys.iterator(); iter.hasNext(); ++i) {
            if (this.pin(iter.next())) {
               set.set(i);
            }
         }

         return set;
      }
   }

   public void pinAll(Class cls, boolean subs) {
      if (this.log.isWarnEnabled()) {
         this.log.warn(s_loc.get("cache-class-pin", (Object)this.getName()));
      }

   }

   public boolean unpin(Object key) {
      boolean bool = this.unpinInternal(key);
      if (this.log.isTraceEnabled()) {
         if (bool) {
            this.log.trace(s_loc.get("cache-unpin-hit", key));
         } else {
            this.log.trace(s_loc.get("cache-unpin-miss", key));
         }
      }

      return bool;
   }

   public BitSet unpinAll(Collection keys) {
      if (keys.isEmpty()) {
         return EMPTY_BITSET;
      } else {
         BitSet set = new BitSet(keys.size());
         int i = 0;

         for(Iterator iter = keys.iterator(); iter.hasNext(); ++i) {
            if (this.unpin(iter.next())) {
               set.set(i);
            }
         }

         return set;
      }
   }

   public void unpinAll(Class cls, boolean subs) {
      if (this.log.isWarnEnabled()) {
         this.log.warn(s_loc.get("cache-class-unpin", (Object)this.getName()));
      }

   }

   public void clear() {
      this.clearInternal();
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("cache-clear", (Object)this.getName()));
      }

   }

   public void close() {
      this.close(true);
   }

   protected void close(boolean clear) {
      if (!this._closed) {
         if (clear) {
            this.clearInternal();
         }

         this._closed = true;
      }

   }

   public boolean isClosed() {
      return this._closed;
   }

   public void addExpirationListener(ExpirationListener listen) {
      this.addListener(listen);
   }

   public boolean removeExpirationListener(ExpirationListener listen) {
      return this.removeListener(listen);
   }

   public String toString() {
      return "[" + super.toString() + ":" + this._name + "]";
   }

   public void afterCommit(RemoteCommitEvent event) {
      if (!this._closed) {
         if (event.getPayloadType() == 2) {
            this.removeAllTypeNamesInternal(event.getUpdatedTypeNames());
            this.removeAllTypeNamesInternal(event.getDeletedTypeNames());
         } else {
            this.removeAllInternal(event.getUpdatedObjectIds());
            this.removeAllInternal(event.getDeletedObjectIds());
         }

      }
   }

   protected void keyRemoved(Object key, boolean expired) {
      if (this.hasListeners()) {
         this.fireEvent(new ExpirationEvent(this, key, expired));
      }

      if (expired && this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("cache-expired", key));
      }

   }

   protected boolean recacheUpdates() {
      return false;
   }

   protected abstract DataCachePCData getInternal(Object var1);

   protected abstract DataCachePCData putInternal(Object var1, DataCachePCData var2);

   protected void putAllInternal(Collection pcs) {
      Iterator iter = pcs.iterator();

      while(iter.hasNext()) {
         DataCachePCData pc = (DataCachePCData)iter.next();
         this.putInternal(pc.getId(), pc);
      }

   }

   protected abstract DataCachePCData removeInternal(Object var1);

   protected abstract void removeAllInternal(Class var1, boolean var2);

   protected void removeAllInternal(Collection oids) {
      Iterator iter = oids.iterator();

      while(iter.hasNext()) {
         this.removeInternal(iter.next());
      }

   }

   protected void removeAllTypeNamesInternal(Collection classNames) {
      Collection classes = Caches.addTypesByName(this.conf, classNames, (Set)null);
      if (classes != null) {
         Class cls;
         for(Iterator iter = classes.iterator(); iter.hasNext(); this.removeAllInternal(cls, false)) {
            cls = (Class)iter.next();
            if (this.log.isTraceEnabled()) {
               this.log.trace(s_loc.get("cache-removeclass", (Object)cls.getName()));
            }
         }

      }
   }

   protected abstract void clearInternal();

   protected abstract boolean pinInternal(Object var1);

   protected abstract boolean unpinInternal(Object var1);

   public void setConfiguration(Configuration conf) {
      this.conf = (OpenJPAConfiguration)conf;
      this.log = conf.getLog("openjpa.DataCache");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      if (this._name == null) {
         this.setName("default");
      }

   }

   protected void fireEvent(Object event, Object listener) {
      ExpirationListener listen = (ExpirationListener)listener;
      ExpirationEvent ev = (ExpirationEvent)event;

      try {
         listen.onExpire(ev);
      } catch (Exception var6) {
         if (this.log.isWarnEnabled()) {
            this.log.warn(s_loc.get("exp-listener-ex"), var6);
         }
      }

   }

   public Map getAll(List keys) {
      Map resultMap = new HashMap(keys.size());

      for(int i = 0; i < keys.size(); ++i) {
         resultMap.put(keys.get(i), this.get(keys.get(i)));
      }

      return resultMap;
   }
}
