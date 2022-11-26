package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.Constant;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.kernel.exps.Literal;
import org.apache.openjpa.kernel.exps.Val;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.rop.EagerResultList;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.NoResultException;
import org.apache.openjpa.util.NonUniqueResultException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

public class QueryImpl implements Query {
   private static final Localizer _loc = Localizer.forPackage(QueryImpl.class);
   private final String _language;
   private final StoreQuery _storeQuery;
   private final transient BrokerImpl _broker;
   private final transient Log _log;
   private transient ClassLoader _loader = null;
   private final ReentrantLock _lock;
   private Class _class = null;
   private boolean _subclasses = true;
   private boolean _readOnly = false;
   private String _query = null;
   private String _params = null;
   private transient Compilation _compiled = null;
   private transient boolean _compiling = false;
   private transient ResultPacker _packer = null;
   private transient Collection _collection = null;
   private transient Extent _extent = null;
   private Map _filtListeners = null;
   private Map _aggListeners = null;
   private FetchConfiguration _fc = null;
   private boolean _ignoreChanges = false;
   private Class _resultMappingScope = null;
   private String _resultMappingName = null;
   private Boolean _unique = null;
   private Class _resultClass = null;
   private transient long _startIdx = 0L;
   private transient long _endIdx = Long.MAX_VALUE;
   private transient boolean _rangeSet = false;
   private final transient Collection _resultLists = new ReferenceHashSet(2);

   public QueryImpl(Broker broker, String language, StoreQuery storeQuery) {
      this._broker = (BrokerImpl)broker;
      this._language = language;
      this._storeQuery = storeQuery;
      this._fc = (FetchConfiguration)broker.getFetchConfiguration().clone();
      this._log = broker.getConfiguration().getLog("openjpa.Query");
      this._storeQuery.setContext(this);
      if (this._broker != null && this._broker.getMultithreaded()) {
         this._lock = new ReentrantLock();
      } else {
         this._lock = null;
      }

   }

   public StoreQuery getStoreQuery() {
      return this._storeQuery;
   }

   public Broker getBroker() {
      return this._broker;
   }

   public Query getQuery() {
      return this;
   }

   public StoreContext getStoreContext() {
      return this._broker;
   }

   public String getLanguage() {
      return this._language;
   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fc;
   }

   public String getQueryString() {
      return this._query;
   }

   public boolean getIgnoreChanges() {
      this.assertOpen();
      return this._ignoreChanges;
   }

   public void setIgnoreChanges(boolean flag) {
      this.lock();

      try {
         this.assertOpen();
         this._ignoreChanges = flag;
      } finally {
         this.unlock();
      }

   }

   public boolean isReadOnly() {
      this.assertOpen();
      return this._readOnly;
   }

   public void setReadOnly(boolean flag) {
      this.lock();

      try {
         this.assertOpen();
         this._readOnly = flag;
      } finally {
         this.unlock();
      }

   }

   public void addFilterListener(FilterListener listener) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (this._filtListeners == null) {
            this._filtListeners = new HashMap(5);
         }

