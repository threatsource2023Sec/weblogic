package weblogic.deploy.service.internal.adminserver;

import java.util.HashMap;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentServiceCallbackHandler;
import weblogic.deploy.service.RegistrationExistsException;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.utils.LocatorUtilities;

@Service
public final class AdminDeploymentsManager {
   private Map registeredCallbackHandlers = null;
   private DomainVersion currentDomainVersion;

   private AdminDeploymentsManager() {
      this.registeredCallbackHandlers = new HashMap();
      this.currentDomainVersion = new DomainVersion();
   }

   /** @deprecated */
   @Deprecated
   public static AdminDeploymentsManager getInstance() {
      return AdminDeploymentsManager.Maker.SINGLETON;
   }

   public void registerCallbackHandler(Version version, DeploymentServiceCallbackHandler callbackHandler) throws RegistrationExistsException {
      String callbackIdentity = callbackHandler.getHandlerIdentity();
      synchronized(this.registeredCallbackHandlers) {
         if (this.registeredCallbackHandlers.get(callbackIdentity) != null) {
            throw new RegistrationExistsException(DeploymentServiceLogger.logCallbackAlreadyRegisteredLoggable(callbackIdentity).getMessage());
         }

         this.registeredCallbackHandlers.put(callbackIdentity, new DeploymentServiceCallbackDeliverer(callbackHandler));
         this.currentDomainVersion.addOrUpdateDeploymentVersion(callbackIdentity, version);
      }

      this.dumpActiveUpdatesList();
      if (Debug.isServiceDebugEnabled()) {
         Debug.serviceDebug("Current Version updated to: " + this.currentDomainVersion.toString());
      }

   }

   public DeploymentServiceCallbackHandler getCallbackHandler(String identity) {
      synchronized(this.registeredCallbackHandlers) {
         return (DeploymentServiceCallbackHandler)this.registeredCallbackHandlers.get(identity);
      }
   }

   public void unregisterCallbackHandler(String callbackIdentity) {
      if (Debug.isServiceDebugEnabled()) {
         Debug.serviceDebug("unregistering DeploymentInfo for '" + callbackIdentity + "'");
      }

      synchronized(this.registeredCallbackHandlers) {
         this.registeredCallbackHandlers.remove(callbackIdentity);
      }

      this.currentDomainVersion.removeDeploymentVersion(callbackIdentity);
   }

   public synchronized DomainVersion getCurrentDomainVersion() {
      return this.currentDomainVersion;
   }

   public synchronized void setCurrentDomainVersion(DomainVersion newVersion) {
      this.currentDomainVersion = newVersion;
      if (Debug.isServiceDebugEnabled()) {
         Debug.serviceDebug("setting current domain version on admin to '" + this.currentDomainVersion.toString() + "'");
      }

   }

   private void dumpActiveUpdatesList() {
      synchronized(this.registeredCallbackHandlers) {
         if (this.registeredCallbackHandlers.size() > 0 && Debug.isServiceDebugEnabled()) {
            Debug.serviceDebug("registered DeploymentServiceCallbackHandlers : " + this.registeredCallbackHandlers);
         }

      }
   }

   static class Maker {
      static final AdminDeploymentsManager SINGLETON = (AdminDeploymentsManager)LocatorUtilities.getService(AdminDeploymentsManager.class);
   }
}
