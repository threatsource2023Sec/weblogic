package org.apache.openjpa.jdbc.kernel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.InvalidStateException;
import serp.util.Numbers;
import serp.util.Strings;

public class TableJDBCSeq extends AbstractJDBCSeq implements Configurable {
   public static final String ACTION_DROP = "drop";
   public static final String ACTION_ADD = "add";
   public static final String ACTION_GET = "get";
   public static final String ACTION_SET = "set";
   private static final Localizer _loc = Localizer.forPackage(TableJDBCSeq.class);
   private transient JDBCConfiguration _conf = null;
   private transient Log _log = null;
   private int _alloc = 50;
   private int _intValue = 1;
   private final ConcurrentHashMap _stat = new ConcurrentHashMap();
   private String _table = "OPENJPA_SEQUENCE_TABLE";
   private String _seqColumnName = "SEQUENCE_VALUE";
   private String _pkColumnName = "ID";
   private Column _seqColumn = null;
   private Column _pkColumn = null;
   private int _schemasIdx = 0;

   public String getTable() {
      return this._table;
   }

   public void setTable(String name) {
      this._table = name;
   }

   /** @deprecated */
   public void setTableName(String name) {
      this.setTable(name);
   }

   public String getSequenceColumn() {
      return this._seqColumnName;
   }

   public void setSequenceColumn(String sequenceColumn) {
      this._seqColumnName = sequenceColumn;
   }

   public String getPrimaryKeyColumn() {
      return this._pkColumnName;
   }

   public void setPrimaryKeyColumn(String primaryKeyColumn) {
      this._pkColumnName = primaryKeyColumn;
   }

   public int getAllocate() {
      return this._alloc;
   }

   public void setAllocate(int alloc) {
      this._alloc = alloc;
   }

   public int getInitialValue() {
      return this._intValue;
   }

   public void setInitialValue(int intValue) {
      this._intValue = intValue;
   }

   /** @deprecated */
   public void setIncrement(int inc) {
      this.setAllocate(inc);
   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
      this._log = this._conf.getLog("openjpa.Runtime");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      this.buildTable();
   }

   public void addSchema(ClassMapping mapping, SchemaGroup group) {
      Schema[] schemas = group.getSchemas();

      for(int i = 0; i < schemas.length; ++i) {
         String schemaName = Strings.getPackageName(this._table);
         if (schemaName.length() == 0) {
            schemaName = Schemas.getNewTableSchema(this._conf);
         }

         if (schemaName == null) {
            schemaName = schemas[i].getName();
         }

         Schema schema = group.getSchema(schemaName);
         if (schema == null) {
            schema = group.addSchema(schemaName);
         }

         schema.importTable(this._pkColumn.getTable());
         this._pkColumn.resetTableName(schemaName + "." + this._pkColumn.getTableName());
         this._conf.getDBDictionaryInstance().createIndexIfNecessary(schema, this._table, this._pkColumn);
      }

   }

   protected Object nextInternal(JDBCStore store, ClassMapping mapping) throws Exception {
      Status stat = this.getStatus(mapping);
      if (stat == null) {
         throw new InvalidStateException(_loc.get("bad-seq-type", this.getClass(), mapping));
      } else {
         while(true) {
            synchronized(stat) {
               stat.seq = Math.max(stat.seq, 1L);
               if (stat.seq < stat.max) {
                  return Numbers.valueOf((long)(stat.seq++));
               }

               this.allocateSequence(store, mapping, stat, this._alloc, true);
            }
         }
      }
   }

   protected Object currentInternal(JDBCStore store, ClassMapping mapping) throws Exception {
      if (this.current == null) {
         Connection conn = this.getConnection(store);

         try {
            long cur = this.getSequence(mapping, conn);
            if (cur != -1L) {
               this.current = Numbers.valueOf(cur);
            }
         } finally {
            this.closeConnection(conn);
         }
      }

      return super.currentInternal(store, mapping);
   }

   protected void allocateInternal(int count, JDBCStore store, ClassMapping mapping) throws SQLException {
      Status stat = this.getStatus(mapping);
      if (stat != null) {
         while(true) {
            int available;
            synchronized(stat) {
               available = (int)(stat.max - stat.seq);
               if (available >= count) {
                  return;
               }
            }

            this.allocateSequence(store, mapping, stat, count - available, false);
         }
      }
   }

   protected Status getStatus(ClassMapping mapping) {
      Status status = (Status)this._stat.get(mapping);
      if (status == null) {
         status = new Status();
         Status tStatus = (Status)this._stat.putIfAbsent(mapping, status);
         if (tStatus != null) {
            return tStatus;
         }
      }

      return status;
   }

   protected Column addPrimaryKeyColumn(Table table) {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      Column pkColumn = table.addColumn(dict.getValidColumnName(this.getPrimaryKeyColumn(), table));
      pkColumn.setType(dict.getPreferredType(-6));
      pkColumn.setJavaType(5);
      return pkColumn;
   }

