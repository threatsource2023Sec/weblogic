package weblogic.deploy.internal.adminserver.operations;

import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.management.ManagementException;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;

class TaskRuntimeValidatorHelper {
   private static final int[] INVALID_WITH_START_TASKS = new int[]{11, 9, 12, 13};
   private static final int[] INVALID_WITH_DEPLOY_TASKS = new int[]{9, 12, 7};

   private static String getTaskValidationMessage(String prefix, DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) {
      StringBuilder msg = new StringBuilder(prefix);
      msg.append("New Task: (");
      msg.append(DeploymentTaskRuntime.getTaskDescription(newTaskRuntime.getTask()));
      msg.append(" for ");
      msg.append(toApplicationString(newTaskRuntime));
      msg.append("), Running Task: (");
      msg.append(DeploymentTaskRuntime.getTaskDescription(runningTaskRuntime.getTask()));
      msg.append(" for ");
      msg.append(toApplicationString(runningTaskRuntime));
      msg.append(")");
      return msg.toString();
   }

   static void validateDifferentOperation(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) throws ManagementException {
      if (isSameApplicationIdAndPartition(runningTaskRuntime, newTaskRuntime) && runningTaskRuntime.getTask() == newTaskRuntime.getTask()) {
         throw new ManagementException(getTaskValidationMessage("There is the same running task. ", runningTaskRuntime, newTaskRuntime));
      }
   }

   static void validateStartOperation(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) throws ManagementException {
      if (isSameApplicationIdAndPartition(runningTaskRuntime, newTaskRuntime) && isInvalidTaskPair(runningTaskRuntime, newTaskRuntime, 7, INVALID_WITH_START_TASKS)) {
         throw new ManagementException(getTaskValidationMessage("Start task cannot run with a new task. ", runningTaskRuntime, newTaskRuntime));
      }
   }

   static void validateDeployOperation(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) throws ManagementException {
      if (isSameApplicationIdAndPartition(runningTaskRuntime, newTaskRuntime) && isInvalidTaskPair(runningTaskRuntime, newTaskRuntime, 11, INVALID_WITH_DEPLOY_TASKS)) {
         throw new ManagementException(getTaskValidationMessage("Deploy task cannot run with a new task. ", runningTaskRuntime, newTaskRuntime));
      }
   }

   private static boolean isSameApplicationIdAndPartition(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) {
      return runningTaskRuntime.getApplicationId().equals(newTaskRuntime.getApplicationId()) && isSamePartition(runningTaskRuntime, newTaskRuntime);
   }

   private static boolean isSamePartition(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime) {
      String runningTaskPartition = getPartitionName(runningTaskRuntime);
      String newTaskPartition = getPartitionName(newTaskRuntime);
      return runningTaskPartition == null && newTaskPartition == null || runningTaskPartition != null && runningTaskPartition.equals(newTaskPartition);
   }

   private static boolean isInvalidTaskPair(DeploymentTaskRuntimeMBean runningTaskRuntime, DeploymentTaskRuntimeMBean newTaskRuntime, int oneTask, int[] invalidTasks) {
      DeploymentTaskRuntimeMBean taskRuntime = null;
      if (runningTaskRuntime.getTask() == oneTask) {
         taskRuntime = newTaskRuntime;
      } else if (newTaskRuntime.getTask() == oneTask) {
         taskRuntime = runningTaskRuntime;
      }

      if (taskRuntime != null) {
         int task = taskRuntime.getTask();

         for(int i = 0; i < invalidTasks.length; ++i) {
            if (task == invalidTasks[i]) {
               return true;
            }
         }
      }

      return false;
   }

   private static String toApplicationString(DeploymentTaskRuntimeMBean taskRuntime) {
      String versionSuffix = taskRuntime.getApplicationVersionIdentifier() == null ? "" : "#" + taskRuntime.getApplicationVersionIdentifier();
      String partitionName = getPartitionName(taskRuntime);
      String partitionSuffix = "";
      if (partitionName != null) {
         partitionSuffix = " on partition " + partitionName;
      }

      return taskRuntime.getApplicationName() + versionSuffix + partitionSuffix;
   }

   private static String getPartitionName(DeploymentTaskRuntimeMBean taskRuntime) {
      DeploymentData taskData = taskRuntime.getDeploymentData();
      DeploymentOptions taskOptions = taskData == null ? null : taskData.getDeploymentOptions();
      return taskOptions == null ? null : taskOptions.getPartition();
   }
}
