package com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.AppDeploymentType;
import javax.xml.namespace.QName;

public class AppDeploymentTypeImpl extends XmlComplexContentImpl implements AppDeploymentType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "name");
   private static final QName SOURCEPATH$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "source-path");
   private static final QName RETIRETIMEOUT$4 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "retire-timeout");
   private static final QName GENERATEDVERSION$6 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "generated-version");

   public AppDeploymentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getSourcePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOURCEPATH$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSourcePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SOURCEPATH$2, 0);
         return target;
      }
   }

   public void setSourcePath(String sourcePath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOURCEPATH$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SOURCEPATH$2);
         }

         target.setStringValue(sourcePath);
      }
   }

   public void xsetSourcePath(XmlString sourcePath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SOURCEPATH$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SOURCEPATH$2);
         }

         target.set(sourcePath);
      }
   }

   public String getRetireTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETIRETIMEOUT$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRetireTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RETIRETIMEOUT$4, 0);
         return target;
      }
   }

   public boolean isSetRetireTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETIRETIMEOUT$4) != 0;
      }
   }

   public void setRetireTimeout(String retireTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETIRETIMEOUT$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETIRETIMEOUT$4);
         }

         target.setStringValue(retireTimeout);
      }
   }

   public void xsetRetireTimeout(XmlString retireTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RETIRETIMEOUT$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RETIRETIMEOUT$4);
         }

         target.set(retireTimeout);
      }
   }

   public void unsetRetireTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETIRETIMEOUT$4, 0);
      }
   }

   public String getGeneratedVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GENERATEDVERSION$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetGeneratedVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GENERATEDVERSION$6, 0);
         return target;
      }
   }

   public boolean isSetGeneratedVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GENERATEDVERSION$6) != 0;
      }
   }

   public void setGeneratedVersion(String generatedVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GENERATEDVERSION$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(GENERATEDVERSION$6);
         }

         target.setStringValue(generatedVersion);
      }
   }

   public void xsetGeneratedVersion(XmlString generatedVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GENERATEDVERSION$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(GENERATEDVERSION$6);
         }

         target.set(generatedVersion);
      }
   }

   public void unsetGeneratedVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GENERATEDVERSION$6, 0);
      }
   }
}