   protected Object getPrimaryKey(ClassMapping mapping) {
      return Numbers.valueOf(0);
   }

   private void buildTable() {
      String tableName = Strings.getClassName(this._table);
      String schemaName = Strings.getPackageName(this._table);
      if (schemaName.length() == 0) {
         schemaName = Schemas.getNewTableSchema(this._conf);
      }

      SchemaGroup group = new SchemaGroup();
      Schema schema = group.addSchema(schemaName);
      Table table = schema.addTable(tableName);
      this._pkColumn = this.addPrimaryKeyColumn(table);
      PrimaryKey pk = table.addPrimaryKey();
      pk.addColumn(this._pkColumn);
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      this._seqColumn = table.addColumn(dict.getValidColumnName(this._seqColumnName, table));
      this._seqColumn.setType(dict.getPreferredType(-5));
      this._seqColumn.setJavaType(6);
   }

   private void allocateSequence(JDBCStore store, ClassMapping mapping, Status stat, int alloc, boolean updateStatSeq) throws SQLException {
      Connection conn = this.getConnection(store);

      try {
         if (this.setSequence(mapping, stat, alloc, updateStatSeq, conn)) {
            return;
         }
      } catch (SQLException var42) {
         throw SQLExceptions.getStore(_loc.get("bad-seq-up", (Object)this._table), var42, this._conf.getDBDictionaryInstance());
      } finally {
         this.closeConnection(conn);
      }

      try {
         SQLException err = null;
         conn = this._conf.getDataSource2(store.getContext()).getConnection();

         try {
            this.insertSequence(mapping, conn);
         } catch (SQLException var38) {
            err = var38;
         } finally {
            try {
               conn.close();
            } catch (SQLException var37) {
            }

         }

         conn = this.getConnection(store);

         try {
            if (!this.setSequence(mapping, stat, alloc, updateStatSeq, conn)) {
               throw err != null ? err : new SQLException(_loc.get("no-seq-row", mapping, this._table).getMessage());
            }
         } finally {
            this.closeConnection(conn);
         }

      } catch (SQLException var41) {
         throw SQLExceptions.getStore(_loc.get("bad-seq-up", (Object)this._table), var41, this._conf.getDBDictionaryInstance());
      }
   }

