package weblogic.management.deploy.classdeployment;

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
public class StartupClassAfterAppsRunningService extends AbstractServerService {
   @Inject
   @Named("DeploymentPostAdminServerService")
   private ServerService dependencyOnDeploymentPostAdminServerService;

   public void start() throws ServiceFailureException {
      ClassDeploymentManager.getInstance().runStartupsAfterAppsRunning();
   }
}
