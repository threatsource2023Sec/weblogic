package weblogic.management.configuration;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.utils.PartitionUtils;

public class ResourceGroupUpdateListener implements BeanUpdateListener {
   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (event.getSourceBean() instanceof ResourceGroupMBean && event.getProposedBean() instanceof ResourceGroupMBean) {
         this.validateUserForAutoTargetAdminServerFlag((ResourceGroupMBean)event.getSourceBean(), (ResourceGroupMBean)event.getProposedBean());
         this.validateIfTargetCanBeRemovedFromRG((ResourceGroupMBean)event.getSourceBean(), (ResourceGroupMBean)event.getProposedBean());
      }

   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private void validateIfTargetCanBeRemovedFromRG(ResourceGroupMBean currRG, ResourceGroupMBean proposedRG) throws BeanUpdateRejectedException {
      TargetMBean[] currRGTargets = currRG.findEffectiveTargets();
      TargetMBean[] proposedRGTargets = proposedRG.findEffectiveTargets();
      TargetMBean[] targetsRemoved = PartitionUtils.getRemovedTargetArray(currRGTargets, proposedRGTargets);
      if (targetsRemoved.length > 0) {
         ResourceGroupValidator.validateRemoveTargetFromRG(currRG, targetsRemoved);
      }

   }

   private void validateUserForAutoTargetAdminServerFlag(ResourceGroupMBean currRG, ResourceGroupMBean proposedRG) {
      boolean currIsAutoTargAdminFlag = currRG.isAutoTargetAdminServer();
      boolean propIsAutoTargAdminFlag = proposedRG.isAutoTargetAdminServer();
      if (currIsAutoTargAdminFlag != propIsAutoTargAdminFlag) {
         String cicPartitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         if (cicPartitionName.compareTo("DOMAIN") != 0) {
            throw new IllegalArgumentException("Only Domain Administrators are allowed to set AutoTargetAdminServer on a resourceGroup");
         }
      }

   }
}
