package kodo.jdo.jdbc;

import java.io.File;
import java.util.List;
import kodo.jdbc.meta.KodoFieldMapping;
import kodo.jdbc.meta.LockGroupVersionMappingInfo;
import kodo.jdo.JDOMetaDataSerializer;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.jdbc.conf.FetchModeValue;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.VersionMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.Extensions;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.InternalException;
import org.xml.sax.SAXException;

public class JDORMetaDataSerializer extends JDOMetaDataSerializer {
   private boolean _sync = false;
   private boolean _names = false;

   public JDORMetaDataSerializer(JDBCConfiguration conf) {
      super(conf);
   }

   public boolean getSyncMappingInfo() {
      return this._sync;
   }

   public void setSyncMappingInfo(boolean sync) {
      this._sync = sync;
   }

   public boolean getConstraintNames() {
      return this._names;
   }

   public void setConstraintNames(boolean names) {
      this._names = names;
   }

   protected File getSourceFile(Object obj) {
      if (this.isMappingMode() && !this.isMetaDataMode()) {
         if (obj instanceof ClassMapping) {
            obj = ((ClassMapping)obj).getMappingInfo();
         } else if (obj instanceof SequenceMapping) {
            return ((SequenceMapping)obj).getMappingFile();
         }
      }

      return super.getSourceFile(obj);
   }

   protected void serializeClass(ClassMetaData meta) throws SAXException {
      if (this._sync && this.isMappingMode()) {
         ClassMapping cls = (ClassMapping)meta;
         if ((cls.getResolve() & 2) != 0 && cls.isMapped()) {
            cls.syncMappingInfo();
            cls.getDiscriminator().syncMappingInfo();
            cls.getVersion().syncMappingInfo();
            FieldMapping[] fields;
            if (cls.getEmbeddingMetaData() == null) {
               fields = cls.getDefinedFieldMappings();
            } else {
               fields = cls.getFieldMappings();
            }

            for(int i = 0; i < fields.length; ++i) {
               fields[i].syncMappingInfo();
            }
         }
      }

      super.serializeClass(meta);
   }

   protected void addComments(Object obj) throws SAXException {
      if (this.isMappingMode() && !this.isMetaDataMode()) {
         if (obj instanceof ClassMapping) {
            obj = ((ClassMapping)obj).getMappingInfo();
         } else if (obj instanceof FieldMapping) {
            obj = ((FieldMapping)obj).getMappingInfo();
         }
      }

      super.addComments(obj);
   }

   protected void addClassMappingAttributes(ClassMetaData meta) throws SAXException {
      ClassMapping cls = (ClassMapping)meta;
      if (cls.getMappingInfo().getTableName() != null) {
         this.addAttribute("table", cls.getMappingInfo().getTableName());
      }

   }

   protected void serializeKnownClassMappingExtensions(ClassMetaData meta) throws SAXException {
      ClassMapping cls = (ClassMapping)meta;
      int fetch = cls.getSubclassFetchMode();
      if (fetch != -99 && (cls.getPCSuperclass() == null || fetch != cls.getPCSuperclassMapping().getSubclassFetchMode())) {
         FetchModeValue val = new FetchModeValue("jdbc-subclass-fetch-mode");
         val.set(fetch);
         this.serializeExtension("openjpa", "jdbc-subclass-fetch-mode", val.getString(), (Extensions)null);
      }

   }

   protected void serializeClassMappingContent(ClassMetaData meta) throws SAXException {
      ClassMapping cls = (ClassMapping)meta;
      ClassMappingInfo info = cls.getMappingInfo();
      Discriminator disc = cls.getDiscriminator();
      DiscriminatorMappingInfo dinfo = disc.getMappingInfo();
      boolean serDisc = dinfo.getStrategy() != null || dinfo.hasSchemaComponents() || dinfo.getValue() != null;
      if (info.getStrategy() != null || serDisc) {
         this.serializeInheritance(cls, serDisc);
      }

      Version vers = cls.getVersion();
      VersionMappingInfo vinfo = vers.getMappingInfo();
      if (vinfo.getStrategy() != null || vinfo.hasSchemaComponents()) {
         this.serializeVersion(vers);
      }

   }

