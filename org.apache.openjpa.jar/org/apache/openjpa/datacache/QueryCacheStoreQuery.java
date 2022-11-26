package org.apache.openjpa.datacache;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ObjectNotFoundException;
import serp.util.Numbers;

public class QueryCacheStoreQuery implements StoreQuery {
   private final StoreQuery _query;
   private final QueryCache _cache;
   private StoreContext _sctx;
   private MetaDataRepository _repos;

   public QueryCacheStoreQuery(StoreQuery query, QueryCache cache) {
      this._query = query;
      this._cache = cache;
   }

   public QueryCache getCache() {
      return this._cache;
   }

   public StoreQuery getDelegate() {
      return this._query;
   }

   private List checkCache(QueryKey qk) {
      if (qk == null) {
         return null;
      } else {
         FetchConfiguration fetch = this.getContext().getFetchConfiguration();
         if (!fetch.getQueryCacheEnabled()) {
            return null;
         } else if (fetch.getReadLockLevel() > 0) {
            return null;
         } else {
            QueryResult res = this._cache.get(qk);
            if (res == null) {
               return null;
            } else if (res.isEmpty()) {
               return Collections.EMPTY_LIST;
            } else {
               int projs = this.getContext().getProjectionAliases().length;
               if (projs == 0) {
                  ClassMetaData meta = this._repos.getMetaData(this.getContext().getCandidateType(), this._sctx.getClassLoader(), true);
                  if (meta.getDataCache() == null) {
                     return null;
                  }

                  BitSet idxs = meta.getDataCache().containsAll(res);
                  int len = idxs.length();
                  if (len < res.size()) {
                     return null;
                  }

                  for(int i = 0; i < len; ++i) {
                     if (!idxs.get(i)) {
                        return null;
                     }
                  }
               }

               return new CachedList(res, projs != 0, this._sctx);
            }
         }
      }
   }

   private ResultObjectProvider wrapResult(ResultObjectProvider rop, QueryKey key) {
      return (ResultObjectProvider)(key == null ? rop : new CachingResultObjectProvider(rop, this.getContext().getProjectionAliases().length > 0, key));
   }

   private static Object copyProjection(Object var0, StoreContext var1) {
      // $FF: Couldn't be decompiled
   }

   private static Object fromObjectId(Object oid, StoreContext sctx) {
      if (oid == null) {
         return null;
      } else {
         Object obj = sctx.find(oid, (FetchConfiguration)null, (BitSet)null, (Object)null, 0);
         if (obj == null) {
            throw new ObjectNotFoundException(oid);
         } else {
            return obj;
         }
      }
   }

   public Object writeReplace() throws ObjectStreamException {
      return this._query;
   }

   public QueryContext getContext() {
      return this._query.getContext();
   }

   public void setContext(QueryContext qctx) {
      this._query.setContext(qctx);
      this._sctx = qctx.getStoreContext();
      this._repos = this._sctx.getConfiguration().getMetaDataRepositoryInstance();
   }

   public boolean setQuery(Object query) {
      return this._query.setQuery(query);
   }

   public FilterListener getFilterListener(String tag) {
      return this._query.getFilterListener(tag);
   }

   public AggregateListener getAggregateListener(String tag) {
      return this._query.getAggregateListener(tag);
   }

   public Object newCompilationKey() {
      return this._query.newCompilationKey();
   }

   public Object newCompilation() {
      return this._query.newCompilation();
   }

   public void populateFromCompilation(Object comp) {
      this._query.populateFromCompilation(comp);
   }

   public void invalidateCompilation() {
      this._query.invalidateCompilation();
   }

   public boolean supportsDataStoreExecution() {
      return this._query.supportsDataStoreExecution();
   }

   public boolean supportsInMemoryExecution() {
      return this._query.supportsInMemoryExecution();
   }

   public StoreQuery.Executor newInMemoryExecutor(ClassMetaData meta, boolean subs) {
      return this._query.newInMemoryExecutor(meta, subs);
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subs) {
      StoreQuery.Executor ex = this._query.newDataStoreExecutor(meta, subs);
      return new QueryCacheExecutor(ex, meta, subs, this.getContext().getFetchConfiguration());
   }

   public boolean supportsAbstractExecutors() {
      return this._query.supportsAbstractExecutors();
   }

   public boolean requiresCandidateType() {
      return this._query.requiresCandidateType();
   }

   public boolean requiresParameterDeclarations() {
      return this._query.requiresParameterDeclarations();
   }

   public boolean supportsParameterDeclarations() {
      return this._query.supportsParameterDeclarations();
   }

   private static class CachedObjectId {
      public final Object oid;

