package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.webservicePolicyRef.OperationPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.PortPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WebservicePolicyRefType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WebservicePolicyRefTypeImpl extends XmlComplexContentImpl implements WebservicePolicyRefType {
   private static final long serialVersionUID = 1L;
   private static final QName REFNAME$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "ref-name");
   private static final QName PORTPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "port-policy");
   private static final QName OPERATIONPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "operation-policy");
   private static final QName VERSION$6 = new QName("", "version");

   public WebservicePolicyRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(REFNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REFNAME$0) != 0;
      }
   }

   public void setRefName(String refName) {
      this.generatedSetterHelperImpl(refName, REFNAME$0, 0, (short)1);
   }

   public String addNewRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(REFNAME$0);
         return target;
      }
   }

   public void unsetRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REFNAME$0, 0);
      }
   }

   public PortPolicyType[] getPortPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTPOLICY$2, targetList);
         PortPolicyType[] result = new PortPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortPolicyType getPortPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortPolicyType target = null;
         target = (PortPolicyType)this.get_store().find_element_user(PORTPOLICY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTPOLICY$2);
      }
   }

   public void setPortPolicyArray(PortPolicyType[] portPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(portPolicyArray, PORTPOLICY$2);
   }

   public void setPortPolicyArray(int i, PortPolicyType portPolicy) {
      this.generatedSetterHelperImpl(portPolicy, PORTPOLICY$2, i, (short)2);
   }

   public PortPolicyType insertNewPortPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortPolicyType target = null;
         target = (PortPolicyType)this.get_store().insert_element_user(PORTPOLICY$2, i);
         return target;
      }
   }

   public PortPolicyType addNewPortPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortPolicyType target = null;
         target = (PortPolicyType)this.get_store().add_element_user(PORTPOLICY$2);
         return target;
      }
   }

   public void removePortPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTPOLICY$2, i);
      }
   }

   public OperationPolicyType[] getOperationPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OPERATIONPOLICY$4, targetList);
         OperationPolicyType[] result = new OperationPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OperationPolicyType getOperationPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationPolicyType target = null;
         target = (OperationPolicyType)this.get_store().find_element_user(OPERATIONPOLICY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOperationPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPERATIONPOLICY$4);
      }
   }

   public void setOperationPolicyArray(OperationPolicyType[] operationPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(operationPolicyArray, OPERATIONPOLICY$4);
   }

   public void setOperationPolicyArray(int i, OperationPolicyType operationPolicy) {
      this.generatedSetterHelperImpl(operationPolicy, OPERATIONPOLICY$4, i, (short)2);
   }

   public OperationPolicyType insertNewOperationPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationPolicyType target = null;
         target = (OperationPolicyType)this.get_store().insert_element_user(OPERATIONPOLICY$4, i);
         return target;
      }
   }

   public OperationPolicyType addNewOperationPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationPolicyType target = null;
         target = (OperationPolicyType)this.get_store().add_element_user(OPERATIONPOLICY$4);
         return target;
      }
   }

   public void removeOperationPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPERATIONPOLICY$4, i);
      }
   }

   public java.lang.String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$6);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$6) != null;
      }
   }

   public void setVersion(java.lang.String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$6);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$6);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$6);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$6);
      }
   }
}
