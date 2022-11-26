package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SAFAgentMBean;

@Service
public class SAFAgentPartitionProcessor extends AbstractComponentPartitionProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processSAFAgent(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, SAFAgentMBean bean, SAFAgentMBean clone) throws InvalidAttributeValueException, ManagementException {
      PersistentStoreMBean delegateStoreMBean = bean.getStore();
      if (delegateStoreMBean != null) {
         PersistentStoreMBean clonedStoreMBean = (PersistentStoreMBean)PartitionProcessor.clone(domain, partition, resourceGroup, delegateStoreMBean);
         clone.setStore(clonedStoreMBean);
      }

   }
}
