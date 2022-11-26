package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceSocketAddressType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class CoherenceSocketAddressTypeImpl extends XmlComplexContentImpl implements CoherenceSocketAddressType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName ADDRESS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "address");
   private static final QName PORT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "port");

   public CoherenceSocketAddressTypeImpl(SchemaType sType) {
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

   public String getAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDRESS$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESS$2, 0);
         return target;
      }
   }

   public boolean isNilAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESS$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADDRESS$2) != 0;
      }
   }

   public void setAddress(String address) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDRESS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADDRESS$2);
         }

         target.setStringValue(address);
      }
   }

   public void xsetAddress(XmlString address) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADDRESS$2);
         }

         target.set(address);
      }
   }

   public void setNilAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADDRESS$2);
         }

         target.setNil();
      }
   }

   public void unsetAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADDRESS$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(PORT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORT$4) != 0;
      }
   }

   public void setPort(XsdNonNegativeIntegerType port) {
      this.generatedSetterHelperImpl(port, PORT$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(PORT$4);
         return target;
      }
   }

   public void unsetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORT$4, 0);
      }
   }
}
