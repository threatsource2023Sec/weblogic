package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AutoDetachType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AutoDetachTypeImpl extends XmlComplexContentImpl implements AutoDetachType {
   private static final long serialVersionUID = 1L;
   private static final QName AUTODETACH$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-detach");

   public AutoDetachTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getAutoDetachArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AUTODETACH$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getAutoDetachArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetAutoDetachArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AUTODETACH$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetAutoDetachArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilAutoDetachArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfAutoDetachArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTODETACH$0);
      }
   }

   public void setAutoDetachArray(String[] autoDetachArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(autoDetachArray, AUTODETACH$0);
      }
   }

   public void setAutoDetachArray(int i, String autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(autoDetach);
         }
      }
   }

   public void xsetAutoDetachArray(XmlString[] autoDetachArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(autoDetachArray, AUTODETACH$0);
      }
   }

   public void xsetAutoDetachArray(int i, XmlString autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(autoDetach);
         }
      }
   }

   public void setNilAutoDetachArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTODETACH$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public void insertAutoDetach(int i, String autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(AUTODETACH$0, i);
         target.setStringValue(autoDetach);
      }
   }

   public void addAutoDetach(String autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(AUTODETACH$0);
         target.setStringValue(autoDetach);
      }
   }

   public XmlString insertNewAutoDetach(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(AUTODETACH$0, i);
         return target;
      }
   }

   public XmlString addNewAutoDetach() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(AUTODETACH$0);
         return target;
      }
   }

   public void removeAutoDetach(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTODETACH$0, i);
      }
   }
}