         this._filtListeners.put(listener.getTag(), listener);
      } finally {
         this.unlock();
      }

   }

   public void removeFilterListener(FilterListener listener) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (this._filtListeners != null) {
            this._filtListeners.remove(listener.getTag());
         }
      } finally {
         this.unlock();
      }

   }

   public Collection getFilterListeners() {
      return (Collection)(this._filtListeners == null ? Collections.EMPTY_LIST : this._filtListeners.values());
   }

   public FilterListener getFilterListener(String tag) {
      if (this._filtListeners != null) {
         FilterListener listen = (FilterListener)this._filtListeners.get(tag);
         if (listen != null) {
            return listen;
         }
      }

      FilterListener[] confListeners = this._broker.getConfiguration().getFilterListenerInstances();

      for(int i = 0; i < confListeners.length; ++i) {
         if (confListeners[i].getTag().equals(tag)) {
            return confListeners[i];
         }
      }

      return this._storeQuery.getFilterListener(tag);
   }

   public void addAggregateListener(AggregateListener listener) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (this._aggListeners == null) {
            this._aggListeners = new HashMap(5);
         }

         this._aggListeners.put(listener.getTag(), listener);
      } finally {
         this.unlock();
      }

   }

   public void removeAggregateListener(AggregateListener listener) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (this._aggListeners != null) {
            this._aggListeners.remove(listener.getTag());
         }
      } finally {
         this.unlock();
      }

   }

   public Collection getAggregateListeners() {
      return (Collection)(this._aggListeners == null ? Collections.EMPTY_LIST : this._aggListeners.values());
   }

   public AggregateListener getAggregateListener(String tag) {
      if (this._aggListeners != null) {
         AggregateListener listen = (AggregateListener)this._aggListeners.get(tag);
         if (listen != null) {
            return listen;
         }
      }

      AggregateListener[] confListeners = this._broker.getConfiguration().getAggregateListenerInstances();

      for(int i = 0; i < confListeners.length; ++i) {
         if (confListeners[i].getTag().equals(tag)) {
            return confListeners[i];
         }
      }

      return this._storeQuery.getAggregateListener(tag);
   }

   public Extent getCandidateExtent() {
      this.lock();

      Extent var2;
      try {
         Class cls = this.getCandidateType();
         if (this._extent == null && this._collection == null && this._broker != null && cls != null) {
            this._extent = this._broker.newExtent(cls, this._subclasses);
            this._extent.setIgnoreChanges(this._ignoreChanges);
         } else if (this._extent != null && this._extent.getIgnoreChanges() != this._ignoreChanges && cls != null) {
            this._extent = this._broker.newExtent(cls, this._extent.hasSubclasses());
            this._extent.setIgnoreChanges(this._ignoreChanges);
         }

         var2 = this._extent;
      } finally {
         this.unlock();
      }

      return var2;
   }

   public void setCandidateExtent(Extent candidateExtent) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (candidateExtent == this._extent) {
            return;
         }

         if (candidateExtent == null) {
            this._extent = null;
            return;
         }

         this._extent = candidateExtent;
         this._collection = null;
         boolean invalidate = false;
         if (this._extent.getElementType() != this._class) {
            this._class = this._extent.getElementType();
            this._loader = null;
            invalidate = true;
         }

         if (this._extent.hasSubclasses() != this._subclasses) {
            this._subclasses = this._extent.hasSubclasses();
            invalidate = true;
         }

         if (invalidate) {
            this.invalidateCompilation();
         }
      } finally {
         this.unlock();
      }

   }

   public Collection getCandidateCollection() {
      this.assertOpen();
      return this._collection;
   }

   public void setCandidateCollection(Collection candidateCollection) {
      if (!this._storeQuery.supportsInMemoryExecution()) {
         throw new UnsupportedException(_loc.get("query-nosupport", (Object)this._language));
      } else {
         this.lock();

         try {
            this.assertOpen();
            this._collection = candidateCollection;
            if (this._collection != null) {
               this._extent = null;
            }
         } finally {
            this.unlock();
         }

      }
   }

   public Class getCandidateType() {
      this.lock();

      Class var1;
      try {
         this.assertOpen();
         if (this._class != null || this._compiled != null || this._query == null || this._broker == null) {
            var1 = this._class;
            return var1;
         }

         this.compileForCompilation();
         var1 = this._class;
      } finally {
         this.unlock();
      }

      return var1;
   }

   public void setCandidateType(Class candidateClass, boolean subs) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         this._class = candidateClass;
         this._subclasses = subs;
         this._loader = null;
         this.invalidateCompilation();
      } finally {
         this.unlock();
      }

   }

   public boolean hasSubclasses() {
      return this._subclasses;
   }

   public String getResultMappingName() {
      this.assertOpen();
      return this._resultMappingName;
   }

   public Class getResultMappingScope() {
      this.assertOpen();
      return this._resultMappingScope;
   }

   public void setResultMapping(Class scope, String name) {
      this.lock();

      try {
         this.assertOpen();
         this._resultMappingScope = scope;
         this._resultMappingName = name;
         this._packer = null;
      } finally {
         this.unlock();
      }

   }

   public boolean isUnique() {
      this.lock();

      try {
         this.assertOpen();
         boolean var1;
         if (this._unique != null) {
            var1 = this._unique;
            return var1;
         } else if (this._query != null && !this._compiling && this._broker != null) {
            if (this._compiled == null) {
               this.compileForCompilation();
               if (this._unique != null) {
                  var1 = this._unique;
                  return var1;
               }
            }

            StoreQuery.Executor ex = this.compileForExecutor();
            boolean var2;
            if (!ex.isAggregate(this._storeQuery)) {
               var2 = false;
               return var2;
            } else {
               var2 = !ex.hasGrouping(this._storeQuery);
               return var2;
            }
         } else {
            var1 = false;
            return var1;
         }
      } finally {
         this.unlock();
      }
   }

   public void setUnique(boolean unique) {
      this.lock();

      try {
         this.assertOpen();
         this.assertNotReadOnly();
         this._unique = unique ? Boolean.TRUE : Boolean.FALSE;
      } finally {
         this.unlock();
      }

   }

   public Class getResultType() {
      this.lock();

      Class var1;
      try {
         this.assertOpen();
         if (this._resultClass != null || this._compiled != null || this._query == null || this._broker == null) {
            var1 = this._resultClass;
            return var1;
         }

         this.compileForCompilation();
         var1 = this._resultClass;
      } finally {
         this.unlock();
      }

      return var1;
   }

   public void setResultType(Class cls) {
      this.lock();

      try {
         this.assertOpen();
         this._resultClass = cls;
         this._packer = null;
      } finally {
         this.unlock();
      }

   }

   public long getStartRange() {
      this.assertOpen();
      return this._startIdx;
   }

   public long getEndRange() {
      this.assertOpen();
      return this._endIdx;
   }

   public void setRange(long start, long end) {
      if (start >= 0L && end >= 0L) {
         if (end - start > 2147483647L && end != Long.MAX_VALUE) {
            throw new UserException(_loc.get("range-too-big", String.valueOf(start), String.valueOf(end)));
         } else {
            this.lock();

            try {
               this.assertOpen();
               this._startIdx = start;
               this._endIdx = end;
               this._rangeSet = true;
            } finally {
               this.unlock();
            }

         }
      } else {
         throw new UserException(_loc.get("invalid-range", String.valueOf(start), String.valueOf(end)));
      }
   }

   public String getParameterDeclaration() {
      this.lock();

      String var1;
      try {
         this.assertOpen();
         if (this._params != null || this._compiled != null || this._compiling || this._broker == null) {
            var1 = this._params;
            return var1;
         }

         this.compileForCompilation();
         var1 = this._params;
      } finally {
         this.unlock();
      }

      return var1;
   }

   public void declareParameters(String params) {
      if (!this._storeQuery.supportsParameterDeclarations()) {
         throw new UnsupportedException(_loc.get("query-nosupport", (Object)this._language));
      } else {
         this.lock();

         try {
            this.assertOpen();
            this.assertNotReadOnly();
            this._params = StringUtils.trimToNull(params);
            this.invalidateCompilation();
         } finally {
            this.unlock();
         }

      }
   }

   public void compile() {
      this.lock();

      try {
         this.assertOpen();
         StoreQuery.Executor ex = this.compileForExecutor();
         this.getResultPacker(this._storeQuery, ex);
         ex.validate(this._storeQuery);
      } finally {
         this.unlock();
      }

   }

   public Object getCompilation() {
      this.lock();

      Object var1;
      try {
         var1 = this.compileForCompilation().storeData;
      } finally {
         this.unlock();
      }

      return var1;
   }

   private Compilation compileForCompilation() {
      if (this._compiled == null && !this._compiling) {
         this.assertNotSerialized();
         this.assertOpen();
         boolean readOnly = this._readOnly;
         this._readOnly = false;
         this._compiling = true;

         Compilation var2;
         try {
            this._compiled = this.compilationFromCache();
            var2 = this._compiled;
         } catch (OpenJPAException var8) {
            throw var8;
         } catch (RuntimeException var9) {
            throw new GeneralException(var9);
         } finally {
            this._compiling = false;
            this._readOnly = readOnly;
         }

         return var2;
      } else {
         return this._compiled;
      }
   }

   protected Compilation compilationFromCache() {
      Map compCache = this._broker.getConfiguration().getQueryCompilationCacheInstance();
      if (compCache == null) {
         return this.newCompilation();
      } else {
         CompilationKey key = new CompilationKey();
         key.queryType = this._storeQuery.getClass();
         key.candidateType = this.getCandidateType();
         key.subclasses = this.hasSubclasses();
         key.query = this.getQueryString();
         key.language = this.getLanguage();
         key.storeKey = this._storeQuery.newCompilationKey();
         Compilation comp = (Compilation)compCache.get(key);
         boolean cache = false;
         if (comp == null) {
            comp = this.newCompilation();
            cache = comp.storeData != null;
         } else {
            this._storeQuery.populateFromCompilation(comp.storeData);
         }

         if (cache) {
            compCache.put(key, comp);
         }

         return comp;
      }
   }

   private Compilation newCompilation() {
      Compilation comp = new Compilation();
      comp.storeData = this._storeQuery.newCompilation();
      this._storeQuery.populateFromCompilation(comp.storeData);
      return comp;
   }

   private StoreQuery.Executor compileForExecutor() {
      Compilation comp = this.compileForCompilation();
      if (this._collection == null) {
         if (comp.datastore != null) {
            return comp.datastore;
         } else if (comp.memory != null) {
            return comp.memory;
         } else {
            return this._storeQuery.supportsDataStoreExecution() ? this.compileForDataStore(comp) : this.compileForInMemory(comp);
         }
      } else if (comp.memory != null) {
         return comp.memory;
      } else if (comp.datastore != null) {
         return comp.datastore;
      } else {
         return this._storeQuery.supportsInMemoryExecution() ? this.compileForInMemory(comp) : this.compileForDataStore(comp);
      }
   }

   private StoreQuery.Executor compileForDataStore(Compilation comp) {
      if (comp.datastore == null) {
         comp.datastore = this.createExecutor(false);
      }

      return comp.datastore;
   }

   private StoreQuery.Executor compileForInMemory(Compilation comp) {
      if (comp.memory == null) {
         comp.memory = this.createExecutor(true);
      }

      return comp.memory;
   }

   private StoreQuery.Executor createExecutor(boolean inMem) {
      this.assertCandidateType();
      MetaDataRepository repos = this._broker.getConfiguration().getMetaDataRepositoryInstance();
      ClassMetaData meta = repos.getMetaData(this._class, this._broker.getClassLoader(), false);
      ClassMetaData[] metas;
      if (this._class != null && !this._storeQuery.supportsAbstractExecutors()) {
         if (!this._subclasses || meta != null && !meta.isManagedInterface()) {
            if (meta == null || !this._subclasses && !meta.isMapped()) {
               metas = StoreQuery.EMPTY_METAS;
            } else {
               metas = new ClassMetaData[]{meta};
            }
         } else {
            metas = repos.getImplementorMetaDatas(this._class, this._broker.getClassLoader(), true);
         }
      } else {
         metas = new ClassMetaData[]{meta};
      }

      if (metas.length == 0) {
         throw new UserException(_loc.get("no-impls", (Object)this._class));
      } else {
         try {
            if (metas.length == 1) {
               return inMem ? this._storeQuery.newInMemoryExecutor(metas[0], this._subclasses) : this._storeQuery.newDataStoreExecutor(metas[0], this._subclasses);
            } else {
               StoreQuery.Executor[] es = new StoreQuery.Executor[metas.length];

               for(int i = 0; i < es.length; ++i) {
                  if (inMem) {
                     es[i] = this._storeQuery.newInMemoryExecutor(metas[i], true);
                  } else {
                     es[i] = this._storeQuery.newDataStoreExecutor(metas[i], true);
                  }
               }

               return new MergedExecutor(es);
            }
         } catch (OpenJPAException var7) {
            throw var7;
         } catch (RuntimeException var8) {
            throw new GeneralException(var8);
         }
      }
   }

   private boolean invalidateCompilation() {
      if (this._compiling) {
         return false;
      } else {
         this._storeQuery.invalidateCompilation();
         this._compiled = null;
         this._packer = null;
         return true;
      }
   }

   public Object execute() {
      return this.execute((Object[])null);
   }

   public Object execute(Object[] params) {
      return this.execute(1, (Object[])params);
   }

   public Object execute(Map params) {
      return this.execute(1, (Map)params);
   }

   private Object execute(int operation, Object[] params) {
      if (params == null) {
         params = StoreQuery.EMPTY_OBJECTS;
      }

      this.lock();

      Object var5;
      try {
         this.assertNotSerialized();
         this._broker.beginOperation(true);

         try {
            this.assertOpen();
            this._broker.assertNontransactionalRead();
            Compilation comp = this.compileForCompilation();
            StoreQuery.Executor ex = this.isInMemory(operation) ? this.compileForInMemory(comp) : this.compileForDataStore(comp);
            this.assertParameters(this._storeQuery, ex, params);
            if (this._log.isTraceEnabled()) {
               this.logExecution(operation, ex.getParameterTypes(this._storeQuery), params);
            }

            if (operation == 1) {
               var5 = this.execute(this._storeQuery, ex, params);
               return var5;
            }

            if (operation != 2) {
               if (operation == 3) {
                  var5 = this.update(this._storeQuery, ex, params);
                  return var5;
               }

               throw new UnsupportedException();
            }

            var5 = this.delete(this._storeQuery, ex, params);
         } catch (OpenJPAException var18) {
            throw var18;
         } catch (Exception var19) {
            throw new UserException(var19);
         } finally {
            this._broker.endOperation();
         }
      } finally {
         this.unlock();
      }

      return var5;
   }

   private Object execute(int operation, Map params) {
      if (params == null) {
         params = Collections.EMPTY_MAP;
      }

      this.lock();

      Object var6;
      try {
         this._broker.beginOperation(true);

         try {
            this.assertNotSerialized();
            this.assertOpen();
            this._broker.assertNontransactionalRead();
            Compilation comp = this.compileForCompilation();
            StoreQuery.Executor ex = this.isInMemory(operation) ? this.compileForInMemory(comp) : this.compileForDataStore(comp);
            Object[] arr = params.isEmpty() ? StoreQuery.EMPTY_OBJECTS : this.toParameterArray(ex.getParameterTypes(this._storeQuery), params);
            this.assertParameters(this._storeQuery, ex, arr);
            if (this._log.isTraceEnabled()) {
               this.logExecution(operation, params);
            }

            if (operation != 1) {
               if (operation == 2) {
                  var6 = this.delete(this._storeQuery, ex, arr);
                  return var6;
               }

               if (operation == 3) {
                  var6 = this.update(this._storeQuery, ex, arr);
                  return var6;
               }

               throw new UnsupportedException();
            }

            var6 = this.execute(this._storeQuery, ex, arr);
         } catch (OpenJPAException var19) {
            throw var19;
         } catch (Exception var20) {
            throw new UserException(var20);
         } finally {
            this._broker.endOperation();
         }
      } finally {
         this.unlock();
      }

      return var6;
   }

   public long deleteAll() {
      return this.deleteAll((Object[])null);
   }

   public long deleteAll(Object[] params) {
      return ((Number)this.execute(2, (Object[])params)).longValue();
   }

   public long deleteAll(Map params) {
      return ((Number)this.execute(2, (Map)params)).longValue();
   }

   public long updateAll() {
      return this.updateAll((Object[])null);
   }

   public long updateAll(Object[] params) {
      return ((Number)this.execute(3, (Object[])params)).longValue();
   }

   public long updateAll(Map params) {
      return ((Number)this.execute(3, (Map)params)).longValue();
   }

   private Object[] toParameterArray(LinkedMap paramTypes, Map params) {
      if (params != null && !params.isEmpty()) {
         Object[] arr = new Object[params.size()];
         int base = -1;
         Iterator itr = params.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            Object key = entry.getKey();
            int idx = paramTypes == null ? -1 : paramTypes.indexOf(key);
            if (idx != -1) {
               arr[idx] = entry.getValue();
            } else {
               if (!(key instanceof Number)) {
                  throw new UserException(_loc.get("bad-param-name", key));
               }

               if (base == -1) {
                  base = positionalParameterBase(params.keySet());
               }

               arr[((Number)key).intValue() - base] = entry.getValue();
            }
         }

         return arr;
      } else {
         return StoreQuery.EMPTY_OBJECTS;
      }
   }

   private static int positionalParameterBase(Collection params) {
      int low = Integer.MAX_VALUE;
      Iterator itr = params.iterator();

      while(itr.hasNext()) {
         Object obj = itr.next();
         if (!(obj instanceof Number)) {
            return 0;
         }

         int val = ((Number)obj).intValue();
         if (val == 0) {
            return val;
         }

         if (val < low) {
            low = val;
         }
      }

      return low;
   }

   private boolean isInMemory(int operation) {
      boolean inMem = !this._storeQuery.supportsDataStoreExecution() || this._collection != null;
      if (!inMem && (!this._ignoreChanges || operation != 1) && this._broker.isActive() && this.isAccessPathDirty()) {
         int flush = this._fc.getFlushBeforeQueries();
         if ((flush == 0 || flush == 2 && this._broker.hasConnection() || operation != 1 || !this._storeQuery.supportsInMemoryExecution()) && this._broker.getConfiguration().supportedOptions().contains("openjpa.option.IncrementalFlush")) {
            this._broker.flush();
         } else {
            if (this._log.isInfoEnabled()) {
               this._log.info(_loc.get("force-in-mem", (Object)this._class));
            }

            inMem = true;
         }
      }

      if (inMem && !this._storeQuery.supportsInMemoryExecution()) {
         throw new InvalidStateException(_loc.get("cant-exec-inmem", (Object)this._language));
      } else {
         return inMem;
      }
   }

   private Object execute(StoreQuery q, StoreQuery.Executor ex, Object[] params) throws Exception {
      StoreQuery.Range range = new StoreQuery.Range(this._startIdx, this._endIdx);
      if (!this._rangeSet) {
         ex.getRange(q, params, range);
      }

      if (range.start >= range.end) {
         return this.emptyResult(q, ex);
      } else {
         range.lrs = this.isLRS(range.start, range.end);
         ResultObjectProvider rop = ex.executeQuery(q, params, range);

         try {
            return this.toResult(q, ex, rop, range);
         } catch (Exception var9) {
            if (rop != null) {
               try {
                  rop.close();
               } catch (Exception var8) {
               }
            }

            throw var9;
         }
      }
   }

   private Number delete(StoreQuery q, StoreQuery.Executor ex, Object[] params) throws Exception {
      this.assertBulkModify(q, ex, params);
      return ex.executeDelete(q, params);
   }

   public Number deleteInMemory(StoreQuery q, StoreQuery.Executor executor, Object[] params) {
      try {
         Object o = this.execute(q, executor, params);
         if (!(o instanceof Collection)) {
            o = Collections.singleton(o);
         }

         int size = 0;

         for(Iterator i = ((Collection)o).iterator(); i.hasNext(); ++size) {
            this._broker.delete(i.next(), (OpCallbacks)null);
         }

         return Numbers.valueOf(size);
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new UserException(var8);
      }
   }

   private Number update(StoreQuery q, StoreQuery.Executor ex, Object[] params) throws Exception {
      this.assertBulkModify(q, ex, params);
      return ex.executeUpdate(q, params);
   }

   public Number updateInMemory(StoreQuery q, StoreQuery.Executor executor, Object[] params) {
      try {
         Object o = this.execute(q, executor, params);
         if (!(o instanceof Collection)) {
            o = Collections.singleton(o);
         }

         int size = 0;

         for(Iterator i = ((Collection)o).iterator(); i.hasNext(); ++size) {
            this.updateInMemory(i.next(), params);
         }

         return Numbers.valueOf(size);
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new UserException(var8);
      }
   }

   private void updateInMemory(Object ob, Object[] params) {
      Iterator it = this.getUpdates().entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry e = (Map.Entry)it.next();
         FieldMetaData fmd = (FieldMetaData)e.getKey();
         Object value = e.getValue();
         Object val;
         if (value instanceof Val) {
            val = ((Val)value).evaluate((Object)ob, (Object)null, this.getStoreContext(), params);
         } else if (value instanceof Literal) {
            val = ((Literal)value).getValue();
         } else {
            if (!(value instanceof Constant)) {
               throw new UserException(_loc.get("only-update-primitives"));
            }

            val = ((Constant)value).getValue(params);
         }

         OpenJPAStateManager sm = this._broker.getStateManager(ob);
         int i = fmd.getIndex();
         PersistenceCapable into = ImplHelper.toPersistenceCapable(ob, this._broker.getConfiguration());
         int set = 0;
         switch (fmd.getDeclaredTypeCode()) {
            case 0:
               sm.settingBooleanField(into, i, sm.fetchBooleanField(i), val == null ? false : (Boolean)val, set);
               break;
            case 1:
               sm.settingByteField(into, i, sm.fetchByteField(i), val == null ? 0 : ((Number)val).byteValue(), set);
               break;
            case 2:
               sm.settingCharField(into, i, sm.fetchCharField(i), val == null ? '\u0000' : val.toString().charAt(0), set);
               break;
            case 3:
               sm.settingDoubleField(into, i, sm.fetchDoubleField(i), val == null ? 0.0 : ((Number)val).doubleValue(), set);
               break;
            case 4:
               sm.settingFloatField(into, i, sm.fetchFloatField(i), val == null ? 0.0F : ((Number)val).floatValue(), set);
               break;
            case 5:
               sm.settingIntField(into, i, sm.fetchIntField(i), val == null ? 0 : ((Number)val).intValue(), set);
               break;
            case 6:
               sm.settingLongField(into, i, sm.fetchLongField(i), val == null ? 0L : ((Number)val).longValue(), set);
               break;
            case 7:
               sm.settingShortField(into, i, sm.fetchShortField(i), val == null ? 0 : ((Number)val).shortValue(), set);
               break;
            case 8:
            case 10:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
               sm.settingObjectField(into, i, sm.fetchObjectField(i), val, set);
               break;
            case 9:
               sm.settingStringField(into, i, sm.fetchStringField(i), val == null ? null : val.toString(), set);
               break;
            case 11:
            case 12:
            case 13:
            case 15:
            case 27:
            case 28:
            default:
               throw new UserException(_loc.get("only-update-primitives"));
         }
      }

   }

   private void logExecution(int op, LinkedMap types, Object[] params) {
      Map pmap = Collections.EMPTY_MAP;
      if (params.length > 0) {
         pmap = new HashMap((int)((double)params.length * 1.33 + 1.0));
         int i;
         if (types != null && types.size() == params.length) {
            i = 0;
            Iterator itr = types.keySet().iterator();

            while(itr.hasNext()) {
               ((Map)pmap).put(itr.next(), params[i++]);
            }
         } else {
            for(i = 0; i < params.length; ++i) {
               ((Map)pmap).put(String.valueOf(i), params[i]);
            }
         }
      }

      this.logExecution(op, (Map)pmap);
   }

   private void logExecution(int op, Map params) {
      String s = this._query;
      if (StringUtils.isEmpty(s)) {
         s = this.toString();
      }

      String msg = "executing-query";
      if (!params.isEmpty()) {
         msg = msg + "-with-params";
      }

      this._log.trace(_loc.get(msg, s, params));
   }

   private boolean isLRS(long start, long end) {
      long range = end - start;
      return this._fc.getFetchBatchSize() >= 0 && range > (long)this._fc.getFetchBatchSize() && (this._fc.getFetchBatchSize() != 0 || range > 50L);
   }

   protected Object toResult(StoreQuery q, StoreQuery.Executor ex, ResultObjectProvider rop, StoreQuery.Range range) throws Exception {
      String[] aliases = ex.getProjectionAliases(q);
      if (!ex.isPacking(q)) {
         ResultPacker packer = this.getResultPacker(q, ex);
         if (packer != null || aliases.length == 1) {
            rop = new PackingResultObjectProvider((ResultObjectProvider)rop, packer, aliases.length);
         }
      }

      if (this._unique != Boolean.TRUE && (aliases.length <= 0 || ex.hasGrouping(q) || !ex.isAggregate(q))) {
         boolean detach = (this._broker.getAutoDetach() & 8) > 0 && !this._broker.isActive();
         boolean lrs = range.lrs && !ex.isAggregate(q) && !ex.hasGrouping(q);
         ResultList res = !detach && lrs ? this._fc.newResultList((ResultObjectProvider)rop) : new EagerResultList((ResultObjectProvider)rop);
         this._resultLists.add(this.decorateResultList((ResultList)res));
         return res;
      } else {
         return this.singleResult((ResultObjectProvider)rop, range);
      }
   }

   protected ResultList decorateResultList(ResultList res) {
      return new RemoveOnCloseResultList(res);
   }

   private ResultPacker getResultPacker(StoreQuery q, StoreQuery.Executor ex) {
      if (this._packer != null) {
         return this._packer;
      } else {
         Class resultClass = this._resultClass != null ? this._resultClass : ex.getResultClass(q);
         if (resultClass == null) {
            return null;
         } else {
            String[] aliases = ex.getProjectionAliases(q);
            if (aliases.length == 0) {
               this._packer = new ResultPacker(this._class, this.getAlias(), resultClass);
            } else if (resultClass != null) {
               Class[] types = ex.getProjectionTypes(q);
               this._packer = new ResultPacker(types, aliases, resultClass);
            }

            return this._packer;
         }
      }
   }

   private Object emptyResult(StoreQuery q, StoreQuery.Executor ex) {
      return this._unique != Boolean.TRUE && (this._unique != null || ex.hasGrouping(q) || !ex.isAggregate(q)) ? Collections.EMPTY_LIST : null;
   }

   private Object singleResult(ResultObjectProvider rop, StoreQuery.Range range) throws Exception {
      rop.open();

      List var5;
      try {
         boolean next = rop.next();
         Object single = null;
         if (next) {
            single = rop.getResultObject();
            if (range.end != range.start + 1L && rop.next()) {
               throw new NonUniqueResultException(_loc.get("not-unique", this._class, this._query));
            }
         } else if (this._unique == Boolean.TRUE) {
            throw new NoResultException(_loc.get("no-result", this._class, this._query));
         }

         if (this._unique != Boolean.FALSE) {
            Object var10 = single;
            return var10;
         }

         if (!next) {
            var5 = Collections.EMPTY_LIST;
            return var5;
         }

         var5 = Arrays.asList(single);
      } finally {
         rop.close();
      }

      return var5;
   }

   private boolean isAccessPathDirty() {
      return isAccessPathDirty(this._broker, this.getAccessPathMetaDatas());
   }

   public static boolean isAccessPathDirty(Broker broker, ClassMetaData[] accessMetas) {
      Collection persisted = broker.getPersistedTypes();
      Collection updated = broker.getUpdatedTypes();
      Collection deleted = broker.getDeletedTypes();
      if (persisted.isEmpty() && updated.isEmpty() && deleted.isEmpty()) {
         return false;
      } else if (accessMetas.length == 0) {
         return true;
      } else {
         for(int i = 0; i < accessMetas.length; ++i) {
            Class accClass = accessMetas[i].getDescribedType();
            if (persisted.contains(accClass) || updated.contains(accClass) || deleted.contains(accClass)) {
               return true;
            }

            Iterator dirty = persisted.iterator();

            while(dirty.hasNext()) {
               if (accClass.isAssignableFrom((Class)dirty.next())) {
                  return true;
               }
            }

            dirty = updated.iterator();

            while(dirty.hasNext()) {
               if (accClass.isAssignableFrom((Class)dirty.next())) {
                  return true;
               }
            }

            dirty = deleted.iterator();

            while(dirty.hasNext()) {
               if (accClass.isAssignableFrom((Class)dirty.next())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void closeAll() {
      this.closeResults(true);
   }

   public void closeResources() {
      this.closeResults(false);
   }

   private void closeResults(boolean force) {
      this.lock();

      try {
         this.assertOpen();
         Iterator itr = this._resultLists.iterator();

         while(itr.hasNext()) {
            RemoveOnCloseResultList res = (RemoveOnCloseResultList)itr.next();
            if (force || res.isProviderOpen()) {
               res.close(false);
            }
         }

         this._resultLists.clear();
      } finally {
         this.unlock();
      }
   }

   public String[] getDataStoreActions(Map params) {
      if (params == null) {
         params = Collections.EMPTY_MAP;
      }

      this.lock();

      String[] var5;
      try {
         this.assertNotSerialized();
         this.assertOpen();
         StoreQuery.Executor ex = this.compileForExecutor();
         Object[] arr = this.toParameterArray(ex.getParameterTypes(this._storeQuery), params);
         this.assertParameters(this._storeQuery, ex, arr);
         StoreQuery.Range range = new StoreQuery.Range(this._startIdx, this._endIdx);
         if (!this._rangeSet) {
            ex.getRange(this._storeQuery, arr, range);
         }

         var5 = ex.getDataStoreActions(this._storeQuery, arr, range);
      } catch (OpenJPAException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new UserException(var12);
      } finally {
         this.unlock();
      }

      return var5;
   }

   public boolean setQuery(Object query) {
      this.lock();

      boolean var2;
      try {
         this.assertOpen();
         this.assertNotReadOnly();
         if (query == null || query instanceof String) {
            this.invalidateCompilation();
            this._query = (String)query;
            if (this._query != null) {
               this._query = this._query.trim();
            }

            var2 = true;
            return var2;
         }

         if (query instanceof QueryImpl) {
            this.invalidateCompilation();
            QueryImpl q = (QueryImpl)query;
            this._class = q._class;
            this._subclasses = q._subclasses;
            this._query = q._query;
            this._ignoreChanges = q._ignoreChanges;
            this._unique = q._unique;
            this._resultClass = q._resultClass;
            this._params = q._params;
            this._resultMappingScope = q._resultMappingScope;
            this._resultMappingName = q._resultMappingName;
            this._readOnly = q._readOnly;
            this._fc.copy(q._fc);
            if (q._filtListeners != null) {
               this._filtListeners = new HashMap(q._filtListeners);
            }

            if (q._aggListeners != null) {
               this._aggListeners = new HashMap(q._aggListeners);
            }

            boolean var3 = true;
            return var3;
         }

         var2 = this._storeQuery.setQuery(query);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public String getAlias() {
      this.lock();

      String var2;
      try {
         String alias = this.compileForExecutor().getAlias(this._storeQuery);
         if (alias == null) {
            alias = Strings.getClassName(this._class);
         }

         var2 = alias;
      } finally {
         this.unlock();
      }

      return var2;
   }

   public String[] getProjectionAliases() {
      this.lock();

      String[] var1;
      try {
         var1 = this.compileForExecutor().getProjectionAliases(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public Class[] getProjectionTypes() {
      this.lock();

      Class[] var1;
      try {
         var1 = this.compileForExecutor().getProjectionTypes(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public int getOperation() {
      this.lock();

      int var1;
      try {
         var1 = this.compileForExecutor().getOperation(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public boolean isAggregate() {
      this.lock();

      boolean var1;
      try {
         var1 = this.compileForExecutor().isAggregate(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public boolean hasGrouping() {
      this.lock();

      boolean var1;
      try {
         var1 = this.compileForExecutor().hasGrouping(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public ClassMetaData[] getAccessPathMetaDatas() {
      this.lock();

      ClassMetaData[] var2;
      try {
         ClassMetaData[] metas = this.compileForExecutor().getAccessPathMetaDatas(this._storeQuery);
         var2 = metas == null ? StoreQuery.EMPTY_METAS : metas;
      } finally {
         this.unlock();
      }

      return var2;
   }

   public LinkedMap getParameterTypes() {
      this.lock();

      LinkedMap var1;
      try {
         var1 = this.compileForExecutor().getParameterTypes(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public Map getUpdates() {
      this.lock();

      Map var1;
      try {
         var1 = this.compileForExecutor().getUpdates(this._storeQuery);
      } finally {
         this.unlock();
      }

      return var1;
   }

   public void lock() {
      if (this._lock != null) {
         this._lock.lock();
      }

   }

   public void unlock() {
      if (this._lock != null && this._lock.isLocked()) {
         this._lock.unlock();
      }

   }

   public Class classForName(String name, String[] imports) {
      Class type = this.toClass(name);
      if (type != null) {
         return type;
      } else {
         ClassLoader loader = this._class == null ? this._loader : (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(this._class));
         ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(name, loader, false);
         if (meta != null) {
            return meta.getDescribedType();
         } else {
            String dotName;
            if (this._class != null) {
               dotName = this._class.getName().substring(0, this._class.getName().lastIndexOf(46) + 1) + name;
               type = this.toClass(dotName);
               if (type != null) {
                  return type;
               }
            }

            type = this.toClass("java.lang." + name);
            if (type != null) {
               return type;
            } else {
               if (imports != null && imports.length > 0) {
                  dotName = "." + name;

                  for(int i = 0; i < imports.length; ++i) {
                     String importName = imports[i];
                     if (importName.endsWith(dotName)) {
                        type = this.toClass(importName);
                     } else if (importName.endsWith(".*")) {
                        importName = importName.substring(0, importName.length() - 1);
                        type = this.toClass(importName + name);
                     }

                     if (type != null) {
                        return type;
                     }
                  }
               }

               return null;
            }
         }
      }
   }

   private Class toClass(String name) {
      if (this._loader == null) {
         this._loader = this._broker.getConfiguration().getClassResolverInstance().getClassLoader(this._class, this._broker.getClassLoader());
      }

      try {
         return Strings.toClass(name, this._loader);
      } catch (RuntimeException var3) {
      } catch (NoClassDefFoundError var4) {
      }

      return null;
   }

   public void assertOpen() {
      if (this._broker != null) {
         this._broker.assertOpen();
      }

   }

   public void assertNotReadOnly() {
      if (this._readOnly) {
         throw new InvalidStateException(_loc.get("read-only"));
      }
   }

   public void assertNotSerialized() {
      if (this._broker == null) {
         throw new InvalidStateException(_loc.get("serialized"));
      }
   }

   private void assertCandidateType() {
      if (this._class == null && this._storeQuery.requiresCandidateType()) {
         throw new InvalidStateException(_loc.get("no-class"));
      }
   }

   private void assertBulkModify(StoreQuery q, StoreQuery.Executor ex, Object[] params) {
      this._broker.assertActiveTransaction();
      if (this._startIdx == 0L && this._endIdx == Long.MAX_VALUE) {
         if (this._resultClass != null) {
            throw new UserException(_loc.get("no-modify-resultclass"));
         } else {
            StoreQuery.Range range = new StoreQuery.Range();
            ex.getRange(q, params, range);
            if (range.start != 0L || range.end != Long.MAX_VALUE) {
               throw new UserException(_loc.get("no-modify-range"));
            }
         }
      } else {
         throw new UserException(_loc.get("no-modify-range"));
      }
   }

   protected void assertParameters(StoreQuery q, StoreQuery.Executor ex, Object[] params) {
      if (q.requiresParameterDeclarations()) {
         LinkedMap paramTypes = ex.getParameterTypes(q);
         int typeCount = paramTypes.size();
         if (typeCount > params.length) {
            throw new UserException(_loc.get("unbound-params", (Object)paramTypes.keySet()));
         } else {
            Iterator itr = paramTypes.entrySet().iterator();

            for(int i = 0; itr.hasNext(); ++i) {
               Map.Entry entry = (Map.Entry)itr.next();
               if (((Class)entry.getValue()).isPrimitive() && params[i] == null) {
                  throw new UserException(_loc.get("null-primitive-param", entry.getKey()));
               }
            }

         }
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(64);
      buf.append("Query: ").append(super.toString());
      buf.append("; candidate class: ").append(this._class);
      buf.append("; query: ").append(this._query);
      return buf.toString();
   }

   public class RemoveOnCloseResultList implements ResultList {
      private final ResultList _res;

      public RemoveOnCloseResultList(ResultList res) {
         this._res = res;
      }

      public ResultList getDelegate() {
         return this._res;
      }

      public boolean isProviderOpen() {
         return this._res.isProviderOpen();
      }

      public boolean isClosed() {
         return this._res.isClosed();
      }

      public void close() {
         this.close(true);
      }

      public void close(boolean remove) {
         if (!this.isClosed()) {
            this._res.close();
            if (remove) {
               QueryImpl.this.lock();

               try {
                  Iterator itr = QueryImpl.this._resultLists.iterator();

                  while(itr.hasNext()) {
                     if (itr.next() == this) {
                        itr.remove();
                        break;
                     }
                  }
               } finally {
                  QueryImpl.this.unlock();
               }

            }
         }
      }

      public int size() {
         return this._res.size();
      }

      public boolean isEmpty() {
         return this._res.isEmpty();
      }

      public boolean contains(Object o) {
         return this._res.contains(o);
      }

      public Iterator iterator() {
         return this._res.iterator();
      }

      public Object[] toArray() {
         return this._res.toArray();
      }

      public Object[] toArray(Object[] a) {
         return this._res.toArray(a);
      }

      public boolean add(Object o) {
         return this._res.add(o);
      }

      public boolean remove(Object o) {
         return this._res.remove(o);
      }

      public boolean containsAll(Collection c) {
         return this._res.containsAll(c);
      }

      public boolean addAll(Collection c) {
         return this._res.addAll(c);
      }

      public boolean addAll(int idx, Collection c) {
         return this._res.addAll(idx, c);
      }

      public boolean removeAll(Collection c) {
         return this._res.removeAll(c);
      }

      public boolean retainAll(Collection c) {
         return this._res.retainAll(c);
      }

      public void clear() {
         this._res.clear();
      }

      public Object get(int idx) {
         return this._res.get(idx);
      }

      public Object set(int idx, Object o) {
         return this._res.set(idx, o);
      }

      public void add(int idx, Object o) {
         this._res.add(idx, o);
      }

      public Object remove(int idx) {
         return this._res.remove(idx);
      }

      public int indexOf(Object o) {
         return this._res.indexOf(o);
      }

      public int lastIndexOf(Object o) {
         return this._res.lastIndexOf(o);
      }

      public ListIterator listIterator() {
         return this._res.listIterator();
      }

      public ListIterator listIterator(int idx) {
         return this._res.listIterator(idx);
      }

      public List subList(int start, int end) {
         return this._res.subList(start, end);
      }

      public boolean equals(Object o) {
         return this._res.equals(o);
      }

      public int hashCode() {
         return this._res.hashCode();
      }

      public String toString() {
         return this._res.toString();
      }

      public Object writeReplace() {
         return this._res;
      }
   }

   private static class PackingResultObjectProvider implements ResultObjectProvider {
      private final ResultObjectProvider _delegate;
      private final ResultPacker _packer;
      private final int _len;

      public PackingResultObjectProvider(ResultObjectProvider delegate, ResultPacker packer, int resultLength) {
         this._delegate = delegate;
         this._packer = packer;
         this._len = resultLength;
      }

      public boolean supportsRandomAccess() {
         return this._delegate.supportsRandomAccess();
      }

      public void open() throws Exception {
         this._delegate.open();
      }

      public Object getResultObject() throws Exception {
         Object ob = this._delegate.getResultObject();
         if (this._packer == null && this._len == 1) {
            return ((Object[])((Object[])ob))[0];
         } else if (this._packer == null) {
            return ob;
         } else {
            return this._len == 0 ? this._packer.pack(ob) : this._packer.pack((Object[])((Object[])ob));
         }
      }

      public boolean next() throws Exception {
         return this._delegate.next();
      }

      public boolean absolute(int pos) throws Exception {
         return this._delegate.absolute(pos);
      }

      public int size() throws Exception {
         return this._delegate.size();
      }

      public void reset() throws Exception {
         this._delegate.reset();
      }

      public void close() throws Exception {
         this._delegate.close();
      }

      public void handleCheckedException(Exception e) {
         this._delegate.handleCheckedException(e);
      }
   }

   private static class MergedExecutor implements StoreQuery.Executor {
      private final StoreQuery.Executor[] _executors;

      public MergedExecutor(StoreQuery.Executor[] executors) {
         this._executors = executors;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         if (this._executors.length == 1) {
            return this._executors[0].executeQuery(q, params, range);
         } else {
            StoreQuery.Range ropRange = new StoreQuery.Range(0L, range.end);
            ropRange.lrs = range.lrs || range.start > 0L && q.getContext().getFetchConfiguration().getFetchBatchSize() >= 0;
            ResultObjectProvider[] rops = new ResultObjectProvider[this._executors.length];

            for(int i = 0; i < this._executors.length; ++i) {
               rops[i] = this._executors[i].executeQuery(q, params, ropRange);
            }

            boolean[] asc = this._executors[0].getAscending(q);
            Object rop;
            if (asc.length == 0) {
               rop = new MergedResultObjectProvider(rops);
            } else {
               rop = new OrderingMergedResultObjectProvider(rops, asc, this._executors, q, params);
            }

            if (range.start != 0L) {
               rop = new RangeResultObjectProvider((ResultObjectProvider)rop, range.start, range.end);
            }

            return (ResultObjectProvider)rop;
         }
      }

      public Number executeDelete(StoreQuery q, Object[] params) {
         long num = 0L;

         for(int i = 0; i < this._executors.length; ++i) {
            num += this._executors[i].executeDelete(q, params).longValue();
         }

         return Numbers.valueOf(num);
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         long num = 0L;

         for(int i = 0; i < this._executors.length; ++i) {
            num += this._executors[i].executeUpdate(q, params).longValue();
         }

         return Numbers.valueOf(num);
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         if (this._executors.length == 1) {
            return this._executors[0].getDataStoreActions(q, params, range);
         } else {
            List results = new ArrayList(this._executors.length);
            StoreQuery.Range ropRange = new StoreQuery.Range(0L, range.end);

            for(int i = 0; i < this._executors.length; ++i) {
               String[] actions = this._executors[i].getDataStoreActions(q, params, ropRange);
               if (actions != null && actions.length > 0) {
                  results.addAll(Arrays.asList(actions));
               }
            }

            return (String[])((String[])results.toArray(new String[results.size()]));
         }
      }

      public void validate(StoreQuery q) {
         this._executors[0].validate(q);
      }

      public void getRange(StoreQuery q, Object[] params, StoreQuery.Range range) {
         this._executors[0].getRange(q, params, range);
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int idx) {
         return this._executors[0].getOrderingValue(q, params, resultObject, idx);
      }

      public boolean[] getAscending(StoreQuery q) {
         return this._executors[0].getAscending(q);
      }

      public String getAlias(StoreQuery q) {
         return this._executors[0].getAlias(q);
      }

      public String[] getProjectionAliases(StoreQuery q) {
         return this._executors[0].getProjectionAliases(q);
      }

      public Class getResultClass(StoreQuery q) {
         return this._executors[0].getResultClass(q);
      }

      public Class[] getProjectionTypes(StoreQuery q) {
         return this._executors[0].getProjectionTypes(q);
      }

      public boolean isPacking(StoreQuery q) {
         return this._executors[0].isPacking(q);
      }

      public ClassMetaData[] getAccessPathMetaDatas(StoreQuery q) {
         if (this._executors.length == 1) {
            return this._executors[0].getAccessPathMetaDatas(q);
         } else {
            List metas = null;

            for(int i = 0; i < this._executors.length; ++i) {
               metas = Filters.addAccessPathMetaDatas(metas, this._executors[i].getAccessPathMetaDatas(q));
            }

            return metas == null ? StoreQuery.EMPTY_METAS : (ClassMetaData[])((ClassMetaData[])metas.toArray(new ClassMetaData[metas.size()]));
         }
      }

      public boolean isAggregate(StoreQuery q) {
         if (!this._executors[0].isAggregate(q)) {
            return false;
         } else {
            throw new UnsupportedException(QueryImpl._loc.get("merged-aggregate", q.getContext().getCandidateType(), q.getContext().getQueryString()));
         }
      }

      public int getOperation(StoreQuery q) {
         return this._executors[0].getOperation(q);
      }

      public boolean hasGrouping(StoreQuery q) {
         return this._executors[0].hasGrouping(q);
      }

      public LinkedMap getParameterTypes(StoreQuery q) {
         return this._executors[0].getParameterTypes(q);
      }

      public Map getUpdates(StoreQuery q) {
         return this._executors[0].getUpdates(q);
      }
   }

   private static class CompilationKey implements Serializable {
      public Class queryType;
      public Class candidateType;
      public boolean subclasses;
      public String query;
      public String language;
      public Object storeKey;

      private CompilationKey() {
         this.queryType = null;
         this.candidateType = null;
         this.subclasses = true;
         this.query = null;
         this.language = null;
         this.storeKey = null;
      }

      public int hashCode() {
         int rs = 17;
         rs = 37 * rs + (this.queryType == null ? 0 : this.queryType.hashCode());
         rs = 37 * rs + (this.query == null ? 0 : this.query.hashCode());
         rs = 37 * rs + (this.language == null ? 0 : this.language.hashCode());
         rs = 37 * rs + (this.storeKey == null ? 0 : this.storeKey.hashCode());
         if (this.subclasses) {
            ++rs;
         }

         return rs;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other != null && other.getClass() == this.getClass()) {
            CompilationKey key = (CompilationKey)other;
            if (key.queryType == this.queryType && StringUtils.equals(key.query, this.query) && StringUtils.equals(key.language, this.language)) {
               if (key.subclasses != this.subclasses) {
                  return false;
               } else if (!ObjectUtils.equals(key.storeKey, this.storeKey)) {
                  return false;
               } else {
                  return key.candidateType == null || this.candidateType == null || key.candidateType == this.candidateType;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      CompilationKey(Object x0) {
         this();
      }
   }

   protected static class Compilation implements Serializable {
      public StoreQuery.Executor memory = null;
      public StoreQuery.Executor datastore = null;
      public Object storeData = null;
   }
}
