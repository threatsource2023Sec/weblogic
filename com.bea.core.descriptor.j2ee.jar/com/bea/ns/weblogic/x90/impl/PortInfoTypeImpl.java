package com.bea.ns.weblogic.x90.impl;

import com.bea.ns.weblogic.x90.PortInfoType;
import com.bea.ns.weblogic.x90.PropertyNamevalueType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PortInfoTypeImpl extends XmlComplexContentImpl implements PortInfoType {
   private static final long serialVersionUID = 1L;
   private static final QName PORTNAME$0 = new QName("http://www.bea.com/ns/weblogic/90", "port-name");
   private static final QName STUBPROPERTY$2 = new QName("http://www.bea.com/ns/weblogic/90", "stub-property");
   private static final QName CALLPROPERTY$4 = new QName("http://www.bea.com/ns/weblogic/90", "call-property");

   public PortInfoTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPortName(String portName) {
      this.generatedSetterHelperImpl(portName, PORTNAME$0, 0, (short)1);
   }

   public String addNewPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTNAME$0);
         return target;
      }
   }

   public PropertyNamevalueType[] getStubPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STUBPROPERTY$2, targetList);
         PropertyNamevalueType[] result = new PropertyNamevalueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyNamevalueType getStubPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().find_element_user(STUBPROPERTY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStubPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STUBPROPERTY$2);
      }
   }

   public void setStubPropertyArray(PropertyNamevalueType[] stubPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(stubPropertyArray, STUBPROPERTY$2);
   }

   public void setStubPropertyArray(int i, PropertyNamevalueType stubProperty) {
      this.generatedSetterHelperImpl(stubProperty, STUBPROPERTY$2, i, (short)2);
   }

   public PropertyNamevalueType insertNewStubProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().insert_element_user(STUBPROPERTY$2, i);
         return target;
      }
   }

   public PropertyNamevalueType addNewStubProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().add_element_user(STUBPROPERTY$2);
         return target;
      }
   }

   public void removeStubProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STUBPROPERTY$2, i);
      }
   }

   public PropertyNamevalueType[] getCallPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CALLPROPERTY$4, targetList);
         PropertyNamevalueType[] result = new PropertyNamevalueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyNamevalueType getCallPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().find_element_user(CALLPROPERTY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCallPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLPROPERTY$4);
      }
   }

   public void setCallPropertyArray(PropertyNamevalueType[] callPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(callPropertyArray, CALLPROPERTY$4);
   }

   public void setCallPropertyArray(int i, PropertyNamevalueType callProperty) {
      this.generatedSetterHelperImpl(callProperty, CALLPROPERTY$4, i, (short)2);
   }

   public PropertyNamevalueType insertNewCallProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().insert_element_user(CALLPROPERTY$4, i);
         return target;
      }
   }

   public PropertyNamevalueType addNewCallProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().add_element_user(CALLPROPERTY$4);
         return target;
      }
   }

   public void removeCallProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLPROPERTY$4, i);
      }
   }
}
