package org.apache.openjpa.jdbc.meta.strats;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.Sized;
import org.apache.openjpa.util.StoreException;

public class CharArrayStreamValueHandler extends AbstractValueHandler {
   private static final CharArrayStreamValueHandler _instance = new CharArrayStreamValueHandler();

   public static CharArrayStreamValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(1005);
      col.setSize(-1);
      return new Column[]{col};
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      if (val == null) {
         return null;
      } else {
         char[] chars = PrimitiveWrapperArrays.toCharArray(val);
         return new Sized(new CharArrayReader(chars), chars.length);
      }
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      if (val == null) {
         return null;
      } else {
         Reader reader = (Reader)val;
         CharArrayWriter writer = new CharArrayWriter();

         int c;
         try {
            while((c = reader.read()) != -1) {
               writer.write(c);
            }
         } catch (IOException var6) {
            throw new StoreException(var6);
         }

         return PrimitiveWrapperArrays.toObjectValue(vm, writer.toCharArray());
      }
   }
}
