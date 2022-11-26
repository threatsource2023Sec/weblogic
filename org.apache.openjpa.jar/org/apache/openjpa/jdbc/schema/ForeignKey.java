package org.apache.openjpa.jdbc.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.StringDistance;
import org.apache.openjpa.util.InvalidStateException;

public class ForeignKey extends Constraint {
   public static final int ACTION_NONE = 1;
   public static final int ACTION_RESTRICT = 2;
   public static final int ACTION_CASCADE = 3;
   public static final int ACTION_NULL = 4;
   public static final int ACTION_DEFAULT = 5;
   private static final Localizer _loc = Localizer.forPackage(ForeignKey.class);
   private String _pkTableName = null;
   private String _pkSchemaName = null;
   private String _pkColumnName = null;
   private int _seq = 0;
   private LinkedHashMap _joins = null;
   private LinkedHashMap _joinsPK = null;
   private LinkedHashMap _consts = null;
   private LinkedHashMap _constsPK = null;
   private int _delAction = 1;
   private int _upAction = 1;
   private int _index = 0;
   private Column[] _locals = null;
   private Column[] _pks = null;
   private Object[] _constVals = null;
   private Column[] _constCols = null;
   private Object[] _constValsPK = null;
   private Column[] _constColsPK = null;
   private Table _pkTable = null;
   private Boolean _autoAssign = null;

   public static int getAction(String name) {
      if (name != null && !"none".equalsIgnoreCase(name)) {
         if ("cascade".equalsIgnoreCase(name)) {
            return 3;
         } else if ("default".equalsIgnoreCase(name)) {
            return 5;
         } else if (!"restrict".equalsIgnoreCase(name) && !"exception".equalsIgnoreCase(name)) {
            if ("null".equalsIgnoreCase(name)) {
               return 4;
            } else {
               List recognized = Arrays.asList("none", "exception", "restrict", "cascade", "null", "default");
               String closest = StringDistance.getClosestLevenshteinDistance(name, (Collection)recognized, 0.5F);
               String msg;
               if (closest != null) {
                  msg = _loc.get("bad-fk-action-hint", name, closest, recognized).getMessage();
               } else {
                  msg = _loc.get("bad-fk-action", name, recognized).getMessage();
               }

               throw new IllegalArgumentException(msg);
            }
         } else {
            return 2;
         }
      } else {
         return 1;
      }
   }

   public static String getActionName(int action) {
      switch (action) {
         case 1:
            return "none";
         case 2:
            return "restrict";
         case 3:
            return "cascade";
         case 4:
            return "null";
         case 5:
            return "default";
         default:
            throw new IllegalArgumentException(String.valueOf(action));
      }
   }

   public ForeignKey() {
   }

   public ForeignKey(String name, Table table) {
      super(name, table);
   }

   public boolean isLogical() {
      return this._delAction == 1;
   }

   public boolean isPrimaryKeyAutoAssigned() {
      return this._autoAssign != null ? this._autoAssign : this.isPrimaryKeyAutoAssigned(new ArrayList(3));
   }

   private boolean isPrimaryKeyAutoAssigned(List seen) {
      if (this._autoAssign != null) {
         return this._autoAssign;
      } else {
         Column[] cols = this.getPrimaryKeyColumns();
         if (cols.length == 0) {
            this._autoAssign = Boolean.FALSE;
            return false;
         } else {
            for(int i = 0; i < cols.length; ++i) {
               if (cols[i].isAutoAssigned()) {
                  this._autoAssign = Boolean.TRUE;
                  return true;
               }
            }

            ForeignKey[] fks = this._pkTable.getForeignKeys();
            seen.add(this);

            for(int i = 0; i < cols.length; ++i) {
               for(int j = 0; j < fks.length; ++j) {
                  if (fks[j].containsColumn(cols[i]) && !seen.contains(fks[j]) && fks[j].isPrimaryKeyAutoAssigned(seen)) {
                     this._autoAssign = Boolean.TRUE;
                     return true;
                  }
               }
            }

            this._autoAssign = Boolean.FALSE;
            return false;
         }
      }
   }

   public String getPrimaryKeyTableName() {
      Table table = this.getPrimaryKeyTable();
      return table != null ? table.getName() : this._pkTableName;
   }

   public void setPrimaryKeyTableName(String pkTableName) {
      if (this.getPrimaryKeyTable() != null) {
         throw new IllegalStateException();
      } else {
         this._pkTableName = pkTableName;
      }
   }

   public String getPrimaryKeySchemaName() {
      Table table = this.getPrimaryKeyTable();
      return table != null ? table.getSchemaName() : this._pkSchemaName;
   }

   public void setPrimaryKeySchemaName(String pkSchemaName) {
      if (this.getPrimaryKeyTable() != null) {
         throw new IllegalStateException();
      } else {
         this._pkSchemaName = pkSchemaName;
      }
   }

   public String getPrimaryKeyColumnName() {
      return this._pkColumnName;
   }

