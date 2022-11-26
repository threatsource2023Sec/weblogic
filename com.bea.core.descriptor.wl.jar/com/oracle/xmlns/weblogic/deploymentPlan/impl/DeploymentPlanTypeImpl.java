package com.oracle.xmlns.weblogic.deploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.deploymentPlan.DeploymentPlanType;
import com.oracle.xmlns.weblogic.deploymentPlan.ModuleOverrideType;
import com.oracle.xmlns.weblogic.deploymentPlan.VariableDefinitionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DeploymentPlanTypeImpl extends XmlComplexContentImpl implements DeploymentPlanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "description");
   private static final QName APPLICATIONNAME$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "application-name");
   private static final QName VERSION$4 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "version");
   private static final QName VARIABLEDEFINITION$6 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "variable-definition");
   private static final QName MODULEOVERRIDE$8 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "module-override");
   private static final QName CONFIGROOT$10 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "config-root");
   private static final QName GLOBALVARIABLES$12 = new QName("", "global-variables");

   public DeploymentPlanTypeImpl(SchemaType sType) {
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

   public String getApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetApplicationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONNAME$2, 0);
         return target;
      }
   }

   public void setApplicationName(String applicationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(APPLICATIONNAME$2);
         }

         target.setStringValue(applicationName);
      }
   }

   public void xsetApplicationName(XmlString applicationName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(APPLICATIONNAME$2);
         }

         target.set(applicationName);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$4, 0);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSION$4) != 0;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSION$4);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSION$4);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSION$4, 0);
      }
   }

   public VariableDefinitionType getVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableDefinitionType target = null;
         target = (VariableDefinitionType)this.get_store().find_element_user(VARIABLEDEFINITION$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLEDEFINITION$6) != 0;
      }
   }

   public void setVariableDefinition(VariableDefinitionType variableDefinition) {
      this.generatedSetterHelperImpl(variableDefinition, VARIABLEDEFINITION$6, 0, (short)1);
   }

   public VariableDefinitionType addNewVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableDefinitionType target = null;
         target = (VariableDefinitionType)this.get_store().add_element_user(VARIABLEDEFINITION$6);
         return target;
      }
   }

   public void unsetVariableDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLEDEFINITION$6, 0);
      }
   }

   public ModuleOverrideType[] getModuleOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MODULEOVERRIDE$8, targetList);
         ModuleOverrideType[] result = new ModuleOverrideType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ModuleOverrideType getModuleOverrideArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleOverrideType target = null;
         target = (ModuleOverrideType)this.get_store().find_element_user(MODULEOVERRIDE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfModuleOverrideArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULEOVERRIDE$8);
      }
   }

   public void setModuleOverrideArray(ModuleOverrideType[] moduleOverrideArray) {
      this.check_orphaned();
      this.arraySetterHelper(moduleOverrideArray, MODULEOVERRIDE$8);
   }

   public void setModuleOverrideArray(int i, ModuleOverrideType moduleOverride) {
      this.generatedSetterHelperImpl(moduleOverride, MODULEOVERRIDE$8, i, (short)2);
   }

   public ModuleOverrideType insertNewModuleOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleOverrideType target = null;
         target = (ModuleOverrideType)this.get_store().insert_element_user(MODULEOVERRIDE$8, i);
         return target;
      }
   }

   public ModuleOverrideType addNewModuleOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleOverrideType target = null;
         target = (ModuleOverrideType)this.get_store().add_element_user(MODULEOVERRIDE$8);
         return target;
      }
   }

   public void removeModuleOverride(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULEOVERRIDE$8, i);
      }
   }

   public String getConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGROOT$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOT$10, 0);
         return target;
      }
   }

   public boolean isNilConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOT$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGROOT$10) != 0;
      }
   }

   public void setConfigRoot(String configRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGROOT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGROOT$10);
         }

         target.setStringValue(configRoot);
      }
   }

   public void xsetConfigRoot(XmlString configRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOT$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGROOT$10);
         }

         target.set(configRoot);
      }
   }

   public void setNilConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOT$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGROOT$10);
         }

         target.setNil();
      }
   }

   public void unsetConfigRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGROOT$10, 0);
      }
   }

   public boolean getGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(GLOBALVARIABLES$12);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(GLOBALVARIABLES$12);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(GLOBALVARIABLES$12);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(GLOBALVARIABLES$12);
         }

         return target;
      }
   }

   public boolean isSetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(GLOBALVARIABLES$12) != null;
      }
   }

   public void setGlobalVariables(boolean globalVariables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(GLOBALVARIABLES$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(GLOBALVARIABLES$12);
         }

         target.setBooleanValue(globalVariables);
      }
   }

   public void xsetGlobalVariables(XmlBoolean globalVariables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(GLOBALVARIABLES$12);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(GLOBALVARIABLES$12);
         }

         target.set(globalVariables);
      }
   }

   public void unsetGlobalVariables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(GLOBALVARIABLES$12);
      }
   }
}
