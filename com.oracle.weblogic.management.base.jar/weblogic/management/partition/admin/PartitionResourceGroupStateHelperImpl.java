package weblogic.management.partition.admin;

import org.jvnet.hk2.annotations.Service;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;

@Service
public class PartitionResourceGroupStateHelperImpl extends ResourceGroupStateHelperImpl {
   protected boolean isRGActiveIn(ResourceGroupMBean resourceGroupMBean, String rgState) throws ResourceGroupLifecycleException {
      if (this.getPartitionName(resourceGroupMBean) == null) {
         throw new RuntimeException("No partition is found associated with resource group : " + resourceGroupMBean.getName());
      } else {
         PartitionRuntimeMBean prMbean = this.getRuntime().getServerRuntime().lookupPartitionRuntime(this.getPartitionName(resourceGroupMBean));
         return prMbean != null ? rgState.equals(ResourceGroupLifecycleOperations.RGState.normalize(prMbean.getRgState(resourceGroupMBean.getName())).name()) : false;
      }
   }

   public void markShutdownAsAdmin() throws ResourceGroupLifecycleException {
      throw new UnsupportedOperationException();
   }

   public void markAdminAsRunning() throws ResourceGroupLifecycleException {
      throw new UnsupportedOperationException();
   }

   public void markRunningAsAdmin() throws ResourceGroupLifecycleException {
      throw new UnsupportedOperationException();
   }

   public void markAdminAsShutdown() throws ResourceGroupLifecycleException {
      throw new UnsupportedOperationException();
   }
}
