package weblogic.deployment;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class DeploymentRegistrationService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   ServerService dependencyOnRemoteNamingService;
   private DeploymentHandler mailDeployer;

   public void start() throws ServiceFailureException {
      this.mailDeployer = new MailDeploymentHandler();
      DeploymentHandlerHome.addDeploymentHandler(this.mailDeployer);
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      DeploymentHandlerHome.removeDeploymentHandler(this.mailDeployer);
   }
}
