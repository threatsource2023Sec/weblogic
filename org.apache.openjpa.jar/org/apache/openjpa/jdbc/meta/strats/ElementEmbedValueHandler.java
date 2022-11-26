package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.LinkedList;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.Embeddable;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;

public class ElementEmbedValueHandler extends EmbedValueHandler implements RelationId {
   private static final Localizer _loc = Localizer.forPackage(ElementEmbedValueHandler.class);
   private ValueMapping _vm = null;
   private Column[] _cols = null;
   private Object[] _args = null;
   private int _nullIdx = -1;
   private boolean _synthetic = false;

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      LinkedList cols = new LinkedList();
      LinkedList args = new LinkedList();
      super.map(vm, name, io, adapt, cols, args);
      ValueMappingInfo vinfo = vm.getValueInfo();
      Column nullInd = vinfo.getNullIndicatorColumn(vm, name, vm.getFieldMapping().getTable(), adapt);
      if (nullInd != null) {
         vm.setColumns(new Column[]{nullInd});
      }

      if (nullInd != null) {
         this._nullIdx = cols.indexOf(nullInd);
         if (this._nullIdx == -1) {
            cols.addFirst(nullInd);
            args.addFirst((Object)null);
            this._nullIdx = 0;
            this._synthetic = true;
         }
      }

      this._vm = vm;
      this._cols = (Column[])((Column[])cols.toArray(new Column[cols.size()]));
      this._args = args.toArray();
      return this._cols;
   }

   public boolean objectValueRequiresLoad(ValueMapping vm) {
      return true;
   }

   public Object getResultArgument(ValueMapping vm) {
      return this._args;
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      OpenJPAStateManager em = store.getContext().getStateManager(val);
      Object rval = null;
      if (this._cols.length > 1) {
         rval = new Object[this._cols.length];
      }

      int idx = 0;
      if (this._synthetic) {
         Object cval = ((EmbeddedClassStrategy)vm.getEmbeddedMapping().getStrategy()).getNullIndicatorValue(em);
         if (this._cols.length == 1) {
            return cval;
         }

         ((Object[])((Object[])rval))[idx++] = cval;
      }

      return super.toDataStoreValue(em, vm, store, this._cols, rval, idx);
   }

   public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (sm == null) {
         throw new InvalidStateException(_loc.get("cant-project-owned", (Object)vm));
      } else {
         if (this._nullIdx != -1) {
            Object nval;
            if (this._cols.length == 1) {
               nval = val;
            } else {
               nval = ((Object[])((Object[])val))[this._nullIdx];
            }

            if (((EmbeddedClassStrategy)vm.getEmbeddedMapping().getStrategy()).indicatesNull(nval)) {
               return null;
            }
         }

         OpenJPAStateManager em = store.getContext().embed((Object)null, (Object)null, sm, vm);
         int idx = this._synthetic ? 1 : 0;
         super.toObjectValue(em, vm, val, store, fetch, this._cols, idx);
         em.load(fetch);
         return em.getManagedInstance();
      }
   }

   public Object toRelationDataStoreValue(OpenJPAStateManager sm, Column col) {
      return this.toRelationDataStoreValue(sm, col, 0);
   }

   private Object toRelationDataStoreValue(OpenJPAStateManager sm, Column col, int idx) {
      FieldMapping field = this.findField(col, idx);
      if (field == null) {
         throw new InternalException();
      } else if (field.getHandler() instanceof RelationId) {
         return ((RelationId)field.getStrategy()).toRelationDataStoreValue(sm, col);
      } else {
         return field.getStrategy() instanceof RelationId ? ((RelationId)field.getStrategy()).toRelationDataStoreValue(sm, col) : this.toRelationDataStoreValue(sm, col, field.getIndex() + 1);
      }
   }

   private FieldMapping findField(Column col, int idx) {
      FieldMapping[] fms = this._vm.getEmbeddedMapping().getFieldMappings();

      for(int i = idx; i < fms.length; ++i) {
         if (fms[i].getManagement() == 3) {
            Column[] cols = ((Embeddable)fms[i]).getColumns();

            for(int j = 0; j < cols.length; ++j) {
               if (cols[j] == col) {
                  return fms[i];
               }
            }
         }
      }

      return null;
   }
}
