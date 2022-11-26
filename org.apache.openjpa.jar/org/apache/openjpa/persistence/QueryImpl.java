package org.apache.openjpa.persistence;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.FlushModeType;
import javax.persistence.TemporalType;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.kernel.DelegatingQuery;
import org.apache.openjpa.kernel.DelegatingResultList;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class QueryImpl implements OpenJPAQuerySPI, Serializable {
   private static final Object[] EMPTY_ARRAY = new Object[0];
   private static final Localizer _loc = Localizer.forPackage(QueryImpl.class);
   private final DelegatingQuery _query;
   private transient EntityManagerImpl _em;
   private transient FetchPlan _fetch;
   private Map _named;
   private List _positional;

   public QueryImpl(EntityManagerImpl em, RuntimeExceptionTranslator ret, Query query) {
      this._em = em;
      this._query = new DelegatingQuery(query, ret);
   }

   public Query getDelegate() {
      return this._query.getDelegate();
   }

   public OpenJPAEntityManager getEntityManager() {
      return this._em;
   }

   public String getLanguage() {
      return this._query.getLanguage();
   }

   public QueryOperationType getOperation() {
      return QueryOperationType.fromKernelConstant(this._query.getOperation());
   }

   public FetchPlan getFetchPlan() {
      this._em.assertNotCloseInvoked();
      this._query.assertNotSerialized();
      this._query.lock();

      FetchPlan var1;
      try {
         if (this._fetch == null) {
            this._fetch = ((EntityManagerFactoryImpl)this._em.getEntityManagerFactory()).toFetchPlan(this._query.getBroker(), this._query.getFetchConfiguration());
         }

         var1 = this._fetch;
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public String getQueryString() {
      return this._query.getQueryString();
   }

   public boolean getIgnoreChanges() {
      return this._query.getIgnoreChanges();
   }

   public OpenJPAQuery setIgnoreChanges(boolean ignore) {
      this._em.assertNotCloseInvoked();
      this._query.setIgnoreChanges(ignore);
      return this;
   }

   public OpenJPAQuery addFilterListener(FilterListener listener) {
      this._em.assertNotCloseInvoked();
      this._query.addFilterListener(listener);
      return this;
   }

   public OpenJPAQuery removeFilterListener(FilterListener listener) {
      this._em.assertNotCloseInvoked();
      this._query.removeFilterListener(listener);
      return this;
   }

   public OpenJPAQuery addAggregateListener(AggregateListener listener) {
      this._em.assertNotCloseInvoked();
      this._query.addAggregateListener(listener);
      return this;
   }

   public OpenJPAQuery removeAggregateListener(AggregateListener listener) {
      this._em.assertNotCloseInvoked();
      this._query.removeAggregateListener(listener);
      return this;
   }

   public Collection getCandidateCollection() {
      return this._query.getCandidateCollection();
   }

   public OpenJPAQuery setCandidateCollection(Collection coll) {
      this._em.assertNotCloseInvoked();
      this._query.setCandidateCollection(coll);
      return this;
   }

   public Class getResultClass() {
      Class res = this._query.getResultType();
      return res != null ? res : this._query.getCandidateType();
   }

   public OpenJPAQuery setResultClass(Class cls) {
      this._em.assertNotCloseInvoked();
      if (ImplHelper.isManagedType(this._em.getConfiguration(), cls)) {
         this._query.setCandidateType(cls, true);
      } else {
         this._query.setResultType(cls);
      }

      return this;
   }

   public boolean hasSubclasses() {
      return this._query.hasSubclasses();
   }

   public OpenJPAQuery setSubclasses(boolean subs) {
      this._em.assertNotCloseInvoked();
      Class cls = this._query.getCandidateType();
      this._query.setCandidateExtent(this._query.getBroker().newExtent(cls, subs));
      return this;
   }

   public int getFirstResult() {
      return asInt(this._query.getStartRange());
   }

   public OpenJPAQuery setFirstResult(int startPosition) {
      this._em.assertNotCloseInvoked();
      long end;
      if (this._query.getEndRange() == Long.MAX_VALUE) {
         end = Long.MAX_VALUE;
      } else {
         end = (long)startPosition + (this._query.getEndRange() - this._query.getStartRange());
      }

      this._query.setRange((long)startPosition, end);
      return this;
   }

   public int getMaxResults() {
      return asInt(this._query.getEndRange() - this._query.getStartRange());
   }

   public OpenJPAQuery setMaxResults(int max) {
      this._em.assertNotCloseInvoked();
      long start = this._query.getStartRange();
      if (max == Integer.MAX_VALUE) {
         this._query.setRange(start, Long.MAX_VALUE);
      } else {
         this._query.setRange(start, start + (long)max);
      }

      return this;
   }

   public OpenJPAQuery compile() {
      this._em.assertNotCloseInvoked();
      this._query.compile();
      return this;
   }

   private Object execute() {
      if (this._query.getOperation() != 1) {
         throw new InvalidStateException(_loc.get("not-select-query", (Object)this._query.getQueryString()), (Throwable[])null, (Object)null, false);
      } else {
         this.validateParameters();
         if (this._positional != null) {
            return this._query.execute(this._positional.toArray());
         } else {
            return this._named != null ? this._query.execute(this._named) : this._query.execute();
         }
      }
   }

   private void validateParameters() {
      LinkedMap types;
      if (this._positional != null) {
         types = this._query.getParameterTypes();
         int i = 0;

         for(int size = Math.min(this._positional.size(), types.size()); i < size; ++i) {
            this.validateParameter(String.valueOf(i), (Class)types.getValue(i), this._positional.get(i));
         }
      } else if (this._named != null) {
         types = this._query.getParameterTypes();
         Iterator i = this._named.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            String name = (String)entry.getKey();
            this.validateParameter(name, (Class)types.get(name), entry.getValue());
         }
      }

   }

   private void validateParameter(String paramDesc, Class type, Object param) {
      if (param != null && type != null) {
         if (!Filters.wrap(type).isInstance(param)) {
            throw new ArgumentException(_loc.get("bad-param-type", paramDesc, param.getClass().getName(), type.getName()), (Throwable[])null, (Object)null, false);
         }
      }
   }

   public List getResultList() {
      this._em.assertNotCloseInvoked();
      Object ob = this.execute();
      if (ob instanceof List) {
         List ret = (List)ob;
         return (List)(ret instanceof ResultList ? new DelegatingResultList((ResultList)ret, PersistenceExceptions.getRollbackTranslator(this._em)) : ret);
      } else {
         return Collections.singletonList(ob);
      }
   }

   public Object getSingleResult() {
      this._em.assertNotCloseInvoked();
      this._query.setUnique(true);

      Object var1;
      try {
         var1 = this.execute();
      } finally {
         this._query.setUnique(false);
      }

      return var1;
   }

   public int executeUpdate() {
      this._em.assertNotCloseInvoked();
      if (this._query.getOperation() == 2) {
         if (this._positional != null) {
            return asInt(this._query.deleteAll(this._positional.toArray()));
         } else {
            return this._named != null ? asInt(this._query.deleteAll(this._named)) : asInt(this._query.deleteAll());
         }
      } else if (this._query.getOperation() == 3) {
         if (this._positional != null) {
            return asInt(this._query.updateAll(this._positional.toArray()));
         } else {
            return this._named != null ? asInt(this._query.updateAll(this._named)) : asInt(this._query.updateAll());
         }
      } else {
         throw new InvalidStateException(_loc.get("not-update-delete-query", (Object)this._query.getQueryString()), (Throwable[])null, (Object)null, false);
      }
   }

   private static int asInt(long l) {
      if (l > 2147483647L) {
         return Integer.MAX_VALUE;
      } else {
         return l < -2147483648L ? Integer.MIN_VALUE : (int)l;
      }
   }

   public FlushModeType getFlushMode() {
      return EntityManagerImpl.fromFlushBeforeQueries(this._query.getFetchConfiguration().getFlushBeforeQueries());
   }

   public OpenJPAQuery setFlushMode(FlushModeType flushMode) {
      this._em.assertNotCloseInvoked();
      this._query.getFetchConfiguration().setFlushBeforeQueries(EntityManagerImpl.toFlushBeforeQueries(flushMode));
      return this;
   }

   public OpenJPAQuery setHint(String key, Object value) {
      this._em.assertNotCloseInvoked();
      if (key != null && key.startsWith("openjpa.")) {
         String k = key.substring("openjpa.".length());

         try {
            if ("Subclasses".equals(k)) {
               if (value instanceof String) {
                  value = Boolean.valueOf((String)value);
               }

               this.setSubclasses((Boolean)value);
            } else if ("FilterListener".equals(k)) {
               this.addFilterListener(Filters.hintToFilterListener(value, this._query.getBroker().getClassLoader()));
            } else {
               int i;
               if ("FilterListeners".equals(k)) {
                  FilterListener[] arr = Filters.hintToFilterListeners(value, this._query.getBroker().getClassLoader());

                  for(i = 0; i < arr.length; ++i) {
                     this.addFilterListener(arr[i]);
                  }
               } else if ("AggregateListener".equals(k)) {
                  this.addAggregateListener(Filters.hintToAggregateListener(value, this._query.getBroker().getClassLoader()));
               } else if ("FilterListeners".equals(k)) {
                  AggregateListener[] arr = Filters.hintToAggregateListeners(value, this._query.getBroker().getClassLoader());

                  for(i = 0; i < arr.length; ++i) {
                     this.addAggregateListener(arr[i]);
                  }
               } else if (k.startsWith("FetchPlan.")) {
                  k = k.substring("FetchPlan.".length());
                  this.hintToSetter(this.getFetchPlan(), k, value);
               } else {
                  if (!k.startsWith("hint.")) {
                     throw new ArgumentException(_loc.get("bad-query-hint", (Object)key), (Throwable[])null, (Object)null, false);
                  }

                  if ("hint.OptimizeResultCount".equals(k)) {
                     if (value instanceof String) {
                        try {
                           value = new Integer((String)value);
                        } catch (NumberFormatException var6) {
                        }
                     }

                     if (!(value instanceof Number) || ((Number)value).intValue() < 0) {
                        throw new ArgumentException(_loc.get("bad-query-hint-value", key, value), (Throwable[])null, (Object)null, false);
                     }
                  }

                  this._query.getFetchConfiguration().setHint(key, value);
               }
            }

            return this;
         } catch (Exception var7) {
            throw PersistenceExceptions.toPersistenceException(var7);
         }
      } else {
         return this;
      }
   }

   private void hintToSetter(FetchPlan fetchPlan, String k, Object value) {
      if (fetchPlan != null && k != null) {
         Method setter = Reflection.findSetter(fetchPlan.getClass(), k, true);
         Class paramType = setter.getParameterTypes()[0];
         if (Enum.class.isAssignableFrom(paramType) && value instanceof String) {
            value = Enum.valueOf(paramType, (String)value);
         }

         Filters.hintToSetter(fetchPlan, k, value);
      }
   }

   public OpenJPAQuery setParameter(int position, Calendar value, TemporalType t) {
      return this.setParameter(position, value);
   }

   public OpenJPAQuery setParameter(int position, Date value, TemporalType type) {
      return this.setParameter(position, value);
   }

   public OpenJPAQuery setParameter(int position, Object value) {
      this._query.assertOpen();
      this._em.assertNotCloseInvoked();
      this._query.lock();

      try {
         if (this._named != null) {
            throw new InvalidStateException(_loc.get("no-pos-named-params-mix", (Object)this._query.getQueryString()), (Throwable[])null, (Object)null, false);
         } else if (position < 1) {
            throw new InvalidStateException(_loc.get("illegal-index", (Object)position), (Throwable[])null, (Object)null, false);
         } else {
            if (this._positional == null) {
               this._positional = new ArrayList();
            }

            while(this._positional.size() < position) {
               this._positional.add((Object)null);
            }

            this._positional.set(position - 1, value);
            QueryImpl var3 = this;
            return var3;
         }
      } finally {
         this._query.unlock();
      }
   }

   public OpenJPAQuery setParameter(String name, Calendar value, TemporalType t) {
      return this.setParameter(name, value);
   }

   public OpenJPAQuery setParameter(String name, Date value, TemporalType type) {
      return this.setParameter(name, value);
   }

   public OpenJPAQuery setParameter(String name, Object value) {
      this._query.assertOpen();
      this._em.assertNotCloseInvoked();
      this._query.lock();

      QueryImpl var3;
      try {
         if (this._positional != null) {
            throw new InvalidStateException(_loc.get("no-pos-named-params-mix", (Object)this._query.getQueryString()), (Throwable[])null, (Object)null, false);
         }

         if (this._named == null) {
            this._named = new HashMap();
         }

         this._named.put(name, value);
         var3 = this;
      } finally {
         this._query.unlock();
      }

      return var3;
   }

   public boolean hasPositionalParameters() {
      return this._positional != null;
   }

   public Object[] getPositionalParameters() {
      this._query.lock();

      Object[] var1;
      try {
         var1 = this._positional == null ? EMPTY_ARRAY : this._positional.toArray();
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public OpenJPAQuery setParameters(Object... params) {
      this._query.assertOpen();
      this._em.assertNotCloseInvoked();
      this._query.lock();

      QueryImpl var6;
      try {
         this._positional = null;
         this._named = null;
         if (params != null) {
            for(int i = 0; i < params.length; ++i) {
               this.setParameter(i + 1, params[i]);
            }
         }

         var6 = this;
      } finally {
         this._query.unlock();
      }

      return var6;
   }

   public Map getNamedParameters() {
      this._query.lock();

      Map var1;
      try {
         var1 = this._named == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(this._named);
      } finally {
         this._query.unlock();
      }

      return var1;
   }

   public OpenJPAQuery setParameters(Map params) {
      this._query.assertOpen();
      this._em.assertNotCloseInvoked();
      this._query.lock();

      QueryImpl var7;
      try {
         this._positional = null;
         this._named = null;
         if (params != null) {
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
               Map.Entry e = (Map.Entry)i$.next();
               this.setParameter((String)e.getKey(), e.getValue());
            }
         }

         var7 = this;
      } finally {
         this._query.unlock();
      }

      return var7;
   }

   public OpenJPAQuery closeAll() {
      this._query.closeAll();
      return this;
   }

   public String[] getDataStoreActions(Map params) {
      return this._query.getDataStoreActions(params);
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
}
