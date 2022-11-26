package weblogic.deploy.internal.adminserver;

import java.io.Serializable;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentException;
import weblogic.deploy.service.DeploymentProvider;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.InvalidCreateChangeDescriptorException;
import weblogic.deploy.service.RegistrationExistsException;
import weblogic.deploy.service.RequiresRestartFailureDescription;
import weblogic.deploy.service.RequiresTaskMediatedStartException;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;

public final class DeploymentServiceDriver implements DeploymentServiceCallbackHandler {
   private String identity;
   private final DeploymentService service;
   private DeploymentManager deploymentManager;

   private DeploymentServiceDriver() {
      this.service = DeploymentService.getDeploymentService();
   }

   public static DeploymentServiceDriver getInstance() {
      return DeploymentServiceDriver.Maker.DRIVER;
   }

   public void initialize(String identity, Version version, DeploymentManager deploymentManager) throws ManagementException {
      try {
         this.identity = identity;
         this.deploymentManager = deploymentManager;
         this.service.register(version, this);
         this.service.registerDeploymentProvider(deploymentManager);
      } catch (RegistrationExistsException var6) {
         String msg = DeploymentServiceLogger.duplicateRegistration(identity);
         throw new ManagementException(msg);
      }
   }

   public void shutdown() {
      this.service.unregister(this.getHandlerIdentity());
   }

   public DeploymentRequestTaskRuntimeMBean deploy(DeploymentRequest deploymentRequest) throws RequiresTaskMediatedStartException {
      return this.service.deploy(deploymentRequest);
   }

   public DeploymentRequestTaskRuntimeMBean startDeploy(DeploymentRequest deploymentRequest) {
      return this.service.startDeploy(deploymentRequest);
   }

   public String getHandlerIdentity() {
      return this.identity;
   }

   public Deployment[] getDeployments(Version fromVersion, Version toVersion, String serverName, String partitionName) {
      return this.deploymentManager.getDeployments(fromVersion, toVersion, serverName, partitionName);
   }

   public void deploySucceeded(long deploymentIdentifier, FailureDescription[] failureDescriptions) {
      String failures = getFailureDescriptions(failureDescriptions);
      if (failures != null) {
         Debug.deploymentLogger.debug("Deployment id '" + deploymentIdentifier + "' succeeded - however the listed servers did not receive the deployment for the provided reasons:" + failures + " - these servers will receive the deployment when they are reachable from the admin server");
      }

      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestSucceeded(deploymentIdentifier, failureDescriptions);
   }

   public void deployFailed(long deploymentIdentifier, DeploymentException reason) {
      Debug.deploymentLogger.debug("Deployment id '" + deploymentIdentifier + "' failed due to the following reason: " + reason.toString());
      FailureDescription[] failureDescriptions = reason.getFailures();
      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestFailed(deploymentIdentifier, reason, failureDescriptions);
   }

   public void appPrepareFailed(long deploymentIdentifier, DeploymentException reason) {
      Debug.deploymentLogger.debug("Deployment id '" + deploymentIdentifier + "' app prepare failed due to the following reason: " + reason.toString());
   }

   public void commitFailed(long deploymentIdentifier, FailureDescription[] failureDescriptions) {
      String failures = getFailureDescriptions(failureDescriptions);
      if (failures != null) {
         Debug.deploymentLogger.debug("Deployment id '" + deploymentIdentifier + "' succeeded - however the listed servers did not receive the 'commit' for the provided reasons:" + failures);
      }

      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestCommitFailed(deploymentIdentifier, failureDescriptions);
   }

   public void commitSucceeded(long deploymentIdentifier) {
      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestCommitSucceeded(deploymentIdentifier);
   }

   public void cancelSucceeded(long deploymentIdentifier, FailureDescription[] failureDescriptions) {
      String failures = getFailureDescriptions(failureDescriptions);
      Debug.deploymentLogger.debug("Deployment id '" + deploymentIdentifier + "' was successfully canceled - however the listed servers did not receive the cancel due to the provided reasons:" + failures);
      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestCancelSucceeded(deploymentIdentifier, failureDescriptions);
   }

   public void cancelFailed(long deploymentIdentifier, DeploymentException reason) {
      Debug.deploymentLogger.debug("Attempt to cancel deployment id '" + deploymentIdentifier + "' failed due to the following reason: " + reason.toString());
      FailureDescription[] failureDescriptions = reason.getFailures();
      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).deploymentRequestCancelFailed(deploymentIdentifier, reason, failureDescriptions);
   }

   private static String getFailureDescriptions(FailureDescription[] fds) {
      String result = null;
      if (fds != null && fds.length > 0) {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < fds.length; ++i) {
            sb.append("Deployment to '");
            sb.append(fds[i].getServer());
            if (fds[i] instanceof RequiresRestartFailureDescription) {
               sb.append("' requires restart due to non-dynamic changes");
            } else {
               sb.append("' failed with the reason: '");
               sb.append(fds[i].getReason().toString());
               sb.append("'");
            }
         }

         result = sb.toString();
      }

      return result;
   }

   public void receivedStatusFrom(long deploymentIdentifier, Serializable statusObject, String serverName) {
      DeploymentManager.getDeploymentManagerForDeployment(deploymentIdentifier).handleReceivedStatus(deploymentIdentifier, (DeploymentState)statusObject, serverName);
   }

   public final void unregisterDeploymentProvider(DeploymentProvider provider) {
      this.service.unregisterDeploymentProvider(provider);
   }

   public final void registerDeploymentProvider(DeploymentProvider provider) {
      this.service.registerDeploymentProvider(provider);
   }

   public Set getRegisteredDeploymentProviders() {
      return this.service.getRegisteredDeploymentProviders();
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version) throws InvalidCreateChangeDescriptorException {
      return this.service.createChangeDescriptor(operation, targetPath, sourcePath, version);
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version, String identity) throws InvalidCreateChangeDescriptorException {
      return this.service.createChangeDescriptor(operation, targetPath, sourcePath, version, identity);
   }

   public final ChangeDescriptor createChangeDescriptor(Serializable data, Serializable version) {
      return this.service.createChangeDescriptor(data, version);
   }

   public DeploymentRequest createDeploymentRequest() throws ManagementException {
      return this.service.createDeploymentRequest();
   }

   // $FF: synthetic method
   DeploymentServiceDriver(Object x0) {
      this();
   }

   static final class Maker {
      static final DeploymentServiceDriver DRIVER = new DeploymentServiceDriver();
   }
}
