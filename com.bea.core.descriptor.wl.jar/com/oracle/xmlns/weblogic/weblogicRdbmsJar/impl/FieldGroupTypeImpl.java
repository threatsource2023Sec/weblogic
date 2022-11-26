package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CmpFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CmrFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.FieldGroupType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GroupNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class FieldGroupTypeImpl extends XmlComplexContentImpl implements FieldGroupType {
   private static final long serialVersionUID = 1L;
   private static final QName GROUPNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "group-name");
   private static final QName CMPFIELD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "cmp-field");
   private static final QName CMRFIELD$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "cmr-field");
   private static final QName ID$6 = new QName("", "id");

   public FieldGroupTypeImpl(SchemaType sType) {
      super(sType);
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$0, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$0);
         return target;
      }
   }

   public CmpFieldType[] getCmpFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CMPFIELD$2, targetList);
         CmpFieldType[] result = new CmpFieldType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CmpFieldType getCmpFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CMPFIELD$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCmpFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMPFIELD$2);
      }
   }

   public void setCmpFieldArray(CmpFieldType[] cmpFieldArray) {
      this.check_orphaned();
      this.arraySetterHelper(cmpFieldArray, CMPFIELD$2);
   }

   public void setCmpFieldArray(int i, CmpFieldType cmpField) {
      this.generatedSetterHelperImpl(cmpField, CMPFIELD$2, i, (short)2);
   }

   public CmpFieldType insertNewCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().insert_element_user(CMPFIELD$2, i);
         return target;
      }
   }

   public CmpFieldType addNewCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CMPFIELD$2);
         return target;
      }
   }

   public void removeCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMPFIELD$2, i);
      }
   }

   public CmrFieldType[] getCmrFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CMRFIELD$4, targetList);
         CmrFieldType[] result = new CmrFieldType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CmrFieldType getCmrFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().find_element_user(CMRFIELD$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCmrFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMRFIELD$4);
      }
   }

   public void setCmrFieldArray(CmrFieldType[] cmrFieldArray) {
      this.check_orphaned();
      this.arraySetterHelper(cmrFieldArray, CMRFIELD$4);
   }

   public void setCmrFieldArray(int i, CmrFieldType cmrField) {
      this.generatedSetterHelperImpl(cmrField, CMRFIELD$4, i, (short)2);
   }

   public CmrFieldType insertNewCmrField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().insert_element_user(CMRFIELD$4, i);
         return target;
      }
   }

   public CmrFieldType addNewCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().add_element_user(CMRFIELD$4);
         return target;
      }
   }

   public void removeCmrField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMRFIELD$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
