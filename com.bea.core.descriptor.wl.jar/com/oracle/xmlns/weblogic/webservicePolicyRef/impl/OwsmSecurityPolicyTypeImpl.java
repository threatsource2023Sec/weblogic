package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.oracle.xmlns.weblogic.webservicePolicyRef.OwsmSecurityPolicyType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.StatusType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class OwsmSecurityPolicyTypeImpl extends XmlComplexContentImpl implements OwsmSecurityPolicyType {
   private static final long serialVersionUID = 1L;
   private static final QName URI$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "uri");
   private static final QName STATUS$2 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "status");

   public OwsmSecurityPolicyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(URI$0, 0);
         return target == null ? null : target;
      }
   }

   public void setUri(String uri) {
      this.generatedSetterHelperImpl(uri, URI$0, 0, (short)1);
   }

   public String addNewUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(URI$0);
         return target;
      }
   }

   public StatusType.Enum getStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$2, 0);
         return target == null ? null : (StatusType.Enum)target.getEnumValue();
      }
   }

   public StatusType xgetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatusType target = null;
         target = (StatusType)this.get_store().find_element_user(STATUS$2, 0);
         return target;
      }
   }

   public boolean isSetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATUS$2) != 0;
      }
   }

   public void setStatus(StatusType.Enum status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATUS$2);
         }

         target.setEnumValue(status);
      }
   }

   public void xsetStatus(StatusType status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatusType target = null;
         target = (StatusType)this.get_store().find_element_user(STATUS$2, 0);
         if (target == null) {
            target = (StatusType)this.get_store().add_element_user(STATUS$2);
         }

         target.set(status);
      }
   }

   public void unsetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATUS$2, 0);
      }
   }
}
