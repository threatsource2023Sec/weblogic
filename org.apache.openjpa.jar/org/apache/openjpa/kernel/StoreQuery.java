package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;

public interface StoreQuery extends QueryOperations, Serializable {
   LinkedMap EMPTY_PARAMS = new LinkedMap(1, 1.0F);
   ClassMetaData[] EMPTY_METAS = new ClassMetaData[0];
   String[] EMPTY_STRINGS = new String[0];
   Object[] EMPTY_OBJECTS = new Object[0];
   Class[] EMPTY_CLASSES = new Class[0];
   boolean[] EMPTY_BOOLEANS = new boolean[0];

   QueryContext getContext();

   void setContext(QueryContext var1);

   boolean setQuery(Object var1);

   FilterListener getFilterListener(String var1);

   AggregateListener getAggregateListener(String var1);

   Object newCompilationKey();

   Object newCompilation();

   void populateFromCompilation(Object var1);

   void invalidateCompilation();

   boolean supportsDataStoreExecution();

   boolean supportsInMemoryExecution();

   Executor newInMemoryExecutor(ClassMetaData var1, boolean var2);

   Executor newDataStoreExecutor(ClassMetaData var1, boolean var2);

   boolean supportsAbstractExecutors();

   boolean requiresCandidateType();

   boolean requiresParameterDeclarations();

   boolean supportsParameterDeclarations();

   public interface Executor {
      ResultObjectProvider executeQuery(StoreQuery var1, Object[] var2, Range var3);

      Number executeDelete(StoreQuery var1, Object[] var2);

      Number executeUpdate(StoreQuery var1, Object[] var2);

      String[] getDataStoreActions(StoreQuery var1, Object[] var2, Range var3);

      void validate(StoreQuery var1);

      void getRange(StoreQuery var1, Object[] var2, Range var3);

      Object getOrderingValue(StoreQuery var1, Object[] var2, Object var3, int var4);

      boolean[] getAscending(StoreQuery var1);

      boolean isPacking(StoreQuery var1);

      String getAlias(StoreQuery var1);

      String[] getProjectionAliases(StoreQuery var1);

      Class[] getProjectionTypes(StoreQuery var1);

      ClassMetaData[] getAccessPathMetaDatas(StoreQuery var1);

      int getOperation(StoreQuery var1);

      boolean isAggregate(StoreQuery var1);

      boolean hasGrouping(StoreQuery var1);

      LinkedMap getParameterTypes(StoreQuery var1);

      Class getResultClass(StoreQuery var1);

      Map getUpdates(StoreQuery var1);
   }

   public static class Range {
      public long start = 0L;
      public long end = Long.MAX_VALUE;
      public boolean lrs = false;

      public Range() {
      }

      public Range(long start, long end) {
         this.start = start;
         this.end = end;
      }
   }
}
