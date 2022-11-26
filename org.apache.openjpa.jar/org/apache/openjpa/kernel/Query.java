package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;

public interface Query extends Serializable, QueryContext, QueryOperations, QueryFlushModes {
   Broker getBroker();

   boolean setQuery(Object var1);

   void setIgnoreChanges(boolean var1);

   void addFilterListener(FilterListener var1);

   void removeFilterListener(FilterListener var1);

   void addAggregateListener(AggregateListener var1);

   void removeAggregateListener(AggregateListener var1);

   Extent getCandidateExtent();

   void setCandidateExtent(Extent var1);

   void setCandidateCollection(Collection var1);

   void compile();

   Object execute();

   Object execute(Map var1);

   Object execute(Object[] var1);

   long deleteAll();

   long deleteAll(Object[] var1);

   long deleteAll(Map var1);

   long updateAll();

   long updateAll(Object[] var1);

   long updateAll(Map var1);

   void closeAll();

   void closeResources();

   String[] getDataStoreActions(Map var1);

   void assertOpen();

   void assertNotReadOnly();

   void assertNotSerialized();
}
