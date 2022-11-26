package org.apache.openjpa.jdbc.meta;

import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.strats.BlobValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ByteArrayValueHandler;
import org.apache.openjpa.jdbc.meta.strats.CharArrayStreamValueHandler;
import org.apache.openjpa.jdbc.meta.strats.CharArrayValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ClassNameDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.ClobValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ElementEmbedValueHandler;
import org.apache.openjpa.jdbc.meta.strats.EmbedFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.EmbeddedClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.FlatClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.FullClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerHandlerMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.HandlerRelationMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.ImmutableValueHandler;
import org.apache.openjpa.jdbc.meta.strats.LobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedBlobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedByteArrayFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedCharArrayFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.MaxEmbeddedClobFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NanoPrecisionTimestampVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.NumberVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.ObjectIdClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.ObjectIdValueHandler;
import org.apache.openjpa.jdbc.meta.strats.PrimitiveFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionInverseKeyFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationHandlerMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationMapInverseKeyFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationRelationMapTableFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.StateComparisonVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.StringFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.SubclassJoinDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.SuperclassDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.SuperclassVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.TimestampVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.UntypedPCValueHandler;
import org.apache.openjpa.jdbc.meta.strats.ValueMapDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.VerticalClassStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.Order;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.MetaDataException;

public class MappingRepository extends MetaDataRepository {
   private static final Localizer _loc = Localizer.forPackage(MappingRepository.class);
   private transient DBDictionary _dict = null;
   private transient MappingDefaults _defaults = null;
   private Map _results = new HashMap();
   private SchemaGroup _schema = null;
   private StrategyInstaller _installer = null;

   public MappingRepository() {
      this.setValidate(2, true);
   }

   public DBDictionary getDBDictionary() {
      return this._dict;
   }

   public MappingDefaults getMappingDefaults() {
      return this._defaults;
   }

   public void setMappingDefaults(MappingDefaults defaults) {
      this._defaults = defaults;
   }

   public synchronized SchemaGroup getSchemaGroup() {
      if (this._schema == null) {
         this._schema = ((JDBCConfiguration)this.getConfiguration()).getSchemaFactoryInstance().readSchema();
      }

      return this._schema;
   }

   public synchronized void setSchemaGroup(SchemaGroup schema) {
      this._schema = schema;
   }

   public synchronized StrategyInstaller getStrategyInstaller() {
      if (this._installer == null) {
         this._installer = new RuntimeStrategyInstaller(this);
      }

      return this._installer;
   }

   public synchronized void setStrategyInstaller(StrategyInstaller installer) {
      this._installer = installer;
   }

   public synchronized QueryResultMapping getQueryResultMapping(Class cls, String name, ClassLoader envLoader, boolean mustExist) {
      QueryResultMapping res = this.getQueryResultMappingInternal(cls, name, envLoader);
      if (res == null && mustExist) {
         throw new MetaDataException(_loc.get("no-query-res", cls, name));
      } else {
         return res;
      }
   }

   private QueryResultMapping getQueryResultMappingInternal(Class cls, String name, ClassLoader envLoader) {
      if (name == null) {
         return null;
      } else {
         Object key = getQueryResultKey(cls, name);
         QueryResultMapping res = (QueryResultMapping)this._results.get(key);
         if (res != null) {
            return res;
         } else {
            if (cls != null && this.getMetaData(cls, envLoader, false) != null) {
               res = (QueryResultMapping)this._results.get(key);
               if (res != null) {
                  return res;
               }
            }

            if ((this.getSourceMode() & 4) == 0) {
               return null;
            } else {
               if (cls == null) {
                  cls = this.getMetaDataFactory().getResultSetMappingScope(name, envLoader);
               }

               this.getMetaDataFactory().load(cls, 3, envLoader);
               return (QueryResultMapping)this._results.get(key);
            }
         }
      }
   }

   public synchronized QueryResultMapping[] getQueryResultMappings() {
      Collection values = this._results.values();
      return (QueryResultMapping[])((QueryResultMapping[])values.toArray(new QueryResultMapping[values.size()]));
   }

   public synchronized QueryResultMapping getCachedQueryResultMapping(Class cls, String name) {
      return (QueryResultMapping)this._results.get(getQueryResultKey(cls, name));
   }

