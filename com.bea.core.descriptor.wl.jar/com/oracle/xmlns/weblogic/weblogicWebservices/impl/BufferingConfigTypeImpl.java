package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicWebservices.BufferingConfigType;
import com.oracle.xmlns.weblogic.weblogicWebservices.BufferingQueueType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class BufferingConfigTypeImpl extends XmlComplexContentImpl implements BufferingConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMIZED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "customized");
   private static final QName REQUESTQUEUE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "request-queue");
   private static final QName RESPONSEQUEUE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "response-queue");
   private static final QName RETRYCOUNT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "retry-count");
   private static final QName RETRYDELAY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "retry-delay");

   public BufferingConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target;
      }
   }

   public boolean isSetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMIZED$0) != 0;
      }
   }

   public void setCustomized(boolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.setBooleanValue(customized);
      }
   }

   public void xsetCustomized(XmlBoolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.set(customized);
      }
   }

   public void unsetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMIZED$0, 0);
      }
   }

   public BufferingQueueType getRequestQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingQueueType target = null;
         target = (BufferingQueueType)this.get_store().find_element_user(REQUESTQUEUE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRequestQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUESTQUEUE$2) != 0;
      }
   }

   public void setRequestQueue(BufferingQueueType requestQueue) {
      this.generatedSetterHelperImpl(requestQueue, REQUESTQUEUE$2, 0, (short)1);
   }

   public BufferingQueueType addNewRequestQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingQueueType target = null;
         target = (BufferingQueueType)this.get_store().add_element_user(REQUESTQUEUE$2);
         return target;
      }
   }

   public void unsetRequestQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUESTQUEUE$2, 0);
      }
   }

   public BufferingQueueType getResponseQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingQueueType target = null;
         target = (BufferingQueueType)this.get_store().find_element_user(RESPONSEQUEUE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResponseQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPONSEQUEUE$4) != 0;
      }
   }

   public void setResponseQueue(BufferingQueueType responseQueue) {
      this.generatedSetterHelperImpl(responseQueue, RESPONSEQUEUE$4, 0, (short)1);
   }

   public BufferingQueueType addNewResponseQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingQueueType target = null;
         target = (BufferingQueueType)this.get_store().add_element_user(RESPONSEQUEUE$4);
         return target;
      }
   }

   public void unsetResponseQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPONSEQUEUE$4, 0);
      }
   }

   public int getRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRYCOUNT$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RETRYCOUNT$6, 0);
         return target;
      }
   }

   public boolean isSetRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRYCOUNT$6) != 0;
      }
   }

   public void setRetryCount(int retryCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRYCOUNT$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETRYCOUNT$6);
         }

         target.setIntValue(retryCount);
      }
   }

   public void xsetRetryCount(XmlInt retryCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RETRYCOUNT$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(RETRYCOUNT$6);
         }

         target.set(retryCount);
      }
   }

   public void unsetRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRYCOUNT$6, 0);
      }
   }

   public String getRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RETRYDELAY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRYDELAY$8) != 0;
      }
   }

   public void setRetryDelay(String retryDelay) {
      this.generatedSetterHelperImpl(retryDelay, RETRYDELAY$8, 0, (short)1);
   }

   public String addNewRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RETRYDELAY$8);
         return target;
      }
   }

   public void unsetRetryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRYDELAY$8, 0);
      }
   }
}
