package org.apache.openjpa.jdbc.schema;

import java.util.ArrayList;
import java.util.List;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;

public abstract class LocalConstraint extends Constraint {
   private static final Localizer _loc = Localizer.forPackage(LocalConstraint.class);
   private List _colList = null;
   private Column[] _cols = null;

   public LocalConstraint() {
   }

   public LocalConstraint(String name, Table table) {
      super(name, table);
   }

   void remove() {
      this.setColumns((Column[])null);
      super.remove();
   }

   public Column[] getColumns() {
      if (this._cols == null) {
         this._cols = this._colList == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])this._colList.toArray(new Column[this._colList.size()]));
      }

      return this._cols;
   }

   public void setColumns(Column[] cols) {
      Column[] cur = this.getColumns();

      int i;
      for(i = 0; i < cur.length; ++i) {
         this.removeColumn(cur[i]);
      }

      if (cols != null) {
         for(i = 0; i < cols.length; ++i) {
            this.addColumn(cols[i]);
         }
      }

   }

   public void addColumn(Column col) {
      if (col == null) {
         throw new InvalidStateException(_loc.get("table-mismatch", col == null ? null : col.getTable(), col == null ? null : this.getTable()));
      } else {
         if (this._colList == null) {
            this._colList = new ArrayList(3);
         } else if (this._colList.contains(col)) {
            return;
         }

         this._colList.add(col);
         this._cols = null;
      }
   }

   public boolean removeColumn(Column col) {
      if (col != null && this._colList != null) {
         if (this._colList.remove(col)) {
            this._cols = null;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean containsColumn(Column col) {
      return col != null && this._colList != null ? this._colList.contains(col) : false;
   }

   public void refColumns() {
      Column[] cols = this.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         cols[i].ref();
      }

   }

   public void derefColumns() {
      Column[] cols = this.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         cols[i].deref();
      }

   }

   public boolean columnsMatch(Column[] ocols) {
      Column[] cols = this.getColumns();
      if (cols.length != ocols.length) {
         return false;
      } else {
         for(int i = 0; i < ocols.length; ++i) {
            if (!hasColumn(cols, ocols[i])) {
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

   protected boolean equalsLocalConstraint(LocalConstraint lc) {
      if (lc == this) {
         return true;
      } else {
         return lc == null ? false : this.columnsMatch(lc.getColumns());
      }
   }
}