   public synchronized QueryResultMapping addQueryResultMapping(Class cls, String name) {
      QueryResultMapping res = new QueryResultMapping(name, this);
      res.setDefiningType(cls);
      this._results.put(getQueryResultKey(res), res);
      return res;
   }

   public synchronized boolean removeQueryResultMapping(QueryResultMapping res) {
      return this._results.remove(getQueryResultKey(res)) != null;
   }

   public synchronized boolean removeQueryResultMapping(Class cls, String name) {
      if (name == null) {
         return false;
      } else {
         return this._results.remove(getQueryResultKey(cls, name)) != null;
      }
   }

   private static Object getQueryResultKey(QueryResultMapping res) {
      return res == null ? null : getQueryResultKey(res.getDefiningType(), res.getName());
   }

   private static Object getQueryResultKey(Class cls, String name) {
      return getQueryKey(cls, name);
   }

   public ClassMapping getMapping(Class cls, ClassLoader envLoader, boolean mustExist) {
      return (ClassMapping)super.getMetaData(cls, envLoader, mustExist);
   }

   public ClassMapping[] getMappings() {
      return (ClassMapping[])((ClassMapping[])super.getMetaDatas());
   }

   public ClassMapping getMapping(Object oid, ClassLoader envLoader, boolean mustExist) {
      return (ClassMapping)super.getMetaData(oid, envLoader, mustExist);
   }

   public ClassMapping[] getImplementorMappings(Class cls, ClassLoader envLoader, boolean mustExist) {
      return (ClassMapping[])((ClassMapping[])super.getImplementorMetaDatas(cls, envLoader, mustExist));
   }

   public synchronized void clear() {
      super.clear();
      this._schema = null;
      this._results.clear();
   }

   protected void prepareMapping(ClassMetaData meta) {
      ClassMapping mapping = (ClassMapping)meta;
      ClassMapping sup = mapping.getPCSuperclassMapping();
      if (sup == null || (mapping.getResolve() & 2) == 0) {
         this.getStrategyInstaller().installStrategy(mapping);
         mapping.defineSuperclassFields(mapping.getJoinablePCSuperclassMapping() == null);
         mapping.resolveNonRelationMappings();
      }
   }

   protected ClassMetaData newClassMetaData(Class type) {
      return new ClassMapping(type, this);
   }

   protected ClassMetaData[] newClassMetaDataArray(int length) {
      return new ClassMapping[length];
   }

   protected FieldMetaData newFieldMetaData(String name, Class type, ClassMetaData owner) {
      return new FieldMapping(name, type, (ClassMapping)owner);
   }

   protected FieldMetaData[] newFieldMetaDataArray(int length) {
      return new FieldMapping[length];
   }

   protected ClassMetaData newEmbeddedClassMetaData(ValueMetaData owner) {
      return new ClassMapping(owner);
   }

   protected ValueMetaData newValueMetaData(FieldMetaData owner) {
      return new ValueMappingImpl((FieldMapping)owner);
   }

   protected SequenceMetaData newSequenceMetaData(String name) {
      return new SequenceMapping(name, this);
   }

   protected Order newValueOrder(FieldMetaData owner, boolean asc) {
      return new JDBCValueOrder((FieldMapping)owner, asc);
   }

   protected Order newRelatedFieldOrder(FieldMetaData owner, FieldMetaData rel, boolean asc) {
      return new JDBCRelatedFieldOrder((FieldMapping)owner, (FieldMapping)rel, asc);
   }

   protected Order[] newOrderArray(int size) {
      return new JDBCOrder[size];
   }

   protected Version newVersion(ClassMapping cls) {
      return new Version(cls);
   }

   protected Discriminator newDiscriminator(ClassMapping cls) {
      return new Discriminator(cls);
   }

   protected ClassMappingInfo newMappingInfo(ClassMapping cls) {
      ClassMappingInfo info = new ClassMappingInfo();
      info.setClassName(cls.getDescribedType().getName());
      return info;
   }

   protected FieldMappingInfo newMappingInfo(FieldMapping fm) {
      return new FieldMappingInfo();
   }

   protected ValueMappingInfo newMappingInfo(ValueMapping vm) {
      return new ValueMappingInfo();
   }

   protected VersionMappingInfo newMappingInfo(Version version) {
      return new VersionMappingInfo();
   }

