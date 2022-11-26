package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CachingElementType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CachingNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationshipCachingType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RelationshipCachingTypeImpl extends XmlComplexContentImpl implements RelationshipCachingType {
   private static final long serialVersionUID = 1L;
   private static final QName CACHINGNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "caching-name");
   private static final QName CACHINGELEMENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "caching-element");
   private static final QName ID$4 = new QName("", "id");

   public RelationshipCachingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CachingNameType getCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingNameType target = null;
         target = (CachingNameType)this.get_store().find_element_user(CACHINGNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCachingName(CachingNameType cachingName) {
      this.generatedSetterHelperImpl(cachingName, CACHINGNAME$0, 0, (short)1);
   }

   public CachingNameType addNewCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingNameType target = null;
         target = (CachingNameType)this.get_store().add_element_user(CACHINGNAME$0);
         return target;
      }
   }

   public CachingElementType[] getCachingElementArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CACHINGELEMENT$2, targetList);
         CachingElementType[] result = new CachingElementType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CachingElementType getCachingElementArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().find_element_user(CACHINGELEMENT$2, i);
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
         return this.get_store().count_elements(CACHINGELEMENT$2);
      }
   }

   public void setCachingElementArray(CachingElementType[] cachingElementArray) {
      this.check_orphaned();
      this.arraySetterHelper(cachingElementArray, CACHINGELEMENT$2);
   }

   public void setCachingElementArray(int i, CachingElementType cachingElement) {
      this.generatedSetterHelperImpl(cachingElement, CACHINGELEMENT$2, i, (short)2);
   }

   public CachingElementType insertNewCachingElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().insert_element_user(CACHINGELEMENT$2, i);
         return target;
      }
   }

   public CachingElementType addNewCachingElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingElementType target = null;
         target = (CachingElementType)this.get_store().add_element_user(CACHINGELEMENT$2);
         return target;
      }
   }

   public void removeCachingElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHINGELEMENT$2, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
