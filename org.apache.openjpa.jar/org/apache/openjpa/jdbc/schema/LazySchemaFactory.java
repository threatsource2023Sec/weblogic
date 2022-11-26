package org.apache.openjpa.jdbc.schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public class LazySchemaFactory extends SchemaGroup implements SchemaFactory, Configurable {
   private transient JDBCConfiguration _conf = null;
   private transient Connection _conn = null;
   private transient DatabaseMetaData _meta = null;
   private transient SchemaGenerator _gen = null;
   private boolean _indexes = false;
   private boolean _pks = false;
   private boolean _fks = false;

   public boolean getPrimaryKeys() {
      return this._pks;
   }

   public void setPrimaryKeys(boolean pks) {
      this._pks = pks;
   }

   public boolean getForeignKeys() {
      return this._fks;
   }

   public void setForeignKeys(boolean fks) {
      this._fks = fks;
   }

   public boolean getIndexes() {
      return this._indexes;
   }

   public void setIndexes(boolean idx) {
      this._indexes = idx;
   }

   public SchemaGroup readSchema() {
      return this;
   }

   public void storeSchema(SchemaGroup schema) {
   }

   public Table findTable(String name) {
      if (name == null) {
         return null;
      } else {
         Table table = super.findTable(name);
         if (table != null) {
            return table;
         } else {
            this.generateSchemaObject(name, true);
            return super.findTable(name);
         }
      }
   }

   public Sequence findSequence(String name) {
      if (name == null) {
         return null;
      } else {
         Sequence seq = super.findSequence(name);
         if (seq != null) {
            return seq;
         } else {
            this.generateSchemaObject(name, false);
            return super.findSequence(name);
         }
      }
   }

   private void generateSchemaObject(String name, boolean isTable) {
      String schemaName = null;
      String objectName = name;
      int dotIdx = name.indexOf(46);
      if (dotIdx == -1) {
         String sep = this._conf.getDBDictionaryInstance().catalogSeparator;
         if (!".".equals(sep)) {
            dotIdx = name.indexOf(sep);
         }
      }

      if (dotIdx != -1) {
         schemaName = name.substring(0, dotIdx);
         objectName = name.substring(dotIdx + 1);
      }

      synchronized(this) {
         boolean close = false;

         try {
            if (this._conn == null) {
               this._conn = this._conf.getDataSource2((StoreContext)null).getConnection();
               close = true;
               this._meta = this._conn.getMetaData();
            }

            if (isTable) {
               this._gen.generateTables(schemaName, objectName, this._conn, this._meta);
               Table table = super.findTable(name);
               if (table != null) {
                  if (this._pks) {
                     this._gen.generatePrimaryKeys(table.getSchemaName(), table.getName(), this._conn, this._meta);
                  }

                  if (this._indexes) {
                     this._gen.generateIndexes(table.getSchemaName(), table.getName(), this._conn, this._meta);
                  }

                  if (this._fks) {
                     this._gen.generateForeignKeys(table.getSchemaName(), table.getName(), this._conn, this._meta);
                  }
               }
            } else {
               this._gen.generateSequences(schemaName, objectName, this._conn, this._meta);
            }
         } catch (SQLException var18) {
            throw SQLExceptions.getStore(var18, this._conf.getDBDictionaryInstance());
         } finally {
            if (close) {
               try {
                  this._conn.close();
               } catch (SQLException var17) {
               }

               this._conn = null;
            }

         }

      }
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
      this._gen = new SchemaGenerator(this._conf);
      this._gen.setSchemaGroup(this);
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}
