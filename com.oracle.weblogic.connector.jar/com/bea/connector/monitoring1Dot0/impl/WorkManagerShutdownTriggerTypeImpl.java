package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.WorkManagerShutdownTriggerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInteger;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class WorkManagerShutdownTriggerTypeImpl extends XmlComplexContentImpl implements WorkManagerShutdownTriggerType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXSTUCKTHREADTIME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "max-stuck-thread-time");
   private static final QName STUCKTHREADCOUNT$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "stuck-thread-count");

   public WorkManagerShutdownTriggerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public BigInteger getMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXSTUCKTHREADTIME$0, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXSTUCKTHREADTIME$0, 0);
         return target;
      }
   }

   public boolean isSetMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSTUCKTHREADTIME$0) != 0;
      }
   }

   public void setMaxStuckThreadTime(BigInteger maxStuckThreadTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXSTUCKTHREADTIME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXSTUCKTHREADTIME$0);
         }

         target.setBigIntegerValue(maxStuckThreadTime);
      }
   }

   public void xsetMaxStuckThreadTime(XmlInteger maxStuckThreadTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXSTUCKTHREADTIME$0, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(MAXSTUCKTHREADTIME$0);
         }

         target.set(maxStuckThreadTime);
      }
   }

   public void unsetMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSTUCKTHREADTIME$0, 0);
      }
   }

   public BigInteger getStuckThreadCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STUCKTHREADCOUNT$2, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetStuckThreadCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(STUCKTHREADCOUNT$2, 0);
         return target;
      }
   }

   public void setStuckThreadCount(BigInteger stuckThreadCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STUCKTHREADCOUNT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STUCKTHREADCOUNT$2);
         }

         target.setBigIntegerValue(stuckThreadCount);
      }
   }

   public void xsetStuckThreadCount(XmlInteger stuckThreadCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(STUCKTHREADCOUNT$2, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(STUCKTHREADCOUNT$2);
         }

         target.set(stuckThreadCount);
      }
   }
}
