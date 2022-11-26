package org.apache.openjpa.jdbc.meta.strats;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class MaxEmbeddedClobFieldStrategy extends MaxEmbeddedLobFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(MaxEmbeddedClobFieldStrategy.class);
   private int _maxSize = 0;

   protected int getExpectedJavaType() {
      return 9;
   }

   protected void update(OpenJPAStateManager sm, Row row) throws SQLException {
      String s = sm.fetchString(this.field.getIndex());
      if (s != null && s.length() <= this._maxSize) {
         row.setString(this.field.getColumns()[0], s);
      } else {
         row.setNull(this.field.getColumns()[0], true);
      }

   }

   protected Boolean isCustom(OpenJPAStateManager sm, JDBCStore store) {
      String s = sm.fetchString(this.field.getIndex());
      return s != null && s.length() > this._maxSize ? null : Boolean.FALSE;
   }

   protected void putData(OpenJPAStateManager sm, ResultSet rs, DBDictionary dict) throws SQLException {
      Object clob = rs.getClob(1);
      dict.putString(clob, sm.fetchString(this.field.getIndex()));
   }

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 9) {
         throw new MetaDataException(_loc.get("not-clobstring", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }

   public void initialize() {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      this._maxSize = dict.maxEmbeddedClobSize;
   }
}
