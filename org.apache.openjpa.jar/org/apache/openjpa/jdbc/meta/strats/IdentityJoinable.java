package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.Id;
import serp.util.Numbers;

class IdentityJoinable implements Joinable {
   private final ClassMapping mapping;

   public IdentityJoinable(ClassMapping mapping) {
      this.mapping = mapping;
   }

   public int getFieldIndex() {
      return -1;
   }

   public Object getPrimaryKeyValue(Result res, Column[] cols, ForeignKey fk, JDBCStore store, Joins joins) throws SQLException {
      Column col = cols[0];
      if (fk != null) {
         col = fk.getColumn(col);
      }

      long id = res.getLong(col);
      return id == 0L && res.wasNull() ? null : Numbers.valueOf(id);
   }

   public Column[] getColumns() {
      return this.mapping.getPrimaryKeyColumns();
   }

   public Object getJoinValue(Object val, Column col, JDBCStore store) {
      return val;
   }

   public Object getJoinValue(OpenJPAStateManager sm, Column col, JDBCStore store) {
      Id id = (Id)sm.getObjectId();
      return id == null ? null : id.getIdObject();
   }

   public void setAutoAssignedValue(OpenJPAStateManager sm, JDBCStore store, Column col, Object autogen) {
      long id = ((Number)autogen).longValue();
      sm.setObjectId(store.newDataStoreId(id, (ClassMapping)sm.getMetaData(), true));
   }
}
