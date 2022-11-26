package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.BridgeDestinationCommonMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Service
public class MessagingBridgePartitionProcessor extends AbstractComponentPartitionProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processMessagingBridge(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, MessagingBridgeMBean bean, MessagingBridgeMBean clone) throws InvalidAttributeValueException, ManagementException {
      BridgeDestinationCommonMBean delegateTargetDestinationMBean = bean.getTargetDestination();
      BridgeDestinationCommonMBean delegateSourceDestinationMBean = bean.getSourceDestination();
      BridgeDestinationCommonMBean clonedTargetDestinationMBean;
      if (delegateSourceDestinationMBean != null) {
         clonedTargetDestinationMBean = (BridgeDestinationCommonMBean)PartitionProcessor.clone(domain, partition, resourceGroup, delegateSourceDestinationMBean);
         clone.setSourceDestination(clonedTargetDestinationMBean);
      }

      if (delegateTargetDestinationMBean != null) {
         clonedTargetDestinationMBean = (BridgeDestinationCommonMBean)PartitionProcessor.clone(domain, partition, resourceGroup, delegateTargetDestinationMBean);
         clone.setTargetDestination(clonedTargetDestinationMBean);
      }

   }
}
