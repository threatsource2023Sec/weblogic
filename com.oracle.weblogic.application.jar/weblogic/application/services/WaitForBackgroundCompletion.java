package weblogic.application.services;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public class WaitForBackgroundCompletion extends AbstractServerService {
   @Inject
   @Named("WebAppShutdownService")
   private ServerService dependencyOnWebAppShutdownService;
   @Inject
   BackgroundDeploymentManagerService backgroundDeploymentManagerService;

   public void stop() throws ServiceFailureException {
      this.backgroundDeploymentManagerService.waitForCompletion();
   }

   public void halt() throws ServiceFailureException {
      this.backgroundDeploymentManagerService.waitForCompletion();
   }

   public void start() throws ServiceFailureException {
   }
}
