package weblogic.deploy.service.internal.adminserver;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.DeploymentServiceCallbackHandlerV2;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.RequestManager;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.deploy.service.internal.transport.ServerDisconnectListener;
import weblogic.deploy.service.internal.transport.ServerDisconnectManager;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.TargetingUtils;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;

@Service
public final class AdminRequestManager extends RequestManager {
   private final HashSet pendingCancels = new HashSet();
   private final HashMap requestsMap = new HashMap();
   private final AdminDeploymentsManager adminDeploymentsManager = AdminDeploymentsManager.getInstance();
   private static final int DEPLOYMENT_SUCCEEDED = 1;
   private static final int COMMIT_FAILURE = 2;
   private static final int CANCEL_SUCCESS = 3;
   private static final int COMMIT_SUCCESS = 4;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private AdminRequestManager() {
      super("AdminRequestManager");
      ServerDisconnectManager.getInstance().initialize();
   }

   /** @deprecated */
   @Deprecated
   public static AdminRequestManager getInstance() {
      return AdminRequestManager.Maker.SINGLETON;
   }

   public ArrayList getDeployments(DomainVersion fromVersion, String targetServer, long msgId, boolean isSynchronous, String deploymentType, Set deploymentTypes, String partitionName) {
      boolean debug = this.isDebugEnabled();
      ArrayList result = new ArrayList();
      Map fromVersionDeploymentsMap = fromVersion.getDeploymentsVersionMap();
      DomainVersion currentDomainVersion = this.adminDeploymentsManager.getCurrentDomainVersion();
      if (deploymentType != null && deploymentType.length() != 0) {
         result.addAll(this.getDeploymentsFor(deploymentType, fromVersion, targetServer, msgId, partitionName));
      } else if (!currentDomainVersion.equals(fromVersion)) {
         if (debug) {
            this.debug("AdminRequestManager: getDeployments: fromVersion '" + fromVersion + "' is not equal to currentVersion '" + currentDomainVersion + "' - need to generate deployments to get  them in sync");
         }

         Map currentVersionDeploymentsMap = currentDomainVersion.getDeploymentsVersionMap();
         Iterator iterator = currentVersionDeploymentsMap.keySet().iterator();

         label67:
         while(true) {
            Deployment[] deployments;
            do {
               String deploymentId;
               Version deploymentVersion;
               Version toV;
               do {
                  do {
                     do {
                        while(true) {
                           if (!iterator.hasNext()) {
                              break label67;
                           }

                           deploymentId = (String)iterator.next();
                           if (deploymentTypes != null && deploymentTypes.contains(deploymentId)) {
                              deploymentVersion = (Version)fromVersionDeploymentsMap.get(deploymentId);
                              toV = (Version)currentVersionDeploymentsMap.get(deploymentId);
                              break;
                           }

                           if (debug) {
                              this.debug("Skipping " + deploymentId + " passed in collection does NOT contain it for server - " + targetServer);
                           }
                        }
                     } while(toV == null);
                  } while(toV.equals(deploymentVersion));
               } while(deploymentVersion == null);

               DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentId);
               deployments = callbackHandler.getDeployments(deploymentVersion, toV, targetServer, partitionName);
            } while(deployments == null);

            for(int i = 0; i < deployments.length; ++i) {
               if (debug) {
                  this.debug("adding deployment '" + deployments[i] + "' to getDeploymentsResponse");
               }

               result.add(deployments[i]);
            }
         }
      }

      if (!isSynchronous) {
         CommonMessageSender messageSender = CommonMessageSender.getInstance();
         messageSender.sendGetDeploymentsResponse(result, targetServer, currentDomainVersion, msgId);
      }

