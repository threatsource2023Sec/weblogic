package org.apache.openjpa.jdbc.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.enhance.ApplicationIdTool;
import org.apache.openjpa.enhance.CodeGenerator;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.strats.FullClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedBlobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedClobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.PrimitiveFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionInverseKeyFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.StateComparisonVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.StringFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.SubclassJoinDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.SuperclassDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.SuperclassVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.VerticalClassStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGenerator;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.SchemaParser;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.schema.XMLSchemaParser;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.CodeFormat;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.NoneMetaDataFactory;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.Project;
import serp.util.Strings;

public class ReverseMappingTool implements MetaDataModes, Cloneable {
   public static final int TABLE_NONE = 0;
   public static final int TABLE_BASE = 1;
   public static final int TABLE_SECONDARY = 2;
   public static final int TABLE_SECONDARY_OUTER = 3;
   public static final int TABLE_ASSOCIATION = 4;
   public static final int TABLE_SUBCLASS = 5;
   public static final String LEVEL_NONE = "none";
   public static final String LEVEL_PACKAGE = "package";
   public static final String LEVEL_CLASS = "class";
   public static final String ACCESS_TYPE_FIELD = "field";
   public static final String ACCESS_TYPE_PROPERTY = "property";
   private static Localizer _loc = Localizer.forPackage(ReverseMappingTool.class);
   private static final Map _javaKeywords = new HashMap();
   private final JDBCConfiguration _conf;
   private final Log _log;
   private final Map _tables = new HashMap();
   private final Project _project = new Project();
   private final BCClassLoader _loader;
   private StrategyInstaller _strat;
   private String _package;
   private File _dir;
   private MappingRepository _repos;
   private SchemaGroup _schema;
   private boolean _nullAsObj;
   private boolean _blobAsObj;
   private boolean _useGenericColl;
   private Properties _typeMap;
   private boolean _useFK;
   private boolean _useSchema;
   private boolean _pkOnJoin;
   private boolean _datastore;
   private boolean _builtin;
   private boolean _inner;
   private String _idSuffix;
   private boolean _inverse;
   private boolean _detachable;
   private boolean _genAnnotations;
   private String _accessType;
   private CodeFormat _format;
   private ReverseCustomizer _custom;
   private String _discStrat;
   private String _versStrat;
   private Set _abandonedFieldNames;
   private Map _annos;

