package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class FlatClassStrategy extends AbstractClassStrategy {
   public static final String ALIAS = "flat";
   private static final Localizer _loc = Localizer.forPackage(FlatClassStrategy.class);

   public String getAlias() {
      return "flat";
   }

   public void map(boolean adapt) {
      ClassMapping sup = this.cls.getMappedPCSuperclassMapping();
      if (sup != null && this.cls.getEmbeddingMetaData() == null) {
         ClassMappingInfo info = this.cls.getMappingInfo();
         info.assertNoSchemaComponents(this.cls, true);
         if (info.getTableName() != null) {
            Table table = info.createTable(this.cls, (MappingInfo.TableDefaults)null, info.getSchemaName(), info.getTableName(), false);
            if (table != sup.getTable()) {
               throw new MetaDataException(_loc.get("flat-table", this.cls, table.getFullName(), sup.getTable().getFullName()));
            }
         }

         this.cls.setTable(sup.getTable());
         this.cls.setPrimaryKeyColumns(sup.getPrimaryKeyColumns());
         this.cls.setColumnIO(sup.getColumnIO());
      } else {
         throw new MetaDataException(_loc.get("not-sub", (Object)this.cls));
      }
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return this.cls.getMappedPCSuperclassMapping().isPrimaryKeyObjectId(hasAll);
   }
}
