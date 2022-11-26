package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SecurityPluginType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class SecurityPluginTypeImpl extends XmlComplexContentImpl implements SecurityPluginType {
   private static final long serialVersionUID = 1L;
   private static final QName PLUGINCLASS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "plugin-class");
   private static final QName KEY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "key");
   private static final QName ID$4 = new QName("", "id");

   public SecurityPluginTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getPluginClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(PLUGINCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPluginClass(XsdStringType pluginClass) {
      this.generatedSetterHelperImpl(pluginClass, PLUGINCLASS$0, 0, (short)1);
   }

   public XsdStringType addNewPluginClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(PLUGINCLASS$0);
         return target;
      }
   }

   public XsdStringType getKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(KEY$2, 0);
         return target == null ? null : target;
      }
   }

   public void setKey(XsdStringType key) {
      this.generatedSetterHelperImpl(key, KEY$2, 0, (short)1);
   }

   public XsdStringType addNewKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(KEY$2);
         return target;
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
