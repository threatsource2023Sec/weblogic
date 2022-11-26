package org.apache.openjpa.jdbc.meta;

import java.util.List;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class ValueMappingInfo extends MappingInfo {
   private static final Localizer _loc = Localizer.forPackage(ValueMappingInfo.class);
   private boolean _criteria = false;
   private boolean _canNull = true;

   public boolean getUseClassCriteria() {
      return this._criteria;
   }

   public void setUseClassCriteria(boolean criteria) {
      this._criteria = criteria;
   }

   public boolean canIndicateNull() {
      return this._canNull;
   }

   public void setCanIndicateNull(boolean ind) {
      this._canNull = ind;
   }

   public ForeignKey getTypeJoin(final ValueMapping val, final String name, boolean inversable, boolean adapt) {
      ClassMapping rel = val.getTypeMapping();
      if (rel == null) {
         return null;
      } else {
         MappingInfo.ForeignKeyDefaults def = new MappingInfo.ForeignKeyDefaults() {
            public ForeignKey get(Table local, Table foreign, boolean inverse) {
               return val.getMappingRepository().getMappingDefaults().getForeignKey(val, name, local, foreign, inverse);
            }

            public void populate(Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
               val.getMappingRepository().getMappingDefaults().populateForeignKeyColumn(val, name, local, foreign, col, target, inverse, pos, cols);
            }
         };
         return this.createForeignKey(val, (String)null, this.getColumns(), def, val.getFieldMapping().getTable(), val.getFieldMapping().getDefiningMapping(), rel, inversable, adapt);
      }
   }

   public ForeignKey getInverseTypeJoin(final ValueMapping val, final String name, boolean adapt) {
      ClassMapping rel = val.getTypeMapping();
      if (rel != null && rel.getTable() != null) {
         MappingInfo.ForeignKeyDefaults def = new MappingInfo.ForeignKeyDefaults() {
            public ForeignKey get(Table local, Table foreign, boolean inverse) {
               return val.getMappingRepository().getMappingDefaults().getForeignKey(val, name, local, foreign, !inverse);
            }

            public void populate(Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
               val.getMappingRepository().getMappingDefaults().populateForeignKeyColumn(val, name, local, foreign, col, target, !inverse, pos, cols);
            }
         };
         return this.createForeignKey(val, (String)null, this.getColumns(), def, rel.getTable(), rel, val.getFieldMapping().getDefiningMapping(), false, adapt);
      } else {
         return null;
      }
   }

   public Column[] getColumns(ValueMapping val, String name, Column[] tmplates, Table table, boolean adapt) {
      this.orderColumnsByTargetField(val, tmplates, adapt);
      val.getMappingRepository().getMappingDefaults().populateColumns(val, name, table, tmplates);
      return this.createColumns(val, (String)null, tmplates, table, adapt);
   }

   private void orderColumnsByTargetField(ValueMapping val, Column[] tmplates, boolean adapt) {
      if (tmplates.length >= 2 && tmplates[0].getTargetField() != null) {
         List cols = this.getColumns();
         if (!cols.isEmpty() && cols.size() == tmplates.length) {
            int pos = false;
            Column cur = (Column)cols.get(0);

            for(int i = 0; i < cols.size(); ++i) {
               if (cur.getTargetField() == null) {
                  throw new MetaDataException(_loc.get("no-targetfield", (Object)val));
               }

               int pos = this.findTargetField(tmplates, cur.getTargetField());
               if (pos == -1) {
                  throw new MetaDataException(_loc.get("bad-targetfield", val, cur.getTargetField()));
               }

               Column next = (Column)cols.get(pos);
               cols.set(pos, cur);
               cur = next;
            }

         }
      }
   }

   public int findTargetField(Column[] tmplates, String target) {
      for(int i = 0; i < tmplates.length; ++i) {
         if (target.equals(tmplates[i].getTargetField())) {
            return i;
         }
      }

      return -1;
   }

   public Unique getUnique(ValueMapping val, String name, boolean adapt) {
      Column[] cols = val.getColumns();
      if (cols.length == 0) {
         return null;
      } else {
         Unique unq = val.getMappingRepository().getMappingDefaults().getUnique(val, name, cols[0].getTable(), cols);
         return this.createUnique(val, (String)null, unq, cols, adapt);
      }
   }

   public Index getIndex(ValueMapping val, String name, boolean adapt) {
      Column[] cols = val.getColumns();
      if (cols.length == 0) {
         return null;
      } else {
         Index idx = val.getMappingRepository().getMappingDefaults().getIndex(val, name, cols[0].getTable(), cols);
         return this.createIndex(val, (String)null, idx, cols, adapt);
      }
   }

   public Column getNullIndicatorColumn(ValueMapping val, String name, Table table, boolean adapt) {
      this.setColumnIO((ColumnIO)null);
      if (!this._canNull) {
         return null;
      } else {
         List cols = this.getColumns();
         Column given = cols.isEmpty() ? null : (Column)cols.get(0);
         MappingDefaults def = val.getMappingRepository().getMappingDefaults();
         if (given == null && !adapt && !def.defaultMissingInfo()) {
            return null;
         } else {
            Column tmplate = new Column();
            tmplate.setName(name + "_null");
            tmplate.setJavaType(5);
            if (!def.populateNullIndicatorColumns(val, name, table, new Column[]{tmplate}) && given == null) {
               return null;
            } else {
               if (given != null && (given.getFlag(2) || given.getFlag(4))) {
                  ColumnIO io = new ColumnIO();
                  io.setInsertable(0, !given.getFlag(2));
                  io.setUpdatable(0, !given.getFlag(4));
                  this.setColumnIO(io);
               }

               if (given != null && given.getName() != null) {
                  ClassMapping embed = val.getEmbeddedMapping();
                  FieldMapping efm = embed == null ? null : embed.getFieldMapping(given.getName());
                  if (efm != null && efm.getColumns().length > 0) {
                     given.setName(efm.getColumns()[0].getName());
                  }
               }

               boolean compat = given == null || given.getName() == null || table == null || !table.isNameTaken(given.getName());
               return mergeColumn(val, "null-ind", tmplate, compat, given, table, adapt, def.defaultMissingInfo());
            }
         }
      }
   }

   public void syncWith(ValueMapping val) {
      this.clear(false);
      this._criteria = val.getUseClassCriteria();
      this.setColumnIO(val.getColumnIO());
      if (val.getForeignKey() != null && val.getTypeMapping() != null && val.getTypeMapping().getTable() != null) {
         FieldMapping fm = val.getFieldMapping();
         Table local = fm.getJoinForeignKey() != null ? fm.getTable() : fm.getDefiningMapping().getTable();
         Table foreign;
         if (val.getJoinDirection() == 2) {
            foreign = local;
            local = val.getTypeMapping().getTable();
            this.setJoinDirection(1);
         } else {
            foreign = val.getTypeMapping().getTable();
            this.setJoinDirection(val.getJoinDirection() == 0 ? 1 : 2);
         }

         this.syncForeignKey(val, val.getForeignKey(), local, foreign);
      } else {
         this.syncColumns(val, val.getColumns(), false);
      }

      this.syncIndex(val, val.getValueIndex());
      this.syncUnique(val, val.getValueUnique());
      if (val.getHandler() != null) {
         ValueHandler def = val.getFieldMapping().getMappingRepository().defaultHandler(val);
         if (def == null || val.getHandler().getClass() != def.getClass()) {
            this.setStrategy(val.getHandler().getClass().getName());
         }
      }

   }

   protected void clear(boolean canFlags) {
      super.clear(canFlags);
      if (canFlags) {
         this._criteria = false;
         this._canNull = true;
      }

   }

   public void copy(MappingInfo info) {
      super.copy(info);
      if (info instanceof ValueMappingInfo) {
         ValueMappingInfo vinfo = (ValueMappingInfo)info;
         if (!this._criteria) {
            this._criteria = vinfo.getUseClassCriteria();
         }

         if (this._canNull) {
            this._canNull = vinfo.canIndicateNull();
         }

      }
   }
}
