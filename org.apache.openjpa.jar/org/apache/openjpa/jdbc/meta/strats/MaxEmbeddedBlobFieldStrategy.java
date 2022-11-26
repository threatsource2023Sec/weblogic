package org.apache.openjpa.jdbc.meta.strats;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class MaxEmbeddedBlobFieldStrategy extends MaxEmbeddedLobFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(MaxEmbeddedBlobFieldStrategy.class);
   private int _maxSize = 0;

   protected int getExpectedJavaType() {
      return 8;
   }

   protected void update(OpenJPAStateManager sm, Row row) throws SQLException {
      byte[] b = (byte[])((byte[])sm.getImplData(this.field.getIndex()));
      if (b != null && b.length <= this._maxSize) {
         sm.setImplData(this.field.getIndex(), (Object)null);
         DBDictionary.SerializedData dat = new DBDictionary.SerializedData(b);
         row.setObject(this.field.getColumns()[0], dat);
      } else {
         row.setNull(this.field.getColumns()[0], true);
      }

   }

   protected Boolean isCustom(OpenJPAStateManager sm, JDBCStore store) {
      byte[] b = (byte[])((byte[])sm.getImplData(this.field.getIndex()));
      if (b == null) {
         Object o = sm.fetch(this.field.getIndex());
         if (o == null) {
            return Boolean.FALSE;
         }

         DBDictionary dict = this.field.getMappingRepository().getDBDictionary();

         try {
            b = dict.serialize(o, store);
         } catch (SQLException var7) {
            throw SQLExceptions.getStore(var7, dict);
         }

         sm.setImplData(this.field.getIndex(), b);
      }

      return b.length > this._maxSize ? null : Boolean.FALSE;
   }

   protected void putData(OpenJPAStateManager sm, ResultSet rs, DBDictionary dict) throws SQLException {
      byte[] b = (byte[])((byte[])sm.setImplData(this.field.getIndex(), (Object)null));
      Object blob = rs.getBlob(1);
      dict.putBytes(blob, b);
   }

   public void map(boolean adapt) {
      if (!this.field.isSerialized()) {
         throw new MetaDataException(_loc.get("not-serialized", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }

   public void initialize() {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      this._maxSize = dict.maxEmbeddedBlobSize;
      this.field.setUsesImplData(Boolean.TRUE);
   }
}
