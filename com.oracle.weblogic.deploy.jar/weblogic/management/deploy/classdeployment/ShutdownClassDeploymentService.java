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
@RunLevel(15)
public class ShutdownClassDeploymentService extends AbstractServerService {
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependencyOnDeploymentServerService;
   private boolean shutdown;

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      synchronized(this) {
         if (this.shutdown) {
            return;
         }

         this.shutdown = true;
      }

      ClassDeploymentManager.getInstance().runShutdownClasses();
   }
}