      public CachedObjectId(Object oid) {
         this.oid = oid;
      }
   }

   private class CachingResultObjectProvider implements ResultObjectProvider, TypesChangedListener {
      private final ResultObjectProvider _rop;
      private final boolean _proj;
      private final QueryKey _qk;
      private final TreeMap _data = new TreeMap();
      private boolean _maintainCache = true;
      private int _pos = -1;
      private int _max = -1;
      private int _size = Integer.MAX_VALUE;

      public CachingResultObjectProvider(ResultObjectProvider rop, boolean proj, QueryKey key) {
         this._rop = rop;
         this._proj = proj;
         this._qk = key;
         QueryCacheStoreQuery.this._cache.addTypesChangedListener(this);
      }

      private void abortCaching() {
         if (this._maintainCache) {
            synchronized(this) {
               this._maintainCache = false;
               QueryCacheStoreQuery.this._cache.removeTypesChangedListener(this);
               this._data.clear();
            }
         }
      }

      private void checkFinished(Object obj, boolean result) {
         boolean finished = false;
         synchronized(this) {
            if (this._maintainCache) {
               if (result) {
                  Integer index = Numbers.valueOf(this._pos);
                  if (!this._data.containsKey(index)) {
                     Object cached;
                     if (obj == null) {
                        cached = null;
                     } else if (!this._proj) {
                        cached = QueryCacheStoreQuery.this._sctx.getObjectId(obj);
                     } else {
                        Object[] arr = (Object[])((Object[])obj);
                        Object[] cp = new Object[arr.length];

                        for(int i = 0; i < arr.length; ++i) {
                           cp[i] = QueryCacheStoreQuery.copyProjection(arr[i], QueryCacheStoreQuery.this._sctx);
                        }

                        cached = cp;
                     }

                     if (cached != null) {
                        this._data.put(index, cached);
                     }
                  }
               }

               finished = this._size == this._data.size();
            }
         }

         if (finished) {
            QueryCacheStoreQuery.this._cache.writeLock();

            try {
               if (this._maintainCache) {
                  QueryResult res = new QueryResult(this._qk, this._data.values());
                  QueryCacheStoreQuery.this._cache.put(this._qk, res);
                  this.abortCaching();
               }
            } finally {
               QueryCacheStoreQuery.this._cache.writeUnlock();
            }
         }

      }

      public boolean supportsRandomAccess() {
         return this._rop.supportsRandomAccess();
      }

      public void open() throws Exception {
         this._rop.open();
      }

      public Object getResultObject() throws Exception {
         Object obj = this._rop.getResultObject();
         this.checkFinished(obj, true);
         return obj;
      }

      public boolean next() throws Exception {
         ++this._pos;
         boolean next = this._rop.next();
         if (!next && this._pos == this._max + 1) {
            this._size = this._pos;
            this.checkFinished((Object)null, false);
         } else if (next && this._pos > this._max) {
            this._max = this._pos;
         }

         return next;
      }

      public boolean absolute(int pos) throws Exception {
         this._pos = pos;
         boolean valid = this._rop.absolute(pos);
         if (!valid && this._pos == this._max + 1) {
            this._size = this._pos;
            this.checkFinished((Object)null, false);
         } else if (valid && this._pos > this._max) {
            this._max = this._pos;
         }

         return valid;
      }

      public int size() throws Exception {
         if (this._size != Integer.MAX_VALUE) {
            return this._size;
         } else {
            int size = this._rop.size();
            this._size = size;
            this.checkFinished((Object)null, false);
            return size;
         }
      }

      public void reset() throws Exception {
         this._rop.reset();
         this._pos = -1;
      }

      public void close() throws Exception {
         this.abortCaching();
         this._rop.close();
      }

      public void handleCheckedException(Exception e) {
         this._rop.handleCheckedException(e);
      }

      public void onTypesChanged(TypesChangedEvent ev) {
         if (this._qk.changeInvalidatesQuery(ev.getTypes())) {
            this.abortCaching();
         }

      }
   }

   public static class CachedList extends AbstractList implements Serializable {
      private final QueryResult _res;
      private final boolean _proj;
      private final StoreContext _sctx;

      public CachedList(QueryResult res, boolean proj, StoreContext ctx) {
         this._res = res;
         this._proj = proj;
         this._sctx = ctx;
      }

      public Object get(int idx) {
         if (!this._proj) {
            return QueryCacheStoreQuery.fromObjectId(this._res.get(idx), this._sctx);
         } else {
            Object[] cached = (Object[])((Object[])this._res.get(idx));
            if (cached == null) {
               return null;
            } else {
               Object[] uncached = new Object[cached.length];

               for(int i = 0; i < cached.length; ++i) {
                  uncached[i] = QueryCacheStoreQuery.copyProjection(cached[i], this._sctx);
               }

               return uncached;
            }
         }
      }

