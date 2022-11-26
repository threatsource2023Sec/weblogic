package weblogic.management.partition.admin;

import java.security.AccessController;
import javax.inject.Inject;
import weblogic.management.PartitionRuntimeStateManagerContract;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class ResourceGroupStateHelperImpl implements ResourceGroupStateHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   protected PartitionRuntimeStateManagerContract stateManager;

   public boolean isDesiredStateAdmin(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      String state = this.stateManager.getResourceGroupState(this.getPartitionName(resourceGroup), resourceGroup.getName(), this.getServerName(), resourceGroup.isAdministrative());
      return state.equals(ResourceGroupLifecycleOperations.RGState.ADMIN.name()) || state.equals(ResourceGroupLifecycleOperations.RGState.runningState().name());
   }

   public boolean isDesiredStateRunning(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      String state = this.stateManager.getResourceGroupState(this.getPartitionName(resourceGroup), resourceGroup.getName(), this.getServerName(), resourceGroup.isAdministrative());
      return state.equals(ResourceGroupLifecycleOperations.RGState.runningState().name());
   }

   public boolean isActiveInAdmin(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      return this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.ADMIN.name());
   }

   public boolean isActiveInRunning(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      return this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.runningState().name());
   }

   public boolean isShuttingDown(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      return this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.SHUTTING_DOWN.name()) || this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.FORCE_SHUTTING_DOWN.name());
   }

   public boolean isSuspending(ResourceGroupMBean resourceGroup) throws ResourceGroupLifecycleException {
      return this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.SUSPENDING.name()) || this.isRGActiveIn(resourceGroup, ResourceGroupLifecycleOperations.RGState.FORCE_SUSPENDING.name());
   }

   protected abstract boolean isRGActiveIn(ResourceGroupMBean var1, String var2) throws ResourceGroupLifecycleException;

   protected RuntimeAccess getRuntime() {
      return ManagementService.getRuntimeAccess(kernelId);
   }

   protected DomainMBean getDomain() {
      return this.getRuntime().getDomain();
   }

   protected String getServerName() {
      return this.getRuntime().getServerRuntime().getName();
   }

   protected String getPartitionName(ResourceGroupMBean resourceGroup) {
      return resourceGroup.getParent() != null && !(resourceGroup.getParent() instanceof DomainMBean) ? resourceGroup.getParent().getName() : null;
   }
}
