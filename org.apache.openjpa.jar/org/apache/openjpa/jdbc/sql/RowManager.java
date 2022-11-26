package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface RowManager {
   Row getRow(Table var1, int var2, OpenJPAStateManager var3, boolean var4);

   Row getSecondaryRow(Table var1, int var2);

   void flushSecondaryRow(Row var1) throws SQLException;

   Row getAllRows(Table var1, int var2);

   void flushAllRows(Row var1) throws SQLException;
}
