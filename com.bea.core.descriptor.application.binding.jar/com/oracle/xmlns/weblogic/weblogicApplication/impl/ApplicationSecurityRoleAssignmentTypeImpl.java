package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationSecurityRoleAssignmentType;
import com.sun.java.xml.ns.javaee.EmptyType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ApplicationSecurityRoleAssignmentTypeImpl extends XmlComplexContentImpl implements ApplicationSecurityRoleAssignmentType {
   private static final long serialVersionUID = 1L;
   private static final QName ROLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "role-name");
   private static final QName PRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "principal-name");
   private static final QName EXTERNALLYDEFINED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "externally-defined");

   public ApplicationSecurityRoleAssignmentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROLENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROLENAME$0, 0);
         return target;
      }
   }

   public void setRoleName(String roleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROLENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROLENAME$0);
         }

         target.setStringValue(roleName);
      }
   }

   public void xsetRoleName(XmlString roleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROLENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ROLENAME$0);
         }

         target.set(roleName);
      }
   }

   public String[] getPrincipalNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PRINCIPALNAME$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getPrincipalNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRINCIPALNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetPrincipalNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PRINCIPALNAME$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetPrincipalNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRINCIPALNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPrincipalNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRINCIPALNAME$2);
      }
   }

   public void setPrincipalNameArray(String[] principalNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(principalNameArray, PRINCIPALNAME$2);
      }
   }

   public void setPrincipalNameArray(int i, String principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRINCIPALNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(principalName);
         }
      }
   }

   public void xsetPrincipalNameArray(XmlString[] principalNameArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(principalNameArray, PRINCIPALNAME$2);
      }
   }

   public void xsetPrincipalNameArray(int i, XmlString principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRINCIPALNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(principalName);
         }
      }
   }

   public void insertPrincipalName(int i, String principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(PRINCIPALNAME$2, i);
         target.setStringValue(principalName);
      }
   }

   public void addPrincipalName(String principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(PRINCIPALNAME$2);
         target.setStringValue(principalName);
      }
   }

   public XmlString insertNewPrincipalName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(PRINCIPALNAME$2, i);
         return target;
      }
   }

   public XmlString addNewPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(PRINCIPALNAME$2);
         return target;
      }
   }

   public void removePrincipalName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRINCIPALNAME$2, i);
      }
   }

   public EmptyType getExternallyDefined() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(EXTERNALLYDEFINED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExternallyDefined() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXTERNALLYDEFINED$4) != 0;
      }
   }

   public void setExternallyDefined(EmptyType externallyDefined) {
      this.generatedSetterHelperImpl(externallyDefined, EXTERNALLYDEFINED$4, 0, (short)1);
   }

   public EmptyType addNewExternallyDefined() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(EXTERNALLYDEFINED$4);
         return target;
      }
   }

   public void unsetExternallyDefined() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXTERNALLYDEFINED$4, 0);
      }
   }
}
