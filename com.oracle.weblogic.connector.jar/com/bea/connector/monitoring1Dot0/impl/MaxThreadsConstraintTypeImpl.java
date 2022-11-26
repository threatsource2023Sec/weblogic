package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.MaxThreadsConstraintType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class MaxThreadsConstraintTypeImpl extends XmlComplexContentImpl implements MaxThreadsConstraintType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "name");
   private static final QName COUNT$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "count");
   private static final QName POOLNAME$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "pool-name");

   public MaxThreadsConstraintTypeImpl(SchemaType sType) {
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

   public BigInteger getCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COUNT$2, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(COUNT$2, 0);
         return target;
      }
   }

   public boolean isSetCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COUNT$2) != 0;
      }
   }

   public void setCount(BigInteger count) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COUNT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COUNT$2);
         }

         target.setBigIntegerValue(count);
      }
   }

   public void xsetCount(XmlInteger count) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(COUNT$2, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(COUNT$2);
         }

         target.set(count);
      }
   }

   public void unsetCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COUNT$2, 0);
      }
   }

   public String getPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POOLNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POOLNAME$4, 0);
         return target;
      }
   }

   public boolean isSetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOLNAME$4) != 0;
      }
   }

   public void setPoolName(String poolName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POOLNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(POOLNAME$4);
         }

         target.setStringValue(poolName);
      }
   }

   public void xsetPoolName(XmlString poolName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POOLNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(POOLNAME$4);
         }

         target.set(poolName);
      }
   }

   public void unsetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOLNAME$4, 0);
      }
   }
}
