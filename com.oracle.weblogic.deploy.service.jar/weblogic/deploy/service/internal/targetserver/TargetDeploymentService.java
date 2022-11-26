package weblogic.deploy.service.internal.targetserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.DeploymentReceiversCoordinator;
import weblogic.deploy.service.RegistrationException;
import weblogic.deploy.service.StatusRelayer;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.ServiceRequest;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;

@Service
public final class TargetDeploymentService implements DeploymentReceiversCoordinator, StatusRelayer {
   @Inject
   private TargetRequestManager requestManager;
   private final TargetDeploymentsManager deploymentsManager = TargetDeploymentsManager.getInstance();

   private TargetDeploymentService() {
   }

   /** @deprecated */
   @Deprecated
   public static TargetDeploymentService getDeploymentService() {
      return TargetDeploymentService.Maker.SINGLETON;
   }

   private void debug(String message) {
      Debug.serviceDebug(message);
   }

   private boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public DeploymentContext registerHandler(Version version, DeploymentReceiver deploymentReceiver) throws RegistrationException {
      final String callbackHandlerId = deploymentReceiver.getHandlerIdentity();
      final String requestId = "registerHandler called from DeploymentReceiver for '" + callbackHandlerId + "'  with version '" + version.toString() + "'";
      if (this.isDebugEnabled()) {
         this.debug(requestId);
      }

      this.deploymentsManager.registerCallbackHandler(version, deploymentReceiver);
      final TargetRequestImpl deploymentRequest = new TargetRequestImpl();
      deploymentRequest.setId();
      final TargetRequestManager requestManagerCopy = this.requestManager;
      final RegistrationResponse registrationResponse = new RegistrationResponse();
      this.requestManager.addRequest(new ServiceRequest() {
         public void run() {
            requestManagerCopy.addToRequestTable(deploymentRequest);
            deploymentRequest.setDeploymentStatus(TargetRequestStatus.createTargetRequestStatus(deploymentRequest));
            deploymentRequest.getDeploymentStatus().setServerStarting();
            CommonMessageSender messageSender = deploymentRequest.getMessageSender();
            DomainVersion currentDomainVersion = TargetDeploymentService.this.deploymentsManager.getCurrentDomainVersion();

            try {
               DeploymentServiceMessage response = messageSender.sendBlockingGetDeploymentsMsg(currentDomainVersion, callbackHandlerId);
               DomainVersion proposedDomainVersion = response.getToVersion();
               Version inVersion = proposedDomainVersion.getDeploymentVersion(callbackHandlerId);
               Iterator iterator = response.getItems().iterator();
               List deploymentsList = new ArrayList();

               while(iterator.hasNext()) {
                  Deployment deployment = (Deployment)iterator.next();
                  String identity = deployment.getCallbackHandlerId();
                  if (TargetDeploymentService.this.isDebugEnabled()) {
                     TargetDeploymentService.this.debug(" TargetDeploymentService: deployment identity = " + identity + " : callback handler id from request = " + callbackHandlerId);
                  }

                  if (identity.equals(callbackHandlerId)) {
                     if (TargetDeploymentService.this.isDebugEnabled()) {
                        TargetDeploymentService.this.debug(" TargetDeploymentService: adding deployment : " + deployment);
                     }

                     deploymentsList.add(deployment);
                  }
               }

               deploymentRequest.setDeployments(deploymentsList);
               DeploymentContextImpl context = new DeploymentContextImpl(deploymentRequest);
               deploymentRequest.setDeploymentContext(context);
               if (inVersion != null) {
                  currentDomainVersion.addOrUpdateDeploymentVersion(callbackHandlerId, inVersion);
               }

               registrationResponse.setResponseReceived(context);
            } catch (Throwable var10) {
               if (TargetDeploymentService.this.isDebugEnabled()) {
                  TargetDeploymentService.this.debug(StackTraceUtils.throwable2StackTrace(var10));
               }

               registrationResponse.setErrorEncountered(var10);
               TargetDeploymentService.this.resetRegistration(deploymentRequest.getDeploymentStatus());
            }
         }

         public String toString() {
            return requestId;
         }
      });
      registrationResponse.waitForResponse();
      if (registrationResponse.errorEncountered()) {
         this.resetRegistration(deploymentRequest.getDeploymentStatus());
         throw new RegistrationException(registrationResponse.getError().toString());
      } else {
         this.resetRegistration(deploymentRequest.getDeploymentStatus());
         return registrationResponse.getContext();
      }
   }

   public void unregisterHandler(final String deploymentType) {
      final String requestId = "registerHandler called from DeploymentReceiver for '" + deploymentType + "' ";
      if (this.isDebugEnabled()) {
         this.debug(requestId);
      }

      this.requestManager.addRequest(new ServiceRequest() {
         public void run() {
            TargetDeploymentService.this.deploymentsManager.unregisterCallbackHandler(deploymentType);
            TargetDeploymentService.this.requestManager.scheduleNextRequest();
         }

         public String toString() {
            return requestId;
         }
      });
   }

