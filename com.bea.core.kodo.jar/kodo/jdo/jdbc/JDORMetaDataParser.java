package kodo.jdo.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kodo.jdbc.meta.KodoClassMapping;
import kodo.jdbc.meta.KodoFieldMapping;
import kodo.jdbc.meta.KodoMappingRepository;
import kodo.jdbc.meta.LockGroup;
import kodo.jdbc.meta.LockGroupVersionMappingInfo;
import kodo.jdo.JDOMetaDataParser;
import org.apache.openjpa.jdbc.conf.FetchModeValue;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.SequenceMetaData;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import serp.util.Strings;

public class JDORMetaDataParser extends JDOMetaDataParser {
   private static final String JDBC = "jdbc-";
   public static final String EXT_LOCK_GROUPS = "lock-groups";
   public static final String EXT_SUBCLASS_FETCH_MODE = "jdbc-subclass-fetch-mode";
   static final String[] CLASS_EXTENSIONS = new String[]{"lock-groups", "jdbc-subclass-fetch-mode"};
   public static final String EXT_LOCK_GROUP = "lock-group";
   public static final String EXT_EAGER_FETCH_MODE = "jdbc-eager-fetch-mode";
   public static final String EXT_CLASS_CRITERIA = "class-criteria";
   public static final String EXT_NONPOLYMORPHIC = "nonpolymorphic";
   public static final String EXT_STRATEGY = "strategy";
   static final String[] FIELD_EXTENSIONS = new String[]{"lock-group", "jdbc-eager-fetch-mode", "jdbc-class-criteria", "jdbc-element-class-criteria", "jdbc-key-class-criteria", "jdbc-value-class-criteria", "jdbc-strategy", "jdbc-element-strategy", "jdbc-key-strategy", "jdbc-value-strategy", "jdbc-nonpolymorphic", "jdbc-element-nonpolymorphic", "jdbc-key-nonpolymorphic", "jdbc-value-nonpolymorphic"};
   public static final String EXT_UNINSERTABLE = "uninsertable";
   public static final String EXT_UNUPDATABLE = "unupdatable";
   private static final String[] COLUMN_EXTENSIONS = new String[]{"uninsertable", "unupdatable"};
   public static final String DOCTYPE_DEC_ORM = "<!DOCTYPE orm SYSTEM 'file:/javax/jdo/orm.dtd'>";
   private static final Localizer _loc = Localizer.forPackage(JDORMetaDataParser.class);
   private String _inher = null;
   private final List _cols = new ArrayList(3);
   private final List _lgs = new ArrayList(3);
   private final List _idxs = new ArrayList(3);
   private final Map _idxFields = new HashMap();

   public JDORMetaDataParser(JDBCConfiguration conf) {
      super(conf);
   }

   protected EntityResolver newResolver() {
      return new JDOREntityResolver();
   }

   protected Reader getDocType() throws IOException {
      Reader reader = super.getDocType();
      if (reader == null && this.isMappingMode() && !this.isMetaDataMode()) {
         reader = new StringReader("<!DOCTYPE orm SYSTEM 'file:/javax/jdo/orm.dtd'>");
      }

      return (Reader)reader;
   }

   protected void reset() {
      super.reset();
      this._cols.clear();
      this._lgs.clear();
      this._idxFields.clear();
   }

   protected void addComments(Object obj) {
      if (this.isMappingMode() && !this.isMetaDataMode()) {
         if (obj instanceof ClassMapping) {
            obj = ((ClassMapping)obj).getMappingInfo();
         } else if (obj instanceof FieldMapping) {
            obj = ((FieldMapping)obj).getMappingInfo();
         }
      }

      super.addComments(obj);
   }

   protected void startSequenceMapping(SequenceMetaData seq, Attributes attrs) throws SAXException {
      if (this.isMappingMode()) {
         ((SequenceMapping)seq).setMappingFile(this.getSourceFile());
      }

   }

   protected void startClassMapping(ClassMetaData mapping, Attributes attrs) throws SAXException {
      ClassMapping cls = (ClassMapping)mapping;
      if (this.getMappingOverride() && this.isMappingMode()) {
         cls.clearMapping();
         cls.getVersion().clearMapping();
         cls.getDiscriminator().clearMapping();
      }

      if (this.isMappingMode()) {
         cls.getMappingInfo().setSource(this.getSourceFile(), 2);
      }

      String val = attrs.getValue("table");
      if (val != null) {
         cls.getMappingInfo().setTableName(val);
      }

   }