   public void setPrimaryKeyColumnName(String pkColumnName) {
      if (this.getPrimaryKeyTable() != null) {
         throw new IllegalStateException();
      } else {
         this._pkColumnName = pkColumnName;
      }
   }

   public int getKeySequence() {
      return this._seq;
   }

   public void setKeySequence(int seq) {
      this._seq = seq;
   }

   public int getDeleteAction() {
      return this._delAction;
   }

   public void setDeleteAction(int action) {
      this._delAction = action;
      if (action == 1) {
         this._upAction = 1;
      } else if (this._upAction == 1) {
         this._upAction = 2;
      }

   }

   public int getUpdateAction() {
      return this._upAction;
   }

   public void setUpdateAction(int action) {
      this._upAction = action;
      if (action == 1) {
         this._delAction = 1;
      } else if (this._delAction == 1) {
         this._delAction = 2;
      }

   }

   public int getIndex() {
      Table table = this.getTable();
      if (table != null) {
         table.indexForeignKeys();
      }

      return this._index;
   }

   void setIndex(int index) {
      this._index = index;
   }

   public Column getPrimaryKeyColumn(Column local) {
      return this._joins == null ? null : (Column)this._joins.get(local);
   }

   public Column getColumn(Column pk) {
      return this._joinsPK == null ? null : (Column)this._joinsPK.get(pk);
   }

   public Object getConstant(Column local) {
      return this._consts == null ? null : this._consts.get(local);
   }

   public Object getPrimaryKeyConstant(Column pk) {
      return this._constsPK == null ? null : this._constsPK.get(pk);
   }

