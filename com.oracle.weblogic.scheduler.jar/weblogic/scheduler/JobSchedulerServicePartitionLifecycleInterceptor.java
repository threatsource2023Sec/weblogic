package weblogic.scheduler;

import java.security.AccessController;
import javax.naming.Context;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.timers.RuntimeDomainSelector;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.work.PartitionUtility;

@Service
@ServerServiceInterceptor(JobSchedulerService.class)
@Interceptor
@ContractsProvided({JobSchedulerServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
public class JobSchedulerServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (DEBUG) {
         debug("JobSchedulerServicePartitionLifecycleInterceptor.startPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartition");
      this.start(this.getPartition(methodInvocation), (RuntimeMBean)this.getPartitionRuntime(methodInvocation));
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (DEBUG) {
         debug("JobSchedulerServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartitionInAdmin");
      this.start(this.getPartition(methodInvocation), (RuntimeMBean)this.getPartitionRuntime(methodInvocation));
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (DEBUG) {
         debug("JobSchedulerServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      TimerMaster.shutdownPartition(RuntimeDomainSelector.getDomain());
      this.unregisterMBean(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (DEBUG) {
         debug("JobSchedulerServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      TimerMaster.shutdownPartition(RuntimeDomainSelector.getDomain());
      this.unregisterMBean(partitionName);
   }

   void start(PartitionMBean partitionMBean, RuntimeMBean partitionRuntimeMBean) throws ManagementException, TimerException {
      JDBCSystemResourceMBean dataSource = partitionMBean.getDataSourceForJobScheduler();
      if (dataSource == null) {
         ClusterExtensionLogger.logJobSchedulerNotConfiguredInPartition();
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ServerMBean serverMBean = runtimeAccess.getServer();
         if (serverMBean.getCluster() == null) {
            ClusterExtensionLogger.logNoPartitionJobSchedulerInStandaloneServer();
         } else {
            TimerMaster timerMaster = TimerMaster.initialize(partitionMBean.getPartitionID());
            JobSchedulerRuntimeMBeanImpl.init(partitionRuntimeMBean, timerMaster);
            TimerExecutor.initialize(timerMaster, partitionMBean.getName());
            this.bindJobSchedulerIfNeeded(partitionMBean.getName());
         }
      }
   }

   private void bindJobSchedulerIfNeeded(String partitionName) throws TimerException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ic = env.getInitialContext();

         try {
            ic.lookup("weblogic.JobScheduler");
            if (DEBUG) {
               debug("weblogic.JobScheduleralready bound to partition " + partitionName);
            }
         } catch (NamingException var5) {
            ic.bind("weblogic.JobScheduler", TimerManagerFactory.getTimerManagerFactory().getCommonjTimerManager(TimerServiceImpl.create("weblogic.JobScheduler")));
            if (DEBUG) {
               debug("Bounded weblogic.JobScheduler successfully for partition " + partitionName);
            }
         }

      } catch (NamingException var6) {
         throw new TimerException("Unable to bind weblogic.JobSchedulerto partition " + partitionName, var6);
      }
   }

   void unregisterMBean(String partitionName) {
      JobSchedulerRuntimeMBeanImpl runtimeMBean = JobSchedulerRuntimeMBeanImpl.getInstance(false);
      if (runtimeMBean != null) {
         try {
            runtimeMBean.unregister();
         } catch (ManagementException var4) {
            if (DEBUG) {
               debug("Error while trying to unregister JobSchedulerRuntimeMBeanImpl for partition" + partitionName + " during shutdown" + var4);
            }
         }
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = PartitionUtility.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in JobSchedulerServicePartitionLifecycleInterceptor");
      }
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[JobScheduler] " + s);
   }
}
