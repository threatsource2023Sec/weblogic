package weblogic.diagnostics.snmp.server;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class SNMPAgentDeploymentService extends AbstractServerService {
   @Inject
   @Named("ClassDeploymentService")
   private ServerService dependencyOnClassDeploymentService;
   private DebugLogger DEBUG;

   public SNMPAgentDeploymentService() {
      this.DEBUG = SNMPService.DEBUG;
   }

   public void start() throws ServiceFailureException {
      if (this.DEBUG.isDebugEnabled()) {
         this.DEBUG.debug("Starting SNMPAgentDeploymentService");
      }

      DeploymentHandlerHome.addDeploymentHandler(SNMPAgentDeploymentHandler.getInstance());
   }
}
