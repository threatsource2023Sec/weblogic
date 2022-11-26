package com.oracle.xmlns.weblogic.weblogicExtension.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicExtension.CustomModuleType;
import com.oracle.xmlns.weblogic.weblogicExtension.ModuleProviderType;
import com.oracle.xmlns.weblogic.weblogicExtension.WeblogicExtensionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicExtensionTypeImpl extends XmlComplexContentImpl implements WeblogicExtensionType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULEPROVIDER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "module-provider");
   private static final QName CUSTOMMODULE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "custom-module");
   private static final QName VERSION$4 = new QName("", "version");

   public WeblogicExtensionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ModuleProviderType[] getModuleProviderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MODULEPROVIDER$0, targetList);
         ModuleProviderType[] result = new ModuleProviderType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ModuleProviderType getModuleProviderArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleProviderType target = null;
         target = (ModuleProviderType)this.get_store().find_element_user(MODULEPROVIDER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfModuleProviderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULEPROVIDER$0);
      }
   }

   public void setModuleProviderArray(ModuleProviderType[] moduleProviderArray) {
      this.check_orphaned();
      this.arraySetterHelper(moduleProviderArray, MODULEPROVIDER$0);
   }

   public void setModuleProviderArray(int i, ModuleProviderType moduleProvider) {
      this.generatedSetterHelperImpl(moduleProvider, MODULEPROVIDER$0, i, (short)2);
   }

   public ModuleProviderType insertNewModuleProvider(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleProviderType target = null;
         target = (ModuleProviderType)this.get_store().insert_element_user(MODULEPROVIDER$0, i);
         return target;
      }
   }

   public ModuleProviderType addNewModuleProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleProviderType target = null;
         target = (ModuleProviderType)this.get_store().add_element_user(MODULEPROVIDER$0);
         return target;
      }
   }

   public void removeModuleProvider(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULEPROVIDER$0, i);
      }
   }

   public CustomModuleType[] getCustomModuleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMMODULE$2, targetList);
         CustomModuleType[] result = new CustomModuleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomModuleType getCustomModuleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomModuleType target = null;
         target = (CustomModuleType)this.get_store().find_element_user(CUSTOMMODULE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCustomModuleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMMODULE$2);
      }
   }

   public void setCustomModuleArray(CustomModuleType[] customModuleArray) {
      this.check_orphaned();
      this.arraySetterHelper(customModuleArray, CUSTOMMODULE$2);
   }

   public void setCustomModuleArray(int i, CustomModuleType customModule) {
      this.generatedSetterHelperImpl(customModule, CUSTOMMODULE$2, i, (short)2);
   }

   public CustomModuleType insertNewCustomModule(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomModuleType target = null;
         target = (CustomModuleType)this.get_store().insert_element_user(CUSTOMMODULE$2, i);
         return target;
      }
   }

   public CustomModuleType addNewCustomModule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomModuleType target = null;
         target = (CustomModuleType)this.get_store().add_element_user(CUSTOMMODULE$2);
         return target;
      }
   }

   public void removeCustomModule(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMMODULE$2, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$4);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$4) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$4);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$4);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$4);
      }
   }
}
