package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.FormChoice;
import com.bea.xbean.xb.xsdschema.LocalSimpleType;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class AttributeImpl extends AnnotatedImpl implements Attribute {
   private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
   private static final QName NAME$2 = new QName("", "name");
   private static final QName REF$4 = new QName("", "ref");
   private static final QName TYPE$6 = new QName("", "type");
   private static final QName USE$8 = new QName("", "use");
   private static final QName DEFAULT$10 = new QName("", "default");
   private static final QName FIXED$12 = new QName("", "fixed");
   private static final QName FORM$14 = new QName("", "form");

   public AttributeImpl(SchemaType sType) {
      super(sType);
   }

   public LocalSimpleType getSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLETYPE$0) != 0;
      }
   }

   public void setSimpleType(LocalSimpleType simpleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         if (target == null) {
            target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         }

         target.set(simpleType);
      }
   }

   public LocalSimpleType addNewSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         return target;
      }
   }

   public void unsetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SIMPLETYPE$0, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$2);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$2) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$2);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$2);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$2);
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

   public QName getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$4);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$4);
         return target;
      }
   }

   public boolean isSetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(REF$4) != null;
      }
   }

   public void setRef(QName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$4);
         }

         target.setQNameValue(ref);
      }
   }

   public void xsetRef(XmlQName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$4);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(REF$4);
         }

         target.set(ref);
      }
   }

   public void unsetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(REF$4);
      }
   }

   public QName getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$6);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(TYPE$6);
         return target;
      }
   }

   public boolean isSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TYPE$6) != null;
      }
   }

   public void setType(QName type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TYPE$6);
         }

         target.setQNameValue(type);
      }
   }

   public void xsetType(XmlQName type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(TYPE$6);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(TYPE$6);
         }

         target.set(type);
      }
   }

   public void unsetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TYPE$6);
      }
   }

   public Attribute.Use.Enum getUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(USE$8);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(USE$8);
         }

         return target == null ? null : (Attribute.Use.Enum)target.getEnumValue();
      }
   }

   public Attribute.Use xgetUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute.Use target = null;
         target = (Attribute.Use)this.get_store().find_attribute_user(USE$8);
         if (target == null) {
            target = (Attribute.Use)this.get_default_attribute_value(USE$8);
         }

         return target;
      }
   }

   public boolean isSetUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(USE$8) != null;
      }
   }

   public void setUse(Attribute.Use.Enum use) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(USE$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(USE$8);
         }

         target.setEnumValue(use);
      }
   }

   public void xsetUse(Attribute.Use use) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute.Use target = null;
         target = (Attribute.Use)this.get_store().find_attribute_user(USE$8);
         if (target == null) {
            target = (Attribute.Use)this.get_store().add_attribute_user(USE$8);
         }

         target.set(use);
      }
   }

   public void unsetUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(USE$8);
      }
   }

   public String getDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DEFAULT$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DEFAULT$10);
         return target;
      }
   }

   public boolean isSetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(DEFAULT$10) != null;
      }
   }

   public void setDefault(String xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DEFAULT$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(DEFAULT$10);
         }

         target.setStringValue(xdefault);
      }
   }

   public void xsetDefault(XmlString xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DEFAULT$10);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(DEFAULT$10);
         }

         target.set(xdefault);
      }
   }

   public void unsetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(DEFAULT$10);
      }
   }

   public String getFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(FIXED$12);
         return target;
      }
   }

   public boolean isSetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FIXED$12) != null;
      }
   }

   public void setFixed(String fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FIXED$12);
         }

         target.setStringValue(fixed);
      }
   }

   public void xsetFixed(XmlString fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(FIXED$12);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(FIXED$12);
         }

         target.set(fixed);
      }
   }

   public void unsetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FIXED$12);
      }
   }

   public FormChoice.Enum getForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FORM$14);
         return target == null ? null : (FormChoice.Enum)target.getEnumValue();
      }
   }

   public FormChoice xgetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormChoice target = null;
         target = (FormChoice)this.get_store().find_attribute_user(FORM$14);
         return target;
      }
   }

   public boolean isSetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FORM$14) != null;
      }
   }

   public void setForm(FormChoice.Enum form) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FORM$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FORM$14);
         }

         target.setEnumValue(form);
      }
   }

   public void xsetForm(FormChoice form) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormChoice target = null;
         target = (FormChoice)this.get_store().find_attribute_user(FORM$14);
         if (target == null) {
            target = (FormChoice)this.get_store().add_attribute_user(FORM$14);
         }

         target.set(form);
      }
   }

   public void unsetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FORM$14);
      }
   }

   public static class UseImpl extends JavaStringEnumerationHolderEx implements Attribute.Use {
      public UseImpl(SchemaType sType) {
         super(sType, false);
      }

      protected UseImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
