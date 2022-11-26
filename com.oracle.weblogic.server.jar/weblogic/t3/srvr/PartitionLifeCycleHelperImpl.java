package weblogic.t3.srvr;

import javax.annotation.PostConstruct;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleHelper;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.server.PartitionLifeCycleRuntime;
import weblogic.server.ResourceGroupLifeCycleRuntime;

@Service
public class PartitionLifeCycleHelperImpl implements PartitionLifeCycleHelper {
   @PostConstruct
   public void start() throws ManagementException {
   }

   public void init(DomainMBean domain, DomainRuntimeMBean domainRuntime) {
   }

   public ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes() {
      return null;
   }

   public ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String name) {
      return null;
   }

   private PartitionRuntimeMBean.State choosePartitionState(PartitionLifeCycleRuntime pLC) {
      return State.runningState();
   }

   private ResourceGroupLifecycleOperations.RGState chooseResourceGroupState(ResourceGroupLifeCycleRuntime rgLC) {
      return RGState.runningState();
   }

   private class BeanEventProcessorException extends Exception {
      private BeanEventProcessorException(String msg) {
         super(msg);
      }
   }

   private abstract class BeanEventProcessor {
      void process(BeanUpdateEvent event) throws BeanEventProcessorException {
         BeanUpdateEvent.PropertyUpdate[] var2 = event.getUpdateList();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BeanUpdateEvent.PropertyUpdate update = var2[var4];
            String propName = update.getPropertyName();
            switch (update.getUpdateType()) {
               case 2:
                  switch (propName) {
                     case "Partitions":
                        this.addedPartition((PartitionMBean)update.getAddedObject());
                        continue;
                     case "ResourceGroups":
                        if (event.getSource() instanceof DomainMBean) {
                           this.addedResourceGroup((ResourceGroupMBean)update.getAddedObject());
                        } else if (event.getSource() instanceof PartitionMBean) {
                           this.addedNestedResourceGroup((PartitionMBean)event.getSource(), (ResourceGroupMBean)update.getAddedObject());
                        }
                     default:
                        continue;
                  }
               case 3:
                  switch (propName) {
                     case "Partitions":
                        this.removedPartition((PartitionMBean)update.getRemovedObject());
                        break;
                     case "ResourceGroups":
                        if (event.getSource() instanceof DomainMBean) {
                           this.removedResourceGroup((ResourceGroupMBean)update.getRemovedObject());
                        } else if (event.getSource() instanceof PartitionMBean) {
                           this.removedNestedResourceGroup((PartitionMBean)event.getSource(), (ResourceGroupMBean)update.getRemovedObject());
                        }
                  }
            }
         }

      }

      abstract void addedPartition(PartitionMBean var1) throws BeanEventProcessorException;

      abstract void removedPartition(PartitionMBean var1) throws BeanEventProcessorException;

      abstract void addedResourceGroup(ResourceGroupMBean var1) throws BeanEventProcessorException;

      abstract void removedResourceGroup(ResourceGroupMBean var1) throws BeanEventProcessorException;

      abstract void addedNestedResourceGroup(PartitionMBean var1, ResourceGroupMBean var2) throws BeanEventProcessorException;

      abstract void removedNestedResourceGroup(PartitionMBean var1, ResourceGroupMBean var2) throws BeanEventProcessorException;
   }
}