   private void insertSequence(ClassMapping mapping, Connection conn) throws SQLException {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("insert-seq"));
      }

      Object pk = this.getPrimaryKey(mapping);
      if (pk == null) {
         throw new InvalidStateException(_loc.get("bad-seq-type", this.getClass(), mapping));
      } else {
         DBDictionary dict = this._conf.getDBDictionaryInstance();
         String tableName = this.resolveTableName(mapping, this._pkColumn.getTable());
         SQLBuffer insert = (new SQLBuffer(dict)).append("INSERT INTO ").append(tableName).append(" (").append(this._pkColumn).append(", ").append(this._seqColumn).append(") VALUES (").appendValue(pk, this._pkColumn).append(", ").appendValue(this._intValue, this._seqColumn).append(")");
         boolean wasAuto = conn.getAutoCommit();
         if (!wasAuto && !this.suspendInJTA()) {
            conn.setAutoCommit(true);
         }

         PreparedStatement stmnt = null;

         try {
            stmnt = this.prepareStatement(conn, insert);
            this.executeUpdate(this._conf, conn, stmnt, insert, 1);
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var15) {
               }
            }

            if (!wasAuto && !this.suspendInJTA()) {
               conn.setAutoCommit(false);
            }

         }

      }
   }

   protected long getSequence(ClassMapping mapping, Connection conn) throws SQLException {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("get-seq"));
      }

      Object pk = this.getPrimaryKey(mapping);
      if (pk == null) {
         return -1L;
      } else {
         DBDictionary dict = this._conf.getDBDictionaryInstance();
         SQLBuffer sel = (new SQLBuffer(dict)).append(this._seqColumn);
         SQLBuffer where = (new SQLBuffer(dict)).append(this._pkColumn).append(" = ").appendValue(pk, this._pkColumn);
         String tableName = this.resolveTableName(mapping, this._seqColumn.getTable());
         SQLBuffer tables = (new SQLBuffer(dict)).append(tableName);
         SQLBuffer select = dict.toSelect(sel, (JDBCFetchConfiguration)null, tables, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, dict.supportsSelectForUpdate, 0L, Long.MAX_VALUE, false, true);
         PreparedStatement stmnt = this.prepareStatement(conn, select);
         ResultSet rs = null;

         long var12;
         try {
            rs = this.executeQuery(this._conf, conn, stmnt, select);
            var12 = this.getSequence(rs, dict);
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var23) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var22) {
               }
            }

         }

         return var12;
      }
   }

   protected boolean setSequence(ClassMapping mapping, Status stat, int inc, boolean updateStatSeq, Connection conn) throws SQLException {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("update-seq"));
      }

      Object pk = this.getPrimaryKey(mapping);
      if (pk == null) {
         throw new InvalidStateException(_loc.get("bad-seq-type", this.getClass(), mapping));
      } else {
         DBDictionary dict = this._conf.getDBDictionaryInstance();
         SQLBuffer where = (new SQLBuffer(dict)).append(this._pkColumn).append(" = ").appendValue(pk, this._pkColumn);
         long cur = 0L;
         int updates = 0;

         while(true) {
            if (updates == 0) {
               PreparedStatement stmnt = null;
               ResultSet rs = null;

               boolean var15;
               try {
                  cur = this.getSequence(mapping, conn);
                  if (cur != -1L) {
                     SQLBuffer upd = new SQLBuffer(dict);
                     String tableName = this.resolveTableName(mapping, this._seqColumn.getTable());
                     upd.append("UPDATE ").append(tableName).append(" SET ").append(this._seqColumn).append(" = ").appendValue(Numbers.valueOf(cur + (long)inc), this._seqColumn).append(" WHERE ").append(where).append(" AND ").append(this._seqColumn).append(" = ").appendValue(Numbers.valueOf(cur), this._seqColumn);
                     stmnt = this.prepareStatement(conn, upd);
                     updates = this.executeUpdate(this._conf, conn, stmnt, upd, 0);
                     continue;
                  }

                  var15 = false;
               } finally {
                  if (rs != null) {
                     try {
                        ((ResultSet)rs).close();
                     } catch (SQLException var29) {
                     }
                  }

                  if (stmnt != null) {
                     try {
                        stmnt.close();
                     } catch (SQLException var28) {
                     }
                  }

               }

               return var15;
            }

            synchronized(stat) {
               if (updateStatSeq && stat.seq < cur) {
                  stat.seq = cur;
               }

               if (stat.max < cur + (long)inc) {
                  stat.max = cur + (long)inc;
               }

               return true;
            }
         }
      }
   }

   public String resolveTableName(ClassMapping mapping, Table table) {
      String sName = mapping.getTable().getSchemaName();
      String tableName;
      if (sName == null) {
         tableName = table.getFullName();
      } else {
         tableName = sName + "." + table.getName();
      }

      return tableName;
   }

   public void refreshTable() throws SQLException {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("make-seq-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.createTable(this._pkColumn.getTable());
   }

   public void dropTable() throws SQLException {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("drop-seq-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.dropTable(this._pkColumn.getTable());
   }

   public static void main(String[] args) throws Exception {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = TableJDBCSeq.run(conf, arguments, (Options)opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("seq-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws Exception {
      String action = opts.removeProperty("action", "a", (String)null);
      Configurations.populateConfiguration(conf, opts);
      return run(conf, args, action);
   }

   public static boolean run(JDBCConfiguration conf, String[] args, String action) throws Exception {
      if (args.length <= 1 && (args.length == 0 || "set".equals(action))) {
         TableJDBCSeq seq = new TableJDBCSeq();
         String props = Configurations.getProperties(conf.getSequence());
         Configurations.configureInstance(seq, conf, (String)props);
         if ("drop".equals(action)) {
            seq.dropTable();
         } else if ("add".equals(action)) {
            seq.refreshTable();
         } else {
            if (!"get".equals(action) && !"set".equals(action)) {
               return false;
            }

            Connection conn = conf.getDataSource2((StoreContext)null).getConnection();

            boolean var7;
            try {
               long cur = seq.getSequence((ClassMapping)null, (Connection)conn);
               if ("get".equals(action)) {
                  System.out.println(cur);
               } else {
                  long set;
                  if (args.length > 0) {
                     set = Long.parseLong(args[0]);
                  } else {
                     set = cur + (long)seq.getAllocate();
                  }

                  if (set < cur) {
                     set = cur;
                  } else {
                     Status stat = seq.getStatus((ClassMapping)null);
                     seq.setSequence((ClassMapping)null, stat, (int)(set - cur), true, conn);
                     set = stat.seq;
                  }

                  System.err.println(set);
               }

               return true;
            } catch (NumberFormatException var19) {
               var7 = false;
            } finally {
               try {
                  conn.close();
               } catch (SQLException var18) {
               }

            }

            return var7;
         }

         return true;
      } else {
         return false;
      }
   }

   protected PreparedStatement prepareStatement(Connection conn, SQLBuffer buf) throws SQLException {
      return buf.prepareStatement(conn);
   }

   protected int executeUpdate(JDBCConfiguration conf, Connection conn, PreparedStatement stmnt, SQLBuffer buf, int opcode) throws SQLException {
      return stmnt.executeUpdate();
   }

   protected ResultSet executeQuery(JDBCConfiguration conf, Connection conn, PreparedStatement stmnt, SQLBuffer buf) throws SQLException {
      return stmnt.executeQuery();
   }

   protected long getSequence(ResultSet rs, DBDictionary dict) throws SQLException {
      return rs != null && rs.next() ? dict.getLong(rs, 1) : -1L;
   }

   protected static class Status implements Serializable {
      public long seq = 1L;
      public long max = 0L;
   }
}
