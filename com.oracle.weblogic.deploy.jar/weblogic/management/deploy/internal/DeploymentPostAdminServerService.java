package weblogic.management.deploy.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public class DeploymentPostAdminServerService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      try {
         ConfiguredDeployments.getConfigureDeploymentsHandler().adminToProduction();
      } catch (Throwable var2) {
         throw new ServiceFailureException(var2);
      }

      DeploymentServerService.startAutoDeploymentPoller();
   }

   public void stop() throws ServiceFailureException {
      try {
         ConfiguredDeployments.getConfigureDeploymentsHandler().productionToAdmin(true);
      } catch (Throwable var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public void halt() throws ServiceFailureException {
      try {
         ConfiguredDeployments.getConfigureDeploymentsHandler().productionToAdmin(false);
      } catch (Throwable var2) {
         throw new ServiceFailureException(var2);
      }
   }
}
