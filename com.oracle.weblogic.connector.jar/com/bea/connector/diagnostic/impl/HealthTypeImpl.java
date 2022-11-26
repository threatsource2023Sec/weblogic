package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.HealthType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class HealthTypeImpl extends XmlComplexContentImpl implements HealthType {
   private static final long serialVersionUID = 1L;
   private static final QName STATE$0 = new QName("http://www.bea.com/connector/diagnostic", "state");
   private static final QName REASON$2 = new QName("http://www.bea.com/connector/diagnostic", "reason");

   public HealthTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$0, 0);
         return target;
      }
   }

   public void setState(String state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATE$0);
         }

         target.setStringValue(state);
      }
   }

   public void xsetState(XmlString state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATE$0);
         }

         target.set(state);
      }
   }

   public String[] getReasonArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REASON$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getReasonArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REASON$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetReasonArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REASON$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetReasonArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REASON$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfReasonArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REASON$2);
      }
   }

   public void setReasonArray(String[] reasonArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(reasonArray, REASON$2);
      }
   }

   public void setReasonArray(int i, String reason) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REASON$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(reason);
         }
      }
   }

   public void xsetReasonArray(XmlString[] reasonArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(reasonArray, REASON$2);
      }
   }

   public void xsetReasonArray(int i, XmlString reason) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REASON$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(reason);
         }
      }
   }

   public void insertReason(int i, String reason) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(REASON$2, i);
         target.setStringValue(reason);
      }
   }

   public void addReason(String reason) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(REASON$2);
         target.setStringValue(reason);
      }
   }

   public XmlString insertNewReason(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(REASON$2, i);
         return target;
      }
   }

   public XmlString addNewReason() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(REASON$2);
         return target;
      }
   }

   public void removeReason(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REASON$2, i);
      }
   }
}