   private void serializeInheritance(ClassMapping cls, boolean serDisc) throws SAXException {
      MappingInfo info = cls.getMappingInfo();
      String strat = info.getStrategy();
      if (strat != null) {
         if (!"full".equals(strat) && !"vertical".equals(strat)) {
            if ("flat".equals(strat)) {
               strat = "superclass-table";
            } else if ("none".equals(strat)) {
               strat = "subclass-table";
            }
         } else {
            strat = "new-table";
         }

         this.addAttribute("strategy", strat);
      }

      this.startElement("inheritance");
      if (info.getJoinDirection() != 0) {
         this.addMappingInfoAttributes(info);
         this.startElement("join");
         this.serializeMappingInfoContent(info);
         this.endElement("join");
      }

      if (serDisc) {
         this.serializeDiscriminator(cls.getDiscriminator());
      }

      this.endElement("inheritance");
   }

   private void addMappingInfoAttributes(MappingInfo info) throws SAXException {
      this.addColumnInfoAttributes(info);
      this.addConstraintInfoAttributes(info);
   }

   private void addColumnInfoAttributes(MappingInfo info) throws SAXException {
      List cols = info.getColumns();
      if (cols.size() == 1 && this.onlyNameIsNonDefault((Column)cols.get(0))) {
         String name = ((Column)cols.get(0)).getName();
         if (name != null) {
            this.addAttribute("column", name);
         }
      }

   }

   private void addConstraintInfoAttributes(MappingInfo info) {
      ForeignKey fk = info.getForeignKey();
      if (fk != null && this.onlyDeleteActionIsNonDefault(fk)) {
         this.addAttribute("delete-action", ForeignKey.getActionName(fk.getDeleteAction()));
      } else if (!info.canForeignKey()) {
         this.addAttribute("delete-action", "none");
      }

      Index idx = info.getIndex();
      if (idx != null && this.onlyIndexActionIsNonDefault(idx)) {
         if (idx.isUnique()) {
            this.addAttribute("indexed", "unique");
         } else {
            this.addAttribute("indexed", "true");
         }
      } else if (!info.canIndex()) {
         this.addAttribute("indexed", "false");
      }

      Unique unq = info.getUnique();
      if (unq != null && this.onlyUniqueActionIsNonDefault(unq)) {
         this.addAttribute("unique", "true");
      } else if (!info.canUnique()) {
         this.addAttribute("unique", "false");
      }

   }

   private void serializeMappingInfoContent(MappingInfo info) throws SAXException {
      this.serializeColumnContent(info);
      this.serializeConstraintContent(info);
   }

   private void serializeColumnContent(MappingInfo info) throws SAXException {
      List cols = info.getColumns();
      if (cols.size() != 1 || !this.onlyNameIsNonDefault((Column)cols.get(0))) {
         for(int i = 0; i < cols.size(); ++i) {
            this.serializeColumn((Column)cols.get(i));
         }
      }

   }

   private void serializeConstraintContent(MappingInfo info) throws SAXException {
      ForeignKey fk = info.getForeignKey();
      if (fk != null && !this.onlyDeleteActionIsNonDefault(fk)) {
         this.serializeForeignKey(fk);
      }

      Index idx = info.getIndex();
      if (idx != null && !this.onlyIndexActionIsNonDefault(idx)) {
         this.serializeIndex(idx);
      }

      Unique unq = info.getUnique();
      if (unq != null && !this.onlyUniqueActionIsNonDefault(unq)) {
         this.serializeUnique(unq);
      }

   }

