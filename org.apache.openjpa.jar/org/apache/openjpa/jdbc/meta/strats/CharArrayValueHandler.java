package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;

public class CharArrayValueHandler extends AbstractValueHandler {
   private static final CharArrayValueHandler _instance = new CharArrayValueHandler();

   public static CharArrayValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(9);
      return new Column[]{col};
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      return val == null ? null : String.valueOf(PrimitiveWrapperArrays.toCharArray(val));
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      if (val == null) {
         return null;
      } else {
         char[] array = ((String)val).toCharArray();
         return PrimitiveWrapperArrays.toObjectValue(vm, array);
      }
   }
}
