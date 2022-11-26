package weblogic.management.deploy.classdeployment;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class ClassDeploymentService extends AbstractServerService {
   @Inject
   @Named("WebService")
   private ServerService dependencyOnWebService;
   @Inject
   @Named("AppClientDeploymentService")
   private ServerService dependencyOnAppClientDeploymentService;
   @Inject
   @Named("DomainRuntimeServerService")
   private ServerService dependencyOnDomainRuntimeServerService;
   @Inject
   @Named("JDBCService")
   private ServerService dependencyOnJDBCService;
   @Inject
   private ClassDeploymentManager classDeploymentManager;

   public void start() throws ServiceFailureException {
      DeploymentHandlerHome.addDeploymentHandler(this.classDeploymentManager);
      this.classDeploymentManager.runStartupsBeforeAppDeployments();
   }
}
