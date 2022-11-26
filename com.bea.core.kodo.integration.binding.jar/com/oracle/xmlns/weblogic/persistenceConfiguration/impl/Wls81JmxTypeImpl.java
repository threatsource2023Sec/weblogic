package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Wls81JmxType;
import javax.xml.namespace.QName;

public class Wls81JmxTypeImpl extends XmlComplexContentImpl implements Wls81JmxType {
   private static final long serialVersionUID = 1L;
   private static final QName MBEANSERVERSTRATEGY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "MBeanServerStrategy");
   private static final QName ENABLELOGMBEAN$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableLogMBean");
   private static final QName ENABLERUNTIMEMBEAN$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableRuntimeMBean");
   private static final QName URL$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "URL");
   private static final QName USERNAME$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "UserName");
   private static final QName PASSWORD$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "Password");

   public Wls81JmxTypeImpl(SchemaType sType) {
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

   public boolean getURL() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetURL() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(URL$6, 0);
         return target;
      }
   }

   public void setURL(boolean url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URL$6);
         }

         target.setBooleanValue(url);
      }
   }

   public void xsetURL(XmlBoolean url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(URL$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(URL$6);
         }

         target.set(url);
      }
   }

   public boolean getUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USERNAME$8, 0);
         return target;
      }
   }

   public void setUserName(boolean userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USERNAME$8);
         }

         target.setBooleanValue(userName);
      }
   }

   public void xsetUserName(XmlBoolean userName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USERNAME$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USERNAME$8);
         }

         target.set(userName);
      }
   }

   public boolean getPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORD$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PASSWORD$10, 0);
         return target;
      }
   }

   public void setPassword(boolean password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORD$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PASSWORD$10);
         }

         target.setBooleanValue(password);
      }
   }

   public void xsetPassword(XmlBoolean password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PASSWORD$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(PASSWORD$10);
         }

         target.set(password);
      }
   }
}
