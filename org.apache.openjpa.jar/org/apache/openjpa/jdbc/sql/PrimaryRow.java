package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;

public class PrimaryRow extends RowImpl {
   private static final byte PK_SET = 4;
   private static final byte PK_WHERE = 8;
   private static final byte DEPENDENT = 32;
   private static final Localizer _loc = Localizer.forPackage(PrimaryRow.class);
   private OpenJPAStateManager _pk;
   private ColumnIO _pkIO;
   private OpenJPAStateManager[] _fkSet;
   private ColumnIO[] _fkIO;
   private OpenJPAStateManager[] _fkWhere;
   private OpenJPAStateManager[] _relSet;
   private RelationId[] _callbacks;
   private Object _failed;
   private int _idx;

   public PrimaryRow(Table table, int action, OpenJPAStateManager owner) {
      this(table.getColumns(), action, owner);
   }

   protected PrimaryRow(Column[] cols, int action, OpenJPAStateManager owner) {
      super(cols, action);
      this._pk = null;
      this._pkIO = null;
      this._fkSet = null;
      this._fkIO = null;
      this._fkWhere = null;
      this._relSet = null;
      this._callbacks = null;
      this._failed = null;
      this._idx = -1;
      this._pk = owner;
   }

   public boolean isDependent() {
      return (this.flags & 32) > 0;
   }

   public void setDependent(boolean dependent) {
      if (dependent) {
         this.flags = (byte)(this.flags | 32);
      } else {
         this.flags &= -33;
      }

   }

   public int getIndex() {
      return this._idx;
   }

   public void setIndex(int idx) {
      this._idx = idx;
   }

   public Object getFailedObject() {
      return this._failed;
   }

   public void setFailedObject(Object failed) {
      this._failed = failed;
   }

   public OpenJPAStateManager getPrimaryKey() {
      return this._pk;
   }

   public void setPrimaryKey(OpenJPAStateManager sm) throws SQLException {
      this.setPrimaryKey((ColumnIO)null, sm);
   }

   public void setPrimaryKey(ColumnIO io, OpenJPAStateManager sm) {
      this._pk = sm;
      this.flags = (byte)(this.flags | 4);
      this._pkIO = io;
      this.setValid(true);
   }

   public void wherePrimaryKey(OpenJPAStateManager sm) throws SQLException {
      this._pk = sm;
      this.flags = (byte)(this.flags | 8);
      if (this.getAction() == 2) {
         this.setValid(true);
      }

   }

   public ColumnIO getForeignKeyIO(ForeignKey fk) {
      return this._fkIO == null ? null : this._fkIO[fk.getIndex()];
   }

   public OpenJPAStateManager getForeignKeySet(ForeignKey fk) {
      return this._fkSet == null ? null : this._fkSet[fk.getIndex()];
   }

   public OpenJPAStateManager getForeignKeyWhere(ForeignKey fk) {
      return this._fkWhere == null ? null : this._fkWhere[fk.getIndex()];
   }

