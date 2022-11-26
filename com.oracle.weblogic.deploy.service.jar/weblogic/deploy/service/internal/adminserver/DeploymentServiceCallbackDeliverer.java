package weblogic.deploy.service.internal.adminserver;

import java.io.Serializable;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentException;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.DeploymentServiceCallbackHandlerV2;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.StatusListener;
import weblogic.deploy.service.Version;

final class DeploymentServiceCallbackDeliverer implements DeploymentServiceCallbackHandlerV2, StatusListener {
   private final DeploymentServiceCallbackHandler delegate;

   DeploymentServiceCallbackDeliverer(DeploymentServiceCallbackHandler delegate) {
      this.delegate = delegate;
      StatusDeliverer.getInstance().registerStatusListener(delegate.getHandlerIdentity(), this);
   }

   private final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public final String getHandlerIdentity() {
      return this.delegate.getHandlerIdentity();
   }

   public final Deployment[] getDeployments(Version fromVersion, Version toVersion, String serverName, String partitionName) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'getDeployments' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  to sync from '" + fromVersion + "' to '" + toVersion + "' for server '" + serverName + "' for partition '" + partitionName + "'");
      }

      return this.delegate.getDeployments(fromVersion, toVersion, serverName, partitionName);
   }

   public final void deploySucceeded(long deploymentIdentifier, FailureDescription[] deferredFailures) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'deploySucceeded' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "'");
      }

      this.delegate.deploySucceeded(deploymentIdentifier, deferredFailures);
   }

   public final void deployFailed(long deploymentIdentifier, DeploymentException reason) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'deployFailed' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "' due to '" + reason + "'");
      }

      this.delegate.deployFailed(deploymentIdentifier, reason);
   }

   public final void appPrepareFailed(long deploymentIdentifier, DeploymentException reason) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'appPrepareFailed' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "' due to '" + reason + "'");
      }

      this.delegate.appPrepareFailed(deploymentIdentifier, reason);
   }

   public void commitFailed(long deploymentIdentifier, FailureDescription[] failureDescriptions) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'commitDeliverFailed' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "'");
      }

      this.delegate.commitFailed(deploymentIdentifier, failureDescriptions);
   }

   public void commitSucceeded(long deploymentIdentifier) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'commitSucceeded' on DeploymentServiceCallbackHandlerfor '" + this.getHandlerIdentity() + "' for id '" + deploymentIdentifier + "'");
      }

      this.delegate.commitSucceeded(deploymentIdentifier);
   }

   public final void cancelSucceeded(long deploymentIdentifier, FailureDescription[] failureDescriptions) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'cancelSucceeded' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "'");
      }

      this.delegate.cancelSucceeded(deploymentIdentifier, failureDescriptions);
   }

   public final void cancelFailed(long deploymentIdentifier, DeploymentException reason) {
      if (this.isDebugEnabled()) {
         this.debug("Calling 'cancelFailed' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + deploymentIdentifier + "' due to '" + reason + "'");
      }

      this.delegate.cancelFailed(deploymentIdentifier, reason);
   }

   public final void statusReceived(Serializable statusObject, String serverName) {
   }

   public final void statusReceived(long sessionId, Serializable statusObject, String serverName) {
      if (Debug.serviceStatusLogger.isDebugEnabled()) {
         Debug.serviceStatusLogger.debug("Calling 'received status' on DeploymentServiceCallbackHandler for '" + this.getHandlerIdentity() + "'  for id '" + sessionId + "' from server '" + serverName + "'");
      }

      this.delegate.receivedStatusFrom(sessionId, statusObject, serverName);
   }

   public final void receivedStatusFrom(long deploymentIdentifier, Serializable statusObject, String serverName) {
   }

   public final void requestStatusUpdated(long deploymentIdentifier, String action, String serverName) {
      if (this.delegate instanceof DeploymentServiceCallbackHandlerV2) {
         DeploymentServiceCallbackHandlerV2 v2 = (DeploymentServiceCallbackHandlerV2)this.delegate;
         v2.requestStatusUpdated(deploymentIdentifier, action, serverName);
      }

   }
}
