package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.FieldMetaData;

public interface FetchConfiguration extends Serializable, Cloneable, LockLevels, QueryFlushModes {
   int DEFAULT = -99;
   int FETCH_NONE = 0;
   int FETCH_LOAD = 1;
   int FETCH_REF = 2;

   StoreContext getContext();

   void setContext(StoreContext var1);

   Object clone();

   void copy(FetchConfiguration var1);

   int getFetchBatchSize();

   FetchConfiguration setFetchBatchSize(int var1);

   int getMaxFetchDepth();

   FetchConfiguration setMaxFetchDepth(int var1);

   boolean getQueryCacheEnabled();

   FetchConfiguration setQueryCacheEnabled(boolean var1);

   int getFlushBeforeQueries();

   FetchConfiguration setFlushBeforeQueries(int var1);

   Set getFetchGroups();

   boolean hasFetchGroup(String var1);

   FetchConfiguration addFetchGroup(String var1);

   FetchConfiguration addFetchGroups(Collection var1);

   FetchConfiguration removeFetchGroup(String var1);

   FetchConfiguration removeFetchGroups(Collection var1);

   FetchConfiguration clearFetchGroups();

   FetchConfiguration resetFetchGroups();

   Set getFields();

   boolean hasField(String var1);

   FetchConfiguration addField(String var1);

   FetchConfiguration addFields(Collection var1);

   FetchConfiguration removeField(String var1);

   FetchConfiguration removeFields(Collection var1);

   FetchConfiguration clearFields();

   int getLockTimeout();

   FetchConfiguration setLockTimeout(int var1);

   int getReadLockLevel();

   FetchConfiguration setReadLockLevel(int var1);

   int getWriteLockLevel();

   FetchConfiguration setWriteLockLevel(int var1);

   ResultList newResultList(ResultObjectProvider var1);

   void setHint(String var1, Object var2);

   Object getHint(String var1);

   Set getRootClasses();

   FetchConfiguration setRootClasses(Collection var1);

   Set getRootInstances();

   FetchConfiguration setRootInstances(Collection var1);

   void lock();

   void unlock();

   int requiresFetch(FieldMetaData var1);

   boolean requiresLoad();

   FetchConfiguration traverse(FieldMetaData var1);
}
