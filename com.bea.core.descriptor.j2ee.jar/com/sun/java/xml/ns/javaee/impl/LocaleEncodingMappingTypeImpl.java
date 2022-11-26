package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.EncodingType;
import com.sun.java.xml.ns.javaee.LocaleEncodingMappingType;
import com.sun.java.xml.ns.javaee.LocaleType;
import javax.xml.namespace.QName;

public class LocaleEncodingMappingTypeImpl extends XmlComplexContentImpl implements LocaleEncodingMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCALE$0 = new QName("http://java.sun.com/xml/ns/javaee", "locale");
   private static final QName ENCODING$2 = new QName("http://java.sun.com/xml/ns/javaee", "encoding");
   private static final QName ID$4 = new QName("", "id");

   public LocaleEncodingMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLocale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public LocaleType xgetLocale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleType target = null;
         target = (LocaleType)this.get_store().find_element_user(LOCALE$0, 0);
         return target;
      }
   }

   public void setLocale(String locale) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALE$0);
         }

         target.setStringValue(locale);
      }
   }

   public void xsetLocale(LocaleType locale) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleType target = null;
         target = (LocaleType)this.get_store().find_element_user(LOCALE$0, 0);
         if (target == null) {
            target = (LocaleType)this.get_store().add_element_user(LOCALE$0);
         }

         target.set(locale);
      }
   }

   public String getEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENCODING$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public EncodingType xgetEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EncodingType target = null;
         target = (EncodingType)this.get_store().find_element_user(ENCODING$2, 0);
         return target;
      }
   }

   public void setEncoding(String encoding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENCODING$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENCODING$2);
         }

         target.setStringValue(encoding);
      }
   }

   public void xsetEncoding(EncodingType encoding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EncodingType target = null;
         target = (EncodingType)this.get_store().find_element_user(ENCODING$2, 0);
         if (target == null) {
            target = (EncodingType)this.get_store().add_element_user(ENCODING$2);
         }

         target.set(encoding);
      }
   }

   public String getId() {
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

   public void setId(String id) {
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
