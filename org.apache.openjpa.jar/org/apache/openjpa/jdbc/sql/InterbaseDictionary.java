package org.apache.openjpa.jdbc.sql;

import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.StoreException;

public class InterbaseDictionary extends DBDictionary {
   private static final Localizer _loc = Localizer.forPackage(InterbaseDictionary.class);

   public InterbaseDictionary() {
      this.platform = "Borland Interbase";
      this.validationSQL = "SELECT 1 FROM RDB$DATABASE";
      this.supportsDeferredConstraints = false;
      this.useGetStringForClobs = true;
      this.useSetStringForClobs = true;
      this.useGetBytesForBlobs = true;
      this.useSetBytesForBlobs = true;
      this.maxTableNameLength = 30;
      this.bigintTypeName = "NUMERIC(18,0)";
      this.integerTypeName = "INTEGER";
      this.doubleTypeName = "DOUBLE PRECISION";
      this.charTypeName = "CHAR(1)";
      this.blobTypeName = "BLOB";
      this.clobTypeName = "BLOB SUB_TYPE 1";
      this.bitTypeName = "SMALLINT";
      this.smallintTypeName = "SMALLINT";
      this.tinyintTypeName = "SMALLINT";
      this.toLowerCaseFunction = null;
      this.stringLengthFunction = null;
   }

   protected String getTableNameForMetadata(String tableName) {
      return tableName == null ? "%" : super.getTableNameForMetadata(tableName);
   }

   protected String getColumnNameForMetadata(String columnName) {
      return columnName == null ? "%" : super.getColumnNameForMetadata(columnName);
   }

   protected String appendSize(Column col, String typeName) {
      if (col.isPrimaryKey() && col.getType() == 12) {
         int numKeys = 1;
         if (col.getTable() != null && col.getTable().getPrimaryKey() != null) {
            numKeys = col.getTable().getPrimaryKey().getColumns().length;
         }

         col.setSize(Math.min(col.getSize(), 200 / numKeys));
      } else if (col.getType() == 12 && col.getSize() > 200 && col.getTable() != null) {
         Index[] idx = col.getTable().getIndexes();

         for(int i = 0; i < idx.length; ++i) {
            if (idx[i].containsColumn(col)) {
               col.setSize(Math.min(col.getSize(), 200));
               break;
            }
         }
      }

      return super.appendSize(col, typeName);
   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      throw new StoreException(_loc.get("indexof-not-supported", (Object)this.platform));
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      throw new StoreException(_loc.get("substring-not-supported", (Object)this.platform));
   }

   public String[] getDropColumnSQL(Column column) {
      return new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " DROP " + column};
   }
}