   private boolean onlyNameIsNonDefault(Column col) {
      if (col.getTarget() != null) {
         return false;
      } else if (col.getTargetField() != null) {
         return false;
      } else if (col.getType() != 1111) {
         return false;
      } else if (col.getTypeName() != null) {
         return false;
      } else if (col.getSize() != 0) {
         return false;
      } else if (col.getDecimalDigits() != 0) {
         return false;
      } else if (col.getDefaultString() != null) {
         return false;
      } else if (col.isNotNullExplicit()) {
         return false;
      } else {
         return !col.getFlag(2) && !col.getFlag(4);
      }
   }

   private boolean onlyDeleteActionIsNonDefault(ForeignKey fk) {
      if (fk.getName() != null && this._names) {
         return false;
      } else if (fk.isDeferred()) {
         return false;
      } else {
         return fk.getUpdateAction() == 2;
      }
   }

   private boolean onlyIndexActionIsNonDefault(Index idx) {
      return idx.getName() == null || !this._names;
   }

   private boolean onlyUniqueActionIsNonDefault(Unique unq) {
      if (unq.getName() != null && this._names) {
         return false;
      } else {
         return !unq.isDeferred();
      }
   }

   private void serializeColumn(Column col) throws SAXException {
      this.addColumnAttributes(col);
      this.startElement("column");
      this.serializeColumnExtensions(col);
      this.endElement("column");
   }

   private void addColumnAttributes(Column col) throws SAXException {
      if (col.getName() != null) {
         this.addAttribute("name", col.getName());
      }

      if (col.getType() != 1111) {
         this.addAttribute("jdbc-type", Schemas.getJDBCName(col.getType()));
      }

      if (col.getTypeName() != null) {
         this.addAttribute("sql-type", col.getTypeName());
      }

      if (col.getSize() != 0 && col.getSize() != 255) {
         this.addAttribute("length", String.valueOf(col.getSize()));
      }

      if (col.getDecimalDigits() != 0) {
         this.addAttribute("scale", String.valueOf(col.getDecimalDigits()));
      }

      if (col.isNotNullExplicit()) {
         this.addAttribute("allows-null", String.valueOf(!col.isNotNull()));
      }

      if (col.getDefaultString() != null) {
         this.addAttribute("default-value", col.getDefaultString());
      }

      if (col.getTarget() != null) {
         this.addAttribute("target", col.getTarget());
      } else if (col.getTargetField() != null) {
         this.addAttribute("target-field", col.getTargetField());
      }

   }

   private void serializeColumnExtensions(Column col) throws SAXException {
      if (col.getFlag(2)) {
         this.serializeExtension("openjpa", "uninsertable", (String)null, (Extensions)null);
      }

      if (col.getFlag(4)) {
         this.serializeExtension("openjpa", "unupdatable", (String)null, (Extensions)null);
      }

   }

   private void serializeForeignKey(ForeignKey fk) throws SAXException {
      if (fk.getName() != null && this._names) {
         this.addAttribute("name", fk.getName());
      }

      if (fk.getDeleteAction() != 2) {
         this.addAttribute("delete-action", ForeignKey.getActionName(fk.getDeleteAction()));
      }

      if (fk.getUpdateAction() != 2) {
         this.addAttribute("update-action", ForeignKey.getActionName(fk.getUpdateAction()));
      }

      if (fk.isDeferred()) {
         this.addAttribute("deferred", "true");
      }

      this.startElement("foreign-key");
      this.endElement("foreign-key");
   }

   private void serializeIndex(Index idx) throws SAXException {
      if (idx.getName() != null && this._names) {
         this.addAttribute("name", idx.getName());
      }

      if (idx.isUnique()) {
         this.addAttribute("unique", "true");
      }

      this.startElement("index");
      this.endElement("index");
   }

