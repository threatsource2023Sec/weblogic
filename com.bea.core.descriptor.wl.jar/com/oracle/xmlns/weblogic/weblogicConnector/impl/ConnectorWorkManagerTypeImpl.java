package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectorWorkManagerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class ConnectorWorkManagerTypeImpl extends XmlComplexContentImpl implements ConnectorWorkManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXCONCURRENTLONGRUNNINGREQUESTS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "max-concurrent-long-running-requests");
   private static final QName ID$2 = new QName("", "id");

   public ConnectorWorkManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCONCURRENTLONGRUNNINGREQUESTS$0) != 0;
      }
   }

   public void setMaxConcurrentLongRunningRequests(XsdNonNegativeIntegerType maxConcurrentLongRunningRequests) {
      this.generatedSetterHelperImpl(maxConcurrentLongRunningRequests, MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0);
         return target;
      }
   }

   public void unsetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
