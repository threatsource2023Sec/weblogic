package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterWellKnownAddressType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class CoherenceClusterWellKnownAddressTypeImpl extends XmlComplexContentImpl implements CoherenceClusterWellKnownAddressType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName LISTENADDRESS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "listen-address");
   private static final QName LISTENPORT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "listen-port");

   public CoherenceClusterWellKnownAddressTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         return target;
      }
   }

   public boolean isNilListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENADDRESS$2) != 0;
      }
   }

   public void setListenAddress(String listenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LISTENADDRESS$2);
         }

         target.setStringValue(listenAddress);
      }
   }

   public void xsetListenAddress(XmlString listenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LISTENADDRESS$2);
         }

         target.set(listenAddress);
      }
   }

   public void setNilListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LISTENADDRESS$2);
         }

         target.setNil();
      }
   }

   public void unsetListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENADDRESS$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(LISTENPORT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENPORT$4) != 0;
      }
   }

   public void setListenPort(XsdNonNegativeIntegerType listenPort) {
      this.generatedSetterHelperImpl(listenPort, LISTENPORT$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(LISTENPORT$4);
         return target;
      }
   }

   public void unsetListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENPORT$4, 0);
      }
   }
}
