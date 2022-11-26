package weblogic.deploy.internal.targetserver;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.Deployment;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.management.ManagementException;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.provider.MSIService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public final class DeploymentServiceDispatcher implements DeploymentReceiver, ConnectListener {
   private String identity;
   private final DeploymentService service;
   private DeploymentManager deploymentManager;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private DeploymentServiceDispatcher() {
      this.service = DeploymentService.getDeploymentService();
   }

   public static DeploymentServiceDispatcher getInstance() {
      return DeploymentServiceDispatcher.Maker.instance;
   }

   private void debug(String s) {
      Debug.deploymentDebug(s);
   }

   public void initialize(String identity, Version version, DeploymentManager deploymentManager) {
      this.identity = identity;
      this.deploymentManager = deploymentManager;

      try {
         DeploymentContext context = this.service.registerHandler(version, this);
         this.synchStateWithAdmin(context, identity);
      } catch (Throwable var8) {
         if (Debug.isDeploymentDebugEnabled()) {
            this.debug("Error registering DeploymentReceiver for '" + identity + "' with DeploymentService due to " + var8);
         }

         MSIService msiService = (MSIService)GlobalServiceLocator.getServiceLocator().getService(MSIService.class, new Annotation[0]);
         msiService.setAdminServerAvailable(false);

         try {
            AppRuntimeStateManager.getManager().loadStartupState((Map)null);
         } catch (ManagementException var7) {
            var7.printStackTrace();
         }

         ConnectMonitorFactory.getConnectMonitor().addConnectListener(this);
      }

   }

   private void synchStateWithAdmin(DeploymentContext context, String identity) throws ManagementException {
      Iterator deployments = context.getDeploymentRequest().getDeployments(identity);
      boolean runtimeStateLoaded = false;

      while(deployments.hasNext()) {
         Deployment deployment = (Deployment)deployments.next();
         if (deployment != null && deployment.isSyncWithAdmin()) {
            Map appRuntimeStates = deployment.getSyncWithAdminState();
            if (appRuntimeStates != null) {
               AppRuntimeStateManager.getManager().loadStartupState(appRuntimeStates);
               runtimeStateLoaded = true;
            }
         }
      }

      if (!runtimeStateLoaded) {
         AppRuntimeStateManager.getManager().loadStartupState((Map)null);
      }

   }

   public final void shutdown() {
      this.service.unregisterHandler(this.getHandlerIdentity());
   }

   public final void notifyContextUpdated(long deploymentId) {
      this.service.notifyContextUpdated(deploymentId, this.getHandlerIdentity());
   }

   public final void notifyContextUpdateFailed(long deploymentId, Throwable reason) {
      this.service.notifyContextUpdateFailed(deploymentId, this.getHandlerIdentity(), reason);
   }

   public final void notifyPrepareSuccess(long deploymentId) {
      this.service.notifyPrepareSuccess(deploymentId, this.getHandlerIdentity());
   }

   public final void notifyPrepareFailure(long deploymentId, Throwable reason) {
      this.service.notifyPrepareFailure(deploymentId, this.getHandlerIdentity(), reason);
   }

   public final void notifyCommitSuccess(long deploymentId) {
      this.service.notifyCommitSuccess(deploymentId, this.getHandlerIdentity());
   }

   public final void notifyCommitFailure(long deploymentId, Throwable reason) {
      this.service.notifyCommitFailure(deploymentId, this.getHandlerIdentity(), reason);
   }

   public final void notifyCancelSuccess(long deploymentId) {
      this.service.notifyCancelSuccess(deploymentId, this.getHandlerIdentity());
   }

   public final void notifyCancelFailure(long deploymentId, Throwable reason) {
      this.service.notifyCancelFailure(deploymentId, this.getHandlerIdentity(), reason);
   }

   public final void notifyStatusUpdate(long deploymentId, Serializable status) {
      this.service.notifyStatusUpdate(deploymentId, this.getHandlerIdentity(), status);
   }

   public final String getHandlerIdentity() {
      return this.identity;
   }

   public void updateDeploymentContext(DeploymentContext context) {
      this.deploymentManager.handleUpdateDeploymentContext(context);
   }

   public final void prepare(DeploymentContext deploymentContext) {
      this.deploymentManager.handlePrepare(deploymentContext);
   }

   public final void commit(DeploymentContext deploymentContext) {
      this.deploymentManager.handleCommit(deploymentContext);
   }

   public final void cancel(DeploymentContext deploymentContext) {
      this.deploymentManager.handleCancel(deploymentContext);
   }

   public final void prepareCompleted(DeploymentContext deploymentContext, String identifier) {
      if (identifier.equals("Configuration")) {
         this.deploymentManager.configPrepareCompleted(deploymentContext);
      }

   }

   public final void commitCompleted(DeploymentContext deploymentContext, String identifier) {
      if (identifier.equals("Configuration")) {
         this.deploymentManager.configCommitCompleted(deploymentContext);
      }

   }

   public void onConnect(ConnectEvent event) {
      ManagementService.getPropertyService(kernelId).waitForChannelServiceReady();
      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      if (rt != null) {
         if (event.getServerName().equals(rt.getAdminServerName())) {
            ConnectMonitorFactory.getConnectMonitor().removeConnectListener(this);
            Collection deployments = OrderedDeployments.getDeployments();
            Iterator var4 = deployments.iterator();

            while(var4.hasNext()) {
               Object dep = var4.next();
               if (dep instanceof BasicDeployment) {
                  BasicDeployment deployment = (BasicDeployment)dep;
                  if (deployment.getSavedState() != null) {
                     DeploymentManager.getInstance().relayStatus(-1L, deployment.getSavedState());
                  }
               }
            }

         }
      }
   }

   // $FF: synthetic method
   DeploymentServiceDispatcher(Object x0) {
      this();
   }

   static class Maker {
      static final DeploymentServiceDispatcher instance = new DeploymentServiceDispatcher();
   }
}
