package weblogic.deploy.internal.targetserver.datamanagement;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.common.Debug;
import weblogic.logging.Loggable;
import weblogic.management.deploy.internal.DeploymentManagerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public final class ConfigRecoveryService extends AbstractServerService {
   @Inject
   @Named("PropertyService")
   private ServerService dependencyOnPropertyService;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      if (ManagementService.getPropertyService(KERNEL_ID).isAdminServer()) {
         try {
            ConfigBackupRecoveryManager.getInstance().restoreFromBackup();
         } catch (Throwable var3) {
            Loggable logger = DeploymentManagerLogger.logFailureOnConfigRecoveryLoggable(var3);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(logger.getMessage());
            }

            logger.log();
         }
      }

   }
}
