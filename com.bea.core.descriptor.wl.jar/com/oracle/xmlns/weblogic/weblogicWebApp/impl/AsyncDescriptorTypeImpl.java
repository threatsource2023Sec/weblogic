package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.AsyncDescriptorType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import javax.xml.namespace.QName;

public class AsyncDescriptorTypeImpl extends XmlComplexContentImpl implements AsyncDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName TIMEOUTSECS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "timeout-secs");
   private static final QName TIMEOUTCHECKINTERVALSECS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "timeout-check-interval-secs");
   private static final QName ASYNCWORKMANAGER$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "async-work-manager");

   public AsyncDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(TIMEOUTSECS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEOUTSECS$0) != 0;
      }
   }

   public void setTimeoutSecs(XsdIntegerType timeoutSecs) {
      this.generatedSetterHelperImpl(timeoutSecs, TIMEOUTSECS$0, 0, (short)1);
   }

   public XsdIntegerType addNewTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(TIMEOUTSECS$0);
         return target;
      }
   }

   public void unsetTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEOUTSECS$0, 0);
      }
   }

   public XsdIntegerType getTimeoutCheckIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(TIMEOUTCHECKINTERVALSECS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeoutCheckIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEOUTCHECKINTERVALSECS$2) != 0;
      }
   }

   public void setTimeoutCheckIntervalSecs(XsdIntegerType timeoutCheckIntervalSecs) {
      this.generatedSetterHelperImpl(timeoutCheckIntervalSecs, TIMEOUTCHECKINTERVALSECS$2, 0, (short)1);
   }

   public XsdIntegerType addNewTimeoutCheckIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(TIMEOUTCHECKINTERVALSECS$2);
         return target;
      }
   }

   public void unsetTimeoutCheckIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEOUTCHECKINTERVALSECS$2, 0);
      }
   }

   public String getAsyncWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ASYNCWORKMANAGER$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAsyncWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ASYNCWORKMANAGER$4, 0);
         return target;
      }
   }

   public boolean isSetAsyncWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASYNCWORKMANAGER$4) != 0;
      }
   }

   public void setAsyncWorkManager(String asyncWorkManager) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ASYNCWORKMANAGER$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ASYNCWORKMANAGER$4);
         }

         target.setStringValue(asyncWorkManager);
      }
   }

   public void xsetAsyncWorkManager(XmlString asyncWorkManager) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ASYNCWORKMANAGER$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ASYNCWORKMANAGER$4);
         }

         target.set(asyncWorkManager);
      }
   }

   public void unsetAsyncWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASYNCWORKMANAGER$4, 0);
      }
   }
}
