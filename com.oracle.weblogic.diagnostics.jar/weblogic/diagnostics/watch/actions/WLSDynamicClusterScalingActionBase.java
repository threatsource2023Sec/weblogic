package weblogic.diagnostics.watch.actions;

import com.oracle.core.interceptor.MethodInvocationContext;
import com.oracle.core.interceptor.impl.MethodInvocationContextManager;
import com.oracle.weblogic.diagnostics.watch.actions.ActionAdapter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Provider;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFScaleUpActionBean;
import weblogic.diagnostics.descriptor.WLDFScalingActionBean;
import weblogic.diagnostics.functions.WLSDomainRuntimeFunctionsProvider;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.elasticity.ClusterScalingStatus;
import weblogic.elasticity.ElasticServiceManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ScalingTaskRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean.ScalingType;
import weblogic.utils.PropertyHelper;

public abstract class WLSDynamicClusterScalingActionBase extends ActionAdapter {
   protected static final DiagnosticsTextWatchTextFormatter txtFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   protected static final long LOOP_DELAY = (long)PropertyHelper.getInteger("weblogic.elasticity.scaleTaskPollingPeriod", 1000);
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticActions");
   @Inject
   protected ElasticServiceManager elasticServiceManager;
   @Inject
   protected MethodInvocationContextManager invocationContextManager;
   @Inject
   protected LCMInvoker lcmInvoker;
   @Inject
   protected Provider runtimeAccessProvider;
   @Inject
   protected WLSDomainRuntimeFunctionsProvider domainFunctionsProvider;
   protected AtomicBoolean cancelRequested = new AtomicBoolean(false);

   public WLSDynamicClusterScalingActionBase(String actionServiceName) {
      super(actionServiceName);
   }

   protected String monitorLCMScalingTask(String taskUrl) throws Exception {
      while(!this.cancelRequested.get() && this.lcmInvoker.isTaskRunning(taskUrl)) {
         try {
            Thread.sleep(LOOP_DELAY);
         } catch (Exception var4) {
         }
      }

      if (this.cancelRequested.get() && this.lcmInvoker.isTaskRunning(taskUrl)) {
         this.lcmInvoker.cancelTask(taskUrl);
      }

      String finalTaskStatus = this.lcmInvoker.getTaskStatus(taskUrl);
      return finalTaskStatus;
   }

   public void cancel() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Scaling action of type " + this.getType() + " canceled");
      }

      this.cancelRequested.set(true);
   }

   protected boolean isCanceled() {
      return this.cancelRequested.get();
   }

   public void reset() {
      this.cancelRequested.set(false);
   }

   protected String invokeESMScalingOperation(WLDFScalingActionBean scalingActionConfig) throws Exception, ManagementException {
      ScalingTaskRuntimeMBean.ScalingType scalingType = ScalingType.ScaleDown;
      if (scalingActionConfig instanceof WLDFScaleUpActionBean) {
         scalingType = ScalingType.ScaleUp;
      }

      String clusterName = scalingActionConfig.getClusterName();
      String finalTaskStatus = null;
      switch (scalingType) {
         case ScaleDown:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Invoking scale down on ElasticServiceManager");
            }

            this.elasticServiceManager.scaleDown(clusterName, scalingActionConfig.getScalingSize(), new HashMap());
            break;
         case ScaleUp:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Invoking scale up on ElasticServiceManager");
            }

            this.elasticServiceManager.scaleUp(clusterName, scalingActionConfig.getScalingSize(), new HashMap());
      }

      MethodInvocationContext ctx = this.invocationContextManager.getMostRecentCompletedMethodInvocationContext();
      if (ctx == null) {
         throw new RuntimeException(txtFormatter.getInvocationContextNotFoundText(scalingActionConfig.getName()));
      } else {
         ScalingTaskRuntimeMBean scalingTask = this.elasticServiceManager.createScalingTask(clusterName, scalingType, scalingActionConfig.getScalingSize(), ctx);
         if (scalingTask == null) {
            throw new RuntimeException(txtFormatter.getCouldNotCreateScalingTaskText(scalingActionConfig.getName()));
         } else {
            while(!this.cancelRequested.get() && LCMInvoker.isRunningStatus(scalingTask.getStatus())) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("ESM Task for action " + scalingActionConfig.getName() + " running, status: " + scalingTask.getStatus());
               }

               try {
                  Thread.sleep(LOOP_DELAY);
               } catch (Exception var8) {
               }
            }

            if (this.cancelRequested.get() && LCMInvoker.isRunningStatus(scalingTask.getStatus())) {
               scalingTask.cancel();
            }

            finalTaskStatus = scalingTask.getStatus();
            return finalTaskStatus;
         }
      }
   }

   protected boolean markScalingActionInProgress(String clusterName) {
      ClusterScalingStatus scalingStatus = this.elasticServiceManager.getClusterScalingStatus(clusterName);
      return scalingStatus.markScalingActionInProgress();
   }

   protected void clearScalingActionInProgress(String clusterName) {
      ClusterScalingStatus scalingStatus = this.elasticServiceManager.getClusterScalingStatus(clusterName);
      scalingStatus.resetScalingActionInProgress();
   }

   protected boolean isTaskRunning(ScalingTaskRuntimeMBean scalingTask) throws Exception {
      String status = scalingTask.getStatus();
      return status != null && (status.equals("NONE") || status.equals("INITIALIZED") || status.equals("STARTED") || status.equals("REVERTING"));
   }

   protected boolean isClusterAtDynamicMax(String clusterName) {
      boolean isMax = false;
      int maxDynamicClusterSize = this.getConfiguredElasticMax(clusterName);
      if (WLSDomainRuntimeFunctionsProvider.aliveServersCount(clusterName) >= maxDynamicClusterSize) {
         isMax = true;
      }

      return isMax;
   }

   protected int getConfiguredElasticMax(String clusterName) {
      RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
      ClusterMBean clusterMBean = runtimeAccess.getDomain().lookupCluster(clusterName);
      int maxDynamicClusterSize = clusterMBean.getDynamicServers().getMaxDynamicClusterSize();
      return maxDynamicClusterSize;
   }

   protected boolean isClusterAtDynamicMin(String clusterName) {
      boolean isMax = false;
      int minDynamicClusterSize = this.getConfiguredElasticMin(clusterName);
      if (WLSDomainRuntimeFunctionsProvider.aliveServersCount(clusterName) <= minDynamicClusterSize) {
         isMax = true;
      }

      return isMax;
   }

   protected int getConfiguredElasticMin(String clusterName) {
      RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
      ClusterMBean clusterMBean = runtimeAccess.getDomain().lookupCluster(clusterName);
      int minDynamicClusterSize = clusterMBean.getDynamicServers().getMinDynamicClusterSize();
      return minDynamicClusterSize;
   }
}
