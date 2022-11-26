package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.OperationPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WsPolicyType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OperationPolicyTypeImpl extends XmlComplexContentImpl implements OperationPolicyType {
   private static final long serialVersionUID = 1L;
   private static final QName OPERATIONNAME$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "operation-name");
   private static final QName SERVICELINK$2 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "service-link");
   private static final QName WSPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "ws-policy");

   public OperationPolicyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getOperationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(OPERATIONNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setOperationName(String operationName) {
      this.generatedSetterHelperImpl(operationName, OPERATIONNAME$0, 0, (short)1);
   }

   public String addNewOperationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(OPERATIONNAME$0);
         return target;
      }
   }

   public String getServiceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SERVICELINK$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICELINK$2) != 0;
      }
   }

   public void setServiceLink(String serviceLink) {
      this.generatedSetterHelperImpl(serviceLink, SERVICELINK$2, 0, (short)1);
   }

   public String addNewServiceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SERVICELINK$2);
         return target;
      }
   }

   public void unsetServiceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICELINK$2, 0);
      }
   }

   public WsPolicyType[] getWsPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WSPOLICY$4, targetList);
         WsPolicyType[] result = new WsPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WsPolicyType getWsPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().find_element_user(WSPOLICY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWsPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSPOLICY$4);
      }
   }

   public void setWsPolicyArray(WsPolicyType[] wsPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(wsPolicyArray, WSPOLICY$4);
   }

   public void setWsPolicyArray(int i, WsPolicyType wsPolicy) {
      this.generatedSetterHelperImpl(wsPolicy, WSPOLICY$4, i, (short)2);
   }

   public WsPolicyType insertNewWsPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().insert_element_user(WSPOLICY$4, i);
         return target;
      }
   }

   public WsPolicyType addNewWsPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().add_element_user(WSPOLICY$4);
         return target;
      }
   }

   public void removeWsPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSPOLICY$4, i);
      }
   }
}
