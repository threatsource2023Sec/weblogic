package weblogic.scheduler;

import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;

@Service
@Named
@RunLevel(20)
public final class JobSchedulerService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();
   private static boolean initialized;

   public void start() throws ServiceFailureException {
      if (markInitialized()) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ServerMBean serverMBean = runtimeAccess.getServer();
         if (serverMBean.getCluster() != null) {
            this.startDomain(serverMBean);
         }
      }
   }

   void startDomain(ServerMBean serverMBean) throws ServiceFailureException {
      try {
         JDBCSystemResourceMBean dataSource = serverMBean.getCluster().getDataSourceForJobScheduler();
         if (dataSource != null) {
            TimerMaster.initialize();
            registerJobSchedulerRuntime();
            TimerExecutor.initialize();
            if (DEBUG) {
               debug("Started job scheduler service for global domain");
            }

         }
      } catch (ManagementException var3) {
         throw new ServiceFailureException("JobScheduler failed to start!", var3);
      } catch (TimerException var4) {
         throw new ServiceFailureException("JobScheduler failed to start!", var4);
      }
   }

   private static synchronized boolean markInitialized() {
      if (initialized) {
         return false;
      } else {
         initialized = true;
         return true;
      }
   }

   private static synchronized void registerJobSchedulerRuntime() throws ManagementException {
      RuntimeMBean parent = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getClusterRuntime();
      JobSchedulerRuntimeMBeanImpl.init(parent);
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[JobScheduler] " + s);
   }
}