   public void setForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
      this.setForeignKey(fk, (ColumnIO)null, sm);
   }

   public void setForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm) throws SQLException {
      if (!this.delayForeignKey(fk, sm, true)) {
         super.setForeignKey(fk, io, sm);
      } else {
         this.recordForeignKey(fk, io, sm, true);
      }

   }

   public void whereForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
      if (!this.delayForeignKey(fk, sm, false)) {
         super.whereForeignKey(fk, sm);
      } else {
         this.recordForeignKey(fk, (ColumnIO)null, sm, false);
      }

   }

   public void clearForeignKey(ForeignKey fk) throws SQLException {
      super.clearForeignKey(fk);
      if (this._fkSet != null) {
         this._fkSet[fk.getIndex()] = null;
      }

      if (this._fkIO != null) {
         this._fkIO[fk.getIndex()] = null;
      }

   }

   private boolean delayForeignKey(ForeignKey fk, OpenJPAStateManager sm, boolean set) {
      if (sm == null) {
         return false;
      } else if (this.getAction() != 2) {
         if (sm.isNew() && !sm.isFlushed()) {
            if (!fk.isDeferred() && !fk.isLogical()) {
               return true;
            } else {
               return fk.isPrimaryKeyAutoAssigned();
            }
         } else {
            return false;
         }
      } else {
         return sm.isDeleted() && !fk.isDeferred() && (fk.getDeleteAction() == 2 || fk.getDeleteAction() == 3);
      }
   }

   private void recordForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm, boolean set) {
      if (set) {
         if (this.canSetAny(io, fk.getColumns().length + fk.getConstantColumns().length, false)) {
            this.setValid(true);
         }

         if (this._fkSet == null) {
            this._fkSet = new OpenJPAStateManager[this.getTable().getForeignKeys().length];
         }

         this._fkSet[fk.getIndex()] = sm;
         if (this._fkIO != null) {
            this._fkIO[fk.getIndex()] = io;
         } else if (io != null && (this.getAction() == 1 && !io.isAllInsertable(fk, false) || this.getAction() != 1 && !io.isAllUpdatable(fk, false))) {
            this._fkIO = new ColumnIO[this._fkSet.length];
            this._fkIO[fk.getIndex()] = io;
         }
      } else {
         if (this.getAction() == 2) {
            this.setValid(true);
         }

         if (this._fkWhere == null) {
            this._fkWhere = new OpenJPAStateManager[this.getTable().getForeignKeys().length];
         }

         this._fkWhere[fk.getIndex()] = sm;
      }

   }

   public OpenJPAStateManager getRelationIdSet(Column col) {
      return this._relSet == null ? null : this._relSet[this.getRelationIdIndex(col)];
   }

   public RelationId getRelationIdCallback(Column col) {
      return this._callbacks == null ? null : this._callbacks[this.getRelationIdIndex(col)];
   }

   public void setRelationId(Column col, OpenJPAStateManager sm, RelationId rel) throws SQLException {
      if (sm != null && sm.getObjectId() == null && sm.isNew() && !sm.isFlushed() && isPrimaryKeyAutoAssigned(sm)) {
         if (this._relSet == null) {
            Column[] cols = this.getTable().getRelationIdColumns();
            this._relSet = new OpenJPAStateManager[cols.length];
            this._callbacks = new RelationId[cols.length];
         }

         int idx = this.getRelationIdIndex(col);
         this._relSet[idx] = sm;
         this._callbacks[idx] = rel;
      } else {
         super.setRelationId(col, sm, rel);
      }

   }

   public void clearRelationId(Column col) throws SQLException {
      super.clearRelationId(col);
      if (this._relSet != null) {
         int idx = this.getRelationIdIndex(col);
         this._relSet[idx] = null;
         this._callbacks[idx] = null;
      }

   }

   private int getRelationIdIndex(Column col) {
      Column[] cols = this.getTable().getRelationIdColumns();

      for(int i = 0; i < cols.length; ++i) {
         if (cols[i] == col) {
            return i;
         }
      }

      return -1;
   }

   private static boolean isPrimaryKeyAutoAssigned(OpenJPAStateManager sm) {
      ClassMapping cls;
      for(cls = (ClassMapping)sm.getMetaData(); cls.getJoinablePCSuperclassMapping() != null; cls = cls.getJoinablePCSuperclassMapping()) {
      }

      Column[] cols = cls.getPrimaryKeyColumns();

      for(int i = 0; i < cols.length; ++i) {
         if (cols[i].isAutoAssigned()) {
            return true;
         }
      }

      return false;
   }

   protected void setObject(Column col, Object val, int metaType, boolean overrideDefault) throws SQLException {
      Object prev = this.getSet(col);
      if (prev != null) {
         if (prev == NULL) {
            prev = null;
         }

         if (!rowValueEquals(prev, val)) {
            throw (new InvalidStateException(_loc.get("diff-values", new Object[]{col.getFullName(), prev == null ? null : prev.getClass(), prev, val == null ? null : val.getClass(), val}))).setFatal(true);
         }
      }

      super.setObject(col, val, metaType, overrideDefault);
   }

   private static boolean rowValueEquals(Object o1, Object o2) {
      if (ObjectUtils.equals(o1, o2)) {
         return true;
      } else {
         return o1 instanceof Number && o2 instanceof Number && ((Number)o1).doubleValue() == ((Number)o2).doubleValue();
      }
   }

   protected String generateSQL(DBDictionary dict) {
      try {
         if ((this.flags & 4) > 0) {
            super.setPrimaryKey(this._pkIO, this._pk);
         }

         if ((this.flags & 8) > 0) {
            super.wherePrimaryKey(this._pk);
         }

         ForeignKey[] fks;
         if (this._fkSet != null) {
            fks = this.getTable().getForeignKeys();

            for(int i = 0; i < this._fkSet.length; ++i) {
               if (this._fkSet[i] != null) {
                  ColumnIO io = this._fkIO == null ? null : this._fkIO[i];
                  super.setForeignKey(fks[i], io, this._fkSet[i]);
               }
            }
         }

         int i;
         if (this._relSet != null) {
            Column[] cols = this.getTable().getRelationIdColumns();

            for(i = 0; i < this._relSet.length; ++i) {
               if (this._relSet[i] != null) {
                  super.setRelationId(cols[i], this._relSet[i], this._callbacks[i]);
               }
            }
         }

         if (this._fkWhere != null) {
            fks = this.getTable().getForeignKeys();

            for(i = 0; i < this._fkWhere.length; ++i) {
               if (this._fkWhere[i] != null) {
                  super.whereForeignKey(fks[i], this._fkWhere[i]);
               }
            }
         }
      } catch (SQLException var5) {
         throw SQLExceptions.getStore(var5, dict);
      }

      return super.generateSQL(dict);
   }

   protected RowImpl newInstance(Column[] cols, int action) {
      return new PrimaryRow(cols, action, this._pk);
   }

   public void copyInto(RowImpl row, boolean whereOnly) {
      super.copyInto(row, whereOnly);
      if (row instanceof PrimaryRow) {
         PrimaryRow prow = (PrimaryRow)row;
         prow._pk = this._pk;
         prow._pkIO = this._pkIO;
         if ((this.flags & 8) > 0) {
            prow.flags = (byte)(prow.flags | 8);
         }

         if (!whereOnly && (this.flags & 4) > 0) {
            prow.flags = (byte)(prow.flags | 4);
         }

         if (this._fkWhere != null) {
            if (prow._fkWhere == null) {
               prow._fkWhere = new OpenJPAStateManager[this._fkWhere.length];
            }

            System.arraycopy(this._fkWhere, 0, prow._fkWhere, 0, this._fkWhere.length);
         }

         if (!whereOnly && this._fkSet != null) {
            if (prow._fkSet == null) {
               prow._fkSet = new OpenJPAStateManager[this._fkSet.length];
            }

            System.arraycopy(this._fkSet, 0, prow._fkSet, 0, this._fkSet.length);
            if (this._fkIO != null) {
               if (prow._fkIO == null) {
                  prow._fkIO = new ColumnIO[this._fkIO.length];
               }

               System.arraycopy(this._fkIO, 0, prow._fkIO, 0, this._fkIO.length);
            }
         }

         if (!whereOnly && this._relSet != null) {
            if (prow._relSet == null) {
               prow._relSet = new OpenJPAStateManager[this._relSet.length];
               prow._callbacks = new RelationId[this._callbacks.length];
            }

            System.arraycopy(this._relSet, 0, prow._relSet, 0, this._relSet.length);
            System.arraycopy(this._callbacks, 0, prow._callbacks, 0, this._callbacks.length);
         }

      }
   }
}
