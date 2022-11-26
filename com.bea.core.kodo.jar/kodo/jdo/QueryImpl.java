package kodo.jdo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;
import kodo.kernel.jdoql.SingleString;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.kernel.DelegatingQuery;
import org.apache.openjpa.kernel.DelegatingResultList;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.NoResultException;

public class QueryImpl implements KodoQuery {
   private static final int CONST_JDOQL = 2;
   private static final int CONST_METHODQL = 4;
   private static final int CONST_SQL = 8;
   private final DelegatingQuery _query;
   private final int _lang;
   private transient FetchPlan _plan = null;
   private transient PersistenceManagerImpl _pm = null;
   private SingleString _sstr;
   private boolean _syncedString = true;
   private boolean _syncedClauses = false;
   private transient Collection _extFiltListeners = null;
   private transient Collection _extAggListeners = null;
   private transient Map _extFetchValues = null;

   public QueryImpl(PersistenceManagerImpl pm, Query query) {
      this._pm = pm;
      this._query = new DelegatingQuery(query, JDOExceptions.TRANSLATOR);
      if ("javax.jdo.query.JDOQL".equals(query.getLanguage())) {
         this._lang = 2;
      } else if ("openjpa.MethodQL".equals(query.getLanguage())) {
         this._lang = 4;
      } else {
         this._lang = 0;
      }

      if (this._lang == 2) {
         this._sstr = new SingleString();
      } else {
         this._sstr = null;
      }

   }

