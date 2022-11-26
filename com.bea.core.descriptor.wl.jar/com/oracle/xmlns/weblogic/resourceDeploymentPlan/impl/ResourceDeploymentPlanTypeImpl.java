package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ConfigResourceOverrideType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ExternalResourceOverrideType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ResourceDeploymentPlanType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableDefinitionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ResourceDeploymentPlanTypeImpl extends XmlComplexContentImpl implements ResourceDeploymentPlanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "description");
   private static final QName VARIABLEDEFINITION$2 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "variable-definition");
   private static final QName EXTERNALRESOURCEOVERRIDE$4 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "external-resource-override");
   private static final QName CONFIGRESOURCEOVERRIDE$6 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "config-resource-override");
   private static final QName GLOBALVARIABLES$8 = new QName("", "global-variables");

   public ResourceDeploymentPlanTypeImpl(SchemaType sType) {
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

   public boolean isNilDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? false : target.isNil();
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

   public void setNilDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setNil();
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public VariableDefinitionType getVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableDefinitionType target = null;
         target = (VariableDefinitionType)this.get_store().find_element_user(VARIABLEDEFINITION$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLEDEFINITION$2) != 0;
      }
   }

   public void setVariableDefinition(VariableDefinitionType variableDefinition) {
      this.generatedSetterHelperImpl(variableDefinition, VARIABLEDEFINITION$2, 0, (short)1);
   }

   public VariableDefinitionType addNewVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableDefinitionType target = null;
         target = (VariableDefinitionType)this.get_store().add_element_user(VARIABLEDEFINITION$2);
         return target;
      }
   }

   public void unsetVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLEDEFINITION$2, 0);
      }
   }

   public ExternalResourceOverrideType[] getExternalResourceOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXTERNALRESOURCEOVERRIDE$4, targetList);
         ExternalResourceOverrideType[] result = new ExternalResourceOverrideType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExternalResourceOverrideType getExternalResourceOverrideArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExternalResourceOverrideType target = null;
         target = (ExternalResourceOverrideType)this.get_store().find_element_user(EXTERNALRESOURCEOVERRIDE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfExternalResourceOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXTERNALRESOURCEOVERRIDE$4);
      }
   }

   public void setExternalResourceOverrideArray(ExternalResourceOverrideType[] externalResourceOverrideArray) {
      this.check_orphaned();
      this.arraySetterHelper(externalResourceOverrideArray, EXTERNALRESOURCEOVERRIDE$4);
   }

   public void setExternalResourceOverrideArray(int i, ExternalResourceOverrideType externalResourceOverride) {
      this.generatedSetterHelperImpl(externalResourceOverride, EXTERNALRESOURCEOVERRIDE$4, i, (short)2);
   }

   public ExternalResourceOverrideType insertNewExternalResourceOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExternalResourceOverrideType target = null;
         target = (ExternalResourceOverrideType)this.get_store().insert_element_user(EXTERNALRESOURCEOVERRIDE$4, i);
         return target;
      }
   }

   public ExternalResourceOverrideType addNewExternalResourceOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExternalResourceOverrideType target = null;
         target = (ExternalResourceOverrideType)this.get_store().add_element_user(EXTERNALRESOURCEOVERRIDE$4);
         return target;
      }
   }

   public void removeExternalResourceOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXTERNALRESOURCEOVERRIDE$4, i);
      }
   }

   public ConfigResourceOverrideType[] getConfigResourceOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGRESOURCEOVERRIDE$6, targetList);
         ConfigResourceOverrideType[] result = new ConfigResourceOverrideType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigResourceOverrideType getConfigResourceOverrideArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigResourceOverrideType target = null;
         target = (ConfigResourceOverrideType)this.get_store().find_element_user(CONFIGRESOURCEOVERRIDE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConfigResourceOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGRESOURCEOVERRIDE$6);
      }
   }

   public void setConfigResourceOverrideArray(ConfigResourceOverrideType[] configResourceOverrideArray) {
      this.check_orphaned();
      this.arraySetterHelper(configResourceOverrideArray, CONFIGRESOURCEOVERRIDE$6);
   }

   public void setConfigResourceOverrideArray(int i, ConfigResourceOverrideType configResourceOverride) {
      this.generatedSetterHelperImpl(configResourceOverride, CONFIGRESOURCEOVERRIDE$6, i, (short)2);
   }

   public ConfigResourceOverrideType insertNewConfigResourceOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigResourceOverrideType target = null;
         target = (ConfigResourceOverrideType)this.get_store().insert_element_user(CONFIGRESOURCEOVERRIDE$6, i);
         return target;
      }
   }

   public ConfigResourceOverrideType addNewConfigResourceOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigResourceOverrideType target = null;
         target = (ConfigResourceOverrideType)this.get_store().add_element_user(CONFIGRESOURCEOVERRIDE$6);
         return target;
      }
   }

   public void removeConfigResourceOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGRESOURCEOVERRIDE$6, i);
      }
   }

   public boolean getGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(GLOBALVARIABLES$8);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(GLOBALVARIABLES$8);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(GLOBALVARIABLES$8);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(GLOBALVARIABLES$8);
         }

         return target;
      }
   }

   public boolean isSetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(GLOBALVARIABLES$8) != null;
      }
   }

   public void setGlobalVariables(boolean globalVariables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(GLOBALVARIABLES$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(GLOBALVARIABLES$8);
         }

         target.setBooleanValue(globalVariables);
      }
   }

   public void xsetGlobalVariables(XmlBoolean globalVariables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(GLOBALVARIABLES$8);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(GLOBALVARIABLES$8);
         }

         target.set(globalVariables);
      }
   }

   public void unsetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(GLOBALVARIABLES$8);
      }
   }
}