   protected DiscriminatorMappingInfo newMappingInfo(Discriminator disc) {
      return new DiscriminatorMappingInfo();
   }

   protected ClassStrategy namedStrategy(ClassMapping cls) {
      String name = cls.getMappingInfo().getStrategy();
      return this.instantiateClassStrategy(name, cls);
   }

   protected ClassStrategy instantiateClassStrategy(String name, ClassMapping cls) {
      if (name == null) {
         return null;
      } else if ("none".equals(name)) {
         return NoneClassStrategy.getInstance();
      } else {
         String props = Configurations.getProperties(name);
         name = Configurations.getClassName(name);
         Class strat = null;
         if ("full".equals(name)) {
            strat = FullClassStrategy.class;
         } else if ("flat".equals(name)) {
            strat = FlatClassStrategy.class;
         } else if ("vertical".equals(name)) {
            strat = VerticalClassStrategy.class;
         }

         try {
            if (strat == null) {
               strat = JavaTypes.classForName(name, (ClassMetaData)cls, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(ClassStrategy.class)));
            }

            ClassStrategy strategy = (ClassStrategy)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(strat));
            Configurations.configureInstance(strategy, this.getConfiguration(), (String)props);
            return strategy;
         } catch (Exception var6) {
            Exception e = var6;
            if (var6 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var6).getException();
            }

