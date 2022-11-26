package weblogic.work;

import java.security.AccessController;
import weblogic.application.DeployHelperService;
import weblogic.application.DeploymentContext;
import weblogic.application.DeploymentFinder;
import weblogic.application.WorkDeployment;
import weblogic.j2ee.descriptor.wl.ApplicationAdminModeTriggerBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

final class ApplicationAdminModeAction extends AbstractStuckThreadAction {
   private final String applicationName;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   ApplicationAdminModeAction(ApplicationAdminModeTriggerBean trigger, String applicationName) {
      super((long)trigger.getMaxStuckThreadTime(), trigger.getStuckThreadCount());
      this.applicationName = applicationName;
      if (this.isDebugEnabled()) {
         this.debug("MaxStuckThreadTime=" + trigger.getMaxStuckThreadTime() + ", StuckThreadCount=" + trigger.getStuckThreadCount());
      }

   }

   public void execute() {
      DeploymentFinder finder = (DeploymentFinder)LocatorUtilities.getService(DeploymentFinder.class);
      final WorkDeployment deployment = finder.findDeployment(this.applicationName);
      if (this.isDebugEnabled()) {
         this.debug("executing action for deployment " + deployment);
      }

      assert deployment != null;

      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            try {
               if (ApplicationAdminModeAction.this.isDebugEnabled()) {
                  ApplicationAdminModeAction.this.debug("invoking forceProductionToAdmin");
               }

               DeployHelperService deployHelper = (DeployHelperService)LocatorUtilities.getService(DeployHelperService.class);
               DeploymentContext deploymentContext = deployHelper.createDeploymentContext((BasicDeploymentMBean)null);
               deploymentContext.setAdminModeTransition(true);
               deploymentContext.setAdminModeCallback(WorkDeployment.noopAdminModeCallback);
               deployment.forceProductionToAdmin(deploymentContext);
               if (ApplicationAdminModeAction.this.isDebugEnabled()) {
                  ApplicationAdminModeAction.this.debug("forceProductionToAdmin invoked");
               }
            } catch (DeploymentException var3) {
               var3.printStackTrace();
            }

         }
      });
   }

   public void withdraw() {
      DeploymentFinder finder = (DeploymentFinder)LocatorUtilities.getService(DeploymentFinder.class);
      final WorkDeployment deployment = finder.findDeployment(this.applicationName);
      if (this.isDebugEnabled()) {
         this.debug("withdraw action for deployment " + deployment);
      }

      assert deployment != null;

      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            try {
               if (ApplicationAdminModeAction.this.isDebugEnabled()) {
                  ApplicationAdminModeAction.this.debug("invoking adminToProduction");
               }

               DeployHelperService deployHelper = (DeployHelperService)LocatorUtilities.getService(DeployHelperService.class);
               DeploymentContext deploymentContext = deployHelper.createDeploymentContext((BasicDeploymentMBean)null);
               deploymentContext.setAdminModeTransition(false);
               deployment.adminToProduction(deploymentContext);
               if (ApplicationAdminModeAction.this.isDebugEnabled()) {
                  ApplicationAdminModeAction.this.debug("adminToProduction invoked");
               }
            } catch (DeploymentException var3) {
               var3.printStackTrace();
            }

         }
      });
   }

   public String getName() {
      return "application-admin-mode-trigger";
   }

   private void debug(String str) {
      WorkManagerLogger.logDebug("[ApplicationAdminModeAction][" + this.applicationName + "]" + str);
   }
}
