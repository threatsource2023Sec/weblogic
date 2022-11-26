package org.apache.openjpa.jdbc.schema;

public abstract class Constraint extends ReferenceCounter {
   private String _name = null;
   private String _fullName = null;
   private Table _table = null;
   private String _tableName = null;
   private String _schemaName = null;
   private String _columnName = null;
   private boolean _deferred = false;

   Constraint() {
   }

   Constraint(String name, Table table) {
      this.setName(name);
      if (table != null) {
         this.setTableName(table.getName());
         this.setSchemaName(table.getSchemaName());
      }

      this._table = table;
   }

   void remove() {
      this._table = null;
   }

   public Table getTable() {
      return this._table;
   }

   public String getTableName() {
      return this._tableName;
   }

   public void setTableName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._tableName = name;
         this._fullName = null;
      }
   }

   public String getSchemaName() {
      return this._schemaName;
   }

   public void setSchemaName(String schema) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._schemaName = schema;
      }
   }

   public String getColumnName() {
      return this._columnName;
   }

   public void setColumnName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._columnName = name;
      }
   }

   public String getName() {
      return this._name;
   }

   public void setName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._name = name;
         this._fullName = null;
      }
   }

   public String getFullName() {
      if (this._fullName == null) {
         String name = this.getName();
         if (name == null) {
            return null;
         }

         String tname = this.getTableName();
         if (tname == null) {
            return name;
         }

         this._fullName = tname + "." + name;
      }

      return this._fullName;
   }

   public abstract boolean isLogical();

   public boolean isDeferred() {
      return this._deferred;
   }

   public void setDeferred(boolean deferred) {
      this._deferred = deferred;
   }

   public String toString() {
      if (this.getName() != null) {
         return this.getName();
      } else {
         String name = this.getClass().getName();
         name = name.substring(name.lastIndexOf(46) + 1);
         return "<" + name.toLowerCase() + ">";
      }
   }
}
