package kodo.jdo.jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
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
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.GeneralException;
import serp.util.Strings;

public class TableDeprecatedJDOMappingFactory extends AbstractDeprecatedJDOMappingFactory implements Configurable {
   public static final String ACTION_ADD = "add";
   public static final String ACTION_DROP = "drop";
   private static final String SINGLE_PK = "all";
   private static final Localizer _loc = Localizer.forPackage(TableDeprecatedJDOMappingFactory.class);
   private static boolean _refreshedTable = false;
   private JDBCConfiguration _conf = null;
   private AbstractDeprecatedJDOMappingFactory.MappingAttrsParser _parser = null;
   private boolean _readWarned = false;
   private boolean _dropWarned = false;
   private boolean _singleRow = false;
   private boolean _parsed = false;
   private String _table = "JDO_MAPPING";
   private String _nameColumnName = "NAME";
   private String _mapColumnName = "MAPPING_DEF";
   private Column _nameColumn = null;
   private Column _mapColumn = null;

   public boolean isSingleRow() {
      return this._singleRow;
   }

   public void setSingleRow(boolean single) {
      this._singleRow = single;
   }

   public String getTableName() {
      return this._table;
   }

   public void setTableName(String name) {
      this._table = name;
   }

   public void setNameColumn(String name) {
      this._nameColumnName = name;
   }

   public String getNameColumn() {
      return this._nameColumnName;
   }

   public void setMappingColumn(String name) {
      this._mapColumnName = name;
   }

   public String getMappingColumn() {
      return this._mapColumnName;
   }

   public void clear() {
      super.clear();
      if (this._parser != null) {
         this._parser.clear();
      }

      this._parsed = false;
      this._readWarned = false;
   }

   public Set getPersistentTypeNames(boolean devpath, ClassLoader envLoader) {
      return null;
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (mode != 2) {
         super.load(cls, mode & -3, envLoader);
      }

      if (cls != null && (mode & 2) != 0) {
         if (this._parser == null) {
            this._parser = this.newMappingParser();
         }

         this._parsed = this.parseMapping(cls.getName(), this._parser, this._parsed);
         AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs cattrs = this._parser.getRepository().getMapping(cls.getName(), false);
         if (cattrs != null) {
            ClassMetaData meta = this.repos.getCachedMetaData(cls);
            if (meta == null) {
               if ((mode & 1) != 0) {
                  return;
               }

               meta = this.repos.addMetaData(cls);
               meta.setEnvClassLoader(envLoader);
            }

            ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
            this.fromMappingAttrs((ClassMapping)meta, cattrs, loader);
         }
      }
   }

   private boolean parseMapping(String name, AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser, boolean parsed) {
      if (this._singleRow && parsed) {
         return true;
      } else {
         if (this._singleRow) {
            name = "all";
         }

         String mapping = null;

         try {
            mapping = this.readMappingColumn(name);
            if (this._singleRow) {
               parsed = true;
            }
         } catch (SQLException var7) {
            if (!this._readWarned && this.log.isWarnEnabled()) {
               this.log.warn(_loc.get("bad-mapping-read", var7));
            }

            this._readWarned = true;
         }

         if (mapping != null && mapping.length() != 0) {
            try {
               parser.parse(new StringReader(mapping), name);
               return parsed;
            } catch (IOException var6) {
               throw new GeneralException(var6);
            }
         } else {
            return parsed;
         }
      }
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      if (mode != 2 && !super.store(metas, queries, seqs, mode & -3, output)) {
         return false;
      } else if ((mode & 2) == 0) {
         return true;
      } else {
         AbstractDeprecatedJDOMappingFactory.MappingAttrsSerializer ser = this.newMappingSerializer();
         if (output != null) {
            for(int i = 0; i < metas.length; ++i) {
               ser.getRepository().addMapping(this.toMappingAttrs((ClassMapping)metas[i]));
            }

            this.serialize(ser, output, 1);
            return true;
         } else {
            AbstractDeprecatedJDOMappingFactory.MappingAttrsRepository arepos = new AbstractDeprecatedJDOMappingFactory.MappingAttrsRepository();
            if (this._singleRow) {
               AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser = this.newMappingParser();
               parser.setRepository(arepos);
               this.parseMapping((String)null, parser, false);
            }

            for(int i = 0; i < metas.length; ++i) {
               arepos.addMapping(this.toMappingAttrs((ClassMapping)metas[i]));
            }

            String mapping;
            if (this._singleRow) {
               ser.setRepository(arepos);
               mapping = this.serializeToString(ser);
               this.writeMapping("all", mapping);
            } else {
               AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs[] cattrs = arepos.getMappings();

               for(int i = 0; i < cattrs.length; ++i) {
                  ser.getRepository().addMapping(cattrs[i]);
                  mapping = this.serializeToString(ser);
                  this.writeMapping(cattrs[i].name, mapping);
                  ser.getRepository().removeMapping(cattrs[i].name);
               }
            }

            return true;
         }
      }
   }

