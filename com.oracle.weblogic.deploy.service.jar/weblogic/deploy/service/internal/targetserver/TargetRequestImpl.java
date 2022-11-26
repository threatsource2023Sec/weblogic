package weblogic.deploy.service.internal.targetserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.RequestImpl;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.utils.StackTraceUtils;

public final class TargetRequestImpl extends RequestImpl {
   private static final TargetRequestManager requestManager = TargetRequestManager.getInstance();
   private static final CommonMessageSender messageSender = CommonMessageSender.getInstance();
   private TargetRequestStatus deploymentStatus;
   private DomainVersion domainVersion;
   private DomainVersion proposedDomainVersion;
   private DomainVersion preparingFromVersion;
   private DeploymentContextImpl context;
   private Map deploymentsMap;
   private DomainVersion syncToAdminVersion;
   private ArrayList syncToAdminDeployments;
   private Map syncToAdminDeploymentsMap;
   private boolean syncAlreadyPerformed;
   private boolean heartbeatRequest;
   private boolean isAborted = false;

   public void setId(long id) {
      this.identifier = id;
      if (isDebugEnabled()) {
         debug("setting id for request to '" + this.identifier + "' on target");
      }

   }

   public final void setDeployments(List deployments) {
      this.deployments = deployments;
   }

   public final Iterator getDeployments() {
      return this.syncToAdminVersion != null ? ((List)this.syncToAdminDeployments.clone()).iterator() : super.getDeployments();
   }

   public final void setDeploymentsMap(Map deploymentsMap) {
      this.deploymentsMap = deploymentsMap;
   }

   public final Map getDeploymentsMap() {
      return this.syncToAdminVersion != null ? this.syncToAdminDeploymentsMap : this.deploymentsMap;
   }

   public final Iterator getDeployments(String callbackIdentity) {
      if (this.syncToAdminVersion != null) {
         ArrayList result = new ArrayList();
         Iterator iterator = this.syncToAdminDeployments.iterator();

         while(iterator.hasNext()) {
            Deployment deployment = (Deployment)iterator.next();
            if (callbackIdentity.equals(deployment.getCallbackHandlerId())) {
               result.add(deployment);
            }
         }

         return result.iterator();
      } else {
         return super.getDeployments(callbackIdentity);
      }
   }

   public final void setHeartbeatRequest() {
      this.heartbeatRequest = true;
   }

   public final boolean isHeartbeatRequest() {
      return this.heartbeatRequest;
   }

   public final void setDomainVersion(DomainVersion version) {
      this.domainVersion = version;
   }

   public final DomainVersion getDomainVersion() {
      return this.domainVersion;
   }

   public final void setProposedDomainVersion(DomainVersion version) {
      this.proposedDomainVersion = version;
   }

   public final DomainVersion getProposedDomainVersion() {
      return this.proposedDomainVersion;
   }

   public final void setPreparingFromVersion(DomainVersion version) {
      this.preparingFromVersion = version;
   }

   public final DomainVersion getPreparingFromVersion() {
      return this.preparingFromVersion;
   }

   public final DomainVersion getSyncToAdminVersion() {
      return this.syncToAdminVersion;
   }

   public final void setSyncToAdminMessage(DeploymentServiceMessage message) {
      this.syncToAdminVersion = message.getToVersion();
      this.syncToAdminDeployments = message.getItems();
      this.setSyncAlreadyPerformed(true);
   }

   public final List getSyncToAdminDeployments() {
      return this.syncToAdminDeployments;
   }

   public final void setSyncToAdminDeploymentsMap(Map deploymentsMap) {
      this.syncToAdminDeploymentsMap = deploymentsMap;
   }

   public final Map getSyncToAdminDeploymentsMap() {
      return this.syncToAdminDeploymentsMap;
   }

   public final void resetSyncToAdminDeployments() {
      this.syncToAdminVersion = null;
      this.syncToAdminDeployments = null;
      this.syncToAdminDeploymentsMap = null;
   }

   public final void setDeploymentStatus(TargetRequestStatus targetStatus) {
      this.deploymentStatus = targetStatus;
   }

   public final TargetRequestStatus getDeploymentStatus() {
      return this.deploymentStatus;
   }

   public final CommonMessageSender getMessageSender() {
      return messageSender;
   }

   public final void setDeploymentContext(DeploymentContextImpl context) {
      this.context = context;
   }

   public final DeploymentContextImpl getDeploymentContext() {
      return this.context;
   }

   public void run() {
      requestManager.addToRequestTable(this);
      if (isDebugEnabled()) {
         debug("DeploymentService call: Starting target side deploy for '" + this.getId() + "'");
      }

      this.deploymentStatus = TargetRequestStatus.createTargetRequestStatus(this);
      this.startTimeoutMonitor("TargetRequest for id '" + this.identifier + "'");

      try {
         this.deploymentStatus.getCurrentState().receivedPrepare();
      } catch (Throwable var4) {
         Throwable t = var4;
         if (isDebugEnabled()) {
            Debug.serviceDebug(StackTraceUtils.throwable2StackTrace(var4));
         }

         try {
            messageSender.sendPrepareNakMsg(this.getId(), t);
         } catch (RemoteException var3) {
            this.abort();
         }

         if (!this.isAborted()) {
            this.deploymentStatus.reset();
         }
      }

   }

   public boolean equals(Object inObj) {
      if (this == inObj) {
         return true;
      } else {
         return inObj instanceof TargetRequestImpl ? super.equals(inObj) : false;
      }
   }

   public int hashCode() {
      return super.hashCode();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TargetDeploymentRequest id '" + this.getId() + "'");
      return sb.toString();
   }

   public void requestTimedout() {
      if (this.deploymentStatus != null) {
         if (isDebugEnabled()) {
            debug(this.identifier + " timed out on target server");
         }

         this.deploymentStatus.setTimedOut();
         this.abort();
      }
   }

   public void abort() {
      this.isAborted = true;
      this.deploymentStatus.getCurrentState().abort();
   }

   public boolean isAborted() {
      return this.isAborted;
   }

   public boolean isSyncAlreadyPerformed() {
      return this.syncAlreadyPerformed;
   }

   private void setSyncAlreadyPerformed(boolean syncAlreadyPerformed) {
      this.syncAlreadyPerformed = syncAlreadyPerformed;
   }
}
