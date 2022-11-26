package org.apache.openjpa.jdbc.meta.strats;

import java.lang.reflect.Method;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.util.MetaDataException;

public class EnumValueHandler extends AbstractValueHandler {
   private Enum[] _vals = null;
   private boolean _ordinal = false;

   public boolean getStoreOrdinal() {
      return this._ordinal;
   }

   public void setStoreOrdinal(boolean ordinal) {
      this._ordinal = ordinal;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      try {
         Method m = vm.getType().getMethod("values", (Class[])null);
         this._vals = (Enum[])((Enum[])m.invoke((Object)null, (Object[])null));
      } catch (Exception var8) {
         throw (new MetaDataException()).setCause(var8);
      }

      Column col = new Column();
      col.setName(name);
      if (this._ordinal) {
         col.setJavaType(7);
      } else {
         int len = 20;

         for(int i = 0; i < this._vals.length; ++i) {
            len = Math.max(this._vals[i].name().length(), len);
         }

         col.setJavaType(9);
         col.setSize(len);
      }

      return new Column[]{col};
   }

   public boolean isVersionable() {
      return true;
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      if (val == null) {
         return null;
      } else {
         return this._ordinal ? new Integer(((Enum)val).ordinal()) : ((Enum)val).name();
      }
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      if (val == null) {
         return null;
      } else {
         return this._ordinal ? this._vals[((Number)val).intValue()] : Enum.valueOf(vm.getType(), (String)val);
      }
   }
}
