package org.apache.openjpa.jdbc.schema;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.InvalidStateException;

public class SchemaTool {
   public static final String ACTION_ADD = "add";
   public static final String ACTION_DROP = "drop";
   public static final String ACTION_RETAIN = "retain";
   public static final String ACTION_REFRESH = "refresh";
   public static final String ACTION_BUILD = "build";
   public static final String ACTION_REFLECT = "reflect";
   public static final String ACTION_CREATEDB = "createDB";
   public static final String ACTION_DROPDB = "dropDB";
   public static final String ACTION_IMPORT = "import";
   public static final String ACTION_EXPORT = "export";
   public static final String ACTION_DELETE_TABLE_CONTENTS = "deleteTableContents";
   public static final String[] ACTIONS = new String[]{"add", "drop", "retain", "refresh", "build", "reflect", "createDB", "dropDB", "import", "export", "deleteTableContents"};
   private static final Localizer _loc = Localizer.forPackage(SchemaTool.class);
   private final JDBCConfiguration _conf;
   private final DataSource _ds;
   private final Log _log;
   private final DBDictionary _dict;
   private final String _action;
   private boolean _ignoreErrs;
   private boolean _openjpaTables;
   private boolean _dropTables;
   private boolean _dropSeqs;
   private boolean _pks;
   private boolean _fks;
   private boolean _indexes;
   private boolean _seqs;
   private PrintWriter _writer;
   private SchemaGroup _group;
   private SchemaGroup _db;
   private boolean _fullDB;

   public SchemaTool(JDBCConfiguration conf) {
      this(conf, (String)null);
   }

   public SchemaTool(JDBCConfiguration conf, String action) {
      this._ignoreErrs = false;
      this._openjpaTables = false;
      this._dropTables = true;
      this._dropSeqs = true;
      this._pks = true;
      this._fks = true;
      this._indexes = true;
      this._seqs = true;
      this._writer = null;
      this._group = null;
      this._db = null;
      this._fullDB = false;
      if (action != null && !Arrays.asList(ACTIONS).contains(action)) {
         throw new IllegalArgumentException("action == " + action);
      } else {
         this._conf = conf;
         this._action = action;
         this._ds = conf.getDataSource2((StoreContext)null);
         this._log = conf.getLog("openjpa.jdbc.Schema");
         this._dict = this._conf.getDBDictionaryInstance();
      }
   }

   public String getAction() {
      return this._action;
   }

   public boolean getIgnoreErrors() {
      return this._ignoreErrs;
   }

   public void setIgnoreErrors(boolean ignoreErrs) {
      this._ignoreErrs = ignoreErrs;
   }

   public boolean getOpenJPATables() {
      return this._openjpaTables;
   }

   public void setOpenJPATables(boolean openjpaTables) {
      this._openjpaTables = openjpaTables;
   }

   public boolean getDropTables() {
      return this._dropTables;
   }

   public void setDropTables(boolean dropTables) {
      this._dropTables = dropTables;
   }

   public boolean getDropSequences() {
      return this._dropSeqs;
   }

   public void setDropSequences(boolean dropSeqs) {
      this._dropSeqs = dropSeqs;
      if (dropSeqs) {
         this.setSequences(true);
      }

   }

   public boolean getSequences() {
      return this._seqs;
   }

