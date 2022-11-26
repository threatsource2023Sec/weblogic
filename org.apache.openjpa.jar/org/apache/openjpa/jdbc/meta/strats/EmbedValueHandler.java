package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.List;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.Embeddable;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public abstract class EmbedValueHandler extends AbstractValueHandler {
   private static final Localizer _loc = Localizer.forPackage(EmbedValueHandler.class);

   protected void map(ValueMapping vm, String name, ColumnIO io, boolean adapt, List cols, List args) {
      vm.getEmbeddedMapping().resolve(1 | 2);
      FieldMapping[] fms = vm.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fms.length; ++i) {
         if (fms[i].getManagement() == 3) {
            if (!(fms[i].getStrategy() instanceof Embeddable)) {
               throw new MetaDataException(_loc.get("not-embeddable", vm, fms[i]));
            }

            Column[] curCols = ((Embeddable)fms[i].getStrategy()).getColumns();
            ColumnIO curIO = ((Embeddable)fms[i].getStrategy()).getColumnIO();

            int j;
            for(j = 0; j < curCols.length; ++j) {
               io.setInsertable(cols.size(), curIO.isInsertable(j, false));
               io.setNullInsertable(cols.size(), curIO.isInsertable(j, true));
               io.setUpdatable(cols.size(), curIO.isUpdatable(j, false));
               io.setNullUpdatable(cols.size(), curIO.isUpdatable(j, true));
               cols.add(curCols[j]);
            }

            Object[] curArgs = ((Embeddable)fms[i].getStrategy()).getResultArguments();
            if (curCols.length == 1) {
               args.add(curArgs);
            } else if (curCols.length > 1) {
               for(j = 0; j < curCols.length; ++j) {
                  args.add(curArgs == null ? null : ((Object[])curArgs)[j]);
               }
            }
         }
      }

   }

   protected Object toDataStoreValue(OpenJPAStateManager em, ValueMapping vm, JDBCStore store, Column[] cols, Object rval, int idx) {
      FieldMapping[] fms = vm.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fms.length; ++i) {
         if (fms[i].getManagement() == 3) {
            Embeddable embed = (Embeddable)fms[i].getStrategy();
            Column[] ecols = embed.getColumns();
            if (ecols.length != 0) {
               Object cval = em == null ? null : em.fetch(i);
               cval = embed.toEmbeddedDataStoreValue(cval, store);
               if (cols.length == 1) {
                  rval = cval;
               } else if (ecols.length == 1) {
                  ((Object[])((Object[])rval))[idx++] = cval;
               } else {
                  System.arraycopy(cval, 0, rval, idx, ecols.length);
                  idx += ecols.length;
               }
            }
         }
      }

      return rval;
   }

   protected void toObjectValue(OpenJPAStateManager em, ValueMapping vm, Object val, JDBCStore store, JDBCFetchConfiguration fetch, Column[] cols, int idx) throws SQLException {
      FieldMapping[] fms = vm.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fms.length; ++i) {
         if (fms[i].getManagement() == 3) {
            Embeddable embed = (Embeddable)fms[i].getStrategy();
            Column[] ecols = embed.getColumns();
            Object cval;
            if (ecols.length == 0) {
               cval = null;
            } else if (idx == 0 && ecols.length == cols.length) {
               cval = val;
            } else if (ecols.length == 1) {
               cval = ((Object[])((Object[])val))[idx++];
            } else {
               cval = new Object[ecols.length];
               System.arraycopy(val, idx, cval, 0, ecols.length);
               idx += ecols.length;
            }

            if (store != null) {
               embed.loadEmbedded(em, store, fetch, cval);
            } else {
               cval = embed.toEmbeddedObjectValue(cval);
               em.store(fms[i].getIndex(), cval);
            }
         }
      }

   }
}
