package weblogic.scheduler;

import java.security.AccessController;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.jndi.Environment;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;

@Service
@Named
@RunLevel(15)
public final class JobSchedulerBindingService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final String JNDI_NAME = "weblogic.JobScheduler";
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();

   public void start() throws ServiceFailureException {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerMBean serverMBean = runtimeAccess.getServer();
      if (serverMBean.getCluster() != null) {
         this.startDomain(serverMBean);
      }
   }

   private void startDomain(ServerMBean serverMBean) throws ServiceFailureException {
      try {
         JDBCSystemResourceMBean dataSource = serverMBean.getCluster().getDataSourceForJobScheduler();
         if (DEBUG) {
            debug("datasource for job scheduler " + dataSource);
         }

         if (dataSource != null) {
            this.bindJNDI();
            if (DEBUG) {
               debug("bound JobScheduler into JNDI");
            }

         }
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3);
      }
   }

   private void bindJNDI() throws NamingException {
      Environment env = new Environment();
      env.setReplicateBindings(false);
      env.setCreateIntermediateContexts(true);
      Context ic = env.getInitialContext();
      ic.bind("weblogic.JobScheduler", TimerManagerFactory.getTimerManagerFactory().getCommonjTimerManager(TimerServiceImpl.create("weblogic.JobScheduler")));
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[JobScheduler] " + s);
   }
}
