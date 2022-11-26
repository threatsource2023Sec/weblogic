package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebApp.FastSwapType;
import com.oracle.xmlns.weblogic.weblogicWebApp.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import javax.xml.namespace.QName;

public class FastSwapTypeImpl extends XmlComplexContentImpl implements FastSwapType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "enabled");
   private static final QName REFRESHINTERVAL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "refresh-interval");
   private static final QName REDEFINITIONTASKLIMIT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "redefinition-task-limit");

   public FastSwapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(TrueFalseType enabled) {
      this.generatedSetterHelperImpl(enabled, ENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLED$0);
         return target;
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public XsdIntegerType getRefreshInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(REFRESHINTERVAL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRefreshInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REFRESHINTERVAL$2) != 0;
      }
   }

   public void setRefreshInterval(XsdIntegerType refreshInterval) {
      this.generatedSetterHelperImpl(refreshInterval, REFRESHINTERVAL$2, 0, (short)1);
   }

   public XsdIntegerType addNewRefreshInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(REFRESHINTERVAL$2);
         return target;
      }
   }

   public void unsetRefreshInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REFRESHINTERVAL$2, 0);
      }
   }

   public XsdIntegerType getRedefinitionTaskLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(REDEFINITIONTASKLIMIT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRedefinitionTaskLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDEFINITIONTASKLIMIT$4) != 0;
      }
   }

   public void setRedefinitionTaskLimit(XsdIntegerType redefinitionTaskLimit) {
      this.generatedSetterHelperImpl(redefinitionTaskLimit, REDEFINITIONTASKLIMIT$4, 0, (short)1);
   }

   public XsdIntegerType addNewRedefinitionTaskLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(REDEFINITIONTASKLIMIT$4);
         return target;
      }
   }

   public void unsetRedefinitionTaskLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDEFINITIONTASKLIMIT$4, 0);
      }
   }
}
