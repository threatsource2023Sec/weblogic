package org.apache.openjpa.persistence.jdbc;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingDefaultsImpl;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.strats.NoneDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.NumberVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.SubclassJoinDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.ValueMapDiscriminatorStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import serp.util.Strings;

public class PersistenceMappingDefaults extends MappingDefaultsImpl {
   private boolean _prependFieldNameToJoinTableInverseJoinColumns = true;

   public PersistenceMappingDefaults() {
      this.setDefaultMissingInfo(true);
      this.setStoreEnumOrdinal(true);
      this.setOrderLists(false);
      this.setAddNullIndicator(false);
      this.setDiscriminatorColumnName("DTYPE");
   }

   public boolean getPrependFieldNameToJoinTableInverseJoinColumns() {
      return this._prependFieldNameToJoinTableInverseJoinColumns;
   }

   public void setPrependFieldNameToJoinTableInverseJoinColumns(boolean val) {
      this._prependFieldNameToJoinTableInverseJoinColumns = val;
   }

   public Object getStrategy(Version vers, boolean adapt) {
      Object strat = super.getStrategy(vers, adapt);
      ClassMapping cls = vers.getClassMapping();
      if (strat == null && cls.getJoinablePCSuperclassMapping() == null && cls.getVersionField() == null) {
         return vers.getMappingInfo().getColumns().isEmpty() ? NoneVersionStrategy.getInstance() : new NumberVersionStrategy();
      } else {
         return strat;
      }
   }

   public Object getStrategy(Discriminator disc, boolean adapt) {
      Object strat = super.getStrategy(disc, adapt);
      ClassMapping cls = disc.getClassMapping();
      if (strat == null && cls.getJoinablePCSuperclassMapping() == null && disc.getMappingInfo().getValue() == null) {
         if (!disc.getMappingInfo().getColumns().isEmpty()) {
            return new ValueMapDiscriminatorStrategy();
         } else {
            ClassMapping base;
            for(base = cls; base.getMappingInfo().getHierarchyStrategy() == null && base.getPCSuperclassMapping() != null; base = base.getPCSuperclassMapping()) {
            }

            Object strat = base.getMappingInfo().getHierarchyStrategy();
            if ("flat".equals(strat)) {
               return new ValueMapDiscriminatorStrategy();
            } else {
               return "vertical".equals(strat) && this.dict.joinSyntax != 1 ? new SubclassJoinDiscriminatorStrategy() : NoneDiscriminatorStrategy.getInstance();
            }
         }
      } else {
         return strat;
      }
   }

   public String getTableName(ClassMapping cls, Schema schema) {
      return cls.getTypeAlias() != null ? cls.getTypeAlias() : Strings.getClassName(cls.getDescribedType()).replace('$', '_');
   }

   public String getTableName(FieldMapping fm, Schema schema) {
      String name = fm.getDefiningMapping().getTable().getName() + "_";
      ClassMapping rel = fm.getElementMapping().getTypeMapping();
      boolean assoc = rel != null && rel.getTable() != null && fm.getTypeCode() != 13;
      if (assoc) {
         name = name + rel.getTable().getName();
      } else {
         name = name + fm.getName();
      }

      return name.replace('$', '_');
   }

   public void populateJoinColumn(FieldMapping fm, Table local, Table foreign, Column col, Object target, int pos, int cols) {
      if (target instanceof Column) {
         FieldMapping[] inverses = fm.getInverseMappings();
         String name;
         if (inverses.length > 0) {
            name = inverses[0].getName();
         } else {
            name = fm.getDefiningMapping().getTypeAlias();
         }

         String targetName = ((Column)target).getName();
         String tempName = null;
         if (name.length() + targetName.length() >= this.dict.maxColumnNameLength) {
            tempName = name.substring(0, this.dict.maxColumnNameLength - targetName.length() - 1);
         }

         if (tempName == null) {
            tempName = name;
         }

         name = tempName + "_" + targetName;
         name = this.dict.getValidColumnName(name, foreign);
         col.setName(name);
      }
   }

   public void populateForeignKeyColumn(ValueMapping vm, String name, Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
      boolean elem = vm == vm.getFieldMapping().getElement() && vm.getFieldMapping().getTypeCode() != 13;
      if (this._prependFieldNameToJoinTableInverseJoinColumns || inverse || !elem) {
         if (target instanceof Column) {
            if (elem) {
               name = vm.getFieldMapping().getName();
            }

            if (this.isRemoveHungarianNotation()) {
               name = this.removeHungarianNotation(name);
            }

            name = name + "_" + ((Column)target).getName();
            name = this.dict.getValidColumnName(name, local, false);
            col.setName(name);
         }

      }
   }

   public void populateColumns(Version vers, Table table, Column[] cols) {
      FieldMapping fm = vers.getClassMapping().getVersionFieldMapping();
      if (fm != null && cols.length == 1) {
         cols[0].setName(fm.getName());
      } else {
         super.populateColumns(vers, table, cols);
      }

   }
}
