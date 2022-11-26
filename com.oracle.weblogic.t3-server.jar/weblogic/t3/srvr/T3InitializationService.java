package weblogic.t3.srvr;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.T3BindableServices;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class T3InitializationService extends AbstractServerService {
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService depdendencyOnRemoteNamingService;

   public void start() throws ServiceFailureException {
      BootServicesImpl.initialize();
      T3BindableServices.initialize();
   }
}
