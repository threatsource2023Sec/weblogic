package com.oracle.xmlns.weblogic.multiVersionState.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.multiVersionState.StateType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class StateTypeImpl extends XmlComplexContentImpl implements StateType {
   private static final long serialVersionUID = 1L;
   private static final QName TARGET$0 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "target");
   private static final QName VALUE$2 = new QName("", "value");

   public StateTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TARGET$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TARGET$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TARGET$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TARGET$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TARGET$0);
      }
   }

   public void setTargetArray(String[] targetValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(targetValueArray, TARGET$0);
      }
   }

   public void setTargetArray(int i, String targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TARGET$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(targetValue);
         }
      }
   }

   public void xsetTargetArray(XmlString[] targetValueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(targetValueArray, TARGET$0);
      }
   }

   public void xsetTargetArray(int i, XmlString targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TARGET$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(targetValue);
         }
      }
   }

   public void insertTarget(int i, String targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(TARGET$0, i);
         target.setStringValue(targetValue);
      }
   }

   public void addTarget(String targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(TARGET$0);
         target.setStringValue(targetValue);
      }
   }

   public XmlString insertNewTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(TARGET$0, i);
         return target;
      }
   }

   public XmlString addNewTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(TARGET$0);
         return target;
      }
   }

   public void removeTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TARGET$0, i);
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALUE$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VALUE$2);
         return target;
      }
   }

   public void setValue(String value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALUE$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VALUE$2);
         }

         target.setStringValue(value);
      }
   }

   public void xsetValue(XmlString value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VALUE$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VALUE$2);
         }

         target.set(value);
      }
   }
}
