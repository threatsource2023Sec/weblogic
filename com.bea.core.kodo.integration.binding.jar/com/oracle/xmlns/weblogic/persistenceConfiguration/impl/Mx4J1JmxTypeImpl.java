package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Mx4J1JmxType;
import javax.xml.namespace.QName;

public class Mx4J1JmxTypeImpl extends XmlComplexContentImpl implements Mx4J1JmxType {
   private static final long serialVersionUID = 1L;
   private static final QName MBEANSERVERSTRATEGY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "MBeanServerStrategy");
   private static final QName ENABLELOGMBEAN$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableLogMBean");
   private static final QName ENABLERUNTIMEMBEAN$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "EnableRuntimeMBean");
   private static final QName HOST$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "Host");
   private static final QName PORT$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "Port");
   private static final QName JNDINAME$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "JNDIName");

   public Mx4J1JmxTypeImpl(SchemaType sType) {
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

   public String getHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HOST$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$6, 0);
         return target;
      }
   }

   public void setHost(String host) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HOST$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HOST$6);
         }

         target.setStringValue(host);
      }
   }

   public void xsetHost(XmlString host) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HOST$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(HOST$6);
         }

         target.set(host);
      }
   }

   public String getPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PORT$8, 0);
         return target;
      }
   }

   public void setPort(String port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PORT$8);
         }

         target.setStringValue(port);
      }
   }

   public void xsetPort(XmlString port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PORT$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PORT$8);
         }

         target.set(port);
      }
   }

   public String getJNDIName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJNDIName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$10, 0);
         return target;
      }
   }

   public void setJNDIName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$10);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJNDIName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$10);
         }

         target.set(jndiName);
      }
   }
}
