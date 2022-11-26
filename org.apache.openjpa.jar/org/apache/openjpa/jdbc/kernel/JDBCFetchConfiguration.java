package org.apache.openjpa.jdbc.kernel;

import java.util.Collection;
import java.util.Set;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.JoinSyntaxes;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.meta.FieldMetaData;

public interface JDBCFetchConfiguration extends FetchConfiguration, EagerFetchModes, LRSSizes, JoinSyntaxes {
   int getEagerFetchMode();

   JDBCFetchConfiguration setEagerFetchMode(int var1);

   int getSubclassFetchMode();

   int getSubclassFetchMode(ClassMapping var1);

   JDBCFetchConfiguration setSubclassFetchMode(int var1);

   int getResultSetType();

   JDBCFetchConfiguration setResultSetType(int var1);

   int getFetchDirection();

   JDBCFetchConfiguration setFetchDirection(int var1);

   int getLRSSize();

   JDBCFetchConfiguration setLRSSize(int var1);

   int getJoinSyntax();

   JDBCFetchConfiguration setJoinSyntax(int var1);

   Set getJoins();

   boolean hasJoin(String var1);

   JDBCFetchConfiguration addJoin(String var1);

   JDBCFetchConfiguration addJoins(Collection var1);

   JDBCFetchConfiguration removeJoin(String var1);

   JDBCFetchConfiguration removeJoins(Collection var1);

   JDBCFetchConfiguration clearJoins();

   int getIsolation();

   JDBCFetchConfiguration setIsolation(int var1);

   JDBCFetchConfiguration traverseJDBC(FieldMetaData var1);

   Set getFetchInnerJoins();

   boolean hasFetchInnerJoin(String var1);

   JDBCFetchConfiguration addFetchInnerJoin(String var1);

   JDBCFetchConfiguration addFetchInnerJoins(Collection var1);
}
