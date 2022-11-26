package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;

public class ImmutableValueHandler extends AbstractValueHandler {
   private static final ImmutableValueHandler _instance = new ImmutableValueHandler();

   public static ImmutableValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      if (vm.getTypeCode() == 14) {
         col.setJavaType(JavaSQLTypes.getDateTypeCode(vm.getType()));
      } else {
         col.setJavaType(vm.getTypeCode());
      }

      return new Column[]{col};
   }

   public boolean isVersionable(ValueMapping vm) {
      switch (vm.getTypeCode()) {
         case 0:
         case 1:
         case 2:
         case 5:
         case 6:
         case 7:
         case 9:
         case 14:
         case 16:
         case 17:
         case 18:
         case 21:
         case 22:
         case 23:
         case 25:
         case 26:
            return true;
         case 3:
         case 4:
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 15:
         case 19:
         case 20:
         case 24:
         default:
            return false;
      }
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      if (val != null) {
         return val;
      } else {
         FieldMapping field = vm.getFieldMapping();
         if (field.getNullValue() != 1) {
            return null;
         } else {
            Column[] cols = vm.getColumns();
            return cols[0].getDefaultString() != null ? null : JavaSQLTypes.getEmptyValue(vm.getTypeCode());
         }
      }
   }
}
