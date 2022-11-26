package kodo.persistence.jdbc;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kodo.jdbc.meta.KodoClassMapping;
import kodo.jdbc.meta.KodoFieldMapping;
import kodo.jdbc.meta.KodoMappingRepository;
import kodo.jdbc.meta.LockGroupVersionMappingInfo;
import kodo.persistence.LockGroup;
import kodo.persistence.LockGroups;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.VersionMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.persistence.jdbc.AnnotationPersistenceMappingParser;
import org.apache.openjpa.util.MetaDataException;

public class KodoAnnotationPersistenceMappingParser extends AnnotationPersistenceMappingParser {
   private static final Localizer _loc = Localizer.forPackage(KodoAnnotationPersistenceMappingParser.class);
   private static final Map _tags = new HashMap();

   public KodoAnnotationPersistenceMappingParser(JDBCConfiguration conf) {
      super(conf);
   }

   protected boolean handleUnknownClassAnnotation(ClassMetaData meta, Annotation anno) {
      if (this.isMetaDataMode() && anno.annotationType() == LockGroups.class) {
         LockGroups lgs = (LockGroups)anno;
         String[] names = lgs.value();
         KodoClassMapping cls = (KodoClassMapping)meta;

         for(int i = 0; i < names.length; ++i) {
            if (!"none".equals(names[i])) {
               cls.addDeclaredLockGroup(((KodoMappingRepository)cls.getMappingRepository()).getLockGroup(names[i]));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean handleUnknownMemberAnnotation(FieldMetaData fmd, Annotation anno) {
      if (this.isMetaDataMode() && anno.annotationType() == LockGroup.class) {
         LockGroup lg = (LockGroup)anno;
         String group = lg.value();
         KodoFieldMapping fm = (KodoFieldMapping)fmd;
         if ("none".equals(group)) {
            fm.setLockGroup((kodo.jdbc.meta.LockGroup)null);
         } else {
            fm.setLockGroup(((KodoMappingRepository)fm.getMappingRepository()).getLockGroup(group));
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean handleUnknownClassMappingAnnotation(ClassMapping cm, Annotation anno) {
      KodoMappingTag tag = (KodoMappingTag)_tags.get(anno.annotationType());
      if (tag == null) {
         return false;
      } else {
         switch (tag) {
            case LOCK_GROUP_VERSION_COL:
               this.parseVersionColumns(cm, (LockGroupVersionColumn)anno);
               return true;
            case LOCK_GROUP_VERSION_COLS:
               this.parseVersionColumns(cm, ((LockGroupVersionColumns)anno).value());
               return true;
            case X_MAPPING_OVERRIDE:
               this.parseMappingOverrides(cm, (XMappingOverride)anno);
               return true;
            case X_MAPPING_OVERRIDES:
               this.parseMappingOverrides(cm, ((XMappingOverrides)anno).value());
               return true;
            default:
               return false;
         }
      }
   }

   protected boolean handleUnknownMemberMappingAnnotation(FieldMapping fm, Annotation anno) {
      KodoMappingTag tag = (KodoMappingTag)_tags.get(anno.annotationType());
      if (tag == null) {
         return false;
      } else {
         switch (tag) {
            case ELEM_COL:
               this.parseElementColumns(fm, (ElementColumn)anno);
               return true;
            case ELEM_COLS:
               this.parseElementColumns(fm, ((ElementColumns)anno).value());
               return true;
            case ELEM_EMBEDDED_MAPPING:
               ElementEmbeddedMapping eembed = (ElementEmbeddedMapping)anno;
               this.parseEmbeddedMapping(fm.getElementMapping(), eembed.nullIndicatorColumnName(), eembed.nullIndicatorAttributeName(), eembed.overrides());
               return true;
            case ELEM_STRAT:
               fm.getElementMapping().getValueInfo().setStrategy(((ElementStrategy)anno).value());
               return true;
            case KEY_COL:
               this.parseKeyColumns(fm, (KeyColumn)anno);
               return true;
            case KEY_COLS:
               this.parseKeyColumns(fm, ((KeyColumns)anno).value());
               return true;
            case KEY_CLASS_CRIT:
               fm.getKeyMapping().getValueInfo().setUseClassCriteria(((KeyClassCriteria)anno).value());
               return true;
            case KEY_EMBEDDED_MAPPING:
               KeyEmbeddedMapping kembed = (KeyEmbeddedMapping)anno;
               this.parseEmbeddedMapping(fm.getKeyMapping(), kembed.nullIndicatorColumnName(), kembed.nullIndicatorAttributeName(), kembed.overrides());
               return true;
            case KEY_FK:
               KeyForeignKey kfk = (KeyForeignKey)anno;
               this.parseForeignKey(fm.getKeyMapping().getValueInfo(), kfk.name(), kfk.enabled(), kfk.deferred(), kfk.deleteAction(), kfk.updateAction());
               return true;
            case KEY_INDEX:
               KeyIndex kidx = (KeyIndex)anno;
               this.parseIndex(fm.getKeyMapping().getValueInfo(), kidx.name(), kidx.enabled(), kidx.unique());
               return true;
            case KEY_JOIN_COL:
               this.parseKeyJoinColumns(fm, (KeyJoinColumn)anno);
               return true;
            case KEY_JOIN_COLS:
               this.parseKeyJoinColumns(fm, ((KeyJoinColumns)anno).value());
               return true;
            case KEY_NONPOLY:
               fm.getKeyMapping().setPolymorphic(toPolymorphicConstant(((KeyNonpolymorphic)anno).value()));
               return true;
            case KEY_STRAT:
               fm.getKeyMapping().getValueInfo().setStrategy(((KeyStrategy)anno).value());
               return true;
            case X_EMBEDDED_MAPPING:
               XEmbeddedMapping embed = (XEmbeddedMapping)anno;
               this.parseEmbeddedMapping(fm, embed.nullIndicatorColumnName(), embed.nullIndicatorAttributeName(), embed.overrides());
               return true;
            default:
               return false;
         }
      }
   }

   private void parseMappingOverrides(ClassMapping cm, XMappingOverride... overs) {
      XMappingOverride[] var4 = overs;
      int var5 = overs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         XMappingOverride over = var4[var6];
         if (StringUtils.isEmpty(over.name())) {
            throw new MetaDataException(_loc.get("no-override-name", cm));
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

   private void parseVersionColumns(ClassMapping cm, LockGroupVersionColumn... vcols) {
      if (vcols.length != 0) {
         List cols = new ArrayList(vcols.length);
         List lgs = null;
         LockGroupVersionColumn[] var5 = vcols;
         int var6 = vcols.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            LockGroupVersionColumn vcol = var5[var7];
            if (StringUtils.isEmpty(vcol.lockGroup())) {
               if (lgs != null) {
                  lgs.add("default");
               }
            } else {
               if (lgs == null) {
                  lgs = new ArrayList(vcols.length);

                  while(lgs.size() < cols.size()) {
                     lgs.add("default");
                  }
               }

               lgs.add(vcol.lockGroup());
            }

            cols.add(newColumn(vcol));
         }

         VersionMappingInfo info = cm.getVersion().getMappingInfo();
         info.setColumns(cols);
         if (lgs != null && info instanceof LockGroupVersionMappingInfo) {
            ((LockGroupVersionMappingInfo)info).setColumnLockGroupNames(lgs);
         }

      }
   }

   private static Column newColumn(LockGroupVersionColumn anno) {
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

   private void parseEmbeddedMapping(ValueMapping vm, String nullIndicatorColumn, String nullIndicatorField, XMappingOverride[] overs) {
      ClassMapping embed = vm.getEmbeddedMapping();
      if (embed == null) {
         throw new MetaDataException(_loc.get("not-embedded", vm));
      } else {
         XMappingOverride[] var7 = overs;
         int var8 = overs.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            XMappingOverride over = var7[var9];
            FieldMapping efm = embed.getFieldMapping(over.name());
            if (efm == null) {
               throw new MetaDataException(_loc.get("embed-override-name", vm, over.name()));
            }

            this.populate(efm, over);
         }

         String nullInd = null;
         if (!StringUtils.isEmpty(nullIndicatorField)) {
            nullInd = nullIndicatorField;
         } else if (!StringUtils.isEmpty(nullIndicatorColumn)) {
            nullInd = nullIndicatorColumn;
         }

         if (nullInd != null) {
            ValueMappingInfo info = vm.getValueInfo();
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
      }
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
         col.setName(join.columnDefinition());
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

   static {
      _tags.put(ElementColumn.class, KodoMappingTag.ELEM_COL);
      _tags.put(ElementColumns.class, KodoMappingTag.ELEM_COLS);
      _tags.put(ElementEmbeddedMapping.class, KodoMappingTag.ELEM_EMBEDDED_MAPPING);
      _tags.put(ElementStrategy.class, KodoMappingTag.ELEM_STRAT);
      _tags.put(KeyColumn.class, KodoMappingTag.KEY_COL);
      _tags.put(KeyColumns.class, KodoMappingTag.KEY_COLS);
      _tags.put(KeyClassCriteria.class, KodoMappingTag.KEY_CLASS_CRIT);
      _tags.put(KeyEmbeddedMapping.class, KodoMappingTag.KEY_EMBEDDED_MAPPING);
      _tags.put(KeyForeignKey.class, KodoMappingTag.KEY_FK);
      _tags.put(KeyIndex.class, KodoMappingTag.KEY_INDEX);
      _tags.put(KeyJoinColumn.class, KodoMappingTag.KEY_JOIN_COL);
      _tags.put(KeyJoinColumns.class, KodoMappingTag.KEY_JOIN_COLS);
      _tags.put(KeyNonpolymorphic.class, KodoMappingTag.KEY_NONPOLY);
      _tags.put(KeyStrategy.class, KodoMappingTag.KEY_STRAT);
      _tags.put(LockGroup.class, KodoMappingTag.LOCK_GROUP);
      _tags.put(LockGroups.class, KodoMappingTag.LOCK_GROUPS);
      _tags.put(LockGroupVersionColumn.class, KodoMappingTag.LOCK_GROUP_VERSION_COL);
      _tags.put(LockGroupVersionColumns.class, KodoMappingTag.LOCK_GROUP_VERSION_COLS);
      _tags.put(XEmbeddedMapping.class, KodoMappingTag.X_EMBEDDED_MAPPING);
      _tags.put(XMappingOverride.class, KodoMappingTag.X_MAPPING_OVERRIDE);
      _tags.put(XMappingOverrides.class, KodoMappingTag.X_MAPPING_OVERRIDES);
   }
}
