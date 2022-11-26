package org.apache.openjpa.kernel;

import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.InternalException;

public abstract class AbstractStoreQuery implements StoreQuery {
   protected QueryContext ctx = null;

   public QueryContext getContext() {
      return this.ctx;
   }

   public void setContext(QueryContext ctx) {
      this.ctx = ctx;
   }

   public boolean setQuery(Object query) {
      return false;
   }

   public FilterListener getFilterListener(String tag) {
      return null;
   }

   public AggregateListener getAggregateListener(String tag) {
      return null;
   }

   public Object newCompilationKey() {
      return null;
   }

   public Object newCompilation() {
      return null;
   }

   public void populateFromCompilation(Object comp) {
   }

   public void invalidateCompilation() {
   }

   public boolean supportsDataStoreExecution() {
      return false;
   }

   public boolean supportsInMemoryExecution() {
      return false;
   }

   public StoreQuery.Executor newInMemoryExecutor(ClassMetaData meta, boolean subs) {
      throw new InternalException();
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subs) {
      throw new InternalException();
   }

   public boolean supportsAbstractExecutors() {
      return false;
   }

   public boolean requiresCandidateType() {
      return true;
   }

   public boolean requiresParameterDeclarations() {
      return true;
   }

   public boolean supportsParameterDeclarations() {
      return true;
   }

   public abstract static class AbstractExecutor implements StoreQuery.Executor {
      public Number executeDelete(StoreQuery q, Object[] params) {
         return q.getContext().deleteInMemory(q, this, params);
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         return q.getContext().updateInMemory(q, this, params);
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return StoreQuery.EMPTY_STRINGS;
      }

      public void validate(StoreQuery q) {
      }

      public void getRange(StoreQuery q, Object[] params, StoreQuery.Range range) {
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int orderIndex) {
         return null;
      }

      public boolean[] getAscending(StoreQuery q) {
         return StoreQuery.EMPTY_BOOLEANS;
      }

      public boolean isPacking(StoreQuery q) {
         return false;
      }

      public String getAlias(StoreQuery q) {
         return null;
      }

      public String[] getProjectionAliases(StoreQuery q) {
         return StoreQuery.EMPTY_STRINGS;
      }

      public Class[] getProjectionTypes(StoreQuery q) {
         return StoreQuery.EMPTY_CLASSES;
      }

      public ClassMetaData[] getAccessPathMetaDatas(StoreQuery q) {
         return StoreQuery.EMPTY_METAS;
      }

      public int getOperation(StoreQuery q) {
         return 1;
      }

      public boolean isAggregate(StoreQuery q) {
         return false;
      }

      public boolean hasGrouping(StoreQuery q) {
         return false;
      }

      public LinkedMap getParameterTypes(StoreQuery q) {
         return StoreQuery.EMPTY_PARAMS;
      }

      public Class getResultClass(StoreQuery q) {
         return null;
      }

      public Map getUpdates(StoreQuery q) {
         return null;
      }
   }
}
