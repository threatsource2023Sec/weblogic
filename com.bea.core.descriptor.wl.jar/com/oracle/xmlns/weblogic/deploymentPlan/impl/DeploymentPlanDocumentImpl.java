package com.oracle.xmlns.weblogic.deploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.deploymentPlan.DeploymentPlanDocument;
import com.oracle.xmlns.weblogic.deploymentPlan.DeploymentPlanType;
import javax.xml.namespace.QName;

public class DeploymentPlanDocumentImpl extends XmlComplexContentImpl implements DeploymentPlanDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEPLOYMENTPLAN$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "deployment-plan");

   public DeploymentPlanDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DeploymentPlanType getDeploymentPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentPlanType target = null;
         target = (DeploymentPlanType)this.get_store().find_element_user(DEPLOYMENTPLAN$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDeploymentPlan(DeploymentPlanType deploymentPlan) {
      this.generatedSetterHelperImpl(deploymentPlan, DEPLOYMENTPLAN$0, 0, (short)1);
   }

   public DeploymentPlanType addNewDeploymentPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentPlanType target = null;
         target = (DeploymentPlanType)this.get_store().add_element_user(DEPLOYMENTPLAN$0);
         return target;
      }
   }
}
