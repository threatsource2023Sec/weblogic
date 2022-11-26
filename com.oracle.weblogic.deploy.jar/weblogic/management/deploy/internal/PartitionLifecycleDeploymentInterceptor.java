package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.services.BackgroundDeploymentManagerService;
import weblogic.deploy.internal.Deployment;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.PartitionManagerResourceGroupAPI;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.utils.StackTraceUtils;

@Service
@ServerServiceInterceptor(DeploymentServerService.class)
@Interceptor
@ContractsProvided({MethodInterceptor.class, PartitionLifecycleDeploymentInterceptor.class})
@PartitionManagerPartitionAPI
@PartitionManagerResourceGroupAPI
public class PartitionLifecycleDeploymentInterceptor extends PartitionManagerInterceptorAdapter {
   @Inject
   private Provider partitionRuntimeStateManagerProvider;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   private BackgroundDeploymentManagerService backgroundDeploymentManagerService;

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      try {
         PartitionLifecycleDebugger.debug("<PartitionLifecycleDeploymentInterceptor> Intercepting MethodInvocation " + methodInvocation.getMethod() + "with arguments :" + Arrays.asList(methodInvocation.getArguments()));
         String methodName = methodInvocation.getMethod().getName();
         PartitionLifecycleDebugger.debug("<PartitionLifecycleDeploymentInterceptor> Intercepting MethodName is " + methodName);
         ConfiguredDeployments configuredDeployments = ConfiguredDeployments.getConfigureDeploymentsHandler();
         Object result;
         switch (methodName) {
            case "bootPartition":
               this.bootPartition(methodInvocation, configuredDeployments);
               result = methodInvocation.proceed();
               break;
            case "startPartition":
               this.startPartition(methodInvocation, configuredDeployments);
               result = methodInvocation.proceed();
               break;
            case "startPartitionInAdmin":
               this.startPartitionInAdmin(methodInvocation, configuredDeployments);
               result = methodInvocation.proceed();
               break;
            case "suspendPartition":
               result = methodInvocation.proceed();
               this.suspendPartition(methodInvocation, configuredDeployments, true);
               break;
            case "forceSuspendPartition":
               result = methodInvocation.proceed();
               this.suspendPartition(methodInvocation, configuredDeployments, false);
               break;
            case "resumePartition":
               this.resumePartition(methodInvocation, configuredDeployments);
               result = methodInvocation.proceed();
               break;
            case "shutdownPartition":
               result = methodInvocation.proceed();
               this.shutdownPartition(methodInvocation, configuredDeployments, true);
               break;
            case "forceShutdownPartition":
               result = methodInvocation.proceed();
               this.shutdownPartition(methodInvocation, configuredDeployments, false);
               break;
            case "haltPartition":
               result = methodInvocation.proceed();
               this.haltPartition(methodInvocation, configuredDeployments);
               break;
            case "startResourceGroup":
               this.startResourceGroup(methodInvocation, configuredDeployments, false);
               result = methodInvocation.proceed();
               break;
            case "startResourceGroupInAdmin":
               this.startResourceGroup(methodInvocation, configuredDeployments, true);
               result = methodInvocation.proceed();
               break;
            case "suspendResourceGroup":
               result = methodInvocation.proceed();
               this.suspendResourceGroup(methodInvocation, configuredDeployments, true);
               break;
            case "forceSuspendResourceGroup":
               result = methodInvocation.proceed();
               this.suspendResourceGroup(methodInvocation, configuredDeployments, false);
               break;
            case "resumeResourceGroup":
               this.resumeResourceGroup(methodInvocation, configuredDeployments);
               result = methodInvocation.proceed();
               break;
            case "shutdownResourceGroup":
               result = methodInvocation.proceed();
               this.shutdownResourceGroup(methodInvocation, configuredDeployments, true);
               break;
            case "forceShutdownResourceGroup":
               result = methodInvocation.proceed();
               this.shutdownResourceGroup(methodInvocation, configuredDeployments, false);
               break;
            default:
               result = methodInvocation.proceed();
         }

         return result;
      } catch (Exception var7) {
         throw new Throwable(var7);
      }
   }

   private void startPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      Set affectedRGs = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      this.startOrStartInAdminPartition(methodInvocation, configuredDeployments, affectedRGs, affectedRGs, true);
   }

   private void startPartitionInAdmin(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      Set affectedRGs = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      this.startOrStartInAdminPartition(methodInvocation, configuredDeployments, affectedRGs, Collections.EMPTY_SET, false);
   }

   private void startOrStartInAdminPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, Set affectedRGs, Set rgsToMoveToProduction, boolean needAdminToProduction) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      boolean wasPartitionHalted = this.wasPartitionHalted(methodInvocation);
      this.startOrBootPartition(methodInvocation, configuredDeployments, wasPartitionHalted ? affectedRGs : PartitionUtils.getNonAdminRGs(affectedRGs), wasPartitionHalted ? rgsToMoveToProduction : PartitionUtils.getNonAdminRGs(rgsToMoveToProduction), needAdminToProduction, wasPartitionHalted ? ConfiguredDeployments.InternalAppScope.ANY : ConfiguredDeployments.InternalAppScope.NON_ADMIN);
   }

   private void startOrBootPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, Set resourceGroupsNeedingDeploy, Set resourceGroupsNeedingAdminToProduction, boolean needAdminToProduction, ConfiguredDeployments.InternalAppScope internalAppScope) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      boolean isDebug = PartitionLifecycleDebugger.isDebugEnabled();
      if (isDebug) {
         PartitionLifecycleDebugger.debug("The resource groups passed to deploy during " + methodInvocation.getMethod() + " are ... " + resourceGroupsNeedingDeploy + " with internal app scope " + internalAppScope.name());
      }

      this.updatePartitionScopeAppRuntimeState(partitionMBean.getName());
      configuredDeployments.deploy(partitionMBean, resourceGroupsNeedingDeploy, internalAppScope);
      if (isDebug) {
         PartitionLifecycleDebugger.debug("The resource groups passed to adminToProduction during " + methodInvocation.getMethod() + " are ... " + resourceGroupsNeedingAdminToProduction);
      }

      if (needAdminToProduction) {
         configuredDeployments.adminToProduction(partitionMBean, resourceGroupsNeedingAdminToProduction, internalAppScope);
      }

      this.backgroundDeploymentManagerService.startBackgroundDeploymentsForPartition(partitionMBean.getName());
   }

   void updatePartitionScopeAppRuntimeState(String partitionName) throws DeploymentException {
      boolean isDebug = PartitionLifecycleDebugger.isDebugEnabled();
      ServerRuntimeMBean runtime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      if (!runtime.isAdminServer() && runtime.getStateVal() == 2) {
         try {
            String deploymentType = "Application";
            DeploymentServiceMessage response = CommonMessageSender.getInstance().sendBlockingGetDeploymentsMsgForPartition(new DomainVersion(), deploymentType, partitionName);
            Iterator iterator = response.getItems().iterator();

            while(iterator.hasNext()) {
               Deployment deployment = (Deployment)iterator.next();
               if (isDebug) {
                  PartitionLifecycleDebugger.debug("PartitionLifecycleDeploymentInterceptor: deployment identity = " + deployment.getCallbackHandlerId() + " : callback handler id from request = " + deploymentType);
               }

               if (deployment.getCallbackHandlerId().equals(deploymentType)) {
                  if (isDebug) {
                     PartitionLifecycleDebugger.debug("PartitionLifecycleDeploymentInterceptor: Should sync with Admin: " + deployment.isSyncWithAdmin());
                  }

                  if (deployment.isSyncWithAdmin()) {
                     Map appRuntimeStates = deployment.getSyncWithAdminState();
                     if (appRuntimeStates != null) {
                        AppRuntimeStateManager.getManager().updatePartitionStartupState(appRuntimeStates, partitionName);
                     }
                  }
               }
            }
         } catch (ManagementException var9) {
            throw new DeploymentException(var9);
         } catch (Exception var10) {
            PartitionLifecycleDebugger.debug("Unable to download app runtime states for partition scoped apps " + partitionName);
            PartitionLifecycleDebugger.debug(StackTraceUtils.throwable2StackTrace(var10));
         }
      }

   }

   private Set getAdminRGs(Set resourceGroups) {
      Set adminRGs = new HashSet();
      Iterator var3 = resourceGroups.iterator();

      while(var3.hasNext()) {
         ResourceGroupMBean rg = (ResourceGroupMBean)var3.next();
         if (rg.isAdministrative()) {
            adminRGs.add(rg);
         }
      }

      return adminRGs;
   }

   private void bootPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws DeploymentException, ServiceFailureException, ResourceGroupLifecycleException {
      Set adminRGs = this.getAdminRGs(PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation)));
      this.startOrBootPartition(methodInvocation, configuredDeployments, adminRGs, adminRGs, true, ConfiguredDeployments.InternalAppScope.ADMIN);
   }

   private void shutdownPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isGraceful) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      Set allResourceGroups = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      Set nonAdminRGs = PartitionUtils.getNonAdminRGs(allResourceGroups);
      this.shutdownOrHaltPartition(methodInvocation, configuredDeployments, isGraceful, nonAdminRGs, nonAdminRGs, ConfiguredDeployments.InternalAppScope.NON_ADMIN);
   }

   private void shutdownOrHaltPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isGraceful, Set resourceGroupsNeedingProductionToAdmin, Set resourceGroupsNeedingUndeploy, ConfiguredDeployments.InternalAppScope internalAppScope) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      boolean isDebug = PartitionLifecycleDebugger.isDebugEnabled();
      this.backgroundDeploymentManagerService.waitForCompletion(partitionMBean.getName());
      this.backgroundDeploymentManagerService.unRegisterOnDemandBackgroundDeployments(partitionMBean.getName());
      if (isDebug) {
         PartitionLifecycleDebugger.debug("The resource groups passed to productionToAdmin during " + methodInvocation.getMethod() + " are ... " + resourceGroupsNeedingProductionToAdmin);
      }

      configuredDeployments.productionToAdmin(partitionMBean, isGraceful, resourceGroupsNeedingProductionToAdmin, internalAppScope);
      if (isDebug) {
         PartitionLifecycleDebugger.debug("The resource groups passed to undeploy during " + methodInvocation.getMethod() + " are ... " + resourceGroupsNeedingUndeploy + " with internal app scope " + internalAppScope);
      }

      configuredDeployments.undeploy(partitionMBean, resourceGroupsNeedingUndeploy, internalAppScope);
   }

   private void suspendPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isGraceful) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      Set resourceGroups = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      PartitionLifecycleDebugger.debug("The resource groups to be " + methodInvocation.getMethod() + " are ... " + resourceGroups);
      configuredDeployments.productionToAdmin(partitionMBean, isGraceful, resourceGroups, ConfiguredDeployments.InternalAppScope.ANY);
   }

   private void resumePartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws ServiceFailureException, DeploymentException, ResourceGroupLifecycleException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      Set resourceGroups = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      PartitionLifecycleDebugger.debug("The resource groups to be " + methodInvocation.getMethod() + " are ... " + resourceGroups);
      configuredDeployments.adminToProduction(partitionMBean, resourceGroups, ConfiguredDeployments.InternalAppScope.ANY);
   }

   private void startResourceGroup(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isAdminMode) throws ServiceFailureException, DeploymentException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      ResourceGroupMBean resourceGroupMBean = getResourceMBean(methodInvocation.getArguments(), partitionMBean);
      configuredDeployments.deploy(resourceGroupMBean);
      if (!isAdminMode) {
         configuredDeployments.adminToProduction(resourceGroupMBean);
      }

   }

   private void shutdownResourceGroup(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isGraceful) throws ServiceFailureException, DeploymentException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      ResourceGroupMBean resourceGroupMBean = getResourceMBean(methodInvocation.getArguments(), partitionMBean);
      configuredDeployments.productionToAdmin(resourceGroupMBean, isGraceful);
      configuredDeployments.undeploy(resourceGroupMBean);
   }

   private void suspendResourceGroup(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments, boolean isGraceful) throws ServiceFailureException, DeploymentException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      ResourceGroupMBean resourceGroupMBean = getResourceMBean(methodInvocation.getArguments(), partitionMBean);
      configuredDeployments.productionToAdmin(resourceGroupMBean, isGraceful);
   }

   private void resumeResourceGroup(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws ServiceFailureException, DeploymentException {
      PartitionMBean partitionMBean = getPartitionMBean(methodInvocation.getArguments());
      ResourceGroupMBean resourceGroupMBean = getResourceMBean(methodInvocation.getArguments(), partitionMBean);
      configuredDeployments.adminToProduction(resourceGroupMBean);
   }

   private void haltPartition(MethodInvocation methodInvocation, ConfiguredDeployments configuredDeployments) throws DeploymentException, ServiceFailureException, ResourceGroupLifecycleException {
      Set allResourceGroups = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      this.shutdownOrHaltPartition(methodInvocation, configuredDeployments, false, allResourceGroups, this.wasPartitionInShutdownAdmin(methodInvocation) ? this.getAdminRGs(allResourceGroups) : allResourceGroups, this.wasPartitionInShutdownAdmin(methodInvocation) ? ConfiguredDeployments.InternalAppScope.ADMIN : ConfiguredDeployments.InternalAppScope.ANY);
   }

   public PartitionRuntimeMBean getPartitionRuntime(MethodInvocation methodInvocation) {
      return (PartitionRuntimeMBean)PartitionRuntimeMBean.class.cast(super.getPartitionRuntime(methodInvocation));
   }

   private boolean wasPartitionHalted(MethodInvocation methodInvocation) {
      PartitionRuntimeMBean pRT = this.getPartitionRuntime(methodInvocation);
      return pRT.getPrevInternalState() == State.UNKNOWN && pRT.getPrevInternalSubState() == State.UNKNOWN || pRT.getPrevInternalState() == State.SHUTDOWN && pRT.getPrevInternalSubState() == State.HALTED;
   }

   private boolean wasPartitionInShutdownAdmin(MethodInvocation methodInvocation) {
      PartitionRuntimeMBean pRT = this.getPartitionRuntime(methodInvocation);
      return pRT.getPrevInternalState() == State.SHUTDOWN && pRT.getPrevInternalSubState() == State.BOOTED;
   }

   private static PartitionMBean getPartitionMBean(Object[] obj) {
      if (obj != null && obj.length != 0) {
         if (obj[0] == null) {
            return null;
         } else if (!(obj[0] instanceof String)) {
            throw new IllegalArgumentException("Argument is not a String");
         } else {
            String partitionName = (String)obj[0];
            return lookupPartition(partitionName);
         }
      } else {
         throw new IllegalArgumentException("No arguments were passed");
      }
   }

   private static ResourceGroupMBean getResourceMBean(Object[] obj, PartitionMBean partitionMBean) {
      if (obj != null && obj.length >= 2) {
         if (!(obj[1] instanceof String)) {
            throw new IllegalArgumentException("Argument is not a string");
         } else {
            String resourceGroupName = (String)obj[1];
            return partitionMBean != null || obj[0] != null && !"DOMAIN".equals(obj[0]) ? lookupResourceGroup(partitionMBean, resourceGroupName) : lookupDomainResourceGroup(resourceGroupName);
         }
      } else {
         throw new IllegalArgumentException("Too few arguments were passed");
      }
   }

   private static PartitionMBean lookupPartition(String partitionName) {
      return getDomain().lookupPartition(partitionName);
   }

   private static ResourceGroupMBean lookupResourceGroup(PartitionMBean partition, String resourceGroupName) {
      return partition.lookupResourceGroup(resourceGroupName);
   }

   private static ResourceGroupMBean lookupDomainResourceGroup(String resourceGroupName) {
      return getDomain().lookupResourceGroup(resourceGroupName);
   }

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }
}
