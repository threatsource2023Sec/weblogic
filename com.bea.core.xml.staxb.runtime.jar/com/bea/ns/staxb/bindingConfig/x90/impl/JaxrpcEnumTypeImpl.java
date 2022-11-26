package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaClassName;
import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType;
import com.bea.ns.staxb.bindingConfig.x90.XmlSignature;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class JaxrpcEnumTypeImpl extends BindingTypeImpl implements JaxrpcEnumType {
   private static final long serialVersionUID = 1L;
   private static final QName BASEXMLCOMPONENT$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "base-xmlcomponent");
   private static final QName BASEJAVATYPE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "base-javatype");
   private static final QName GETVALUEMETHOD$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "getValue-method");
   private static final QName FROMVALUEMETHOD$6 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "fromValue-method");
   private static final QName FROMSTRINGMETHOD$8 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "fromString-method");
   private static final QName TOXMLMETHOD$10 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "toXML-method");

   public JaxrpcEnumTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getBaseXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEXMLCOMPONENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlSignature xgetBaseXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(BASEXMLCOMPONENT$0, 0);
         return target;
      }
   }

   public void setBaseXmlcomponent(String baseXmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEXMLCOMPONENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASEXMLCOMPONENT$0);
         }

         target.setStringValue(baseXmlcomponent);
      }
   }

   public void xsetBaseXmlcomponent(XmlSignature baseXmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(BASEXMLCOMPONENT$0, 0);
         if (target == null) {
            target = (XmlSignature)this.get_store().add_element_user(BASEXMLCOMPONENT$0);
         }

         target.set(baseXmlcomponent);
      }
   }

   public String getBaseJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEJAVATYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaClassName xgetBaseJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(BASEJAVATYPE$2, 0);
         return target;
      }
   }

   public void setBaseJavatype(String baseJavatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEJAVATYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASEJAVATYPE$2);
         }

         target.setStringValue(baseJavatype);
      }
   }

   public void xsetBaseJavatype(JavaClassName baseJavatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(BASEJAVATYPE$2, 0);
         if (target == null) {
            target = (JavaClassName)this.get_store().add_element_user(BASEJAVATYPE$2);
         }

         target.set(baseJavatype);
      }
   }

   public JavaMethodName getGetValueMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(GETVALUEMETHOD$4, 0);
         return target == null ? null : target;
      }
   }

   public void setGetValueMethod(JavaMethodName getValueMethod) {
      this.generatedSetterHelperImpl(getValueMethod, GETVALUEMETHOD$4, 0, (short)1);
   }

   public JavaMethodName addNewGetValueMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(GETVALUEMETHOD$4);
         return target;
      }
   }

   public JavaMethodName getFromValueMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(FROMVALUEMETHOD$6, 0);
         return target == null ? null : target;
      }
   }

   public void setFromValueMethod(JavaMethodName fromValueMethod) {
      this.generatedSetterHelperImpl(fromValueMethod, FROMVALUEMETHOD$6, 0, (short)1);
   }

   public JavaMethodName addNewFromValueMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(FROMVALUEMETHOD$6);
         return target;
      }
   }

   public JavaMethodName getFromStringMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(FROMSTRINGMETHOD$8, 0);
         return target == null ? null : target;
      }
   }

   public void setFromStringMethod(JavaMethodName fromStringMethod) {
      this.generatedSetterHelperImpl(fromStringMethod, FROMSTRINGMETHOD$8, 0, (short)1);
   }

   public JavaMethodName addNewFromStringMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(FROMSTRINGMETHOD$8);
         return target;
      }
   }

   public JavaMethodName getToXMLMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(TOXMLMETHOD$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetToXMLMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOXMLMETHOD$10) != 0;
      }
   }

   public void setToXMLMethod(JavaMethodName toXMLMethod) {
      this.generatedSetterHelperImpl(toXMLMethod, TOXMLMETHOD$10, 0, (short)1);
   }

   public JavaMethodName addNewToXMLMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(TOXMLMETHOD$10);
         return target;
      }
   }

   public void unsetToXMLMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOXMLMETHOD$10, 0);
      }
   }
}
