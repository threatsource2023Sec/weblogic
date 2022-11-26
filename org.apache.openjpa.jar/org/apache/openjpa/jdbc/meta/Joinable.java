package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface Joinable extends Serializable {
   int getFieldIndex();

   Object getPrimaryKeyValue(Result var1, Column[] var2, ForeignKey var3, JDBCStore var4, Joins var5) throws SQLException;

   Column[] getColumns();

   Object getJoinValue(Object var1, Column var2, JDBCStore var3);

   Object getJoinValue(OpenJPAStateManager var1, Column var2, JDBCStore var3);

   void setAutoAssignedValue(OpenJPAStateManager var1, JDBCStore var2, Column var3, Object var4);
}
