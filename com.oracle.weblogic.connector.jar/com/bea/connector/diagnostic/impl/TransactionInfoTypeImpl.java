package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.TransactionInfoType;
import com.bea.connector.diagnostic.TransactionType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class TransactionInfoTypeImpl extends XmlComplexContentImpl implements TransactionInfoType {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSACTION$0 = new QName("http://www.bea.com/connector/diagnostic", "Transaction");

   public TransactionInfoTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TransactionType[] getTransactionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TRANSACTION$0, targetList);
         TransactionType[] result = new TransactionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TransactionType getTransactionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionType target = null;
         target = (TransactionType)this.get_store().find_element_user(TRANSACTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTransactionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTION$0);
      }
   }

   public void setTransactionArray(TransactionType[] transactionArray) {
      this.check_orphaned();
      this.arraySetterHelper(transactionArray, TRANSACTION$0);
   }

   public void setTransactionArray(int i, TransactionType transaction) {
      this.generatedSetterHelperImpl(transaction, TRANSACTION$0, i, (short)2);
   }

   public TransactionType insertNewTransaction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionType target = null;
         target = (TransactionType)this.get_store().insert_element_user(TRANSACTION$0, i);
         return target;
      }
   }

   public TransactionType addNewTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionType target = null;
         target = (TransactionType)this.get_store().add_element_user(TRANSACTION$0);
         return target;
      }
   }

   public void removeTransaction(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTION$0, i);
      }
   }
}
