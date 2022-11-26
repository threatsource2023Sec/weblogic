package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.meta.ValueHandler;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;

public class HandlerStrategies {
   private static final Localizer _loc = Localizer.forPackage(HandlerStrategies.class);

   public static Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      ValueMappingInfo vinfo = vm.getValueInfo();
      vinfo.assertNoJoin(vm, true);
      vinfo.assertNoForeignKey(vm, !adapt);
      Column[] cols = vm.getHandler().map(vm, name, io, adapt);
      if (cols.length > 0 && cols[0].getTable() == null) {
         cols = vinfo.getColumns(vm, name, cols, vm.getFieldMapping().getTable(), adapt);
         ColumnIO mappedIO = vinfo.getColumnIO();
         vm.setColumns(cols);
         vm.setColumnIO(mappedIO);
         if (mappedIO != null) {
            for(int i = 0; i < cols.length; ++i) {
               io.setInsertable(i, mappedIO.isInsertable(i, false));
               io.setNullInsertable(i, mappedIO.isInsertable(i, true));
               io.setUpdatable(i, mappedIO.isUpdatable(i, false));
               io.setNullUpdatable(i, mappedIO.isUpdatable(i, true));
            }
         }
      }

      vm.mapConstraints(name, adapt);
      return cols;
   }

   public static void set(ValueMapping vm, Object val, JDBCStore store, Row row, Column[] cols, ColumnIO io, boolean nullNone) throws SQLException {
      if (canSetAny(row, io, cols)) {
         ValueHandler handler = vm.getHandler();
         val = handler.toDataStoreValue(vm, val, store);
         if (val == null) {
            for(int i = 0; i < cols.length; ++i) {
               if (canSet(row, io, i, true)) {
                  set(row, cols[i], (Object)null, handler, nullNone);
               }
            }
         } else if (cols.length == 1) {
            if (canSet(row, io, 0, val == null)) {
               set(row, cols[0], val, handler, nullNone);
            }
         } else {
            Object[] vals = (Object[])((Object[])val);

            for(int i = 0; i < vals.length; ++i) {
               if (canSet(row, io, i, vals[i] == null)) {
                  set(row, cols[i], vals[i], handler, nullNone);
               }
            }
         }

      }
   }

   private static boolean canSet(Row row, ColumnIO io, int i, boolean nullValue) {
      if (row.getAction() == 1) {
         return io.isInsertable(i, nullValue);
      } else {
         return row.getAction() == 0 ? io.isUpdatable(i, nullValue) : true;
      }
   }

   private static boolean canSetAny(Row row, ColumnIO io, Column[] cols) {
      if (row.getAction() == 1) {
         return io.isAnyInsertable(cols, false);
      } else {
         return row.getAction() == 0 ? io.isAnyUpdatable(cols, false) : true;
      }
   }

   private static void set(Row row, Column col, Object val, ValueHandler handler, boolean nullNone) throws SQLException {
      if (val == null) {
         row.setNull(col, nullNone);
      } else if (col.isRelationId() && handler instanceof RelationId) {
         row.setRelationId(col, (OpenJPAStateManager)val, (RelationId)handler);
      } else {
         row.setObject(col, val);
      }

   }

   public static void where(ValueMapping vm, Object val, JDBCStore store, Row row, Column[] cols) throws SQLException {
      if (cols.length != 0) {
         val = toDataStoreValue(vm, val, cols, store);
         if (val == null) {
            for(int i = 0; i < cols.length; ++i) {
               row.whereNull(cols[i]);
            }
         } else if (cols.length == 1) {
            where(row, cols[0], val);
         } else {
            Object[] vals = (Object[])((Object[])val);

            for(int i = 0; i < vals.length; ++i) {
               where(row, cols[i], vals[i]);
            }
         }

      }
   }

   private static void where(Row row, Column col, Object val) throws SQLException {
      if (val == null) {
         row.whereNull(col);
      } else {
         row.whereObject(col, val);
      }

   }

   public static Object loadObject(ValueMapping vm, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins, Column[] cols, boolean objectValueRequiresLoad) throws SQLException {
      if (cols.length == 0) {
         throw new InvalidStateException(_loc.get("cant-project-owned", (Object)vm));
      } else {
         Object val = loadDataStore(vm, res, joins, cols);
         return objectValueRequiresLoad ? vm.getHandler().toObjectValue(vm, val, sm, store, fetch) : vm.getHandler().toObjectValue(vm, val);
      }
   }

   public static Object loadDataStore(ValueMapping vm, Result res, Joins joins, Column[] cols) throws SQLException {
      if (cols.length == 0) {
         return null;
      } else if (cols.length == 1) {
         return res.getObject(cols[0], vm.getHandler().getResultArgument(vm), joins);
      } else {
         Object[] vals = new Object[cols.length];
         Object[] args = (Object[])((Object[])vm.getHandler().getResultArgument(vm));

         for(int i = 0; i < cols.length; ++i) {
            vals[i] = res.getObject(cols[i], args == null ? null : args[i], joins);
         }

         return vals;
      }
   }

   public static Object toDataStoreValue(ValueMapping vm, Object val, Column[] cols, JDBCStore store) {
      ValueHandler handler = vm.getHandler();
      val = handler.toDataStoreValue(vm, val, store);
      if (val == null) {
         return cols.length > 1 ? new Object[cols.length] : null;
      } else {
         for(int i = 0; i < cols.length; ++i) {
            if (cols[i].isRelationId()) {
               if (!(handler instanceof RelationId)) {
                  break;
               }

               if (cols.length == 1) {
                  val = ((RelationId)handler).toRelationDataStoreValue((OpenJPAStateManager)val, cols[i]);
               } else {
                  Object[] vals = (Object[])((Object[])val);
                  vals[i] = ((RelationId)handler).toRelationDataStoreValue((OpenJPAStateManager)vals[i], cols[i]);
               }
            }
         }

         return val;
      }
   }

   public static void assertJoinable(ValueMapping vm) {
      ClassMapping rel = vm.getTypeMapping();
      if (rel != null && (rel.getTable() == null || !rel.getTable().equals(vm.getFieldMapping().getTable()))) {
         throw RelationStrategies.unjoinable(vm);
      }
   }
}
