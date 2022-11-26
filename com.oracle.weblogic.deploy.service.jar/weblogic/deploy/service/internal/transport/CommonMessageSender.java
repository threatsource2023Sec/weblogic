package weblogic.deploy.service.internal.transport;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationRuntimeStateManager;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentsManager;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;
import weblogic.deploy.service.internal.adminserver.AdminRequestManager;
import weblogic.deploy.service.internal.adminserver.AdminRequestStatus;
import weblogic.deploy.service.internal.transport.http.HTTPMessageSender;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

@Service
public final class CommonMessageSender implements AdminServerMessageSender, TargetServerMessageSender {
   private static final byte deploymentServiceVersion = 2;
   private MessageSender delegate;
   @Inject
   private AdminDeploymentsManager adminDeploymentsManager;
   @Inject
   private AdminRequestManager adminRequestManager;
   private final Map serverToHandlers = Collections.synchronizedMap(new HashMap());
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ApplicationRuntimeStateManager appRuntimeStateManager = (ApplicationRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(ApplicationRuntimeStateManager.class, new Annotation[0]);
   private String localServerName;

   private CommonMessageSender() {
      this.setDelegate(HTTPMessageSender.getMessageSender());
   }

   /** @deprecated */
   @Deprecated
   public static CommonMessageSender getInstance() {
      return CommonMessageSender.Maker.SINGLETON;
   }

   private static final void debug(String message) {
      Debug.serviceTransportDebug(message);
   }

   private static final boolean isDebugEnabled() {
      return Debug.isServiceTransportDebugEnabled();
   }

   private void setDelegate(MessageSender messageSender) {
      this.setDelegate(messageSender, false);
   }

   public void setDelegate(MessageSender messageSender, boolean force) {
      if (force) {
         this.delegate = messageSender;
      } else {
         if (this.delegate == null) {
            this.delegate = messageSender;
         }

      }
   }

   public final MessageSender getDelegate() {
      return this.delegate;
   }

   public String getLocalServerName() {
      if (this.localServerName == null) {
         this.localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      }

      return this.localServerName;
   }

   public void sendHeartbeatMsg(List servers) {
      if (servers != null && !servers.isEmpty()) {
         DeploymentServiceMessage deploymentServiceMsg = this.createHeartbeatMessage();
         DomainVersion dv = this.adminDeploymentsManager.getCurrentDomainVersion();
         Iterator iter = servers.iterator();

         while(iter.hasNext()) {
            String serverName = (String)iter.next();
            if (!this.getLocalServerName().equals(serverName)) {
               Set handlers = this.getHandlers(serverName);
               deploymentServiceMsg.setFromVersion(dv.getFilteredVersion(handlers));
               this.sendOutHeartbeatMsg(deploymentServiceMsg, serverName);
            }
         }

      }
   }

   private DeploymentServiceMessage createHeartbeatMessage() {
      ArrayList itemList = new ArrayList();
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)0, -1L, itemList);
      return deploymentServiceMsg;
   }

   public void sendTransmitAppStateMsg(long requestId, String appName, String appState) {
      DeploymentServiceMessage deploymentServiceMsg = this.createTransmitAppStateMsg(requestId, appName, this.getLocalServerName(), appState);

      try {
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'transmit app state' for id '" + requestId + "' for " + appName + " with app state of '" + appState + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (Exception var8) {
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'transmit app state' for id '" + requestId + "' failed due to " + var8.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   private DeploymentServiceMessage createTransmitAppStateMsg(long requestId, String appName, String serverName, String appState) {
      ArrayList itemList = new ArrayList();
      itemList.add(appName);
      itemList.add(serverName);
      itemList.add(appState);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)17, requestId, itemList);
      return deploymentServiceMsg;
   }

   public void sendTransmitMultiVersionStateMsg(long requestId, Map appVersions) {
      DeploymentServiceMessage deploymentServiceMsg = this.createTransmitMultiVersionStateMsg(requestId, this.getLocalServerName(), appVersions);

      try {
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'transmit active application versions' for id '" + requestId + " with app versions of '" + appVersions + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (Exception var7) {
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'transmit active application versions' for id '" + requestId + "' failed due to " + var7.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   private DeploymentServiceMessage createTransmitMultiVersionStateMsg(long requestId, String serverName, Map appVersions) {
      ArrayList itemList = new ArrayList();
      itemList.add(serverName);
      itemList.add(appVersions);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)19, requestId, itemList);
      return deploymentServiceMsg;
   }

   public void sendRequestAppStateMsg(long requestId, List servers, String appName) {
      if (servers != null && !servers.isEmpty()) {
         DeploymentServiceMessage deploymentServiceMsg = this.createRequestAppStateMsg(requestId, appName);
         Iterator var6 = servers.iterator();

         while(var6.hasNext()) {
            String serverName = (String)var6.next();

            try {
               if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Sending 'request app state' for id '" + requestId + "' for '" + appName + "' to '" + serverName + "'";
                  if (isDebugEnabled()) {
                     debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               this.delegate.sendMessageToTargetServer(deploymentServiceMsg, serverName);
            } catch (Exception var10) {
               if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Sending 'request app state' for id '" + requestId + "' failed due to " + var10.getMessage() + "'. Will update the app state for '" + appName + "' to app state '" + "STATE_NOT_RESPONDING" + "'";
                  if (isDebugEnabled()) {
                     debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               appRuntimeStateManager.updateApplicationCheckerWithUnresponsiveTarget(requestId, serverName);
            }
         }

      }
   }

   private DeploymentServiceMessage createRequestAppStateMsg(long requestId, String appName) {
      ArrayList itemList = new ArrayList();
      itemList.add(appName);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)16, requestId, itemList);
      return deploymentServiceMsg;
   }

   public void sendRequestMultiVersionStateMsg(long requestId, Map targetsWithApps) {
      if (targetsWithApps != null && !targetsWithApps.isEmpty()) {
         Iterator var4 = targetsWithApps.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry targetWithAppsEntry = (Map.Entry)var4.next();
            String serverName = (String)targetWithAppsEntry.getKey();
            Map configIdsPerDeploymentType = (Map)targetWithAppsEntry.getValue();
            List appNames = null;
            List libNames = null;
            Iterator var10 = configIdsPerDeploymentType.entrySet().iterator();

            while(var10.hasNext()) {
               Map.Entry configIdsEntry = (Map.Entry)var10.next();
               switch ((DeploymentObject)configIdsEntry.getKey()) {
                  case APPLICATION:
                     appNames = (List)configIdsEntry.getValue();
                     break;
                  case LIBRARY:
                     libNames = (List)configIdsEntry.getValue();
               }
            }

            DeploymentServiceMessage deploymentServiceMsg = this.createRequestMultiVersionStateMsg(requestId, appNames, libNames);

            try {
               if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Sending 'request multi version state' for id '" + requestId + "' for '" + appNames + "' to '" + serverName + "'";
                  if (isDebugEnabled()) {
                     debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               this.delegate.sendMessageToTargetServer(deploymentServiceMsg, serverName);
            } catch (Exception var13) {
               if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Sending 'request multi version state' for id '" + requestId + "' failed due to " + var13.getMessage() + "'. Will update the app state for '" + appNames + "' to app state '" + "STATE_NOT_RESPONDING" + "'";
                  if (isDebugEnabled()) {
                     debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               appRuntimeStateManager.updateApplicationCheckerWithUnresponsiveTarget(requestId, serverName);
            }
         }

      }
   }

   private DeploymentServiceMessage createRequestMultiVersionStateMsg(long requestId, List appNames, List libNames) {
      ArrayList itemList = new ArrayList();
      itemList.add(appNames);
      itemList.add(libNames);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)18, requestId, itemList);
      return deploymentServiceMsg;
   }

   public final void sendRequestPrepareMsg(AdminRequestImpl request) {
      if (isDebugEnabled()) {
         debug("start send 'prepare' for id '" + request.getId() + "'");
      }

      Iterator iterator = request.getTargetServers();
      if (iterator != null) {
         String server;
         DeploymentServiceMessage deploymentServiceMsg;
         for(DomainVersion dv = this.adminDeploymentsManager.getCurrentDomainVersion(); iterator.hasNext(); this.sendOutPrepareMsg(deploymentServiceMsg, server, request)) {
            server = (String)iterator.next();
            deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)1, request, server);
            DomainVersion fromVersion = dv;
            if (!this.getLocalServerName().equals(server)) {
               Set handlers = this.getHandlers(server);
               fromVersion = dv.getFilteredVersion(handlers);
            }

            deploymentServiceMsg.setFromVersion(fromVersion);
            if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
               String debugMsg = "Sending 'prepare' for id '" + request.getExtendedIdName() + "' to '" + server + "'";
               if (isDebugEnabled()) {
                  debug(debugMsg + " message -->" + deploymentServiceMsg);
               } else {
                  Debug.deploymentDebugConcise(debugMsg);
               }
            }
         }

      }
   }

   public final void sendRequestCommitMsg(AdminRequestImpl request) {
      if (isDebugEnabled()) {
         debug("start send 'commit' for id '" + request.getId() + "'");
      }

      AdminRequestStatus status = request.getStatus();
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)2, request.getId(), new ArrayList());

      String server;
      for(Iterator iterator = status.getTargetsToBeCommitted(); iterator.hasNext(); this.sendOutCommitMsg(deploymentServiceMsg, server, request)) {
         server = (String)iterator.next();
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'commit' for id '" + request.getExtendedIdName() + "' to '" + server + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   public final void sendRequestCancelMsg(AdminRequestImpl request, Throwable reason) {
      if (isDebugEnabled()) {
         debug("start send 'cancel' for id '" + request.getId() + "'");
      }

      AdminRequestStatus status = request.getStatus();
      ArrayList itemList = new ArrayList();
      itemList.add(reason);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)3, request.getId(), itemList);

      String server;
      for(Iterator iterator = status.getTargetsToBeCancelled(); iterator.hasNext(); this.sendOutCancelMsg(deploymentServiceMsg, server, request)) {
         server = (String)iterator.next();
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Sending 'cancel' for id '" + request.getExtendedIdName() + "' to '" + server + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   public final void sendGetDeploymentsResponse(ArrayList messageList, String targetServer, DomainVersion toVersion, long msgId) {
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)5, msgId, messageList);
      deploymentServiceMsg.setToVersion(toVersion);

      try {
         if (this.getLocalServerName().equals(targetServer)) {
            this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
         } else {
            Set handlers = this.getHandlers(targetServer);
            DomainVersion filteredVersion = toVersion.getFilteredVersion(handlers);
            deploymentServiceMsg.setToVersion(filteredVersion);
            this.delegate.sendMessageToTargetServer(deploymentServiceMsg, targetServer);
         }

         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Start send 'get deployments response' '" + messageList + "' to '" + targetServer + " to version '" + deploymentServiceMsg.getToVersion() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      } catch (Exception var9) {
         if (Debug.isServiceTransportDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'get deployments response' to '" + targetServer + "' failed due to '" + var9.getMessage() + "'";
            if (Debug.isServiceTransportDebugEnabled()) {
               Debug.serviceTransportDebug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   private final void sendOutPrepareMsg(final DeploymentServiceMessage deploymentServiceMsg, final String server, final AdminRequestImpl request) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            long requestId = request.getId();
            AdminRequestImpl req = CommonMessageSender.this.adminRequestManager.getRequest(requestId);

            try {
               if (CommonMessageSender.this.getLocalServerName().equals(server)) {
                  request.prepareDeliveredTo(server);
                  CommonMessageSender.this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
               } else {
                  if (req != null) {
                     CommonMessageSender.this.adminRequestManager.addPrepareDisconnectListener(server, req);
                  }

                  CommonMessageSender.this.delegate.sendMessageToTargetServer(deploymentServiceMsg, server);
                  request.prepareDeliveredTo(server);
               }
            } catch (Throwable var6) {
               if (CommonMessageSender.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Send 'prepare' of id '" + request.getExtendedIdName() + "' to '" + server + "' failed due to '" + (var6 instanceof UnreachableHostException ? var6 : StackTraceUtils.throwable2StackTrace(var6) + "'");
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               if (req == null) {
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug("prepare delivery failure to '" + server + "' for request '" + requestId + "' could not be dispatched since request is no longer available");
                  }

                  return;
               }

               Exception expn = var6 instanceof Exception ? (Exception)var6 : new Exception(var6);
               if (expn instanceof UnreachableHostException) {
                  req.prepareDeliveryFailureWhenContacting(server, expn);
               } else {
                  req.prepareDeliveredTo(server);
                  req.receivedPrepareFailed(server, expn, true);
               }
            }

         }
      });
   }

   private final void sendOutCommitMsg(final DeploymentServiceMessage deploymentServiceMsg, final String server, final AdminRequestImpl request) {
      final long requestId = request.getId();
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            AdminRequestImpl req = CommonMessageSender.this.adminRequestManager.getRequest(requestId);

            try {
               if (CommonMessageSender.this.getLocalServerName().equals(server)) {
                  request.commitDeliveredTo(server);
                  CommonMessageSender.this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
               } else {
                  if (req != null) {
                     CommonMessageSender.this.adminRequestManager.addCommitDisconnectListener(server, req);
                  }

                  CommonMessageSender.this.delegate.sendMessageToTargetServer(deploymentServiceMsg, server);
                  request.commitDeliveredTo(server);
               }
            } catch (Throwable var4) {
               if (CommonMessageSender.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Send 'commit' of id '" + request.getExtendedIdName() + "' to '" + server + "' failed due to '" + (var4 instanceof UnreachableHostException ? var4 : StackTraceUtils.throwable2StackTrace(var4) + "'");
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               if (req == null) {
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug("commit delivery failure to '" + server + "' for request '" + requestId + "' could not be dispatched since request is no longer available");
                  }

                  return;
               }

               Exception expn = var4 instanceof Exception ? (Exception)var4 : new Exception(var4);
               if (expn instanceof UnreachableHostException) {
                  req.commitDeliveryFailureWhenContacting(server, expn);
               } else {
                  req.commitDeliveredTo(server);
                  req.receivedCommitFailed(server, expn);
               }
            }

         }
      });
   }

   private void sendOutCancelMsg(final DeploymentServiceMessage deploymentServiceMsg, final String server, final AdminRequestImpl request) {
      final long requestId = request.getId();
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            AdminRequestImpl req = CommonMessageSender.this.adminRequestManager.getRequest(requestId);

            try {
               if (CommonMessageSender.this.getLocalServerName().equals(server)) {
                  request.cancelDeliveredTo(server);
                  CommonMessageSender.this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
               } else {
                  if (req != null) {
                     CommonMessageSender.this.adminRequestManager.addCancelDisconnectListener(server, req);
                  }

                  CommonMessageSender.this.delegate.sendMessageToTargetServer(deploymentServiceMsg, server);
                  request.cancelDeliveredTo(server);
               }
            } catch (Throwable var4) {
               if (CommonMessageSender.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Send 'cancel' of id '" + request.getExtendedIdName() + "' to '" + server + "' failed due to '" + var4.getMessage() + "'";
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               if (req == null) {
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug("cancel delivery failure to '" + server + "' for request '" + requestId + "' could not be dispatched since request is no longer available");
                  }

                  return;
               }

               Exception expn = var4 instanceof Exception ? (Exception)var4 : new Exception(var4);
               if (expn instanceof UnreachableHostException) {
                  req.cancelDeliveryFailureWhenContacting(server, expn);
               } else {
                  req.cancelDeliveredTo(server);
                  req.receivedCancelFailed(server, expn);
               }
            }

         }
      });
   }

   private void sendOutHeartbeatMsg(final DeploymentServiceMessage deploymentServiceMsg, final String server) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            try {
               if (CommonMessageSender.isDebugEnabled()) {
                  CommonMessageSender.debug("sending heartbeat to server '" + server + "', message -->" + deploymentServiceMsg);
               }

               CommonMessageSender.this.delegate.sendHeartbeatMessage(deploymentServiceMsg, server);
            } catch (Throwable var2) {
               if (CommonMessageSender.isDebugEnabled()) {
                  CommonMessageSender.debug("Failed to send heartbeat message to server '" + server + "' due to: " + var2);
               }
            }

         }
      });
   }

   public final void sendPrepareAckMsg(long deploymentId, boolean requiresRestart) throws RemoteException {
      if (isDebugEnabled()) {
         debug("sending 'prepare ack' for id '" + deploymentId + "'");
      }

      ArrayList items = new ArrayList();
      items.add(requiresRestart);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)6, deploymentId, items);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (UnreachableHostException var8) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("PREPARE_ACK", deploymentId, var8);
         throw var8;
      } catch (Exception var9) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("PREPARE_ACK", deploymentId, var9);
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'prepare ack' of id '" + deploymentId + "' failed due to " + var9.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         throw new RemoteException("Error sending prepare ack", var9);
      }
   }

   public final void sendPrepareNakMsg(long deploymentId, Throwable reason) throws RemoteException {
      if (isDebugEnabled()) {
         debug("sending 'prepare nak' for id '" + deploymentId + "' with reason '" + reason.getMessage() + "'");
      }

      ArrayList itemList = new ArrayList();
      itemList.add(reason);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)7, deploymentId, itemList);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (UnreachableHostException var11) {
         throw var11;
      } catch (Exception var12) {
         Exception e = var12;
         DeploymentServiceLogger.errorSendMessageToAdminServer("PREPARE_NAK", deploymentId, var12);
         if (!(reason instanceof DeploymentException)) {
            ArrayList anotherList = new ArrayList();
            anotherList.add(new DeploymentException(var12.toString()));
            DeploymentServiceMessage anotherMsg = new DeploymentServiceMessage((byte)2, (byte)7, deploymentId, anotherList);

            try {
               this.delegate.sendMessageToAdminServer(anotherMsg);
               e = null;
            } catch (Exception var10) {
               DeploymentServiceLogger.errorSendMessageToAdminServer("PREPARE_NAK", deploymentId, var10);
            }

            if (e != null) {
               if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Send 'prepare nak' of id '" + deploymentId + "' failed due to '" + e.getMessage() + "'";
                  if (isDebugEnabled()) {
                     debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }

               throw new RemoteException("Error sending prepare nak", e);
            }
         }
      }

   }

   public final void sendConfigPrepareAckMsg(long deploymentId, boolean requiresRestart) throws RemoteException {
      if (isDebugEnabled()) {
         debug("sending 'config prepare ack' for id '" + deploymentId + "'");
      }

      ArrayList items = new ArrayList();
      items.add(requiresRestart);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)14, deploymentId, items);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (UnreachableHostException var8) {
         throw var8;
      } catch (Exception var9) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("CONFIG_PREPARE_ACK", deploymentId, var9);
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'config prepare ack' of id '" + deploymentId + "' failed due to " + var9.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         throw new RemoteException("Error sending config prepare ack", var9);
      }
   }

   public final void sendConfigPrepareNakMsg(long deploymentId, Throwable reason) throws RemoteException {
      if (isDebugEnabled()) {
         debug("sending 'config prepare nak' for id '" + deploymentId + "' with reason '" + reason.getMessage() + "'");
      }

      ArrayList itemList = new ArrayList();
      itemList.add(reason);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)15, deploymentId, itemList);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (UnreachableHostException var8) {
         throw var8;
      } catch (Exception var9) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("CONFIG_PREPARE_NAK", deploymentId, var9);
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'config prepare nak' of id '" + deploymentId + "' failed due to '" + var9.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   public final DeploymentServiceMessage sendBlockingGetDeploymentsMsg(DomainVersion fromVersion, String deploymentType) throws Exception {
      return this.sendBlockingGetDeploymentsMsgForPartition(fromVersion, deploymentType, (String)null);
   }

   public final DeploymentServiceMessage sendBlockingGetDeploymentsMsgForPartition(DomainVersion fromVersion, String deploymentType, String partitionName) throws Exception {
      ArrayList itemList = new ArrayList();
      DeploymentServiceMessage deploymentServiceMsg;
      if (partitionName == null) {
         deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)13, -1L, itemList);
      } else {
         deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)20, -1L, itemList);
         deploymentServiceMsg.setPartitionName(partitionName);
      }

      deploymentServiceMsg.setFromVersion(fromVersion);
      deploymentServiceMsg.setDeploymentType(deploymentType);
      if (isDebugEnabled()) {
         debug("sending 'blocking get deployments' request to catch up from version '" + fromVersion + "' for deployment type '" + deploymentType + "' for partition '" + partitionName + "'");
      }

      DeploymentServiceMessage result = this.delegate.sendBlockingMessageToAdminServer(deploymentServiceMsg);
      if (isDebugEnabled()) {
         debug("received 'blocking get deployments' response to catch up from version '" + fromVersion + "' for deployment type '" + deploymentType + "' for partition '" + partitionName + "'");
      }

      return result;
   }

   public final void sendGetDeploymentsMsg(final DomainVersion fromVersionCopy, final long deploymentId) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            if (CommonMessageSender.isDebugEnabled()) {
               CommonMessageSender.debug("sending 'get deployments' request to catch up from version '" + fromVersionCopy + "'");
            }

            ArrayList itemList = new ArrayList();
            DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)4, deploymentId, itemList);
            deploymentServiceMsg.setFromVersion(fromVersionCopy);

            try {
               CommonMessageSender.this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
            } catch (Exception var5) {
               if (CommonMessageSender.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
                  String debugMsg = "Send 'get deployments' request to catch up from version '" + fromVersionCopy + "' failed due to '" + var5.getMessage() + "'";
                  if (CommonMessageSender.isDebugEnabled()) {
                     CommonMessageSender.debug(debugMsg);
                  } else {
                     Debug.deploymentDebugConcise(debugMsg);
                  }
               }
            }

         }
      });
   }

   public final void sendCommitSucceededMsg(long deploymentId, long timeout) {
      if (isDebugEnabled()) {
         debug("sending 'commit success' for id '" + deploymentId + "' with timeout '" + timeout + "'");
      }

      ArrayList items = new ArrayList();
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)8, deploymentId, items);
      long timeoutTime = timeout != Long.MAX_VALUE && timeout >= 0L ? System.currentTimeMillis() + timeout : Long.MAX_VALUE;
      int numRetries = 0;
      boolean msgSent = false;
      Exception sendFailure = null;
      int maxRetries = 0;
      int retryInterval = 0;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (domain != null) {
         DeploymentConfigurationMBean conf = domain.getDeploymentConfiguration();
         if (conf != null) {
            maxRetries = conf.getDeploymentServiceMessageRetryCount();
            retryInterval = conf.getDeploymentServiceMessageRetryInterval();
         }
      }

      while(!msgSent && (numRetries < maxRetries || numRetries == 0) && System.currentTimeMillis() < timeoutTime) {
         try {
            this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
            msgSent = true;
            if (isDebugEnabled()) {
               debug("sent 'commit success' for id '" + deploymentId + "' with number retries of '" + numRetries + "'");
            }
         } catch (UnreachableHostException var18) {
            sendFailure = var18;
            if (isDebugEnabled()) {
               debug("send 'commit success' of id '" + deploymentId + "' retry '" + numRetries + "' failed due to " + var18.getMessage() + "'. Will retry.");
            }

            try {
               ++numRetries;
               if (maxRetries > 0) {
                  Thread.sleep((long)retryInterval);
               }
            } catch (Exception var17) {
            }
         } catch (Exception var19) {
            sendFailure = var19;
            if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
               String debugMsg = "Send 'commit success' of id '" + deploymentId + "' retry '" + numRetries + "' failed due to " + var19.getMessage() + "'";
               if (isDebugEnabled()) {
                  debug(debugMsg);
               } else {
                  Debug.deploymentDebugConcise(debugMsg);
               }
            }
            break;
         }
      }

      if (!msgSent) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("COMMIT_SUCCEEDED", deploymentId, (Throwable)sendFailure);
      }

   }

   public final void sendCommitFailedMsg(long deploymentId, Throwable reason) throws RemoteException {
      if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Sending 'commit failed' for id '" + deploymentId + "' with reason '" + reason.getMessage() + "'";
         if (isDebugEnabled()) {
            debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      ArrayList itemList = new ArrayList();
      itemList.add(reason);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)9, deploymentId, itemList);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (Exception var8) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("COMMIT_FAILED", deploymentId, var8);
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'commit failed' of id '" + deploymentId + "' failed due to '" + var8.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         throw new RemoteException("Error sending commit failed", var8);
      }
   }

   public final void sendCancelSucceededMsg(long deploymentId) {
      if (isDebugEnabled()) {
         debug("sending 'cancel success' for id '" + deploymentId + "'");
      }

      ArrayList itemList = new ArrayList();
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)10, deploymentId, itemList);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (Exception var7) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("CANCEL_SUCCEEDED", deploymentId, var7);
         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Send 'cancel success' of id '" + deploymentId + "' failed due to '" + var7.getMessage() + "'";
            if (isDebugEnabled()) {
               debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   public final void sendCancelFailedMsg(long deploymentId, Throwable reason) {
      if (isDebugEnabled()) {
         debug("sending 'cancel failed' for id '" + deploymentId + "' with reason '" + reason.getMessage() + "'");
      }

      ArrayList itemList = new ArrayList();
      itemList.add(reason);
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)11, deploymentId, itemList);

      try {
         this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
      } catch (Exception var7) {
         DeploymentServiceLogger.errorSendMessageToAdminServer("CANCEL_FAILED", deploymentId, var7);
         if (Debug.isServiceDebugEnabled()) {
            Debug.serviceDebug("send 'cancel failed' of id '" + deploymentId + "' failed due to '" + var7.getMessage() + "'");
         }
      }

   }

   public final void sendStatusMsg(String channelId, Serializable statusObject) {
      if (Debug.isServiceStatusDebugEnabled()) {
         Debug.serviceStatusDebug("send 'status' '" + statusObject + "' for channel '" + channelId + "'");
      }

      try {
         ArrayList itemList = new ArrayList();
         itemList.add(channelId);
         itemList.add(statusObject);
         this.createAndSendStatusMessage(itemList);
      } catch (Exception var4) {
         if (Debug.isServiceDebugEnabled()) {
            Debug.serviceDebug("send 'status' for channel '" + channelId + "' failed due to '" + var4.getMessage() + "'");
         }
      }

   }

   public final void sendStatusMsg(long deploymentId, String handlerId, Serializable statusObject) {
      if (Debug.isServiceStatusDebugEnabled()) {
         Debug.serviceStatusDebug("send 'status' '" + statusObject + "' for channel '" + deploymentId + "' and handler id '" + handlerId + "'");
      }

      try {
         ArrayList itemList = new ArrayList();
         itemList.add(handlerId);
         itemList.add(statusObject);
         itemList.add(new Long(deploymentId));
         this.createAndSendStatusMessage(itemList);
      } catch (Exception var6) {
         if (Debug.isServiceDebugEnabled()) {
            Debug.serviceDebug("send 'status' for channel '" + deploymentId + "' failed due to '" + var6.getMessage() + "'");
         }
      }

   }

   private void createAndSendStatusMessage(ArrayList itemList) throws Exception {
      DeploymentServiceMessage deploymentServiceMsg = new DeploymentServiceMessage((byte)2, (byte)12, -1L, itemList);
      this.delegate.sendMessageToAdminServer(deploymentServiceMsg);
   }

   Set putHandlers(String serverName, Set handlers) {
      return (Set)this.serverToHandlers.put(serverName, Collections.unmodifiableSet(handlers));
   }

   Set getHandlers(String serverName) {
      return (Set)this.serverToHandlers.get(serverName);
   }

   private static final class Maker {
      private static final CommonMessageSender SINGLETON = (CommonMessageSender)LocatorUtilities.getService(CommonMessageSender.class);
   }

   public static enum DeploymentObject {
      APPLICATION,
      LIBRARY;
   }
}