   private void serializeUnique(Unique unq) throws SAXException {
      if (unq.getName() != null && this._names) {
         this.addAttribute("name", unq.getName());
      }

      if (unq.isDeferred()) {
         this.addAttribute("deferred", "true");
      }

      this.startElement("unique");
      this.endElement("unique");
   }

   private void serializeDiscriminator(Discriminator disc) throws SAXException {
      DiscriminatorMappingInfo info = disc.getMappingInfo();
      String strat = info.getStrategy();
      if (strat != null) {
         if ("none".equals(strat)) {
            strat = "final";
         } else if ("subclass-join".equals(strat)) {
            strat = "none";
         } else if ("class-name".equals(strat)) {
            strat = "class-name";
         } else if ("value-map".equals(strat)) {
            strat = "value-map";
         }

         this.addAttribute("strategy", strat);
      }

      if (info.getValue() != null) {
         this.addAttribute("value", info.getValue());
      }

      this.addMappingInfoAttributes(info);
      this.startElement("discriminator");
      this.serializeMappingInfoContent(info);
      this.endElement("discriminator");
   }

   private void serializeVersion(Version vers) throws SAXException {
      VersionMappingInfo info = vers.getMappingInfo();
      List cols = info.getColumns();
      List lgs = ((LockGroupVersionMappingInfo)info).getColumnLockGroupNames();
      boolean ext = !cols.isEmpty() && (lgs.size() > 1 || !lgs.isEmpty() && !"default".equals(lgs.get(0)));
      String strat = info.getStrategy();
      if (strat != null) {
         if ("version-number".equals(strat)) {
            strat = "version-number";
         } else if ("timestamp".equals(strat)) {
            strat = "date-time";
         } else if ("state-comparison".equals(strat)) {
            strat = "state-comparison";
         } else if ("none".equals(strat)) {
            strat = "none";
         }

         this.addAttribute("strategy", strat);
      }

      if (!ext) {
         this.addColumnInfoAttributes(info);
      }

      this.addConstraintInfoAttributes(info);
      this.startElement("version");
      if (!ext) {
         this.serializeColumnContent(info);
      } else {
         for(int i = 0; i < cols.size(); ++i) {
            Column col = (Column)cols.get(i);
            this.addColumnAttributes(col);
            this.startElement("column");
            if (!"default".equals(lgs.get(i))) {
               this.serializeExtension("kodo", "lock-group", (String)lgs.get(i), (Extensions)null);
            }

            this.serializeColumnExtensions(col);
            this.endElement("column");
         }
      }

      this.serializeConstraintContent(info);
      this.endElement("version");
   }

   protected boolean hasMappedPCSuperclass(ClassMetaData meta) {
      return ((ClassMapping)meta).getJoinablePCSuperclassMapping() != null;
   }

   protected boolean hasDataStoreIdentityMappingInformation(ClassMetaData meta) {
      return ((ClassMapping)meta).getMappingInfo().hasSchemaComponents();
   }

   protected void addDataStoreIdentityAttributes(ClassMetaData meta) throws SAXException {
      super.addDataStoreIdentityAttributes(meta);
      this.addMappingInfoAttributes(((ClassMapping)meta).getMappingInfo());
   }

   protected void serializeDataStoreIdentityContent(ClassMetaData meta) throws SAXException {
      this.serializeMappingInfoContent(((ClassMapping)meta).getMappingInfo());
   }

   protected Boolean needsSerialization(FieldMetaData fmd, FieldMetaData orig) {
      Boolean sup = super.needsSerialization(fmd, orig);
      if (sup == Boolean.FALSE && this.isMetaDataMode()) {
         KodoFieldMapping fm = (KodoFieldMapping)fmd;
         if (orig == null) {
            if (fm.getLockGroup() == null || !fm.getLockGroup().isDefault()) {
               return Boolean.TRUE;
            }
         } else {
            KodoFieldMapping ofm = (KodoFieldMapping)orig;
            if (!ObjectUtils.equals(fm.getLockGroup(), ofm.getLockGroup())) {
               return Boolean.TRUE;
            }
         }

         return Boolean.FALSE;
      } else {
         return sup;
      }
   }