      public int size() {
         return this._res.size();
      }

      public Object writeReplace() throws ObjectStreamException {
         return new ArrayList(this);
      }
   }

   private static class QueryCacheExecutor implements StoreQuery.Executor {
      private final StoreQuery.Executor _ex;
      private final Class _candidate;
      private final boolean _subs;
      private final FetchConfiguration _fc;

      public QueryCacheExecutor(StoreQuery.Executor ex, ClassMetaData meta, boolean subs, FetchConfiguration fc) {
         this._ex = ex;
         this._candidate = meta == null ? null : meta.getDescribedType();
         this._subs = subs;
         this._fc = fc;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         QueryCacheStoreQuery cq = (QueryCacheStoreQuery)q;
         QueryKey key = QueryKey.newInstance(cq.getContext(), this._ex.isPacking(q), params, this._candidate, this._subs, range.start, range.end);
         List cached = cq.checkCache(key);
         if (cached != null) {
            return new ListResultObjectProvider(cached);
         } else {
            ResultObjectProvider rop = this._ex.executeQuery(cq.getDelegate(), params, range);
            return this._fc.getQueryCacheEnabled() ? cq.wrapResult(rop, key) : rop;
         }
      }

      private void clearAccessPath(StoreQuery q) {
         if (q != null) {
            ClassMetaData[] cmd = this.getAccessPathMetaDatas(q);
            if (cmd != null && cmd.length != 0) {
               List classes = new ArrayList(cmd.length);

               for(int i = 0; i < cmd.length; ++i) {
                  classes.add(cmd[i].getDescribedType());
               }

               QueryCacheStoreQuery cq = (QueryCacheStoreQuery)q;
               cq.getCache().onTypesChanged(new TypesChangedEvent(q.getContext(), classes));

               for(int i = 0; i < cmd.length; ++i) {
                  if (cmd[i].getDataCache() != null) {
                     cmd[i].getDataCache().removeAll(cmd[i].getDescribedType(), true);
                  }
               }

            }
         }
      }

      public Number executeDelete(StoreQuery q, Object[] params) {
         Number var3;
         try {
            var3 = this._ex.executeDelete(unwrap(q), params);
         } finally {
            this.clearAccessPath(q);
         }

         return var3;
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         Number var3;
         try {
            var3 = this._ex.executeUpdate(unwrap(q), params);
         } finally {
            this.clearAccessPath(q);
         }

         return var3;
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return StoreQuery.EMPTY_STRINGS;
      }

      public void validate(StoreQuery q) {
         this._ex.validate(unwrap(q));
      }

      public void getRange(StoreQuery q, Object[] params, StoreQuery.Range range) {
         this._ex.getRange(q, params, range);
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int orderIndex) {
         return this._ex.getOrderingValue(unwrap(q), params, resultObject, orderIndex);
      }

      public boolean[] getAscending(StoreQuery q) {
         return this._ex.getAscending(unwrap(q));
      }

      public boolean isPacking(StoreQuery q) {
         return this._ex.isPacking(unwrap(q));
      }

      public String getAlias(StoreQuery q) {
         return this._ex.getAlias(unwrap(q));
      }

      public Class getResultClass(StoreQuery q) {
         return this._ex.getResultClass(unwrap(q));
      }

      public String[] getProjectionAliases(StoreQuery q) {
         return this._ex.getProjectionAliases(unwrap(q));
      }

      public Class[] getProjectionTypes(StoreQuery q) {
         return this._ex.getProjectionTypes(unwrap(q));
      }

      public ClassMetaData[] getAccessPathMetaDatas(StoreQuery q) {
         return this._ex.getAccessPathMetaDatas(unwrap(q));
      }

      public int getOperation(StoreQuery q) {
         return this._ex.getOperation(unwrap(q));
      }

      public boolean isAggregate(StoreQuery q) {
         return this._ex.isAggregate(unwrap(q));
      }

      public boolean hasGrouping(StoreQuery q) {
         return this._ex.hasGrouping(unwrap(q));
      }

      public LinkedMap getParameterTypes(StoreQuery q) {
         return this._ex.getParameterTypes(unwrap(q));
      }

      public Map getUpdates(StoreQuery q) {
         return this._ex.getUpdates(unwrap(q));
      }

      private static StoreQuery unwrap(StoreQuery q) {
         return ((QueryCacheStoreQuery)q).getDelegate();
      }
   }
}
