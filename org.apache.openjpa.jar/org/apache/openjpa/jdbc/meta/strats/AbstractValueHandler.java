package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueHandler;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public abstract class AbstractValueHandler implements ValueHandler {
   public boolean isVersionable(ValueMapping vm) {
      return false;
   }

   public boolean objectValueRequiresLoad(ValueMapping vm) {
      return false;
   }

   public Object getResultArgument(ValueMapping vm) {
      return null;
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      return val;
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      return val;
   }

   public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      return val;
   }
}
