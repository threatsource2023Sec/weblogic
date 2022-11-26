package com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.DeploymentConfigOverridesInputDocument;
import com.oracle.xmlns.weblogic.deploymentConfigOverridesInput.DeploymentConfigOverridesInputType;
import javax.xml.namespace.QName;

public class DeploymentConfigOverridesInputDocumentImpl extends XmlComplexContentImpl implements DeploymentConfigOverridesInputDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEPLOYMENTCONFIGOVERRIDESINPUT$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-config-overrides-input", "deployment-config-overrides-input");

   public DeploymentConfigOverridesInputDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DeploymentConfigOverridesInputType getDeploymentConfigOverridesInput() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentConfigOverridesInputType target = null;
         target = (DeploymentConfigOverridesInputType)this.get_store().find_element_user(DEPLOYMENTCONFIGOVERRIDESINPUT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDeploymentConfigOverridesInput(DeploymentConfigOverridesInputType deploymentConfigOverridesInput) {
      this.generatedSetterHelperImpl(deploymentConfigOverridesInput, DEPLOYMENTCONFIGOVERRIDESINPUT$0, 0, (short)1);
   }

   public DeploymentConfigOverridesInputType addNewDeploymentConfigOverridesInput() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentConfigOverridesInputType target = null;
         target = (DeploymentConfigOverridesInputType)this.get_store().add_element_user(DEPLOYMENTCONFIGOVERRIDESINPUT$0);
         return target;
      }
   }
}
