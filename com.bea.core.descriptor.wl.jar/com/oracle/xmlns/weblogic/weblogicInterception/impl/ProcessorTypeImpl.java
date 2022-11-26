package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicInterception.ProcessorType;
import javax.xml.namespace.QName;

public class ProcessorTypeImpl extends XmlComplexContentImpl implements ProcessorType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "type");
   private static final QName NAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "name");
   private static final QName METADATA$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "metadata");

   public ProcessorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$0, 0);
         return target;
      }
   }

   public void setType(String type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$0);
         }

         target.setStringValue(type);
      }
   }

   public void xsetType(XmlString type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPE$0);
         }

         target.set(type);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$2);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$2);
         }

         target.set(name);
      }
   }

   public String getMetadata() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METADATA$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMetadata() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METADATA$4, 0);
         return target;
      }
   }

   public boolean isSetMetadata() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METADATA$4) != 0;
      }
   }

   public void setMetadata(String metadata) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METADATA$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(METADATA$4);
         }

         target.setStringValue(metadata);
      }
   }

   public void xsetMetadata(XmlString metadata) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METADATA$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(METADATA$4);
         }

         target.set(metadata);
      }
   }

   public void unsetMetadata() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METADATA$4, 0);
      }
   }
}
