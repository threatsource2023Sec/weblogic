package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationSecurityRoleAssignmentType;
import com.oracle.xmlns.weblogic.weblogicApplication.SecurityType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SecurityTypeImpl extends XmlComplexContentImpl implements SecurityType {
   private static final long serialVersionUID = 1L;
   private static final QName REALMNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "realm-name");
   private static final QName SECURITYROLEASSIGNMENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "security-role-assignment");

   public SecurityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REALMNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALMNAME$0, 0);
         return target;
      }
   }

   public boolean isSetRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REALMNAME$0) != 0;
      }
   }

   public void setRealmName(String realmName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REALMNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REALMNAME$0);
         }

         target.setStringValue(realmName);
      }
   }

   public void xsetRealmName(XmlString realmName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALMNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REALMNAME$0);
         }

         target.set(realmName);
      }
   }

   public void unsetRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REALMNAME$0, 0);
      }
   }

   public ApplicationSecurityRoleAssignmentType[] getSecurityRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEASSIGNMENT$2, targetList);
         ApplicationSecurityRoleAssignmentType[] result = new ApplicationSecurityRoleAssignmentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ApplicationSecurityRoleAssignmentType getSecurityRoleAssignmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationSecurityRoleAssignmentType target = null;
         target = (ApplicationSecurityRoleAssignmentType)this.get_store().find_element_user(SECURITYROLEASSIGNMENT$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLEASSIGNMENT$2);
      }
   }

   public void setSecurityRoleAssignmentArray(ApplicationSecurityRoleAssignmentType[] securityRoleAssignmentArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleAssignmentArray, SECURITYROLEASSIGNMENT$2);
   }

   public void setSecurityRoleAssignmentArray(int i, ApplicationSecurityRoleAssignmentType securityRoleAssignment) {
      this.generatedSetterHelperImpl(securityRoleAssignment, SECURITYROLEASSIGNMENT$2, i, (short)2);
   }

   public ApplicationSecurityRoleAssignmentType insertNewSecurityRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationSecurityRoleAssignmentType target = null;
         target = (ApplicationSecurityRoleAssignmentType)this.get_store().insert_element_user(SECURITYROLEASSIGNMENT$2, i);
         return target;
      }
   }

   public ApplicationSecurityRoleAssignmentType addNewSecurityRoleAssignment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationSecurityRoleAssignmentType target = null;
         target = (ApplicationSecurityRoleAssignmentType)this.get_store().add_element_user(SECURITYROLEASSIGNMENT$2);
         return target;
      }
   }

   public void removeSecurityRoleAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEASSIGNMENT$2, i);
      }
   }
}
