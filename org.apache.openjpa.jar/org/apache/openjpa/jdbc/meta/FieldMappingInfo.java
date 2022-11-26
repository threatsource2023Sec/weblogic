package org.apache.openjpa.jdbc.meta;

import java.util.List;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.util.MetaDataException;

public class FieldMappingInfo extends MappingInfo implements Commentable {
   private static final Localizer _loc = Localizer.forPackage(FieldMappingInfo.class);
   private String _tableName = null;
   private boolean _outer = false;
   private Column _orderCol = null;
   private boolean _canOrderCol = true;
   private String[] _comments = null;

   public String getTableName() {
      return this._tableName;
   }

   public void setTableName(String tableName) {
      this._tableName = tableName;
   }

   public boolean isJoinOuter() {
      return this._outer;
   }

   public void setJoinOuter(boolean outer) {
      this._outer = outer;
   }

   public Column getOrderColumn() {
      return this._orderCol;
   }

   public void setOrderColumn(Column order) {
      this._orderCol = order;
   }

   public boolean canOrderColumn() {
      return this._canOrderCol;
   }

   public void setCanOrderColumn(boolean canOrder) {
      this._canOrderCol = canOrder;
   }

   public Table getTable(final FieldMapping field, boolean create, boolean adapt) {
      if (this._tableName == null && !create) {
         return null;
      } else {
         Table table = field.getDefiningMapping().getTable();
         String schemaName = table == null ? null : table.getSchema().getName();
         String tableName = this._tableName;
         if (tableName != null && this.getColumns().isEmpty()) {
            tableName = field.getDefiningMapping().getMappingInfo().getSecondaryTableName(tableName);
         }

         return this.createTable(field, new MappingInfo.TableDefaults() {
            public String get(Schema schema) {
               return field.getMappingRepository().getMappingDefaults().getTableName(field, schema);
            }
         }, schemaName, tableName, adapt);
      }
   }

   public ForeignKey getJoin(final FieldMapping field, Table table, boolean adapt) {
      List cols = this.getColumns();
      if (cols.isEmpty()) {
         cols = field.getDefiningMapping().getMappingInfo().getSecondaryTableJoinColumns(this._tableName);
      }

      MappingInfo.ForeignKeyDefaults def = new MappingInfo.ForeignKeyDefaults() {
         public ForeignKey get(Table local, Table foreign, boolean inverse) {
            return field.getMappingRepository().getMappingDefaults().getJoinForeignKey(field, local, foreign);
         }

         public void populate(Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
            field.getMappingRepository().getMappingDefaults().populateJoinColumn(field, local, foreign, col, target, pos, cols);
         }
      };
      ClassMapping cls = field.getDefiningMapping();
      return this.createForeignKey(field, "join", cols, def, table, cls, cls, false, adapt);
   }

   public Unique getJoinUnique(FieldMapping field, boolean def, boolean adapt) {
      ForeignKey fk = field.getJoinForeignKey();
      if (fk == null) {
         return null;
      } else {
         Unique unq = null;
         if (fk.getColumns().length > 0) {
            unq = field.getMappingRepository().getMappingDefaults().getJoinUnique(field, fk.getTable(), fk.getColumns());
         }

         return this.createUnique(field, "join", unq, fk.getColumns(), adapt);
      }
   }

   public Index getJoinIndex(FieldMapping field, boolean adapt) {
      ForeignKey fk = field.getJoinForeignKey();
      if (fk == null) {
         return null;
      } else {
         Index idx = null;
         if (fk.getColumns().length > 0) {
            idx = field.getMappingRepository().getMappingDefaults().getJoinIndex(field, fk.getTable(), fk.getColumns());
         }

         return this.createIndex(field, "join", idx, fk.getColumns(), adapt);
      }
   }

   public Column getOrderColumn(FieldMapping field, Table table, boolean adapt) {
      if (this._orderCol != null && field.getOrderDeclaration() != null) {
         throw new MetaDataException(_loc.get("order-conflict", (Object)field));
      } else {
         this.setColumnIO((ColumnIO)null);
         if (this._canOrderCol && field.getOrderDeclaration() == null) {
            MappingDefaults def = field.getMappingRepository().getMappingDefaults();
            if (this._orderCol == null && !adapt && !def.defaultMissingInfo()) {
               return null;
            } else {
               Column tmplate = new Column();
               tmplate.setName("ordr");
               tmplate.setJavaType(5);
               if (!def.populateOrderColumns(field, table, new Column[]{tmplate}) && this._orderCol == null) {
                  return null;
               } else {
                  if (this._orderCol != null && (this._orderCol.getFlag(2) || this._orderCol.getFlag(4))) {
                     ColumnIO io = new ColumnIO();
                     io.setInsertable(0, !this._orderCol.getFlag(2));
                     io.setUpdatable(0, !this._orderCol.getFlag(4));
                     this.setColumnIO(io);
                  }

                  return mergeColumn(field, "order", tmplate, true, this._orderCol, table, adapt, def.defaultMissingInfo());
               }
            }
         } else {
            return null;
         }
      }
   }

   public void syncWith(FieldMapping field) {
      this.clear(false);
      if (field.getJoinForeignKey() != null) {
         this._tableName = field.getMappingRepository().getDBDictionary().getFullName(field.getTable(), true);
      }

      ClassMapping def = field.getDefiningMapping();
      this.setColumnIO(field.getJoinColumnIO());
      if (field.getJoinForeignKey() != null && def.getTable() != null) {
         this.syncForeignKey(field, field.getJoinForeignKey(), field.getTable(), def.getTable());
      }

      this._outer = field.isJoinOuter();
      this.syncIndex(field, field.getJoinIndex());
      this.syncUnique(field, field.getJoinUnique());
      this.syncOrderColumn(field);
      this.syncStrategy(field);
   }

   public void syncStrategy(FieldMapping field) {
      this.setStrategy((String)null);
      if (field.getHandler() == null && field.getStrategy() != null) {
         Strategy strat = field.getMappingRepository().defaultStrategy(field, false);
         if (strat == null || !strat.getAlias().equals(field.getAlias())) {
            this.setStrategy(field.getAlias());
         }

      }
   }

   public void syncOrderColumn(FieldMapping field) {
      if (field.getOrderColumn() != null) {
         this._orderCol = syncColumn(field, field.getOrderColumn(), 1, false, field.getTable(), (Table)null, (Object)null, false);
      } else {
         this._orderCol = null;
      }

   }

   public boolean hasSchemaComponents() {
      return super.hasSchemaComponents() || this._tableName != null || this._orderCol != null;
   }

   protected void clear(boolean canFlags) {
      super.clear(canFlags);
      this._tableName = null;
      this._orderCol = null;
      if (canFlags) {
         this._canOrderCol = true;
      }

   }

   public void copy(MappingInfo info) {
      super.copy(info);
      if (info instanceof FieldMappingInfo) {
         FieldMappingInfo finfo = (FieldMappingInfo)info;
         if (this._tableName == null) {
            this._tableName = finfo.getTableName();
         }

         if (!this._outer) {
            this._outer = finfo.isJoinOuter();
         }

         if (this._canOrderCol && this._orderCol == null) {
            this._canOrderCol = finfo.canOrderColumn();
         }

         if (this._canOrderCol && finfo.getOrderColumn() != null) {
            if (this._orderCol == null) {
               this._orderCol = new Column();
            }

            this._orderCol.copy(finfo.getOrderColumn());
         }

      }
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }
}
