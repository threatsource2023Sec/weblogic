package org.apache.openjpa.jdbc.meta;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCSeq;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.DynamicSchemaFactory;
import org.apache.openjpa.jdbc.schema.LazySchemaFactory;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.SchemaSerializer;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.XMLSchemaSerializer;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.lib.util.Services;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class MappingTool implements MetaDataModes {
   public static final String SCHEMA_ACTION_NONE = "none";
   public static final String ACTION_ADD = "add";
   public static final String ACTION_REFRESH = "refresh";
   public static final String ACTION_BUILD_SCHEMA = "buildSchema";
   public static final String ACTION_DROP = "drop";
   public static final String ACTION_VALIDATE = "validate";
   public static final String ACTION_EXPORT = "export";
   public static final String ACTION_IMPORT = "import";
   public static final String[] ACTIONS = new String[]{"add", "refresh", "buildSchema", "drop", "validate", "export", "import"};
   private static final Localizer _loc = Localizer.forPackage(MappingTool.class);
   private final JDBCConfiguration _conf;
   private final Log _log;
   private final String _action;
   private final boolean _meta;
   private final int _mode;
   private final DBDictionary _dict;
   private MappingRepository _repos = null;
   private SchemaGroup _schema = null;
   private SchemaTool _schemaTool = null;
   private String _schemaActions = "add";
   private boolean _readSchema = false;
   private boolean _pks = false;
   private boolean _fks = false;
   private boolean _indexes = false;
   private boolean _seqs = true;
   private boolean _dropUnused = true;
   private boolean _ignoreErrors = false;
   private File _file = null;
   private Writer _mappingWriter = null;
   private Writer _schemaWriter = null;
   private Set _dropCls = null;
   private Set _dropMap = null;
   private boolean _flush = false;
   private boolean _flushSchema = false;

   public MappingTool(JDBCConfiguration conf, String action, boolean meta) {
      this._conf = conf;
      this._log = conf.getLog("openjpa.MetaData");
      this._meta = meta;
      if (action == null) {
         this._action = "refresh";
      } else {
         if (!Arrays.asList(ACTIONS).contains(action)) {
            throw new IllegalArgumentException("action == " + action);
         }

         this._action = action;
      }

      if (meta && "add".equals(this._action)) {
         this._mode = 1;
      } else if (meta && "drop".equals(this._action)) {
         this._mode = 7;
      } else {
         this._mode = 2;
      }

      this._dict = this._conf.getDBDictionaryInstance();
   }

   public String getAction() {
      return this._action;
   }

   public boolean isMetaDataAction() {
      return this._meta;
   }

   public String getSchemaAction() {
      return this._schemaActions;
   }

   public void setSchemaAction(String schemaAction) {
      this._schemaActions = schemaAction;
   }

   public boolean getReadSchema() {
      return this._readSchema;
   }

   public void setReadSchema(boolean readSchema) {
      this._readSchema = readSchema;
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

   public boolean getDropUnusedComponents() {
      return this._dropUnused;
   }

   public void setDropUnusedComponents(boolean dropUnused) {
      this._dropUnused = dropUnused;
   }

   public void setIgnoreErrors(boolean ignoreErrors) {
      this._ignoreErrors = ignoreErrors;
   }

   public boolean getIgnoreErrors() {
      return this._ignoreErrors;
   }

   private SchemaTool newSchemaTool(String action) {
      if ("none".equals(action)) {
         action = null;
      }

      SchemaTool tool = new SchemaTool(this._conf, action);
      tool.setIgnoreErrors(this.getIgnoreErrors());
      tool.setPrimaryKeys(this.getPrimaryKeys());
      tool.setForeignKeys(this.getForeignKeys());
      tool.setIndexes(this.getIndexes());
      tool.setSequences(this.getSequences());
      return tool;
   }

   public void setSchemaTool(SchemaTool tool) {
      this._schemaTool = tool;
   }

   public Writer getSchemaWriter() {
      return this._schemaWriter;
   }

   public void setSchemaWriter(Writer schemaWriter) {
      this._schemaWriter = schemaWriter;
   }

   public Writer getMappingWriter() {
      return this._mappingWriter;
   }

   public void setMappingWriter(Writer mappingWriter) {
      this._mappingWriter = mappingWriter;
   }

   public File getMetaDataFile() {
      return this._file;
   }

   public void setMetaDataFile(File file) {
      this._file = file;
   }

   public MappingRepository getRepository() {
      if (this._repos == null) {
         this._repos = this._conf.newMappingRepositoryInstance();
         this._repos.setSchemaGroup(this.getSchemaGroup());
         MappingRepository var10001 = this._repos;
         this._repos.setValidate(4, false);
      }

      return this._repos;
   }

   public void setRepository(MappingRepository repos) {
      this._repos = repos;
   }

   public SchemaGroup getSchemaGroup() {
      if (this._schema == null) {
         if (this._action.indexOf("buildSchema") != -1) {
            DynamicSchemaFactory factory = new DynamicSchemaFactory();
            factory.setConfiguration(this._conf);
            this._schema = factory;
         } else if (!this._readSchema && !contains(this._schemaActions, "retain") && !contains(this._schemaActions, "refresh")) {
            LazySchemaFactory factory = new LazySchemaFactory();
            factory.setConfiguration(this._conf);
            factory.setPrimaryKeys(this.getPrimaryKeys());
            factory.setForeignKeys(this.getForeignKeys());
            factory.setIndexes(this.getIndexes());
            this._schema = factory;
         } else {
            this._schema = (SchemaGroup)this.newSchemaTool((String)null).getDBSchemaGroup().clone();
         }

         if (this._schema.getSchemas().length == 0) {
            this._schema.addSchema();
         }
      }

      return this._schema;
   }

   public void setSchemaGroup(SchemaGroup schema) {
      this._schema = schema;
   }

   public void clear() {
      this._repos = null;
      this._schema = null;
      this._schemaTool = null;
      this._flush = false;
      this._flushSchema = false;
      if (this._dropCls != null) {
         this._dropCls.clear();
      }

      if (this._dropMap != null) {
         this._dropMap.clear();
      }

   }

   public void record() {
      this.record((Flags)null);
   }

   private void record(Flags flags) {
      MappingRepository repos = this.getRepository();
      MetaDataFactory io = repos.getMetaDataFactory();
      ClassMapping[] mappings;
      if (!"drop".equals(this._action)) {
         mappings = repos.getMappings();
      } else if (this._dropMap != null) {
         mappings = (ClassMapping[])((ClassMapping[])this._dropMap.toArray(new ClassMapping[this._dropMap.size()]));
      } else {
         mappings = new ClassMapping[0];
      }

      try {
         if (this._dropCls != null && !this._dropCls.isEmpty()) {
            Class[] cls = (Class[])((Class[])this._dropCls.toArray(new Class[this._dropCls.size()]));
            if (!io.drop(cls, this._mode, (ClassLoader)null)) {
               this._log.warn(_loc.get("bad-drop", (Object)this._dropCls));
            }
         }

         if (this._flushSchema) {
            if (this._dropUnused) {
               this.dropUnusedSchemaComponents(mappings);
            }

            this.addSequenceComponents(mappings);
            String[] schemaActions = this._schemaActions.split(",");
            int i = 0;

            while(true) {
               if (i >= schemaActions.length) {
                  if (this._schemaWriter != null) {
                     SchemaSerializer ser = new XMLSchemaSerializer(this._conf);
                     ser.addAll(this.getSchemaGroup());
                     ser.serialize(this._schemaWriter, 1);
                     this._schemaWriter.flush();
                  }
                  break;
               }

               if (!"none".equals(schemaActions[i]) && (this._schemaWriter == null || this._schemaTool != null && this._schemaTool.getWriter() != null)) {
                  SchemaTool tool = this.newSchemaTool(schemaActions[i]);
                  if (flags != null) {
                     tool.setDropTables(flags.dropTables);
                     tool.setDropSequences(flags.dropSequences);
                     tool.setWriter(flags.sqlWriter);
                     tool.setOpenJPATables(flags.openjpaTables);
                  }

                  tool.setSchemaGroup(this.getSchemaGroup());
                  tool.run();
                  tool.record();
               }

               ++i;
            }
         }

         if (this._flush) {
            QueryMetaData[] queries = repos.getQueryMetaDatas();
            SequenceMetaData[] seqs = repos.getSequenceMetaDatas();
            Map output = null;
            if (this._mappingWriter != null) {
               output = new HashMap();
               File tmp = new File("openjpatmp");

               int i;
               for(i = 0; i < mappings.length; ++i) {
                  ClassMapping var10000 = mappings[i];
                  ClassMapping var10002 = mappings[i];
                  var10000.setSource(tmp, 0);
               }

               Object var26;
               for(i = 0; i < queries.length; ++i) {
                  QueryMetaData var24 = queries[i];
                  var26 = queries[i].getSourceScope();
                  QueryMetaData var10003 = queries[i];
                  var24.setSource(tmp, var26, 0);
               }

               for(i = 0; i < seqs.length; ++i) {
                  SequenceMetaData var25 = seqs[i];
                  var26 = seqs[i].getSourceScope();
                  SequenceMetaData var27 = seqs[i];
                  var25.setSource(tmp, var26, 0);
               }
            }

            if (!io.store(mappings, queries, seqs, this._mode, output)) {
               throw new MetaDataException(_loc.get("bad-store"));
            }

            if (this._mappingWriter == null) {
               return;
            }

            PrintWriter out = new PrintWriter(this._mappingWriter);
            Iterator itr = output.values().iterator();

            while(itr.hasNext()) {
               out.println((String)itr.next());
            }

            out.flush();
            return;
         }
      } catch (RuntimeException var14) {
         throw var14;
      } catch (Exception var15) {
         throw new GeneralException(var15);
      } finally {
         this.clear();
      }

   }

   private void dropUnusedSchemaComponents(ClassMapping[] mappings) {
      for(int i = 0; i < mappings.length; ++i) {
         mappings[i].refSchemaComponents();
         mappings[i].getDiscriminator().refSchemaComponents();
         mappings[i].getVersion().refSchemaComponents();
         FieldMapping[] fields = mappings[i].getDefinedFieldMappings();

         for(int j = 0; j < fields.length; ++j) {
            fields[j].refSchemaComponents();
         }
      }

      SchemaGroup group = this.getSchemaGroup();
      Schema[] schemas = group.getSchemas();

      for(int i = 0; i < schemas.length; ++i) {
         Table[] tables = schemas[i].getTables();

         for(int j = 0; j < tables.length; ++j) {
            this._dict.refSchemaComponents(tables[j]);
         }
      }

      group.removeUnusedComponents();
   }

   private void addSequenceComponents(ClassMapping[] mappings) {
      SchemaGroup group = this.getSchemaGroup();

      for(int i = 0; i < mappings.length; ++i) {
         this.addSequenceComponents(mappings[i], group);
      }

   }

   private void addSequenceComponents(ClassMapping mapping, SchemaGroup group) {
      SequenceMetaData smd = mapping.getIdentitySequenceMetaData();
      Seq seq = null;
      if (smd != null) {
         seq = smd.getInstance((ClassLoader)null);
      } else if (mapping.getIdentityStrategy() == 1 || mapping.getIdentityStrategy() == 0 && mapping.getIdentityType() == 1) {
         seq = this._conf.getSequenceInstance();
      }

      if (seq instanceof JDBCSeq) {
         ((JDBCSeq)seq).addSchema(mapping, group);
      }

      FieldMapping[] fmds;
      if (mapping.getEmbeddingMetaData() == null) {
         fmds = mapping.getDefinedFieldMappings();
      } else {
         fmds = mapping.getFieldMappings();
      }

      for(int i = 0; i < fmds.length; ++i) {
         smd = fmds[i].getValueSequenceMetaData();
         if (smd != null) {
            seq = smd.getInstance((ClassLoader)null);
            if (seq instanceof JDBCSeq) {
               ((JDBCSeq)seq).addSchema(mapping, group);
            }
         } else if (fmds[i].getEmbeddedMapping() != null) {
            this.addSequenceComponents(fmds[i].getEmbeddedMapping(), group);
         }
      }

   }

   public void run(Class cls) {
      if ("add".equals(this._action)) {
         if (this._meta) {
            this.addMeta(cls);
         } else {
            this.add(cls);
         }
      } else if ("refresh".equals(this._action)) {
         this.refresh(cls);
      } else if ("buildSchema".equals(this._action)) {
         this.buildSchema(cls);
      } else if ("drop".equals(this._action)) {
         this.drop(cls);
      } else if ("validate".equals(this._action)) {
         this.validate(cls);
      }

   }

   private void add(Class cls) {
      if (cls != null) {
         MappingRepository repos = this.getRepository();
         repos.setStrategyInstaller(new MappingStrategyInstaller(repos));
         if (getMapping(repos, cls, true) != null) {
            this._flush = true;
            this._flushSchema = true;
         }

      }
   }

   private static ClassMapping getMapping(MappingRepository repos, Class cls, boolean validate) {
      ClassMapping mapping = repos.getMapping((Class)cls, (ClassLoader)null, false);
      if (mapping != null) {
         return mapping;
      } else if (validate && !cls.isInterface() && repos.getPersistenceAware(cls) == null) {
         throw new MetaDataException(_loc.get("no-meta", (Object)cls));
      } else {
         return null;
      }
   }

   private void addMeta(Class cls) {
      if (cls != null) {
         this._flush = true;
         MappingRepository repos = this.getRepository();
         repos.setResolve(2, false);
         MetaDataFactory factory = repos.getMetaDataFactory();
         factory.getDefaults().setIgnoreNonPersistent(false);
         factory.setStoreMode(2);
         ClassMetaData meta = repos.addMetaData(cls);
         FieldMetaData[] fmds = meta.getDeclaredFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (fmds[i].getDeclaredTypeCode() == 8 && fmds[i].getDeclaredType() != Object.class) {
               fmds[i].setDeclaredTypeCode(15);
            }
         }

         meta.setSource(this._file, meta.getSourceType());
         meta.setResolve(1, true);
      }
   }

   private void refresh(Class cls) {
      if (cls != null) {
         MappingRepository repos = this.getRepository();
         repos.setStrategyInstaller(new RefreshStrategyInstaller(repos));
         if (getMapping(repos, cls, true) != null) {
            this._flush = true;
            this._flushSchema = true;
         }

      }
   }

   private void validate(Class cls) {
      if (cls != null) {
         MappingRepository repos = this.getRepository();
         repos.setStrategyInstaller(new RuntimeStrategyInstaller(repos));
         if (getMapping(repos, cls, true) != null) {
            this._flushSchema = !contains(this._schemaActions, "none") && !contains(this._schemaActions, "add");
         }

      }
   }

   private void buildSchema(Class cls) {
      if (cls != null) {
         MappingRepository repos = this.getRepository();
         repos.setStrategyInstaller(new RuntimeStrategyInstaller(repos));
         if (getMapping(repos, cls, true) != null) {
            this._flushSchema = true;
            Schema[] schemas = this._schema.getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Table[] tables = schemas[i].getTables();

               for(int j = 0; j < tables.length; ++j) {
                  if (tables[j].getPrimaryKey() != null) {
                     tables[j].getPrimaryKey().setLogical(false);
                     Column[] cols = tables[j].getPrimaryKey().getColumns();

                     for(int k = 0; k < cols.length; ++k) {
                        cols[k].setNotNull(true);
                     }
                  }
               }
            }

         }
      }
   }

   private void drop(Class cls) {
      if (cls != null) {
         if (this._dropCls == null) {
            this._dropCls = new HashSet();
         }

         this._dropCls.add(cls);
         if (contains(this._schemaActions, "drop")) {
            MappingRepository repos = this.getRepository();
            repos.setStrategyInstaller(new RuntimeStrategyInstaller(repos));
            ClassMapping mapping = null;

            try {
               mapping = repos.getMapping((Class)cls, (ClassLoader)null, false);
            } catch (Exception var5) {
            }

            if (mapping != null) {
               this._flushSchema = true;
               if (this._dropMap == null) {
                  this._dropMap = new HashSet();
               }

               this._dropMap.add(mapping);
            } else {
               this._log.warn(_loc.get("no-drop-meta", (Object)cls));
            }

         }
      }
   }

   public static void main(String[] arguments) throws IOException, SQLException {
      Options opts = new Options();
      final String[] args = opts.setFromCmdLine(arguments);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws IOException, SQLException {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = MappingTool.run(conf, args, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.err.println(_loc.get("tool-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws IOException, SQLException {
      Flags flags = new Flags();
      flags.action = opts.removeProperty("action", "a", flags.action);
      flags.schemaAction = opts.removeProperty("schemaAction", "sa", flags.schemaAction);
      flags.dropTables = opts.removeBooleanProperty("dropTables", "dt", flags.dropTables);
      flags.openjpaTables = opts.removeBooleanProperty("openjpaTables", "ot", flags.openjpaTables);
      flags.dropSequences = opts.removeBooleanProperty("dropSequences", "dsq", flags.dropSequences);
      flags.readSchema = opts.removeBooleanProperty("readSchema", "rs", flags.readSchema);
      flags.primaryKeys = opts.removeBooleanProperty("primaryKeys", "pk", flags.primaryKeys);
      flags.indexes = opts.removeBooleanProperty("indexes", "ix", flags.indexes);
      flags.foreignKeys = opts.removeBooleanProperty("foreignKeys", "fk", flags.foreignKeys);
      flags.sequences = opts.removeBooleanProperty("sequences", "sq", flags.sequences);
      flags.ignoreErrors = opts.removeBooleanProperty("ignoreErrors", "i", flags.ignoreErrors);
      flags.meta = opts.removeBooleanProperty("meta", "m", flags.meta);
      String fileName = opts.removeProperty("file", "f", (String)null);
      String schemaFileName = opts.removeProperty("schemaFile", "sf", (String)null);
      String sqlFileName = opts.removeProperty("sqlFile", "sql", (String)null);
      String schemas = opts.removeProperty("s");
      if (schemas != null) {
         opts.setProperty("schemas", schemas);
      }

      Configurations.populateConfiguration(conf, opts);
      ClassLoader loader = conf.getClassResolverInstance().getClassLoader(MappingTool.class, (ClassLoader)null);
      if (flags.meta && "add".equals(flags.action)) {
         flags.metaDataFile = Files.getFile(fileName, loader);
      } else {
         flags.mappingWriter = Files.getWriter(fileName, loader);
      }

      flags.schemaWriter = Files.getWriter(schemaFileName, loader);
      flags.sqlWriter = Files.getWriter(sqlFileName, loader);
      return run(conf, args, flags, loader);
   }

   public static boolean run(JDBCConfiguration conf, String[] args, Flags flags, ClassLoader loader) throws IOException, SQLException {
      if (flags.action == null) {
         if (conf.getMappingDefaultsInstance().defaultMissingInfo()) {
            flags.action = "buildSchema";
         } else {
            flags.action = "refresh";
         }
      }

      Log log = conf.getLog("openjpa.Tool");
      Collection classes = null;
      int i;
      if (args.length == 0) {
         if ("import".equals(flags.action)) {
            return false;
         }

         log.info(_loc.get("running-all-classes"));
         classes = conf.getMappingRepositoryInstance().loadPersistentTypes(true, loader);
      } else {
         classes = new HashSet();
         ClassArgParser classParser = conf.getMetaDataRepositoryInstance().getMetaDataFactory().newClassArgParser();
         classParser.setClassLoader(loader);

         for(i = 0; args != null && i < args.length; ++i) {
            Class[] parsed = classParser.parseTypes(args[i]);
            ((Collection)classes).addAll(Arrays.asList(parsed));
         }
      }

      Class[] act = (Class[])((Class[])((Collection)classes).toArray(new Class[((Collection)classes).size()]));
      ImportExport[] instances;
      if ("export".equals(flags.action)) {
         instances = newImportExports();

         for(i = 0; i < instances.length; ++i) {
            if (instances[i].exportMappings(conf, act, flags.meta, log, flags.mappingWriter)) {
               return true;
            }
         }

         return false;
      } else if ("import".equals(flags.action)) {
         instances = newImportExports();

         for(i = 0; i < instances.length; ++i) {
            if (instances[i].importMappings(conf, act, args, flags.meta, log, loader)) {
               return true;
            }
         }

         return false;
      } else {
         MappingTool tool;
         try {
            tool = new MappingTool(conf, flags.action, flags.meta);
         } catch (IllegalArgumentException var9) {
            return false;
         }

         tool.setIgnoreErrors(flags.ignoreErrors);
         tool.setMetaDataFile(flags.metaDataFile);
         tool.setMappingWriter(flags.mappingWriter);
         tool.setSchemaAction(flags.schemaAction);
         tool.setSchemaWriter(flags.schemaWriter);
         tool.setReadSchema(flags.readSchema && !"validate".equals(flags.action));
         tool.setPrimaryKeys(flags.primaryKeys);
         tool.setForeignKeys(flags.foreignKeys);
         tool.setIndexes(flags.indexes);
         tool.setSequences(flags.sequences || flags.dropSequences);

         for(i = 0; i < act.length; ++i) {
            log.info(_loc.get("tool-running", act[i], flags.action));
            if (i == 0 && flags.readSchema) {
               log.info(_loc.get("tool-time"));
            }

            tool.run(act[i]);
         }

         log.info(_loc.get("tool-record"));
         tool.record(flags);
         return true;
      }
   }

   private static ImportExport[] newImportExports() {
      try {
         Class[] types = Services.getImplementorClasses(ImportExport.class);
         ImportExport[] instances = new ImportExport[types.length];

         for(int i = 0; i < types.length; ++i) {
            instances[i] = (ImportExport)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(types[i]));
         }

         return instances;
      } catch (Throwable var3) {
         Throwable t = var3;
         if (var3 instanceof PrivilegedActionException) {
            t = ((PrivilegedActionException)var3).getException();
         }

         throw new InternalException(_loc.get("importexport-instantiate"), (Throwable)t);
      }
   }

   private static boolean contains(String list, String key) {
      return list == null ? false : list.indexOf(key) != -1;
   }

   public interface ImportExport {
      boolean importMappings(JDBCConfiguration var1, Class[] var2, String[] var3, boolean var4, Log var5, ClassLoader var6) throws IOException;

      boolean exportMappings(JDBCConfiguration var1, Class[] var2, boolean var3, Log var4, Writer var5) throws IOException;
   }

   public static class Flags {
      public String action = null;
      public boolean meta = false;
      public String schemaAction = "add";
      public File metaDataFile = null;
      public Writer mappingWriter = null;
      public Writer schemaWriter = null;
      public Writer sqlWriter = null;
      public boolean ignoreErrors = false;
      public boolean readSchema = false;
      public boolean dropTables = false;
      public boolean openjpaTables = false;
      public boolean dropSequences = false;
      public boolean sequences = true;
      public boolean primaryKeys = false;
      public boolean foreignKeys = false;
      public boolean indexes = false;
   }
}
