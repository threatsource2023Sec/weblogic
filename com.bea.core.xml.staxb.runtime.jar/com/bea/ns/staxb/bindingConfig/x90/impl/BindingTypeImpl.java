package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.BindingType;
import com.bea.ns.staxb.bindingConfig.x90.JavaClassName;
import com.bea.ns.staxb.bindingConfig.x90.XmlSignature;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class BindingTypeImpl extends XmlComplexContentImpl implements BindingType {
   private static final long serialVersionUID = 1L;
   private static final QName XMLCOMPONENT$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "xmlcomponent");
   private static final QName JAVATYPE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "javatype");

   public BindingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlSignature xgetXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         return target;
      }
   }

   public void setXmlcomponent(String xmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XMLCOMPONENT$0);
         }

         target.setStringValue(xmlcomponent);
      }
   }

   public void xsetXmlcomponent(XmlSignature xmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         if (target == null) {
            target = (XmlSignature)this.get_store().add_element_user(XMLCOMPONENT$0);
         }

         target.set(xmlcomponent);
      }
   }

   public String getJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVATYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaClassName xgetJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(JAVATYPE$2, 0);
         return target;
      }
   }

   public void setJavatype(String javatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVATYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JAVATYPE$2);
         }

         target.setStringValue(javatype);
      }
   }

   public void xsetJavatype(JavaClassName javatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(JAVATYPE$2, 0);
         if (target == null) {
            target = (JavaClassName)this.get_store().add_element_user(JAVATYPE$2);
         }

         target.set(javatype);
      }
   }
}