   public void setSequences(boolean seqs) {
      this._seqs = seqs;
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

   public Writer getWriter() {
      return this._writer;
   }

   public void setWriter(Writer writer) {
      if (writer == null) {
         this._writer = null;
      } else if (writer instanceof PrintWriter) {
         this._writer = (PrintWriter)writer;
      } else {
         this._writer = new PrintWriter(writer);
      }

   }

   public SchemaGroup getSchemaGroup() {
      return this._group;
   }

   public void setSchemaGroup(SchemaGroup group) {
      this._group = group;
   }

   public void run() throws SQLException {
      if (this._action != null) {
         if ("add".equals(this._action)) {
            this.add();
         } else if ("drop".equals(this._action)) {
            this.drop();
         } else if ("retain".equals(this._action)) {
            this.retain();
         } else if ("refresh".equals(this._action)) {
            this.refresh();
         } else if ("build".equals(this._action)) {
            this.build();
         } else if ("createDB".equals(this._action)) {
            this.createDB();
         } else if ("dropDB".equals(this._action)) {
            this.dropDB();
         } else if ("deleteTableContents".equals(this._action)) {
            this.deleteTableContents();
         }

      }
   }

   void add() throws SQLException {
      this.add(this.getDBSchemaGroup(false), this.assertSchemaGroup());
   }

   void drop() throws SQLException {
      this.drop(this.getDBSchemaGroup(false), this.assertSchemaGroup());
   }

   void retain() throws SQLException {
      this.retain(this.getDBSchemaGroup(true), this.assertSchemaGroup(), this.getDropTables(), this.getDropSequences());
   }

   void refresh() throws SQLException {
      SchemaGroup local = this.assertSchemaGroup();
      SchemaGroup db = this.getDBSchemaGroup(true);
      this.retain(db, local, this.getDropTables(), this.getDropSequences());
      this.add(db, local);
   }

   void createDB() throws SQLException {
      SchemaGroup group = new SchemaGroup();
      group.addSchema();
      this.add(group, this.getDBSchemaGroup(true));
   }

   void build() throws SQLException {
      SchemaGroup group = new SchemaGroup();
      group.addSchema();
      this.add(group, this.assertSchemaGroup());
   }

   void dropDB() throws SQLException {
      this.retain(this.getDBSchemaGroup(true), new SchemaGroup(), true, true);
   }

   private void deleteTableContents() throws SQLException {
      SchemaGroup group = this.getSchemaGroup();
      Schema[] schemas = group.getSchemas();
      Collection tables = new LinkedHashSet();

      for(int i = 0; i < schemas.length; ++i) {
         Table[] ts = schemas[i].getTables();

         for(int j = 0; j < ts.length; ++j) {
            tables.add(ts[j]);
         }
      }

      Table[] tableArray = (Table[])((Table[])tables.toArray(new Table[tables.size()]));
      String[] sql = this._conf.getDBDictionaryInstance().getDeleteTableContentsSQL(tableArray);
      if (!this.executeSQL(sql)) {
         this._log.warn(_loc.get("delete-table-contents"));
      }

   }

   public void record() {
      if (this._db != null && this._writer == null) {
         this._conf.getSchemaFactoryInstance().storeSchema(this._db);
      }

   }

   private void add(SchemaGroup db, SchemaGroup repos) throws SQLException {
      Schema[] schemas = repos.getSchemas();
      Schema schema;
      if (this._seqs) {
         for(int i = 0; i < schemas.length; ++i) {
            Sequence[] seqs = schemas[i].getSequences();

            for(int j = 0; j < seqs.length; ++j) {
               if (db.findSequence(schemas[i], seqs[j].getFullName()) == null) {
                  if (this.createSequence(seqs[j])) {
                     schema = db.getSchema(seqs[j].getSchemaName());
                     if (schema == null) {
                        schema = db.addSchema(seqs[j].getSchemaName());
                     }

                     schema.importSequence(seqs[j]);
                  } else {
                     this._log.warn(_loc.get("add-seq", (Object)seqs[j]));
                  }
               }
            }
         }
      }

      int j;
      int j;
      Table[] tabs;
      Table dbTable;
      for(int i = 0; i < schemas.length; ++i) {
         tabs = schemas[i].getTables();

         for(j = 0; j < tabs.length; ++j) {
            Column[] cols = tabs[j].getColumns();
            dbTable = db.findTable(schemas[i], tabs[j].getFullName());

            for(j = 0; j < cols.length; ++j) {
               if (dbTable != null) {
                  Column col = dbTable.getColumn(cols[j].getName());
                  if (col == null) {
                     if (this.addColumn(cols[j])) {
                        dbTable.importColumn(cols[j]);
                     } else {
                        this._log.warn(_loc.get("add-col", cols[j], tabs[j]));
                     }
                  } else if (!cols[j].equalsColumn(col)) {
                     this._log.warn(_loc.get("bad-col", new Object[]{col, dbTable, col.getDescription(), cols[j].getDescription()}));
                  }
               }
            }
         }
      }

      if (this._pks) {
         for(j = 0; j < schemas.length; ++j) {
            tabs = schemas[j].getTables();

            for(j = 0; j < tabs.length; ++j) {
               PrimaryKey pk = tabs[j].getPrimaryKey();
               dbTable = db.findTable(schemas[j], tabs[j].getFullName());
               if (pk != null && !pk.isLogical() && dbTable != null) {
                  if (dbTable.getPrimaryKey() == null && this.addPrimaryKey(pk)) {
                     dbTable.importPrimaryKey(pk);
                  } else if (dbTable.getPrimaryKey() == null) {
                     this._log.warn(_loc.get("add-pk", pk, tabs[j]));
                  } else if (!pk.equalsPrimaryKey(dbTable.getPrimaryKey())) {
                     this._log.warn(_loc.get("bad-pk", dbTable.getPrimaryKey(), dbTable));
                  }
               }
            }
         }
      }

      Set newTables = new HashSet();

      for(j = 0; j < schemas.length; ++j) {
         tabs = schemas[j].getTables();

         for(j = 0; j < tabs.length; ++j) {
            if (db.findTable(schemas[j], tabs[j].getFullName()) == null) {
               if (this.createTable(tabs[j])) {
                  newTables.add(tabs[j]);
                  schema = db.getSchema(tabs[j].getSchemaName());
                  if (schema == null) {
                     schema = db.addSchema(tabs[j].getSchemaName());
                  }

                  schema.importTable(tabs[j]);
               } else {
                  this._log.warn(_loc.get("add-table", (Object)tabs[j]));
               }
            }
         }
      }

      int j;
      int k;
      for(int i = 0; i < schemas.length; ++i) {
         tabs = schemas[i].getTables();

         for(j = 0; j < tabs.length; ++j) {
            if (this._indexes || newTables.contains(tabs[j])) {
               Index[] idxs = tabs[j].getIndexes();
               dbTable = db.findTable(schemas[i], tabs[j].getFullName());

               for(k = 0; k < idxs.length; ++k) {
                  if (dbTable != null) {
                     Index idx = this.findIndex(dbTable, idxs[k]);
                     if (idx == null) {
                        if (this.createIndex(idxs[k], dbTable)) {
                           dbTable.importIndex(idxs[k]);
                        } else {
                           this._log.warn(_loc.get("add-index", idxs[k], tabs[j]));
                        }
                     } else if (!idxs[k].equalsIndex(idx)) {
                        this._log.warn(_loc.get("bad-index", idx, dbTable));
                     }
                  }
               }
            }
         }
      }

      int k;
      for(j = 0; j < schemas.length; ++j) {
         tabs = schemas[j].getTables();

         for(k = 0; k < tabs.length; ++k) {
            if (newTables.contains(tabs[k])) {
               Unique[] uniques = tabs[k].getUniques();
               if (uniques != null && uniques.length != 0) {
                  dbTable = db.findTable(tabs[k]);
                  if (dbTable != null) {
                     for(k = 0; k < uniques.length; ++k) {
                        dbTable.importUnique(uniques[k]);
                     }
                  }
               }
            }
         }
      }

      for(k = 0; k < schemas.length; ++k) {
         tabs = schemas[k].getTables();

         for(int j = 0; j < tabs.length; ++j) {
            if (this._fks || newTables.contains(tabs[j])) {
               ForeignKey[] fks = tabs[j].getForeignKeys();
               dbTable = db.findTable(schemas[k], tabs[j].getFullName());

               for(int k = 0; k < fks.length; ++k) {
                  if (!fks[k].isLogical() && dbTable != null) {
                     ForeignKey fk = this.findForeignKey(dbTable, fks[k]);
                     if (fk == null) {
                        if (this.addForeignKey(fks[k])) {
                           dbTable.importForeignKey(fks[k]);
                        } else {
                           this._log.warn(_loc.get("add-fk", fks[k], tabs[j]));
                        }
                     } else if (!fks[k].equalsForeignKey(fk)) {
                        this._log.warn(_loc.get("bad-fk", fk, dbTable));
                     }
                  }
               }
            }
         }
      }

   }

   private void retain(SchemaGroup db, SchemaGroup repos, boolean tables, boolean sequences) throws SQLException {
      Schema[] schemas = db.getSchemas();
      if (this._seqs && sequences) {
         for(int i = 0; i < schemas.length; ++i) {
            Sequence[] seqs = schemas[i].getSequences();

            for(int j = 0; j < seqs.length; ++j) {
               if (this.isDroppable(seqs[j]) && repos.findSequence(seqs[j]) == null) {
                  if (this.dropSequence(seqs[j])) {
                     schemas[i].removeSequence(seqs[j]);
                  } else {
                     this._log.warn(_loc.get("drop-seq", (Object)seqs[j]));
                  }
               }
            }
         }
      }

      int j;
      int j;
      int j;
      Table[] tabs;
      Table reposTable;
      if (this._fks) {
         for(j = 0; j < schemas.length; ++j) {
            tabs = schemas[j].getTables();

            for(j = 0; j < tabs.length; ++j) {
               if (this.isDroppable(tabs[j])) {
                  ForeignKey[] fks = tabs[j].getForeignKeys();
                  reposTable = repos.findTable(tabs[j]);
                  if (tables || reposTable != null) {
                     for(j = 0; j < fks.length; ++j) {
                        if (!fks[j].isLogical()) {
                           ForeignKey fk = null;
                           if (reposTable != null) {
                              fk = this.findForeignKey(reposTable, fks[j]);
                           }

                           if (reposTable == null || fk == null || !fks[j].equalsForeignKey(fk)) {
                              if (this.dropForeignKey(fks[j])) {
                                 tabs[j].removeForeignKey(fks[j]);
                              } else {
                                 this._log.warn(_loc.get("drop-fk", fks[j], tabs[j]));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (this._pks) {
         for(int i = 0; i < schemas.length; ++i) {
            tabs = schemas[i].getTables();

            for(j = 0; j < tabs.length; ++j) {
               if (this.isDroppable(tabs[j])) {
                  PrimaryKey pk = tabs[j].getPrimaryKey();
                  if (pk == null || !pk.isLogical()) {
                     reposTable = repos.findTable(tabs[j]);
                     if (pk != null && reposTable != null && (reposTable.getPrimaryKey() == null || !pk.equalsPrimaryKey(reposTable.getPrimaryKey()))) {
                        if (this.dropPrimaryKey(pk)) {
                           tabs[j].removePrimaryKey();
                        } else {
                           this._log.warn(_loc.get("drop-pk", pk, tabs[j]));
                        }
                     }
                  }
               }
            }
         }
      }

      Collection drops = new LinkedList();

      for(j = 0; j < schemas.length; ++j) {
         tabs = schemas[j].getTables();

         for(j = 0; j < tabs.length; ++j) {
            if (this.isDroppable(tabs[j])) {
               Column[] cols = tabs[j].getColumns();
               reposTable = repos.findTable(tabs[j]);
               if (reposTable != null) {
                  for(int k = 0; k < cols.length; ++k) {
                     Column col = reposTable.getColumn(cols[k].getName());
                     if (col == null || !cols[k].equalsColumn(col)) {
                        if (tabs[j].getColumns().length == 1) {
                           drops.add(tabs[j]);
                        } else if (this.dropColumn(cols[k])) {
                           tabs[j].removeColumn(cols[k]);
                        } else {
                           this._log.warn(_loc.get("drop-col", cols[k], tabs[j]));
                        }
                     }
                  }
               }
            }
         }
      }

      if (tables) {
         for(j = 0; j < schemas.length; ++j) {
            tabs = schemas[j].getTables();

            for(j = 0; j < tabs.length; ++j) {
               if (this.isDroppable(tabs[j]) && repos.findTable(tabs[j]) == null) {
                  drops.add(tabs[j]);
               }
            }
         }
      }

      this.dropTables(drops, db);
   }

   private void drop(SchemaGroup db, SchemaGroup repos) throws SQLException {
      Schema[] schemas = repos.getSchemas();
      if (this._seqs) {
         for(int i = 0; i < schemas.length; ++i) {
            Sequence[] seqs = schemas[i].getSequences();

            for(int j = 0; j < seqs.length; ++j) {
               if (this.isDroppable(seqs[j])) {
                  Sequence dbSeq = db.findSequence(seqs[j]);
                  if (dbSeq != null) {
                     if (this.dropSequence(seqs[j])) {
                        dbSeq.getSchema().removeSequence(dbSeq);
                     } else {
                        this._log.warn(_loc.get("drop-seq", (Object)seqs[j]));
                     }
                  }
               }
            }
         }
      }

      Collection drops = new LinkedList();

      int k;
      Table[] tabs;
      Table dbTable;
      for(int i = 0; i < schemas.length; ++i) {
         tabs = schemas[i].getTables();

         label180:
         for(int j = 0; j < tabs.length; ++j) {
            if (this.isDroppable(tabs[j])) {
               dbTable = db.findTable(tabs[j]);
               if (dbTable != null) {
                  Column[] dbCols = dbTable.getColumns();

                  for(k = 0; k < dbCols.length; ++k) {
                     if (tabs[j].getColumn(dbCols[k].getName()) == null) {
                        continue label180;
                     }
                  }

                  drops.add(tabs[j]);
               }
            }
         }
      }

      int j;
      int k;
      if (this._fks) {
         ForeignKey[] fks;
         for(k = 0; k < schemas.length; ++k) {
            tabs = schemas[k].getTables();

            for(j = 0; j < tabs.length; ++j) {
               if (this.isDroppable(tabs[j])) {
                  fks = tabs[j].getForeignKeys();
                  dbTable = db.findTable(tabs[j]);

                  for(k = 0; k < fks.length; ++k) {
                     if (!fks[k].isLogical()) {
                        ForeignKey fk = null;
                        if (dbTable != null) {
                           fk = this.findForeignKey(dbTable, fks[k]);
                        }

                        if (dbTable != null && fk != null && this.dropForeignKey(fks[k])) {
                           if (dbTable != null) {
                              dbTable.removeForeignKey(fk);
                           } else {
                              this._log.warn(_loc.get("drop-fk", fks[k], tabs[j]));
                           }
                        }
                     }
                  }
               }
            }
         }

         Iterator itr = drops.iterator();

         label139:
         while(true) {
            do {
               if (!itr.hasNext()) {
                  break label139;
               }

               Table tab = (Table)itr.next();
               dbTable = db.findTable(tab);
            } while(dbTable == null);

            fks = db.findExportedForeignKeys(dbTable.getPrimaryKey());

            for(k = 0; k < fks.length; ++k) {
               if (this.dropForeignKey(fks[k])) {
                  dbTable.removeForeignKey(fks[k]);
               } else {
                  this._log.warn(_loc.get("drop-fk", fks[k], dbTable));
               }
            }
         }
      }

      this.dropTables(drops, db);

      for(k = 0; k < schemas.length; ++k) {
         tabs = schemas[k].getTables();

         for(j = 0; j < tabs.length; ++j) {
            if (this.isDroppable(tabs[j])) {
               Column[] cols = tabs[j].getColumns();
               dbTable = db.findTable(tabs[j]);

               for(k = 0; k < cols.length; ++k) {
                  Column col = null;
                  if (dbTable != null) {
                     col = dbTable.getColumn(cols[k].getName());
                  }

                  if (dbTable != null && col != null && this.dropColumn(cols[k])) {
                     if (dbTable != null) {
                        dbTable.removeColumn(col);
                     } else {
                        this._log.warn(_loc.get("drop-col", cols[k], tabs[j]));
                     }
                  }
               }
            }
         }
      }

   }

   private boolean isDroppable(Table table) {
      return this._openjpaTables || !table.getName().toUpperCase().startsWith("OPENJPA_") && !table.getName().toUpperCase().startsWith("JDO_");
   }

   private boolean isDroppable(Sequence seq) {
      return this._openjpaTables || !seq.getName().toUpperCase().startsWith("OPENJPA_") && !seq.getName().toUpperCase().startsWith("JDO_");
   }

   private Index findIndex(Table dbTable, Index idx) {
      Index[] idxs = dbTable.getIndexes();

      for(int i = 0; i < idxs.length; ++i) {
         if (idx.columnsMatch(idxs[i].getColumns())) {
            return idxs[i];
         }
      }

      return null;
   }

   private ForeignKey findForeignKey(Table dbTable, ForeignKey fk) {
      if (fk.getConstantColumns().length <= 0 && fk.getConstantPrimaryKeyColumns().length <= 0) {
         ForeignKey[] fks = dbTable.getForeignKeys();

         for(int i = 0; i < fks.length; ++i) {
            if (fk.columnsMatch(fks[i].getColumns(), fks[i].getPrimaryKeyColumns())) {
               return fks[i];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void dropTables(Collection tables, SchemaGroup change) throws SQLException {
      if (!tables.isEmpty()) {
         Iterator itr = tables.iterator();

         while(itr.hasNext()) {
            Table table = (Table)itr.next();
            if (this.dropTable(table)) {
               Table changeTable = change.findTable(table);
               if (changeTable != null) {
                  changeTable.getSchema().removeTable(changeTable);
               }
            } else {
               this._log.warn(_loc.get("drop-table", (Object)table));
            }
         }

      }
   }

   public boolean createTable(Table table) throws SQLException {
      return this.executeSQL(this._dict.getCreateTableSQL(table));
   }

   public boolean dropTable(Table table) throws SQLException {
      return this.executeSQL(this._dict.getDropTableSQL(table));
   }

   public boolean createSequence(Sequence seq) throws SQLException {
      return this.executeSQL(this._dict.getCreateSequenceSQL(seq));
   }

   public boolean dropSequence(Sequence seq) throws SQLException {
      return this.executeSQL(this._dict.getDropSequenceSQL(seq));
   }

   public boolean createIndex(Index idx, Table table) throws SQLException {
      int max = this._dict.maxIndexesPerTable;
      int len = table.getIndexes().length;
      if (table.getPrimaryKey() != null) {
         len += table.getPrimaryKey().getColumns().length;
      }

      if (len >= max) {
         this._log.warn(_loc.get("too-many-indexes", idx, table, max + ""));
         return false;
      } else {
         return this.executeSQL(this._dict.getCreateIndexSQL(idx));
      }
   }

   public boolean dropIndex(Index idx) throws SQLException {
      return this.executeSQL(this._dict.getDropIndexSQL(idx));
   }

   public boolean addColumn(Column col) throws SQLException {
      return this.executeSQL(this._dict.getAddColumnSQL(col));
   }

   public boolean dropColumn(Column col) throws SQLException {
      return this.executeSQL(this._dict.getDropColumnSQL(col));
   }

   public boolean addPrimaryKey(PrimaryKey pk) throws SQLException {
      return this.executeSQL(this._dict.getAddPrimaryKeySQL(pk));
   }

   public boolean dropPrimaryKey(PrimaryKey pk) throws SQLException {
      return this.executeSQL(this._dict.getDropPrimaryKeySQL(pk));
   }

   public boolean addForeignKey(ForeignKey fk) throws SQLException {
      return this.executeSQL(this._dict.getAddForeignKeySQL(fk));
   }

   public boolean dropForeignKey(ForeignKey fk) throws SQLException {
      return this.executeSQL(this._dict.getDropForeignKeySQL(fk));
   }

   public SchemaGroup getDBSchemaGroup() {
      try {
         return this.getDBSchemaGroup(true);
      } catch (SQLException var2) {
         throw SQLExceptions.getStore(var2, this._dict);
      }
   }

   public void setDBSchemaGroup(SchemaGroup db) {
      this._db = db;
      if (db != null) {
         this._fullDB = true;
      }

   }

   private SchemaGroup getDBSchemaGroup(boolean full) throws SQLException {
      if (this._db == null || full && !this._fullDB) {
         SchemaGenerator gen = new SchemaGenerator(this._conf);
         gen.setPrimaryKeys(this._pks);
         gen.setForeignKeys(this._fks);
         gen.setIndexes(this._indexes);
         if (full) {
            gen.generateSchemas();
         } else {
            Collection tables = new LinkedList();
            SchemaGroup group = this.assertSchemaGroup();
            Schema[] schemas = group.getSchemas();
            int i = 0;

            while(true) {
               if (i >= schemas.length) {
                  if (!tables.isEmpty()) {
                     gen.generateSchemas((String[])((String[])tables.toArray(new String[tables.size()])));
                  }
                  break;
               }

               Table[] tabs = schemas[i].getTables();

               for(int j = 0; j < tabs.length; ++j) {
                  if (tabs[j].getSchemaName() == null) {
                     tables.add("." + tabs[j].getName());
                  } else {
                     tables.add(tabs[j].getFullName());
                  }
               }

               ++i;
            }
         }

         this._db = gen.getSchemaGroup();
      }

      return this._db;
   }

   private SchemaGroup assertSchemaGroup() {
      SchemaGroup local = this.getSchemaGroup();
      if (local == null) {
         throw new InvalidStateException(_loc.get("tool-norepos"));
      } else {
         return local;
      }
   }

   private boolean executeSQL(String[] sql) throws SQLException {
      if (sql.length == 0) {
         return false;
      } else {
         boolean err = false;
         if (this._writer == null) {
            Connection conn = this._ds.getConnection();
            Statement statement = null;
            boolean wasAuto = true;

            try {
               wasAuto = conn.getAutoCommit();
               if (!wasAuto) {
                  conn.setAutoCommit(true);
               }

               for(int i = 0; i < sql.length; ++i) {
                  try {
                     try {
                        conn.rollback();
                     } catch (Exception var34) {
                     }

                     statement = conn.createStatement();
                     statement.executeUpdate(sql[i]);

                     try {
                        conn.commit();
                     } catch (Exception var33) {
                     }
                  } catch (SQLException var35) {
                     err = true;
                     this.handleException(var35);
                  } finally {
                     if (statement != null) {
                        try {
                           statement.close();
                        } catch (SQLException var32) {
                        }
                     }

                  }
               }
            } finally {
               if (!wasAuto) {
                  conn.setAutoCommit(false);
               }

               try {
                  conn.close();
               } catch (SQLException var31) {
               }

            }
         } else {
            for(int i = 0; i < sql.length; ++i) {
               this._writer.println(sql[i] + ";");
            }

            this._writer.flush();
         }

         return !err;
      }
   }

   private void handleException(SQLException sql) throws SQLException {
      if (!this._ignoreErrs) {
         throw sql;
      } else {
         this._log.warn(sql.getMessage(), sql);
      }
   }

   public static void main(String[] args) throws IOException, SQLException {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = SchemaTool.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("tool-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws IOException, SQLException {
      Flags flags = new Flags();
      flags.dropTables = opts.removeBooleanProperty("dropTables", "dt", flags.dropTables);
      flags.dropSequences = opts.removeBooleanProperty("dropSequences", "dsq", flags.dropSequences);
      flags.ignoreErrors = opts.removeBooleanProperty("ignoreErrors", "i", flags.ignoreErrors);
      flags.openjpaTables = opts.removeBooleanProperty("openjpaTables", "ot", flags.openjpaTables);
      flags.primaryKeys = opts.removeBooleanProperty("primaryKeys", "pk", flags.primaryKeys);
      flags.foreignKeys = opts.removeBooleanProperty("foreignKeys", "fks", flags.foreignKeys);
      flags.indexes = opts.removeBooleanProperty("indexes", "ix", flags.indexes);
      flags.sequences = opts.removeBooleanProperty("sequences", "sq", flags.sequences);
      flags.record = opts.removeBooleanProperty("record", "r", flags.record);
      String fileName = opts.removeProperty("file", "f", (String)null);
      String schemas = opts.removeProperty("s");
      if (schemas != null) {
         opts.setProperty("schemas", schemas);
      }

      String[] actions = opts.removeProperty("action", "a", flags.action).split(",");
      Configurations.populateConfiguration(conf, opts);
      ClassLoader loader = conf.getClassResolverInstance().getClassLoader(SchemaTool.class, (ClassLoader)null);
      flags.writer = Files.getWriter(fileName, loader);
      boolean returnValue = true;

      for(int i = 0; i < actions.length; ++i) {
         flags.action = actions[i];
         returnValue &= run(conf, args, flags, loader);
      }

      return returnValue;
   }

   public static boolean run(JDBCConfiguration conf, String[] args, Flags flags, ClassLoader loader) throws IOException, SQLException {
      Log log = conf.getLog("openjpa.Tool");
      if ("reflect".equals(flags.action)) {
         if (args.length > 0) {
            return false;
         } else {
            if (flags.writer == null) {
               flags.writer = new PrintWriter(System.out);
            }

            SchemaGenerator gen = new SchemaGenerator(conf);
            gen.setPrimaryKeys(flags.primaryKeys);
            gen.setIndexes(flags.indexes);
            gen.setForeignKeys(flags.foreignKeys);
            gen.setSequences(flags.sequences);
            gen.setOpenJPATables(flags.openjpaTables);
            String schemas = conf.getSchemas();
            if (StringUtils.isEmpty(schemas)) {
               schemas = "all";
            }

            log.info(_loc.get("sch-reflect", (Object)schemas));
            gen.generateSchemas();
            log.info(_loc.get("sch-reflect-write"));
            SchemaSerializer ser = new XMLSchemaSerializer(conf);
            ser.addAll(gen.getSchemaGroup());
            ser.serialize(flags.writer, 1);
            return true;
         }
      } else if (args.length == 0 && !"createDB".equals(flags.action) && !"dropDB".equals(flags.action) && !"export".equals(flags.action) && !"deleteTableContents".equals(flags.action)) {
         return false;
      } else {
         SchemaParser parser = new XMLSchemaParser(conf);
         parser.setDelayConstraintResolve(true);

         for(int i = 0; i < args.length; ++i) {
            File file = Files.getFile(args[i], loader);
            log.info(_loc.get("tool-running", (Object)file));
            parser.parse(file);
         }

         parser.resolveConstraints();
         SchemaGroup schema;
         if ("import".equals(flags.action)) {
            log.info(_loc.get("tool-import-store"));
            schema = parser.getSchemaGroup();
            conf.getSchemaFactoryInstance().storeSchema(schema);
            return true;
         } else if ("export".equals(flags.action)) {
            if (flags.writer == null) {
               flags.writer = new PrintWriter(System.out);
            }

            log.info(_loc.get("tool-export-gen"));
            schema = conf.getSchemaFactoryInstance().readSchema();
            log.info(_loc.get("tool-export-write"));
            SchemaSerializer ser = new XMLSchemaSerializer(conf);
            ser.addAll(schema);
            ser.serialize(flags.writer, 1);
            return true;
         } else {
            SchemaTool tool = new SchemaTool(conf, flags.action);
            tool.setIgnoreErrors(flags.ignoreErrors);
            tool.setDropTables(flags.dropTables);
            tool.setSequences(flags.sequences);
            tool.setDropSequences(flags.dropSequences);
            tool.setPrimaryKeys(flags.primaryKeys);
            tool.setForeignKeys(flags.foreignKeys);
            tool.setIndexes(flags.indexes);
            tool.setOpenJPATables(flags.openjpaTables);
            if (args.length > 0) {
               tool.setSchemaGroup(parser.getSchemaGroup());
            }

            if (flags.writer != null) {
               tool.setWriter(flags.writer);
            }

            log.info(_loc.get("tool-action", (Object)flags.action));

            try {
               tool.run();
            } finally {
               if (flags.record) {
                  log.info(_loc.get("tool-record"));
                  tool.record();
               }

            }

            if (flags.writer != null) {
               flags.writer.flush();
            }

            return true;
         }
      }
   }

   public static class Flags {
      public String action = "add";
      public Writer writer = null;
      public boolean dropTables = true;
      public boolean dropSequences = true;
      public boolean ignoreErrors = false;
      public boolean openjpaTables = false;
      public boolean primaryKeys = true;
      public boolean foreignKeys = true;
      public boolean indexes = true;
      public boolean sequences = true;
      public boolean record = true;
   }
}
