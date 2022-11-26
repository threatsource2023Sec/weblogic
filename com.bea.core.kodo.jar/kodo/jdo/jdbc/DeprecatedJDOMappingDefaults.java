package kodo.jdo.jdbc;

import java.util.List;
import kodo.jdbc.meta.KodoClassMapping;
import kodo.jdbc.meta.LockGroup;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingDefaultsImpl;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;

public class DeprecatedJDOMappingDefaults extends MappingDefaultsImpl {
   public DeprecatedJDOMappingDefaults() {
      this.setDefaultMissingInfo(false);
      this.setJoinForeignKeyDeleteAction(1);
      this.setForeignKeyDeleteAction(1);
      this.setDeferConstraints(false);
      this.setIndexLogicalForeignKeys(true);
      this.setIndexDiscriminator(true);
      this.setIndexVersion(true);
      this.setOrderLists(true);
      this.setAddNullIndicator(true);
      this.setDataStoreIdColumnName("jdoId");
      this.setDiscriminatorColumnName("jdoClass");
   }

   public void populateColumns(Version vers, Table table, Column[] cols) {
      LockGroup[] lgs = ((KodoClassMapping)vers.getClassMapping()).getLockGroups();
      if ((cols.length == 1 || cols.length == lgs.length) && this.getVersionColumnName() == null) {
         for(int i = 0; i < cols.length; ++i) {
            if (lgs[i].isDefault()) {
               cols[i].setName("jdoVersion");
            } else {
               cols[i].setName(lgs[i].getName() + "Version");
            }

            this.correctName(table, cols[i]);
         }
      } else {
         super.populateColumns(vers, table, cols);
      }

   }

   public void populateColumns(Discriminator disc, Table table, Column[] cols) {
      if (cols.length == 1 && this.getDiscriminatorColumnName() == null) {
         cols[0].setName("jdoClass");
         this.correctName(table, cols[0]);
      } else {
         super.populateColumns(disc, table, cols);
      }

   }

   public void populateForeignKeyColumn(ValueMapping vm, String name, Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
      if (!(target instanceof Column)) {
         super.populateForeignKeyColumn(vm, name, local, foreign, col, target, inverse, pos, cols);
      } else {
         Column tcol = (Column)target;
         if (vm == vm.getFieldMapping().getKey()) {
            col.setName(tcol.getName() + "_key");
         } else if (vm == vm.getFieldMapping().getElement() && vm.getTypeCode() == 13) {
            col.setName(tcol.getName() + "_value");
         } else {
            col.setName(vm.getFieldMapping().getName() + "_" + tcol.getName());
         }

         this.correctName(local, col);
      }

   }

   public boolean populateOrderColumns(FieldMapping fm, Table table, Column[] cols) {
      if (cols.length == 1 && this.getOrderColumnName() == null) {
         cols[0].setName(fm.getName() + "_order");
         this.correctName(table, cols[0]);
         return this.getOrderLists() && (11 == fm.getTypeCode() || List.class.isAssignableFrom(fm.getType()));
      } else {
         return super.populateOrderColumns(fm, table, cols);
      }
   }

   public boolean populateNullIndicatorColumns(ValueMapping vm, String name, Table table, Column[] cols) {
      if (cols.length == 1 && this.getNullIndicatorColumnName() == null) {
         cols[0].setName(name + "_null");
         this.correctName(table, cols[0]);
         return this.getAddNullIndicator();
      } else {
         return super.populateNullIndicatorColumns(vm, name, table, cols);
      }
   }
}
