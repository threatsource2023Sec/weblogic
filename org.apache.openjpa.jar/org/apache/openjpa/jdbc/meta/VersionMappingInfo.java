package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.meta.strats.SuperclassVersionStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Table;

public class VersionMappingInfo extends MappingInfo {
   public Column[] getColumns(Version version, Column[] tmplates, boolean adapt) {
      Table table = version.getClassMapping().getTable();
      version.getMappingRepository().getMappingDefaults().populateColumns(version, table, tmplates);
      return this.createColumns(version, (String)null, tmplates, table, adapt);
   }

   public Index getIndex(Version version, Column[] cols, boolean adapt) {
      Index idx = null;
      if (cols.length > 0) {
         idx = version.getMappingRepository().getMappingDefaults().getIndex(version, cols[0].getTable(), cols);
      }

      return this.createIndex(version, (String)null, idx, cols, adapt);
   }

   public void syncWith(Version version) {
      this.clear(false);
      ClassMapping cls = version.getClassMapping();
      Column[] cols = version.getColumns();
      this.setColumnIO(version.getColumnIO());
      this.syncColumns(version, cols, false);
      this.syncIndex(version, version.getIndex());
      if (version.getStrategy() != null && !(version.getStrategy() instanceof SuperclassVersionStrategy)) {
         String strat = version.getStrategy().getAlias();
         if (!cls.isMapped() && !"none".equals(strat) || cls.isMapped() && cls.getJoinablePCSuperclassMapping() == null) {
            this.setStrategy(strat);
         }

      }
   }
}
