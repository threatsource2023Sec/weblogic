package weblogic.work.concurrent.services;

import java.util.concurrent.ExecutionException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.server.ServiceFailureException;
import weblogic.work.concurrent.context.ContextCache;
import weblogic.work.concurrent.runtime.GlobalConstraints;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.spi.ThreadNumberConstraints;

@Service
@Interceptor
@ContractsProvided({ConcurrentPartitionLifeCycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(ConcurrentManagedObjectDeploymentService.class)
public class ConcurrentPartitionLifeCycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private final BeanUpdateListener constraintsUpdateListener = new BeanUpdateListener() {
      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         PartitionMBean partitionBean = (PartitionMBean)event.getSourceBean();
         BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var4 = list;
         int var5 = list.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BeanUpdateEvent.PropertyUpdate prop = var4[var6];
            if (prop.getUpdateType() == 1) {
               String propertyName = prop.getPropertyName();
               ThreadNumberConstraints mtfLimit;
               if (propertyName.equals("MaxConcurrentLongRunningRequests")) {
                  mtfLimit = GlobalConstraints.getExecutorConstraints().getPartitionLimit(partitionBean.getName());
                  mtfLimit.adjustMax(partitionBean.getMaxConcurrentLongRunningRequests());
               } else if (propertyName.equals("MaxConcurrentNewThreads")) {
                  mtfLimit = GlobalConstraints.getMTFConstraints().getPartitionLimit(partitionBean.getName());
                  mtfLimit.adjustMax(partitionBean.getMaxConcurrentNewThreads());
               }
            }
         }

      }
   };

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.startPartitionInAdmin(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "startPartitionInAdmin");
      this.initialize(methodInvocation, partitionName);
      methodInvocation.proceed();
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.resumePartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "resumePartition");
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.startPartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "startPartition");
      this.initialize(methodInvocation, partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.suspendPartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.forceSuspendPartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.shutdownPartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.cleanup(methodInvocation, partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentPartitionLifeCycleInterceptor.forceShutdownPartition(" + partitionName + "): " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.cleanup(methodInvocation, partitionName);
   }

   private void getOrCreateConcurrentManagedObjectsRuntime(MethodInvocation methodInvocation, String partitionName) throws ManagementException {
      PartitionRuntimeMBean partitionRuntimeMBean = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      RuntimeAccessUtils.getOrCreatePartitionConcurrentRuntime(partitionName, partitionRuntimeMBean);
   }

   private void initialize(MethodInvocation methodInvocation, String partitionName) throws ManagementException, ExecutionException {
      this.getOrCreateConcurrentManagedObjectsRuntime(methodInvocation, partitionName);
      ConcurrentManagedObjectDeploymentService.createDefaultObjectsForCurrentPartition();
      PartitionMBean partitionMBean = RuntimeAccessUtils.getPartitionMBean(partitionName);
      partitionMBean.addBeanUpdateListener(this.constraintsUpdateListener);
   }

   private void cleanup(MethodInvocation methodInvocation, String partitionName) {
      ConcurrentManagedObjectDeploymentService.cleanupDefaultObjects(partitionName);
      ContextCache.removeData(partitionName);
      PartitionMBean partitionMBean = RuntimeAccessUtils.getPartitionMBean(partitionName);
      partitionMBean.removeBeanUpdateListener(this.constraintsUpdateListener);
      GlobalConstraints.getExecutorConstraints().removeForPartition(partitionName);
      GlobalConstraints.getMTFConstraints().removeForPartition(partitionName);
      PartitionRuntimeMBean partitionRuntimeMBean = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);

      try {
         RuntimeAccessUtils.removePartitionConcurrentRuntime(partitionName, partitionRuntimeMBean);
      } catch (ManagementException var6) {
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      String currentPartitionName = RuntimeAccessUtils.getCurrentPartitionName();
      if (!currentPartitionName.equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext() + "], expected partition name " + partitionName + " on " + method + "() in ConcurrentPartitionLifeCycleInterceptor");
      }
   }
}
