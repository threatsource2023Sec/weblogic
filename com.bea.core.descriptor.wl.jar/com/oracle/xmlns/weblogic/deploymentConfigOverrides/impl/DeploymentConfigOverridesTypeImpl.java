package com.oracle.xmlns.weblogic.deploymentConfigOverrides.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.deploymentConfigOverrides.AppDeploymentType;
import com.oracle.xmlns.weblogic.deploymentConfigOverrides.DeploymentConfigOverridesType;
import com.oracle.xmlns.weblogic.deploymentConfigOverrides.LibraryType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DeploymentConfigOverridesTypeImpl extends XmlComplexContentImpl implements DeploymentConfigOverridesType {
   private static final long serialVersionUID = 1L;
   private static final QName COMMANDLINEOPTIONS$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides", "command-line-options");
   private static final QName APPDEPLOYMENT$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides", "app-deployment");
   private static final QName LIBRARY$4 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides", "library");

   public DeploymentConfigOverridesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getCommandLineOptions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMMANDLINEOPTIONS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCommandLineOptions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMMANDLINEOPTIONS$0, 0);
         return target;
      }
   }

   public boolean isSetCommandLineOptions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMMANDLINEOPTIONS$0) != 0;
      }
   }

   public void setCommandLineOptions(String commandLineOptions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMMANDLINEOPTIONS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COMMANDLINEOPTIONS$0);
         }

         target.setStringValue(commandLineOptions);
      }
   }

   public void xsetCommandLineOptions(XmlString commandLineOptions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMMANDLINEOPTIONS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COMMANDLINEOPTIONS$0);
         }

         target.set(commandLineOptions);
      }
   }

   public void unsetCommandLineOptions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMMANDLINEOPTIONS$0, 0);
      }
   }

   public AppDeploymentType[] getAppDeploymentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(APPDEPLOYMENT$2, targetList);
         AppDeploymentType[] result = new AppDeploymentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AppDeploymentType getAppDeploymentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$2, i);
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
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$2, i);
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
         return this.get_store().count_elements(APPDEPLOYMENT$2);
      }
   }

   public void setAppDeploymentArray(AppDeploymentType[] appDeploymentArray) {
      this.check_orphaned();
      this.arraySetterHelper(appDeploymentArray, APPDEPLOYMENT$2);
   }

   public void setAppDeploymentArray(int i, AppDeploymentType appDeployment) {
      this.generatedSetterHelperImpl(appDeployment, APPDEPLOYMENT$2, i, (short)2);
   }

   public void setNilAppDeploymentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().find_element_user(APPDEPLOYMENT$2, i);
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
         target = (AppDeploymentType)this.get_store().insert_element_user(APPDEPLOYMENT$2, i);
         return target;
      }
   }

   public AppDeploymentType addNewAppDeployment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AppDeploymentType target = null;
         target = (AppDeploymentType)this.get_store().add_element_user(APPDEPLOYMENT$2);
         return target;
      }
   }

   public void removeAppDeployment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPDEPLOYMENT$2, i);
      }
   }

   public LibraryType[] getLibraryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LIBRARY$4, targetList);
         LibraryType[] result = new LibraryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LibraryType getLibraryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$4, i);
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
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$4, i);
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
         return this.get_store().count_elements(LIBRARY$4);
      }
   }

   public void setLibraryArray(LibraryType[] libraryArray) {
      this.check_orphaned();
      this.arraySetterHelper(libraryArray, LIBRARY$4);
   }

   public void setLibraryArray(int i, LibraryType library) {
      this.generatedSetterHelperImpl(library, LIBRARY$4, i, (short)2);
   }

   public void setNilLibraryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().find_element_user(LIBRARY$4, i);
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
         target = (LibraryType)this.get_store().insert_element_user(LIBRARY$4, i);
         return target;
      }
   }

   public LibraryType addNewLibrary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LibraryType target = null;
         target = (LibraryType)this.get_store().add_element_user(LIBRARY$4);
         return target;
      }
   }

   public void removeLibrary(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIBRARY$4, i);
      }
   }
}
