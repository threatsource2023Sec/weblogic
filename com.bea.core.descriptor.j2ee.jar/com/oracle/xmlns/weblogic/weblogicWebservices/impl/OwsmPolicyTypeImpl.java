package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.OwsmPolicyType;
import com.oracle.xmlns.weblogic.weblogicWebservices.PropertyNamevalueType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OwsmPolicyTypeImpl extends XmlComplexContentImpl implements OwsmPolicyType {
   private static final long serialVersionUID = 1L;
   private static final QName URI$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "uri");
   private static final QName STATUS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "status");
   private static final QName CATEGORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "category");
   private static final QName SECURITYCONFIGURATIONPROPERTY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "security-configuration-property");

   public OwsmPolicyTypeImpl(SchemaType sType) {
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

   public String getStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(STATUS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATUS$2) != 0;
      }
   }

   public void setStatus(String status) {
      this.generatedSetterHelperImpl(status, STATUS$2, 0, (short)1);
   }

   public String addNewStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(STATUS$2);
         return target;
      }
   }

   public void unsetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATUS$2, 0);
      }
   }

   public String getCategory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CATEGORY$4, 0);
         return target == null ? null : target;
      }
   }

   public void setCategory(String category) {
      this.generatedSetterHelperImpl(category, CATEGORY$4, 0, (short)1);
   }

   public String addNewCategory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CATEGORY$4);
         return target;
      }
   }

   public PropertyNamevalueType[] getSecurityConfigurationPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYCONFIGURATIONPROPERTY$6, targetList);
         PropertyNamevalueType[] result = new PropertyNamevalueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyNamevalueType getSecurityConfigurationPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().find_element_user(SECURITYCONFIGURATIONPROPERTY$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityConfigurationPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYCONFIGURATIONPROPERTY$6);
      }
   }

   public void setSecurityConfigurationPropertyArray(PropertyNamevalueType[] securityConfigurationPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityConfigurationPropertyArray, SECURITYCONFIGURATIONPROPERTY$6);
   }

   public void setSecurityConfigurationPropertyArray(int i, PropertyNamevalueType securityConfigurationProperty) {
      this.generatedSetterHelperImpl(securityConfigurationProperty, SECURITYCONFIGURATIONPROPERTY$6, i, (short)2);
   }

   public PropertyNamevalueType insertNewSecurityConfigurationProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().insert_element_user(SECURITYCONFIGURATIONPROPERTY$6, i);
         return target;
      }
   }

   public PropertyNamevalueType addNewSecurityConfigurationProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyNamevalueType target = null;
         target = (PropertyNamevalueType)this.get_store().add_element_user(SECURITYCONFIGURATIONPROPERTY$6);
         return target;
      }
   }

   public void removeSecurityConfigurationProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYCONFIGURATIONPROPERTY$6, i);
      }
   }
}
