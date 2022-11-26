package org.apache.openjpa.jdbc.schema;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
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
import org.apache.openjpa.util.GeneralException;
import serp.util.Numbers;
import serp.util.Strings;

public class TableSchemaFactory implements SchemaFactory, Configurable {
   public static final String ACTION_ADD = "add";
   public static final String ACTION_DROP = "drop";
   private static final Localizer _loc = Localizer.forPackage(TableSchemaFactory.class);
   private static boolean _refreshedTable = false;
   private JDBCConfiguration _conf = null;
   private Log _log = null;
   private String _table = "OPENJPA_SCHEMA";
   private String _pkColumnName = "ID";
   private String _schemaColumnName = "SCHEMA_DEF";
   private Column _pkColumn = null;
   private Column _schemaColumn = null;

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

   public void setPrimaryKeyColumn(String name) {
      this._pkColumnName = name;
   }

   public String getPrimaryKeyColumn() {
      return this._pkColumnName;
   }

   public void setSchemaColumn(String name) {
      this._schemaColumnName = name;
   }

   public String getSchemaColumn() {
      return this._schemaColumnName;
   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
      this._log = this._conf.getLog("openjpa.jdbc.Schema");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      this.buildTable();
   }

   public synchronized SchemaGroup readSchema() {
      String schema = null;

      try {
         schema = this.readSchemaColumn();
      } catch (SQLException var5) {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("bad-sch-read", (Object)var5));
         }
      }

      if (schema == null) {
         return new SchemaGroup();
      } else {
         XMLSchemaParser parser = new XMLSchemaParser(this._conf);

         try {
            parser.parse(new StringReader(schema), this._schemaColumn.getFullName());
         } catch (IOException var4) {
            throw new GeneralException(var4);
         }

         return parser.getSchemaGroup();
      }
   }

   public void storeSchema(SchemaGroup schema) {
      XMLSchemaSerializer ser = new XMLSchemaSerializer(this._conf);
      ser.addAll(schema);
      Writer writer = new StringWriter();

      try {
         ser.serialize(writer, 0);
      } catch (IOException var9) {
         throw new GeneralException(var9);
      }

      String schemaStr = writer.toString();

      try {
         this.writeSchemaColumn(schemaStr);
      } catch (SQLException var13) {
         SQLException se = var13;
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("bad-sch-write-1", (Object)var13));
         }

         Class var6 = TableSchemaFactory.class;
         synchronized(TableSchemaFactory.class) {
            if (!_refreshedTable) {
               _refreshedTable = true;

               try {
                  this.refreshTable();
               } catch (Exception var11) {
                  if (this._log.isWarnEnabled()) {
                     this._log.warn(_loc.get("bad-sch-ref", (Object)var11));
                  }
               }
            }

            try {
               this.writeSchemaColumn(schemaStr);
            } catch (Exception var10) {
               if (this._log.isWarnEnabled()) {
                  this._log.warn(_loc.get("bad-sch-write-2"));
               }

               throw SQLExceptions.getStore(se, this._conf.getDBDictionaryInstance());
            }
         }
      }

   }

   public void refreshTable() throws SQLException {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("make-sch-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.createTable(this._pkColumn.getTable());
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      boolean wasAuto = true;

      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         DBDictionary dict = this._conf.getDBDictionaryInstance();
         stmnt = conn.prepareStatement("INSERT INTO " + dict.getFullName(this._pkColumn.getTable(), false) + " (" + this._pkColumn + ", " + this._schemaColumn + ") VALUES (?, ?)");
         dict.setInt(stmnt, 1, 1, this._pkColumn);
         dict.setNull(stmnt, 2, this._schemaColumn.getType(), this._schemaColumn);
         stmnt.executeUpdate();
      } finally {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var14) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var13) {
         }

      }

   }

   public void dropTable() throws SQLException {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("drop-sch-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.dropTable(this._pkColumn.getTable());
   }

   public String readSchemaColumn() throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      SQLBuffer sel = (new SQLBuffer(dict)).append(this._schemaColumn);
      SQLBuffer where = (new SQLBuffer(dict)).append(this._pkColumn).append(" = ").appendValue(Numbers.valueOf(1), this._pkColumn);
      SQLBuffer tables = (new SQLBuffer(dict)).append(this._pkColumn.getTable());
      SQLBuffer select = dict.toSelect(sel, (JDBCFetchConfiguration)null, tables, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;
      boolean wasAuto = true;

      String var11;
      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         stmnt = select.prepareStatement(conn);
         rs = stmnt.executeQuery();
         rs.next();
         String schema = this._schemaColumn.getType() == 2005 ? dict.getClobString(rs, 1) : dict.getString(rs, 1);
         var11 = schema;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var24) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var23) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var22) {
         }

      }

      return var11;
   }

   public void writeSchemaColumn(String schema) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      boolean embedded = dict.maxEmbeddedClobSize == -1;
      String update;
      if (embedded) {
         update = "UPDATE " + dict.getFullName(this._pkColumn.getTable(), false) + " SET " + this._schemaColumn + " = ?  WHERE " + this._pkColumn + " = ?";
      } else {
         update = "SELECT " + this._schemaColumn + " FROM " + dict.getFullName(this._pkColumn.getTable(), false) + " WHERE " + this._pkColumn + " = ?";
      }

      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;
      boolean wasAuto = true;

      try {
         wasAuto = conn.getAutoCommit();
         if (wasAuto != embedded) {
            conn.setAutoCommit(embedded);
         }

         if (embedded) {
            stmnt = conn.prepareStatement(update);
            if (schema == null) {
               dict.setNull(stmnt, 1, this._schemaColumn.getType(), this._schemaColumn);
            } else if (this._schemaColumn.getType() == 2005) {
               dict.setClobString(stmnt, 1, schema, this._schemaColumn);
            } else {
               dict.setString(stmnt, 1, schema, this._schemaColumn);
            }

            dict.setInt(stmnt, 2, 1, this._pkColumn);
            stmnt.executeUpdate();
         } else {
            stmnt = conn.prepareStatement(update, 1004, 1008);
            dict.setInt(stmnt, 1, 1, this._pkColumn);
            rs = stmnt.executeQuery();
            rs.next();
            dict.putString(rs.getClob(1), schema);
            conn.commit();
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var21) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var20) {
            }
         }

         if (wasAuto != embedded) {
            conn.setAutoCommit(wasAuto);
         }

         try {
            conn.close();
         } catch (SQLException var19) {
         }

      }

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
      PrimaryKey pk = table.addPrimaryKey();
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      this._pkColumn = table.addColumn(dict.getValidColumnName(this._pkColumnName, table));
      this._pkColumn.setType(dict.getPreferredType(-6));
      this._pkColumn.setJavaType(5);
      pk.addColumn(this._pkColumn);
      this._schemaColumn = table.addColumn(dict.getValidColumnName(this._schemaColumnName, table));
      this._schemaColumn.setType(dict.getPreferredType(2005));
      this._schemaColumn.setJavaType(9);
   }

   private Connection getConnection() throws SQLException {
      return this._conf.getDataSource2((StoreContext)null).getConnection();
   }

   public static void main(String[] args) throws IOException, SQLException {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = TableSchemaFactory.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("sch-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws IOException, SQLException {
      String action = opts.removeProperty("action", "a", (String)null);
      Configurations.populateConfiguration(conf, opts);
      return run(conf, action);
   }

   public static boolean run(JDBCConfiguration conf, String action) throws IOException, SQLException {
      TableSchemaFactory factory = new TableSchemaFactory();
      String props = Configurations.getProperties(conf.getSchemaFactory());
      Configurations.configureInstance(factory, conf, (String)props);
      if ("drop".equals(action)) {
         factory.dropTable();
      } else {
         if (!"add".equals(action)) {
            return false;
         }

         factory.refreshTable();
      }

      return true;
   }
}
