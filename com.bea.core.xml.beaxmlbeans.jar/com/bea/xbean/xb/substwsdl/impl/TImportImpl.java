package com.bea.xbean.xb.substwsdl.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.substwsdl.TImport;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnyURI;
import javax.xml.namespace.QName;

public class TImportImpl extends XmlComplexContentImpl implements TImport {
   private static final QName NAMESPACE$0 = new QName("", "namespace");
   private static final QName LOCATION$2 = new QName("", "location");

   public TImportImpl(SchemaType sType) {
      super(sType);
   }

   public String getNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlAnyURI xgetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(NAMESPACE$0);
         return target;
      }
   }

   public void setNamespace(String namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAMESPACE$0);
         }

         target.setStringValue(namespace);
      }
   }

   public void xsetNamespace(XmlAnyURI namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (XmlAnyURI)this.get_store().add_attribute_user(NAMESPACE$0);
         }

         target.set(namespace);
      }
   }

   public String getLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LOCATION$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlAnyURI xgetLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(LOCATION$2);
         return target;
      }
   }

   public void setLocation(String location) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(LOCATION$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(LOCATION$2);
         }

         target.setStringValue(location);
      }
   }

   public void xsetLocation(XmlAnyURI location) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_attribute_user(LOCATION$2);
         if (target == null) {
            target = (XmlAnyURI)this.get_store().add_attribute_user(LOCATION$2);
         }

         target.set(location);
      }
   }
}
