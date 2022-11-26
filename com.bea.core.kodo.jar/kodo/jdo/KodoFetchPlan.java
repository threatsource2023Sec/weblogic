package kodo.jdo;

import java.util.Collection;
import java.util.Set;
import javax.jdo.FetchPlan;
import org.apache.openjpa.kernel.LockLevels;
import org.apache.openjpa.kernel.QueryFlushModes;

public interface KodoFetchPlan extends FetchPlan, LockLevels, QueryFlushModes {
   int DEFAULT_VALUE = -99;
   int DETACH_ALL_FIELDS = 8;

   boolean getQueryResultCache();

   KodoFetchPlan setQueryResultCache(boolean var1);

   int getFlushBeforeQueries();

   KodoFetchPlan setFlushBeforeQueries(int var1);

   KodoFetchPlan resetGroups();

   Set getFields();

   boolean hasField(Class var1, String var2);

   KodoFetchPlan addField(String var1);

   KodoFetchPlan addField(Class var1, String var2);

   KodoFetchPlan setFields(String[] var1);

   KodoFetchPlan setFields(Class var1, String[] var2);

   KodoFetchPlan setFields(Collection var1);

   KodoFetchPlan setFields(Class var1, Collection var2);

   KodoFetchPlan removeField(String var1);

   KodoFetchPlan removeField(Class var1, String var2);

   KodoFetchPlan clearFields();

   int getLockTimeout();

   KodoFetchPlan setLockTimeout(int var1);

   int getReadLockLevel();

   KodoFetchPlan setReadLockLevel(int var1);

   int getWriteLockLevel();

   KodoFetchPlan setWriteLockLevel(int var1);
}
