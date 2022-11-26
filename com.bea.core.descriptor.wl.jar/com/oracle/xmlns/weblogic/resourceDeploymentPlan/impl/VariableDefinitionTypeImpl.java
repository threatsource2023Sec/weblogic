package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableDefinitionType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class VariableDefinitionTypeImpl extends XmlComplexContentImpl implements VariableDefinitionType {
   private static final long serialVersionUID = 1L;
   private static final QName VARIABLE$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "variable");

   public VariableDefinitionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public VariableType[] getVariableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(VARIABLE$0, targetList);
         VariableType[] result = new VariableType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public VariableType getVariableArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableType target = null;
         target = (VariableType)this.get_store().find_element_user(VARIABLE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfVariableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLE$0);
      }
   }

   public void setVariableArray(VariableType[] variableArray) {
      this.check_orphaned();
      this.arraySetterHelper(variableArray, VARIABLE$0);
   }

   public void setVariableArray(int i, VariableType variable) {
      this.generatedSetterHelperImpl(variable, VARIABLE$0, i, (short)2);
   }

   public VariableType insertNewVariable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableType target = null;
         target = (VariableType)this.get_store().insert_element_user(VARIABLE$0, i);
         return target;
      }
   }

   public VariableType addNewVariable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableType target = null;
         target = (VariableType)this.get_store().add_element_user(VARIABLE$0);
         return target;
      }
   }

   public void removeVariable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLE$0, i);
      }
   }
}
