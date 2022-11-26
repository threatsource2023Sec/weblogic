package weblogic.management.deploy.internal;

import java.io.File;
import java.security.AccessController;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ApplicationCompatibilityProcessor implements ConfigurationProcessor {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void updateConfiguration(DomainMBean root) throws UpdateException {
      AppDeploymentMBean[] appDeployments = root.getAppDeployments();
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);

      for(int i = 0; i < appDeployments.length; ++i) {
         AppDeploymentMBean appDeployment = appDeployments[i];
         File path;
         if (ra.isAdminServer()) {
            path = new File(appDeployment.getAbsoluteSourcePath());
         } else {
            path = AppDeployment.getFile(appDeployment, ra.getServer());
         }

         if (path.exists()) {
            ApplicationMBean application = null;

            try {
               application = MBeanConverter.createApplicationForAppDeployment(root, appDeployment, path.getPath());
               if (application != null) {
                  application.setAppDeployment(appDeployment);
               }
            } catch (DeploymentException var10) {
               String infoMsg = DeploymentManagerLogger.logConfigureAppMBeanFailedLoggable(appDeployment.getName()).getMessage();
               throw new UpdateException(infoMsg, var10);
            }
         }
      }

   }
}