   public Query getDelegate() {
      this._query.lock();

      Query var1;
      try {
         this.syncQueryString();
         var1 = this._query.getDelegate();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void compile() {
      this.assertLanguage(-9);
      this._query.lock();

      try {
         this.syncQueryString();
         this._query.compile();
      } finally {
         this._query.unlock();
      }

   }

   public Object execute() {
      this._query.lock();

      Object var2;
      try {
         this.syncQueryString();
         Object var1 = this.wrapResultList(this._query.execute());
         return var1;
      } catch (NoResultException var6) {
         var2 = null;
      } finally {
         this._query.unlock();
      }

      return var2;
   }

   private Object wrapResultList(Object ob) {
      if (ob instanceof ResultList) {
         ob = new DelegatingResultList((ResultList)ob, JDOExceptions.TRANSLATOR);
      }

      return ob;
   }

   public Object execute(Object p) {
      return this.executeWithArray(new Object[]{p});
   }

   public Object execute(Object p1, Object p2) {
      return this.executeWithArray(new Object[]{p1, p2});
   }

   public Object execute(Object p1, Object p2, Object p3) {
      return this.executeWithArray(new Object[]{p1, p2, p3});
   }

   public Object executeWithMap(Map params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      Object var3;
      try {
         this.syncQueryString();
         Object var2 = this.wrapResultList(this._query.execute(params));
         return var2;
      } catch (NoResultException var7) {
         var3 = null;
      } finally {
         this._query.unlock();
      }

      return var3;
   }

   Map toKodoParameters(Map params) {
      if (params == null) {
         return null;
      } else {
         Map copy = null;
         Iterator itr = params.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            Object orig;
            Object key;
            Object val;
            do {
               if (!itr.hasNext()) {
                  return (Map)(copy == null ? params : copy);
               }

               entry = (Map.Entry)itr.next();
               orig = entry.getKey();
               key = orig;
               this.assertPersistenceManager(entry.getValue());
               val = KodoJDOHelper.toKodoObjectId(entry.getValue(), this._pm);
               if (this._lang == 2 && orig.toString().charAt(0) == ':') {
                  key = orig.toString().substring(1);
                  break;
               }
            } while(val == entry.getValue());

            if (copy == null) {
               copy = new HashMap(params);
            }

            copy.put(key, val);
            if (orig != key) {
               copy.remove(orig);
            }
         }
      }
   }

   public Object executeWithArray(Object[] params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      Object var3;
      try {
         this.syncQueryString();
         Object var2 = this.wrapResultList(this._query.execute(params));
         return var2;
      } catch (NoResultException var7) {
         var3 = null;
      } finally {
         this._query.unlock();
      }

      return var3;
   }

   Object[] toKodoParameters(Object[] params) {
      for(int i = 0; i < params.length; ++i) {
         this.assertPersistenceManager(params[i]);
      }

      return KodoJDOHelper.toKodoObjectIds((Object[])params, this._pm);
   }

   private void assertPersistenceManager(Object obj) {
      PersistenceManager pm = KodoJDOHelper.getPersistenceManager(obj);
      if (pm != null && pm != this._pm) {
         throw new UserException(QueryImpl.Loc._loc.get("managed-param", Exceptions.toString(obj)), (Throwable[])null, obj);
      }
   }

   public PersistenceManager getPersistenceManager() {
      return this._pm;
   }

   public void close(Object result) {
      KodoJDOHelper.close(result);
   }

   public void closeAll() {
      this._query.closeAll();
   }

   public boolean getIgnoreCache() {
      return this._query.getIgnoreChanges();
   }

   public void setIgnoreCache(boolean ignore) {
      this._query.setIgnoreChanges(ignore);
   }

   public String getLanguage() {
      String lang = this._query.getLanguage();
      return "openjpa.SQL".equals(lang) ? "javax.jdo.query.SQL" : lang;
   }

   public FetchPlan getFetchPlan() {
      this._query.assertNotSerialized();
      this._query.lock();

      FetchPlan var1;
      try {
         if (this._plan == null) {
            this._plan = this.getPersistenceManagerFactoryImpl().toFetchPlan(this._query.getBroker(), this._query.getFetchConfiguration());
         }

         var1 = this._plan;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   protected PersistenceManagerFactoryImpl getPersistenceManagerFactoryImpl() {
      return (PersistenceManagerFactoryImpl)this._pm.getPersistenceManagerFactory();
   }

   public void addFilterListener(FilterListener listener) {
      this._query.addFilterListener(listener);
   }

   public void removeFilterListener(FilterListener listener) {
      this._query.removeFilterListener(listener);
   }

   public void addAggregateListener(AggregateListener listener) {
      this._query.addAggregateListener(listener);
   }

   public void removeAggregateListener(AggregateListener listener) {
      this._query.removeAggregateListener(listener);
   }

   public String[] getDataStoreActions(Map params) {
      return this._query.getDataStoreActions(params);
   }

   public Extent getCandidateExtent() {
      this._query.assertNotSerialized();
      return new ExtentImpl(this._pm, this._query.getCandidateExtent());
   }

   public void setCandidates(Extent extent) {
      this._query.setCandidateExtent(((ExtentImpl)extent).getDelegate());
   }

   public Collection getCandidateCollection() {
      return this._query.getCandidateCollection();
   }

   public void setCandidates(Collection coll) {
      this._query.setCandidateCollection(coll);
   }

   public Class getCandidateClass() {
      return this._query.getCandidateType();
   }

   public void setClass(Class cls) {
      this._query.setCandidateType(cls, true);
   }

   public boolean hasSubclasses() {
      return this._query.hasSubclasses();
   }

   public String getQueryString() {
      this._query.lock();

      String var1;
      try {
         this.syncQueryString();
         var1 = this._query.getQueryString();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public String getFilter() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang != 2) {
            var1 = this._query.getQueryString();
            return var1;
         }

         this.syncQueryClauses();
         var1 = this._sstr.filter;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void setFilter(String filter) {
      this.assertLanguage(2);
      if (filter != null && filter.length() == 0) {
         filter = null;
      }

      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.filter = filter;
      } finally {
         this._query.unlock();
      }

   }

   public String getOrdering() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang != 2) {
            var1 = null;
            return var1;
         }

         this.syncQueryClauses();
         var1 = this._sstr.ordering;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void setOrdering(String order) {
      this.assertLanguage(2);
      if (order != null && order.length() == 0) {
         order = null;
      }

      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.ordering = order;
      } finally {
         this._query.unlock();
      }

   }

   public String getImports() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang != 2) {
            var1 = null;
            return var1;
         }

