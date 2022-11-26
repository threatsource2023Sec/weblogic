package org.apache.openjpa.jdbc.schema;

import java.io.Serializable;

public class ColumnIO implements Serializable {
   public static final ColumnIO UNRESTRICTED = new ColumnIO() {
      public void setInsertable(int col, boolean insertable) {
         throw new UnsupportedOperationException();
      }

      public void setUpdatable(int col, boolean updatable) {
         throw new UnsupportedOperationException();
      }

      public void setNullInsertable(int col, boolean insertable) {
         throw new UnsupportedOperationException();
      }

      public void setNullUpdatable(int col, boolean insertable) {
         throw new UnsupportedOperationException();
      }
   };
   private int _unInsertable = 0;
   private int _unUpdatable = 0;
   private int _unNullInsertable = 0;
   private int _unNullUpdatable = 0;

   public boolean isInsertable(int col, boolean nullValue) {
      return this.is(col, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isInsertable(Column col, boolean nullValue) {
      return this.is(col, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isAnyInsertable(int col, boolean nullValue) {
      return this.isAny(col, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isAnyInsertable(Column[] cols, boolean nullValue) {
      return this.isAny(cols, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isAnyInsertable(ForeignKey fk, boolean nullValue) {
      return this.isAny(fk, this._unInsertable, this._unNullInsertable, nullValue) && (!nullValue || fk.isLogical() || this.isNullable(fk));
   }

   public boolean isAllInsertable(int col, boolean nullValue) {
      return this.isAll(col, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isAllInsertable(Column[] cols, boolean nullValue) {
      return this.isAll(cols, this._unInsertable, this._unNullInsertable, nullValue);
   }

   public boolean isAllInsertable(ForeignKey fk, boolean nullValue) {
      return this.isAll(fk, this._unInsertable, this._unNullInsertable, nullValue) && (!nullValue || fk.isLogical() || this.isNullable(fk));
   }

   public void setInsertable(int col, boolean insertable) {
      this._unInsertable = this.set(col, insertable, this._unInsertable);
   }

   public void setNullInsertable(int col, boolean insertable) {
      this._unNullInsertable = this.set(col, insertable, this._unNullInsertable);
   }

   public boolean isUpdatable(int col, boolean nullValue) {
      return this.is(col, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isUpdatable(Column col, boolean nullValue) {
      return this.is(col, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isAnyUpdatable(int col, boolean nullValue) {
      return this.isAny(col, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isAnyUpdatable(Column[] cols, boolean nullValue) {
      return this.isAny(cols, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isAnyUpdatable(ForeignKey fk, boolean nullValue) {
      return this.isAny(fk, this._unUpdatable, this._unNullUpdatable, nullValue) && (!nullValue || fk.isLogical() || this.isNullable(fk));
   }

   public boolean isAllUpdatable(int col, boolean nullValue) {
      return this.isAll(col, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isAllUpdatable(Column[] cols, boolean nullValue) {
      return this.isAll(cols, this._unUpdatable, this._unNullUpdatable, nullValue);
   }

   public boolean isAllUpdatable(ForeignKey fk, boolean nullValue) {
      return this.isAll(fk, this._unUpdatable, this._unNullUpdatable, nullValue) && (!nullValue || fk.isLogical() || this.isNullable(fk));
   }

   public void setUpdatable(int col, boolean updatable) {
      this._unUpdatable = this.set(col, updatable, this._unUpdatable);
   }

   public void setNullUpdatable(int col, boolean updatable) {
      this._unNullUpdatable = this.set(col, updatable, this._unNullUpdatable);
   }

   private boolean is(int col, int property, int nullProperty, boolean nullValue) {
      return (property & 2 << col) == 0 && (!nullValue || (nullProperty & 2 << col) == 0);
   }

   private boolean is(Column col, int property, int nullProperty, boolean nullValue) {
      return col != null && this.is(0, property, nullProperty, nullValue);
   }

   private boolean isAny(int col, int property, int nullProperty, boolean nullValue) {
      if (col == 0) {
         return false;
      } else if (property == 0) {
         return true;
      } else {
         for(int i = 0; i < col; ++i) {
            if (this.is(i, property, nullProperty, nullValue)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isAny(Column[] cols, int property, int nullProperty, boolean nullValue) {
      return this.isAny(cols.length, property, nullProperty, nullValue);
   }

   private boolean isAny(ForeignKey fk, int property, int nullProperty, boolean nullValue) {
      return fk != null && this.isAny(fk.getColumns().length + fk.getConstantColumns().length, property, nullProperty, nullValue);
   }

   private boolean isAll(int col, int property, int nullProperty, boolean nullValue) {
      if (col == 0) {
         return false;
      } else if (property == 0) {
         return true;
      } else {
         for(int i = 0; i < col; ++i) {
            if (!this.is(i, property, nullProperty, nullValue)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isAll(Column[] cols, int property, int nullProperty, boolean nullValue) {
      return this.isAll(cols.length, property, nullProperty, nullValue);
   }

   private boolean isAll(ForeignKey fk, int property, int nullProperty, boolean nullValue) {
      return fk != null && this.isAll(fk.getColumns().length + fk.getConstantColumns().length, property, nullProperty, nullValue);
   }

   private int set(int col, boolean is, int property) {
      return is ? property & ~(2 << col) : property | 2 << col;
   }

   private boolean isNullable(ForeignKey fk) {
      Column[] cols = fk.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         if (cols[i].isNotNull() || cols[i].isPrimaryKey()) {
            return false;
         }
      }

      return true;
   }
}
