package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;

public class ByteArrayValueHandler extends AbstractValueHandler {
   private static final ByteArrayValueHandler _instance = new ByteArrayValueHandler();

   public static ByteArrayValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(1004);
      col.setSize(-1);
      return new Column[]{col};
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      return PrimitiveWrapperArrays.toByteArray(val);
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      return PrimitiveWrapperArrays.toObjectValue(vm, (byte[])((byte[])val));
   }
}
