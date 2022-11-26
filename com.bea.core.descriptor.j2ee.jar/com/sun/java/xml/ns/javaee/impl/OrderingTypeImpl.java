package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.OrderingOrderingType;
import com.sun.java.xml.ns.javaee.OrderingType;
import javax.xml.namespace.QName;

public class OrderingTypeImpl extends XmlComplexContentImpl implements OrderingType {
   private static final long serialVersionUID = 1L;
   private static final QName AFTER$0 = new QName("http://java.sun.com/xml/ns/javaee", "after");
   private static final QName BEFORE$2 = new QName("http://java.sun.com/xml/ns/javaee", "before");

   public OrderingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public OrderingOrderingType getAfter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOrderingType target = null;
         target = (OrderingOrderingType)this.get_store().find_element_user(AFTER$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAfter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AFTER$0) != 0;
      }
   }

   public void setAfter(OrderingOrderingType after) {
      this.generatedSetterHelperImpl(after, AFTER$0, 0, (short)1);
   }

   public OrderingOrderingType addNewAfter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOrderingType target = null;
         target = (OrderingOrderingType)this.get_store().add_element_user(AFTER$0);
         return target;
      }
   }

   public void unsetAfter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AFTER$0, 0);
      }
   }

   public OrderingOrderingType getBefore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOrderingType target = null;
         target = (OrderingOrderingType)this.get_store().find_element_user(BEFORE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBefore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BEFORE$2) != 0;
      }
   }

   public void setBefore(OrderingOrderingType before) {
      this.generatedSetterHelperImpl(before, BEFORE$2, 0, (short)1);
   }

   public OrderingOrderingType addNewBefore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOrderingType target = null;
         target = (OrderingOrderingType)this.get_store().add_element_user(BEFORE$2);
         return target;
      }
   }

   public void unsetBefore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BEFORE$2, 0);
      }
   }
}
