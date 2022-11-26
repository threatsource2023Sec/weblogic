package org.apache.openjpa.persistence.jdbc;

import java.util.Collection;
import javax.persistence.LockModeType;
import org.apache.openjpa.persistence.FetchPlan;

public interface JDBCFetchPlan extends FetchPlan {
   /** @deprecated */
   int EAGER_NONE = 0;
   /** @deprecated */
   int EAGER_JOIN = 1;
   /** @deprecated */
   int EAGER_PARALLEL = 2;
   /** @deprecated */
   int SIZE_UNKNOWN = 0;
   /** @deprecated */
   int SIZE_LAST = 1;
   /** @deprecated */
   int SIZE_QUERY = 2;
   /** @deprecated */
   int SYNTAX_SQL92 = 0;
   /** @deprecated */
   int SYNTAX_TRADITIONAL = 1;
   /** @deprecated */
   int SYNTAX_DATABASE = 2;

   FetchMode getEagerFetchMode();

   JDBCFetchPlan setEagerFetchMode(FetchMode var1);

   FetchMode getSubclassFetchMode();

   JDBCFetchPlan setSubclassFetchMode(FetchMode var1);

   ResultSetType getResultSetType();

   JDBCFetchPlan setResultSetType(ResultSetType var1);

   FetchDirection getFetchDirection();

   JDBCFetchPlan setFetchDirection(FetchDirection var1);

   LRSSizeAlgorithm getLRSSizeAlgorithm();

   JDBCFetchPlan setLRSSizeAlgorithm(LRSSizeAlgorithm var1);

   JoinSyntax getJoinSyntax();

   JDBCFetchPlan setJoinSyntax(JoinSyntax var1);

   IsolationLevel getIsolation();

   JDBCFetchPlan setIsolation(IsolationLevel var1);

   JDBCFetchPlan addFetchGroup(String var1);

   JDBCFetchPlan addFetchGroups(Collection var1);

   JDBCFetchPlan addFetchGroups(String... var1);

   JDBCFetchPlan addField(Class var1, String var2);

   JDBCFetchPlan addField(String var1);

   JDBCFetchPlan addFields(Class var1, Collection var2);

   JDBCFetchPlan addFields(Class var1, String... var2);

   JDBCFetchPlan addFields(Collection var1);

   JDBCFetchPlan addFields(String... var1);

   JDBCFetchPlan clearFetchGroups();

   JDBCFetchPlan clearFields();

   JDBCFetchPlan removeFetchGroup(String var1);

   JDBCFetchPlan removeFetchGroups(Collection var1);

   JDBCFetchPlan removeFetchGroups(String... var1);

   JDBCFetchPlan removeField(Class var1, String var2);

   JDBCFetchPlan removeField(String var1);

   JDBCFetchPlan removeFields(Class var1, Collection var2);

   JDBCFetchPlan removeFields(Class var1, String... var2);

   JDBCFetchPlan removeFields(String... var1);

   JDBCFetchPlan removeFields(Collection var1);

   JDBCFetchPlan resetFetchGroups();

   JDBCFetchPlan setQueryResultCacheEnabled(boolean var1);

   JDBCFetchPlan setFetchBatchSize(int var1);

   JDBCFetchPlan setLockTimeout(int var1);

   JDBCFetchPlan setMaxFetchDepth(int var1);

   JDBCFetchPlan setReadLockMode(LockModeType var1);

   JDBCFetchPlan setWriteLockMode(LockModeType var1);

   /** @deprecated */
   JDBCFetchPlan setEagerFetchMode(int var1);

   /** @deprecated */
   JDBCFetchPlan setSubclassFetchMode(int var1);

   /** @deprecated */
   JDBCFetchPlan setResultSetType(int var1);

   /** @deprecated */
   JDBCFetchPlan setFetchDirection(int var1);

   /** @deprecated */
   int getLRSSize();

   /** @deprecated */
   JDBCFetchPlan setLRSSize(int var1);

   /** @deprecated */
   JDBCFetchPlan setJoinSyntax(int var1);
}
