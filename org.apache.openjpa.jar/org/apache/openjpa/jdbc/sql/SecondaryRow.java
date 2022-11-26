package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class SecondaryRow extends RowImpl {
   private OpenJPAStateManager[] _fks;
   private ColumnIO[] _fkIO;
   private OpenJPAStateManager[] _rels;
   private RelationId[] _callbacks;

   public SecondaryRow(Table table, int action) {
      this(table.getColumns(), action);
   }

   protected SecondaryRow(Column[] cols, int action) {
      super(cols, action);
      this._fks = null;
      this._fkIO = null;
      this._rels = null;
      this._callbacks = null;
   }

   public void setForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
      this.setForeignKey(fk, (ColumnIO)null, sm);
   }

   public void setForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm) throws SQLException {
      if (!this.delayForeignKey(fk, sm)) {
         super.setForeignKey(fk, io, sm);
      } else {
         if (this.canSetAny(io, fk.getColumns().length + fk.getConstantColumns().length, false)) {
            this.setValid(true);
         }

         if (this._fks == null) {
            this._fks = new OpenJPAStateManager[this.getTable().getForeignKeys().length];
         }

         this._fks[fk.getIndex()] = sm;
         if (this._fkIO != null) {
            this._fkIO[fk.getIndex()] = io;
         } else if (io != null && (this.getAction() == 1 && !io.isAllInsertable(fk, false) || this.getAction() != 1 && !io.isAllUpdatable(fk, false))) {
            this._fkIO = new ColumnIO[this._fks.length];
            this._fkIO[fk.getIndex()] = io;
         }

      }
   }

   private boolean delayForeignKey(ForeignKey fk, OpenJPAStateManager sm) {
      return fk.isPrimaryKeyAutoAssigned() && this.getAction() != 2 && sm != null && sm.isNew() && !sm.isFlushed();
   }

   public void setRelationId(Column col, OpenJPAStateManager sm, RelationId rel) throws SQLException {
      if (sm != null && sm.getObjectId() == null && sm.isNew() && !sm.isFlushed() && isPrimaryKeyAutoAssigned(sm)) {
         if (this._rels == null) {
            Column[] cols = this.getTable().getRelationIdColumns();
            this._rels = new OpenJPAStateManager[cols.length];
            this._callbacks = new RelationId[cols.length];
         }

         int idx = this.getRelationIdIndex(col);
         this._rels[idx] = sm;
         this._callbacks[idx] = rel;
      } else {
         super.setRelationId(col, sm, rel);
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

   protected String generateSQL(DBDictionary dict) {
      try {
         if (this._fks != null) {
            ForeignKey[] fks = this.getTable().getForeignKeys();

            for(int i = 0; i < this._fks.length; ++i) {
               if (this._fks[i] != null) {
                  ColumnIO io = this._fkIO == null ? null : this._fkIO[i];
                  super.setForeignKey(fks[i], io, this._fks[i]);
               }
            }
         }

         if (this._rels != null) {
            Column[] cols = this.getTable().getRelationIdColumns();

            for(int i = 0; i < this._rels.length; ++i) {
               if (this._rels[i] != null) {
                  super.setRelationId(cols[i], this._rels[i], this._callbacks[i]);
               }
            }
         }
      } catch (SQLException var5) {
         throw SQLExceptions.getStore(var5, dict);
      }

      return super.generateSQL(dict);
   }

   protected RowImpl newInstance(Column[] cols, int action) {
      return new SecondaryRow(cols, action);
   }

   public void copyInto(RowImpl row, boolean whereOnly) {
      super.copyInto(row, whereOnly);
      if (this._fks != null && !whereOnly && row.getAction() != 2 && row instanceof SecondaryRow) {
         SecondaryRow srow = (SecondaryRow)row;
         if (srow._fks == null) {
            srow._fks = new OpenJPAStateManager[this._fks.length];
         }

         System.arraycopy(this._fks, 0, srow._fks, 0, this._fks.length);
         if (this._fkIO != null) {
            if (srow._fkIO == null) {
               srow._fkIO = new ColumnIO[this._fkIO.length];
            }

            System.arraycopy(this._fkIO, 0, srow._fkIO, 0, this._fkIO.length);
         }

      }
   }
}
