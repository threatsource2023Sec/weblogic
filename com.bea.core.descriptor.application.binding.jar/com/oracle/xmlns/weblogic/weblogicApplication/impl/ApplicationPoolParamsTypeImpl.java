package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationPoolParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionCheckParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.SizeParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicApplication.XaParamsType;
import javax.xml.namespace.QName;

public class ApplicationPoolParamsTypeImpl extends XmlComplexContentImpl implements ApplicationPoolParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName SIZEPARAMS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "size-params");
   private static final QName XAPARAMS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xa-params");
   private static final QName LOGINDELAYSECONDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "login-delay-seconds");
   private static final QName LEAKPROFILINGENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "leak-profiling-enabled");
   private static final QName CONNECTIONCHECKPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "connection-check-params");
   private static final QName JDBCXADEBUGLEVEL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "jdbcxa-debug-level");
   private static final QName REMOVEINFECTEDCONNECTIONSENABLED$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "remove-infected-connections-enabled");

   public ApplicationPoolParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SizeParamsType getSizeParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SizeParamsType target = null;
         target = (SizeParamsType)this.get_store().find_element_user(SIZEPARAMS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSizeParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIZEPARAMS$0) != 0;
      }
   }

   public void setSizeParams(SizeParamsType sizeParams) {
      this.generatedSetterHelperImpl(sizeParams, SIZEPARAMS$0, 0, (short)1);
   }

   public SizeParamsType addNewSizeParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SizeParamsType target = null;
         target = (SizeParamsType)this.get_store().add_element_user(SIZEPARAMS$0);
         return target;
      }
   }

   public void unsetSizeParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SIZEPARAMS$0, 0);
      }
   }

   public XaParamsType getXaParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XaParamsType target = null;
         target = (XaParamsType)this.get_store().find_element_user(XAPARAMS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXaParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XAPARAMS$2) != 0;
      }
   }

   public void setXaParams(XaParamsType xaParams) {
      this.generatedSetterHelperImpl(xaParams, XAPARAMS$2, 0, (short)1);
   }

   public XaParamsType addNewXaParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XaParamsType target = null;
         target = (XaParamsType)this.get_store().add_element_user(XAPARAMS$2);
         return target;
      }
   }

   public void unsetXaParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XAPARAMS$2, 0);
      }
   }

   public int getLoginDelaySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINDELAYSECONDS$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetLoginDelaySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOGINDELAYSECONDS$4, 0);
         return target;
      }
   }

   public boolean isSetLoginDelaySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGINDELAYSECONDS$4) != 0;
      }
   }

   public void setLoginDelaySeconds(int loginDelaySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINDELAYSECONDS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGINDELAYSECONDS$4);
         }

         target.setIntValue(loginDelaySeconds);
      }
   }

   public void xsetLoginDelaySeconds(XmlInt loginDelaySeconds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOGINDELAYSECONDS$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(LOGINDELAYSECONDS$4);
         }

         target.set(loginDelaySeconds);
      }
   }

   public void unsetLoginDelaySeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINDELAYSECONDS$4, 0);
      }
   }

   public TrueFalseType getLeakProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(LEAKPROFILINGENABLED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLeakProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LEAKPROFILINGENABLED$6) != 0;
      }
   }

   public void setLeakProfilingEnabled(TrueFalseType leakProfilingEnabled) {
      this.generatedSetterHelperImpl(leakProfilingEnabled, LEAKPROFILINGENABLED$6, 0, (short)1);
   }

   public TrueFalseType addNewLeakProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(LEAKPROFILINGENABLED$6);
         return target;
      }
   }

   public void unsetLeakProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LEAKPROFILINGENABLED$6, 0);
      }
   }

   public ConnectionCheckParamsType getConnectionCheckParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionCheckParamsType target = null;
         target = (ConnectionCheckParamsType)this.get_store().find_element_user(CONNECTIONCHECKPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionCheckParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONCHECKPARAMS$8) != 0;
      }
   }

   public void setConnectionCheckParams(ConnectionCheckParamsType connectionCheckParams) {
      this.generatedSetterHelperImpl(connectionCheckParams, CONNECTIONCHECKPARAMS$8, 0, (short)1);
   }

   public ConnectionCheckParamsType addNewConnectionCheckParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionCheckParamsType target = null;
         target = (ConnectionCheckParamsType)this.get_store().add_element_user(CONNECTIONCHECKPARAMS$8);
         return target;
      }
   }

   public void unsetConnectionCheckParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONCHECKPARAMS$8, 0);
      }
   }

   public int getJdbcxaDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JDBCXADEBUGLEVEL$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetJdbcxaDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(JDBCXADEBUGLEVEL$10, 0);
         return target;
      }
   }

   public boolean isSetJdbcxaDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBCXADEBUGLEVEL$10) != 0;
      }
   }

   public void setJdbcxaDebugLevel(int jdbcxaDebugLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JDBCXADEBUGLEVEL$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JDBCXADEBUGLEVEL$10);
         }

         target.setIntValue(jdbcxaDebugLevel);
      }
   }

   public void xsetJdbcxaDebugLevel(XmlInt jdbcxaDebugLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(JDBCXADEBUGLEVEL$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(JDBCXADEBUGLEVEL$10);
         }

         target.set(jdbcxaDebugLevel);
      }
   }

   public void unsetJdbcxaDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBCXADEBUGLEVEL$10, 0);
      }
   }

   public TrueFalseType getRemoveInfectedConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REMOVEINFECTEDCONNECTIONSENABLED$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemoveInfectedConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOVEINFECTEDCONNECTIONSENABLED$12) != 0;
      }
   }

   public void setRemoveInfectedConnectionsEnabled(TrueFalseType removeInfectedConnectionsEnabled) {
      this.generatedSetterHelperImpl(removeInfectedConnectionsEnabled, REMOVEINFECTEDCONNECTIONSENABLED$12, 0, (short)1);
   }

   public TrueFalseType addNewRemoveInfectedConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REMOVEINFECTEDCONNECTIONSENABLED$12);
         return target;
      }
   }

   public void unsetRemoveInfectedConnectionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOVEINFECTEDCONNECTIONSENABLED$12, 0);
      }
   }
}
