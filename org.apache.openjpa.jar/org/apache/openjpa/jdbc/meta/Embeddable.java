package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface Embeddable {
   Object UNSUPPORTED = new Object();

   Column[] getColumns();

   ColumnIO getColumnIO();

   Object[] getResultArguments();

   Object toEmbeddedDataStoreValue(Object var1, JDBCStore var2);

   Object toEmbeddedObjectValue(Object var1);

   void loadEmbedded(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Object var4) throws SQLException;
}