         this.syncQueryClauses();
         var1 = this._sstr.imports;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void declareImports(String imports) {
      this.assertLanguage(2);
      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.imports = imports;
      } finally {
         this._query.unlock();
      }

   }

   public String getParameters() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang == 2) {
            this.syncQueryClauses();
            var1 = this._sstr.parameters;
            return var1;
         }

         var1 = this._query.getParameterDeclaration();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void declareParameters(String params) {
      this.assertLanguage(6);
      params = StringUtils.trimToNull(params);
      this._query.lock();

      try {
         if (this._lang == 2) {
            this._query.assertNotReadOnly();
            this.unsyncedQueryClause();
            this._sstr.parameters = params;
         } else {
            this._query.declareParameters(params);
         }
      } finally {
         this._query.unlock();
      }

   }

   public String getVariables() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang != 2) {
            var1 = null;
            return var1;
         }

         this.syncQueryClauses();
         var1 = this._sstr.variables;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void declareVariables(String vars) {
      this.assertLanguage(2);
      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.variables = vars;
      } finally {
         this._query.unlock();
      }

   }

   public boolean isUnique() {
      return this._query.isUnique();
   }

   public void setUnique(boolean unique) {
      this._query.lock();

      try {
         this._query.setUnique(unique);
         if (this._lang == 2) {
            this.unsyncedQueryClause();
            this._sstr.unique = null;
         }
      } finally {
         this._query.unlock();
      }

   }

   public String getResult() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang == 2) {
            this.syncQueryClauses();
            var1 = this._sstr.result;
            return var1;
         }

         var1 = null;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void setResult(String result) {
      this.assertLanguage(2);
      if (result != null && result.length() == 0) {
         result = null;
      }

      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.result = result;
      } finally {
         this._query.unlock();
      }

   }

   public Class getResultClass() {
      return this._query.getResultType();
   }

   public void setResultClass(Class cls) {
      this._query.lock();

      try {
         this._query.setResultType(cls);
         if (this._lang == 2) {
            this.unsyncedQueryClause();
            this._sstr.resultType = null;
         }
      } finally {
         this._query.unlock();
      }

   }

   public String getGrouping() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang != 2) {
            var1 = null;
            return var1;
         }

         this.syncQueryClauses();
         var1 = this._sstr.grouping;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void setGrouping(String group) {
      this.assertLanguage(2);
      if (group != null && group.length() == 0) {
         group = null;
      }

      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.unsyncedQueryClause();
         this._sstr.grouping = group;
      } finally {
         this._query.unlock();
      }

   }

   public long getStartRange() {
      return this._query.getStartRange();
   }

   public long getEndRange() {
      return this._query.getEndRange();
   }

   public void setRange(long start, long end) {
      this._query.lock();

      try {
         this._query.setRange(start, end);
         if (this._lang == 2) {
            this.unsyncedQueryClause();
            this._sstr.range = null;
         }
      } finally {
         this._query.unlock();
      }

   }

   public String getRange() {
      this._query.lock();

      String var1;
      try {
         this._query.assertOpen();
         if (this._lang == 2) {
            this.syncQueryClauses();
            var1 = this._sstr.range;
            return var1;
         }

         var1 = null;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public void setRange(String range) {
      this.assertLanguage(2);
      if (range != null) {
         range = range.trim();
      }

      if (range != null && range.length() != 0) {
         this._query.lock();

         try {
            this._query.assertOpen();
            this._query.assertNotReadOnly();
            this.unsyncedQueryClause();
            this._sstr.range = range;
         } finally {
            this._query.unlock();
         }

      } else {
         this.setRange(0L, Long.MAX_VALUE);
      }
   }

   public boolean isUnmodifiable() {
      return this._query.isReadOnly();
   }

   public void setUnmodifiable() {
      this._query.lock();

      try {
         this.syncQueryClauses();
         this._query.setReadOnly(true);
      } finally {
         this._query.unlock();
      }

   }

   public long deletePersistentAll() {
      this._query.lock();

      long var1;
      try {
         this.syncQueryString();
         var1 = this._query.deleteAll();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public long deletePersistentAll(Object[] params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      long var2;
      try {
         this.syncQueryString();
         var2 = this._query.deleteAll(params);
      } finally {
         this._query.unlock();
      }

      return var2;
   }

   public long deletePersistentAll(Map params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      long var2;
      try {
         this.syncQueryString();
         var2 = this._query.deleteAll(params);
      } finally {
         this._query.unlock();
      }

      return var2;
   }

   public long updatePersistentAll() {
      this._query.lock();

      long var1;
      try {
         this.syncQueryString();
         var1 = this._query.updateAll();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public long updatePersistentAll(Object[] params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      long var2;
      try {
         this.syncQueryString();
         var2 = this._query.updateAll(params);
      } finally {
         this._query.unlock();
      }

      return var2;
   }

   public long updatePersistentAll(Map params) {
      params = this.toKodoParameters(params);
      this._query.lock();

      long var2;
      try {
         this.syncQueryString();
         var2 = this._query.updateAll(params);
      } finally {
         this._query.unlock();
      }

      return var2;
   }

   public void addExtension(String key, Object value) {
      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         this.addExtension(key, value, true);
      } catch (Exception var7) {
         throw JDOExceptions.toJDOException(var7);
      } finally {
         this._query.unlock();
      }

   }

   public void setExtensions(Map extensions) {
      this._query.lock();

      try {
         this._query.assertOpen();
         this._query.assertNotReadOnly();
         Iterator itr;
         if (this._extFiltListeners != null && !this._extFiltListeners.isEmpty()) {
            itr = this._extFiltListeners.iterator();

            while(itr.hasNext()) {
               this.removeFilterListener((FilterListener)itr.next());
            }

            this._extFiltListeners.clear();
         }

         if (this._extAggListeners != null && !this._extAggListeners.isEmpty()) {
            itr = this._extAggListeners.iterator();

            while(itr.hasNext()) {
               this.removeAggregateListener((AggregateListener)itr.next());
            }

            this._extAggListeners.clear();
         }

         Iterator itr;
         Map.Entry entry;
         if (this._extFetchValues != null && !this._extFetchValues.isEmpty()) {
            itr = this._extFetchValues.entrySet().iterator();

            while(itr.hasNext()) {
               entry = (Map.Entry)itr.next();
               this.addExtension(entry.getKey().toString(), entry.getValue(), false);
            }

            this._extFetchValues.clear();
         }

         if (extensions != null && !extensions.isEmpty()) {
            itr = extensions.entrySet().iterator();

            while(itr.hasNext()) {
               entry = (Map.Entry)itr.next();
               this.addExtension(entry.getKey().toString(), entry.getValue(), true);
            }
         }
      } catch (Exception var7) {
         throw JDOExceptions.toJDOException(var7);
      } finally {
         this._query.unlock();
      }

   }

   private void addExtension(String key, Object value, boolean record) {
      if (key != null) {
         String[] prefixes = ProductDerivations.getConfigurationPrefixes();
         String prefix = null;

         for(int i = 0; i < prefixes.length; ++i) {
            prefix = prefixes[i] + ".";
            if (key.startsWith(prefix)) {
               break;
            }

            prefix = null;
         }

         if (prefix != null) {
            String k = key.substring(prefix.length());
            if (record && "FilterListener".equals(k)) {
               this.addExtensionListener(Filters.hintToFilterListener(value, this._query.getBroker().getClassLoader()));
            } else {
               int i;
               if (record && "FilterListeners".equals(k) && value != null) {
                  FilterListener[] arr = Filters.hintToFilterListeners(value, this._query.getBroker().getClassLoader());

                  for(i = 0; i < arr.length; ++i) {
                     this.addExtensionListener(arr[i]);
                  }
               } else if (record && "AggregateListener".equals(k)) {
                  this.addExtensionListener(Filters.hintToAggregateListener(value, this._query.getBroker().getClassLoader()));
               } else if (record && "AggregateListeners".equals(k) && value != null) {
                  AggregateListener[] arr = Filters.hintToAggregateListeners(value, this._query.getBroker().getClassLoader());

                  for(i = 0; i < arr.length; ++i) {
                     this.addExtensionListener(arr[i]);
                  }
               } else if (k.startsWith("FetchPlan.")) {
                  k = k.substring("FetchPlan.".length());
                  FetchPlan plan = this.getFetchPlan();
                  if (record && (this._extFetchValues == null || !this._extFetchValues.containsKey(key))) {
                     if (this._extFetchValues == null) {
                        this._extFetchValues = new HashMap(5);
                     }

                     this._extFetchValues.put(key, Filters.hintToGetter(plan, k));
                  }

                  Filters.hintToSetter(plan, k, value);
               } else {
                  if (!k.startsWith("hint.")) {
                     throw new UserException(QueryImpl.Loc._loc.get("bad-query-extension", key), (Throwable[])null, (Object)null);
                  }

                  this._query.getFetchConfiguration().setHint(key, value);
               }
            }

         }
      }
   }

   private void addExtensionListener(FilterListener value) {
      if (value != null && !this._query.getFilterListeners().contains(value)) {
         this.addFilterListener(value);
         if (this._extFiltListeners == null) {
            this._extFiltListeners = new LinkedList();
         }

         this._extFiltListeners.add(value);
      }
   }

   private void addExtensionListener(AggregateListener value) {
      if (value != null && !this._query.getAggregateListeners().contains(value)) {
         this.addAggregateListener(value);
         if (this._extAggListeners == null) {
            this._extAggListeners = new LinkedList();
         }

         this._extAggListeners.add(value);
      }
   }

   private void assertLanguage(int lang) {
      if ((lang & this._lang) == 0) {
         throw new UserException(QueryImpl.Loc._loc.get("bad-query-op", this.getLanguage()), (Throwable[])null, (Object)null);
      }
   }

   private void syncQueryString() {
      if (!this._syncedString) {
         boolean readOnly = this._query.isReadOnly();
         this._query.setReadOnly(false);
         this._query.setQuery(this._sstr.toString());
         this._query.setQuery(this._sstr);
         this._query.setReadOnly(readOnly);
         this._syncedString = true;
      }
   }

   private void syncQueryClauses() {
      if (!this._syncedClauses) {
         this._sstr.fromString(this._query.getQueryString());
         this._syncedClauses = true;
      }

   }

   private void unsyncedQueryClause() {
      this._sstr = (SingleString)this._sstr.clone();
      this._syncedString = false;
      this.syncQueryClauses();
   }

   public int hashCode() {
      return this._query.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof QueryImpl) ? false : this._query.equals(((QueryImpl)other)._query);
      }
   }

   private static class Loc {
      static final Localizer _loc = Localizer.forPackage(QueryImpl.class);
   }
}
