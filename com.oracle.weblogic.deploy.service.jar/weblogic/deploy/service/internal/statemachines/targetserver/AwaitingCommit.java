package weblogic.deploy.service.internal.statemachines.targetserver;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.internal.CalloutManager;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.targetserver.DeploymentContextImpl;
import weblogic.deploy.service.internal.targetserver.TargetCalloutManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.targetserver.TimeAuditorManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public final class AwaitingCommit extends TargetServerState {
   private static final CalloutManager calloutManager = (CalloutManager)LocatorUtilities.getService(TargetCalloutManager.class);
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public final synchronized TargetServerState receivedCancel() {
      this.fireStateTransitionEvent(this, "receivedCancel", this.getId());
      this.cancelIfNecessary();
      return this.getCurrentState();
   }

   public final synchronized TargetServerState receivedCommit() {
      this.fireStateTransitionEvent(this, "receivedCommit", this.getId());

      try {
         if (requestShouldBeAborted(this.getDeploymentRequest())) {
            this.setExpectedNextState(4);
            this.deploymentStatus.scheduleNextRequest();
            if (this.isDebugEnabled()) {
               this.debug("receivedCommit(): AbortCommit, sending commit succeeded msg ");
            }

            this.sendCommitSucceeded();
            return this.getCurrentState();
         }

         if (this.isDebugEnabled()) {
            this.debug("receivedCommit(): continue to handleCommit");
         }

         this.handleCommit();
      } catch (Throwable var4) {
         Throwable t = var4;

         try {
            this.sendCommitFailed(new Exception(t.getMessage()));
         } catch (Exception var3) {
         }
      }

      return this.getCurrentState();
   }

   protected static boolean requestShouldBeAborted(TargetRequestImpl deploymentRequest) {
      if (calloutManager.hasExpectedChangeCallouts() && calloutManager.abortForExpectedChangeCallout("commit")) {
         return true;
      } else {
         if (calloutManager.hasChangeReceivedCallouts()) {
            try {
               DomainMBean current = ManagementService.getRuntimeAccess(kernelId).getDomain();
               DomainMBean proposed = current;
               DeploymentContextImpl context = deploymentRequest.getDeploymentContext();
               if (context != null) {
                  proposed = (DomainMBean)context.getContextComponent("calloutAbortedProposedConfig");
                  if (proposed == null) {
                     proposed = (DomainMBean)context.getContextComponent("PROPOSED_CONFIGURATION");
                  }
               }

               if (proposed == null) {
                  proposed = current;
               }

               if (calloutManager.abortForChangeReceivedCallout("commit", current, proposed)) {
                  return true;
               }
            } catch (Exception var4) {
               Debug.serviceDebug(var4.getMessage());
            }
         }

         return false;
      }
   }

   private final void handleRestartIsPending() {
      DeploymentServiceLogger.logCommitPendingRestart(this.getId());
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      DomainVersion syncToAdminVersion = deploymentRequest.getSyncToAdminVersion();
      if (syncToAdminVersion != null) {
         this.deploymentsManager.setCurrentDomainVersion(syncToAdminVersion);
         if (this.isDebugEnabled()) {
            this.debug("Treating request id " + deploymentRequest.getId() + " with sync to admin version " + syncToAdminVersion + " on this target as 'Commit Success'");
         }

         this.deploymentStatus.commitSkipped();
      } else {
         boolean needsVersionUpdate = !deploymentRequest.isControlRequest();
         if (this.isDebugEnabled()) {
            this.debug("Needs VersionUpdate : " + needsVersionUpdate);
         }

         if (needsVersionUpdate) {
            this.deploymentsManager.setCurrentDomainVersion(deploymentRequest.getProposedDomainVersion());
         }

         if (this.isDebugEnabled()) {
            this.debug("Treating request id " + deploymentRequest.getId() + " on this target as 'Commit Success'");
         }

         this.deploymentStatus.commitSkipped();
         this.sendCommitSucceeded();
      }

      if (this.deploymentStatus != null) {
         this.deploymentStatus.reset();
      }

   }

   private final void handleCommit() throws Exception {
      if (this.deploymentStatus != null && this.deploymentStatus.isRestartPending()) {
         this.handleRestartIsPending();
      } else {
         this.doPreCommitWork();
         this.setExpectedNextState(4);
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         if (this.isDebugEnabled()) {
            this.debug("'commit' for id '" + deploymentRequest + "' from version '" + deploymentRequest.getPreparingFromVersion() + "' to version '" + deploymentRequest.getProposedDomainVersion());
         }

         this.callDeploymentReceivers();
         this.deploymentStatus.scheduleNextRequest();
      }
   }

   private void doPreCommitWork() throws Exception {
      if (this.deploymentStatus == null || !this.deploymentStatus.isCanceledOrAborted()) {
         TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
         if (!deploymentRequest.getConfigCommitCalled()) {
            this.doPreCommitValidation(deploymentRequest);
            if (!this.handleOptimisticConcurrencyViolationIfNecessary(deploymentRequest)) {
               DomainVersion syncToAdminVersion = deploymentRequest.getSyncToAdminVersion();
               if (syncToAdminVersion != null) {
                  this.deploymentsManager.setCurrentDomainVersion(syncToAdminVersion);
               } else {
                  boolean needsVersionUpdate = !deploymentRequest.isControlRequest();
                  if (this.isDebugEnabled()) {
                     this.debug("Needs VersionUpdate : " + needsVersionUpdate);
                  }

                  if (needsVersionUpdate) {
                     this.deploymentsManager.setCurrentDomainVersion(deploymentRequest.getProposedDomainVersion());
                  }
               }

            }
         }
      }
   }

   private boolean handleOptimisticConcurrencyViolationIfNecessary(TargetRequestImpl deploymentRequest) {
      boolean result = false;
      if (deploymentRequest.getSyncToAdminDeployments() == null && !deploymentRequest.isControlRequest()) {
         DomainVersion currentVersion = this.deploymentsManager.getCurrentDomainVersion();
         if (!currentVersion.equals(deploymentRequest.getPreparingFromVersion())) {
            if (this.isDebugEnabled()) {
               this.debug("request '" + deploymentRequest.getId() + "' was 'prepare'-d against version '" + deploymentRequest.getPreparingFromVersion() + "' but is being 'commit'-ted against version '" + currentVersion + "' - request will be aborted locally and an attempt will be made to resynchronize with the admin after which the request will be retried");
            }

            result = true;
            this.deploymentStatus.setAborted();
            this.cancelIfNecessary();
         }

         return result;
      } else {
         return result;
      }
   }

   private final void doPreCommitValidation(TargetRequestImpl deploymentRequest) throws Exception {
      if (deploymentRequest == null) {
         String message = DeploymentServiceLogger.commitNoRequest();
         if (this.isDebugEnabled()) {
            this.debug(message);
         }

         throw new Exception(message);
      }
   }

   protected final void callDeploymentReceivers() throws Exception {
      TargetRequestImpl deploymentRequest = this.getDeploymentRequest();
      TimeAuditorManager.getInstance().startTransition(deploymentRequest.getId(), 2);
      Map deploymentsMap = deploymentRequest.getDeploymentsMap();
      DeploymentReceiver configCallbackHandler = null;
      DeploymentContextImpl context = deploymentRequest.getDeploymentContext();
      Iterator iterator = deploymentsMap.keySet().iterator();

      while(true) {
         while(true) {
            String deploymentType;
            DeploymentReceiver callbackHandler;
            do {
               if (!iterator.hasNext()) {
                  if (configCallbackHandler != null) {
                     if (this.isDebugEnabled()) {
                        this.debug("calling DeploymentReceiver 'Configuration' to 'commit' for id '" + this.getId());
                     }

                     TimeAuditorManager.getInstance().startDeploymentTransition(deploymentRequest.getId(), configCallbackHandler.getHandlerIdentity(), 2);
                     configCallbackHandler.commit(context);
                  }

                  return;
               }

               deploymentType = (String)iterator.next();
               callbackHandler = this.deploymentsManager.getDeploymentReceiver(deploymentType);
            } while(callbackHandler == null);

            boolean isConfigCallbackHandler = deploymentType.equals("Configuration");
            if (deploymentRequest.isConfigurationProviderCalledLast() && configCallbackHandler == null && isConfigCallbackHandler) {
               configCallbackHandler = callbackHandler;
            } else {
               if (this.isDebugEnabled()) {
                  this.debug("calling DeploymentReceiver '" + deploymentType + "' to 'commit' for id '" + this.getId());
               }

               TimeAuditorManager.getInstance().startDeploymentTransition(deploymentRequest.getId(), deploymentType, 2);
               callbackHandler.commit(context);
            }
         }
      }
   }

   public final String toString() {
      return "AwaitingCommit";
   }
}
