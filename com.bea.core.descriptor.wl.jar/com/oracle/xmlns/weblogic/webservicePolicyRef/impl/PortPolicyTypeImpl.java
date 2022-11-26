package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.OwsmSecurityPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.PortPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WsPolicyType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PortPolicyTypeImpl extends XmlComplexContentImpl implements PortPolicyType {
   private static final long serialVersionUID = 1L;
   private static final QName PORTNAME$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "port-name");
   private static final QName WSPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "ws-policy");
   private static final QName OWSMSECURITYPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "owsm-security-policy");

   public PortPolicyTypeImpl(SchemaType sType) {
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

   public WsPolicyType[] getWsPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WSPOLICY$2, targetList);
         WsPolicyType[] result = new WsPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WsPolicyType getWsPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().find_element_user(WSPOLICY$2, i);
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
         return this.get_store().count_elements(WSPOLICY$2);
      }
   }

   public void setWsPolicyArray(WsPolicyType[] wsPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(wsPolicyArray, WSPOLICY$2);
   }

   public void setWsPolicyArray(int i, WsPolicyType wsPolicy) {
      this.generatedSetterHelperImpl(wsPolicy, WSPOLICY$2, i, (short)2);
   }

   public WsPolicyType insertNewWsPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().insert_element_user(WSPOLICY$2, i);
         return target;
      }
   }

   public WsPolicyType addNewWsPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsPolicyType target = null;
         target = (WsPolicyType)this.get_store().add_element_user(WSPOLICY$2);
         return target;
      }
   }

   public void removeWsPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSPOLICY$2, i);
      }
   }

   public OwsmSecurityPolicyType[] getOwsmSecurityPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OWSMSECURITYPOLICY$4, targetList);
         OwsmSecurityPolicyType[] result = new OwsmSecurityPolicyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OwsmSecurityPolicyType getOwsmSecurityPolicyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmSecurityPolicyType target = null;
         target = (OwsmSecurityPolicyType)this.get_store().find_element_user(OWSMSECURITYPOLICY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOwsmSecurityPolicyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OWSMSECURITYPOLICY$4);
      }
   }

   public void setOwsmSecurityPolicyArray(OwsmSecurityPolicyType[] owsmSecurityPolicyArray) {
      this.check_orphaned();
      this.arraySetterHelper(owsmSecurityPolicyArray, OWSMSECURITYPOLICY$4);
   }

   public void setOwsmSecurityPolicyArray(int i, OwsmSecurityPolicyType owsmSecurityPolicy) {
      this.generatedSetterHelperImpl(owsmSecurityPolicy, OWSMSECURITYPOLICY$4, i, (short)2);
   }

   public OwsmSecurityPolicyType insertNewOwsmSecurityPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmSecurityPolicyType target = null;
         target = (OwsmSecurityPolicyType)this.get_store().insert_element_user(OWSMSECURITYPOLICY$4, i);
         return target;
      }
   }

   public OwsmSecurityPolicyType addNewOwsmSecurityPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OwsmSecurityPolicyType target = null;
         target = (OwsmSecurityPolicyType)this.get_store().add_element_user(OWSMSECURITYPOLICY$4);
         return target;
      }
   }

   public void removeOwsmSecurityPolicy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OWSMSECURITYPOLICY$4, i);
      }
   }
}
