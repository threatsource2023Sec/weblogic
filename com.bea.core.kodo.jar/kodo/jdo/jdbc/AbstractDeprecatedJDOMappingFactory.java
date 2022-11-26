package kodo.jdo.jdbc;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import kodo.jdbc.meta.KodoClassMapping;
import kodo.jdbc.meta.KodoFieldMapping;
import kodo.jdbc.meta.KodoMappingRepository;
import kodo.jdbc.meta.LockGroup;
import kodo.jdo.DeprecatedJDOMetaDataFactory;
import org.apache.openjpa.jdbc.conf.FetchModeValue;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.ValueHandler;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.strats.BlobValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ByteArrayValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ClassNameDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.ClobValueHandler;
import org.apache.openjpa.jdbc.meta.strats.EmbedFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.EmbedValueHandler;
import org.apache.openjpa.jdbc.meta.strats.FlatClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.FullClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerHandlerMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerRelationMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.ImmutableValueHandler;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedBlobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedByteArrayFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedClobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.NumberVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.PrimitiveFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionInverseKeyFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationHandlerMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationRelationMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.StateComparisonVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.StringFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.SubclassJoinDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.TimestampVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.UntypedPCValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ValueMapDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.VerticalClassStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.meta.CFMetaDataParser;
import org.apache.openjpa.lib.meta.CFMetaDataSerializer;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import serp.util.Strings;

public abstract class AbstractDeprecatedJDOMappingFactory extends DeprecatedJDOMetaDataFactory {
   public static final String KODO = "kodo";
   public static final String JDBC = "jdbc-";
   public static final String REF = "ref-";
   public static final String JDBC_CLASS_IND = "jdbc-class-ind-";
   public static final String JDBC_VERSION_IND = "jdbc-version-ind-";
   public static final String EXT_CLASS_MAP_NAME = "jdbc-class-map-name";
   public static final String EXT_VERSION_IND_NAME = "jdbc-version-ind-name";
   public static final String EXT_CLASS_IND_NAME = "jdbc-class-ind-name";
   public static final String EXT_CLASS_IND_VALUE = "jdbc-class-ind-value";
   public static final String EXT_INDEXED = "indexed";
   public static final String EXT_DELETE_ACTION = "delete-action";
   public static final String[] CLASS_EXTENSIONS = new String[]{"jdbc-class-map-name", "jdbc-version-ind-name", "jdbc-class-ind-name", "jdbc-class-ind-value", "jdbc-version-ind-indexed", "jdbc-class-ind-indexed", "jdbc-ref-indexed", "jdbc-ref-delete-action"};
   public static final String EXT_JDBC_TYPE = "jdbc-type";
   public static final String EXT_SQL_TYPE = "jdbc-sql-type";
   public static final String EXT_FIELD_MAP_NAME = "jdbc-field-map-name";
   public static final String EXT_ORDERED = "jdbc-ordered";
   public static final String EXT_NULL_IND = "jdbc-null-ind";
   public static final String EXT_SIZE = "size";
   public static final String EXT_LOCK_GROUP = "lock-group";
   public static final String LOCK_GROUP_DEFAULT = "JDOVERSION";
   public static final String[] FIELD_EXTENSIONS = new String[]{"jdbc-type", "jdbc-sql-type", "jdbc-field-map-name", "jdbc-ordered", "jdbc-null-ind", "jdbc-indexed", "jdbc-element-indexed", "jdbc-key-indexed", "jdbc-value-indexed", "jdbc-delete-action", "jdbc-element-delete-action", "jdbc-key-delete-action", "jdbc-value-delete-action", "jdbc-size", "jdbc-element-size", "jdbc-key-size", "jdbc-value-size", "lock-group"};
   private static final Localizer _loc = Localizer.forPackage(AbstractDeprecatedJDOMappingFactory.class);

   public AbstractDeprecatedJDOMappingFactory() {
      this.strict = true;
   }

   public void setStrict(boolean strict) {
   }

