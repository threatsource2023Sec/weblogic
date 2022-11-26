package weblogic.deploy.service.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.ChangeDescriptorFactory;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferHandlerExistsException;
import weblogic.deploy.service.DataTransferHandlerManager;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentProvider;
import weblogic.deploy.service.DeploymentProviderManager;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.DeploymentReceiversCoordinator;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentRequestFactory;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.DeploymentServiceOperations;
import weblogic.deploy.service.InvalidCreateChangeDescriptorException;
import weblogic.deploy.service.RegistrationException;
import weblogic.deploy.service.RegistrationExistsException;
import weblogic.deploy.service.RequiresTaskMediatedStartException;
import weblogic.deploy.service.StatusListener;
import weblogic.deploy.service.StatusListenerManager;
import weblogic.deploy.service.StatusRelayer;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.datatransferhandlers.DataHandlerManager;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentService;
import weblogic.deploy.service.internal.targetserver.TargetDeploymentService;
import weblogic.deploy.service.internal.targetserver.TargetDeploymentsManager;
import weblogic.deploy.service.internal.transport.CommonMessageReceiver;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.management.ManagementException;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.LocatorUtilities;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(
   value = 5,
   mode = 0
)
public final class DeploymentService extends AbstractServerService implements DataTransferHandlerManager, DeploymentRequestFactory, DeploymentServiceOperations, DeploymentReceiversCoordinator, ChangeDescriptorFactory, DeploymentProviderManager, StatusListenerManager, StatusRelayer {
   @Inject
   @Named("PropertyService")
   private ServerService dependencyOnPropertyService;
   @Inject
   @Named("PreConfigBootService")
   private ServerService dependencyOnPreConfigBootService;
   public static final byte DEPLOYMENT_SERVICE_VERSION = 2;
   private final AdminDeploymentService adminDelegate = AdminDeploymentService.getDeploymentService();
   @Inject
   private TargetDeploymentService targetDelegate;
   @Inject
   private CommonMessageSender messageSender;
   @Inject
   private CommonMessageReceiver messageReceiver;
   private final Map dataTransferHandlers = new HashMap();
   private static Boolean crossPartitionConcurrentAppPrepareEnabled;

   /** @deprecated */
   @Deprecated
   public static DeploymentService getDeploymentService() {
      return DeploymentService.DeploymentServiceInitializer.singleton;
   }

   public final CommonMessageReceiver getMessageReceiver() {
      return this.messageReceiver;
   }

   public final CommonMessageSender getMessageSender() {
      return this.messageSender;
   }

   public static final byte getVersionByte() {
      return 2;
   }

   private static final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private static final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public final void registerDataTransferHandler(DataTransferHandler provider) throws DataTransferHandlerExistsException {
      String handlerType = provider.getType();
      synchronized(this.dataTransferHandlers) {
         if (this.dataTransferHandlers.get(handlerType) != null) {
            throw new DataTransferHandlerExistsException(DeploymentServiceLogger.logDataHandlerExistsLoggable(handlerType).getMessage());
         } else {
            this.dataTransferHandlers.put(handlerType, provider);
         }
      }
   }

   public final DataTransferHandler getDataTransferHandler(String identity) {
      synchronized(this.dataTransferHandlers) {
         return (DataTransferHandler)this.dataTransferHandlers.get(identity);
      }
   }

   public final String[] getRegisteredDataTransferHandlerTypes() {
      synchronized(this.dataTransferHandlers) {
         Set tmp = this.dataTransferHandlers.keySet();
         String[] result = (String[])tmp.toArray(new String[tmp.size()]);
         return result;
      }
   }

   public final DeploymentRequest createDeploymentRequest() throws ManagementException {
      return this.adminDelegate.createDeploymentRequest();
   }

   public final void register(Version version, DeploymentServiceCallbackHandler callbackHandler) throws RegistrationExistsException {
      this.adminDelegate.register(version, callbackHandler);
   }

   public final DeploymentRequestTaskRuntimeMBean deploy(DeploymentRequest deploymentRequest) throws RequiresTaskMediatedStartException {
      return this.adminDelegate.deploy(deploymentRequest);
   }

   public final DeploymentRequestTaskRuntimeMBean startDeploy(DeploymentRequest deploymentRequest) {
      return this.adminDelegate.startDeploy(deploymentRequest);
   }

   public final void cancel(DeploymentRequest deploymentRequest) {
      this.adminDelegate.cancel(deploymentRequest);
   }

   public final void unregister(String callbackHandlerIdentity) {
      this.adminDelegate.unregister(callbackHandlerIdentity);
   }

   public final DeploymentContext registerHandler(Version version, DeploymentReceiver deploymentReceiver) throws RegistrationException {
      return this.targetDelegate.registerHandler(version, deploymentReceiver);
   }

   public final void unregisterHandler(String deploymentType) {
      this.targetDelegate.unregisterHandler(deploymentType);
   }

