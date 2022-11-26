package com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.AppDeploymentType;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.DeploymentConfigOverridesInputType;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.LibraryType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DeploymentConfigOverridesInputTypeImpl extends XmlComplexContentImpl implements DeploymentConfigOverridesInputType {
   private static final long serialVersionUID = 1L;
   private static final QName APPDEPLOYMENT$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "app-deployment");
   private static final QName LIBRARY$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "library");

   public DeploymentConfigOverridesInputTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AppDeploymentType[] getAppDeploymentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(APPDEPLOYMENT$0, targetList);
         AppDeploymentType[] result = new AppDeploymentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AppDeploymentType getAppDeploymentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilAppDeploymentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfAppDeploymentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(APPDEPLOYMENT$0);
      }
   }

   public void setAppDeploymentArray(AppDeploymentType[] appDeploymentArray) {
      this.check_orphaned();
      this.arraySetterHelper(appDeploymentArray, APPDEPLOYMENT$0);
   }

   public void setAppDeploymentArray(int i, AppDeploymentType appDeployment) {
      this.generatedSetterHelperImpl(appDeployment, APPDEPLOYMENT$0, i, (short)2);
   }

   public void setNilAppDeploymentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public AppDeploymentType insertNewAppDeployment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().insert_element_user(APPDEPLOYMENT$0, i);
         return target;
      }
   }

   public AppDeploymentType addNewAppDeployment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().add_element_user(APPDEPLOYMENT$0);
         return target;
      }
   }

   public void removeAppDeployment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPDEPLOYMENT$0, i);
      }
   }

   public LibraryType[] getLibraryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LIBRARY$2, targetList);
         LibraryType[] result = new LibraryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LibraryType getLibraryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilLibraryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfLibraryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIBRARY$2);
      }
   }

   public void setLibraryArray(LibraryType[] libraryArray) {
      this.check_orphaned();
      this.arraySetterHelper(libraryArray, LIBRARY$2);
   }

   public void setLibraryArray(int i, LibraryType library) {
      this.generatedSetterHelperImpl(library, LIBRARY$2, i, (short)2);
   }

   public void setNilLibraryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public LibraryType insertNewLibrary(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().insert_element_user(LIBRARY$2, i);
         return target;
      }
   }

   public LibraryType addNewLibrary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().add_element_user(LIBRARY$2);
         return target;
      }
   }

   public void removeLibrary(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIBRARY$2, i);
      }
   }
}
