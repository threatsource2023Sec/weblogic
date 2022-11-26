package weblogic.jms.dotnet.t3.server;

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
public class CSharpInitializationService extends AbstractServerService {
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;

   public void start() throws ServiceFailureException {
      CSharpServicesImpl.initialize();
   }
}
