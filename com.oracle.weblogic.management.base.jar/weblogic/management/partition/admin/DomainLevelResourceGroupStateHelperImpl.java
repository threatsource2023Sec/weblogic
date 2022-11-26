package weblogic.management.partition.admin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

@Service
public class DomainLevelResourceGroupStateHelperImpl extends ResourceGroupStateHelperImpl {
   private DomainLevelResourceGroupStateHelperImpl() {
   }

   protected boolean isRGActiveIn(ResourceGroupMBean resourceGroupMBean, String rgState) throws ResourceGroupLifecycleException {
      if (this.getPartitionName(resourceGroupMBean) != null) {
         throw new RuntimeException("Not a domain resource group : " + resourceGroupMBean.getName());
      } else {
         ServerRuntimeMBean serverRuntimeMBean = this.getRuntime().getServerRuntime();
         if (serverRuntimeMBean.isResourceGroupPresent(resourceGroupMBean.getName())) {
            String rgStateStr = serverRuntimeMBean.getRgState(resourceGroupMBean.getName());
            return rgState.equals(rgStateStr);
         } else {
            return false;
         }
      }
   }

   public void markShutdownAsAdmin() throws ResourceGroupLifecycleException {
      this.changeDomainRGsState(ResourceGroupLifecycleOperations.RGState.SHUTDOWN.name(), ResourceGroupLifecycleOperations.RGState.ADMIN.name());
   }

   public void markAdminAsRunning() throws ResourceGroupLifecycleException {
      this.changeDomainRGsState(ResourceGroupLifecycleOperations.RGState.ADMIN.name(), ResourceGroupLifecycleOperations.RGState.runningState().name());
   }

   public void markRunningAsAdmin() throws ResourceGroupLifecycleException {
      this.changeDomainRGsState(ResourceGroupLifecycleOperations.RGState.runningState().name(), ResourceGroupLifecycleOperations.RGState.ADMIN.name());
   }

   public void markAdminAsShutdown() throws ResourceGroupLifecycleException {
      this.changeDomainRGsState(ResourceGroupLifecycleOperations.RGState.ADMIN.name(), ResourceGroupLifecycleOperations.RGState.SHUTDOWN.name());
   }

   private void changeDomainRGsState(String currentRgState, String newRgState) throws ResourceGroupLifecycleException {
      Set rgSet = this.findDomainRGsForThisServer();
      Iterator var4 = rgSet.iterator();

      while(var4.hasNext()) {
         ResourceGroupMBean rg = (ResourceGroupMBean)var4.next();
         if (this.isRGActiveIn(rg, currentRgState)) {
            this.setDomainRGState(rg.getName(), newRgState, rg.isAdministrative());
         }
      }

   }

   private void setDomainRGState(String resourceGroupName, String newRgState, boolean isAdminRG) throws ResourceGroupLifecycleException {
      ResourceGroupLifecycleOperations.RGState caculatedState = ResourceGroupLifecycleOperations.RGState.min(ResourceGroupLifecycleOperations.RGState.valueOf(this.stateManager.getResourceGroupState((String)null, resourceGroupName, isAdminRG)), ResourceGroupLifecycleOperations.RGState.valueOf(newRgState));
      this.getRuntime().getServerRuntime().setRgState(resourceGroupName, caculatedState.name());
   }

   private Set findDomainRGsForThisServer() {
      HashSet rgSet = new HashSet();
      return rgSet;
   }
}
