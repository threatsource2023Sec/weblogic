package kodo.jdo.jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kodo.jdo.JDOMetaDataParser;
import kodo.jdo.JDOMetaDataSerializer;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.meta.MappingRepository;
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
import org.apache.openjpa.meta.AbstractMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.InternalException;
import serp.util.Numbers;
import serp.util.Strings;

public class TableJDORMappingFactory extends AbstractMetaDataFactory implements Configurable {
   public static final String ACTION_ADD = "add";
   public static final String ACTION_DROP = "drop";
   public static final int TYPE_CLASS = 0;
   public static final int TYPE_SEQUENCE = 1;
   public static final int TYPE_QUERY = 2;
   public static final int TYPE_CLASS_QUERY = 3;
   private static final Localizer _loc = Localizer.forPackage(TableJDORMappingFactory.class);
   private static boolean _refreshedTable = false;
   private JDBCConfiguration _conf = null;
   private JDORMetaDataParser _parser = null;
   private boolean _seqsRead = false;
   private boolean _queriesRead = false;
   private boolean _readWarned = false;
   private boolean _dropWarned = false;
   private boolean _cnames = false;
   private boolean _useSchema = false;
   private String _table = "KODO_JDO_MAPPINGS";
   private String _nameColumnName = "NAME";
   private String _typeColumnName = "MAPPING_TYPE";
   private String _mapColumnName = "MAPPING_DEF";
   private Column _nameColumn = null;
   private Column _mapColumn = null;
   private Column _typeColumn = null;

   public String getTable() {
      return this._table;
   }