   protected void startSuperclassFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
      this.startFieldMapping(field, attrs);
   }

   protected void endSuperclassFieldMapping(FieldMetaData field) throws SAXException {
      this.endFieldMapping(field);
   }

   protected void startFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
      if (this.currentParentCode() == 18) {
         Index idx = (Index)this._idxs.get(this._idxs.size() - 1);
         Set fields = (Set)this._idxFields.get(idx);
         if (fields == null) {
            this._idxFields.put(idx, fields = new HashSet());
         }

         ((Set)fields).add(field.getName());
      } else {
         FieldMapping fm = (FieldMapping)field;
         if (this.getMappingOverride() && this.isMappingMode()) {
            fm.clearMapping();
         }

         String val = attrs.getValue("table");
         if (val != null) {
            fm.getMappingInfo().setTableName(val);
         }

         this.setMappingAttributes(fm.getValueInfo(), attrs);
      }

   }

   protected void endFieldMapping(FieldMetaData field) {
      if (this.currentParentCode() != 18) {
         if (this._cols.isEmpty()) {
            return;
         }

         this.setColumns(((FieldMapping)field).getValueInfo(), this._cols);
      }

   }

   protected void endClassMapping(ClassMetaData mapping) throws SAXException {
      super.endClassMapping(mapping);
      Iterator i = this._idxFields.entrySet().iterator();

      label49:
      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         Index idx = (Index)entry.getKey();
         Collection values = (Collection)entry.getValue();
         Iterator j = values.iterator();

         while(true) {
            Object info;
            List cols;
            do {
               do {
                  do {
                     if (!j.hasNext()) {
                        continue label49;
                     }

                     String name = (String)j.next();
                     FieldMapping fm = ((ClassMapping)mapping).getDeclaredFieldMapping(name);
                     info = fm instanceof ValueMapping ? fm.getValueInfo() : fm.getMappingInfo();
                  } while(info == null);

                  cols = ((MappingInfo)info).getColumns();
               } while(cols == null);
            } while(cols.size() == 0);

            for(int k = 0; k < cols.size(); ++k) {
               idx.addColumn((Column)cols.get(k));
            }

            ((MappingInfo)info).setIndex(idx);
         }
      }

   }

   private void setMappingAttributes(MappingInfo info, Attributes attrs) throws SAXException {
      String val = attrs.getValue("column");
      if (val != null) {
         Column col = this.newColumn(val);
         this.setColumns(info, new ArrayList(Collections.singletonList(col)));
      }

      val = attrs.getValue("delete-action");
      if (val != null) {
         int action = this.toForeignKeyAction(val);
         if (action == 1) {
            info.setCanForeignKey(false);
         } else {
            ForeignKey fk = new ForeignKey();
            fk.setDeleteAction(action);
            info.setForeignKey(fk);
         }
      }

      val = attrs.getValue("indexed");
      if (val != null) {
         if ("false".equals(val)) {
            info.setCanIndex(false);
         } else {
            Index idx = new Index();
            if ("unique".equals(val)) {
               idx.setUnique(true);
            }

            info.setIndex(idx);
         }
      }

      val = attrs.getValue("unique");
      if (val != null) {
         if ("false".equals(val)) {
            info.setCanUnique(false);
         } else {
            info.setUnique(new Unique());
         }
      }

   }

   private int toForeignKeyAction(String name) throws SAXException {
      try {
         return ForeignKey.getAction(name);
      } catch (IllegalArgumentException var4) {
         Object context = this.currentElement();
         throw this.getException(context + ": " + var4.getMessage());
      }
   }

   protected boolean setKnownMappingExtension(int elemCode, String key, String value) {
      if (super.setKnownMappingExtension(elemCode, key, value)) {
         return true;
      } else if (elemCode != 7) {
         return false;
      } else {
         Column col = (Column)this._cols.get(this._cols.size() - 1);
         if ("uninsertable".equals(key)) {
            col.setFlag(2, !"false".equals(value));
         } else if ("unupdatable".equals(key)) {
            col.setFlag(4, !"false".equals(value));
         } else {
            if (!"lock-group".equals(key)) {
               Log log = this.getLog();
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("bad-col-extension", key, this.getSourceName(), Arrays.asList(COLUMN_EXTENSIONS)));
               }

               return false;
            }

            this._lgs.add(value);
         }

         return true;
      }
   }

   protected boolean setKnownClassExtension(ClassMetaData meta, String key, String value) throws SAXException {
      if (super.setKnownClassExtension(meta, key, value)) {
         return true;
      } else {
         ClassMapping cls = (ClassMapping)meta;
         if ("lock-groups".equals(key)) {
            String[] names = Strings.split(value, ",", 0);

            for(int i = 0; i < names.length; ++i) {
               names[i] = names[i].trim();
               if (!"none".equals(names[i])) {
                  ((KodoClassMapping)cls).addDeclaredLockGroup(((KodoMappingRepository)cls.getMappingRepository()).getLockGroup(names[i]));
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean setKnownClassMappingExtension(ClassMetaData meta, String key, String value) throws SAXException {
      if (super.setKnownClassMappingExtension(meta, key, value)) {
         return true;
      } else {
         ClassMapping cls = (ClassMapping)meta;
         if ("jdbc-subclass-fetch-mode".equals(key)) {
            FetchModeValue val = new FetchModeValue("jdbc-subclass-fetch-mode");
            val.setString(value);
            cls.setSubclassFetchMode(val.get());
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean setKnownFieldExtension(FieldMetaData field, String key, String value) throws SAXException {
      if (super.setKnownFieldExtension(field, key, value)) {
         return true;
      } else {
         FieldMapping fm = (FieldMapping)field;
         if ("lock-group".equals(key)) {
            if ("none".equals(value)) {
               ((KodoFieldMapping)fm).setLockGroup((LockGroup)null);
            } else {
               ((KodoFieldMapping)fm).setLockGroup(((KodoMappingRepository)fm.getMappingRepository()).getLockGroup(value));
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean setKnownFieldMappingExtension(FieldMetaData field, String key, String value) throws SAXException {
      if (super.setKnownFieldMappingExtension(field, key, value)) {
         return true;
      } else {
         FieldMapping fm = (FieldMapping)field;
         if ("jdbc-eager-fetch-mode".equals(key)) {
            FetchModeValue val = new FetchModeValue("jdbc-eager-fetch-mode");
            val.setString(value);
            fm.setEagerFetchMode(val.get());
         } else if ("jdbc-strategy".equals(key)) {
            fm.getMappingInfo().setStrategy(value);
         } else if ("jdbc-key-strategy".equals(key)) {
            fm.getKeyMapping().getValueInfo().setStrategy(value);
         } else if (!"jdbc-element-strategy".equals(key) && !"jdbc-value-strategy".equals(key)) {
            if ("jdbc-nonpolymorphic".equals(key)) {
               fm.setPolymorphic(this.toPolymorphicConstant(fm, value));
            } else if ("jdbc-key-nonpolymorphic".equals(key)) {
               fm.getKeyMapping().setPolymorphic(this.toPolymorphicConstant(fm, value));
            } else if (!"jdbc-element-nonpolymorphic".equals(key) && !"jdbc-value-nonpolymorphic".equals(key)) {
               if ("jdbc-class-criteria".equals(key)) {
                  fm.getValueInfo().setUseClassCriteria(!"false".equals(value));
               } else if ("jdbc-key-class-criteria".equals(key)) {
                  fm.getKeyMapping().getValueInfo().setUseClassCriteria(!"false".equals(value));
               } else {
                  if (!"jdbc-element-class-criteria".equals(key) && !"jdbc-value-class-criteria".equals(key)) {
                     return false;
                  }

                  fm.getElementMapping().getValueInfo().setUseClassCriteria(!"false".equals(value));
               }
            } else {
               fm.getElementMapping().setPolymorphic(this.toPolymorphicConstant(fm, value));
            }
         } else {
            fm.getElementMapping().getValueInfo().setStrategy(value);
         }

         return true;
      }
   }

   private int toPolymorphicConstant(FieldMapping context, String value) throws SAXException {
      if (!"exact".equals(value) && value != null && value.length() != 0) {
         if ("joinable".equals(value)) {
            return 2;
         } else if ("false".equals(value)) {
            return 0;
         } else {
            throw this.getException(_loc.get("bad-poly-extension", context, value));
         }
      } else {
         return 1;
      }
   }

   protected boolean startColumn(Attributes attrs) throws SAXException {
      Column col = this.newColumn(attrs.getValue("name"));
      String val = attrs.getValue("jdbc-type");
      if (val != null) {
         col.setType(Schemas.getJDBCType(val));
         col.setJavaType(JavaTypes.getTypeCode(Schemas.getJavaType(col.getType(), col.getSize(), col.getDecimalDigits())));
      }

      val = attrs.getValue("sql-type");
      if (val != null) {
         col.setTypeName(val);
      }

      val = attrs.getValue("allows-null");
      if (val != null) {
         col.setNotNull(!"true".equals(val));
      }

      val = attrs.getValue("target");
      if (val != null) {
         col.setTarget(val);
      }

      val = attrs.getValue("target-field");
      if (val != null) {
         col.setTargetField(val);
      }

      val = attrs.getValue("default-value");
      if (val != null) {
         col.setDefaultString(val);
      }

      try {
         val = attrs.getValue("length");
         if (val != null) {
            col.setSize(Integer.parseInt(val));
         }

         val = attrs.getValue("scale");
         if (val != null) {
            col.setDecimalDigits(Integer.parseInt(val));
         }
      } catch (NumberFormatException var5) {
         throw this.getException(_loc.get("bad-length-scale", this.currentElement(), col.getName(), val));
      }

      this._cols.add(col);
      return true;
   }

   protected void endColumn() {
      if (this.currentParentCode() == 11 && this._lgs.size() < this._cols.size()) {
         this._lgs.add("default");
      }

   }

   protected boolean startDatastoreIdentity(Attributes attrs) throws SAXException {
      if (!super.startDatastoreIdentity(attrs)) {
         return false;
      } else {
         ClassMapping cls = (ClassMapping)this.currentElement();
         this.setMappingAttributes(cls.getMappingInfo(), attrs);
         return true;
      }
   }

   protected void endDatastoreIdentity() throws SAXException {
      if (!this._cols.isEmpty()) {
         ClassMapping cls = (ClassMapping)this.currentElement();
         this.setColumns(cls.getMappingInfo(), this._cols);
      }
   }

   protected boolean startInheritance(Attributes attrs) throws SAXException {
      this._inher = attrs.getValue("strategy");
      return true;
   }

   protected void endInheritance() {
      if (this._inher != null) {
         ClassMappingInfo info = ((ClassMapping)this.currentElement()).getMappingInfo();
         if ("new-table".equals(this._inher)) {
            if (info.isJoinedSubclass()) {
               info.setStrategy("vertical");
            } else {
               info.setStrategy("full");
            }
         } else if ("superclass-table".equals(this._inher)) {
            info.setStrategy("flat");
         } else if ("subclass-table".equals(this._inher)) {
            info.setStrategy("none");
         } else {
            info.setStrategy(this._inher);
         }

      }
   }

   protected boolean startDiscriminator(Attributes attrs) throws SAXException {
      ClassMapping cls = (ClassMapping)this.currentElement();
      Discriminator discrim = cls.getDiscriminator();
      String val = attrs.getValue("strategy");
      if (val != null) {
         if ("class-name".equals(val)) {
            val = "class-name";
         } else if ("value-map".equals(val)) {
            val = "value-map";
         } else if ("final".equals(val)) {
            val = "none";
         } else if ("none".equals(val)) {
            if (!Modifier.isFinal(cls.getDescribedType().getModifiers()) && ((JDBCConfiguration)this.getConfiguration()).getDBDictionaryInstance().joinSyntax != 1) {
               val = "subclass-join";
            } else {
               val = "none";
            }
         }

         discrim.getMappingInfo().setStrategy(val);
      }

      val = attrs.getValue("value");
      if (val != null) {
         discrim.getMappingInfo().setValue(val);
      }

      this.setMappingAttributes(discrim.getMappingInfo(), attrs);
      this.pushElement(discrim);
      return true;
   }

   protected void endDiscriminator() throws SAXException {
      Discriminator discrim = (Discriminator)this.popElement();
      if (!this._cols.isEmpty()) {
         Iterator it = this._cols.iterator();
         if (it.hasNext()) {
            Column col = (Column)it.next();
            switch (col.getJavaType()) {
               case 1:
               case 5:
               case 7:
               case 17:
               case 21:
               case 23:
                  discrim.setJavaType(5);
                  break;
               case 2:
                  discrim.setJavaType(2);
                  break;
               case 3:
               case 4:
               case 6:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 18:
               case 19:
               case 20:
               case 22:
               default:
                  discrim.setJavaType(9);
            }
         }

         this.setColumns(discrim.getMappingInfo(), this._cols);
      }

   }

   protected boolean startVersion(Attributes attrs) throws SAXException {
      ClassMapping cls = (ClassMapping)this.currentElement();
      Version version = cls.getVersion();
      String val = attrs.getValue("strategy");
      if (val != null) {
         if ("version-number".equals(val)) {
            val = "version-number";
         } else if ("date-time".equals(val)) {
            val = "timestamp";
         } else if ("state-comparison".equals(val)) {
            val = "state-comparison";
         } else if ("none".equals(val)) {
            val = "none";
         }

         version.getMappingInfo().setStrategy(val);
      }

      this.setMappingAttributes(version.getMappingInfo(), attrs);
      this._lgs.clear();
      this.pushElement(version);
      return true;
   }

   protected void endVersion() throws SAXException {
      Version version = (Version)this.popElement();
      if (!this._cols.isEmpty()) {
         this.setColumns(version.getMappingInfo(), this._cols);
         ((LockGroupVersionMappingInfo)version.getMappingInfo()).setColumnLockGroupNames(new ArrayList(this._lgs));
         this._lgs.clear();
      }

   }

   protected boolean startJoin(Attributes attrs) throws SAXException {
      MappingInfo info = null;
      Object elem = this.currentElement();
      if (this.currentParentCode() == 9) {
         info = ((ClassMapping)elem).getMappingInfo();
         ((ClassMappingInfo)info).setJoinedSubclass(true);
      } else {
         FieldMappingInfo fmi = ((FieldMapping)elem).getMappingInfo();
         String outer = attrs.getValue("outer");
         if (outer != null) {
            fmi.setJoinOuter("true".equals(outer));
         }

         info = fmi;
      }

      ((MappingInfo)info).setJoinDirection(1);
      this.setMappingAttributes((MappingInfo)info, attrs);
      return true;
   }

   protected void endJoin() throws SAXException {
      if (!this._cols.isEmpty()) {
         MappingInfo info = null;
         Object elem = this.currentElement();
         if (this.currentParentCode() == 9) {
            info = ((ClassMapping)elem).getMappingInfo();
         } else {
            info = ((FieldMapping)elem).getMappingInfo();
         }

         this.setColumns((MappingInfo)info, this._cols);
      }
   }

   protected boolean startCollectionElement(Attributes attrs) throws SAXException {
      if (!super.startCollectionElement(attrs)) {
         return false;
      } else {
         ValueMapping vm = (ValueMapping)this.currentElement();
         this.setMappingAttributes(vm.getValueInfo(), attrs);
         return true;
      }
   }

   protected void endCollectionElement() throws SAXException {
      ValueMapping vm = (ValueMapping)this.currentElement();
      if (!this._cols.isEmpty()) {
         this.setColumns(vm.getValueInfo(), this._cols);
      }

      super.endCollectionElement();
   }

   protected boolean startMapKey(Attributes attrs) throws SAXException {
      if (!super.startMapKey(attrs)) {
         return false;
      } else {
         ValueMapping vm = (ValueMapping)this.currentElement();
         this.setMappingAttributes(vm.getValueInfo(), attrs);
         return true;
      }
   }

   protected void endMapKey() throws SAXException {
      ValueMapping vm = (ValueMapping)this.currentElement();
      if (!this._cols.isEmpty()) {
         this.setColumns(vm.getValueInfo(), this._cols);
      }

      super.endMapKey();
   }

   protected boolean startMapValue(Attributes attrs) throws SAXException {
      if (!super.startMapValue(attrs)) {
         return false;
      } else {
         ValueMapping vm = (ValueMapping)this.currentElement();
         this.setMappingAttributes(vm.getValueInfo(), attrs);
         return true;
      }
   }

   protected void endMapValue() throws SAXException {
      ValueMapping vm = (ValueMapping)this.currentElement();
      if (!this._cols.isEmpty()) {
         this.setColumns(vm.getValueInfo(), this._cols);
      }

      super.endMapValue();
   }

   protected boolean startOrder(Attributes attrs) throws SAXException {
      String val = attrs.getValue("column");
      if (val == null) {
         return true;
      } else {
         FieldMapping field = (FieldMapping)this.currentElement();
         if ("false".equals(val)) {
            field.getMappingInfo().setCanOrderColumn(false);
         } else {
            Column col = this.newColumn("true".equals(val) ? null : val);
            field.getMappingInfo().setOrderColumn(col);
         }

         return true;
      }
   }

   protected void endOrder() throws SAXException {
      if (!this._cols.isEmpty()) {
         FieldMapping field = (FieldMapping)this.currentElement();
         field.getMappingInfo().setOrderColumn((Column)this._cols.get(0));
         this._cols.clear();
      }
   }

   protected void startEmbeddedMapping(ClassMetaData embed, Attributes attrs) throws SAXException {
      String val = attrs.getValue("null-indicator-column");
      ValueMapping vm = ((ClassMapping)embed).getEmbeddingMapping();
      if ("false".equals(val)) {
         vm.getValueInfo().setCanIndicateNull(false);
      } else if (val != null) {
         Column col = this.newColumn("true".equals(val) ? null : val);
         this.setColumns(vm.getValueInfo(), new ArrayList(Collections.singletonList(col)));
      }

   }

   protected boolean startIndex(Attributes attrs) throws SAXException {
      Index idx = new Index();
      String val = attrs.getValue("name");
      if (val != null) {
         idx.setName(val);
      }

      val = attrs.getValue("unique");
      if (val != null) {
         idx.setUnique("true".equals(val));
      }

      this._idxs.add(idx);
      return true;
   }

   protected void endIndex() throws SAXException {
      Object elem = this.currentElement();
      Index idx = (Index)this._idxs.get(this._idxs.size() - 1);
      if (this.currentParentCode() == 0) {
         if (this._cols.size() > 0) {
            idx.setColumns((Column[])((Column[])this._cols.toArray(new Column[this._cols.size()])));
            this._cols.clear();
         }
      } else {
         this.currentConstraintInfo().setIndex(idx);
         this._idxs.remove(this._idxs.size() - 1);
      }

   }

   protected Column newColumn(String name) {
      Column col = new Column();
      if (name != null) {
         col.setName(name);
      }

      return col;
   }

   private void setColumns(MappingInfo info, List cols) {
      if (cols != null && cols.size() != 0) {
         info.setColumns(new ArrayList(cols));
         Iterator i = this._idxs.iterator();

         while(i.hasNext()) {
            Index idx = (Index)i.next();

            for(int j = 0; cols != null && j < cols.size(); ++j) {
               Column col = (Column)cols.get(j);
               Column[] idxCols = idx.getColumns();

               for(int k = 0; idxCols != null && k < idxCols.length; ++k) {
                  if (col != null && col.getName() != null && idxCols[k] != null && col.getName().equals(idxCols[k].getName())) {
                     i.remove();
                     info.setIndex(idx);
                     break;
                  }
               }
            }
         }

         cols.clear();
      }
   }

   protected boolean startForeignKey(Attributes attrs) throws SAXException {
      ForeignKey fk = new ForeignKey();
      String val = attrs.getValue("name");
      if (val != null) {
         fk.setName(val);
      }

      val = attrs.getValue("deferred");
      if (val != null) {
         fk.setDeferred("true".equals(val));
      }

      val = attrs.getValue("delete-action");
      int action = val == null ? 2 : this.toForeignKeyAction(val);
      fk.setDeleteAction(action);
      val = attrs.getValue("update-action");
      action = val == null ? 2 : this.toForeignKeyAction(val);
      fk.setUpdateAction(action);
      this.currentConstraintInfo().setForeignKey(fk);
      return true;
   }

   protected boolean startUnique(Attributes attrs) throws SAXException {
      Unique unq = new Unique();
      String val = attrs.getValue("name");
      if (val != null) {
         unq.setName(val);
      }

      val = attrs.getValue("deferred");
      if (val != null) {
         unq.setDeferred("true".equals(val));
      }

      this.currentConstraintInfo().setUnique(unq);
      return true;
   }

   private MappingInfo currentConstraintInfo() {
      Object elem = this.currentElement();
      switch (this.currentParentCode()) {
         case 0:
            return ((ClassMapping)elem).getMappingInfo();
         case 10:
            return ((Discriminator)elem).getMappingInfo();
         case 11:
            return ((Version)elem).getMappingInfo();
         case 12:
            if (elem instanceof ClassMapping) {
               return ((ClassMapping)elem).getMappingInfo();
            }

            return ((FieldMapping)elem).getMappingInfo();
         default:
            return ((ValueMapping)elem).getValueInfo();
      }
   }
}
