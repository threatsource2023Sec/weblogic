package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.MaxThreadsConstraintType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class MaxThreadsConstraintTypeImpl extends XmlComplexContentImpl implements MaxThreadsConstraintType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "name");
   private static final QName COUNT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "count");
   private static final QName POOLNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "pool-name");
   private static final QName QUEUESIZE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "queue-size");
   private static final QName ID$8 = new QName("", "id");

   public MaxThreadsConstraintTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(XsdStringType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public XsdStringType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public XsdIntegerType getCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(COUNT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COUNT$2) != 0;
      }
   }

   public void setCount(XsdIntegerType count) {
      this.generatedSetterHelperImpl(count, COUNT$2, 0, (short)1);
   }

   public XsdIntegerType addNewCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(COUNT$2);
         return target;
      }
   }

   public void unsetCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COUNT$2, 0);
      }
   }

   public XsdStringType getPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(POOLNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOLNAME$4) != 0;
      }
   }

   public void setPoolName(XsdStringType poolName) {
      this.generatedSetterHelperImpl(poolName, POOLNAME$4, 0, (short)1);
   }

   public XsdStringType addNewPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(POOLNAME$4);
         return target;
      }
   }

   public void unsetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOLNAME$4, 0);
      }
   }

   public XsdIntegerType getQueueSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(QUEUESIZE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetQueueSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUEUESIZE$6) != 0;
      }
   }

   public void setQueueSize(XsdIntegerType queueSize) {
      this.generatedSetterHelperImpl(queueSize, QUEUESIZE$6, 0, (short)1);
   }

   public XsdIntegerType addNewQueueSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(QUEUESIZE$6);
         return target;
      }
   }

   public void unsetQueueSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUEUESIZE$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
