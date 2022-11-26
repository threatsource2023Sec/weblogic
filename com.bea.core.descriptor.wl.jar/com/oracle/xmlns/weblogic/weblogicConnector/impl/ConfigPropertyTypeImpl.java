package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertyType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class ConfigPropertyTypeImpl extends XmlComplexContentImpl implements ConfigPropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "name");
   private static final QName VALUE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "value");
   private static final QName ID$4 = new QName("", "id");

   public ConfigPropertyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(String name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public String addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(VALUE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALUE$2) != 0;
      }
   }

   public void setValue(String value) {
      this.generatedSetterHelperImpl(value, VALUE$2, 0, (short)1);
   }

   public String addNewValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(VALUE$2);
         return target;
      }
   }

   public void unsetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALUE$2, 0);
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
