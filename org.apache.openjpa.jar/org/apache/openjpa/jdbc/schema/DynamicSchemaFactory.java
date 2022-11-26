package org.apache.openjpa.jdbc.schema;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public class DynamicSchemaFactory extends SchemaGroup implements SchemaFactory, Configurable {
   private transient DBDictionary _dict = null;
   private String _schema = null;

   public void setConfiguration(Configuration conf) {
      JDBCConfiguration jconf = (JDBCConfiguration)conf;
      this._dict = jconf.getDBDictionaryInstance();
      this._schema = jconf.getSchema();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public SchemaGroup readSchema() {
      return this;
   }

   public void storeSchema(SchemaGroup schema) {
   }

   public boolean isKnownTable(Table table) {
      return super.findTable(table) != null;
   }

   public boolean isKnownTable(String name) {
      return super.findTable(name) != null;
   }

   public Table findTable(String name) {
      if (name == null) {
         return null;
      } else {
         Table table = super.findTable(name);
         if (table != null) {
            return table;
         } else {
            String schemaName = null;
            String tableName = name;
            int dotIdx = name.lastIndexOf(46);
            if (dotIdx != -1) {
               schemaName = name.substring(0, dotIdx);
               tableName = name.substring(dotIdx + 1);
            } else {
               schemaName = this._schema;
            }

            Schema schema = this.getSchema(schemaName);
            if (schema == null) {
               schema = this.addSchema(schemaName);
            }

            return schema.addTable(tableName);
         }
      }
   }

   protected Table newTable(String name, Schema schema) {
      return new DynamicTable(name, schema);
   }

   protected Column newColumn(String name, Table table) {
      return new DynamicColumn(name, table);
   }

   private class DynamicColumn extends Column {
      public DynamicColumn(String name, Table table) {
         super(name, table);
      }

      public boolean isCompatible(int type, String typeName, int size, int decimals) {
         if (this.getType() != 1111) {
            return super.isCompatible(type, typeName, size, decimals);
         } else {
            if (type == 12 && size <= 0) {
               size = DynamicSchemaFactory.this._dict.characterColumnSize;
            }

            this.setType(type);
            this.setSize(size);
            if (typeName != null) {
               this.setTypeName(typeName);
            }

            if (decimals >= 0) {
               this.setDecimalDigits(decimals);
            }

            return true;
         }
      }
   }

   private static class DynamicTable extends Table {
      public DynamicTable(String name, Schema schema) {
         super(name, schema);
      }

      public Column getColumn(String name) {
         if (name == null) {
            return null;
         } else {
            Column col = super.getColumn(name);
            return col != null ? col : this.addColumn(name);
         }
      }
   }
}
