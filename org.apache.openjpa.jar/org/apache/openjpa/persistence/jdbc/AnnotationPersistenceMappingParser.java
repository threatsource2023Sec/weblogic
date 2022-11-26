package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.ColumnResult;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FieldResult;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPriv5Helper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataParser;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class AnnotationPersistenceMappingParser extends AnnotationPersistenceMetaDataParser {
   protected static final int TRUE = 1;
   protected static final int FALSE = 2;
   private static final Localizer _loc = Localizer.forPackage(AnnotationPersistenceMappingParser.class);
   private static final Map _tags = new HashMap();

   public AnnotationPersistenceMappingParser(JDBCConfiguration conf) {
      super(conf);
   }

   protected void parsePackageMappingAnnotations(Package pkg) {
      Annotation[] arr$ = pkg.getDeclaredAnnotations();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Annotation anno = arr$[i$];
         MappingTag tag = (MappingTag)_tags.get(anno.annotationType());
         if (tag == null) {
            this.handleUnknownPackageMappingAnnotation(pkg, anno);
         } else {
            switch (tag) {
               case TABLE_GEN:
                  this.parseTableGenerator(pkg, (TableGenerator)anno);
                  break;
               default:
                  throw new UnsupportedException(_loc.get("unsupported", pkg, anno.toString()));
            }
         }
      }

   }

   protected boolean handleUnknownPackageMappingAnnotation(Package pkg, Annotation anno) {
      return false;
   }

   private void parseTableGenerator(AnnotatedElement el, TableGenerator gen) {
      String name = gen.name();
      if (StringUtils.isEmpty(name)) {
         throw new MetaDataException(_loc.get("no-gen-name", (Object)el));
      } else {
         Log log = this.getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-gen", (Object)name));
         }

         SequenceMapping meta = (SequenceMapping)this.getRepository().getCachedSequenceMetaData(name);
         if (meta != null) {
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("dup-gen", name, el));
            }

         } else {
            meta = (SequenceMapping)this.getRepository().addSequenceMetaData(name);
            meta.setSequencePlugin("value-table");
            meta.setTable(toTableName(gen.schema(), gen.table()));
            meta.setPrimaryKeyColumn(gen.pkColumnName());
            meta.setSequenceColumn(gen.valueColumnName());
            meta.setPrimaryKeyValue(gen.pkColumnValue());
            meta.setInitialValue(gen.initialValue());
            meta.setAllocate(gen.allocationSize());
            meta.setSource(this.getSourceFile(), el instanceof Class ? el : null, 1);
            if (gen.uniqueConstraints().length > 0 && log.isWarnEnabled()) {
               log.warn(_loc.get("unique-constraints", (Object)name));
            }

         }
      }
   }

   protected void parseClassMappingAnnotations(ClassMetaData meta) {
      ClassMapping cm = (ClassMapping)meta;
      Class cls = cm.getDescribedType();
      Annotation[] arr$ = cls.getDeclaredAnnotations();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Annotation anno = arr$[i$];
         MappingTag tag = (MappingTag)_tags.get(anno.annotationType());
         if (tag == null) {
            this.handleUnknownClassMappingAnnotation(cm, anno);
         } else {
            switch (tag) {
               case TABLE_GEN:
                  this.parseTableGenerator(cls, (TableGenerator)anno);
                  break;
               case ASSOC_OVERRIDE:
                  this.parseAssociationOverrides(cm, (AssociationOverride)anno);
                  break;
               case ASSOC_OVERRIDES:
                  this.parseAssociationOverrides(cm, ((AssociationOverrides)anno).value());
                  break;
               case ATTR_OVERRIDE:
                  this.parseAttributeOverrides(cm, (AttributeOverride)anno);
                  break;
               case ATTR_OVERRIDES:
                  this.parseAttributeOverrides(cm, ((AttributeOverrides)anno).value());
                  break;
               case DISCRIM_COL:
                  this.parseDiscriminatorColumn(cm, (DiscriminatorColumn)anno);
                  break;
               case DISCRIM_VAL:
                  cm.getDiscriminator().getMappingInfo().setValue(((DiscriminatorValue)anno).value());
                  if (Modifier.isAbstract(cm.getDescribedType().getModifiers()) && this.getLog().isInfoEnabled()) {
                     this.getLog().info(_loc.get("discriminator-on-abstract-class", (Object)cm.getDescribedType().getName()));
                  }
                  break;
               case INHERITANCE:
                  this.parseInheritance(cm, (Inheritance)anno);
                  break;
               case PK_JOIN_COL:
                  this.parsePrimaryKeyJoinColumns(cm, (PrimaryKeyJoinColumn)anno);
                  break;
               case PK_JOIN_COLS:
                  this.parsePrimaryKeyJoinColumns(cm, ((PrimaryKeyJoinColumns)anno).value());
                  break;
               case SECONDARY_TABLE:
                  this.parseSecondaryTables(cm, (SecondaryTable)anno);
                  break;
               case SECONDARY_TABLES:
                  this.parseSecondaryTables(cm, ((SecondaryTables)anno).value());
                  break;
               case SQL_RESULT_SET_MAPPING:
                  this.parseSQLResultSetMappings(cm, (SqlResultSetMapping)anno);
                  break;
               case SQL_RESULT_SET_MAPPINGS:
                  this.parseSQLResultSetMappings(cm, ((SqlResultSetMappings)anno).value());
                  break;
               case TABLE:
                  this.parseTable(cm, (Table)anno);
                  break;
               case DATASTORE_ID_COL:
                  this.parseDataStoreIdColumn(cm, (DataStoreIdColumn)anno);
                  break;
               case DISCRIM_STRAT:
                  cm.getDiscriminator().getMappingInfo().setStrategy(((DiscriminatorStrategy)anno).value());
                  break;
               case FK:
                  this.parseForeignKey(cm.getMappingInfo(), (ForeignKey)anno);
                  break;
               case MAPPING_OVERRIDE:
                  this.parseMappingOverrides(cm, (MappingOverride)anno);
                  break;
               case MAPPING_OVERRIDES:
                  this.parseMappingOverrides(cm, ((MappingOverrides)anno).value());
                  break;
               case STRAT:
                  cm.getMappingInfo().setStrategy(((Strategy)anno).value());
                  break;
               case SUBCLASS_FETCH_MODE:
                  cm.setSubclassFetchMode(toEagerFetchModeConstant(((SubclassFetchMode)anno).value()));
                  break;
               case VERSION_COL:
                  this.parseVersionColumns(cm, (VersionColumn)anno);
                  break;
               case VERSION_COLS:
                  this.parseVersionColumns(cm, ((VersionColumns)anno).value());
                  break;
               case VERSION_STRAT:
                  cm.getVersion().getMappingInfo().setStrategy(((VersionStrategy)anno).value());
                  break;
               case X_MAPPING_OVERRIDE:
                  this.parseMappingOverrides(cm, (XMappingOverride)anno);
                  break;
               case X_MAPPING_OVERRIDES:
                  this.parseMappingOverrides(cm, ((XMappingOverrides)anno).value());
                  break;
               case X_TABLE:
               case X_SECONDARY_TABLE:
               case X_SECONDARY_TABLES:
               default:
                  throw new UnsupportedException(_loc.get("unsupported", cm, anno));
            }
         }
      }

   }

   protected boolean handleUnknownClassMappingAnnotation(ClassMapping cls, Annotation anno) {
      return false;
   }

   private void parseAssociationOverrides(ClassMapping cm, AssociationOverride... assocs) {
      AssociationOverride[] arr$ = assocs;
      int len$ = assocs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AssociationOverride assoc = arr$[i$];
         if (StringUtils.isEmpty(assoc.name())) {
            throw new MetaDataException(_loc.get("no-override-name", (Object)cm));
         }

         FieldMapping sup = (FieldMapping)cm.getDefinedSuperclassField(assoc.name());
         if (sup == null) {
            sup = (FieldMapping)cm.addDefinedSuperclassField(assoc.name(), Object.class, Object.class);
         }

         JoinColumn[] scols = assoc.joinColumns();
         if (scols != null && scols.length != 0) {
            List jcols = new ArrayList(scols.length);
            int unique = 0;
            JoinColumn[] arr$ = scols;
            int len$ = scols.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               JoinColumn scol = arr$[i$];
               unique |= scol.unique() ? 1 : 2;
               jcols.add(newColumn(scol));
            }

            this.setColumns(sup, sup.getValueInfo(), jcols, unique);
         }
      }

   }

   private void parseAttributeOverrides(ClassMapping cm, AttributeOverride... attrs) {
      AttributeOverride[] arr$ = attrs;
      int len$ = attrs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AttributeOverride attr = arr$[i$];
         if (StringUtils.isEmpty(attr.name())) {
            throw new MetaDataException(_loc.get("no-override-name", (Object)cm));
         }

         FieldMapping sup = (FieldMapping)cm.getDefinedSuperclassField(attr.name());
         if (sup == null) {
            sup = (FieldMapping)cm.addDefinedSuperclassField(attr.name(), Object.class, Object.class);
         }

         if (attr.column() != null) {
            this.parseColumns(sup, attr.column());
         }
      }

   }

   private void parsePrimaryKeyJoinColumns(ClassMapping cm, PrimaryKeyJoinColumn... joins) {
      List cols = new ArrayList(joins.length);
      PrimaryKeyJoinColumn[] arr$ = joins;
      int len$ = joins.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         PrimaryKeyJoinColumn join = arr$[i$];
         cols.add(newColumn(join));
      }

      cm.getMappingInfo().setColumns(cols);
   }

   private static Column newColumn(PrimaryKeyJoinColumn join) {
      Column col = new Column();
      col.setFlag(128, true);
      if (!StringUtils.isEmpty(join.name())) {
         col.setName(join.name());
      }

      if (!StringUtils.isEmpty(join.columnDefinition())) {
         col.setTypeName(join.columnDefinition());
      }

      if (!StringUtils.isEmpty(join.referencedColumnName())) {
         col.setTarget(join.referencedColumnName());
      }

      return col;
   }

   private void parseSecondaryTables(ClassMapping cm, SecondaryTable... tables) {
      ClassMappingInfo info = cm.getMappingInfo();
      Log log = this.getLog();
      boolean warnUnique = false;
      SecondaryTable[] arr$ = tables;
      int len$ = tables.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         SecondaryTable table = arr$[i$];
         String name = table.name();
         if (StringUtils.isEmpty(name)) {
            throw new MetaDataException(_loc.get("second-name", (Object)cm));
         }

         if (!StringUtils.isEmpty(table.schema())) {
            name = table.schema() + "." + name;
         }

         if (table.pkJoinColumns().length > 0) {
            List joins = new ArrayList(table.pkJoinColumns().length);
            PrimaryKeyJoinColumn[] arr$ = table.pkJoinColumns();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               PrimaryKeyJoinColumn join = arr$[i$];
               joins.add(newColumn(join));
            }

            info.setSecondaryTableJoinColumns(name, joins);
         }

         warnUnique |= table.uniqueConstraints().length > 0;
      }

      if (warnUnique && log.isWarnEnabled()) {
         log.warn(_loc.get("unique-constraints", (Object)cm));
      }

   }

   private void parseTable(ClassMapping cm, Table table) {
      String tableName = toTableName(table.schema(), table.name());
      if (tableName != null) {
         cm.getMappingInfo().setTableName(tableName);
      }

      UniqueConstraint[] arr$ = table.uniqueConstraints();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         UniqueConstraint uniqueConstraint = arr$[i$];
         org.apache.openjpa.jdbc.schema.Unique unique = newUnique(cm, (String)null, uniqueConstraint.columnNames());
         cm.getMappingInfo().addUnique(unique);
      }

   }

   private static String toTableName(String schema, String table) {
      if (StringUtils.isEmpty(table)) {
         return null;
      } else {
         return StringUtils.isEmpty(schema) ? table : schema + "." + table;
      }
   }

   private void parseSQLResultSetMappings(ClassMapping cm, SqlResultSetMapping... annos) {
      MappingRepository repos = (MappingRepository)this.getRepository();
      Log log = this.getLog();
      SqlResultSetMapping[] arr$ = annos;
      int len$ = annos.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         SqlResultSetMapping anno = arr$[i$];
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-sqlrsmapping", (Object)anno.name()));
         }

         QueryResultMapping result = repos.getCachedQueryResultMapping((Class)null, anno.name());
         if (result != null) {
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("dup-sqlrsmapping", anno.name(), cm));
            }
         } else {
            result = repos.addQueryResultMapping((Class)null, anno.name());
            result.setSource(this.getSourceFile(), cm.getDescribedType(), 1);
            EntityResult[] arr$ = anno.entities();
            int len$ = arr$.length;

            int i$;
            for(i$ = 0; i$ < len$; ++i$) {
               EntityResult entity = arr$[i$];
               QueryResultMapping.PCResult entityResult = result.addPCResult(entity.entityClass());
               if (!StringUtils.isEmpty(entity.discriminatorColumn())) {
                  entityResult.addMapping("<discriminator>", entity.discriminatorColumn());
               }

               FieldResult[] arr$ = entity.fields();
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  FieldResult field = arr$[i$];
                  entityResult.addMapping(field.name(), field.column());
               }
            }

            ColumnResult[] arr$ = anno.columns();
            len$ = arr$.length;

            for(i$ = 0; i$ < len$; ++i$) {
               ColumnResult column = arr$[i$];
               result.addColumnResult(column.name());
            }
         }
      }

   }

   private void parseDiscriminatorColumn(ClassMapping cm, DiscriminatorColumn dcol) {
      Column col = new Column();
      if (!StringUtils.isEmpty(dcol.name())) {
         col.setName(dcol.name());
      }

      if (!StringUtils.isEmpty(dcol.columnDefinition())) {
         col.setTypeName(dcol.columnDefinition());
      }

      Discriminator discrim = cm.getDiscriminator();
      switch (dcol.discriminatorType()) {
         case CHAR:
            col.setJavaType(2);
            discrim.setJavaType(2);
            break;
         case INTEGER:
            col.setJavaType(5);
            if (dcol.length() != 31) {
               col.setSize(dcol.length());
            }

            discrim.setJavaType(5);
            break;
         default:
            col.setJavaType(9);
            col.setSize(dcol.length());
            discrim.setJavaType(9);
      }

      cm.getDiscriminator().getMappingInfo().setColumns(Arrays.asList(col));
   }

   private void parseInheritance(ClassMapping cm, Inheritance inherit) {
      ClassMappingInfo info = cm.getMappingInfo();
      switch (inherit.strategy()) {
         case SINGLE_TABLE:
            info.setHierarchyStrategy("flat");
            break;
         case JOINED:
            info.setHierarchyStrategy("vertical");
            break;
         case TABLE_PER_CLASS:
            info.setHierarchyStrategy("full");
            break;
         default:
            throw new InternalException();
      }

   }

   private void parseMappingOverrides(ClassMapping cm, MappingOverride... overs) {
      MappingOverride[] arr$ = overs;
      int len$ = overs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         MappingOverride over = arr$[i$];
         if (StringUtils.isEmpty(over.name())) {
            throw new MetaDataException(_loc.get("no-override-name", (Object)cm));
         }

         FieldMapping sup = (FieldMapping)cm.getDefinedSuperclassField(over.name());
         if (sup == null) {
            sup = (FieldMapping)cm.addDefinedSuperclassField(over.name(), Object.class, Object.class);
         }

         this.populate(sup, over);
      }

   }

   private void populate(FieldMapping fm, MappingOverride over) {
      if (over.containerTable().specified()) {
         this.parseContainerTable(fm, over.containerTable());
      }

      this.parseColumns(fm, over.columns());
      this.parseXJoinColumns(fm, fm.getValueInfo(), true, over.joinColumns());
      this.parseElementJoinColumns(fm, over.elementJoinColumns());
   }

   private void parseDataStoreIdColumn(ClassMapping cm, DataStoreIdColumn id) {
      Column col = new Column();
      if (!StringUtils.isEmpty(id.name())) {
         col.setName(id.name());
      }

      if (!StringUtils.isEmpty(id.columnDefinition())) {
         col.setTypeName(id.columnDefinition());
      }

      if (id.precision() != 0) {
         col.setSize(id.precision());
      }

      col.setFlag(2, !id.insertable());
      col.setFlag(4, !id.updatable());
      cm.getMappingInfo().setColumns(Arrays.asList(col));
   }

   private void parseForeignKey(MappingInfo info, ForeignKey fk) {
      this.parseForeignKey(info, fk.name(), fk.enabled(), fk.deferred(), fk.deleteAction(), fk.updateAction());
   }

   protected void parseForeignKey(MappingInfo info, String name, boolean enabled, boolean deferred, ForeignKeyAction deleteAction, ForeignKeyAction updateAction) {
      if (!enabled) {
         info.setCanForeignKey(false);
      } else {
         org.apache.openjpa.jdbc.schema.ForeignKey fk = new org.apache.openjpa.jdbc.schema.ForeignKey();
         if (!StringUtils.isEmpty(name)) {
            fk.setName(name);
         }

         fk.setDeferred(deferred);
         fk.setDeleteAction(this.toForeignKeyAction(deleteAction));
         fk.setUpdateAction(this.toForeignKeyAction(updateAction));
         info.setForeignKey(fk);
      }
   }

   private int toForeignKeyAction(ForeignKeyAction action) {
      switch (action) {
         case RESTRICT:
            return 2;
         case CASCADE:
            return 3;
         case NULL:
            return 4;
         case DEFAULT:
            return 5;
         default:
            throw new InternalException();
      }
   }

   private void parseIndex(MappingInfo info, Index idx) {
      this.parseIndex(info, idx.name(), idx.enabled(), idx.unique());
   }

   protected void parseIndex(MappingInfo info, String name, boolean enabled, boolean unique) {
      if (!enabled) {
         info.setCanIndex(false);
      } else {
         org.apache.openjpa.jdbc.schema.Index idx = new org.apache.openjpa.jdbc.schema.Index();
         if (!StringUtils.isEmpty(name)) {
            idx.setName(name);
         }

         idx.setUnique(unique);
         info.setIndex(idx);
      }
   }

   private void parseUnique(FieldMapping fm, Unique anno) {
      ValueMappingInfo info = fm.getValueInfo();
      if (!anno.enabled()) {
         info.setCanUnique(false);
      } else {
         org.apache.openjpa.jdbc.schema.Unique unq = new org.apache.openjpa.jdbc.schema.Unique();
         if (!StringUtils.isEmpty(anno.name())) {
            unq.setName(anno.name());
         }

         unq.setDeferred(anno.deferred());
         info.setUnique(unq);
      }
   }

   private void parseVersionColumns(ClassMapping cm, VersionColumn... vcols) {
      if (vcols.length != 0) {
         List cols = new ArrayList(vcols.length);
         VersionColumn[] arr$ = vcols;
         int len$ = vcols.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            VersionColumn vcol = arr$[i$];
            cols.add(newColumn(vcol));
         }

         cm.getVersion().getMappingInfo().setColumns(cols);
      }
   }

   private static Column newColumn(VersionColumn anno) {
      Column col = new Column();
      if (!StringUtils.isEmpty(anno.name())) {
         col.setName(anno.name());
      }

      if (!StringUtils.isEmpty(anno.columnDefinition())) {
         col.setTypeName(anno.columnDefinition());
      }

      if (anno.precision() != 0) {
         col.setSize(anno.precision());
      } else if (anno.length() != 255) {
         col.setSize(anno.length());
      }

      col.setNotNull(!anno.nullable());
      col.setDecimalDigits(anno.scale());
      col.setFlag(2, !anno.insertable());
      col.setFlag(4, !anno.updatable());
      return col;
   }

   private void parseMappingOverrides(ClassMapping cm, XMappingOverride... overs) {
      XMappingOverride[] arr$ = overs;
      int len$ = overs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         XMappingOverride over = arr$[i$];
         if (StringUtils.isEmpty(over.name())) {
            throw new MetaDataException(_loc.get("no-override-name", (Object)cm));
         }

         FieldMapping sup = (FieldMapping)cm.getDefinedSuperclassField(over.name());
         if (sup == null) {
            sup = (FieldMapping)cm.addDefinedSuperclassField(over.name(), Object.class, Object.class);
         }

         this.populate(sup, over);
      }

   }

   private void populate(FieldMapping fm, XMappingOverride over) {
      if (over.containerTable().specified()) {
         this.parseContainerTable(fm, over.containerTable());
      }

      this.parseColumns(fm, over.columns());
      this.parseXJoinColumns(fm, fm.getValueInfo(), true, over.joinColumns());
      this.parseElementColumns(fm, over.elementColumns());
      this.parseElementJoinColumns(fm, over.elementJoinColumns());
      this.parseKeyColumns(fm, over.keyColumns());
      this.parseKeyJoinColumns(fm, over.keyJoinColumns());
   }

   private void parseElementColumns(FieldMapping fm, ElementColumn... pcols) {
      if (pcols.length != 0) {
         List cols = new ArrayList(pcols.length);
         int unique = 0;

         for(int i = 0; i < pcols.length; ++i) {
            cols.add(newColumn(pcols[i]));
            unique |= pcols[i].unique() ? 1 : 2;
         }

         this.setColumns(fm, fm.getElementMapping().getValueInfo(), cols, unique);
      }
   }

   private static Column newColumn(ElementColumn anno) {
      Column col = new Column();
      if (!StringUtils.isEmpty(anno.name())) {
         col.setName(anno.name());
      }

      if (!StringUtils.isEmpty(anno.columnDefinition())) {
         col.setTypeName(anno.columnDefinition());
      }

      if (anno.precision() != 0) {
         col.setSize(anno.precision());
      } else if (anno.length() != 255) {
         col.setSize(anno.length());
      }

      col.setNotNull(!anno.nullable());
      col.setDecimalDigits(anno.scale());
      col.setFlag(2, !anno.insertable());
      col.setFlag(4, !anno.updatable());
      return col;
   }

   private void parseKeyJoinColumns(FieldMapping fm, KeyJoinColumn... joins) {
      if (joins.length != 0) {
         List cols = new ArrayList(joins.length);
         int unique = 0;

         for(int i = 0; i < joins.length; ++i) {
            cols.add(newColumn(joins[i]));
            unique |= joins[i].unique() ? 1 : 2;
         }

         this.setColumns(fm, fm.getKeyMapping().getValueInfo(), cols, unique);
      }
   }

   private static Column newColumn(KeyJoinColumn join) {
      Column col = new Column();
      if (!StringUtils.isEmpty(join.name())) {
         col.setName(join.name());
      }

      if (!StringUtils.isEmpty(join.columnDefinition())) {
         col.setTypeName(join.columnDefinition());
      }

      if (!StringUtils.isEmpty(join.referencedColumnName())) {
         col.setTarget(join.referencedColumnName());
      }

      if (!StringUtils.isEmpty(join.referencedAttributeName())) {
         col.setTargetField(join.referencedAttributeName());
      }

      col.setNotNull(!join.nullable());
      col.setFlag(2, !join.insertable());
      col.setFlag(4, !join.updatable());
      return col;
   }

   private static int toEagerFetchModeConstant(FetchMode mode) {
      switch (mode) {
         case NONE:
            return 0;
         case JOIN:
            return 1;
         case PARALLEL:
            return 2;
         default:
            throw new InternalException();
      }
   }

   protected void parseLobMapping(FieldMetaData fmd) {
      Column col = new Column();
      if (fmd.getDeclaredTypeCode() != 9 && fmd.getDeclaredType() != char[].class && fmd.getDeclaredType() != Character[].class) {
         col.setType(2004);
      } else {
         col.setType(2005);
      }

      ((FieldMapping)fmd).getValueInfo().setColumns(Arrays.asList(col));
   }

   protected void parseMemberMappingAnnotations(FieldMetaData fmd) {
      FieldMapping fm = (FieldMapping)fmd;
      AnnotatedElement el = (AnnotatedElement)this.getRepository().getMetaDataFactory().getDefaults().getBackingMember(fmd);
      Annotation[] arr$ = el.getDeclaredAnnotations();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Annotation anno = arr$[i$];
         MappingTag tag = (MappingTag)_tags.get(anno.annotationType());
         if (tag == null) {
            this.handleUnknownMemberMappingAnnotation(fm, anno);
         } else {
            switch (tag) {
               case TABLE_GEN:
                  this.parseTableGenerator(el, (TableGenerator)anno);
                  break;
               case ASSOC_OVERRIDE:
                  this.parseAssociationOverrides(fm, (AssociationOverride)anno);
                  break;
               case ASSOC_OVERRIDES:
                  this.parseAssociationOverrides(fm, ((AssociationOverrides)anno).value());
                  break;
               case ATTR_OVERRIDE:
                  this.parseAttributeOverrides(fm, (AttributeOverride)anno);
                  break;
               case ATTR_OVERRIDES:
                  this.parseAttributeOverrides(fm, ((AttributeOverrides)anno).value());
                  break;
               case DISCRIM_COL:
               case DISCRIM_VAL:
               case INHERITANCE:
               case SECONDARY_TABLE:
               case SECONDARY_TABLES:
               case SQL_RESULT_SET_MAPPING:
               case SQL_RESULT_SET_MAPPINGS:
               case TABLE:
               case DATASTORE_ID_COL:
               case DISCRIM_STRAT:
               case MAPPING_OVERRIDE:
               case MAPPING_OVERRIDES:
               case SUBCLASS_FETCH_MODE:
               case VERSION_COL:
               case VERSION_COLS:
               case VERSION_STRAT:
               case X_MAPPING_OVERRIDE:
               case X_MAPPING_OVERRIDES:
               case X_TABLE:
               case X_SECONDARY_TABLE:
               case X_SECONDARY_TABLES:
               default:
                  throw new UnsupportedException(_loc.get("unsupported", fm, anno.toString()));
               case PK_JOIN_COL:
                  this.parsePrimaryKeyJoinColumns(fm, (PrimaryKeyJoinColumn)anno);
                  break;
               case PK_JOIN_COLS:
                  this.parsePrimaryKeyJoinColumns(fm, ((PrimaryKeyJoinColumns)anno).value());
                  break;
               case FK:
                  this.parseForeignKey(fm.getValueInfo(), (ForeignKey)anno);
                  break;
               case STRAT:
                  fm.getValueInfo().setStrategy(((Strategy)anno).value());
                  break;
               case COL:
                  this.parseColumns(fm, (javax.persistence.Column)anno);
                  break;
               case COLS:
                  this.parseColumns(fm, ((Columns)anno).value());
                  break;
               case ENUMERATED:
                  this.parseEnumerated(fm, (Enumerated)anno);
                  break;
               case JOIN_COL:
                  this.parseJoinColumns(fm, fm.getValueInfo(), true, (JoinColumn)anno);
                  break;
               case JOIN_COLS:
                  this.parseJoinColumns(fm, fm.getValueInfo(), true, ((JoinColumns)anno).value());
                  break;
               case JOIN_TABLE:
                  this.parseJoinTable(fm, (JoinTable)anno);
                  break;
               case KEY_CLASS_CRIT:
                  fm.getKeyMapping().getValueInfo().setUseClassCriteria(((KeyClassCriteria)anno).value());
                  break;
               case KEY_COL:
                  this.parseKeyColumns(fm, (KeyColumn)anno);
                  break;
               case KEY_COLS:
                  this.parseKeyColumns(fm, ((KeyColumns)anno).value());
                  break;
               case KEY_EMBEDDED_MAPPING:
                  KeyEmbeddedMapping kembed = (KeyEmbeddedMapping)anno;
                  this.parseEmbeddedMapping(fm.getKeyMapping(), kembed.nullIndicatorColumnName(), kembed.nullIndicatorAttributeName(), kembed.overrides());
                  break;
               case KEY_FK:
                  KeyForeignKey kfk = (KeyForeignKey)anno;
                  this.parseForeignKey(fm.getKeyMapping().getValueInfo(), kfk.name(), kfk.enabled(), kfk.deferred(), kfk.deleteAction(), kfk.updateAction());
                  break;
               case KEY_INDEX:
                  KeyIndex kidx = (KeyIndex)anno;
                  this.parseIndex(fm.getKeyMapping().getValueInfo(), kidx.name(), kidx.enabled(), kidx.unique());
                  break;
               case KEY_JOIN_COL:
                  this.parseKeyJoinColumns(fm, (KeyJoinColumn)anno);
                  break;
               case KEY_JOIN_COLS:
                  this.parseKeyJoinColumns(fm, ((KeyJoinColumns)anno).value());
                  break;
               case KEY_NONPOLY:
                  fm.getKeyMapping().setPolymorphic(toPolymorphicConstant(((KeyNonpolymorphic)anno).value()));
                  break;
               case KEY_STRAT:
                  fm.getKeyMapping().getValueInfo().setStrategy(((KeyStrategy)anno).value());
                  break;
               case TEMPORAL:
                  this.parseTemporal(fm, (Temporal)anno);
                  break;
               case CLASS_CRIT:
                  fm.getValueInfo().setUseClassCriteria(((ClassCriteria)anno).value());
                  break;
               case CONTAINER_TABLE:
                  this.parseContainerTable(fm, (ContainerTable)anno);
                  break;
               case EAGER_FETCH_MODE:
                  fm.setEagerFetchMode(toEagerFetchModeConstant(((EagerFetchMode)anno).value()));
                  break;
               case ELEM_CLASS_CRIT:
                  fm.getElementMapping().getValueInfo().setUseClassCriteria(((ElementClassCriteria)anno).value());
                  break;
               case ELEM_COL:
                  this.parseElementColumns(fm, (ElementColumn)anno);
                  break;
               case ELEM_COLS:
                  this.parseElementColumns(fm, ((ElementColumns)anno).value());
                  break;
               case ELEM_EMBEDDED_MAPPING:
                  ElementEmbeddedMapping ee = (ElementEmbeddedMapping)anno;
                  this.parseEmbeddedMapping(fm.getElementMapping(), ee.nullIndicatorAttributeName(), ee.nullIndicatorColumnName(), ee.overrides());
                  break;
               case ELEM_FK:
                  ElementForeignKey efk = (ElementForeignKey)anno;
                  this.parseForeignKey(fm.getElementMapping().getValueInfo(), efk.name(), efk.enabled(), efk.deferred(), efk.deleteAction(), efk.updateAction());
                  break;
               case ELEM_INDEX:
                  ElementIndex eidx = (ElementIndex)anno;
                  this.parseIndex(fm.getElementMapping().getValueInfo(), eidx.name(), eidx.enabled(), eidx.unique());
                  break;
               case ELEM_JOIN_COL:
                  this.parseElementJoinColumns(fm, (ElementJoinColumn)anno);
                  break;
               case ELEM_JOIN_COLS:
                  this.parseElementJoinColumns(fm, ((ElementJoinColumns)anno).value());
                  break;
               case ELEM_NONPOLY:
                  fm.getElementMapping().setPolymorphic(toPolymorphicConstant(((ElementNonpolymorphic)anno).value()));
                  break;
               case ELEM_STRAT:
                  fm.getElementMapping().getValueInfo().setStrategy(((ElementStrategy)anno).value());
                  break;
               case EMBEDDED_MAPPING:
                  this.parseEmbeddedMapping(fm, (EmbeddedMapping)anno);
                  break;
               case INDEX:
                  this.parseIndex(fm.getValueInfo(), (Index)anno);
                  break;
               case NONPOLY:
                  fm.setPolymorphic(toPolymorphicConstant(((Nonpolymorphic)anno).value()));
                  break;
               case ORDER_COL:
                  this.parseOrderColumn(fm, (OrderColumn)anno);
                  break;
               case UNIQUE:
                  this.parseUnique(fm, (Unique)anno);
                  break;
               case X_EMBEDDED_MAPPING:
                  XEmbeddedMapping embed = (XEmbeddedMapping)anno;
                  this.parseEmbeddedMapping(fm, embed.nullIndicatorColumnName(), embed.nullIndicatorAttributeName(), embed.overrides());
                  break;
               case X_JOIN_COL:
                  this.parseXJoinColumns(fm, fm.getValueInfo(), true, (XJoinColumn)anno);
                  break;
               case X_JOIN_COLS:
                  this.parseXJoinColumns(fm, fm.getValueInfo(), true, ((XJoinColumns)anno).value());
            }
         }
      }

   }

   protected boolean handleUnknownMemberMappingAnnotation(FieldMapping fm, Annotation anno) {
      return false;
   }

   protected static int toPolymorphicConstant(NonpolymorphicType val) {
      switch (val) {
         case EXACT:
            return 1;
         case JOINABLE:
            return 2;
         case FALSE:
            return 0;
         default:
            throw new InternalException();
      }
   }

   private void parseAssociationOverrides(FieldMapping fm, AssociationOverride... assocs) {
      ClassMapping embed = fm.getEmbeddedMapping();
      if (embed == null) {
         throw new MetaDataException(_loc.get("not-embedded", (Object)fm));
      } else {
         AssociationOverride[] arr$ = assocs;
         int len$ = assocs.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AssociationOverride assoc = arr$[i$];
            FieldMapping efm = embed.getFieldMapping(assoc.name());
            if (efm == null) {
               throw new MetaDataException(_loc.get("embed-override-name", fm, assoc.name()));
            }

            JoinColumn[] ecols = assoc.joinColumns();
            if (ecols != null && ecols.length != 0) {
               int unique = 0;
               List jcols = new ArrayList(ecols.length);
               JoinColumn[] arr$ = ecols;
               int len$ = ecols.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  JoinColumn ecol = arr$[i$];
                  unique |= ecol.unique() ? 1 : 2;
                  jcols.add(newColumn(ecol));
               }

               this.setColumns(efm, efm.getValueInfo(), jcols, unique);
            }
         }

      }
   }

   private void parseAttributeOverrides(FieldMapping fm, AttributeOverride... attrs) {
      ClassMapping embed = fm.getEmbeddedMapping();
      if (embed == null) {
         throw new MetaDataException(_loc.get("not-embedded", (Object)fm));
      } else {
         AttributeOverride[] arr$ = attrs;
         int len$ = attrs.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AttributeOverride attr = arr$[i$];
            FieldMapping efm = embed.getFieldMapping(attr.name());
            if (efm == null) {
               throw new MetaDataException(_loc.get("embed-override-name", fm, attr.name()));
            }

            if (attr.column() != null) {
               this.parseColumns(efm, attr.column());
            }
         }

      }
   }

   private void parseEnumerated(FieldMapping fm, Enumerated anno) {
      String strat = EnumValueHandler.class.getName() + "(StoreOrdinal=" + (anno.value() == EnumType.ORDINAL) + ")";
      fm.getValueInfo().setStrategy(strat);
   }

   private void parseTemporal(FieldMapping fm, Temporal anno) {
      List cols = fm.getValueInfo().getColumns();
      if (!cols.isEmpty() && cols.size() != 1) {
         throw new MetaDataException(_loc.get("num-cols-mismatch", fm, String.valueOf(cols.size()), "1"));
      } else {
         if (cols.isEmpty()) {
            cols = Arrays.asList(new Column());
            fm.getValueInfo().setColumns(cols);
         }

         Column col = (Column)cols.get(0);
         switch (anno.value()) {
            case DATE:
               col.setType(91);
               break;
            case TIME:
               col.setType(92);
               break;
            case TIMESTAMP:
               col.setType(93);
         }

      }
   }

   protected void parseColumns(FieldMapping fm, javax.persistence.Column... pcols) {
      if (pcols.length != 0) {
         List cols = fm.getValueInfo().getColumns();
         if (!((List)cols).isEmpty() && ((List)cols).size() != pcols.length) {
            throw new MetaDataException(_loc.get("num-cols-mismatch", fm, String.valueOf(((List)cols).size()), String.valueOf(pcols.length)));
         } else {
            Class xmlTypeClass = null;

            try {
               xmlTypeClass = Class.forName("javax.xml.bind.annotation.XmlType");
            } catch (Exception var9) {
            }

            int unique = 0;
            String secondary = null;

            for(int i = 0; i < pcols.length; ++i) {
               if (((List)cols).size() > i) {
                  setupColumn((Column)((List)cols).get(i), pcols[i]);
               } else {
                  if (((List)cols).isEmpty()) {
                     cols = new ArrayList(pcols.length);
                  }

                  ((List)cols).add(newColumn(pcols[i]));
               }

               if (xmlTypeClass != null && StringUtils.isEmpty(pcols[i].columnDefinition()) && (Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(fm.getDeclaredType(), xmlTypeClass))) {
                  DBDictionary dict = ((MappingRepository)this.getRepository()).getDBDictionary();
                  if (dict.supportsXMLColumn) {
                     ((Column)((List)cols).get(i)).setTypeName(dict.xmlTypeName);
                  }
               }

               unique |= pcols[i].unique() ? 1 : 2;
               secondary = this.trackSecondaryTable(fm, secondary, pcols[i].table(), i);
            }

            this.setColumns(fm, fm.getValueInfo(), (List)cols, unique);
            if (secondary != null) {
               fm.getMappingInfo().setTableName(secondary);
            }

         }
      }
   }

   private static Column newColumn(javax.persistence.Column anno) {
      Column col = new Column();
      setupColumn(col, anno);
      return col;
   }

   private static void setupColumn(Column col, javax.persistence.Column anno) {
      if (!StringUtils.isEmpty(anno.name())) {
         col.setName(anno.name());
      }

      if (!StringUtils.isEmpty(anno.columnDefinition())) {
         col.setTypeName(anno.columnDefinition());
      }

      if (anno.precision() != 0) {
         col.setSize(anno.precision());
      } else if (anno.length() != 255) {
         col.setSize(anno.length());
      }

      col.setNotNull(!anno.nullable());
      col.setDecimalDigits(anno.scale());
      col.setFlag(2, !anno.insertable());
      col.setFlag(4, !anno.updatable());
   }

   protected void setColumns(FieldMapping fm, MappingInfo info, List cols, int unique) {
      info.setColumns(cols);
      if (unique == 1) {
         info.setUnique(new org.apache.openjpa.jdbc.schema.Unique());
      }

      Log log = this.getLog();
      if (log.isWarnEnabled() && unique == 3) {
         log.warn(_loc.get("inconsist-col-attrs", (Object)fm));
      }

   }

   private String trackSecondaryTable(FieldMapping fm, String secondary, String colSecondary, int col) {
      if (StringUtils.isEmpty(colSecondary)) {
         colSecondary = null;
      }

      if (col == 0) {
         return colSecondary;
      } else if (!StringUtils.equalsIgnoreCase(secondary, colSecondary)) {
         throw new MetaDataException(_loc.get("second-inconsist", (Object)fm));
      } else {
         return secondary;
      }
   }

   private void parseJoinTable(FieldMapping fm, JoinTable join) {
      fm.getMappingInfo().setTableName(toTableName(join.schema(), join.name()));
      this.parseJoinColumns(fm, fm.getMappingInfo(), false, join.joinColumns());
      this.parseJoinColumns(fm, fm.getElementMapping().getValueInfo(), false, join.inverseJoinColumns());
   }

   private void parseJoinColumns(FieldMapping fm, MappingInfo info, boolean secondaryAllowed, JoinColumn... joins) {
      if (joins.length != 0) {
         List cols = new ArrayList(joins.length);
         int unique = 0;
         String secondary = null;

         for(int i = 0; i < joins.length; ++i) {
            cols.add(newColumn(joins[i]));
            unique |= joins[i].unique() ? 1 : 2;
            secondary = this.trackSecondaryTable(fm, secondary, joins[i].table(), i);
            if (!secondaryAllowed && secondary != null) {
               throw new MetaDataException(_loc.get("bad-second", (Object)fm));
            }
         }

         this.setColumns(fm, info, cols, unique);
         if (secondary != null) {
            fm.getMappingInfo().setTableName(secondary);
         }

      }
   }

   private static Column newColumn(JoinColumn join) {
      Column col = new Column();
      if (!StringUtils.isEmpty(join.name())) {
         col.setName(join.name());
      }

      if (!StringUtils.isEmpty(join.columnDefinition())) {
         col.setTypeName(join.columnDefinition());
      }

      if (!StringUtils.isEmpty(join.referencedColumnName())) {
         col.setTarget(join.referencedColumnName());
      }

      col.setNotNull(!join.nullable());
      col.setFlag(2, !join.insertable());
      col.setFlag(4, !join.updatable());
      return col;
   }

   private void parseKeyColumns(FieldMapping fm, KeyColumn... pcols) {
      if (pcols.length != 0) {
         List cols = new ArrayList(pcols.length);
         int unique = 0;

         for(int i = 0; i < pcols.length; ++i) {
            cols.add(newColumn(pcols[i]));
            unique |= pcols[i].unique() ? 1 : 2;
         }

         this.setColumns(fm, fm.getKeyMapping().getValueInfo(), cols, unique);
      }
   }

   private static Column newColumn(KeyColumn anno) {
      Column col = new Column();
      if (!StringUtils.isEmpty(anno.name())) {
         col.setName(anno.name());
      }

      if (!StringUtils.isEmpty(anno.columnDefinition())) {
         col.setTypeName(anno.columnDefinition());
      }

      if (anno.precision() != 0) {
         col.setSize(anno.precision());
      } else if (anno.length() != 255) {
         col.setSize(anno.length());
      }

      col.setNotNull(!anno.nullable());
      col.setDecimalDigits(anno.scale());
      col.setFlag(2, !anno.insertable());
      col.setFlag(4, !anno.updatable());
      return col;
   }

   private void parsePrimaryKeyJoinColumns(FieldMapping fm, PrimaryKeyJoinColumn... joins) {
      List cols = new ArrayList(joins.length);
      PrimaryKeyJoinColumn[] arr$ = joins;
      int len$ = joins.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         PrimaryKeyJoinColumn join = arr$[i$];
         cols.add(newColumn(join));
      }

      this.setColumns(fm, fm.getValueInfo(), cols, 0);
   }

   protected void parseXJoinColumns(FieldMapping fm, MappingInfo info, boolean secondaryAllowed, XJoinColumn... joins) {
      if (joins.length != 0) {
         List cols = new ArrayList(joins.length);
         int unique = 0;
         String secondary = null;

         for(int i = 0; i < joins.length; ++i) {
            cols.add(newColumn(joins[i]));
            unique |= joins[i].unique() ? 1 : 2;
            secondary = this.trackSecondaryTable(fm, secondary, joins[i].table(), i);
            if (!secondaryAllowed && secondary != null) {
               throw new MetaDataException(_loc.get("bad-second", (Object)fm));
            }
         }

         this.setColumns(fm, info, cols, unique);
         if (secondary != null) {
            fm.getMappingInfo().setTableName(secondary);
         }

      }
   }

   private static Column newColumn(XJoinColumn join) {
      Column col = new Column();
      if (!StringUtils.isEmpty(join.name())) {
         col.setName(join.name());
      }

      if (!StringUtils.isEmpty(join.columnDefinition())) {
         col.setTypeName(join.columnDefinition());
      }

      if (!StringUtils.isEmpty(join.referencedColumnName())) {
         col.setTarget(join.referencedColumnName());
      }

      if (!StringUtils.isEmpty(join.referencedAttributeName())) {
         col.setTargetField(join.referencedAttributeName());
      }

      col.setNotNull(!join.nullable());
      col.setFlag(2, !join.insertable());
      col.setFlag(4, !join.updatable());
      return col;
   }

   private void parseEmbeddedMapping(FieldMapping fm, EmbeddedMapping anno) {
      ClassMapping embed = fm.getEmbeddedMapping();
      if (embed == null) {
         throw new MetaDataException(_loc.get("not-embedded", (Object)fm));
      } else {
         MappingOverride[] arr$ = anno.overrides();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            MappingOverride over = arr$[i$];
            FieldMapping efm = embed.getFieldMapping(over.name());
            if (efm == null) {
               throw new MetaDataException(_loc.get("embed-override-name", fm, over.name()));
            }

            this.populate(efm, over);
         }

         String nullInd = null;
         if (!StringUtils.isEmpty(anno.nullIndicatorAttributeName())) {
            nullInd = anno.nullIndicatorAttributeName();
         } else if (!StringUtils.isEmpty(anno.nullIndicatorColumnName())) {
            nullInd = anno.nullIndicatorColumnName();
         }

         if (nullInd != null) {
            ValueMappingInfo info = fm.getValueInfo();
            this.populateNullIndicator(nullInd, info);
         }
      }
   }

   private void parseEmbeddedMapping(ValueMapping vm, String nullIndicatorAttribute, String nullIndicatorColumn, XMappingOverride[] overrides) {
      ClassMapping embed = vm.getEmbeddedMapping();
      if (embed == null) {
         throw new MetaDataException(_loc.get("not-embedded", (Object)vm));
      } else {
         XMappingOverride[] arr$ = overrides;
         int len$ = overrides.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            XMappingOverride over = arr$[i$];
            FieldMapping efm = embed.getFieldMapping(over.name());
            if (efm == null) {
               throw new MetaDataException(_loc.get("embed-override-name", vm, over.name()));
            }

            this.populate(efm, over);
         }

         String nullInd = null;
         if (!StringUtils.isEmpty(nullIndicatorAttribute)) {
            nullInd = nullIndicatorAttribute;
         } else if (!StringUtils.isEmpty(nullIndicatorColumn)) {
            nullInd = nullIndicatorColumn;
         }

         if (nullInd != null) {
            ValueMappingInfo info = vm.getValueInfo();
            this.populateNullIndicator(nullInd, info);
         }
      }
   }

   private void populateNullIndicator(String nullInd, ValueMappingInfo info) {
      if ("false".equals(nullInd)) {
         info.setCanIndicateNull(false);
      } else {
         Column col = new Column();
         if (!"true".equals(nullInd)) {
            col.setName(nullInd);
         }

         info.setColumns(Arrays.asList(col));
      }

   }

   protected void parseContainerTable(FieldMapping fm, ContainerTable ctbl) {
      fm.getMappingInfo().setTableName(toTableName(ctbl.schema(), ctbl.name()));
      this.parseXJoinColumns(fm, fm.getMappingInfo(), false, ctbl.joinColumns());
      if (ctbl.joinForeignKey().specified()) {
         this.parseForeignKey(fm.getMappingInfo(), ctbl.joinForeignKey());
      }

      if (ctbl.joinIndex().specified()) {
         this.parseIndex(fm.getMappingInfo(), ctbl.joinIndex());
      }

   }

   private void parseOrderColumn(FieldMapping fm, OrderColumn order) {
      if (!order.enabled()) {
         fm.getMappingInfo().setCanOrderColumn(false);
      } else {
         Column col = new Column();
         if (!StringUtils.isEmpty(order.name())) {
            col.setName(order.name());
         }

         if (!StringUtils.isEmpty(order.columnDefinition())) {
            col.setTypeName(order.columnDefinition());
         }

         if (order.precision() != 0) {
            col.setSize(order.precision());
         }

         col.setFlag(2, !order.insertable());
         col.setFlag(4, !order.updatable());
         fm.getMappingInfo().setOrderColumn(col);
      }
   }

   protected void parseElementJoinColumns(FieldMapping fm, ElementJoinColumn... joins) {
      if (joins.length != 0) {
         List cols = new ArrayList(joins.length);
         int unique = 0;

         for(int i = 0; i < joins.length; ++i) {
            cols.add(newColumn(joins[i]));
            unique |= joins[i].unique() ? 1 : 2;
         }

         this.setColumns(fm, fm.getElementMapping().getValueInfo(), cols, unique);
      }
   }

   private static Column newColumn(ElementJoinColumn join) {
      Column col = new Column();
      if (!StringUtils.isEmpty(join.name())) {
         col.setName(join.name());
      }

      if (!StringUtils.isEmpty(join.columnDefinition())) {
         col.setTypeName(join.columnDefinition());
      }

      if (!StringUtils.isEmpty(join.referencedColumnName())) {
         col.setTarget(join.referencedColumnName());
      }

      if (!StringUtils.isEmpty(join.referencedAttributeName())) {
         col.setTargetField(join.referencedAttributeName());
      }

      col.setNotNull(!join.nullable());
      col.setFlag(2, !join.insertable());
      col.setFlag(4, !join.updatable());
      return col;
   }

   private static org.apache.openjpa.jdbc.schema.Unique newUnique(ClassMapping cm, String name, String[] columnNames) {
      if (columnNames != null && columnNames.length != 0) {
         org.apache.openjpa.jdbc.schema.Unique uniqueConstraint = new org.apache.openjpa.jdbc.schema.Unique();
         uniqueConstraint.setName(name);

         for(int i = 0; i < columnNames.length; ++i) {
            if (StringUtils.isEmpty(columnNames[i])) {
               throw new UserException(_loc.get("empty-unique-column", Arrays.toString(columnNames), cm));
            }

            Column column = new Column();
            column.setName(columnNames[i]);
            uniqueConstraint.addColumn(column);
         }

         return uniqueConstraint;
      } else {
         return null;
      }
   }

   static {
      _tags.put(AssociationOverride.class, MappingTag.ASSOC_OVERRIDE);
      _tags.put(AssociationOverrides.class, MappingTag.ASSOC_OVERRIDES);
      _tags.put(AttributeOverride.class, MappingTag.ATTR_OVERRIDE);
      _tags.put(AttributeOverrides.class, MappingTag.ATTR_OVERRIDES);
      _tags.put(javax.persistence.Column.class, MappingTag.COL);
      _tags.put(ColumnResult.class, MappingTag.COLUMN_RESULT);
      _tags.put(DiscriminatorColumn.class, MappingTag.DISCRIM_COL);
      _tags.put(DiscriminatorValue.class, MappingTag.DISCRIM_VAL);
      _tags.put(ElementColumn.class, MappingTag.ELEM_COL);
      _tags.put(ElementColumns.class, MappingTag.ELEM_COLS);
      _tags.put(ElementEmbeddedMapping.class, MappingTag.ELEM_EMBEDDED_MAPPING);
      _tags.put(ElementStrategy.class, MappingTag.ELEM_STRAT);
      _tags.put(EntityResult.class, MappingTag.ENTITY_RESULT);
      _tags.put(Enumerated.class, MappingTag.ENUMERATED);
      _tags.put(FieldResult.class, MappingTag.FIELD_RESULT);
      _tags.put(Inheritance.class, MappingTag.INHERITANCE);
      _tags.put(JoinColumn.class, MappingTag.JOIN_COL);
      _tags.put(JoinColumns.class, MappingTag.JOIN_COLS);
      _tags.put(JoinTable.class, MappingTag.JOIN_TABLE);
      _tags.put(KeyColumn.class, MappingTag.KEY_COL);
      _tags.put(KeyColumns.class, MappingTag.KEY_COLS);
      _tags.put(KeyClassCriteria.class, MappingTag.KEY_CLASS_CRIT);
      _tags.put(KeyEmbeddedMapping.class, MappingTag.KEY_EMBEDDED_MAPPING);
      _tags.put(KeyForeignKey.class, MappingTag.KEY_FK);
      _tags.put(KeyIndex.class, MappingTag.KEY_INDEX);
      _tags.put(KeyJoinColumn.class, MappingTag.KEY_JOIN_COL);
      _tags.put(KeyJoinColumns.class, MappingTag.KEY_JOIN_COLS);
      _tags.put(KeyNonpolymorphic.class, MappingTag.KEY_NONPOLY);
      _tags.put(KeyStrategy.class, MappingTag.KEY_STRAT);
      _tags.put(PrimaryKeyJoinColumn.class, MappingTag.PK_JOIN_COL);
      _tags.put(PrimaryKeyJoinColumns.class, MappingTag.PK_JOIN_COLS);
      _tags.put(SecondaryTable.class, MappingTag.SECONDARY_TABLE);
      _tags.put(SecondaryTables.class, MappingTag.SECONDARY_TABLES);
      _tags.put(SqlResultSetMapping.class, MappingTag.SQL_RESULT_SET_MAPPING);
      _tags.put(SqlResultSetMappings.class, MappingTag.SQL_RESULT_SET_MAPPINGS);
      _tags.put(Table.class, MappingTag.TABLE);
      _tags.put(Temporal.class, MappingTag.TEMPORAL);
      _tags.put(TableGenerator.class, MappingTag.TABLE_GEN);
      _tags.put(ClassCriteria.class, MappingTag.CLASS_CRIT);
      _tags.put(Columns.class, MappingTag.COLS);
      _tags.put(ContainerTable.class, MappingTag.CONTAINER_TABLE);
      _tags.put(DataStoreIdColumn.class, MappingTag.DATASTORE_ID_COL);
      _tags.put(DiscriminatorStrategy.class, MappingTag.DISCRIM_STRAT);
      _tags.put(EagerFetchMode.class, MappingTag.EAGER_FETCH_MODE);
      _tags.put(ElementClassCriteria.class, MappingTag.ELEM_CLASS_CRIT);
      _tags.put(ElementForeignKey.class, MappingTag.ELEM_FK);
      _tags.put(ElementIndex.class, MappingTag.ELEM_INDEX);
      _tags.put(ElementJoinColumn.class, MappingTag.ELEM_JOIN_COL);
      _tags.put(ElementJoinColumns.class, MappingTag.ELEM_JOIN_COLS);
      _tags.put(ElementNonpolymorphic.class, MappingTag.ELEM_NONPOLY);
      _tags.put(EmbeddedMapping.class, MappingTag.EMBEDDED_MAPPING);
      _tags.put(ForeignKey.class, MappingTag.FK);
      _tags.put(Index.class, MappingTag.INDEX);
      _tags.put(MappingOverride.class, MappingTag.MAPPING_OVERRIDE);
      _tags.put(MappingOverrides.class, MappingTag.MAPPING_OVERRIDES);
      _tags.put(Nonpolymorphic.class, MappingTag.NONPOLY);
      _tags.put(OrderColumn.class, MappingTag.ORDER_COL);
      _tags.put(Strategy.class, MappingTag.STRAT);
      _tags.put(SubclassFetchMode.class, MappingTag.SUBCLASS_FETCH_MODE);
      _tags.put(org.apache.openjpa.jdbc.schema.Unique.class, MappingTag.UNIQUE);
      _tags.put(VersionColumn.class, MappingTag.VERSION_COL);
      _tags.put(VersionColumns.class, MappingTag.VERSION_COLS);
      _tags.put(VersionStrategy.class, MappingTag.VERSION_STRAT);
      _tags.put(XEmbeddedMapping.class, MappingTag.X_EMBEDDED_MAPPING);
      _tags.put(XJoinColumn.class, MappingTag.X_JOIN_COL);
      _tags.put(XJoinColumns.class, MappingTag.X_JOIN_COLS);
      _tags.put(XMappingOverride.class, MappingTag.X_MAPPING_OVERRIDE);
      _tags.put(XMappingOverrides.class, MappingTag.X_MAPPING_OVERRIDES);
      _tags.put(XSecondaryTable.class, MappingTag.X_SECONDARY_TABLE);
      _tags.put(XSecondaryTables.class, MappingTag.X_SECONDARY_TABLES);
      _tags.put(XTable.class, MappingTag.X_TABLE);
   }
}
