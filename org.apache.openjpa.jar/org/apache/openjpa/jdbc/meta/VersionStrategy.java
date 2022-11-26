package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface VersionStrategy extends Strategy {
   void setVersion(Version var1);

   boolean select(Select var1, ClassMapping var2);

   void load(OpenJPAStateManager var1, JDBCStore var2, Result var3) throws SQLException;

   void afterLoad(OpenJPAStateManager var1, JDBCStore var2);

   boolean checkVersion(OpenJPAStateManager var1, JDBCStore var2, boolean var3) throws SQLException;

   int compareVersion(Object var1, Object var2);

   Map getBulkUpdateValues();
}