            throw new MetaDataException(_loc.get("bad-cls-strategy", cls, name), e);
         }
      }
   }

   protected FieldStrategy namedStrategy(FieldMapping field, boolean installHandlers) {
      String name = field.getMappingInfo().getStrategy();
      if (name == null) {
         return null;
      } else if ("none".equals(name)) {
         return NoneFieldStrategy.getInstance();
      } else {
         String props = Configurations.getProperties(name);
         name = Configurations.getClassName(name);

         try {
            Class c = JavaTypes.classForName(name, (ValueMetaData)field, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(FieldStrategy.class)));
            if (FieldStrategy.class.isAssignableFrom(c)) {
               FieldStrategy strat = (FieldStrategy)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
               Configurations.configureInstance(strat, this.getConfiguration(), (String)props);
               return strat;
            } else {
               if (installHandlers) {
                  ValueHandler vh = (ValueHandler)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
                  Configurations.configureInstance(vh, this.getConfiguration(), (String)props);
                  field.setHandler(vh);
               }

               return new HandlerFieldStrategy();
            }
         } catch (Exception var7) {
            Exception e = var7;
            if (var7 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var7).getException();
            }

            throw new MetaDataException(_loc.get("bad-field-strategy", field, name), e);
         }
      }
   }

   protected DiscriminatorStrategy namedStrategy(Discriminator discrim) {
      String name = discrim.getMappingInfo().getStrategy();
      if (name == null) {
         return null;
      } else {
         ClassMapping cls;
         for(cls = discrim.getClassMapping(); cls.getJoinablePCSuperclassMapping() != null; cls = cls.getJoinablePCSuperclassMapping()) {
         }

         Discriminator base = cls.getDiscriminator();
         return base != discrim && base.getStrategy() != null && name.equals(base.getStrategy().getAlias()) ? null : this.instantiateDiscriminatorStrategy(name, discrim);
      }
   }

   protected DiscriminatorStrategy instantiateDiscriminatorStrategy(String name, Discriminator discrim) {
      if ("none".equals(name)) {
         return NoneDiscriminatorStrategy.getInstance();
      } else {
         String props = Configurations.getProperties(name);
         name = Configurations.getClassName(name);
         Class strat = null;
         if ("class-name".equals(name)) {
            strat = ClassNameDiscriminatorStrategy.class;
         } else if ("value-map".equals(name)) {
            strat = ValueMapDiscriminatorStrategy.class;
         } else if ("subclass-join".equals(name)) {
            strat = SubclassJoinDiscriminatorStrategy.class;
         }

         try {
            if (strat == null) {
               strat = JavaTypes.classForName(name, (ClassMetaData)discrim.getClassMapping(), (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(DiscriminatorStrategy.class)));
            }

            DiscriminatorStrategy strategy = (DiscriminatorStrategy)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(strat));
            Configurations.configureInstance(strategy, this.getConfiguration(), (String)props);
            return strategy;
         } catch (Exception var6) {
            Exception e = var6;
            if (var6 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var6).getException();
            }

            throw new MetaDataException(_loc.get("bad-discrim-strategy", discrim.getClassMapping(), name), e);
         }
      }
   }

   protected VersionStrategy namedStrategy(Version version) {
      String name = version.getMappingInfo().getStrategy();
      if (name == null) {
         return null;
      } else {
         ClassMapping cls;
         for(cls = version.getClassMapping(); cls.getJoinablePCSuperclassMapping() != null; cls = cls.getJoinablePCSuperclassMapping()) {
         }

         Version base = cls.getVersion();
         return base != version && base.getStrategy() != null && name.equals(base.getStrategy().getAlias()) ? null : this.instantiateVersionStrategy(name, version);
      }
   }

   protected VersionStrategy instantiateVersionStrategy(String name, Version version) {
      if ("none".equals(name)) {
         return NoneVersionStrategy.getInstance();
      } else {
         String props = Configurations.getProperties(name);
         name = Configurations.getClassName(name);
         Class strat = null;
         if ("version-number".equals(name)) {
            strat = NumberVersionStrategy.class;
         } else if ("timestamp".equals(name)) {
            strat = TimestampVersionStrategy.class;
         } else if ("nano-timestamp".equals(name)) {
            strat = NanoPrecisionTimestampVersionStrategy.class;
         } else if ("state-comparison".equals(name)) {
            strat = StateComparisonVersionStrategy.class;
         }

         try {
            if (strat == null) {
               strat = JavaTypes.classForName(name, (ClassMetaData)version.getClassMapping(), (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(VersionStrategy.class)));
            }
         } catch (Exception var6) {
            throw new MetaDataException(_loc.get("bad-version-strategy", version.getClassMapping(), name), var6);
         }

         return this.instantiateVersionStrategy(strat, version, props);
      }
   }

   protected VersionStrategy instantiateVersionStrategy(Class strat, Version version, String props) {
      try {
         VersionStrategy strategy = (VersionStrategy)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(strat));
         Configurations.configureInstance(strategy, this.getConfiguration(), (String)props);
         return strategy;
      } catch (Exception var5) {
         Exception e = var5;
         if (var5 instanceof PrivilegedActionException) {
            e = ((PrivilegedActionException)var5).getException();
         }

         throw new MetaDataException(_loc.get("bad-version-strategy", version.getClassMapping(), strat + ""), e);
      }
   }

   protected ClassStrategy defaultStrategy(ClassMapping cls) {
      return this.defaultStrategy(cls, this.getStrategyInstaller().isAdapting());
   }

   protected ClassStrategy defaultStrategy(ClassMapping cls, boolean adapting) {
      ValueMapping embed = cls.getEmbeddingMapping();
      if (embed != null) {
         if (embed.getType() == cls.getDescribedType() && embed.getFieldMapping().getStrategy() != NoneFieldStrategy.getInstance()) {
            return (ClassStrategy)(embed.getTypeCode() == 29 ? new ObjectIdClassStrategy() : new EmbeddedClassStrategy());
         } else {
            return NoneClassStrategy.getInstance();
         }
      } else if (cls.isEmbeddedOnly()) {
         return NoneClassStrategy.getInstance();
      } else {
         Object strat = this._defaults.getStrategy(cls, adapting);
         if (strat instanceof String) {
            return this.instantiateClassStrategy((String)strat, cls);
         } else if (strat != null) {
            return (ClassStrategy)strat;
         } else {
            ClassStrategy hstrat = null;

            ClassMapping sup;
            for(sup = cls; sup != null && hstrat == null; sup = sup.getMappedPCSuperclassMapping()) {
               hstrat = this.instantiateClassStrategy(sup.getMappingInfo().getHierarchyStrategy(), cls);
            }

            if (hstrat instanceof FullClassStrategy && !cls.isManagedInterface() && Modifier.isAbstract(cls.getDescribedType().getModifiers())) {
               return NoneClassStrategy.getInstance();
            } else {
               sup = cls.getMappedPCSuperclassMapping();
               if (sup == null) {
                  return new FullClassStrategy();
               } else {
                  return (ClassStrategy)(hstrat != null ? hstrat : new FlatClassStrategy());
               }
            }
         }
      }
   }

   protected FieldStrategy defaultStrategy(FieldMapping field, boolean installHandlers) {
      return this.defaultStrategy(field, installHandlers, this.getStrategyInstaller().isAdapting());
   }

   protected FieldStrategy defaultStrategy(FieldMapping field, boolean installHandlers, boolean adapting) {
      if (field.getManagement() == 3 && !field.isVersion()) {
         if (field.getDefiningMapping().getStrategy() == NoneClassStrategy.getInstance()) {
            return NoneFieldStrategy.getInstance();
         } else {
            ValueHandler handler = this.namedHandler(field);
            if (handler != null) {
               if (installHandlers) {
                  field.setHandler(handler);
               }

               return new HandlerFieldStrategy();
            } else {
               if (field.isSerialized()) {
                  if (this._dict.maxEmbeddedBlobSize != -1) {
                     return new MaxEmbeddedBlobFieldStrategy();
                  }
               } else {
                  Object strat = this.mappedStrategy(field, field.getType(), adapting);
                  if (strat instanceof FieldStrategy) {
                     return (FieldStrategy)strat;
                  }

                  if (strat != null) {
                     if (installHandlers) {
                        field.setHandler((ValueHandler)strat);
                     }

                     return new HandlerFieldStrategy();
                  }
               }

               if (!field.isSerialized() && (field.getType() == byte[].class || field.getType() == Byte[].class)) {
                  if (this._dict.maxEmbeddedBlobSize != -1) {
                     return new MaxEmbeddedByteArrayFieldStrategy();
                  }
               } else if (!field.isSerialized() && (field.getType() == char[].class || field.getType() == Character[].class)) {
                  if (this._dict.maxEmbeddedClobSize != -1 && this.isClob(field, false)) {
                     return new MaxEmbeddedCharArrayFieldStrategy();
                  }
               } else if (!field.isSerialized()) {
                  FieldStrategy strat = this.defaultTypeStrategy(field, installHandlers, adapting);
                  if (strat != null) {
                     return strat;
                  }
               }

               handler = this.defaultHandler(field, adapting);
               if (handler != null) {
                  if (installHandlers) {
                     field.setHandler(handler);
                  }

                  return new HandlerFieldStrategy();
               } else {
                  if (installHandlers) {
                     if (this.getLog().isWarnEnabled()) {
                        this.getLog().warn(_loc.get("no-field-strategy", (Object)field));
                     }

                     field.setSerialized(true);
                  }

                  if (this._dict.maxEmbeddedBlobSize == -1) {
                     if (installHandlers) {
                        field.setHandler(BlobValueHandler.getInstance());
                     }

                     return new HandlerFieldStrategy();
                  } else {
                     return new MaxEmbeddedBlobFieldStrategy();
                  }
               }
            }
         }
      } else {
         return NoneFieldStrategy.getInstance();
      }
   }

   protected FieldStrategy defaultTypeStrategy(FieldMapping field, boolean installHandlers, boolean adapting) {
      switch (field.getTypeCode()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
            return new PrimitiveFieldStrategy();
         case 8:
         case 10:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         default:
            break;
         case 9:
            if (!this.isClob(field, false)) {
               return new StringFieldStrategy();
            }

            if (this._dict.maxEmbeddedClobSize != -1) {
               return new MaxEmbeddedClobFieldStrategy();
            }
            break;
         case 11:
         case 12:
            ValueMapping elem = field.getElementMapping();
            ValueHandler ehandler = this.namedHandler(elem);
            if (ehandler == null) {
               ehandler = this.defaultHandler(elem);
            }

            if (ehandler != null) {
               return this.handlerCollectionStrategy(field, ehandler, installHandlers);
            }

            if (elem.getTypeCode() == 15 && !elem.isSerialized() && !elem.isEmbeddedPC()) {
               if (this.useInverseKeyMapping(field)) {
                  return new RelationCollectionInverseKeyFieldStrategy();
               }

               return new RelationCollectionTableFieldStrategy();
            }
            break;
         case 13:
            ValueMapping key = field.getKeyMapping();
            ValueHandler khandler = this.namedHandler(key);
            if (khandler == null) {
               khandler = this.defaultHandler(key);
            }

            ValueMapping val = field.getElementMapping();
            ValueHandler vhandler = this.namedHandler(val);
            if (vhandler == null) {
               vhandler = this.defaultHandler(val);
            }

            boolean krel = khandler == null && key.getTypeCode() == 15 && !key.isSerialized() && !key.isEmbeddedPC();
            boolean vrel = vhandler == null && val.getTypeCode() == 15 && !val.isSerialized() && !val.isEmbeddedPC();
            if (!krel && vrel && key.getValueMappedBy() != null) {
               if (this.useInverseKeyMapping(field)) {
                  return new RelationMapInverseKeyFieldStrategy();
               }

               return new RelationMapTableFieldStrategy();
            }

            if ((krel || khandler != null) && (vrel || vhandler != null)) {
               return this.handlerMapStrategy(field, khandler, vhandler, krel, vrel, installHandlers);
            }
            break;
         case 15:
            if (field.isEmbeddedPC()) {
               return new EmbedFieldStrategy();
            }

            if (!field.getTypeMapping().isMapped() && this.useUntypedPCHandler(field)) {
               break;
            }

            return new RelationFieldStrategy();
         case 30:
         case 31:
            return new LobFieldStrategy();
      }

      return null;
   }

   protected FieldStrategy handlerCollectionStrategy(FieldMapping field, ValueHandler ehandler, boolean installHandlers) {
      if (this.getConfiguration().getCompatibilityInstance().getStoreMapCollectionInEntityAsBlob()) {
         return null;
      } else {
         if (installHandlers) {
            field.getElementMapping().setHandler(ehandler);
         }

         return new HandlerCollectionTableFieldStrategy();
      }
   }

   protected FieldStrategy handlerMapStrategy(FieldMapping field, ValueHandler khandler, ValueHandler vhandler, boolean krel, boolean vrel, boolean installHandlers) {
      if (this.getConfiguration().getCompatibilityInstance().getStoreMapCollectionInEntityAsBlob()) {
         return null;
      } else {
         if (installHandlers) {
            field.getKeyMapping().setHandler(khandler);
            field.getElementMapping().setHandler(vhandler);
         }

         if (!krel && !vrel) {
            return new HandlerHandlerMapTableFieldStrategy();
         } else if (!krel && vrel) {
            return new HandlerRelationMapTableFieldStrategy();
         } else {
            return (FieldStrategy)(krel && !vrel ? new RelationHandlerMapTableFieldStrategy() : new RelationRelationMapTableFieldStrategy());
         }
      }
   }

   private boolean useInverseKeyMapping(FieldMapping field) {
      FieldMapping mapped = field.getMappedByMapping();
      if (mapped != null) {
         if (mapped.getTypeCode() == 15) {
            return true;
         } else if (mapped.getElement().getTypeCode() == 15) {
            return false;
         } else {
            throw new MetaDataException(_loc.get("bad-mapped-by", field, mapped));
         }
      } else {
         FieldMappingInfo info = field.getMappingInfo();
         ValueMapping elem = field.getElementMapping();
         return info.getTableName() == null && info.getColumns().isEmpty() && !elem.getValueInfo().getColumns().isEmpty();
      }
   }

   private Object mappedStrategy(ValueMapping val, Class type, boolean adapting) {
      if (type != null && type != Object.class) {
         Object strat = this._defaults.getStrategy(val, type, adapting);
         if (strat == null) {
            return this.mappedStrategy(val, type.getSuperclass(), adapting);
         } else if (!(strat instanceof String)) {
            return strat;
         } else {
            String name = (String)strat;
            if ("none".equals(name)) {
               return NoneFieldStrategy.getInstance();
            } else {
               String props = Configurations.getProperties(name);
               name = Configurations.getClassName(name);

               try {
                  Class c = JavaTypes.classForName(name, (ValueMetaData)val, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(FieldStrategy.class)));
                  Object o = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
                  Configurations.configureInstance(o, this.getConfiguration(), (String)props);
                  return o;
               } catch (Exception var9) {
                  Exception e = var9;
                  if (var9 instanceof PrivilegedActionException) {
                     e = ((PrivilegedActionException)var9).getException();
                  }

                  throw new MetaDataException(_loc.get("bad-mapped-strategy", val, name), e);
               }
            }
         }
      } else {
         return null;
      }
   }

   protected ValueHandler namedHandler(ValueMapping val) {
      String name = val.getValueInfo().getStrategy();
      if (name == null) {
         return null;
      } else {
         String props = Configurations.getProperties(name);
         name = Configurations.getClassName(name);

         try {
            Class c = JavaTypes.classForName(name, (ValueMetaData)val, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(ValueHandler.class)));
            if (ValueHandler.class.isAssignableFrom(c)) {
               ValueHandler vh = (ValueHandler)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
               Configurations.configureInstance(vh, this.getConfiguration(), (String)props);
               return vh;
            } else {
               return null;
            }
         } catch (Exception var6) {
            Exception e = var6;
            if (var6 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var6).getException();
            }

            throw new MetaDataException(_loc.get("bad-value-handler", val, name), e);
         }
      }
   }

   protected ValueHandler defaultHandler(ValueMapping val) {
      return this.defaultHandler(val, this.getStrategyInstaller().isAdapting());
   }

   protected ValueHandler defaultHandler(ValueMapping val, boolean adapting) {
      if (val.isSerialized()) {
         if (this._dict.maxEmbeddedBlobSize != -1) {
            this.warnMaxEmbedded(val, this._dict.maxEmbeddedBlobSize);
         }

         return BlobValueHandler.getInstance();
      } else {
         Object handler = this.mappedStrategy(val, val.getType(), adapting);
         if (handler instanceof ValueHandler) {
            return (ValueHandler)handler;
         } else if (val.getType() != byte[].class && val.getType() != Byte[].class) {
            if (val.getType() != char[].class && val.getType() != Character[].class) {
               switch (val.getTypeCode()) {
                  case 0:
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 10:
                  case 14:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                  case 26:
                  case 28:
                     return ImmutableValueHandler.getInstance();
                  case 9:
                     if (this.isClob(val, true)) {
                        return ClobValueHandler.getInstance();
                     }

                     return ImmutableValueHandler.getInstance();
                  case 15:
                     if (!val.getTypeMapping().isMapped() && this.useUntypedPCHandler(val)) {
                        return UntypedPCValueHandler.getInstance();
                     }
                  case 8:
                  case 11:
                  case 12:
                  case 13:
                  default:
                     if (!this.getConfiguration().getCompatibilityInstance().getStoreMapCollectionInEntityAsBlob() && val.isEmbeddedPC()) {
                        return new ElementEmbedValueHandler();
                     }

                     return null;
                  case 27:
                     return UntypedPCValueHandler.getInstance();
                  case 29:
                     return new ObjectIdValueHandler();
               }
            } else {
               return (ValueHandler)(this.isClob(val, true) ? CharArrayStreamValueHandler.getInstance() : CharArrayValueHandler.getInstance());
            }
         } else {
            if (this._dict.maxEmbeddedBlobSize != -1) {
               this.warnMaxEmbedded(val, this._dict.maxEmbeddedBlobSize);
            }

            return ByteArrayValueHandler.getInstance();
         }
      }
   }

   private boolean useUntypedPCHandler(ValueMapping val) {
      ClassMapping rel = val.getTypeMapping();
      return rel.getIdentityType() == 0 || rel.getIdentityType() == 2 && (rel.getPrimaryKeyFields().length == 0 || !rel.isOpenJPAIdentity() && Modifier.isAbstract(rel.getObjectIdType().getModifiers()));
   }

   private boolean isClob(ValueMapping val, boolean warn) {
      List cols = val.getValueInfo().getColumns();
      if (cols.size() != 1) {
         return false;
      } else {
         Column col = (Column)cols.get(0);
         if (col.getSize() != -1 && col.getType() != 2005) {
            return false;
         } else if (this._dict.getPreferredType(2005) != 2005) {
            return false;
         } else {
            if (warn && this._dict.maxEmbeddedClobSize != -1) {
               this.warnMaxEmbedded(val, this._dict.maxEmbeddedClobSize);
            }

            return true;
         }
      }
   }

   private void warnMaxEmbedded(ValueMapping val, int size) {
      if (this.getLog().isWarnEnabled()) {
         this.getLog().warn(_loc.get("max-embed-lob", val, String.valueOf(size)));
      }

   }

   protected DiscriminatorStrategy defaultStrategy(Discriminator discrim) {
      return this.defaultStrategy(discrim, this.getStrategyInstaller().isAdapting());
   }

   protected DiscriminatorStrategy defaultStrategy(Discriminator discrim, boolean adapting) {
      ClassMapping cls = discrim.getClassMapping();
      if (cls.getEmbeddingMetaData() != null) {
         return NoneDiscriminatorStrategy.getInstance();
      } else if (cls.getJoinablePCSuperclassMapping() != null || cls.getStrategy() != NoneClassStrategy.getInstance() && !Modifier.isFinal(discrim.getClassMapping().getDescribedType().getModifiers())) {
         Object strat = this._defaults.getStrategy(discrim, adapting);
         if (strat instanceof String) {
            return this.instantiateDiscriminatorStrategy((String)strat, discrim);
         } else if (strat != null) {
            return (DiscriminatorStrategy)strat;
         } else if (cls.getJoinablePCSuperclassMapping() != null) {
            return new SuperclassDiscriminatorStrategy();
         } else if (discrim.getMappingInfo().getValue() != null) {
            return new ValueMapDiscriminatorStrategy();
         } else if (cls.getMappedPCSuperclassMapping() != null) {
            return NoneDiscriminatorStrategy.getInstance();
         } else if (!adapting && !this._defaults.defaultMissingInfo()) {
            DBDictionary dict = ((JDBCConfiguration)this.getConfiguration()).getDBDictionaryInstance();
            return (DiscriminatorStrategy)(dict.joinSyntax == 1 ? NoneDiscriminatorStrategy.getInstance() : new SubclassJoinDiscriminatorStrategy());
         } else {
            return new ClassNameDiscriminatorStrategy();
         }
      } else {
         return NoneDiscriminatorStrategy.getInstance();
      }
   }

   protected VersionStrategy defaultStrategy(Version version) {
      return this.defaultStrategy(version, this.getStrategyInstaller().isAdapting());
   }

   protected VersionStrategy defaultStrategy(Version version, boolean adapting) {
      ClassMapping cls = version.getClassMapping();
      if (cls.getEmbeddingMetaData() != null) {
         return NoneVersionStrategy.getInstance();
      } else if (cls.getJoinablePCSuperclassMapping() == null && cls.getStrategy() == NoneClassStrategy.getInstance()) {
         return NoneVersionStrategy.getInstance();
      } else {
         Object strat = this._defaults.getStrategy(version, adapting);
         if (strat instanceof String) {
            return this.instantiateVersionStrategy((String)strat, version);
         } else if (strat != null) {
            return (VersionStrategy)strat;
         } else if (cls.getJoinablePCSuperclassMapping() != null) {
            return new SuperclassVersionStrategy();
         } else {
            FieldMapping vfield = version.getClassMapping().getVersionFieldMapping();
            if (vfield != null) {
               return this.defaultStrategy(version, vfield);
            } else {
               return (VersionStrategy)(!adapting && !this._defaults.defaultMissingInfo() ? NoneVersionStrategy.getInstance() : new NumberVersionStrategy());
            }
         }
      }
   }

   protected VersionStrategy defaultStrategy(Version vers, FieldMapping vfield) {
      switch (vfield.getTypeCode()) {
         case 1:
         case 5:
         case 6:
         case 7:
         case 10:
         case 17:
         case 21:
         case 22:
         case 23:
            return new NumberVersionStrategy();
         case 2:
         case 3:
         case 4:
         case 8:
         case 9:
         case 11:
         case 12:
         case 13:
         case 15:
         case 16:
         case 18:
         case 19:
         case 20:
         case 24:
         case 25:
         case 26:
         case 27:
         default:
            return NoneVersionStrategy.getInstance();
         case 14:
         case 28:
            return (VersionStrategy)(JavaVersions.VERSION >= 5 ? new NanoPrecisionTimestampVersionStrategy() : new TimestampVersionStrategy());
      }
   }

   public void endConfiguration() {
      super.endConfiguration();
      JDBCConfiguration conf = (JDBCConfiguration)this.getConfiguration();
      this._dict = conf.getDBDictionaryInstance();
      if (this._defaults == null) {
         this._defaults = conf.getMappingDefaultsInstance();
      }

      if (this._schema != null && this._schema instanceof Configurable) {
         ((Configurable)this._schema).setConfiguration(conf);
         ((Configurable)this._schema).startConfiguration();
         ((Configurable)this._schema).endConfiguration();
      }

   }
}
