package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.SessionConfigType;
import com.sun.java.xml.ns.j2Ee.XsdIntegerType;
import javax.xml.namespace.QName;

public class SessionConfigTypeImpl extends XmlComplexContentImpl implements SessionConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName SESSIONTIMEOUT$0 = new QName("http://java.sun.com/xml/ns/j2ee", "session-timeout");
   private static final QName ID$2 = new QName("", "id");

   public SessionConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(SESSIONTIMEOUT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONTIMEOUT$0) != 0;
      }
   }

   public void setSessionTimeout(XsdIntegerType sessionTimeout) {
      this.generatedSetterHelperImpl(sessionTimeout, SESSIONTIMEOUT$0, 0, (short)1);
   }

   public XsdIntegerType addNewSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(SESSIONTIMEOUT$0);
         return target;
      }
   }

   public void unsetSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONTIMEOUT$0, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
