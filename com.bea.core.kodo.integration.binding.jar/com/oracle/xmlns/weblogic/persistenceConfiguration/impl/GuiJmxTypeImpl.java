package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiJmxType;
import javax.xml.namespace.QName;

public class GuiJmxTypeImpl extends XmlComplexContentImpl implements GuiJmxType {
   private static final long serialVersionUID = 1L;
   private static final QName MBEANSERVERSTRATEGY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "MBeanServerStrategy");
   private static final QName ENABLELOGMBEAN$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableLogMBean");
   private static final QName ENABLERUNTIMEMBEAN$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableRuntimeMBean");

   public GuiJmxTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getMBeanServerStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MBEANSERVERSTRATEGY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMBeanServerStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MBEANSERVERSTRATEGY$0, 0);
         return target;
      }
   }

   public void setMBeanServerStrategy(String mBeanServerStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MBEANSERVERSTRATEGY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MBEANSERVERSTRATEGY$0);
         }

         target.setStringValue(mBeanServerStrategy);
      }
   }

   public void xsetMBeanServerStrategy(XmlString mBeanServerStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MBEANSERVERSTRATEGY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MBEANSERVERSTRATEGY$0);
         }

         target.set(mBeanServerStrategy);
      }
   }

   public boolean getEnableLogMBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLELOGMBEAN$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnableLogMBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLELOGMBEAN$2, 0);
         return target;
      }
   }

   public void setEnableLogMBean(boolean enableLogMBean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLELOGMBEAN$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLELOGMBEAN$2);
         }

         target.setBooleanValue(enableLogMBean);
      }
   }

   public void xsetEnableLogMBean(XmlBoolean enableLogMBean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLELOGMBEAN$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLELOGMBEAN$2);
         }

         target.set(enableLogMBean);
      }
   }

   public boolean getEnableRuntimeMBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLERUNTIMEMBEAN$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnableRuntimeMBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLERUNTIMEMBEAN$4, 0);
         return target;
      }
   }

   public void setEnableRuntimeMBean(boolean enableRuntimeMBean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLERUNTIMEMBEAN$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLERUNTIMEMBEAN$4);
         }

         target.setBooleanValue(enableRuntimeMBean);
      }
   }

   public void xsetEnableRuntimeMBean(XmlBoolean enableRuntimeMBean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLERUNTIMEMBEAN$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLERUNTIMEMBEAN$4);
         }

         target.set(enableRuntimeMBean);
      }
   }
}
