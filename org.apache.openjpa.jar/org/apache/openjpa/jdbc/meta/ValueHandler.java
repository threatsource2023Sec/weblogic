package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface ValueHandler extends Serializable {
   Column[] map(ValueMapping var1, String var2, ColumnIO var3, boolean var4);

   boolean isVersionable(ValueMapping var1);

   boolean objectValueRequiresLoad(ValueMapping var1);

   Object getResultArgument(ValueMapping var1);

   Object toDataStoreValue(ValueMapping var1, Object var2, JDBCStore var3);

   Object toObjectValue(ValueMapping var1, Object var2);

   Object toObjectValue(ValueMapping var1, Object var2, OpenJPAStateManager var3, JDBCStore var4, JDBCFetchConfiguration var5) throws SQLException;
}
