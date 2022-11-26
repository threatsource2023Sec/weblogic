package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicInterception.ProcessorTypeType;
import javax.xml.namespace.QName;

public class ProcessorTypeTypeImpl extends XmlComplexContentImpl implements ProcessorTypeType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "type");
   private static final QName FACTORY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "factory");

   public ProcessorTypeTypeImpl(SchemaType sType) {
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

   public String getFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FACTORY$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FACTORY$2, 0);
         return target;
      }
   }

   public void setFactory(String factory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FACTORY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FACTORY$2);
         }

         target.setStringValue(factory);
      }
   }

   public void xsetFactory(XmlString factory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FACTORY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FACTORY$2);
         }

         target.set(factory);
      }
   }
}
