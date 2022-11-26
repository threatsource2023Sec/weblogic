package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.ClassloaderStructureType;
import com.oracle.xmlns.weblogic.weblogicApplication.ModuleRefType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ClassloaderStructureTypeImpl extends XmlComplexContentImpl implements ClassloaderStructureType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULEREF$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "module-ref");
   private static final QName CLASSLOADERSTRUCTURE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "classloader-structure");

   public ClassloaderStructureTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ModuleRefType[] getModuleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MODULEREF$0, targetList);
         ModuleRefType[] result = new ModuleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ModuleRefType getModuleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleRefType target = null;
         target = (ModuleRefType)this.get_store().find_element_user(MODULEREF$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfModuleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULEREF$0);
      }
   }

   public void setModuleRefArray(ModuleRefType[] moduleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(moduleRefArray, MODULEREF$0);
   }

   public void setModuleRefArray(int i, ModuleRefType moduleRef) {
      this.generatedSetterHelperImpl(moduleRef, MODULEREF$0, i, (short)2);
   }

   public ModuleRefType insertNewModuleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleRefType target = null;
         target = (ModuleRefType)this.get_store().insert_element_user(MODULEREF$0, i);
         return target;
      }
   }

   public ModuleRefType addNewModuleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ModuleRefType target = null;
         target = (ModuleRefType)this.get_store().add_element_user(MODULEREF$0);
         return target;
      }
   }

   public void removeModuleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULEREF$0, i);
      }
   }

   public ClassloaderStructureType[] getClassloaderStructureArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CLASSLOADERSTRUCTURE$2, targetList);
         ClassloaderStructureType[] result = new ClassloaderStructureType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ClassloaderStructureType getClassloaderStructureArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassloaderStructureType target = null;
         target = (ClassloaderStructureType)this.get_store().find_element_user(CLASSLOADERSTRUCTURE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfClassloaderStructureArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSLOADERSTRUCTURE$2);
      }
   }

   public void setClassloaderStructureArray(ClassloaderStructureType[] classloaderStructureArray) {
      this.check_orphaned();
      this.arraySetterHelper(classloaderStructureArray, CLASSLOADERSTRUCTURE$2);
   }

   public void setClassloaderStructureArray(int i, ClassloaderStructureType classloaderStructure) {
      this.generatedSetterHelperImpl(classloaderStructure, CLASSLOADERSTRUCTURE$2, i, (short)2);
   }

   public ClassloaderStructureType insertNewClassloaderStructure(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassloaderStructureType target = null;
         target = (ClassloaderStructureType)this.get_store().insert_element_user(CLASSLOADERSTRUCTURE$2, i);
         return target;
      }
   }

   public ClassloaderStructureType addNewClassloaderStructure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassloaderStructureType target = null;
         target = (ClassloaderStructureType)this.get_store().add_element_user(CLASSLOADERSTRUCTURE$2);
         return target;
      }
   }

   public void removeClassloaderStructure(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSLOADERSTRUCTURE$2, i);
      }
   }
}
