package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.DeploymentInternalDataInfo;

public class DeploymentInternalDataInfoImpl implements DeploymentInternalDataInfo {
   private static final String DEPLOY_TASK_ACTIVATE = "DEPLOY_TASK_ACTIVATE";
   private static final String DEPLOY_TASK_PREPARE = "DEPLOY_TASK_PREPARE";
   private static final String DEPLOY_TASK_DEACTIVATE = "DEPLOY_TASK_DEACTIVATE";
   private static final String DEPLOY_TASK_REMOVE = "DEPLOY_TASK_REMOVE";
   private static final String DEPLOY_TASK_UNPREPARE = "DEPLOY_TASK_UNPREPARE";
   private static final String DEPLOY_TASK_DISTRIBUTE = "DEPLOY_TASK_DISTRIBUTE";
   private static final String DEPLOY_TASK_START = "DEPLOY_TASK_START";
   private static final String DEPLOY_TASK_STOP = "DEPLOY_TASK_STOP";
   private static final String DEPLOY_TASK_REDEPLOY = "DEPLOY_TASK_REDEPLOY";
   private static final String DEPLOY_TASK_UPDATE = "DEPLOY_TASK_UPDATE";
   private static final String DEPLOY_TASK_DEPLOY = "DEPLOY_TASK_DEPLOY";
   private static final String DEPLOY_TASK_UNDEPLOY = "DEPLOY_TASK_UNDEPLOY";
   private static final String DEPLOY_TASK_RETIRE = "DEPLOY_TASK_RETIRE";
   private String operationName = null;
   private String deploymentName = null;

   public DeploymentInternalDataInfoImpl(int operation, String deploymentName) {
      if (operation == 1) {
         this.operationName = "DEPLOY_TASK_ACTIVATE";
      } else if (operation == 2) {
         this.operationName = "DEPLOY_TASK_PREPARE";
      } else if (operation == 3) {
         this.operationName = "DEPLOY_TASK_DEACTIVATE";
      } else if (operation == 4) {
         this.operationName = "DEPLOY_TASK_REMOVE";
      } else if (operation == 5) {
         this.operationName = "DEPLOY_TASK_UNPREPARE";
      } else if (operation == 6) {
         this.operationName = "DEPLOY_TASK_DISTRIBUTE";
      } else if (operation == 7) {
         this.operationName = "DEPLOY_TASK_START";
      } else if (operation == 8) {
         this.operationName = "DEPLOY_TASK_STOP";
      } else if (operation == 9) {
         this.operationName = "DEPLOY_TASK_REDEPLOY";
      } else if (operation == 10) {
         this.operationName = "DEPLOY_TASK_UPDATE";
      } else if (operation == 11) {
         this.operationName = "DEPLOY_TASK_DEPLOY";
      } else if (operation == 12) {
         this.operationName = "DEPLOY_TASK_UNDEPLOY";
      } else if (operation == 13) {
         this.operationName = "DEPLOY_TASK_RETIRE";
      } else {
         this.operationName = Integer.toString(operation);
      }

      this.deploymentName = deploymentName;
   }

   public String getOperationName() {
      return this.operationName;
   }

   public void setOperationName(String operationName) {
      this.operationName = operationName;
   }

   public String getDeploymentName() {
      return this.deploymentName;
   }

   public void setDeploymentName(String deploymentName) {
      this.deploymentName = deploymentName;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("operationName=");
      sb.append(this.operationName);
      sb.append("deploymentName=");
      sb.append(this.deploymentName);
      return sb.toString();
   }
}
