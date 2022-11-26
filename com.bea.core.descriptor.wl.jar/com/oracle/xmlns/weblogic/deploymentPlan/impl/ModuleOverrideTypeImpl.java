package com.oracle.xmlns.weblogic.deploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.deploymentPlan.ModuleDescriptorType;
import com.oracle.xmlns.weblogic.deploymentPlan.ModuleOverrideType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ModuleOverrideTypeImpl extends XmlComplexContentImpl implements ModuleOverrideType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULENAME$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "module-name");
   private static final QName MODULETYPE$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "module-type");
   private static final QName MODULEDESCRIPTOR$4 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "module-descriptor");

   public ModuleOverrideTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULENAME$0, 0);
         return target;
      }
   }

   public void setModuleName(String moduleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MODULENAME$0);
         }

         target.setStringValue(moduleName);
      }
   }

   public void xsetModuleName(XmlString moduleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MODULENAME$0);
         }

         target.set(moduleName);
      }
   }

   public String getModuleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULETYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetModuleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULETYPE$2, 0);
         return target;
      }
   }

   public void setModuleType(String moduleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULETYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MODULETYPE$2);
         }

         target.setStringValue(moduleType);
      }
   }

   public void xsetModuleType(XmlString moduleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULETYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MODULETYPE$2);
         }

         target.set(moduleType);
      }
   }

   public ModuleDescriptorType[] getModuleDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MODULEDESCRIPTOR$4, targetList);
         ModuleDescriptorType[] result = new ModuleDescriptorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ModuleDescriptorType getModuleDescriptorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleDescriptorType target = null;
         target = (ModuleDescriptorType)this.get_store().find_element_user(MODULEDESCRIPTOR$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfModuleDescriptorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULEDESCRIPTOR$4);
      }
   }

   public void setModuleDescriptorArray(ModuleDescriptorType[] moduleDescriptorArray) {
      this.check_orphaned();
      this.arraySetterHelper(moduleDescriptorArray, MODULEDESCRIPTOR$4);
   }

   public void setModuleDescriptorArray(int i, ModuleDescriptorType moduleDescriptor) {
      this.generatedSetterHelperImpl(moduleDescriptor, MODULEDESCRIPTOR$4, i, (short)2);
   }

   public ModuleDescriptorType insertNewModuleDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleDescriptorType target = null;
         target = (ModuleDescriptorType)this.get_store().insert_element_user(MODULEDESCRIPTOR$4, i);
         return target;
      }
   }

   public ModuleDescriptorType addNewModuleDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleDescriptorType target = null;
         target = (ModuleDescriptorType)this.get_store().add_element_user(MODULEDESCRIPTOR$4);
         return target;
      }
   }

   public void removeModuleDescriptor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULEDESCRIPTOR$4, i);
      }
   }
}
