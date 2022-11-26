package weblogic.management.partition.admin;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Iterator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class PartitionVirtualTargetBeanUpdateListener implements DescriptorUpdateListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final String AVAILABLE_TARGET = "AvailableTargets";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void prepareUpdate(DescriptorUpdateEvent descriptorUpdateEvent) throws DescriptorUpdateRejectedException {
   }

   public void activateUpdate(DescriptorUpdateEvent descriptorUpdateEvent) throws DescriptorUpdateFailedException {
      this.doActivateUpdate(descriptorUpdateEvent);
   }

   public void rollbackUpdate(DescriptorUpdateEvent descriptorUpdateEvent) {
      DescriptorDiff diff = descriptorUpdateEvent.getDiff();
      Iterator iter = diff.iterator();

      while(iter.hasNext()) {
         BeanUpdateEvent event = (BeanUpdateEvent)iter.next();
         DescriptorBean descriptorBean = event.getProposedBean();
         descriptorBean.getDescriptor().rollbackUpdate();
      }

   }

   private void doActivateUpdate(DescriptorUpdateEvent descriptorUpdateEvent) {
      DescriptorDiff diff = descriptorUpdateEvent.getDiff();
      if (diff.size() != 0) {
         Iterator beanUpdateEventIterator = diff.iterator();

         while(beanUpdateEventIterator.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)beanUpdateEventIterator.next();
            BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

            for(int i = 0; i < updated.length; ++i) {
               BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
               if ("AvailableTargets".equals(propertyUpdate.getPropertyName())) {
                  switch (propertyUpdate.getUpdateType()) {
                     case 1:
                        this.handleAvailableTargetChange(propertyUpdate, event);
                        break;
                     case 2:
                        this.handleAvailableTargetAdd(propertyUpdate, event);
                        break;
                     case 3:
                        this.handleAvailableTargetRemove(propertyUpdate, event);
                  }
               }
            }
         }

      }
   }

   private void handleAvailableTargetChange(BeanUpdateEvent.PropertyUpdate propertyUpdate, BeanUpdateEvent event) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Updating Available Target");
      }

   }

   private void handleAvailableTargetAdd(BeanUpdateEvent.PropertyUpdate propertyUpdate, BeanUpdateEvent event) {
      PartitionMBean partitionMBean = (PartitionMBean)event.getProposedBean();
      if (propertyUpdate.getAddedObject() instanceof VirtualTargetMBean) {
         VirtualTargetMBean vt = (VirtualTargetMBean)propertyUpdate.getAddedObject();
         if (debugLogger.isDebugEnabled()) {
            this.debug("VT = " + vt.getName() + " added to Partition = " + partitionMBean.getName());
         }

         WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
         ((WorkingVirtualTargetManagerImpl)workingVirtualTargetManager).addVirtualTarget(partitionMBean.getName(), vt);
      }

   }

   private void handleAvailableTargetRemove(BeanUpdateEvent.PropertyUpdate propertyUpdate, BeanUpdateEvent event) {
      PartitionMBean partitionMBean = (PartitionMBean)event.getProposedBean();
      if (propertyUpdate.getRemovedObject() instanceof VirtualTargetMBean) {
         VirtualTargetMBean vt = (VirtualTargetMBean)propertyUpdate.getRemovedObject();
         if (debugLogger.isDebugEnabled()) {
            this.debug("VT = " + vt.getName() + " removed from Partition = " + partitionMBean.getName());
         }

         WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
         ((WorkingVirtualTargetManagerImpl)workingVirtualTargetManager).removeVirtualTarget(partitionMBean.getName(), vt);
      }

   }

   private void debug(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[DEBUG]::[PartitionVirtualTargetBeanUpdateListener]::" + msg);
      }

   }
}
