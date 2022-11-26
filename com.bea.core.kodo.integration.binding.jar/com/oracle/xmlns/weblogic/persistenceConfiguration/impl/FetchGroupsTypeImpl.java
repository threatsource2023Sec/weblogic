package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FetchGroupsType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class FetchGroupsTypeImpl extends XmlComplexContentImpl implements FetchGroupsType {
   private static final long serialVersionUID = 1L;
   private static final QName FETCHGROUP$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "fetch-group");

   public FetchGroupsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getFetchGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FETCHGROUP$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getFetchGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetFetchGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FETCHGROUP$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetFetchGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilFetchGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfFetchGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FETCHGROUP$0);
      }
   }

   public void setFetchGroupArray(String[] fetchGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(fetchGroupArray, FETCHGROUP$0);
      }
   }

   public void setFetchGroupArray(int i, String fetchGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(fetchGroup);
         }
      }
   }

   public void xsetFetchGroupArray(XmlString[] fetchGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(fetchGroupArray, FETCHGROUP$0);
      }
   }

   public void xsetFetchGroupArray(int i, XmlString fetchGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(fetchGroup);
         }
      }
   }

   public void setNilFetchGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FETCHGROUP$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public void insertFetchGroup(int i, String fetchGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(FETCHGROUP$0, i);
         target.setStringValue(fetchGroup);
      }
   }

   public void addFetchGroup(String fetchGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(FETCHGROUP$0);
         target.setStringValue(fetchGroup);
      }
   }

   public XmlString insertNewFetchGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(FETCHGROUP$0, i);
         return target;
      }
   }

   public XmlString addNewFetchGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(FETCHGROUP$0);
         return target;
      }
   }

   public void removeFetchGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FETCHGROUP$0, i);
      }
   }
}
