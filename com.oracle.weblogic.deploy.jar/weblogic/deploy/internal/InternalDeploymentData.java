package weblogic.deploy.internal;

import java.io.Serializable;
import weblogic.management.deploy.DeploymentData;

public class InternalDeploymentData implements Serializable {
   private int deploymentOperation;
   private String deploymentTaskRuntimeId;
   private int notificationLevel;
   private DeploymentData externalDeploymentData;
   private String deploymentName;

   public DeploymentData getExternalDeploymentData() {
      return this.externalDeploymentData;
   }

   public void setExternalDeploymentData(DeploymentData data) {
      this.externalDeploymentData = data;
   }

   public int getNotificationLevel() {
      return this.notificationLevel;
   }

   public void setNotificationLevel(int level) {
      this.notificationLevel = level;
   }

   public int getDeploymentOperation() {
      return this.deploymentOperation;
   }

   public void setDeploymentOperation(int newOperation) {
      this.deploymentOperation = newOperation;
   }

   public void setDeploymentTaskRuntimeId(String taskId) {
      this.deploymentTaskRuntimeId = taskId;
   }

   public String getDeploymentTaskRuntimeId() {
      return this.deploymentTaskRuntimeId;
   }

   public void setDeploymentName(String name) {
      this.deploymentName = name;
   }

   public String getDeploymentName() {
      return this.deploymentName;
   }
}
