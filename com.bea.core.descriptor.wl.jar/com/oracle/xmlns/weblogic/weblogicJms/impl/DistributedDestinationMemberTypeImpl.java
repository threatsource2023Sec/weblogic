package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedDestinationMemberType;
import javax.xml.namespace.QName;

public class DistributedDestinationMemberTypeImpl extends NamedEntityTypeImpl implements DistributedDestinationMemberType {
   private static final long serialVersionUID = 1L;
   private static final QName WEIGHT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "weight");
   private static final QName PHYSICALDESTINATIONNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "physical-destination-name");

   public DistributedDestinationMemberTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getWeight() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WEIGHT$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetWeight() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(WEIGHT$0, 0);
         return target;
      }
   }

   public boolean isSetWeight() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEIGHT$0) != 0;
      }
   }

   public void setWeight(int weight) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WEIGHT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WEIGHT$0);
         }

         target.setIntValue(weight);
      }
   }

   public void xsetWeight(XmlInt weight) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(WEIGHT$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(WEIGHT$0);
         }

         target.set(weight);
      }
   }

   public void unsetWeight() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEIGHT$0, 0);
      }
   }

   public String getPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         return target;
      }
   }

   public boolean isNilPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PHYSICALDESTINATIONNAME$2) != 0;
      }
   }

   public void setPhysicalDestinationName(String physicalDestinationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PHYSICALDESTINATIONNAME$2);
         }

         target.setStringValue(physicalDestinationName);
      }
   }

   public void xsetPhysicalDestinationName(XmlString physicalDestinationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PHYSICALDESTINATIONNAME$2);
         }

         target.set(physicalDestinationName);
      }
   }

   public void setNilPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PHYSICALDESTINATIONNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PHYSICALDESTINATIONNAME$2);
         }

         target.setNil();
      }
   }

   public void unsetPhysicalDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PHYSICALDESTINATIONNAME$2, 0);
      }
   }
}
