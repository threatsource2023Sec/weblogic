package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingQuery implements Query {
   private final Query _query;
   private final DelegatingQuery _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingQuery(Query query) {
      this(query, (RuntimeExceptionTranslator)null);
   }

   public DelegatingQuery(Query query, RuntimeExceptionTranslator trans) {
      this._query = query;
      if (query instanceof DelegatingQuery) {
         this._del = (DelegatingQuery)query;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public Query getDelegate() {
      return this._query;
   }

   public Query getInnermostDelegate() {
      return this._del == null ? this._query : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingQuery) {
            other = ((DelegatingQuery)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public Broker getBroker() {
      try {
         return this._query.getBroker();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Query getQuery() {
      return this;
   }

   public StoreContext getStoreContext() {
      try {
         return this._query.getStoreContext();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int getOperation() {
      try {
         return this._query.getOperation();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getLanguage() {
      try {
         return this._query.getLanguage();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration getFetchConfiguration() {
      try {
         return this._query.getFetchConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getQueryString() {
      try {
         return this._query.getQueryString();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean getIgnoreChanges() {
      try {
         return this._query.getIgnoreChanges();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object getCompilation() {
      try {
         return this._query.getCompilation();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getAlias() {
      try {
         return this._query.getAlias();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String[] getProjectionAliases() {
      try {
         return this._query.getProjectionAliases();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Class[] getProjectionTypes() {
      try {
         return this._query.getProjectionTypes();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isAggregate() {
      try {
         return this._query.isAggregate();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasGrouping() {
      try {
         return this._query.hasGrouping();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public ClassMetaData[] getAccessPathMetaDatas() {
      try {
         return this._query.getAccessPathMetaDatas();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FilterListener getFilterListener(String tag) {
      try {
         return this._query.getFilterListener(tag);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public AggregateListener getAggregateListener(String tag) {
      try {
         return this._query.getAggregateListener(tag);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Collection getFilterListeners() {
      try {
         return this._query.getFilterListeners();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getAggregateListeners() {
      try {
         return this._query.getAggregateListeners();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getCandidateCollection() {
      try {
         return this._query.getCandidateCollection();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Class getCandidateType() {
      try {
         return this._query.getCandidateType();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasSubclasses() {
      try {
         return this._query.hasSubclasses();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setCandidateType(Class cls, boolean subs) {
      try {
         this._query.setCandidateType(cls, subs);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean isReadOnly() {
      try {
         return this._query.isReadOnly();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setReadOnly(boolean readOnly) {
      try {
         this._query.setReadOnly(readOnly);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Class getResultMappingScope() {
      try {
         return this._query.getResultMappingScope();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getResultMappingName() {
      try {
         return this._query.getResultMappingName();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setResultMapping(Class scope, String name) {
      try {
         this._query.setResultMapping(scope, name);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean isUnique() {
      try {
         return this._query.isUnique();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setUnique(boolean unique) {
      try {
         this._query.setUnique(unique);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Class getResultType() {
      try {
         return this._query.getResultType();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setResultType(Class cls) {
      try {
         this._query.setResultType(cls);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public long getStartRange() {
      try {
         return this._query.getStartRange();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public long getEndRange() {
      try {
         return this._query.getEndRange();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setRange(long start, long end) {
      try {
         this._query.setRange(start, end);
      } catch (RuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public String getParameterDeclaration() {
      try {
         return this._query.getParameterDeclaration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public LinkedMap getParameterTypes() {
      try {
         return this._query.getParameterTypes();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Map getUpdates() {
      try {
         return this._query.getUpdates();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void declareParameters(String params) {
      try {
         this._query.declareParameters(params);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Number deleteInMemory(StoreQuery q, StoreQuery.Executor ex, Object[] params) {
      try {
         return this._query.deleteInMemory(q, ex, params);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Number updateInMemory(StoreQuery q, StoreQuery.Executor ex, Object[] params) {
      try {
         return this._query.updateInMemory(q, ex, params);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Class classForName(String name, String[] imports) {
      try {
         return this._query.classForName(name, imports);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void lock() {
      try {
         this._query.lock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void unlock() {
      try {
         this._query.unlock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void addFilterListener(FilterListener listener) {
      try {
         this._query.addFilterListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeFilterListener(FilterListener listener) {
      try {
         this._query.removeFilterListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void addAggregateListener(AggregateListener listener) {
      try {
         this._query.addAggregateListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeAggregateListener(AggregateListener listener) {
      try {
         this._query.removeAggregateListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Extent getCandidateExtent() {
      try {
         return this._query.getCandidateExtent();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setCandidateExtent(Extent extent) {
      try {
         this._query.setCandidateExtent(extent);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void setCandidateCollection(Collection coll) {
      try {
         this._query.setCandidateCollection(coll);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void compile() {
      try {
         this._query.compile();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object execute() {
      try {
         return this._query.execute();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object execute(Map params) {
      try {
         return this._query.execute(params);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object execute(Object[] params) {
      try {
         return this._query.execute(params);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public long deleteAll() {
      try {
         return this._query.deleteAll();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public long deleteAll(Object[] parameters) {
      try {
         return this._query.deleteAll(parameters);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public long deleteAll(Map parameterMap) {
      try {
         return this._query.deleteAll(parameterMap);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public long updateAll() {
      try {
         return this._query.updateAll();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public long updateAll(Object[] parameters) {
      try {
         return this._query.updateAll(parameters);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public long updateAll(Map parameterMap) {
      try {
         return this._query.updateAll(parameterMap);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void closeAll() {
      try {
         this._query.closeAll();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void closeResources() {
      try {
         this._query.closeResources();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String[] getDataStoreActions(Map params) {
      try {
         return this._query.getDataStoreActions(params);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean setQuery(Object query) {
      try {
         return this._query.setQuery(query);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void setIgnoreChanges(boolean ignore) {
      try {
         this._query.setIgnoreChanges(ignore);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void assertOpen() {
      try {
         this._query.assertOpen();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertNotReadOnly() {
      try {
         this._query.assertNotReadOnly();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertNotSerialized() {
      try {
         this._query.assertNotSerialized();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }
}
