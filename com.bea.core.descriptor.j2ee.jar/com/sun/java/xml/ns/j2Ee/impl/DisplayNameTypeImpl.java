package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlLanguage;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import javax.xml.namespace.QName;

public class DisplayNameTypeImpl extends JavaStringHolderEx implements DisplayNameType {
   private static final long serialVersionUID = 1L;
   private static final QName ID$0 = new QName("", "id");
   private static final QName LANG$2 = new QName("http://www.w3.org/XML/1998/namespace", "lang");

   public DisplayNameTypeImpl(SchemaType sType) {
      super(sType, true);
   }

   protected DisplayNameTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$0);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$0) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$0);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$0);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$0);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$0);
      }
   }

   public String getLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlLanguage xgetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$2);
         return target;
      }
   }

   public boolean isSetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(LANG$2) != null;
      }
   }

   public void setLang(String lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(LANG$2);
         }

         target.setStringValue(lang);
      }
   }

   public void xsetLang(XmlLanguage lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$2);
         if (target == null) {
            target = (XmlLanguage)this.get_store().add_attribute_user(LANG$2);
         }

         target.set(lang);
      }
   }

   public void unsetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(LANG$2);
      }
   }
}
