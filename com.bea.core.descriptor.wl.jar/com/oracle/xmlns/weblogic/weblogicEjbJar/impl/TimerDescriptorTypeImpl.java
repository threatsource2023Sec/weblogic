package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerDescriptorType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class TimerDescriptorTypeImpl extends XmlComplexContentImpl implements TimerDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENTSTORELOGICALNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "persistent-store-logical-name");
   private static final QName ID$2 = new QName("", "id");

   public TimerDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getPersistentStoreLogicalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(PERSISTENTSTORELOGICALNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentStoreLogicalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTORELOGICALNAME$0) != 0;
      }
   }

   public void setPersistentStoreLogicalName(XsdStringType persistentStoreLogicalName) {
      this.generatedSetterHelperImpl(persistentStoreLogicalName, PERSISTENTSTORELOGICALNAME$0, 0, (short)1);
   }

   public XsdStringType addNewPersistentStoreLogicalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(PERSISTENTSTORELOGICALNAME$0);
         return target;
      }
   }

   public void unsetPersistentStoreLogicalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTORELOGICALNAME$0, 0);
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
