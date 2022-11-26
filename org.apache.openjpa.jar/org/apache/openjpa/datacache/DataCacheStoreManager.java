package org.apache.openjpa.datacache;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.enhance.PCDataGenerator;
import org.apache.openjpa.kernel.DelegatingStoreManager;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.QueryLanguages;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.OptimisticException;

public class DataCacheStoreManager extends DelegatingStoreManager {
   private Collection _inserts = null;
   private Map _updates = null;
   private Collection _deletes = null;
   private StoreContext _ctx = null;
   private PCDataGenerator _gen = null;

   public DataCacheStoreManager(StoreManager sm) {
      super(sm);
   }

   public void setContext(StoreContext ctx) {
      this._ctx = ctx;
      this._gen = ctx.getConfiguration().getDataCacheManagerInstance().getPCDataGenerator();
      super.setContext(ctx);
   }

   public void begin() {
      super.begin();
   }

   public void commit() {
      try {
         super.commit();
         this.updateCaches();
      } finally {
         this._inserts = null;
         this._updates = null;
         this._deletes = null;
      }

   }

   public void rollback() {
      try {
         super.rollback();
      } finally {
         this._inserts = null;
         this._updates = null;
         this._deletes = null;
      }

   }

   private void evictTypes(Collection classes) {
      if (!classes.isEmpty()) {
         MetaDataRepository mdr = this._ctx.getConfiguration().getMetaDataRepositoryInstance();
         ClassLoader loader = this._ctx.getClassLoader();
         Iterator itr = classes.iterator();

         while(itr.hasNext()) {
            Class cls = (Class)itr.next();
            DataCache cache = mdr.getMetaData(cls, loader, false).getDataCache();
            if (cache != null) {
               cache.removeAll(cls, false);
            }
         }

      }
   }

   private void updateCaches() {
      Map modMap = null;
      Modifications mods;
      OpenJPAStateManager sm;
      DataCachePCData data;
      DataCache cache;
      if (this._ctx.getPopulateDataCache() && this._inserts != null) {
         Iterator itr = this._inserts.iterator();

         while(itr.hasNext()) {
            sm = (OpenJPAStateManager)itr.next();
            cache = sm.getMetaData().getDataCache();
            if (cache != null) {
               if (modMap == null) {
                  modMap = new HashMap();
               }

               mods = getModifications(modMap, cache);
               data = this.newPCData(sm);
               data.store(sm);
               mods.additions.add(new PCDataHolder(data, sm));
            }
         }
      }

      Map.Entry entry;
      if (this._updates != null) {
         Iterator itr = this._updates.entrySet().iterator();

         while(itr.hasNext()) {
            entry = (Map.Entry)itr.next();
            sm = (OpenJPAStateManager)entry.getKey();
            BitSet fields = (BitSet)entry.getValue();
            cache = sm.getMetaData().getDataCache();
            if (cache != null) {
               if (modMap == null) {
                  modMap = new HashMap();
               }

               data = cache.get(sm.getObjectId());
               mods = getModifications(modMap, cache);
               if (data == null) {
                  data = this.newPCData(sm);
                  data.store(sm);
                  mods.newUpdates.add(new PCDataHolder(data, sm));
               } else {
                  data.store(sm, fields);
                  mods.existingUpdates.add(new PCDataHolder(data, sm));
               }
            }
         }
      }

      Iterator itr;
      if (this._deletes != null) {
         itr = this._deletes.iterator();

         while(itr.hasNext()) {
            sm = (OpenJPAStateManager)itr.next();
            cache = sm.getMetaData().getDataCache();
            if (cache != null) {
               if (modMap == null) {
                  modMap = new HashMap();
               }

               mods = getModifications(modMap, cache);
               mods.deletes.add(sm.getObjectId());
            }
         }
      }

      if (modMap != null) {
         itr = modMap.entrySet().iterator();

         while(itr.hasNext()) {
            entry = (Map.Entry)itr.next();
            cache = (DataCache)entry.getKey();
            mods = (Modifications)entry.getValue();
            cache.writeLock();

            try {
               this.transformToVersionSafePCDatas(cache, mods.additions);
               this.transformToVersionSafePCDatas(cache, mods.newUpdates);
               this.transformToVersionSafePCDatas(cache, mods.existingUpdates);
               cache.commit(mods.additions, mods.newUpdates, mods.existingUpdates, mods.deletes);
            } finally {
               cache.writeUnlock();
            }
         }
      }

      if (this._ctx.isTrackChangesByType()) {
         this.evictTypes(this._ctx.getDeletedTypes());
         this.evictTypes(this._ctx.getUpdatedTypes());
      }

      QueryCache queryCache = this._ctx.getConfiguration().getDataCacheManagerInstance().getSystemQueryCache();
      if (queryCache != null) {
         Collection pers = this._ctx.getPersistedTypes();
         Collection del = this._ctx.getDeletedTypes();
         Collection up = this._ctx.getUpdatedTypes();
         int size = pers.size() + del.size() + up.size();
         if (size > 0) {
            Collection types = new ArrayList(size);
            types.addAll(pers);
            types.addAll(del);
            types.addAll(up);
            queryCache.onTypesChanged(new TypesChangedEvent(this, types));
         }
      }

   }

