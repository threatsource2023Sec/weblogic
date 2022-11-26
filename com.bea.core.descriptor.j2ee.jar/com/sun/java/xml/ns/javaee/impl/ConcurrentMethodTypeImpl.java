package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.AccessTimeoutType;
import com.sun.java.xml.ns.javaee.ConcurrentLockTypeType;
import com.sun.java.xml.ns.javaee.ConcurrentMethodType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import javax.xml.namespace.QName;

public class ConcurrentMethodTypeImpl extends XmlComplexContentImpl implements ConcurrentMethodType {
   private static final long serialVersionUID = 1L;
   private static final QName METHOD$0 = new QName("http://java.sun.com/xml/ns/javaee", "method");
   private static final QName LOCK$2 = new QName("http://java.sun.com/xml/ns/javaee", "lock");
   private static final QName ACCESSTIMEOUT$4 = new QName("http://java.sun.com/xml/ns/javaee", "access-timeout");
   private static final QName ID$6 = new QName("", "id");

   public ConcurrentMethodTypeImpl(SchemaType sType) {
      super(sType);
   }

   public NamedMethodType getMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(METHOD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMethod(NamedMethodType method) {
      this.generatedSetterHelperImpl(method, METHOD$0, 0, (short)1);
   }

   public NamedMethodType addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(METHOD$0);
         return target;
      }
   }

   public ConcurrentLockTypeType getLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentLockTypeType target = null;
         target = (ConcurrentLockTypeType)this.get_store().find_element_user(LOCK$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCK$2) != 0;
      }
   }

   public void setLock(ConcurrentLockTypeType lock) {
      this.generatedSetterHelperImpl(lock, LOCK$2, 0, (short)1);
   }

   public ConcurrentLockTypeType addNewLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentLockTypeType target = null;
         target = (ConcurrentLockTypeType)this.get_store().add_element_user(LOCK$2);
         return target;
      }
   }

   public void unsetLock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCK$2, 0);
      }
   }

   public AccessTimeoutType getAccessTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessTimeoutType target = null;
         target = (AccessTimeoutType)this.get_store().find_element_user(ACCESSTIMEOUT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAccessTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACCESSTIMEOUT$4) != 0;
      }
   }

   public void setAccessTimeout(AccessTimeoutType accessTimeout) {
      this.generatedSetterHelperImpl(accessTimeout, ACCESSTIMEOUT$4, 0, (short)1);
   }

   public AccessTimeoutType addNewAccessTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessTimeoutType target = null;
         target = (AccessTimeoutType)this.get_store().add_element_user(ACCESSTIMEOUT$4);
         return target;
      }
   }

   public void unsetAccessTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACCESSTIMEOUT$4, 0);
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
