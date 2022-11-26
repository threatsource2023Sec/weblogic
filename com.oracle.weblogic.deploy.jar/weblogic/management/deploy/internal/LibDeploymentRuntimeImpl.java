package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Properties;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.LibDeploymentRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class LibDeploymentRuntimeImpl extends DomainRuntimeMBeanDelegate implements LibDeploymentRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String libName;
   private final String libId;
   private final String libSpecVersion;
   private final String libImplVersion;
   private final LibraryMBean deployable;
   private final String partitionName;
   private DeploymentManagerImpl deploymentManager = null;
   private static HashSet redeployRecognizedOptions = new HashSet();
   private static HashSet undeployRecognizedOptions = new HashSet();

   LibDeploymentRuntimeImpl(LibraryMBean deployable, RuntimeMBeanDelegate restParent) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(deployable.getName()), (RuntimeMBean)("DOMAIN".equals(ApplicationVersionUtils.getPartitionName(deployable.getName())) ? ManagementService.getDomainAccess(kernelId).getDomainRuntime() : restParent));
      this.setRestParent(restParent);
      this.deploymentManager = (DeploymentManagerImpl)restParent;
      this.libId = deployable.getApplicationIdentifier();
      this.libName = ApplicationVersionUtils.getApplicationName(this.libId);
      this.libSpecVersion = ApplicationVersionUtils.getLibSpecVersion(this.libId);
      this.libImplVersion = ApplicationVersionUtils.getLibImplVersion(this.libId);
      this.deployable = deployable;
      String pNameToUse = ApplicationVersionUtils.getPartitionName(deployable.getName());
      if ("DOMAIN".equals(pNameToUse)) {
         pNameToUse = null;
      }

      this.partitionName = pNameToUse;
   }

   public String getLibraryName() {
      return this.libName;
   }

   public String getLibraryIdentifier() {
      return this.libId;
   }

   public String getSpecificationVersion() {
      return this.libSpecVersion;
   }

   public String getImplementationVersion() {
      return this.libImplVersion;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public DeploymentProgressObjectMBean undeploy() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(LibDeploymentRuntimeImpl.OperationType.UNDEPLOY, true, deploymentData);
   }

   public DeploymentProgressObjectMBean undeploy(String[] targets, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, undeployRecognizedOptions, "undeploy");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
      return this.doOperation(LibDeploymentRuntimeImpl.OperationType.UNDEPLOY, false, deploymentData);
   }

   public DeploymentProgressObjectMBean redeploy() throws RuntimeException {
      DeploymentData deploymentData = new DeploymentData();
      return this.doOperation(LibDeploymentRuntimeImpl.OperationType.REDEPLOY, true, deploymentData);
   }

   public DeploymentProgressObjectMBean redeploy(String[] targets, Properties deploymentOptions) throws RuntimeException {
      DeployHelper.validateOptions(deploymentOptions, redeployRecognizedOptions, "redeploy");
      DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
      return this.doOperation(LibDeploymentRuntimeImpl.OperationType.REDEPLOY, false, deploymentData);
   }

   private DeploymentProgressObjectMBean doOperation(OperationType operation, boolean sync, DeploymentData deploymentData) throws RuntimeException {
      DeploymentProgressObjectMBean progressObjectMBean = null;
      DeploymentTaskRuntimeMBean task = null;

      try {
         DeployerRuntimeMBean deployer = null;
         if (this.partitionName == null) {
            deployer = ManagementService.getDomainAccess(kernelId).getDeployerRuntime();
         } else {
            DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            DomainPartitionRuntimeMBean domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(this.partitionName);
            deployer = domainPartitionRuntime.getDeployerRuntime();
         }

         deploymentData.setLibrary(true);
         deploymentData.getDeploymentOptions().setLibSpecVersion((String)null);
         deploymentData.getDeploymentOptions().setLibImplVersion((String)null);
         if (this.partitionName != null) {
            deploymentData.getDeploymentOptions().setPartition(this.partitionName);
         }

         switch (operation) {
            case UNDEPLOY:
               task = deployer.undeploy(this.name, deploymentData, (String)null, false);
               break;
            case REDEPLOY:
               task = deployer.redeploy(this.name, deploymentData, (String)null, false);
         }

         progressObjectMBean = this.deploymentManager.allocateDeploymentProgressObject(this.getName(), task, this.deployable);
         task.start();
         if (sync) {
            task.waitForTaskCompletion(-1L);
         }
      } catch (Throwable var9) {
         RuntimeException rtEx = ExceptionTranslator.translateException(var9);
         if (progressObjectMBean == null || task == null) {
            throw rtEx;
         }

         ((DeploymentProgressObjectImpl)progressObjectMBean).addException(rtEx);
      }

      return progressObjectMBean;
   }

   static {
      redeployRecognizedOptions.add("clusterDeploymentTimeout");
      redeployRecognizedOptions.add("timeout");
      redeployRecognizedOptions.add("useNonExclusiveLock");
      redeployRecognizedOptions.add("partition");
      redeployRecognizedOptions.add("editSession");
      redeployRecognizedOptions.add("resourceGroupTemplate");
      undeployRecognizedOptions.add("clusterDeploymentTimeout");
      undeployRecognizedOptions.add("timeout");
      undeployRecognizedOptions.add("useNonExclusiveLock");
      undeployRecognizedOptions.add("partition");
      undeployRecognizedOptions.add("editSession");
      undeployRecognizedOptions.add("resourceGroupTemplate");
   }

   private static enum OperationType {
      UNDEPLOY,
      REDEPLOY;
   }
}
