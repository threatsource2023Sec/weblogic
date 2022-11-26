package weblogic.security.service;

import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.event.DeploymentEvent;
import weblogic.deploy.event.DeploymentEventListener;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.event.VetoableDeploymentEvent;
import weblogic.deploy.event.VetoableDeploymentListener;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.shared.LoggerWrapper;

public class DeploymentListener implements DeploymentEventListener, VetoableDeploymentListener {
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityRealm");

   DeploymentListener() {
   }

   public void applicationDeployed(DeploymentEvent evt) {
      if (log.isDebugEnabled()) {
         log.debug("DeploymentListener.applicationDeployed()");
      }

   }

   public void applicationRedeployed(DeploymentEvent evt) {
      if (log.isDebugEnabled()) {
         log.debug("DeploymentListener.applicationRedeployed()");
      }

   }

   public void applicationDeleted(DeploymentEvent evt) {
      if (log.isDebugEnabled()) {
         log.debug("DeploymentListener.applicationDeleted()");
      }

      SecurityServiceManager.applicationDeleted(evt.getAppDeployment());
   }

   public void vetoableApplicationDeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
      if (log.isDebugEnabled()) {
         log.debug("DeploymentListener.vetoableApplicationDeploy()");
      }

      boolean versioned = evt.getAppDeployment().getVersionIdentifier() != null;
      AppDeploymentMBean source;
      if (versioned) {
         String realm = evt.getSecurityInfo().getRealm();
         if (!SecurityServiceManager.isApplicationVersioningSupported(realm)) {
            if (log.isDebugEnabled()) {
               log.debug("Application versioning not supported for realm " + realm);
            }

            source = evt.getAppDeployment();
            throw new DeploymentVetoException(SecurityLogger.getAppVersioningNotSupported(realm, ApplicationVersionUtils.getDisplayName(source)));
         }
      }

      if (evt.isAdminServer()) {
         if (versioned && evt.isNewAppDeployment()) {
            AppDeploymentMBean mbean = evt.getAppDeployment();
            source = evt.getSecurityInfo().getSource();
            String realm = evt.getSecurityInfo().getRealm();

            try {
               SecurityServiceManager.applicationVersionCreated(mbean, source, realm);
            } catch (SecurityServiceRuntimeException var7) {
               if (log.isDebugEnabled()) {
                  log.debug("applicationVersionCreated() failure", var7);
               }

               throw new DeploymentVetoException(SecurityLogger.getVersionCreateFailure(ApplicationVersionUtils.getDisplayName(mbean)), var7.getNested());
            }
         }

      }
   }

   public void vetoableApplicationUndeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
   }
}