   public void setTable(String name) {
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

   public void setTypeColumn(String name) {
      this._typeColumnName = name;
   }

   public String getTypeColumn() {
      return this._typeColumnName;
   }

   public boolean getConstraintNames() {
      return this._cnames;
   }

   public void setConstraintNames(boolean cnames) {
      this._cnames = cnames;
   }

   public boolean useSchemaValidation() {
      return this._useSchema;
   }

   public void setUseSchemaValidation(boolean useSchema) {
      this._useSchema = useSchema;
   }

   public JDORMetaDataParser getParser() {
      if (this._parser == null) {
         this._parser = this.newParser();
         this._parser.setMappingOverride(((MappingRepository)this.repos).getStrategyInstaller().isAdapting());
         this._parser.setUseSchemaValidation(this._useSchema);
         this._parser.setRepository(this.repos);
      }

      return this._parser;
   }

   public void setParser(JDORMetaDataParser parser) {
      if (this._parser != null) {
         this._parser.setRepository((MetaDataRepository)null);
      }

      if (parser != null) {
         parser.setRepository(this.repos);
      }

      this._parser = parser;
   }

   private JDORMetaDataParser newParser() {
      return new JDORMetaDataParser((JDBCConfiguration)this.repos.getConfiguration());
   }

   private JDORMetaDataSerializer newSerializer() {
      JDORMetaDataSerializer ser = new JDORMetaDataSerializer((JDBCConfiguration)this.repos.getConfiguration());
      ser.setConstraintNames(this._cnames);
      ser.setSyncMappingInfo(true);
      return ser;
   }

   public void clear() {
      this._seqsRead = false;
      this._queriesRead = false;
      this._readWarned = false;
   }

   public void loadXMLMetaData(FieldMetaData fieldMetaData) {
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (cls != null && (mode & 2) != 0) {
         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
         JDOMetaDataParser parser = this.getParser();
         parser.setClassLoader(loader);
         parser.setEnvClassLoader(envLoader);
         List mapping;
         if (!this._queriesRead && (mode & 4) != 0) {
            parser.setTrackResults(true);

            try {
               this._queriesRead = true;
               mapping = this.readMapping((String)null, 2, false);
               parser.setMode(4);
               this.parse(parser, mapping, "Query");
               this.setSourceMode(parser.getResults());
            } finally {
               parser.getResults().clear();
               parser.setTrackResults(false);
            }
         }

         if (cls != null) {
            if (!this._seqsRead) {
               this._seqsRead = true;
               mapping = this.readMapping((String)null, 1, false);
               parser.setMode(2);
               this.parse(parser, mapping, "Sequence");
            }

            if ((mode & 4) != 0) {
               parser.setTrackResults(true);

               try {
                  String name = cls.getName() + ".";
                  List queries = this.readMapping(name, 3, true);
                  parser.setMode(4);
                  this.parse(parser, queries, name + "Query");
                  this.setSourceMode(parser.getResults());
               } finally {
                  parser.getResults().clear();
                  parser.setTrackResults(false);
               }
            }

            mapping = this.readMapping(cls.getName(), 0, false);
            parser.setMode(2);
            this.parse(parser, mapping, cls.getName());
         }
      }
   }

   private List readMapping(String name, int type, boolean startsWith) {
      try {
         return this.readMappingColumn(name, type, startsWith);
      } catch (SQLException var5) {
         if (!this._readWarned && this.log.isWarnEnabled()) {
            this.log.warn(_loc.get("bad-mapping-read", var5));
         }

         this._readWarned = true;
         return Collections.EMPTY_LIST;
      }
   }

   private void parse(JDOMetaDataParser parser, List strings, String name) {
      try {
         for(int i = 0; i < strings.size(); ++i) {
            parser.parse(new StringReader((String)strings.get(i)), name + i);
         }

      } catch (IOException var5) {
         throw new GeneralException(var5);
      }
   }

   private void setSourceMode(List queries) {
      for(int i = 0; i < queries.size(); ++i) {
         ((QueryMetaData)queries.get(i)).setSourceMode(2);
      }

   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      if ((mode & 2) == 0) {
         return true;
      } else {
         JDOMetaDataSerializer ser = this.newSerializer();
         if (output != null) {
            ser.setMode(6);

            int i;
            for(i = 0; i < metas.length; ++i) {
               ser.addMetaData(metas[i]);
            }

            for(i = 0; i < seqs.length; ++i) {
               ser.addSequenceMetaData(seqs[i]);
            }

            for(i = 0; i < queries.length; ++i) {
               if (queries[i].getSourceMode() == 2) {
                  ser.addQueryMetaData(queries[i]);
               }
            }

            try {
               ser.serialize(output, 1);
               return true;
            } catch (IOException var11) {
               throw new GeneralException(var11);
            }
         } else {
            ser.setMode(2);

            String str;
            for(int i = 0; i < metas.length; ++i) {
               ser.addMetaData(metas[i]);
               str = this.serializeToString(ser);
               this.writeMapping(metas[i].getDescribedType().getName(), 0, str);
               ser.removeMetaData(metas[i]);
            }

            ser.setMode(4);

            int i;
            for(i = 0; i < queries.length; ++i) {
               if (queries[i].getSourceMode() == 2) {
                  String name = queries[i].getName();
                  byte type;
                  if (queries[i].getDefiningType() == null) {
                     type = 2;
                  } else {
                     type = 3;
                     name = queries[i].getDefiningType().getName() + "." + name;
                  }

                  ser.addQueryMetaData(queries[i]);
                  str = this.serializeToString(ser);
                  this.writeMapping(name, type, str);
                  ser.removeQueryMetaData(queries[i]);
               }
            }

            ser.setMode(2);

            for(i = 0; i < seqs.length; ++i) {
               ser.addSequenceMetaData(seqs[i]);
               str = this.serializeToString(ser);
               this.writeMapping(seqs[i].getName(), 1, str);
               ser.removeSequenceMetaData(seqs[i]);
            }

            return true;
         }
      }
   }

   private String serializeToString(JDOMetaDataSerializer ser) {
      Writer writer = new StringWriter();

      try {
         ser.serialize(writer, 0);
      } catch (IOException var4) {
         throw new GeneralException(var4);
      }

      return writer.toString();
   }

   private void writeMapping(String name, int type, String str) {
      try {
         this.writeMappingColumn(name, type, str);
      } catch (SQLException var11) {
         SQLException se = var11;
         if (this.log.isWarnEnabled()) {
            this.log.warn(_loc.get("bad-mapping-write-1", var11));
         }

         Class var5 = TableJDORMappingFactory.class;
         synchronized(TableJDORMappingFactory.class) {
            if (!_refreshedTable) {
               _refreshedTable = true;

               try {
                  this.refreshTable();
               } catch (Exception var9) {
                  if (this.log.isWarnEnabled()) {
                     this.log.warn(_loc.get("bad-mapping-ref", var9));
                  }

                  if (this.log.isTraceEnabled()) {
                     this.log.trace(var9);
                  }
               }
            }

            try {
               this.writeMappingColumn(name, type, str);
            } catch (Exception var8) {
               if (this.log.isWarnEnabled()) {
                  this.log.warn(_loc.get("bad-mapping-write-2"));
               }

               throw SQLExceptions.getStore(se, this._conf.getDBDictionaryInstance());
            }
         }
      }

   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      if ((mode & 2) == 0) {
         return true;
      } else {
         for(int i = 0; i < cls.length; ++i) {
            this.deleteMapping(cls[i].getName(), 0, false);
            this.deleteMapping(cls[i].getName() + ".", 3, true);
         }

         return true;
      }
   }

   private void deleteMapping(String name, int type, boolean startsWith) {
      try {
         this.deleteMappingRow(name, type, startsWith);
      } catch (SQLException var5) {
         if (!this._dropWarned && this.log.isWarnEnabled()) {
            this.log.warn(_loc.get("bad-mapping-drop", var5));
         }

         this._dropWarned = true;
      }

   }

   public MetaDataDefaults getDefaults() {
      throw new InternalException();
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

   public List readMappingColumn(String name, int type, boolean startsWith) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      SQLBuffer sel = (new SQLBuffer(dict)).append(this._mapColumn);
      SQLBuffer where = new SQLBuffer(dict);
      if (name != null) {
         where.append(this._nameColumn);
         if (startsWith) {
            where.append(" LIKE ");
            name = name + "%";
         } else {
            where.append(" = ");
         }

         where.appendValue(name, this._nameColumn);
         where.append(" AND ");
      }

      where.append(this._typeColumn).append(" = ").appendValue(Numbers.valueOf(type), this._typeColumn);
      SQLBuffer tables = (new SQLBuffer(dict)).append(this._mapColumn.getTable());
      SQLBuffer select = dict.toSelect(sel, (JDBCFetchConfiguration)null, tables, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
      List results = new ArrayList(3);
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;
      boolean wasAuto = true;

      ArrayList var15;
      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         stmnt = select.prepareStatement(conn);
         rs = stmnt.executeQuery();

         while(rs.next()) {
            String result = this._mapColumn.getType() == 2005 ? dict.getClobString(rs, 1) : dict.getString(rs, 1);
            if (result != null) {
               results.add(result);
            }
         }

         var15 = results;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var28) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var27) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var26) {
         }

      }

      return var15;
   }

   public void writeMappingColumn(String name, int type, String mapping) throws SQLException {
      boolean embedded = this._conf.getDBDictionaryInstance().maxEmbeddedClobSize == -1;
      if (!this.writeMappingColumn(name, type, mapping, embedded)) {
         this.insertNullMapping(name, type);
         this.writeMappingColumn(name, type, mapping, embedded);
      }

   }

   private void insertNullMapping(String name, int type) throws SQLException {
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      boolean wasAuto = true;

      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         DBDictionary dict = this._conf.getDBDictionaryInstance();
         stmnt = conn.prepareStatement("INSERT INTO " + dict.getFullName(this._nameColumn.getTable(), false) + " (" + this._nameColumn + ", " + this._typeColumn + ", " + this._mapColumn + ") VALUES (?, ?, ?)");
         dict.setString(stmnt, 1, name, this._nameColumn);
         dict.setInt(stmnt, 2, type, this._typeColumn);
         dict.setNull(stmnt, 3, this._mapColumn.getType(), this._mapColumn);
         stmnt.executeUpdate();
      } finally {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var15) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var14) {
         }

      }

   }

   private boolean writeMappingColumn(String name, int type, String mapping, boolean embedded) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      String update;
      if (embedded) {
         update = "UPDATE " + dict.getFullName(this._mapColumn.getTable(), false) + " SET " + this._mapColumn + " = ?  WHERE " + this._nameColumn + " = ?" + " AND " + this._typeColumn + " = ?";
      } else {
         update = "SELECT " + this._mapColumn + " FROM " + dict.getFullName(this._mapColumn.getTable(), false) + " WHERE " + this._nameColumn + " = ? AND " + this._typeColumn + " = ?";
         if (dict.forUpdateClause != null && dict.supportsSelectForUpdate) {
            update = update + " " + dict.forUpdateClause;
         }
      }

      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;
      boolean wasAuto = true;

      boolean var12;
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
            dict.setInt(stmnt, 3, type, this._typeColumn);
            ret = stmnt.executeUpdate() != 0;
         } else {
            stmnt = conn.prepareStatement(update, 1004, 1008);
            dict.setString(stmnt, 1, name, this._nameColumn);
            dict.setInt(stmnt, 2, type, this._typeColumn);
            rs = stmnt.executeQuery();
            ret = rs.next();
            if (ret) {
               dict.putString(rs.getClob(1), mapping);
            }

            conn.commit();
         }

         var12 = ret;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var25) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var24) {
            }
         }

         if (wasAuto != embedded) {
            conn.setAutoCommit(wasAuto);
         }

         try {
            conn.close();
         } catch (SQLException var23) {
         }

      }

      return var12;
   }

   public int deleteMappingRow(String name, int type, boolean startsWith) throws SQLException {
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      StringBuffer del = (new StringBuffer("DELETE FROM ")).append(dict.getFullName(this._mapColumn.getTable(), false)).append(" WHERE ");
      if (name != null) {
         del.append(this._nameColumn);
         if (startsWith) {
            del.append(" LIKE ?");
            name = name + "%";
         } else {
            del.append(" = ?");
         }

         del.append(" AND ");
      }

      del.append(this._typeColumn).append(" = ?");
      Connection conn = this.getConnection();
      PreparedStatement stmnt = null;
      boolean wasAuto = true;

      int var10;
      try {
         wasAuto = conn.getAutoCommit();
         if (!wasAuto) {
            conn.setAutoCommit(true);
         }

         stmnt = conn.prepareStatement(del.toString());
         int idx = 1;
         if (name != null) {
            dict.setString(stmnt, idx++, name, this._nameColumn);
         }

         dict.setInt(stmnt, idx, type, this._typeColumn);
         var10 = stmnt.executeUpdate();
      } finally {
         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var20) {
            }
         }

         if (!wasAuto) {
            conn.setAutoCommit(false);
         }

         try {
            conn.close();
         } catch (SQLException var19) {
         }

      }

      return var10;
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
      this._typeColumn = table.addColumn(dict.getValidColumnName(this._typeColumnName, table));
      this._typeColumn.setType(dict.getPreferredType(-6));
      this._typeColumn.setJavaType(5);
      pk.addColumn(this._typeColumn);
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
            System.out.println(_loc.get("mappingtable-usage"));
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
      TableJDORMappingFactory factory = new TableJDORMappingFactory();
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
