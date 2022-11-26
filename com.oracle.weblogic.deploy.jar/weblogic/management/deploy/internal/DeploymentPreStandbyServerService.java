package weblogic.management.deploy.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class DeploymentPreStandbyServerService extends AbstractServerService {
   @Inject
   @Named("WebService")
   private ServerService dependencyOnWebService;
   @Inject
   @Named("RegisterInternalApps")
   private ServerService dependencyOnRegisterInternalApps;
   @Inject
   @Named("ApplicationShutdownService")
   private ServerService dependencyOnApplicationShutdownService;
   @Inject
   private ConfiguredDeployments configuredDeployments;
   private static final DeploymentManagerLogger logger = new DeploymentManagerLogger();

   public void start() throws ServiceFailureException {
      this.configuredDeployments.deployPreStandbyInternalApps();
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      try {
         this.configuredDeployments.undeployPreStandbyInternalApps();
      } catch (Throwable var2) {
         throw new ServiceFailureException(var2);
      }
   }
}
