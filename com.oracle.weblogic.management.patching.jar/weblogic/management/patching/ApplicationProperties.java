package weblogic.management.patching;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import weblogic.management.configuration.AppDeploymentMBean;

public class ApplicationProperties implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String APPLICATION_NAME_KEY = "applicationName";
   public static final String APPLICATION_PATCHED_LOCATION_KEY = "patchedLocation";
   public static final String APPLICATION_BACKUP_LOCATION_KEY = "backupLocation";
   public static final String APPLICATION_PLAN_LOCATION_KEY = "planLocation";
   public static final String APPLICATION_REMOVE_PLAN_KEY = "removePlanOverride";
   public static final String APPLICATION_RESOURCEGROUP_TEMPLATE_KEY = "resourceGroupTemplate";
   public static final String APPLICATION_PARTITION_KEY = "partition";
   String applicationName;
   String patchedLocation;
   String currentLocation;
   String backupLocation;
   String planLocation;
   boolean removePlanOverride;
   String targets;
   String stagingSubdirectory;
   StageMode stageMode;
   Set serverNames;
   Set partitionNames;
   String resourceGroupTemplateName;
   String partitionFieldName;
   Map partitionResourceGroups;
   Set globalResourceGroups;

   public ApplicationProperties() {
      this("", "", "", "", false, "", "");
   }

   public ApplicationProperties(String applicationName, String patchedLocation, String backupLocation, String planLocation, boolean removePlanOverride, String resourceGroupTemplateName, String partitionFieldName) {
      this.removePlanOverride = false;
      this.applicationName = applicationName;
      this.resourceGroupTemplateName = resourceGroupTemplateName;
      this.patchedLocation = InputUtils.sanitizePath(patchedLocation);
      this.backupLocation = InputUtils.sanitizePath(backupLocation);
      this.planLocation = InputUtils.sanitizePath(planLocation);
      this.removePlanOverride = removePlanOverride;
      this.stageMode = null;
      this.currentLocation = "";
      this.serverNames = new HashSet();
      this.globalResourceGroups = new HashSet();
      this.partitionResourceGroups = new HashMap();
      this.partitionNames = new HashSet();
      this.partitionFieldName = partitionFieldName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getPatchedLocation() {
      return this.patchedLocation;
   }

   public void setPatchedLocation(String patchedLocation) {
      this.patchedLocation = patchedLocation;
   }

   public String getCurrentLocation() {
      return this.currentLocation;
   }

   public void setCurrentLocation(String currentLocation) {
      this.currentLocation = currentLocation;
   }

   public String getBackupLocation() {
      return this.backupLocation;
   }

   public void setBackupLocation(String backupLocation) {
      this.backupLocation = backupLocation;
   }

   public String getPlanLocation() {
      return this.planLocation;
   }

   public void setPlanLocation(String planLocation) {
      this.planLocation = planLocation;
   }

   public boolean getRemovePlanOverride() {
      return this.removePlanOverride;
   }

   public void setRemovePlanOverride(boolean removePlanOverride) {
      this.removePlanOverride = removePlanOverride;
   }

   public StageMode getStageMode() {
      return this.stageMode;
   }

   public void setStageMode(StageMode stageMode) {
      this.stageMode = stageMode;
   }

   public void setStageMode(String stageModeStr) {
      StageMode stageMode = ApplicationProperties.StageMode.getStageMode(stageModeStr);
      this.setStageMode(stageMode);
   }

   public boolean isStaged() {
      return this.stageMode == ApplicationProperties.StageMode.STAGE;
   }

   public boolean isNoStaged() {
      return this.stageMode == ApplicationProperties.StageMode.NO_STAGE;
   }

   public boolean isExternalStaged() {
      return this.stageMode == ApplicationProperties.StageMode.EXTERNAL_STAGE;
   }

   public boolean isDefaultStaged() {
      return this.stageMode == ApplicationProperties.StageMode.DEFAULT_STAGE;
   }

   public String getStagingSubdirectory() {
      return this.stagingSubdirectory;
   }

   public void setStagingSubdirectory(String stagingSubdirectory) {
      this.stagingSubdirectory = stagingSubdirectory;
   }

   public void setTargets(String targets) {
      this.targets = targets;
   }

   public String getTargets() {
      return this.targets;
   }

   public void addServerName(String serverName) {
      if (!this.serverNames.contains(serverName)) {
         this.serverNames.add(serverName);
      }

   }

   public Set getServerNames() {
      return this.serverNames;
   }

   public boolean isServerTargeted(String serverName) {
      return this.serverNames.contains(serverName);
   }

   public Set getPartitionNames() {
      return this.partitionNames;
   }

   public void addPartitionName(String partitionName) {
      this.partitionNames.add(partitionName);
   }

   public boolean isPartitionTargeted(String partitionName) {
      return this.partitionNames.contains(partitionName);
   }

   public String getResourceGroupTemplateName() {
      return this.resourceGroupTemplateName;
   }

   public void setResourceGroupTemplateName(String resourceGroupTemplateName) {
      this.resourceGroupTemplateName = resourceGroupTemplateName;
   }

   public String getPartitionFieldName() {
      return this.partitionFieldName;
   }

   public void setPartitionFieldName(String partitionFieldName) {
      this.partitionFieldName = partitionFieldName;
   }

   public void addPartitionResourceGroup(String partitionName, String resourceGroupName) {
      this.partitionResourceGroups.put(partitionName, resourceGroupName);
   }

   public void addGlobalResourceGroup(String resourceGroupName) {
      this.globalResourceGroups.add(resourceGroupName);
   }

   public Set getGlobalResourceGroups() {
      return this.globalResourceGroups;
   }

   public String getPartitionResourceGroup(String partitionName) {
      return (String)this.partitionResourceGroups.get(partitionName);
   }

   static enum StageMode {
      DEFAULT_STAGE(AppDeploymentMBean.DEFAULT_STAGE),
      STAGE("stage"),
      NO_STAGE("nostage"),
      EXTERNAL_STAGE("external_stage");

      String displayString;

      private StageMode(String displayString) {
         this.displayString = displayString;
      }

      public static StageMode getStageMode(String stageModeStr) {
         StageMode mode = null;
         if (stageModeStr != null && !stageModeStr.isEmpty()) {
            if (stageModeStr.equals(DEFAULT_STAGE.displayString)) {
               mode = DEFAULT_STAGE;
            } else if (stageModeStr.equals(STAGE.displayString)) {
               mode = STAGE;
            } else if (stageModeStr.equals(NO_STAGE.displayString)) {
               mode = NO_STAGE;
            } else if (stageModeStr.equals(EXTERNAL_STAGE.displayString)) {
               mode = EXTERNAL_STAGE;
            }
         }

         return mode;
      }
   }
}
