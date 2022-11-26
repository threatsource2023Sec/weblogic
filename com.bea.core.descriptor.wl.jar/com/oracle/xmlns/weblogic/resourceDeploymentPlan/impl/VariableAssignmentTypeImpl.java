package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableAssignmentType;
import com.sun.java.xml.ns.j2Ee.PathType;
import javax.xml.namespace.QName;

public class VariableAssignmentTypeImpl extends XmlComplexContentImpl implements VariableAssignmentType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "description");
   private static final QName NAME$2 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "name");
   private static final QName XPATH$4 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "xpath");
   private static final QName ORIGIN$6 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "origin");
   private static final QName OPERATION$8 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "operation");

   public VariableAssignmentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.set(description);
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$2);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$2);
         }

         target.set(name);
      }
   }

   public PathType getXpath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(XPATH$4, 0);
         return target == null ? null : target;
      }
   }

   public void setXpath(PathType xpath) {
      this.generatedSetterHelperImpl(xpath, XPATH$4, 0, (short)1);
   }

   public PathType addNewXpath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(XPATH$4);
         return target;
      }
   }

   public String getOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORIGIN$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORIGIN$6, 0);
         return target;
      }
   }

   public boolean isSetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORIGIN$6) != 0;
      }
   }

   public void setOrigin(String origin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORIGIN$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ORIGIN$6);
         }

         target.setStringValue(origin);
      }
   }

   public void xsetOrigin(XmlString origin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORIGIN$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ORIGIN$6);
         }

         target.set(origin);
      }
   }

   public void unsetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORIGIN$6, 0);
      }
   }

   public VariableAssignmentType.Operation.Enum getOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPERATION$8, 0);
         return target == null ? null : (VariableAssignmentType.Operation.Enum)target.getEnumValue();
      }
   }

   public VariableAssignmentType.Operation xgetOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType.Operation target = null;
         target = (VariableAssignmentType.Operation)this.get_store().find_element_user(OPERATION$8, 0);
         return target;
      }
   }

   public boolean isSetOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPERATION$8) != 0;
      }
   }

   public void setOperation(VariableAssignmentType.Operation.Enum operation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPERATION$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OPERATION$8);
         }

         target.setEnumValue(operation);
      }
   }

   public void xsetOperation(VariableAssignmentType.Operation operation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType.Operation target = null;
         target = (VariableAssignmentType.Operation)this.get_store().find_element_user(OPERATION$8, 0);
         if (target == null) {
            target = (VariableAssignmentType.Operation)this.get_store().add_element_user(OPERATION$8);
         }

         target.set(operation);
      }
   }

   public void unsetOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPERATION$8, 0);
      }
   }

   public static class OperationImpl extends JavaStringEnumerationHolderEx implements VariableAssignmentType.Operation {
      private static final long serialVersionUID = 1L;

      public OperationImpl(SchemaType sType) {
         super(sType, false);
      }

      protected OperationImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
