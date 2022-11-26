package org.apache.openjpa.datacache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.Id;

public abstract class AbstractQueryCache extends AbstractConcurrentEventManager implements QueryCache, Configurable {
   private static final Localizer s_loc = Localizer.forPackage(AbstractQueryCache.class);
   protected OpenJPAConfiguration conf;
   protected Log log;
   private boolean _closed = false;

   public void initialize(DataCacheManager manager) {
   }

   public void onTypesChanged(TypesChangedEvent ev) {
      this.writeLock();
      Collection keys = null;

      try {
         if (this.hasListeners()) {
            this.fireEvent(ev);
         }

         keys = this.keySet();
      } finally {
         this.writeUnlock();
      }

      ArrayList removes = null;
      Iterator iter = keys.iterator();

      while(iter.hasNext()) {
         QueryKey qk = (QueryKey)iter.next();
         if (qk.changeInvalidatesQuery(ev.getTypes())) {
            if (removes == null) {
               removes = new ArrayList();
            }

            removes.add(qk);
         }
      }

      if (removes != null) {
         this.removeAllInternal(removes);
      }

   }

   public QueryResult get(QueryKey key) {
      QueryResult o = this.getInternal(key);
      if (o != null && o.isTimedOut()) {
         o = null;
         this.removeInternal(key);
         if (this.log.isTraceEnabled()) {
            this.log.trace(s_loc.get("cache-timeout", (Object)key));
         }
      }

      if (this.log.isTraceEnabled()) {
         if (o == null) {
            this.log.trace(s_loc.get("cache-miss", (Object)key));
         } else {
            this.log.trace(s_loc.get("cache-hit", (Object)key));
         }
      }

      return o;
   }

   public QueryResult put(QueryKey qk, QueryResult oids) {
      QueryResult o = this.putInternal(qk, oids);
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("cache-put", (Object)qk));
      }

      return o != null && !o.isTimedOut() ? o : null;
   }

   public QueryResult remove(QueryKey key) {
      QueryResult o = this.removeInternal(key);
      if (o != null && o.isTimedOut()) {
         o = null;
      }

      if (this.log.isTraceEnabled()) {
         if (o == null) {
            this.log.trace(s_loc.get("cache-remove-miss", (Object)key));
         } else {
            this.log.trace(s_loc.get("cache-remove-hit", (Object)key));
         }
      }

      return o;
   }

   public boolean pin(QueryKey key) {
      boolean bool = this.pinInternal(key);
      if (this.log.isTraceEnabled()) {
         if (bool) {
            this.log.trace(s_loc.get("cache-pin-hit", (Object)key));
         } else {
            this.log.trace(s_loc.get("cache-pin-miss", (Object)key));
         }
      }

      return bool;
   }

   public boolean unpin(QueryKey key) {
      boolean bool = this.unpinInternal(key);
      if (this.log.isTraceEnabled()) {
         if (bool) {
            this.log.trace(s_loc.get("cache-unpin-hit", (Object)key));
         } else {
            this.log.trace(s_loc.get("cache-unpin-miss", (Object)key));
         }
      }

      return bool;
   }

   public void clear() {
      this.clearInternal();
      if (this.log.isTraceEnabled()) {
         this.log.trace(s_loc.get("cache-clear", (Object)"<query-cache>"));
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

   public void addTypesChangedListener(TypesChangedListener listen) {
      this.addListener(listen);
   }

   public boolean removeTypesChangedListener(TypesChangedListener listen) {
      return this.removeListener(listen);
   }

   public void afterCommit(RemoteCommitEvent event) {
      if (!this._closed) {
         Set classes = Caches.addTypesByName(this.conf, event.getPersistedTypeNames(), (Set)null);
         if (event.getPayloadType() == 2) {
            classes = Caches.addTypesByName(this.conf, event.getUpdatedTypeNames(), classes);
            classes = Caches.addTypesByName(this.conf, event.getDeletedTypeNames(), classes);
         } else {
            classes = this.addTypes(event.getUpdatedObjectIds(), classes);
            classes = this.addTypes(event.getDeletedObjectIds(), classes);
         }

         if (classes != null) {
            this.onTypesChanged(new TypesChangedEvent(this, classes));
         }

      }
   }

   private Set addTypes(Collection oids, Set classes) {
      if (oids.isEmpty()) {
         return (Set)classes;
      } else {
         if (classes == null) {
            classes = new HashSet();
         }

         MetaDataRepository repos = this.conf.getMetaDataRepositoryInstance();
         Iterator itr = oids.iterator();

         while(itr.hasNext()) {
            Object oid = itr.next();
            if (oid instanceof Id) {
               ((Set)classes).add(((Id)oid).getType());
            } else {
               ClassMetaData meta = repos.getMetaData((Object)oid, (ClassLoader)null, false);
               if (meta != null) {
                  ((Set)classes).add(meta.getDescribedType());
               }
            }
         }

         return (Set)classes;
      }
   }

   protected abstract Collection keySet();

   protected abstract QueryResult getInternal(QueryKey var1);

   protected abstract QueryResult putInternal(QueryKey var1, QueryResult var2);

   protected abstract QueryResult removeInternal(QueryKey var1);

   protected void removeAllInternal(Collection qks) {
      Iterator iter = qks.iterator();

      while(iter.hasNext()) {
         this.removeInternal((QueryKey)iter.next());
      }

   }

   protected abstract void clearInternal();

   protected abstract boolean pinInternal(QueryKey var1);

   protected abstract boolean unpinInternal(QueryKey var1);

   public void setConfiguration(Configuration conf) {
      this.conf = (OpenJPAConfiguration)conf;
      this.log = conf.getLog("openjpa.DataCache");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   protected void fireEvent(Object event, Object listener) {
      TypesChangedListener listen = (TypesChangedListener)listener;
      TypesChangedEvent ev = (TypesChangedEvent)event;

      try {
         listen.onTypesChanged(ev);
      } catch (Exception var6) {
         if (this.log.isWarnEnabled()) {
            this.log.warn(s_loc.get("exp-listener-ex"), var6);
         }
      }

   }

   protected Collection newListenerCollection() {
      return new ConcurrentReferenceHashSet(2);
   }
}
