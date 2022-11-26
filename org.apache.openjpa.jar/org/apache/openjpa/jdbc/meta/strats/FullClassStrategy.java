package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class FullClassStrategy extends AbstractClassStrategy {
   public static final String ALIAS = "full";
   private static final Localizer _loc = Localizer.forPackage(FullClassStrategy.class);

   public String getAlias() {
      return "full";
   }

   public void map(boolean adapt) {
      if (this.cls.getEmbeddingMetaData() != null) {
         throw new MetaDataException(_loc.get("not-full", (Object)this.cls));
      } else {
         ClassMapping sup = this.cls.getMappedPCSuperclassMapping();
         ClassMappingInfo info = this.cls.getMappingInfo();
         if (sup != null && info.isJoinedSubclass()) {
            throw new MetaDataException(_loc.get("not-full", (Object)this.cls));
         } else {
            info.assertNoJoin(this.cls, true);
            info.assertNoForeignKey(this.cls, !adapt);
            info.assertNoIndex(this.cls, false);
            info.assertNoUnique(this.cls, false);
            Table table = info.getTable(this.cls, adapt);
            Column[] pkCols = null;
            int var10000 = this.cls.getIdentityType();
            ClassMapping var10001 = this.cls;
            if (var10000 == 1) {
               Column id = new Column();
               id.setName("id");
               id.setJavaType(6);
               id.setComment("datastore id");
               if (this.cls.getIdentityStrategy() == 3) {
                  id.setAutoAssigned(true);
               }

               id.setNotNull(true);
               pkCols = info.getDataStoreIdColumns(this.cls, new Column[]{id}, table, adapt);
               this.cls.setPrimaryKeyColumns(pkCols);
               this.cls.setColumnIO(info.getColumnIO());
            }

            this.cls.setTable(table);
            PrimaryKey pk = table.getPrimaryKey();
            if (pk == null) {
               String pkname = null;
               if (adapt) {
                  pkname = this.cls.getMappingRepository().getMappingDefaults().getPrimaryKeyName(this.cls, table);
               }

               pk = table.addPrimaryKey(pkname);
               pk.setLogical(!adapt);
               if (pkCols != null) {
                  pk.setColumns(pkCols);
               }
            }

            if (this.cls.getIdentityType() == 1) {
               this.cls.setJoinable(this.cls.getPrimaryKeyColumns()[0], new IdentityJoinable(this.cls));
            }

         }
      }
   }

   public boolean supportsEagerSelect(Select sel, OpenJPAStateManager sm, JDBCStore store, ClassMapping base, JDBCFetchConfiguration fetch) {
      return false;
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 1, sm, true);
      int var10000 = this.cls.getIdentityType();
      ClassMapping var10001 = this.cls;
      if (var10000 == 1) {
         row.setPrimaryKey(this.cls.getColumnIO(), sm);
      }

   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 0, sm, false);
      if (row != null) {
         row.wherePrimaryKey(sm);
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 2, sm, true);
      row.wherePrimaryKey(sm);
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return true;
   }
}
