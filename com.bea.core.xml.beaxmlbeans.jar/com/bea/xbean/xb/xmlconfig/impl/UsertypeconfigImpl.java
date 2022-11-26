package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xmlconfig.Usertypeconfig;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class UsertypeconfigImpl extends XmlComplexContentImpl implements Usertypeconfig {
   private static final QName STATICHANDLER$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "staticHandler");
   private static final QName NAME$2 = new QName("", "name");
   private static final QName JAVANAME$4 = new QName("", "javaname");

   public UsertypeconfigImpl(SchemaType sType) {
      super(sType);
   }

   public String getStaticHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStaticHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
         return target;
      }
   }

   public void setStaticHandler(String staticHandler) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATICHANDLER$0);
         }

         target.setStringValue(staticHandler);
      }
   }

   public void xsetStaticHandler(XmlString staticHandler) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATICHANDLER$0);
         }

         target.set(staticHandler);
      }
   }

   public QName getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(NAME$2);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$2) != null;
      }
   }

   public void setName(QName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$2);
         }

         target.setQNameValue(name);
      }
   }

   public void xsetName(XmlQName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(NAME$2);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(NAME$2);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$2);
      }
   }

   public String getJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(JAVANAME$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(JAVANAME$4);
         return target;
      }
   }

   public boolean isSetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(JAVANAME$4) != null;
      }
   }

   public void setJavaname(String javaname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(JAVANAME$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(JAVANAME$4);
         }

         target.setStringValue(javaname);
      }
   }

   public void xsetJavaname(XmlString javaname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(JAVANAME$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(JAVANAME$4);
         }

         target.set(javaname);
      }
   }

   public void unsetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(JAVANAME$4);
      }
   }
}
