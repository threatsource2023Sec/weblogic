package weblogic.management.provider.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public class WorkManagerService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;
   public static final String ADMIN_RMI_QUEUE = "weblogic.admin.RMI";

   public void start() throws ServiceFailureException {
      int minThreads = 2;
      ServerMBean server = this.runtimeAccess.getServer();
      boolean isAdminServer = this.runtimeAccess.isAdminServer();
      if (server.getUse81StyleExecuteQueues() || isAdminServer) {
         minThreads = 5;
      }

      WorkManagerFactory.getInstance().findOrCreate("weblogic.admin.RMI", -1, minThreads, -1);
   }
}
