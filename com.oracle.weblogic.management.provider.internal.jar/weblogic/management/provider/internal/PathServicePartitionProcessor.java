package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Service
public class PathServicePartitionProcessor extends AbstractComponentPartitionProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processPathService(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, PathServiceMBean bean, PathServiceMBean clone) throws InvalidAttributeValueException, ManagementException {
      PersistentStoreMBean delegateStoreMBean = bean.getPersistentStore();
      if (delegateStoreMBean != null) {
         PersistentStoreMBean clonedStoreMBean = (PersistentStoreMBean)PartitionProcessor.clone(domain, partition, resourceGroup, delegateStoreMBean);
         clone.setPersistentStore(clonedStoreMBean);
      }

   }
}