   public ReverseMappingTool(JDBCConfiguration conf) {
      this._loader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(this._project));
      this._strat = null;
      this._package = null;
      this._dir = null;
      this._repos = null;
      this._schema = null;
      this._nullAsObj = false;
      this._blobAsObj = false;
      this._useGenericColl = false;
      this._typeMap = null;
      this._useFK = false;
      this._useSchema = false;
      this._pkOnJoin = false;
      this._datastore = false;
      this._builtin = true;
      this._inner = false;
      this._idSuffix = "Id";
      this._inverse = true;
      this._detachable = false;
      this._genAnnotations = false;
      this._accessType = "field";
      this._format = null;
      this._custom = null;
      this._discStrat = null;
      this._versStrat = null;
      this._abandonedFieldNames = null;
      this._annos = null;
      this._conf = conf;
      this._log = conf.getLog("openjpa.MetaData");
   }

   private StrategyInstaller getStrategyInstaller() {
      if (this._strat == null) {
         this._strat = new ReverseStrategyInstaller(this.getRepository());
      }

      return this._strat;
   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public Log getLog() {
      return this._log;
   }

   public String getPackageName() {
      return this._package;
   }

   public void setPackageName(String packageName) {
      this._package = StringUtils.trimToNull(packageName);
   }

   public File getDirectory() {
      return this._dir;
   }

   public void setDirectory(File dir) {
      this._dir = dir;
   }

   public boolean getUseSchemaName() {
      return this._useSchema;
   }

   public void setUseSchemaName(boolean useSchema) {
      this._useSchema = useSchema;
   }

   public boolean getUseForeignKeyName() {
      return this._useFK;
   }

   public void setUseForeignKeyName(boolean useFK) {
      this._useFK = useFK;
   }

   public boolean getNullableAsObject() {
      return this._nullAsObj;
   }

   public void setNullableAsObject(boolean nullAsObj) {
      this._nullAsObj = nullAsObj;
   }

   public boolean getBlobAsObject() {
      return this._blobAsObj;
   }

   public void setBlobAsObject(boolean blobAsObj) {
      this._blobAsObj = blobAsObj;
   }

   public boolean getUseGenericCollections() {
      return this._useGenericColl;
   }

   public void setUseGenericCollections(boolean useGenericCollections) {
      this._useGenericColl = useGenericCollections;
   }

   public Properties getTypeMap() {
      return this._typeMap;
   }

   public void setTypeMap(Properties typeMap) {
      this._typeMap = typeMap;
   }

   public boolean getPrimaryKeyOnJoin() {
      return this._pkOnJoin;
   }

   public void setPrimaryKeyOnJoin(boolean pkOnJoin) {
      this._pkOnJoin = pkOnJoin;
   }

   public boolean getUseDataStoreIdentity() {
      return this._datastore;
   }

   public void setUseDataStoreIdentity(boolean datastore) {
      this._datastore = datastore;
   }

   public boolean getUseBuiltinIdentityClass() {
      return this._builtin;
   }

   public void setUseBuiltinIdentityClass(boolean builtin) {
      this._builtin = builtin;
   }

   public boolean getInnerIdentityClasses() {
      return this._inner;
   }

   public void setInnerIdentityClasses(boolean inner) {
      this._inner = inner;
   }

   public String getIdentityClassSuffix() {
      return this._idSuffix;
   }

   public void setIdentityClassSuffix(String suffix) {
      this._idSuffix = suffix;
   }

   public boolean getInverseRelations() {
      return this._inverse;
   }

   public void setInverseRelations(boolean inverse) {
      this._inverse = inverse;
   }

   public boolean getDetachable() {
      return this._detachable;
   }

   public void setDetachable(boolean detachable) {
      this._detachable = detachable;
   }

   public String getDiscriminatorStrategy() {
      return this._discStrat;
   }

   public void setDiscriminatorStrategy(String discStrat) {
      this._discStrat = discStrat;
   }

   public String getVersionStrategy() {
      return this._versStrat;
   }

   public void setVersionStrategy(String versionStrat) {
      this._versStrat = versionStrat;
   }

   public boolean getGenerateAnnotations() {
      return this._genAnnotations;
   }

   public void setGenerateAnnotations(boolean genAnnotations) {
      this._genAnnotations = genAnnotations;
   }

   public String getAccessType() {
      return this._accessType;
   }

   public void setAccessType(String accessType) {
      this._accessType = "property".equalsIgnoreCase(accessType) ? "property" : "field";
   }

   public CodeFormat getCodeFormat() {
      return this._format;
   }

   public void setCodeFormat(CodeFormat format) {
      this._format = format;
   }

   public ReverseCustomizer getCustomizer() {
      return this._custom;
   }

   public void setCustomizer(ReverseCustomizer customizer) {
      if (customizer != null) {
         customizer.setTool(this);
      }

      this._custom = customizer;
   }

   public MappingRepository getRepository() {
      if (this._repos == null) {
         this._repos = this._conf.newMappingRepositoryInstance();
         this._repos.setMetaDataFactory(NoneMetaDataFactory.getInstance());
         this._repos.setMappingDefaults(NoneMappingDefaults.getInstance());
         this._repos.setResolve(0);
         MappingRepository var10001 = this._repos;
         this._repos.setValidate(0);
      }

      return this._repos;
   }

   public void setRepository(MappingRepository repos) {
      this._repos = repos;
   }

   public SchemaGroup getSchemaGroup() {
      if (this._schema == null) {
         SchemaGenerator gen = new SchemaGenerator(this._conf);

         try {
            gen.generateSchemas();
         } catch (SQLException var3) {
            throw SQLExceptions.getStore(var3, this._conf.getDBDictionaryInstance());
         }

         this._schema = gen.getSchemaGroup();
      }

      return this._schema;
   }

   public void setSchemaGroup(SchemaGroup schema) {
      this._schema = schema;
   }

   public ClassMapping[] getMappings() {
      return this.getRepository().getMappings();
   }

   public void run() {
      Schema[] schemas = this.getSchemaGroup().getSchemas();

      Table[] tables;
      int j;
      for(int i = 0; i < schemas.length; ++i) {
         tables = schemas[i].getTables();

         for(j = 0; j < tables.length; ++j) {
            if (this.isBaseTable(tables[j])) {
               this.mapBaseClass(tables[j]);
            }
         }
      }

      Set subs = null;

      int j;
      for(j = 0; j < schemas.length; ++j) {
         tables = schemas[j].getTables();

         for(j = 0; j < tables.length; ++j) {
            if (!this._tables.containsKey(tables[j]) && this.getSecondaryType(tables[j], false) == 5) {
               if (subs == null) {
                  subs = new HashSet();
               }

               subs.add(tables[j]);
            }
         }
      }

      if (subs != null) {
         this.mapSubclasses(subs);
      }

      Iterator itr = this._tables.values().iterator();

      ClassMapping cls;
      while(itr.hasNext()) {
         cls = (ClassMapping)itr.next();
         this.mapColumns(cls, cls.getTable(), (ForeignKey)null, false);
      }

      for(j = 0; j < schemas.length; ++j) {
         tables = schemas[j].getTables();

         for(int j = 0; j < tables.length; ++j) {
            if (!this._tables.containsKey(tables[j])) {
               this.mapTable(tables[j], this.getSecondaryType(tables[j], false));
            }
         }
      }

      Iterator itr = this._tables.values().iterator();

      int i;
      FieldMapping[] fields;
      while(itr.hasNext()) {
         cls = (ClassMapping)itr.next();
         cls.refSchemaComponents();
         if (cls.getDiscriminator().getStrategy() == null) {
            this.getStrategyInstaller().installStrategy(cls.getDiscriminator());
         }

         cls.getDiscriminator().refSchemaComponents();
         if (cls.getVersion().getStrategy() == null) {
            this.getStrategyInstaller().installStrategy(cls.getVersion());
         }

         cls.getVersion().refSchemaComponents();
         if (cls.getPCSuperclass() == null && cls.getIdentityType() == 2) {
            if (cls.getPrimaryKeyFields().length == 0) {
               throw new MetaDataException(_loc.get("no-pk-fields", (Object)cls));
            }

            if (cls.getObjectIdType() == null || cls.isOpenJPAIdentity() && !isBuiltinIdentity(cls)) {
               this.setObjectIdType(cls);
            }
         } else if (cls.getIdentityType() == 1) {
            cls.getPrimaryKeyColumns()[0].setJavaType(6);
         }

         fields = cls.getDeclaredFieldMappings();

         for(i = 0; i < fields.length; ++i) {
            fields[i].refSchemaComponents();
            setColumnJavaType(fields[i]);
            setColumnJavaType(fields[i].getElementMapping());
         }
      }

      itr = this._tables.values().iterator();

      while(itr.hasNext()) {
         cls = (ClassMapping)itr.next();
         setForeignKeyJavaType(cls.getJoinForeignKey());
         fields = cls.getDeclaredFieldMappings();

         for(i = 0; i < fields.length; ++i) {
            setForeignKeyJavaType(fields[i].getJoinForeignKey());
            setForeignKeyJavaType(fields[i].getForeignKey());
            setForeignKeyJavaType(fields[i].getElementMapping().getForeignKey());
         }
      }

      Collection unmappedCols = new ArrayList(5);

      for(int i = 0; i < schemas.length; ++i) {
         tables = schemas[i].getTables();

         for(int j = 0; j < tables.length; ++j) {
            unmappedCols.clear();
            Column[] cols = tables[j].getColumns();

            for(int k = 0; k < cols.length; ++k) {
               if (cols[k].getRefCount() == 0) {
                  unmappedCols.add(cols[k]);
               }
            }

            if (unmappedCols.size() == cols.length) {
               if (this._custom == null || !this._custom.unmappedTable(tables[j])) {
                  this._log.info(_loc.get("unmap-table", (Object)tables[j]));
               }
            } else if (unmappedCols.size() > 0) {
               this._log.info(_loc.get("unmap-cols", tables[j], unmappedCols));
            }
         }
      }

      if (this._custom != null) {
         this._custom.close();
      }

      Iterator itr = this._tables.values().iterator();

      while(itr.hasNext()) {
         ((ClassMapping)itr.next()).resolve(3);
      }

   }

   private void mapTable(Table table, int type) {
      switch (type) {
         case 2:
         case 3:
            this.mapSecondaryTable(table, type != 2);
            break;
         case 4:
            this.mapAssociationTable(table);
      }

   }

   private static boolean isBuiltinIdentity(ClassMapping cls) {
      FieldMapping[] fields = cls.getPrimaryKeyFieldMappings();
      if (fields.length != 1) {
         return false;
      } else {
         switch (fields[0].getDeclaredTypeCode()) {
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            case 9:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 29:
               return true;
            case 3:
            case 4:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 19:
            case 20:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            default:
               return false;
         }
      }
   }

   private static void setColumnJavaType(ValueMapping vm) {
      Column[] cols = vm.getColumns();
      if (cols.length == 1) {
         cols[0].setJavaType(vm.getDeclaredTypeCode());
      }

   }

   private static void setForeignKeyJavaType(ForeignKey fk) {
      if (fk != null) {
         Column[] cols = fk.getColumns();
         Column[] pks = fk.getPrimaryKeyColumns();

         for(int i = 0; i < cols.length; ++i) {
            if (cols[i].getJavaType() == 8) {
               cols[i].setJavaType(pks[i].getJavaType());
            }
         }

      }
   }

   public List recordCode() throws IOException {
      return this.recordCode((Map)null);
   }

   public List recordCode(Map output) throws IOException {
      List written = new LinkedList();
      ClassMapping[] mappings = this.getMappings();

      for(int i = 0; i < mappings.length; ++i) {
         if (this._log.isInfoEnabled()) {
            this._log.info(_loc.get("class-code", (Object)mappings[i]));
         }

         ApplicationIdTool aid = this.newApplicationIdTool(mappings[i]);
         Object gen;
         if (this.getGenerateAnnotations()) {
            gen = new AnnotatedCodeGenerator(mappings[i], aid);
         } else {
            gen = new ReverseCodeGenerator(mappings[i], aid);
         }

         ((ReverseCodeGenerator)gen).generateCode();
         if (output == null) {
            ((ReverseCodeGenerator)gen).writeCode();
            written.add(((ReverseCodeGenerator)gen).getFile());
            if (aid != null && !aid.isInnerClass()) {
               aid.record();
            }
         } else {
            StringWriter writer = new StringWriter();
            ((ReverseCodeGenerator)gen).writeCode(writer);
            output.put(mappings[i].getDescribedType(), writer.toString());
            if (aid != null && !aid.isInnerClass()) {
               writer = new StringWriter();
               aid.setWriter(writer);
               aid.record();
               output.put(mappings[i].getObjectIdType(), writer.toString());
            }
         }
      }

      return written;
   }

   public Collection recordMetaData(boolean perClass) throws IOException {
      return this.recordMetaData(perClass, (Map)null);
   }

   public Collection recordMetaData(boolean perClass, Map output) throws IOException {
      ClassMapping[] mappings = this.getMappings();

      for(int i = 0; i < mappings.length; ++i) {
         mappings[i].setResolve(3, true);
      }

      MetaDataFactory mdf = this._conf.newMetaDataFactoryInstance();
      mdf.setRepository(this.getRepository());
      mdf.setStoreDirectory(this._dir);
      if (perClass) {
         mdf.setStoreMode(1);
      }

      mdf.store(mappings, new QueryMetaData[0], new SequenceMetaData[0], 3, output);
      Set files = new TreeSet();

      for(int i = 0; i < mappings.length; ++i) {
         if (mappings[i].getSourceFile() != null) {
            files.add(mappings[i].getSourceFile());
         }
      }

      return files;
   }

   public void buildAnnotations() {
      Map output = new HashMap();
      ClassMapping[] mappings = this.getMappings();

      for(int i = 0; i < mappings.length; ++i) {
         mappings[i].setResolve(3, true);
      }

      MetaDataFactory mdf = this._conf.newMetaDataFactoryInstance();
      mdf.setRepository(this.getRepository());
      mdf.setStoreDirectory(this._dir);
      mdf.store(mappings, new QueryMetaData[0], new SequenceMetaData[0], 19, output);
      this._annos = output;
   }

   protected List getAnnotationsForMeta(Object meta) {
      return null == this._annos ? null : (List)this._annos.get(meta);
   }

   private ApplicationIdTool newApplicationIdTool(ClassMapping mapping) {
      if (mapping.getIdentityType() == 2 && !mapping.isOpenJPAIdentity() && mapping.getPCSuperclass() == null) {
         ApplicationIdTool tool = new ApplicationIdTool(this._conf, mapping.getDescribedType(), mapping);
         tool.setDirectory(this._dir);
         tool.setCodeFormat(this._format);
         return !tool.run() ? null : tool;
      } else {
         return null;
      }
   }

   public ClassMapping getClassMapping(Table table) {
      return (ClassMapping)this._tables.get(table);
   }

   public ClassMapping newClassMapping(Class cls, Table table) {
      ClassMapping mapping = (ClassMapping)this.getRepository().addMetaData(cls);
      Class sup = mapping.getDescribedType().getSuperclass();
      if (sup == Object.class) {
         this.setObjectIdType(mapping);
      } else {
         mapping.setPCSuperclass(sup);
      }

      mapping.setTable(table);
      if (this._detachable) {
         mapping.setDetachable(true);
      }

      this._tables.put(table, mapping);
      return mapping;
   }

   private void setObjectIdType(ClassMapping cls) {
      String name = cls.getDescribedType().getName();
      if (this._inner) {
         name = name + "$";
      }

      name = name + this._idSuffix;
      cls.setObjectIdType(this.generateClass(name, (Class)null), false);
   }

   public Class generateClass(String name, Class parent) {
      BCClass bc = this._project.loadClass(name, (ClassLoader)null);
      if (parent != null) {
         bc.setSuperclass(parent);
      }

      bc.addDefaultConstructor();

      try {
         return Class.forName(name, false, this._loader);
      } catch (ClassNotFoundException var5) {
         throw new InternalException(var5.toString(), var5);
      }
   }

   public boolean isUnique(ForeignKey fk) {
      PrimaryKey pk = fk.getTable().getPrimaryKey();
      if (pk != null && pk.columnsMatch(fk.getColumns())) {
         return true;
      } else {
         Index[] idx = fk.getTable().getIndexes();

         for(int i = 0; i < idx.length; ++i) {
            if (idx[i].isUnique() && idx[i].columnsMatch(fk.getColumns())) {
               return true;
            }
         }

         Unique[] unq = fk.getTable().getUniques();

         for(int i = 0; i < unq.length; ++i) {
            if (unq[i].columnsMatch(fk.getColumns())) {
               return true;
            }
         }

         return false;
      }
   }

   public ForeignKey getUniqueForeignKey(Table table) {
      ForeignKey[] fks = table.getForeignKeys();
      PrimaryKey pk = table.getPrimaryKey();
      ForeignKey unq = null;
      int count = 0;

      for(int i = 0; i < fks.length; ++i) {
         if (pk != null && pk.columnsMatch(fks[i].getColumns())) {
            return fks[i];
         }

         if (this.isUnique(fks[i])) {
            ++count;
            if (unq == null) {
               unq = fks[i];
            }
         }
      }

      return count == 1 ? unq : null;
   }

   public void addJoinConstraints(FieldMapping field) {
      ForeignKey fk = field.getJoinForeignKey();
      if (fk != null) {
         Index idx = this.findIndex(fk.getColumns());
         if (idx != null) {
            field.setJoinIndex(idx);
         }

         Unique unq = this.findUnique(fk.getColumns());
         if (unq != null) {
            field.setJoinUnique(unq);
         }

      }
   }

   public void addConstraints(ValueMapping vm) {
      Column[] cols = vm.getForeignKey() != null ? vm.getForeignKey().getColumns() : vm.getColumns();
      Index idx = this.findIndex(cols);
      if (idx != null) {
         vm.setValueIndex(idx);
      }

      Unique unq = this.findUnique(cols);
      if (unq != null) {
         vm.setValueUnique(unq);
      }

   }

   private Index findIndex(Column[] cols) {
      if (cols != null && cols.length != 0) {
         Table table = cols[0].getTable();
         Index[] idxs = table.getIndexes();

         for(int i = 0; i < idxs.length; ++i) {
            if (idxs[i].columnsMatch(cols)) {
               return idxs[i];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private Unique findUnique(Column[] cols) {
      if (cols != null && cols.length != 0) {
         Table table = cols[0].getTable();
         Unique[] unqs = table.getUniques();

         for(int i = 0; i < unqs.length; ++i) {
            if (unqs[i].columnsMatch(cols)) {
               return unqs[i];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public boolean isBaseTable(Table table) {
      if (table.getPrimaryKey() == null) {
         return false;
      } else {
         int type = this.getSecondaryType(table, true);
         if (type != -1) {
            return type == 1;
         } else if (this._custom != null) {
            return this._custom.getTableType(table, 1) == 1;
         } else {
            return true;
         }
      }
   }

   private int getSecondaryType(Table table, boolean maybeBase) {
      ForeignKey[] fks = table.getForeignKeys();
      int type;
      if (fks.length != 2 || table.getPrimaryKey() != null && !this._pkOnJoin || fks[0].getColumns().length + fks[1].getColumns().length != table.getColumns().length || this.isUnique(fks[0]) && this.isUnique(fks[1])) {
         if (maybeBase && table.getPrimaryKey() != null) {
            type = -1;
         } else if (this.getUniqueForeignKey(table) != null) {
            type = 2;
         } else if (fks.length == 1) {
            type = 0;
         } else {
            type = -1;
         }
      } else {
         type = 4;
      }

      if (this._custom != null && type != -1) {
         type = this._custom.getTableType(table, type);
      }

      return type;
   }

   private void mapBaseClass(Table table) {
      ClassMapping cls = this.newClassMapping((Table)table, (Class)null);
      if (cls != null) {
         Column[] pks = table.getPrimaryKey().getColumns();
         cls.setPrimaryKeyColumns(pks);
         if (pks.length == 1 && this._datastore && pks[0].isCompatible(-5, (String)null, 0, 0)) {
            cls.setObjectIdType((Class)null, false);
            cls.setIdentityType(1);
         } else if (pks.length == 1 && this._builtin) {
            cls.setObjectIdType((Class)null, false);
         }

         cls.setStrategy(new FullClassStrategy(), (Boolean)null);
         if (this._custom != null) {
            this._custom.customize(cls);
         }

      }
   }

   private void mapSubclasses(Set tables) {
      Table table = null;
      ForeignKey fk = null;

      while(!tables.isEmpty()) {
         ClassMapping base = null;
         Iterator itr = tables.iterator();

         label36:
         while(true) {
            while(true) {
               if (!itr.hasNext()) {
                  break label36;
               }

               table = (Table)itr.next();
               fk = this.getUniqueForeignKey(table);
               if (fk == null && table.getForeignKeys().length == 1) {
                  fk = table.getForeignKeys()[0];
               } else if (fk == null) {
                  itr.remove();
               } else {
                  base = (ClassMapping)this._tables.get(fk.getPrimaryKeyTable());
                  if (base != null) {
                     itr.remove();
                     break label36;
                  }
               }
            }
         }

         if (base == null) {
            return;
         }

         ClassMapping sub = this.newClassMapping(table, base.getDescribedType());
         sub.setJoinForeignKey(fk);
         sub.setPrimaryKeyColumns(fk.getColumns());
         sub.setIdentityType(base.getIdentityType());
         sub.setStrategy(new VerticalClassStrategy(), (Boolean)null);
         if (this._custom != null) {
            this._custom.customize(sub);
         }
      }

   }

   private void mapAssociationTable(Table table) {
      ForeignKey[] fks = table.getForeignKeys();
      if (fks.length == 2) {
         ClassMapping cls1 = (ClassMapping)this._tables.get(fks[0].getPrimaryKeyTable());
         ClassMapping cls2 = (ClassMapping)this._tables.get(fks[1].getPrimaryKeyTable());
         if (cls1 != null && cls2 != null) {
            String name = this.getRelationName(cls2.getDescribedType(), true, fks[1], false, cls1);
            FieldMapping field1 = this.newFieldMapping(name, Set.class, (Column)null, fks[1], cls1);
            if (field1 != null) {
               field1.setJoinForeignKey(fks[0]);
               this.addJoinConstraints(field1);
               ValueMapping vm = field1.getElementMapping();
               vm.setDeclaredType(cls2.getDescribedType());
               vm.setForeignKey(fks[1]);
               this.addConstraints(vm);
               field1.setStrategy(new RelationCollectionTableFieldStrategy(), (Boolean)null);
               if (this._custom != null) {
                  this._custom.customize(field1);
               }
            }

            name = this.getRelationName(cls1.getDescribedType(), true, fks[0], false, cls2);
            FieldMapping field2 = this.newFieldMapping(name, Set.class, (Column)null, fks[0], cls2);
            if (field2 != null) {
               field2.setJoinForeignKey(fks[1]);
               this.addJoinConstraints(field2);
               ValueMapping vm = field2.getElementMapping();
               vm.setDeclaredType(cls1.getDescribedType());
               vm.setForeignKey(fks[0]);
               this.addConstraints(vm);
               if (field1 != null && field1.getMappedBy() == null) {
                  field2.setMappedBy(field1.getName());
               }

               field2.setStrategy(new RelationCollectionTableFieldStrategy(), (Boolean)null);
               if (this._custom != null) {
                  this._custom.customize(field2);
               }

            }
         }
      }
   }

   private void mapSecondaryTable(Table table, boolean outer) {
      ForeignKey fk = this.getUniqueForeignKey(table);
      if (fk == null && table.getForeignKeys().length == 1) {
         fk = table.getForeignKeys()[0];
      } else if (fk == null) {
         return;
      }

      ClassMapping cls = (ClassMapping)this._tables.get(fk.getPrimaryKeyTable());
      if (cls != null) {
         this.mapColumns(cls, table, fk, outer);
      }
   }

   private void mapColumns(ClassMapping cls, Table table, ForeignKey join, boolean outer) {
      ForeignKey[] fks = table.getForeignKeys();

      for(int i = 0; i < fks.length; ++i) {
         if (fks[i] != join && fks[i] != cls.getJoinForeignKey()) {
            this.mapForeignKey(cls, fks[i], join, outer);
         }
      }

      PrimaryKey pk = join != null ? null : table.getPrimaryKey();
      Column[] cols = table.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         boolean pkcol = pk != null && pk.containsColumn(cols[i]);
         if ((!pkcol || cls.getIdentityType() != 1) && (cls.getPCSuperclass() == null && pkcol || !isForeignKeyColumn(cols[i]))) {
            this.mapColumn(cls, cols[i], join, outer);
         }
      }

   }

   private static boolean isForeignKeyColumn(Column col) {
      ForeignKey[] fks = col.getTable().getForeignKeys();

      for(int i = 0; i < fks.length; ++i) {
         if (fks[i].containsColumn(col)) {
            return true;
         }
      }

      return false;
   }

   private void mapForeignKey(ClassMapping cls, ForeignKey fk, ForeignKey join, boolean outer) {
      ClassMapping rel = (ClassMapping)this._tables.get(fk.getPrimaryKeyTable());
      if (rel != null) {
         String name = this.getRelationName(rel.getDescribedType(), false, fk, false, cls);
         FieldMapping field1 = this.newFieldMapping(name, rel.getDescribedType(), (Column)null, fk, cls);
         if (field1 != null) {
            field1.setJoinForeignKey(join);
            field1.setJoinOuter(outer);
            this.addJoinConstraints(field1);
            field1.setForeignKey(fk);
            this.addConstraints(field1);
            field1.setStrategy(new RelationFieldStrategy(), (Boolean)null);
            if (this._custom != null) {
               this._custom.customize(field1);
            }
         }

         if (this._inverse && join == null) {
            boolean unq = this.isUnique(fk);
            name = this.getRelationName(cls.getDescribedType(), !unq, fk, true, rel);
            Class type = unq ? cls.getDescribedType() : Set.class;
            FieldMapping field2 = this.newFieldMapping(name, type, (Column)null, fk, rel);
            if (field2 != null) {
               if (field1 != null) {
                  field2.setMappedBy(field1.getName());
               }

               if (unq) {
                  field2.setForeignKey(fk);
                  field2.setJoinDirection(1);
                  field2.setStrategy(new RelationFieldStrategy(), (Boolean)null);
               } else {
                  ValueMapping vm = field2.getElementMapping();
                  vm.setDeclaredType(cls.getDescribedType());
                  vm.setForeignKey(fk);
                  vm.setJoinDirection(2);
                  field2.setStrategy(new RelationCollectionInverseKeyFieldStrategy(), (Boolean)null);
               }

               if (this._custom != null) {
                  this._custom.customize(field2);
               }

            }
         }
      }
   }

   private void mapColumn(ClassMapping cls, Column col, ForeignKey join, boolean outer) {
      String name = this.getFieldName(col.getName(), cls);
      Class type = this.getFieldType(col, false);
      FieldMapping field = this.newFieldMapping(name, type, col, (ForeignKey)null, cls);
      field.setSerialized(type == Object.class);
      field.setJoinForeignKey(join);
      field.setJoinOuter(outer);
      this.addJoinConstraints(field);
      field.setColumns(new Column[]{col});
      this.addConstraints(field);
      if (col.isPrimaryKey() && cls.getIdentityType() != 1) {
         field.setPrimaryKey(true);
      }

      Object strat;
      if (type.isPrimitive()) {
         strat = new PrimitiveFieldStrategy();
      } else if (col.getType() == 2005 && this._conf.getDBDictionaryInstance().maxEmbeddedClobSize != -1) {
         strat = new MaxEmbeddedClobFieldStrategy();
      } else if (col.isLob() && this._conf.getDBDictionaryInstance().maxEmbeddedBlobSize != -1) {
         strat = new MaxEmbeddedBlobFieldStrategy();
      } else if (type == String.class) {
         strat = new StringFieldStrategy();
      } else {
         strat = new HandlerFieldStrategy();
      }

      field.setStrategy((FieldStrategy)strat, (Boolean)null);
      if (this._custom != null) {
         this._custom.customize(field);
      }

   }

   private ClassMapping newClassMapping(Table table, Class parent) {
      String name = this.getClassName(table);
      if (this._custom != null) {
         name = this._custom.getClassName(table, name);
      }

      return name == null ? null : this.newClassMapping(this.generateClass(name, parent), table);
   }

   public FieldMapping newFieldMapping(String name, Class type, Column col, ForeignKey fk, ClassMapping dec) {
      if (this._custom != null) {
         Column[] cols = fk == null ? new Column[]{col} : fk.getColumns();
         String newName = this._custom.getFieldName(dec, cols, fk, name);
         if (newName == null || !newName.equals(name)) {
            if (this._abandonedFieldNames == null) {
               this._abandonedFieldNames = new HashSet();
            }

            this._abandonedFieldNames.add(dec.getDescribedType().getName() + "." + name);
            if (newName == null) {
               return null;
            }

            name = newName;
         }
      }

      FieldMapping field = dec.addDeclaredFieldMapping(name, type);
      field.setExplicit(true);
      return field;
   }

   private String getClassName(Table table) {
      StringBuffer buf = new StringBuffer();
      if (this.getPackageName() != null) {
         buf.append(this.getPackageName()).append(".");
      }

      String name = replaceInvalidCharacters(table.getSchemaName());
      String[] subs;
      int i;
      if (this._useSchema && name != null) {
         if (allUpperCase(name)) {
            name = name.toLowerCase();
         }

         subs = Strings.split(name, "_", 0);

         for(i = 0; i < subs.length; ++i) {
            buf.append(StringUtils.capitalise(subs[i]));
         }
      }

      name = replaceInvalidCharacters(table.getName());
      if (allUpperCase(name)) {
         name = name.toLowerCase();
      }

      subs = Strings.split(name, "_", 0);

      for(i = 0; i < subs.length; ++i) {
         if (i == subs.length - 1 && subs[i].equalsIgnoreCase("id")) {
            subs[i] = "ident";
         }

         buf.append(StringUtils.capitalise(subs[i]));
      }

      return buf.toString();
   }

   public String getFieldName(String name, ClassMapping dec) {
      name = replaceInvalidCharacters(name);
      if (allUpperCase(name)) {
         name = name.toLowerCase();
      } else {
         name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
      }

      StringBuffer buf = new StringBuffer();
      String[] subs = Strings.split(name, "_", 0);

      for(int i = 0; i < subs.length; ++i) {
         if (i > 0) {
            subs[i] = StringUtils.capitalise(subs[i]);
         }

         buf.append(subs[i]);
      }

      return this.getUniqueName(buf.toString(), dec);
   }

   private String getRelationName(Class fieldType, boolean coll, ForeignKey fk, boolean inverse, ClassMapping dec) {
      String name;
      if (this._useFK && fk.getName() != null) {
         name = this.getFieldName(fk.getName(), dec);
         if (inverse && coll) {
            name = name + "Inverses";
         } else if (inverse) {
            name = name + "Inverse";
         }

         return this.getUniqueName(name, dec);
      } else {
         name = fieldType.getName();
         name = name.substring(name.lastIndexOf(46) + 1);
         name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
         if (coll && !name.endsWith("s")) {
            name = name + "s";
         }

         return this.getUniqueName(name, dec);
      }
   }

   private static boolean allUpperCase(String str) {
      for(int i = 0; i < str.length(); ++i) {
         if (Character.isLetter(str.charAt(i)) && !Character.isUpperCase(str.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   static String replaceInvalidCharacters(String str) {
      if (StringUtils.isEmpty(str)) {
         return str;
      } else {
         StringBuffer buf = new StringBuffer(str);

         int start;
         for(start = 0; start < buf.length(); ++start) {
            char c = buf.charAt(start);
            if (c == '$' || !Character.isJavaIdentifierPart(str.charAt(start))) {
               buf.setCharAt(start, '_');
            }
         }

         for(start = 0; start < buf.length() && buf.charAt(start) == '_'; ++start) {
         }

         int end;
         for(end = buf.length() - 1; end >= 0 && buf.charAt(end) == '_'; --end) {
         }

         if (start > end) {
            return "x";
         } else {
            return buf.substring(start, end + 1);
         }
      }
   }

   private String getUniqueName(String name, ClassMapping dec) {
      if (_javaKeywords.containsKey(name)) {
         name = (String)_javaKeywords.get(name);
      }

      String prefix = dec.getDescribedType().getName() + ".";
      int version = 2;

      for(int chars = 1; dec.getDeclaredField(name) != null || this._abandonedFieldNames != null && this._abandonedFieldNames.contains(prefix + name); ++version) {
         if (version > 2) {
            name = name.substring(0, name.length() - chars);
         }

         if ((double)version >= Math.pow(10.0, (double)chars)) {
            ++chars;
         }

         name = name + version;
      }

      return name;
   }

   public Class getFieldType(Column col, boolean forceObject) {
      Class type = null;
      if (this._typeMap != null) {
         String[] propNames = new String[]{col.getTypeName() + "(" + col.getSize() + "," + col.getDecimalDigits() + ")", col.getTypeName() + "(" + col.getSize() + ")", col.getTypeName()};
         String typeName = null;
         String typeSpec = null;

         for(int nameIdx = 0; typeSpec == null && nameIdx < propNames.length; ++nameIdx) {
            if (propNames[nameIdx] != null) {
               typeSpec = StringUtils.trimToNull(this._typeMap.getProperty(propNames[nameIdx]));
               if (typeSpec != null) {
                  typeName = propNames[nameIdx];
               }
            }
         }

         if (typeSpec != null) {
            this._log.info(_loc.get("reverse-type", typeName, typeSpec));
         } else {
            this._log.trace(_loc.get("no-reverse-type", (Object)propNames[propNames.length - 1]));
         }

         if (typeSpec != null) {
            type = Strings.toClass(typeSpec, this._conf.getClassResolverInstance().getClassLoader(ReverseMappingTool.class, (ClassLoader)null));
         }
      }

      if (type == null) {
         type = Schemas.getJavaType(col.getType(), col.getSize(), col.getDecimalDigits());
      }

      if (type == Object.class) {
         return !this._blobAsObj ? byte[].class : type;
      } else {
         if (type == Character.TYPE && this._conf.getDBDictionaryInstance().storeCharsAsNumbers) {
            type = String.class;
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("cant-use-char", (Object)col.getFullName()));
            }
         }

         if (!type.isPrimitive()) {
            return type;
         } else if (!forceObject && (col.isNotNull() || !this._nullAsObj)) {
            return type;
         } else {
            switch (type.getName().charAt(0)) {
               case 'b':
                  if (type == Boolean.TYPE) {
                     return Boolean.class;
                  }

                  return Byte.class;
               case 'c':
                  return Character.class;
               case 'd':
                  return Double.class;
               case 'e':
               case 'g':
               case 'h':
               case 'j':
               case 'k':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               default:
                  throw new InternalException();
               case 'f':
                  return Float.class;
               case 'i':
                  return Integer.class;
               case 'l':
                  return Long.class;
               case 's':
                  return Short.class;
            }
         }
      }
   }

   public Object clone() {
      ReverseMappingTool tool = new ReverseMappingTool(this._conf);
      tool.setSchemaGroup(this.getSchemaGroup());
      tool.setPackageName(this.getPackageName());
      tool.setDirectory(this.getDirectory());
      tool.setUseSchemaName(this.getUseSchemaName());
      tool.setUseForeignKeyName(this.getUseForeignKeyName());
      tool.setNullableAsObject(this.getNullableAsObject());
      tool.setBlobAsObject(this.getBlobAsObject());
      tool.setUseGenericCollections(this.getUseGenericCollections());
      tool.setPrimaryKeyOnJoin(this.getPrimaryKeyOnJoin());
      tool.setUseDataStoreIdentity(this.getUseDataStoreIdentity());
      tool.setUseBuiltinIdentityClass(this.getUseBuiltinIdentityClass());
      tool.setInnerIdentityClasses(this.getInnerIdentityClasses());
      tool.setIdentityClassSuffix(this.getIdentityClassSuffix());
      tool.setInverseRelations(this.getInverseRelations());
      tool.setDetachable(this.getDetachable());
      tool.setGenerateAnnotations(this.getGenerateAnnotations());
      tool.setCustomizer(this.getCustomizer());
      tool.setCodeFormat(this.getCodeFormat());
      return tool;
   }

   public static void main(String[] args) throws IOException, SQLException {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = ReverseMappingTool.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("revtool-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws IOException, SQLException {
      Flags flags = new Flags();
      flags.packageName = opts.removeProperty("package", "pkg", flags.packageName);
      flags.directory = Files.getFile(opts.removeProperty("directory", "d", (String)null), (ClassLoader)null);
      flags.useSchemaName = opts.removeBooleanProperty("useSchemaName", "sn", flags.useSchemaName);
      flags.useForeignKeyName = opts.removeBooleanProperty("useForeignKeyName", "fkn", flags.useForeignKeyName);
      flags.nullableAsObject = opts.removeBooleanProperty("nullableAsObject", "no", flags.nullableAsObject);
      flags.blobAsObject = opts.removeBooleanProperty("blobAsObject", "bo", flags.blobAsObject);
      flags.useGenericCollections = opts.removeBooleanProperty("useGenericCollections", "gc", flags.useGenericCollections);
      flags.primaryKeyOnJoin = opts.removeBooleanProperty("primaryKeyOnJoin", "pkj", flags.primaryKeyOnJoin);
      flags.useDataStoreIdentity = opts.removeBooleanProperty("useDatastoreIdentity", "ds", flags.useDataStoreIdentity);
      flags.useBuiltinIdentityClass = opts.removeBooleanProperty("useBuiltinIdentityClass", "bic", flags.useBuiltinIdentityClass);
      flags.innerIdentityClasses = opts.removeBooleanProperty("innerIdentityClasses", "inn", flags.innerIdentityClasses);
      flags.identityClassSuffix = opts.removeProperty("identityClassSuffix", "is", flags.identityClassSuffix);
      flags.inverseRelations = opts.removeBooleanProperty("inverseRelations", "ir", flags.inverseRelations);
      flags.detachable = opts.removeBooleanProperty("detachable", "det", flags.detachable);
      flags.discriminatorStrategy = opts.removeProperty("discriminatorStrategy", "ds", flags.discriminatorStrategy);
      flags.versionStrategy = opts.removeProperty("versionStrategy", "vs", flags.versionStrategy);
      flags.metaDataLevel = opts.removeProperty("metadata", "md", flags.metaDataLevel);
      flags.generateAnnotations = opts.removeBooleanProperty("annotations", "ann", flags.generateAnnotations);
      flags.accessType = opts.removeProperty("accessType", "access", flags.accessType);
      String typeMap = opts.removeProperty("typeMap", "typ", (String)null);
      if (typeMap != null) {
         flags.typeMap = Configurations.parseProperties(typeMap);
      }

      if (opts.containsKey("s")) {
         opts.put("schemas", opts.get("s"));
      }

      String customCls = opts.removeProperty("customizerClass", "cc", PropertiesReverseCustomizer.class.getName());
      File customFile = Files.getFile(opts.removeProperty("customizerProperties", "cp", (String)null), (ClassLoader)null);
      Properties customProps = new Properties();
      if (customFile != null && (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(customFile))) {
         FileInputStream fis = null;

         try {
            fis = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(customFile));
         } catch (PrivilegedActionException var13) {
            throw (FileNotFoundException)var13.getException();
         }

         customProps.load(fis);
      }

      Options customOpts = new Options();
      Options formatOpts = new Options();
      Iterator itr = opts.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         String key = (String)entry.getKey();
         if (key.startsWith("customizer.")) {
            customOpts.put(key.substring(11), entry.getValue());
            itr.remove();
         } else if (key.startsWith("c.")) {
            customOpts.put(key.substring(2), entry.getValue());
            itr.remove();
         } else if (key.startsWith("codeFormat.")) {
            formatOpts.put(key.substring(11), entry.getValue());
            itr.remove();
         } else if (key.startsWith("cf.")) {
            formatOpts.put(key.substring(3), entry.getValue());
            itr.remove();
         }
      }

      if (!formatOpts.isEmpty()) {
         flags.format = new CodeFormat();
         formatOpts.setInto(flags.format);
      }

      Configurations.populateConfiguration(conf, opts);
      ClassLoader loader = conf.getClassResolverInstance().getClassLoader(ReverseMappingTool.class, (ClassLoader)null);
      flags.customizer = (ReverseCustomizer)Configurations.newInstance(customCls, loader);
      if (flags.customizer != null) {
         Configurations.configureInstance(flags.customizer, conf, (Properties)customOpts);
         flags.customizer.setConfiguration(customProps);
      }

      run(conf, args, flags, loader);
      return true;
   }

   public static void run(JDBCConfiguration conf, String[] args, Flags flags, ClassLoader loader) throws IOException, SQLException {
      Log log = conf.getLog("openjpa.Tool");
      SchemaGroup schema;
      if (args.length == 0) {
         log.info(_loc.get("revtool-running"));
         SchemaGenerator gen = new SchemaGenerator(conf);
         gen.generateSchemas();
         schema = gen.getSchemaGroup();
      } else {
         SchemaParser parser = new XMLSchemaParser(conf);

         for(int i = 0; i < args.length; ++i) {
            File file = Files.getFile(args[i], loader);
            log.info(_loc.get("revtool-running-file", (Object)file));
            parser.parse(file);
         }

         schema = parser.getSchemaGroup();
      }

      ReverseMappingTool tool = new ReverseMappingTool(conf);
      tool.setSchemaGroup(schema);
      tool.setPackageName(flags.packageName);
      tool.setDirectory(flags.directory);
      tool.setUseSchemaName(flags.useSchemaName);
      tool.setUseForeignKeyName(flags.useForeignKeyName);
      tool.setNullableAsObject(flags.nullableAsObject);
      tool.setBlobAsObject(flags.blobAsObject);
      tool.setUseGenericCollections(flags.useGenericCollections);
      tool.setTypeMap(flags.typeMap);
      tool.setPrimaryKeyOnJoin(flags.primaryKeyOnJoin);
      tool.setUseDataStoreIdentity(flags.useDataStoreIdentity);
      tool.setUseBuiltinIdentityClass(flags.useBuiltinIdentityClass);
      tool.setInnerIdentityClasses(flags.innerIdentityClasses);
      tool.setIdentityClassSuffix(flags.identityClassSuffix);
      tool.setInverseRelations(flags.inverseRelations);
      tool.setDetachable(flags.detachable);
      tool.setGenerateAnnotations(flags.generateAnnotations);
      tool.setAccessType(flags.accessType);
      tool.setCustomizer(flags.customizer);
      tool.setCodeFormat(flags.format);
      log.info(_loc.get("revtool-map"));
      tool.run();
      if (flags.generateAnnotations) {
         log.info(_loc.get("revtool-gen-annos"));
         tool.buildAnnotations();
      }

      log.info(_loc.get("revtool-write-code"));
      tool.recordCode();
      if (!"none".equals(flags.metaDataLevel)) {
         log.info(_loc.get("revtool-write-metadata"));
         tool.recordMetaData("class".equals(flags.metaDataLevel));
      }

   }

   static {
      InputStream in = ReverseMappingTool.class.getResourceAsStream("java-keywords.rsrc");

      try {
         String[] keywords = Strings.split((new BufferedReader(new InputStreamReader(in))).readLine(), ",", 0);

         for(int i = 0; i < keywords.length; i += 2) {
            _javaKeywords.put(keywords[i], keywords[i + 1]);
         }
      } catch (IOException var10) {
         throw new InternalException(var10);
      } finally {
         try {
            in.close();
         } catch (IOException var9) {
         }

      }

   }

   private class AnnotatedCodeGenerator extends ReverseCodeGenerator {
      public AnnotatedCodeGenerator(ClassMapping mapping, ApplicationIdTool aid) {
         super(mapping, aid);
      }

      public Set getImportPackages() {
         Set pkgs = super.getImportPackages();
         pkgs.add("javax.persistence");
         return pkgs;
      }

      protected List getClassAnnotations() {
         return ReverseMappingTool.this.getAnnotationsForMeta(this._mapping);
      }

      protected List getFieldAnnotations(FieldMetaData field) {
         return ReverseMappingTool.this.getAnnotationsForMeta(field);
      }

      protected boolean usePropertyBasedAccess() {
         return "property".equals(ReverseMappingTool.this._accessType);
      }
   }

   private class ReverseCodeGenerator extends CodeGenerator {
      protected final ClassMapping _mapping;
      protected final ApplicationIdTool _appid;

      public ReverseCodeGenerator(ClassMapping mapping, ApplicationIdTool aid) {
         super(mapping);
         super.setDirectory(ReverseMappingTool.this._dir);
         super.setCodeFormat(ReverseMappingTool.this._format);
         this._mapping = mapping;
         if (aid != null && aid.isInnerClass()) {
            this._appid = aid;
         } else {
            this._appid = null;
         }

      }

      protected void closeClassBrace(CodeFormat code) {
         if (this._appid != null) {
            code.afterSection();
            code.append(this._appid.getCode());
            code.endl();
         }

         super.closeClassBrace(code);
      }

      public Set getImportPackages() {
         Set pkgs = super.getImportPackages();
         if (this._appid != null) {
            pkgs.addAll(this._appid.getImportPackages());
         }

         return pkgs;
      }

      protected String getClassCode() {
         return ReverseMappingTool.this._custom == null ? null : ReverseMappingTool.this._custom.getClassCode(this._mapping);
      }

      protected String getInitialValue(FieldMetaData field) {
         return ReverseMappingTool.this._custom == null ? null : ReverseMappingTool.this._custom.getInitialValue((FieldMapping)field);
      }

      protected String getDeclaration(FieldMetaData field) {
         return ReverseMappingTool.this._custom == null ? null : ReverseMappingTool.this._custom.getDeclaration((FieldMapping)field);
      }

      protected String getFieldCode(FieldMetaData field) {
         return ReverseMappingTool.this._custom == null ? null : ReverseMappingTool.this._custom.getFieldCode((FieldMapping)field);
      }

      protected boolean useGenericCollections() {
         return ReverseMappingTool.this._useGenericColl;
      }
   }

   private class ReverseStrategyInstaller extends StrategyInstaller {
      public ReverseStrategyInstaller(MappingRepository repos) {
         super(repos);
      }

      public void installStrategy(ClassMapping cls) {
         throw new InternalException();
      }

      public void installStrategy(FieldMapping field) {
         throw new InternalException();
      }

      public void installStrategy(Version version) {
         ClassMapping cls = version.getClassMapping();
         if (cls.getPCSuperclass() != null) {
            version.setStrategy(new SuperclassVersionStrategy(), (Boolean)null);
         } else if (ReverseMappingTool.this._versStrat != null) {
            VersionStrategy strat = this.repos.instantiateVersionStrategy(ReverseMappingTool.this._versStrat, version);
            version.setStrategy(strat, (Boolean)null);
         } else {
            version.setStrategy(new StateComparisonVersionStrategy(), (Boolean)null);
         }

      }

      public void installStrategy(Discriminator discrim) {
         ClassMapping cls = discrim.getClassMapping();
         if (cls.getPCSuperclass() != null) {
            discrim.setStrategy(new SuperclassDiscriminatorStrategy(), (Boolean)null);
         } else if (!this.hasSubclasses(cls)) {
            discrim.setStrategy(NoneDiscriminatorStrategy.getInstance(), (Boolean)null);
         } else if (ReverseMappingTool.this._discStrat != null) {
            DiscriminatorStrategy strat = this.repos.instantiateDiscriminatorStrategy(ReverseMappingTool.this._discStrat, discrim);
            discrim.setStrategy(strat, (Boolean)null);
         } else {
            discrim.setStrategy(new SubclassJoinDiscriminatorStrategy(), (Boolean)null);
         }

      }

      private boolean hasSubclasses(ClassMapping cls) {
         ClassMetaData[] metas = this.repos.getMetaDatas();

         for(int i = 0; i < metas.length; ++i) {
            if (metas[i].getPCSuperclass() == cls.getDescribedType()) {
               return true;
            }
         }

         return false;
      }
   }

   public static class Flags {
      public String packageName = null;
      public File directory = null;
      public boolean useSchemaName = false;
      public boolean useForeignKeyName = false;
      public boolean nullableAsObject = false;
      public boolean blobAsObject = false;
      public boolean useGenericCollections = false;
      public Properties typeMap = null;
      public boolean primaryKeyOnJoin = false;
      public boolean useDataStoreIdentity = false;
      public boolean useBuiltinIdentityClass = true;
      public boolean innerIdentityClasses = false;
      public String identityClassSuffix = "Id";
      public boolean inverseRelations = true;
      public boolean detachable = false;
      public boolean generateAnnotations = false;
      public String accessType = "field";
      public String metaDataLevel = "package";
      public String discriminatorStrategy = null;
      public String versionStrategy = null;
      public ReverseCustomizer customizer = null;
      public CodeFormat format = null;
   }
}
