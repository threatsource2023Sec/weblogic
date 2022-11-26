package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PoolType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class PoolTypeImpl extends XmlComplexContentImpl implements PoolType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXBEANSINFREEPOOL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-beans-in-free-pool");
   private static final QName INITIALBEANSINFREEPOOL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "initial-beans-in-free-pool");
   private static final QName IDLETIMEOUTSECONDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "idle-timeout-seconds");
   private static final QName ID$6 = new QName("", "id");

   public PoolTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getMaxBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXBEANSINFREEPOOL$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXBEANSINFREEPOOL$0) != 0;
      }
   }

   public void setMaxBeansInFreePool(XsdNonNegativeIntegerType maxBeansInFreePool) {
      this.generatedSetterHelperImpl(maxBeansInFreePool, MAXBEANSINFREEPOOL$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXBEANSINFREEPOOL$0);
         return target;
      }
   }

   public void unsetMaxBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXBEANSINFREEPOOL$0, 0);
      }
   }

   public XsdNonNegativeIntegerType getInitialBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(INITIALBEANSINFREEPOOL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitialBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALBEANSINFREEPOOL$2) != 0;
      }
   }

   public void setInitialBeansInFreePool(XsdNonNegativeIntegerType initialBeansInFreePool) {
      this.generatedSetterHelperImpl(initialBeansInFreePool, INITIALBEANSINFREEPOOL$2, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewInitialBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(INITIALBEANSINFREEPOOL$2);
         return target;
      }
   }

   public void unsetInitialBeansInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALBEANSINFREEPOOL$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(IDLETIMEOUTSECONDS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDLETIMEOUTSECONDS$4) != 0;
      }
   }

   public void setIdleTimeoutSeconds(XsdNonNegativeIntegerType idleTimeoutSeconds) {
      this.generatedSetterHelperImpl(idleTimeoutSeconds, IDLETIMEOUTSECONDS$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(IDLETIMEOUTSECONDS$4);
         return target;
      }
   }

   public void unsetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDLETIMEOUTSECONDS$4, 0);
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
