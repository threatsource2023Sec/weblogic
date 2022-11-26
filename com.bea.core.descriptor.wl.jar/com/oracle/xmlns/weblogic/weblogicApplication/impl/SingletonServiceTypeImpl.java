package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.SingletonServiceType;
import javax.xml.namespace.QName;

public class SingletonServiceTypeImpl extends XmlComplexContentImpl implements SingletonServiceType {
   private static final long serialVersionUID = 1L;
   private static final QName CLASSNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "class-name");
   private static final QName NAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "name");
   private static final QName SINGLETONURI$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "singleton-uri");

   public SingletonServiceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target;
      }
   }

   public void setClassName(String className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.setStringValue(className);
      }
   }

   public void xsetClassName(XmlString className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.set(className);
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

   public String getSingletonUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SINGLETONURI$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSingletonUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SINGLETONURI$4, 0);
         return target;
      }
   }

   public boolean isSetSingletonUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONURI$4) != 0;
      }
   }

   public void setSingletonUri(String singletonUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SINGLETONURI$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SINGLETONURI$4);
         }

         target.setStringValue(singletonUri);
      }
   }

   public void xsetSingletonUri(XmlString singletonUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SINGLETONURI$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SINGLETONURI$4);
         }

         target.set(singletonUri);
      }
   }

   public void unsetSingletonUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONURI$4, 0);
      }
   }
}