   protected boolean hasMappingInformation(FieldMetaData fmd) {
      FieldMapping field = (FieldMapping)fmd;
      if (!field.getMappingInfo().hasSchemaComponents() && !field.getValueInfo().hasSchemaComponents() && !field.getKeyMapping().getValueInfo().hasSchemaComponents() && !field.getElementMapping().getValueInfo().hasSchemaComponents()) {
         boolean crit = field.getMappingRepository().getMappingDefaults().useClassCriteria();
         if (field.getValueInfo().getUseClassCriteria() == crit && field.getKeyMapping().getValueInfo().getUseClassCriteria() == crit && field.getElementMapping().getValueInfo().getUseClassCriteria() == crit) {
            return field.getPolymorphic() == 0 && field.getKeyMapping().getPolymorphic() == 0 && field.getElementMapping().getPolymorphic() == 0 ? super.hasMappingInformation(fmd) : true;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   protected void addFieldMappingAttributes(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
      super.addFieldMappingAttributes(fmd, orig);
      FieldMappingInfo info = ((FieldMapping)fmd).getMappingInfo();
      if (info.getTableName() != null) {
         this.addAttribute("table", info.getTableName());
      }

   }

   protected void serializeKnownFieldExtensions(FieldMetaData fmd) throws SAXException {
      super.serializeKnownFieldExtensions(fmd);
      KodoFieldMapping fm = (KodoFieldMapping)fmd;
      if (fm.getLockGroup() == null) {
         this.serializeExtension("kodo", "lock-group", "none", (Extensions)null);
      } else if (!fm.getLockGroup().isDefault()) {
         String lg = fm.getLockGroup().getName();
         this.serializeExtension("kodo", "lock-group", lg, (Extensions)null);
      }

   }

   protected void serializeKnownFieldMappingExtensions(FieldMetaData fmd) throws SAXException {
      super.serializeKnownFieldMappingExtensions(fmd);
      FieldMapping mapping = (FieldMapping)fmd;
      if (mapping.getEagerFetchMode() != -99) {
         FetchModeValue val = new FetchModeValue("jdbc-eager-fetch-mode");
         val.set(mapping.getEagerFetchMode());
         this.serializeExtension("openjpa", "jdbc-eager-fetch-mode", val.getString(), (Extensions)null);
      }

      if (mapping.getMappingInfo().getStrategy() != null) {
         this.serializeExtension("openjpa", "jdbc-strategy", this.getClassName(mapping.getMappingInfo().getStrategy()), (Extensions)null);
      } else if (mapping.getValueInfo().getStrategy() != null) {
         this.serializeExtension("openjpa", "jdbc-strategy", this.getClassName(mapping.getValueInfo().getStrategy()), (Extensions)null);
      }

      if (mapping.getKeyMapping().getValueInfo().getStrategy() != null) {
         this.serializeExtension("openjpa", "jdbc-key-strategy", this.getClassName(mapping.getKeyMapping().getValueInfo().getStrategy()), (Extensions)null);
      }

      String prefix;
      if (mapping.getElementMapping().getValueInfo().getStrategy() != null) {
         prefix = mapping.getTypeCode() == 13 ? "jdbc-value-" : "jdbc-element-";
         this.serializeExtension("openjpa", prefix + "strategy", this.getClassName(mapping.getElementMapping().getValueInfo().getStrategy()), (Extensions)null);
      }

      if (mapping.getPolymorphic() != 0) {
         this.serializeExtension("openjpa", "jdbc-nonpolymorphic", toNonpolymorphicValue(mapping), (Extensions)null);
      }

      if (mapping.getKeyMapping().getPolymorphic() != 0) {
         this.serializeExtension("openjpa", "jdbc-key-nonpolymorphic", toNonpolymorphicValue(mapping.getKeyMapping()), (Extensions)null);
      }

      if (mapping.getElementMapping().getPolymorphic() != 0) {
         prefix = mapping.getTypeCode() == 13 ? "jdbc-value-" : "jdbc-element-";
         this.serializeExtension("openjpa", prefix + "nonpolymorphic", toNonpolymorphicValue(mapping.getElementMapping()), (Extensions)null);
      }

      boolean crit = mapping.getMappingRepository().getMappingDefaults().useClassCriteria();
      if (mapping.getValueInfo().getUseClassCriteria() != crit) {
         this.serializeExtension("openjpa", "jdbc-class-criteria", String.valueOf(!crit), (Extensions)null);
      }

      if (mapping.getKeyMapping().getValueInfo().getUseClassCriteria() != crit) {
         this.serializeExtension("openjpa", "jdbc-key-class-criteria", String.valueOf(!crit), (Extensions)null);
      }

      if (mapping.getElementMapping().getValueInfo().getUseClassCriteria() != crit) {
         prefix = mapping.getTypeCode() == 13 ? "jdbc-value-" : "jdbc-element-";
         this.serializeExtension("openjpa", prefix + "class-criteria", String.valueOf(!crit), (Extensions)null);
      }

   }

   private static String toNonpolymorphicValue(ValueMapping vm) {
      switch (vm.getPolymorphic()) {
         case 0:
            return "false";
         case 1:
            return null;
         case 2:
            return "joinable";
         default:
            throw new InternalException();
      }
   }

   protected void serializeFieldMappingContent(FieldMetaData fmd) throws SAXException {
      MappingInfo info = ((FieldMapping)fmd).getMappingInfo();
      if (info.getJoinDirection() != 0) {
         this.addMappingInfoAttributes(info);
         this.startElement("join");
         this.serializeMappingInfoContent(info);
         this.endElement("join");
      }

   }

   protected boolean hasMappingInformation(ValueMetaData vmd) {
      return ((ValueMapping)vmd).getValueInfo().hasSchemaComponents();
   }

   protected void addValueMappingAttributes(ValueMetaData vmd) throws SAXException {
      super.addValueMappingAttributes(vmd);
      if (vmd.getEmbeddedMetaData() == null) {
         this.addMappingInfoAttributes(((ValueMapping)vmd).getValueInfo());
      }

   }

   protected void addEmbedMappingAttributes(ClassMetaData embed) throws SAXException {
      ValueMappingInfo info = ((ValueMapping)embed.getEmbeddingMetaData()).getValueInfo();
      if (!info.canIndicateNull()) {
         this.addAttribute("null-indicator-column", "false");
      } else if (!info.getColumns().isEmpty()) {
         String name = ((Column)info.getColumns().get(0)).getName();
         if (name != null) {
            this.addAttribute("null-indicator-column", name);
         }
      }

   }

   protected void serializeValueMappingContent(ValueMetaData vmd) throws SAXException {
      if (vmd.getEmbeddedMetaData() == null) {
         ValueMapping val = (ValueMapping)vmd;
         this.serializeMappingInfoContent(val.getValueInfo());
         if (val instanceof FieldMapping) {
            FieldMappingInfo info = ((FieldMapping)val).getMappingInfo();
            if (!info.canOrderColumn()) {
               this.addAttribute("column", "false");
               this.startElement("order");
               this.endElement("order");
            } else if (info.getOrderColumn() != null) {
               boolean serCol = this.onlyNameIsNonDefault(info.getOrderColumn());
               if (serCol && info.getOrderColumn().getName() != null) {
                  this.addAttribute("column", info.getOrderColumn().getName());
               }

               this.startElement("order");
               if (!serCol) {
                  this.serializeColumn(info.getOrderColumn());
               }

               this.endElement("order");
            }
         }

      }
   }
}
