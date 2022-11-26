package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebservices.ShareableType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ShareableTypeImpl extends XmlComplexContentImpl implements ShareableType {
   private static final long serialVersionUID = 1L;
   private static final QName INCLUDE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "include");
   private static final QName EXCLUDE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "exclude");
   private static final QName DIR$4 = new QName("", "dir");

   public ShareableTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDE$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getIncludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCLUDE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDE$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetIncludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INCLUDE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDE$0);
      }
   }

   public void setIncludeArray(String[] includeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(includeArray, INCLUDE$0);
      }
   }

   public void setIncludeArray(int i, String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCLUDE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(include);
         }
      }
   }

   public void xsetIncludeArray(XmlString[] includeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(includeArray, INCLUDE$0);
      }
   }

   public void xsetIncludeArray(int i, XmlString include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INCLUDE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(include);
         }
      }
   }

   public void insertInclude(int i, String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(INCLUDE$0, i);
         target.setStringValue(include);
      }
   }

   public void addInclude(String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(INCLUDE$0);
         target.setStringValue(include);
      }
   }

   public XmlString insertNewInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(INCLUDE$0, i);
         return target;
      }
   }

   public XmlString addNewInclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(INCLUDE$0);
         return target;
      }
   }

   public void removeInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDE$0, i);
      }
   }

   public String[] getExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCLUDE$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getExcludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCLUDE$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetExcludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXCLUDE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDE$2);
      }
   }

   public void setExcludeArray(String[] excludeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(excludeArray, EXCLUDE$2);
      }
   }

   public void setExcludeArray(int i, String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(exclude);
         }
      }
   }

   public void xsetExcludeArray(XmlString[] excludeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(excludeArray, EXCLUDE$2);
      }
   }

   public void xsetExcludeArray(int i, XmlString exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXCLUDE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(exclude);
         }
      }
   }

   public void insertExclude(int i, String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(EXCLUDE$2, i);
         target.setStringValue(exclude);
      }
   }

   public void addExclude(String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(EXCLUDE$2);
         target.setStringValue(exclude);
      }
   }

   public XmlString insertNewExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(EXCLUDE$2, i);
         return target;
      }
   }

   public XmlString addNewExclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(EXCLUDE$2);
         return target;
      }
   }

   public void removeExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDE$2, i);
      }
   }

   public String getDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DIR$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DIR$4);
         return target;
      }
   }

   public boolean isSetDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(DIR$4) != null;
      }
   }

   public void setDir(String dir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DIR$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(DIR$4);
         }

         target.setStringValue(dir);
      }
   }

   public void xsetDir(XmlString dir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DIR$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(DIR$4);
         }

         target.set(dir);
      }
   }

   public void unsetDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(DIR$4);
      }
   }
}
