package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.TransactionType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class TransactionTypeImpl extends XmlComplexContentImpl implements TransactionType {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSACTIONTYPE$0 = new QName("http://www.bea.com/connector/diagnostic", "transactionType");
   private static final QName ID$2 = new QName("http://www.bea.com/connector/diagnostic", "id");
   private static final QName STATUS$4 = new QName("http://www.bea.com/connector/diagnostic", "status");
   private static final QName ENLISTMENTTIME$6 = new QName("http://www.bea.com/connector/diagnostic", "enlistmentTime");

   public TransactionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSACTIONTYPE$0, 0);
         return target;
      }
   }

   public void setTransactionType(String transactionType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONTYPE$0);
         }

         target.setStringValue(transactionType);
      }
   }

   public void xsetTransactionType(XmlString transactionType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRANSACTIONTYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRANSACTIONTYPE$0);
         }

         target.set(transactionType);
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

   public String getStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATUS$4, 0);
         return target;
      }
   }

   public void setStatus(String status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATUS$4);
         }

         target.setStringValue(status);
      }
   }

   public void xsetStatus(XmlString status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATUS$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATUS$4);
         }

         target.set(status);
      }
   }

   public String getEnlistmentTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENLISTMENTTIME$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEnlistmentTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENLISTMENTTIME$6, 0);
         return target;
      }
   }

   public void setEnlistmentTime(String enlistmentTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENLISTMENTTIME$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENLISTMENTTIME$6);
         }

         target.setStringValue(enlistmentTime);
      }
   }

   public void xsetEnlistmentTime(XmlString enlistmentTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENLISTMENTTIME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENLISTMENTTIME$6);
         }

         target.set(enlistmentTime);
      }
   }
}