   private String serializeToString(AbstractDeprecatedJDOMappingFactory.MappingAttrsSerializer ser) {
      Writer writer = new StringWriter();

      try {
         ser.serialize(writer, 0);
      } catch (IOException var4) {
         throw new GeneralException(var4);
      }

      return writer.toString();
   }

   private void writeMapping(String name, String str) {
      try {
         this.writeMappingColumn(name, str);
      } catch (SQLException var10) {
         SQLException se = var10;
         if (this.log.isWarnEnabled()) {
            this.log.warn(_loc.get("bad-mapping-write-1", var10));
         }

         Class var4 = TableDeprecatedJDOMappingFactory.class;
         synchronized(TableDeprecatedJDOMappingFactory.class) {
            if (!_refreshedTable) {
               _refreshedTable = true;

               try {
                  this.refreshTable();
               } catch (Exception var8) {
                  if (this.log.isWarnEnabled()) {
                     this.log.warn(_loc.get("bad-mapping-ref", var8));
                  }

                  if (this.log.isTraceEnabled()) {
                     this.log.trace(var8);
                  }
               }
            }

            try {
               this.writeMappingColumn(name, str);
            } catch (Exception var7) {
               if (this.log.isWarnEnabled()) {
                  this.log.warn(_loc.get("bad-mapping-write-2"));
               }

               throw SQLExceptions.getStore(se, this._conf.getDBDictionaryInstance());
            }
         }
      }

   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      boolean drop = true;
      if (mode != 2) {
         drop = super.drop(cls, mode & -3, envLoader);
      }

      if ((mode & 2) == 0) {
         return drop;
      } else if (!this._singleRow) {
         for(int i = 0; i < cls.length; ++i) {
            this.deleteMapping(cls[i].getName());
         }

         return drop;
      } else {
         AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser = this.newMappingParser();
         this.parseMapping((String)null, parser, false);

         for(int i = 0; i < cls.length; ++i) {
            parser.getRepository().removeMapping(cls[i].getName());
         }

         AbstractDeprecatedJDOMappingFactory.MappingAttrsSerializer ser = this.newMappingSerializer();
         ser.setRepository(parser.getRepository());
         this.writeMapping("all", this.serializeToString(ser));
         return drop;
      }
   }

   private void deleteMapping(String name) {
      try {
         this.deleteMappingRow(name);
      } catch (SQLException var3) {
         if (!this._dropWarned && this.log.isWarnEnabled()) {
            this.log.warn(_loc.get("bad-mapping-drop", var3));
         }

         this._dropWarned = true;
      }

   }

