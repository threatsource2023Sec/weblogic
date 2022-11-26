package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;

public interface SelectExecutor {
   JDBCConfiguration getConfiguration();

   SQLBuffer toSelect(boolean var1, JDBCFetchConfiguration var2);

   SQLBuffer toSelectCount();

   boolean getAutoDistinct();

   void setAutoDistinct(boolean var1);

   boolean isDistinct();

   void setDistinct(boolean var1);

   boolean isLRS();

   void setLRS(boolean var1);

   int getExpectedResultCount();

   void setExpectedResultCount(int var1, boolean var2);

   int getJoinSyntax();

   void setJoinSyntax(int var1);

   boolean supportsRandomAccess(boolean var1);

   boolean supportsLocking();

   int getCount(JDBCStore var1) throws SQLException;

   Result execute(JDBCStore var1, JDBCFetchConfiguration var2) throws SQLException;

   Result execute(JDBCStore var1, JDBCFetchConfiguration var2, int var3) throws SQLException;
}
