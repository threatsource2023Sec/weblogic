package org.apache.openjpa.jdbc.meta.strats;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class MaxEmbeddedByteArrayFieldStrategy extends MaxEmbeddedLobFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(MaxEmbeddedByteArrayFieldStrategy.class);
   private int _maxSize = 0;

   protected int getExpectedJavaType() {
      return 1004;
   }

   protected void update(OpenJPAStateManager sm, Row row) throws SQLException {
      byte[] b = PrimitiveWrapperArrays.toByteArray(sm.fetchObject(this.field.getIndex()));
      if (b != null && b.length <= this._maxSize) {
         row.setBytes(this.field.getColumns()[0], b);
      } else {
         row.setBytes(this.field.getColumns()[0], (byte[])null);
      }

   }

   protected Boolean isCustom(OpenJPAStateManager sm, JDBCStore store) {
      Object val = sm.fetchObject(this.field.getIndex());
      return val != null && Array.getLength(val) > this._maxSize ? null : Boolean.FALSE;
   }

   protected void putData(OpenJPAStateManager sm, ResultSet rs, DBDictionary dict) throws SQLException {
      Object blob = rs.getBlob(1);
      dict.putBytes(blob, PrimitiveWrapperArrays.toByteArray(sm.fetchObject(this.field.getIndex())));
   }

   protected Object load(Column col, Result res, Joins joins) throws SQLException {
      return PrimitiveWrapperArrays.toObjectValue(this.field, (byte[])((byte[])res.getBytes(col, joins)));
   }

   public void map(boolean adapt) {
      if (this.field.getType() != byte[].class && this.field.getType() != Byte[].class) {
         throw new MetaDataException(_loc.get("not-bytes", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }

   public void initialize() {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      this._maxSize = dict.maxEmbeddedBlobSize;
   }
}
