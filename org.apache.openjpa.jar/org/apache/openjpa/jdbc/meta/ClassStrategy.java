package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.rop.ResultObjectProvider;

public interface ClassStrategy extends Strategy {
   void setClassMapping(ClassMapping var1);

   boolean isPrimaryKeyObjectId(boolean var1);

   Joins joinSuperclass(Joins var1, boolean var2);

   boolean supportsEagerSelect(Select var1, OpenJPAStateManager var2, JDBCStore var3, ClassMapping var4, JDBCFetchConfiguration var5);

   ResultObjectProvider customLoad(JDBCStore var1, boolean var2, JDBCFetchConfiguration var3, long var4, long var6) throws SQLException;

   boolean customLoad(OpenJPAStateManager var1, JDBCStore var2, PCState var3, JDBCFetchConfiguration var4) throws SQLException, ClassNotFoundException;

   boolean customLoad(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4) throws SQLException;
}
