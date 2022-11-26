package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CmpFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DbmsColumnType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DbmsColumnTypeType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.FieldMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GroupNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import javax.xml.namespace.QName;

public class FieldMapTypeImpl extends XmlComplexContentImpl implements FieldMapType {
   private static final long serialVersionUID = 1L;
   private static final QName CMPFIELD$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "cmp-field");
   private static final QName DBMSCOLUMN$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "dbms-column");
   private static final QName DBMSCOLUMNTYPE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "dbms-column-type");
   private static final QName DBMSDEFAULTVALUE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "dbms-default-value");
   private static final QName GROUPNAME$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "group-name");
   private static final QName ID$10 = new QName("", "id");

   public FieldMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CmpFieldType getCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CMPFIELD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCmpField(CmpFieldType cmpField) {
      this.generatedSetterHelperImpl(cmpField, CMPFIELD$0, 0, (short)1);
   }

   public CmpFieldType addNewCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CMPFIELD$0);
         return target;
      }
   }

   public DbmsColumnType getDbmsColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().find_element_user(DBMSCOLUMN$2, 0);
         return target == null ? null : target;
      }
   }

   public void setDbmsColumn(DbmsColumnType dbmsColumn) {
      this.generatedSetterHelperImpl(dbmsColumn, DBMSCOLUMN$2, 0, (short)1);
   }

   public DbmsColumnType addNewDbmsColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnType target = null;
         target = (DbmsColumnType)this.get_store().add_element_user(DBMSCOLUMN$2);
         return target;
      }
   }

   public DbmsColumnTypeType getDbmsColumnType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnTypeType target = null;
         target = (DbmsColumnTypeType)this.get_store().find_element_user(DBMSCOLUMNTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDbmsColumnType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DBMSCOLUMNTYPE$4) != 0;
      }
   }

   public void setDbmsColumnType(DbmsColumnTypeType dbmsColumnType) {
      this.generatedSetterHelperImpl(dbmsColumnType, DBMSCOLUMNTYPE$4, 0, (short)1);
   }

   public DbmsColumnTypeType addNewDbmsColumnType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DbmsColumnTypeType target = null;
         target = (DbmsColumnTypeType)this.get_store().add_element_user(DBMSCOLUMNTYPE$4);
         return target;
      }
   }

   public void unsetDbmsColumnType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DBMSCOLUMNTYPE$4, 0);
      }
   }

   public TrueFalseType getDbmsDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DBMSDEFAULTVALUE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDbmsDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DBMSDEFAULTVALUE$6) != 0;
      }
   }

   public void setDbmsDefaultValue(TrueFalseType dbmsDefaultValue) {
      this.generatedSetterHelperImpl(dbmsDefaultValue, DBMSDEFAULTVALUE$6, 0, (short)1);
   }

   public TrueFalseType addNewDbmsDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DBMSDEFAULTVALUE$6);
         return target;
      }
   }

   public void unsetDbmsDefaultValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DBMSDEFAULTVALUE$6, 0);
      }
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$8) != 0;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$8, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$8);
         return target;
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
