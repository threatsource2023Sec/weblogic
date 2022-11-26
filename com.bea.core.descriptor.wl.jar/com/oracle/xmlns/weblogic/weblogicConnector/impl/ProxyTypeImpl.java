package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ProxyType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class ProxyTypeImpl extends XmlComplexContentImpl implements ProxyType {
   private static final long serialVersionUID = 1L;
   private static final QName INACTIVECONNECTIONTIMEOUTSECONDS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "inactive-connection-timeout-seconds");
   private static final QName CONNECTIONPROFILINGENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-profiling-enabled");
   private static final QName USECONNECTIONPROXIES$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "use-connection-proxies");

   public ProxyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INACTIVECONNECTIONTIMEOUTSECONDS$0) != 0;
      }
   }

   public void setInactiveConnectionTimeoutSeconds(XsdNonNegativeIntegerType inactiveConnectionTimeoutSeconds) {
      this.generatedSetterHelperImpl(inactiveConnectionTimeoutSeconds, INACTIVECONNECTIONTIMEOUTSECONDS$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(INACTIVECONNECTIONTIMEOUTSECONDS$0);
         return target;
      }
   }

   public void unsetInactiveConnectionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INACTIVECONNECTIONTIMEOUTSECONDS$0, 0);
      }
   }

   public TrueFalseType getConnectionProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CONNECTIONPROFILINGENABLED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPROFILINGENABLED$2) != 0;
      }
   }

   public void setConnectionProfilingEnabled(TrueFalseType connectionProfilingEnabled) {
      this.generatedSetterHelperImpl(connectionProfilingEnabled, CONNECTIONPROFILINGENABLED$2, 0, (short)1);
   }

   public TrueFalseType addNewConnectionProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CONNECTIONPROFILINGENABLED$2);
         return target;
      }
   }

   public void unsetConnectionProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPROFILINGENABLED$2, 0);
      }
   }

   public String getUseConnectionProxies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(USECONNECTIONPROXIES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseConnectionProxies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECONNECTIONPROXIES$4) != 0;
      }
   }

   public void setUseConnectionProxies(String useConnectionProxies) {
      this.generatedSetterHelperImpl(useConnectionProxies, USECONNECTIONPROXIES$4, 0, (short)1);
   }

   public String addNewUseConnectionProxies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(USECONNECTIONPROXIES$4);
         return target;
      }
   }

   public void unsetUseConnectionProxies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECONNECTIONPROXIES$4, 0);
      }
   }
}
