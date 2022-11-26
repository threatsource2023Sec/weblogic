package com.oracle.xmlns.weblogic.jdbcDataSource.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.jdbcDataSource.OperationInfoType;
import com.oracle.xmlns.weblogic.jdbcDataSource.OwsmPolicyType;
import com.oracle.xmlns.weblogic.jdbcDataSource.PortInfoType;
import com.oracle.xmlns.weblogic.jdbcDataSource.PropertyNamevalueType;
import com.oracle.xmlns.weblogic.jdbcDataSource.WsatConfigType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PortInfoTypeImpl extends XmlComplexContentImpl implements PortInfoType {
   private static final long serialVersionUID = 1L;
   private static final QName PORTNAME$0 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "port-name");
   private static final QName STUBPROPERTY$2 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "stub-property");
   private static final QName CALLPROPERTY$4 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "call-property");
   private static final QName WSATCONFIG$6 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "wsat-config");
   private static final QName OWSMPOLICY$8 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "owsm-policy");
   private static final QName OPERATION$10 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "operation");

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

   public WsatConfigType getWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().find_element_user(WSATCONFIG$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSATCONFIG$6) != 0;
      }
   }

   public void setWsatConfig(WsatConfigType wsatConfig) {
      this.generatedSetterHelperImpl(wsatConfig, WSATCONFIG$6, 0, (short)1);
   }

   public WsatConfigType addNewWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().add_element_user(WSATCONFIG$6);
         return target;
      }
   }

   public void unsetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSATCONFIG$6, 0);
      }
   }

   public OwsmPolicyType[] getOwsmPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OWSMPOLICY$8, targetList);
         OwsmPolicyType[] result = new OwsmPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OwsmPolicyType getOwsmPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmPolicyType target = null;
         target = (OwsmPolicyType)this.get_store().find_element_user(OWSMPOLICY$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOwsmPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OWSMPOLICY$8);
      }
   }

   public void setOwsmPolicyArray(OwsmPolicyType[] owsmPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(owsmPolicyArray, OWSMPOLICY$8);
   }

   public void setOwsmPolicyArray(int i, OwsmPolicyType owsmPolicy) {
      this.generatedSetterHelperImpl(owsmPolicy, OWSMPOLICY$8, i, (short)2);
   }

   public OwsmPolicyType insertNewOwsmPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmPolicyType target = null;
         target = (OwsmPolicyType)this.get_store().insert_element_user(OWSMPOLICY$8, i);
         return target;
      }
   }

   public OwsmPolicyType addNewOwsmPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmPolicyType target = null;
         target = (OwsmPolicyType)this.get_store().add_element_user(OWSMPOLICY$8);
         return target;
      }
   }

   public void removeOwsmPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OWSMPOLICY$8, i);
      }
   }

   public OperationInfoType[] getOperationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OPERATION$10, targetList);
         OperationInfoType[] result = new OperationInfoType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OperationInfoType getOperationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationInfoType target = null;
         target = (OperationInfoType)this.get_store().find_element_user(OPERATION$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOperationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPERATION$10);
      }
   }

   public void setOperationArray(OperationInfoType[] operationArray) {
      this.check_orphaned();
      this.arraySetterHelper(operationArray, OPERATION$10);
   }

   public void setOperationArray(int i, OperationInfoType operation) {
      this.generatedSetterHelperImpl(operation, OPERATION$10, i, (short)2);
   }

   public OperationInfoType insertNewOperation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationInfoType target = null;
         target = (OperationInfoType)this.get_store().insert_element_user(OPERATION$10, i);
         return target;
      }
   }

   public OperationInfoType addNewOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationInfoType target = null;
         target = (OperationInfoType)this.get_store().add_element_user(OPERATION$10);
         return target;
      }
   }

   public void removeOperation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPERATION$10, i);
      }
   }
}