   public synchronized void notifyContextUpdated(long deploymentId, String deploymentType) {
      if (this.isDebugEnabled()) {
         this.debug("'context updated' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'context updated' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedContextUpdateCompletedFrom(deploymentType);
         if (status.receivedAllContextUpdates()) {
            Throwable failureReason = status.getSavedError();
            if (failureReason == null) {
               status.getCurrentState().contextUpdated();
            } else {
               status.getCurrentState().contextUpdateFailed(failureReason);
            }
         }
      }

   }

   public synchronized void notifyContextUpdateFailed(long deploymentId, String deploymentType, Throwable reason) {
      if (this.isDebugEnabled()) {
         this.debug("'context update failed' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' due to '" + reason.toString() + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'context update failure' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedContextUpdateCompletedFrom(deploymentType, reason);
         if (status.receivedAllContextUpdates()) {
            status.getCurrentState().contextUpdateFailed(reason);
         }
      }

   }

   public synchronized void notifyPrepareSuccess(long deploymentId, String deploymentType) {
      if (this.isDebugEnabled()) {
         this.debug("'prepare ack' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'prepare ack' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedPrepareAckFrom(deploymentType);
         if ("Configuration".equals(deploymentType) && status.getSavedError() == null) {
            status.getCurrentState().configDeploymentPrepareSucceeded();
         }

         if (status.receivedAllPrepareCompletions()) {
            Throwable t = status.getSavedError();
            if (t == null) {
               status.getCurrentState().prepareSucceeded();
            } else {
               status.getCurrentState().prepareFailed(t);
            }
         }
      }

   }

   public synchronized void notifyPrepareFailure(long deploymentId, String deploymentType, Throwable reason) {
      if (this.isDebugEnabled()) {
         this.debug("'prepare nak' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' due to '" + reason.toString() + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'prepare nak' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedPrepareNakFrom(deploymentType, reason);
         if ("Configuration".equals(deploymentType)) {
            status.getCurrentState().configDeploymentPrepareFailed(reason);
         }

         if (status.receivedAllPrepareCompletions()) {
            status.getCurrentState().prepareFailed(reason);
         }
      }

   }

   public synchronized void notifyCommitSuccess(long deploymentId, String deploymentType) {
      if (this.isDebugEnabled()) {
         this.debug("'commit success' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'commit success' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedCommitAckFrom(deploymentType);
         if (status.receivedAllCommitResponses()) {
            Throwable e = status.getCommitFailureError();
            if (e == null) {
               status.getCurrentState().commitSucceeded();
            } else {
               status.getCurrentState().commitFailed(e);
            }
         }
      }

   }

   public synchronized void notifyCommitFailure(long deploymentId, String deploymentType, Throwable reason) {
      if (this.isDebugEnabled()) {
         this.debug("'commit failure' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' due to '" + reason.toString() + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'commit failure' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.receivedCommitFailureFrom(deploymentType, reason);
         if (status.receivedAllCommitResponses()) {
            status.getCurrentState().commitFailed(reason);
         }
      }

   }

   public synchronized void notifyCancelSuccess(long deploymentId, String deploymentType) {
      if (this.isDebugEnabled()) {
         this.debug("'cancel success' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'cancel success' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.cancelSuccessFrom(deploymentType);
         if (status.receivedAllCancelResponses()) {
            Throwable e = status.getCancelFailureError();
            if (e == null) {
               status.getCurrentState().cancelSucceeded();
            } else {
               status.getCurrentState().cancelFailed();
            }
         }
      }

   }

   public synchronized void notifyCancelFailure(long deploymentId, String deploymentType, Throwable reason) {
      if (this.isDebugEnabled()) {
         this.debug("'cancel failure' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' due to '" + reason.toString() + "'");
      }

      TargetRequestImpl request = this.requestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'cancel failure' from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "' does not have a corresponding request - ignoring notification");
         }
      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.cancelFailureFrom(deploymentType, reason);
         if (status.receivedAllCancelResponses()) {
            status.getCurrentState().cancelFailed();
         }
      }

   }

   public void notifyStatusUpdate(long deploymentId, String deploymentType, Serializable statusObject) {
      if (Debug.isServiceStatusDebugEnabled()) {
         Debug.serviceStatusDebug("'status update' received from DeploymentReceiver for '" + deploymentType + "'  for id '" + deploymentId + "'");
      }

      this.relayStatus(deploymentId, deploymentType, statusObject);
   }

   public void relayStatus(String channelId, Serializable statusObject) {
      if (Debug.isServiceStatusDebugEnabled()) {
         Debug.serviceStatusDebug("'relaying status ' '" + statusObject + "' on '" + channelId + "'");
      }

      CommonMessageSender messageSender = CommonMessageSender.getInstance();
      messageSender.sendStatusMsg(channelId, statusObject);
   }

   public void relayStatus(long sessionId, String channelId, Serializable statusObject) {
      if (Debug.isServiceStatusDebugEnabled()) {
         Debug.serviceStatusDebug("'relaying status ' '" + statusObject + "' with id '" + sessionId + "' on '" + channelId + "'");
      }

      CommonMessageSender messageSender = CommonMessageSender.getInstance();
      messageSender.sendStatusMsg(sessionId, channelId, statusObject);
   }

   private void resetRegistration(TargetRequestStatus deploymentStatus) {
      deploymentStatus.reset();
      this.requestManager.scheduleNextRequest();
   }

   private final class RegistrationResponse {
      boolean responseReceived;
      DeploymentContext context;
      Throwable error;

      RegistrationResponse() {
      }

      synchronized void waitForResponse() {
         if (!this.responseReceived) {
            try {
               this.wait();
            } catch (InterruptedException var2) {
               if (Debug.isServiceStatusDebugEnabled()) {
                  Debug.serviceDebug("DeploymentService: registerHandler: Interrupted while waiting for response");
               }
            }

         }
      }

      DeploymentContext getContext() {
         return this.context;
      }

      synchronized void setResponseReceived(DeploymentContext inContext) {
         this.context = inContext;
         this.responseReceived = true;
         this.notify();
      }

      synchronized void setErrorEncountered(Throwable t) {
         this.error = t;
         this.responseReceived = true;
         this.notify();
      }

      boolean errorEncountered() {
         return this.error != null;
      }

      Throwable getError() {
         return this.error;
      }
   }

   private static class Maker {
      private static final TargetDeploymentService SINGLETON = (TargetDeploymentService)LocatorUtilities.getService(TargetDeploymentService.class);
   }
}