   public Column[] getColumns() {
      if (this._locals == null) {
         this._locals = this._joins == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])this._joins.keySet().toArray(new Column[this._joins.size()]));
      }

      return this._locals;
   }

   public Object[] getConstants() {
      if (this._constVals == null) {
         this._constVals = this._consts == null ? Schemas.EMPTY_VALUES : this._consts.values().toArray();
      }

      return this._constVals;
   }

   public Object[] getPrimaryKeyConstants() {
      if (this._constValsPK == null) {
         this._constValsPK = this._constsPK == null ? Schemas.EMPTY_VALUES : this._constsPK.values().toArray();
      }

      return this._constValsPK;
   }

   public boolean containsColumn(Column col) {
      return this._joins != null && this._joins.containsKey(col);
   }

   public boolean containsPrimaryKeyColumn(Column col) {
      return this._joinsPK != null && this._joinsPK.containsKey(col);
   }

   public boolean containsConstantColumn(Column col) {
      return this._consts != null && this._consts.containsKey(col);
   }

   public boolean containsConstantPrimaryKeyColumn(Column col) {
      return this._constsPK != null && this._constsPK.containsKey(col);
   }

   public Column[] getPrimaryKeyColumns() {
      if (this._pks == null) {
         this._pks = this._joins == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])this._joins.values().toArray(new Column[this._joins.size()]));
      }

      return this._pks;
   }

   public Column[] getConstantColumns() {
      if (this._constCols == null) {
         this._constCols = this._consts == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])this._consts.keySet().toArray(new Column[this._consts.size()]));
      }

      return this._constCols;
   }

   public Column[] getConstantPrimaryKeyColumns() {
      if (this._constColsPK == null) {
         this._constColsPK = this._constsPK == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])this._constsPK.keySet().toArray(new Column[this._constsPK.size()]));
      }

      return this._constColsPK;
   }

   public void setJoins(Column[] cols, Column[] pkCols) {
      Column[] cur = this.getColumns();

      int i;
      for(i = 0; i < cur.length; ++i) {
         this.removeJoin(cur[i]);
      }

      if (cols != null) {
         for(i = 0; i < cols.length; ++i) {
            this.join(cols[i], pkCols[i]);
         }
      }

   }

   public void setConstantJoins(Object[] consts, Column[] pkCols) {
      Column[] cur = this.getConstantPrimaryKeyColumns();

      int i;
      for(i = 0; i < cur.length; ++i) {
         this.removeJoin(cur[i]);
      }

      if (consts != null) {
         for(i = 0; i < consts.length; ++i) {
            this.joinConstant(consts[i], pkCols[i]);
         }
      }

   }

   public void setConstantJoins(Column[] cols, Object[] consts) {
      Column[] cur = this.getConstantColumns();

      int i;
      for(i = 0; i < cur.length; ++i) {
         this.removeJoin(cur[i]);
      }

      if (consts != null) {
         for(i = 0; i < consts.length; ++i) {
            this.joinConstant(cols[i], consts[i]);
         }
      }

   }

   public void join(Column local, Column toPK) {
      if (!ObjectUtils.equals(local.getTable(), this.getTable())) {
         throw new InvalidStateException(_loc.get("table-mismatch", local.getTable(), this.getTable()));
      } else {
         Table pkTable = toPK.getTable();
         if (this._pkTable != null && !this._pkTable.equals(pkTable)) {
            throw new InvalidStateException(_loc.get("fk-mismatch", pkTable, this._pkTable));
         } else {
            this._pkTable = pkTable;
            if (this._joins == null) {
               this._joins = new LinkedHashMap();
            }

            this._joins.put(local, toPK);
            if (this._joinsPK == null) {
               this._joinsPK = new LinkedHashMap();
            }

            this._joinsPK.put(toPK, local);
            this._locals = null;
            this._pks = null;
            if (this._autoAssign == Boolean.FALSE) {
               this._autoAssign = null;
            }

         }
      }
   }

   public void joinConstant(Object val, Column toPK) {
      Table pkTable = toPK.getTable();
      if (this._pkTable != null && !this._pkTable.equals(pkTable)) {
         throw new InvalidStateException(_loc.get("fk-mismatch", pkTable, this._pkTable));
      } else {
         this._pkTable = pkTable;
         if (this._constsPK == null) {
            this._constsPK = new LinkedHashMap();
         }

         this._constsPK.put(toPK, val);
         this._constValsPK = null;
         this._constColsPK = null;
      }
   }

   public void joinConstant(Column col, Object val) {
      if (this._consts == null) {
         this._consts = new LinkedHashMap();
      }

      this._consts.put(col, val);
      this._constVals = null;
      this._constCols = null;
   }

   public boolean removeJoin(Column col) {
      boolean remd = false;
      Object rem;
      if (this._joins != null) {
         rem = this._joins.remove(col);
         if (rem != null) {
            this._locals = null;
            this._pks = null;
            this._joinsPK.remove(rem);
            remd = true;
         }
      }

      if (this._joinsPK != null) {
         rem = this._joinsPK.remove(col);
         if (rem != null) {
            this._locals = null;
            this._pks = null;
            this._joins.remove(rem);
            remd = true;
         }
      }

      if (this._consts != null && this._consts.remove(col) != null) {
         this._constVals = null;
         this._constCols = null;
         remd = true;
      }

      if (this._constsPK != null && this._constsPK.containsKey(col)) {
         this._constsPK.remove(col);
         this._constValsPK = null;
         this._constColsPK = null;
         remd = true;
      }

      if ((this._joins == null || this._joins.isEmpty()) && (this._constsPK == null || this._constsPK.isEmpty())) {
         this._pkTable = null;
      }

      if (remd && this._autoAssign == Boolean.TRUE) {
         this._autoAssign = null;
      }

      return remd;
   }

   public Table getPrimaryKeyTable() {
      return this._pkTable;
   }

   public void refColumns() {
      Column[] cols = this.getColumns();

      int i;
      for(i = 0; i < cols.length; ++i) {
         cols[i].ref();
      }

      cols = this.getConstantColumns();

      for(i = 0; i < cols.length; ++i) {
         cols[i].ref();
      }

   }

   public void derefColumns() {
      Column[] cols = this.getColumns();

      int i;
      for(i = 0; i < cols.length; ++i) {
         cols[i].deref();
      }

      cols = this.getConstantColumns();

      for(i = 0; i < cols.length; ++i) {
         cols[i].deref();
      }

   }

   public boolean equalsForeignKey(ForeignKey fk) {
      if (fk == this) {
         return true;
      } else if (fk == null) {
         return false;
      } else if (this.getDeleteAction() != fk.getDeleteAction()) {
         return false;
      } else if (this.isDeferred() != fk.isDeferred()) {
         return false;
      } else if (!this.columnsMatch(fk.getColumns(), fk.getPrimaryKeyColumns())) {
         return false;
      } else if (!match(this.getConstantColumns(), fk.getConstantColumns())) {
         return false;
      } else if (!match(this.getConstants(), fk.getConstants())) {
         return false;
      } else if (!match(this.getConstantPrimaryKeyColumns(), fk.getConstantPrimaryKeyColumns())) {
         return false;
      } else {
         return match(this.getPrimaryKeyConstants(), fk.getPrimaryKeyConstants());
      }
   }

   public boolean columnsMatch(Column[] fkCols, Column[] fkPKCols) {
      return match(this.getColumns(), fkCols) && match(this.getPrimaryKeyColumns(), fkPKCols);
   }

   public boolean hasNotNullColumns() {
      Column[] columns = this.getColumns();

      for(int j = 0; j < columns.length; ++j) {
         if (columns[j].isNotNull()) {
            return true;
         }
      }

      return false;
   }

   private static boolean match(Column[] cols, Column[] fkCols) {
      if (cols.length != fkCols.length) {
         return false;
      } else {
         for(int i = 0; i < fkCols.length; ++i) {
            if (!hasColumn(cols, fkCols[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean hasColumn(Column[] cols, Column col) {
      for(int i = 0; i < cols.length; ++i) {
         if (cols[i].getFullName().equalsIgnoreCase(col.getFullName())) {
            return true;
         }
      }

      return false;
   }

   private static boolean match(Object[] vals, Object[] fkVals) {
      if (vals.length != fkVals.length) {
         return false;
      } else {
         for(int i = 0; i < vals.length; ++i) {
            if (!ObjectUtils.equals(vals[i], fkVals[i])) {
               return false;
            }
         }

         return true;
      }
   }
}
