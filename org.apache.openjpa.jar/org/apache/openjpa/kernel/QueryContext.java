package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.meta.ClassMetaData;

public interface QueryContext {
   Query getQuery();

   StoreContext getStoreContext();

   FetchConfiguration getFetchConfiguration();

   int getOperation();

   String getLanguage();

   String getQueryString();

   Collection getCandidateCollection();

   Class getCandidateType();

   boolean hasSubclasses();

   void setCandidateType(Class var1, boolean var2);

   boolean isReadOnly();

   void setReadOnly(boolean var1);

   boolean isUnique();

   void setUnique(boolean var1);

   Class getResultMappingScope();

   String getResultMappingName();

   void setResultMapping(Class var1, String var2);

   Class getResultType();

   void setResultType(Class var1);

   long getStartRange();

   long getEndRange();

   void setRange(long var1, long var3);

   String getParameterDeclaration();

   void declareParameters(String var1);

   LinkedMap getParameterTypes();

   Map getUpdates();

   boolean getIgnoreChanges();

   Object getCompilation();

   String getAlias();

   String[] getProjectionAliases();

   Class[] getProjectionTypes();

   boolean isAggregate();

   boolean hasGrouping();

   ClassMetaData[] getAccessPathMetaDatas();

   FilterListener getFilterListener(String var1);

   AggregateListener getAggregateListener(String var1);

   Collection getFilterListeners();

   Collection getAggregateListeners();

   Number deleteInMemory(StoreQuery var1, StoreQuery.Executor var2, Object[] var3);

   Number updateInMemory(StoreQuery var1, StoreQuery.Executor var2, Object[] var3);

   Class classForName(String var1, String[] var2);

   void lock();

   void unlock();
}
