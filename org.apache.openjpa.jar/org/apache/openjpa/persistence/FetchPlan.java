package org.apache.openjpa.persistence;

import java.util.Collection;
import javax.persistence.LockModeType;
import org.apache.openjpa.kernel.FetchConfiguration;

public interface FetchPlan {
   String GROUP_ALL = "all";
   String GROUP_DEFAULT = "default";
   int DEPTH_INFINITE = -1;
   int DEFAULT = -99;

   int getMaxFetchDepth();

   FetchPlan setMaxFetchDepth(int var1);

   int getFetchBatchSize();

   FetchPlan setFetchBatchSize(int var1);

   boolean getQueryResultCacheEnabled();

   FetchPlan setQueryResultCacheEnabled(boolean var1);

   /** @deprecated */
   boolean getQueryResultCache();

   /** @deprecated */
   FetchPlan setQueryResultCache(boolean var1);

   Collection getFetchGroups();

   FetchPlan addFetchGroup(String var1);

   FetchPlan addFetchGroups(String... var1);

   FetchPlan addFetchGroups(Collection var1);

   FetchPlan removeFetchGroup(String var1);

   FetchPlan removeFetchGroups(String... var1);

   FetchPlan removeFetchGroups(Collection var1);

   FetchPlan clearFetchGroups();

   FetchPlan resetFetchGroups();

   Collection getFields();

   boolean hasField(String var1);

   boolean hasField(Class var1, String var2);

   FetchPlan addField(String var1);

   FetchPlan addField(Class var1, String var2);

   FetchPlan addFields(String... var1);

   FetchPlan addFields(Class var1, String... var2);

   FetchPlan addFields(Collection var1);

   FetchPlan addFields(Class var1, Collection var2);

   FetchPlan removeField(String var1);

   FetchPlan removeField(Class var1, String var2);

   FetchPlan removeFields(String... var1);

   FetchPlan removeFields(Class var1, String... var2);

   FetchPlan removeFields(Collection var1);

   FetchPlan removeFields(Class var1, Collection var2);

   FetchPlan clearFields();

   int getLockTimeout();

   FetchPlan setLockTimeout(int var1);

   LockModeType getReadLockMode();

   FetchPlan setReadLockMode(LockModeType var1);

   LockModeType getWriteLockMode();

   FetchPlan setWriteLockMode(LockModeType var1);

   /** @deprecated */
   FetchConfiguration getDelegate();
}
