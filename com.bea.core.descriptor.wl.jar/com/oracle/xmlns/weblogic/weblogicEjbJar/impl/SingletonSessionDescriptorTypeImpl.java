package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SingletonClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SingletonSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerDescriptorType;
import javax.xml.namespace.QName;

public class SingletonSessionDescriptorTypeImpl extends XmlComplexContentImpl implements SingletonSessionDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName TIMERDESCRIPTOR$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "timer-descriptor");
   private static final QName SINGLETONCLUSTERING$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "singleton-clustering");
   private static final QName ID$4 = new QName("", "id");

   public SingletonSessionDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TimerDescriptorType getTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().find_element_user(TIMERDESCRIPTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMERDESCRIPTOR$0) != 0;
      }
   }

   public void setTimerDescriptor(TimerDescriptorType timerDescriptor) {
      this.generatedSetterHelperImpl(timerDescriptor, TIMERDESCRIPTOR$0, 0, (short)1);
   }

   public TimerDescriptorType addNewTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().add_element_user(TIMERDESCRIPTOR$0);
         return target;
      }
   }

   public void unsetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMERDESCRIPTOR$0, 0);
      }
   }

   public SingletonClusteringType getSingletonClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonClusteringType target = null;
         target = (SingletonClusteringType)this.get_store().find_element_user(SINGLETONCLUSTERING$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingletonClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONCLUSTERING$2) != 0;
      }
   }

   public void setSingletonClustering(SingletonClusteringType singletonClustering) {
      this.generatedSetterHelperImpl(singletonClustering, SINGLETONCLUSTERING$2, 0, (short)1);
   }

   public SingletonClusteringType addNewSingletonClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingletonClusteringType target = null;
         target = (SingletonClusteringType)this.get_store().add_element_user(SINGLETONCLUSTERING$2);
         return target;
      }
   }

   public void unsetSingletonClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONCLUSTERING$2, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
