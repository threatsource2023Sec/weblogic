package weblogic.deploy.service.internal.targetserver;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentReceiver;
import weblogic.deploy.service.RegistrationExistsException;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;

@Service
public final class TargetDeploymentsManager {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Map registeredCallbackHandlers = new HashMap();
   private DomainVersion currentDomainVersion = new DomainVersion();
   private String localServerName;
   private ServerRuntimeMBean serverBean;

   private TargetDeploymentsManager() {
   }

   /** @deprecated */
   @Deprecated
   public static TargetDeploymentsManager getInstance() {
      return TargetDeploymentsManager.Maker.SINGLETON;
   }

   public final synchronized void registerCallbackHandler(Version version, DeploymentReceiver callbackHandler) throws RegistrationExistsException {
      String callbackHandlerId = callbackHandler.getHandlerIdentity();
      if (this.registeredCallbackHandlers.get(callbackHandlerId) == null) {
         this.registeredCallbackHandlers.put(callbackHandlerId, new DeploymentReceiverCallbackDeliverer(callbackHandler));
         this.currentDomainVersion.addOrUpdateDeploymentVersion(callbackHandlerId, version);
         this.dumpActiveUpdatesList();
      } else {
         throw new RegistrationExistsException(DeploymentServiceLogger.logCallbackAlreadyRegisteredLoggable(callbackHandlerId).getMessage());
      }
   }

   public final synchronized DeploymentReceiver getDeploymentReceiver(String callbackHandlerId) {
      return (DeploymentReceiver)this.registeredCallbackHandlers.get(callbackHandlerId);
   }

   public final synchronized void unregisterCallbackHandler(String callbackHandlerId) {
      if (Debug.isServiceDebugEnabled()) {
         Debug.serviceDebug("Unregistering DeploymentReceiver callback  handler for " + callbackHandlerId + " from the target DeploymentService");
      }

      this.registeredCallbackHandlers.remove(callbackHandlerId);
      this.currentDomainVersion.removeDeploymentVersion(callbackHandlerId);
   }

   public final synchronized DomainVersion getCurrentDomainVersion() {
      return this.currentDomainVersion;
   }

   public final synchronized void setCurrentDomainVersion(DomainVersion newVersion) {
      if (newVersion == null) {
         if (Debug.isServiceDebugEnabled()) {
            String msg = "Attempt to set the current domain version to 'null' ";
            Debug.serviceDebug(msg + StackTraceUtils.throwable2StackTrace(new Exception(msg)));
         }

      } else {
         if (Debug.isServiceDebugEnabled()) {
            Debug.serviceDebug("Current domain version being set to: " + newVersion.toString());
         }

         this.currentDomainVersion = newVersion;
      }
   }

   public final String getLocalServerName() {
      if (this.localServerName == null) {
         this.localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      }

      return this.localServerName;
   }

   private final ServerRuntimeMBean getServerBean() {
      if (this.serverBean == null) {
         this.serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      }

      return this.serverBean;
   }

   public final synchronized boolean restartPending() {
      return this.getServerBean().isRestartRequired();
   }

   private synchronized void dumpActiveUpdatesList() {
      if (this.registeredCallbackHandlers.size() > 0 && Debug.isServiceDebugEnabled()) {
         Debug.serviceDebug("Active DeploymentInfos on target : " + this.registeredCallbackHandlers);
      }

   }

   static class Maker {
      static final TargetDeploymentsManager SINGLETON = (TargetDeploymentsManager)LocatorUtilities.getService(TargetDeploymentsManager.class);
   }
}
