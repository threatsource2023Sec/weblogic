package kodo.jdo.jdbc;

import java.util.Collection;
import kodo.jdo.KodoFetchPlan;
import org.apache.openjpa.jdbc.kernel.EagerFetchModes;
import org.apache.openjpa.jdbc.kernel.LRSSizes;
import org.apache.openjpa.jdbc.sql.JoinSyntaxes;

public interface JDBCFetchPlan extends KodoFetchPlan, EagerFetchModes, LRSSizes, JoinSyntaxes {
   int getEagerFetchMode();

   JDBCFetchPlan setEagerFetchMode(int var1);

   int getSubclassFetchMode();

   JDBCFetchPlan setSubclassFetchMode(int var1);

   int getResultSetType();

   JDBCFetchPlan setResultSetType(int var1);

   int getFetchDirection();

   JDBCFetchPlan setFetchDirection(int var1);

   int getLRSSize();

   JDBCFetchPlan setLRSSize(int var1);

   int getJoinSyntax();

   JDBCFetchPlan setJoinSyntax(int var1);

   Collection getJoins();

   boolean hasJoin(Class var1, String var2);

   JDBCFetchPlan addJoin(String var1);

   JDBCFetchPlan addJoin(Class var1, String var2);

   JDBCFetchPlan setJoins(String[] var1);

   JDBCFetchPlan setJoins(Class var1, String[] var2);

   JDBCFetchPlan setJoins(Collection var1);

   JDBCFetchPlan setJoins(Class var1, Collection var2);

   JDBCFetchPlan removeJoin(String var1);

   JDBCFetchPlan removeJoin(Class var1, String var2);

   JDBCFetchPlan clearJoins();
}