   public final void notifyContextUpdated(final long deploymentId, final String deploymentType) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyContextUpdated(deploymentId, deploymentType);
         }
      });
   }

   public final void notifyContextUpdateFailed(final long deploymentId, final String deploymentType, final Throwable reason) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyContextUpdateFailed(deploymentId, deploymentType, reason);
         }
      });
   }

   public final void notifyPrepareSuccess(final long deploymentId, final String deploymentType) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyPrepareSuccess(deploymentId, deploymentType);
         }
      });
   }

   public final void notifyPrepareFailure(final long deploymentId, final String deploymentType, final Throwable reason) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyPrepareFailure(deploymentId, deploymentType, reason);
         }
      });
   }

   public final void notifyCommitSuccess(final long deploymentId, final String deploymentType) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyCommitSuccess(deploymentId, deploymentType);
         }
      });
   }

   public final void notifyCommitFailure(final long deploymentId, final String deploymentType, final Throwable reason) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyCommitFailure(deploymentId, deploymentType, reason);
         }
      });
   }

   public final void notifyCancelSuccess(final long deploymentId, final String deploymentType) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyCancelSuccess(deploymentId, deploymentType);
         }
      });
   }

   public final void notifyCancelFailure(final long deploymentId, final String deploymentType, final Throwable reason) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            DeploymentService.this.targetDelegate.notifyCancelFailure(deploymentId, deploymentType, reason);
         }
      });
   }

   public final void notifyStatusUpdate(long deploymentId, String deploymentType, Serializable statusObject) {
      this.targetDelegate.notifyStatusUpdate(deploymentId, deploymentType, statusObject);
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version) throws InvalidCreateChangeDescriptorException {
      return this.adminDelegate.createChangeDescriptor(operation, targetPath, sourcePath, version);
   }

   public final ChangeDescriptor createChangeDescriptor(String operation, String targetPath, String sourcePath, Serializable version, String identity) throws InvalidCreateChangeDescriptorException {
      return this.adminDelegate.createChangeDescriptor(operation, targetPath, sourcePath, version, identity);
   }

   public final ChangeDescriptor createChangeDescriptor(Serializable data, Serializable version) {
      return this.adminDelegate.createChangeDescriptor(data, version);
   }

   public final void unregisterDeploymentProvider(DeploymentProvider provider) {
      this.adminDelegate.unregisterDeploymentProvider(provider);
   }

   public final void registerDeploymentProvider(DeploymentProvider provider) {
      this.adminDelegate.registerDeploymentProvider(provider);
   }

   public final Set getRegisteredDeploymentProviders() {
      return this.adminDelegate.getRegisteredDeploymentProviders();
   }

   public void registerStatusListener(String channelId, StatusListener listener) {
      this.adminDelegate.registerStatusListener(channelId, listener);
   }

   public void unregisterStatusListener(String channelId) {
      this.adminDelegate.unregisterStatusListener(channelId);
   }

   public void relayStatus(String channelId, Serializable statusObject) {
      this.targetDelegate.relayStatus(channelId, statusObject);
   }

   public void relayStatus(long sessionId, String channelId, Serializable statusObject) {
      this.targetDelegate.relayStatus(sessionId, channelId, statusObject);
   }

   public final void start() throws ServiceFailureException {
      if (isDebugEnabled()) {
         debug("Starting DeploymentService");
      }

      this.messageSender.getDelegate().setLoopbackReceiver(this.messageReceiver.getDelegate());

      try {
         this.registerDataTransferHandler(DataHandlerManager.getInstance().getHttpDataTransferHandler());
      } catch (DataTransferHandlerExistsException var2) {
         if (Debug.isServiceDebugEnabled()) {
            Debug.serviceLogger.debug("Data transfer handler for Http already registered");
         }
      }

   }

   public final void stop() throws ServiceFailureException {
      if (isDebugEnabled()) {
         debug("Stopping DeploymentService");
      }

   }

   public final void halt() throws ServiceFailureException {
      if (isDebugEnabled()) {
         debug("Halting DeploymentService");
      }

   }

   public static boolean isCrossPartitionConcurrentAppPrepareEnabled() {
      if (crossPartitionConcurrentAppPrepareEnabled != null) {
         return crossPartitionConcurrentAppPrepareEnabled;
      } else {
         String propName = "weblogic.deploy.internal.CrossPartitionConcurrentAppPrepareEnabled";
         crossPartitionConcurrentAppPrepareEnabled = System.getProperty(propName) == null ? true : Boolean.getBoolean(propName);
         return crossPartitionConcurrentAppPrepareEnabled;
      }
   }

   public static void setCrossPartitionConcurrentAppPrepareEnabled(Boolean enabled) {
      crossPartitionConcurrentAppPrepareEnabled = enabled;
   }

   public void addOrUpdateCurrentDomainVersion(String identity, Version version) {
      TargetDeploymentsManager.getInstance().getCurrentDomainVersion().addOrUpdateDeploymentVersion(identity, version);
   }

   private static final class DeploymentServiceInitializer {
      private static final DeploymentService singleton = (DeploymentService)LocatorUtilities.getService(DeploymentService.class);
   }
}
