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
@RunLevel(15)
public final class BackgroundDeploymentService extends AbstractServerService {
   @Inject
   @Named("StartupClassPrelistenService")
   private ServerService dependencyOnStartupClassPrelistenService;
   @Inject
   @Named("ShutdownClassDeploymentService")
   private ServerService dependencyOnShutdownClassDeploymentService;
   @Inject
   BackgroundDeploymentManagerService backgroundDeploymentManagerService;

   public void start() throws ServiceFailureException {
      this.backgroundDeploymentManagerService.startBackgroundDeploymentsForDomain();
   }
}
