package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.ManagedConnectionType;
import com.bea.connector.diagnostic.TransactionInfoType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ManagedConnectionTypeImpl extends XmlComplexContentImpl implements ManagedConnectionType {
   private static final long serialVersionUID = 1L;
   private static final QName HASHCODE$0 = new QName("http://www.bea.com/connector/diagnostic", "hashcode");
   private static final QName ID$2 = new QName("http://www.bea.com/connector/diagnostic", "id");
   private static final QName TRANSACTIONINFO$4 = new QName("http://www.bea.com/connector/diagnostic", "transactionInfo");

   public ManagedConnectionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getHashcode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HASHCODE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetHashcode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HASHCODE$0, 0);
         return target;
      }
   }

   public void setHashcode(int hashcode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HASHCODE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HASHCODE$0);
         }

         target.setIntValue(hashcode);
      }
   }

   public void xsetHashcode(XmlInt hashcode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(HASHCODE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(HASHCODE$0);
         }

         target.set(hashcode);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ID$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ID$2, 0);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ID$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlString id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ID$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ID$2);
         }

         target.set(id);
      }
   }

   public TransactionInfoType getTransactionInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionInfoType target = null;
         target = (TransactionInfoType)this.get_store().find_element_user(TRANSACTIONINFO$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONINFO$4) != 0;
      }
   }

   public void setTransactionInfo(TransactionInfoType transactionInfo) {
      this.generatedSetterHelperImpl(transactionInfo, TRANSACTIONINFO$4, 0, (short)1);
   }

   public TransactionInfoType addNewTransactionInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionInfoType target = null;
         target = (TransactionInfoType)this.get_store().add_element_user(TRANSACTIONINFO$4);
         return target;
      }
   }

   public void unsetTransactionInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONINFO$4, 0);
      }
   }
}
