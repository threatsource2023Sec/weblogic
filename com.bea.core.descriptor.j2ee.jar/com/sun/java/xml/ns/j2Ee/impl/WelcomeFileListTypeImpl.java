package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.WelcomeFileListType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WelcomeFileListTypeImpl extends XmlComplexContentImpl implements WelcomeFileListType {
   private static final long serialVersionUID = 1L;
   private static final QName WELCOMEFILE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "welcome-file");
   private static final QName ID$2 = new QName("", "id");

   public WelcomeFileListTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getWelcomeFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WELCOMEFILE$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getWelcomeFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WELCOMEFILE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetWelcomeFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WELCOMEFILE$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetWelcomeFileArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WELCOMEFILE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWelcomeFileArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WELCOMEFILE$0);
      }
   }

   public void setWelcomeFileArray(String[] welcomeFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(welcomeFileArray, WELCOMEFILE$0);
      }
   }

   public void setWelcomeFileArray(int i, String welcomeFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WELCOMEFILE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(welcomeFile);
         }
      }
   }

   public void xsetWelcomeFileArray(XmlString[] welcomeFileArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(welcomeFileArray, WELCOMEFILE$0);
      }
   }

   public void xsetWelcomeFileArray(int i, XmlString welcomeFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WELCOMEFILE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(welcomeFile);
         }
      }
   }

   public void insertWelcomeFile(int i, String welcomeFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(WELCOMEFILE$0, i);
         target.setStringValue(welcomeFile);
      }
   }

   public void addWelcomeFile(String welcomeFile) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(WELCOMEFILE$0);
         target.setStringValue(welcomeFile);
      }
   }

   public XmlString insertNewWelcomeFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(WELCOMEFILE$0, i);
         return target;
      }
   }

   public XmlString addNewWelcomeFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(WELCOMEFILE$0);
         return target;
      }
   }

   public void removeWelcomeFile(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WELCOMEFILE$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
