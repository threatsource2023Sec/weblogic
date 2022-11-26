package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.SecurityRoleAssignmentType;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.RoleNameType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SecurityRoleAssignmentTypeImpl extends XmlComplexContentImpl implements SecurityRoleAssignmentType {
   private static final long serialVersionUID = 1L;
   private static final QName ROLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "role-name");
   private static final QName PRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "principal-name");
   private static final QName EXTERNALLYDEFINED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "externally-defined");
   private static final QName ID$6 = new QName("", "id");

   public SecurityRoleAssignmentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public RoleNameType getRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RoleNameType target = null;
         target = (RoleNameType)this.get_store().find_element_user(ROLENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setRoleName(RoleNameType roleName) {
      this.generatedSetterHelperImpl(roleName, ROLENAME$0, 0, (short)1);
   }

   public RoleNameType addNewRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RoleNameType target = null;
         target = (RoleNameType)this.get_store().add_element_user(ROLENAME$0);
         return target;
      }
   }

   public String[] getPrincipalNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PRINCIPALNAME$2, targetList);
         String[] result = new String[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public String getPrincipalNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PRINCIPALNAME$2, i);
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
      this.check_orphaned();
      this.arraySetterHelper(principalNameArray, PRINCIPALNAME$2);
   }

   public void setPrincipalNameArray(int i, String principalName) {
      this.generatedSetterHelperImpl(principalName, PRINCIPALNAME$2, i, (short)2);
   }

   public String insertNewPrincipalName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().insert_element_user(PRINCIPALNAME$2, i);
         return target;
      }
   }

   public String addNewPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PRINCIPALNAME$2);
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

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
