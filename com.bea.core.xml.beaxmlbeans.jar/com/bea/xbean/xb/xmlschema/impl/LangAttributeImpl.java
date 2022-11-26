package com.bea.xbean.xb.xmlschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xmlschema.LangAttribute;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLanguage;
import javax.xml.namespace.QName;

public class LangAttributeImpl extends XmlComplexContentImpl implements LangAttribute {
   private static final QName LANG$0 = new QName("http://www.w3.org/XML/1998/namespace", "lang");

   public LangAttributeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlLanguage xgetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$0);
         return target;
      }
   }

   public boolean isSetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(LANG$0) != null;
      }
   }

   public void setLang(String lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(LANG$0);
         }

         target.setStringValue(lang);
      }
   }

   public void xsetLang(XmlLanguage lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$0);
         if (target == null) {
            target = (XmlLanguage)this.get_store().add_attribute_user(LANG$0);
         }

         target.set(lang);
      }
   }

   public void unsetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(LANG$0);
      }
   }
}
