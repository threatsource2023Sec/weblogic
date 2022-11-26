package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransactionDescriptorType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class TransactionDescriptorTypeImpl extends XmlComplexContentImpl implements TransactionDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSTIMEOUTSECONDS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "trans-timeout-seconds");
   private static final QName ID$2 = new QName("", "id");

   public TransactionDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getTransTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(TRANSTIMEOUTSECONDS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSTIMEOUTSECONDS$0) != 0;
      }
   }

   public void setTransTimeoutSeconds(XsdNonNegativeIntegerType transTimeoutSeconds) {
      this.generatedSetterHelperImpl(transTimeoutSeconds, TRANSTIMEOUTSECONDS$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewTransTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(TRANSTIMEOUTSECONDS$0);
         return target;
      }
   }

   public void unsetTransTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSTIMEOUTSECONDS$0, 0);
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
