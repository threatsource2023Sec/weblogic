package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.PathType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.WebType;
import javax.xml.namespace.QName;

public class WebTypeImpl extends XmlComplexContentImpl implements WebType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBURI$0 = new QName("http://java.sun.com/xml/ns/j2ee", "web-uri");
   private static final QName CONTEXTROOT$2 = new QName("http://java.sun.com/xml/ns/j2ee", "context-root");
   private static final QName ID$4 = new QName("", "id");

   public WebTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PathType getWebUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(WEBURI$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebUri(PathType webUri) {
      this.generatedSetterHelperImpl(webUri, WEBURI$0, 0, (short)1);
   }

   public PathType addNewWebUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(WEBURI$0);
         return target;
      }
   }

   public String getContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CONTEXTROOT$2, 0);
         return target == null ? null : target;
      }
   }

   public void setContextRoot(String contextRoot) {
      this.generatedSetterHelperImpl(contextRoot, CONTEXTROOT$2, 0, (short)1);
   }

   public String addNewContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CONTEXTROOT$2);
         return target;
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