   public void refreshTable() throws SQLException {
      if (this.log.isInfoEnabled()) {
         this.log.info(_loc.get("make-mapping-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.createTable(this._mapColumn.getTable());
   }

   public void dropTable() throws SQLException {
      if (this.log.isInfoEnabled()) {
         this.log.info(_loc.get("drop-mapping-table"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.dropTable(this._mapColumn.getTable());
   }

   public String readMappingColumn(String name) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      SQLBuffer sel = (new SQLBuffer(dict)).append(this._mapColumn);
      SQLBuffer where = (new SQLBuffer(dict)).append(this._nameColumn).append(" = ").appendValue(name, this._nameColumn);
      SQLBuffer tables = (new SQLBuffer(dict)).append(this._mapColumn.getTable());
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
         if (!rs.next()) {
            var11 = null;
            return var11;
         }

         var11 = this._mapColumn.getType() == 2005 ? dict.getClobString(rs, 1) : dict.getString(rs, 1);
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var27) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var26) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var25) {
         }

      }

      return var11;
   }

   public void writeMappingColumn(String name, String mapping) throws SQLException {
      boolean embedded = this._conf.getDBDictionaryInstance().maxEmbeddedClobSize == -1;
      if (!this.writeMappingColumn(name, mapping, embedded)) {
         this.insertNullMapping(name);
         this.writeMappingColumn(name, mapping, embedded);
      }

   }

   private void insertNullMapping(String name) throws SQLException {
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      boolean wasAuto = true;

      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         DBDictionary dict = this._conf.getDBDictionaryInstance();
         stmnt = conn.prepareStatement("INSERT INTO " + dict.getFullName(this._nameColumn.getTable(), false) + " (" + this._nameColumn + ", " + this._mapColumn + ") " + "VALUES (?, ?)");
         dict.setString(stmnt, 1, name, this._nameColumn);
         dict.setNull(stmnt, 2, this._mapColumn.getType(), this._mapColumn);
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

   private boolean writeMappingColumn(String name, String mapping, boolean embedded) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      String update;
      if (embedded) {
         update = "UPDATE " + dict.getFullName(this._mapColumn.getTable(), false) + " SET " + this._mapColumn + " = ?  WHERE " + this._nameColumn + " = ?";
      } else {
         update = "SELECT " + this._mapColumn + " FROM " + dict.getFullName(this._mapColumn.getTable(), false) + " WHERE " + this._nameColumn + " = ?";
         if (dict.forUpdateClause != null && dict.supportsSelectForUpdate) {
            update = update + " " + dict.forUpdateClause;
         }
      }

      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;
      boolean wasAuto = true;

      boolean var11;
      try {
         wasAuto = conn.getAutoCommit();
         if (wasAuto != embedded) {
            conn.setAutoCommit(embedded);
         }

         boolean ret;
         if (embedded) {
            stmnt = conn.prepareStatement(update);
            if (mapping == null) {
               dict.setNull(stmnt, 1, this._mapColumn.getType(), this._mapColumn);
            } else if (this._mapColumn.getType() == 2005) {
               dict.setClobString(stmnt, 1, mapping, this._mapColumn);
            } else {
               dict.setString(stmnt, 1, mapping, this._mapColumn);
            }

            dict.setString(stmnt, 2, name, this._nameColumn);
            ret = stmnt.executeUpdate() != 0;
         } else {
            stmnt = conn.prepareStatement(update, 1004, 1008);
            dict.setString(stmnt, 1, name, this._nameColumn);
            rs = stmnt.executeQuery();
            ret = rs.next();
            if (ret) {
               dict.putString(rs.getClob(1), mapping);
            }

            conn.commit();
         }

         var11 = ret;
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

         if (wasAuto != embedded) {
            conn.setAutoCommit(wasAuto);
         }

         try {
            conn.close();
         } catch (SQLException var22) {
         }

      }

      return var11;
   }

   public int deleteMappingRow(String name) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      StringBuffer del = (new StringBuffer("DELETE FROM ")).append(dict.getFullName(this._mapColumn.getTable(), false)).append(" WHERE ").append(this._nameColumn).append(" = ?");
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      boolean wasAuto = true;

      int var7;
      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         stmnt = conn.prepareStatement(del.toString());
         dict.setString(stmnt, 1, name, this._nameColumn);
         var7 = stmnt.executeUpdate();
      } finally {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var17) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var16) {
         }

      }

      return var7;
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
      this._nameColumn = table.addColumn(dict.getValidColumnName(this._nameColumnName, table));
      this._nameColumn.setType(dict.getPreferredType(12));
      this._nameColumn.setJavaType(9);
      this._nameColumn.setSize(dict.characterColumnSize);
      pk.addColumn(this._nameColumn);
      this._mapColumn = table.addColumn(dict.getValidColumnName(this._mapColumnName, table));
      this._mapColumn.setType(dict.getPreferredType(2005));
      this._mapColumn.setJavaType(9);
   }

   private Connection getConnection() throws SQLException {
      return this._conf.getDataSource2((StoreContext)null).getConnection();
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      this.buildTable();
   }

   public static void main(String[] args) throws IOException, SQLException {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      JDBCConfiguration conf = new JDBCConfigurationImpl();

      try {
         if (!run(conf, args, opts)) {
            System.out.println(_loc.get("dep-mappingtable-usage"));
         }
      } finally {
         conf.close();
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws IOException, SQLException {
      if (!opts.containsKey("help") && !opts.containsKey("-help")) {
         String action = opts.removeProperty("action", "a", (String)null);
         Configurations.populateConfiguration(conf, opts);
         return run(conf, action);
      } else {
         return false;
      }
   }

   public static boolean run(JDBCConfiguration conf, String action) throws IOException, SQLException {
      TableDeprecatedJDOMappingFactory factory = new TableDeprecatedJDOMappingFactory();
      String props = Configurations.getProperties(conf.getMapping());
      Configurations.configureInstance(factory, conf, props);
      factory.setRepository(conf.getMetaDataRepositoryInstance());
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
