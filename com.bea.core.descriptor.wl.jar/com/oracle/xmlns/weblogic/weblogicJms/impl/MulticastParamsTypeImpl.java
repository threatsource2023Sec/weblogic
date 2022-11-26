package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.MulticastParamsType;
import javax.xml.namespace.QName;

public class MulticastParamsTypeImpl extends XmlComplexContentImpl implements MulticastParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName MULTICASTADDRESS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast-address");
   private static final QName MULTICASTPORT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast-port");
   private static final QName MULTICASTTIMETOLIVE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast-time-to-live");

   public MulticastParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         return target;
      }
   }

   public boolean isNilMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTADDRESS$0) != 0;
      }
   }

   public void setMulticastAddress(String multicastAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTICASTADDRESS$0);
         }

         target.setStringValue(multicastAddress);
      }
   }

   public void xsetMulticastAddress(XmlString multicastAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MULTICASTADDRESS$0);
         }

         target.set(multicastAddress);
      }
   }

   public void setNilMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTADDRESS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MULTICASTADDRESS$0);
         }

         target.setNil();
      }
   }

   public void unsetMulticastAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTADDRESS$0, 0);
      }
   }

   public int getMulticastPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTPORT$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMulticastPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MULTICASTPORT$2, 0);
         return target;
      }
   }

   public boolean isSetMulticastPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTPORT$2) != 0;
      }
   }

   public void setMulticastPort(int multicastPort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTPORT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTICASTPORT$2);
         }

         target.setIntValue(multicastPort);
      }
   }

   public void xsetMulticastPort(XmlInt multicastPort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MULTICASTPORT$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MULTICASTPORT$2);
         }

         target.set(multicastPort);
      }
   }

   public void unsetMulticastPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTPORT$2, 0);
      }
   }

   public int getMulticastTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTTIMETOLIVE$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMulticastTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MULTICASTTIMETOLIVE$4, 0);
         return target;
      }
   }

   public boolean isSetMulticastTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTTIMETOLIVE$4) != 0;
      }
   }

   public void setMulticastTimeToLive(int multicastTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTTIMETOLIVE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTICASTTIMETOLIVE$4);
         }

         target.setIntValue(multicastTimeToLive);
      }
   }

   public void xsetMulticastTimeToLive(XmlInt multicastTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MULTICASTTIMETOLIVE$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MULTICASTTIMETOLIVE$4);
         }

         target.set(multicastTimeToLive);
      }
   }

   public void unsetMulticastTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTTIMETOLIVE$4, 0);
      }
   }
}
