package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AppinfoDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnyURI;
import javax.xml.namespace.QName;

public class AppinfoDocumentImpl extends XmlComplexContentImpl implements AppinfoDocument {
   private static final QName APPINFO$0 = new QName("http://www.w3.org/2001/XMLSchema", "appinfo");

   public AppinfoDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AppinfoDocument.Appinfo getAppinfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppinfoDocument.Appinfo target = null;
         target = (AppinfoDocument.Appinfo)this.get_store().find_element_user((QName)APPINFO$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAppinfo(AppinfoDocument.Appinfo appinfo) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppinfoDocument.Appinfo target = null;
         target = (AppinfoDocument.Appinfo)this.get_store().find_element_user((QName)APPINFO$0, 0);
         if (target == null) {
            target = (AppinfoDocument.Appinfo)this.get_store().add_element_user(APPINFO$0);
         }

         target.set(appinfo);
      }
   }

   public AppinfoDocument.Appinfo addNewAppinfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppinfoDocument.Appinfo target = null;
         target = (AppinfoDocument.Appinfo)this.get_store().add_element_user(APPINFO$0);
         return target;
      }
   }

   public static class AppinfoImpl extends XmlComplexContentImpl implements AppinfoDocument.Appinfo {
      private static final QName SOURCE$0 = new QName("", "source");

      public AppinfoImpl(SchemaType sType) {
         super(sType);
      }

      public String getSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SOURCE$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SOURCE$0);
            return target;
         }
      }

      public boolean isSetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(SOURCE$0) != null;
         }
      }

      public void setSource(String source) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SOURCE$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SOURCE$0);
            }

            target.setStringValue(source);
         }
      }

      public void xsetSource(XmlAnyURI source) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SOURCE$0);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SOURCE$0);
            }

            target.set(source);
         }
      }

      public void unsetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(SOURCE$0);
         }
      }
   }
}
