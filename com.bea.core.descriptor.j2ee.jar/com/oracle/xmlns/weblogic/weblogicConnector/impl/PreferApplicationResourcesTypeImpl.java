package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicConnector.PreferApplicationResourcesType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PreferApplicationResourcesTypeImpl extends XmlComplexContentImpl implements PreferApplicationResourcesType {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "resource-name");

   public PreferApplicationResourcesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getResourceNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCENAME$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getResourceNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetResourceNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCENAME$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetResourceNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCENAME$0);
      }
   }

   public void setResourceNameArray(String[] resourceNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(resourceNameArray, RESOURCENAME$0);
      }
   }

   public void setResourceNameArray(int i, String resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(resourceName);
         }
      }
   }

   public void xsetResourceNameArray(XmlString[] resourceNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(resourceNameArray, RESOURCENAME$0);
      }
   }

   public void xsetResourceNameArray(int i, XmlString resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(resourceName);
         }
      }
   }

   public void insertResourceName(int i, String resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(RESOURCENAME$0, i);
         target.setStringValue(resourceName);
      }
   }

   public void addResourceName(String resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(RESOURCENAME$0);
         target.setStringValue(resourceName);
      }
   }

   public XmlString insertNewResourceName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(RESOURCENAME$0, i);
         return target;
      }
   }

   public XmlString addNewResourceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(RESOURCENAME$0);
         return target;
      }
   }

   public void removeResourceName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCENAME$0, i);
      }
   }
}