   private void transformToVersionSafePCDatas(DataCache cache, List holders) {
      Map ids = new HashMap(holders.size());
      List idList = new ArrayList(holders.size());
      int i = 0;
      Iterator i$ = holders.iterator();

      while(i$.hasNext()) {
         PCDataHolder holder = (PCDataHolder)i$.next();
         ids.put(holder.sm.getObjectId(), i++);
         idList.add(holder.sm.getObjectId());
      }

      Map pcdatas = cache.getAll(idList);
      Iterator i$ = pcdatas.entrySet().iterator();

      while(true) {
         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            Integer index = (Integer)ids.get(entry.getKey());
            DataCachePCData oldpc = (DataCachePCData)entry.getValue();
            PCDataHolder holder = (PCDataHolder)holders.get(index);
            if (oldpc != null && this.compareVersion(holder.sm, holder.sm.getVersion(), oldpc.getVersion()) == 2) {
               holders.remove(index);
            } else {
               holders.set(index, holder.pcdata);
            }
         }

         return;
      }
   }

   private static Modifications getModifications(Map modMap, DataCache cache) {
      Modifications mods = (Modifications)modMap.get(cache);
      if (mods == null) {
         mods = new Modifications();
         modMap.put(cache, mods);
      }

      return mods;
   }

   public boolean exists(OpenJPAStateManager sm, Object edata) {
      DataCache cache = sm.getMetaData().getDataCache();
      return cache != null && !this.isLocking((FetchConfiguration)null) && cache.contains(sm.getObjectId()) ? true : super.exists(sm, edata);
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object edata) {
      DataCache cache = sm.getMetaData().getDataCache();
      if (cache != null && !sm.isEmbedded()) {
         Object version = null;
         DataCachePCData data = cache.get(sm.getObjectId());
         if (!this.isLocking((FetchConfiguration)null) && data != null) {
            version = data.getVersion();
         }

         if (version != null) {
            if (!version.equals(sm.getVersion())) {
               sm.setVersion(version);
               return false;
            } else {
               return true;
            }
         } else {
            return super.syncVersion(sm, edata);
         }
      } else {
         return super.syncVersion(sm, edata);
      }
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object edata) {
      DataCache cache = sm.getMetaData().getDataCache();
      if (cache != null && !sm.isEmbedded()) {
         DataCachePCData data = cache.get(sm.getObjectId());
         if (data != null && !this.isLocking(fetch)) {
            sm.initialize(data.getType(), state);
            data.load(sm, fetch, edata);
            return true;
         } else if (!super.initialize(sm, state, fetch, edata)) {
            return false;
         } else if (!this._ctx.getPopulateDataCache()) {
            return true;
         } else {
            cache.writeLock();

            try {
               data = cache.get(sm.getObjectId());
               if (data != null && this.compareVersion(sm, sm.getVersion(), data.getVersion()) == 2) {
                  boolean var7 = true;
                  return var7;
               }

               if (data == null) {
                  data = this.newPCData(sm);
               }

               data.store(sm);
               cache.put(data);
            } finally {
               cache.writeUnlock();
            }

            return true;
         }
      } else {
         return super.initialize(sm, state, fetch, edata);
      }
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object edata) {
      DataCache cache = sm.getMetaData().getDataCache();
      if (cache != null && !sm.isEmbedded()) {
         DataCachePCData data = cache.get(sm.getObjectId());
         if (lockLevel == 0 && !this.isLocking(fetch) && data != null) {
            data.load(sm, fields, fetch, edata);
         }

         if (fields.length() == 0) {
            return true;
         } else if (!super.load(sm, (BitSet)fields.clone(), fetch, lockLevel, edata)) {
            return false;
         } else if (!this._ctx.getPopulateDataCache()) {
            return true;
         } else if (sm.isFlushed()) {
            return true;
         } else {
            cache.writeLock();

            try {
               data = cache.get(sm.getObjectId());
               boolean isNew;
               if (data != null && this.compareVersion(sm, sm.getVersion(), data.getVersion()) == 2) {
                  isNew = true;
                  return isNew;
               }

               isNew = data == null;
               if (isNew) {
                  data = this.newPCData(sm);
               }

               data.store(sm, fields);
               if (isNew) {
                  cache.put(data);
               } else {
                  cache.update(data);
               }
            } finally {
               cache.writeUnlock();
            }

            return true;
         }
      } else {
         return super.load(sm, fields, fetch, lockLevel, edata);
      }
   }

   protected void addObjectIdToOIDList(List oidList, OpenJPAStateManager sm) {
      oidList.add(sm.getObjectId());
   }

   protected DataCachePCData getDataFromDataMap(Map dataMap, OpenJPAStateManager sm) {
      return (DataCachePCData)dataMap.get(sm.getObjectId());
   }

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object edata) {
      if (this.isLocking(fetch)) {
         return super.loadAll(sms, state, load, fetch, edata);
      } else {
         Map unloaded = null;
         List smList = null;
         Map caches = new HashMap();
         Iterator itr = sms.iterator();

         while(true) {
            while(true) {
               OpenJPAStateManager sm;
               DataCache cache;
               while(itr.hasNext()) {
                  sm = (OpenJPAStateManager)itr.next();
                  cache = sm.getMetaData().getDataCache();
                  if (cache != null && !sm.isEmbedded()) {
                     if (sm.getManagedInstance() != null && load == 0 && sm.getPCState() != PCState.HOLLOW) {
                        if (!cache.contains(sm.getObjectId())) {
                           unloaded = addUnloaded(sm, (BitSet)null, unloaded);
                        }
                     } else {
                        smList = (List)caches.get(cache);
                        if (smList == null) {
                           smList = new ArrayList();
                           caches.put(cache, smList);
                        }

                        ((List)smList).add(sm);
                     }
                  } else {
                     unloaded = addUnloaded(sm, (BitSet)null, unloaded);
                  }
               }

               itr = caches.keySet().iterator();

               DataCachePCData data;
               BitSet fields;
               label299:
               while(itr.hasNext()) {
                  cache = (DataCache)itr.next();
                  List smList = (List)caches.get(cache);
                  List oidList = new ArrayList(smList.size());
                  itr = smList.iterator();

                  while(itr.hasNext()) {
                     sm = (OpenJPAStateManager)itr.next();
                     this.addObjectIdToOIDList(oidList, sm);
                  }

                  Map dataMap = cache.getAll(oidList);
                  itr = smList.iterator();

                  while(true) {
                     while(true) {
                        if (!itr.hasNext()) {
                           continue label299;
                        }

                        sm = (OpenJPAStateManager)itr.next();
                        data = this.getDataFromDataMap(dataMap, sm);
                        if (sm.getManagedInstance() == null) {
                           if (data != null) {
                              sm.initialize(data.getType(), state);
                              data.load(sm, fetch, edata);
                           } else {
                              unloaded = addUnloaded(sm, (BitSet)null, unloaded);
                           }
                        } else if (load != 0 || sm.getPCState() == PCState.HOLLOW) {
                           data = cache.get(sm.getObjectId());
                           if (data != null) {
                              fields = sm.getUnloaded(fetch);
                              data.load(sm, fields, fetch, edata);
                              if (fields.length() > 0) {
                                 unloaded = addUnloaded(sm, fields, unloaded);
                              }
                           } else {
                              unloaded = addUnloaded(sm, (BitSet)null, unloaded);
                           }
                        }
                     }
                  }
               }

               if (unloaded == null) {
                  return Collections.EMPTY_LIST;
               }

               Collection failed = super.loadAll(unloaded.keySet(), state, load, fetch, edata);
               if (!this._ctx.getPopulateDataCache()) {
                  return failed;
               }

               Iterator itr = unloaded.entrySet().iterator();

               while(true) {
                  do {
                     do {
                        do {
                           if (!itr.hasNext()) {
                              return failed;
                           }

                           Map.Entry entry = (Map.Entry)itr.next();
                           sm = (OpenJPAStateManager)entry.getKey();
                           fields = (BitSet)entry.getValue();
                           cache = sm.getMetaData().getDataCache();
                        } while(cache == null);
                     } while(sm.isEmbedded());
                  } while(failed != null && failed.contains(sm.getId()));

                  cache.writeLock();

                  try {
                     data = cache.get(sm.getObjectId());
                     if (data == null || this.compareVersion(sm, sm.getVersion(), data.getVersion()) != 2) {
                        boolean isNew = data == null;
                        if (isNew) {
                           data = this.newPCData(sm);
                        }

                        if (fields == null) {
                           data.store(sm);
                        } else {
                           data.store(sm, fields);
                        }

                        if (isNew) {
                           cache.put(data);
                        } else {
                           cache.update(data);
                        }
                     }
                  } finally {
                     cache.writeUnlock();
                  }
               }
            }
         }
      }
   }

   private static Map addUnloaded(OpenJPAStateManager sm, BitSet fields, Map unloaded) {
      if (unloaded == null) {
         unloaded = new HashMap();
      }

      ((Map)unloaded).put(sm, fields);
      return (Map)unloaded;
   }

   public Collection flush(Collection states) {
      Collection exceps = super.flush(states);
      if (!exceps.isEmpty()) {
         Iterator iter = exceps.iterator();

         while(iter.hasNext()) {
            Exception e = (Exception)iter.next();
            if (e instanceof OptimisticException) {
               this.notifyOptimisticLockFailure((OptimisticException)e);
            }
         }

         return exceps;
      } else if (this._ctx.isTrackChangesByType()) {
         return exceps;
      } else {
         Iterator itr = states.iterator();

         while(true) {
            while(itr.hasNext()) {
               OpenJPAStateManager sm = (OpenJPAStateManager)itr.next();
               if (sm.getPCState() == PCState.PNEW && !sm.isFlushed()) {
                  if (this._inserts == null) {
                     this._inserts = new ArrayList();
                  }

                  this._inserts.add(sm);
                  if (this._deletes != null) {
                     this._deletes.remove(sm);
                  }
               } else if (this._inserts != null && (sm.getPCState() == PCState.PNEWDELETED || sm.getPCState() == PCState.PNEWFLUSHEDDELETED)) {
                  this._inserts.remove(sm);
               } else if (sm.getPCState() == PCState.PDIRTY) {
                  if (this._updates == null) {
                     this._updates = new HashMap();
                  }

                  this._updates.put(sm, sm.getDirty());
               } else if (sm.getPCState() == PCState.PDELETED) {
                  if (this._deletes == null) {
                     this._deletes = new HashSet();
                  }

                  this._deletes.add(sm);
               }
            }

            return Collections.EMPTY_LIST;
         }
      }
   }

   private void notifyOptimisticLockFailure(OptimisticException e) {
      Object o = e.getFailedObject();
      OpenJPAStateManager sm = this._ctx.getStateManager(o);
      if (sm != null) {
         Object oid = sm.getId();
         ClassMetaData meta = sm.getMetaData();
         DataCache cache = meta.getDataCache();
         if (cache != null) {
            cache.writeLock();

            try {
               DataCachePCData data = cache.get(oid);
               if (data == null) {
                  return;
               }

               boolean remove;
               switch (this.compareVersion(sm, sm.getVersion(), data.getVersion())) {
                  case 1:
                  case 3:
                     remove = true;
                     break;
                  case 2:
                     remove = false;
                     break;
                  case 4:
                     remove = true;
                     break;
                  default:
                     remove = true;
               }

               if (remove) {
                  cache.remove(sm.getId());
               }
            } finally {
               cache.writeUnlock();
            }

            this._ctx.getConfiguration().getRemoteCommitEventManager().fireLocalStaleNotification(oid);
         }
      }
   }

   public StoreQuery newQuery(String language) {
      StoreQuery q = super.newQuery(language);
      if (q != null && QueryLanguages.parserForLanguage(language) != null) {
         QueryCache queryCache = this._ctx.getConfiguration().getDataCacheManagerInstance().getSystemQueryCache();
         return (StoreQuery)(queryCache == null ? q : new QueryCacheStoreQuery(q, queryCache));
      } else {
         return q;
      }
   }

   private DataCachePCData newPCData(OpenJPAStateManager sm) {
      ClassMetaData meta = sm.getMetaData();
      return (DataCachePCData)(this._gen != null ? (DataCachePCData)this._gen.generatePCData(sm.getObjectId(), meta) : new DataCachePCDataImpl(sm.fetchObjectId(), meta));
   }

   private boolean isLocking(FetchConfiguration fetch) {
      if (fetch == null) {
         fetch = this._ctx.getFetchConfiguration();
      }

      return fetch.getReadLockLevel() > 0;
   }

   private static class PCDataHolder {
      public final DataCachePCData pcdata;
      public final OpenJPAStateManager sm;

      public PCDataHolder(DataCachePCData pcdata, OpenJPAStateManager sm) {
         this.pcdata = pcdata;
         this.sm = sm;
      }
   }

   private static class Modifications {
      public final List additions;
      public final List newUpdates;
      public final List existingUpdates;
      public final List deletes;

      private Modifications() {
         this.additions = new ArrayList();
         this.newUpdates = new ArrayList();
         this.existingUpdates = new ArrayList();
         this.deletes = new ArrayList();
      }

      // $FF: synthetic method
      Modifications(Object x0) {
         this();
      }
   }
}
