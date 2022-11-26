package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableJdorMappingFactoryType;
import javax.xml.namespace.QName;

public class TableJdorMappingFactoryTypeImpl extends MappingFactoryTypeImpl implements TableJdorMappingFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName USESCHEMAVALIDATION$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-schema-validation");
   private static final QName TYPECOLUMN$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "type-column");
   private static final QName CONSTRAINTNAMES$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "constraint-names");
   private static final QName TABLE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table");
   private static final QName TYPES$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "types");
   private static final QName STOREMODE$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-mode");
   private static final QName MAPPINGCOLUMN$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mapping-column");
   private static final QName STRICT$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "strict");
   private static final QName NAMECOLUMN$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "name-column");

   public TableJdorMappingFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getUseSchemaValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESCHEMAVALIDATION$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSchemaValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESCHEMAVALIDATION$0, 0);
         return target;
      }
   }

   public boolean isSetUseSchemaValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESCHEMAVALIDATION$0) != 0;
      }
   }

   public void setUseSchemaValidation(boolean useSchemaValidation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESCHEMAVALIDATION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESCHEMAVALIDATION$0);
         }

         target.setBooleanValue(useSchemaValidation);
      }
   }

   public void xsetUseSchemaValidation(XmlBoolean useSchemaValidation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESCHEMAVALIDATION$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESCHEMAVALIDATION$0);
         }

         target.set(useSchemaValidation);
      }
   }

   public void unsetUseSchemaValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESCHEMAVALIDATION$0, 0);
      }
   }

   public String getTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         return target;
      }
   }

   public boolean isNilTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TYPECOLUMN$2) != 0;
      }
   }

   public void setTypeColumn(String typeColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPECOLUMN$2);
         }

         target.setStringValue(typeColumn);
      }
   }

   public void xsetTypeColumn(XmlString typeColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPECOLUMN$2);
         }

         target.set(typeColumn);
      }
   }

   public void setNilTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPECOLUMN$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPECOLUMN$2);
         }

         target.setNil();
      }
   }

   public void unsetTypeColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TYPECOLUMN$2, 0);
      }
   }

   public boolean getConstraintNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTNAMES$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetConstraintNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSTRAINTNAMES$4, 0);
         return target;
      }
   }

   public boolean isSetConstraintNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRAINTNAMES$4) != 0;
      }
   }

   public void setConstraintNames(boolean constraintNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTNAMES$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSTRAINTNAMES$4);
         }

         target.setBooleanValue(constraintNames);
      }
   }

   public void xsetConstraintNames(XmlBoolean constraintNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSTRAINTNAMES$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CONSTRAINTNAMES$4);
         }

         target.set(constraintNames);
      }
   }

   public void unsetConstraintNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRAINTNAMES$4, 0);
      }
   }

   public String getTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         return target;
      }
   }

   public boolean isNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLE$6) != 0;
      }
   }

   public void setTable(String table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLE$6);
         }

         target.setStringValue(table);
      }
   }

   public void xsetTable(XmlString table) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$6);
         }

         target.set(table);
      }
   }

   public void setNilTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLE$6);
         }

         target.setNil();
      }
   }

   public void unsetTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLE$6, 0);
      }
   }

   public String getTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPES$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$8, 0);
         return target;
      }
   }

   public boolean isNilTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TYPES$8) != 0;
      }
   }

   public void setTypes(String types) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPES$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPES$8);
         }

         target.setStringValue(types);
      }
   }

   public void xsetTypes(XmlString types) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPES$8);
         }

         target.set(types);
      }
   }

   public void setNilTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPES$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPES$8);
         }

         target.setNil();
      }
   }

   public void unsetTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TYPES$8, 0);
      }
   }

   public int getStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREMODE$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STOREMODE$10, 0);
         return target;
      }
   }

   public boolean isSetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREMODE$10) != 0;
      }
   }

   public void setStoreMode(int storeMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREMODE$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STOREMODE$10);
         }

         target.setIntValue(storeMode);
      }
   }

   public void xsetStoreMode(XmlInt storeMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STOREMODE$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(STOREMODE$10);
         }

         target.set(storeMode);
      }
   }

   public void unsetStoreMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREMODE$10, 0);
      }
   }

   public String getMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         return target;
      }
   }

   public boolean isNilMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPINGCOLUMN$12) != 0;
      }
   }

   public void setMappingColumn(String mappingColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAPPINGCOLUMN$12);
         }

         target.setStringValue(mappingColumn);
      }
   }

   public void xsetMappingColumn(XmlString mappingColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPINGCOLUMN$12);
         }

         target.set(mappingColumn);
      }
   }

   public void setNilMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGCOLUMN$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPINGCOLUMN$12);
         }

         target.setNil();
      }
   }

   public void unsetMappingColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPINGCOLUMN$12, 0);
      }
   }

   public boolean getStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICT$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICT$14, 0);
         return target;
      }
   }

   public boolean isSetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRICT$14) != 0;
      }
   }

   public void setStrict(boolean strict) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICT$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STRICT$14);
         }

         target.setBooleanValue(strict);
      }
   }

   public void xsetStrict(XmlBoolean strict) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICT$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STRICT$14);
         }

         target.set(strict);
      }
   }

   public void unsetStrict() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRICT$14, 0);
      }
   }

   public String getNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         return target;
      }
   }

   public boolean isNilNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAMECOLUMN$16) != 0;
      }
   }

   public void setNameColumn(String nameColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAMECOLUMN$16);
         }

         target.setStringValue(nameColumn);
      }
   }

   public void xsetNameColumn(XmlString nameColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMECOLUMN$16);
         }

         target.set(nameColumn);
      }
   }

   public void setNilNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMECOLUMN$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAMECOLUMN$16);
         }

         target.setNil();
      }
   }

   public void unsetNameColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAMECOLUMN$16, 0);
      }
   }
}
