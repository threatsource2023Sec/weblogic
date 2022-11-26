package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.PreferApplicationPackagesType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PreferApplicationPackagesTypeImpl extends XmlComplexContentImpl implements PreferApplicationPackagesType {
   private static final long serialVersionUID = 1L;
   private static final QName PACKAGENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "package-name");

   public PreferApplicationPackagesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getPackageNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PACKAGENAME$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getPackageNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PACKAGENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetPackageNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PACKAGENAME$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetPackageNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PACKAGENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPackageNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PACKAGENAME$0);
      }
   }

   public void setPackageNameArray(String[] packageNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(packageNameArray, PACKAGENAME$0);
      }
   }

   public void setPackageNameArray(int i, String packageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PACKAGENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(packageName);
         }
      }
   }

   public void xsetPackageNameArray(XmlString[] packageNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(packageNameArray, PACKAGENAME$0);
      }
   }

   public void xsetPackageNameArray(int i, XmlString packageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PACKAGENAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(packageName);
         }
      }
   }

   public void insertPackageName(int i, String packageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(PACKAGENAME$0, i);
         target.setStringValue(packageName);
      }
   }

   public void addPackageName(String packageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(PACKAGENAME$0);
         target.setStringValue(packageName);
      }
   }

   public XmlString insertNewPackageName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(PACKAGENAME$0, i);
         return target;
      }
   }

   public XmlString addNewPackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(PACKAGENAME$0);
         return target;
      }
   }

   public void removePackageName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PACKAGENAME$0, i);
      }
   }
}
