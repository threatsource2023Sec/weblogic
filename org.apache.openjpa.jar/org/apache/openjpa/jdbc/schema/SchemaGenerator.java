package org.apache.openjpa.jdbc.schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class SchemaGenerator {
   private static final Localizer _loc = Localizer.forPackage(SchemaGenerator.class);
   private final DataSource _ds;
   private final DBDictionary _dict;
   private final Log _log;
   private final Object[][] _allowed;
   private boolean _indexes = true;
   private boolean _fks = true;
   private boolean _pks = true;
   private boolean _seqs = true;
   private boolean _openjpaTables = true;
   private SchemaGroup _group = null;
   private List _listeners = null;
   private int _schemaObjects = 0;

   public SchemaGenerator(JDBCConfiguration conf) {
      this._ds = conf.getDataSource2((StoreContext)null);
      this._log = conf.getLog("openjpa.jdbc.Schema");
      this._dict = conf.getDBDictionaryInstance();
      this._allowed = parseSchemasList(conf.getSchemasList());
   }

   private static Object[][] parseSchemasList(String[] args) {
      if (args != null && args.length != 0) {
         Map schemas = new HashMap();

         for(int i = 0; i < args.length; ++i) {
            int dotIdx = args[i].indexOf(46);
            String schema;
            String table;
            if (dotIdx == -1) {
               schema = args[i];
               table = null;
            } else if (dotIdx == 0) {
               schema = null;
               table = args[i].substring(1);
            } else {
               schema = args[i].substring(0, dotIdx);
               table = args[i].substring(dotIdx + 1);
            }

            if (table == null && !schemas.containsKey(schema)) {
               schemas.put(schema, (Object)null);
            } else if (table != null) {
               Collection tables = (Collection)schemas.get(schema);
               if (tables == null) {
                  tables = new LinkedList();
                  schemas.put(schema, tables);
               }

               ((Collection)tables).add(table);
            }
         }

         Object[][] parsed = new Object[schemas.size()][2];
         int idx = 0;

         for(Iterator itr = schemas.entrySet().iterator(); itr.hasNext(); ++idx) {
            Map.Entry entry = (Map.Entry)itr.next();
            Collection tables = (Collection)entry.getValue();
            parsed[idx][0] = entry.getKey();
            if (tables != null) {
               parsed[idx][1] = tables.toArray(new String[tables.size()]);
            }
         }

         return parsed;
      } else {
         return (Object[][])null;
      }
   }

   public boolean getIndexes() {
      return this._indexes;
   }

   public void setIndexes(boolean indexes) {
      this._indexes = indexes;
   }

   public boolean getForeignKeys() {
      return this._fks;
   }

   public void setForeignKeys(boolean fks) {
      this._fks = fks;
   }

   public boolean getPrimaryKeys() {
      return this._pks;
   }

   public void setPrimaryKeys(boolean pks) {
      this._pks = pks;
   }

   public boolean getSequences() {
      return this._seqs;
   }

   public void setSequences(boolean seqs) {
      this._seqs = seqs;
   }

   public boolean getOpenJPATables() {
      return this._openjpaTables;
   }

   public void setOpenJPATables(boolean openjpaTables) {
      this._openjpaTables = openjpaTables;
   }

   public SchemaGroup getSchemaGroup() {
      if (this._group == null) {
         this._group = new SchemaGroup();
      }

      return this._group;
   }

   public void setSchemaGroup(SchemaGroup group) {
      this._group = group;
   }

   public void generateSchemas() throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-schemas"));
      this.generateSchemas((String[])null);
   }

   public void generateSchemas(String[] schemasAndTables) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-schemas"));
      Object[][] schemaMap;
      if (schemasAndTables != null && schemasAndTables.length != 0) {
         schemaMap = parseSchemasList(schemasAndTables);
      } else {
         schemaMap = this._allowed;
      }

      int i;
      if (schemaMap == null) {
         this.generateSchema((String)null, (String[])null);
         i = this.getTables((String)null).size();
         this._schemaObjects += i + (this._pks ? i : 0) + (this._indexes ? i : 0) + (this._fks ? i : 0);
         if (this._pks) {
            this.generatePrimaryKeys((String)null, (String[])null);
         }

         if (this._indexes) {
            this.generateIndexes((String)null, (String[])null);
         }

         if (this._fks) {
            this.generateForeignKeys((String)null, (String[])null);
         }

      } else {
         for(i = 0; i < schemaMap.length; ++i) {
            this.generateSchema((String)schemaMap[i][0], (String[])((String[])schemaMap[i][1]));
         }

         for(int i = 0; i < schemaMap.length; ++i) {
            String schemaName = (String)schemaMap[i][0];
            String[] tableNames = (String[])((String[])schemaMap[i][1]);
            int numTables = tableNames != null ? tableNames.length : this.getTables(schemaName).size();
            this._schemaObjects += numTables + (this._pks ? numTables : 0) + (this._indexes ? numTables : 0) + (this._fks ? numTables : 0);
            if (this._pks) {
               this.generatePrimaryKeys(schemaName, tableNames);
            }

            if (this._indexes) {
               this.generateIndexes(schemaName, tableNames);
            }

            if (this._fks) {
               this.generateForeignKeys(schemaName, tableNames);
            }
         }

      }
   }

   public void generateSchema(String name, String[] tableNames) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-schema", (Object)name));
      Connection conn = this._ds.getConnection();
      DatabaseMetaData meta = conn.getMetaData();

      try {
         if (tableNames == null) {
            this.generateTables(name, (String)null, conn, meta);
         } else {
            for(int i = 0; i < tableNames.length; ++i) {
               this.generateTables(name, tableNames[i], conn, meta);
            }
         }

         if (this._seqs) {
            this.generateSequences(name, (String)null, conn, meta);
         }
      } finally {
         try {
            conn.commit();
         } catch (SQLException var14) {
         }

         try {
            conn.close();
         } catch (SQLException var13) {
         }

      }

   }

   public void generatePrimaryKeys(String schemaName, String[] tableNames) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-all-primaries", (Object)schemaName));
      Connection conn = this._ds.getConnection();
      DatabaseMetaData meta = conn.getMetaData();

      try {
         if (tableNames == null) {
            this.generatePrimaryKeys(schemaName, (String)null, conn, meta);
         } else {
            for(int i = 0; i < tableNames.length; ++i) {
               this.generatePrimaryKeys(schemaName, tableNames[i], conn, meta);
            }
         }
      } finally {
         try {
            conn.commit();
         } catch (SQLException var14) {
         }

         try {
            conn.close();
         } catch (SQLException var13) {
         }

      }

   }

   public void generateIndexes(String schemaName, String[] tableNames) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-all-indexes", (Object)schemaName));
      Connection conn = this._ds.getConnection();
      DatabaseMetaData meta = conn.getMetaData();

      try {
         if (tableNames == null) {
            this.generateIndexes(schemaName, (String)null, conn, meta);
         } else {
            for(int i = 0; i < tableNames.length; ++i) {
               this.generateIndexes(schemaName, tableNames[i], conn, meta);
            }
         }
      } finally {
         try {
            conn.commit();
         } catch (SQLException var14) {
         }

         try {
            conn.close();
         } catch (SQLException var13) {
         }

      }

   }

   public void generateForeignKeys(String schemaName, String[] tableNames) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-all-foreigns", (Object)schemaName));
      Connection conn = this._ds.getConnection();
      DatabaseMetaData meta = conn.getMetaData();

      try {
         if (tableNames == null) {
            this.generateForeignKeys(schemaName, (String)null, conn, meta);
         } else {
            for(int i = 0; i < tableNames.length; ++i) {
               this.generateForeignKeys(schemaName, tableNames[i], conn, meta);
            }
         }
      } finally {
         try {
            conn.commit();
         } catch (SQLException var14) {
         }

         try {
            conn.close();
         } catch (SQLException var13) {
         }

      }

   }

   public void generateTables(String schemaName, String tableName, Connection conn, DatabaseMetaData meta) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-columns", schemaName, tableName));
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("gen-tables", schemaName, tableName));
      }

      Column[] cols = this._dict.getColumns(meta, conn.getCatalog(), schemaName, tableName, (String)null, conn);
      Set tableNames = null;
      if (tableName == null || "%".equals(tableName)) {
         Table[] tables = this._dict.getTables(meta, conn.getCatalog(), schemaName, tableName, conn);
         tableNames = new HashSet();

         for(int i = 0; tables != null && i < tables.length; ++i) {
            if (cols == null) {
               tableNames.add(tables[i].getName());
            } else {
               tableNames.add(tables[i].getName().toUpperCase());
            }
         }
      }

      if (cols == null && tableName == null) {
         Iterator itr = tableNames.iterator();

         while(itr.hasNext()) {
            this.generateTables(schemaName, (String)itr.next(), conn, meta);
         }

      } else {
         SchemaGroup group = this.getSchemaGroup();

         for(int i = 0; cols != null && i < cols.length; ++i) {
            tableName = cols[i].getTableName();
            String tableSchema = StringUtils.trimToNull(cols[i].getSchemaName());
            if ((this._openjpaTables || !tableName.toUpperCase().startsWith("OPENJPA_") && !tableName.toUpperCase().startsWith("JDO_")) && !this._dict.isSystemTable(tableName, tableSchema, schemaName != null) && (tableNames == null || tableNames.contains(tableName.toUpperCase())) && this.isAllowedTable(tableSchema, tableName)) {
               Schema schema = group.getSchema(tableSchema);
               if (schema == null) {
                  schema = group.addSchema(tableSchema);
               }

               Table table = schema.getTable(tableName);
               if (table == null) {
                  table = schema.addTable(tableName);
                  if (this._log.isTraceEnabled()) {
                     this._log.trace(_loc.get("col-table", (Object)table));
                  }
               }

               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("gen-column", cols[i].getName(), table));
               }

               if (table.getColumn(cols[i].getName()) == null) {
                  table.importColumn(cols[i]);
               }
            }
         }

      }
   }

   private boolean isAllowedTable(String schema, String table) {
      if (this._allowed == null) {
         return true;
      } else {
         String[] anySchemaTables = null;

         int i;
         for(i = 0; i < this._allowed.length; ++i) {
            if (this._allowed[i][0] == null) {
               anySchemaTables = (String[])((String[])this._allowed[i][1]);
               if (schema == null) {
                  break;
               }
            } else if (StringUtils.equalsIgnoreCase(schema, (String)this._allowed[i][0])) {
               if (table == null) {
                  return true;
               }

               String[] tables = (String[])((String[])this._allowed[i][1]);
               if (tables == null) {
                  return true;
               }

               for(int j = 0; j < tables.length; ++j) {
                  if (StringUtils.equalsIgnoreCase(table, tables[j])) {
                     return true;
                  }
               }
            }
         }

         if (anySchemaTables != null) {
            if (table == null) {
               return true;
            }

            for(i = 0; i < anySchemaTables.length; ++i) {
               if (StringUtils.equalsIgnoreCase(table, anySchemaTables[i])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void generatePrimaryKeys(String schemaName, String tableName, Connection conn, DatabaseMetaData meta) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-primary", schemaName, tableName));
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("gen-pks", schemaName, tableName));
      }

      SchemaGroup group = this.getSchemaGroup();
      if (tableName == null || group.findTable(tableName) != null) {
         PrimaryKey[] pks = this._dict.getPrimaryKeys(meta, conn.getCatalog(), schemaName, tableName, conn);
         Table table;
         if (pks == null && tableName == null) {
            Collection tables = this.getTables(schemaName);
            Iterator itr = tables.iterator();

            while(itr.hasNext()) {
               table = (Table)itr.next();
               this.generatePrimaryKeys(table.getSchemaName(), table.getName(), conn, meta);
            }

         } else {
            for(int i = 0; pks != null && i < pks.length; ++i) {
               schemaName = StringUtils.trimToNull(pks[i].getSchemaName());
               Schema schema = group.getSchema(schemaName);
               if (schema != null) {
                  table = schema.getTable(pks[i].getTableName());
                  if (table != null) {
                     String colName = pks[i].getColumnName();
                     String name = pks[i].getName();
                     if (this._log.isTraceEnabled()) {
                        this._log.trace(_loc.get("gen-pk", name, table, colName));
                     }

                     PrimaryKey pk = table.getPrimaryKey();
                     if (pk == null) {
                        pk = table.addPrimaryKey(name);
                     }

                     pk.addColumn(table.getColumn(colName));
                  }
               }
            }

         }
      }
   }

   public void generateIndexes(String schemaName, String tableName, Connection conn, DatabaseMetaData meta) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-indexes", schemaName, tableName));
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("gen-indexes", schemaName, tableName));
      }

      SchemaGroup group = this.getSchemaGroup();
      if (tableName == null || group.findTable(tableName) != null) {
         Index[] idxs = this._dict.getIndexInfo(meta, conn.getCatalog(), schemaName, tableName, false, true, conn);
         Table table;
         if (idxs == null && tableName == null) {
            Collection tables = this.getTables(schemaName);
            Iterator itr = tables.iterator();

            while(itr.hasNext()) {
               table = (Table)itr.next();
               this.generateIndexes(table.getSchemaName(), table.getName(), conn, meta);
            }

         } else {
            for(int i = 0; idxs != null && i < idxs.length; ++i) {
               schemaName = StringUtils.trimToNull(idxs[i].getSchemaName());
               Schema schema = group.getSchema(schemaName);
               if (schema != null) {
                  table = schema.getTable(idxs[i].getTableName());
                  if (table != null) {
                     String pkName;
                     if (table.getPrimaryKey() != null) {
                        pkName = table.getPrimaryKey().getName();
                     } else {
                        pkName = null;
                     }

                     String name = idxs[i].getName();
                     if (!StringUtils.isEmpty(name) && (pkName == null || !name.equalsIgnoreCase(pkName)) && !this._dict.isSystemIndex(name, table)) {
                        String colName = idxs[i].getColumnName();
                        if (table.getColumn(colName) != null) {
                           if (this._log.isTraceEnabled()) {
                              this._log.trace(_loc.get("gen-index", name, table, colName));
                           }

                           Index idx = table.getIndex(name);
                           if (idx == null) {
                              idx = table.addIndex(name);
                              idx.setUnique(idxs[i].isUnique());
                           }

                           idx.addColumn(table.getColumn(colName));
                        }
                     }
                  }
               }
            }

         }
      }
   }

   public void generateForeignKeys(String schemaName, String tableName, Connection conn, DatabaseMetaData meta) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-foreign", schemaName, tableName));
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("gen-fks", schemaName, tableName));
      }

      SchemaGroup group = this.getSchemaGroup();
      if (tableName == null || group.findTable(tableName) != null) {
         ForeignKey[] fks = this._dict.getImportedKeys(meta, conn.getCatalog(), schemaName, tableName, conn);
         Table table;
         if (fks == null && tableName == null) {
            Collection tables = this.getTables(schemaName);
            Iterator itr = tables.iterator();

            while(itr.hasNext()) {
               table = (Table)itr.next();
               this.generateForeignKeys(table.getSchemaName(), table.getName(), conn, meta);
            }

         } else {
            boolean seqWas0 = false;
            Collection invalids = null;

            ForeignKey fk;
            for(int i = 0; fks != null && i < fks.length; ++i) {
               schemaName = StringUtils.trimToNull(fks[i].getSchemaName());
               Schema schema = group.getSchema(schemaName);
               if (schema != null) {
                  table = schema.getTable(fks[i].getTableName());
                  if (table != null) {
                     String name = fks[i].getName();
                     String fkColName = fks[i].getColumnName();
                     String pkColName = fks[i].getPrimaryKeyColumnName();
                     int seq = fks[i].getKeySequence();
                     if (seq == 0) {
                        seqWas0 = true;
                     }

                     if (seqWas0) {
                        ++seq;
                     }

                     String pkSchemaName = fks[i].getPrimaryKeySchemaName();
                     String pkTableName = fks[i].getPrimaryKeyTableName();
                     if (this._log.isTraceEnabled()) {
                        this._log.trace(_loc.get("gen-fk", new Object[]{name, table, fkColName, pkTableName, pkColName, seq + ""}));
                     }

                     if (!StringUtils.isEmpty(pkSchemaName)) {
                        pkTableName = pkSchemaName + "." + pkTableName;
                     }

                     Table pkTable = group.findTable(pkTableName);
                     if (pkTable == null) {
                        throw new SQLException(_loc.get("gen-nofktable", table, pkTableName).getMessage());
                     }

                     fk = table.getForeignKey(name);
                     if (seq == 1 || fk == null) {
                        fk = table.addForeignKey(name);
                        fk.setDeferred(fks[i].isDeferred());
                        fk.setDeleteAction(fks[i].getDeleteAction());
                     }

                     if (invalids == null || !invalids.contains(fk)) {
                        try {
                           fk.join(table.getColumn(fkColName), pkTable.getColumn(pkColName));
                        } catch (IllegalArgumentException var21) {
                           if (this._log.isWarnEnabled()) {
                              this._log.warn(_loc.get("bad-join", (Object)var21.toString()));
                           }

                           if (invalids == null) {
                              invalids = new HashSet();
                           }

                           invalids.add(fk);
                        }
                     }
                  }
               }
            }

            if (invalids != null) {
               Iterator itr = invalids.iterator();

               while(itr.hasNext()) {
                  fk = (ForeignKey)itr.next();
                  fk.getTable().removeForeignKey(fk);
               }
            }

         }
      }
   }

   public void generateSequences(String schemaName, String sequenceName, Connection conn, DatabaseMetaData meta) throws SQLException {
      this.fireGenerationEvent(_loc.get("generating-sequences", (Object)schemaName));
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("gen-seqs", schemaName, sequenceName));
      }

      Sequence[] seqs = this._dict.getSequences(meta, conn.getCatalog(), (String)null, sequenceName, conn);
      SchemaGroup group = this.getSchemaGroup();

      for(int i = 0; seqs != null && i < seqs.length; ++i) {
         sequenceName = seqs[i].getName();
         String sequenceSchema = StringUtils.trimToNull(seqs[i].getSchemaName());
         if ((this._openjpaTables || !sequenceName.toUpperCase().startsWith("OPENJPA_") && !sequenceName.toUpperCase().startsWith("JDO_")) && !this._dict.isSystemSequence(sequenceName, sequenceSchema, schemaName != null) && this.isAllowedTable(sequenceSchema, (String)null)) {
            Schema schema = group.getSchema(sequenceSchema);
            if (schema == null) {
               schema = group.addSchema(sequenceSchema);
            }

            if (schema.getSequence(sequenceName) == null) {
               schema.addSequence(sequenceName);
            }
         }
      }

   }

   private void fireGenerationEvent(Object schemaObject) throws SQLException {
      if (schemaObject != null) {
         if (this._listeners != null && this._listeners.size() != 0) {
            Event e = new Event(schemaObject, this._schemaObjects);
            Iterator i = this._listeners.iterator();

            Listener l;
            do {
               if (!i.hasNext()) {
                  return;
               }

               l = (Listener)i.next();
            } while(l.schemaObjectGenerated(e));

            throw new SQLException(_loc.get("refresh-cancelled").getMessage());
         }
      }
   }

   public void addListener(Listener l) {
      if (this._listeners == null) {
         this._listeners = new LinkedList();
      }

      this._listeners.add(l);
   }

   public boolean removeListener(Listener l) {
      return this._listeners != null && this._listeners.remove(l);
   }

   private Collection getTables(String schemaName) {
      SchemaGroup group = this.getSchemaGroup();
      if (schemaName != null) {
         Schema schema = group.getSchema(schemaName);
         return schema == null ? Collections.EMPTY_LIST : Arrays.asList(schema.getTables());
      } else {
         Schema[] schemas = group.getSchemas();
         Collection tables = new LinkedList();

         for(int i = 0; i < schemas.length; ++i) {
            tables.addAll(Arrays.asList(schemas[i].getTables()));
         }

         return tables;
      }
   }

   public class Event extends EventObject {
      private final int _total;

      public Event(Object ob, int total) {
         super(ob);
         this._total = total;
      }

      public int getTotal() {
         return this._total;
      }
   }

   public interface Listener {
      boolean schemaObjectGenerated(Event var1);
   }
}