   public void stripDeprecatedExtensions(ClassMapping cls) {
      if (cls != null) {
         String[] cexts = DeprecatedJDOMetaDataFactory.CLASS_EXTENSIONS;

         int i;
         for(i = 0; i < cexts.length; ++i) {
            cls.removeExtension("kodo", cexts[i]);
         }

         for(i = 0; i < CLASS_EXTENSIONS.length; ++i) {
            cls.removeExtension("kodo", CLASS_EXTENSIONS[i]);
         }

         String[] fexts = DeprecatedJDOMetaDataFactory.FIELD_EXTENSIONS;
         FieldMapping[] fields = cls.getDefinedFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            int j;
            for(j = 0; j < fexts.length; ++j) {
               fields[i].removeExtension("kodo", fexts[j]);
            }

            for(j = 0; j < FIELD_EXTENSIONS.length; ++j) {
               fields[i].removeExtension("kodo", FIELD_EXTENSIONS[j]);
            }
         }

         this.stripMappingExtensions(cls);
      }
   }

   protected void stripMappingExtensions(ClassMetaData cls) {
      cls.removeExtension("kodo", "jdbc-class-map");
      cls.removeExtension("kodo", "jdbc-class-ind");
      cls.removeExtension("kodo", "jdbc-version-ind");
      cls.removeExtension("kodo", "jdbc-field-mappings");
      FieldMetaData[] fmds = cls.getDefinedFields();

      for(int i = 0; i < fmds.length; ++i) {
         fmds[i].removeExtension("kodo", "jdbc-field-map");
         if (fmds[i].getEmbeddedMetaData() != null) {
            this.stripMappingExtensions(fmds[i].getEmbeddedMetaData());
         }
      }

   }

   public void addClassExtensionKeys(Collection exts) {
      super.addClassExtensionKeys(exts);
      exts.addAll(Arrays.asList(CLASS_EXTENSIONS));
   }

   public void addFieldExtensionKeys(Collection exts) {
      super.addFieldExtensionKeys(exts);
      exts.addAll(Arrays.asList(FIELD_EXTENSIONS));
   }

   protected boolean translateClassExtension(ClassMetaData meta, String key, String value) {
      if (super.translateClassExtension(meta, key, value)) {
         return true;
      } else {
         ClassMapping cls = (ClassMapping)meta;
         if ("subclass-fetch-mode".equals(key)) {
            FetchModeValue val = new FetchModeValue("subclass-fetch-mode");
            val.setString(value);
            cls.setSubclassFetchMode(val.get());
         } else if ("jdbc-class-map-name".equals(key)) {
            this.translateClassStrategy(cls, value);
         } else if ("jdbc-version-ind-name".equals(key)) {
            cls.getVersion().getMappingInfo().setStrategy(this.translateVersionStrategy(value));
         } else if ("jdbc-class-ind-name".equals(key)) {
            cls.getDiscriminator().getMappingInfo().setStrategy(this.translateDiscriminatorStrategy(value));
         } else if ("jdbc-class-ind-value".equals(key)) {
            cls.getDiscriminator().getMappingInfo().setValue(value);
         } else if ("jdbc-version-ind-indexed".equals(key)) {
            this.setIndex(cls.getVersion().getMappingInfo(), value);
         } else if ("jdbc-class-ind-indexed".equals(key)) {
            this.setIndex(cls.getDiscriminator().getMappingInfo(), value);
         } else if ("jdbc-ref-indexed".equals(key)) {
            this.setIndex(cls.getMappingInfo(), value);
         } else if ("jdbc-ref-delete-action".equals(key)) {
            this.setDeleteAction(cls, cls.getMappingInfo(), value);
         }

         return false;
      }
   }

   protected boolean translateFieldExtension(FieldMetaData field, String key, String value) {
      if (super.translateFieldExtension(field, key, value)) {
         return true;
      } else {
         FieldMapping fm = (FieldMapping)field;
         if ("eager-fetch-mode".equals(key)) {
            FetchModeValue val = new FetchModeValue("eager-fetch-mode");
            val.setString(value);
            fm.setEagerFetchMode(val.get());
         } else if ("jdbc-type".equals(key)) {
            this.addColumn(fm.getValueInfo(), 0).setType(Schemas.getJDBCType(value));
         } else if ("jdbc-sql-type".equals(key)) {
            this.addColumn(fm.getValueInfo(), 0).setTypeName(value);
         } else if ("jdbc-field-map-name".equals(key)) {
            this.translateFieldStrategy(fm, value);
         } else if ("jdbc-ordered".equals(key)) {
            if ("true".equals(value)) {
               fm.getMappingInfo().setOrderColumn(new Column());
            } else {
               fm.getMappingInfo().setCanOrderColumn(false);
            }
         } else if ("jdbc-null-ind".equals(key) && !"false".equals(value)) {
            this.addColumn(fm.getValueInfo(), 0).setName("synthetic".equals(value) ? "true" : value);
         } else if ("jdbc-indexed".equals(key)) {
            this.setIndex(fm.getValueInfo(), value);
         } else if (!"jdbc-element-indexed".equals(key) && !"jdbc-value-indexed".equals(key)) {
            if ("jdbc-key-indexed".equals(key)) {
               this.setIndex(fm.getKeyMapping().getValueInfo(), value);
            } else if ("jdbc-delete-action".equals(key)) {
               this.setDeleteAction(fm, fm.getValueInfo(), value);
            } else if (!"jdbc-element-delete-action".equals(key) && !"jdbc-value-delete-action".equals(key)) {
               if ("jdbc-key-delete-action".equals(key)) {
                  this.setDeleteAction(fm.getKey(), fm.getKeyMapping().getValueInfo(), value);
               } else if ("jdbc-size".equals(key)) {
                  this.addColumn(fm.getValueInfo(), 0).setSize(Integer.parseInt(value));
               } else if (!"jdbc-element-size".equals(key) && !"jdbc-value-size".equals(key)) {
                  if ("jdbc-key-size".equals(key)) {
                     this.addColumn(fm.getKeyMapping().getValueInfo(), 0).setSize(Integer.parseInt(value));
                  } else if ("lock-group".equals(key)) {
                     if ("none".equals(value)) {
                        ((KodoFieldMapping)fm).setLockGroup((LockGroup)null);
                     } else {
                        ((KodoFieldMapping)fm).setLockGroup(((KodoMappingRepository)fm.getMappingRepository()).getLockGroup(value));
                     }
                  }
               } else {
                  this.addColumn(fm.getElementMapping().getValueInfo(), 0).setSize(Integer.parseInt(value));
               }
            } else {
               this.setDeleteAction(fm.getElement(), fm.getElementMapping().getValueInfo(), value);
            }
         } else {
            this.setIndex(fm.getElementMapping().getValueInfo(), value);
         }

         return false;
      }
   }

   private Column addColumn(MappingInfo info, int idx) {
      List cols = info.getColumns();
      if (idx != -1 && ((List)cols).size() > idx) {
         return (Column)((List)cols).get(idx);
      } else {
         if (((List)cols).isEmpty()) {
            cols = new ArrayList(idx == -1 ? 3 : idx + 1);
            info.setColumns((List)cols);
         }

         while(((List)cols).size() < idx) {
            ((List)cols).add(new Column());
         }

         Column col = new Column();
         ((List)cols).add(col);
         return col;
      }
   }

   private void setDeleteAction(Object context, MappingInfo info, String val) {
      boolean deferred = val.endsWith("-deferred");
      if (deferred) {
         val = val.substring(0, val.length() - "-deferred".length());
      }

      int action = true;

      int action;
      try {
         action = ForeignKey.getAction(val);
      } catch (IllegalArgumentException var7) {
         throw new MetaDataException(context + ": " + var7.getMessage());
      }

      if (action == 1) {
         info.setCanForeignKey(false);
      } else if (info.getForeignKey() == null) {
         ForeignKey fk = new ForeignKey();
         fk.setDeleteAction(action);
         fk.setDeferred(deferred);
         info.setForeignKey(fk);
      }

   }

   private void setIndex(MappingInfo info, String val) {
      if ("false".equals(val)) {
         info.setCanIndex(false);
      } else if (info.getIndex() == null) {
         Index idx = new Index();
         if ("unique".equals(val)) {
            idx.setUnique(true);
         }

         info.setIndex(idx);
      }

   }

   private void translateClassStrategy(ClassMapping cls, String strat) {
      ClassMappingInfo cinfo = cls.getMappingInfo();
      if ("base".equals(strat)) {
         cinfo.setStrategy("full");
      } else if ("vertical".equals(strat)) {
         cinfo.setStrategy("vertical");
      } else if ("flat".equals(strat)) {
         cinfo.setStrategy("flat");
      } else if ("none".equals(strat)) {
         cinfo.setStrategy("none");
      } else if ("horizontal".equals(strat)) {
         cinfo.setStrategy("none");
         cls.addExtension("kodo", "jdbc-class-map-name", "horizontal");
      } else {
         cinfo.setStrategy(strat);
      }

   }

   private String translateClassStrategy(ClassMapping cls) {
      if (cls.getStrategy() instanceof FullClassStrategy) {
         return "base";
      } else if (cls.getStrategy() instanceof VerticalClassStrategy) {
         return "vertical";
      } else if (cls.getStrategy() instanceof FlatClassStrategy) {
         return "flat";
      } else if (cls.getStrategy() instanceof NoneClassStrategy) {
         return "horizontal".equals(cls.getStringExtension("kodo", "jdbc-class-map-name")) ? "horizontal" : "none";
      } else {
         return cls.getStrategy().getAlias();
      }
   }

   private String translateVersionStrategy(String strat) {
      if ("version-number".equals(strat)) {
         return "version-number";
      } else if ("version-date".equals(strat)) {
         return "timestamp";
      } else if ("state-image".equals(strat)) {
         return "state-comparison";
      } else {
         return "none".equals(strat) ? "none" : strat;
      }
   }

   private String translateVersionStrategy(Version vers) {
      if (vers.getStrategy() instanceof NumberVersionStrategy) {
         return "version-number";
      } else if (vers.getStrategy() instanceof TimestampVersionStrategy) {
         return "version-date";
      } else if (vers.getStrategy() instanceof StateComparisonVersionStrategy) {
         return "state-image";
      } else {
         return vers.getStrategy() instanceof NoneVersionStrategy ? "none" : vers.getStrategy().getAlias();
      }
   }

   private String translateDiscriminatorStrategy(String strat) {
      if ("in-class-name".equals(strat)) {
         return "class-name";
      } else if ("metadata-value".equals(strat)) {
         return "value-map";
      } else if ("subclass-join".equals(strat)) {
         return "subclass-join";
      } else {
         return "none".equals(strat) ? "none" : strat;
      }
   }

   private String translateDiscriminatorStrategy(Discriminator disc) {
      if (disc.getStrategy() instanceof ClassNameDiscriminatorStrategy) {
         return "in-class-name";
      } else if (disc.getStrategy() instanceof ValueMapDiscriminatorStrategy) {
         return "metadata-value";
      } else if (disc.getStrategy() instanceof SubclassJoinDiscriminatorStrategy) {
         return "subclass-join";
      } else {
         return disc.getStrategy() instanceof NoneDiscriminatorStrategy ? "none" : disc.getStrategy().getAlias();
      }
   }

   private void translateFieldStrategy(FieldMapping fm, String strat) {
      if ("blob".equals(strat)) {
         fm.setSerialized(true);
      } else if ("blob-collection".equals(strat)) {
         fm.getElement().setSerialized(true);
      } else if ("clob".equals(strat)) {
         this.addColumn(fm.getValueInfo(), 0).setType(2005);
      } else if ("clob-collection".equals(strat)) {
         this.addColumn(fm.getElementMapping().getValueInfo(), 0).setType(2005);
      } else if (!"none".equals(strat) && strat.indexOf(46) == -1) {
         if ("pc".equals(strat)) {
            fm.getMappingInfo().setStrategy(UntypedPCValueHandler.class.getName());
         } else if ("pc-collection".equals(strat)) {
            fm.getElementMapping().getValueInfo().setStrategy(UntypedPCValueHandler.class.getName());
         } else {
            if (strat.startsWith("blob-")) {
               fm.getKey().setSerialized(true);
            } else if (strat.startsWith("clob-")) {
               this.addColumn(fm.getKeyMapping().getValueInfo(), 0).setType(2005);
            } else if (strat.startsWith("pc-")) {
               fm.getKeyMapping().getValueInfo().setStrategy(UntypedPCValueHandler.class.getName());
            }

            if (strat.endsWith("-blob-map")) {
               fm.getElement().setSerialized(true);
            } else if (strat.endsWith("-clob-map")) {
               this.addColumn(fm.getElementMapping().getValueInfo(), 0).setType(2005);
            } else if (strat.endsWith("-pc-map")) {
               fm.getElementMapping().getValueInfo().setStrategy(UntypedPCValueHandler.class.getName());
            }
         }
      } else {
         fm.getMappingInfo().setStrategy(strat);
      }

   }

   private String translateFieldStrategy(FieldMapping fm) {
      if (fm.getManagement() != 3) {
         return null;
      } else if (fm.getDefiningMapping().getTable() == null) {
         return null;
      } else if (fm.getStrategy() instanceof EmbedFieldStrategy) {
         return "embedded";
      } else {
         ValueHandler handler;
         if (fm.getStrategy() instanceof HandlerCollectionTableFieldStrategy) {
            handler = fm.getElementMapping().getHandler();
            return handler instanceof ImmutableValueHandler ? "collection" : this.translateValueHandler(handler) + "-collection";
         } else if (fm.getStrategy() instanceof HandlerFieldStrategy) {
            return this.translateValueHandler(fm.getHandler());
         } else if (fm.getStrategy() instanceof HandlerHandlerMapTableFieldStrategy) {
            handler = fm.getKeyMapping().getHandler();
            ValueHandler vhandler = fm.getElementMapping().getHandler();
            if (handler instanceof ImmutableValueHandler && vhandler instanceof ImmutableValueHandler) {
               return "map";
            } else if (handler instanceof UntypedPCValueHandler && vhandler instanceof UntypedPCValueHandler) {
               return "pc-map";
            } else {
               String k = handler instanceof ImmutableValueHandler ? "n" : this.translateValueHandler(handler);
               String v = vhandler instanceof ImmutableValueHandler ? "n" : this.translateValueHandler(vhandler);
               return k + "-" + v + "-map";
            }
         } else if (fm.getStrategy() instanceof HandlerRelationMapTableFieldStrategy) {
            handler = fm.getKeyMapping().getHandler();
            return handler instanceof ImmutableValueHandler ? "n-many-map" : this.translateValueHandler(handler) + "-many-map";
         } else if (fm.getStrategy() instanceof MaxEmbeddedBlobFieldStrategy) {
            return "blob";
         } else if (fm.getStrategy() instanceof MaxEmbeddedByteArrayFieldStrategy) {
            return "byte-array";
         } else if (fm.getStrategy() instanceof MaxEmbeddedClobFieldStrategy) {
            return "clob";
         } else if (fm.getStrategy() instanceof NoneFieldStrategy) {
            return "none";
         } else if (!(fm.getStrategy() instanceof PrimitiveFieldStrategy) && !(fm.getStrategy() instanceof StringFieldStrategy)) {
            if (fm.getStrategy() instanceof RelationCollectionInverseKeyFieldStrategy) {
               return "one-many";
            } else if (fm.getStrategy() instanceof RelationCollectionTableFieldStrategy) {
               return "many-many";
            } else if (fm.getStrategy() instanceof RelationFieldStrategy) {
               return "one-one";
            } else if (fm.getStrategy() instanceof RelationHandlerMapTableFieldStrategy) {
               handler = fm.getElementMapping().getHandler();
               return handler instanceof ImmutableValueHandler ? "many-n-map" : "many-" + this.translateValueHandler(handler) + "-map";
            } else {
               return fm.getStrategy() instanceof RelationRelationMapTableFieldStrategy ? "many-many-map" : fm.getStrategy().getClass().getName();
            }
         } else {
            return "value";
         }
      }
   }

   private String translateValueHandler(ValueHandler handler) {
      if (handler instanceof ImmutableValueHandler) {
         return "value";
      } else if (handler instanceof BlobValueHandler) {
         return "blob";
      } else if (handler instanceof ByteArrayValueHandler) {
         return "byte-array";
      } else if (handler instanceof ClobValueHandler) {
         return "clob";
      } else if (handler instanceof EmbedValueHandler) {
         return "embedded";
      } else {
         return handler instanceof UntypedPCValueHandler ? "pc" : "custom";
      }
   }

   protected MappingAttrsParser newMappingParser() {
      return new MappingAttrsParser((JDBCConfiguration)this.repos.getConfiguration());
   }

   protected MappingAttrsSerializer newMappingSerializer() {
      return new MappingAttrsSerializer((JDBCConfiguration)this.repos.getConfiguration());
   }

   protected void fromMappingAttrs(ClassMapping cls, ClassMappingAttrs cattrs, ClassLoader loader) {
      if (cls != null && cattrs != null) {
         cls.setSourceMode(2, true);
         ClassMappingInfo cinfo = cls.getMappingInfo();
         cinfo.setSource(cattrs.getSourceFile(), cattrs.getSourceType());
         if (cattrs.type != null) {
            this.translateClassStrategy(cls, cattrs.type);
         }

         String val = cattrs.getAttribute("table");
         if (val != null) {
            cinfo.setTableName(val);
         }

         val = cattrs.getAttribute("pk-column");
         if (val != null) {
            this.addColumn(cinfo, 0).setName(val);
         }

         this.setColumns(cinfo, cattrs, "ref-");
         MappingInfo info = cls.getVersion().getMappingInfo();
         if (cattrs.version.type != null) {
            info.setStrategy(this.translateVersionStrategy(cattrs.version.type));
         }

         this.setColumns(info, cattrs.version, "");
         MappingInfo info = cls.getDiscriminator().getMappingInfo();
         if (cattrs.discriminator.type != null) {
            info.setStrategy(this.translateDiscriminatorStrategy(cattrs.discriminator.type));
         }

         this.setColumns(info, cattrs.discriminator, "");
         this.fieldsFromMappingAttrs(cls, cattrs, loader);
      }
   }

   private void fieldsFromMappingAttrs(ClassMapping cls, MappingAttrs mattrs, ClassLoader loader) {
      Iterator itr = mattrs.fields.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         String name = (String)entry.getKey();
         int idx = name.lastIndexOf(46);
         FieldMapping fm;
         if (idx != -1) {
            fm = this.addSuperclassField(cls, name, idx, loader);
         } else {
            fm = this.addField(cls, name);
         }

         this.assertSupported(fm);
         this.fromMappingAttrs(fm, (FieldMappingAttrs)entry.getValue(), loader);
      }

   }

   private FieldMapping addSuperclassField(ClassMapping cls, String name, int idx, ClassLoader loader) {
      String supName = name.substring(0, idx);
      Class sup = null;

      try {
         sup = Class.forName(supName, false, loader);
      } catch (Throwable var12) {
         if (supName.indexOf(46) == -1) {
            try {
               sup = Class.forName(Strings.getPackageName(cls.getDescribedType()) + "." + supName, false, loader);
            } catch (Throwable var11) {
            }
         }
      }

      if (sup == null || cls.getDescribedType() != Object.class && !sup.isAssignableFrom(cls.getDescribedType())) {
         throw new MetaDataException(_loc.get("not-supclass-field", name, cls.getDescribedType(), supName));
      } else {
         name = name.substring(idx + 1);
         FieldMapping fm = (FieldMapping)cls.getDefinedSuperclassField(name);
         if (fm == null || fm.getDeclaredType() == Object.class) {
            Field f = null;

            try {
               f = sup.getDeclaredField(name);
            } catch (Exception var10) {
               throw (new MetaDataException(_loc.get("invalid-field", name, sup))).setCause(var10);
            }

            if (fm == null) {
               fm = (FieldMapping)cls.addDefinedSuperclassField(name, f.getType(), sup);
            }

            fm.backingMember(f);
         }

         return fm;
      }
   }

   private FieldMapping addField(ClassMapping cls, String name) {
      FieldMapping fm = cls.getDeclaredFieldMapping(name);
      if ((fm == null || fm.getDeclaredType() == Object.class) && cls.getDescribedType() != Object.class) {
         Field f = null;

         try {
            f = cls.getDescribedType().getDeclaredField(name);
         } catch (Exception var6) {
            throw (new MetaDataException(_loc.get("invalid-field", name, cls))).setCause(var6);
         }

         if (fm == null) {
            fm = (FieldMapping)cls.addDeclaredField(name, f.getType());
         }

         fm.backingMember(f);
      } else if (fm == null) {
         fm = (FieldMapping)cls.addDeclaredField(name, Object.class);
      }

      return fm;
   }

   private void fromMappingAttrs(FieldMapping fm, FieldMappingAttrs fattrs, ClassLoader loader) {
      FieldMappingInfo finfo = fm.getMappingInfo();
      ValueMappingInfo vinfo = fm.getValueInfo();
      String val;
      if (fm.getMappedBy() == null) {
         this.translateFieldStrategy(fm, fattrs.type);
         val = fattrs.getAttribute("ref-join-type");
         if ("outer".equals(val)) {
            finfo.setJoinOuter(true);
         }

         if ("one-many".equals(fattrs.type)) {
            this.setColumns(fm.getElementMapping().getValueInfo(), fattrs, "ref-");
         } else if ("embedded".equals(fattrs.type)) {
            val = fattrs.getAttribute("table");
            if (val != null) {
               finfo.setTableName(val);
            }

            val = fattrs.getAttribute("null-ind-column");
            if (val != null) {
               this.addColumn(vinfo, 0).setName(val);
            }

            this.setColumns(finfo, fattrs, "ref-");
            ClassMapping embed = fm.getEmbeddedMapping();
            if (embed == null) {
               embed = (ClassMapping)fm.addEmbeddedMetaData();
            }

            this.fieldsFromMappingAttrs(embed, fattrs, loader);
         } else {
            val = fattrs.getAttribute("table");
            if (val != null) {
               finfo.setTableName(val);
            }

            this.setColumns(finfo, fattrs, "ref-");
            this.setColumns(fm.getValueInfo(), fattrs, "");
            this.setColumns(fm.getKeyMapping().getValueInfo(), fattrs, "key-");
            this.setColumns(fm.getElementMapping().getValueInfo(), fattrs, "value-");
            this.setColumns(fm.getElementMapping().getValueInfo(), fattrs, "element-");
         }
      }

      val = fattrs.getAttribute("order-column");
      if (val != null) {
         if (finfo.getOrderColumn() == null) {
            finfo.setOrderColumn(new Column());
         }

         finfo.getOrderColumn().setName(val);
      }

      val = fattrs.getAttribute("class-criteria");
      if ("true".equals(val)) {
         if ("one-one".equals(fattrs.type)) {
            fm.getValueInfo().setUseClassCriteria(true);
         } else {
            fm.getElementMapping().getValueInfo().setUseClassCriteria(true);
         }
      }

   }

   private void setColumns(MappingInfo info, MappingAttrs mattrs, String prefix) {
      String colPrefix = prefix + "column";
      String fkPrefix = prefix + "column.";
      String constPrefix = prefix + "constant.";
      int seq = 0;

      for(Iterator itr = mattrs.attrs.entrySet().iterator(); itr.hasNext(); ++seq) {
         Map.Entry entry = (Map.Entry)itr.next();
         String attr = (String)entry.getKey();
         String val = (String)entry.getValue();
         Column col;
         if (!attr.startsWith(fkPrefix)) {
            if (attr.startsWith(constPrefix)) {
               attr = attr.substring(constPrefix.length());
               col = this.addColumn(info, -1);
               col.setName(attr);
               col.setTarget(val);
            } else if (attr.startsWith(colPrefix)) {
               attr = attr.substring(colPrefix.length());
               int idx = attr.indexOf(45);
               int num = 0;
               if (idx != -1) {
                  num = Integer.parseInt(attr.substring(idx + 1));
               }

               col = this.addColumn(info, num);
               col.setName(val);
            } else if (mattrs.type.equals("version-number") && attr.endsWith('-' + colPrefix)) {
               col = this.addColumn(info, seq);
               col.setName(val);
            }
         } else {
            attr = attr.substring(fkPrefix.length());
            col = this.addColumn(info, -1);
            if (!"null".equals(val) && (val.length() <= 0 || val.charAt(0) != '\'' && val.charAt(0) != '-' && val.charAt(0) != '.' && !Character.isDigit(val.charAt(0)))) {
               col.setName(val);
               col.setTarget(attr);
            } else {
               col.setName("." + attr);
               col.setTarget(val);
            }
         }
      }

   }

   protected ClassMappingAttrs toMappingAttrs(ClassMapping cls) {
      if (cls == null) {
         return null;
      } else if ((cls.getResolve() & 2) == 0) {
         throw new InternalException(_loc.get("no-mapping-resolve", cls));
      } else {
         ClassMappingAttrs cattrs = new ClassMappingAttrs(cls.getDescribedType().getName());
         cattrs.setSource(cls.getMappingInfo().getSourceFile());
         cattrs.type = this.translateClassStrategy(cls);
         if (cls.getTable() != null && !(cls.getStrategy() instanceof FlatClassStrategy)) {
            DBDictionary dict = ((JDBCConfiguration)cls.getRepository().getConfiguration()).getDBDictionaryInstance();
            cattrs.attrs.put("table", dict.getFullName(cls.getTable(), true));
            if (cls.getJoinForeignKey() != null) {
               this.addForeignKeyAttrs(cattrs, cls.getJoinForeignKey(), "ref-");
            } else if (cls.getIdentityType() == 1 && cls.getPrimaryKeyColumns().length == 1) {
               cattrs.attrs.put("pk-column", cls.getPrimaryKeyColumns()[0].getName());
            }
         }

         if (cls.getMappedPCSuperclassMapping() == null && cls.getTable() != null) {
            Version vers = cls.getVersion();
            if ((vers.getResolve() & 2) == 0) {
               throw new InternalException(_loc.get("no-mapping-resolve", vers));
            }

            cattrs.version.type = this.translateVersionStrategy(vers);
            Column[] cols = vers.getColumns();
            LockGroup[] lgs = ((KodoClassMapping)cls).getLockGroups();

            for(int i = 0; i < cols.length; ++i) {
               if (cols[i].getName().equals("JDOVERSION")) {
                  cattrs.version.attrs.put("column", cols[i].getName());
               } else {
                  cattrs.version.attrs.put(lgs[i].getName() + "-column", cols[i].getName());
               }
            }

            Discriminator disc = cls.getDiscriminator();
            if ((disc.getResolve() & 2) == 0) {
               throw new InternalException(_loc.get("no-mapping-resolve", disc));
            }

            cattrs.discriminator.type = this.translateDiscriminatorStrategy(disc);
            if (disc.getColumns().length == 1) {
               cattrs.discriminator.attrs.put("column", disc.getColumns()[0].getName());
            }
         }

         FieldMapping[] fields = cls.getDefinedFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            FieldMappingAttrs fattrs = this.toMappingAttrs(cls, fields[i]);
            if (fattrs != null) {
               cattrs.fields.put(fattrs.name, fattrs);
            }
         }

         return cattrs;
      }
   }

   private FieldMappingAttrs toMappingAttrs(ClassMapping cls, FieldMapping fm) {
      String type = this.translateFieldStrategy(fm);
      if (type == null) {
         return null;
      } else {
         this.assertSupported(fm);
         String name;
         if (cls.getDescribedType() == fm.getDeclaringType()) {
            name = fm.getName();
         } else {
            name = fm.getFullName(false);
            String pkg = Strings.getPackageName(cls.getDescribedType());
            if (pkg.length() > 0 && pkg.equals(Strings.getPackageName(fm.getDeclaringType()))) {
               name = name.substring(pkg.length() + 1);
            }
         }

         FieldMappingAttrs fattrs = new FieldMappingAttrs(name);
         fattrs.type = type;
         DBDictionary dict = ((JDBCConfiguration)cls.getRepository().getConfiguration()).getDBDictionaryInstance();
         if (fm.getTable() != cls.getTable()) {
            fattrs.attrs.put("table", dict.getFullName(fm.getTable(), true));
         }

         ForeignKey fk = fm.getJoinForeignKey();
         if (fk != null) {
            this.addForeignKeyAttrs(fattrs, fk, "ref-");
            if (fm.isJoinOuter()) {
               fattrs.attrs.put("ref-join-type", "outer");
            }
         }

         this.addMappingAttrs(fattrs, fm, "", dict);
         this.addMappingAttrs(fattrs, fm.getKeyMapping(), "key-", dict);
         String prefix = fm.getTypeCode() == 13 ? "value-" : "element-";
         this.addMappingAttrs(fattrs, fm.getElementMapping(), prefix, dict);
         if (fm.getOrderColumn() != null) {
            fattrs.attrs.put("order-column", fm.getOrderColumn().getName());
         }

         return fattrs;
      }
   }

   private void assertSupported(FieldMapping fm) {
      String msg = null;
      switch (fm.getTypeCode()) {
         case 11:
         case 12:
            if (fm.getElement().isEmbeddedPC()) {
               msg = "dep-embed-coll";
            }
            break;
         case 13:
            if (fm.getElement().isEmbeddedPC() || fm.getKey().isEmbeddedPC()) {
               msg = "dep-embed-map";
            }
      }

      if (msg != null) {
         throw new MetaDataException(_loc.get(msg, fm));
      }
   }

   private void addMappingAttrs(MappingAttrs mattrs, ValueMapping vm, String prefix, DBDictionary dict) {
      ForeignKey fk = vm.getForeignKey();
      int i;
      if (fk != null) {
         String ref = vm.getJoinDirection() != 0 ? "ref-" : "";
         this.addForeignKeyAttrs(mattrs, fk, ref + prefix);
         if (vm.getUseClassCriteria()) {
            mattrs.attrs.put("class-criteria", "true");
         }

         if (vm.getJoinDirection() == 1 && vm.getTypeMapping() != null) {
            mattrs.attrs.put("table", dict.getFullName(fk.getTable(), true));
            Column[] pks = vm.getTypeMapping().getPrimaryKeyColumns();

            for(i = 0; i < pks.length; ++i) {
               mattrs.attrs.put(prefix + "column." + pks[i].getName(), pks[i].getName());
            }
         }
      } else if (!vm.isEmbeddedPC()) {
         Column[] cols = vm.getColumns();

         for(i = 0; i < cols.length; ++i) {
            String key = prefix + "column";
            if (i > 0) {
               key = key + "-" + i;
            }

            mattrs.attrs.put(key, cols[i].getName());
         }
      } else if (vm.getEmbeddedMapping() != null) {
         if (vm.getColumns().length == 1) {
            mattrs.attrs.put("null-ind-column", vm.getColumns()[0].getName());
         }

         ClassMapping embed = vm.getEmbeddedMapping();
         FieldMapping[] fields = embed.getDefinedFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            FieldMappingAttrs fattrs = this.toMappingAttrs(embed, fields[i]);
            if (fattrs != null) {
               mattrs.fields.put(fattrs.name, fattrs);
            }
         }
      }

   }

   private void addForeignKeyAttrs(MappingAttrs mattrs, ForeignKey fk, String prefix) {
      Column[] cols = fk.getColumns();
      Column[] pks = fk.getPrimaryKeyColumns();

      for(int i = 0; i < cols.length; ++i) {
         mattrs.attrs.put(prefix + "column." + pks[i].getName(), cols[i].getName());
      }

      cols = fk.getConstantColumns();
      Object[] consts = fk.getConstants();

      int i;
      for(i = 0; i < cols.length; ++i) {
         mattrs.attrs.put(prefix + "constant." + cols[i].getName(), this.constantToString(consts[i]));
      }

      consts = fk.getPrimaryKeyConstants();
      pks = fk.getConstantPrimaryKeyColumns();

      for(i = 0; i < pks.length; ++i) {
         mattrs.attrs.put(prefix + "column." + pks[i].getName(), this.constantToString(consts[i]));
      }

   }

   private String constantToString(Object obj) {
      if (obj == null) {
         return "null";
      } else {
         return obj instanceof String ? "'" + obj + "'" : obj.toString();
      }
   }

   protected static class MappingAttrsSerializer extends CFMetaDataSerializer {
      private MappingAttrsRepository _repos = null;

      public MappingAttrsSerializer(JDBCConfiguration conf) {
         this.setLog(conf.getLog("openjpa.MetaData"));
      }

      public MappingAttrsRepository getRepository() {
         if (this._repos == null) {
            this._repos = new MappingAttrsRepository();
         }

         return this._repos;
      }

      public void setRepository(MappingAttrsRepository repos) {
         this._repos = repos;
      }

      public void clear() {
         this._repos = null;
      }

      protected Collection getObjects() {
         return Arrays.asList(this.getRepository().getMappings());
      }

      protected void serialize(Collection objs) throws SAXException {
         Map byPackage = this.groupByPackage(objs);
         this.startElement("mapping");
         Collection system = (Collection)byPackage.remove((Object)null);
         if (system != null) {
            this.serializePackage((String)null, system);
         }

         Iterator itr = byPackage.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            this.serializePackage((String)entry.getKey(), (Collection)entry.getValue());
         }

         this.endElement("mapping");
      }

      protected String getPackage(Object obj) {
         ClassMappingAttrs mapping = (ClassMappingAttrs)obj;
         int dotIdx = mapping.name.lastIndexOf(46);
         return dotIdx == -1 ? "" : mapping.name.substring(0, dotIdx);
      }

      private void serializePackage(String name, Collection objs) throws SAXException {
         this.setPackage(name);
         if (name != null) {
            this.addAttribute("name", name);
            this.startElement("package");
         }

         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            this.serializeClass((ClassMappingAttrs)itr.next());
         }

         if (name != null) {
            this.endElement("package");
         }

         this.setPackage((String)null);
      }

      private void serializeClass(ClassMappingAttrs mapping) throws SAXException {
         this.addAttribute("name", this.getClassName(mapping.name));
         this.startElement("class");
         this.serializeAttributes(mapping);
         this.startElement("jdbc-class-map");
         this.endElement("jdbc-class-map");
         if (mapping.version.type != null) {
            this.serializeAttributes(mapping.version);
            this.startElement("jdbc-version-ind");
            this.endElement("jdbc-version-ind");
         }

         if (mapping.discriminator.type != null) {
            this.serializeAttributes(mapping.discriminator);
            this.startElement("jdbc-class-ind");
            this.endElement("jdbc-class-ind");
         }

         Iterator itr = mapping.fields.values().iterator();

         while(itr.hasNext()) {
            this.serializeField((FieldMappingAttrs)itr.next());
         }

         this.endElement("class");
      }

      private void serializeField(FieldMappingAttrs field) throws SAXException {
         this.addAttribute("name", field.name);
         this.startElement("field");
         this.serializeAttributes(field);
         this.startElement("jdbc-field-map");
         Iterator itr = field.fields.values().iterator();

         while(itr.hasNext()) {
            this.serializeField((FieldMappingAttrs)itr.next());
         }

         this.endElement("jdbc-field-map");
         this.endElement("field");
      }

      private void serializeAttributes(MappingAttrs mapping) throws SAXException {
         this.addAttribute("type", mapping.type);
         Iterator itr = mapping.attrs.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            this.addAttribute((String)entry.getKey(), (String)entry.getValue());
         }

      }
   }

   protected static class MappingAttrsParser extends CFMetaDataParser {
      public static final String CLASS_MAPPING = "jdbc-class-map";
      public static final String FIELD_MAPPING = "jdbc-field-map";
      public static final String FIELD_MAPPINGS = "jdbc-field-mappings";
      public static final String VERSION_IND = "jdbc-version-ind";
      public static final String CLASS_IND = "jdbc-class-ind";
      public static final String SUFFIX = ".mapping";
      private MappingAttrsRepository _repos = null;
      private ClassMappingAttrs _class = null;
      private final Stack _fields = new Stack();

      public MappingAttrsParser(JDBCConfiguration conf) {
         this.setLog(conf.getLog("openjpa.MetaData"));
         this.setValidating(false);
         this.setSuffix(".mapping");
      }

      public MappingAttrsRepository getRepository() {
         if (this._repos == null) {
            this._repos = new MappingAttrsRepository();
         }

         return this._repos;
      }

      public void setRepository(MappingAttrsRepository repos) {
         this._repos = repos;
      }

      protected void finish() {
         super.finish();
         Collection results = this.getResults();
         Iterator itr = results.iterator();

         while(itr.hasNext()) {
            this.getRepository().addMapping((ClassMappingAttrs)itr.next());
         }

      }

      protected void reset() {
         super.reset();
         this._class = null;
         this._fields.clear();
      }

      protected boolean startClass(String elem, Attributes attrs) throws SAXException {
         super.startClass(elem, attrs);
         this._class = new ClassMappingAttrs(this.currentClassName());
         this._class.setSource(this.getSourceFile());
         return true;
      }

      protected void endClass(String elem) throws SAXException {
         if (this._class.type == null) {
            throw this.getException(AbstractDeprecatedJDOMappingFactory._loc.get("no-type-info", this._class.name));
         } else {
            this.addResult(this._class);
            this._class = null;
            super.endClass(elem);
         }
      }

      protected boolean startClassElement(String name, Attributes attrs) throws SAXException {
         if ("jdbc-class-map".equals(name)) {
            this.startClassMapping(attrs);
         } else if ("jdbc-version-ind".equals(name)) {
            this.startVersionIndicator(attrs);
         } else if ("jdbc-class-ind".equals(name)) {
            this.startDiscriminator(attrs);
         } else if ("jdbc-field-map".equals(name)) {
            this.startFieldMapping(attrs);
         } else {
            if (!"field".equals(name)) {
               return false;
            }

            this.startField(attrs);
         }

         return true;
      }

      protected void endClassElement(String name) throws SAXException {
         if ("field".equals(name)) {
            this.endField();
         }

      }

      private void startField(Attributes attrs) {
         FieldMappingAttrs field = new FieldMappingAttrs(attrs.getValue("name"));
         this._fields.push(field);
      }

      private void endField() throws SAXException {
         FieldMappingAttrs field = (FieldMappingAttrs)this._fields.pop();
         if (field.type == null) {
            throw this.getException(AbstractDeprecatedJDOMappingFactory._loc.get("no-type-info", this._class.name + "." + field.name));
         } else {
            if (!this._fields.isEmpty()) {
               ((FieldMappingAttrs)this._fields.peek()).fields.put(field.name, field);
            } else {
               this._class.fields.put(field.name, field);
            }

         }
      }

      private void startClassMapping(Attributes attrs) throws SAXException {
         this._class.type = attrs.getValue("type");
         this.addAttributes(this._class, attrs);
      }

      private void startFieldMapping(Attributes attrs) throws SAXException {
         FieldMappingAttrs field = (FieldMappingAttrs)this._fields.peek();
         field.type = attrs.getValue("type");
         this.addAttributes(field, attrs);
      }

      private void startVersionIndicator(Attributes attrs) throws SAXException {
         this._class.version.type = attrs.getValue("type");
         if (this._class.version.type == null) {
            throw this.getException(AbstractDeprecatedJDOMappingFactory._loc.get("no-type-info", this._class.name + ".<version>"));
         } else {
            this.addAttributes(this._class.version, attrs);
         }
      }

      private void startDiscriminator(Attributes attrs) throws SAXException {
         this._class.discriminator.type = attrs.getValue("type");
         if (this._class.discriminator.type == null) {
            throw this.getException(AbstractDeprecatedJDOMappingFactory._loc.get("no-type-info", this._class.name + ".<discriminator>"));
         } else {
            this.addAttributes(this._class.discriminator, attrs);
         }
      }

      private void addAttributes(MappingAttrs info, Attributes attrs) throws SAXException {
         int i = 0;

         for(int len = attrs.getLength(); i < len; ++i) {
            String name = attrs.getQName(i);
            String val = attrs.getValue(i);
            if (!"type".equals(name)) {
               info.attrs.put(name, val);
            }
         }

      }
   }

   protected static class MappingAttrsRepository {
      private final Map _mappings = new TreeMap();

      public ClassMappingAttrs getMapping(String clsName, boolean mustExist) {
         ClassMappingAttrs mapping = (ClassMappingAttrs)this._mappings.get(clsName);
         if (mapping != null) {
            return mapping;
         } else if (!mustExist) {
            return null;
         } else {
            throw new MetaDataException(AbstractDeprecatedJDOMappingFactory._loc.get("no-mapping", clsName));
         }
      }

      public ClassMappingAttrs[] getMappings() {
         Collection mappings = new ArrayList(this._mappings.size());
         Iterator itr = this._mappings.values().iterator();

         while(itr.hasNext()) {
            Object mapping = itr.next();
            if (mapping != null) {
               mappings.add(mapping);
            }
         }

         return (ClassMappingAttrs[])((ClassMappingAttrs[])mappings.toArray(new ClassMappingAttrs[mappings.size()]));
      }

      public void addMapping(ClassMappingAttrs mapping) {
         if (mapping != null) {
            this._mappings.put(mapping.name, mapping);
         }

      }

      public void addMappings(MappingAttrsRepository repos) {
         if (repos != null) {
            ClassMappingAttrs[] mappings = repos.getMappings();

            for(int i = 0; i < mappings.length; ++i) {
               this.addMapping(mappings[i]);
            }

         }
      }

      public boolean removeMapping(String clsName) {
         return this._mappings.remove(clsName) != null;
      }

      public boolean removeMappings(MappingAttrsRepository repos) {
         if (repos == null) {
            return false;
         } else {
            boolean removed = false;
            ClassMappingAttrs[] mappings = repos.getMappings();

            for(int i = 0; i < mappings.length; ++i) {
               removed |= this.removeMapping(mappings[i].name);
            }

            return removed;
         }
      }

      public void clear() {
         this._mappings.clear();
      }
   }

   protected static class FieldMappingAttrs extends MappingAttrs implements Comparable {
      public final String name;

      public FieldMappingAttrs(String name) {
         this.name = name;
      }

      public int compareTo(Object other) {
         return this.name.compareTo(((FieldMappingAttrs)other).name);
      }

      public String toString() {
         return super.toString() + ":" + this.name;
      }
   }

   protected static class ClassMappingAttrs extends MappingAttrs implements Comparable, SourceTracker {
      public final String name;
      public final MappingAttrs version = new MappingAttrs();
      public final MappingAttrs discriminator = new MappingAttrs();
      private File _sourceFile = null;

      public ClassMappingAttrs(String name) {
         this.name = name;
      }

      public File getSourceFile() {
         return this._sourceFile;
      }

      public Object getSourceScope() {
         return null;
      }

      public int getSourceType() {
         return 2;
      }

      public void setSource(File sourceFile) {
         this._sourceFile = sourceFile;
      }

      public String getResourceName() {
         return this.name;
      }

      public int compareTo(Object other) {
         return this.name.compareTo(((ClassMappingAttrs)other).name);
      }

      public String toString() {
         return super.toString() + ":" + this.name;
      }
   }

   protected static class MappingAttrs {
      public String type = null;
      public final Map attrs = new TreeMap();
      public final Map fields = new TreeMap();

      public String getAttribute(String name) {
         String val = (String)this.attrs.get(name);
         if (val == null && name != null) {
            Iterator itr = this.attrs.entrySet().iterator();

            Map.Entry entry;
            do {
               if (!itr.hasNext()) {
                  return null;
               }

               entry = (Map.Entry)itr.next();
            } while(!name.equalsIgnoreCase((String)entry.getKey()));

            return (String)entry.getValue();
         } else {
            return val;
         }
      }

      public String toString() {
         return super.toString() + ":" + this.type;
      }
   }
}
