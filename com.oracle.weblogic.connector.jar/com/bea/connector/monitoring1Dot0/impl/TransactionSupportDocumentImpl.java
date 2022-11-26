package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.TransactionSupportDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class TransactionSupportDocumentImpl extends XmlComplexContentImpl implements TransactionSupportDocument {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSACTIONSUPPORT$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "transaction-support");

   public TransactionSupportDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONSUPPORT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSACTIONSUPPORT$0, 0);
         return target;
      }
   }

   public void setTransactionSupport(String transactionSupport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONSUPPORT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONSUPPORT$0);
         }

         target.setStringValue(transactionSupport);
      }
   }

   public void xsetTransactionSupport(XmlString transactionSupport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSACTIONSUPPORT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRANSACTIONSUPPORT$0);
         }

         target.set(transactionSupport);
      }
   }
}
