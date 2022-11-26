package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.QnameProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class QnamePropertyImpl extends BindingPropertyImpl implements QnameProperty {
   private static final long serialVersionUID = 1L;
   private static final QName QNAME$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "qname");
   private static final QName ATTRIBUTE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "attribute");
   private static final QName MULTIPLE$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "multiple");
   private static final QName NILLABLE$6 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "nillable");
   private static final QName OPTIONAL$8 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "optional");
   private static final QName DEFAULT$10 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "default");

   public QnamePropertyImpl(SchemaType sType) {
      super(sType);
   }

   public QName getQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QNAME$0, 0);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_element_user(QNAME$0, 0);
         return target;
      }
   }

   public void setQname(QName qname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(QNAME$0);
         }

         target.setQNameValue(qname);
      }
   }

   public void xsetQname(XmlQName qname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_element_user(QNAME$0, 0);
         if (target == null) {
            target = (XmlQName)this.get_store().add_element_user(QNAME$0);
         }

         target.set(qname);
      }
   }

   public boolean getAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTRIBUTE$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ATTRIBUTE$2, 0);
         return target;
      }
   }

   public boolean isSetAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTE$2) != 0;
      }
   }

   public void setAttribute(boolean attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTRIBUTE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ATTRIBUTE$2);
         }

         target.setBooleanValue(attribute);
      }
   }

   public void xsetAttribute(XmlBoolean attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ATTRIBUTE$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ATTRIBUTE$2);
         }

         target.set(attribute);
      }
   }

   public void unsetAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTE$2, 0);
      }
   }

   public boolean getMultiple() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTIPLE$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMultiple() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTIPLE$4, 0);
         return target;
      }
   }

   public boolean isSetMultiple() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTIPLE$4) != 0;
      }
   }

   public void setMultiple(boolean multiple) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTIPLE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTIPLE$4);
         }

         target.setBooleanValue(multiple);
      }
   }

   public void xsetMultiple(XmlBoolean multiple) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTIPLE$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MULTIPLE$4);
         }

         target.set(multiple);
      }
   }

   public void unsetMultiple() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTIPLE$4, 0);
      }
   }

   public boolean getNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NILLABLE$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NILLABLE$6, 0);
         return target;
      }
   }

   public boolean isSetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NILLABLE$6) != 0;
      }
   }

   public void setNillable(boolean nillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NILLABLE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NILLABLE$6);
         }

         target.setBooleanValue(nillable);
      }
   }

   public void xsetNillable(XmlBoolean nillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NILLABLE$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NILLABLE$6);
         }

         target.set(nillable);
      }
   }

   public void unsetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NILLABLE$6, 0);
      }
   }

   public boolean getOptional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIONAL$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetOptional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIONAL$8, 0);
         return target;
      }
   }

   public boolean isSetOptional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIONAL$8) != 0;
      }
   }

   public void setOptional(boolean optional) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIONAL$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OPTIONAL$8);
         }

         target.setBooleanValue(optional);
      }
   }

   public void xsetOptional(XmlBoolean optional) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIONAL$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(OPTIONAL$8);
         }

         target.set(optional);
      }
   }

   public void unsetOptional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIONAL$8, 0);
      }
   }

   public String getDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULT$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULT$10, 0);
         return target;
      }
   }

   public boolean isSetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULT$10) != 0;
      }
   }

   public void setDefault(String xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULT$10);
         }

         target.setStringValue(xdefault);
      }
   }

   public void xsetDefault(XmlString xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULT$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULT$10);
         }

         target.set(xdefault);
      }
   }

   public void unsetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULT$10, 0);
      }
   }
}
