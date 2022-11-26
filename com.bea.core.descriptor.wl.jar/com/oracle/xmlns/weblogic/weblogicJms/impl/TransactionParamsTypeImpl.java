package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlLong;
import com.oracle.xmlns.weblogic.weblogicJms.TransactionParamsType;
import javax.xml.namespace.QName;

public class TransactionParamsTypeImpl extends XmlComplexContentImpl implements TransactionParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSACTIONTIMEOUT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "transaction-timeout");
   private static final QName XACONNECTIONFACTORYENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "xa-connection-factory-enabled");

   public TransactionParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public long getTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTIMEOUT$0, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TRANSACTIONTIMEOUT$0, 0);
         return target;
      }
   }

   public boolean isSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONTIMEOUT$0) != 0;
      }
   }

   public void setTransactionTimeout(long transactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTIMEOUT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONTIMEOUT$0);
         }

         target.setLongValue(transactionTimeout);
      }
   }

   public void xsetTransactionTimeout(XmlLong transactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TRANSACTIONTIMEOUT$0, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TRANSACTIONTIMEOUT$0);
         }

         target.set(transactionTimeout);
      }
   }

   public void unsetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONTIMEOUT$0, 0);
      }
   }

   public boolean getXaConnectionFactoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XACONNECTIONFACTORYENABLED$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetXaConnectionFactoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(XACONNECTIONFACTORYENABLED$2, 0);
         return target;
      }
   }

   public boolean isSetXaConnectionFactoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XACONNECTIONFACTORYENABLED$2) != 0;
      }
   }

   public void setXaConnectionFactoryEnabled(boolean xaConnectionFactoryEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XACONNECTIONFACTORYENABLED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XACONNECTIONFACTORYENABLED$2);
         }

         target.setBooleanValue(xaConnectionFactoryEnabled);
      }
   }

   public void xsetXaConnectionFactoryEnabled(XmlBoolean xaConnectionFactoryEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(XACONNECTIONFACTORYENABLED$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(XACONNECTIONFACTORYENABLED$2);
         }

         target.set(xaConnectionFactoryEnabled);
      }
   }

   public void unsetXaConnectionFactoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XACONNECTIONFACTORYENABLED$2, 0);
      }
   }
}
