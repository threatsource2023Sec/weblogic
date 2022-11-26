package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.PortMappingType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class PortMappingTypeImpl extends XmlComplexContentImpl implements PortMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName PORTNAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "port-name");
   private static final QName JAVAPORTNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "java-port-name");
   private static final QName ID$4 = new QName("", "id");

   public PortMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPortName(String portName) {
      this.generatedSetterHelperImpl(portName, PORTNAME$0, 0, (short)1);
   }

   public String addNewPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTNAME$0);
         return target;
      }
   }

   public String getJavaPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JAVAPORTNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaPortName(String javaPortName) {
      this.generatedSetterHelperImpl(javaPortName, JAVAPORTNAME$2, 0, (short)1);
   }

   public String addNewJavaPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JAVAPORTNAME$2);
         return target;
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
