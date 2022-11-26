package org.apache.openjpa.jdbc.schema;

public class PrimaryKey extends LocalConstraint {
   private boolean _logical = false;

   public PrimaryKey() {
   }

   public PrimaryKey(String name, Table table) {
      super(name, table);
   }

   public boolean isLogical() {
      return this._logical;
   }

   public void setLogical(boolean logical) {
      this._logical = logical;
   }

   void remove() {
      Table table = this.getTable();
      if (table != null && table.getSchema() != null && table.getSchema().getSchemaGroup() != null) {
         ForeignKey[] fks = table.getSchema().getSchemaGroup().findExportedForeignKeys(this);

         for(int i = 0; i < fks.length; ++i) {
            fks[i].getTable().removeForeignKey(fks[i]);
         }
      }

      super.remove();
   }

   public void addColumn(Column col) {
      super.addColumn(col);
      col.setPrimaryKey(true);
      if (!this._logical) {
         col.setNotNull(true);
      }

   }

   public boolean equalsPrimaryKey(PrimaryKey pk) {
      return this.equalsLocalConstraint(pk);
   }
}
