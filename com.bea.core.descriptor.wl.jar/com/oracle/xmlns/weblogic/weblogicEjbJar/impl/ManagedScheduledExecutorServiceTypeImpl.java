package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.DispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ManagedScheduledExecutorServiceType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class ManagedScheduledExecutorServiceTypeImpl extends XmlComplexContentImpl implements ManagedScheduledExecutorServiceType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "name");
   private static final QName DISPATCHPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "dispatch-policy");
   private static final QName MAXCONCURRENTLONGRUNNINGREQUESTS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-concurrent-long-running-requests");
   private static final QName LONGRUNNINGPRIORITY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "long-running-priority");
   private static final QName ID$8 = new QName("", "id");

   public ManagedScheduledExecutorServiceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DispatchPolicyType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(DispatchPolicyType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public DispatchPolicyType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public DispatchPolicyType getDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().find_element_user(DISPATCHPOLICY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPATCHPOLICY$2) != 0;
      }
   }

   public void setDispatchPolicy(DispatchPolicyType dispatchPolicy) {
      this.generatedSetterHelperImpl(dispatchPolicy, DISPATCHPOLICY$2, 0, (short)1);
   }

   public DispatchPolicyType addNewDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().add_element_user(DISPATCHPOLICY$2);
         return target;
      }
   }

   public void unsetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPATCHPOLICY$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCONCURRENTLONGRUNNINGREQUESTS$4) != 0;
      }
   }

   public void setMaxConcurrentLongRunningRequests(XsdNonNegativeIntegerType maxConcurrentLongRunningRequests) {
      this.generatedSetterHelperImpl(maxConcurrentLongRunningRequests, MAXCONCURRENTLONGRUNNINGREQUESTS$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$4);
         return target;
      }
   }

   public void unsetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCONCURRENTLONGRUNNINGREQUESTS$4, 0);
      }
   }

   public XsdNonNegativeIntegerType getLongRunningPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(LONGRUNNINGPRIORITY$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLongRunningPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LONGRUNNINGPRIORITY$6) != 0;
      }
   }

   public void setLongRunningPriority(XsdNonNegativeIntegerType longRunningPriority) {
      this.generatedSetterHelperImpl(longRunningPriority, LONGRUNNINGPRIORITY$6, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewLongRunningPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(LONGRUNNINGPRIORITY$6);
         return target;
      }
   }

   public void unsetLongRunningPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LONGRUNNINGPRIORITY$6, 0);
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
