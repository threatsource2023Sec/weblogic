package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceServiceType;
import javax.xml.namespace.QName;

public class CoherenceServiceTypeImpl extends XmlComplexContentImpl implements CoherenceServiceType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName PARTITION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "partition");

   public CoherenceServiceTypeImpl(SchemaType sType) {
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

   public String getPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARTITION$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITION$2, 0);
         return target;
      }
   }

   public boolean isNilPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITION$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARTITION$2) != 0;
      }
   }

   public void setPartition(String partition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARTITION$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARTITION$2);
         }

         target.setStringValue(partition);
      }
   }

   public void xsetPartition(XmlString partition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITION$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARTITION$2);
         }

         target.set(partition);
      }
   }

   public void setNilPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITION$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARTITION$2);
         }

         target.setNil();
      }
   }

   public void unsetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARTITION$2, 0);
      }
   }
}
