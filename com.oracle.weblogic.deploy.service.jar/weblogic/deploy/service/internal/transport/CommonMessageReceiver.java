package weblogic.deploy.service.internal.transport;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationRuntimeStateManager;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.internal.CalloutManager;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.ServiceRequest;
import weblogic.deploy.service.internal.adminserver.AdminCalloutManager;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentsManager;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;
import weblogic.deploy.service.internal.adminserver.StatusDeliverer;
import weblogic.deploy.service.internal.targetserver.TargetCalloutManager;
import weblogic.deploy.service.internal.targetserver.TargetDeploymentsManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestImpl;
import weblogic.deploy.service.internal.targetserver.TargetRequestManager;
import weblogic.deploy.service.internal.targetserver.TargetRequestStatus;
import weblogic.deploy.service.internal.transport.http.HTTPMessageReceiver;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

@Service
public final class CommonMessageReceiver implements AdminServerMessageReceiver, TargetServerMessageReceiver, MessageDispatcher {
   private static final byte deploymentServiceVersion = 2;
   private MessageReceiver delegate = HTTPMessageReceiver.getMessageReceiver();
   private final AdminRequestManager adminRequestManager;
   private final AdminDeploymentsManager adminDeploymentsManager;
   private final CalloutManager calloutManager;
   public static boolean CalloutsListenerAttached = false;
   @Inject
   private TargetRequestManager targetRequestManager;
   private final TargetDeploymentsManager targetDeploymentsManager;
   private final StatusDeliverer statusDeliverer;
   private CommonMessageSender messageSender;
   private boolean heartbeatServiceInitialized = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ApplicationRuntimeStateManager appRuntimeStateManager = (ApplicationRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(ApplicationRuntimeStateManager.class, new Annotation[0]);

   private CommonMessageReceiver() {
      if (ManagementService.getPropertyService(kernelId).isAdminServer()) {
         this.adminRequestManager = (AdminRequestManager)LocatorUtilities.getService(AdminRequestManager.class);
         this.adminDeploymentsManager = (AdminDeploymentsManager)LocatorUtilities.getService(AdminDeploymentsManager.class);
         this.statusDeliverer = StatusDeliverer.getInstance();
         this.calloutManager = (CalloutManager)LocatorUtilities.getService(AdminCalloutManager.class);
      } else {
         this.adminRequestManager = null;
         this.adminDeploymentsManager = null;
         this.statusDeliverer = null;
         this.calloutManager = (CalloutManager)LocatorUtilities.getService(TargetCalloutManager.class);
      }

      this.targetDeploymentsManager = TargetDeploymentsManager.getInstance();
      this.delegate.setDispatcher(this);
   }

   public void setDelegate(MessageReceiver delegate) {
      this.delegate = delegate;
   }

   /** @deprecated */
   @Deprecated
   public static CommonMessageReceiver getInstance() {
      return CommonMessageReceiver.Maker.SINGLETON;
   }

   private synchronized CommonMessageSender getMessageSender() {
      if (this.messageSender == null) {
         this.messageSender = CommonMessageSender.getInstance();
      }

      return this.messageSender;
   }

   private final void debug(String message) {
      Debug.serviceTransportDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isServiceTransportDebugEnabled();
   }

   private final void handlePendingCancel(long requestId) {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Handling pending cancel for request with id '" + requestId + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      this.targetRequestManager.removePendingCancelFor(requestId);
      this.getMessageSender().sendCancelSucceededMsg(requestId);
   }

   public void setHeartbeatServiceInitialized() {
      this.heartbeatServiceInitialized = true;
   }

   public final void receivePrepareAckMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      boolean requiresRestart = (Boolean)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'prepare succeeded' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedPrepareSucceeded(deploymentId, messageSrc, requiresRestart);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'prepare succeeded' for id '" + deploymentId + "' from '" + messageSrc + "' that has no corresponding request";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receivePrepareNakMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      Throwable e = (Throwable)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'prepare failed' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "' with reason '" + (e != null ? e.getMessage() : "Unknown") + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedPrepareFailed(messageSrc, e);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'prepare failed' for id " + deploymentId + "' from '" + messageSrc + "' that has no corresponding request - it may have been cancelled";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receiveConfigDeploymentPrepareAckMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      boolean requiresRestart = (Boolean)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'config prepare succeeded' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedConfigPrepareSucceeded(deploymentId, messageSrc, requiresRestart);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'config prepare succeeded' for id '" + deploymentId + "' from '" + messageSrc + "' that has no corresponding request";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         }

         Debug.deploymentDebugConcise(debugMsg);
      }

   }

   public final void receiveConfigDeploymentPrepareNakMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      Throwable e = (Throwable)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'config prepare failed' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "' with reason '" + e.getMessage() + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            }

            Debug.deploymentDebugConcise(debugMsg);
         }

         request.receivedConfigPrepareFailed(messageSrc, e);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'config prepare failed' for id " + deploymentId + "' from '" + messageSrc + "' that has no corresponding request - it may have been cancelled";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receiveCommitSucceededMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'commit succeeded' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedCommitSucceeded(messageSrc);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'commit success' from '" + messageSrc + "' for id '" + deploymentId + "' has no corresponding request - it may have been cancelled";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receiveCommitFailedMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      Throwable e = (Throwable)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'commit failed' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "' with reason '" + (e != null ? e.getMessage() : "Unknown") + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedCommitFailed(messageSrc, e);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'commit failed' from '" + messageSrc + "' for id '" + deploymentId + "' has no corresponding request - it may have been cancelled";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receiveCancelSucceededMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      if (this.isDebugEnabled()) {
         this.debug("received 'cancel succeeded' from '" + messageSrc + "' for id '" + deploymentId + "'");
      }

      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      if (request != null) {
         request.receivedCancelSucceeded(messageSrc);
      } else if (this.isDebugEnabled()) {
         this.debug("'cancel succeeded' from '" + messageSrc + "' for id '" + deploymentId + "' has no corresponding request - it may have been cancelled ");
      }

   }

   public final void receiveCancelFailedMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      String messageSrc = message.getMessageSrc();
      Throwable e = (Throwable)message.getItems().get(0);
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      AdminRequestImpl request = this.adminRequestManager.getRequest(deploymentId);
      String debugMsg;
      if (request != null) {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            debugMsg = "Received 'cancel failed' from '" + messageSrc + "' for id '" + request.getExtendedIdName() + "' with reason '" + (e != null ? e.getMessage() : "Unknown") + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         request.receivedCancelFailed(messageSrc, e);
      } else if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         debugMsg = "Received 'cancel failed' from '" + messageSrc + "' for id '" + deploymentId + "' has no corresponding request - it may be already complete or have been cancelled";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

   }

   public final void receiveGetDeploymentsMsg(DeploymentServiceMessage message) {
      if (this.isDebugEnabled()) {
         this.debug("received 'get deployments' from '" + message.getMessageSrc() + "' to sync from version '" + message.getFromVersion() + "' for id '" + message.getDeploymentId() + "'");
      }

      String messageSrc = message.getMessageSrc();
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      String serverName = message.getMessageSrc();
      this.adminRequestManager.getDeployments(message.getFromVersion(), serverName, message.getDeploymentId(), false, (String)null, this.getMessageSender().getHandlers(serverName), (String)null);
   }

   public final DeploymentServiceMessage receiveBlockingGetDeploymentsMsg(DeploymentServiceMessage message) {
      if (this.isDebugEnabled()) {
         this.debug("received 'blocking get deployments' from '" + message.getMessageSrc() + "'");
      }

      String serverName = message.getMessageSrc();
      if (this.adminRequestManager == null) {
         this.rejectAdminServerOperation(serverName);
      }

      DomainVersion fromV = message.getFromVersion();
      Set handlers = fromV.getDeploymentsVersionMap().keySet();
      this.getMessageSender().putHandlers(serverName, handlers);
      ArrayList deployments = this.adminRequestManager.getDeployments(fromV, serverName, message.getDeploymentId(), true, message.getDeploymentType(), handlers, message.getPartitionName());
      DeploymentServiceMessage responseMessage = new DeploymentServiceMessage((byte)2, (byte)5, message.getDeploymentId(), deployments);
      responseMessage.setFromVersion(message.getFromVersion());
      responseMessage.setToVersion(this.adminDeploymentsManager.getCurrentDomainVersion().getFilteredVersion(handlers));
      if (this.isDebugEnabled()) {
         this.debug("'get deployments response' being returned to '" + message.getMessageSrc() + "' message --> " + responseMessage);
      }

      return responseMessage;
   }

   public final void receiveStatusMsg(DeploymentServiceMessage message) {
      ArrayList items = message.getItems();
      String channelId = (String)items.get(0);
      Serializable statusObject = (Serializable)items.get(1);
      boolean sessionIdPresent = false;
      long sessionId = 0L;
      if (items.size() > 2) {
         sessionIdPresent = true;
         sessionId = (Long)items.get(2);
      }

      String messageSrc;
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         messageSrc = "Received 'status update' from '" + message.getMessageSrc() + "' on channel id '" + channelId + "'";
         if (this.isDebugEnabled()) {
            this.debug(messageSrc);
         } else {
            Debug.deploymentDebugConcise(messageSrc);
         }
      }

      messageSrc = message.getMessageSrc();
      if (this.statusDeliverer == null) {
         this.rejectAdminServerOperation(messageSrc);
      }

      if (sessionIdPresent) {
         this.statusDeliverer.deliverStatus(sessionId, channelId, statusObject, message.getMessageSrc());
      } else {
         this.statusDeliverer.deliverStatus(channelId, statusObject, message.getMessageSrc());
      }

   }

   private final String getExtendedIdName(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      ArrayList items = message.getItems();
      String appId = null;

      for(int i = 0; i < items.size(); ++i) {
         Object item = items.get(i);
         if (item instanceof Deployment) {
            Deployment deployment = (Deployment)item;
            if (deployment.getCallbackHandlerId().equals("Application")) {
               appId = deployment.getIdentity();
               break;
            }
         }
      }

      return deploymentId + (appId != null ? " [" + appId + "]" : "");
   }

   public final void receiveRequestPrepareMsg(DeploymentServiceMessage messageCopy) {
      long deploymentId = messageCopy.getDeploymentId();
      String deploymentRequest;
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         deploymentRequest = "Received 'prepare' from '" + messageCopy.getMessageSrc() + "' with id '" + this.getExtendedIdName(messageCopy) + "'";
         if (this.isDebugEnabled()) {
            this.debug(deploymentRequest);
         } else {
            Debug.deploymentDebugConcise(deploymentRequest);
         }
      }

      if (this.targetRequestManager.hasAPendingCancelFor(deploymentId)) {
         this.handlePendingCancel(deploymentId);
      } else {
         deploymentRequest = null;

         try {
            final TargetRequestImpl deploymentRequest = createTargetRequestImpl(messageCopy);
            deploymentRequest.setControlRequest(!messageCopy.needsVersionUpdate());
            deploymentRequest.setDomainVersion(messageCopy.getFromVersion());
            this.targetRequestManager.addRequest(new ServiceRequest() {
               public void run() {
                  deploymentRequest.run();
               }

               public String toString() {
                  return deploymentRequest.toString();
               }
            });
         } catch (Throwable var8) {
            Throwable t = var8;
            if (this.isDebugEnabled()) {
               Debug.serviceDebug(var8.getMessage() + " " + StackTraceUtils.throwable2StackTrace(var8));
            }

            try {
               this.getMessageSender().sendPrepareNakMsg(messageCopy.getDeploymentId(), t);
            } catch (RemoteException var7) {
               deploymentRequest.abort();
            }

            if (deploymentRequest != null && deploymentRequest.getDeploymentStatus() != null && !deploymentRequest.isAborted()) {
               deploymentRequest.getDeploymentStatus().reset();
            }
         }

      }
   }

   public final void receiveRequestCommitMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      if (this.isDebugEnabled()) {
         this.debug("received 'commit' with id '" + deploymentId + "'");
      }

      TargetRequestImpl request = this.targetRequestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'commit' with id '" + deploymentId + "' does not have a corresponding request - ignoring message");
         }

      } else {
         TargetRequestStatus status = null;

         try {
            status = request.getDeploymentStatus();
            status.getCurrentState().receivedCommit();
         } catch (Throwable var12) {
            Throwable t = var12;

            try {
               this.getMessageSender().sendCommitFailedMsg(deploymentId, t);
            } catch (RemoteException var11) {
               String msg = DeploymentServiceLogger.sendCommitFailMsgFailed(deploymentId);

               try {
                  this.getMessageSender().sendCommitFailedMsg(deploymentId, new Exception(msg));
               } catch (RemoteException var10) {
               }
            }

            if (status != null) {
               status.reset();
            }

            if (this.isDebugEnabled()) {
               this.debug(var12.getMessage() + " " + StackTraceUtils.throwable2StackTrace(var12));
            }
         }

      }
   }

   public final void receiveRequestCancelMsg(DeploymentServiceMessage message) {
      long deploymentId = message.getDeploymentId();
      Throwable e = (Throwable)message.getItems().get(0);
      if (this.isDebugEnabled()) {
         this.debug("Received 'cancel' for id '" + deploymentId + "' due to '" + e + "'");
      }

      if (this.targetRequestManager.hasAPendingCancelFor(deploymentId)) {
         if (this.isDebugEnabled()) {
            this.debug("request with id '" + deploymentId + "' is a pending cancel so send back success message");
         }

         this.handlePendingCancel(deploymentId);
      } else {
         TargetRequestImpl request = this.targetRequestManager.getRequest(deploymentId);
         if (request == null) {
            if (this.isDebugEnabled()) {
               this.debug("'cancel' with id '" + deploymentId + "' does not have a corresponding request - saving to 'pending cancel' list");
            }

            this.targetRequestManager.addToPendingCancels(deploymentId);
         } else {
            TargetRequestStatus status = null;

            try {
               status = request.getDeploymentStatus();
               if (status != null) {
                  status.setCanceled();
                  if (status.isAborted()) {
                     if (this.isDebugEnabled()) {
                        this.debug("Request '" + deploymentId + "' aborted so cancel already performed. Just send back success");
                     }

                     this.getMessageSender().sendCancelSucceededMsg(deploymentId);
                  } else {
                     status.getCurrentState().receivedCancel();
                  }
               }
            } catch (Throwable var8) {
               this.getMessageSender().sendCancelFailedMsg(deploymentId, var8);
               if (status != null) {
                  status.reset();
               }

               if (this.isDebugEnabled()) {
                  this.debug(var8.getMessage() + " " + StackTraceUtils.throwable2StackTrace(var8));
               }
            }

         }
      }
   }

   public final void receiveHeartbeatMsg(final DeploymentServiceMessage message) {
      if (!this.heartbeatServiceInitialized) {
         if (this.isDebugEnabled()) {
            this.debug("ignoring 'heartbeat' - heartbeat service not initialized");
         }

      } else if (this.targetRequestManager.handlingRequests()) {
         if (this.isDebugEnabled()) {
            this.debug("ignoring 'heartbeat' - requests still in progress");
         }

      } else {
         this.targetRequestManager.addRequest(new ServiceRequest() {
            public void run() {
               try {
                  CommonMessageReceiver.this.handleHeartbeatMessage(message);
               } catch (Throwable var2) {
               }

            }

            public String toString() {
               return "Heartbeat request";
            }
         });
      }
   }

   private boolean serverInAdminState() {
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      return serverRuntime.getState() == "ADMIN";
   }

   private void handleHeartbeatMessage(DeploymentServiceMessage message) {
      boolean abortForCallout = this.calloutManager.abortForExpectedChangeCallout("poll");
      if (!this.targetRequestManager.handlingRequests() && !this.serverInAdminState() && !abortForCallout) {
         DomainVersion inVersion = message.getFromVersion();
         DomainVersion domainVersion = this.targetDeploymentsManager.getCurrentDomainVersion();
         if (domainVersion.equals(inVersion)) {
            if (this.isDebugEnabled()) {
               this.debug("domain version in 'heartbeat' '" + inVersion + "' is equal to current domain version");
            }

         } else {
            if (this.isDebugEnabled()) {
               this.debug("domain version in 'heartbeat' '" + inVersion + "' not equal to current domain version - needs to sync with admin server");
            }

            TargetRequestImpl deploymentRequest = new TargetRequestImpl();
            deploymentRequest.setId();
            deploymentRequest.setHeartbeatRequest();
            synchronized(this.targetRequestManager) {
               if (this.targetRequestManager.handlingRequests() || this.serverInAdminState()) {
                  if (this.isDebugEnabled()) {
                     this.debug(" 1 skipping 'heartbeat' handling");
                  }

                  return;
               }

               this.targetRequestManager.addToRequestTable(deploymentRequest);
            }

            TargetRequestStatus targetStatus = TargetRequestStatus.createTargetRequestStatus(deploymentRequest);
            targetStatus.setCurrentState(targetStatus.getTargetServerState(5));
            deploymentRequest.setDeploymentStatus(targetStatus);
            this.getMessageSender().sendGetDeploymentsMsg(domainVersion, deploymentRequest.getId());
         }
      } else {
         if (this.isDebugEnabled()) {
            this.debug("skipping 'heartbeat' handling :  abortForCallout=" + abortForCallout + "serverInAdminState = " + this.serverInAdminState());
         }

      }
   }

   public final void receiveGetDeploymentsResponse(DeploymentServiceMessage message) {
      if (this.isDebugEnabled()) {
         this.debug("received 'get deployments response' from '" + message.getMessageSrc());
      }

      long deploymentId = message.getDeploymentId();
      TargetRequestImpl request = this.targetRequestManager.getRequest(deploymentId);
      if (request == null) {
         if (this.isDebugEnabled()) {
            this.debug("'get deployments response' does not have  corresponding request - ignoring message");
         }

      } else {
         TargetRequestStatus status = request.getDeploymentStatus();
         status.getCurrentState().receivedGetDeploymentsResponse(message);
      }
   }

   private void receiveRequestAppStateMsg(DeploymentServiceMessage message) {
      long requestId = message.getDeploymentId();
      String appName = (String)message.getItems().get(0);
      String appState = appRuntimeStateManager.getCurrentStateFromLocalData(appName, this.getMessageSender().getLocalServerName());
      if (this.isDebugEnabled()) {
         this.debug("Received 'request app state' for id '" + requestId + "' for application '" + appName + "' with app state of '" + appState + "'");
      }

      this.getMessageSender().sendTransmitAppStateMsg(requestId, appName, appState);
   }

   private void receiveTransmitAppStateMsg(DeploymentServiceMessage message) {
      long requestId = message.getDeploymentId();
      String appName = (String)message.getItems().get(0);
      String serverName = (String)message.getItems().get(1);
      String appState = (String)message.getItems().get(2);
      if (this.isDebugEnabled()) {
         this.debug("Received 'transmit app state' for id '" + requestId + "' to server '" + serverName + "' for application '" + appName + "' with app state of '" + appState + "'");
      }

      appRuntimeStateManager.updateApplicationChecker(requestId, serverName, appState);
   }

   private void receiveRequestMultiVersionStateMsg(DeploymentServiceMessage message) {
      long requestId = message.getDeploymentId();
      List appNames = (List)message.getItems().get(0);
      List libNames = (List)message.getItems().get(1);
      Map multiVersionState = appRuntimeStateManager.getCurrentStateOfAllVersionsFromLocalData(appNames, libNames, this.getMessageSender().getLocalServerName());
      if (this.isDebugEnabled()) {
         this.debug("Received 'request multi version state' for id '" + requestId + "' for application '" + appNames + "' with app state of '" + multiVersionState + "'");
      }

      this.getMessageSender().sendTransmitMultiVersionStateMsg(requestId, multiVersionState);
   }

   private void receiveTransmitMultiVersionStateMsg(DeploymentServiceMessage message) {
      long requestId = message.getDeploymentId();
      String serverName = (String)message.getItems().get(0);
      Map multiVersionState = (Map)message.getItems().get(1);
      if (this.isDebugEnabled()) {
         this.debug("Received 'transmit multi version state' for id '" + requestId + "' to server '" + serverName + "' with app state of '" + multiVersionState + "'");
      }

      appRuntimeStateManager.updateApplicationChecker(requestId, serverName, multiVersionState);
   }

   public final void dispatch(DeploymentServiceMessage message) {
      byte messageType = message.getMessageType();
      switch (messageType) {
         case 0:
            this.receiveHeartbeatMsg(message);
            break;
         default:
            if (Debug.isServiceDebugEnabled()) {
               Debug.serviceDebug("received illegal message '" + message.toString() + "'");
            }
      }

   }

   public final DeploymentServiceMessage blockingDispatch(final DeploymentServiceMessage messageCopy) {
      final byte messageType = messageCopy.getMessageType();
      if (messageType != 13 && messageType != 20) {
         if (messageType == 12) {
            this.receiveStatusMsg(messageCopy);
            return null;
         } else {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  switch (messageType) {
                     case 0:
                        CommonMessageReceiver.this.receiveHeartbeatMsg(messageCopy);
                        break;
                     case 1:
                        CommonMessageReceiver.this.receiveRequestPrepareMsg(messageCopy);
                        break;
                     case 2:
                        CommonMessageReceiver.this.receiveRequestCommitMsg(messageCopy);
                        break;
                     case 3:
                        CommonMessageReceiver.this.receiveRequestCancelMsg(messageCopy);
                        break;
                     case 4:
                        CommonMessageReceiver.this.receiveGetDeploymentsMsg(messageCopy);
                        break;
                     case 5:
                        CommonMessageReceiver.this.receiveGetDeploymentsResponse(messageCopy);
                        break;
                     case 6:
                        CommonMessageReceiver.this.receivePrepareAckMsg(messageCopy);
                        break;
                     case 7:
                        CommonMessageReceiver.this.receivePrepareNakMsg(messageCopy);
                        break;
                     case 8:
                        CommonMessageReceiver.this.receiveCommitSucceededMsg(messageCopy);
                        break;
                     case 9:
                        CommonMessageReceiver.this.receiveCommitFailedMsg(messageCopy);
                        break;
                     case 10:
                        CommonMessageReceiver.this.receiveCancelSucceededMsg(messageCopy);
                        break;
                     case 11:
                        CommonMessageReceiver.this.receiveCancelFailedMsg(messageCopy);
                        break;
                     case 12:
                     case 13:
                     default:
                        if (Debug.isServiceDebugEnabled()) {
                           Debug.serviceDebug("blocking dispatch received illegal message '" + messageCopy.toString() + "'");
                        }
                        break;
                     case 14:
                        CommonMessageReceiver.this.receiveConfigDeploymentPrepareAckMsg(messageCopy);
                        break;
                     case 15:
                        CommonMessageReceiver.this.receiveConfigDeploymentPrepareNakMsg(messageCopy);
                        break;
                     case 16:
                        CommonMessageReceiver.this.receiveRequestAppStateMsg(messageCopy);
                        break;
                     case 17:
                        CommonMessageReceiver.this.receiveTransmitAppStateMsg(messageCopy);
                        break;
                     case 18:
                        CommonMessageReceiver.this.receiveRequestMultiVersionStateMsg(messageCopy);
                        break;
                     case 19:
                        CommonMessageReceiver.this.receiveTransmitMultiVersionStateMsg(messageCopy);
                  }

               }
            });
            return null;
         }
      } else {
         return this.receiveBlockingGetDeploymentsMsg(messageCopy);
      }
   }

   public final MessageReceiver getDelegate() {
      return this.delegate;
   }

   private static TargetRequestImpl createTargetRequestImpl(DeploymentServiceMessage message) {
      TargetRequestImpl request = new TargetRequestImpl();
      List deployments = message.getItems();
      long deploymentId = message.getDeploymentId();
      request.setDeployments(deployments);
      request.setId(deploymentId);
      request.setTimeoutInterval(message.getTimeoutInterval());
      if (message.isConfigurationProviderCalledLast()) {
         request.setCallConfigurationProviderLast();
      }

      AuthenticatedSubject initiator = message.getInitiator();
      if (initiator != null) {
         request.setInitiator(initiator);
      }

      return request;
   }

   protected void rejectAdminServerOperation(String source) {
      throw new UnsupportedOperationException(DeploymentServiceLogger.logExceptionInServletRequestIntendedForAdminServerLoggable(source).getMessage());
   }

   private static final class Maker {
      private static final CommonMessageReceiver SINGLETON = (CommonMessageReceiver)LocatorUtilities.getService(CommonMessageReceiver.class);
   }
}
