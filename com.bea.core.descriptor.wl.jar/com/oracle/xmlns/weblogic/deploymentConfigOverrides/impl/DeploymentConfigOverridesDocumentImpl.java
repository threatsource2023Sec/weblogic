package com.oracle.xmlns.weblogic.deploymentConfigOverrides.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.deploymentConfigOverrides.DeploymentConfigOverridesDocument;
import com.oracle.xmlns.weblogic.deploymentConfigOverrides.DeploymentConfigOverridesType;
import javax.xml.namespace.QName;

public class DeploymentConfigOverridesDocumentImpl extends XmlComplexContentImpl implements DeploymentConfigOverridesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEPLOYMENTCONFIGOVERRIDES$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides", "deployment-config-overrides");

   public DeploymentConfigOverridesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DeploymentConfigOverridesType getDeploymentConfigOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentConfigOverridesType target = null;
         target = (DeploymentConfigOverridesType)this.get_store().find_element_user(DEPLOYMENTCONFIGOVERRIDES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDeploymentConfigOverrides(DeploymentConfigOverridesType deploymentConfigOverrides) {
      this.generatedSetterHelperImpl(deploymentConfigOverrides, DEPLOYMENTCONFIGOVERRIDES$0, 0, (short)1);
   }

   public DeploymentConfigOverridesType addNewDeploymentConfigOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentConfigOverridesType target = null;
         target = (DeploymentConfigOverridesType)this.get_store().add_element_user(DEPLOYMENTCONFIGOVERRIDES$0);
         return target;
      }
   }
}
