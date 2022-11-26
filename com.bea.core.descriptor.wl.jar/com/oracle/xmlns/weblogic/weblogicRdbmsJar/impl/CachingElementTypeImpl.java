package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CachingElementType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CmrFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GroupNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CachingElementTypeImpl extends XmlComplexContentImpl implements CachingElementType {
   private static final long serialVersionUID = 1L;
   private static final QName CMRFIELD$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "cmr-field");
   private static final QName GROUPNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "group-name");
   private static final QName CACHINGELEMENT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "caching-element");
   private static final QName ID$6 = new QName("", "id");

   public CachingElementTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CmrFieldType getCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().find_element_user(CMRFIELD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCmrField(CmrFieldType cmrField) {
      this.generatedSetterHelperImpl(cmrField, CMRFIELD$0, 0, (short)1);
   }

   public CmrFieldType addNewCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().add_element_user(CMRFIELD$0);
         return target;
      }
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$2) != 0;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$2, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$2);
         return target;
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$2, 0);
      }
   }

   public CachingElementType[] getCachingElementArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CACHINGELEMENT$4, targetList);
         CachingElementType[] result = new CachingElementType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CachingElementType getCachingElementArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().find_element_user(CACHINGELEMENT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCachingElementArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHINGELEMENT$4);
      }
   }

   public void setCachingElementArray(CachingElementType[] cachingElementArray) {
      this.check_orphaned();
      this.arraySetterHelper(cachingElementArray, CACHINGELEMENT$4);
   }

   public void setCachingElementArray(int i, CachingElementType cachingElement) {
      this.generatedSetterHelperImpl(cachingElement, CACHINGELEMENT$4, i, (short)2);
   }

   public CachingElementType insertNewCachingElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().insert_element_user(CACHINGELEMENT$4, i);
         return target;
      }
   }

   public CachingElementType addNewCachingElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().add_element_user(CACHINGELEMENT$4);
         return target;
      }
   }

   public void removeCachingElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHINGELEMENT$4, i);
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
