package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlLanguage;
import com.sun.java.xml.ns.javaee.IconType;
import com.sun.java.xml.ns.javaee.PathType;
import javax.xml.namespace.QName;

public class IconTypeImpl extends XmlComplexContentImpl implements IconType {
   private static final long serialVersionUID = 1L;
   private static final QName SMALLICON$0 = new QName("http://java.sun.com/xml/ns/javaee", "small-icon");
   private static final QName LARGEICON$2 = new QName("http://java.sun.com/xml/ns/javaee", "large-icon");
   private static final QName LANG$4 = new QName("http://www.w3.org/XML/1998/namespace", "lang");
   private static final QName ID$6 = new QName("", "id");

   public IconTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PathType getSmallIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(SMALLICON$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSmallIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SMALLICON$0) != 0;
      }
   }

   public void setSmallIcon(PathType smallIcon) {
      this.generatedSetterHelperImpl(smallIcon, SMALLICON$0, 0, (short)1);
   }

   public PathType addNewSmallIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(SMALLICON$0);
         return target;
      }
   }

   public void unsetSmallIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SMALLICON$0, 0);
      }
   }

   public PathType getLargeIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(LARGEICON$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLargeIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LARGEICON$2) != 0;
      }
   }

   public void setLargeIcon(PathType largeIcon) {
      this.generatedSetterHelperImpl(largeIcon, LARGEICON$2, 0, (short)1);
   }

   public PathType addNewLargeIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(LARGEICON$2);
         return target;
      }
   }

   public void unsetLargeIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LARGEICON$2, 0);
      }
   }

   public String getLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlLanguage xgetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$4);
         return target;
      }
   }

   public boolean isSetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(LANG$4) != null;
      }
   }

   public void setLang(String lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LANG$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(LANG$4);
         }

         target.setStringValue(lang);
      }
   }

   public void xsetLang(XmlLanguage lang) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLanguage target = null;
         target = (XmlLanguage)this.get_store().find_attribute_user(LANG$4);
         if (target == null) {
            target = (XmlLanguage)this.get_store().add_attribute_user(LANG$4);
         }

         target.set(lang);
      }
   }

   public void unsetLang() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(LANG$4);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
