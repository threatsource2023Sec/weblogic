package weblogic.elasticity;

import java.lang.annotation.Annotation;
import java.util.Map;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.watch.actions.ClusterInfo;
import weblogic.diagnostics.watch.actions.ClusterMember;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class InstanceChooser implements CommandInterface, CommandRevertInterface {
   private static final ElasticityTextTextFormatter txtFmt = ElasticityTextTextFormatter.getInstance();
   private static final String NEED_NOT_RESET_SCALING_STATUS = InstanceChooser.class.getName() + "_NEED_NOT_RESET_SCALING_STATUS";
   @SharedState(
      name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
   )
   private Map sharedMap;
   private final String clusterName;
   private final String operationName;
   private int scalingSize;
   private WorkflowContext context;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugElasticServices");

   public InstanceChooser(String clusterName, String operationName, int scalingSize) {
      this.clusterName = clusterName;
      this.operationName = operationName;
      this.scalingSize = scalingSize;
   }

   public void initialize(WorkflowContext context) {
      this.context = context;
   }

   public boolean execute() throws Exception {
      if (this.operationName.equals("scaleUp") || this.operationName.equals("scaleDown")) {
         if (this.operationName.equals("scaleUp")) {
            ElasticityLogger.logScaleUpOperationStarted(this.context.getWorkflowId(), this.clusterName);
         } else {
            ElasticityLogger.logScaleDownOperationStarted(this.context.getWorkflowId(), this.clusterName);
         }

         if (ClusterInfo.getClusterInfo(this.clusterName) == null) {
            throw new IllegalArgumentException("Invalid cluster name: " + this.clusterName);
         }

         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         ElasticServiceManager esm = (ElasticServiceManager)serviceLocator.getService(ElasticServiceManager.class, new Annotation[0]);
         ClusterScalingStatus clusterScalingStatus = esm.getClusterScalingStatus(this.clusterName);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Checking if workflow is in progress before executing " + this.context.getWorkflowName() + ", id: " + this.context.getWorkflowId());
         }

         if (esm.isWorkflowInProgressForCluster(this.clusterName, this.context.getWorkflowName())) {
            this.sharedMap.put(NEED_NOT_RESET_SCALING_STATUS, new Boolean(true));
            throw new CommandFailedNoTraceException(txtFmt.getInstanceChooserScalingOperationAlreadyInProgressText(this.clusterName));
         }

         clusterScalingStatus.setLastScalingStartTime(System.currentTimeMillis());
         WLSDynamicClusterScalingService wlsDynamicClusterScalingService = (WLSDynamicClusterScalingService)serviceLocator.getService(WLSDynamicClusterScalingService.class, new Annotation[0]);
         DynamicServersMBean dynamicServersMBean = wlsDynamicClusterScalingService.getDynamicServersMBean(this.clusterName);
         long timeSincePreviousScalingInSeconds = (System.currentTimeMillis() - clusterScalingStatus.getLastScalingEndTime()) / 1000L;
         if (timeSincePreviousScalingInSeconds < (long)dynamicServersMBean.getDynamicClusterCooloffPeriodSeconds()) {
            long remainingLockoutTime = (long)dynamicServersMBean.getDynamicClusterCooloffPeriodSeconds() - timeSincePreviousScalingInSeconds;
            throw new CommandFailedNoTraceException(txtFmt.getInstanceChooserCoolingOffPeriodLockoutText(this.clusterName, remainingLockoutTime));
         }

         ScalingOperationStatus operationStatus = new ScalingOperationStatus(this.operationName.equals("scaleUp"), this.clusterName, this.scalingSize, dynamicServersMBean.getDynamicClusterSize(), dynamicServersMBean.getMinDynamicClusterSize(), dynamicServersMBean.getMaxDynamicClusterSize());
         this.sharedMap.put("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key", operationStatus);
         if (operationStatus.isScaleUp()) {
            this.checkAndPrepareToScaleUp(dynamicServersMBean, operationStatus);
         } else {
            this.checkAndPrepareToScaleDown(dynamicServersMBean, operationStatus);
         }
      }

      return true;
   }

   public boolean revert() throws Exception {
      return true;
   }

   private boolean checkAndPrepareToScaleUp(DynamicServersMBean dynamicServersMBean, ScalingOperationStatus operationStatus) throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Scaling up cluster " + this.clusterName);
      }

      ClusterInfo currentClusterInfo = ClusterInfo.getClusterInfo(this.clusterName);
      if (currentClusterInfo == null) {
         throw new IllegalArgumentException("Invalid cluster name: " + this.clusterName);
      } else {
         int runningCount = 0;
         String[] var5 = currentClusterInfo.getMemberNames();
         int additionalServersNeeded = var5.length;

         int newSize;
         for(newSize = 0; newSize < additionalServersNeeded; ++newSize) {
            String name = var5[newSize];
            ClusterMember m = currentClusterInfo.getMember(name);
            if ("SHUTDOWN".equals(m.getState()) && operationStatus.getCandidateMemberNames().size() < this.scalingSize) {
               operationStatus.getCandidateMemberNames().add(m.getName());
            } else if (m.isRunning()) {
               ++runningCount;
            }
         }

         int shutdownInstanceCount = operationStatus.getCandidateMemberNames().size();
         if (shutdownInstanceCount == 0 && dynamicServersMBean.getDynamicClusterSize() >= dynamicServersMBean.getMaxDynamicClusterSize()) {
            operationStatus.setAllowedScalingSize(0);
            operationStatus.setCompleted(true);
            operationStatus.setSuccess(false);
            throw new CommandFailedNoTraceException(txtFmt.getClusterRunningAtMaxSizeText(this.context.getWorkflowId(), this.clusterName, runningCount, dynamicServersMBean.getDynamicClusterSize(), dynamicServersMBean.getMaxDynamicClusterSize()));
         } else {
            if (this.scalingSize <= shutdownInstanceCount) {
               ElasticityLogger.logClusterHasSufficientAvailableServers(this.context.getWorkflowId(), this.clusterName, this.scalingSize);
               operationStatus.setAllowedScalingSize(this.scalingSize);
            } else {
               additionalServersNeeded = this.scalingSize - shutdownInstanceCount;
               newSize = dynamicServersMBean.getDynamicClusterSize() + additionalServersNeeded;
               int maxSize = dynamicServersMBean.getMaxDynamicClusterSize();
               if (newSize > maxSize) {
                  ElasticityLogger.logProposedSizeExceedsLimit(this.context.getWorkflowId(), this.clusterName, newSize, maxSize);
                  additionalServersNeeded -= newSize - maxSize;
               }

               this.scalingSize = additionalServersNeeded + shutdownInstanceCount;
               if (additionalServersNeeded > 0) {
                  ElasticityLogger.logAdditionalServersNeeded(this.context.getWorkflowId(), this.clusterName, additionalServersNeeded);
               }

               operationStatus.setAdditionalServersNeeded(additionalServersNeeded);

               for(int idx = 0; idx < additionalServersNeeded; ++idx) {
                  String serverName = dynamicServersMBean.getServerNamePrefix() + (dynamicServersMBean.getDynamicClusterSize() + idx + dynamicServersMBean.getServerNameStartingIndex());
                  operationStatus.getCandidateMemberNames().add(serverName);
               }

               operationStatus.setAllowedScalingSize(this.scalingSize);
            }

            return true;
         }
      }
   }

   private boolean checkAndPrepareToScaleDown(DynamicServersMBean dynamicServersMBean, ScalingOperationStatus operationStatus) throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Scaling down cluster " + this.clusterName);
      }

      ClusterInfo clusterInfo = ClusterInfo.getClusterInfo(this.clusterName);
      if (clusterInfo == null) {
         throw new IllegalArgumentException(txtFmt.getInstanceChooserInvalidClusterNameText(this.clusterName));
      } else {
         String[] sortedMemberNames = clusterInfo.getMemberNamesReverseOrder();
         int runningCount = 0;
         String[] var6 = sortedMemberNames;
         int var7 = sortedMemberNames.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String name = var6[var8];
            ClusterMember m = clusterInfo.getMember(name);
            if (m.isRunning()) {
               ++runningCount;
               if (operationStatus.getCandidateMemberNames().size() < this.scalingSize) {
                  operationStatus.getCandidateMemberNames().add(m.getName());
               }
            }
         }

         int maxShutdownCount = runningCount - dynamicServersMBean.getMinDynamicClusterSize();
         if (maxShutdownCount <= 0) {
            operationStatus.setSuccess(false);
            operationStatus.setCompleted(true);
            operationStatus.setAllowedScalingSize(0);
            throw new CommandFailedNoTraceException(txtFmt.getClusterRunningAtMinSizeText(this.context.getWorkflowId(), this.clusterName, runningCount, dynamicServersMBean.getMinDynamicClusterSize()));
         } else {
            if (maxShutdownCount >= this.scalingSize) {
               ElasticityLogger.logClusterScalingDown(this.context.getWorkflowId(), this.clusterName, this.scalingSize);
               operationStatus.setAllowedScalingSize(this.scalingSize);
            } else {
               ElasticityLogger.logClusterPartiallyScalingDown(this.context.getWorkflowId(), this.clusterName, runningCount, maxShutdownCount, this.scalingSize, dynamicServersMBean.getMinDynamicClusterSize());
               this.scalingSize = maxShutdownCount;
               operationStatus.setAllowedScalingSize(this.scalingSize);
            }

            return true;
         }
      }
   }
}
