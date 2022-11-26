package weblogic.management.workflow;

import java.util.Objects;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.PartitionMBean;

public class WorkflowDomainBeanUpdateListener implements BeanUpdateListener {
   WorkflowLifecycleManager manager;

   private WorkflowDomainBeanUpdateListener() {
   }

   public WorkflowDomainBeanUpdateListener(WorkflowLifecycleManager manager) {
      this.manager = manager;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
      BeanUpdateEvent.PropertyUpdate[] var3 = updated;
      int var4 = updated.length;
      int var5 = 0;

      while(var5 < var4) {
         BeanUpdateEvent.PropertyUpdate propertyUpdate = var3[var5];
         switch (propertyUpdate.getUpdateType()) {
            case 3:
               String name = propertyUpdate.getPropertyName();
               if (null != name) {
                  switch (name) {
                     case "Partitions":
                        PartitionMBean oldPartition = (PartitionMBean)propertyUpdate.getRemovedObject();
                        String partitionName = oldPartition.getName();
                        Objects.requireNonNull(this.manager);
                        this.manager.deleteWorkflowsForPartition(partitionName);
                  }
               }
            default:
               ++var5;
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
