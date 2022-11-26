package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectorWorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInteger;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class ConnectorWorkManagerTypeImpl extends XmlComplexContentImpl implements ConnectorWorkManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXCONCURRENTLONGRUNNINGREQUESTS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "max-concurrent-long-running-requests");

   public ConnectorWorkManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public BigInteger getMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
         return target;
      }
   }

   public boolean isSetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCONCURRENTLONGRUNNINGREQUESTS$0) != 0;
      }
   }

   public void setMaxConcurrentLongRunningRequests(BigInteger maxConcurrentLongRunningRequests) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0);
         }

         target.setBigIntegerValue(maxConcurrentLongRunningRequests);
      }
   }

   public void xsetMaxConcurrentLongRunningRequests(XmlInteger maxConcurrentLongRunningRequests) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(MAXCONCURRENTLONGRUNNINGREQUESTS$0);
         }

         target.set(maxConcurrentLongRunningRequests);
      }
   }

   public void unsetMaxConcurrentLongRunningRequests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCONCURRENTLONGRUNNINGREQUESTS$0, 0);
      }
   }
}
