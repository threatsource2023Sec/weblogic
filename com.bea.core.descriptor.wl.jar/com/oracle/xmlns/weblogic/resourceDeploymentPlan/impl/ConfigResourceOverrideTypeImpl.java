package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ConfigResourceOverrideType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableAssignmentType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConfigResourceOverrideTypeImpl extends XmlComplexContentImpl implements ConfigResourceOverrideType {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCENAME$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "resource-name");
   private static final QName RESOURCETYPE$2 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "resource-type");
   private static final QName VARIABLEASSIGNMENT$4 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "variable-assignment");

   public ConfigResourceOverrideTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getResourceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResourceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCENAME$0, 0);
         return target;
      }
   }

   public void setResourceName(String resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCENAME$0);
         }

         target.setStringValue(resourceName);
      }
   }

   public void xsetResourceName(XmlString resourceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCENAME$0);
         }

         target.set(resourceName);
      }
   }

   public String getResourceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCETYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResourceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCETYPE$2, 0);
         return target;
      }
   }

   public void setResourceType(String resourceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCETYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCETYPE$2);
         }

         target.setStringValue(resourceType);
      }
   }

   public void xsetResourceType(XmlString resourceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCETYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCETYPE$2);
         }

         target.set(resourceType);
      }
   }

   public VariableAssignmentType[] getVariableAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(VARIABLEASSIGNMENT$4, targetList);
         VariableAssignmentType[] result = new VariableAssignmentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public VariableAssignmentType getVariableAssignmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().find_element_user(VARIABLEASSIGNMENT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfVariableAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLEASSIGNMENT$4);
      }
   }

   public void setVariableAssignmentArray(VariableAssignmentType[] variableAssignmentArray) {
      this.check_orphaned();
      this.arraySetterHelper(variableAssignmentArray, VARIABLEASSIGNMENT$4);
   }

   public void setVariableAssignmentArray(int i, VariableAssignmentType variableAssignment) {
      this.generatedSetterHelperImpl(variableAssignment, VARIABLEASSIGNMENT$4, i, (short)2);
   }

   public VariableAssignmentType insertNewVariableAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().insert_element_user(VARIABLEASSIGNMENT$4, i);
         return target;
      }
   }

   public VariableAssignmentType addNewVariableAssignment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().add_element_user(VARIABLEASSIGNMENT$4);
         return target;
      }
   }

   public void removeVariableAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLEASSIGNMENT$4, i);
      }
   }
}
