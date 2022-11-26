package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ResourceDeploymentPlanDocument;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.ResourceDeploymentPlanType;
import javax.xml.namespace.QName;

public class ResourceDeploymentPlanDocumentImpl extends XmlComplexContentImpl implements ResourceDeploymentPlanDocument {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCEDEPLOYMENTPLAN$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "resource-deployment-plan");

   public ResourceDeploymentPlanDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ResourceDeploymentPlanType getResourceDeploymentPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDeploymentPlanType target = null;
         target = (ResourceDeploymentPlanType)this.get_store().find_element_user(RESOURCEDEPLOYMENTPLAN$0, 0);
         return target == null ? null : target;
      }
   }

   public void setResourceDeploymentPlan(ResourceDeploymentPlanType resourceDeploymentPlan) {
      this.generatedSetterHelperImpl(resourceDeploymentPlan, RESOURCEDEPLOYMENTPLAN$0, 0, (short)1);
   }

   public ResourceDeploymentPlanType addNewResourceDeploymentPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceDeploymentPlanType target = null;
         target = (ResourceDeploymentPlanType)this.get_store().add_element_user(RESOURCEDEPLOYMENTPLAN$0);
         return target;
      }
   }
}
