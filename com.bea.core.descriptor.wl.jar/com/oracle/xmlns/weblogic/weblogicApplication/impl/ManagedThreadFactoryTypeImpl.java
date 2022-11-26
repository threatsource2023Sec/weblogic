package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicApplication.DispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicApplication.ManagedThreadFactoryType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class ManagedThreadFactoryTypeImpl extends XmlComplexContentImpl implements ManagedThreadFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "name");
   private static final QName MAXCONCURRENTNEWTHREADS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-concurrent-new-threads");
   private static final QName PRIORITY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "priority");
   private static final QName ID$6 = new QName("", "id");

   public ManagedThreadFactoryTypeImpl(SchemaType sType) {
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

   public XsdNonNegativeIntegerType getMaxConcurrentNewThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXCONCURRENTNEWTHREADS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxConcurrentNewThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCONCURRENTNEWTHREADS$2) != 0;
      }
   }

   public void setMaxConcurrentNewThreads(XsdNonNegativeIntegerType maxConcurrentNewThreads) {
      this.generatedSetterHelperImpl(maxConcurrentNewThreads, MAXCONCURRENTNEWTHREADS$2, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxConcurrentNewThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXCONCURRENTNEWTHREADS$2);
         return target;
      }
   }

   public void unsetMaxConcurrentNewThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCONCURRENTNEWTHREADS$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(PRIORITY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIORITY$4) != 0;
      }
   }

   public void setPriority(XsdNonNegativeIntegerType priority) {
      this.generatedSetterHelperImpl(priority, PRIORITY$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(PRIORITY$4);
         return target;
      }
   }

   public void unsetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIORITY$4, 0);
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