      return result;
   }

   public synchronized void addToRequestTable(AdminRequestImpl newRequest) {
      if (this.isDebugEnabled()) {
         this.debug("adding request  '" + newRequest.getId() + "' to admin request table");
      }

      this.requestsMap.put(new Long(newRequest.getId()), newRequest);
   }

   public final synchronized Set getRequests() {
      return this.requestsMap.entrySet();
   }

   public final synchronized AdminRequestImpl getRequest(long id) {
      return (AdminRequestImpl)this.requestsMap.get(new Long(id));
   }

   public final synchronized void removeRequest(long requestId) {
      if (this.isDebugEnabled()) {
         this.debug("removing request '" + requestId + "' from admin request table");
      }

      this.requestsMap.remove(new Long(requestId));
   }

   public final synchronized void addPendingCancel(long id) {
      Long cancelledId = new Long(id);
      if (!this.pendingCancels.contains(cancelledId)) {
         this.pendingCancels.add(cancelledId);
      }

      if (this.isDebugEnabled()) {
         this.debug("adding '" + id + "' to list of pending cancels on admin");
      }

   }

   public final synchronized boolean isCancelPending(long id) {
      Long cancelledId = new Long(id);
      return this.pendingCancels.contains(cancelledId);
   }

   public final synchronized void removePendingCancel(long id) {
      this.pendingCancels.remove(new Long(id));
   }

   public final void deliverDeploySucceededCallback(AdminRequestImpl request, Map deferredDeployments, List restartFailureList) {
      long deploymentId = request.getId();
      HashMap deploymentCallbackTable = new LinkedHashMap();
      this.setUpCallbacksToFailuresTable(request, deploymentCallbackTable, deferredDeployments);
      this.deliverCallbacks(deploymentCallbackTable, deploymentId, 1, restartFailureList);
   }

   private boolean isDeferredDeploymentTargetInDeploymentTargetCluster(Set deferredDeploymentSet, String deploymentTarget) {
      ClusterMBean targetCluster = TargetingUtils.getTargetCluster(deploymentTarget);
      if (targetCluster == null) {
         return false;
      } else {
         ServerMBean[] servers = targetCluster.getServers();
         if (servers == null) {
            return false;
         } else {
            for(int i = 0; i < servers.length; ++i) {
               if (deferredDeploymentSet.contains(servers[i])) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public ArrayList getDeploymentsFor(String deploymentType, DomainVersion fromVersion, String targetServer, long msgId, String partitionName) {
      boolean debug = this.isDebugEnabled();
      ArrayList result = new ArrayList();
      if (deploymentType != null && deploymentType.length() != 0) {
         Map fromVersionDeploymentsMap = fromVersion.getDeploymentsVersionMap();
         DomainVersion currentDomainVersion = this.adminDeploymentsManager.getCurrentDomainVersion();
         if (debug) {
            this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') : current DomainVersion : " + currentDomainVersion);
         }

         if (currentDomainVersion.equals(fromVersion)) {
            if (debug) {
               this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') : managed server is at the same version as admin server.");
            }

            return result;
         } else {
            if (debug) {
               this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "' : managed server is *NOT* at the same version as admin server.");
            }

            Map currentVersionDeploymentsMap = currentDomainVersion.getDeploymentsVersionMap();
            Version deploymentVersion = (Version)fromVersionDeploymentsMap.get(deploymentType);
            Version toV = (Version)currentVersionDeploymentsMap.get(deploymentType);
            if (debug) {
               this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  fromVersion : '" + deploymentVersion);
               this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  toVersion : '" + toV);
            }

            if (toV != null && !toV.equals(deploymentVersion) && this.isKnownServer(targetServer)) {
               if (debug) {
                  this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  From-Version is *NOT* equal to To-Version");
               }

               DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
               Deployment[] deployments = callbackHandler.getDeployments(deploymentVersion, toV, targetServer, partitionName);
               if (deployments != null) {
                  List foundDeployments = Arrays.asList(deployments);
                  if (debug) {
                     this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  foundDeployments : " + foundDeployments);
                  }

                  result.addAll(foundDeployments);
               } else if (debug) {
                  this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  did *NOT* find Deployments");
               }
            } else if (debug) {
               this.debug(" AdminRequestManager: getDeploymentsFor('" + deploymentType + "', '" + targetServer + "') :  From-Version is equal to To-Version");
            }

            if (debug) {
               this.debug("AdminRequestManager.getDeploymentsFor() : returning deployments : " + result);
            }

            return result;
         }
      } else {
         return result;
      }
   }

   private void updateDeferredDeploymentsSubset(Map deferredDeployments, Set deferredDeploymentsSet, Deployment deployment) {
      if (deferredDeployments != null && deferredDeployments.size() > 0) {
         String[] deploymentTargets = deployment.getTargets();

         for(int i = 0; i < deploymentTargets.length; ++i) {
            if (deferredDeployments.keySet().contains(deploymentTargets[i])) {
               this.addServerToDeferredSet(deferredDeploymentsSet, deploymentTargets[i], deployment, deferredDeployments);
            } else if (this.isDeferredDeploymentTargetInDeploymentTargetCluster(deferredDeployments.keySet(), deploymentTargets[i])) {
               ClusterMBean deploymentTargetCluster = TargetingUtils.getTargetCluster(deploymentTargets[i]);
               ServerMBean[] servers = deploymentTargetCluster.getServers();

               for(int j = 0; j < servers.length; ++j) {
                  String serverName = servers[j].getName();
                  if (deferredDeployments.keySet().contains(serverName)) {
                     this.addServerToDeferredSet(deferredDeploymentsSet, serverName, deployment, deferredDeployments);
                  }
               }
            }
         }
      }

   }

   private void addServerToDeferredSet(Set deferredDeploymentsSet, String serverName, Deployment deployment, Map deferredDeployments) {
      if (!deferredDeploymentsSet.contains(serverName)) {
         if (this.isDebugEnabled()) {
            this.debug("AdminRequestManager: updateDeferredDeploymentsSubset adding " + serverName + " to deferred set of " + deployment.getCallbackHandlerId());
         }

         deferredDeploymentsSet.add(deferredDeployments.get(serverName));
      }

   }

   public final void deliverAppPrepareFailedCallback(AdminRequestImpl request, AdminDeploymentException deploymentException) {
      long deploymentId = request.getId();
      Iterator iterator = request.getDeployments();
      HashSet deploymentCallbackTable = new HashSet();
      boolean debug = this.isDebugEnabled();

      while(true) {
         while(true) {
            String deploymentType;
            do {
               if (!iterator.hasNext()) {
                  return;
               }

               Deployment deployment = (Deployment)iterator.next();
               deploymentType = deployment.getCallbackHandlerId();
            } while(deploymentCallbackTable.contains(deploymentType));

            deploymentCallbackTable.add(deploymentType);
            DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
            if (callbackHandler == null) {
               if (debug) {
                  this.debug("No DeploymentServiceCallbackHandler to dispatch app prepare delivery failure for deployment id '" + deploymentId + "' for '" + deploymentType + "'");
               }
            } else {
               try {
                  callbackHandler.appPrepareFailed(deploymentId, deploymentException);
               } catch (Throwable var12) {
                  if (debug) {
                     this.debug("DeploymentServiceCallbackHandler appPrepareFailed callback failed for deployment id '" + deploymentId + "' and deployment type '" + deploymentType + "' due to '" + var12.getMessage() + "'");
                  }
               }
            }
         }
      }
   }

   public final void deliverDeployFailedCallback(AdminRequestImpl request, AdminDeploymentException deploymentException) {
      long deploymentId = request.getId();
      Iterator iterator = request.getDeployments();
      HashSet deploymentCallbackTable = new HashSet();
      boolean debug = this.isDebugEnabled();

      while(true) {
         while(true) {
            String deploymentType;
            do {
               if (!iterator.hasNext()) {
                  return;
               }

               Deployment deployment = (Deployment)iterator.next();
               deploymentType = deployment.getCallbackHandlerId();
            } while(deploymentCallbackTable.contains(deploymentType));

            deploymentCallbackTable.add(deploymentType);
            DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
            if (callbackHandler == null) {
               if (debug) {
                  this.debug("No DeploymentServiceCallbackHandler to dispatch prepare delivery failure for deployment id '" + deploymentId + "' for '" + deploymentType + "'");
               }
            } else {
               try {
                  callbackHandler.deployFailed(deploymentId, deploymentException);
               } catch (Throwable var12) {
                  if (debug) {
                     this.debug("DeploymentServiceCallbackHandler deployFailed callback failed for deployment id '" + deploymentId + "' and deployment type '" + deploymentType + "' due to '" + var12.getMessage() + "'");
                  }
               }
            }
         }
      }
   }

   public final void deliverCommitFailureCallback(AdminRequestImpl request, Map commitFailures) {
      long deploymentId = request.getId();
      HashMap deploymentCallbackTable = new LinkedHashMap();
      this.setUpCallbacksToFailuresTable(request, deploymentCallbackTable, commitFailures);
      this.deliverCallbacks(deploymentCallbackTable, deploymentId, 2, new ArrayList());
   }

   public final void deliverCommitSucceededCallback(AdminRequestImpl request) {
      long deploymentId = request.getId();
      HashMap deploymentCallbackTable = new LinkedHashMap();
      this.setUpCallbacksToFailuresTable(request, deploymentCallbackTable, (Map)null);
      this.deliverCallbacks(deploymentCallbackTable, deploymentId, 4, new ArrayList());
   }

   public final void deliverDeployCancelSucceededCallback(AdminRequestImpl request, Map deferredDeployments) {
      long deploymentId = request.getId();
      HashMap deploymentCallbackTable = new LinkedHashMap();
      this.setUpCallbacksToFailuresTable(request, deploymentCallbackTable, deferredDeployments);
      this.deliverCallbacks(deploymentCallbackTable, deploymentId, 3, new ArrayList());
   }

   private void deliverCallbacks(HashMap deploymentCallbackTable, long deploymentId, int operation, List restartFailureList) {
      if (deploymentCallbackTable.size() > 0) {
         Iterator iter = deploymentCallbackTable.keySet().iterator();
         FailureDescription[] dummy = new FailureDescription[0];
         boolean debug = this.isDebugEnabled();

         while(true) {
            while(iter.hasNext()) {
               String deploymentType = (String)iter.next();
               Set deferredDeploymentsSubset = (Set)deploymentCallbackTable.get(deploymentType);
               DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
               if (callbackHandler == null) {
                  if (debug) {
                     this.debug("No DeploymentServiceCallbackHandler to deliver '" + operation + "' for deployment id '" + deploymentId + "' for '" + deploymentType + "'");
                  }
               } else {
                  try {
                     switch (operation) {
                        case 1:
                           List finalFailures = new ArrayList();
                           if (restartFailureList != null) {
                              finalFailures.addAll(restartFailureList);
                           }

                           if (deferredDeploymentsSubset != null) {
                              finalFailures.addAll(deferredDeploymentsSubset);
                           }

                           callbackHandler.deploySucceeded(deploymentId, (FailureDescription[])((FailureDescription[])finalFailures.toArray(dummy)));
                           break;
                        case 2:
                           callbackHandler.commitFailed(deploymentId, (FailureDescription[])((FailureDescription[])deferredDeploymentsSubset.toArray(dummy)));
                           break;
                        case 3:
                           callbackHandler.cancelSucceeded(deploymentId, (FailureDescription[])((FailureDescription[])deferredDeploymentsSubset.toArray(dummy)));
                           break;
                        case 4:
                           callbackHandler.commitSucceeded(deploymentId);
                     }
                  } catch (Throwable var13) {
                     if (debug) {
                        this.debug("DeploymentServiceCallbackHandler '" + this.getOperationString(operation) + "' callback failed for request '" + deploymentId + "' and deployment type '" + deploymentType + "' due to '" + StackTraceUtils.throwable2StackTrace(var13) + "'");
                     }
                  }
               }
            }

            return;
         }
      }
   }

   private void setUpCallbacksToFailuresTable(AdminRequestImpl request, HashMap deploymentCallbackTable, Map deferredDeployments) {
      Iterator iterator = request.getDeployments();

      while(iterator.hasNext()) {
         Deployment deployment = (Deployment)iterator.next();
         String deploymentType = deployment.getCallbackHandlerId();
         Set deferredDeploymentsSet = (Set)deploymentCallbackTable.get(deploymentType);
         if (deferredDeploymentsSet == null) {
            deferredDeploymentsSet = new HashSet();
            deploymentCallbackTable.put(deploymentType, deferredDeploymentsSet);
         }

         if (deferredDeployments != null) {
            this.updateDeferredDeploymentsSubset(deferredDeployments, (Set)deferredDeploymentsSet, deployment);
         }
      }

   }

   public final void deliverDeployCancelFailedCallback(AdminRequestImpl request, AdminDeploymentException deploymentException) {
      long deploymentId = request.getId();
      Iterator iterator = request.getDeployments();
      boolean debug = this.isDebugEnabled();

      while(true) {
         while(iterator.hasNext()) {
            Deployment deployment = (Deployment)iterator.next();
            String deploymentType = deployment.getCallbackHandlerId();
            DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
            if (callbackHandler == null) {
               if (debug) {
                  this.debug("No DeploymentServiceCallbackHandler to deliver failure of deployment id '" + deploymentId + "' for '" + deploymentType + "'");
               }
            } else {
               try {
                  callbackHandler.cancelFailed(deploymentId, deploymentException);
               } catch (Throwable var11) {
                  if (debug) {
                     this.debug("DeploymentServiceCallbackHandler cancelFailed callback failed for deployment id " + deploymentId + "' and deployment type '" + deploymentType + "' due to '" + var11.getMessage() + "'");
                  }
               }
            }
         }

         return;
      }
   }

   public void addPrepareDisconnectListener(String server, AdminRequestImpl request) throws RemoteException {
      if (request != null) {
         AdminRequestStatus reqStatus = request.getStatus();
         DisconnectListener reqListener = reqStatus.getPrepareDisconnectListener(server);
         this.addDisconnectListener(server, reqListener);
      }
   }

   public void addCommitDisconnectListener(String server, AdminRequestImpl request) throws RemoteException {
      if (request != null) {
         AdminRequestStatus reqStatus = request.getStatus();
         DisconnectListener reqListener = reqStatus.getCommitDisconnectListener(server);
         this.addDisconnectListener(server, reqListener);
      }
   }

   public void addCancelDisconnectListener(String server, AdminRequestImpl request) throws RemoteException {
      if (request != null) {
         AdminRequestStatus reqStatus = request.getStatus();
         DisconnectListener reqListener = reqStatus.getCancelDisconnectListener(server);
         this.addDisconnectListener(server, reqListener);
      }
   }

   public void removeDisconnectListener(String server, DisconnectListener listener) {
      ServerDisconnectManager discMngr = ServerDisconnectManager.getInstance();
      ServerDisconnectListener discLsnr = discMngr.findDisconnectListener(server);
      if (discLsnr != null) {
         discLsnr.unregisterListener(listener);
         if (this.isDebugEnabled()) {
            this.debug(" +++ Removed listener='" + listener + "' : from : " + discLsnr);
         }

      }
   }

   public final void deliverRequestStatusUpdateCallback(AdminRequestImpl request, String action, String server) {
      long deploymentId = request.getId();
      Iterator iterator = request.getDeployments();

      while(true) {
         while(iterator.hasNext()) {
            Deployment deployment = (Deployment)iterator.next();
            String deploymentType = deployment.getCallbackHandlerId();
            DeploymentServiceCallbackHandler callbackHandler = this.adminDeploymentsManager.getCallbackHandler(deploymentType);
            if (callbackHandler == null) {
               if (this.isDebugEnabled()) {
                  this.debug("No DeploymentServiceCallbackHandler to deliver '" + action + "' for deployment id '" + deploymentId + "' for '" + deploymentType + "'");
               }
            } else if (!(callbackHandler instanceof DeploymentServiceCallbackHandlerV2)) {
               if (this.isDebugEnabled()) {
                  this.debug("Callback handler is not V2 to deliver '" + action + "' for deployment id '" + deploymentId + "' for '" + deploymentType + "'");
               }
            } else {
               DeploymentServiceCallbackHandlerV2 v2Handler = (DeploymentServiceCallbackHandlerV2)callbackHandler;

               try {
                  v2Handler.requestStatusUpdated(deploymentId, action, server);
               } catch (Throwable var12) {
                  if (this.isDebugEnabled()) {
                     this.debug("DeploymentServiceCallbackHandlerV2 requestStatusUpdated callback failed for deployment id " + deploymentId + "' and deployment type '" + deploymentType + "' due to '" + var12.getMessage() + "'");
                  }
               }
            }
         }

         return;
      }
   }

   private void addDisconnectListener(String server, DisconnectListener listener) throws RemoteException {
      ServerDisconnectManager discMngr = ServerDisconnectManager.getInstance();
      ServerDisconnectListener discLsnr = discMngr.findOrCreateDisconnectListener(server);
      if (discLsnr == null) {
         String msg = DeployerRuntimeLogger.serverUnreachable(server);
         throw new RemoteException(msg);
      } else {
         discLsnr.registerListener(listener);
         if (this.isDebugEnabled()) {
            this.debug(" +++ Added listener='" + listener + "' : to : " + discLsnr);
         }

      }
   }

   private String getOperationString(int operation) {
      String result = null;
      switch (operation) {
         case 1:
            result = "DEPLOYMENT_SUCCEEDED";
            break;
         case 2:
            result = "COMMIT_FAILURE";
            break;
         case 3:
            result = "CANCEL_SUCCESS";
            break;
         case 4:
            result = "COMMIT_SUCCESS";
      }

      return result;
   }

   private boolean isKnownServer(String srcServer) {
      ServerMBean[] servers = ManagementService.getRuntimeAccess(kernelId).getDomain().getServers();
      ServerMBean[] var3 = servers;
      int var4 = servers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerMBean server = var3[var5];
         if (srcServer.equals(server.getName())) {
            return true;
         }
      }

      return false;
   }

   private static class Maker {
      private static final AdminRequestManager SINGLETON = (AdminRequestManager)LocatorUtilities.getService(AdminRequestManager.class);
   }
}
