package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicApplication.RunAsRoleAssignmentType;
import com.sun.java.xml.ns.javaee.RoleNameType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class RunAsRoleAssignmentTypeImpl extends XmlComplexContentImpl implements RunAsRoleAssignmentType {
   private static final long serialVersionUID = 1L;
   private static final QName ROLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "role-name");
   private static final QName RUNASPRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "run-as-principal-name");
   private static final QName ID$4 = new QName("", "id");

   public RunAsRoleAssignmentTypeImpl(SchemaType sType) {
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

   public String getRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASPRINCIPALNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setRunAsPrincipalName(String runAsPrincipalName) {
      this.generatedSetterHelperImpl(runAsPrincipalName, RUNASPRINCIPALNAME$2, 0, (short)1);
   }

   public String addNewRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RUNASPRINCIPALNAME$2);
         return target;
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
