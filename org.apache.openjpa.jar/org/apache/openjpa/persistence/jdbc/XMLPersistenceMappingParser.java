package org.apache.openjpa.persistence.jdbc;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.DiscriminatorType;
import javax.persistence.EnumType;
import javax.persistence.InheritanceType;
import javax.persistence.TemporalType;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.persistence.XMLPersistenceMetaDataParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLPersistenceMappingParser extends XMLPersistenceMetaDataParser {
   private static final Map _elems = new HashMap();
   private static final Localizer _loc;
   private String _override = null;
   private String _schema = null;
   private String _colTable = null;
   private String _secondaryTable = null;
   private List _cols = null;
   private List _joinCols = null;
   private List _supJoinCols = null;
   private boolean _lob = false;
   private TemporalType _temporal = null;
   private EnumSet _unique = EnumSet.noneOf(UniqueFlag.class);
   private DiscriminatorType _discType;
   private Column _discCol;
   private int _resultIdx = 0;

   public XMLPersistenceMappingParser(JDBCConfiguration conf) {
      super(conf);
   }

   protected void reset() {
      super.reset();
      this.clearColumnInfo();
      this.clearClassInfo();
      this.clearSecondaryTableInfo();
      this._override = null;
      this._schema = null;
      this._resultIdx = 0;
   }

   protected Object startSystemMappingElement(String name, Attributes attrs) throws SAXException {
      MappingTag tag = (MappingTag)_elems.get(name);
      if (tag == null) {
         return "schema".equals(name) ? name : null;
      } else {
         boolean ret;
         switch (tag) {
            case TABLE_GEN:
               ret = this.startTableGenerator(attrs);
               break;
            case SQL_RESULT_SET_MAPPING:
               ret = this.startSQLResultSetMapping(attrs);
               break;
            case ENTITY_RESULT:
               ret = this.startEntityResult(attrs);
               break;
            case FIELD_RESULT:
               ret = this.startFieldResult(attrs);
               break;
            case COLUMN_RESULT:
               ret = this.startColumnResult(attrs);
               break;
            default:
               ret = false;
         }

         return ret ? tag : null;
      }
   }

   protected void endSystemMappingElement(String name) throws SAXException {
      MappingTag tag = (MappingTag)_elems.get(name);
      if (tag == null) {
         if ("schema".equals(name)) {
            this._schema = this.currentText();
         }

      } else {
         switch (tag) {
            case SQL_RESULT_SET_MAPPING:
               this.endSQLResultSetMapping();
               break;
            case ENTITY_RESULT:
               this.endEntityResult();
         }

      }
   }

   protected Object startClassMappingElement(String name, Attributes attrs) throws SAXException {
      MappingTag tag = (MappingTag)_elems.get(name);
      if (tag == null) {
         return null;
      } else {
         boolean ret;
         switch (tag) {
            case TABLE_GEN:
               ret = this.startTableGenerator(attrs);
               break;
            case SQL_RESULT_SET_MAPPING:
               ret = this.startSQLResultSetMapping(attrs);
               break;
            case ENTITY_RESULT:
               ret = this.startEntityResult(attrs);
               break;
            case FIELD_RESULT:
               ret = this.startFieldResult(attrs);
               break;
            case COLUMN_RESULT:
               ret = this.startColumnResult(attrs);
               break;
            case TABLE:
               ret = this.startTable(attrs);
               break;
            case SECONDARY_TABLE:
               ret = this.startSecondaryTable(attrs);
               break;
            case DISCRIM_COL:
               this.parseDiscriminatorColumn(attrs);
               this._discCol = this.parseColumn(attrs);
               ret = true;
               break;
            case DISCRIM_VAL:
               ret = true;
               break;
            case INHERITANCE:
               ret = this.startInheritance(attrs);
               break;
            case ASSOC_OVERRIDE:
            case ATTR_OVERRIDE:
               ret = this.startAttributeOverride(attrs);
               break;
            case PK_JOIN_COL:
               ret = this.startPrimaryKeyJoinColumn(attrs);
               break;
            case COL:
               ret = this.startColumn(attrs);
               break;
            case JOIN_COL:
               ret = this.startJoinColumn(attrs);
               break;
            case JOIN_TABLE:
               ret = this.startJoinTable(attrs);
               break;
            case UNIQUE:
               ret = this.startUniqueConstraint(attrs);
               break;
            case TEMPORAL:
            case ENUMERATED:
               ret = true;
               break;
            case COLUMN_NAME:
               ret = true;
               break;
            default:
               ret = false;
         }

         return ret ? tag : null;
      }
   }

   protected void endClassMappingElement(String name) throws SAXException {
      MappingTag tag = (MappingTag)_elems.get(name);
      if (tag != null) {
         switch (tag) {
            case SQL_RESULT_SET_MAPPING:
               this.endSQLResultSetMapping();
               break;
            case ENTITY_RESULT:
               this.endEntityResult();
            case FIELD_RESULT:
            case COLUMN_RESULT:
            case TABLE:
            case DISCRIM_COL:
            case INHERITANCE:
            case ASSOC_OVERRIDE:
            case PK_JOIN_COL:
            case COL:
            case JOIN_COL:
            default:
               break;
            case SECONDARY_TABLE:
               this.endSecondaryTable();
               break;
            case DISCRIM_VAL:
               this.endDiscriminatorValue();
               break;
            case ATTR_OVERRIDE:
               this.endAttributeOverride();
               break;
            case JOIN_TABLE:
               this.endJoinTable();
               break;
            case UNIQUE:
               this.endUniqueConstraint();
               break;
            case TEMPORAL:
               this.endTemporal();
               break;
            case ENUMERATED:
               this.endEnumerated();
               break;
            case COLUMN_NAME:
               this.endColumnName();
         }

      }
   }

   protected void startClassMapping(ClassMetaData meta, boolean mappedSuper, Attributes attrs) throws SAXException {
      if (mappedSuper) {
         ((ClassMapping)meta).getMappingInfo().setStrategy("none");
      }

   }

   protected void endClassMapping(ClassMetaData meta) throws SAXException {
      ClassMapping cm = (ClassMapping)meta;
      if (this._schema != null) {
         cm.getMappingInfo().setSchemaName(this._schema);
      }

      if (this._supJoinCols != null) {
         cm.getMappingInfo().setColumns(this._supJoinCols);
      }

      if (this._discCol != null) {
         DiscriminatorMappingInfo dinfo = cm.getDiscriminator().getMappingInfo();
         switch (this._discType) {
            case CHAR:
               this._discCol.setJavaType(2);
               cm.getDiscriminator().setJavaType(2);
               break;
            case INTEGER:
               this._discCol.setJavaType(5);
               cm.getDiscriminator().setJavaType(5);
               break;
            default:
               this._discCol.setJavaType(9);
               cm.getDiscriminator().setJavaType(9);
         }

         dinfo.setColumns(Arrays.asList(this._discCol));
      }

      this.clearClassInfo();
   }

   private void clearClassInfo() {
      this._supJoinCols = null;
      this._discCol = null;
      this._discType = null;
   }

   private boolean startSecondaryTable(Attributes attrs) throws SAXException {
      this._secondaryTable = this.toTableName(attrs.getValue("schema"), attrs.getValue("name"));
      return true;
   }

   private void endSecondaryTable() {
      ClassMapping cm = (ClassMapping)this.currentElement();
      ClassMappingInfo info = cm.getMappingInfo();
      info.setSecondaryTableJoinColumns(this._secondaryTable, this._joinCols);
      this.clearSecondaryTableInfo();
   }

   private void clearSecondaryTableInfo() {
      this._joinCols = null;
      this._secondaryTable = null;
   }

   private boolean startTableGenerator(Attributes attrs) {
      String name = attrs.getValue("name");
      Log log = this.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("parse-gen", (Object)name));
      }

      if (this.getRepository().getCachedSequenceMetaData(name) != null && log.isWarnEnabled()) {
         log.warn(_loc.get("override-gen", (Object)name));
      }

      SequenceMapping seq = (SequenceMapping)this.getRepository().addSequenceMetaData(name);
      seq.setSequencePlugin("value-table");
      seq.setTable(this.toTableName(attrs.getValue("schema"), attrs.getValue("table")));
      seq.setPrimaryKeyColumn(attrs.getValue("pk-column-name"));
      seq.setSequenceColumn(attrs.getValue("value-column-name"));
      seq.setPrimaryKeyValue(attrs.getValue("pk-column-value"));
      String val = attrs.getValue("initial-value");
      if (val != null) {
         seq.setInitialValue(Integer.parseInt(val));
      }

      val = attrs.getValue("allocation-size");
      if (val != null) {
         seq.setAllocate(Integer.parseInt(val));
      }

      Object cur = this.currentElement();
      Object scope = cur instanceof ClassMetaData ? ((ClassMetaData)cur).getDescribedType() : null;
      seq.setSource(this.getSourceFile(), scope, 2);
      return true;
   }

   private boolean startInheritance(Attributes attrs) {
      String val = attrs.getValue("strategy");
      if (val == null) {
         return true;
      } else {
         ClassMapping cm = (ClassMapping)this.currentElement();
         ClassMappingInfo info = cm.getMappingInfo();
         switch ((InheritanceType)Enum.valueOf(InheritanceType.class, val)) {
            case SINGLE_TABLE:
               info.setHierarchyStrategy("flat");
               break;
            case JOINED:
               info.setHierarchyStrategy("vertical");
               break;
            case TABLE_PER_CLASS:
               info.setHierarchyStrategy("full");
         }

         return true;
      }
   }

   private void endDiscriminatorValue() {
      String val = this.currentText();
      if (!StringUtils.isEmpty(val)) {
         ClassMapping cm = (ClassMapping)this.currentElement();
         cm.getDiscriminator().getMappingInfo().setValue(val);
         if (Modifier.isAbstract(cm.getDescribedType().getModifiers()) && this.getLog().isInfoEnabled()) {
            this.getLog().info(_loc.get("discriminator-on-abstract-class", (Object)cm.getDescribedType().getName()));
         }

      }
   }

   private void endTemporal() {
      String temp = this.currentText();
      if (!StringUtils.isEmpty(temp)) {
         this._temporal = (TemporalType)Enum.valueOf(TemporalType.class, temp);
      }

   }

   private void endEnumerated() {
      String text = this.currentText();
      if (!StringUtils.isEmpty(text)) {
         EnumType type = (EnumType)Enum.valueOf(EnumType.class, text);
         FieldMapping fm = (FieldMapping)this.currentElement();
         String strat = EnumValueHandler.class.getName() + "(StoreOrdinal=" + (type == EnumType.ORDINAL) + ")";
         fm.getValueInfo().setStrategy(strat);
      }
   }

   protected boolean startLob(Attributes attrs) throws SAXException {
      if (super.startLob(attrs)) {
         this._lob = true;
         return true;
      } else {
         return false;
      }
   }

   protected void startFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
      super.startFieldMapping(field, attrs);
      if (this.getAnnotationParser() != null) {
         FieldMapping fm = (FieldMapping)field;
         fm.getMappingInfo().clear();
         fm.getValueInfo().clear();
         fm.getElementMapping().getValueInfo().clear();
         fm.getKeyMapping().getValueInfo().clear();
      }

   }

   protected void endFieldMapping(FieldMetaData field) throws SAXException {
      FieldMapping fm = (FieldMapping)field;
      if (this._lob || this._temporal != null) {
         if (this._cols == null) {
            this._cols = new ArrayList(1);
            this._cols.add(new Column());
         }

         Iterator i$ = this._cols.iterator();

         label70:
         while(true) {
            while(true) {
               if (!i$.hasNext()) {
                  break label70;
               }

               Column col = (Column)i$.next();
               if (this._lob && (fm.getDeclaredTypeCode() == 9 || fm.getDeclaredType() == char[].class || fm.getDeclaredType() == Character[].class)) {
                  col.setSize(-1);
                  col.setType(2005);
               } else if (this._lob) {
                  col.setType(2004);
               } else {
                  switch (this._temporal) {
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
         }
      }

      if (this._cols != null) {
         switch (fm.getDeclaredTypeCode()) {
            case 11:
               Class type = fm.getDeclaredType();
               if (type == byte[].class || type == Byte[].class || type == char[].class || type == Character[].class) {
                  fm.getValueInfo().setColumns(this._cols);
                  break;
               }
            case 12:
            case 13:
               fm.getElementMapping().getValueInfo().setColumns(this._cols);
               break;
            default:
               fm.getValueInfo().setColumns(this._cols);
         }

         if (this._colTable != null) {
            fm.getMappingInfo().setTableName(this._colTable);
         }

         this.setUnique(fm);
      }

      this.clearColumnInfo();
   }

   private void setUnique(FieldMapping fm) {
      if (this._unique.size() == 2) {
         this.getLog().warn(_loc.get("inconsist-col-attrs", (Object)fm));
      } else if (this._unique.contains(XMLPersistenceMappingParser.UniqueFlag.TRUE)) {
         fm.getValueInfo().setUnique(new org.apache.openjpa.jdbc.schema.Unique());
      }

   }

   private void clearColumnInfo() {
      this._cols = null;
      this._joinCols = null;
      this._colTable = null;
      this._lob = false;
      this._temporal = null;
      this._unique.clear();
   }

   private boolean startAttributeOverride(Attributes attr) {
      this._override = attr.getValue("name");
      return true;
   }

   private void endAttributeOverride() throws SAXException {
      Object elem = this.currentElement();
      FieldMapping fm;
      if (elem instanceof ClassMapping) {
         fm = this.getAttributeOverride((ClassMapping)elem);
      } else {
         fm = this.getAttributeOverride((FieldMapping)elem);
      }

      if (this._cols != null) {
         fm.getValueInfo().setColumns(this._cols);
         if (this._colTable != null) {
            fm.getMappingInfo().setTableName(this._colTable);
         }

         this.setUnique(fm);
      }

      this.clearColumnInfo();
      this._override = null;
   }

   private FieldMapping getAttributeOverride(ClassMapping cm) {
      FieldMapping sup = (FieldMapping)cm.getDefinedSuperclassField(this._override);
      if (sup == null) {
         sup = (FieldMapping)cm.addDefinedSuperclassField(this._override, Object.class, Object.class);
      }

      return sup;
   }

   private FieldMapping getAttributeOverride(FieldMapping fm) throws SAXException {
      ClassMapping embed = fm.getEmbeddedMapping();
      if (embed == null) {
         throw this.getException(_loc.get("not-embedded", (Object)fm));
      } else {
         FieldMapping efm = embed.getFieldMapping(this._override);
         if (efm == null) {
            throw this.getException(_loc.get("embed-override-name", fm, this._override));
         } else {
            return efm;
         }
      }
   }

   private boolean startTable(Attributes attrs) throws SAXException {
      String table = this.toTableName(attrs.getValue("schema"), attrs.getValue("name"));
      if (table != null) {
         ((ClassMapping)this.currentElement()).getMappingInfo().setTableName(table);
      }

      return true;
   }

   private boolean startJoinTable(Attributes attrs) throws SAXException {
      String table = this.toTableName(attrs.getValue("schema"), attrs.getValue("name"));
      if (table != null) {
         ((FieldMapping)this.currentElement()).getMappingInfo().setTableName(table);
      }

      return true;
   }

   private void endJoinTable() {
      FieldMapping fm = (FieldMapping)this.currentElement();
      if (this._joinCols != null) {
         fm.getMappingInfo().setColumns(this._joinCols);
      }

      if (this._cols != null) {
         fm.getElementMapping().getValueInfo().setColumns(this._cols);
      }

      this.clearColumnInfo();
   }

   private boolean startPrimaryKeyJoinColumn(Attributes attrs) throws SAXException {
      Column col = this.parseColumn(attrs);
      col.setFlag(128, true);
      if (this.currentElement() instanceof FieldMapping) {
         if (this._cols == null) {
            this._cols = new ArrayList(3);
         }

         this._cols.add(col);
      } else if (this.currentParent() == MappingTag.SECONDARY_TABLE) {
         if (this._joinCols == null) {
            this._joinCols = new ArrayList(3);
         }

         this._joinCols.add(col);
      } else {
         if (this._supJoinCols == null) {
            this._supJoinCols = new ArrayList(3);
         }

         this._supJoinCols.add(col);
      }

      return true;
   }

   private boolean startJoinColumn(Attributes attrs) throws SAXException {
      if (this.currentParent() != MappingTag.JOIN_TABLE) {
         return this.startColumn(attrs);
      } else {
         if (this._joinCols == null) {
            this._joinCols = new ArrayList(3);
         }

         this._joinCols.add(this.parseColumn(attrs));
         return true;
      }
   }

   private boolean startColumn(Attributes attrs) throws SAXException {
      if (this._cols == null) {
         this._cols = new ArrayList(3);
      }

      this._cols.add(this.parseColumn(attrs));
      return true;
   }

   private Column parseColumn(Attributes attrs) throws SAXException {
      Column col = new Column();
      String val = attrs.getValue("name");
      if (val != null) {
         col.setName(val);
      }

      val = attrs.getValue("referenced-column-name");
      if (val != null) {
         col.setTarget(val);
      }

      val = attrs.getValue("column-definition");
      if (val != null) {
         col.setTypeName(val);
      }

      val = attrs.getValue("precision");
      if (val != null) {
         col.setSize(Integer.parseInt(val));
      }

      val = attrs.getValue("length");
      if (val != null) {
         col.setSize(Integer.parseInt(val));
      }

      val = attrs.getValue("scale");
      if (val != null) {
         col.setDecimalDigits(Integer.parseInt(val));
      }

      val = attrs.getValue("nullable");
      if (val != null) {
         col.setNotNull("false".equals(val));
      }

      val = attrs.getValue("insertable");
      if (val != null) {
         col.setFlag(2, "false".equals(val));
      }

      val = attrs.getValue("updatable");
      if (val != null) {
         col.setFlag(4, "false".equals(val));
      }

      val = attrs.getValue("unique");
      if (val != null) {
         this._unique.add(Enum.valueOf(UniqueFlag.class, val.toUpperCase()));
      }

      val = attrs.getValue("table");
      if (val != null) {
         if (this._colTable != null && !this._colTable.equals(val)) {
            throw this.getException(_loc.get("second-inconsist", this.currentElement()));
         }

         this._colTable = val;
      }

      return col;
   }

   private String toTableName(String schema, String table) {
      if (StringUtils.isEmpty(table)) {
         return null;
      } else {
         if (StringUtils.isEmpty(schema)) {
            schema = this._schema;
         }

         return StringUtils.isEmpty(schema) ? table : schema + "." + table;
      }
   }

   private boolean startSQLResultSetMapping(Attributes attrs) {
      String name = attrs.getValue("name");
      Log log = this.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("parse-sqlrsmapping", (Object)name));
      }

      MappingRepository repos = (MappingRepository)this.getRepository();
      QueryResultMapping result = repos.getCachedQueryResultMapping((Class)null, name);
      if (result != null && log.isWarnEnabled()) {
         log.warn(_loc.get("override-sqlrsmapping", name, this.currentLocation()));
      }

      result = repos.addQueryResultMapping((Class)null, name);
      result.setListingIndex(this._resultIdx++);
      this.addComments(result);
      Object cur = this.currentElement();
      Object scope = cur instanceof ClassMetaData ? ((ClassMetaData)cur).getDescribedType() : null;
      result.setSource(this.getSourceFile(), scope, 2);
      this.pushElement(result);
      return true;
   }

   private void endSQLResultSetMapping() throws SAXException {
      this.popElement();
   }

   private boolean startEntityResult(Attributes attrs) throws SAXException {
      Class entityClass = this.classForName(attrs.getValue("entity-class"));
      String discriminator = attrs.getValue("discriminator-column");
      QueryResultMapping parent = (QueryResultMapping)this.currentElement();
      QueryResultMapping.PCResult result = parent.addPCResult(entityClass);
      if (!StringUtils.isEmpty(discriminator)) {
         result.addMapping("<discriminator>", discriminator);
      }

      this.pushElement(result);
      return true;
   }

   private void endEntityResult() throws SAXException {
      this.popElement();
   }

   private boolean startFieldResult(Attributes attrs) throws SAXException {
      String fieldName = attrs.getValue("name");
      String columnName = attrs.getValue("column");
      QueryResultMapping.PCResult parent = (QueryResultMapping.PCResult)this.currentElement();
      parent.addMapping(fieldName, columnName);
      return true;
   }

   private boolean startColumnResult(Attributes attrs) throws SAXException {
      QueryResultMapping parent = (QueryResultMapping)this.currentElement();
      parent.addColumnResult(attrs.getValue("name"));
      return true;
   }

   private boolean startUniqueConstraint(Attributes attrs) throws SAXException {
      Object current = this.currentElement();
      if (current instanceof ClassMapping && this._secondaryTable == null) {
         org.apache.openjpa.jdbc.schema.Unique unique = new org.apache.openjpa.jdbc.schema.Unique();
         this.pushElement(unique);
         return true;
      } else {
         return false;
      }
   }

   private void endUniqueConstraint() {
      org.apache.openjpa.jdbc.schema.Unique unique = (org.apache.openjpa.jdbc.schema.Unique)this.popElement();
      Object current = this.currentElement();
      if (current instanceof ClassMapping && this._secondaryTable == null) {
         ((ClassMapping)current).getMappingInfo().addUnique(unique);
      }

   }

   private boolean endColumnName() {
      Object current = this.currentElement();
      if (current instanceof org.apache.openjpa.jdbc.schema.Unique) {
         org.apache.openjpa.jdbc.schema.Unique unique = (org.apache.openjpa.jdbc.schema.Unique)current;
         Column column = new Column();
         column.setName(this.currentText());
         unique.addColumn(column);
         return true;
      } else {
         return false;
      }
   }

   private void parseDiscriminatorColumn(Attributes attrs) {
      String val = attrs.getValue("discriminator-type");
      if (val != null) {
         this._discType = (DiscriminatorType)Enum.valueOf(DiscriminatorType.class, val);
      } else {
         this._discType = DiscriminatorType.STRING;
      }

   }

   static {
      _elems.put("association-override", MappingTag.ASSOC_OVERRIDE);
      _elems.put("attribute-override", MappingTag.ATTR_OVERRIDE);
      _elems.put("column", MappingTag.COL);
      _elems.put("column-name", MappingTag.COLUMN_NAME);
      _elems.put("column-result", MappingTag.COLUMN_RESULT);
      _elems.put("discriminator-column", MappingTag.DISCRIM_COL);
      _elems.put("discriminator-value", MappingTag.DISCRIM_VAL);
      _elems.put("entity-result", MappingTag.ENTITY_RESULT);
      _elems.put("enumerated", MappingTag.ENUMERATED);
      _elems.put("field-result", MappingTag.FIELD_RESULT);
      _elems.put("inheritance", MappingTag.INHERITANCE);
      _elems.put("join-column", MappingTag.JOIN_COL);
      _elems.put("inverse-join-column", MappingTag.COL);
      _elems.put("join-table", MappingTag.JOIN_TABLE);
      _elems.put("primary-key-join-column", MappingTag.PK_JOIN_COL);
      _elems.put("secondary-table", MappingTag.SECONDARY_TABLE);
      _elems.put("sql-result-set-mapping", MappingTag.SQL_RESULT_SET_MAPPING);
      _elems.put("table", MappingTag.TABLE);
      _elems.put("table-generator", MappingTag.TABLE_GEN);
      _elems.put("temporal", MappingTag.TEMPORAL);
      _elems.put("unique-constraint", MappingTag.UNIQUE);
      _loc = Localizer.forPackage(XMLPersistenceMappingParser.class);
   }

   private static enum UniqueFlag {
      TRUE,
      FALSE;
   }
}
