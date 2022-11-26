package weblogic.deploy.service.internal.adminserver;

import java.io.File;
import java.io.Serializable;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.ChangeDescriptorFactory;
import weblogic.deploy.service.DeploymentProvider;
import weblogic.deploy.service.DeploymentProviderManager;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentRequestFactory;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.DeploymentServiceOperations;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.InvalidCreateChangeDescriptorException;
import weblogic.deploy.service.RegistrationExistsException;
import weblogic.deploy.service.RequiresTaskMediatedStartException;
import weblogic.deploy.service.StatusListener;
import weblogic.deploy.service.StatusListenerManager;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentRequestTaskRuntimeMBeanImpl;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.InvalidStateException;
import weblogic.deploy.service.internal.ServiceRequest;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;

@Singleton
public final class AdminDeploymentService implements DeploymentRequestFactory, DeploymentServiceOperations, ChangeDescriptorFactory, DeploymentProviderManager, StatusListenerManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   private AdminRequestManager requestManager;
   private AdminDeploymentsManager deploymentsManager;
   private final StatusDeliverer statusDeliverer = StatusDeliverer.getInstance();
   private final HashSet providers = new HashSet();
   private String localServerName;

   private AdminDeploymentService() {
   }

   public static AdminDeploymentService getDeploymentService() {
      return AdminDeploymentService.AdminDeploymentServiceInitializer.SINGLETON;
   }

   private static final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private static final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   private String getLocalServerName() {
      if (this.localServerName == null) {
         this.localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      }

      return this.localServerName;
   }

   private void sendCancelFailedTo(DeploymentRequest request, Exception reason) {
      AdminDeploymentException ade = new AdminDeploymentException();
      String operation = DeploymentServiceLogger.indeterminate();
      FailureDescription failureDescription = new FailureDescription(this.localServerName, reason, operation);
      ade.addFailureDescription(failureDescription);
      DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)request.getTaskRuntime();
      if (taskRuntime == null) {
         if (isDebugEnabled()) {
            debug("Cancel attempt failed since underlying request " + request + " has completed");
         }

      } else {
         taskRuntime.addFailedTarget(this.localServerName, reason);
         this.requestManager.deliverDeployCancelFailedCallback((AdminRequestImpl)request, ade);
         taskRuntime.setState(7);
      }
   }

   public final DeploymentRequest createDeploymentRequest() throws ManagementException {
      Class var2 = AdminDeploymentService.class;
      AdminRequestImpl request;
      synchronized(AdminDeploymentService.class) {
         request = new AdminRequestImpl();
         request.setId();
      }

      DeploymentRequestTaskRuntimeMBean runtimeTask = new DeploymentRequestTaskRuntimeMBeanImpl("Deploy request with id '" + request.getId() + "'", request);
      request.setTaskRuntime(runtimeTask);
      return request;
   }

   public final void register(Version version, DeploymentServiceCallbackHandler callbackHandler) throws RegistrationExistsException {
      if (isDebugEnabled()) {
         debug("DeploymentServiceCallbackHandler for '" + callbackHandler.getHandlerIdentity() + "' registering with version '" + version.toString() + "'");
      }

      this.deploymentsManager = AdminDeploymentsManager.getInstance();
      this.deploymentsManager.registerCallbackHandler(version, callbackHandler);
   }

   public final DeploymentRequestTaskRuntimeMBean deploy(DeploymentRequest deploymentRequest) throws RequiresTaskMediatedStartException {
      if (isDebugEnabled()) {
         debug("'deploy'-ing id " + deploymentRequest.getId());
      }

      if (deploymentRequest.isStartControlEnabled()) {
         throw new RequiresTaskMediatedStartException(DeploymentServiceLogger.logStartControlLoggable().getMessage());
      } else {
         return this.startDeploy(deploymentRequest);
      }
   }

   public final DeploymentRequestTaskRuntimeMBean startDeploy(final DeploymentRequest deploymentRequest) {
      final DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)deploymentRequest.getTaskRuntime();
      if (deploymentRequest instanceof AdminRequestImpl) {
         if (isDebugEnabled()) {
            debug("starting 'deploy' of id '" + deploymentRequest.getId() + "'");
         }

         this.requestManager.addRequest(new ServiceRequest() {
            public void run() {
               taskRuntime.setState(1);
               ((AdminRequestImpl)deploymentRequest).run();
            }

            public String toString() {
               return deploymentRequest.toString();
            }
         });
      } else {
         taskRuntime.setState(3);
         String msg = DeploymentServiceLogger.incompatibleModification();
         Exception failure = new Exception(msg);
         taskRuntime.addFailedTarget(this.getLocalServerName(), failure);
      }

      return taskRuntime;
   }

   public final void cancel(DeploymentRequest deploymentRequest) {
      long deploymentIdentifier = deploymentRequest.getId();
      if (isDebugEnabled()) {
         debug("'cancel' called on id '" + deploymentIdentifier + "'");
      }

      AdminRequestImpl requestToBeCancelled = this.requestManager.getRequest(deploymentIdentifier);
      if (requestToBeCancelled == null) {
         String reason = DeploymentServiceLogger.noRequestToCancel(deploymentIdentifier);
         if (isDebugEnabled()) {
            debug(reason);
         }

         this.requestManager.addPendingCancel(deploymentIdentifier);
         this.sendCancelFailedTo(deploymentRequest, new Exception(reason));
      } else {
         DeploymentRequestTaskRuntimeMBeanImpl taskRuntime = (DeploymentRequestTaskRuntimeMBeanImpl)deploymentRequest.getTaskRuntime();
         taskRuntime.setState(5);

         try {
            requestToBeCancelled.cancel();
         } catch (InvalidStateException var8) {
            if (isDebugEnabled()) {
               debug("attempt to 'cancel' id '" + deploymentIdentifier + "' failed due to '" + var8.getMessage() + "'");
            }

            AdminRequestStatus requestStatus = requestToBeCancelled.getStatus();
            if (requestStatus != null) {
               requestStatus.signalCancelFailed(false);
            }
         }

      }
   }

   public final void unregister(final String callbackHandlerIdentity) {
      final String requestId = "DeploymentServiceCallbackHandler for '" + callbackHandlerIdentity + "' unregistering ";
      if (isDebugEnabled()) {
         debug(requestId);
      }

      this.requestManager.addRequest(new ServiceRequest() {
         public void run() {
            AdminDeploymentService.this.deploymentsManager.unregisterCallbackHandler(callbackHandlerIdentity);
            AdminDeploymentService.this.requestManager.scheduleNextRequest();
         }

         public String toString() {
            return requestId;
         }
      });
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version) throws InvalidCreateChangeDescriptorException {
      return this.createChangeDescriptor(operation, targetPath, sourcePath, version, (String)null);
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version, String identity) throws InvalidCreateChangeDescriptorException {
      String sep = File.separator;
      String filePath = DomainDir.getRootDir() + sep + sourcePath;
      File file = new File(filePath);
      if (!file.exists()) {
         throw new InvalidCreateChangeDescriptorException(DeploymentServiceLogger.logNoFileLoggable(filePath).getMessage());
      } else if (!operation.equals("add") && !operation.equals("delete") && !operation.equals("update")) {
         String msg = DeploymentServiceLogger.unsupportedOperation(operation);
         throw new InvalidCreateChangeDescriptorException(msg);
      } else {
         ChangeDescriptor result = new ChangeDescriptorImpl(operation, targetPath, sourcePath, version, identity);
         return result;
      }
   }

   public final ChangeDescriptor createChangeDescriptor(Serializable data, Serializable version) {
      return new ChangeDescriptorImpl(data, version);
   }

   public final void unregisterDeploymentProvider(DeploymentProvider provider) {
      synchronized(this.providers) {
         this.providers.remove(provider);
      }
   }

   public final void registerDeploymentProvider(DeploymentProvider provider) {
      synchronized(this.providers) {
         this.providers.add(provider);
      }
   }

   public final Set getRegisteredDeploymentProviders() {
      synchronized(this.providers) {
         return (Set)this.providers.clone();
      }
   }

   public void registerStatusListener(String channelId, StatusListener listener) {
      this.statusDeliverer.registerStatusListener(channelId, listener);
   }

   public void unregisterStatusListener(String channelId) {
      this.statusDeliverer.unregisterStatusListener(channelId);
   }

   private static class AdminDeploymentServiceInitializer {
      private static final AdminDeploymentService SINGLETON;

      static {
         if (ManagementService.getPropertyService(AdminDeploymentService.kernelId).isAdminServer()) {
            ServiceLocatorUtilities.addClasses(GlobalServiceLocator.getServiceLocator(), new Class[]{AdminDeploymentService.class});
            SINGLETON = (AdminDeploymentService)LocatorUtilities.getService(AdminDeploymentService.class);
         } else {
            SINGLETON = null;
         }

      }
   }
}
