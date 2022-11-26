package weblogic.server.channels;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.AdminPortLifeCycleService;
import weblogic.utils.Debug;

@Service
@Named
@RunLevel(10)
public class EnableAdminListenersService extends AbstractServerService {
   @Inject
   @Named("AdminPortService")
   private AdminPortLifeCycleService adminPortLifeCycleService;
   @Inject
   @Named("DeploymentPreStandbyServerService")
   private ServerService dependencyOnDeploymentPreStandbyServerService;
   private static EnableAdminListenersService singleton;

   private static void setSingleton(EnableAdminListenersService oneOnly) {
      singleton = oneOnly;
   }

   public EnableAdminListenersService() {
      setSingleton(this);
   }

   static EnableAdminListenersService getInstance() {
      Debug.assertion(singleton != null);
      return singleton;
   }

   public void start() throws ServiceFailureException {
      if (!this.adminPortLifeCycleService.isServerSocketsBound()) {
         this.adminPortLifeCycleService.createAndBindServerSockets();
      }

      this.adminPortLifeCycleService.enableServerSockets();
   }
}
