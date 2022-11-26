package org.apache.openjpa.jdbc.meta.strats;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
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

public class MaxEmbeddedCharArrayFieldStrategy extends MaxEmbeddedLobFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(MaxEmbeddedCharArrayFieldStrategy.class);
   private int _maxSize = 0;

   protected int getExpectedJavaType() {
      return 1005;
   }

   protected void update(OpenJPAStateManager sm, Row row) throws SQLException {
      char[] c = PrimitiveWrapperArrays.toCharArray(sm.fetchObject(this.field.getIndex()));
      if (c != null && c.length <= this._maxSize) {
         row.setCharacterStream(this.field.getColumns()[0], new CharArrayReader(c), c.length);
      } else {
         row.setNull(this.field.getColumns()[0], true);
      }

   }

   protected Boolean isCustom(OpenJPAStateManager sm, JDBCStore store) {
      Object val = sm.fetchObject(this.field.getIndex());
      return val != null && Array.getLength(val) > this._maxSize ? null : Boolean.FALSE;
   }

   protected void putData(OpenJPAStateManager sm, ResultSet rs, DBDictionary dict) throws SQLException {
      Object clob = rs.getClob(1);
      dict.putChars(clob, PrimitiveWrapperArrays.toCharArray(sm.fetchObject(this.field.getIndex())));
   }

   protected Object load(Column col, Result res, Joins joins) throws SQLException {
      Reader reader = res.getCharacterStream(col, joins);
      if (reader == null) {
         return null;
      } else {
         try {
            CharArrayWriter writer = new CharArrayWriter();

            int c;
            while((c = reader.read()) != -1) {
               writer.write(c);
            }

            return PrimitiveWrapperArrays.toObjectValue(this.field, (char[])writer.toCharArray());
         } catch (IOException var7) {
            throw new SQLException(var7.toString());
         }
      }
   }

   public void map(boolean adapt) {
      if (this.field.getType() != char[].class && this.field.getType() != Character[].class) {
         throw new MetaDataException(_loc.get("not-chars", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }

   public void initialize() {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      this._maxSize = dict.maxEmbeddedClobSize;
   }
}
