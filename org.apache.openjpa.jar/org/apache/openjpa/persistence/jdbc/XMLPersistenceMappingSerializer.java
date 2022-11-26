package org.apache.openjpa.persistence.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EnumType;
import javax.persistence.TemporalType;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.persistence.PersistenceStrategy;
import org.apache.openjpa.persistence.XMLPersistenceMetaDataSerializer;
import org.xml.sax.SAXException;
import serp.util.Strings;

public class XMLPersistenceMappingSerializer extends XMLPersistenceMetaDataSerializer {
   private static final int TYPE_RESULTMAP = 21;
   private static final Map _names = new EnumMap(ColType.class);
   private List _results = null;
   private boolean _sync = false;

   public XMLPersistenceMappingSerializer(JDBCConfiguration conf) {
      super(conf);
   }

   public boolean getSyncMappingInfo() {
      return this._sync;
   }

   public void setSyncMappingInfo(boolean sync) {
      this._sync = sync;
   }

   public void addQueryResultMapping(QueryResultMapping meta) {
      if (this._results == null) {
         this._results = new ArrayList();
      }

      this._results.add(meta);
   }

   public boolean removeQueryResultMapping(QueryResultMapping meta) {
      return this._results != null && this._results.remove(meta);
   }

   public void addAll(MetaDataRepository repos) {
      super.addAll(repos);
      QueryResultMapping[] arr$ = ((MappingRepository)repos).getQueryResultMappings();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         QueryResultMapping res = arr$[i$];
         this.addQueryResultMapping(res);
      }

   }

   public boolean removeAll(MetaDataRepository repos) {
      boolean removed = super.removeAll(repos);
      QueryResultMapping[] arr$ = ((MappingRepository)repos).getQueryResultMappings();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         QueryResultMapping res = arr$[i$];
         removed |= this.removeQueryResultMapping(res);
      }

      return removed;
   }

   public void clear() {
      super.clear();
      if (this._results != null) {
         this._results.clear();
      }

   }

   protected void addCommments(Object obj) throws SAXException {
      if (this.isMappingMode() && !this.isMetaDataMode()) {
         if (obj instanceof ClassMapping) {
            obj = ((ClassMapping)obj).getMappingInfo();
         } else if (obj instanceof FieldMapping) {
            obj = ((FieldMapping)obj).getMappingInfo();
         }
      }

      super.addComments(obj);
   }

   protected void serializeClass(ClassMetaData meta, boolean access) throws SAXException {
      if (this._sync && this.isMappingMode() && meta instanceof ClassMapping) {
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

            FieldMapping[] arr$ = fields;
            int len$ = fields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               FieldMapping f = arr$[i$];
               f.syncMappingInfo();
            }
         }
      }

      super.serializeClass(meta, access);
   }

   protected void serializeClassMappingContent(ClassMetaData mapping) throws SAXException {
      ClassMapping cls = (ClassMapping)mapping;
      ClassMappingInfo info = cls.getMappingInfo();
      this.serializeTable(info.getTableName(), "table", Strings.getClassName(mapping.getDescribedType()), (ClassMappingInfo)null, info.getUniques());
      String[] arr$ = info.getSecondaryTableNames();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String second = arr$[i$];
         this.serializeTable(second, "secondary-table", (String)null, info, (org.apache.openjpa.jdbc.schema.Unique[])null);
      }

      this.serializeColumns(info, XMLPersistenceMappingSerializer.ColType.PK_JOIN, (String)null);
   }

   protected void serializeInheritanceContent(ClassMetaData mapping) throws SAXException {
      ClassMapping cls = (ClassMapping)mapping;
      ClassMappingInfo info = cls.getMappingInfo();
      DiscriminatorMappingInfo dinfo = cls.getDiscriminator().getMappingInfo();
      String strat = info.getHierarchyStrategy();
      if ("flat".equals(strat)) {
         this.addAttribute("strategy", "SINGLE_TABLE");
      } else if ("vertical".equals(strat)) {
         this.addAttribute("strategy", "JOINED");
      } else if ("full".equals(strat)) {
         this.addAttribute("strategy", "TABLE_PER_CLASS");
      }

      if (strat != null) {
         this.startElement("inheritance");
         this.endElement("inheritance");
      }

      if (dinfo.getValue() != null) {
         this.startElement("discriminator-value");
         this.addText(dinfo.getValue());
         this.endElement("discriminator-value");
      }

      this.serializeColumns(dinfo, XMLPersistenceMappingSerializer.ColType.DISC, (String)null);
   }

   private void serializeTable(String table, String elementName, String defaultName, ClassMappingInfo secondaryInfo, org.apache.openjpa.jdbc.schema.Unique[] uniques) throws SAXException {
      List cols = null;
      if (secondaryInfo != null) {
         cols = secondaryInfo.getSecondaryTableJoinColumns(table);
      }

      boolean print = cols != null && cols.size() > 0 || uniques != null && uniques.length > 0;
      if (table != null && (defaultName == null || !defaultName.equals(table))) {
         print = true;
         int index = table.indexOf(46);
         if (index < 0) {
            this.addAttribute("name", table);
         } else {
            this.addAttribute("schema", table.substring(0, index));
            this.addAttribute("name", table.substring(index + 1));
         }
      }

      if (print) {
         this.startElement(elementName);
         if (cols != null) {
            Iterator i$ = cols.iterator();

            while(i$.hasNext()) {
               Column col = (Column)i$.next();
               this.serializeColumn(col, XMLPersistenceMappingSerializer.ColType.PK_JOIN, (String)null, false);
            }
         }

         if (uniques != null) {
            org.apache.openjpa.jdbc.schema.Unique[] arr$ = uniques;
            int len$ = uniques.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               org.apache.openjpa.jdbc.schema.Unique unique = arr$[i$];
               this.serializeUniqueConstraint(unique);
            }
         }

         this.endElement(elementName);
      }

   }

   protected boolean serializeAttributeOverride(FieldMetaData fmd, FieldMetaData orig) {
      if (orig != null && fmd != orig) {
         FieldMapping field = (FieldMapping)fmd;
         FieldMapping field2 = (FieldMapping)orig;
         if (!field.getMappingInfo().hasSchemaComponents() && !field2.getMappingInfo().hasSchemaComponents()) {
            ValueMappingInfo info = field.getValueInfo();
            List cols = info.getColumns();
            if (cols != null && cols.size() != 0) {
               ValueMappingInfo info2 = field2.getValueInfo();
               List cols2 = info2.getColumns();
               if (cols2 != null && cols2.size() == cols.size()) {
                  if (cols.size() != 1) {
                     return true;
                  } else {
                     for(int i = 0; i < cols.size(); ++i) {
                        Column col = (Column)cols.get(i);
                        Column col2 = (Column)cols2.get(i);
                        if (!StringUtils.equals(col.getName(), col2.getName())) {
                           return true;
                        }

                        if (!StringUtils.equals(col.getTypeName(), col2.getTypeName())) {
                           return true;
                        }

                        if (col.getSize() != col2.getSize()) {
                           return true;
                        }

                        if (col.getDecimalDigits() != col2.getDecimalDigits()) {
                           return true;
                        }

                        if (col.getFlag(2) != col2.getFlag(2)) {
                           return true;
                        }

                        if (col.getFlag(4) != col2.getFlag(4)) {
                           return true;
                        }
                     }

                     return false;
                  }
               } else {
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected void serializeAttributeOverrideMappingContent(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
      FieldMapping fm = (FieldMapping)fmd;
      this.serializeColumns(fm.getValueInfo(), XMLPersistenceMappingSerializer.ColType.COL, fm.getMappingInfo().getTableName());
   }

   protected PersistenceStrategy getStrategy(FieldMetaData fmd) {
      PersistenceStrategy strat = super.getStrategy(fmd);
      FieldMapping field = (FieldMapping)fmd;
      switch (strat) {
         case MANY_MANY:
            if (field.getMappedBy() != null || field.getMappingInfo().getJoinDirection() != 0 && field.getElementMapping().getValueInfo().getUnique() == null) {
               break;
            }

            return PersistenceStrategy.ONE_MANY;
         case MANY_ONE:
            if (field.getValueInfo().getJoinDirection() == 2 || field.getValueInfo().getUnique() != null) {
               return PersistenceStrategy.ONE_ONE;
            }

            List cols = field.getValueInfo().getColumns();
            boolean pkJoin = cols != null && cols.size() > 0;

            for(int i = 0; pkJoin && i < cols.size(); ++i) {
               pkJoin = ((Column)cols.get(i)).getFlag(128);
            }

            if (pkJoin) {
               return PersistenceStrategy.ONE_ONE;
            }
      }

      return strat;
   }

   protected void serializeFieldMappingContent(FieldMetaData fmd, PersistenceStrategy strategy) throws SAXException {
      if (fmd.getMappedBy() == null) {
         FieldMapping field = (FieldMapping)fmd;
         switch (strategy) {
            case MANY_MANY:
               break;
            case MANY_ONE:
            case ONE_ONE:
               this.serializeColumns(field.getValueInfo(), XMLPersistenceMappingSerializer.ColType.JOIN, field.getMappingInfo().getTableName());
               return;
            case ONE_MANY:
               if (field.getMappingInfo().getJoinDirection() == 0) {
                  this.serializeColumns(field.getElementMapping().getValueInfo(), XMLPersistenceMappingSerializer.ColType.JOIN, (String)null);
                  return;
               }
               break;
            default:
               this.serializeColumns(field.getValueInfo(), XMLPersistenceMappingSerializer.ColType.COL, field.getMappingInfo().getTableName());
               if (strategy == PersistenceStrategy.BASIC && this.isLob(field)) {
                  this.startElement("lob");
                  this.endElement("lob");
               }

               TemporalType temporal = this.getTemporal(field);
               if (temporal != null) {
                  this.startElement("temporal");
                  this.addText(temporal.toString());
                  this.endElement("temporal");
               }

               EnumType enumType = this.getEnumType(field);
               if (enumType != null && enumType != EnumType.ORDINAL) {
                  this.startElement("enumerated");
                  this.addText(enumType.toString());
                  this.endElement("enumerated");
               }

               return;
         }

         if (field.getMappingInfo().hasSchemaComponents() || field.getElementMapping().getValueInfo().hasSchemaComponents()) {
            String table = field.getMappingInfo().getTableName();
            if (table != null) {
               int index = table.indexOf(46);
               if (index < 0) {
                  this.addAttribute("name", table);
               } else {
                  this.addAttribute("schema", table.substring(0, index));
                  this.addAttribute("name", table.substring(index + 1));
               }
            }

            this.startElement("join-table");
            this.serializeColumns(field.getMappingInfo(), XMLPersistenceMappingSerializer.ColType.JOIN, (String)null);
            this.serializeColumns(field.getElementMapping().getValueInfo(), XMLPersistenceMappingSerializer.ColType.INVERSE, (String)null);
            this.endElement("join-table");
         }

      }
   }

   private boolean isLob(FieldMapping field) {
      Iterator i$ = field.getValueInfo().getColumns().iterator();

      Column col;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         col = (Column)i$.next();
      } while(col.getType() != 2004 && col.getType() != 2005);

      return true;
   }

   private TemporalType getTemporal(FieldMapping field) {
      if (field.getDeclaredTypeCode() != 14 && field.getDeclaredTypeCode() != 28) {
         return null;
      } else {
         DBDictionary dict = ((JDBCConfiguration)this.getConfiguration()).getDBDictionaryInstance();
         int def = dict.getJDBCType(field.getTypeCode(), false);
         Iterator i$ = field.getValueInfo().getColumns().iterator();

         while(i$.hasNext()) {
            Column col = (Column)i$.next();
            if (col.getType() != def) {
               switch (col.getType()) {
                  case 91:
                     return TemporalType.DATE;
                  case 92:
                     return TemporalType.TIME;
                  case 93:
                     return TemporalType.TIMESTAMP;
               }
            }
         }

         return null;
      }
   }

   protected EnumType getEnumType(FieldMapping field) {
      if (field.getDeclaredTypeCode() != 8) {
         return null;
      } else if (!(field.getHandler() instanceof EnumValueHandler)) {
         return null;
      } else {
         return ((EnumValueHandler)field.getHandler()).getStoreOrdinal() ? EnumType.ORDINAL : EnumType.STRING;
      }
   }

   private void serializeColumns(MappingInfo info, ColType type, String secondary) throws SAXException {
      List cols = info.getColumns();
      if (cols != null) {
         Iterator i$ = cols.iterator();

         while(i$.hasNext()) {
            Column col = (Column)i$.next();
            this.serializeColumn(col, type, secondary, info.getUnique() != null);
         }

      }
   }

   private void serializeColumn(Column col, ColType type, String secondary, boolean unique) throws SAXException {
      if (col.getName() != null) {
         this.addAttribute("name", col.getName());
      }

      if (col.getTypeName() != null) {
         this.addAttribute("column-definition", col.getTypeName());
      }

      if (col.getTarget() != null && (type == XMLPersistenceMappingSerializer.ColType.JOIN || type == XMLPersistenceMappingSerializer.ColType.INVERSE || type == XMLPersistenceMappingSerializer.ColType.PK_JOIN)) {
         this.addAttribute("referenced-column-name", col.getTarget());
      }

      if (type == XMLPersistenceMappingSerializer.ColType.COL || type == XMLPersistenceMappingSerializer.ColType.JOIN || type == XMLPersistenceMappingSerializer.ColType.PK_JOIN) {
         if (unique) {
            this.addAttribute("unique", "true");
         }

         if (col.isNotNull()) {
            this.addAttribute("nullable", "false");
         }

         if (col.getFlag(2)) {
            this.addAttribute("insertable", "false");
         }

         if (col.getFlag(4)) {
            this.addAttribute("updatable", "false");
         }

         if (secondary != null) {
            this.addAttribute("table", secondary);
         }

         if (type == XMLPersistenceMappingSerializer.ColType.COL) {
            if (col.getSize() > 0 && col.getSize() != 255) {
               this.addAttribute("length", col.getSize() + "");
            }

            if (col.getDecimalDigits() != 0) {
               this.addAttribute("scale", col.getDecimalDigits() + "");
            }
         }
      }

      if (type != XMLPersistenceMappingSerializer.ColType.COL || this.getAttributes().getLength() > 0) {
         String name = col.getFlag(128) ? (String)_names.get(XMLPersistenceMappingSerializer.ColType.PK_JOIN) : (String)_names.get(type);
         this.startElement(name);
         this.endElement(name);
      }

   }

   private void serializeUniqueConstraint(org.apache.openjpa.jdbc.schema.Unique unique) throws SAXException {
      this.startElement("unique-constraint");
      Column[] columns = unique.getColumns();
      Column[] arr$ = columns;
      int len$ = columns.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Column column = arr$[i$];
         this.startElement("column-name");
         this.addText(column.getName());
         this.endElement("column-name");
      }

      this.endElement("unique-constraint");
   }

   protected XMLPersistenceMetaDataSerializer.SerializationComparator newSerializationComparator() {
      return new MappingSerializationComparator();
   }

   protected void addSystemMappingElements(Collection toSerialize) {
      if (this.isQueryMode()) {
         toSerialize.addAll(this.getQueryResultMappings((ClassMetaData)null));
      }

   }

   protected int type(Object o) {
      int type = super.type(o);
      return type == -1 && o instanceof QueryResultMapping ? 21 : type;
   }

   private List getQueryResultMappings(ClassMetaData cm) {
      if (this._results != null && !this._results.isEmpty()) {
         List result = null;

         for(int i = 0; i < this._results.size(); ++i) {
            QueryResultMapping element = (QueryResultMapping)this._results.get(i);
            if ((cm != null || element.getSourceScope() == null) && (cm == null || element.getSourceScope() == cm.getDescribedType())) {
               if (result == null) {
                  result = new ArrayList(this._results.size() - i);
               }

               result.add(element);
            }
         }

         return (List)(result == null ? Collections.EMPTY_LIST : result);
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   protected void serializeSystemMappingElement(Object obj) throws SAXException {
      if (obj instanceof QueryResultMapping) {
         this.serializeQueryResultMapping((QueryResultMapping)obj);
      }

   }

   protected void serializeQueryMappings(ClassMetaData meta) throws SAXException {
      Iterator i$ = this.getQueryResultMappings(meta).iterator();

      while(i$.hasNext()) {
         QueryResultMapping res = (QueryResultMapping)i$.next();
         this.serializeQueryResultMapping(res);
      }

   }

   private void serializeQueryResultMapping(QueryResultMapping meta) throws SAXException {
      if (this.getSerializeAnnotations() || meta.getSourceType() != 1) {
         this.addAttribute("name", meta.getName());
         this.startElement("sql-result-set-mapping");
         QueryResultMapping.PCResult[] arr$ = meta.getPCResults();
         int len$ = arr$.length;

         int i$;
         for(i$ = 0; i$ < len$; ++i$) {
            QueryResultMapping.PCResult pc = arr$[i$];
            this.addAttribute("entity-class", pc.getCandidateType().getName());
            Object discrim = pc.getMapping("<discriminator>");
            if (discrim != null) {
               this.addAttribute("discriminator-column", discrim.toString());
            }

            this.startElement("entity-result");
            String[] arr$ = pc.getMappingPaths();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String path = arr$[i$];
               this.addAttribute("name", path);
               this.addAttribute("column", pc.getMapping(path).toString());
               this.startElement("field-result");
               this.endElement("field-result");
            }

            this.endElement("entity-result");
         }

         Object[] arr$ = meta.getColumnResults();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            Object col = arr$[i$];
            this.addAttribute("name", col.toString());
            this.startElement("column-result");
            this.endElement("column-result");
         }

         this.endElement("sql-result-set-mapping");
      }
   }

   protected void serializeSequence(SequenceMetaData meta) throws SAXException {
      if (this.getSerializeAnnotations() || meta.getSourceType() != 1) {
         if ("value-table".equals(meta.getSequencePlugin())) {
            super.serializeSequence(meta);
         } else {
            SequenceMapping seq = (SequenceMapping)meta;
            this.addAttribute("name", seq.getName());
            String table = seq.getTable();
            if (table != null) {
               int dotIdx = table.indexOf(46);
               if (dotIdx == -1) {
                  this.addAttribute("table", table);
               } else {
                  this.addAttribute("table", table.substring(dotIdx + 1));
                  this.addAttribute("schema", table.substring(0, dotIdx));
               }
            }

            if (!StringUtils.isEmpty(seq.getPrimaryKeyColumn())) {
               this.addAttribute("pk-column-name", seq.getPrimaryKeyColumn());
            }

            if (!StringUtils.isEmpty(seq.getSequenceColumn())) {
               this.addAttribute("value-column-name", seq.getSequenceColumn());
            }

            if (!StringUtils.isEmpty(seq.getPrimaryKeyValue())) {
               this.addAttribute("pk-column-value", seq.getPrimaryKeyValue());
            }

            if (seq.getAllocate() != 50 && seq.getAllocate() != -1) {
               this.addAttribute("allocation-size", seq.getAllocate() + "");
            }

            if (seq.getInitialValue() != 0 && seq.getInitialValue() != -1) {
               this.addAttribute("initial-value", seq.getInitialValue() + "");
            }

            this.startElement("table-generator");
            this.endElement("table-generator");
         }
      }
   }

   static {
      _names.put(XMLPersistenceMappingSerializer.ColType.COL, "column");
      _names.put(XMLPersistenceMappingSerializer.ColType.JOIN, "join-column");
      _names.put(XMLPersistenceMappingSerializer.ColType.INVERSE, "inverse-join-column");
      _names.put(XMLPersistenceMappingSerializer.ColType.PK_JOIN, "primary-key-join-column");
      _names.put(XMLPersistenceMappingSerializer.ColType.DISC, "discriminator-column");
   }

   protected class MappingSerializationComparator extends XMLPersistenceMetaDataSerializer.SerializationComparator {
      protected MappingSerializationComparator() {
         super();
      }

      protected int compareUnknown(Object o1, Object o2) {
         if (!(o1 instanceof QueryResultMapping)) {
            return super.compareUnknown(o1, o2);
         } else {
            QueryResultMapping res1 = (QueryResultMapping)o1;
            QueryResultMapping res2 = (QueryResultMapping)o2;
            Object scope1 = res1.getSourceScope();
            Object scope2 = res2.getSourceScope();
            if (scope1 == null && scope2 != null) {
               return -1;
            } else if (scope1 != null && scope2 == null) {
               return 1;
            } else {
               int listingIndex1 = res1.getListingIndex();
               int listingIndex2 = res2.getListingIndex();
               return listingIndex1 != listingIndex2 ? listingIndex1 - listingIndex2 : res1.getName().compareTo(res2.getName());
            }
         }
      }
   }

   private static enum ColType {
      COL,
      JOIN,
      INVERSE,
      PK_JOIN,
      DISC;
   }
}
