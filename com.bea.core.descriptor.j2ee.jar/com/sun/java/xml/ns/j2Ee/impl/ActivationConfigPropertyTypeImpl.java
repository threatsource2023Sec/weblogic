package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ActivationConfigPropertyType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import javax.xml.namespace.QName;

public class ActivationConfigPropertyTypeImpl extends XmlComplexContentImpl implements ActivationConfigPropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName ACTIVATIONCONFIGPROPERTYNAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "activation-config-property-name");
   private static final QName ACTIVATIONCONFIGPROPERTYVALUE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "activation-config-property-value");
   private static final QName ID$4 = new QName("", "id");

   public ActivationConfigPropertyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getActivationConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(ACTIVATIONCONFIGPROPERTYNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setActivationConfigPropertyName(XsdStringType activationConfigPropertyName) {
      this.generatedSetterHelperImpl(activationConfigPropertyName, ACTIVATIONCONFIGPROPERTYNAME$0, 0, (short)1);
   }

   public XsdStringType addNewActivationConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(ACTIVATIONCONFIGPROPERTYNAME$0);
         return target;
      }
   }

   public XsdStringType getActivationConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(ACTIVATIONCONFIGPROPERTYVALUE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setActivationConfigPropertyValue(XsdStringType activationConfigPropertyValue) {
      this.generatedSetterHelperImpl(activationConfigPropertyValue, ACTIVATIONCONFIGPROPERTYVALUE$2, 0, (short)1);
   }

   public XsdStringType addNewActivationConfigPropertyValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(ACTIVATIONCONFIGPROPERTYVALUE$2);
         return target;
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
