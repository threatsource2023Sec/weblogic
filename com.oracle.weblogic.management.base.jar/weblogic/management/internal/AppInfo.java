package weblogic.management.internal;

import java.io.File;

public class AppInfo {
   private String name;
   private String sourcePath;
   private String planPath;
   private String deployedPlanPath;
   private String type;
   private boolean enableScaffolding;
   private String partitionName;
   private String resourceGroupName;

   public AppInfo() {
   }

   public AppInfo(String name, String sourcePath, String planPath, String deployedPlanPath, String type, boolean enableScaffolding, String partitionName, String resourceGroupName) {
      this.name = name;
      this.sourcePath = sourcePath;
      this.planPath = planPath;
      this.deployedPlanPath = deployedPlanPath;
      this.type = type;
      this.enableScaffolding = enableScaffolding;
      this.partitionName = partitionName;
      this.resourceGroupName = resourceGroupName;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setSourcePath(String sourcePath) {
      this.sourcePath = sourcePath;
   }

   public String getSourcePath() {
      return this.sourcePath;
   }

   public void setPlanPath(String planPath) {
      this.planPath = planPath;
   }

   public String getPlanPath() {
      return this.planPath;
   }

   public void setDeployedPlanPath(String deployedPlanPath) {
      this.deployedPlanPath = deployedPlanPath;
   }

   public String getDeployedPlanPath() {
      return this.deployedPlanPath;
   }

   public String getPlanDir() {
      File planDir = new File(this.planPath);
      return planDir.getParent();
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return this.type;
   }

   public void setEnableScaffolding(boolean enableScaffolding) {
      this.enableScaffolding = enableScaffolding;
   }

   public boolean isEnableScaffolding() {
      return this.enableScaffolding;
   }

   public void setPartitionName(String name) {
      this.partitionName = name;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setResourceGroupName(String name) {
      this.resourceGroupName = name;
   }

   public String getResourceGroupName() {
      return this.resourceGroupName;
   }

   public String toString() {
      return "name:  " + this.name + "\nsourcePath:  " + this.sourcePath + "\nplanPath:  " + this.planPath + "\ndeployedPlanPath:  " + this.deployedPlanPath + "\ntype:  " + this.type + "\nenableScaffolding:  " + this.enableScaffolding + "\npartitionName:  " + this.partitionName + "\nresourceGroupName:  " + this.resourceGroupName;
   }
}
