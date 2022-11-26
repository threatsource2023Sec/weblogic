package weblogic.work;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.server.ServiceFailureException;
import weblogic.timers.internal.TimerManagerImpl;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
@Interceptor
@ContractsProvided({WorkManagerPartitionLifecycleInterceptor.class, MethodInterceptor.class})
public class WorkManagerPartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static final DebugCategory debugWMService = Debug.getCategory("weblogic.workmanagerservice");

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugEnabled()) {
         debug("WorkManagerPartitionLifecycleInterceptor.startPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartition");
      this.doStartup(partitionName, this.getPartition(methodInvocation));
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugEnabled()) {
         debug("WorkManagerPartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartitionInAdmin");
      this.doStartup(partitionName, this.getPartition(methodInvocation));
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (debugEnabled()) {
         debug("WorkManagerPartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.doShutdown(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugEnabled()) {
         debug("WorkManagerPartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + ")current cic=" + PartitionUtility.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.doShutdown(partitionName);
   }

   private void doStartup(String partitionName, PartitionMBean partitionMBean) throws ManagementException {
      GlobalWorkManagerComponentsFactory.ensureInitialized(partitionName, partitionMBean);
      WorkManagerFactory workManagerFactory = WorkManagerFactory.getInstance();
      if (workManagerFactory instanceof SelfTuningWorkManagerFactory) {
         ((SelfTuningWorkManagerFactory)workManagerFactory).startPartitionWorkManagerHolder();
      }

   }

   private void doShutdown(String partitionName) {
      WorkManagerFactory workManagerFactory = WorkManagerFactory.getInstance();
      if (workManagerFactory instanceof SelfTuningWorkManagerFactory) {
         ((SelfTuningWorkManagerFactory)workManagerFactory).stopPartitionWorkManagerHolder();
      }

      GlobalWorkManagerComponentsFactory.shutdownPartition(partitionName);
      WorkManagerControl.getInstance().shutdownPartition(partitionName);
      RequestManager.getInstance().cleanupForPartition(partitionName);
      WorkManagerImageSource.getInstance().cleanupForPartition(partitionName);
      TimerManagerImpl.cleanupForPartition();
   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = PartitionUtility.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in WorkManagerPartitionLifecycleInterceptor");
      }
   }

   protected static boolean debugEnabled() {
      return debugWMService.isEnabled();
   }

   protected static void debug(String str) {
      WorkManagerLogger.logDebug("-- wmPartitionLifecycle - " + str);
   }
}
