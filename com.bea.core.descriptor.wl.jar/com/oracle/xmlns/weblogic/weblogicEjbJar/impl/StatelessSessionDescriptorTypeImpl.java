package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PoolType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatelessClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatelessSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerDescriptorType;
import javax.xml.namespace.QName;

public class StatelessSessionDescriptorTypeImpl extends XmlComplexContentImpl implements StatelessSessionDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName POOL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "pool");
   private static final QName TIMERDESCRIPTOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "timer-descriptor");
   private static final QName STATELESSCLUSTERING$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateless-clustering");
   private static final QName ID$6 = new QName("", "id");

   public StatelessSessionDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PoolType getPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().find_element_user(POOL$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOL$0) != 0;
      }
   }

   public void setPool(PoolType pool) {
      this.generatedSetterHelperImpl(pool, POOL$0, 0, (short)1);
   }

   public PoolType addNewPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().add_element_user(POOL$0);
         return target;
      }
   }

   public void unsetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOL$0, 0);
      }
   }

   public TimerDescriptorType getTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().find_element_user(TIMERDESCRIPTOR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMERDESCRIPTOR$2) != 0;
      }
   }

   public void setTimerDescriptor(TimerDescriptorType timerDescriptor) {
      this.generatedSetterHelperImpl(timerDescriptor, TIMERDESCRIPTOR$2, 0, (short)1);
   }

   public TimerDescriptorType addNewTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().add_element_user(TIMERDESCRIPTOR$2);
         return target;
      }
   }

   public void unsetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMERDESCRIPTOR$2, 0);
      }
   }

   public StatelessClusteringType getStatelessClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatelessClusteringType target = null;
         target = (StatelessClusteringType)this.get_store().find_element_user(STATELESSCLUSTERING$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatelessClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATELESSCLUSTERING$4) != 0;
      }
   }

   public void setStatelessClustering(StatelessClusteringType statelessClustering) {
      this.generatedSetterHelperImpl(statelessClustering, STATELESSCLUSTERING$4, 0, (short)1);
   }

   public StatelessClusteringType addNewStatelessClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatelessClusteringType target = null;
         target = (StatelessClusteringType)this.get_store().add_element_user(STATELESSCLUSTERING$4);
         return target;
      }
   }

   public void unsetStatelessClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATELESSCLUSTERING$4, 0);
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
