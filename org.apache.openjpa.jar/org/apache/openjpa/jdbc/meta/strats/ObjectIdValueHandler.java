package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.ObjectIdStateManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.InternalException;

public class ObjectIdValueHandler extends EmbedValueHandler {
   private Object[] _args = null;

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      List cols = new ArrayList();
      List args = new ArrayList();
      super.map(vm, name, io, adapt, cols, args);
      vm.setColumns((Column[])((Column[])cols.toArray(new Column[cols.size()])));
      this._args = args.toArray();
      return vm.getColumns();
   }

   public Object getResultArgument(ValueMapping vm) {
      return this._args;
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      OpenJPAStateManager sm = val == null ? null : new ObjectIdStateManager(val, (OpenJPAStateManager)null, vm);
      Column[] cols = vm.getColumns();
      Object rval = null;
      if (cols.length > 1) {
         rval = new Object[cols.length];
      }

      return super.toDataStoreValue(sm, vm, store, cols, rval, 0);
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      if (val == null) {
         return null;
      } else {
         OpenJPAStateManager sm = new ObjectIdStateManager((Object)null, (OpenJPAStateManager)null, vm);

         try {
            super.toObjectValue(sm, vm, val, (JDBCStore)null, (JDBCFetchConfiguration)null, vm.getColumns(), 0);
         } catch (SQLException var5) {
            throw new InternalException(var5);
         }

         return sm.getManagedInstance();
      }
   }
}
